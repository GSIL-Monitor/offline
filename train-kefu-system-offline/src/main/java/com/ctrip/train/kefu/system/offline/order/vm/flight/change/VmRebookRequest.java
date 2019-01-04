package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmRebookRequest {

    private String tyUserId;
    private String ctripUId;
    private String operatorName;
    private String source;
    private String flightData;
    private long orderId;
    private String subClassData;

    private List<VmFlightPassenger> passengers;
    private VmRebookInformation rebookInformation;

}
