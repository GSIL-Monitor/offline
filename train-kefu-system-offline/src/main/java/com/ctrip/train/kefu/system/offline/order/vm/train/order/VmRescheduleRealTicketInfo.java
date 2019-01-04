package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class VmRescheduleRealTicketInfo {
    //实际出票id
    private long realTicketId;

    //乘客id
    private long passengerInfoId;

    //改签车票价格
    private BigDecimal rescheduleTicketPrice;

    //实际车票价格
    private BigDecimal rescheduleRealPrice;

    //改签坐席
    private String rescheduleSeatName;

    //改签后真实坐席
    private String rescheduleRealSeatName;

    //改签车厢
    private String rescheduleCarriageNo;

    //改签长电子订单号
    private String rescheduleLongTrainNum;

    //改签座位号
    private String rescheduleSeatNo;

    //改签表状态 未知-0 待支付补款-10 待推送到退改签系统-20 改签中-30 改签成功-40 改签失败-50 退票中-60 退票成功-70 退票失败-80
    private int rescheduleState;

    //提示信息
    private String tips;

    //改签失败理由
    private String reason;

    //子订单号
    private long extOrderId;

    //改签类型（0：普通改签 1：捡漏改签）
    private int rescheduleType;

    //是否快速进站
    private int quickPass;

    //车次状态：1-停运
    private long trainStatus;
}
