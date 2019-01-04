package com.ctrip.train.kefu.system.offline.notice.enums;

import java.util.Arrays;

/**
 * 通知紧急程度枚举
 */
public enum EmergencyStateEnum {
    Normal(0,"一般"),
    Urgent(1,"紧急");
    private int state;
    private String name;

    EmergencyStateEnum(int state, String name) {
        this.state = state;
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }
    public static EmergencyStateEnum convertEmergencyState(int state){
        return Arrays.stream(EmergencyStateEnum.values()).filter(it->it.getState()==state).findFirst().orElse(null);
    }
}
