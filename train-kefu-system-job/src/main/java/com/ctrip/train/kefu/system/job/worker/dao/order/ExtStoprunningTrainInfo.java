package com.ctrip.train.kefu.system.job.worker.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.StoprunningTrainInfoDao;
import dao.ctrip.ctrainchat.entity.StoprunningTrainInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExtStoprunningTrainInfo extends StoprunningTrainInfoDao {
    protected DalQueryDao baseDao;
    public ExtStoprunningTrainInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取停运的
     * @return
     * @throws Exception
     */
    public List<StoprunningTrainInfo> getstopdetaillist(){
        List<StoprunningTrainInfo> listStop = new ArrayList<StoprunningTrainInfo>();
        try {
            DalHints hints = new DalHints();
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT * FROM stoprunning_train_info ");
            builder.combine(" WHERE OrderCount > 0 and IsRecovery = 0 and StatusSms = 0  ");
            builder.combine(" and DepartDate >= date_format(now(),'%y-%m-%d')");
            hints.set(DalHintEnum.allowPartial);
            listStop = baseDao.query(builder.getSql(), builder.getParameters(), hints, StoprunningTrainInfo.class);
        } catch (Exception ex) {
            CLogger.error("getstopdetaillist", ex);
        }
        return listStop;
    }
}
