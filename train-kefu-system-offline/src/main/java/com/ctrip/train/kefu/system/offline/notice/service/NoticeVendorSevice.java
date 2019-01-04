package com.ctrip.train.kefu.system.offline.notice.service;

import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeVendor;

import java.sql.SQLException;


public interface NoticeVendorSevice {

    /**
     * 供应商通知数据
     * @return
     */
     VmNoticeVendor getNoitceVendor(Long noticeId)  ;

    /**
     * 保存供应商通知数据
     * @return
     */
    boolean sendVendor(VmNoticeVendor vm,String sendCode,String sendName) throws SQLException;

    /**
     * 是否已经存在供应商通知
     * @return
     */
    boolean exisNoitceVendor(Long noticeId) throws SQLException;
}

