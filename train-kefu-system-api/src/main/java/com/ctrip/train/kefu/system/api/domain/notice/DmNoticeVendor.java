package com.ctrip.train.kefu.system.api.domain.notice;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@Entity
public class DmNoticeVendor {

    /**
     * 主键
     */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value=Types.BIGINT)
    private  Long  id;

    /**
     * 通知ID
     */
    @Column(name="noticeId")
    @Type(value= Types.VARCHAR)
    private  Long noticeId;

    /**
     * 紧急程度
     */
    @Column(name="emergeState")
    @Type(value= Types.INTEGER)
    private  Integer emergeState;

    /**
     * 订单id
     */
    @Column(name="orderId")
    @Type(value= Types.VARCHAR)
    private  String orderId;

    /**
     * 通知类型
     */
    @Column(name="noticeType")
    @Type(value=Types.INTEGER)
    private Integer noticeType;


    /**
     * 通知状态,待处理-80； 处理中-81； 暂缓-82； 已解决-83
     */
    @Column(name="noticeState")
    @Type(value=Types.INTEGER)
    private Integer noticeState;

    /**
     * 催次数
     */
    @Column(name="opCount")
    @Type(value=Types.INTEGER)
    private Integer opCount;

    /**
     * 删除：1；未删除：0；
     */
    @Column(name="isDelete")
    @Type(value=Types.INTEGER)
    private Integer isDelete;

    /**
     * 通知二类 重构后：也表示投诉二级类型
     */
    @Column(name="noticeSecondType")
    @Type(value=Types.INTEGER)
    private Integer noticeSecondType;

    /**
     * 供应商code
     */
    @Column(name="verdorCode")
    @Type(value= Types.VARCHAR)
    private String verdorCode;

    /**
     * 供应商姓名
     */
    @Column(name="verdorName")
    @Type(value= Types.VARCHAR)
    private String verdorName;

    /**
     * 备注
     */
    @Column(name="contents")
    @Type(value= Types.VARCHAR)
    private String contents;


    /**
     * 发送人类型 1，客服，2，供应商
     */
    @Column(name="sendType")
    @Type(value= Types.INTEGER)
    private Integer sendType;

    /**
     * 发送人编号
     */
    @Column(name="sendCode")
    @Type(value= Types.VARCHAR)
    private String sendCode;

    /**
     * 发送人姓名
     */
    @Column(name="sendName")
    @Type(value= Types.VARCHAR)
    private String sendName;

    /**
     * 发送时间
     */
    @Column(name="sendTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp sendTime;

    /**
     * 预约回电时间
     */
    @Column(name="appointedProcessTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp appointedProcessTime;

    /**
     * 完成时间
     */
    @Column(name="completeTime")
    @Type(value=Types.TIMESTAMP)
    private Timestamp completeTime;

    /**
     * 最新处理人
     */
    @Column(name = "OpUser")
    @Type(value = Types.VARCHAR)
    private String opUser;

    /**
     * 处理人类型
     */
    @Column(name = "OpUserType")
    @Type(value = Types.SMALLINT)
    private Integer opUserType;

    /**
     * 处理时间
     */
    @Column(name = "OpTime")
    @Type(value = Types.TIMESTAMP)
    private Timestamp opTime;

    /**
     * 二线通知处理客服高工号
     */
    @Column(name = "OpUserNum")
    @Type(value = Types.VARCHAR)
    private String opUserNum;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getEmergeState() {
        return emergeState;
    }

    public void setEmergeState(Integer emergeState) {
        this.emergeState = emergeState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(Integer noticeState) {
        this.noticeState = noticeState;
    }

    public Integer getOpCount() {
        return opCount;
    }

    public void setOpCount(Integer opCount) {
        this.opCount = opCount;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getNoticeSecondType() {
        return noticeSecondType;
    }

    public void setNoticeSecondType(Integer noticeSecondType) {
        this.noticeSecondType = noticeSecondType;
    }

    public String getVerdorCode() {
        return verdorCode;
    }

    public void setVerdorCode(String verdorCode) {
        this.verdorCode = verdorCode;
    }

    public String getVerdorName() {
        return verdorName;
    }

    public void setVerdorName(String verdorName) {
        this.verdorName = verdorName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Timestamp getAppointedProcessTime() {
        return appointedProcessTime;
    }

    public void setAppointedProcessTime(Timestamp appointedProcessTime) {
        this.appointedProcessTime = appointedProcessTime;
    }

    public Timestamp getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getOpUserNum() {
        return opUserNum;
    }

    public void setOpUserNum(String opUserNum) {
        this.opUserNum = opUserNum;
    }

    public Timestamp getOpTime() {
        return opTime;
    }

    public void setOpTime(Timestamp opTime) {
        this.opTime = opTime;
    }

    public Integer getOpUserType() {
        return opUserType;
    }

    public void setOpUserType(Integer opUserType) {
        this.opUserType = opUserType;
    }
}
