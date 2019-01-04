package com.ctrip.train.kefu.system.offline.notice.service;



public interface AgentNoticeService {
    /**
     * 查看代售点通知
     */
    int searchAgentNotice(String orderId);
}
