package com.ctrip.train.kefu.system.offline.order.vm.train.refund;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VmRefundSxfRequest {

    /**
//     * 订单ID
//     */
//    private String orderId ;
//
//    /**
//     * 票ID
//     */
//    private String ticketId ;

    /**
     * 票价格
     */
    private BigDecimal totalPrice ;

    /**
     * 票类型 原票-0 改签票——1
     */
    private Integer ticketType ;

    /**
     * 原车票时间
     */
    private Date ticketDate ;

    /**
     * 改签票发车时间
     */
    private Date changeTicketDate ;

    /**
     * 改签成功时间
     */
    private Date changeSuccessDate ;

    /**
     * 是否不扣手续费 比喻停运的情况下不扣手续费
     */
    private Boolean noHasSxf ;
    /**
     * 到达
     */
    private String toStationName;
}
