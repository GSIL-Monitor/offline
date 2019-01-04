package com.ctrip.train.kefu.system.offline.system.service.impl;



import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysOfflinePosition;
import com.ctrip.train.kefu.system.offline.system.service.SysPositionService;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostResponse;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import dao.ctrip.ctrainchat.entity.OfflinePosition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysPositionServiceImpl implements SysPositionService {

    @Autowired
    private ExtSysOfflinePosition extSysOfflinePosition;

    @Override
    public PageInfo searchPostForPage(VmPostRequest request) {
        List<OfflinePosition> reports= extSysOfflinePosition.searchPost(request);
        List<VmPostResponse> vmList=new ArrayList<>();
        if (reports!=null){
            ModelMapper modelMapper=new ModelMapper();
            vmList= modelMapper.map(reports,new TypeToken<List<VmPostResponse>>() {}.getType());
        }
        int count=extSysOfflinePosition.searchPostCount(request);
        PageInfo pg=new PageInfo();
        pg.setData(vmList);
        pg.setRecordsFiltered(count);
        pg.setRecordsTotal(count);
        return pg;
    }

    @Override
    public VmPostResponse queryPostBytId(Long tid) throws SQLException {
        OfflinePosition op=extSysOfflinePosition.queryByPk(tid);
        ModelMapper modelMapper=new ModelMapper();
        VmPostResponse vpr= modelMapper.map(op,new TypeToken<VmPostResponse>() {}.getType());
        return vpr;
    }

    @Override
    public int addPost(VmPostRequest request) throws SQLException {
        OfflinePosition op=new OfflinePosition();
        op.setPositionName(request.getPositionName());
        op.setPositionDesc(request.getPositionDesc());
        op.setIsDelete(1);
        return extSysOfflinePosition.insert(op);
    }

    @Override
    public int editPost(VmPostRequest request) throws SQLException {

        OfflinePosition op=extSysOfflinePosition.queryByPk(request.getId());
        op.setPositionDesc(request.getPositionDesc());
        op.setPositionName(request.getPositionName());
        return extSysOfflinePosition.update(op);
    }

    @Override
    public int updatePost(long tid) throws SQLException {

        OfflinePosition op=extSysOfflinePosition.queryByPk(tid);
        op.setIsDelete(0);
        return extSysOfflinePosition.update(op);
    }

    @Override
    public List<VmPostResponse> selectAllPost() throws SQLException {
        List<OfflinePosition> list=extSysOfflinePosition.searchAllPost();
        ModelMapper modelMapper=new ModelMapper();
        List<VmPostResponse> vmList= modelMapper.map(list,new TypeToken<List<VmPostResponse>>() {}.getType());
        return vmList;
    }

}
