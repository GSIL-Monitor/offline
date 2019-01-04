package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
/**
 * 优惠券套餐
 */
public class DmTrainCouponInfo {

    private String couponCancleTime;   //优惠券取消时间
    private String couponCode;   //优惠券编号
    private Long couponId;   //优惠券表
    private BigDecimal couponPrice;   //优惠券金额
    private BigDecimal couponRealPrice;   //优惠券当前使用金额
    private Integer couponState;   //优惠券状态
    private String couponType;   //优惠券类型
    private String couponUsageTime;   //优惠券使用时间
    private String createTime;   //创建时间
    private String dataChangeLastTime;   //更新时间
    private Long orderId;   //订单号
    private String partnerName;   //合作方名称
    private String partnerOrderId;   //合作方订单号
    private BigDecimal couponDiscount;   //优惠券抵扣金额
    private String couponPolicyNo;   //优惠券策略使用号
}
