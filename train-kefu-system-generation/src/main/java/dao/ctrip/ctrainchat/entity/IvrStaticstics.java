package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author lsy刘书亚
 * @date 2018-11-22
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "ivr_staticstics")
public class IvrStaticstics implements DalPojo {

    /**
     * 主键
     */
    @Id
    @Column(name = "Tid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private Long tid;

    /**
     * 订单号
     */
    @Column(name = "OrderNumber")
    @Type(value = Types.VARCHAR)
    private String orderNumber;

    /**
     * 渠道 如:火车票 国际火车票
     */
    @Column(name = "Channel")
    @Type(value = Types.VARCHAR)
    private String channel;

    /**
     * 子渠道 如:智行, 铁友, 携程
     */
    @Column(name = "SubChannel")
    @Type(value = Types.VARCHAR)
    private String subChannel;

    /**
     * 呼叫ID
     */
    @Column(name = "CallID")
    @Type(value = Types.VARCHAR)
    private String callID;

    /**
     * 呼叫号码
     */
    @Column(name = "CallNumber")
    @Type(value = Types.VARCHAR)
    private String callNumber;

    /**
     * 操作类型编号
     */
    @Column(name = "OperTag")
    @Type(value = Types.INTEGER)
    private Integer operTag;

    /**
     * 操作类型
     */
    @Column(name = "OpTagString")
    @Type(value = Types.VARCHAR)
    private String opTagString;

    /**
     * 语音播报类型 1：语音播报 2 TTS播报
     */
    @Column(name = "MsgType")
    @Type(value = Types.INTEGER)
    private Integer msgType;

    /**
     * 语音播报内容ID
     */
    @Column(name = "MsgCode")
    @Type(value = Types.INTEGER)
    private Integer msgCode;

    /**
     * 语音播报内容
     */
    @Column(name = "MsgContent")
    @Type(value = Types.VARCHAR)
    private String msgContent;

    /**
     * 按键 10代表* 11 代理#
     */
    @Column(name = "DTMF")
    @Type(value = Types.INTEGER)
    private Integer dTMF;

    /**
     * 操作类型
     */
    @Column(name = "Operator")
    @Type(value = Types.VARCHAR)
    private String operator;

    /**
     * 用户呼叫时间
     */
    @Column(name = "CallDttm")
    @Type(value = Types.TIMESTAMP)
    private Timestamp callDttm;

    /**
     * 备注
     */
    @Column(name = "Remark")
    @Type(value = Types.VARCHAR)
    private String remark;

    /**
     * 更新时间
     */
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    /**
     * 订单类型
     */
    @Column(name = "OrderType")
    @Type(value = Types.SMALLINT)
    private Integer orderType;

    /**
     * VIP标记
     */
    @Column(name = "VipFlag")
    @Type(value = Types.SMALLINT)
    private Integer vipFlag;

    /**
     * 订单状态
     */
    @Column(name = "OrderState")
    @Type(value = Types.SMALLINT)
    private Integer orderState;

    /**
     * 退票状态
     */
    @Column(name = "RefundStatus")
    @Type(value = Types.SMALLINT)
    private Integer refundStatus;

    /**
     * 转接人工类型
     */
    @Column(name = "TransferType")
    @Type(value = Types.SMALLINT)
    private Integer transferType;

    /**
     * 用户等级
     */
    @Column(name = "UserGrade")
    @Type(value = Types.SMALLINT)
    private Integer userGrade;

    /**
     * 场景值
     */
    @Column(name = "CaseValue")
    @Type(value = Types.VARCHAR)
    private String caseValue;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSubChannel() {
        return subChannel;
    }

    public void setSubChannel(String subChannel) {
        this.subChannel = subChannel;
    }

    public String getCallID() {
        return callID;
    }

    public void setCallID(String callID) {
        this.callID = callID;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Integer getOperTag() {
        return operTag;
    }

    public void setOperTag(Integer operTag) {
        this.operTag = operTag;
    }

    public String getOpTagString() {
        return opTagString;
    }

    public void setOpTagString(String opTagString) {
        this.opTagString = opTagString;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getDTMF() {
        return dTMF;
    }

    public void setDTMF(Integer dTMF) {
        this.dTMF = dTMF;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Timestamp getCallDttm() {
        return callDttm;
    }

    public void setCallDttm(Timestamp callDttm) {
        this.callDttm = callDttm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Timestamp datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getVipFlag() {
        return vipFlag;
    }

    public void setVipFlag(Integer vipFlag) {
        this.vipFlag = vipFlag;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public String getCaseValue() {
        return caseValue;
    }

    public void setCaseValue(String caseValue) {
        this.caseValue = caseValue;
    }

}