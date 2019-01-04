package com.ctrip.train.kefu.system.client.offline.train;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.*;
import com.ctrip.train.kefu.system.client.pojo.train.StationContractPojo;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.DateUtils;
import common.util.MD5Utils;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class StationContract {
    private final String md5Uid = "ctrip.dreamworks";
    private final String md5key = "151e79da7961dc2813748528ef7069b8";

    /**
     *
     * StationContractPojo
     *  查询火车站经停站信息
     **/
    public SearchS2SResponseType getStopStations(StationContractPojo scp){
        SearchS2SResponseType response = new SearchS2SResponseType();
        try {
            PhenixDataApiServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = PhenixDataApiServiceClient.getInstance("http://ws.phenix.dataapi.train.ctripcorp.com/phenix-dataapi-service/api");
            } else {
                client = PhenixDataApiServiceClient.getInstance();
            }
            SearchS2SRequestType requestType = new SearchS2SRequestType();
            requestType.setDepartDate(scp.getDepartDate());
            requestType.setTrainNo(scp.getTrainNo());
            requestType.setTo(scp.getToCity());
            requestType.setFrom(scp.getFromCity());
            String timeStamp= String.valueOf(System.currentTimeMillis() / 1000);
            requestType.setTimeStamp(timeStamp);
            requestType.setUser(md5Uid);
            requestType.setSign(MD5Utils.getMD5(timeStamp+md5key));

            response = client.searchS2S(requestType);
            //CLogger.info("getStopStations");
        }catch(Exception ex){
            CLogger.error("getStopStations",ex);
        }
        return response;
    }

    /**
     *
     * StationContractPojo2
     *  查询火车站经停站信息
     **/
    public SearchS2SResponseType getStopStations2(StationContractPojo scp){
        SearchS2SResponseType response = new SearchS2SResponseType();
        try {
            PhenixDataApiServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = PhenixDataApiServiceClient.getInstance("http://ws.phenix.dataapi.train.ctripcorp.com/phenix-dataapi-service/api");
            } else {
                client = PhenixDataApiServiceClient.getInstance();
            }
            SearchS2SRequestType requestType = new SearchS2SRequestType();
            requestType.setDepartDate(scp.getDepartDate());
            requestType.setTrainNo(scp.getTrainNo());
            requestType.setTo(scp.getToCity());
            requestType.setFrom(scp.getFromCity());
            requestType.setTimeStamp(scp.getTimeStamp());
            requestType.setUser(scp.getUser());
            requestType.setSign(scp.getSign());

            response = client.searchS2S(requestType);
            //CLogger.info("getStopStations");
        }catch(Exception ex){
            CLogger.error("getStopStations",ex);
        }
        return response;
    }

    /**
     * 查询车站地址信息
     * @return
     */
    public GetAllStationsResponseType getAllStations(){
        GetAllStationsResponseType response = new GetAllStationsResponseType();
        try {
            PhenixDataApiServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = PhenixDataApiServiceClient.getInstance("http://ws.phenix.dataapi.train.ctripcorp.com/phenix-dataapi-service/api");
            } else {
                client = PhenixDataApiServiceClient.getInstance();
            }
            GetAllStationsRequestType requestType =new GetAllStationsRequestType();
            String timeStamp= String.valueOf(System.currentTimeMillis() / 1000);
            requestType.setUser(md5Uid);
            requestType.setSign(MD5Utils.getMD5(timeStamp+md5key));
            requestType.setTimeStamp(timeStamp);
            response = client.getAllStations(requestType);
        }catch(Exception ex){
            CLogger.error("getStopStations",ex);
        }
        return response;
    }

    /**
     * 查经停
     * @param trainNo
     * @param departDate
     * @return
     */
    public GetStopStationsResponseType getStopStations(String trainNo,String departDate){
        GetStopStationsResponseType response = new GetStopStationsResponseType();
        try {
            PhenixDataApiServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = PhenixDataApiServiceClient.getInstance("http://ws.phenix.dataapi.train.ctripcorp.com/phenix-dataapi-service/api");
            } else {
                client = PhenixDataApiServiceClient.getInstance();
            }
            GetStopStationsRequestType requestType=new GetStopStationsRequestType();
            requestType.setTrainNo(trainNo);
            requestType.setDepartDate(DateUtils.toCalendar(DateUtils.parseDate(departDate,DateUtils.YMD_UNDERLINED)));
            String timeStamp= String.valueOf(System.currentTimeMillis() / 1000);
            requestType.setUser(md5Uid);
            requestType.setSign(MD5Utils.getMD5(timeStamp+md5key));
            requestType.setTimeStamp(timeStamp);
            response = client.getStopStations(requestType);
        }catch(Exception ex){
            CLogger.error("getStopStations",ex);
        }
        return response;
    }

}
