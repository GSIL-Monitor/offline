package com.ctrip.train.kefu.system.offline.order.domain.train.order;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class DmTrainRoute {
    /**
     * 行程类型
     */
    private int SectionNumber;
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
     * 到达站
     */
    private String arriveStationName;
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
     * 车票信息
     */
    private List<DmTrainTicket> trainTickets;
}
