package com.ctrip.train.kefu.system.offline.notice.enums;

import java.util.Arrays;

/**
 * 供应商枚举
 */
public enum VendorEnum {
    ctrip("ctrip","携程国内"),
    Intlctrip("Intlctrip","携程国际"),
    Qunar("Qunar","去哪儿国内"),
    IntlQunar("IntlQunar","去哪儿国际"),
    QunarWine("QunarWine","去哪儿机酒"),
    Jiulx("Jiulx","就旅行"),
    V19e("19e","19e"),
    LuTao("lutao","路淘网"),
    LetsFly("LetsFly","聪明点"),
    GloryHoliday("GloryHoliday","悦途");

    private String code;
    private String name;

    VendorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code;}

    public String getName() {
        return name;
    }

    public static VendorEnum convertVendor(String code){
        return Arrays.stream(VendorEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(null);
    }
}
