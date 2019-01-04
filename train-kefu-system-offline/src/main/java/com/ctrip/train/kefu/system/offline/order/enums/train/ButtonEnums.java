package com.ctrip.train.kefu.system.offline.order.enums.train;

public enum ButtonEnums {

//    ImportantNotice("","重要通知"," ","ImportantNotice"),

//    ExEvent("","异常件"," ","ExEvent"),

//    Notice119("","119"," ","Notice119"),

    Certificate("","凭证"," ","Certificate"),

    Invoice("","发票"," ","Invoice"),

    TwoPush("","二推"," ","TwoPush"),

//    SaleNotice("","代售点通知"," ","SaleNotice"),

    AddAdvance("","添加垫付","","AddAdvance"),

    In12306("","进入12306","","In12306"),

    UnbindAccount("","解绑账号","","UnbindAccount");

    ButtonEnums(String value, String name,String url,String code) {
        this.value = value;
        this.name = name;
        this.url = url;
        this.code = code;
    }

    private String value;
    private String name;
    private String url;
    private String code;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
