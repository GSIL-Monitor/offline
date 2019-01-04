package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.ticketagent.service.client.CounterTicketRequest;
import com.ctrip.train.ticketagent.service.client.CounterTicketResponse;
import com.ctrip.train.ticketagent.service.client.TicketagentServiceClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * 代售点
 */
@Component
public class TrainTicketAgentServiceContract {

    /**
     * 判断是否柜台票
     **/
    public CounterTicketResponse getIscounterTicket(String partnerOrderId) {
        CounterTicketResponse response = new CounterTicketResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketagentServiceClient.getInstance("http://10.28.153.180:8080/api/");
            } else {
                client = TicketagentServiceClient.getInstance();
            }
            CounterTicketRequest requestType = new CounterTicketRequest();
            requestType.setPartnerOrderId(partnerOrderId);
            response = client.counterTicket(requestType);
        } catch (Exception ex) {
            CLogger.error("TrainTicketAgentServiceContract.getIscounterTicket", ex);
        }
        return response;
    }
}
