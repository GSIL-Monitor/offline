package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.worker.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

@Component
public class OnceSolveNoticeCountWorker extends BaseWorker {
    @Autowired
    private NoticeService noticeService;
    /**
     * 统计一次性解决率
     */
    @QSchedule(value = "com.ctrip.train.offline.OnceSolveNoticeCountWorker")
    @Override
    public void doWorker(Parameter parameter) {
        noticeService.queryOnceSolveNotice();
    }
}
