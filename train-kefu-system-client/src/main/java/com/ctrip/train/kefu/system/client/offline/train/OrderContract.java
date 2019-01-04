package com.ctrip.train.kefu.system.client.offline.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.train.javaxproductservice.v1.*;
import com.ctrip.soa.train.javaxproductservice.v1.ApplyRedEleterInvoiceRequestType;
import com.ctrip.soa.train.javaxproductservice.v1.ApplyRedEleterInvoiceResponseType;
import com.ctrip.soa.train.ticketing.clickservice.v1.ClickServiceClient;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailRequest;
import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailResponse;
import com.ctrip.soa.train.trainordercentreservice.noncore.v1.*;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.*;
import com.ctrip.soa.train.trainordercentreservice.v1.*;
import com.ctrip.soa.train.trainordercentreservice.v1.RefundGrabProductIdRequestType;
import com.ctrip.soa.train.trainordercentreservice.v1.RefundGrabProductIdResponseType;
import com.ctrip.soa.train.xproductservice.v1.*;
import com.ctrip.soa.train.xproductservice.v1.ApplyCommonEleterInvoiceRequestType;
import com.ctrip.soa.train.xproductservice.v1.ApplyCommonEleterInvoiceResponseType;
import com.ctrip.soa.train.xproductservice.v1.CommonResponseType;
import com.ctrip.soa.train.xproductservice.v1.DeliveryDTO;
import com.ctrip.soa.train.xproductservice.v1.RepairInvoiceRequestType;
import com.ctrip.soa.train.xproductservice.v1.SearchClaimRequestType;
import com.ctrip.soa.train.xproductservice.v1.SearchClaimResponseType;
import com.ctrip.train.kefu.system.client.pojo.train.RepairInvoiceRequestPojo;
import com.ctriposs.baiji.rpc.common.types.AckCodeType;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import common.util.DateUtils;
import common.util.HttpUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static qunar.util.RequestUtil.mapper;

@Component
public class OrderContract {

    public final String strTrainOrderCentreServiceOfflineClientUrl = "http://10.28.237.252:8080/api/";
    public final String strTrainOrderCentreServiceClientUrl = "http://10.28.100.95:8080/api/";

