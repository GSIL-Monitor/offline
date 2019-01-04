package com.ctrip.train.kefu.system.offline.order.vm.flight.refund;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmInsurance {

    /**
     * 保险名称
     */
    private String insuranceName;


    /**
     * 保险金额
     */
    private String insurancePrice;
}
