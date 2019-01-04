package com.ctrip.train.kefu.system.job.worker.service.order.train;

import com.ctrip.train.kefu.system.job.worker.dao.order.ActivityDetailEx;
import common.log.CLogger;
import dao.ctrip.ctrainchat.entity.ActivityDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityFxService {

    @Autowired
    private ActivityDetailEx activityDetailEx;

    /**
     * 优惠券返现业务处理
     */
    public void ActivityFx() {
        try {
            String strLog = "获取问题调用开始";
            List<ActivityDetail> activityDetailList = activityDetailEx.getNeedDealData();
            if (activityDetailList != null && activityDetailList.size() > 0) {
            } else {
            }
            //strLog += ".........参数_" + mapper.writeValueAsString(request);
        } catch (Exception ex) {
            CLogger.error("优惠券返现", ex);
        }
    }

}