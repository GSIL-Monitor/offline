package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmFlightChangeCollect {
    private String msgConteng;
    //
    private List<VmFlightChange> flightChangeDetail;
}
