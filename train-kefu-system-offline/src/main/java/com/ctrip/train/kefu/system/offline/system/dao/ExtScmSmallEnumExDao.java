package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import dao.ctrip.ctrainpps.dao.ScmSmallEnumDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtScmSmallEnumExDao extends ScmSmallEnumDao {
    protected DalQueryDao baseDao;
    public ExtScmSmallEnumExDao() throws SQLException {
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
}
