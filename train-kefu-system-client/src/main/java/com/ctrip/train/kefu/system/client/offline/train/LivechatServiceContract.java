package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.innovationwork.customerservice.livechatservice.v1.LivechatServiceClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;
import org.tempuri.livechatservice.GetChatListRequestType;
import org.tempuri.livechatservice.GetChatListResponseType;

@Component
public class LivechatServiceContract {

    /**
     *获取在线咨询次数
     **/
    public GetChatListResponseType getChatList(String orderId) {
        GetChatListResponseType response = new GetChatListResponseType();
        try {
            GetChatListRequestType request = new GetChatListRequestType();
            LivechatServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = LivechatServiceClient.getInstance("http://ws.kefucore.ctripcorp.com/chatdata");
            } else {
                client = LivechatServiceClient.getInstance();
            }
            request.setOrderID(orderId);
            request.setBusinessType("zhixingflight");   //旧版offline这样传的。。。
            response = client.getChatList(request);
        } catch (Exception ex) {
            CLogger.error("getChatList", ex);
        }
        return response;
    }
}
