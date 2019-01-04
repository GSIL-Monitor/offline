package com.ctrip.train.kefu.system.offline.order.vm.flight.refund;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VmTickets {

    /**
     * 航段ID，申请用
     */
    private String sequence;


    /**
     * 供应商子订单号
     */
    private String vendorOrderNumber;


    /**
     * 航段标签 退票用
     */
    private int segmentNo;

    /**
     * 乘客ID
     */
    private  long passengerId;

    /**
     * 乘客姓名
     */
    private  String passengerName;

    /**
     * 乘客类型 成人票 儿童票 婴儿票
     */
    private  String passengerType;

    /**
     * 票号状态
     */
    private  String ticketStatu;

    /**
     * 机票状态
     */
    private  String stateDesc ;

    /**
     * 是否可退
     */
    private  Boolean canRefund;

    /**
     * 支付总金额
     */
    private String totalPrice;

    /**
     * 机票款
     */
    private String ticketsPrice;

    /**
     * 机建燃油
     */
    private String buildingFuelPrice;

    /**
     * 退票费
     */
    private  String refundPrice ;

    /**
     * 返现
     */
    private  String cashBackPrice;

    /**
     * 代金券
     */
    private  String voucherPrice;

    /**
     * 保险明细
     */
    private List<VmInsurance> insurances;

    /**
     * 不可退备注
     */
    private  String refundRemark ;

    /**
     * 退票类型
     */
    private  int refundType ;

    /**
     * 退票原因对应id
     */
    private  String reasonId ;
}
