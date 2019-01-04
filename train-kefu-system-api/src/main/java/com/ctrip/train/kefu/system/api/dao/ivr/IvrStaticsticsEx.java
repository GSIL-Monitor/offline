package com.ctrip.train.kefu.system.api.dao.ivr;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.IvrStaticsticsDao;
import dao.ctrip.ctrainchat.entity.IvrStaticstics;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class IvrStaticsticsEx extends IvrStaticsticsDao {
    protected DalQueryDao baseDao;

    public IvrStaticsticsEx() throws SQLException {
        super();
        baseDao = new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 根据CallID判断是否是第一次交互
     */
    public boolean isFirstRequest(String callId) {
        boolean isFirstRequest = false;
        try {
            if (StringUtils.isNotEmpty(callId)) {
                DalHints hints = new DalHints();
                hints.set(DalHintEnum.allowPartial);
                DalUtils.Builder builder = DalUtils.createBuilder();
                builder.combine("select count(0)");
                builder.combine("from ivr_staticstics");
                builder.combine("where 1=1");
                builder.combine("and CallID=?", Types.VARCHAR, callId);
                long count = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, long.class);
                if (count == 0) {
                    isFirstRequest = true;
                }
            }
        } catch (SQLException ex) {
            CLogger.error("IvrStaticsticsEx", ex);
        }
        return isFirstRequest;
    }

    /**
     * 根据CallID找到获取自助问题时的IvrStaticstics实体
     */
    public IvrStaticstics getIvrStaticsticsByCallId(String callId) {
        IvrStaticstics ivrStaticstics = new IvrStaticstics();
        try {
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select *");
            builder.combine("from ivr_staticstics");
            builder.combine("where 1=1");
            builder.combine("and CallID=?", Types.VARCHAR, callId);
            builder.combine("and OpTagString='GetQuestion'");
            builder.combine("order by Tid desc");
            builder.combine("limit 1");
            ivrStaticstics = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, IvrStaticstics.class);
        } catch (SQLException ex) {
            CLogger.error("IvrStaticsticsEx", ex);
        }
        return ivrStaticstics;
    }

    /**
     * 获取转人工问题
     */
    public String getLastQuestionDescByCallId(String callId) {
        String relationGuid = null;
        try {
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select Remark");
            builder.combine("from ivr_staticstics");
            builder.combine("where 1=1");
            builder.combine("and CallID=?", Types.VARCHAR, callId);
            builder.combine("order by Tid desc");
            builder.combine("limit 1");
            relationGuid = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, String.class);
        } catch (SQLException ex) {
            CLogger.error("IvrStaticsticsEx", ex);
        }
        return relationGuid;
    }

    /**
     * 判断查询无订单是否超过限制
     */
    public boolean isMoreNoOrder(String callId) {
        boolean isMoreNoOrder = false;
        try {
            if (StringUtils.isNotEmpty(callId)) {
                DalHints hints = new DalHints();
                hints.set(DalHintEnum.allowPartial);
                DalUtils.Builder builder = DalUtils.createBuilder();
                builder.combine("select count(0)");
                builder.combine("from ivr_staticstics");
                builder.combine("where 1=1");
                builder.combine("and CallID=?", Types.VARCHAR, callId);
                builder.combine("and OpTagString='GetQuestion'");
                builder.combine("and OperTag=106");
                builder.combine("and DTMF=100");
                long count = baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, long.class);
                if (count > 1) {
                    isMoreNoOrder = true;
                }
            }
        } catch (SQLException ex) {
            CLogger.error("IvrStaticsticsEx", ex);
        }
        return isMoreNoOrder;
    }
}
