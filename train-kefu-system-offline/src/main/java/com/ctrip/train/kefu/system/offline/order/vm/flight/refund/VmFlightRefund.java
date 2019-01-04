package com.ctrip.train.kefu.system.offline.order.vm.flight.refund;

import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.dto.DtoRefund;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class VmFlightRefund {

    /**
     * 退票订单信息
     */
    private DtoRefund refund;

    /**
     * 退票乘客信息
     */
    private List<VmTickets> tickets;

    /**
     * 配送类型
     */
    private  int deliveryType;

    /**
     * 配送地址
     */
    private  String address;

    /**
     * 城市
     */
    private  String city;

    /**
     * 地区
     */
    private  String district;

    /**
     * 省
     */
    private  String province;

    /**
     * 联系人姓名
     */
    private  String contactName;

    /**
     * 联系电话
     */
    private  String contactPhone;

    /**
     * 电子邮箱
     */
    private  String email;

    /**
     * 发票title
     */
    private  String invoiceTitle;


    /**
     * 税号
     */
    private  String taxNumber;

    /**
     * 发票类型
     */
    private  int invoiceType;

    /**
     * 操作人
     */
    private  String operateName;

}
