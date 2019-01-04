package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class DmTrainOrderGrabTask {
    //行程
    private List<DmOrderTicketModel> orderTicketModels;
    //跨站抢信息
    private List<DmCrossStationInfo> crossStationInfo;
    //订单扩展信息
    private DmTrainorderExtInfo trainOrderExtInfo;
    //抢票订单信息
    private DmOrderGrabInfo orderGrabInfo;

}
