package com.ctrip.train.kefu.system.offline.order.enums.train;

import java.util.Arrays;

/**
 *
 */
public enum EleterInvoiceEnum {

    PaperInvoice(1,"纸质票状态打印"),
    WaitOpenInvoice(3,"电子发票已申请"),
    SuccessOpenInvoice(4,"X产品成功开票"),
    FailOpenInvoice(5,"X产品开票失败"),
    FailOpenInvoiceBySign(6,"X产品验签失败"),
    WaitRedFlush(7,"X产品发起红冲"),
    SuccessRedFlush(8,"X产品红冲成功"),
    FailRedFlush(9,"X产品红冲失败"),
    SuccessSendEmail(10,"成功发送Email"),
    FailSendEmail(11,"失败发送Email"),
    DefaultInvoice(99,"");

    private Integer code;
    private String name;

    EleterInvoiceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() { return code;}
    public String getName() {
        return name;
    }

    public static EleterInvoiceEnum convertVendor(Integer code){
        return Arrays.stream(EleterInvoiceEnum.values()).filter(it->it.getCode().equals(code)).findFirst().orElse(DefaultInvoice);
    }
}
