package com.ctrip.train.kefu.system.job.worker.workers.notice;

import com.ctrip.train.kefu.system.job.worker.BaseWorker;
import com.ctrip.train.kefu.system.job.enums.notice.NoticeStateEnum;
import com.ctrip.train.kefu.system.job.worker.service.notice.NoticeService;
import com.ctrip.train.kefu.system.job.worker.service.staff.StaffService;
import common.log.CLogger;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;
import qunar.tc.schedule.Parameter;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointedNoticeWorker extends BaseWorker {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private StaffService staffService;

    /**
     * 预约回电的==3分钟若员工在上班，
     * 若员工的状态是离线的，就再次进入拉单分配。
     * 每分钟执行一次
     */
    @QSchedule(value = "com.ctrip.train.offline.AppointedNoticeCheckWorker")
    @Override
    public void doWorker(Parameter parameter) {

        //提前多少分钟左右检查
        int advanceMinute= Integer.parseInt(parameter.getString("advanceMinute"));

        //notice 数据
        List<NoticeComplainInfo>  noticeComplains =noticeService.getAppointedNotice(advanceMinute);
        if (noticeComplains!=null&&noticeComplains.size()>0) {
            //检查通知的处理人是否在线
            List<NoticeComplainInfo> unhandled = noticeComplains.stream().filter(notice -> {
                String opUser = notice.getOpUser();
                if (StringUtils.isEmpty(opUser)) {
                    return false;
                } else {
                    if (opUser.contains("(")) {
                        return staffService.getStaffState(opUser.split("\\(")[0]) == 2;
                    } else {
                        return staffService.getStaffState(opUser) == 2;
                    }
                }
            }).collect(Collectors.toList());
            List<Long> ids = unhandled.stream().map(NoticeComplainInfo::getID).collect(Collectors.toList());
            int rs = noticeService.updateNoticeStates(ids, NoticeStateEnum.Wait);
            CLogger.info("AppointedNoticeCheckWorker", String.format("成功更新%s条", rs));
        }
    }
}
