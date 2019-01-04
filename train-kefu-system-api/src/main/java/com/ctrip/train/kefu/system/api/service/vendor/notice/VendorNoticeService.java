package com.ctrip.train.kefu.system.api.service.vendor.notice;

import com.ctrip.platform.dal.dao.*;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.soa.train.tieyoubookingservice.v1.GetOfflineOrderDetailInnerV2Response;
import com.ctrip.train.kefu.system.api.contract.*;
import com.ctrip.train.kefu.system.api.dao.notice.ExtNoitceVendor;
import com.ctrip.train.kefu.system.api.dao.notice.ExtNotice;
import com.ctrip.train.kefu.system.api.dao.notice.ExtOperateInfo;
import com.ctrip.train.kefu.system.api.dao.scm.ExtScmEnum;
import com.ctrip.train.kefu.system.api.dao.staff.ExtStaffInfo;
import com.ctrip.train.kefu.system.api.dao.user.ExtUserBasicInfo;
import com.ctrip.train.kefu.system.api.domain.notice.DmNoticeVendor;
import com.ctrip.train.kefu.system.api.domain.notice.DmNoticeVendorCondition;
import com.ctrip.train.kefu.system.api.domain.notice.enums.NoticeOperateEnum;
import com.ctrip.train.kefu.system.api.domain.notice.enums.NoticeStateEnum;
import com.ctrip.train.kefu.system.api.domain.notice.enums.VendorEnum;
import com.ctrip.train.kefu.system.api.domain.user.DmUserInfo;
import com.ctrip.train.kefu.system.api.infrastructure.helper.page.PageRequest;
import com.ctrip.train.kefu.system.api.service.vendor.notice.domainService.DmNoticeService;
import com.ctrip.train.kefu.system.client.offline.flight.FlightOrderContract;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import common.util.DateUtils;
import common.util.SendMailUtils;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeVendor;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendorNoticeService {

    @Autowired
    private ExtNoitceVendor noitceVendor;

    @Autowired
    private ExtNotice extNotice;

    @Autowired
    private ExtScmEnum scmEnum;

    @Autowired
    private FlightOrderContract orderContract;

    @Autowired
    private ExtOperateInfo operateInfo;

    @Autowired
    private DmNoticeService dmNoticeService;

    @Autowired
    private ExtStaffInfo extStaff;

    @Autowired
    private ExtUserBasicInfo extUserBasicInfo;

    //dal client
    private DalClient dalClient = DalClientFactory.getClient(NoticeComplainInfo.class.getAnnotation(Database.class).name());

    /**
     * 根据主键获取供应商通知数据
     * 分页
     */
    public GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request)  {
        GetVendorNoticeRespose respose = new GetVendorNoticeRespose();
        if (request == null || request.getNoticeId() == null) {
            return respose;
        }
        DmNoticeVendor vendor = noitceVendor.getVendorNotice(request.getNoticeId());
        if (vendor != null) {
            VendorNotice notice = dmNoticeService.mapVendorNotice(vendor);
            respose.setVendorNotice(notice);
        }
        return respose;
    }

    /**
     * 获取供应商待处理通知
     */
    public GetPendingVendorNoticeRespose getVendorPendingList(GetPendingVendorNoticeRequest request)  {
        GetPendingVendorNoticeRespose respose = new GetPendingVendorNoticeRespose();
        List<DmNoticeVendor> verdorList = noitceVendor.getVendorPendingList(request.getVendorCode());
        if (verdorList!=null&&verdorList.size()>0) {
            List<VendorNotice> vm = dmNoticeService.mapVendorNotice(verdorList);

            //初始化操作记录
            vm = dmNoticeService.initVendorOperateInfos(vm);
            respose.setVendorNotice(vm);
        }
        return respose;
    }



    /**
     * 获取供应商通知
     */
    public GetVendorNoticeListRespose getVendorList(GetVendorNoticeListRequest request)  {
        PageRequest<DmNoticeVendorCondition> search = new PageRequest<>();
        //初始化查询数据
        getCondition(request,search);

        GetVendorNoticeListRespose respose = new GetVendorNoticeListRespose();
        respose.setPageIndex(request.getPageIndex());
        respose.setPageSize(request.getPageSize());

        //获取通知数据
        List<DmNoticeVendor> list = noitceVendor.getVendorList(search);
        List<VendorNotice> vm = dmNoticeService.mapVendorNotice(list);
        //初始化操作记录
        dmNoticeService.initVendorOperateInfos(vm);
        //初始化通知类型
        List<ScmSmallEnum>  scmSmallEnums= scmEnum.GetEnumList("TYZXFlightNoticeType");
        vm.forEach((VendorNotice p) ->{
            //一级分类
            Optional<ScmSmallEnum> scmEnum=scmSmallEnums.stream().filter(q-> q.getTid().equals(Long.valueOf(p.getNoticeType()))).findFirst();
            scmEnum.ifPresent(r -> p.setNoticeTypeName(r.getFieldName()));

            if (p.getNoticeSecondType()!=null) {
                //二级分类
                Optional<ScmSmallEnum> scmSecondEnum = scmSmallEnums.stream().filter(q -> q.getTid().equals(Long.valueOf(p.getNoticeSecondType()))).findFirst();
                scmSecondEnum.ifPresent(r -> p.setNoticeSecondTypeName(r.getFieldName()));
            }
        });

        //获取数量
        int count = noitceVendor.getVendorCount(search.getCondition());

        respose.setCount(count);
        respose.setVendorNotice(vm);

        return respose;
    }

    /**
     * 初始化查询条件
     */
    private  void getCondition(GetVendorNoticeListRequest request,PageRequest<DmNoticeVendorCondition> search) {
        search.setPageIndex(request.getPageIndex());
        search.setPageSize(request.getPageSize());
        if (request.getCondition() != null) {
            DmNoticeVendorCondition dm = new DmNoticeVendorCondition();
            dm.setVerdorCode(request.getCondition().getVendorCode());
            dm.setOrderId(request.getCondition().getOrderId() != null ? request.getCondition().getOrderId().trim() : null);
            dm.setSendName(request.getCondition().getSendName() != null ? request.getCondition().getSendName().trim() : null);
            dm.setStartTime(DateUtils.format(DateUtils.parseDate(request.getCondition().getStartTime())));
            dm.setOpUserType(request.getCondition().getOpUserType());
            dm.setNoticeStatus(request.getCondition().getNoticeStatus() == null ? 0 : request.getCondition().getNoticeStatus());
            dm.setSendType(request.getCondition().getSendType() != null ? Integer.valueOf(request.getCondition().getSendType()) : 0);
            if (request.getCondition().getEndTime() != null) {
                dm.setEndTime(DateUtils.format(DateUtils.addDays(Objects.requireNonNull(DateUtils.parseDate(request.condition.getEndTime())), 1)));
            }
            dm.setNoticeType(request.getCondition().getNoticeType());
            //供应商有俩种状态：1 待处理 2 已解决
            if (request.getCondition().getNoticeState() != null) {
                if (request.getCondition().getNoticeState().equals(2)) {
                    List<Integer> noticeStates = Arrays.asList(83, 94, 100);
                    dm.setNoticeStates(noticeStates);
                } else {
                    //没有解决的通知在供应商系统都视为待处理
                    List<Integer> noticeStates = Arrays.asList(80, 81, 82, 84, 102);
                    dm.setNoticeStates(noticeStates);
                }
            }
            search.setCondition(dm);
        }
    }

    /**
     * 供应商发送通知
     */
    public SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request)  {
        SendVendorNoticeRespose respose =new  SendVendorNoticeRespose();
        if (request == null || request.getVendorNotice() == null) {
            respose.setResult(false);
            respose.setMessage("参数不可为空");
            return respose;
        }

        NoticeComplainInfo info = dmNoticeService.mapNoticeComlainInfo(request.getVendorNotice());
        //机票详情接口
        GetOfflineOrderDetailInnerV2Response orderDetail = orderContract.getOrderDedetail(request.getVendorNotice().getOrderId());
        if (orderDetail == null || orderDetail.getOrderInfo() == null) {
            respose.setResult(false);
            respose.setMessage("订单号错误或订单不存在");
            return respose;
        }

        //通知未解决不可重复发
        List<DmNoticeVendor> notice= noitceVendor.getVendorNotice(request.getVendorNotice().getOrderId());
        if (notice!=null&&notice.size()>0) {
            if (notice.stream().anyMatch(p -> p.getNoticeState() != 83)) {
                respose.setResult(false);
                respose.setMessage("该订单已发送过工单，不可重复发送");
                return respose;
            }
        }

        //机票产品线
        if (orderDetail.getOrderInfo().getOrderSourceFlag() == 0) {
            info.setProductLine("135");
        } else {
            info.setProductLine("31");
        }

        if (orderDetail.getOrderInfo().getContactInfo() != null) {
            info.setContactUser(orderDetail.getOrderInfo().getContactInfo().getContactName());
            info.setContactPhone(orderDetail.getOrderInfo().getContactInfo().getContactPhone());
        }

        VendorNotice vendorNotice = request.getVendorNotice();
        NoticeVendor vendor = new NoticeVendor();
        vendor.setContents(vendorNotice.getContents());
        vendor.setNoticeId(vendorNotice.getNoticeId());

        //供应商信息
        VendorEnum vendorEnum = VendorEnum.convertVendor(vendorNotice.getVendorCode());
        if (vendorEnum!=null){
            vendor.setVerdorCode(vendorEnum.getCode());
            vendor.setVerdorName(vendorEnum.getName());
        }
        vendor.setAppointedProcessTime(DateUtils.parseTimestamp(request.getVendorNotice().getAppointedProcessTime()));

        //初始化操作数据
        vendor.setOpUser(vendorNotice.getSendName());
        vendor.setOpTime(DateUtils.getCurFullTimestamp());
        vendor.setOpUserType(2);//1客服 2供应商

        //发送人信息
        vendor.setSendCode(vendorNotice.getSendCode());
        vendor.setSendName(vendorNotice.getSendName());
        vendor.setSendTime(DateUtils.getCurFullTimestamp());
        vendor.setSendType(2);//1 客服发送 2 供应商发送
        vendor.setIsDelete(0);

        try {
            dalClient.execute(client -> {
                KeyHolder keyHolder = new KeyHolder();
                int result = extNotice.insertWithKeyHolder(keyHolder,info);
                if (result > 0) {
                    long noticeid = keyHolder.getKey().longValue();
                    vendor.setNoticeId(noticeid);
                    noitceVendor.insert(vendor);
                }
                respose.setResult(true);
                return  true;
            }, new DalHints());

        } catch (Exception ex) {
            CLogger.error("sendVendorNotice", ex);
            respose.setResult(false);
            respose.setMessage("数据保存失败");
            return respose;
        }
        return respose;
    }


    /**
     * 获取供应商通知类型数据
     */
    public GetScmEnumRespose getNoticeTypeList(GetScmEnumRequest request){
        GetScmEnumRespose respose =new  GetScmEnumRespose();
        List<ScmSmallEnum>  scmSmallEnums= scmEnum.GetEnumList("TYZXFlightNoticeType");
        if (scmSmallEnums!=null&&scmSmallEnums.size()>0){
            List<ScmEnum> vm =dmNoticeService.mapScmEnum(scmSmallEnums);
            respose.setScmEnum(vm);
        }
        return respose;
    }


    /**
     *根据通知id 获取通知操作记录
     */
    public GetNoticeOperateRespose getOperateList(Long noticeid)  {
        GetNoticeOperateRespose respose=new GetNoticeOperateRespose();
        List<Long> noticeIds=new  ArrayList<>();
        noticeIds.add(noticeid);
        try {
            List<OperateInfo> lst=operateInfo.getOperateInfos(noticeIds);
            if (lst!=null) {
                lst = lst.stream()
                        .sorted(Comparator.comparing(OperateInfo::getOperateTime))
                        .collect(Collectors.toList());
            }
            assert lst != null;
            List<Operate> vm =dmNoticeService.mapOperate(lst);
            respose.setOperateInfo(vm);
            return respose;
        }
        catch (Exception ex)
        {
            CLogger.warn("getOperateList",ex);
            return  null;
        }
    }

    /**
     *保存供应商备注内容
     */
    public  AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request){
        AddNoticeOperateRespose respose=new AddNoticeOperateRespose();
        try {
            OperateInfo info=dmNoticeService.mapOperate(request.getOperate());
            if (operateInfo.insert(new DalHints(),info)>0){
                NoticeVendor vendor=new  NoticeVendor();
                vendor.setOpUserType(2);
                vendor.setOpUser(info.getOperateUser());
                vendor.setOpTime(DateUtils.getCurFullTimestamp());
                vendor.setNoticeId(request.getOperate().getNoticeId());
                noitceVendor.updateOpdate(vendor);
                respose.setResult(true);
            }
            else {
                respose.setResult(false);
            }
        }
        catch (Exception ex)
        {
            CLogger.warn("addNoticeOperate",ex);
            respose.setResult(false);
        }
        return respose;
    }


    /**
     * 供应商催单
     */
    public ReminderKefuNoticeResponseType reminderKefuNotice(Long noticeId,String contents) {
        ReminderKefuNoticeResponseType responseType = new ReminderKefuNoticeResponseType();
        DmNoticeVendor notice = noitceVendor.getVendorNotice(noticeId);
        if (notice == null) {
            responseType.setIsSuccess(false);
            responseType.setMsg("通知不存在");
            return responseType;
        }

        //已完成 ，转投诉 ，转领班 不可催
        if (notice.getNoticeState() == 83) {
            responseType.setIsSuccess(false);
            responseType.setMsg("通知已解决，无法催单");
            return responseType;
        }

        //催单间隔15分钟
        GetNoticeOperateRespose operateRespose = getOperateList(noticeId);
        if (operateRespose.getOperateInfo() != null && operateRespose.getOperateInfo().size() > 0) {
            Optional<Operate> operate = operateRespose.getOperateInfo().stream()
                    .filter(p -> p.getOperateType() == 104)
                    .max(Comparator.comparing(Operate::getOperateTime));
            if (operate.isPresent()) {
                boolean cenre = Objects.requireNonNull(DateUtils.addMinutes(Objects.requireNonNull(DateUtils.parseDate(operate.get().getOperateTime(),
                        DateUtils.YMDHMS_UNDERLINED)), 15)).after(DateUtils.getDateFromTimestamp(DateUtils.getCurFullTimestamp()));
                if (cenre) {
                    responseType.setIsSuccess(false);
                    responseType.setMsg("两次催促时间间隔太短，已在处理中，请耐心等待哦~");
                    return responseType;
                }
            }
        }

        //待分配订单催三次之后发邮件
        if (notice.getOpCount() != null && notice.getOpCount() >= 3) {
            if (notice.getNoticeState() == 80) {
                List<String> receipts = new ArrayList<>();
                receipts.add(QConfigHelper.getAppSetting("mailreceipts"));
                String title = "订单多次催处理邮件";

                //给领班发邮件
                String emailBody = String.format("<p>订单号：%s 已催 %s 次未处理</p><p>发送时间：%s </p><br/>",
                        notice.getOrderId(),
                        notice.getOpCount() + 1,
                        notice.getSendTime());
                SendMailUtils.sendNormalEmail(receipts, receipts, "TrainOffline@ctrip.com",
                        "TrainOffline", title, emailBody);
            }
        }
        noitceVendor.reminderVendoNotice(noticeId);//更新数据

        //已经分配的订单，如果处理人在线通知状态改成待处理，如果处理人不在线通知改成待分配，重新分配
        reminderNoitceState(notice);

        //添加操作记录
        String content = notice.getVerdorName() + "催:" + contents;

        //客服发送工单验证处理人存不存在
        DmUserInfo dmUserInfo = extUserBasicInfo.getUserInfo(notice.getVerdorCode(), notice.getSendCode());
        if (dmUserInfo == null) {
            dmNoticeService.insertOperate(noticeId, content, notice.getVerdorName(),
                    NoticeOperateEnum.Urge.getState(), 2);
        } else {
            dmNoticeService.insertOperate(noticeId, content, notice.getVerdorName() + dmUserInfo.getRealName(),
                    NoticeOperateEnum.Urge.getState(), 2);
        }


        responseType.setIsSuccess(true);
        return responseType;
    }

    /**
     * 客服催供应商
     */
    public  ReminderVendorNoticeResponseType reminderVendorNotice(Long noticeId,String contents) {

        ReminderVendorNoticeResponseType responseType = new ReminderVendorNoticeResponseType();
        DmNoticeVendor notice = noitceVendor.getVendorNotice(noticeId);
        if (notice == null) {
            responseType.setIsSuccess(false);
            responseType.setMsg("通知不存在");
            return responseType;
        }

        //已完成 ，转投诉 ，转领班 不可催
        if (notice.getNoticeState() == 83) {
            responseType.setIsSuccess(false);
            responseType.setMsg("通知已解决，无法催单");
            return responseType;
        }


        DmUserInfo dmUserInfo = extUserBasicInfo.getUserInfo(notice.getVerdorCode(), notice.getSendCode());

        //待分配订单催三次之后发邮件
        if (dmUserInfo != null && notice.getOpCount() != null && notice.getOpCount() >= 3) {
            List<String> receipts = new ArrayList<>();
            receipts.add(dmUserInfo.getEmail());
            String title = "工单催处理邮件";

            String emailContent = "<p>订单号：%s 已催 %s 次未处理</p><p>发送时间：%s </p><br/> ";
            emailContent += "<a href='http://supplier.tieyou.com/notice/detail?type=2&noticeId=%s' >查看详情 </a>";
            //给领班发邮件
            String emailBody = String.format(emailContent,
                    notice.getOrderId(),
                    notice.getOpCount() + 1,
                    notice.getSendTime(),
                    notice.getId());
            SendMailUtils.sendNormalEmail(receipts, receipts, "TrainOffline@ctrip.com",
                    "TrainOffline", title, emailBody);
        }

        NoticeVendor vendor = new NoticeVendor();
        vendor.setID(notice.getId());
        vendor.setOpCount(notice.getOpCount() == null ? 1 : notice.getOpCount() + 1);
        try {
            noitceVendor.update(vendor);//更新数据
        } catch (Exception ex) {
            CLogger.error("reminderNoticeKefu", ex);
        }
        //添加操作记录
        String content = "二线客服催:" + contents;
        dmNoticeService.insertOperate(noticeId, content, notice.getOpUser(), NoticeOperateEnum.Urge.getState(), 3);

        responseType.setIsSuccess(true);
        return responseType;
    }

    /**
     * 催单更新通知状态
     */
    private void reminderNoitceState(DmNoticeVendor  notice){
        if (notice.getOpUserNum() != null) {
            ChatStaffInfo staffInfo =extStaff.getStaffInfoByNum(notice.getOpUserNum());
            if (staffInfo!=null) {
                NoticeComplainInfo noticeComplainInfo=new  NoticeComplainInfo();
                noticeComplainInfo.setID(notice.getNoticeId());
                noticeComplainInfo.setDatachangeLastTime(DateUtils.getCurFullTimestamp());
                noticeComplainInfo.setLastTimeUrge(DateUtils.getCurFullTimestamp());

                //不在线
                if (staffInfo.getStaffState().equals("0")  && notice.getNoticeState() != 80) {
                    noticeComplainInfo.setNoticeState(NoticeStateEnum.Wait.getState());
                } else {
                    //暂缓状态
                    if (notice.getNoticeState() == NoticeStateEnum.Deferred.getState()) {
                        noticeComplainInfo.setNoticeState(NoticeStateEnum.Assigned.getState());
                    }
                }
                try {
                    extNotice.update(noticeComplainInfo);
                }
                catch (Exception ex){
                    CLogger.error("reminderNotice",ex);
                }
            }
        }
    }


    public GetVendorNoticeNumResponse  getVendorNoticeNum(GetVendorNoticeNumRequest request){
        GetVendorNoticeNumResponse response=new GetVendorNoticeNumResponse();
        DmNoticeVendorCondition dm = new DmNoticeVendorCondition();
        dm.setVerdorCode(request.getVendorCode());

        //没有解决的通知在供应商系统都视为待处理
        List<Integer> noticeStates = Arrays.asList(80, 81, 82, 84, 102);
        dm.setNoticeStates(noticeStates);

        //待回复
        dm.setOpUserType(1);
        response.setPendingNum(noitceVendor.getVendorCount(dm));

        //已回复
        dm.setOpUserType(2);
        response.setRepliedNum(noitceVendor.getVendorCount(dm));

        //催工单
        dm.setOpUserType(request.getOpUserType());
        dm.setNoticeStatus(1);
        response.setOpCountNum(noitceVendor.getVendorCount(dm));

        //紧急工单
        dm.setNoticeStatus(2);
        response.setEmergeStateNum(noitceVendor.getVendorCount(dm));

        //一般工单
        dm.setNoticeStatus(3);
        response.setNumalNum(noitceVendor.getVendorCount(dm));

        return response;

    }

}
