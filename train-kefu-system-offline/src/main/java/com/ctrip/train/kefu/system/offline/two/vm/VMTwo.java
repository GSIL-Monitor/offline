package com.ctrip.train.kefu.system.offline.two.vm;

import com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo;
import com.ctrip.train.ticketagent.service.client.CheckResultInfo;

import java.util.List;

public class VMTwo {
    private String orderId;
    private String partnerOrderId;
    private String contactName;
    private String contactAddress;
    private String contactMobile;
    private String fromCity;
    private String toCity;
    private String ticketNum; //原订单车票张数
    private String seatNo;   //席位
    private String trainNumber;  //车次
    private String ticketTime; //发车时间
    private List<TrainInfo> SearchS2SNum; //站站+车次查询
    private List<TrainInfo> SearchS2S; //站站查询
    private List<TrainInfo> SearchS2Sbefore; //站站查询前一天
    private List<TrainInfo> SearchS2SAfter; //站站查询后一天
    private TrainInfo  TrainInfo;
    private List<CheckResultInfo> checkTrain;
    private String partnerName;

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public List<CheckResultInfo> getCheckTrain() {
        return checkTrain;
    }

    public void setCheckTrain(List<CheckResultInfo> checkTrain) {
        this.checkTrain = checkTrain;
    }

    public List<com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo> getSearchS2Sbefore() {
        return SearchS2Sbefore;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public void setSearchS2Sbefore(List<com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo> searchS2Sbefore) {
        SearchS2Sbefore = searchS2Sbefore;
    }

    public List<com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo> getSearchS2SAfter() {
        return SearchS2SAfter;
    }

    public void setSearchS2SAfter(List<com.ctrip.soa.train.traindata.phenixdataapiservice.v1.TrainInfo> searchS2SAfter) {
        SearchS2SAfter = searchS2SAfter;
    }

    private String date; //出发日期

    public TrainInfo getTrainInfo() {
        return TrainInfo;
    }

    public void setTrainInfo(TrainInfo trainInfo) {
        TrainInfo = trainInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public List<TrainInfo> getSearchS2SNum() {
        return SearchS2SNum;
    }

    public void setSearchS2SNum(List<TrainInfo> searchS2SNum) {
        SearchS2SNum = searchS2SNum;
    }

    public List<TrainInfo> getSearchS2S() {
        return SearchS2S;
    }

    public void setSearchS2S(List<TrainInfo> searchS2S) {
        SearchS2S = searchS2S;
    }

    public VMTwo() {
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
