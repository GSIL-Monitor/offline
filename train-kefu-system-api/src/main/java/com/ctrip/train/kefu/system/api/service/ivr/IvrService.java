package com.ctrip.train.kefu.system.api.service.ivr;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.framework.soa.chatofflineservice.v1.GetRuleLevelManageRequestType;
import com.ctrip.soa.framework.soa.chatofflineservice.v1.GetRuleLevelManageResponseType;
import com.ctrip.soa.train.trainordercentreservice.offline.v1.*;
import com.ctrip.soa.train.trainordercentreservice.v1.*;
import com.ctrip.train.faq.contract.GetAnswerRequest;
import com.ctrip.train.faq.contract.GetAnswerResponse;
import com.ctrip.train.faq.contract.GetQuestionListRequest;
import com.ctrip.train.faq.contract.GetQuestionListResponse;
import com.ctrip.train.kefu.system.api.contract.*;
import com.ctrip.train.kefu.system.api.dao.ivr.IvrConfigEx;
import com.ctrip.train.kefu.system.api.dao.ivr.IvrStaticsticsEx;
import com.ctrip.train.kefu.system.api.dao.order.ChatConfigEx;
import com.ctrip.train.kefu.system.api.service.ivr.enums.IvrEnum;
import com.ctrip.train.kefu.system.client.offline.common.ShenDunContract;
import com.ctrip.train.kefu.system.client.offline.train.IvrContract;
import com.ctrip.train.kefu.system.client.offline.train.OrderContract;
import common.log.CLogger;
import common.qconfig.QConfigHelper;
import common.util.DateUtils;
import common.util.HttpUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.entity.IvrStaticstics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.livechatservice.CoreInfoKeyType;
import org.tempuri.livechatservice.SingleEncryptRequestType;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static qunar.util.RequestUtil.mapper;

@Service
public class IvrService {

    @Autowired
    private IvrConfigEx ivrConfigEx;
    @Autowired
    private ChatConfigEx chatConfigEx;
    @Autowired
    private IvrStaticsticsEx ivrStaticsticsEx;
    @Autowired
    private ShenDunContract shenDunContract;
    @Autowired
    private OrderContract orderContract;
    @Autowired
    private IvrContract ivrContract;

