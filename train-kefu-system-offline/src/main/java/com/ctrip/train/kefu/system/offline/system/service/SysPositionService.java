package com.ctrip.train.kefu.system.offline.system.service;

import com.ctrip.train.kefu.system.offline.common.utils.PageInfo;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostResponse;

import java.sql.SQLException;
import java.util.List;

public interface SysPositionService {

    PageInfo searchPostForPage(VmPostRequest request);

    VmPostResponse queryPostBytId(Long tid) throws SQLException;

    int addPost(VmPostRequest request) throws SQLException;

    int editPost(VmPostRequest request) throws SQLException;

    int updatePost(long tid) throws SQLException;

    List<VmPostResponse> selectAllPost () throws SQLException;

}
