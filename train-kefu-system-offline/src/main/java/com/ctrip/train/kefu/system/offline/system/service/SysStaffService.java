package com.ctrip.train.kefu.system.offline.system.service;


import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.ResponseStaffForPage;
import com.ctrip.train.kefu.system.offline.system.vm.VmRoleRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffResponse;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;

import java.sql.SQLException;

public interface SysStaffService {
    /**
     * 分页查询
     * @return
     */
    PageInfo searchStaffForPage(VmStaffRequest request);

    VmStaffResponse queryStaffByStaffNum(String staffNum);

    VmStaffResponse queryStaffBytId(Long tid);

    int addStaff(VmStaffRequest request) throws SQLException;

    int deleteStaff(String staffNum,int isDelete);

    int editStaff(VmStaffRequest request) throws SQLException;

    int deleteStaffByTid(long id,int isDelete) throws SQLException;

    VmStaffResponse queryStaffByStaffName (String staffName);
}
