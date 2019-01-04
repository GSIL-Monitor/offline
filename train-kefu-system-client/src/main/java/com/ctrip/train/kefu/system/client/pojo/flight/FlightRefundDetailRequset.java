package com.ctrip.train.kefu.system.client.pojo.flight;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightRefundDetailRequset {

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 退票操作人
     */
    private String operatorName;

    /**
     * 铁友Uid
     */
    private String tyUerid;

    /**
     * 携程Uid
     */
    private String ctripUid;
}
