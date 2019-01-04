package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeChatStaffInfo;
import com.ctrip.train.kefu.system.offline.notice.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private ExtNoticeChatStaffInfo chatStaffInfo;

    /**
     * @param eid
     * @return 1 online  2 offline 3 rest
     */
    public int getStaffState(String eid) {
       return   chatStaffInfo.getStaffState(eid);
    }
}
