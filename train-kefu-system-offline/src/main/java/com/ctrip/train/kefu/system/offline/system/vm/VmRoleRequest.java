package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRoleRequest {
    private Integer roleId;
    private String roleName;
    private String productLine;
    private Integer available;
    private String inputRoleDesc;
    private int pageIndex;
    private int pageSize;
}
