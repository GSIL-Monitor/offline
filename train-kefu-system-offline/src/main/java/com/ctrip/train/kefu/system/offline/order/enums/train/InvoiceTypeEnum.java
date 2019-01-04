package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 * 混合电子发票补寄的 补寄类型
 */
public enum InvoiceTypeEnum {

    InvoiceXproductType(1,"附属产品"),
    InvoiceInsuranceType(2,"保险"),
    InvoiceMemberCardType(4,"超级会员"),
    InvoiceRobType(8,"抢票险");

    private Integer code;
    private String name;

    InvoiceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static InvoiceTypeEnum convertVendor(Integer code){
        return Arrays.stream(InvoiceTypeEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(null);
    }
}
