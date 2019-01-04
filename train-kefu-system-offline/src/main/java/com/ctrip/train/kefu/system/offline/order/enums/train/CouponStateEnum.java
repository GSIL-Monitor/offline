package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 优惠券状态 10 已使用，20 已取消，30 未发放，35 已发放，40 发放失败，45 已退，50 策略优惠券取消
 */
public enum CouponStateEnum {

    Used(10,"已使用"),
    Cancled(20,"已取消"),
    UnSend(30,"未发放"),
    SendDealing(31,"发放处理中"),
    Sended(35,"已发放"),
    SendFail(40,"发放失败"),
    Refund(45,"已退"),
    CancelMarketCoupon(50,"策略优惠券取消"),
    CashCouponBacked(55,"已返现"),
    Default(99,"");    //

    private Integer code;
    private String name;

    CouponStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static CouponStateEnum convertVendor(Integer code){
        return Arrays.stream(CouponStateEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(Default);
    }
}
