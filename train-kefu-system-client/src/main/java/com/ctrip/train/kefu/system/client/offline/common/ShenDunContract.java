package com.ctrip.train.kefu.system.client.offline.common;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.innovationwork.customerservice.livechatservice.v1.LivechatServiceClient;
import common.log.CLogger;
import common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.tempuri.livechatservice.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShenDunContract {

    /**
     *offline订单详情接口
     **/
    public String singleEncrypt(SingleEncryptRequestType request) {
        LivechatServiceClient client;
        if (Foundation.server().getEnv().isFAT()) {
            client = LivechatServiceClient.getInstance("http://ws.kefucore.ctripcorp.com/chatdata");
        } else {
            client = LivechatServiceClient.getInstance();
        }
        try {
            SingleEncryptResponseType response = client.singleEncrypt(request);
            if (response.getState()) {
                return response.getData();
            }
        } catch (Exception ex) {
            CLogger.error("encrypt", ex);
        }
        return request.getStrNeedEncrypt();
    }

    /// <summary>
    /// 单个解密
    /// </summary>
    public String singleDecrypt(SingleDecryptRequestType request) {
        LivechatServiceClient client;
        if (Foundation.server().getEnv().isFAT()) {
            client = LivechatServiceClient.getInstance("http://ws.kefucore.ctripcorp.com/chatdata");
        } else {
            client = LivechatServiceClient.getInstance();
        }
        try {
            SingleDecryptResponseType response = client.singleDecrypt(request);
            if (response.getState()) {
                return response.getData();
            }
        } catch (Exception ex) {
            CLogger.error("decrypt", ex);

        }
        return request.getStrNeedEncrypt();
    }



    /// <summary>
    /// 根据手机号获取加密解密手机号信息
    /// </summary>
    /// <returns></returns>
    public Map<String, String> getShenDunMobile(String mobile) {
        Map<String, String> dic = new HashMap<>();
        dic.put("EncryptMobile", mobile);//加密后的手机号
        dic.put("DecryptMobile", mobile);//解密后的手机号

        try {
            if (StringUtils.isNotEmpty(mobile)) {
                SingleEncryptRequestType sEncryptRequest = new SingleEncryptRequestType();
                sEncryptRequest.setKeyType(CoreInfoKeyType.Phone);
                sEncryptRequest.setStrNeedEncrypt(mobile);
                SingleDecryptRequestType sDecryptRequest = new SingleDecryptRequestType();
                sDecryptRequest.setKeyType(CoreInfoKeyType.Phone);
                sDecryptRequest.setStrNeedEncrypt(dic.get("EncryptMobile"));
                sDecryptRequest.setEid("GetShenDunMobile");
                dic.put("EncryptMobile", singleEncrypt(sEncryptRequest));
                dic.put("DecryptMobile", singleDecrypt(sDecryptRequest));
            }
        } catch (Exception ex) {
            CLogger.info("神盾GetShenDunMobileEx", ex);
        }
        return dic;
    }

    public String singleEncrypt(CoreInfoKeyType CoreInfoKeyType, String strNeedEncrypt) {
        SingleEncryptRequestType request = new SingleEncryptRequestType();
        request.setKeyType(CoreInfoKeyType);
        request.setStrNeedEncrypt(strNeedEncrypt);
        return singleEncrypt(request);
    }
}
