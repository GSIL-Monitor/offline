package com.ctrip.train.kefu.system.offline.system.service;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionResponse;

import java.sql.SQLException;

public interface SysPermissionService {
    PageInfo searchPermissionForPage(VmPermissionRequest request);

    VmPermissionResponse queryPermissionByPermissionNum(String Permission);

    VmPermissionResponse queryPermissionBytId(Long tid) throws SQLException;

    int addPermission(VmPermissionRequest request) throws SQLException;

    int deletePermission(String PermissionNum,int isDelete);

    int editPermission(VmPermissionRequest request) throws SQLException;

    int updatePerm(long tid) throws SQLException;
}
