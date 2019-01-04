package com.ctrip.train.kefu.system.contract;

import com.google.common.util.concurrent.ListenableFuture;
import com.ctrip.soa.caravan.common.delegate.Func;
import com.ctriposs.baiji.rpc.client.*;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;

@SuppressWarnings("all")
public interface TrainOfflineServiceClientInterface {
    /**
     * checkHealth
     */
    CheckHealthResponseType checkHealth(CheckHealthRequestType request) throws Exception;

    /**
     * checkHealth
     */
    CheckHealthResponseType checkHealth(CheckHealthRequestType request, Func<CheckHealthResponseType> fallbackProvider) throws Exception;

    /**
     * checkHealth
     */
    ListenableFuture<CheckHealthResponseType> checkHealthAsync(CheckHealthRequestType request) throws Exception;

    GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request) throws Exception;

    GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request, Func<GetIVRPredictingUserBehaviorResponseType> fallbackProvider) throws Exception;

    ListenableFuture<GetIVRPredictingUserBehaviorResponseType> getIVRPredictingUserBehaviorAsync(GetIVRPredictingUserBehaviorRequestType request) throws Exception;

    IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request) throws Exception;

    IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request, Func<IVRUserSelfServiceResponseType> fallbackProvider) throws Exception;

    ListenableFuture<IVRUserSelfServiceResponseType> iVRUserSelfServiceAsync(IVRUserSelfServiceRequestType request) throws Exception;

    IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request) throws Exception;

    IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request, Func<IVRUserTransferLogResponseType> fallbackProvider) throws Exception;

    ListenableFuture<IVRUserTransferLogResponseType> iVRUserTransferLogAsync(IVRUserTransferLogRequestType request) throws Exception;

    StaffPermissionResponseType staffPermission(StaffPermissionRequestType request) throws Exception;

    StaffPermissionResponseType staffPermission(StaffPermissionRequestType request, Func<StaffPermissionResponseType> fallbackProvider) throws Exception;

    ListenableFuture<StaffPermissionResponseType> staffPermissionAsync(StaffPermissionRequestType request) throws Exception;

    SendNoticeResponseType sendNotice(SendNoticeRequestType request) throws Exception;

    SendNoticeResponseType sendNotice(SendNoticeRequestType request, Func<SendNoticeResponseType> fallbackProvider) throws Exception;

    ListenableFuture<SendNoticeResponseType> sendNoticeAsync(SendNoticeRequestType request) throws Exception;

    NoticeExisResponseType noticeExis(NoticeExisRequestType request) throws Exception;

    NoticeExisResponseType noticeExis(NoticeExisRequestType request, Func<NoticeExisResponseType> fallbackProvider) throws Exception;

    ListenableFuture<NoticeExisResponseType> noticeExisAsync(NoticeExisRequestType request) throws Exception;

    GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request) throws Exception;

    GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request, Func<GetVendorNoticeListRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetVendorNoticeListRespose> getVendorNoticeListAsync(GetVendorNoticeListRequest request) throws Exception;

    GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request) throws Exception;

    GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request, Func<GetPendingVendorNoticeRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetPendingVendorNoticeRespose> getPendingVendorNoticeAsync(GetPendingVendorNoticeRequest request) throws Exception;

    GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request) throws Exception;

    GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request, Func<GetVendorNoticeRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetVendorNoticeRespose> getVendorNoticeAsync(GetVendorNoticeRequest request) throws Exception;

    GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request) throws Exception;

    GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request, Func<GetVendorUserInfoRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetVendorUserInfoRespose> getVendorUserInfoAsync(GetVendorUserInfoRequest request) throws Exception;

    AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request) throws Exception;

    AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request, Func<AddVendorUserInfoRespose> fallbackProvider) throws Exception;

    ListenableFuture<AddVendorUserInfoRespose> addVendorUserInfoAsync(AddVendorUserInfoRequest request) throws Exception;

    SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request) throws Exception;

    SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request, Func<SendVendorNoticeRespose> fallbackProvider) throws Exception;

    ListenableFuture<SendVendorNoticeRespose> sendVendorNoticeAsync(SendVendorNoticeRequest request) throws Exception;

    GetScmEnumRespose getScmEnum(GetScmEnumRequest request) throws Exception;

    GetScmEnumRespose getScmEnum(GetScmEnumRequest request, Func<GetScmEnumRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetScmEnumRespose> getScmEnumAsync(GetScmEnumRequest request) throws Exception;

    GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request) throws Exception;

    GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request, Func<GetNoticeOperateRespose> fallbackProvider) throws Exception;

    ListenableFuture<GetNoticeOperateRespose> getNoticeOperateAsync(GetNoticeOperateRequest request) throws Exception;

    AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request) throws Exception;

    AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request, Func<AddNoticeOperateRespose> fallbackProvider) throws Exception;

    ListenableFuture<AddNoticeOperateRespose> addNoticeOperateAsync(AddNoticeOperateRequest request) throws Exception;

    FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request) throws Exception;

    FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request, Func<FlightExOrderResponseType> fallbackProvider) throws Exception;

    ListenableFuture<FlightExOrderResponseType> sendFlightExOrderAsync(FlightExOrderRequsetType request) throws Exception;

    QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request) throws Exception;

    QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request, Func<QueryTwoTaskOrderStateResponseType> fallbackProvider) throws Exception;

    ListenableFuture<QueryTwoTaskOrderStateResponseType> queryTwoTaskOrderByOrderIdAsync(QueryTwoTaskOrderStateRequsetType request) throws Exception;

    OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request) throws Exception;

    OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request, Func<OfflineUpdateFlightContactResponseType> fallbackProvider) throws Exception;

    ListenableFuture<OfflineUpdateFlightContactResponseType> offlineUpdateFlightContactAsync(OfflineUpdateFlightContactRequestType request) throws Exception;

    ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request) throws Exception;

    ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request, Func<ReminderKefuNoticeResponseType> fallbackProvider) throws Exception;

    ListenableFuture<ReminderKefuNoticeResponseType> reminderKefuNoticeAsync(ReminderKefuNoticeRequestType request) throws Exception;

    ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request) throws Exception;

    ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request, Func<ReminderVendorNoticeResponseType> fallbackProvider) throws Exception;

    ListenableFuture<ReminderVendorNoticeResponseType> reminderVendorNoticeAsync(ReminderVendorNoticeRequestType request) throws Exception;

    GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request) throws Exception;

    GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request, Func<GetVendorNoticeNumResponse> fallbackProvider) throws Exception;

    ListenableFuture<GetVendorNoticeNumResponse> getVendorNoticeNumAsync(GetVendorNoticeNumRequest request) throws Exception;
}