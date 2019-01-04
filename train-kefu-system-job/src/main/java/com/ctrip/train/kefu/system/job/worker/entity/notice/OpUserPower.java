package com.ctrip.train.kefu.system.job.worker.entity.notice;

public class OpUserPower {

    String opUserName;

    String opUserNum;

    double opUserBusy;

    long solveAbility;

    String productLine;

    String envenType;

    String noticeType;

    //处理上限
    int processLimit;
    //处理中
    long solveingNotice;

    long waitNotice;

    //通知待处理上限
    int noticeprocessLimit;
    //投诉待处理上限
    int complainprocessLimit;

    public int getNoticeprocessLimit() {
        return noticeprocessLimit;
    }

    public void setNoticeprocessLimit(int noticeprocessLimit) {
        this.noticeprocessLimit = noticeprocessLimit;
    }

    public int getComplainprocessLimit() {
        return complainprocessLimit;
    }

    public void setComplainprocessLimit(int complainprocessLimit) {
        this.complainprocessLimit = complainprocessLimit;
    }

    public long getSolveingNotice() {
        return solveingNotice;
    }

    public void setSolveingNotice(long solveingNotice) {
        this.solveingNotice = solveingNotice;
    }

    public int getProcessLimit() {
        return processLimit;
    }

    public void setProcessLimit(int processLimit) {
        this.processLimit = processLimit;
    }

    public long getWaitNotice() {
        return waitNotice;
    }

    public void setWaitNotice(long waitNotice) {
        this.waitNotice = waitNotice;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getEnvenType() {
        return envenType;
    }

    public void setEnvenType(String envenType) {
        this.envenType = envenType;
    }

    public String getOpUserName() {
        return opUserName;
    }

    public void setOpUserName(String opUserName) {
        this.opUserName = opUserName;
    }

    public String getOpUserNum() {
        return opUserNum;
    }

    public void setOpUserNum(String opUserNum) {
        this.opUserNum = opUserNum;
    }

    public double getOpUserBusy() {
        return opUserBusy;
    }

    public void setOpUserBusy(double opUserBusy) {
        this.opUserBusy = opUserBusy;
    }

    public long getSolveAbility() {
        return solveAbility;
    }

    public void setSolveAbility(long solveAbility) {
        this.solveAbility = solveAbility;
    }
}
