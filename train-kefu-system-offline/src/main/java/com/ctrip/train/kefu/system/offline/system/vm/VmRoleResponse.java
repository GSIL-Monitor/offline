package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRoleResponse {

    private String roleName;
    private Long roleId;
    private String productLine;
    private int available;
    private String description;

}
