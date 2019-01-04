package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExAgentNoticeInfo;
import com.ctrip.train.kefu.system.offline.notice.service.AgentNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AgentNoticeServiceImpl implements AgentNoticeService{

    @Autowired
    private ExAgentNoticeInfo exAgentNotice;

    @Override
    public int searchAgentNotice(String orderId) {
        return exAgentNotice.searchAgentNoticeCount(orderId);
    }
}
