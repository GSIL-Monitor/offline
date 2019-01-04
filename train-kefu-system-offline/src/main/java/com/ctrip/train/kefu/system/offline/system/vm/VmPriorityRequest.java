package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmPriorityRequest {

    private Long id;
    private String staffNum;
    private String staffName;
    private String noticeProductLine;
    private String envenType;
    private String available;
    private String noticeTypes;
    private String complainTypes;
    private String leaderTypes;
    private int pageIndex;
    private int pageSize;
}
