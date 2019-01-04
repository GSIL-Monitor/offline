package com.ctrip.train.kefu.system.offline.system.vm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmGroupResponse {

    private int tid;
    private String groupName;
    private String productLine;
    private int isDelete;
    private String supervisorStaffNUmber;
    private String groupDesc;
    private int groupType;
    private String staffName;

}
