package com.ctrip.train.kefu.system.offline.order.vm.flight.change;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRebookInformation {

    private int segmentNo;
    private int sequence;
    private int rebookMinDaysBefore;
    private int rebookMinDaysAfter;
    private Boolean rebookAble;
    private String rebookDateAfter;
    private String rebookDateBefore;
    private String freeDateAfter;
    private String freeDateBefore;

}
