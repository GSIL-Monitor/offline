package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.ticketaccount.service.client.contract.SearchAccountInfoRequestType;
import com.ctrip.train.ticketaccount.service.client.contract.SearchAccountInfoResponseType;
import com.ctrip.train.ticketaccount.service.client.contract.TicketAccountJavaServiceClient;
import com.ctrip.train.ticketagent.service.client.*;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * 帐号相关信息
 */
@Component
public class TicketaccountContract {

    /**
     * 获取12306帐号信息
     **/
    public SearchAccountInfoResponseType searchAccountInfo(String userName,String requestUser) {
        SearchAccountInfoResponseType response = new SearchAccountInfoResponseType();
        try {
            TicketAccountJavaServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketAccountJavaServiceClient.getInstance("http://10.28.103.33:8080/api/");
            } else {
                client = TicketAccountJavaServiceClient.getInstance();
            }
            SearchAccountInfoRequestType requestType = new SearchAccountInfoRequestType();
            requestType.setUserName(userName);
            requestType.setRequestUser(requestUser);
            response = client.searchAccountInfo(requestType);
        } catch (Exception ex) {
            CLogger.error("TicketaccountContract.searchAccountInfo", ex);
        }
        return response;
    }

    /**
     * 配送票取消订单
     * @param partnerOrderId
     * @return
     */
    public CancelOrderResponse cancelOrder(String partnerOrderId){
        CancelOrderResponse response = new CancelOrderResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketagentServiceClient.getInstance("http://10.5.76.219:8080/api");
                partnerOrderId ="7666476900";
            } else {
                client = TicketagentServiceClient.getInstance();
            }
            CancelOrderRequest request = new CancelOrderRequest();
            request.setCancelOrderType(CancelOrderType.HasPulledCancel);
            request.setOrderNumber(partnerOrderId);
            response = client.cancelOrder(request);
        } catch (Exception ex) {
            CLogger.error("TicketaccountContract.CancelOrder", ex);
        }
        return response;
    }

}
