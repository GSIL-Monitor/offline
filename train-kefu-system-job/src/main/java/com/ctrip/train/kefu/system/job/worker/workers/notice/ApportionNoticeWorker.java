package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.alibaba.fastjson.JSON;
import com.ctrip.train.kefu.system.job.enums.notice.EventTypeEnum;
import com.ctrip.train.kefu.system.job.enums.notice.ProductLineEnum;
import com.ctrip.train.kefu.system.job.constants.CRedisKeyConstant;
import com.ctrip.train.kefu.system.job.constants.PropertyFile;
import com.ctrip.train.kefu.system.job.config.CommonBusiness;
import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtPriorityStaff;
import com.ctrip.train.kefu.system.job.worker.domain.StaffPriorityResult;
import com.ctrip.train.kefu.system.job.worker.entity.notice.OpUserPower;
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
import dao.ctrip.ctrainchat.entity.OfflineStaffPriorityNotice;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ApportionNoticeWorker extends BaseWorker {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CommonBusiness commonBusiness;

    /**
     * 自动分配通知job
     *
     * 为避免不必要的问题 在客服和通知查询中增加产线控制 增加产线需要注意
     * @author wql
     */
    @QSchedule(value = "com.ctrip.train.offline.ApportionNoticeWorker")
    @Override
    public void doWorker(Parameter parameter) {
        //事件类型
        //待分配的通知数据
        List<ResultApportionNotice>  noticeComplains =noticeService.searchApportionNoticeByWorker();
        //待分配通知的员工
        List<OpUserPower> opUserPowerList=getOpUserPower();
        //查询除了待分配80状态下的通知用于 同一订单,优先分配至上一次处理的员工
        List<NoticeComplainInfo> notWait = noticeService.searchNoticeNotWait();
        //分配任务
        if(noticeComplains!=null&&noticeComplains.size()!=0&&opUserPowerList!=null&&opUserPowerList.size()!=0){
            StringBuilder airMailContent =new StringBuilder();
            StringBuilder trainMailContent =new StringBuilder();
            for (ResultApportionNotice notice:noticeComplains){
                CLogger.info("开始分配通知",notice.getOrderId()+"  "+notice.getId());
                int flag=0;
                //可处理的订单的员工
                List<OpUserPower> users = getOpUserPowersList(opUserPowerList, notice);
//                同一订单,优先分配至上一次处理的员工
                flag = getPullFlag(opUserPowerList, notWait, notice, flag, users);
                if(flag==0){
                    //可处理的订单的员工
                    users = getOpUserPowersList(opUserPowerList, notice);
                    //判断要不要发邮件
                    if(users!=null&&users.size()!=0){
                        List <OpUserPower> userssorted=new ArrayList<>();
                        //分配通知
                        if(notice.getEnvenType() == EventTypeEnum.Complain.getValue()) {
                            userssorted=users.stream().filter(o->o.getWaitNotice() < o.getComplainprocessLimit())
                                    .sorted(Comparator.comparing(OpUserPower::getSolveAbility)).collect(Collectors.toList());
                        }else {
                            //设置员工的待处理上限
                            userssorted=users.stream().filter(o->o.getWaitNotice() < o.getNoticeprocessLimit())
                                    .sorted(Comparator.comparing(OpUserPower::getOpUserBusy).thenComparing(o -> -o.getSolveAbility()))
                                    .collect(Collectors.toList());
                        }
                        if(userssorted!=null&&userssorted.size()!=0){
                            tryPullNotice(notice, userssorted.get(0).getOpUserNum(), userssorted.get(0).getOpUserName());
                            //及时修改员工的处理能力及待处理数
                            changeUserPower(opUserPowerList, userssorted.get(0).getOpUserNum());
                        }
                    }
                    else{
                        //该通知无人分配 发送邮件
                        CLogger.info("通知无人可分配",notice.getOrderId()+"  "+notice.getId());
                        String content=ProductLineEnum.convertByCode(notice.getProductLine()).getProductLineName()+
                                EventTypeEnum.convertEventType(notice.getEnvenType()).getName()+
                                " 订单号为："+notice.getOrderId()+"; 通知单号为："+notice.getId()
                                +" 无人可分配,请及时处理！！！ <br />";
                        //机票通知
                        if (ChangeProductLine(notice.getProductLine()).equals("135")){
                            airMailContent.append(content);
                        }else{
                            //火车票通知
                            trainMailContent.append(content);
                        }
                    }
                }
            }
            //在此统一发送邮件
            Map<String,StringBuilder> mailMap=new HashMap<>();
            if (!airMailContent.toString().equals("")&&airMailContent.length()!=0)
                 mailMap.put("airMailContent",airMailContent);
            if (!trainMailContent.toString().equals("")&&trainMailContent.length()!=0)
                mailMap.put("trainMailContent",trainMailContent);
            if(!mailMap.isEmpty())
                SendEmail(mailMap);
        }
    }

    /**
     * 同一订单,优先分配至上一次处理的员工
     * @param opUserPowerList
     * @param notWait
     * @param notice
     * @param flag
     * @param users
     * @return
     */
    private int getPullFlag(List<OpUserPower> opUserPowerList, List<NoticeComplainInfo> notWait, ResultApportionNotice notice, int flag, List<OpUserPower> users) {

        List<NoticeComplainInfo> ncp=notWait.stream().filter(nw->nw.getOrderID().equals(notice.getOrderId())&&nw.getOpUser()!=null
                &&nw.getOpUser().length()!=0&&!nw.getOpUser().equals("System")).collect(Collectors.toList());
        if(ncp!=null&&ncp.size()!=0&&users!=null&&users.size()!=0){
            Map<String,List<OpUserPower>> userMap=users.stream().collect(Collectors.groupingBy(OpUserPower::getOpUserNum));
            List<OpUserPower> ouserlist=new ArrayList<>();
            //可处理的人员
            for (int i=0;i<ncp.size();i++) {
                String opuserNum=ncp.get(i).getOpUser().replaceAll("[\\u4e00-\\u9fa5|(|)]", "");
                if (userMap.containsKey(opuserNum))
                    ouserlist.add(userMap.get(opuserNum).get(0));
            }
            if(ouserlist.size()!=0){
                List <OpUserPower> userssorted=new ArrayList<>();
                //分配通知
                if(notice.getEnvenType()==2) {
                    userssorted=users.stream().sorted(Comparator.comparing(OpUserPower::getSolveAbility)).collect(Collectors.toList());
                }else {
                    userssorted=users.stream().sorted(Comparator.comparing(OpUserPower::getOpUserBusy).thenComparing(o -> -o.getSolveAbility()))
                            .collect(Collectors.toList());
                }
                flag=1;//标记位
                //分配通知
                tryPullNotice(notice, userssorted.get(0).getOpUserNum(), userssorted.get(0).getOpUserName());
                //及时修改员工的处理能力及待处理数
                changeUserPower(opUserPowerList, userssorted.get(0).getOpUserNum());
            }
        }
        return flag;
    }

    /**
     *  专车（火车）通知/投诉的产线分给 国内火车客服 137 》 134
     *  专车（机票）通知/投诉的产线分给 机票客服   138 》 135
     *  国际机票 通知/投诉的产线 国内机票  31 》 135
     * @param productLine
     * @return
     */
    public String ChangeProductLine(String productLine){
        switch (productLine){
            case "31":
                return "135";
            case "137":
                return "134";
            case "138":
                return "135";
        }
        return productLine;
    }
    private List<OpUserPower> getOpUserPowersList(List<OpUserPower> opUserPowerList, ResultApportionNotice notice) {
        //获取可以处理人的列表
        return opUserPowerList.stream().filter(o->{
                //事件类型 若事件类型扩展此处需要修改
                String [] envenTypes=o.getEnvenType().split(",");
                int etFlag=0;
                for (int et=0;et<envenTypes.length&&etFlag==0;et++){
                if(envenTypes[et].equals(String.valueOf(notice.getEnvenType()))){
                    //可处处理产品线
                    etFlag=1;
                    //国际国内机票产线合并
                    if(o.getProductLine().equals(ChangeProductLine(notice.getProductLine()))){
                        if(o.getNoticeType()!=null&&o.getNoticeType().length()!=0){
                            //可处理通知类型
                            String [] noticeTypes=o.getNoticeType().split(",");
                            int ntFlag=0;
                            for(int nt=0;nt<noticeTypes.length&&ntFlag==0;nt++){
                                if(noticeTypes[nt].equals(String.valueOf(notice.getNoticeType()))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     * 重新计算忙闲程度
     * @param opUserPowerList
     * @param users
     */
    private void changeUserPower(List<OpUserPower> opUserPowerList, String users) {
        for(int i=0;i<opUserPowerList.size();i++){
            OpUserPower ou=opUserPowerList.get(i);
            if(ou.getOpUserNum().equals(users)){
                OpUserPower oup=new OpUserPower();
                oup.setOpUserName(ou.getOpUserName());
                oup.setOpUserNum(ou.getOpUserNum());
                oup.setNoticeType(ou.getNoticeType());
                oup.setEnvenType(ou.getEnvenType());
                oup.setProductLine(ou.getProductLine());
                oup.setProcessLimit(ou.getProcessLimit());
                oup.setWaitNotice(ou.getWaitNotice()+1);
                oup.setSolveingNotice(ou.getSolveingNotice()+1);
                oup.setNoticeprocessLimit(ou.getNoticeprocessLimit());
                oup.setComplainprocessLimit(ou.getComplainprocessLimit());
                //重新计算忙闲
                if("1".equals(ou.getEnvenType())&&ou.getNoticeprocessLimit()!=0){
                    oup.setOpUserBusy(new BigDecimal((float)oup.getWaitNotice()/ou.getNoticeprocessLimit())
                            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }else if("2".equals(ou.getEnvenType())&&ou.getComplainprocessLimit()!=0){
                    oup.setOpUserBusy(new BigDecimal((float)oup.getWaitNotice()/ou.getComplainprocessLimit())
                            .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }else {
                    oup.setOpUserBusy(Double.valueOf("1"));
                }
                oup.setSolveAbility(ou.getSolveAbility());
                opUserPowerList.set(i,oup);
            }
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

            //添加分配记录
            OperateInfo operateInfo=new OperateInfo();
            operateInfo.setTid(notice.getId());
            operateInfo.setOperateUser("system");
            operateInfo.setOperateComment("系统分配至"+opuserNum+"("+opuserName+")");
            operateInfo.setOperateTime(DateUtils.getCurFullTimestamp());
            //备注
            operateInfo.setOperateType(81);
            operateInfo.setOperateSource(4);
            noticeService.insertOperateInfo(operateInfo);

            ArrayList<String> noticeLst= new ArrayList<>();
            String listStr= CRedisHelper.get(String.format(CRedisKeyConstant.NOTICE_NOT_HANDLED_BY_STAFF,opuserNum));
            if (StringUtils.isNotEmpty(listStr)){
                List<String> jsonArray = JSON.parseArray(listStr,String.class);
                if (jsonArray!=null&&!jsonArray.isEmpty()){
                    noticeLst.addAll(jsonArray);
                }
            }
            noticeLst.add(String.valueOf(notice.getId()));
            CRedisHelper.set(String.format(CRedisKeyConstant.NOTICE_NOT_HANDLED_BY_STAFF,opuserNum),noticeLst,30, TimeUnit.DAYS);
        }
    }

    /**
     * 获取当前上班的人员
     * @return
     */
    private List<ChatStaffInfo> searchOnlineStaff() {
        List<ChatStaffInfo> staffIlist=staffService.searchWorkingStaff();
            return staffIlist;
    }
    /**
     * 设置在线客服分配通知时的权重
     *
     * @return
     */
    private  List<OpUserPower> getOpUserPower(){
        //当前系统客服处理任务明细
        List<ResultCollectNotice> rlist=noticeService.searchCollectNotice();
        List<OpUserPower> userpList=new ArrayList<>();
        //在线客服列表
        List<ChatStaffInfo> cslist=searchOnlineStaff();
        if(cslist!=null&&cslist.size()!=0){
            if (rlist!=null&&rlist.size()!=0){
                Map<String, List<ResultCollectNotice>> userMap = rlist.stream().collect(Collectors.groupingBy(ResultCollectNotice::getOpUserNum));
                for (ChatStaffInfo csi:cslist){
                    //设置在线各个员工分配比重
                    OpUserPower oup= new OpUserPower();
                    oup.setOpUserName(csi.getStaffName());
                    oup.setOpUserNum(csi.getStaffNumber());
                    //先默认设置为五
                    if(!userMap.containsKey(csi.getStaffNumber())){
                        oup.setOpUserBusy(0);
                        oup.setWaitNotice(0);
                        oup.setSolveAbility(0);
                        oup.setSolveingNotice(0);
                    }else{
                        if (csi.getEventType()==1&&csi.getNoticeWaitLimit()!=0){
                            oup.setOpUserBusy(new BigDecimal((float)userMap.get(csi.getStaffNumber()).get(0)
                                    .getWaitNoticeCount()/csi.getNoticeWaitLimit()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }else if (csi.getEventType()==2&&csi.getComplainWaitLimit()!=0){
                            oup.setOpUserBusy(new BigDecimal((float)userMap.get(csi.getStaffNumber()).get(0)
                                    .getWaitNoticeCount()/csi.getComplainWaitLimit()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }else {
                            oup.setOpUserBusy(Double.valueOf("1"));
                        }
                        oup.setWaitNotice(userMap.get(csi.getStaffNumber()).get(0).getWaitNoticeCount());
                        oup.setSolveAbility(userMap.get(csi.getStaffNumber()).get(0).getSolveAbility());
                        oup.setSolveingNotice(userMap.get(csi.getStaffNumber()).get(0).getSolveingNoticeCount());
                    }
                    oup.setEnvenType(String.valueOf(csi.getEventType()));
                    oup.setProductLine(String.valueOf(csi.getNoticeProductLine()));
                    oup.setNoticeType(csi.getNoticeType());
                    oup.setComplainprocessLimit(csi.getComplainWaitLimit());
                    oup.setNoticeprocessLimit(csi.getNoticeWaitLimit());
                    userpList.add(oup);
                }
            }else{
                //初次分配设置
                for (ChatStaffInfo csi:cslist){
                    //设置在线各个员工分配比重
                    OpUserPower oup= new OpUserPower();
                    oup.setOpUserName(csi.getStaffName());
                    oup.setOpUserNum(csi.getStaffNumber());
                    oup.setOpUserBusy(0);
                    oup.setWaitNotice(0);
                    oup.setSolveAbility(0);
                    oup.setEnvenType(String.valueOf(csi.getEventType()));
                    oup.setProductLine(String.valueOf(csi.getNoticeProductLine()));
                    oup.setNoticeType(csi.getNoticeType());
                    oup.setSolveingNotice(0);
                    oup.setComplainprocessLimit(csi.getComplainWaitLimit());
                    oup.setNoticeprocessLimit(csi.getNoticeWaitLimit());
                    userpList.add(oup);
                }
            }
            return userpList.stream().sorted(Comparator.comparing(OpUserPower::getOpUserBusy).reversed().
                    thenComparing((OpUserPower::getSolveAbility))).collect(Collectors.toList());
        }
        return null;
    }
    /**
     * 获得处理事件类型
     * 处理通知类型：按位与0不处理通知、2^0处理通知、2^1处理投诉、2^2处理领班
     * @return
     */
    public String getEventTypes(Integer eventType){
        StringBuilder builder=new StringBuilder("0");
        if(eventType==null||eventType!=0){
            //现在通知投诉领班的值正好是1、2、4，暂时可以这么玩，如有拓展，需要重新改写
            List<Integer> eventValues= Arrays.stream(EventTypeEnum.values()).map(EventTypeEnum::getValue).collect(Collectors.toList());
            eventValues.forEach(it->{
                if((eventType&it)>0){
                    builder.append(",").append(it);
                }
            });
        }
        return builder.toString();
    }
    /**
     * 发送邮件
     * @return
     */
    public int SendEmail(Map<String,StringBuilder>  mailMap){
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
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_TRAIN_UMANNED).split(",");
                    for(String s:recipient){
                        receipts.add(s);
                    }
                }else if(entry.getKey().equals("airMailContent")){
                    title =String.format("机票通知问题类型无人可分",month);
                    String [] recipient=commonBusiness.getAppSetting(PropertyFile.OFFLINE_AIR_RECEIPTS).split(",");
                    for(String s:recipient){
                        receipts.add(s);
                    }
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
