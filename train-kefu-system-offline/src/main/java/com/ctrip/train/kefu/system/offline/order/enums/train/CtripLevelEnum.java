package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

public enum CtripLevelEnum {
    LowSpeed("0-4","低速"),
    KuaiSpeed("5-19","快速"),
    GaoSpeed("20-34","高速"),
    JiSpeed("35-49","极速"),
    GuangSpeed("50-79","光速"),
    Vip("80","VIP");
    private String code;
    private String name;

    CtripLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() { return code;}
    public String getName() {
        return name;
    }

    public static CtripLevelEnum convertVendor(Integer code){
        return Arrays.stream(CtripLevelEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(LowSpeed);
    }
}
