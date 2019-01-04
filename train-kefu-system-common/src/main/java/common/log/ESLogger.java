package common.log;

import com.ctrip.soa.common.types.v1.ResponseStatusType;
import com.ctriposs.baiji.rpc.server.HttpRequestContext;
import com.dianping.cat.Cat;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.ctriposs.baiji.rpc.common.util.LegacyResponseStatusUtil.getResponseStatus;

public class ESLogger {
    public static void  log(String method,long milliseconds, Object request, Object response){
        try {
            Map<String,String> indexTags=new HashMap<>();
            indexTags.put("clientAppID", getClientAppId());
            indexTags.put("requestType", method);
            indexTags.put("duration", String.valueOf(milliseconds));
            indexTags.put("requestTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
            indexTags.put("clientIp",getClientIp());

            String errorCode ="";
            String ack ="Success";
            ResponseStatusType responseStatus = getResponseStatus(response);
            if(responseStatus !=null) {
                ack = String.valueOf(responseStatus.getAck());
            }
            indexTags.put("ack", ack);
            indexTags.put("errorCode", errorCode);
            Gson gson =new Gson();
            Map<String, String> storeTags =new HashMap<>();
            storeTags.put("request", gson.toJson(request));
            storeTags.put("response", gson.toJson(response));
            Cat.logTags("train-offline-client", indexTags, storeTags);
        }
        catch ( Exception ex){
            CLogger.error("ESLogger",ex.getMessage());
        }

    }

    /**
     * 获取APPID
     */
    private static String getClientAppId() {
        HttpRequestContext httpRequestContext = HttpRequestContext.getInstance();
        return httpRequestContext.request().clientAppId();
    }

    /**
     * 获取IP
     */
    private static String getClientIp() {
        HttpRequestContext httpRequestContext = HttpRequestContext.getInstance();
        return httpRequestContext.request().clientIp();
    }
}
