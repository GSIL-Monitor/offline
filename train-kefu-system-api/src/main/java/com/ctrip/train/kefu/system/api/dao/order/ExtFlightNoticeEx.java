package com.ctrip.train.kefu.system.api.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.AirNoticeExcetionDao;
import dao.ctrip.ctrainchat.entity.AirNoticeExcetion;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtFlightNoticeEx extends AirNoticeExcetionDao {
    protected DalQueryDao baseDao;
    public ExtFlightNoticeEx() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<AirNoticeExcetion> getExtAirNoticeEx(AirNoticeExcetion airNotice) {

        DalHints hints=new DalHints();
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine(" SELECT * FROM air_notice_excetion where 1=1 ");
        builder.combine(StringUtils.isNotBlank(airNotice.getOrderId())," and orderId =? ", Types.VARCHAR,airNotice.getOrderId());
        builder.combine(StringUtils.isNotBlank(airNotice.getSupplier())," and supplier =? ", Types.VARCHAR,airNotice.getSupplier());
        builder.combine(airNotice.getProductLine()!=null," and ProductLine =? ", Types.INTEGER,airNotice.getProductLine());
        builder.combine(StringUtils.isNotBlank(airNotice.getEnterUser())," and EnterUser =? ", Types.VARCHAR,airNotice.getEnterUser());
        builder.combine(airNotice.getExOrderType()!=null," and exOrderType=? ", Types.INTEGER,airNotice.getExOrderType());
        hints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.query(builder.getSql(),builder.getParameters(),hints,AirNoticeExcetion.class);
        } catch (SQLException e) {
            CLogger.error("dao:getExtAirNoticeEx", e);
            return null;
        }
    }
}
