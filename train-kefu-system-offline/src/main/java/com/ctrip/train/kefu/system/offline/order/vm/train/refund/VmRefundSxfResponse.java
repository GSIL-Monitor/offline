package com.ctrip.train.kefu.system.offline.order.vm.train.refund;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VmRefundSxfResponse {
    /**
     * 票总额
     */
    public BigDecimal totalPrice ;

    /**
     * 手续费
     */
    public BigDecimal xsfPrice;

    /**
     * 可退金额
     */
    public BigDecimal ktPrice;

    /**
     * 手续费百分比
     */
    public Integer xsfRate;

}

