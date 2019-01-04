package com.ctrip.train.kefu.system.offline.notice.vm.notice;

import com.ctrip.train.kefu.system.offline.notice.enums.EmergencyStateEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.EventTypeEnum;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmSendNoticeInfo {
    private String orderId;
    private String productLine;
    private String contactUser;
    private String contactPhone;
    private List<EmergencyStateEnum> emergency;
    private List<EventTypeEnum> eventTypes;
    private List<ScmSmallEnum> noticeSources;
}
