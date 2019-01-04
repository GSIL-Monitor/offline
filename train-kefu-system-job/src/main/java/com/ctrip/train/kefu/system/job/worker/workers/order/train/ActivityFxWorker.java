package com.ctrip.train.kefu.system.job.worker.workers.order.train;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.order.train.ActivityFxService;
import common.log.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

@Component
public class ActivityFxWorker extends BaseWorker {

    @Autowired
    private ActivityFxService activityFxService;

    /**
     * 优惠券返现job
     */
    @QSchedule(value = "com.ctrip.train.offline.ActivityFxWorker")
    @Override
    public void doWorker(Parameter parameter) {
        try {
            CLogger.info("优惠券返现", "执行开始");
            activityFxService.ActivityFx();
            CLogger.info("优惠券返现", "执行结束");
        } catch (Exception ex) {
            CLogger.error("优惠券返现", ex);
        }
    }

}
