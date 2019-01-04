package com.ctrip.train.kefu.system.offline.order.service.impl.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailResponse;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailTicketInfoDto;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailTraceInfoDto;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailTraceInfoLogDto;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.GetAllStationsResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.GetStopStationsResponseType;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.Station;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.StopStation;
import com.ctrip.soa.train.traingrabticket.graborder.v1.GetOrderDetailForOfflineResponseType;
import com.ctrip.soa.train.trainordercentreservice.noncore.v1.AddOrderLogRequestType;
import com.ctrip.soa.train.trainordercentreservice.noncore.v1.DeleteOrderResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OfflineOrderDetailResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.OfflinePassengerInfo;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.RefundAllGrabBagResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.RefundCouponResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.OrderDetailResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.RefundGrabProductIdResponseType;
import com.ctrip.soa.train.trainordercentreservice.v1.ReturnTicketServiceRequestType;
import com.ctrip.soa.train.trainordercentreservice.v1.ReturnTicketServiceResponseType;
import com.ctrip.soa.train.xproductservice.v1.ClaimEntityDTO;
import com.ctrip.soa.train.xproductservice.v1.SearchClaimResponseType;
import com.ctrip.train.kefu.system.client.offline.common.ShenDunContract;
import com.ctrip.train.kefu.system.client.offline.train.*;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PassportTypesInfo;
import com.ctrip.train.kefu.system.offline.order.dao.ExtChatOrderMessageDao;
import com.ctrip.train.kefu.system.offline.order.dao.ExtOrderClickDao;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.enums.train.ClaimStatusEnum;
import com.ctrip.train.kefu.system.offline.order.service.TrainOrderService;
import com.ctrip.train.kefu.system.offline.order.service.impl.train.dmService.*;
import com.ctrip.train.kefu.system.offline.order.vm.QueryOrderDetailEx;
import com.ctrip.train.kefu.system.offline.order.vm.QueryOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmApplyRefund;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmRequestSearchS2S;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmResponseS2S;
import com.ctrip.train.kefu.system.offline.order.vm.train.refund.VmRefundApplyInfo;
import com.ctrip.train.product.contract.product.contract.FastServicePointV2ResponseType;
import com.ctrip.train.product.contract.product.contract.GetTrainXProductInfoResponseType;
import com.ctrip.train.ticketaccount.service.client.contract.SearchAccountInfoResponseType;
import com.ctrip.train.ticketagent.service.client.CancelOrderResponse;
import common.credis.CRedisHelper;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.Des3Utils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.ChatOrderMessage;
import dao.ctrip.ctrainchat.entity.OrderClick;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.livechatservice.CoreInfoKeyType;
import org.tempuri.livechatservice.SingleDecryptRequestType;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Service
public class TrainOrderServiceImpl implements TrainOrderService {

    @Autowired
    private OrderContract orderContract;

//    @Autowired
//    private DmOrderDetailService dmOrderDetailService;

    @Autowired
    private ExtOrderClickDao extOrderClickDao;

    @Autowired
    private ExtChatOrderMessageDao extchatOrderMessageDao;

    @Autowired
    private TicketaccountContract ticketaccountContract;

    @Autowired
    private ShenDunContract shenDunContract;

    @Autowired
    private TrainProductServiceContract trainProductServiceContract;

    @Autowired
    private StationContract stationContract;

    @Autowired
    private TrainGrabOrderContract trainGrabOrderContract;


