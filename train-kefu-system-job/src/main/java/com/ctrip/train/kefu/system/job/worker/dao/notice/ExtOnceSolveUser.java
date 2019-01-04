package com.ctrip.train.kefu.system.job.worker.dao.notice;


import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;

import common.constants.DatabaseName;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.NoticeOnceSolveUserDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;


@Repository
public class ExtOnceSolveUser extends NoticeOnceSolveUserDao {

    protected DalQueryDao baseDao;
    public ExtOnceSolveUser() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    public  int delectOnceSolveUser(Date startTime) throws SQLException {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("DELETE FROM notice_once_solve_user WHERE date_format(opTime,'%Y-%m-%d')=date_format(?,'%Y-%m-%d')", Types.DATE, startTime);
        return baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
    }

}
