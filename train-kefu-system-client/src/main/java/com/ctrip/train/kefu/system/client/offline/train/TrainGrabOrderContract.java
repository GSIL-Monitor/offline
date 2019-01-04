package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.train.traingrabticket.graborder.v1.GetOrderDetailForOfflineRequestType;
import com.ctrip.soa.train.traingrabticket.graborder.v1.GetOrderDetailForOfflineResponseType;
import com.ctrip.soa.train.traingrabticket.graborder.v1.TrainGrabOrderClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;

@Component
public class TrainGrabOrderContract {

    public GetOrderDetailForOfflineResponseType getOrderDetailForOffline(String OrderNumber){
        GetOrderDetailForOfflineResponseType response = new GetOrderDetailForOfflineResponseType();
        try {
            TrainGrabOrderClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainGrabOrderClient.getInstance("http://10.28.103.33:8080/api/");
            } else {
                client = TrainGrabOrderClient.getInstance();
            }
            GetOrderDetailForOfflineRequestType requestType =new GetOrderDetailForOfflineRequestType();
            requestType.setOrderNumber(OrderNumber);
            response = client.getOrderDetailForOffline(requestType);
        } catch (Exception ex) {
            CLogger.error("TrainGrabOrderContract.getOrderDetailForOffline", ex);
        }
        return response;
    }

}
