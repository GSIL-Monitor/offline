package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmFlightPassenger {
    private String passengerName;
    private int passengerType;
    private int cardType;
    private String cardNo;
    private int gender;
    private String birthday;
    private int rebookType;
}
