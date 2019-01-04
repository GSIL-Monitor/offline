package com.ctrip.train.kefu.system.offline.system.enums;

import java.util.Arrays;

public enum StaffGroupEnum {
    Notice(1,"通知"),
    Complain(2,"投诉"),
    LeaderNotice(4,"领班"),
    Online(5,"在线"),
    Telephone(6,"电话");
    private int value;
    private String name;

    StaffGroupEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    public static StaffGroupEnum convertEventType(int value){
        return Arrays.stream(StaffGroupEnum.values()).filter(it->it.getValue()==value).findFirst().orElse(null);
    }
}
