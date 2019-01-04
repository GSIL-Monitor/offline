package com.ctrip.train.kefu.system.api.dao.ivr;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.IvrConfigDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class IvrConfigEx extends IvrConfigDao {
    protected DalQueryDao baseDao;

    public IvrConfigEx() throws SQLException {
        super();
        baseDao = new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 根据渠道获取对应的紧急录音
     */
    public String getSmsContentByPartnerName(String partnerName) {
        try {
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select SMS_Content");
            builder.combine("from ivr_config");
            builder.combine("where 1=1");
            builder.combine("and SMS_Type=2");
            builder.combine("and PartnerName=?", Types.VARCHAR, partnerName);
            builder.combine("limit 1");
            String strSmsContent = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, String.class);
            return strSmsContent;
        } catch (SQLException ex) {
            CLogger.error("IvrConfigEx", ex);
            return null;
        }
    }
}