    /**
     * 获取问题
     */
    public GetIVRPredictingUserBehaviorResponseType getQuestion(GetIVRPredictingUserBehaviorRequestType request) {
        GetIVRPredictingUserBehaviorResponseType response = new GetIVRPredictingUserBehaviorResponseType();
        response.setResultCode("Failure");
        response.setResultMessage("初始状态");
        String mobileNumber = "0";
        String partnerName = "Ctrip.Train";
        String orderNumber = "0";
        String userLevel = "0";
        String strCaseValue = null;//场景值
        int optag = IvrEnum.QueryNoOrder.getCode();
        Map<String, String> dic = new HashMap<>();
        String strLog = "获取问题调用开始";
        try {
            strLog += ".........参数_" + mapper.writeValueAsString(request);
            if (request != null && StringUtils.isNotEmpty(request.getOperType()) && request.getOperType().equals("getOrder")) {
                dic.put("ani", StringUtils.isEmpty(request.getAni()) ? "" : request.getAni());
                if (StringUtils.isNotEmpty(request.getMobileNumber())) {
                    mobileNumber = request.getMobileNumber().replaceAll("^(0+)", "");
                    SingleEncryptRequestType shenDunRequest = new SingleEncryptRequestType();
                    shenDunRequest.setStrNeedEncrypt(mobileNumber);
                    shenDunRequest.setKeyType(CoreInfoKeyType.Phone);
                    mobileNumber = shenDunContract.singleEncrypt(shenDunRequest);
                    if (StringUtils.isEmpty(mobileNumber)) {
                        mobileNumber = "0";
                    }
                }
                if (StringUtils.isNotEmpty(request.getPartnerName())) {
                    if (request.getPartnerName().trim().toLowerCase().equals("tieyou")) {
                        partnerName = "tieyou";
                    } else if (request.getPartnerName().trim().toLowerCase().equals("zhixing")) {
                        partnerName = "zhixing";
                    }
                }
                boolean isFirstRequest = ivrStaticsticsEx.isFirstRequest(request.getCallId());
                strLog += ".........是否首次交互_" + isFirstRequest;
                String strSmsContent = "";
                if (isFirstRequest) {
                    strSmsContent = ivrConfigEx.getSmsContentByPartnerName(partnerName.equals("Ctrip.Train") ? "ctrip" : partnerName);
                    if (strSmsContent == null) {
                        strSmsContent = "";
                    }
                }
                strLog += ".........紧急录音_" + strSmsContent;
                orderNumber = getOrderNumberByParam(mobileNumber, request.getOrderDateString(), partnerName);
                strLog += ".........获取订单号_" + orderNumber;
                List<BroadcastMedia> broadcastMediaList = new ArrayList<BroadcastMedia>();
                if (StringUtils.isNotEmpty(orderNumber)) {
                    optag = IvrEnum.HasOrder.getCode();
                    userLevel = getUserLevel(orderNumber, request.callId);
                    strLog += ".........获取用户分层_" + userLevel;
                    TransferInfo transferInfo = getTransferInfo(userLevel, request.getVersion());
                    String isTransfer = isTransfer(orderNumber);
                    strLog += ".........是否直接转人工_" + isTransfer;
                    if (StringUtils.isNotEmpty(isTransfer)) {
                        optag = Integer.parseInt(isTransfer);
                        BroadcastMedia broadcastMedia = new BroadcastMedia();
                        broadcastMedia.setOperTag(isTransfer);
                        broadcastMedia.setDTMF("1");
                        broadcastMedia.setOrderNo(1);
                        broadcastMedia.setTransferInfo(getTransferInfo(userLevel, request.getVersion()));
                        Media media = new Media();
                        media.setMediaType("TTS");
                        media.setText(strSmsContent + "。正在为您转人工服务, 请稍后");
                        broadcastMedia.setMedia(media);
                        broadcastMediaList.add(broadcastMedia);
                        response.setOperType("TransferWithMedia");
                    } else {
                        Map<String, String> questionDic = getQuestionList(orderNumber);
                        String strOrderDetail = "";
                        if (questionDic.containsValue("订单详情")) {
                            String strValue = "";
                            for (String questionDicKey : questionDic.keySet()) {
                                if (questionDic.get(questionDicKey).equals("订单详情")) {
                                    strValue = questionDicKey;
                                    break;
                                }
                            }
                            strOrderDetail = getAnswer(orderNumber, strValue).getAnswer();
                            questionDic.remove(strValue);
                        }
                        Boolean artificial = false;//是否转人工
                        try {
                            if (questionDic.containsKey("artificial")) {
                                artificial = questionDic.get("artificial").equals("true");
                                questionDic.remove("artificial");
                            }
                            if (questionDic.containsKey("caseValue")) {//场景值
                                strCaseValue = questionDic.get("caseValue");
                                questionDic.remove("caseValue");
                            }
                            if (questionDic.containsKey("caseValueDesc")) {//场景值名称
                                strCaseValue += "|" + questionDic.get("caseValueDesc");
                                questionDic.remove("caseValueDesc");
                            }
                        } catch (Exception ex) {
                            CLogger.info("getQuestion", ex);
                        }
                        int iDTMF = 0;
                        for (String questionDicKey : questionDic.keySet()) {
                            iDTMF++;
                            BroadcastMedia broadcastMedia = new BroadcastMedia();
                            broadcastMedia.setOperTag(questionDicKey);
                            broadcastMedia.setDTMF(String.valueOf(iDTMF));
                            broadcastMedia.setOrderNo(iDTMF);
                            broadcastMedia.setTransferInfo(transferInfo);
                            Media media = new Media();
                            media.setMediaType("TTS");
                            media.setText(iDTMF == 1 ? strSmsContent + "," + (StringUtils.isEmpty(strOrderDetail) ? "" : strOrderDetail) + "," + questionDic.get(questionDicKey) + "请按" + iDTMF : iDTMF == questionDic.size() ? "," + questionDic.get(questionDicKey) + "请按" + iDTMF + ",重听请按9" : "," + questionDic.get(questionDicKey) + "请按" + iDTMF);
                            broadcastMedia.setMedia(media);
                            broadcastMediaList.add(broadcastMedia);
                        }
                        if (artificial) {
                            BroadcastMedia broadcastMedia = new BroadcastMedia();
                            broadcastMedia.setOperTag(String.valueOf(IvrEnum.TransferInQuestion.getCode()));
                            broadcastMedia.setDTMF("0");
                            broadcastMedia.setOrderNo(10);
                            broadcastMedia.setTransferInfo(transferInfo);
                            Media media = new Media();
                            media.setMediaType("TTS");
                            media.setText(questionDic.size() == 0 ? strOrderDetail + ",如需人工服务请按0" : "如需人工服务请按0");
                            broadcastMedia.setMedia(media);
                            broadcastMediaList.add(broadcastMedia);
                        }
                        response.setOperType("BasicMenu");
                    }
                } else {
                    boolean isMoreNoOrder = ivrStaticsticsEx.isMoreNoOrder(request.getCallId());
                    optag = IvrEnum.QueryNoOrder.getCode();
                    BroadcastMedia broadcastMedia = new BroadcastMedia();
                    broadcastMedia.setOperTag("");
                    broadcastMedia.setDTMF("1");
                    broadcastMedia.setOrderNo(1);
                    broadcastMedia.setTransferInfo(getTransferInfo("2", request.getVersion()));
                    Media media = new Media();
                    media.setMediaType("TTS");
                    media.setText(isMoreNoOrder ? "您输入的手机未查询到订单，请确认正确的信息再来电。" : isFirstRequest ? strSmsContent + "请输入订单中留的联系手机，以井号结束。如无订单，请按星号键进入常规问题答疑。" : "输入的手机号和出行日期未查询到订单，请重新输入。" + "请输入订单中留的联系手机，以井号结束。如无订单，请按星号键进入常规问题答疑。");
                    broadcastMedia.setMedia(media);
                    broadcastMediaList.add(broadcastMedia);
                    response.setOperType(isMoreNoOrder ? "HungupWithMedia" : "CollectDTMF");
                }
                response.setBroadcastMediaList(broadcastMediaList);
                response.setResultCode("Success");
                response.setResultMessage("成功");
            } else {
                String strJson = chatConfigEx.getConfigValue("IVRGetMenu");
                response = JSON.parseObject(strJson, GetIVRPredictingUserBehaviorResponseType.class);
                if (request != null && StringUtils.isNotEmpty(request.getAni())) {
                    SingleEncryptRequestType shenDunRequest = new SingleEncryptRequestType();
                    shenDunRequest.setKeyType(CoreInfoKeyType.Phone);
                    shenDunRequest.setStrNeedEncrypt(request.getAni());
                    mobileNumber = shenDunContract.singleEncrypt(shenDunRequest);
                }
                if (request != null && StringUtils.isNotEmpty(request.getPartnerName())) {
                    if (request.getPartnerName().trim().toLowerCase().equals("tieyou")) {
                        partnerName = "tieyou";
                    } else if (request.getPartnerName().trim().toLowerCase().equals("zhixing")) {
                        partnerName = "zhixing";
                    }
                }
            }
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        int dataIndex = 0;
        if (response != null && response.getResultCode().equals("Success")) {
            try {
                IvrStaticstics ivrStaticstics = new IvrStaticstics();
                ivrStaticstics.setOrderNumber(orderNumber);
                ivrStaticstics.setChannel(partnerName);
                ivrStaticstics.setSubChannel("Ctrip.Train");
                ivrStaticstics.setCallID(request == null || StringUtils.isEmpty(request.getCallId()) ? "" : request.getCallId());
                ivrStaticstics.setCallNumber(mobileNumber);
                ivrStaticstics.setOperTag(optag);
                ivrStaticstics.setOpTagString("GetQuestion");
                ivrStaticstics.setMsgContent(mapper.writeValueAsString(response));
                ivrStaticstics.setDTMF(request != null && StringUtils.isNotEmpty(request.getOrderDateString()) ? IvrEnum.InputHaveDate.getCode() : IvrEnum.InputNoDate.getCode());
                ivrStaticstics.setOperator("IVR_System");
                if (StringUtils.isNotEmpty(strCaseValue)) {
                    ivrStaticstics.setCaseValue(strCaseValue);
                }
                ivrStaticstics.setVipFlag(Integer.parseInt(userLevel));
                dataIndex = ivrStaticsticsEx.insert(ivrStaticstics);
            } catch (Exception ex) {
                CLogger.info("插入数据异常", ex);
            }
        }
        strLog += ".........插入数据结果_" + dataIndex;
        strLog += ".........结果_" + mapper.writeValueAsString(response);
        CLogger.info("获取问题", strLog, dic);
        return response;
    }

    /**
     * 获取答案
     */
    public IVRUserSelfServiceResponseType iVRUserSelfService(IVRUserSelfServiceRequestType request) {
        IVRUserSelfServiceResponseType response = new IVRUserSelfServiceResponseType();
        response.setResultCode("Failure");
        response.setResultMessage("初始状态");
        Map<String, String> dic = new HashMap<>();
        String strLog = "获取答案调用开始";
        try {
            dic.put("ani", request == null || StringUtils.isEmpty(request.getAni()) ? "" : request.getAni());
            strLog += ".........参数_" + mapper.writeValueAsString(request);
            if (request == null || StringUtils.isEmpty(request.getOperTag()) || StringUtils.isEmpty(request.getCallId())) {
                response.setResultMessage("参数错误");
            } else {
                Boolean artificial = false;//是否转人工
                String strQuestionDesc = "";//问题内容
                IvrStaticstics ivrStaticstics = ivrStaticsticsEx.getIvrStaticsticsByCallId(request.getCallId());
                if (ivrStaticstics == null) {
                    response.setResultMessage("数据错误");
                } else {
                    List<BroadcastMedia> broadcastMediaList = new ArrayList<BroadcastMedia>();
                    BroadcastMedia broadcastMedia = new BroadcastMedia();
                    broadcastMedia.setDTMF("");
                    broadcastMedia.setOrderNo(1);
                    Media media = new Media();
                    media.setMediaType("TTS");
                    if (request.getOperTag().equals(String.valueOf(IvrEnum.TransferInQuestion.getCode())) || request.getOperTag().equals(String.valueOf(IvrEnum.TransferInResult.getCode()))) {//转人工
                        broadcastMedia.setOperTag(request.getOperTag());
                        broadcastMedia.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                        media.setText("正在为您转人工服务, 请稍后。。");
                    } else if (request.getOperTag().contains("IVR_R_ZZZZZZ0")) {//无单自助
                        broadcastMedia.setOperTag(request.getOperTag());
                        broadcastMedia.setTransferInfo(getTransferInfo("2", request.getVersion()));
                        media.setText(chatConfigEx.getConfigValue(request.getOperTag()) + ",重听请按9。");
                    } else {
                        if (request.getOperTag().contains("|refund|")) {//退票
                            artificial = true;
                            strQuestionDesc = "确认退票";
                            broadcastMedia.setOperTag(request.getOperTag());
                            broadcastMedia.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                            media.setText("退票申请已提交, 建议您登陆APP，关注退票结果。" + "。重听请按9");
                            try {
                                OrderDetailResponseType orderDetailResponseType = orderContract.orderDetail(ivrStaticstics.getOrderNumber());
                                ReturnTicketServiceRequestType returnTicketServiceRequestType = new ReturnTicketServiceRequestType();
                                returnTicketServiceRequestType.setOrderNumber(ivrStaticstics.getOrderNumber());
                                List<String> longElecNums = new ArrayList<String>();
                                if (orderDetailResponseType != null && orderDetailResponseType.getOrderRealTickets() != null) {
                                    longElecNums = orderDetailResponseType.getOrderRealTickets().stream().filter(T -> T.getTicketState() == 0).map(T -> T.getLongTrainNo()).collect(Collectors.toList());//原票：查看ctrainfinancedb库orderrealticket表对应字段意义：0未退 1已退 2退票中 3退票失败
                                }
                                if (orderDetailResponseType != null && orderDetailResponseType.getOrderrescheduleTicketInfos() != null && orderDetailResponseType.getOrderrescheduleTicketInfos().size() > 0) {
                                    for (int i = 0; i < orderDetailResponseType.getOrderrescheduleTicketInfos().size(); i++) {
                                        if (orderDetailResponseType.getOrderrescheduleTicketInfos().get(i) != null && orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos() != null && orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().size() > 0) {
                                            for (int j = 0; j < orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().size(); j++) {
                                                if (orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().get(j) != null && (orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().get(j).getRescheduleState() == 40 || orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().get(j).getRescheduleState() == 80)) {
                                                    longElecNums.add(orderDetailResponseType.getOrderrescheduleTicketInfos().get(i).getRescheduleRealTicketInfos().get(j).getRescheduleLongTrainNum());//改签票：改签表状态  未知-0  待支付补款-10 待推送到退改签系统-20  改签中-30 改签成功-40  改签失败-50  退票中-60 退票成功-70 退票失败-80
                                                }
                                            }
                                        }
                                    }
                                }
                                returnTicketServiceRequestType.setLongElecNums(longElecNums);
                                returnTicketServiceRequestType.setOperator("IVR_User");
                                returnTicketServiceRequestType.setPartnerName(orderDetailResponseType.getOrderMaster().getPartnerName());
                                strLog += ".........退票参数_" + mapper.writeValueAsString(returnTicketServiceRequestType);
                                ReturnTicketServiceResponseType returnTicketServiceResponseType = orderContract.returnTicketService(returnTicketServiceRequestType);
                                strLog += ".........退票结果_" + mapper.writeValueAsString(returnTicketServiceResponseType);
                            } catch (Exception ex) {
                                strLog += ".........退票异常_" + mapper.writeValueAsString(ex);
                            }
                        } else if (request.getOperTag().contains("|cancel|")) {//取消
                            artificial = true;
                            strQuestionDesc = "确认取消订单";
                            broadcastMedia.setOperTag(request.getOperTag());
                            broadcastMedia.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                            media.setText("取消申请已提交, 建议您登陆APP，关注取消结果。" + "。重听请按9");
                            try {
                                OfflineCancelOrderRequestType offlineCancelOrderRequestType = new OfflineCancelOrderRequestType();
                                offlineCancelOrderRequestType.setOrderNumber(ivrStaticstics.getOrderNumber());
                                offlineCancelOrderRequestType.setPartnerName("Ctrip.Train");
                                offlineCancelOrderRequestType.setCancelReason("IVR用户系统取消");
                                offlineCancelOrderRequestType.setOperator("IVR_User");
                                offlineCancelOrderRequestType.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                strLog += ".........取消参数_" + mapper.writeValueAsString(offlineCancelOrderRequestType);
                                OfflineCancelOrderResponseType offlineCancelOrderResponseType = orderContract.offlineCancelOrder(offlineCancelOrderRequestType);
                                strLog += ".........取消结果_" + mapper.writeValueAsString(offlineCancelOrderResponseType);
                            } catch (Exception ex) {
                                strLog += ".........取消异常_" + mapper.writeValueAsString(ex);
                            }
                        } else {//普通
                            GetAnswerResponse getAnswerResponse = getAnswer(ivrStaticstics.getOrderNumber(), request.getOperTag());
                            if (getAnswerResponse == null) {
                                broadcastMedia.setOperTag(request.getOperTag());
                                broadcastMedia.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                                media.setText("系统忙线中，请稍后重试。");
                            } else {
                                artificial = getAnswerResponse.isArtificial();
                                if (StringUtils.isEmpty(getAnswerResponse.getOperate())) {
                                    broadcastMedia.setOperTag(request.getOperTag());
                                } else if (getAnswerResponse.getOperate().equals("refundTikets")) {
                                    broadcastMedia.setDTMF("1");
                                    broadcastMedia.setOperTag(request.getOperTag() + "|refund|");
                                } else if (getAnswerResponse.getOperate().equals("cancelOrder")) {
                                    broadcastMedia.setDTMF("1");
                                    broadcastMedia.setOperTag(request.getOperTag() + "|cancel|");
                                } else {
                                    broadcastMedia.setOperTag(request.getOperTag());
                                }
                                broadcastMedia.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                                media.setText(getAnswerResponse.getAnswer());
                            }
                        }
                    }
                    broadcastMedia.setMedia(media);
                    broadcastMediaList.add(broadcastMedia);
                    if (artificial) {
                        BroadcastMedia broadcastMediaArtificial = new BroadcastMedia();
                        broadcastMediaArtificial.setOperTag(String.valueOf(IvrEnum.TransferInResult.getCode()));
                        broadcastMediaArtificial.setDTMF("0");
                        broadcastMediaArtificial.setOrderNo(10);
                        broadcastMediaArtificial.setTransferInfo(getTransferInfo(String.valueOf(ivrStaticstics.getVipFlag()), request.getVersion()));
                        Media mediaArtificial = new Media();
                        mediaArtificial.setMediaType("TTS");
                        mediaArtificial.setText("如需帮助请按0");
                        broadcastMediaArtificial.setMedia(mediaArtificial);
                        broadcastMediaList.add(broadcastMediaArtificial);
                    }
                    response.setBroadcastMediaList(broadcastMediaList);
                    response.setOperType(request.getOperTag().equals(String.valueOf(IvrEnum.TransferInQuestion.getCode())) || request.getOperTag().equals(String.valueOf(IvrEnum.TransferInResult.getCode())) ? "TransferWithMedia" : "BasicMenu");
                    response.setResultCode("Success");
                    response.setResultMessage("成功");
                    try {
                        SingleEncryptRequestType shenDunRequest = new SingleEncryptRequestType();
                        shenDunRequest.setKeyType(CoreInfoKeyType.Phone);
                        shenDunRequest.setStrNeedEncrypt(request.getAni());
                        ivrStaticstics.setCallNumber(shenDunContract.singleEncrypt(shenDunRequest));
                        ivrStaticstics.setOperTag(request.getOperTag().equals(String.valueOf(IvrEnum.TransferInQuestion.getCode())) ? IvrEnum.TransferInQuestion.getCode() : request.getOperTag().equals(String.valueOf(IvrEnum.TransferInResult.getCode())) ? IvrEnum.TransferInResult.getCode() : IvrEnum.GetResult.getCode());
                        ivrStaticstics.setOpTagString(request.getOperTag());
                        ivrStaticstics.setDTMF(0);
                        ivrStaticstics.setCallDttm(DateUtils.getCurFullTimestamp());
                        if (StringUtils.isNotEmpty(ivrStaticstics.getCaseValue())) {
                            ivrStaticstics.setCaseValue(ivrStaticstics.getCaseValue());
                        }
                        if (request.getOperTag().equals(String.valueOf(IvrEnum.TransferInResult.getCode()))) {
                            ivrStaticstics.setRemark(ivrStaticsticsEx.getLastQuestionDescByCallId(request.getCallId()));
                        } else if (!request.getOperTag().equals(String.valueOf(IvrEnum.TransferInQuestion.getCode()))) {
                            try {
                                if (StringUtils.isEmpty(strQuestionDesc)) {
                                    for (int i = 0; i < JSON.parseObject(ivrStaticstics.getMsgContent(), GetIVRPredictingUserBehaviorResponseType.class).getBroadcastMediaList().size(); i++) {
                                        if (JSON.parseObject(ivrStaticstics.getMsgContent(), GetIVRPredictingUserBehaviorResponseType.class).getBroadcastMediaList().get(i).getOperTag().equals(request.getOperTag())) {
                                            String strText = JSON.parseObject(ivrStaticstics.getMsgContent(), GetIVRPredictingUserBehaviorResponseType.class).getBroadcastMediaList().get(i).getMedia().getText();
                                            strText = strText.replace(",重听请按", "");
                                            strText = strText.substring(strText.lastIndexOf(',') + 1, strText.lastIndexOf("请按")).replace("，", "");
                                            strQuestionDesc = strText;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                            }
                            if (StringUtils.isNotEmpty(strQuestionDesc)) {
                                ivrStaticstics.setRemark(strQuestionDesc);
                            }
                        }
                        ivrStaticstics.setMsgContent(media.getText());
                        ivrStaticstics.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
                        ivrStaticsticsEx.insert(ivrStaticstics);
                    } catch (Exception ex) {
                        CLogger.info("getQuestion", ex);
                    }
                }
            }
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        strLog += ".........结果_" + mapper.writeValueAsString(response);
        CLogger.info("获取答案", strLog, dic);
        return response;
    }

    /**
     * 答案转人工日志记录
     */
    public IVRUserTransferLogResponseType iVRUserTransferLog(IVRUserTransferLogRequestType request) {
        IVRUserTransferLogResponseType response = new IVRUserTransferLogResponseType();
        Map<String, String> dic = new HashMap<>();
        String strLog = "答案转人工日志记录调用开始";
        try {
            dic.put("ani", request == null || StringUtils.isEmpty(request.getAni()) ? "" : request.getAni());
            strLog += ".........参数_" + mapper.writeValueAsString(request);
            IvrStaticstics ivrStaticstics = ivrStaticsticsEx.getIvrStaticsticsByCallId(request.getCallId());
            if (ivrStaticstics == null) {
                strLog += ".........数据错误_";
            } else {
                SingleEncryptRequestType shenDunRequest = new SingleEncryptRequestType();
                shenDunRequest.setKeyType(CoreInfoKeyType.Phone);
                shenDunRequest.setStrNeedEncrypt(request.getAni());
                ivrStaticstics.setCallNumber(shenDunContract.singleEncrypt(shenDunRequest));
                ivrStaticstics.setOperTag(IvrEnum.TransferInResult.getCode());
                ivrStaticstics.setOpTagString(IvrEnum.TransferInResult.getName());
                ivrStaticstics.setMsgContent("");
                ivrStaticstics.setDTMF(0);
                ivrStaticstics.setCallDttm(DateUtils.getCurFullTimestamp());
                ivrStaticstics.setDatachangeLasttime(DateUtils.getCurFullTimestamp());
                ivrStaticsticsEx.insert(ivrStaticstics);
            }
        } catch (Exception ex) {
            strLog += ".........异常_" + mapper.writeValueAsString(ex);
        }
        response.setResultCode("Success");
        response.setResultMessage("成功");
        strLog += ".........结果_" + mapper.writeValueAsString(response);
        CLogger.info("答案转人工日志记录", strLog, dic);
        return response;
    }

    /**
     * 获取订单号
     */
    public String getOrderNumberByParam(String mobileNumber, String orderDateString, String partnerName) {
        String orderNumber = "";
        try {
            if (StringUtils.isNotEmpty(orderDateString)) {
                String orderDateStringTemp = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addYears(new Date(), -10));
                if (orderDateString.length() == 4
                        && Pattern.compile("[0-9]*").matcher(orderDateString).matches()
                        && Integer.parseInt(orderDateString.split("|")[0] + orderDateString.split("|")[1]) > 0
                        && Integer.parseInt(orderDateString.split("|")[0] + orderDateString.split("|")[1]) <= 12
                        && Integer.parseInt(orderDateString.split("|")[2] + orderDateString.split("|")[3]) > 0
                        && Integer.parseInt(orderDateString.split("|")[2] + orderDateString.split("|")[3]) <= 31) {
                    try {
                        orderDateString = new SimpleDateFormat("yyyy").format(new Date()) + orderDateString;
                        Date dateTemp = DateUtils.parseDate(orderDateString, DateUtils.YMD);
                        if (DateUtils.getTimeSpanDay(new Date(), dateTemp) > 200) {
                            dateTemp = DateUtils.addYears(dateTemp, -1);
                        } else if (DateUtils.getTimeSpanDay(new Date(), dateTemp) < -200) {
                            dateTemp = DateUtils.addYears(dateTemp, 1);
                        }
                        orderDateString = new SimpleDateFormat("yyyy-MM-dd").format(dateTemp);
                    } catch (Exception ex) {
                        orderDateString = orderDateStringTemp;
                    }
                } else {
                    orderDateString = orderDateStringTemp;
                }
            }
            String startTicketDate = StringUtils.isEmpty(orderDateString) ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.addMonths(new Date(), -1)) : orderDateString + " 00:00:00";
            String endTicketDate = StringUtils.isEmpty(orderDateString) ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.addMonths(new Date(), 3)) : orderDateString + " 00:00:00";
            OfflineOrderListRequestType request = new OfflineOrderListRequestType();
            List<String> list = new ArrayList<String>();
            list.add(partnerName);
            request.setPartnerNames(list);
            request.setContactMobile(mobileNumber);
            request.setStartTicketDate(startTicketDate);
            request.setEndTicketDate(endTicketDate);
            request.setPageNo(1);
            request.setPageSize(25);//每页最多25条数据，传多了也没用
            String strLog = ".........请求报文_" + mapper.writeValueAsString(request);
            OfflineOrderListResponseType response;
            if (Foundation.server().getEnv().isFAT()) {
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + orderContract.strTrainOrderCentreServiceOfflineClientUrl + "OfflineOrderList" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, OfflineOrderListResponseType.class);
            } else {
                response = orderContract.getUserOrderListResponse(request);
            }
            strLog += ".........返回报文_" + mapper.writeValueAsString(response);
            if (response != null && response.getRetCode() == 0 && response.getOrderCount() > 0 && response.getOfflineOrders() != null) {
                orderNumber = response.getOfflineOrders().get(0).getOrderNumber();
            }
            strLog += ".........订单号_" + orderNumber;
            Map<String, String> dic = new HashMap<>();
            dic.put("mobileNumber", mobileNumber);
            CLogger.info("获取订单号", strLog, dic);
        } catch (Exception ex) {
            CLogger.info("获取订单号", ex);
        }
        return orderNumber;
    }

    /**
     * 获取用户分层
     */
    public String getUserLevel(String orderNumber, String callId) {
        String strLevel = "2";
        try {
            GetRuleLevelManageRequestType request = new GetRuleLevelManageRequestType();
            request.setOrderNumber(orderNumber);
            request.setMobilePhone(callId);
            request.setOrderSource((short) 1);
            GetRuleLevelManageResponseType response = ivrContract.getRuleLevelManage(request);
            if (response != null && response.getReturnCode() == 0) {
                if (response.getRuleLevel().trim().toUpperCase().equals("A")) {
                    strLevel = "3";
                } else if (response.getRuleLevel().trim().toUpperCase().equals("C")) {
                    strLevel = "1";
                }
            }
        } catch (Exception ex) {
            CLogger.info("获取用户分层", ex);
        }
        return strLevel;
    }

    /**
     * 是否直接转人工
     */
    public String isTransfer(String orderNumber) {
        String isTransfer = "";
        try {
            OfflineOrderDetailResponseType response;
            if (Foundation.server().getEnv().isFAT()) {
                OfflineOrderDetailRequestType request = new OfflineOrderDetailRequestType();
                request.setOrderNumber(orderNumber);
                String pathUrl = QConfigHelper.getAppSetting("OfflineClientProxyUrl") + "?clienturl=" + orderContract.strTrainOrderCentreServiceOfflineClientUrl + "offlineOrderDetail" + "&postdate=" + mapper.writeValueAsString(request);
                String strJson = HttpUtils.doGet(pathUrl.replace("{", "llleft").replace("}", "rrright").replace("\"", "dddot").replace(" ", "eeempty")).replace("Date(-", "Date(");
                response = JSON.parseObject(strJson, OfflineOrderDetailResponseType.class);
            } else {
                response = orderContract.getOrderDedetail(orderNumber);
            }
            if (response != null &&
                    (
                            response.getOrderDetail().getOrderState().equals("4")
                                    || response.getOrderDetail().getOrderState().equals("5")
                                    || response.getOrderDetail().getOrderState().equals("6")
                                    || response.getOrderDetail().getOrderState().equals("7")
                                    || response.getOrderDetail().getOrderState().equals("8")
                    )
            ) {
                for (int i = 0; i < response.getOfflinePassengers().size(); i++) {
                    if (DateUtils.getTimeSpanDay(new Date(), DateUtils.parseDate(response.getOfflinePassengers().get(i).getTicketTime(), DateUtils.YMDHM_UNDERLINED)) == 0
                            &&
                            DateUtils.getTimeSpanHour(new Date(), DateUtils.parseDate(response.getOfflinePassengers().get(i).getTicketTime(), DateUtils.YMDHM_UNDERLINED)) == 0
                            &&
                            response.getOfflinePassengers().get(i).getTicketState().equals("0")
                    ) {
                        isTransfer = String.valueOf(IvrEnum.TicketInOneHour.getCode());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            CLogger.info("是否直接转人工", ex);
        }
        return isTransfer;
    }

    /**
     * 获取电话自助问题
     **/
    public Map<String, String> getQuestionList(String orderNumber) {
        Map<String, String> questionDic = new LinkedHashMap<>();
        Boolean artificial = false;
        try {
            GetQuestionListRequest request = new GetQuestionListRequest();
            request.setOrderId(orderNumber);
            request.setGroupCode("train_ivr");
            GetQuestionListResponse response = ivrContract.getQuestionList(request);
            if (response != null && response.getQuestions() != null && response.getQuestions().size() > 0) {
                artificial = response.isArtificial();
                questionDic.put("artificial", String.valueOf(artificial));
                questionDic.put("caseValue", response.getCaseValue());
                questionDic.put("caseValueDesc", response.getCaseValueDesc());
                for (int i = 0; i < response.getQuestions().size(); i++) {
                    String key = response.getQuestions().get(i).getRelationGuid();
                    String value = response.getQuestions().get(i).getContent();
                    questionDic.put(key, value);
                }
            }
        } catch (Exception ex) {
            CLogger.info("获取电话自助问题", ex);
        }
        return questionDic;
    }

    /**
     * 获取转接信息
     */
    public TransferInfo getTransferInfo(String userLevel, String version) {
        TransferInfo transferInfo = new TransferInfo();
        try {
            String transferType = chatConfigEx.ivrTransferInfoType();
            Map<String, String> transferDic = new HashMap<>();
            if (userLevel.equals("3")) {
                if (version.equals("2.0"))
                    transferDic = chatConfigEx.getTransferParam("IVR_TransferInfoVIP_Slice");
                else {
                    transferDic = chatConfigEx.getTransferParam("IVR_TransferInfoVIP");
                }
            } else {
                if (version.equals("2.0"))
                    transferDic = chatConfigEx.getTransferParam("IVR_TransferInfo_Slice");
                else {
                    transferDic = chatConfigEx.getTransferParam("IVR_TransferInfo");
                }
            }
            transferInfo.setWaitTimeSec(chatConfigEx.ivrTransferInfoWaitSec());
            transferInfo.setOutSourceGroupVdn(chatConfigEx.getConfigValue("IVR_TransferInfo_OutSourceVdn"));
            transferInfo.setTransferType(transferType);
            transferInfo.setTransferText(transferDic.containsKey(transferType) ? transferDic.get(transferType) : "74");
            if (userLevel.equals("1")) {//TransferStatus:是否转接到外包组,1表示转接外包组
                transferInfo.setTransferStatus(1);
            } else {
                transferInfo.setTransferStatus(0);
            }
        } catch (Exception ex) {
            CLogger.info("获取转接信息", ex);
        }
        return transferInfo;
    }

    /**
     * 获取电话自助答案
     **/
    public GetAnswerResponse getAnswer(String orderId, String relationGuid) {
        GetAnswerResponse response = new GetAnswerResponse();
        try {
            GetAnswerRequest request = new GetAnswerRequest();
            request.setOrderId(orderId);
            request.setRelationGuid(relationGuid);
            request.setGroupCode("train_ivr");
            response = ivrContract.getAnswer(request);
        } catch (Exception ex) {
            CLogger.info("获取电话自助答案", ex);
        }
        return response;
    }
}

