package com.ctrip.train.kefu.system.offline.order.vm;

/**
 * 12306订单详情
 */
public class QueryOrderDetailEx {
    private String trianInfo;       //车次信息
    private String seatInfo;        //座席信息
    private String passengerInfo;   //旅客信息
    private String tickprice;       //票款金额
    private String ticketstate;    //车票状态
    private String refundInfo;      //退款详情
    private String orderTrace;     //订单跟踪

    public String getTrianInfo() {
        return trianInfo;
    }

    public void setTrianInfo(String trianInfo) {
        this.trianInfo = trianInfo;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    public String getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(String passengerInfo) { this.passengerInfo = passengerInfo; }

    public String getTickprice() {
        return tickprice;
    }

    public void setTickprice(String tickprice) {
        this.tickprice = tickprice;
    }

    public String getTicketstate() {
        return ticketstate;
    }

    public void setTicketstate(String ticketstate) {
        this.ticketstate = ticketstate;
    }

    public String getRefundInfo() {
        return refundInfo;
    }

    public void setRefundInfo(String refundInfo) {
        this.refundInfo = refundInfo;
    }

    public String getOrderTrace() {
        return orderTrace;
    }

    public void setOrderTrace(String orderTrace) {
        this.orderTrace = orderTrace;
    }

}
