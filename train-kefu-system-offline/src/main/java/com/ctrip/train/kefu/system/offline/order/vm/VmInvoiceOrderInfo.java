package com.ctrip.train.kefu.system.offline.order.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmInvoiceOrderInfo {

    private String orderNumber;
    private String ticketTime;
    private String fromStationName;
    private String toStationName;
    private int invoiceType;
    private String partnerName;
    private String invoiceTypebtn;
}
