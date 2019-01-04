package com.ctrip.train.kefu.system.job.worker.workers.order.train;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.order.train.CancelCouponsService;
import common.log.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

@Component
public class CancelCouponsWorker extends BaseWorker {


    @Autowired
    private CancelCouponsService cancelCouponsService;

    /**
     * 退套餐job
     */
    @QSchedule(value = "com.ctrip.train.offline.CancelCouponsWorker")
    @Override
    public void doWorker(Parameter parameter) {
        try {
            cancelCouponsService.CancelCoupons();
        }
        catch (Exception ex) {
            CLogger.info("CancelCouponsWorker", ex);
        }
    }
}
