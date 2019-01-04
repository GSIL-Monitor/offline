package com.ctrip.train.kefu.system.client.offline.train;


import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.payment.soa.cwallet.account.server.AccountSoa2ServerClient;
import com.ctrip.payment.soa.cwallet.account.server.model.IsUserAuthedReq;
import com.ctrip.payment.soa.cwallet.account.server.model.IsUserAuthedRsp;
import com.ctrip.sp.emergency.contract.EmergencyServiceClient;
import com.ctrip.sp.emergency.contract.GetFamilyNumberByPhoneRequestType;
import com.ctrip.sp.emergency.contract.GetFamilyNumberByPhoneResponseType;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 * Created by jian_ji on 2018/7/2.
 */
@Component
public class AccountSoa2ServerContract {

    public IsUserAuthedRsp isCtripUserAuthed(String merchantUid, String merchantId){
        IsUserAuthedRsp response = new IsUserAuthedRsp();
        try {
            AccountSoa2ServerClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = AccountSoa2ServerClient.getInstance();
            } else {
                client = AccountSoa2ServerClient.getInstance();
            }
            IsUserAuthedReq request = new IsUserAuthedReq();
            request.setMerchantId(merchantId);
            request.setMerchantUid(merchantUid);
            CLogger.info("isCtripUserAuthed", JSON.toJSONString(request));
            response = client.isUserAuthed(request);
        }catch(Exception ex){
            CLogger.error("getFamilyNumberByPhone",ex);
        }
        CLogger.info("isCtripUserAuthed", JSON.toJSONString(response));
        return response;
    }
}
