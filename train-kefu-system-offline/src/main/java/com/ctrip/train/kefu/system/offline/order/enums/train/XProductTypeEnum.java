package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 混合电子发票补寄的 补寄类型
 */
public enum XProductTypeEnum {

    XInsurance(1,"保险"),
    XGiftCard(2,"礼品卡"),
    ServiceCharge(3,"服务费"),   //（针对汽车票）
    XDelayInsurance(4,"延误险"),
    DeliveryTicket(5,"配送票"),
    XQiangPiaoInsurance(6,"抢票险"),
    XHCouponInvoiceType(7,"酒店优惠券"),
    XFiveHotboomInvoiceType(8,"5元代购费"),
    XInsureService(9,"保险套餐"),   //客户实际出的钱和出保险的钱不一致【例如：三十元保险的钱，只出五元的保险，另外二十五作为服务费】
    XShenSuanRufundTicket(10,"神算退票费"),   //（张越那边在用，xbind项目不用，为了统一类型）
    XProductElecteric(11,"X产品电子发票"),
    XProductCtripVip(12,"携程超级会员费用"),
    XProductTypeDefault(99,"");    //返回null的时候会报错，给默认值

    private Integer code;
    private String name;

    XProductTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static XProductTypeEnum convertVendor(Integer code){
        return Arrays.stream(XProductTypeEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(XProductTypeDefault);
    }
}
