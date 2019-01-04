package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoitceVendor;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeChatStaffInfo;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeComplainInfo;
import com.ctrip.train.kefu.system.offline.notice.enums.*;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeService;
import com.ctrip.train.kefu.system.offline.notice.service.OperateInfoService;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeDetail;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.ResponseUrge;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.RpsImportantNotice;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.VmSendNoticeInfo;
import com.ctrip.train.kefu.system.offline.notice.vm.request.VmImportantNotice;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import common.util.DateUtils;
import common.util.SendMailUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private ExtNoticeComplainInfo noticeService;

    @Autowired
    private ExtNoticeChatStaffInfo chatStaffInfo;

    @Autowired
    private  NoticeEnumServiceImpl enumService;

    @Autowired
    private OperateInfoService operateInfoService;

    /**
     *根据订单号获取通知列表
     */
    public List<VmNoticeDetail> getNoticeList(String orderid ,Integer type) {
        NoticeComplainInfo model = new NoticeComplainInfo();
        if (type != null && type == 2) {
            model.setEnvenType(2);//查投诉
        }
        model.setOrderID(orderid);
        model.setIsDelete(0);

        List<NoticeComplainInfo> lst = new ArrayList<>();
        try {
            lst = noticeService.queryLike(model);
        } catch (Exception ex) {
            CLogger.error("noticeVendorList", ex);
        }
        if (lst != null) {
            lst = lst.stream()
                    .filter(p -> p.getEnvenType() == 1 || p.getEnvenType() == 2 || p.getEnvenType() == 4)
                    .sorted(Comparator.comparing(NoticeComplainInfo::getSendTime))
                    .collect(Collectors.toList());
            // 转化vm
            ModelMapper modelMapper = new ModelMapper();
            List<VmNoticeDetail> vm = modelMapper.map(lst, new TypeToken<List<VmNoticeDetail>>() {
            }.getType());
            if (vm != null && vm.size() > 0) {
                //模型隐射通知类型
                List<ScmSmallEnum> scmSmallEnumList = enumService.GetNoticeTypeEnum(vm.get(0).getProductLine());
                vm.forEach(p -> {
                    List<ScmSmallEnum> scm = scmSmallEnumList.stream()
                            .filter(q -> q.getTid().equals(Long.valueOf(p.getNoticeType())))
                            .collect(Collectors.toList());
                    if (scm != null && scm.size() > 0) {
                        p.setNoticeTypoStr(scm.get(0).getFieldName());
                    } else {
                        List<ScmSmallEnum> scmTemp = scmSmallEnumList.stream()
                                .filter(q -> q.getFieldValue() != null && q.getFieldValue().equals(String.valueOf(p.getNoticeType())))
                                .collect(Collectors.toList());
                        if (scmTemp != null && scmTemp.size() > 0) {
                            p.setNoticeTypoStr(scmTemp.get(0).getFieldName());
                        } else {
                            p.setNoticeTypoStr("-");
                        }
                    }
                });
            }
            return vm;
        }
        return null;
    }


    /**
     *通知备注
     */
    public boolean remarksNotice(Long noticeId) {
        try {
            NoticeComplainInfo notice = noticeService.queryByPk(noticeId);
            if (notice == null)
                return false;

            //已完成 ，转投诉 ，转领班 不可催
            if (notice.getNoticeState() == 83 || notice.getNoticeState() == 94 || notice.getNoticeState() == 100) {
                return false;
            }
        } catch (Exception ex) {
            CLogger.warn("remarksNotice", ex);
            return false;
        }
        return  true;
    }
    /**
     *通知催处理
     */
    public ResponseUrge reminderNotice(Long noticeId) {
        try {
            NoticeComplainInfo notice = noticeService.queryByPk(noticeId);
            if (notice == null)
                return ResponseUrge.fail("未查到该通知!");

            //已完成 ，转投诉 ，转领班 不可催
            if (notice.getNoticeState()==83||notice.getNoticeState()==94||notice.getNoticeState()==100)
                return ResponseUrge.fail("已完成 ，转投诉 ，转领班 不可催!");

            //待处理订单催三次之后直接转领班通知
            if (notice.getOpCount()>=3){
                if (notice.getNoticeState()==80){
                    List<String> receipts=new ArrayList<>();
                    receipts.add(QConfigHelper.getAppSetting("mailreceipts"));
                    String title="订单多次催处理邮件";
                    //给领班发邮件
                    String emailBody = String.format("<p>订单号：%s 已催 %s 次未处理</p><p>发送时间：%s </p><br/>",
                            notice.getOrderID(),
                            notice.getOpCount() + 1,
                            notice.getSendTime());
                    SendMailUtils.sendNormalEmail(receipts,receipts,"TrainOffline@ctrip.com",
                            "TrainOffline",title,emailBody);
                    //offline 逻辑 直接改成领班通知
                    notice.setEnvenType(4);
                    //此处应该将处理人置空
//                    if (StringUtils.isNotBlank(notice.getOpUser())){
//                        notice.setOpUser(null);
//                        notice.setOpUserName(null);
//                        notice.setOpUserNum(null);
//                    }
                    //增加日志系统转成领班通知
                    operateInfoService.insertOperate(notice.getID(),"通知催三次仍是待处理状态，系统升级为领班",
                            "system",NoticeOperateTypeEnum.TurnLeader);
                }
            }
            //更新催数据
            notice.setOpCount(notice.getOpCount() + 1);
            //手动更新当前时间
            notice.setDatachangeLastTime(DateUtils.getCurFullTimestamp());
            notice.setLastTimeUrge(DateUtils.getCurFullTimestamp());
            noticeService.update(notice);
            if (notice.getOpUser()!=null) {
                int state = chatStaffInfo.getStaffState(notice.getOpUser().split("\\(")[0]);
                if (state == 2 && notice.getNoticeState() != 80) {
                    if (noticeService.updateNoticeStateByNoticeId(noticeId, NoticeStateEnum.Wait.getState())==1)
                        return ResponseUrge.success("催成功,客服不在线转待分配成功！");
                    return ResponseUrge.fail("催成功,客服不在线转待分配失败！");
                }else{
                    //通知待处理和暂缓的订单，点击催之后在“立即处理页”标红置顶
                    // 员工在线
                    if(notice.getNoticeState()==NoticeStateEnum.WaitDeal.getState()
                            ||notice.getNoticeState()==NoticeStateEnum.Deferred.getState()
                            ||(notice.getAppointedProcessTime() != null && !notice.getAppointedProcessTime().equals("0001-01-01 00:00:00"))){
                        notice.setNoticeState(NoticeStateEnum.Assigned.getState());
                        if (noticeService.updateNoticeStateById(notice.getID(),notice.getNoticeState())==1)
                            return ResponseUrge.success("催成功,待处理/暂缓通知转立即处理页成功！");
                        return ResponseUrge.fail("催成功,待处理/暂缓通知转立即处理页失败！");
                    }
                }
            }
            return ResponseUrge.success("催成功！");
        } catch (Exception ex) {
            CLogger.warn("ReminderNotice", ex);
            return ResponseUrge.fail("催失败，请联系开发人员！");
        }
    }


    @Override
    public List<RpsImportantNotice> importantNotice(String orderId) {
        //发过重要通知的进入是查看页面，没发过的进去看到是发送页面
        List<RpsImportantNotice> vmNotices=null;
        List <NoticeComplainInfo> noticeList = noticeService.searchNoticeByOrderId(orderId);
        if(noticeList!=null&&noticeList.size()>0){
            ModelMapper modelMapper = new ModelMapper();
            vmNotices = modelMapper.map(noticeList,new TypeToken<List<RpsImportantNotice>>() {}.getType());
        }
        return vmNotices;
    }

    @Override
    public VmSendNoticeInfo getSendNoticeInfo(String orderId,String contactPhone ,String contactUser) {
        VmSendNoticeInfo sendNotice= new VmSendNoticeInfo();
        //调接口查询客户信息
        sendNotice.setOrderId(orderId);
        sendNotice.setContactPhone(contactPhone);
        sendNotice.setContactUser(contactUser);
        sendNotice.setEventTypes(Arrays.stream(EventTypeEnum.values()).collect(Collectors.toList()));
        sendNotice.setEmergency(Arrays.stream(EmergencyStateEnum.values()).collect(Collectors.toList()));
        ScmSmallEnum req=new ScmSmallEnum();
        req.setIsDeleted("0");
        req.setFieldType(ScmEnumFieldTypeEnum.NoticeSource.getFieldType());

//        req.setFieldType(productLineEnum.getLeaderNoticeType());

        sendNotice.setNoticeSources(noticeService.searchScmSmallEnum(req));
        return sendNotice;
    }

    @Override
    public int sendImportantNotice(VmImportantNotice request) {
        try {
            NoticeComplainInfo notice=new NoticeComplainInfo();
            notice.setAppointedProcessTime(DateUtils.parseTimestamp(request.getAppointedProcessTime(),DateUtils.YMDHMS_UNDERLINED));
            notice.setContactPhone(request.getContactPhone());
            notice.setContactUser(request.getContactUser());
            notice.setContents(request.getContents());
            notice.setDataSource(request.getChannelSource());
            notice.setEmergeState(request.getEmergency());
            notice.setEnterDate(DateUtils.getCurFullTimestamp());
            notice.setEnterUser(request.getEnterUser());
            notice.setEnvenType(request.getEventType());
            notice.setNoticeType(request.getFirstType());
            notice.setNoticeSecondType(request.getSecondType());
            notice.setNoticeState(NoticeStateEnum.Wait.getState());
            notice.setOrderID(request.getOrderId());
            notice.setProductLine(String.valueOf(request.getProductLine()));
            notice.setSendTime(DateUtils.getCurFullTimestamp());
            notice.setComplainSource(request.getNoticeSource());
            notice.setOrderType(request.getOrderType());
            return noticeService.insert(notice);
        } catch (Exception e) {
            CLogger.error("sendImportantNotice",e);
            return 0;
        }
    }

    @Override
    public int search119Notice(String orderId) {
        return noticeService.search119NoticesCount(orderId);
    }

    @Override
    public int searchImportantNoticeCount(String orderId) {

        return noticeService.searchNoticeByOrderIdCount(orderId);
    }


}
