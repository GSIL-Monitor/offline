package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.platform.dal.exceptions.DalException;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.IvrCdrsubDao;
import dao.ctrip.ctrainchat.entity.ChatConfig;
import dao.ctrip.ctrainchat.entity.IvrCdrsub;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class ExtIvrCdrsubDao extends IvrCdrsubDao {

    protected DalQueryDao baseDao;
    public ExtIvrCdrsubDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * @param
     * @return 1 online  2 offline 3 rest
     * default returned value is 2
     */
    public List<IvrCdrsub> GetIvrListByPhone(String phone){

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select MainCID,SubCID,ANI_NO ,DNIS_NO ,OPERATOR_NO ,CALL_TYPE,DataChange_LastTime,TIME_IN,TIME_OUT from ivr_cdrsub WHERE 1=1 ");
            builder.combine(StringUtils.isNotBlank(phone),"and (ANI_NO=? ", Types.VARCHAR,phone);
            builder.combine(StringUtils.isNotBlank(phone),"or ANI_NO=?", Types.VARCHAR,"0" + phone);
            builder.combine(") and TIME_IN>=? ", Types.DATE, DateUtils.addMonths(new Date(),-1));
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, IvrCdrsub.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchExcePriceCount", ex);
            return null;
        }
    }
}
