package com.ctrip.train.kefu.system.offline.order.vm.train.order;


import lombok.Getter;
import lombok.Setter;

/**
 * 订单基本信息模块
 */
@Getter
@Setter
public class VmTrainLog {
    private String actionType;
    private String operator;
    private String operateTime;
    private String comment;
}
