package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VmOrderDeliveryInfo {

    //配送地址
    private String deliveryAddress;
    //四级地址
    private String areaName;
    //代售点名称
    private String agentName;
    //物流费
    private BigDecimal deliveryPrice;
    //物流单号
    private String deliveryNumber;
    //配送状态
    private int deliveryState;
    //物流总公司缩写
    private String deliverCompany;

    //地址图片
    private String addressPicUrl;
    // 柜台票 1
    private int ticketFlag;


}
