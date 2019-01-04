package com.ctrip.train.kefu.system.offline.order.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.ScmTwoTaskTableDao;
import dao.ctrip.ctrainpps.entity.ScmTwoTaskTable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtScmTwoTaskTableDao extends ScmTwoTaskTableDao {

    protected DalQueryDao baseDao;
    public ExtScmTwoTaskTableDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public Boolean isTwoRecommendForPersonal(String orderId)
    {
        Boolean hr = false;
        try
        {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT * FROM scm_two_task_table WHERE recommendType = 1 and and processing_state in ('2','3','6','9','12')");
            builder.combine(StringUtils.isNotBlank(orderId)," and partnerOrderId=?", Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            List<ScmTwoTaskTable> dt = baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmTwoTaskTable.class);
            if(dt!=null && dt.size()>0){
                hr= true;
            }
        }
        catch (Exception ex)
        {
            CLogger.error("ExtScmTwoTaskTableDao.isTwoRecommendForPersonal",ex);
        }
        return hr;
    }

    /**
     * 和 下面 isTwoRecommending 融合到一起了
     * @param orderId
     * @return
     */
    public Boolean isTwoRecommend(String orderId)
    {
        Boolean hr = false;
        try
        {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT * FROM scm_two_task_table WHERE (processing_state = '2' or processing_state = '6' or processing_state = '12')");
            builder.combine(StringUtils.isNotBlank(orderId)," and partnerOrderId=?", Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            List<ScmTwoTaskTable> dt = baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmTwoTaskTable.class);
            if(dt!=null && dt.size()>0){
                hr= true;
            }
        }
        catch (Exception ex)
        {
            CLogger.error("ExtScmTwoTaskTableDao.isTwoRecommend",ex);
        }
        return hr;
    }

    public Boolean isTwoRecommending(String orderId)
    {
        Boolean hr = false;
        try
        {
            ScmTwoTaskTable scmTwoTaskTable = new ScmTwoTaskTable();
            scmTwoTaskTable.setProcessingState("9");
            scmTwoTaskTable.setPartnerOrderId(orderId);
            List<ScmTwoTaskTable> scmTwoList = queryLike(scmTwoTaskTable);
            if(scmTwoList!=null && scmTwoList.size()>0){
                hr= true;
            }
        }
        catch (Exception ex)
        {
            CLogger.error("ExtScmTwoTaskTableDao.isTwoRecommending",ex);
        }
        return hr;
    }

    public Boolean isNeedCancelTwoRecommend(String orderId)
    {
        Boolean hr = false;
        try
        {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT * FROM scm_two_task_table WHERE ( recommendtype='1' and ( processing_state = '10' or processing_state = '12' or processing_state = '2'))");
            builder.combine(StringUtils.isNotBlank(orderId)," and partnerOrderId=?", Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            List<ScmTwoTaskTable> dt = baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmTwoTaskTable.class);
            if(dt!=null && dt.size()>0){
                hr= true;
            }
        }
        catch (Exception ex)
        {
            CLogger.error("ExtScmTwoTaskTableDao.isNeedCancelTwoRecommend",ex);
        }
        return hr;
    }
}