    /**
     * offline订单详情接口
     **/
    public OfflineOrderDetailResponseType getOrderDedetail(String orderid) {
        OfflineOrderDetailResponseType response = new OfflineOrderDetailResponseType();
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.28.139.201:8080/api/");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            OfflineOrderDetailRequestType requestType = new OfflineOrderDetailRequestType();
            requestType.setOrderNumber(orderid);
            response = client.offlineOrderDetail(requestType);
        } catch (Exception ex) {
            CLogger.error("订单详情接口getOrderDedetail", ex);
        }
        return response;
    }


    /**
     * 订单退优惠卷套餐
     * orderForm
     * 订单id
     * 套餐id
     * 用户名
     */
    public RefundCouponResponseType refundCoupon(String partnerName, String orderId, String couponId, String userName) {
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.28.139.201:8080/api/");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            RefundCouponRequestType requestType = new RefundCouponRequestType();
            requestType.setCouponId(couponId);
            requestType.setOrderNumber(orderId);
            requestType.setPartnerName(partnerName);
            requestType.setUserName(userName);
            return client.refundCoupon(requestType);
        } catch (Exception ex) {
            CLogger.error("RefundCoupon", ex);
            return null;
        }
    }

    /**
     * 增加日志信息
     */
    public AddOrderLogResponseType addOrderLog(Map<String, String> map) {
        AddOrderLogResponseType aolrt = new AddOrderLogResponseType();
        try {
            TrainOrderCentreServiceNonCoreClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceNonCoreClient.getInstance("http://10.28.151.124:8080/api/");
            } else {
                client = TrainOrderCentreServiceNonCoreClient.getInstance();
            }
            AddOrderLogRequestType requestType = new AddOrderLogRequestType();

            requestType.setPartnerOrderId(map.get("partnerOrderId"));//合作方订单号
            requestType.setOrderNumber(map.get("orderId"));//订单号
            requestType.setComment(map.get("comment"));   //内容
            requestType.setOperator(map.get("operator"));  //操作人
            requestType.setReasonType(map.get("reasonType"));//操作原因
            requestType.setActionName(map.get("actionName"));//日志操作类型
            requestType.setOperateTime(DateUtils.getCurTimeStr());//操作时间
            aolrt = client.addOrderLog(requestType);
        } catch (Exception ex) {
            CLogger.error("addOrderLog", ex);
        }
        return aolrt;
    }

    /**
     * 增加日志信息
     */
    public AddOrderLogResponseType addOrderLogDetail(AddOrderLogRequestType request) {
        AddOrderLogResponseType aolrt = new AddOrderLogResponseType();
        try {
            TrainOrderCentreServiceNonCoreClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceNonCoreClient.getInstance("http://10.28.151.124:8080/api/");
            } else {
                client = TrainOrderCentreServiceNonCoreClient.getInstance();
            }
            aolrt = client.addOrderLog(request);
        } catch (Exception ex) {
            CLogger.error("addOrderLog", ex);
        }
        return aolrt;
    }

    /**
     * to do 添加备注信息
     */
    public DeliveryRecommendChangeOrderResponseType requestOrderCentreChangeOrder(Map<String, Object> map) {
        DeliveryRecommendChangeOrderResponseType drcor = new DeliveryRecommendChangeOrderResponseType();
        try {
            TrainOrderCentreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceClient.getInstance("http://10.28.100.101:8080/api/");
            } else {
                client = TrainOrderCentreServiceClient.getInstance();
            }
            DeliveryRecommendChangeOrderRequestType drco = new DeliveryRecommendChangeOrderRequestType();
            drco.setOrderNumber(String.valueOf(map.get("orderId")));
            drco.setPartnerName(String.valueOf(map.get("partnerName")));

            List<Map<String, String>> tickets = (List<Map<String, String>>) map.get("ChangeTicket");

            List<ChangeTicketInfoEntity> changeTicketInfos = new ArrayList<>();
            for (Map<String, String> ticket : tickets) {
                ChangeTicketInfoEntity ctie = new ChangeTicketInfoEntity();
                ctie.setTicketTime(ticket.get("ticketTime"));
                ctie.setTicketSeat(ticket.get("ticketSeat"));
                ctie.setTrainNumber(ticket.get("TrainNumber"));
                ctie.setSectionNumber(Integer.parseInt(ticket.get("SectionNumber")));
                ctie.setAcceptSeat(ticket.get("AcceptSeat"));
                changeTicketInfos.add(ctie);
            }
            drco.setChangeTicketInfos(changeTicketInfos);

            drcor = client.deliveryRecommendChangeOrder(drco);
        } catch (Exception ex) {
            CLogger.error("requestOrderCentreChangeOrder", ex);
        }
        return drcor;
    }

    /**
     * to do 从新接口获取订单详情
     * Creat By:LSY 刘 ; Creat Time:2018-11-02；Remark:从新接口获取订单详情，接口联络人 订单中心HB黄
     */
    public OrderDetailResponseType orderDetail(String orderNumber) {
        OrderDetailResponseType response = new OrderDetailResponseType();
        String strLog = "";
        Map<String, String> dic = new HashMap<>();
        dic.put("orderNumber", orderNumber);
        try {
            TrainOrderCentreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceClient.getInstance("http://10.28.100.95:8080/api/");
            } else {
                client = TrainOrderCentreServiceClient.getInstance();
            }
            OrderDetailRequestType request = new OrderDetailRequestType();
            request.setOrderNumber(orderNumber);
            strLog += ".........请求报文:" + JSON.toJSONString(request);
            response = client.orderDetail(request);
            strLog += ".........返回报文:" + JSON.toJSONString(response);
            CLogger.info("从新接口获取订单详情", strLog, dic);
        } catch (Exception ex) {
//            String strJson = "";
//            response = JSON.parseObject(strJson, OrderDetailResponseType.class);
            CLogger.error("从新接口获取订单详情", ex, dic);
        }
        return response;
    }

    /**
     *
     */
    public com.ctrip.soa.train.xproductservice.v1.RefundAppendProductResponseType refundAppendProdunt(Integer productid, String orderId, String uid) {
        com.ctrip.soa.train.xproductservice.v1.RefundAppendProductResponseType response = new com.ctrip.soa.train.xproductservice.v1.RefundAppendProductResponseType();
        try {
            XProductServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = XProductServiceClient.getInstance("http://ws.ordercenter.train.ctripcorp.com/xbind/api/");
            } else {
                client = XProductServiceClient.getInstance();
            }
            com.ctrip.soa.train.xproductservice.v1.RefundAppendProductRequestType requestType = new com.ctrip.soa.train.xproductservice.v1.RefundAppendProductRequestType();
            requestType.setPartnerOrderId(orderId);
            requestType.setProductId(productid);
            requestType.setPassengerId(0);
            requestType.setProductSubtype("CtripVip");
            requestType.setOperator(uid);
            response = client.refundAppendProduct(requestType);
        } catch (Exception ex) {
            CLogger.error("refundAppendProdunt", ex);
        }
        return response;
    }

    /**
     * 通过UID
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public SearchProductByUidResponseType searchProductByUid(String uid) {
        SearchProductByUidResponseType response = new SearchProductByUidResponseType();
        try {
            TrainOrderCentreServiceXProductClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceXProductClient.getInstance("http://10.28.178.178:8080/api/");
            } else {
                client = TrainOrderCentreServiceXProductClient.getInstance();
            }
            SearchProductByUidRequestType requestType = new SearchProductByUidRequestType();
            requestType.setProductSubType("CtripVip");
            requestType.setUid(uid);
            response = client.searchProductByUid(requestType);
        } catch (Exception ex) {
            CLogger.error("searchProductByUid", ex);
        }
        return response;
    }

    /**
     * @param orderNumber
     * @param username12306 12306用户名
     * @param eOrderNumber  取票号
     * @return
     */
    public QueryOrderDetailResponse queryOrderDetail(String orderNumber, String username12306, String eOrderNumber) {
        QueryOrderDetailResponse response = new QueryOrderDetailResponse();
        try {
            ClickServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = ClickServiceClient.getInstance("http://ws.ticketnetcore.train.ctripcorp.com/train-ticket-clickservice/api/");
            } else {
                client = ClickServiceClient.getInstance();
            }
            client.setFormat("json");
            QueryOrderDetailRequest request = new QueryOrderDetailRequest();
            request.setOrderNumber(orderNumber);
            request.setUsername12306(username12306);
            request.setEOrderNumber(eOrderNumber);
            response = client.queryOrderDetail(request);
        } catch (Exception ex) {
            CLogger.error("QueryOrderDetail", ex);
            return null;
        }
        return response;
    }

    /**
     * 开发票接口
     *
     * @param requestPojo invoiceType：1 纸字发票  3 电子发票
     * @return
     */
    public boolean repairInvoice(RepairInvoiceRequestPojo requestPojo) {
        StringBuilder log = new StringBuilder();
        try {
            XProductServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = XProductServiceClient.getInstance("http://ws.ordercenter.train.ctripcorp.com/xbind/api/");
                //client = XProductServiceClient.getInstance("http://ws.ordercenter.train.uat.qa.nt.ctripcorp.com/xbind/api/");
            } else {
                client = XProductServiceClient.getInstance();
            }
            RepairInvoiceRequestType request = new RepairInvoiceRequestType();
            DeliveryDTO delivery = new DeliveryDTO();
            delivery.setReceiveAddress(requestPojo.getAddress() == null ? "" : requestPojo.getAddress().replace("\n", ""));
            delivery.setReceiverMobile(requestPojo.getMobile());
            delivery.setReceiverName(requestPojo.getName());
            delivery.setZipCode(requestPojo.getZip());
            delivery.setInvoiceTitle(requestPojo.getInvoiceTitle());
            delivery.setTaxpayNumber(requestPojo.getPayerNumber());
            delivery.setOpenBank(requestPojo.getOpenBank());
            delivery.setAccoutNumber(requestPojo.getBankNumber());

            request.setEmail(requestPojo.getEmail());
            request.setDelivery(delivery);
            request.setOperator(requestPojo.getOperatorName());
            request.setPartnerOrderId(requestPojo.getOrderNumber());//订单号
            request.setPartnerName(requestPojo.getPartnerName());//订单来源
            switch (requestPojo.getProductType()) {
                case "1":  // 保险
                case "6":  // 抢票险
                    request.setType(0);
                    break;
                case "2":  // 礼品卡
                    request.setType(1);
                    break;
                case "4": // 延误险
                    request.setType(4);
                    break;
                case "11":
                    request.setType(5);
                    break;
                default:
                    request.setType(0); //
                    break;
            }
            request.setProductLine(101);
            request.setInvoiceType(requestPojo.getInvoiceType());
            log.append(String.format("Request: %s", JSON.toJSONString(request)));
            CommonResponseType commonresponse = client.repairInvoice(request);
            log.append(String.format("Response: %s", JSON.toJSONString(commonresponse)));
            CLogger.info("RepairInvoice", log.toString());
            if (commonresponse.getResponseStatus().getAck() == AckCodeType.Success) {
                return true;
            }

        } catch (Exception ex) {
            CLogger.error("RepairInvoice", ex);
            return false;
        }
        return false;
    }

    /**
     * offline获取订单列表
     **/
    public OfflineOrderListResponseType getUserOrderListResponse(OfflineOrderListRequestType request) {
        OfflineOrderListResponseType response = new OfflineOrderListResponseType();
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.28.182.216:8080/api");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            response = client.offlineOrderList(request);
        } catch (Exception ex) {
            CLogger.error("getUserOrderListResponse", ex);
        }
        return response;
    }

    /**
     * 取消订单
     **/
    public OfflineCancelOrderResponseType offlineCancelOrder(OfflineCancelOrderRequestType request) {
        OfflineCancelOrderResponseType response = new OfflineCancelOrderResponseType();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + strTrainOrderCentreServiceOfflineClientUrl + "offlineCancelOrder" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, OfflineCancelOrderResponseType.class);
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
                response = client.offlineCancelOrder(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            CLogger.error("offlineCancelOrder", ex);
        }
        CLogger.info("取消订单", strLog);
        return response;
    }

    /**
     * 退票(订单中心XQ提供，应该只能退订改签票)
     **/
    public RequestReturnRescheduleResponseType requestReturnReschedule(RequestReturnRescheduleRequestType request) {
        RequestReturnRescheduleResponseType response = new RequestReturnRescheduleResponseType();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainOrderCentreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + strTrainOrderCentreServiceClientUrl + "requestReturnReschedule" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, RequestReturnRescheduleResponseType.class);
            } else {
                client = TrainOrderCentreServiceClient.getInstance();
                response = client.requestReturnReschedule(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            CLogger.error("requestReturnReschedule", ex);
        }
        CLogger.info("退票", strLog);
        return response;
    }

    /**
     * 退票(订单中心HB提供，原票和改签票应该都可以退)
     **/
    public ReturnTicketServiceResponseType returnTicketService(ReturnTicketServiceRequestType request) {
        ReturnTicketServiceResponseType response = new ReturnTicketServiceResponseType();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainOrderCentreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + strTrainOrderCentreServiceClientUrl + "returnTicketService" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, ReturnTicketServiceResponseType.class);
            } else {
                client = TrainOrderCentreServiceClient.getInstance();
                response = client.returnTicketService(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            CLogger.error("returnTicketService", ex);
        }
        CLogger.info("退票", strLog);
        return response;
    }

    /**
     * 超级会员电子发票
     *
     * @return
     */
    public ApplyCommonEleterInvoiceResponseType getApplyCommonEleterInvoice(ApplyCommonEleterInvoiceRequestType request) {
        ApplyCommonEleterInvoiceResponseType response = new ApplyCommonEleterInvoiceResponseType();
        try {
            XProductServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = XProductServiceClient.getInstance("http://ws.ordercenter.train.ctripcorp.com/xbind/api/");
            } else {
                client = XProductServiceClient.getInstance();
            }
            response = client.applyCommonEleterInvoice(request);
        } catch (Exception ex) {
            CLogger.error("getApplyCommonEleterInvoice", ex);
        }
        return response;
    }

    /**
     * 获取发票订单详情
     *
     * @param partnerOrderId
     * @param xProductType
     * @return
     * @throws Exception
     */
    public GetOrderInvoiceByPartnerOrderIdResponseType getOrderInvoiceByPartnerOrderId(String partnerOrderId, int xProductType) {
        GetOrderInvoiceByPartnerOrderIdResponseType response = new GetOrderInvoiceByPartnerOrderIdResponseType();
        try {
            TrainOrderCentreServiceXProductClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceXProductClient.getInstance("http://10.28.178.178:8080/api/");
            } else {
                client = TrainOrderCentreServiceXProductClient.getInstance();
            }
            GetOrderInvoiceByPartnerOrderIdRequestType request = new GetOrderInvoiceByPartnerOrderIdRequestType();
            request.setPartnerOrderId(partnerOrderId);
            request.setXProductType(xProductType);

            response = client.getOrderInvoiceByPartnerOrderId(request);
            CLogger.info("getOrderInvoiceByPartnerOrderId", JSON.toJSONString(response));
        } catch (Exception ex) {
            CLogger.error("getOrderInvoiceByPartnerOrderId", ex);
        }
        return response;
    }

    /**
     * 红冲
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    public boolean applyRedEleterInvoice(String orderId) {
        TrainOrderCentreServiceXProductClient client;
        ApplyRedEleterInvoiceResponseType response = new ApplyRedEleterInvoiceResponseType();
        Map<String, String> logInfo = new HashMap<String, String>();
        logInfo.put("orderid", orderId);
        try {
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceXProductClient.getInstance("http://ws.ordercenter.train.ctripcorp.com/xbind/api/");
            } else {
                client = TrainOrderCentreServiceXProductClient.getInstance();
            }
            ApplyRedEleterInvoiceRequestType request = new ApplyRedEleterInvoiceRequestType();
            request.setOrderId(orderId);
            response = client.applyRedEleterInvoice(request);
            if (response == null) {
                return false;
            }
            if (response.getRetCode() >= 0) {
                return true;
            }
        } catch (Exception ex) {
            CLogger.info("设置红冲异常contract", ex.toString(), logInfo);
        } finally {
            CLogger.info("设置红冲", JSON.toJSONString(response), logInfo);
        }
        return false;
    }

    /**
     * 订单退优惠卷套餐
     * orderForm
     * 订单id
     * 套餐id
     * 用户名
     */
    public OfflineOrderLogListResponseType getOfflineOrderLogList(String orderId) {
        OfflineOrderLogListResponseType response;
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.5.58.185:8080/api/");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            OfflineOrderLogListRequestType requestType = new OfflineOrderLogListRequestType();
            if (Foundation.server().getEnv().isFAT()) {
                orderId = "3016200903";
            }
            requestType.setOrderNumber(orderId);
            response = client.offlineOrderLogList(requestType);
        } catch (Exception ex) {
            CLogger.error("getOfflineOrderLogList", ex);
            return null;
        }
        return response;
    }

    /**
     * 批量退默认加速包
     *
     * @param orderNumber
     * @return
     * @throws Exception
     */
    public RefundGrabProductIdResponseType refundGrabProductId(String orderNumber, String operator) {
        RefundGrabProductIdResponseType responseType = new RefundGrabProductIdResponseType();
        try {
            TrainOrderCentreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceClient.getInstance("http://10.28.100.95:8080/api/");
            } else {
                client = TrainOrderCentreServiceClient.getInstance();
            }
            RefundGrabProductIdRequestType requestType = new RefundGrabProductIdRequestType();
            requestType.setOperator(operator);
            requestType.setOrderNumber(orderNumber);
            requestType.setProductId(9999);

            responseType = client.refundGrabProductId(requestType);
            CLogger.info("refundGrabProductId", JSON.toJSONString(responseType), (new HashMap<String, String>() {{
                put("orderNumber", orderNumber);
            }}));
        } catch (Exception ex) {
            CLogger.error("refundGrabProductId", ex);
            return null;
        }
        return responseType;
    }

    /**
     * 退加速包
     **/
    public RefundAllGrabBagResponseType refundAllGrabBag(String orderid, String operator) {
        RefundAllGrabBagResponseType response = new RefundAllGrabBagResponseType();
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.28.139.201:8080/api/");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            RefundAllGrabBagRequestType requestType = new RefundAllGrabBagRequestType();
            requestType.setOrderNumber(orderid);
            requestType.setOperator(operator);
            response = client.refundAllGrabBag(requestType);
            CLogger.info("refundAllGrabBag", JSON.toJSONString(response), (new HashMap<String, String>() {{
                put("orderid", orderid);
            }}));
        } catch (Exception ex) {
            CLogger.error("退加速包refundAllGrabBag", ex);
            return null;
        }
        return response;
    }

    /**
     * 取消订单
     **/
    public OfflineCancelOrderResponseType offlineCancelOrder(String orderNumber, String partnerName,
                                                             String operator, String cancelReason) {
        OfflineCancelOrderResponseType response = new OfflineCancelOrderResponseType();
        try {
            TrainOrderCentreServiceOfflineClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceOfflineClient.getInstance("http://10.28.101.29:8080/api/");
            } else {
                client = TrainOrderCentreServiceOfflineClient.getInstance();
            }
            OfflineCancelOrderRequestType requestType = new OfflineCancelOrderRequestType();
            requestType.setOrderNumber(orderNumber);
            requestType.setCancelReason(cancelReason);
            requestType.setOperator(operator);
            requestType.setPartnerName(partnerName);
            requestType.setOperateTime(DateUtils.getCurTimeStr());
            response = client.offlineCancelOrder(requestType);
        } catch (Exception ex) {
            CLogger.error("getTrainXProductInfo", ex);
        }
        return response;
    }

    /**
     * 删除订单接口
     *
     * @param
     * @return
     */
    public DeleteOrderResponseType deleteOrder(String orderNumber, String partnerName,
                                               String operator, String reason, String userId) {
        DeleteOrderResponseType responseType = new DeleteOrderResponseType();
        try {
            TrainOrderCentreServiceNonCoreClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainOrderCentreServiceNonCoreClient.getInstance("http://10.5.59.10:8080/api/");
            } else {
                client = TrainOrderCentreServiceNonCoreClient.getInstance();
            }
            DeleteOrderRequestType requestType = new DeleteOrderRequestType();
            requestType.setUserID(userId);
            requestType.setDeleteReason(reason);
            requestType.setOrderNumber(orderNumber);
            requestType.setOperateTime(DateUtils.getCurTimeStr());
            requestType.setPartnerName(partnerName);
            requestType.setOperator(operator);
            responseType = client.deleteOrder(requestType);
        } catch (Exception ex) {
            CLogger.error("deleteOrder", ex);
        }
        return responseType;
    }

    /**
     * 抢票险理赔状态
     */
    public SearchClaimResponseType searchClaim(String insuranceNos) {
        SearchClaimResponseType response = new SearchClaimResponseType();
        try {
            XProductServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = XProductServiceClient.getInstance("http://ws.ordercenter.train.ctripcorp.com/xbind/api/");
                insuranceNos = "42648547784356655";
            } else {
                client = XProductServiceClient.getInstance();
            }
            SearchClaimRequestType requestType = new SearchClaimRequestType();
            requestType.setInsuranceNos(insuranceNos);
            response = client.searchClaim(requestType);
            CLogger.info("searchClaim", JSON.toJSONString(response));
        } catch (Exception ex) {
            CLogger.error("searchClaim", ex);
        }
        return response;
    }
}
