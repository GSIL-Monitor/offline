package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DmPassengerModel {
    //乘客id
    private long passengerId;

    //车票id
    private long orderTicketId;

    //订单号
    private long orderId;

    //合作方订单号
    private String partnerOrderId;

    //合作方
    private String partnerName;

    //证件类型
    private String passportType;

    //证件名称
    private String passportName;

    //证件号
    private String passportNumber;

    //真实姓名
    private String realName;

    //乘客类型（1成人；2儿童；3学生）
    private int passengerType;

    //生日
    private String birthday;

    //CreateTime
    private String createTime;

    //DataChangeLastTime
    private String dataChangeLastTime;

    //0:不是本人 1:是本人
    private int isSelf;
}
