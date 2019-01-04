package com.ctrip.train.kefu.system.job.worker.dao.notice;

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
import java.util.List;

@Repository
public class ExtOperateInfo extends OperateInfoDao {

    private DalQueryDao baseDao;

    public ExtOperateInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
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
        builder.combine(" where 1=1  ");
        builder.combineIn("and tid IN (?)", Types.VARCHAR,noticeIds);
        builder.combine(" order by  OperateTime desc  ");
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
