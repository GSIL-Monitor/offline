package com.ctrip.train.kefu.system.job.worker.service.staff;

import com.ctrip.train.kefu.system.job.constants.CRedisKeyConstant;
import com.ctrip.train.kefu.system.job.worker.dao.staff.ExtStaff;
import common.credis.CRedisHelper;
import common.log.CLogger;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StaffService {

    @Autowired
    private ExtStaff staff;

    /**
     * @param eid
     * @return 1 online  2 offline 3 rest
     * default returned value is 2
     */
    public int getStaffState(String eid) {
        Integer value = CRedisHelper.get(String.format(CRedisKeyConstant.STAFF_ONLINE_STATE_KEY, eid), Integer.class);
        if (value != null) {
            return value;
        }
        return 2;
    }

    /**
     * 查询在上班的员工
     * @return
     */
    public List<ChatStaffInfo> searchWorkingStaff(){
        try {
            return staff.searchWorkingStaff();
        } catch (SQLException e) {
            CLogger.error("searchWorkingStaff",e);
            return  null;
        }
    }

}
