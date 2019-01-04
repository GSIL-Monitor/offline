package com.ctrip.train.kefu.system.client.pojo.train;

import java.util.Calendar;

public class StationContractPojo {
    private String fromCity;
    private String toCity;
    private String User;
    private Calendar departDate;
    private String trainNo;
    private String sign;
    private String TimeStamp;

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

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public Calendar getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Calendar departDate) {
        this.departDate = departDate;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
    public StationContractPojo(){}
    public StationContractPojo(String fromCity, String toCity, String user, Calendar departDate, String trainNo, String sign, String timeStamp) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        User = user;
        this.departDate = departDate;
        this.trainNo = trainNo;
        this.sign = sign;
        TimeStamp = timeStamp;
    }
}
