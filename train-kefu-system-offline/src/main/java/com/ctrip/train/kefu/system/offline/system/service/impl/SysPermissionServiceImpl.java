package com.ctrip.train.kefu.system.offline.system.service.impl;


import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtOfflinePermissionInfo;
import com.ctrip.train.kefu.system.offline.system.service.SysPermissionService;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionResponse;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private ExtOfflinePermissionInfo extOfflinePermissionInfo;
    @Override
    public PageInfo searchPermissionForPage(VmPermissionRequest request) {
        List<OfflinePermission> reports= extOfflinePermissionInfo.searchPerm(request);
        if (reports!=null){
            ModelMapper modelMapper=new ModelMapper();
            List<VmPermissionResponse> vmList= modelMapper.map(reports,new TypeToken<List<VmPermissionResponse>>() {}.getType());
            int count=extOfflinePermissionInfo.searchPermCount(request);
            PageInfo pg=new PageInfo();
            pg.setData(vmList);
            pg.setRecordsFiltered(count);
            pg.setRecordsTotal(count);
            return pg;
        }
        return null;
    }

    @Override
    public VmPermissionResponse queryPermissionByPermissionNum(String Permission) {
        return null;
    }

    @Override
    public VmPermissionResponse queryPermissionBytId(Long tid) throws SQLException {
        OfflinePermission op=extOfflinePermissionInfo.queryByPk(tid);
        ModelMapper modelMapper=new ModelMapper();
        VmPermissionResponse vpr= modelMapper.map(op,new TypeToken<VmPermissionResponse>() {}.getType());
        return vpr;
    }

    @Override
    public int addPermission(VmPermissionRequest request) throws SQLException {
        ModelMapper modelMapper=new ModelMapper();
        OfflinePermission vpr= modelMapper.map(request,new TypeToken<OfflinePermission>() {}.getType());
        vpr.setIsDelete(1);
        return extOfflinePermissionInfo.insert(vpr);
    }

    @Override
    public int deletePermission(String PermissionNum, int isDelete) {

        return 0;
    }

    @Override
    public int editPermission(VmPermissionRequest request) throws SQLException {

        OfflinePermission op=extOfflinePermissionInfo.queryByPk(request.getId());

        op.setPermCode(request.getPermCode());
        op.setPermDesc(request.getPermDesc());
        op.setPermName(request.getPermName());
        op.setPermType(request.getPermType());

        return extOfflinePermissionInfo.update(op);
    }

    @Override
    public int updatePerm(long tid) throws SQLException {
        OfflinePermission op=extOfflinePermissionInfo.queryByPk(tid);
        op.setIsDelete(0);
        return extOfflinePermissionInfo.update(op);
    }
}
