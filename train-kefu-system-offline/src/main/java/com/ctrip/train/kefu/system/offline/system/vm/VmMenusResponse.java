package com.ctrip.train.kefu.system.offline.system.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class VmMenusResponse {
    //菜单图标
    String icon;
    String menuName;
    String url;
    String menuId;

    List<VmMenusResponse> nodes;
}
