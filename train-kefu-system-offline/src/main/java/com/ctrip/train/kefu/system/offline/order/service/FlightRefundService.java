package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.VmFlightRefund;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.VmFlightRefundDetail;
import com.ctrip.train.kefu.system.offline.order.vm.flight.refund.dto.DtoRefund;

import java.util.Map;

public interface FlightRefundService {

    /**
     * 退票详情接口
     */
     VmFlightRefundDetail getRefundDetail(DtoRefund refund) ;

    /**
     * 退票接口
     */
    Map<String,String> refundDetail(VmFlightRefund refund);
}
