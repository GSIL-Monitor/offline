package com.ctrip.train.kefu.system.offline.notice.vm;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VmOnceSolveReport {
    private String opUserName;
    private String opTime;
    private String percentage;
    private int envenType;
    private String productLine;
    private int allsovle;
    private int oncesovle;
    private String opuserNum;
}
