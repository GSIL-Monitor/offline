package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysMenuRoleInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysOfflineRole;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysPermRole;
import com.ctrip.train.kefu.system.offline.system.domain.PermRoleResult;
import com.ctrip.train.kefu.system.offline.system.domain.RoleMenuResult;
import com.ctrip.train.kefu.system.offline.system.service.SysRoleService;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleResponse;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainchat.entity.OfflineMenuRole;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import dao.ctrip.ctrainchat.entity.OfflineRolePerm;
import org.modelmapper.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private ExtSysOfflineRole extSysOfflineRole;

    @Autowired
    private ExtSysMenuRoleInfo extSysMenuRoleInfo;

    @Autowired
    private ExtSysPermRole extSysPermRole;

    @Override
    public PageInfo searchRoleForPage(int pageIndex, int pageSize) {
        List<OfflineRole> list=extSysOfflineRole.searchRoleList(pageIndex,pageSize);
        int count=extSysOfflineRole.searchRoleListCount();
        PageInfo response= new PageInfo();
        if(list!=null){
            ModelMapper modelMapper = new ModelMapper();
            List<VmRoleResponse> vmlist = modelMapper.map(list,new TypeToken<List<VmRoleResponse>>() {}.getType());
            response.setData(vmlist);
            response.setRecordsTotal(count);
            response.setRecordsFiltered(count);
            return response;
        }
        return null;
    }

    @Override
    public int addRole(VmRoleRequest request) throws SQLException {
        OfflineRole role =new OfflineRole();
        long roleId= extSysOfflineRole.getRoleIdInfo();
        role.setRoleId(++roleId);
        role.setCreateTime(DateUtils.getCurFullTimestamp());
        role.setAvailable(request.getAvailable());
        role.setRoleName(request.getRoleName());
        role.setDescription(request.getInputRoleDesc());
        return extSysOfflineRole.insert(role);
    }

    @Override
    public int editRole(VmRoleRequest request) {
        OfflineRole or= extSysOfflineRole.getRoleByRoleId(Long.valueOf(request.getRoleId()));
        or.setDescription(request.getInputRoleDesc());
        or.setAvailable(request.getAvailable());
        or.setRoleName(request.getRoleName());
        or.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
        try {
            return extSysOfflineRole.update(or);
        } catch (SQLException e) {
            CLogger.error("dao:editRole", e);
            return 0;
        }
    }


    @Override
    public int deleteRole(int tid) {
        return extSysOfflineRole.deleteRole(tid);
    }

    @Override
    public VmRoleResponse getRoleByRoleId(long roleId) {
        OfflineRole or= extSysOfflineRole.getRoleByRoleId(roleId);
        if(or!=null){
            ModelMapper modelMapper = new ModelMapper();
            return  modelMapper.map(or,new TypeToken<VmRoleResponse>() {}.getType());
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> searchRoleMenu(Long rid) {

        List<RoleMenuResult> list = extSysOfflineRole.searchRoleMenu(rid);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (RoleMenuResult roleMean : list) {
            map = new HashMap<String, Object>();
            map.put("id", roleMean.getMenuId());
            map.put("pId", roleMean.getParentId());
            map.put("checked", roleMean.getChecked());
            map.put("name", roleMean.getMenuName());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void updateRoleMenu(long roleId, String menuIds) throws SQLException {
        // 删除原先绑定关系
        extSysMenuRoleInfo.removeByRoleId(roleId);
        String[] menuIdArr = menuIds.split(",");
        if (menuIdArr.length == 0) {
            return;
        }
        OfflineMenuRole r = null;
        List<OfflineMenuRole> roleMenus = new ArrayList<>();
        for (String id:menuIdArr){
            r = new OfflineMenuRole();
            r.setRoleId(roleId);
            r.setMenuId(Long.parseLong(id));
            r.setCreateTime(DateUtils.getCurFullTimestamp());
            r.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
            roleMenus.add(r);
        }
        extSysMenuRoleInfo.batchInsert(roleMenus);
    }

    @Override
    public List<VmRoleResponse> searchAllRole() {
        List<OfflineRole> roleList=extSysOfflineRole.searchAllRoles(1);
        List<VmRoleResponse> returnList=new ArrayList<>();
        if (roleList!=null&&roleList.size()>0){
            for (OfflineRole role:roleList){
                VmRoleResponse vrr=new VmRoleResponse();
                vrr.setRoleId(role.getRoleId());
                vrr.setRoleName(role.getRoleName());
                returnList.add(vrr);
            }
        }
        return returnList;
    }

    @Override
    public List<Map<String, Object>> searchRolePerm(Long rid) {
        List<PermRoleResult>  perms=extSysPermRole.searchRolePerm(rid);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (PermRoleResult rolePerm : perms) {
            map = new HashMap<String, Object>();
            map.put("id", rolePerm.getPermId());
            map.put("pId", 0);
            map.put("checked", rolePerm.getChecked());
            map.put("name", rolePerm.getPermName());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void updateRolePerm(long roleId, String permId) throws SQLException {
        extSysPermRole.removePermByRoleId(roleId);
        String[] permIds=permId.split(",");
        List<OfflineRolePerm> rp=new ArrayList<>();
        for (String s:permIds){
            OfflineRolePerm nrp=new OfflineRolePerm();
            nrp.setPermId(Long.valueOf(s));
            nrp.setRoleId(roleId);
            nrp.setIsDelete((long) 1);
            nrp.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
            rp.add(nrp);
        }
        extSysPermRole.batchInsert(rp);
    }

}
