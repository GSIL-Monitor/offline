package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmFlightSegments {

    private String departDate;
    private String departCityCode;
    private String arriveCityCode;
    private String flightNumber;
    private String cabinCode;
    private String cabinName;
    private String airlineCode;
    private String routeIndex;

}
