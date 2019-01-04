package com.ctrip.train.kefu.system.api.soa;

import com.ctrip.train.kefu.system.api.contract.*;
import com.ctrip.train.kefu.system.api.service.ivr.IvrService;
import com.ctrip.train.kefu.system.api.service.order.flight.FlightOrderService;
import com.ctrip.train.kefu.system.api.service.notice.NoticeService;
import com.ctrip.train.kefu.system.api.service.order.train.TwoTaskOrderService;
import com.ctrip.train.kefu.system.api.service.staff.StaffPermissionService;
import com.ctrip.train.kefu.system.api.service.vendor.notice.VendorNoticeService;
import com.ctrip.train.kefu.system.api.service.vendor.user.UserService;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OfflineServiceImpl implements TrainOfflineService {

    private final StaffPermissionService staffPermissionService;

    private final IvrService ivrService;

    private final NoticeService noticeService;

    private final VendorNoticeService vendorNoticeService;

    private final FlightOrderService flightOrderService;

    private final UserService userService;

    private final TwoTaskOrderService twoTaskOrderService;

    @Autowired
    public OfflineServiceImpl(StaffPermissionService staffPermissionService, IvrService ivrService, NoticeService noticeService, VendorNoticeService vendorNoticeService, FlightOrderService flightOrderService, UserService userService, TwoTaskOrderService twoTaskOrderService) {
        this.staffPermissionService = staffPermissionService;
        this.ivrService = ivrService;
        this.noticeService = noticeService;
        this.vendorNoticeService = vendorNoticeService;
        this.flightOrderService = flightOrderService;
        this.userService = userService;
        this.twoTaskOrderService = twoTaskOrderService;
    }

    @Override
    public CheckHealthResponseType checkHealth(CheckHealthRequestType request) {
        return new CheckHealthResponseType();
    }

    @Override
    public GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request) {
        return ivrService.getQuestion(request);
    }

    @Override
    public IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request) {
        return ivrService.iVRUserSelfService(request);
    }

    @Override
    public IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request) {
        return ivrService.iVRUserTransferLog(request);
    }

    @Override
    public StaffPermissionResponseType staffPermission(StaffPermissionRequestType request) {
        StaffPermissionResponseType responseType = new StaffPermissionResponseType();
        boolean hasPermission = staffPermissionService.hasPermission(request.getEid(), request.getPermissionCode());
        responseType.setHasPermission(hasPermission);
        return responseType;
    }

    @Override
    public SendNoticeResponseType sendNotice(SendNoticeRequestType request) {
        return noticeService.sendNotice(request);
    }

    @Override
    public NoticeExisResponseType noticeExis(NoticeExisRequestType request) {
        return noticeService.noticeExis(request);
    }

    @Override
    public GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request) {
        return vendorNoticeService.getVendorList(request);
    }

    @Override
    public GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request) {
        return vendorNoticeService.getVendorPendingList(request);
    }

    @Override
    public GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request) {
        return vendorNoticeService.getVendorNotice(request);
    }

    @Override
    public GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request) {
        return userService.getUserInfo(request);
    }

    @Override
    public AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request) {
        return userService.addUserInfo(request);
    }

    @Override
    public SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request) {
        return vendorNoticeService.sendVendorNotice(request);
    }

    @Override
    public GetScmEnumRespose getScmEnum(GetScmEnumRequest request) {
        return vendorNoticeService.getNoticeTypeList(request);
    }

    @Override
    public GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request) {
        return vendorNoticeService.getOperateList(request.getNoticeId());
    }

    @Override
    public AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request) {
        return vendorNoticeService.addNoticeOperate(request);
    }

    @Override
    public FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request) {
        return flightOrderService.SendFlightNoticeEx(request);
    }

    @Override
    public QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request) {
        return twoTaskOrderService.QueryTwoTaskOrderState(request);
    }

    @Override
    public OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request) {
        return flightOrderService.offlineUpdateFlightContact(request);
    }

    @Override
    public ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request) {
        return vendorNoticeService.reminderKefuNotice(request.getNoticeId(), request.getContents());
    }

    @Override
    public ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request) {
        return vendorNoticeService.reminderVendorNotice(request.getNoticeId(), request.getContents());
    }

    @Override
    public GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request) {
        return vendorNoticeService.getVendorNoticeNum(request);
    }
}
