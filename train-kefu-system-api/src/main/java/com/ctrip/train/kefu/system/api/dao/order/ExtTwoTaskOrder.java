package com.ctrip.train.kefu.system.api.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.ScmTwoTaskTableDao;
import dao.ctrip.ctrainpps.entity.ScmTwoTaskTable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;


@Repository
public class ExtTwoTaskOrder extends ScmTwoTaskTableDao {
    protected DalQueryDao baseDao;
    public ExtTwoTaskOrder() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public ScmTwoTaskTable queryTwoTaskOrderState(String partnerOrderId)  {
        try {
            DalHints hints=new DalHints();
            DalUtils.Builder builder=DalUtils.createBuilder();
            builder.combine(" SELECT * FROM scm_two_task_table where partnerOrderId =?  ",Types.VARCHAR,partnerOrderId);
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObjectNullable(builder.getSql(),builder.getParameters(),hints,ScmTwoTaskTable.class);
        } catch (SQLException e) {
            CLogger.error("dao:queryTwoTaskOrderState  "+ partnerOrderId , e);
            return null;
        }
    }
}
