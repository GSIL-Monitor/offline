package com.ctrip.train.kefu.system.offline.system.service;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;

import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupStaffRequest;

import java.sql.SQLException;

public interface SysGroupStaffService {
    /**
     * 分页查询分组
     * @return
     */
    PageInfo searchGroupStaffForPage(int pageIndex, int pageSize, long groupId);


    /**
     * 增加分组
     */
    int addGroup(VmGroupStaffRequest request);

    /**
     * 编辑组员
     */
    PageInfo editGroupStaffPre(long groupId);

    /**
     * 删除分组
     */
    int deleteGroupStaff(long tid) throws SQLException;

    int editGroupStaff(VmGroupStaffRRequest request) throws SQLException;
}
