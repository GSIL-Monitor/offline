package com.ctrip.train.kefu.system.client.offline.train;


import com.ctrip.framework.foundation.Foundation;
import com.ctrip.market.membercard.MemberCardGetUserRightsResponseType;
import com.ctrip.sp.emergency.contract.EmergencyServiceClient;
import com.ctrip.sp.emergency.contract.GetFamilyNumberByPhoneRequestType;
import com.ctrip.sp.emergency.contract.GetFamilyNumberByPhoneResponseType;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Component
public class EmergencyServiceContract {

    public GetFamilyNumberByPhoneResponseType getFamilyNumberByPhone(String phone){
        GetFamilyNumberByPhoneResponseType response = new GetFamilyNumberByPhoneResponseType();
        try {
            EmergencyServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = EmergencyServiceClient.getInstance("http://10.25.110.205:8080/api");
            } else {
                client = EmergencyServiceClient.getInstance();
            }
            GetFamilyNumberByPhoneRequestType request = new GetFamilyNumberByPhoneRequestType();
            request.setPhone(phone);
            response = client.getFamilyNumberByPhone(request);
        }catch(Exception ex){
            CLogger.error("getFamilyNumberByPhone",ex);
        }
        return response;
    }
}
