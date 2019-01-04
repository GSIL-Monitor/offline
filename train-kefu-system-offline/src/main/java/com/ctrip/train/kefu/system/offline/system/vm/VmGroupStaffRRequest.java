package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmGroupStaffRRequest {
    private long tid;
    private int groupId;
    private String staffNums;
}
