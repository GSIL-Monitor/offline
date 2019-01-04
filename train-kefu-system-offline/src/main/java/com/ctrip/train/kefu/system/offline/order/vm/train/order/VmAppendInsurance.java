package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 乘客附加信息（抢票险）
 */
@Setter
@Getter
public class VmAppendInsurance {
    private Integer productId;  //产品ID
    private String productTitle;  //产品名称
    private String productType; //产品类型 附加产品类型I保险 G礼品卡 C优惠券 V VIP休息室 T门票 O其他
    private BigDecimal productPrice;   //产品单价
    private Integer productNum; //产品数量
    private String productState;
    private String passportType; //证件类型
    private String passportNumber;  //证件号码
    private String passengerName;   //乘客名
    private String birthDay;    //出生日期
    private String insuranceId;
}


