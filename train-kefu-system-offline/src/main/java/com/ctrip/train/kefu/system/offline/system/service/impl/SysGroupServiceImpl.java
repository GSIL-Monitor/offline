package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysStaffGroupInfo;
import com.ctrip.train.kefu.system.offline.system.domain.StaffGroupResult;
import com.ctrip.train.kefu.system.offline.system.service.SysGroupService;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupResponse;
import common.log.CLogger;
import dao.ctrip.ctrainchat.entity.StaffGroupInfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;

@Service
public class SysGroupServiceImpl implements SysGroupService{

    @Autowired
    private ExtSysStaffGroupInfo extSysStaffGroupInfo;

    @Override
    public PageInfo searchGroupForPage(int pageIndex, int pageSize, String productLine, String groupName) {

        List<StaffGroupResult> list= extSysStaffGroupInfo.searchStaffGroupList(pageIndex,pageSize,productLine,groupName);
        int count=extSysStaffGroupInfo.searchStaffGroupListCount( productLine, groupName);
        PageInfo response= new PageInfo();
        if(list!=null&&list.size()>0){
            ModelMapper modelMapper = new ModelMapper();
            List<VmGroupResponse> vmlist = modelMapper.map(list,new TypeToken<List<VmGroupResponse>>() {}.getType());
            response.setData(vmlist);
            response.setRecordsTotal(count);
            response.setRecordsFiltered(count);
            return response;
        }
        return null;
    }

    @Override
    public int addGroup(VmGroupRequest request) {

        StaffGroupInfo sgi=new StaffGroupInfo();
        sgi.setGroupDesc(request.getGroupDesc());
        sgi.setGroupName(request.getGroupName());
        sgi.setProductLine(request.getProductLine());
        sgi.setSupervisorStaffNUmber(request.getSupervisorStaffNUmber());
        sgi.setIsDelete(1);
        try {
            int r=extSysStaffGroupInfo.insert(sgi);
            return r;
        } catch (SQLException e) {
            CLogger.error("dao:addGroup", e);
            return 0;
        }
    }

    @Override
    public int editGroup(VmGroupRequest request) throws SQLException {
        StaffGroupInfo sgi=extSysStaffGroupInfo.queryByPk(request.getTid());
        sgi.setGroupDesc(request.getGroupDesc());
        sgi.setGroupName(request.getGroupName());
        sgi.setProductLine(request.getProductLine());
        sgi.setSupervisorStaffNUmber(request.getSupervisorStaffNUmber());
        return extSysStaffGroupInfo.update(sgi);
    }

    @Override
    public int deleteGroup(long tid) throws SQLException {
        StaffGroupInfo sgi=extSysStaffGroupInfo.queryByPk(tid);
        sgi.setIsDelete(0);
        return extSysStaffGroupInfo.update(sgi);
    }

    @Override
    public StaffGroupResult getGroup(long tid)  {
        return extSysStaffGroupInfo.getGroup(tid);
    }
}
