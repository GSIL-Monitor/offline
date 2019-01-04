package com.ctrip.train.kefu.system.client.offline.train;


import com.ctrip.framework.foundation.Foundation;
import com.ctrip.market.membercard.*;
import com.ctrip.market.soa.rightsInterestService.RightsInterestServiceClient;
import com.ctrip.market.soa.rightsInterestService.SelectUserRightsInterestRequestType;
import com.ctrip.market.soa.rightsInterestService.SelectUserRightsInterestResponseType;
import com.ctrip.market.soa.rightsInterestService.UserInfo;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Component
public class MemberCardContract {

    public MemberCardGetUserRightsResponseType memberCardGetUserRights(String uid,String cardcode){
        MemberCardGetUserRightsResponseType doc = new MemberCardGetUserRightsResponseType();
        try {
            MembercardClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = MembercardClient.getInstance("http://10.25.115.133:8080/api");
            } else {
                client = MembercardClient.getInstance();
            }
            MemberCardGetUserRightsRequestType request = new MemberCardGetUserRightsRequestType();
            request.setUserID(uid);
            request.setCardCode(cardcode);
            doc = client.memberCardGetUserRights(request);
        }catch(Exception ex){
            CLogger.error("memberCardGetUserRights",ex);
        }
        return doc;
    }

    public SelectUserRightsInterestResponseType selectUserRightsInterest(String uid){
        SelectUserRightsInterestResponseType response = new SelectUserRightsInterestResponseType();
        try {
            RightsInterestServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = RightsInterestServiceClient.getInstance("http://10.15.191.227:8080/api");
            } else {
                client = RightsInterestServiceClient.getInstance();
            }
            SelectUserRightsInterestRequestType request = new SelectUserRightsInterestRequestType();
            UserInfo userInfo = new UserInfo();
            userInfo.setUID(uid);
            userInfo.setDid("");
            userInfo.setClientIP("");
            userInfo.setClientID("");
            request.setUserInfo(userInfo);
            request.setPlatform("app");
            request.setRightsInterestID(0L);
            //request.setProductGroupID(678L);
            request.setProductLineID(17);
            response = client.selectUserRightsInterest(request);
        }catch(Exception ex){
            CLogger.error("selectUserRightsInterest",ex);
        }
        return response;
    }
}
