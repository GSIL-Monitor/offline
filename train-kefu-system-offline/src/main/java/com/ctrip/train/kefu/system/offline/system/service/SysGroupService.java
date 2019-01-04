package com.ctrip.train.kefu.system.offline.system.service;


import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.domain.StaffGroupResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmGroupResponse;

import java.sql.SQLException;


public interface SysGroupService {
    /**
     * 分页查询分组
     * @return
     */
    PageInfo searchGroupForPage(int pageIndex, int pageSize, String productLine, String groupName);


    /**
     * 增加分组
     */
    int addGroup(VmGroupRequest request);

    /**
     * 增加分组
     */
    int editGroup(VmGroupRequest request) throws SQLException;

    /**
     * 删除分组
     */
    int deleteGroup(long tid) throws SQLException;

    /**
     * 查询分组
     */
    StaffGroupResult getGroup(long tid);

}
