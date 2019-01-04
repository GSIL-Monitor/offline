package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.train.product.contract.product.contract.*;
import common.log.CLogger;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class TrainProductServiceContract {

    /**
     *
     **/
    public GetTrainXProductInfoResponseType getTrainXProductInfo( Integer productID) {
        GetTrainXProductInfoResponseType response = new GetTrainXProductInfoResponseType();
        try {
            TrainProductJavaServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainProductJavaServiceClient.getInstance("http://10.28.101.29:8080/api/");
            } else {
                client = TrainProductJavaServiceClient.getInstance();
            }
            GetTrainXProductInfoRequestType requestType = new GetTrainXProductInfoRequestType();
            requestType.setProductID(productID);
            response = client.getTrainXProductInfo(requestType);
        } catch (Exception ex) {
            CLogger.error("getTrainXProductInfo", ex);
        }
        return response;
    }
    /**
     * 查看地址图片
     */
    public FastServicePointV2ResponseType getStationImageUrl( String departStationName,String DepartureDateTime) {
        FastServicePointV2ResponseType response = new FastServicePointV2ResponseType();
        try {
            TrainProductJavaServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainProductJavaServiceClient.getInstance("http://10.28.101.29:8080/api/");
            } else {
                client = TrainProductJavaServiceClient.getInstance();
            }
            FastServicePointV2RequestType request = new FastServicePointV2RequestType();
            request.setDepartureDateTime(DepartureDateTime);
            request.setDepartureStationName(departStationName);
//            request.setServiceType();
            response=client.fastServicePointV2(request);
        } catch (Exception ex) {
            CLogger.error("getTrainXProductInfo", ex);
        }
        return response;
    }
}
