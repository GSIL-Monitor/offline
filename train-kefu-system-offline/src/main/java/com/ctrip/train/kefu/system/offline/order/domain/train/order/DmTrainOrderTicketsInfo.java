package com.ctrip.train.kefu.system.offline.order.domain.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class DmTrainOrderTicketsInfo {

    //订单类型
    private int orderType;
    //0 单程 1往返 2多程 票类型
    private int ticketType;
    //订单状态
    private int orderStatus;
    //真实出票
    private List<DmRealTicketDetailInfo> realTickets;
    //行程
    private List<DmOrderTicketModel> orderTicketModels;
    //乘客
    private List<DmPassengerModel> passengerModels;

    private Boolean isSleep;  //判断二推状态的时候有一个筛选卧铺的动作
    //总票价
    private BigDecimal totalFare;
    //附属产品金额
    private BigDecimal appendProductPrice;
    //行程乘客信息
    private List<DmTrainRoute> trainRoutes;
    //配送信息
    private DmOrderDeliveryInfo orderDeliveryInfo;
    //改签票信息
    private List<DmChangeTrainTicketInfo> changeTicketInfo;

}
