package com.ctrip.train.kefu.system.offline.order.vm.train.order;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmResponseS2S {

    //车站名
    private String stationName;
    //到站序号
    private String stationNo;
    //离站时间
    private String startTime;
    //到站时间
    private String arrivalTime;
    //停靠分钟
    private String stopMinutes;
    //是否在行程内 0是 1否 2出发站 3到达站
    private int stationFlag;

}
