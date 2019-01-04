package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
/**
 * 乘客信息
 */
public class DmPassengersInfo {
    private Long passengerId;	//乘客id
    private Long orderTicketId;	//车票id
    private Long orderId;	//订单号
    private String partnerOrderId;	//合作方订单号
    private String partnerName;	//合作方
    private String passportType;	//证件类型
    private String passportName;	//证件名称
    private String passportNumber;	//证件号
    private String realName;	//真实姓名
    private Integer passengerType;	//乘客类型
    private String birthday;	//生日
    private String createTime;	//CreateTime
    private String dataChangeLastTime;	//DataChangeLastTime
    private Integer isSelf;    //0:不是本人 1:是本人
}
