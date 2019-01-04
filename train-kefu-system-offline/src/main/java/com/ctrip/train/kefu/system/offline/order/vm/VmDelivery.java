package com.ctrip.train.kefu.system.offline.order.vm;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmDelivery {

    private String zipCode;
    private String invoiceMailAddress;
    private String addresseeName;
    private String receiverMobile;
}
