package com.ctrip.train.kefu.system.job.worker.workers.order.train;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.order.train.CheckStopInfoService;
import common.log.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

@Component
public class CheckStopInfoWorker extends BaseWorker {

    @Autowired
    private CheckStopInfoService checkStopInfoService;

    /**
     * 退套餐job
     */
    @QSchedule(value = "com.ctrip.train.offline.CheckStopInfoWorker")
    @Override
    public void doWorker(Parameter parameter) {
        try {
            checkStopInfoService.checkStopInfo();
        }
        catch (Exception ex) {
            CLogger.info("CheckStopInfoWorker", ex);
        }
    }
}
