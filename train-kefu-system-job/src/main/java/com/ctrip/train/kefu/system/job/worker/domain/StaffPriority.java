package com.ctrip.train.kefu.system.job.worker.domain;

public class StaffPriority {

    private String staffName;
    private String staffNum;
    private int envenType;
    private int noticeProductLine;
    private String noticeTypes;

    private int noticeWaitLimit;
    private long complainWaitLimit;

    private double staffNoticeBusy;
    private long solveAbility;
    private double staffComplainBusy;
    private long  noticeWait;
    private long  complainWait;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public int getEnvenType() {
        return envenType;
    }

    public void setEnvenType(int envenType) {
        this.envenType = envenType;
    }

    public int getNoticeProductLine() {
        return noticeProductLine;
    }

    public void setNoticeProductLine(int noticeProductLine) {
        this.noticeProductLine = noticeProductLine;
    }

    public String getNoticeTypes() {
        return noticeTypes;
    }

    public void setNoticeTypes(String noticeTypes) {
        this.noticeTypes = noticeTypes;
    }

    public int getNoticeWaitLimit() {
        return noticeWaitLimit;
    }

    public void setNoticeWaitLimit(int noticeWaitLimit) {
        this.noticeWaitLimit = noticeWaitLimit;
    }

    public long getComplainWaitLimit() {
        return complainWaitLimit;
    }

    public void setComplainWaitLimit(long complainWaitLimit) {
        this.complainWaitLimit = complainWaitLimit;
    }

    public double getStaffNoticeBusy() {
        return staffNoticeBusy;
    }

    public void setStaffNoticeBusy(double staffNoticeBusy) {
        this.staffNoticeBusy = staffNoticeBusy;
    }

    public long getSolveAbility() {
        return solveAbility;
    }

    public void setSolveAbility(long solveAbility) {
        this.solveAbility = solveAbility;
    }

    public double getStaffComplainBusy() {
        return staffComplainBusy;
    }

    public void setStaffComplainBusy(double staffComplainBusy) {
        this.staffComplainBusy = staffComplainBusy;
    }

    public long getNoticeWait() {
        return noticeWait;
    }

    public void setNoticeWait(long noticeWait) {
        this.noticeWait = noticeWait;
    }

    public long getComplainWait() {
        return complainWait;
    }

    public void setComplainWait(long complainWait) {
        this.complainWait = complainWait;
    }
}
