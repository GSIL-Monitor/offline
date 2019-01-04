package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.alibaba.fastjson.JSON;
import com.ctrip.train.kefu.system.job.config.CommonBusiness;
import com.ctrip.train.kefu.system.job.config.MappingConfig;
import com.ctrip.train.kefu.system.job.constants.CRedisKeyConstant;
import com.ctrip.train.kefu.system.job.constants.PropertyFile;
import com.ctrip.train.kefu.system.job.enums.notice.EventTypeEnum;
import com.ctrip.train.kefu.system.job.enums.notice.ProductLineEnum;
import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtPriorityStaff;
import com.ctrip.train.kefu.system.job.worker.domain.StaffPriority;
import com.ctrip.train.kefu.system.job.worker.domain.StaffPriorityResult;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultApportionNotice;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultCollectNotice;
import com.ctrip.train.kefu.system.job.worker.service.notice.NoticeService;
import com.ctrip.train.kefu.system.job.worker.service.staff.StaffService;
import common.credis.CRedisHelper;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.SendMailUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ApportionNoticesUpgradeWorker extends BaseWorker {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ExtPriorityStaff extPriorityStaffImpl;

    @Autowired
    private CommonBusiness commonBusiness;

    @QSchedule(value = "com.ctrip.train.offline.ApportionNoticesUpgradeWorker")
    @Override
    public void doWorker(Parameter parameter) {
        //待分配的通知数据
        List<ResultApportionNotice> noticeComplains =noticeService.searchApportionNoticeByWorker();
        if(noticeComplains!=null&&noticeComplains.size()>0){
            //邮件数据
            StringBuilder airMailContent =new StringBuilder();
            StringBuilder trainMailContent =new StringBuilder();
            //查询在上班的员工 关联可处理的通知
            List<ChatStaffInfo> staffList=staffService.searchWorkingStaff();
            List<String> staffNums =new ArrayList<>();
            for(ChatStaffInfo temp:staffList)
                staffNums.add(temp.getStaffNumber());
            //在上班的员工及处理类型
            List<StaffPriorityResult> staffPriority = extPriorityStaffImpl.searchPrioritys(staffNums);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addMappings(MappingConfig.MapLimit);
            List<StaffPriority> vmlist = modelMapper.map(staffPriority,new TypeToken<List<StaffPriority>>() {}.getType());
            //客服当前待处理通知，处理能力
            List<ResultCollectNotice> staffsAbility =noticeService.searchCollectNoticeGroup();
            //查询除了待分配80状态下的通知用于 同一订单,优先分配至上一次处理的员工
            List<NoticeComplainInfo> notWait = noticeService.searchNoticeNotWait();

            //整合客服可处理通知 及处理能力 待处理数据
            changeUserSolveAbility(vmlist, staffsAbility);

            //初始状态
//            for (StaffPriority sp:vmlist){
//                System.out.println(
//                        sp.getStaffName()+" 通知类型 " + sp.getEnvenType() +" 待处理通知:"+ sp.getNoticeWait() + "   待处理上限： " +sp.getNoticeWaitLimit()
//                                + "    处理能力：" +sp.getSolveAbility() +"   忙闲程度投诉"+sp.getStaffComplainBusy()
//                                +"   忙闲程度通知 " +sp.getStaffNoticeBusy());
//            }
            for (ResultApportionNotice notice:noticeComplains.stream().filter(p->p.getEnvenType()==2).collect(Collectors.toList())){
                int flag=0;
                CLogger.info("开始分配通知",notice.getOrderId()+"  "+notice.getId());
                //优先分配至上次处理的客服
                flag = getPullFlag(vmlist, notWait, notice, flag);
                if(flag==0) {
                    //可处理的客服
                    List<StaffPriority> useingStaff = vmlist.stream().filter(s -> {
                        if (notice.getEnvenType() == s.getEnvenType() && notice.getProductLine().equals(String.valueOf(s.getNoticeProductLine()))) {
                            if(StringUtils.isNotBlank(s.getNoticeTypes())){
                                String[] typeArr = s.getNoticeTypes().split(",");
                                for (String type : typeArr) {
                                    if (Integer.valueOf(type) == notice.getNoticeType())
                                        return true;
                                }
                            }
                        }
                        return false;
                    }).collect(Collectors.toList());
                    if (useingStaff != null && useingStaff.size() > 0) {
                        //分配通知
                        pullNotice(vmlist, notice, useingStaff);
                    } else {
                        //该通知无人分配 初始化邮件信息
                        emailInfo(airMailContent, trainMailContent, notice);
                    }
                }
            }
            //发送邮件
            Map<String,StringBuilder> mailMap=new HashMap<>();
            if (!airMailContent.toString().equals("")&&airMailContent.length()!=0)
                mailMap.put("airMailContent",airMailContent);
            if (!trainMailContent.toString().equals("")&&trainMailContent.length()!=0)
                mailMap.put("trainMailContent",trainMailContent);
            if (!mailMap.isEmpty())
                sendEmail(mailMap);
        }
    }

    private void pullNotice(List<StaffPriority> vmlist, ResultApportionNotice notice, List<StaffPriority> useingStaff) {
        List<StaffPriority> userssorted = new ArrayList<>();
        //分配通知
        if (notice.getEnvenType() == 2) {
            userssorted = useingStaff.stream().filter(o -> o.getComplainWait() < o.getComplainWaitLimit()).sorted(Comparator.comparing(StaffPriority::getSolveAbility))
                    .collect(Collectors.toList());
        } else {
            userssorted = useingStaff.stream().filter(o -> o.getNoticeWait() < o.getNoticeWaitLimit()).sorted(Comparator.comparing(StaffPriority::getStaffNoticeBusy)
                    .thenComparing(o -> -o.getSolveAbility())).collect(Collectors.toList());
        }
        if (userssorted != null && userssorted.size() > 0) {
            //分配通知
            tryPullNotice(notice, userssorted.get(0).getStaffNum(), userssorted.get(0).getStaffName());
            //更新处理能力及待处理
//                            System.out.println("++++++++++++++++++"+userssorted.get(0).getStaffName()+"+++++++++++++++++++++分配通知+++++++++++++++++++++"+notice.getOrderId()+"+++++++++++++++++++++++++");
//                            System.out.println(notice.getOrderId()+"  " + notice.getProductLine()+ "    "+ notice.getEnvenType() );
//                            System.out.println("处理能力");
            changeStaffsAbility(vmlist, userssorted.get(0).getStaffNum(), userssorted.get(0).getEnvenType());

            //分配通知之后
//                            for (StaffPriority sp:vmlist){
//                                System.out.println(
//                                        sp.getStaffName()+" 通知类型 " + sp.getEnvenType()+" 产线 "+sp.getNoticeProductLine()
//                                                +"  待处理通知:"+ sp.getNoticeWait() + "   待处理上限： " +sp.getNoticeWaitLimit()
//                                                +"  待处理投诉："+sp.getComplainWait()+"   待处理上限"+sp.getComplainWaitLimit()
//                                                +"  处理能力：" +sp.getSolveAbility() +"   忙闲程度投诉 " +sp.getStaffComplainBusy()
//                                                +"  忙闲程度通知 " +sp.getStaffNoticeBusy());
//                            }
        }
    }

    private void emailInfo(StringBuilder airMailContent, StringBuilder trainMailContent, ResultApportionNotice notice) {
        CLogger.info("通知无人可分配",notice.getOrderId()+"  "+notice.getId());
        String content= ProductLineEnum.convertByCode(notice.getProductLine()).getProductLineName()+
                EventTypeEnum.convertEventType(notice.getEnvenType()).getName()+
                " 订单号为："+notice.getOrderId()+"; 通知单号为："+notice.getId()
                +" 无人可分配,请及时处理！！！ <br />";
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        //机票通知机票 通知工作时间：6：00-24：00  投诉工作时间：6：00-24：00
        if (changeProductLine(notice.getProductLine()).equals("135")){
             airMailContent.append(content);
        }else{
            //火车票通知1、通知工作时间：9：00-23：00  2、投诉工作时间：9：00-18：00
            if (notice.getEnvenType()==EventTypeEnum.Notice.getValue()&&hour>=9&&hour<=23){
                trainMailContent.append(content);
            }else if (notice.getEnvenType()==EventTypeEnum.Complain.getValue()&&hour>=9&&hour<=18) {
                trainMailContent.append(content);
            }
        }
    }

    /**
     * 优先分配至上次处理的客服
     * @param vmlist
     * @param notWait
     * @param notice
     * @param flag
     * @return
     */
    private int getPullFlag(List<StaffPriority> vmlist, List<NoticeComplainInfo> notWait, ResultApportionNotice notice, int flag) {
        List<NoticeComplainInfo> ncp=notWait.stream().filter(nw->nw.getOrderID().equals(notice.getOrderId())&&nw.getOpUser()!=null
                &&nw.getOpUser().length()!=0&&!nw.getOpUser().equals("System")).collect(Collectors.toList());
        if(ncp!=null&&ncp.size()!=0&&vmlist.size()!=0){
            Map<String,List<StaffPriority>> staffNameMap= vmlist.stream().collect(Collectors.groupingBy(StaffPriority::getStaffNum));
            String staffNum=ncp.get(0).getOpUser().replaceAll("[\\u4e00-\\u9fa5|(|)]", "");
            if (staffNameMap.containsKey(staffNum)){
                //分配通知
                tryPullNotice(notice,staffNum,staffNameMap.get(staffNum).get(0).getStaffName());
                //更改待处理时上线
                changeStaffsAbility(vmlist,staffNum,notice.getEnvenType());
                flag=1;
            }
        }
        return flag;
    }

    /**
     * 整合客服可处理通知 及处理能力 待处理数据
     * @param vmlist
     * @param staffsAbility
     */
    private void changeUserSolveAbility(List<StaffPriority> vmlist, List<ResultCollectNotice> staffsAbility) {
        if(vmlist!=null&&vmlist.size()>0){
            vmlist.stream().forEach(it->{
                if(staffsAbility!=null&&staffsAbility.size()>0){
                    List<ResultCollectNotice> list=staffsAbility.stream().filter(p->{
                        if (p.getOpUserNum().equals(it.getStaffNum())){
                            if(it.getEnvenType()==p.getEnvenType())
                                return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
                    if (list!=null&&list.size()>0){
                        it.setSolveAbility(list.get(0).getSolveAbility());
                        if(EventTypeEnum.Notice.getValue()==it.getEnvenType()){
                            it.setStaffNoticeBusy(new BigDecimal((float)list.get(0).getWaitNoticeCount()/it.getNoticeWaitLimit())
                                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            it.setNoticeWait(list.get(0).getWaitNoticeCount());
                        } else if (EventTypeEnum.Complain.getValue()==it.getEnvenType()){
                            it.setStaffComplainBusy(new BigDecimal((float)list.get(0).getWaitNoticeCount()/it.getComplainWaitLimit())
                                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            it.setComplainWait(list.get(0).getWaitNoticeCount());
                        }
                    }else {
                        it.setStaffNoticeBusy(Double.valueOf("0"));
                        it.setStaffComplainBusy(Double.valueOf("0"));
                        it.setNoticeWait(0);
                        it.setComplainWait(0);
                        it.setSolveAbility(0);
                    }
                }else {
                    it.setNoticeWait(0);
                    it.setComplainWait(0);
                    it.setSolveAbility(0);
                    it.setStaffNoticeBusy(Double.valueOf("0"));
                    it.setStaffComplainBusy(Double.valueOf("0"));
                }
            });
        }
    }

    /**
     * 分配订单
     * @param notice
     * @param opuserNum
     * @param opuserName
     */
    private void tryPullNotice(ResultApportionNotice notice, String opuserNum, String opuserName) {
        //乐观锁 String opUserName,String opUserNum, long noticeId
        int rs= noticeService.updatePulledNoticeById(opuserName,opuserNum,notice.getId());
        CLogger.info("分配是否成功",String.valueOf(rs));
        if(rs>0){
            //增加处理日志
            OperateInfo operateInfo=new OperateInfo();
            operateInfo.setTid(notice.getId());
            operateInfo.setOperateUser("system");
            operateInfo.setOperateComment("系统分配至"+opuserNum+"("+opuserName+")");
            operateInfo.setOperateTime(DateUtils.getCurFullTimestamp());
            operateInfo.setOperateType(81); //备注
            //操作来源 1 携程客服，2 供应商 4 系统操作
            operateInfo.setOperateSource(4);
            noticeService.insertOperateInfo(operateInfo);
            ArrayList<String> noticeLst= new ArrayList<>();
            String listStr= CRedisHelper.get(String.format(CRedisKeyConstant.NOTICE_NOT_HANDLED_BY_STAFF,opuserNum));
            if (StringUtils.isNotEmpty(listStr)){
                List<String> jsonArray = JSON.parseArray(listStr,String.class);
                if (jsonArray!=null&&!jsonArray.isEmpty())
                    noticeLst.addAll(jsonArray);
            }
            noticeLst.add(String.valueOf(notice.getId()));
            CRedisHelper.set(String.format(CRedisKeyConstant.NOTICE_NOT_HANDLED_BY_STAFF,opuserNum),noticeLst,30, TimeUnit.DAYS);
        }
    }
    /**
     * 更新待处理能力
     */
    void changeStaffsAbility( List<StaffPriority> vmlist,String staffNum,int eventType){
        vmlist.stream().forEach(it->{
            if (it.getStaffNum().equals(staffNum)&&it.getEnvenType()==eventType){
                if (EventTypeEnum.Complain.getValue()==eventType){
                    long sum=it.getComplainWait()+1;
                    it.setComplainWait(sum);
                    Double busy=new BigDecimal((float)it.getComplainWait()/it.getComplainWaitLimit()) .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    it.setStaffComplainBusy(busy);
                }else{
                    long sum=it.getNoticeWait()+1;
                    it.setNoticeWait(sum);
                    Double busy=new BigDecimal((float)it.getNoticeWait()/it.getNoticeWaitLimit()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    it.setStaffNoticeBusy(busy);
                }
            }
        });
    }

    /**
     *  专车（火车）通知/投诉的产线分给 国内火车客服 137 》 134
     *  专车（机票）通知/投诉的产线分给 机票客服   138 》 135
     *  国际机票 通知/投诉的产线 国内机票  31 》 135
     * @param productLine
     * @return
     */
    public String changeProductLine(String productLine){
        switch (productLine){
            case "137":
                return "134";
            case "138":
                return "135";
            case "31":
                return "135";
        }
        return productLine;
    }

    /**
     * 发送邮件
     * @return
     */
    public int sendEmail(Map<String,StringBuilder>  mailMap){
        String sender=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_SENDER);
        String ccMail=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_CC);
        String senderName=commonBusiness.getAppSetting(PropertyFile.OFFLINE_MAIL_SENDERNAME);
        List<String> cc=new ArrayList<>();
        cc.add(ccMail);
        int month= DateUtils.getCurrentMonth();
        String title="";
        List<String> receipts=new ArrayList<>();
        try {
            for (Map.Entry<String, StringBuilder> entry : mailMap.entrySet()) {
                if(entry.getKey().equals("trainMailContent")){
                    title =String.format("火车票通知问题类型无人可分",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_TRAIN_RECEIPTS).split(",");
                    for(String s:recipient)
                        receipts.add(s);
                }else if(entry.getKey().equals("airMailContent")){
                    title =String.format("机票通知问题类型无人可分",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_AIR_RECEIPTS).split(",");
                    for(String s:recipient)
                        receipts.add(s);
                }
                SendMailUtils.sendNormalEmail(receipts,cc,sender,senderName,title,entry.getValue().toString());
            }
            return 1;
        } catch (Exception e) {
            CLogger.error("ApportionNoticeWorker邮件发送失败",e);
            return 0;
        }
    }
}






