package com.ctrip.train.kefu.system.offline.notice.vm;


public class VmNoticeReport {

    /**
     * 操作人
     */
    private String opUser;

    /**
     * 产品线
     */
    private String productLine;

    /**
     *通知总量
     */
    private String noticeCount;

    /**
     * 平均响应时效
     */
    private String AvgResponseAging;

    /**
     * 平均处理时效
     */
    private String AvgProcessingAging;

    /**
     * 关闭率
     */
    private String closeRate;

    /**
     * 被催率
     */
    private String promotedRate;

    /**
     * 转投诉量
     */
    private String transferComplaints;

    /**
     * 转领班次数
     */
    private String transferForeman;

    /**
     * 预约回电次数
     */
    private String reservationTimes;

    /**
     * 外呼次数
     */
    private String callTimes;

    /**
     * 交班量
     */
    private String turnOver;

    /**
     * 首次外呼时间
     */
    private String fristCallTimes;

    private String starTime;

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getFristCallTimes() {
        return fristCallTimes;
    }

    public void setFristCallTimes(String fristCallTimes) {
        this.fristCallTimes = fristCallTimes;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(String noticeCount) {
        this.noticeCount = noticeCount;
    }

    public String getAvgResponseAging() {
        return AvgResponseAging;
    }

    public void setAvgResponseAging(String avgResponseAging) {
        AvgResponseAging = avgResponseAging;
    }

    public String getAvgProcessingAging() {
        return AvgProcessingAging;
    }

    public void setAvgProcessingAging(String avgProcessingAging) {
        AvgProcessingAging = avgProcessingAging;
    }

    public String getCloseRate() {
        return closeRate;
    }

    public void setCloseRate(String closeRate) {
        this.closeRate = closeRate;
    }

    public String getPromotedRate() {
        return promotedRate;
    }

    public void setPromotedRate(String promotedRate) {
        this.promotedRate = promotedRate;
    }

    public String getTransferComplaints() {
        return transferComplaints;
    }

    public void setTransferComplaints(String transferComplaints) {
        this.transferComplaints = transferComplaints;
    }

    public String getTransferForeman() {
        return transferForeman;
    }

    public void setTransferForeman(String transferForeman) {
        this.transferForeman = transferForeman;
    }

    public String getReservationTimes() {
        return reservationTimes;
    }

    public void setReservationTimes(String reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public String getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(String callTimes) {
        this.callTimes = callTimes;
    }

    public String getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }
}
