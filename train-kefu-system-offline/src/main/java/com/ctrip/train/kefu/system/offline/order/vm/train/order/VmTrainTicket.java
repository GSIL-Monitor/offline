package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 乘客车票信息
 */
@Getter
@Setter
public class VmTrainTicket {

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
    /**
     * 备注
     */
    private String returnRemark;
    /**
     * 是否可退票
     */
    private boolean enableReturnTicket;
    /**
     * 退票手续费
     */
    private String handlingFee;
    /**
     * 退票手续费百分比
     */
    private String handlingPercentage;
    /**
     * 退款信息
     */
    private String refundPrice;
    /**
     * 长订单号
     */
    private String longTrainNo;
    /**
     * 快速进站
     */
    private int quickPass;

    /**
     * 快速进站
     */
    private int rescheduleType;



}
