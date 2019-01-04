package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmFlightSerach {

    private Long orderid;
    private String departDate;
    private String departCityCode;
    private String arriveCityCode;
    private List<VmFlightSegments> segments;
    private List<VmFlightPassenger> passengers;
    private VmRebookInformation rebookInformation;

}
