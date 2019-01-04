package com.ctrip.train.kefu.system.offline.two.vm;

import java.util.Map;

public class VMS2S {
    private String fromCity;
    private String toCity;
    private String ticketNum; //原订单车票张数
    private String seatNo;   //席位
    private String trainNumber;  //车次
    private String ticketTime; //发车时间
    private Map<String,Object> shangSeat; //上铺
    private Map<String,Object> zhongSeat; //中铺
    private Map<String,Object> xiaSeat; //下铺
    private Map<String,Object> s2sSeat; //站站推荐

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

    public Map<String, Object> getShangSeat() {
        return shangSeat;
    }

    public void setShangSeat(Map<String, Object> shangSeat) {
        this.shangSeat = shangSeat;
    }

    public Map<String, Object> getZhongSeat() {
        return zhongSeat;
    }

    public void setZhongSeat(Map<String, Object> zhongSeat) {
        this.zhongSeat = zhongSeat;
    }

    public Map<String, Object> getXiaSeat() {
        return xiaSeat;
    }

    public void setXiaSeat(Map<String, Object> xiaSeat) {
        this.xiaSeat = xiaSeat;
    }

    public Map<String, Object> getS2sSeat() {
        return s2sSeat;
    }

    public void setS2sSeat(Map<String, Object> s2sSeat) {
        this.s2sSeat = s2sSeat;
    }
}
