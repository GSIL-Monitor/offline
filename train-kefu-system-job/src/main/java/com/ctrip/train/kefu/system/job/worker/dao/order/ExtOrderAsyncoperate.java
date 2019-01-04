package com.ctrip.train.kefu.system.job.worker.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.OrderAsyncoperateDao;
import dao.ctrip.ctrainpps.entity.OrderAsyncoperate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtOrderAsyncoperate  extends OrderAsyncoperateDao{
    protected DalQueryDao baseDao;
    public ExtOrderAsyncoperate() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }


    /**
     * 获取异步操作的数据
     * @throws SQLException
     */
    public List<OrderAsyncoperate> getOrderAsyncoperates(){
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("SELECT * FROM order_asyncoperate");
        builder.combine(" WHERE  type=1 and state =3");
        builder.combine(" order by sendtime asc");
        builder.combine(" limit 5");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        try {
            return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, OrderAsyncoperate.class);
        }
        catch (Exception ex){
            CLogger.error("getOrderAsyncoperates",ex);
        }
        return  null;
    }


    /**
     * 获取异步操作结果
     */
    public  int updateOrderAsyncoperate(Long id,String respose,Integer state,String contactName)  {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("update order_asyncoperate");
        builder.combine(" set  respose =?",respose);
        builder.combine(",state=?",state);
        builder.combine(",contactname=?",contactName);
        builder.combine(",requestcout=1");
        builder.combine("  WHERE  id=?",id);
        try {
            return baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        }
        catch (Exception ex){
            CLogger.error("updateOrderAsyncoperate",ex);
        }
        return  0;
    }
}
