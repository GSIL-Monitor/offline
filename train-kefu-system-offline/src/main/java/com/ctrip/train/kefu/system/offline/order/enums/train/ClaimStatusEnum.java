package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 优惠券状态 10 已使用，20 已取消，30 未发放，35 已发放，40 发放失败，45 已退，50 策略优惠券取消
 */
public enum ClaimStatusEnum {

    Wait(0, "等待处理"),
    CheckAllowPay(1, "检查是否允许理赔"),
    RejectPay(2, "拒绝理赔"),
    AllowPay(3, "允许理赔"),
    LaunchPay(4, "发起理赔"),
    CompletePay(5, "理赔完成"),
    FailurePay(6, "理赔失败"),
    SystemError(99, "系统异常"),
    Blacklist(-1, "黑名单用户"),
    Default(999, "等待处理");
    private Integer code;
    private String name;

    ClaimStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static ClaimStatusEnum convertVendor(Integer code){
        return Arrays.stream(ClaimStatusEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(Default);
    }
}
