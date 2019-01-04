package com.ctrip.train.kefu.system.offline.common.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.notice.constants.CRedisKeyConstant;
import common.constants.DatabaseName;
import common.credis.CRedisHelper;
import dao.ctrip.ctrainchat.dao.ChatStaffInfoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtChatStaffInfo extends ChatStaffInfoDao{

    protected DalQueryDao baseDao;
    public ExtChatStaffInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }


}
