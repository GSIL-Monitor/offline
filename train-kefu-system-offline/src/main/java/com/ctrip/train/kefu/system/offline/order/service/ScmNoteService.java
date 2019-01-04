package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.train.kefu.system.offline.order.vm.train.order.VmRemarks;

import java.util.List;

public interface ScmNoteService {

    /**
     * 查询备注列表
     * @param orderId
     * @return
     */
    List<VmRemarks> searchRemarksList(String orderId);


    /**
     * 添加详情页面备注
     * @param orderId
     * @param remarks
     * @param opUserName
     * @return
     */
    int addRemarks(String orderId,String remarks,String opUserName);
}
