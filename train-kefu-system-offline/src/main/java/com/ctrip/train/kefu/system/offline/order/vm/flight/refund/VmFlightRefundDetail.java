package com.ctrip.train.kefu.system.offline.order.vm.flight.refund;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class VmFlightRefundDetail {

    /**
     * 退票操作人
     */
    private  String operatorName;

    /**
     * 联系人手机号
     */
    private  String contactMobile;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 铁友Uid
     */
    private String tyUerid;

    /**
     * 携程Uid
     */
    private String ctripUid;

    /**
     * 是否需要退票手续费
     */
    private  Boolean HasRefundPrice ;

    /**
     * 是否是未确认订单
     */
    private  Boolean IsNonComfirmOrder ;

    /**
     * 是否首次退票
     */
    private  Boolean IsFirstRefund;

    /**
     * 是否已经要过发票
     */
    private  Boolean HasRefundInvoice ;

    /**
     * 是否支付过邮寄费用
     */
    private  Boolean HasPaidDelivery ;

    /**
     * 接口返回信息
     */
    private  String message ;

    /**
     * 航程信息
     */
    private List<VmSegment> vmSegmentsList;

}


