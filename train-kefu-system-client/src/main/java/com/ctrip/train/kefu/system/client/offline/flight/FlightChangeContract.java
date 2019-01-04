package com.ctrip.train.kefu.system.client.offline.flight;


import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.framework.soa.tieyouflightvendor.v1.*;
import com.ctrip.soa.train.tieyouflightvendor.v1.TieyouFlightVendorServiceClient;
import com.ctrip.train.kefu.system.client.pojo.flight.FlightChangeDetailRequest;
import com.ctriposs.baiji.specific.SpecificRecord;
import common.log.CLogger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FlightChangeContract {
    /**
     * 改签条件查询
     *
     * @param request
     * @return
     */
    public FlightRebookConditionResponseType getFlightChangeDetail(FlightChangeDetailRequest request){
        TieyouFlightVendorServiceClient client;
        FlightRebookConditionResponseType response;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        } else {
            client = TieyouFlightVendorServiceClient.getInstance();
        }
        FlightRebookConditionRequestType requestType = new FlightRebookConditionRequestType();
        Data6 data6 = new Data6();
        data6.setCtripUId(request.getCtripUId());
        data6.setOperateType(request.getOperateType());
        data6.setOrderNumber(request.getOrderNumber());
        data6.setTyUserId(request.getTyUserId());
        requestType.setData6(data6);
        try {
            response = client.flightRebookCondition(requestType);
            CLogger.info("flightRebookCondition",JSON.toJSONString(response),new HashMap<String,String >(){{put("OrderNumber",request.getOrderNumber());}});
            return response;
        } catch (Exception e) {
            CLogger.error("flightRebookCondition", e);
            return null;
        }
    }

    /**
     * 改签航班列表
     * GetRebookFlightListRequest
     */
    public GetRebookFlightListResponseType getRebookFlightList(GetRebookFlightListRequestType request) {
        TieyouFlightVendorServiceClient client;
        GetRebookFlightListResponseType response;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        } else {
            client = TieyouFlightVendorServiceClient.getInstance();
        }
        try {
            client.setSocketTimeout(10000);
            response = client.getRebookFlightList(request);
            setLog("getRebookFlightList",request,response);
            return response;
        } catch (Exception e) {
            CLogger.error("getRebookFlightList", e);
            return null;
        }
    }

    /**
     * 提交改签接口
     */
    public RebookResponseType pushFlightChange(RebookRequestType request) {
        TieyouFlightVendorServiceClient client;
        RebookResponseType response;
        if (Foundation.server().getEnv().isFAT()) {
            client = TieyouFlightVendorServiceClient.getInstance("http://10.5.2.30:8080/api/");
        } else {
            client = TieyouFlightVendorServiceClient.getInstance();
        }
        try {
            client.setConnectTimeout(10000);
            response = client.rebook(request);
            setLog("pushFlightChange",request,response);
            return response;
        } catch (Exception e) {
            CLogger.error("pushFlightChange", e);
            return null;
        }
    }

    private void setLog(String logname, SpecificRecord request, SpecificRecord response) {
        try {
            Map<String, String> mapLog = new HashMap<String, String>();
            mapLog.put("request", JSON.toJSONString(request));
            mapLog.put("response", JSON.toJSONString(response));
            CLogger.info(logname, logname, mapLog);
        } catch (Exception e) {
            CLogger.error("FlightChangeContract.setLog失败", e);
        }

    }
}
