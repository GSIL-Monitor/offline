package com.ctrip.train.kefu.system.offline.order.vm;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExcePriceEx {

    /**
     * 主键
     */
    private Long exceID;

/**
 * 下单时间
 * */
    private Timestamp orderDate;

/**
 * 创建日期
 * */
    private Timestamp createDate;

    /**
     * 渠道
     * */
    private String channel;

    /**
     * 录入人
     * */
    private String insertName;

    /**
     * 旅程方式
     * */
    private String journey;

    /**
     * 订单号
     * */
    private String orderNumber;

    /**
     * 责任方
     * */
    private Integer responsibility;

    /**
     * 责任方问题
     * */
    private Integer responsQuestion;

    /**
     * 责任方原因
     * */
    private Integer responsReason;

    /**
     * 问题描述
     * */
    private String questionDesc;

    /**
     * 操作类型
     * */
    private Integer operatingType;

    /**
     * 补退款方式
     * */
    private Integer refundType;

    /**
     * 票款
     * */
    private BigDecimal ticketPrice;

    /**
     * 代购费
     * */
    private BigDecimal salefeePrice;

    /**
     * 额外赔偿
     * */
    private BigDecimal otherPrice;

    /**
     * 合计金额
     * */
    private BigDecimal totalPrice;


    /**
     * 赔偿金额
     * */
    private BigDecimal repaPrice;

    /**
     * 赔偿金额类型
     * */
    private Integer repaPriceTpye;

    /**
     * 收款公司
     * */
    private Integer accpetCompany;

    /**
     * 收款方
     * */
    private Integer accpetName;

    /**
     * 回款原因
     * */
    private Integer backReason;

    /**
     * 审核日期
     * */
    private Timestamp auditDate;

    /**
     * 审核人
     * */
    private String auditName;

    /**
     * 审核原因
     * */
    private String auditReason;

    /**
     * 是否审核
     * */
    private Integer isAudit;

    /**
     * 是否删除
     * */
    private Integer isDelete;

    /**
     * 最后更新时间
     * */
    private Timestamp updateDate;

    /**
     * 最后更新人
     * */
    private String updateName;

    /**
     * 审核拒绝备注
     * */
    private String remark;

    /**
     * 产品线:1:火车,2:汽车，3：欧铁
     * */
    private String productLine;

    /**
     * 银行卡号
     * */
    private String bankNo;

    /**
     * 开户行名称,如招商银行
     * */
    private String bankName;

    /**
     * 开户行地址（某某支行）
     * */
    private String bankAddress;

    /**
     * 收款人姓名
     * */
    private String bankAccountName;

    /**
     * 付款唯一标示
     * */
    private String referenceid;

    /**
     * 线下自动退款标记: 0 未退 1 退款成功 2 退款失败
     * */
    private Integer autoRefundStatus;

    /**
     * 退款方式 1 退款 3 损失金额
     */
    private String typePrice;

    /**
     * 存供应商名称
     */
    private String gsdmName;

    public Long getExceID() {
        return exceID;
    }

    public void setExceID(Long exceID) {
        this.exceID = exceID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInsertName() {
        return insertName;
    }

    public void setInsertName(String insertName) {
        this.insertName = insertName;
    }

    public String getJourney() {
        return journey;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Integer responsibility) {
        this.responsibility = responsibility;
    }

    public Integer getResponsQuestion() {
        return responsQuestion;
    }

    public void setResponsQuestion(Integer responsQuestion) {
        this.responsQuestion = responsQuestion;
    }

    public Integer getResponsReason() {
        return responsReason;
    }

    public void setResponsReason(Integer responsReason) {
        this.responsReason = responsReason;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public Integer getOperatingType() {
        return operatingType;
    }

    public void setOperatingType(Integer operatingType) {
        this.operatingType = operatingType;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public BigDecimal getSalefeePrice() {
        return salefeePrice;
    }

    public void setSalefeePrice(BigDecimal salefeePrice) {
        this.salefeePrice = salefeePrice;
    }

    public BigDecimal getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(BigDecimal otherPrice) {
        this.otherPrice = otherPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getRepaPrice() {
        return repaPrice;
    }

    public void setRepaPrice(BigDecimal repaPrice) {
        this.repaPrice = repaPrice;
    }

    public Integer getRepaPriceTpye() {
        return repaPriceTpye;
    }

    public void setRepaPriceTpye(Integer repaPriceTpye) {
        this.repaPriceTpye = repaPriceTpye;
    }

    public Integer getAccpetCompany() {
        return accpetCompany;
    }

    public void setAccpetCompany(Integer accpetCompany) {
        this.accpetCompany = accpetCompany;
    }

    public Integer getAccpetName() {
        return accpetName;
    }

    public void setAccpetName(Integer accpetName) {
        this.accpetName = accpetName;
    }

    public Integer getBackReason() {
        return backReason;
    }

    public void setBackReason(Integer backReason) {
        this.backReason = backReason;
    }

    public Timestamp getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Timestamp auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }

    public Integer getAutoRefundStatus() {
        return autoRefundStatus;
    }

    public void setAutoRefundStatus(Integer autoRefundStatus) {this.autoRefundStatus = autoRefundStatus;}

    public String getTypePrice()  {
        return typePrice;
    }

    public void setTypePrice(String typePrice) {this.typePrice = typePrice;}

    public String getGsdmName() {
        return gsdmName;
    }

    public void setGsdmName(String gsdmName) {
        this.gsdmName = gsdmName;
    }
}
