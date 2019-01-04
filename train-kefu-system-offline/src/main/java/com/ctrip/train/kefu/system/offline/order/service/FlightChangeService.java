package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.RebookResponseType;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightChangeDetailRequest;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmFlightChange;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmFlightChangeCollect;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmFlightSerach;
import com.ctrip.train.kefu.system.offline.order.vm.flight.change.VmRebookRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightChangeService {

    /**
     * 机票改签详情
     * @param requset
     */
    VmFlightChangeCollect getFlightChangeDetail(FlightChangeDetailRequest requset);

    /**
     * 改签航变信息
     */
    JsonResult getRebookFlightList(VmFlightSerach request);

    /**
     * 提交改签申请
     */
    RebookResponseType pushFlightChange(VmRebookRequest request);
}
