package com.ctrip.train.kefu.system.offline.order.vm.train.refund;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmRefundApplyInfo {

    /**
     * 子订单ID
     */
    private String childBillId;

    /**
     * 订单票ID
     */
    private String orderTid;

    /**
     * 证件姓名
     */
    private String passportName;

    /**
     * 证件号码
     */
    private String passportNumber;

    /**
     * 证件类型
     */
    private String passportType;

    /**
     * 票类型
     */
    private String ticketType;

    /**
     * 行程类型
     */
    private String ticketSectionTypeName;

    /**
     * 出发站
     */
    private String fromStationName;

    /**
     * 到达站
     */
    private String toStationName;

    /**
     * 车票时间
     */
    private String ticketTime;

    /**
     * 到达时间
     */
    private String arriveTime;

    /**
     * 车次
     */
    private String trainNumber;

    /**
     * 是否可退
     */
    private Boolean enableReturnTicket;

    /**
     * 是否可退原因
     */
    private String enableReturnTicketMark;

    /**
     * 是否改签
     */
    private Boolean isChanged;

    /**
     * 改签类型 票状态为改签中的时候  1和2 是改签抢票中   其他 改签中
     */
    private Integer rescheduleType;

    /**
     * 票状态显示名称
     */
    private  String statusName;

    /**
     * 检票口信息
     */
    private  String  ticketCheck;

    /**
     * 坐席
     */
    private  String  seatName;

    /**
     * 坐席号
     */
    private  String  seatNo;

    /**
     * 车票票价
     */
    private  Float ticketPrice;

    /**
     * 下单票价
     */
    private  Float orderTicketPrice;

    /**
     * 退票手续费
     */
    private  Float sxfPrice;

    /**
     * 退票手续费百分比
     */
    private  String sxfPriceRate;


}


