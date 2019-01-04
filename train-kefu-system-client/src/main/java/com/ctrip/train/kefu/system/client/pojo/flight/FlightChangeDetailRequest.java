package com.ctrip.train.kefu.system.client.pojo.flight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightChangeDetailRequest {
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 退票操作人
     */
    private int operateType;

    /**
     * 铁友Uid
     */
    private String tyUserId;

    /**
     * 携程Uid
     */
    private String ctripUId;

}
