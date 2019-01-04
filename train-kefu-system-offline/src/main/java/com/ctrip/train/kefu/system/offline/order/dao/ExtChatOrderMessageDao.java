package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import dao.ctrip.ctrainchat.dao.ChatOrderMessageDao;
import dao.ctrip.ctrainchat.dao.OrderClickDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtChatOrderMessageDao extends ChatOrderMessageDao {

    protected DalQueryDao baseDao;
    public ExtChatOrderMessageDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
}
