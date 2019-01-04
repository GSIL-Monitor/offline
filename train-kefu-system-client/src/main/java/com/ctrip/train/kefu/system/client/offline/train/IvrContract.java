package com.ctrip.train.kefu.system.client.offline.train;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.framework.soa.chatofflineservice.v1.GetRuleLevelManageRequestType;
import com.ctrip.soa.framework.soa.chatofflineservice.v1.GetRuleLevelManageResponseType;
import com.ctrip.soa.framework.soa.recommendservice.v1.*;
import com.ctrip.soa.train.trainchatserver.v1.TrainChatServerClient;
import com.ctrip.train.faq.contract.*;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import common.util.HttpUtils;
import org.springframework.stereotype.Component;

import static qunar.util.RequestUtil.mapper;

@Component
public class IvrContract {

    public final String strTrainFaqServiceClientUrl = "http://10.25.162.107:8080/api/";

    /**
     * 获取用户分层
     **/
    public GetRuleLevelManageResponseType getRuleLevelManage(GetRuleLevelManageRequestType request) {
        GetRuleLevelManageResponseType response = new GetRuleLevelManageResponseType();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainChatServerClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainChatServerClient.getInstance("http://ws.train.ctripcorp.com/TrainChat/api/");
            } else {
                client = TrainChatServerClient.getInstance();
            }
            response = client.getRuleLevelManage(request);
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        CLogger.info("获取用户分层", strLog);
        return response;
    }

    /**
     * 获取电话自助问题
     **/
    public GetQuestionListResponse getQuestionList(GetQuestionListRequest request) {
        GetQuestionListResponse response = new GetQuestionListResponse();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainFaqServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + strTrainFaqServiceClientUrl + "getQuestionList" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, GetQuestionListResponse.class);
            } else {
                client = TrainFaqServiceClient.getInstance();
                response = client.getQuestionList(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        CLogger.info("获取电话自助问题", strLog);
        return response;
    }

    /**
     * 获取电话自助答案
     **/
    public GetAnswerResponse getAnswer(GetAnswerRequest request) {
        GetAnswerResponse response = new GetAnswerResponse();
        String strLog = "";
        try {
            strLog += ".........请求报文_" + mapper.writeValueAsString(request);
            TrainFaqServiceClient client;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + strTrainFaqServiceClientUrl + "getAnswer" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, GetAnswerResponse.class);
            } else {
                client = TrainFaqServiceClient.getInstance();
                response = client.getAnswer(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        CLogger.info("获取电话自助答案", strLog);
        return response;
    }

    /**
     * 获取可二次推荐详情
     **/
    public IsCanInsertRecommendResponseType isCanInsertRecommend(String orderId) {
        IsCanInsertRecommendResponseType response = new IsCanInsertRecommendResponseType();
        try {
            TrainChatServerClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainChatServerClient.getInstance("http://ws.train.ctripcorp.com/TrainChat/api/");
            } else {
                client = TrainChatServerClient.getInstance();
            }
            IsCanInsertRecommendRequestType request = new IsCanInsertRecommendRequestType();
            request.setOrderId(orderId);
            request.setCallingParty(CallingPartyEnum.Offline);
            response = client.isCanInsertRecommend(request);
        } catch (Exception ex) {
            CLogger.error("isCanInsertRecommend",ex);
        }
        CLogger.info("获取用户分层", mapper.writeValueAsString(response));
        return response;
    }

    /**
     * 取消二推
     **/
    public RecommendCancelResponseType recommendCancel(String orderId,String Operator) {
        RecommendCancelResponseType response = new RecommendCancelResponseType();
        try {
            TrainChatServerClient client;
            if (Foundation.server().getEnv().isFAT()) {
                client = TrainChatServerClient.getInstance("http://ws.train.ctripcorp.com/TrainChat/api/");
            } else {
                client = TrainChatServerClient.getInstance();
            }
            RecommendCancelRequestType request = new RecommendCancelRequestType();
            request.setOperatorName(Operator);
            request.setOrderNumber(orderId);
            request.setRecommendType(0);
            request.setSourceName("客服取消");
            request.setOpSource((short) 1);
            request.setOpReason((short) 10);
            response = client.recommendCancel(request);
        } catch (Exception ex) {
            CLogger.error("recommendCancel",ex);
        }
        CLogger.info("取消二推", mapper.writeValueAsString(response));
        return response;
    }
}
