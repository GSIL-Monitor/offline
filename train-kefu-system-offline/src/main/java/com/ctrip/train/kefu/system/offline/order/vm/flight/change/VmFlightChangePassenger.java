package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmFlightChangePassenger {

    private String passengerName;

    private int passengerType;

    private String remarks;

    private String cardNo;

    private int cardType;

    private int gender;

    private int rebookType;

    private String birthday;
}
