package com.ctrip.train.kefu.system.offline.system.service;

import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.SmallEnumResponse;
import com.ctrip.train.kefu.system.offline.system.vm.VmPriorityRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPriorityResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SysPriorityService {

    PageInfo searchPriorityForPage(VmPriorityRequest request) throws SQLException;

    List<Map<String, Object>> queryPriorityBytId(Long tid) throws SQLException;

    JsonResult addPriority(VmPriorityRequest request) throws SQLException;

    int editPriority(VmPriorityRequest request) throws SQLException;

    JsonResult updatePriorityStatus(long tid) throws SQLException;

    SmallEnumResponse searchSmallEnumsAll(int productLine) throws SQLException;
}
