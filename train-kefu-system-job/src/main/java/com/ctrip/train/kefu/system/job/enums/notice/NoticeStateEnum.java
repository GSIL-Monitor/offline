package com.ctrip.train.kefu.system.job.enums.notice;

import java.util.Arrays;

public enum NoticeStateEnum {
    Wait{
        @Override public int getState() { return 80; }
        @Override public String getName() { return "待分配"; }
    },
    Assigned{
        @Override public int getState() { return 81; }
        @Override public String getName() { return "处理中"; }
    },
    Deferred {
        @Override public int getState() { return 82; }
        @Override public String getName() { return "暂缓"; }
    },
    Solved{
        @Override public int getState() { return 83; }
        @Override public String getName() { return "已解决"; }
    },
    WaitDeal {
        @Override public int getState() { return 84; }
        @Override public String getName() { return "员工待处理"; }
    },
    TurnComplain {
        @Override public int getState() { return 94; }
        @Override public String getName() { return "已转投诉"; }
    },
    TurnLeaderNotice {
        @Override public int getState() { return 100; }
        @Override public String getName() { return "已转领班"; }
    },
    ChangeDuty {
        @Override public int getState() { return 102; }
        @Override public String getName() { return "已交班"; }
    };
    public static NoticeStateEnum convertNoticeState(int state){
        return Arrays.stream(NoticeStateEnum.values()).filter(it->it.getState()==state).findFirst().orElse(null);
    }
    public abstract int getState();
    public abstract String getName();
}
