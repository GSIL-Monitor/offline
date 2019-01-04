package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 附属产品表状态
 */
public enum AppendStateEnum {

    OutUnKnow("0",""),
    OutProcess("20","处理中(出)"),
    BackProcess("30","处理中(退)"),
    HandOutSuccess("40","处理成功(出)"),
    HandBackSuccess("50","处理成功(退)"),
    HandOutFail("60","处理失败(出)"),
    HandBackFail("70","处理失败(退)"),
    SystemError("99","系统异常"),
    IsUse("1","已消费");

    private String code;
    private String name;

    AppendStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code;}
    public String getName() {
        return name;
    }

    public static AppendStateEnum convertVendor(String code){
        return Arrays.stream(AppendStateEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(SystemError);
    }
}
