package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmPermissionRequest {
    private Long id;
    private String permName;
    private String permCode;
    private String permType;
    private String permDesc;
    private String isDelete;
    private int pageIndex;
    private int pageSize;
}
