package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VmPostRequest {
    private long id;
    private String positionName;
    private String positionDesc;
    private int isDelete;
    private int pageIndex;
    private int pageSize;
}
