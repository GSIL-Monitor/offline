package com.ctrip.train.kefu.system.api.domain.notice.enums;

import java.util.Arrays;

/**
 * 通知处理类型枚举
 */
public enum NoticeOperateEnum {
    TurnComplain{
        @Override
        public String getCode() { return "turnComplain"; }
        @Override
        public int getState() { return NoticeStateEnum.TurnComplain.getState(); }
        @Override
        public String getName() { return "转投诉"; }
    }, TurnLeader{
        @Override
        public String getCode() { return "turnLeader"; }
        @Override
        public int getState() { return NoticeStateEnum.TurnLeaderNotice.getState(); }
        @Override
        public String getName() { return "转领班"; }
    }, ChangeDuty{
        @Override
        public String getCode() { return "changeDuty"; }
        @Override
        public int getState() { return NoticeStateEnum.ChangeDuty.getState(); }
        @Override
        public String getName() { return "交班"; }
    }, Defer{
        @Override
        public String getCode() { return "defer"; }
        @Override
        public int getState() { return NoticeStateEnum.Deferred.getState(); }
        @Override
        public String getName() { return "转投诉"; }
    }, Save{
        @Override
        public String getCode() { return "save"; }
        @Override
        public int getState() { return NoticeStateEnum.Assigned.getState(); }
        @Override
        public String getName() { return "备注"; }
    }, Solve{
        @Override
        public String getCode() { return "solve"; }
        @Override
        public int getState() { return NoticeStateEnum.Solved.getState(); }
        @Override
        public String getName() { return "解决"; }
    }, Call{
        @Override
        public String getCode() { return "call"; }
        @Override
        public int getState() { return 103 ; }
        @Override
        public String getName() { return "外呼"; }
    }, Urge{
        @Override
        public String getCode() { return "urge"; }
        @Override
        public int getState() { return 104 ; }
        @Override
        public String getName() { return "催处理"; }
    },
    Send{
        @Override
        public String getCode() { return "send"; }
        @Override
        public int getState() { return 105 ; }
        @Override
        public String getName() { return "回复供应商"; }
    }
    ;
    public abstract String getCode();
    public abstract int getState();
    public abstract String getName();

    public static NoticeOperateEnum convertNoticeOperateType(String action){
        return Arrays.stream(values()).filter(it->it.getCode().equals(action)).findFirst().orElse(null);
    }
}
