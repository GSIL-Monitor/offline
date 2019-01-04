package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.enums.notice.NoticeStateEnum;
import com.ctrip.train.kefu.system.job.worker.service.notice.NoticeService;
import common.log.CLogger;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChangeDutyNoticeWorker extends BaseWorker {

    @Autowired
    private NoticeService noticeService;

    /**
     * 交班通知到预约时间改成待处理
     */
    @QSchedule(value = "com.ctrip.train.offline.DutyChangedNoticePullableWorker")
    @Override
    public void doWorker(Parameter parameter) {
        int advanceMinute= Integer.parseInt(parameter.getString("advanceMinute"));

        //notice 数据
        List<NoticeComplainInfo>  noticeComplains =noticeService.getDutyChangedNotice(advanceMinute);

        if (noticeComplains!=null&&noticeComplains.size()>0) {
            List<Long> ids = noticeComplains.stream().map(NoticeComplainInfo::getID).collect(Collectors.toList());
            int rs = noticeService.updateNoticeStates(ids, NoticeStateEnum.Wait);
            CLogger.info("DutyChangedNoticeWorker", String.format("成功更新%s条", rs));
        }
    }
}
