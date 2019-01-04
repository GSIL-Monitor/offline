package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

public enum TieyouLevelEnum {
    LowSpeed("0-9","低速"),
    ZhongSpeed("10-19","中速"),
    KuaiSpeed("20-29","快速"),
    GaoSpeed("30-49","高速"),
    Vip("50","VIP");
    private String code;
    private String name;

    TieyouLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() { return code;}
    public String getName() {
        return name;
    }

    public static TieyouLevelEnum convertVendor(Integer code){
        return Arrays.stream(TieyouLevelEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(LowSpeed);
    }
}
