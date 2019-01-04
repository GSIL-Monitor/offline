package com.ctrip.train.kefu.system.job.enums.notice;

import java.util.Arrays;

/**
 * @author ying_zhou 2017/11/8 9:28
 */
public enum EventTypeEnum {
    Notice(1,"通知"),
    Complain(2,"投诉"),
    LeaderNotice(4,"领班");
    private int value;
    private String name;

    EventTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    public static EventTypeEnum convertEventType(int value){
        return Arrays.stream(EventTypeEnum.values()).filter(it->it.getValue()==value).findFirst().orElse(null);
    }
}
