package com.ctrip.train.kefu.system.offline.order.vm.train.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class VmTrainOrderTicketsInfo {
    //0 单程 1往返 2多程
    private int ticketType;
    //总票价
    private BigDecimal totalFare;

    //订单状态 1-全改 2-部分改 3-全退 4-部分退 仅自用
    private int orderStatus;
    //附属产品金额
    private BigDecimal appendProductPrice;
    //行程乘客信息
    private List<VmTrainRoute> trainRoutes;
    //改签信息
    private List<VmTrainRoute> changeTickets;
    //配送信息
    private VmOrderDeliveryInfo orderDelivery;

    //配送票位置的服务费
    private String saleFeePrice;

    //是否是柜台票
    private boolean coutnerTicket;

    //ticketType=1 多程票  billid = 乘客个数*（0,|）+乘客个数*（1,|）
    //billid=0,|0,|0,|1,|1,|1,| 三张去程票 三张返程票
    //ticketType=2 单程票  billid 为空
    //发异常件要求的字段
    private String childBillId;

}
