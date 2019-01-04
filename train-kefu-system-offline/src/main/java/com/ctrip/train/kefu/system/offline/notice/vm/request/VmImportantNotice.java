package com.ctrip.train.kefu.system.offline.notice.vm.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmImportantNotice {
    private String orderId;
    private String contactUser;
    private String contactPhone;
    private Integer noticeSource;
    private Integer productLine;
    private String contents;
    private Integer emergency;
    private Integer eventType;
    private Integer firstType;
    private Integer secondType;
    private String appointedProcessTime;
    private Integer channelSource;
    private Integer orderType;
    private String enterUser;
}
