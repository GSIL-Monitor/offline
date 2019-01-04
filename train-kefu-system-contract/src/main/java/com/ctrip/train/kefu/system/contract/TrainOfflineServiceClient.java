package com.ctrip.train.kefu.system.contract;

import com.google.common.util.concurrent.ListenableFuture;
import com.ctrip.soa.caravan.common.delegate.Func;
import com.ctriposs.baiji.rpc.client.*;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;

@SuppressWarnings("all")
public class TrainOfflineServiceClient extends ServiceClientBase<TrainOfflineServiceClient> implements TrainOfflineServiceClientInterface {
    public static final String ORIGINAL_SERVICE_NAME = "TrainOfflineService";

    public static final String ORIGINAL_SERVICE_NAMESPACE = "http://soa.ctrip.com/train/TrainOffline/v1";

    public static final String CODE_GENERATOR_VERSION = "BJCG-2.4.5.17";

    private TrainOfflineServiceClient(String baseUri) {
        super(TrainOfflineServiceClient.class, baseUri);
    }

    private TrainOfflineServiceClient(String serviceName, String serviceNamespace, String subEnv) throws ServiceLookupException {
        super(TrainOfflineServiceClient.class, serviceName, serviceNamespace, subEnv);
    }

    public static TrainOfflineServiceClient getInstance() {
        return ServiceClientBase.getInstance(TrainOfflineServiceClient.class);
    }

    public static TrainOfflineServiceClient getInstance(String baseUrl) {
        return ServiceClientBase.getInstance(TrainOfflineServiceClient.class, baseUrl);
    }

    /**
     * checkHealth
     */
    @Override
    public CheckHealthResponseType checkHealth(CheckHealthRequestType request)
                                    throws Exception {
        return super.invoke("checkHealth", request, CheckHealthResponseType.class);
    }

    /**
     * checkHealth
     */
    @Override
    public CheckHealthResponseType checkHealth(CheckHealthRequestType request, Func<CheckHealthResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("checkHealth", request, fallbackProvider, CheckHealthResponseType.class);
    }

    /**
     * checkHealth
     */
    @Override
    public ListenableFuture<CheckHealthResponseType> checkHealthAsync(CheckHealthRequestType request)
                                    throws Exception {
        return super.invokeAsync("checkHealth", request, CheckHealthResponseType.class);
    }

