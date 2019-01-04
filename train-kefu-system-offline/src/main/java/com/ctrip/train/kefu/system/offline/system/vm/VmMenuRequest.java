package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmMenuRequest {

    private long id;
    private String menuName;
    private String type;
    private String url;
    private long parentId;
    private String pMenuName;
    private int sort;
    private int available;
    private String icon;
    private String menuId;
    private int pageIndex;
    private int pageSize;

}
