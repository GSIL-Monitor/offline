package com.ctrip.train.kefu.system.offline.system.service;


import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenusResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<VmMenusResponse> searchStaffMenus (Map<String,String> map);

    PageInfo searchMenuForPage(VmMenuRequest request);

    VmMenuResponse queryMenuBytId(Long tid) throws SQLException;

    int addMenu(VmMenuRequest request) throws SQLException;

    int editMenu(VmMenuRequest request) throws SQLException;

    int deleteMenu(long tid) throws SQLException;

    List<VmMenusResponse> availableMenus();
}
