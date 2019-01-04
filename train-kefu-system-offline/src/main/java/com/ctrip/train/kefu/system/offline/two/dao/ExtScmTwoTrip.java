package com.ctrip.train.kefu.system.offline.two.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.ScmTwoTripTableDao;
import dao.ctrip.ctrainpps.entity.ScmTwoTripTable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtScmTwoTrip extends ScmTwoTripTableDao {
    protected DalQueryDao baseDao;
    public ExtScmTwoTrip() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    /**
     * 根据订单号获取scmtwotrip
     * @param orderId
     * @return
     * @throws SQLException
     */
    public List<ScmTwoTripTable> getScmTwoTripList(String orderId)   {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  trip.* From scm_two_trip_table trip");
            builder.combine("inner join scm_two_task_table task on trip.task_id=task.id");
            builder.combine(orderId != null, " where partnerOrderId=?",Types.VARCHAR , orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints,ScmTwoTripTable.class);
        } catch (SQLException e) {
            CLogger.error("dao:updateOrderStatusByOrderId", e);
            return null;
        }
    }
}
