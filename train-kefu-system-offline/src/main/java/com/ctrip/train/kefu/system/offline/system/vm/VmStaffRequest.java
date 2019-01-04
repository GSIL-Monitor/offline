package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmStaffRequest {
    private String staffNum;
    private String staffName;
    private String groupId;
    private String productLine;
    private Long positionId;

    private String roleId;
    private String email;

    private int pageIndex;
    private int pageSize;

}
