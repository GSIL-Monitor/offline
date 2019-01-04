package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysGroupStaffRelationDao;
import com.ctrip.train.kefu.system.offline.system.domain.GroupStaffName;
import com.ctrip.train.kefu.system.offline.system.domain.GroupStaffNameResult;
import com.ctrip.train.kefu.system.offline.system.service.SysGroupStaffService;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRequest;
import common.log.CLogger;
import dao.ctrip.ctrainchat.entity.GroupStaffRelation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysGroupStaffServiceImpl implements SysGroupStaffService {
    @Autowired
    private ExtSysGroupStaffRelationDao extSysGroupStaffRelationDao;

    @Override
    public PageInfo searchGroupStaffForPage(int pageIndex, int pageSize, long groupId) {
        List<GroupStaffNameResult> reports= extSysGroupStaffRelationDao.searchGroupStaff( pageIndex,pageSize, groupId);
        PageInfo pg=new PageInfo();
        if (reports!=null&&reports.size()>0){
            ModelMapper modelMapper=new ModelMapper();
            List<VmGroupStaffRResponse> vmList= modelMapper.map(reports,new TypeToken<List<VmGroupStaffRResponse>>() {}.getType());
            int count=extSysGroupStaffRelationDao.searchGroupStaffCount(groupId);
            pg.setData(vmList);
            pg.setRecordsFiltered(count);
            pg.setRecordsTotal(count);
        }
        return pg;
    }

    @Override
    public int addGroup(VmGroupStaffRequest request) {
        return 0;
    }

    @Override
    public PageInfo editGroupStaffPre(long groupId) {
        List<GroupStaffName> reports=extSysGroupStaffRelationDao.searchGroupStaffCheck(groupId);
        PageInfo pg=new PageInfo();
        if (reports!=null&&reports.size()>0){
            ModelMapper modelMapper=new ModelMapper();
            List<VmGroupStaffRResponse> vmList= modelMapper.map(reports,new TypeToken<List<VmGroupStaffRResponse>>() {}.getType());
            pg.setData(vmList);
            pg.setRecordsFiltered(vmList.size());
            pg.setRecordsTotal(vmList.size());
        }
        return pg;
    }

    @Override
    public int deleteGroupStaff(long tid) {
        return 0;
    }

    @Override
    public int editGroupStaff(VmGroupStaffRRequest request)  {
        try {
            String [] staffArr =  request.getStaffNums().split(",");
            extSysGroupStaffRelationDao.deleteStaffGroupByGroupId(request.getGroupId());
            List<GroupStaffRelation> list =new ArrayList<>();
            for (String staffNum:staffArr){
                GroupStaffRelation gsr=new GroupStaffRelation();
                gsr.setStaffNumber(staffNum);
                gsr.setGroupTid(request.getGroupId());
                gsr.setIsDelete(1);
                list.add(gsr);
            }
            extSysGroupStaffRelationDao.insert(list);
            return 1;
        } catch (SQLException e) {
            CLogger.error("editGroupStaff",e);
            return 0;
        }
    }

}
