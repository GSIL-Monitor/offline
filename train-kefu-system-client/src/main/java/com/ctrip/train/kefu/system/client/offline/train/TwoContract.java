package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.kefu.system.client.pojo.train.TwoContractCheckPojo;
import com.ctrip.train.kefu.system.client.pojo.train.TwoContractPojo;
import com.ctrip.train.ticketagent.service.client.*;
import common.log.CLogger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TwoContract {
    /**
     * 有票更改订单
     * @param
     * @return
     */
    public UpdateDeliveryOrderForSecPushResponse twoPushUpdateOrder(TwoContractPojo tcp)   {
        UpdateDeliveryOrderForSecPushResponse response =new UpdateDeliveryOrderForSecPushResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketagentServiceClient.getInstance("http://10.28.153.180:8080/api/");
            }
            else {
                client = TicketagentServiceClient.getInstance();
            }
            UpdateDeliveryOrderForSecPushRequest request= new UpdateDeliveryOrderForSecPushRequest();
            request.setPartnerOrderId(tcp.getPartnerOrderId());
            List<Map<String,String>> ticket = tcp.getTicketInfo();
            if(ticket.size()>0){
                List<UpdateDeliveryTicketInfo> updateDeliveryTicketInfo=new ArrayList<>();
                for(Map map : ticket){
                    UpdateDeliveryTicketInfo temp=new UpdateDeliveryTicketInfo();
                    temp.setTicketSeat(String.valueOf(map.get("ticketSeat")));
                    temp.setTicketTime(String.valueOf(map.get("ticketTime")));
                    temp.setTicketDate(String.valueOf(map.get("ticketDate")));
                    temp.setAcceptSeat(String.valueOf(map.get("acceptSeat")));
                    updateDeliveryTicketInfo.add(temp);
                }
                request.setUpdateDeliveryTicketInfo(updateDeliveryTicketInfo);
            }
            CLogger.info("请求报文：",request.toString());
            response= client.updateDeliveryOrderForSecPush(request);
        } catch (Exception e) {
            CLogger.error("getStopStations",e);
        }
        return response;
    }

    /**
     * 无票更改订单状态
     * @param
     * @return
     * @throws Exception
     */
    public SetNoTicketResponse twoPushNoTicket(String orderId,String cancelReason)   {
        SetNoTicketResponse response=new SetNoTicketResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketagentServiceClient.getInstance("http://10.28.151.136:8080/api/");
            } else {
                client = TicketagentServiceClient.getInstance();
            }
            SetNoTicketRequest setNoTicketRequest =new SetNoTicketRequest();
            setNoTicketRequest.setPartnerOrderId(orderId);
            setNoTicketRequest.setNoTicketReason(cancelReason);
            response=client.setNoTicket(setNoTicketRequest);
        } catch (Exception e) {
            CLogger.error("twoPushNoTicket",e);
        }
        return response;
    }
    /**
     *
     * 查询代售点车票信息
     *
     */
    public GetCheckResultResponse getCheckResult(TwoContractCheckPojo tccp) {
        GetCheckResultResponse response = new GetCheckResultResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                 client = TicketagentServiceClient.getInstance("http://10.28.151.136:8080/api/");
            } else {
                client = TicketagentServiceClient.getInstance();
            }
            GetCheckResultRequest gcrr =new GetCheckResultRequest();
            gcrr.setPartnerOrderId(tccp.getPartnerOrderId());
            gcrr.setCheckId(tccp.getCheckId());
            response =client.getCheckResult(gcrr);
        } catch (Exception ex) {
            CLogger.error("订单详情接口getCheckResult",ex);
        }
        return response;
    }
    /**
     *
     * 增加一次实时查询
     */
    public AddcheckResultResponse addcheckResult(TwoContractCheckPojo tccp)  {
        AddcheckResultResponse response =new AddcheckResultResponse();
        try {
            TicketagentServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TicketagentServiceClient.getInstance("http://10.28.151.136:8080/api/");
            } else {
                client = TicketagentServiceClient.getInstance();
            }
            AddcheckResultRequest acrr =new AddcheckResultRequest();
            acrr.setPartnerOrderId(tccp.getPartnerOrderId());
            acrr.setTicketTime(tccp.getTicketTime());
            acrr.setTicketDate(tccp.getTicketDate());
            acrr.setTrainNo(tccp.getTrainNo());
            acrr.setSeatName(tccp.getSeatName());//硬软卧 动卧
            acrr.setStartStation(tccp.getStartStation());
            acrr.setEndStation(tccp.getEndStation());
            acrr.setExceLevel(tccp.getExceLevel());

            response= client.addcheckResult(acrr);
        } catch (Exception e) {
            CLogger.error("订单详情接口addcheckResult",e);
        }
        return response;
    }

}

