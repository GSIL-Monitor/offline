package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRemarks {
    private Long Tid;
    private String orderId;
    private String operator;
    private String operatorTime;
    private String node;
}
