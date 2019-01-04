package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmFlightChange {
    private Long orderId;

    private String contactName;
    private String contactPhone;

    //航班信息
    private VmFlightChangeDetail flightDetails;
    //乘客信息
    private List<VmFlightChangePassenger> passengers;
}
