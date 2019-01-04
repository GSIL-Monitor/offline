package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import dao.ctrip.ctrainpps.dao.NoticeVendorDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtNoitceVendor extends NoticeVendorDao {
    protected DalQueryDao baseDao;
    public ExtNoitceVendor() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
}