    public QueryOrderEx getQueryOrderDetail(String OrderNumber, String Username12306, String EOrderNumber) {
        QueryOrderEx vm = new QueryOrderEx();
        try {
            List<QueryOrderDetailEx> queryOrderList = new ArrayList<QueryOrderDetailEx>();
            QueryOrderDetailResponse response = orderContract.queryOrderDetail(OrderNumber, Username12306, EOrderNumber);
            vm.setRetCode(response != null ? response.getRetCode() : -1);
            if (response != null && response.getRetCode() == 1 && response.getResult() != null) {   //请求成功
                for (QueryOrderDetailTicketInfoDto item : response.getResult().getTicketInfos()) {
                    QueryOrderDetailEx queryOrderDetailEx = new QueryOrderDetailEx();
                    queryOrderDetailEx.setTrianInfo(item.getDepartureTime() + " 开 " + item.getTrainNo() + item.getFromStation() + " - " + item.getToStation());       //车次信息
                    queryOrderDetailEx.setSeatInfo(item.getCoachNo() + item.getSeatNo() + item.getSeatType());        //座席信息
                    queryOrderDetailEx.setPassengerInfo(item.getPassengerName() + item.getIdentityType());   //旅客信息
                    queryOrderDetailEx.setTickprice(item.getTicketType() + item.getTicketPrice());       //票款金额
                    queryOrderDetailEx.setTicketstate(item.getTicketStatus());    //车票状态
                    queryOrderDetailEx.setRefundInfo("");      //退款详情

                    List<QueryOrderDetailTraceInfoDto> listTemp = response.getResult().getTraceInfos().stream()
                            .filter(q -> q.getTrainNo().equals(item.getTrainNo()) && q.getPassengerName().equals(item.getPassengerName())
                                    && q.getFromStation().equals(item.getFromStation()))
                            .filter(T -> T.getToStation().equals(item.getToStation())
                                    && (DateUtils.parseDate(item.getDepartureTime(), DateUtils.YMD_UNDERLINED)).equals(DateUtils.parseDate(T.getDepartureDate(), DateUtils.YMD_UNDERLINED)))
                            .collect(Collectors.toList());
                    if (listTemp != null && listTemp.size() > 0 && listTemp.get(0).getLogs() != null && listTemp.get(0).getLogs().size() > 0) {
                        StringBuilder tempOrderTrace = new StringBuilder();
                        for (QueryOrderDetailTraceInfoLogDto item2 : listTemp.get(0).getLogs()) {
                            tempOrderTrace.append("<span style='display:block;'>").append(item2.getOperateTime()).append(item2.getContent()).append("</span>");     //获取订单追踪信息
                        }
                        queryOrderDetailEx.setOrderTrace(tempOrderTrace.toString());
                    }
                    queryOrderList.add(queryOrderDetailEx);
                }
                vm.setQueryOrderList(queryOrderList);
                //退款信息
                vm.setRefundInfos(response.getResult().getRefundInfos());
            }
        } catch (Exception ex) {
            CLogger.error("getQueryOrderDetail", ex);
        }
        return vm;

    }


