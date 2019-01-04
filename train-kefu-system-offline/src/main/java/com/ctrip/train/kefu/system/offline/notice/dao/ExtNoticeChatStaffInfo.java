package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.notice.constants.CRedisKeyConstant;
import common.constants.DatabaseName;
import common.credis.CRedisHelper;
import dao.ctrip.ctrainchat.dao.ChatStaffInfoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtNoticeChatStaffInfo extends ChatStaffInfoDao{

    protected DalQueryDao baseDao;
    public ExtNoticeChatStaffInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }


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
}
