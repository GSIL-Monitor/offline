package com.ctrip.train.kefu.system.client.offline.car;


import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.cartel.adminservice.contract.GetOrderDetailRequestType;
import com.ctrip.train.cartel.adminservice.contract.GetOrderDetailResponseType;
import com.ctrip.train.cartel.adminservice.contract.TrainCartelAdminClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Component
public class ZhixingCarContract {

    public GetOrderDetailResponseType getOrderDetail(Long orderNumber){
        GetOrderDetailResponseType response = new GetOrderDetailResponseType();
        try {
            TrainCartelAdminClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainCartelAdminClient.getInstance("http://10.28.101.33:8080/api");
            } else {
                client = TrainCartelAdminClient.getInstance();
            }
            GetOrderDetailRequestType request = new GetOrderDetailRequestType();
            request.setOrderNumber(orderNumber);
            response = client.getOrderDetail(request);
        }catch(Exception ex){
            CLogger.error("专车异常件GetOrderDetail",ex);
        }
        return response;
    }
}
