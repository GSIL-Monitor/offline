package com.ctrip.train.kefu.system.offline.order.vm.train.order;


import lombok.Getter;
import lombok.Setter;

/**
 * 支付信息
 */
@Getter
@Setter
public class PayWayItemInfo {

    private String paymentWayID;
    private String paymentWayName;
    private String prepayType;
    private int subPaySystem;
    private String paySystemName;
    private String actionMode;
    private int creditCardType;
    private String paymentWayGlobalName;
    private int creditCardBankID;
}
