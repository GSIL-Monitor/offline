package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.tyusercore.service.client.GetUserVipDetailInfoForOfflineRequestType;
import com.ctrip.train.tyusercore.service.client.GetUserVipDetailInfoForOfflineResponseType;
import com.ctrip.train.tyusercore.service.client.TyUserCoreServiceClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * 智行铁友接口
 */
@Component
public class TyUserCoreServiceContract {

    /**
     * 判断智行会员信息
     **/
    public GetUserVipDetailInfoForOfflineResponseType getUserVipDetailInfoForOffline(String mobile, String orderNumber, Integer tyUserId, String PartnerName) {
        GetUserVipDetailInfoForOfflineResponseType response = new GetUserVipDetailInfoForOfflineResponseType();
        try {
            TyUserCoreServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TyUserCoreServiceClient.getInstance("http://10.28.100.138:8080/api/");
            } else {
                client = TyUserCoreServiceClient.getInstance();
            }
            client.setFormat("json");
            GetUserVipDetailInfoForOfflineRequestType requestType = new GetUserVipDetailInfoForOfflineRequestType();
            requestType.setMobile(mobile);
            requestType.setOrderNumber(orderNumber);
            requestType.setTyUserId(tyUserId);
            requestType.setPartnerName(PartnerName);
            response = client.getUserVipDetailInfoForOffline(requestType);
        } catch (Exception ex) {
            CLogger.error("订单详情接口getUserVipDetailInfoForOffline", ex);
        }
        return response;
    }
}
