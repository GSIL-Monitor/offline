package com.ctrip.train.kefu.system.job.worker.dao.order;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.ActivityDetailDao;
import dao.ctrip.ctrainchat.entity.ActivityDetail;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityDetailEx extends ActivityDetailDao {
    protected DalQueryDao baseDao;

    public ActivityDetailEx() throws SQLException {
        super();
        baseDao = new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取待处理的数据
     */
    public List<ActivityDetail> getNeedDealData() {
        List<ActivityDetail> activityDetailList = new ArrayList<ActivityDetail>();
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select *");
            builder.combine("from activity_detail");
            builder.combine("where 1=1");
            builder.combine("and ActivityType=2");
            builder.combine("and PartnerName='Ctrip.Train'");
            builder.combine("and Status=0");
            builder.combine("order by 1 desc");
            DalHints dalHints = new DalHints();
            dalHints.set(DalHintEnum.allowPartial);
            activityDetailList = baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ActivityDetail.class);
        } catch (Exception ex) {
            CLogger.error("ActivityDetailEx", ex);
        }
        return activityDetailList;
    }

}
