package com.ctrip.train.kefu.system.client.offline.flight;


import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.OfflineUpdateContactInfoRequestType;
import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.OfflineUpdateContactInfoResponseType;
import com.ctrip.soa.train.tieyoubookingservice.v1.GetOfflineOrderDetailInnerV2Request;
import com.ctrip.soa.train.tieyoubookingservice.v1.GetOfflineOrderDetailInnerV2Response;
import com.ctrip.soa.train.tieyoubookingservice.v1.TieyouFlightBookingServiceClient;
import com.ctrip.soa.train.tieyouflightvendor.v1.TieyouFlightVendorServiceClient;
import common.log.CLogger;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class FlightOrderContract {

    /**
     * offline机票订单详情接口
     **/
    public GetOfflineOrderDetailInnerV2Response getOrderDedetail(String orderid)  {

        TieyouFlightBookingServiceClient client;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightBookingServiceClient.getInstance("http://ws.tieyou.flightbooking.train.ctripcorp.com/api/");
        } else {
            client = TieyouFlightBookingServiceClient.getInstance();
        }

        GetOfflineOrderDetailInnerV2Request request = new GetOfflineOrderDetailInnerV2Request();
        request.setOrderNumber(orderid);
        try {
            return client.getOfflineOrderDetailInnerV2(request);
        }
        catch (Exception ex){
            CLogger.error("getOrderDedetail",ex);
        }

        return null;
    }


    /**
     * offline 机票修改手机号接口
     */
    public Pair<Boolean,String> updateFlightPhone(String phone, String orderId, String eid) {
        TieyouFlightVendorServiceClient client = TieyouFlightVendorServiceClient.getInstance();
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        }

        Pair<Boolean, String> pair;
        pair = new Pair<>(false, "");

        OfflineUpdateContactInfoRequestType request = new OfflineUpdateContactInfoRequestType();
        request.setContactPhone(phone);
        request.setOrderNumber(orderId);
        request.setOperatorName(eid);
        try {
            OfflineUpdateContactInfoResponseType response = client.offlineUpdateContactInfo(request);
            if (response != null) {
                if (response.getResultCode() == 1) {
                    pair = new Pair<>(true, response.getResultMessage());
                } else {
                    pair = new Pair<>(false, response.getResultMessage());
                }
            }
        }
        catch (Exception ex){
            CLogger.error("updateFlightPhone",ex);
        }

        return pair;
    }
}
