package com.ctrip.train.kefu.system.client.offline.flight;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.*;
import com.ctrip.soa.train.tieyouflightvendor.v1.TieyouFlightVendorServiceClient;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightRefundDetailRequset;
import common.log.CLogger;
import org.springframework.stereotype.Component;

@Component
public class FlightRefundContract {

    /**
     * 获取机票退票详情
     */
    public FlightRefundConditionResponseType getFlightRefundDetail(FlightRefundDetailRequset flight) {

        TieyouFlightVendorServiceClient client;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        } else {
            client = TieyouFlightVendorServiceClient.getInstance();
        }

        FlightRefundConditionRequestType request = new FlightRefundConditionRequestType();

        Data data = new Data();
        data.setOrderNumber(flight.getOrderid());
        data.setCtripUId(flight.getCtripUid());
        data.setOperatorName(flight.getOperatorName());
        data.setTyUserId(flight.getTyUerid());
        request.setData(data);

        try {
            client.setSocketTimeout(5000);//机票同事要求设置5秒
            return client.flightRefundCondition(request);
        }
        catch (Exception ex){
            CLogger.error("flightRefundCondition",ex);
        }

        return  null;
    }

    /**
     * 机票退票接口
     */

    public ReturnTicketResponseType sendFlightRefund(ReturnTicketRequestType request)  {

        TieyouFlightVendorServiceClient client;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        } else {

            client = TieyouFlightVendorServiceClient.getInstance();
        }
        try {
            client.setConnectTimeout(5000);//机票同事要求设置5秒
            client.setSocketTimeout(10000);
            return client.returnTicket(request);
        }
        catch (Exception ex){
            CLogger.error("sendFlightRefund",ex);
        }

        return  null;

    }
}
