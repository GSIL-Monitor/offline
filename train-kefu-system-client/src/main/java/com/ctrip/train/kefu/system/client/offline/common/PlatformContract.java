package com.ctrip.train.kefu.system.client.offline.common;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.platform.accountsecurityservice.integrationgrade.v1.GetCurrentLevelRequest;
import com.ctrip.soa.platform.accountsecurityservice.integrationgrade.v1.GetCurrentLevelResponse;
import com.ctrip.soa.platform.accountsecurityservice.integrationgrade.v1.IntegrationMemberGradeWSClient;
import common.log.CLogger;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 研发中心公共接口
 */
@Component
public class PlatformContract {

    /**
     *获取用户等级
     * 接口提供的是List的,我们应该每次都传一个的。。。
     **/
    public GetCurrentLevelResponse getCurrentLevel(List<String> uidList)
    {
        GetCurrentLevelResponse response = new GetCurrentLevelResponse();
        try
        {
            GetCurrentLevelRequest request = new GetCurrentLevelRequest();
            request.setUiDs(uidList);
            IntegrationMemberGradeWSClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = IntegrationMemberGradeWSClient.getInstance("http://10.2.49.71/integration-membergradews/api/");
            } else {
                client = IntegrationMemberGradeWSClient.getInstance();
            }
             response = client.getCurrentLevel(request);
        }
        catch (Exception ex)
        {
            CLogger.error("refundAppendProdunt", ex);
        }
        return response;
    }

}
