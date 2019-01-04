package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.ExcePriceDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class ExtExcePriceDao extends ExcePriceDao {

    protected DalQueryDao baseDao;
    public ExtExcePriceDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public int searchExcePriceCount(String orderId) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT count(1) from exce_price WHERE IsDelete = 1");
            builder.combine(StringUtils.isNotBlank(orderId),"  and OrderNumber=?", Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchExcePriceCount", ex);
            return 0;
        }
    }
}