    /**
     * 获取订单DoMain
     */
    public DmTrainOrderDetail getOrderDetail(String orderNumber) {
        DmTrainOrderDetail dm = new DmTrainOrderDetail();
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderNumber);
        try {
            OrderDetailResponseType orderDetailResponseType = new OrderDetailResponseType();
            orderDetailResponseType = orderContract.orderDetail(orderNumber);
            if (orderDetailResponseType.getRetCode() == 0) {
                dm.setDmTrainOrderAppend(new DmTrainOrderAppendService().getDmOrderAppend(orderDetailResponseType));
                dm.setDmTrainCouponInfo(new DmTrainOrderAppendService().getDmOrderCouponInfo(orderDetailResponseType));
                dm.setDmTrainOrderBasicInfo(new DmTrainOrderBasicInfoService().getDmOrderBasicInfo(orderDetailResponseType));
                dm.setDmTrainOrderGrabTask(new DmTrainOrderGrabTaskService().getDmOrderGrabTask(orderDetailResponseType));
                dm.setDmTrainOrderOperate(new DmTrainOrderOperateService().getDmOrderOperate(orderDetailResponseType));
                dm.setDmTrainOrderTicketsInfo(new DmTrainOrderTicketsInfoService().getDmOrderTicketsInfo(orderDetailResponseType));
                dm.setDmPassengersInfo(new DmTrainOrderBasicInfoService().getDmPassengersInfo(orderDetailResponseType));
            }
        } catch (Exception ex) {
            CLogger.error("获取订单DoMain", ex, dic);
        }
        return dm;
    }


    /**
     * 获取退票详情列表
     */
    public List<VmRefundApplyInfo> getRefundApplyInfo(OfflineOrderDetailResponseType orderResponse) {
        if (orderResponse == null || orderResponse.getOrderDetail() == null) {
            return null;
        }

        if (orderResponse.getOfflinePassengers() == null || orderResponse.getOfflinePassengers().size() == 0) {
            return null;
        }

        List<VmRefundApplyInfo> vmRefundApplyInfoList = new ArrayList<>();

        for (OfflinePassengerInfo p : orderResponse.getOfflinePassengers()) {
            VmRefundApplyInfo vm = new VmRefundApplyInfo();
            //神盾解密身份证等信息 TODO 2
            SingleDecryptRequestType sdrt=new SingleDecryptRequestType();
            sdrt.setKeyType(CoreInfoKeyType.Identity_Card);
            sdrt.setStrNeedEncrypt(p.getPassportNumber());

            if (p.getTicketSection() == 1) {
                vm.setTicketSectionTypeName("第一程");
            } else if (p.getTicketSection() == 2) {
                vm.setTicketSectionTypeName("第二程");
            } else {
                vm.setTicketSectionTypeName("未知");
            }

            // 1原始票 2改签票
            if (p.getIsChanged().trim().equals("1")) {
                vm.setIsChanged(false);
            } else {
                vm.setIsChanged(true);
            }
            //乘客姓名
            vm.setPassportName(p.getPassportName());
            //证件类型
            vm.setPassportType(PassportTypesInfo.GetPassportType(p.getPassportType()));
            //证件号码
            vm.setPassportNumber(shenDunContract.singleDecrypt(sdrt));
            //坐席
            vm.setSeatName(p.getSeatName());
            //坐席号
            vm.setSeatNo((p.getSeatNo()));
            //车票票价
            vm.setTicketPrice(p.getTicketPrice());
            //下单票价
            vm.setOrderTicketPrice(p.getOrderTicketPrice());

            //票显示状态 TODO

            //是否可退票 逻辑
            //TrainStatus：1-停运；0-正常/恢复
            // TicketType 车票类型 0配送票 2电子票 6 抢票
//            Pair<Boolean, String> pair = isEnableReturnTicket(p.getTrainStatus() == 1 ,
//                    p.getTicketType().trim().equals("0"),
//                    vm.getIsChanged(),
//                    p.getTicketState(),
//                    p.getFromStationName(),
//                    p.getToStationName(),
//                    p.getTicketTime());
//
//            vm.setEnableReturnTicket(pair.getKey());
//            vm.setEnableReturnTicketMark(pair.getValue());

            //退票手续费 可以退票的情况下计算退票手续费
            if (vm.getEnableReturnTicket()) {

            }

        }

        return vmRefundApplyInfoList;

    }

