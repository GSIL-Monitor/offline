package com.ctrip.train.kefu.system.offline.order.vm.train;

import com.ctrip.train.kefu.system.offline.order.vm.train.order.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VmTrainOrderDetail {

    /**
     * 订单详情基础信息
     */
    private VmTrainOrderBasicInfo basicInfo;


    /**
     *订单详情车票（乘客）信息
     */
    private VmTrainOrderTicketsInfo ticketsInfo;


    /**
     * 订单详情抢票任务
     */
    private VmTrainOrderGrabTask grabTask;

    /**
     *订单详情附加产品
     */
    private VmTrainOrderAppend appendProduct;

    /**
     * 订单详情右侧栏操作
     */
    private VmTrainOrderOperate operate;

}
