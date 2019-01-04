package com.ctrip.train.kefu.system.api.service.staff;

import com.ctrip.train.kefu.system.api.dao.staff.ExtStaffPermission;
import common.log.CLogger;
import dao.ctrip.ctrainpps.entity.StaffPermission;
import dao.ctrip.ctrainpps.entity.StaffPermissionRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StaffPermissionService {

    @Autowired
    private ExtStaffPermission staffPermission;

    /**
     * 验证用户是否有权限（老代码迁移需要优化）
     **/
    public boolean hasPermission(String eid, String permissionCode) {
        try {
            StaffPermission sample = new StaffPermission();
            sample.setIsDelete(1);
            sample.setPermissionCode(permissionCode);
            List<StaffPermission> permissions = staffPermission.queryLike(sample);
            if (permissions == null || permissions.isEmpty()) {
                return true;
            }
            List<StaffPermissionRelation> list = staffPermission.getPermissionsRelation(eid, permissionCode, 0);
            return list != null && !list.isEmpty();
        } catch (SQLException e) {
            CLogger.error("hasPermission", e);
        }
        return false;
    }
}
