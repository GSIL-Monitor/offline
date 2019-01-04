package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 行程
 */
@Getter
@Setter

public class VmTrainRoute {

    /**
     * 行程类型
     */
    private int sectionNumber;
    /**
     * 行程类型
     */
    private String sectionStr;
    /**
     * 合作方订单号
     */
    private String partnerOrderId;

    private long orderTicketId;
    /**
     * 车票张数
     */
    private int ticketCount;
    /**
     * 车票订单面价
     */
    private BigDecimal ticketPrice;
    /**
     * 代收费
     */
    private BigDecimal saleFeePrice;
    /**
     * 出发站
     */
    private String departStationName;
    /**
     * 出发站地址
     */
    private String departAddress;
    /**
     * 到达站
     */
    private String arriveStationName;
    /**
     * 到达站地址
     */
    private String arriveAddress;
    /**
     * 到达时间
     */
    private String arrivalDateTime;
    /**
     * 车次
     */
    private String trainNumber;
    /**
     * 车票日期
     */
    private String ticketDate;

    private String vmTicketDate;
    /**
     * 车票时间
     */
    private String ticketTime;
    /**
     * 耗时
     */
    private String timeConsuming;
    /**
     * 取票号
     */
    private String electronicNum;
    /**
     * 检票口信息
     */
    private String ticketCheck;

    /**
     * 特别备注
     */
    private String acceptSeat;

    /**
     * 车次状态
     */
    private String trainStatus;

    /**
     * 车票信息
     */
    private List<VmTrainTicket> trainTickets;

    /**
     * 经停
     */
    private List<VmResponseS2S> stopStations;
    /**
     * 开售时间
     */
    private  String preSaleTime;

}
