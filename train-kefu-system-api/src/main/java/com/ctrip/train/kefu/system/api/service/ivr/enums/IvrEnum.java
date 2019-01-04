package com.ctrip.train.kefu.system.api.service.ivr.enums;

import java.util.Arrays;

public enum IvrEnum {
    InputHaveDate(100, "InputHaveDate"),//键入日期非空
    InputNoDate(101, "InputNoDate"),//键入日期为空

    Other(0, "Other"),//其它
    GetQuestion(1, "GetQuestion"),//获取问题
    GetResult(2, "GetResult"),//获取答案

    HasOrder(100, "HasOrder"),//过滤有订单
    TicketInOneHour(101, "TicketInOneHour"),//发车前后一小时
    QueryNoOrder(106, "QueryNoOrder"),//查询无订单
    TransferInQuestion(105, "TransferInQuestion"),//问题转人工
    TransferInResult(104, "TransferInResult");//答案转人工

    private Integer code;
    private String name;

    IvrEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static IvrEnum convertVendor(Integer code) {
        return Arrays.stream(IvrEnum.values()).filter(it -> it.getCode().equals(code)).findFirst().orElse(null);
    }
}
