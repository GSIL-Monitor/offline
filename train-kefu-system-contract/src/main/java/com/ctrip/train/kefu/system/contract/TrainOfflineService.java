package com.ctrip.train.kefu.system.contract;

import com.ctriposs.baiji.rpc.common.BaijiContract;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;

@SuppressWarnings("all")
@BaijiContract(serviceName = "TrainOfflineService", serviceNamespace = "http://soa.ctrip.com/train/TrainOffline/v1", codeGeneratorVersion = "BJCG-2.4.5.17")
public interface TrainOfflineService {

    /**
     * checkHealth
     */
    CheckHealthResponseType checkHealth(CheckHealthRequestType request) throws Exception;

    GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request) throws Exception;

    IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request) throws Exception;

    IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request) throws Exception;

    StaffPermissionResponseType staffPermission(StaffPermissionRequestType request) throws Exception;

    SendNoticeResponseType sendNotice(SendNoticeRequestType request) throws Exception;

    NoticeExisResponseType noticeExis(NoticeExisRequestType request) throws Exception;

    GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request) throws Exception;

    GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request) throws Exception;

    GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request) throws Exception;

    GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request) throws Exception;

    AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request) throws Exception;

    SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request) throws Exception;

    GetScmEnumRespose getScmEnum(GetScmEnumRequest request) throws Exception;

    GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request) throws Exception;

    AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request) throws Exception;

    FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request) throws Exception;

    QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request) throws Exception;

    OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request) throws Exception;

    ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request) throws Exception;

    ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request) throws Exception;

    GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request) throws Exception;
}