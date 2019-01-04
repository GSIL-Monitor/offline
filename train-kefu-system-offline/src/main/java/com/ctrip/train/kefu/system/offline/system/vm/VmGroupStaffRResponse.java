package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmGroupStaffRResponse {
    private Long tid;
    private Integer groupTid;
    private String staffNumber;
    private Integer isDelete;
    private String staffName;
    private String checked;
}
