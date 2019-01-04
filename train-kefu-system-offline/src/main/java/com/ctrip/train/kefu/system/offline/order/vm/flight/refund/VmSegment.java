package com.ctrip.train.kefu.system.offline.order.vm.flight.refund;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class VmSegment {

    /**
     * 供应商子订单号
     */
    private String vendorOrderNumber;

    /**
     * 航段标签
     */
    private String segmentTag;

    /**
     * 航段ID，申请用
     */
    private String sequence;

    /**
     * 航段标签 退票用
     */
    private int segmentNo;

    /**
     * 航班号
     */
    private String filghtNumber;

    /**
     * 出发时间
     */
    private String departDateTime;

    /**
     * 出发城市
     */
    private String  arriveCityName;

    /**
     * 到达城市
     */
    private String  departCityName;

    /**
     * 是否已经起飞
     */
    private Boolean  HasDepart;

    /**
     * 退票原因
     */
    private String  reasonId;


    /**
     * 乘客信息
     */
    private List<VmTickets> vmTickets;

}
