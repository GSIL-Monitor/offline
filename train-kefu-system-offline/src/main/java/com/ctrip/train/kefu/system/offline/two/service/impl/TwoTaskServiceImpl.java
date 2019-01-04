package com.ctrip.train.kefu.system.offline.two.service.impl;


import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import com.ctrip.train.kefu.system.offline.two.dao.ExtScmTwoTask;
import com.ctrip.train.kefu.system.offline.two.service.TwoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TwoTaskServiceImpl implements TwoTaskService {

    @Autowired
    private ExtScmTwoTask extScmTwoTask;
    @Autowired
    private OrderContract trainOrderCentreService;
    /**
     * 根据orderId更改订单状态
     * @param map
     * @return
     * @throws SQLException
     */
    public int updateScmTwoTripTable(Map<String, Object> map)   {

        Map<String, String> logMap=new HashMap<>();
        logMap.put("orderId",String.valueOf(map.get("orderId")));
        logMap.put("partnerOrderId",String.valueOf(map.get("partnerOrderId")));
        logMap.put("comment",String.valueOf(map.get("processingRemark")));//内容
        logMap.put("operator",String.valueOf(map.get("operator")));
        logMap.put("reasonType",String.valueOf(map.get("reasonType")));
        logMap.put("actionName",String.valueOf(map.get("actionName")));//日志操作类型
        map.put("opReason","0");
        int temp=extScmTwoTask.updateOrderStatusByOrderId(map);
        if(temp==1){
            trainOrderCentreService.addOrderLog(logMap);
        }
        return temp;
    }
}
