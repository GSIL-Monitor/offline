package com.ctrip.train.kefu.system.api.dao.notice;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
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

    public List<OperateInfo> searchOperateInfo(Date startTime , Date endTime , Integer operateType){

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  From operate_info ");
            builder.combine(" where 1=1 ");
            builder.combine(startTime != null, " and operateTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and operateTime<?", Types.DATE, endTime);
            builder.combine(operateType != null, " and OperateType=?", Types.SMALLINT, operateType);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OperateInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return null;
        }
    }

    /**
     * 根据noticeIds获取操作日志
     */
    public List<OperateInfo> getOperateInfos(List<Long> noticeIds)  {
        if(noticeIds==null||noticeIds.size()==0){
            return null;
        }
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("SELECT  *  From operate_info ");
        builder.combine(" where 1=1 and  OperateSource IN (2,3) ");
        builder.combineIn("and tid IN (?)", Types.VARCHAR,noticeIds);
        DalHints dalHints=new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.query(builder.getSql(),builder.getParameters(),dalHints,OperateInfo.class);
        }
        catch (Exception ex){
            CLogger.error("dao:getOperateInfos", ex);
        }
        return  null;

    }


}
