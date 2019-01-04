package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmPermissionResponse {
    private Long id;
    private String permName;
    private String permCode;
    private String permType;
    private String permDesc;
    private String isDelete;
}
