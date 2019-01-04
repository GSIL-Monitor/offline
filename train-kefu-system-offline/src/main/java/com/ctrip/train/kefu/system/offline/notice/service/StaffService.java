package com.ctrip.train.kefu.system.offline.notice.service;


public interface StaffService {

    /**
     * @param eid
     * @return 1 online  2 offline 3 rest
     * default returned value is 2
     */
    int getStaffState(String eid);
}