//    /**
//     * 是否可以退票
//     *
//     * @param isReturnTrue         是否直接返回true 比喻 停运的车次都可以退票，配送票除外，
//     * @param isDistributionTicket 是否配送票
//     * @param isChangeTicket       是否改签票
//     * @param ticketState          票状态
//     * @param fromStationName      出发站
//     * @param toStationName        达到站
//     * @param ticketTime           车票时间
//     * @return
//     */
//    public Pair<Boolean, String> isEnableReturnTicket(boolean isReturnTrue, boolean isDistributionTicket, boolean isChangeTicket,
//                                                      String ticketState, String fromStationName, String toStationName, String ticketTime) {
//        // Pair<Boolean, String> pair=new Pair<Boolean, String>(false, "默认不可退");
//
//        //配送票不可退
//        //停运车次且不是配送票的直接显示退票按钮
//        if (isDistributionTicket) {
//            return new Pair<Boolean, String>(false, "配送票不可退");
//        }
//
//        if (isReturnTrue) {
//            return new Pair<Boolean, String>(true, "停运非配送票可退");
//        }
//
//        Date tickedTime = DateUtils.parseDate(ticketTime);//车票时间
//        long second = tickedTime.getTime() - new Date().getTime();
//        long minute = second / 60;//距离发车时间还有多少分钟
//
//        //改签票 ticketStatus //0改签中1改签成功2改签失败3退票中4退票成功5退票失败
//        //出发站或者到达站是香港西九龙的改签票改签成功后无法退票
//        if (isChangeTicket
//                && (ticketState.trim().equals("1") || ticketState.trim().equals("5"))
//                && (fromStationName.trim().equals("香港西九龙") || toStationName.trim().equals("香港西九龙"))) {
//            return new Pair<Boolean, String>(false, "出发站或者到达站是香港西九龙的改签票改签成功后无法退票");
//        } else {
//            //未停运 出发站是香港西九龙的大于60分钟可退票
//            //未停运 出发站不是香港西九龙的大于30分钟可退票
//
//            if ((fromStationName.trim().equals("香港西九龙") && minute > 60)
//                    || (!fromStationName.trim().equals("香港西九龙") && minute > 30)
//            ) {
//
//                //原始票中 ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功 原票作废状态
//                //原始票中，0：未退   3：退票失败  可以退票
//                //改签票 ticketStatus //0改签中1改签成功2改签失败3退票中4退票成功5退票失败
//                //改签票中，1：改签成功  5：退票失败  可以退票
//                if ((!isChangeTicket && (ticketState.trim().equals("0") || ticketState.trim().equals("3")))
//                        || (isChangeTicket && (ticketState.trim().equals("1") || ticketState.trim().equals("5")))) {
//                    return new Pair<Boolean, String>(true, "");
//                } else {
//                    if (!isChangeTicket) {
//                        return new Pair<Boolean, String>(false, "原票状态为未退和退票失败可以退票，其余状态不可退票");
//                    } else {
//                        return new Pair<Boolean, String>(false, "改签票状态为改签成功和退票失败可以退票，其余状态不可退票");
//                    }
//                }
//            } else {
//                if (fromStationName.trim().equals("香港西九龙")) {
//                    return new Pair<Boolean, String>(false, "发车前30分内不可退票");
//                } else {
//                    return new Pair<Boolean, String>(false, "出发站为香港西九龙发车前60分内不可退票");
//                }
//            }
//        }
//
//
//    }




    /**
     * 获取12306帐号
     * @param username
     * @param orderId
     * @param operId
     */
    public String searchPassword(String username, String orderId, String operId){
        String result = "";
        try {
            OrderClick orderClickentity = new OrderClick();
            orderClickentity.setOrderId(orderId);
            orderClickentity.setOperId(operId);
            orderClickentity.setClickType("1");
            orderClickentity.setClickRemark("");
            extOrderClickDao.insert(orderClickentity);
            CLogger.info("Search12306Password", orderId, new HashMap<String, String>(){{put("orderId",orderId);}});
            if (StringUtils.isEmpty(username))
            {
                return "";
            }
            String pwd = "";
            StringBuffer accountType = new StringBuffer();
            for(String account : username.split(","))
            {
                SearchAccountInfoResponseType response = ticketaccountContract.searchAccountInfo( account, operId);
                if (response != null && response.getCode().equals(200))
                {
                    pwd += Des3Utils.decryptDESCBC(response.getPassword(),"") + ",";
                    accountType.append(response.getAccountType());

                    ChatOrderMessage chatOrderMessage = new ChatOrderMessage();
                    chatOrderMessage.setOrderId(orderId);
                    chatOrderMessage.setAction(6);
                    List<ChatOrderMessage> chatResultList = extchatOrderMessageDao.queryBy(chatOrderMessage); // 出票成功的记录
                    if(chatResultList!=null && chatResultList.size() > 0 && chatResultList.get(0).getRemark() != null) {
                        accountType.append(String.format("<span style='color:red'>(%s)</span>", chatResultList.get(0).getRemark()));
                    }
                }
            }
            if (StringUtils.isNotEmpty(pwd))
            {
                result = trimEnd(pwd) + "|" + accountType.toString();
            }
        }catch (Exception ex){
            CLogger.info("TrainOrderServiceImpl.searchPassword",ex);
        }
        return result;
    }

    public String appendProductInfo(String action,Integer productID){
        String infoUrl = "";
        if(action.equals("infoUrl")){
            GetTrainXProductInfoResponseType response = trainProductServiceContract.getTrainXProductInfo(productID);
            if(response!=null && StringUtils.isNotEmpty(response.getInfoUrl())){
                infoUrl = response.getInfoUrl();
            }
        }
        return infoUrl;
    }

    public String refundCoupon(String partnerName, String orderId, String couponId, String uid){
        String resultMsg;
        RefundCouponResponseType responseType = orderContract.refundCoupon(partnerName, orderId, couponId, uid);
        if (responseType!=null) {
            if (responseType.getRetCode() == 0) {
                resultMsg = "退套餐申请成功";
            } else {
                resultMsg = "退套餐申请失败！：" + StringUtils.trimToEmpty(responseType.getMsg());
            }
        }else{
            resultMsg ="退套餐申请接口异常";
        }
        return resultMsg;
    }

    /**
     * 批量退默认加速包
     * @param orderNumber
     * @param operator
     * @return
     */
    public String refundGrabProductId(String orderNumber,String operator){
        RefundGrabProductIdResponseType responseType = orderContract.refundGrabProductId(orderNumber, operator);
        if(responseType!=null ){
            if (responseType.getRetCode() == 0 && StringUtils.isEmpty(responseType.getMsg()))
            {
                return "成功";
            }
            return responseType.getMsg();
        }
        return "调用订单中心接口退默认加速包失败";
    }

    /**
     * 批量退加速包
     * @param orderNumber
     * @param operator
     * @return
     */
    public String refundAllGrabBag(String orderNumber,String operator) {
        RefundAllGrabBagResponseType responseType = orderContract.refundAllGrabBag(orderNumber, operator);
        if (responseType != null) {
            if (responseType.getRetCode() == 0 && StringUtils.isEmpty(responseType.getMsg())) {
                return "成功";
            }
            return responseType.getMsg();
        }
        return "调用订单中心批量退加速包接口失败";
    }

    /**
     * 抢票险理赔状态
     * @param orderId
     * @param insuranceNos
     * @return
     */
    public String getInsuranceState(String orderId,String insuranceNos){
        String msg = "";
        SearchClaimResponseType response = orderContract.searchClaim(insuranceNos);
        if (response != null) {

            if (response.getRetCode() > -1 && response.getClaimEntity() != null)
                msg = "理赔结果：" + getClaimStatus(response.getClaimEntity());
            else
            {
                msg = "查询理赔结果异常：" + response.getMessage();
                CLogger.info("抢票险理赔状态", String.format(" 抢票险理赔状态异常,订单号:%s,AppendId:%s;详情:%s", orderId, insuranceNos, response.getMessage()));
            }
        } else {
            msg="查询理赔结果异常:查询接口失败!";
        }
        return msg;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public String cancelOrder(String orderId,String operater){
        return cancelOrderTicketagent(orderId,operater);
    }

    @Override
    public String applyRefundTicket(VmApplyRefund request) {
        ReturnTicketServiceRequestType requestType= new ReturnTicketServiceRequestType();
        requestType.setPartnerName(request.getPartnerName());
        requestType.setOperator(request.getOperator());
        requestType.setOrderNumber(request.getOrderNumber());
        requestType.setLongElecNums(Arrays.asList(request.getLongElecNums().split(",")));
        ReturnTicketServiceResponseType responseType=orderContract.returnTicketService(requestType);
        responseType.getResponseStatus();
        return responseType.getMsg();
    }

    /**
     * 配送票取消订单
     * @param orderid
     * @return
     */
    private String cancelOrderTicketagent(String orderid,String operater){
        String msg = "";
        try
        {
            if (StringUtils.isNotEmpty(orderid))
            {
                CancelOrderResponse resonse = ticketaccountContract.cancelOrder(orderid);
                CLogger.info("cancelOrderTicketagent", JSON.toJSONString(resonse),new HashMap<String,String>(){{put("orderid",orderid);}});
                if (resonse != null)
                {
                    if (resonse.getRetCode().equals(0))
                    {
                        msg = "已申请取消!";
                        try {
                            AddOrderLogRequestType request = new AddOrderLogRequestType();
                            request.setActionName("申请取消订单");
                            request.setComment(String.format("%s已申请取消订单!", operater));
                            request.setOperator(URLDecoder.decode(operater, "utf-8"));
                            request.setOperateTime(DateUtils.formatTime(new Date()));
                            request.setOrderNumber(orderid);
                            request.setOrderTid("");
                            request.setPartnerOrderId("");
                            request.setReasonType("申请取消订单");
                            orderContract.addOrderLogDetail(request);
                        } catch (Exception ex) {
                            CLogger.info("申请取消订单addlog", ex);
                        }
                    }
                    else
                    {
                        msg ="申请取消失败：" + resonse.getMsg();
                    }
                }
                else
                {
                    msg = "系统异常，申请失败，请重试！";
                }
            }
        }
        catch (Exception ex)
        {
            CLogger.error("配送票申请取消失败",ex);
            msg = "系统异常，申请失败，请重试！";
        }
        return msg;
    }

    private String getClaimStatus(ClaimEntityDTO claimEntity){
        String msg = "";
        if (claimEntity != null) {
            if (claimEntity.getStatus() == 5) {
                msg = ClaimStatusEnum.CompletePay.getName();
            } else {
                msg = ClaimStatusEnum.convertVendor(claimEntity.getStatus()).getName() + claimEntity.getComment();
            }
        }
        return msg;
    }

    @Override
    public List<VmResponseS2S> searchS2S(VmRequestSearchS2S request) {
        GetStopStationsResponseType responseType=stationContract.getStopStations(request.getTrainNo(),request.getTicketDate());
        List<StopStation> stations= responseType.getStopStations();
        if (stations!=null&&stations.size()>0){
            ModelMapper modelMapper=new ModelMapper();
            return modelMapper.map(stations,new TypeToken<List<VmResponseS2S>>() {}.getType());
        }
        return null;
    }

    @Override
    public String getStationsAddress(String name) {
        String str="station_Address_";
        String aa=str+name;
        //先从redis查询查不到 再查接口
        String returnValue=CRedisHelper.get(aa);
        if(StringUtils.isBlank(returnValue)) {
            GetAllStationsResponseType responseType=stationContract.getAllStations();
            responseType.getResponseStatus();
            List<Station> tempStation=responseType.getStations();
            if (tempStation!=null&&tempStation.size()>0){
                for (Station temp:tempStation){
                    CRedisHelper.set(str+temp.getStationName(),temp.getAddress());
                }
            }
            return CRedisHelper.get(String.format(str,name));
        }
        return returnValue;
    }

    @Override
    public BigDecimal getTrainOrderPackageLevel(String orderNum) {
        GetOrderDetailForOfflineResponseType response = trainGrabOrderContract.getOrderDetailForOffline(orderNum);

        BigDecimal level = response.getPackageLevel();

        return level;
    }

    @Override
    public String trainOrderDelete(String orderNumber, String operator,String partnerName,String userId) {
        //TODO 测试时链接超时 上生产测测看
        DeleteOrderResponseType responseType=orderContract.deleteOrder(orderNumber,partnerName,operator,"无",userId);
        if (responseType.getStatus().equals("SUCCESS")){
            return "删除成功";
        }else {
            if (StringUtils.isBlank(responseType.getMsg())){
                return "删除失败";
            }
            return responseType.getMsg();
        }
    }

    @Override
    public String getStationImageUrl(String departStationName,String DepartureDateTime) {
        //TODO 接口对接人不鸟我
        FastServicePointV2ResponseType responseType=trainProductServiceContract.getStationImageUrl(departStationName,DepartureDateTime);

        return null;
    }

    private String trimEnd(String strvalue){
        String str = strvalue;
        try {
            if (StringUtils.isNotEmpty(strvalue) && strvalue.length() > 0) {
                str = strvalue.substring(0, strvalue.length() - 1);
            }
        } catch (Exception ex) {
            CLogger.info("TrainOrderServiceImpl.trimEnd", ex);
        }
        return str;
    }
}
