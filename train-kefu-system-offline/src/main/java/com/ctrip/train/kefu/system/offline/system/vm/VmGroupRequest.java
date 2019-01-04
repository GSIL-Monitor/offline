package com.ctrip.train.kefu.system.offline.system.vm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmGroupRequest {
    private int tid;
    private String groupName;
    private String productLine;
    private String supervisorStaffNUmber;
    private String groupDesc;

}
