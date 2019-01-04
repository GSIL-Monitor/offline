package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.AgentNoticeInfoDao;
import dao.ctrip.ctrainpps.entity.AgentNoticeInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExAgentNoticeInfo extends AgentNoticeInfoDao {

    protected DalQueryDao baseDao;
    public ExAgentNoticeInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    public List<AgentNoticeInfo> searchAgentNotice(String orderId) {
         try {
             DalUtils.Builder builder = DalUtils.createBuilder();
             builder.combine(" select * from agent_notice_info where 1=1  ");
             builder.combine(StringUtils.isNotBlank(orderId)," and orderId =? ", Types.VARCHAR,orderId);
             DalHints hints = new DalHints();
             hints.set(DalHintEnum.allowPartial);
             return baseDao.query(builder.getSql(), builder.getParameters(), hints, AgentNoticeInfo.class);
         } catch (Exception ex) {
             CLogger.error("dao:searchAgentNotice", ex);
             return null;
         }
    }

    public int searchAgentNoticeCount(String orderId) {
         try {
             DalUtils.Builder builder = DalUtils.createBuilder();
             builder.combine(" select count(1) from agent_notice_info where 1=1  ");
             builder.combine(StringUtils.isNotBlank(orderId)," and orderId =? ", Types.VARCHAR,orderId);
             DalHints hints = new DalHints();
             hints.set(DalHintEnum.allowPartial);
             return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
         } catch (Exception ex) {
             CLogger.error("dao:searchAgentNoticeCount", ex);
             return 0;
         }
    }

}
