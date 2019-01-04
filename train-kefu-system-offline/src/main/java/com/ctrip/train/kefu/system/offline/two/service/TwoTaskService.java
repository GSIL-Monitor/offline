package com.ctrip.train.kefu.system.offline.two.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TwoTaskService {
    /**
     * 根据orderId更改订单状态
     * @param map
     * @return
     * @throws Exception
     */
    int updateScmTwoTripTable(Map<String,Object> map)  ;

}
