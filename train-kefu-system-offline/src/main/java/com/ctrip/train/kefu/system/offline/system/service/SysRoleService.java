package com.ctrip.train.kefu.system.offline.system.service;


import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface SysRoleService {
    /**
     * 分页查询分组
     * @return
     */
    PageInfo searchRoleForPage(int pageIndex, int pageSize);


    /**
     * 增加分组
     */
    int addRole(VmRoleRequest request) throws SQLException;

    /**
     * 增加分组
     */
    int editRole(VmRoleRequest request);

    /**
     * 删除分组
     */
    int deleteRole(int tid);

    /**
     * @param roleId
     * @return
     */
    VmRoleResponse getRoleByRoleId(long roleId);

    /**
     * 角色关联菜单
     * @param rid
     * @return
     */
    List<Map<String, Object>> searchRoleMenu(Long rid);

    /**
     * 增加角色菜单关联
     * @param roleId
     * @param menuIds
     */
    void updateRoleMenu(long roleId,String menuIds) throws SQLException;

    /**
     *
     * @return
     */
    List<VmRoleResponse> searchAllRole();


    /**
     * 查询角色关联权限
     * @param rid
     * @return
     */
    List<Map<String, Object>> searchRolePerm(Long rid);

    /**
     * 更新角色权限关联
     * @param roleId
     * @param menuIds
     */
    void updateRolePerm(long roleId,String menuIds) throws SQLException;
}
