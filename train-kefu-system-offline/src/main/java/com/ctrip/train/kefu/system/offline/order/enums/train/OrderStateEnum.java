package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 订单状态
 */
public enum OrderStateEnum {

    WaitPay(1, "已提交待支付"),
    SuccessPay(2, "支付中"),
    Extract(3, "支付失败"),
    WaitTicket(4, "支付成功"),   //WaitTicket(4, "待出票"),
    Ticketing(5, " 出票中"),
    WatiDelivery(6, "待配送"),  //WatiDelivery(6, "已出票"),
    hasDelivery(7, "已配送"),
    Sign(8, "出票成功"),    //Sign(8, "已完成"),
    Cancel(9, "已取消"),
    Default(99, "");    //
    private Integer code;
    private String name;

    OrderStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static OrderStateEnum convertVendor(Integer code){
        return Arrays.stream(OrderStateEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(Default);
    }
}
