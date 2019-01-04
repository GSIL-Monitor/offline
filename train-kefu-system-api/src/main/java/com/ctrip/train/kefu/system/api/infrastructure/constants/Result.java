package com.ctrip.train.kefu.system.api.infrastructure.constants;

//返回接口
public class Result {
    public static final Integer  SUCCESS = 1;
    public static final Integer   FAIL= 0;
    public static final Integer    EXEP = -1;


    public static final String Message_Fail = "接口调用失败";
    public static final String Message_Parameter = "%s不可能为空";

    public static String ParamIsEmpty(String param) {
        return String.format(Message_Parameter, param);
    }
}
