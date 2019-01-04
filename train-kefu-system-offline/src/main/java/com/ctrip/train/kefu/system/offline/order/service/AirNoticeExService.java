package com.ctrip.train.kefu.system.offline.order.service;

import com.ctrip.train.kefu.system.offline.order.vm.RequestAirOrderEx;
import com.ctrip.train.kefu.system.offline.order.vm.ResponseAirOrderEx;

public interface AirNoticeExService {
    ResponseAirOrderEx searchAirNoticeEx (RequestAirOrderEx request);
}
