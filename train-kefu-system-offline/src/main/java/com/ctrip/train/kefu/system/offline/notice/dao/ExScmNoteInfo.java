package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.ScmNoteDao;
import dao.ctrip.ctrainpps.entity.AgentNoticeInfo;
import dao.ctrip.ctrainpps.entity.ScmNote;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExScmNoteInfo extends ScmNoteDao{

    protected DalQueryDao baseDao;
    public ExScmNoteInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public List<ScmNote> searchScmNoteList(String orderId) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select OrderID,Note,Operator,OperateTime from scm_note where 1=1  ");
            builder.combine(StringUtils.isNotBlank(orderId)," and orderId =? ", Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            CLogger.info("OrderOperate",orderId);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmNote.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchAgentNotice", ex);
            return null;
        }
    }

}
