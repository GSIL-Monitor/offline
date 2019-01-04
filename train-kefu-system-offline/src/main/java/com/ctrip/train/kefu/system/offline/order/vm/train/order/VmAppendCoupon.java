package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 默认套餐（优惠券）
 */
@Setter
@Getter
public class VmAppendCoupon {
    private String couponTypeName;   //优惠券名称
    private String couponCode;   //优惠券码;
    private BigDecimal couponPrice;     //优惠券金额;
    private BigDecimal couponRealPrice; //优惠券使用金额;
    private String couponUsageTime;  //优惠券出成功时间;
    private Integer couponState; //优惠券状态;
    private String couponStateName;
    private String couponTicketFeedesc;    //抵扣类型;
    private Boolean isCanRefund;   //是否可退套餐
    private Long couponId;  //优惠券表
}

