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
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author lsy刘书亚
 * @date 2018-12-20
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "activity_detail")
public class ActivityDetail implements DalPojo {

    /**
     * 主键id
     */
    @Id
    @Column(name = "Tid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private Long tid;

    /**
     * 活动类型,1信用卡营销返现活动,2火车票订单优惠券返现活动
     */
    @Column(name = "ActivityType")
    @Type(value = Types.SMALLINT)
    private Integer activityType;

    /**
     * ActivityType=1时为活动策略ID，ActivityType=2时为优惠券ID
     */
    @Column(name = "StrategyId")
    @Type(value = Types.VARCHAR)
    private String strategyId;

    /**
     * 用户id
     */
    @Column(name = "Uid")
    @Type(value = Types.VARCHAR)
    private String uid;

    /**
     * 订单ID
     */
    @Column(name = "OrderId")
    @Type(value = Types.VARCHAR)
    private String orderId;

    /**
     * 手机号码
     */
    @Column(name = "MobileNumber")
    @Type(value = Types.VARCHAR)
    private String mobileNumber;

    /**
     * 订单提交时间
     */
    @Column(name = "OrderTime")
    @Type(value = Types.TIMESTAMP)
    private Timestamp orderTime;

    /**
     * 订单渠道
     */
    @Column(name = "PartnerName")
    @Type(value = Types.VARCHAR)
    private String partnerName;

    /**
     * 订单金额
     */
    @Column(name = "OrderPrice")
    @Type(value = Types.DECIMAL)
    private BigDecimal orderPrice;

    /**
     * 是否必须大于金额
     */
    @Column(name = "IsGreateThan")
    @Type(value = Types.BIT)
    private Boolean isGreateThan;

    /**
     * 订单最小金额
     */
    @Column(name = "MinOrderAmount")
    @Type(value = Types.DECIMAL)
    private BigDecimal minOrderAmount;

    /**
     * 返现金额
     */
    @Column(name = "ReturnPrice")
    @Type(value = Types.DECIMAL)
    private BigDecimal returnPrice;

    /**
     * 活动描述
     */
    @Column(name = "ActivityDesc")
    @Type(value = Types.VARCHAR)
    private String activityDesc;

    /**
     * 状态 0待处理,1处理成功,2处理失败,3不满足条件无需处理
     */
    @Column(name = "Status")
    @Type(value = Types.SMALLINT)
    private Integer status;

    /**
     * 数据扩展
     */
    @Column(name = "Data_Ext")
    @Type(value = Types.VARCHAR)
    private String dataExt;

    /**
     * 最后更新时间
     */
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    /**
     * 活动名称：数据来源于活动接口对应字段
     */
    @Column(name = "DisplayName")
    @Type(value = Types.VARCHAR)
    private String displayName;

    /**
     * 打款方式：0：初始状态；1：线下退到携程钱包；2：线上原路退回
     */
    @Column(name = "PaymentWay")
    @Type(value = Types.SMALLINT)
    private Integer paymentWay;

    /**
     * 线下退钱包请求标识
     */
    @Column(name = "TransferOrderid")
    @Type(value = Types.VARCHAR)
    private String transferOrderid;

    /**
     * 申请时间
     */
    @Column(name = "ApplyDate")
    @Type(value = Types.TIMESTAMP)
    private Timestamp applyDate;

    /**
     * 回调时间
     */
    @Column(name = "CallBackDate")
    @Type(value = Types.TIMESTAMP)
    private Timestamp callBackDate;

    /**
     * 备注
     */
    @Column(name = "Remark")
    @Type(value = Types.VARCHAR)
    private String remark;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Boolean getIsGreateThan() {
        return isGreateThan;
    }

    public void setIsGreateThan(Boolean isGreateThan) {
        this.isGreateThan = isGreateThan;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public BigDecimal getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDataExt() {
        return dataExt;
    }

    public void setDataExt(String dataExt) {
        this.dataExt = dataExt;
    }

    public Timestamp getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Timestamp datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Integer paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getTransferOrderid() {
        return transferOrderid;
    }

    public void setTransferOrderid(String transferOrderid) {
        this.transferOrderid = transferOrderid;
    }

    public Timestamp getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Timestamp applyDate) {
        this.applyDate = applyDate;
    }

    public Timestamp getCallBackDate() {
        return callBackDate;
    }

    public void setCallBackDate(Timestamp callBackDate) {
        this.callBackDate = callBackDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
