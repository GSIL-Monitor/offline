package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter

public class DmOrderDeliveryInfo {
    //订单号
    private long orderId;
    //物流类型
    private int deliveryType;
    //配送地址
    private String deliveryAddress;
    //物流区域
    private int areaId;
    //订单分配规则
    private int ruleId;
    //四级地址
    private String areaName;
    //非配送票的联系人
    private String receiverName;
    //非配送票时候联系人电话
    private String receiverPhone;
    //非配送票时候联系人手机
    private String receiverMobile;
    //代售点名称
    private String agentName;
    //代售点出票ID
    private String agentUserId;
    //代售点编号
    private String agentCode;
    //物流费
    private BigDecimal deliveryPrice;
    //物流成本
    private BigDecimal deliveryCost;
    //物流单号
    private String deliveryNumber;
    //物流公司名称
    private String supplierName;
    //物流公司id
    private int supplierId;
    //是否打印过
    private int printState;
    //签收时间
    private String signTime;
    //配送状态
    private int deliveryState;
    //是否周末配送
    private int weekendDelivery;
    //创建时间
    private String createTime;
    //最后更新时间
    private String dataChangeLastTime;
    //礼品卡，保险 发票收件人地址
    private String insuranceUserAddress;
    //礼品卡，保险 发票收件人姓名
    private String insuranceUserName;
    //礼品卡，保险 发票收件人电话
    private String insuranceUserTel;
    //礼品卡，保险 发票收件人邮编
    private String insuranceUserZipCode;
    //配送票的多语言配送地址
    private String shipAddressDetailMultiLangu;
    //配送时间
    private String deliveryTime;
    //代售点操作人
    private String agentUserName;
    //物流总公司缩写
    private String deliverCompany;
    //二维码Url
    private String qrCodeUrl;
    //二维码取票码
    private String fetchTicketCode;
}
