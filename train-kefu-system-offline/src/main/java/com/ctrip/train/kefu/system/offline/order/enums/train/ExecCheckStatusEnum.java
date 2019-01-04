package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 来源offline，以那边为准
 */
public enum ExecCheckStatusEnum {
    /// 异常件审核状态
    /// 审核流程 ： 提出线下异常件, 服务老大审核, 财务审核

    ExecCheckPending(0,"财务待审核"),
    ExecCheckSuccess(1,"财务审核通过"),
    ExecCheckFail(2,"财务审核驳回"),
    ExecCheckServicePending(3,"服务待审核"),
    ExecCheckServiceManagerPending(4,"服务主管待审核"),
    ExecCheckServiceQunarPending(5,"财务待审核");

    private Integer code;
    private String name;

    ExecCheckStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static ExecCheckStatusEnum convertVendor(String code){
        return Arrays.stream(ExecCheckStatusEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(null);
    }
}
