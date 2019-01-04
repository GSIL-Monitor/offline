package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.domain.train.DmTrainOrderDetail;
import com.ctrip.train.kefu.system.offline.order.vm.QueryOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmApplyRefund;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmRequestSearchS2S;
import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmResponseS2S;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jian_ji on 2018/7/2.
 */
public interface TrainOrderService {

    QueryOrderEx getQueryOrderDetail(String OrderNumber, String Username12306, String EOrderNumber);

    /**
     * 获取订单详情
     */
    DmTrainOrderDetail getOrderDetail(String orderID);

    /**
     * 获取12306帐号密码
     * @param username
     * @param orderId
     * @param operId
     */
    String searchPassword(String username, String orderId, String operId);

    /**
     * 查询附加产品信息
     * @param productID
     * @return
     */
    String appendProductInfo(String action,Integer productID);

    /**
     * 退优惠券套餐
     * @param partnerName
     * @param orderId
     * @param couponId
     * @param uid
     */
    String refundCoupon(String partnerName, String orderId, String couponId, String uid);

    /**
     * 批量退默认加速包
     * @param orderNumber
     * @param operator
     * @return
     */
    String refundGrabProductId(String orderNumber,String operator);

    /**
     * 批量退加速包
     * @param orderNumber
     * @param operator
     * @return
     */
    String refundAllGrabBag(String orderNumber,String operator);

    /**
     * 查询火车经停
     * @param request
     * @return
     */
    List<VmResponseS2S> searchS2S(VmRequestSearchS2S request);
    /**
     * 查询火车站地址
     * @return
     */
    String getStationsAddress(String name);

    /**
     * 火车票的真实加速等级
     * @param orderNum
     * @return
     */
    BigDecimal getTrainOrderPackageLevel(String orderNum);

     /**
     * 抢票险理赔状态
     * @param orderId
     * @param insuranceNos
     * @return
     */
    String getInsuranceState(String orderId,String insuranceNos);

    /**
     * 删除火车票订单
     * @param orderNumber
     * @param operator
     * @param partnerName
     * @param userId
     * @return
     */
    String trainOrderDelete(String orderNumber, String operator,String partnerName,String userId);

    /**
     * 查看地址图片
     * @return
     */
    String getStationImageUrl(String departStationName,String DepartureDateTime);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    String cancelOrder(String orderId,String operater);

    /**
     * 申请退票
     */
    String applyRefundTicket(VmApplyRefund refund);

}