    @Override
    public GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request)
                                    throws Exception {
        return super.invoke("getIVRPredictingUserBehavior", request, GetIVRPredictingUserBehaviorResponseType.class);
    }

    @Override
    public GetIVRPredictingUserBehaviorResponseType getIVRPredictingUserBehavior(GetIVRPredictingUserBehaviorRequestType request, Func<GetIVRPredictingUserBehaviorResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("getIVRPredictingUserBehavior", request, fallbackProvider, GetIVRPredictingUserBehaviorResponseType.class);
    }

    @Override
    public ListenableFuture<GetIVRPredictingUserBehaviorResponseType> getIVRPredictingUserBehaviorAsync(GetIVRPredictingUserBehaviorRequestType request)
                                    throws Exception {
        return super.invokeAsync("getIVRPredictingUserBehavior", request, GetIVRPredictingUserBehaviorResponseType.class);
    }

    @Override
    public IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request)
                                    throws Exception {
        return super.invoke("iVRUserSelfService", request, IVRUserSelfServiceResponseType.class);
    }

    @Override
    public IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request, Func<IVRUserSelfServiceResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("iVRUserSelfService", request, fallbackProvider, IVRUserSelfServiceResponseType.class);
    }

    @Override
    public ListenableFuture<IVRUserSelfServiceResponseType> iVRUserSelfServiceAsync(IVRUserSelfServiceRequestType request)
                                    throws Exception {
        return super.invokeAsync("iVRUserSelfService", request, IVRUserSelfServiceResponseType.class);
    }

    @Override
    public IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request)
                                    throws Exception {
        return super.invoke("iVRUserTransferLog", request, IVRUserTransferLogResponseType.class);
    }

    @Override
    public IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request, Func<IVRUserTransferLogResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("iVRUserTransferLog", request, fallbackProvider, IVRUserTransferLogResponseType.class);
    }

    @Override
    public ListenableFuture<IVRUserTransferLogResponseType> iVRUserTransferLogAsync(IVRUserTransferLogRequestType request)
                                    throws Exception {
        return super.invokeAsync("iVRUserTransferLog", request, IVRUserTransferLogResponseType.class);
    }

    @Override
    public StaffPermissionResponseType staffPermission(StaffPermissionRequestType request)
                                    throws Exception {
        return super.invoke("staffPermission", request, StaffPermissionResponseType.class);
    }

    @Override
    public StaffPermissionResponseType staffPermission(StaffPermissionRequestType request, Func<StaffPermissionResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("staffPermission", request, fallbackProvider, StaffPermissionResponseType.class);
    }

    @Override
    public ListenableFuture<StaffPermissionResponseType> staffPermissionAsync(StaffPermissionRequestType request)
                                    throws Exception {
        return super.invokeAsync("staffPermission", request, StaffPermissionResponseType.class);
    }

    @Override
    public SendNoticeResponseType sendNotice(SendNoticeRequestType request)
                                    throws Exception {
        return super.invoke("sendNotice", request, SendNoticeResponseType.class);
    }

    @Override
    public SendNoticeResponseType sendNotice(SendNoticeRequestType request, Func<SendNoticeResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("sendNotice", request, fallbackProvider, SendNoticeResponseType.class);
    }

    @Override
    public ListenableFuture<SendNoticeResponseType> sendNoticeAsync(SendNoticeRequestType request)
                                    throws Exception {
        return super.invokeAsync("sendNotice", request, SendNoticeResponseType.class);
    }

    @Override
    public NoticeExisResponseType noticeExis(NoticeExisRequestType request)
                                    throws Exception {
        return super.invoke("noticeExis", request, NoticeExisResponseType.class);
    }

    @Override
    public NoticeExisResponseType noticeExis(NoticeExisRequestType request, Func<NoticeExisResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("noticeExis", request, fallbackProvider, NoticeExisResponseType.class);
    }

    @Override
    public ListenableFuture<NoticeExisResponseType> noticeExisAsync(NoticeExisRequestType request)
                                    throws Exception {
        return super.invokeAsync("noticeExis", request, NoticeExisResponseType.class);
    }

    @Override
    public GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request)
                                    throws Exception {
        return super.invoke("getVendorNoticeList", request, GetVendorNoticeListRespose.class);
    }

    @Override
    public GetVendorNoticeListRespose getVendorNoticeList(GetVendorNoticeListRequest request, Func<GetVendorNoticeListRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getVendorNoticeList", request, fallbackProvider, GetVendorNoticeListRespose.class);
    }

    @Override
    public ListenableFuture<GetVendorNoticeListRespose> getVendorNoticeListAsync(GetVendorNoticeListRequest request)
                                    throws Exception {
        return super.invokeAsync("getVendorNoticeList", request, GetVendorNoticeListRespose.class);
    }

    @Override
    public GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request)
                                    throws Exception {
        return super.invoke("getPendingVendorNotice", request, GetPendingVendorNoticeRespose.class);
    }

    @Override
    public GetPendingVendorNoticeRespose getPendingVendorNotice(GetPendingVendorNoticeRequest request, Func<GetPendingVendorNoticeRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getPendingVendorNotice", request, fallbackProvider, GetPendingVendorNoticeRespose.class);
    }

    @Override
    public ListenableFuture<GetPendingVendorNoticeRespose> getPendingVendorNoticeAsync(GetPendingVendorNoticeRequest request)
                                    throws Exception {
        return super.invokeAsync("getPendingVendorNotice", request, GetPendingVendorNoticeRespose.class);
    }

    @Override
    public GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request)
                                    throws Exception {
        return super.invoke("getVendorNotice", request, GetVendorNoticeRespose.class);
    }

    @Override
    public GetVendorNoticeRespose getVendorNotice(GetVendorNoticeRequest request, Func<GetVendorNoticeRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getVendorNotice", request, fallbackProvider, GetVendorNoticeRespose.class);
    }

    @Override
    public ListenableFuture<GetVendorNoticeRespose> getVendorNoticeAsync(GetVendorNoticeRequest request)
                                    throws Exception {
        return super.invokeAsync("getVendorNotice", request, GetVendorNoticeRespose.class);
    }

    @Override
    public GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request)
                                    throws Exception {
        return super.invoke("getVendorUserInfo", request, GetVendorUserInfoRespose.class);
    }

    @Override
    public GetVendorUserInfoRespose getVendorUserInfo(GetVendorUserInfoRequest request, Func<GetVendorUserInfoRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getVendorUserInfo", request, fallbackProvider, GetVendorUserInfoRespose.class);
    }

    @Override
    public ListenableFuture<GetVendorUserInfoRespose> getVendorUserInfoAsync(GetVendorUserInfoRequest request)
                                    throws Exception {
        return super.invokeAsync("getVendorUserInfo", request, GetVendorUserInfoRespose.class);
    }

    @Override
    public AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request)
                                    throws Exception {
        return super.invoke("addVendorUserInfo", request, AddVendorUserInfoRespose.class);
    }

    @Override
    public AddVendorUserInfoRespose addVendorUserInfo(AddVendorUserInfoRequest request, Func<AddVendorUserInfoRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("addVendorUserInfo", request, fallbackProvider, AddVendorUserInfoRespose.class);
    }

    @Override
    public ListenableFuture<AddVendorUserInfoRespose> addVendorUserInfoAsync(AddVendorUserInfoRequest request)
                                    throws Exception {
        return super.invokeAsync("addVendorUserInfo", request, AddVendorUserInfoRespose.class);
    }

    @Override
    public SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request)
                                    throws Exception {
        return super.invoke("sendVendorNotice", request, SendVendorNoticeRespose.class);
    }

    @Override
    public SendVendorNoticeRespose sendVendorNotice(SendVendorNoticeRequest request, Func<SendVendorNoticeRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("sendVendorNotice", request, fallbackProvider, SendVendorNoticeRespose.class);
    }

    @Override
    public ListenableFuture<SendVendorNoticeRespose> sendVendorNoticeAsync(SendVendorNoticeRequest request)
                                    throws Exception {
        return super.invokeAsync("sendVendorNotice", request, SendVendorNoticeRespose.class);
    }

    @Override
    public GetScmEnumRespose getScmEnum(GetScmEnumRequest request)
                                    throws Exception {
        return super.invoke("getScmEnum", request, GetScmEnumRespose.class);
    }

    @Override
    public GetScmEnumRespose getScmEnum(GetScmEnumRequest request, Func<GetScmEnumRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getScmEnum", request, fallbackProvider, GetScmEnumRespose.class);
    }

    @Override
    public ListenableFuture<GetScmEnumRespose> getScmEnumAsync(GetScmEnumRequest request)
                                    throws Exception {
        return super.invokeAsync("getScmEnum", request, GetScmEnumRespose.class);
    }

    @Override
    public GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request)
                                    throws Exception {
        return super.invoke("getNoticeOperate", request, GetNoticeOperateRespose.class);
    }

    @Override
    public GetNoticeOperateRespose getNoticeOperate(GetNoticeOperateRequest request, Func<GetNoticeOperateRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("getNoticeOperate", request, fallbackProvider, GetNoticeOperateRespose.class);
    }

    @Override
    public ListenableFuture<GetNoticeOperateRespose> getNoticeOperateAsync(GetNoticeOperateRequest request)
                                    throws Exception {
        return super.invokeAsync("getNoticeOperate", request, GetNoticeOperateRespose.class);
    }

    @Override
    public AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request)
                                    throws Exception {
        return super.invoke("addNoticeOperate", request, AddNoticeOperateRespose.class);
    }

    @Override
    public AddNoticeOperateRespose addNoticeOperate(AddNoticeOperateRequest request, Func<AddNoticeOperateRespose> fallbackProvider)
                                    throws Exception {
        return super.invoke("addNoticeOperate", request, fallbackProvider, AddNoticeOperateRespose.class);
    }

    @Override
    public ListenableFuture<AddNoticeOperateRespose> addNoticeOperateAsync(AddNoticeOperateRequest request)
                                    throws Exception {
        return super.invokeAsync("addNoticeOperate", request, AddNoticeOperateRespose.class);
    }

    @Override
    public FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request)
                                    throws Exception {
        return super.invoke("sendFlightExOrder", request, FlightExOrderResponseType.class);
    }

    @Override
    public FlightExOrderResponseType sendFlightExOrder(FlightExOrderRequsetType request, Func<FlightExOrderResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("sendFlightExOrder", request, fallbackProvider, FlightExOrderResponseType.class);
    }

    @Override
    public ListenableFuture<FlightExOrderResponseType> sendFlightExOrderAsync(FlightExOrderRequsetType request)
                                    throws Exception {
        return super.invokeAsync("sendFlightExOrder", request, FlightExOrderResponseType.class);
    }

    @Override
    public QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request)
                                    throws Exception {
        return super.invoke("queryTwoTaskOrderByOrderId", request, QueryTwoTaskOrderStateResponseType.class);
    }

    @Override
    public QueryTwoTaskOrderStateResponseType queryTwoTaskOrderByOrderId(QueryTwoTaskOrderStateRequsetType request, Func<QueryTwoTaskOrderStateResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("queryTwoTaskOrderByOrderId", request, fallbackProvider, QueryTwoTaskOrderStateResponseType.class);
    }

    @Override
    public ListenableFuture<QueryTwoTaskOrderStateResponseType> queryTwoTaskOrderByOrderIdAsync(QueryTwoTaskOrderStateRequsetType request)
                                    throws Exception {
        return super.invokeAsync("queryTwoTaskOrderByOrderId", request, QueryTwoTaskOrderStateResponseType.class);
    }

    @Override
    public OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request)
                                    throws Exception {
        return super.invoke("offlineUpdateFlightContact", request, OfflineUpdateFlightContactResponseType.class);
    }

    @Override
    public OfflineUpdateFlightContactResponseType offlineUpdateFlightContact(OfflineUpdateFlightContactRequestType request, Func<OfflineUpdateFlightContactResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("offlineUpdateFlightContact", request, fallbackProvider, OfflineUpdateFlightContactResponseType.class);
    }

    @Override
    public ListenableFuture<OfflineUpdateFlightContactResponseType> offlineUpdateFlightContactAsync(OfflineUpdateFlightContactRequestType request)
                                    throws Exception {
        return super.invokeAsync("offlineUpdateFlightContact", request, OfflineUpdateFlightContactResponseType.class);
    }

    @Override
    public ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request)
                                    throws Exception {
        return super.invoke("reminderKefuNotice", request, ReminderKefuNoticeResponseType.class);
    }

    @Override
    public ReminderKefuNoticeResponseType reminderKefuNotice(ReminderKefuNoticeRequestType request, Func<ReminderKefuNoticeResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("reminderKefuNotice", request, fallbackProvider, ReminderKefuNoticeResponseType.class);
    }

    @Override
    public ListenableFuture<ReminderKefuNoticeResponseType> reminderKefuNoticeAsync(ReminderKefuNoticeRequestType request)
                                    throws Exception {
        return super.invokeAsync("reminderKefuNotice", request, ReminderKefuNoticeResponseType.class);
    }

    @Override
    public ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request)
                                    throws Exception {
        return super.invoke("reminderVendorNotice", request, ReminderVendorNoticeResponseType.class);
    }

    @Override
    public ReminderVendorNoticeResponseType reminderVendorNotice(ReminderVendorNoticeRequestType request, Func<ReminderVendorNoticeResponseType> fallbackProvider)
                                    throws Exception {
        return super.invoke("reminderVendorNotice", request, fallbackProvider, ReminderVendorNoticeResponseType.class);
    }

    @Override
    public ListenableFuture<ReminderVendorNoticeResponseType> reminderVendorNoticeAsync(ReminderVendorNoticeRequestType request)
                                    throws Exception {
        return super.invokeAsync("reminderVendorNotice", request, ReminderVendorNoticeResponseType.class);
    }

    @Override
    public GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request)
                                    throws Exception {
        return super.invoke("getVendorNoticeNum", request, GetVendorNoticeNumResponse.class);
    }

    @Override
    public GetVendorNoticeNumResponse getVendorNoticeNum(GetVendorNoticeNumRequest request, Func<GetVendorNoticeNumResponse> fallbackProvider)
                                    throws Exception {
        return super.invoke("getVendorNoticeNum", request, fallbackProvider, GetVendorNoticeNumResponse.class);
    }

    @Override
    public ListenableFuture<GetVendorNoticeNumResponse> getVendorNoticeNumAsync(GetVendorNoticeNumRequest request)
                                    throws Exception {
        return super.invokeAsync("getVendorNoticeNum", request, GetVendorNoticeNumResponse.class);
    }
}