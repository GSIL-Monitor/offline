package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Sensitive;
import com.ctrip.platform.dal.dao.annotation.Type;
import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author jj季健(IT)
 * @date 2018-12-04
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "ivr_cdrsub")
public class IvrCdrsub implements DalPojo {

    /**
     * 序列号
     */
    @Id
    @Column(name = "SerialNo")
    @Type(value = Types.BIGINT)
    private Long serialNo;

    /**
     * callid
     */
    @Column(name = "MainCID")
    @Type(value = Types.VARCHAR)
    private String mainCID;

    /**
     * SubCID
     */
    @Column(name = "SubCID")
    @Type(value = Types.VARCHAR)
    private String subCID;

    /**
     * 设备号码
     */
    @Column(name = "DeviceID")
    @Type(value = Types.VARCHAR)
    private String deviceID;

    /**
     * 主叫（客户打入的手机号）
     */
    @Column(name = "Ani_No")
    @Type(value = Types.VARCHAR)
    private String aniNo;

    /**
     * 被叫
     */
    @Column(name = "Dnis_No")
    @Type(value = Types.VARCHAR)
    private String dnisNo;

    /**
     * 设备类型0：分机，2：VDN
     */
    @Column(name = "Device_Type")
    @Type(value = Types.VARCHAR)
    private String deviceType;

    /**
     * 操作员号码
     */
    @Column(name = "Operator_No")
    @Type(value = Types.VARCHAR)
    private String operatorNo;

    /**
     * 电话类型 D：呼入电话 O:呼出电话
     */
    @Column(name = "Call_Type")
    @Type(value = Types.VARCHAR)
    private String callType;

    /**
     * 电话落到设备的时刻
     */
    @Column(name = "Time_In")
    @Type(value = Types.TIMESTAMP)
    private Timestamp timeIn;

    /**
     * 电话在设备停留的时间
     */
    @Column(name = "Time_Out")
    @Type(value = Types.SMALLINT)
    private Integer timeOut;

    /**
     * 接通时间 一般 50 and 310
     */
    @Column(name = "Time_To_Alert")
    @Type(value = Types.SMALLINT)
    private Integer timeToAlert;

    /**
     * 振铃时间
     */
    @Column(name = "Time_To_Answer")
    @Type(value = Types.SMALLINT)
    private Integer timeToAnswer;

    /**
     * 排队时间
     */
    @Column(name = "Time_To_QUEUE")
    @Type(value = Types.SMALLINT)
    private Integer timeToQueue;

    /**
     * 终止原因( B：设备不可用C：被叫不可用E：其它 I：网络不可用M：正常N：线路不可用W：CTI link不可用)
     */
    @Column(name = "Term_Reason")
    @Type(value = Types.VARCHAR)
    private String termReason;

    /**
     * 上级VDN
     */
    @Column(name = "VDN")
    @Type(value = Types.VARCHAR)
    private String vDN;

    /**
     * 释放号码
     */
    @Column(name = "Release_No")
    @Type(value = Types.VARCHAR)
    private String releaseNo;

    /**
     * 释放状态(A：振铃 C：连接 F：失败H：保持 I：初始化O：外呼 Q：排队)
     */
    @Column(name = "Release_State")
    @Type(value = Types.VARCHAR)
    private String releaseState;

    /**
     * 最后更新时间
     */
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public String getMainCID() {
        return mainCID;
    }

    public void setMainCID(String mainCID) {
        this.mainCID = mainCID;
    }

    public String getSubCID() {
        return subCID;
    }

    public void setSubCID(String subCID) {
        this.subCID = subCID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getAniNo() {
        return aniNo;
    }

    public void setAniNo(String aniNo) {
        this.aniNo = aniNo;
    }

    public String getDnisNo() {
        return dnisNo;
    }

    public void setDnisNo(String dnisNo) {
        this.dnisNo = dnisNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Timestamp getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Timestamp timeIn) {
        this.timeIn = timeIn;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getTimeToAlert() {
        return timeToAlert;
    }

    public void setTimeToAlert(Integer timeToAlert) {
        this.timeToAlert = timeToAlert;
    }

    public Integer getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(Integer timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    public Integer getTimeToQueue() {
        return timeToQueue;
    }

    public void setTimeToQueue(Integer timeToQueue) {
        this.timeToQueue = timeToQueue;
    }

    public String getTermReason() {
        return termReason;
    }

    public void setTermReason(String termReason) {
        this.termReason = termReason;
    }

    public String getVDN() {
        return vDN;
    }

    public void setVDN(String vDN) {
        this.vDN = vDN;
    }

    public String getReleaseNo() {
        return releaseNo;
    }

    public void setReleaseNo(String releaseNo) {
        this.releaseNo = releaseNo;
    }

    public String getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(String releaseState) {
        this.releaseState = releaseState;
    }

    public Timestamp getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Timestamp datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

}
