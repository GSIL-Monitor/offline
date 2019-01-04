package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import dao.ctrip.ctrainpps.dao.OfflinePartnerinfoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtOfflinePartnerinfoDao extends OfflinePartnerinfoDao {

    protected DalQueryDao baseDao;
    public ExtOfflinePartnerinfoDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
}
