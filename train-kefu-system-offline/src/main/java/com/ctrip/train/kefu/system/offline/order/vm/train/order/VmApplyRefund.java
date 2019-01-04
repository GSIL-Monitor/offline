package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请退票
 */
@Getter
@Setter
public class VmApplyRefund {
    //合作方名称
    private String partnerName;
    //合作方订单号
    private String orderNumber;
    //操作者
    private String operator;
    //申请改签票退票的长电子订单号
    private String longElecNums;
}
