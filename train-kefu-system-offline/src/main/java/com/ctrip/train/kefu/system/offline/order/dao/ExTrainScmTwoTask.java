package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.ScmTwoTaskTableDao;
import dao.ctrip.ctrainpps.entity.ScmTwoTaskTable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExTrainScmTwoTask extends ScmTwoTaskTableDao {
    protected DalQueryDao baseDao;

    public ExTrainScmTwoTask() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public List<ScmTwoTaskTable> searchScmTwotaskList(String partnerOrderId)   {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select * from scm_two_task_table where recommendType=1 and processing_state in ('2','3','6','9','12') ");
            builder.combine(StringUtils.isNotBlank(partnerOrderId), " and partnerOrderId=?", Types.VARCHAR , partnerOrderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints,ScmTwoTaskTable.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchScmTwotaskList", e);
            return null;
        }
    }
    public int searchScmTwoTaskCount(String partnerOrderId)   {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select count(1) from scm_two_task_table where recommendType=1 and processing_state in ('2','3','6','9','12') ");
            builder.combine(StringUtils.isNotBlank(partnerOrderId), " and partnerOrderId=?", Types.VARCHAR , partnerOrderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints,Integer.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchScmTwoTaskCount", e);
            return 0;
        }
    }
}
