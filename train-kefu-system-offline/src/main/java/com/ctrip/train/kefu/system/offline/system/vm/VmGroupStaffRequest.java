package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmGroupStaffRequest {
    private long groupId;
    private String groupName;
    private String staffName;
    private String staffNum;
    private String groupDesc;
}
