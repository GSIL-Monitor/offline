package com.ctrip.train.kefu.system.offline.system.service.impl;

import com.ctc.wstx.util.DataUtil;
import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysChatStaffInfo;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysOfflineRole;
import com.ctrip.train.kefu.system.offline.system.dao.ExtSysOfflineStaffRole;
import com.ctrip.train.kefu.system.offline.system.domain.StaffRoleResult;
import com.ctrip.train.kefu.system.offline.system.domain.StaffsResult;
import com.ctrip.train.kefu.system.offline.system.service.SysStaffService;
import com.ctrip.train.kefu.system.offline.system.vm.ResponseStaffForPage;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffResponse;
import common.util.DalUtils;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import dao.ctrip.ctrainchat.entity.OfflineStaffRole;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysStaffServiceImpl implements SysStaffService {
    @Autowired
    private ExtSysChatStaffInfo extSysChatStaffInfo;

    @Autowired
    private ExtSysOfflineRole  extSysOfflineRole;

    @Autowired
    private ExtSysOfflineStaffRole extSysOfflineStaffRole;


    @Override
    public PageInfo searchStaffForPage(VmStaffRequest request) {
        //用户信息
        List<StaffsResult> stafflist = extSysChatStaffInfo.searchStaff(request);
        //角色信息
        List<StaffRoleResult> rolelist =extSysOfflineRole.searchStaffRoles();
        List<VmStaffResponse> vmStaffList=new ArrayList<>();
        if(stafflist!=null&&stafflist.size()>0){
            for(StaffsResult staff:stafflist){
                List<StaffRoleResult> temps= rolelist.stream().filter(r->r.getStaffNum().equals(staff.getStaffNumber())).collect(Collectors.toList());
                VmStaffResponse vmStaff=new VmStaffResponse();
                vmStaff.setId(staff.getTid());
                vmStaff.setStaffNum(staff.getStaffNumber());
                vmStaff.setStaffName(staff.getStaffName());
                vmStaff.setEventType(String.valueOf(staff.getEventType()));
                vmStaff.setProductLine(staff.getProductLine());
                vmStaff.setPositionId((long) (staff.getPositionId()!=null?staff.getPositionId():0));
                vmStaff.setPositionName(staff.getPositionName());
                //角色信息
                vmStaff.setRoles(temps);
                StringBuilder sb=new StringBuilder();
                if (temps!=null&&temps.size()>0){
                    for(StaffRoleResult temp:temps){
                        sb.append(temp.getRoleName());
                        sb.append(" ");
                    }
                }else {
                    sb.append("无");
                }
                vmStaff.setRolesName(sb.toString());
                vmStaffList.add(vmStaff);
            }
        }
        int totalCount=extSysChatStaffInfo.searchStaffCount(request);
        PageInfo response= new PageInfo();
        response.setData(vmStaffList);
        response.setRecordsTotal(totalCount);
        response.setRecordsFiltered(totalCount);
        return response;
    }

    @Override
    public VmStaffResponse queryStaffByStaffNum(String staffNum) {
        //角色信息
        List<StaffRoleResult> rolelist =extSysOfflineRole.searchStaffRoles();
        ChatStaffInfo staff=extSysChatStaffInfo.queryStaffByStaffNum(staffNum,1);
        if (staff!=null){
            List<StaffRoleResult> temps= rolelist.stream().filter(r->r.getStaffNum().equals(staff.getStaffNumber())).collect(Collectors.toList());
            VmStaffResponse vmStaff=new VmStaffResponse();
            vmStaff.setId(staff.getTid());
            vmStaff.setStaffNum(staff.getStaffNumber());
            vmStaff.setStaffName(staff.getStaffName());
            //角色信息
            vmStaff.setRoles(temps);
            vmStaff.setEmail(staff.getEmail());
            vmStaff.setEventType(String.valueOf(staff.getEventType()));
            vmStaff.setProductLine(staff.getProductLine());
            return vmStaff;
        }
        return null;
    }

    @Override
    public VmStaffResponse queryStaffBytId(Long tid) {
        List<StaffRoleResult> rolelist =extSysOfflineRole.searchStaffRoles();
        ChatStaffInfo staff=extSysChatStaffInfo.queryStaffBytId(tid);
        List<StaffRoleResult> temps= rolelist.stream().filter(r->r.getStaffNum().equals(staff.getStaffNumber())).collect(Collectors.toList());
        VmStaffResponse vmStaff=new VmStaffResponse();
        vmStaff.setId(staff.getTid());
        vmStaff.setStaffNum(staff.getStaffNumber());
        vmStaff.setStaffName(staff.getStaffName());
        //角色信息
        vmStaff.setRoles(temps);
        vmStaff.setEmail(staff.getEmail());
        vmStaff.setEventType(String.valueOf(staff.getEventType()));
        vmStaff.setProductLine(staff.getProductLine());
        vmStaff.setPositionId(staff.getPositionId());
        return vmStaff;
    }

    @Override
    public int addStaff(VmStaffRequest request) throws SQLException {
        ChatStaffInfo staffInfo=new ChatStaffInfo();
        staffInfo.setStaffName(request.getStaffName());
        staffInfo.setStaffNumber(request.getStaffNum());
        staffInfo.setEmail(request.getEmail());
        staffInfo.setPositionId(request.getPositionId());
        staffInfo.setProductLine(request.getProductLine());
        staffInfo.setIsDelete(1);
        String[] roles=request.getRoleId().split(",");
        List<OfflineStaffRole> staffRoles=new ArrayList<>();
        for (String s:roles){
            OfflineStaffRole osr=new OfflineStaffRole();
            osr.setAvailable(1);
            osr.setRoleId(Long.valueOf(s));
            osr.setStaffNum(request.getStaffNum());
            osr.setCreateTime(DateUtils.getCurFullTimestamp());
            staffRoles.add(osr);
        }
        extSysOfflineStaffRole.insert(staffRoles);
        return extSysChatStaffInfo.insert(staffInfo); //增加客服;
    }

    @Override
    public int deleteStaff(String staffNum,int isDelete) {
        return extSysChatStaffInfo.deleteStaffByStaffNum(staffNum,isDelete);
    }

    @Override
    public int editStaff(VmStaffRequest request) throws SQLException {
        ChatStaffInfo staffInfo= extSysChatStaffInfo.queryStaffByStaffNum(request.getStaffNum(),1);
        staffInfo.setStaffName(request.getStaffName());
        staffInfo.setStaffNumber(request.getStaffNum());
        staffInfo.setEmail(request.getEmail());
        staffInfo.setProductLine(request.getProductLine());
        staffInfo.setPositionId(request.getPositionId());
        //删除关联
        extSysOfflineStaffRole.removeStaffRoleByStaffNum(request.getStaffNum());
        List<OfflineStaffRole> staffRoles=new ArrayList<>();

        //新角色关联
        String [] roles=request.getRoleId().split(",");
        for (String s:roles){
            OfflineStaffRole osr=new OfflineStaffRole();
            osr.setStaffNum(request.getStaffNum());
            osr.setCreateTime(DateUtils.getCurFullTimestamp());
            osr.setAvailable(1);
            osr.setRoleId(Long.valueOf(s));
            staffRoles.add(osr);
        }

        extSysOfflineStaffRole.insert(staffRoles);
        return extSysChatStaffInfo.update(staffInfo);
    }

    @Override
    public int deleteStaffByTid(long id, int isDelete) throws SQLException {
        ChatStaffInfo staffInfo= extSysChatStaffInfo.queryByPk(id);
        staffInfo.setIsDelete(0);
        return extSysChatStaffInfo.update(staffInfo);
    }

    @Override
    public VmStaffResponse queryStaffByStaffName(String staffName) {
        VmStaffResponse vmStaff =new VmStaffResponse();
        ChatStaffInfo staff= extSysChatStaffInfo.queryStaffByStaffName(staffName);
        if (staff!=null){
            vmStaff.setId(staff.getTid());
            vmStaff.setStaffNum(staff.getStaffNumber());
            vmStaff.setStaffName(staff.getStaffName());
            vmStaff.setEventType(String.valueOf(staff.getEventType()));
            vmStaff.setProductLine(staff.getProductLine());
            vmStaff.setPositionId((staff.getPositionId()!=null?staff.getPositionId():0));
        }
        return vmStaff;
    }

}
