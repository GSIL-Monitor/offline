package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DmTrainTicket {
    /**
     * 乘客id
     */
    private Long passengerId;
    /**
     * 车票id
     */
    private Long orderTicketId;
    /**
     * 订单号
     */
    private Long orderId;
    /**
     * 合作方
     */
    private String partnerName;
    /**
     * 证件类型
     */
    private String passportType;
    /**
     * 姓名
     */
    private String passportName;
    /**
     *证件号
     */
    private String passportNumber;
    /**
     *真实姓名
     */
    private String realName;
    /**
     *"乘客类型（1成人；2儿童；3学生）
     */
    private int passengerType;
    /**
     * 车票状态
     */
    private int ticketState;
    /**
     * 坐席
     */
    private String seatName;
    /**
     * 票价
     */
    private BigDecimal ticketPrice;
    /**
     * 真实票价
     */
    private BigDecimal realTicketPrice;
}
