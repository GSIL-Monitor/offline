package com.ctrip.train.kefu.system.offline.order.domain.train;

import com.ctrip.train.kefu.system.offline.order.domain.train.order.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DmTrainOrderDetail {

    /**
     * 附加产品信息
     */
    private List<DmTrainOrderAppend> dmTrainOrderAppend;

    /**
     * 优惠券套餐
     */
    private List<DmTrainCouponInfo> dmTrainCouponInfo;
    /**
     * 基本信息
     */
    private DmTrainOrderBasicInfo dmTrainOrderBasicInfo;

    /**
     * 抢票信息
     */
    private DmTrainOrderGrabTask dmTrainOrderGrabTask;

    /**
     * 操作信息
     */
    private DmTrainOrderOperate dmTrainOrderOperate;

    /**
     * 行程乘客信息
     */
    private DmTrainOrderTicketsInfo dmTrainOrderTicketsInfo;

    /**
     * 乘客信息
     */
    private List<DmPassengersInfo> dmPassengersInfo;
}
