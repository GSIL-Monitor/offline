package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DmOrderGrabInfo {
    //最早出发时间，03:00
    private String earliestDepartTime;
    //接受临近车次时间段：12:00-15:00
    private String grabNearTime;
    //最晚出发时间，15:00
    private String latestDepartTime;
    //最大运行时长，单位分钟
    private int maxRunTime;
    //最大票价
    private BigDecimal maxTicketPrice;
    //虚拟速度
    private BigDecimal virtualPackageUnitPrice;
    //真实速度
    private BigDecimal packageUnitPrice;
    //抢票成功率
    private BigDecimal grabSuccessRate;
}
