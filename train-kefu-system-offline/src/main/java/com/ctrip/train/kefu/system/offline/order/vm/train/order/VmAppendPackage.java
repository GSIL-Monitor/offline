package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 附属产品（加速包）
 */
@Setter
@Getter
public class VmAppendPackage {

    private BigDecimal productPrice;   //产品单价
    private Integer productNum; //产品数量
    private BigDecimal totalPrice;   //总金额
    private String isDefault;  //默认非默认
    private String appendState;   //状态
    private Integer productId;  //产品ID
    private String productTitle;  //产品名称
    private String productType; //产品类型 附加产品类型I保险 G礼品卡 C优惠券 V VIP休息室 T门票 O其他
}

