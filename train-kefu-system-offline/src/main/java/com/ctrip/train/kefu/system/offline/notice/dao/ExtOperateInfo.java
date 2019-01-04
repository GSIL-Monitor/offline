package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.notice.domain.OperateNoticeTime;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.OperateInfoDao;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class ExtOperateInfo extends OperateInfoDao {

    protected DalQueryDao baseDao;
    public ExtOperateInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    public List<OperateInfo> searchOperateInfo(Date startTime , Date endTime , Integer OperateType){

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  From operate_info ");
            builder.combine(" where 1=1 ");
            builder.combine(startTime != null, " and operateTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and operateTime<?", Types.DATE, endTime);
            builder.combine(OperateType != null, " and OperateType=?", Types.SMALLINT, OperateType);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OperateInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return null;
        }
    }

    public List<OperateNoticeTime> searchOperateNotice(Date startTime , Date endTime){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select n.id as id, n.opuser as opUser,  date_format(n.sendTime, '%Y-%m-%d %H:%i:%s') as sendTime, date_format(o.operateTime, '%Y-%m-%d %H:%i:%s') as operateTime from  operate_info o left join notice_complain_info n  on n.Id=o.Tid");
            builder.combine(" where 1=1 and o.OperateType=103");
            builder.combine(startTime != null, " and operateTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and operateTime<?", Types.DATE, endTime);
            builder.combine(" group by o.Tid ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OperateNoticeTime.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return null;
        }
    }
}
