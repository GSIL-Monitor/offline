package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

@Setter
@Getter
public class DmOrderTicketModel {
    //订单号
    private long orderTicketId;
    //订单号
    private long orderId;

    //合作方订单号
    private String partnerOrderId;

    //合作方名称
    private String partnerName;

    //登陆名称
    private String loginName;

    //车票日期
    private String ticketDate;

    //车票时间
    private String ticketTime;

    //车票张数
    private int ticketCount;

    //车票订单面价
    private BigDecimal ticketPrice;

    //代收费
    private BigDecimal saleFeePrice;

    //出发站
    private String departStationName;

    //到达站
    private String arriveStationName;

    //车次
    private String trainNumber;

    //所有可接受车次
    private String allAccpetTrainNumber;

    //坐席
    private String ticketSeat;

    //可接受坐席
    private String acceptSeat;

    //子订单号
    private String subOrderId;

    //行程编号
    private int sectionNumber;

    //车票状态
    private int ticketState;

    //创建时间
    private Calendar createTime;

    //更新时间
    private Calendar dataChangeLastTime;

    //服务费
    private BigDecimal serverFee;

    //直连票的电子订单号
    private String electronicNum;

    //直连票表示
    private int archive;

    //车次id
    private long trainID;

    //到达时间
    private Calendar arrivalDateTime;

    //出发站英文名
    private String departureCityNameEn;

    //到达站英文名
    private String arrivalCityNameEn;

    //到达车站类型
    private String arriveStopType;

    //出发车站类型
    private String departureStopType;

    //到达站id
    private long arrivalStationID;

    //到达站英文名
    private String arrivalStationEn;

    //出发站id
    private long departureStationID;

    //出发站英文名
    private String departureStationEn;

    //车票id
    private long ticketID;

    //成人数量
    private int auditTicketCount;

    //儿童数量
    private int childTicketCount;

    //出发城市id
    private String fromCityId;

    //到达城市id
    private String toCityId;

    //出发城市
    private String fromCityName;

    //到达城市
    private String toCityName;

    //余票数量
    private int seatInventory;

    //检票口信息
    private String ticketCheck;

    //抢票条件（0：按车次座席抢票 1：按时间段抢票）
    private int grabType;

    //抢票备选可接受日期(2016-01-01,2016-01-02)
    private String acceptDepartDates;

    //抢票接受时间段（06:00-07:00）
    private String acceptTimeRanges;

    //抢票接受车次类型(G,D,C)
    private String acceptTrainTypes;

    //抢票接受出发站集合(上海虹桥,上海南)
    private String acceptDepartStations;

    //抢票接受到达站(北京南,北京西)
    private String acceptArriveStations;

    //RecLinePrepaidMoney
    private BigDecimal recLinePrepaidMoney;

    //保底票车次
    private String backupTrainNumbers;

    //保底票出行日期
    private String backupDepartDates;

    //保底票到达站
    private String backupArriveStations;

    //保底票座席)
    private String backupSeatNames;

    //预售时间
    private Calendar preSaleTime;
}
