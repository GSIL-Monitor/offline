package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRequestSearchS2S {
    private String departStationName;
    private String arriveStationName;
    private String ticketDate;
    private String trainNo;
}
