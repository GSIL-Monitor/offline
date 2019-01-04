package com.ctrip.train.kefu.system.client.offline.common;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.payment.router.dataserver.*;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * 支付中心接口
 */
@Component
public class PaymentContract {

    /**
     *获取支付渠道
     **/
    public GetAllPaymentwayListResponse getAllPaymentwayList()
    {
        GetAllPaymentwayListResponse response = new GetAllPaymentwayListResponse();
        try
        {
            PaymentRouterBackupServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = PaymentRouterBackupServiceClient.getInstance("http://10.28.119.122:8080/router-backup-service");
            } else {
                client = PaymentRouterBackupServiceClient.getInstance();
            }
             response = client.getAllPaymentwayList(new GetAllPaymentwayListRequestType());
        }
        catch (Exception ex)
        {
            CLogger.error("getAllPaymentwayList", ex);
        }
        return response;
    }

}
