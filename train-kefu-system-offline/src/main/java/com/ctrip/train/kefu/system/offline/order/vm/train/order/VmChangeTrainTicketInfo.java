package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import com.ctrip.soa.train.trainordercentreservice.v1.RescheduleRealTicketInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
public class VmChangeTrainTicketInfo {
    //订单号
    private long orderId;
    //改签车次
    private String rescheduleTrainNumber;
    //改签出发站
    private String departStationName;
    //改签到达站
    private String arriveStationName;
    //改签出发时间
    private String rescheduleDepartTime;
    //改签到达时间
    private String rescheduleArriveTime;
    //改签具体车票信息
    private List<RescheduleRealTicketInfo> rescheduleRealTicketInfos;
    //改签出发时间
    private Calendar rescheduleSuccessTime;

}
