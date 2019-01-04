package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class DmCrossStationInfo {
    //原始出发站
    private String originDepStation;
    //原始到达站
    private String originArrStation;
    //推荐出发站
    private String recommendDepStation;
    //推荐到达站
    private String recommendArrStation;
    //推荐出发站类型 大于0多抢 小于0少抢
    private int recommendDepStationType;
    //推荐到达站类型 大于0多抢 小于0少抢
    private int recommendArrStationType;
    //推荐Title (如：跨站抢票)
    private String recommendTitle;
    //标题后提示
    private String recommendHint;
    //多花金额，如无则为0
    private BigDecimal extraAmount;
    //抢票成功率
    private BigDecimal successGrabRate;
    //
    private String trainNumber;
}
