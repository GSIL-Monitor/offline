package com.ctrip.train.kefu.system.offline.two.dao;

import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.ScmTwoTaskTableDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

@Repository
public class ExtScmTwoTask extends ScmTwoTaskTableDao {
    protected DalQueryDao baseDao;

    public ExtScmTwoTask() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }
    public int updateOrderStatusByOrderId (Map<String, Object> map) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            String processingRemark=String.valueOf(map.get("processingRemark"));
            String processingState=String.valueOf(map.get("processingState"));
            int opReason=Integer.valueOf(String.valueOf(map.get("opReason")));
            String operator=map.get("operator")+"("+map.get("operatorNum")+")";
            String partnerOrderId=String.valueOf(map.get("partnerOrderId"));
            builder.combine(" UPDATE scm_two_task_table ")
                    .combine(" Set processing_remark= ?",processingRemark)// 备注
                    .combine(" ,processing_state= ?",processingState)           //订单状态
                    .combine(" ,operator= ?",operator)           //处理人
                    .combine(" ,opSource=1 ")
                    .combine(" ,OpReason= ?",opReason)
                    .combine(" WHERE partnerOrderId= ?",partnerOrderId);
            return baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:updateOrderStatusByOrderId", e);
            return 0;
        }
    }
}
