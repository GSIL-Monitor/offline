package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.train.ctripticketingservice.v1.CtripTicketingServiceClient;
import com.ctrip.soa.train.ctripticketingservice.v1.QueryRefundTicketResultV20RequestType;
import com.ctrip.soa.train.ctripticketingservice.v1.QueryRefundTicketResultV20ResponseType;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Component
public class CtripticketingserviceContract {

    /**
     * 退票结果查询 offline 方法备注 是这个 但是用的时候 通过 DealType != 0 判断他是否神算
     * @param orderNumber
     * @return
     */
    public QueryRefundTicketResultV20ResponseType getQueryRefundTicketResult(String orderNumber){
        QueryRefundTicketResultV20ResponseType response = new QueryRefundTicketResultV20ResponseType();
        try {
            CtripTicketingServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = CtripTicketingServiceClient.getInstance("http://ws.refundendorse.ticketing.train.ctripcorp.com/train-ticketing-refundendorsews/api/");
            } else {
                client = CtripTicketingServiceClient.getInstance();
            }
            QueryRefundTicketResultV20RequestType request = new QueryRefundTicketResultV20RequestType();
            request.setOrderID(orderNumber);
            response = client.queryRefundTicketResultV20(request);
        }catch(Exception ex){
            CLogger.error("getQueryRefundTicketResult",ex);
        }
        return response;
    }
}
