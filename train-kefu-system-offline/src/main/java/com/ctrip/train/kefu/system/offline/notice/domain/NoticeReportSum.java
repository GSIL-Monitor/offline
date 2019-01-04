package com.ctrip.train.kefu.system.offline.notice.domain;



import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
public class NoticeReportSum {

    //OpCount,OpTime,NoticeState,OpUserName,OpUserNum,FirstDealTime,StartDealTIme,AppointedProcessTime,FirstCallTime,EnvenType,SendTime,CompleteTime
    /**
     * 催次数
     */
    @Column(name="OpCount")
    @Type(value=Types.INTEGER)
    private Integer opCount;

    /**
     * 处理时间
     */
    @Column(name="OpTime")
    @Type(value=Types.VARCHAR)
    private String opTime;
    /**
     * 通知状态,待处理-80； 处理中-81； 暂缓-82； 已解决-83； 无需处理-84； 已转二线-93;已转投诉-94 重构后不区分通知、投诉：80待处理 81处理中 82暂缓 83已解决 84无需处理 94已转投诉 100已转领班
     */
    @Column(name="NoticeState")
    @Type(value=Types.INTEGER)
    private Integer noticeState;
    /**
     * 操作人姓名
     */
    @Column(name="OpUserName")
    @Type(value= Types.VARCHAR)
    private String opUserName;
    /**
     * 处理人
     */
    @Column(name="OpUser")
    @Type(value=Types.VARCHAR)
    private String opUser;
    /**
     * 操作人编号
     */
    @Column(name="OpUserNum")
    @Type(value=Types.VARCHAR)
    private String opUserNum;

    /**
     * 第一次处理时间，值不变
     */
    @Column(name="FirstDealTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp firstDealTime;

    /**
     * 第一次外呼时间
     */
    @Column(name="FirstCallTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp firstCallTime;

    /**
     * 开始处理时间，重新分配值会变化
     */
    @Column(name="StartDealTIme")
    @Type(value=Types.TIMESTAMP)
    private Timestamp startDealTIme;

    /**
     * 预约回电时间
     */
    @Column(name="AppointedProcessTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp appointedProcessTime;


    /**
     * 事件类别（二线通知：1；投诉：2；一线通知：3；领班通知：4） 重构后：通知1 投诉2 领班4
     */
    @Column(name="EnvenType")
    @Type(value=Types.INTEGER)
    private Integer envenType;

    /**
     * 发送时间
     */
    @Column(name="SendTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp sendTime;

    /**
     * 处理完成时间
     */
    @Column(name="CompleteTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp completeTime;

    /**
     * 交班时间
     */
    @Column(name="ChangeDutyTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp changeDutyTime;

    public Timestamp getChangeDutyTime() {
        return changeDutyTime;
    }

    public void setChangeDutyTime(Timestamp changeDutyTime) {
        this.changeDutyTime = changeDutyTime;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    public Integer getEnvenType() {
        return envenType;
    }

    public void setEnvenType(Integer envenType) {
        this.envenType = envenType;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getOpCount() {
        return opCount;
    }

    public void setOpCount(Integer opCount) {
        this.opCount = opCount;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public Integer getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(Integer noticeState) {
        this.noticeState = noticeState;
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

    public Timestamp getFirstDealTime() {
        return firstDealTime;
    }

    public void setFirstDealTime(Timestamp firstDealTime) {
        this.firstDealTime = firstDealTime;
    }

    public Timestamp getFirstCallTime() {
        return firstCallTime;
    }

    public void setFirstCallTime(Timestamp firstCallTime) {
        this.firstCallTime = firstCallTime;
    }

    public Timestamp getStartDealTIme() {
        return startDealTIme;
    }

    public void setStartDealTIme(Timestamp startDealTIme) {
        this.startDealTIme = startDealTIme;
    }

    public Timestamp getAppointedProcessTime() {
        return appointedProcessTime;
    }

    public void setAppointedProcessTime(Timestamp appointedProcessTime) {
        this.appointedProcessTime = appointedProcessTime;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
}
