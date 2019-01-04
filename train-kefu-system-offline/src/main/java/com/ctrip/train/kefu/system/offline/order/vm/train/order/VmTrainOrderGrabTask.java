package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmTrainOrderGrabTask {

    //加速等级
    private String speedLevel;
    //加速等级详情
    private List<VmTrainGrabLevel> levels;

    //原票 改签票
    private String ticketType;
    //接受车次
    private String allAccpetTrainNumber;
    //接受坐席
    private String acceptSeat;
    //接受日期
    private String acceptDepartDates;
    //接受时间段
    private String acceptTimeRanges;
    //创建时间
    private String createTime;
    //捡漏截止时间 TrainorderExtInfo
    private String leakCutOffTime;
    //抢票可接受站 TrainCrossStationGrabTicketInfo
    private String acceptArriveStations;
    //跨站信息
    private String crossStation;
}
