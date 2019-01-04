package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VmPriorityResponse {
    private Long id;
    private String staffNum;
    private String staffName;
    private String noticeProductLine;
    private String noticeTypes;
    private String envenType;
    private String available;
}
