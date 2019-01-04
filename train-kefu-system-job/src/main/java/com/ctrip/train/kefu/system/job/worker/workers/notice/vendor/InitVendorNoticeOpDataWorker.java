package com.ctrip.train.kefu.system.job.worker.workers.notice.vendor;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.notice.VendorNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

@Component
public class InitVendorNoticeOpDataWorker extends BaseWorker {

    @Autowired
    private VendorNoticeService vendorNoticeService;

    /**
     * 初始化操供应商通知操作数据
     */
    @QSchedule(value = "com.ctrip.train.offline.InitVendorNoticeOpDataWorker")
    @Override
    public void doWorker(Parameter parameter) {
        vendorNoticeService.initNoticeOpData();
    }
}
