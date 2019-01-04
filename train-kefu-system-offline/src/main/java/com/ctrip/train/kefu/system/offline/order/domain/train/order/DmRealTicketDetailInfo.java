package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DmRealTicketDetailInfo {

    private long orderRealTicketId;

    //订单号
    private long orderId;

    //乘客主机号
    private long passengerInfoId;

    //出票价格
    private BigDecimal ticketPrice;

    //坐席
    private String seatName;

    //合作方名称
    private String partnerName;

    //行程主键号
    private long orderTicketID;

    //短电子订单号
    private String shortElecNum;

    //0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功
    private int ticketState;

    //创建时间
    private String createTime;

    //更新时间
    private String dataChangeLastTime;

    //车票日期
    private String ticketDate;

    //车票时间
    private String ticketTime;

    //车票到达时间
    private String arriveDateTime;

    //坐席号
    private String seatNumber;

    //退票失败描述
    private String returnFailRemark;

    //退票失败代码
    private String returnStateCode;

    //实际出票账户
    private String userId12306;

    //实际出票密码
    private String passWord12306;

    //车厢
    private String carriageNo;

    //出票提醒
    private String tips;

    //长电子订单号
    private String longTrainNo;

    //是否可退票
    private boolean enableReturnTicket;

    //是否快速进站
    private int quickPass;

    //车次状态(1-停运;0正常)
    private long trainStatus;
}
