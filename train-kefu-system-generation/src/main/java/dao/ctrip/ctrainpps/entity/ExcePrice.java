package dao.ctrip.ctrainpps.entity;

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
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

@Entity
@Database(name="ctrainppsdb")
@Table(name="exce_price")
public class ExcePrice implements DalPojo {

    // 自增ID
	@Id
	@Column(name="ExceID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.INTEGER)
	private Long exceID;

    // 下单时间
	@Column(name="OrderDate")
	@Type(value=Types.TIMESTAMP)
	private Timestamp orderDate;

    // 创建日期
	@Column(name="CreateDate")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createDate;

    // 渠道
	@Column(name="Channel")
	@Type(value=Types.VARCHAR)
	private String channel;

    // 录入人
	@Column(name="InsertName")
	@Type(value=Types.VARCHAR)
	private String insertName;

    // 旅程方式
	@Column(name="Journey")
	@Type(value=Types.VARCHAR)
	private String journey;

    // 子订单号
	@Column(name="Billid")
	@Type(value=Types.VARCHAR)
	private String billid;

    // 订单号
	@Column(name="OrderNumber")
	@Type(value=Types.VARCHAR)
	private String orderNumber;

    // 所属票台
	@Column(name="TicketDesk")
	@Type(value=Types.VARCHAR)
	private String ticketDesk;

    // 合作快递
	@Column(name="Delivery")
	@Type(value=Types.INTEGER)
	private Integer delivery;

    // 申请部门
	@Column(name="Department")
	@Type(value=Types.INTEGER)
	private Integer department;

    // 责任方
	@Column(name="Responsibility")
	@Type(value=Types.INTEGER)
	private Integer responsibility;

    // 责任方问题
	@Column(name="ResponsQuestion")
	@Type(value=Types.INTEGER)
	private Integer responsQuestion;

    // 责任方原因
	@Column(name="ResponsReason")
	@Type(value=Types.INTEGER)
	private Integer responsReason;

    // 问题描述
	@Column(name="QuestionDesc")
	@Type(value=Types.VARCHAR)
	private String questionDesc;

    // 操作类型
	@Column(name="OperatingType")
	@Type(value=Types.INTEGER)
	private Integer operatingType;

    // 补退款方式
	@Column(name="RefundType")
	@Type(value=Types.INTEGER)
	private Integer refundType;

    // 票款
	@Column(name="TicketPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal ticketPrice;

    // 代购费
	@Column(name="SalefeePrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal salefeePrice;

    // 快递费
	@Column(name="DeliveryPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal deliveryPrice;

    // 保险费
	@Column(name="SafetyPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal safetyPrice;

    // 额外赔偿
	@Column(name="OtherPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal otherPrice;

    // 合计金额
	@Column(name="TotalPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal totalPrice;

    // 回款金额
	@Column(name="BackPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal backPrice;

    // 赔偿金额
	@Column(name="RepaPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal repaPrice;

    // 赔偿金额类型
	@Column(name="RepaPriceTpye")
	@Type(value=Types.INTEGER)
	private Integer repaPriceTpye;

    // 收款公司
	@Column(name="AccpetCompany")
	@Type(value=Types.INTEGER)
	private Integer accpetCompany;

    // 收款方
	@Column(name="AccpetName")
	@Type(value=Types.INTEGER)
	private Integer accpetName;

    // 回款原因
	@Column(name="BackReason")
	@Type(value=Types.INTEGER)
	private Integer backReason;

    // 审核日期
	@Column(name="AuditDate")
	@Type(value=Types.TIMESTAMP)
	private Timestamp auditDate;

    // 审核人
	@Column(name="AuditName")
	@Type(value=Types.VARCHAR)
	private String auditName;

    // 审核原因
	@Column(name="AuditReason")
	@Type(value=Types.VARCHAR)
	private String auditReason;

    // 是否审核
	@Column(name="IsAudit")
	@Type(value=Types.INTEGER)
	private Integer isAudit;

    // 是否删除
	@Column(name="IsDelete")
	@Type(value=Types.INTEGER)
	private Integer isDelete;

    // 最后更新时间
	@Column(name="UpdateDate")
	@Type(value=Types.TIMESTAMP)
	private Timestamp updateDate;

    // 最后更新人
	@Column(name="UpdateName")
	@Type(value=Types.VARCHAR)
	private String updateName;

    // 审核拒绝备注
	@Column(name="Remark")
	@Type(value=Types.VARCHAR)
	private String remark;

    // 产品线:1:火车,2:汽车，3：欧铁
	@Column(name="Product_Line")
	@Type(value=Types.VARCHAR)
	private String productLine;

    // 银行卡号
	@Column(name="BankNo")
	@Type(value=Types.VARCHAR)
	private String bankNo;

    // 开户行名称,如招商银行
	@Column(name="BankName")
	@Type(value=Types.VARCHAR)
	private String bankName;

    // 开户行地址（某某支行）
	@Column(name="BankAddress")
	@Type(value=Types.VARCHAR)
	private String bankAddress;

    // 收款人姓名
	@Column(name="BankAccountName")
	@Type(value=Types.VARCHAR)
	private String bankAccountName;

    // 付款唯一标示
	@Column(name="Referenceid")
	@Type(value=Types.VARCHAR)
	private String referenceid;

    // 是否是携程钱包用户 1是,0否
	@Column(name="IsCtripWalletUser")
	@Type(value=Types.SMALLINT)
	private Integer isCtripWalletUser;

    // 携程用户UID
	@Column(name="CtripUid")
	@Type(value=Types.VARCHAR)
	private String ctripUid;

    // 携程用户姓名
	@Column(name="CtripUserName")
	@Type(value=Types.VARCHAR)
	private String ctripUserName;

    // 线下自动退款标记: 0 未退 1 退款成功 2 退款失败
	@Column(name="AutoRefundStatus")
	@Type(value=Types.SMALLINT)
	private Integer autoRefundStatus;

    // 去哪儿赔付数据
	@Column(name="QunarData")
	@Type(value=Types.VARCHAR)
	private String qunarData;

    // 附加标志     1：到场无票
	@Column(name="AppendFlag")
	@Type(value=Types.INTEGER)
	private Integer appendFlag;

    // 保单号列表，分号“;”分割
	@Column(name="InsuranceNos")
	@Type(value=Types.VARCHAR)
	private String insuranceNos;

    // 代售点编号
	@Column(name="gsdmCode")
	@Type(value=Types.VARCHAR)
	private String gsdmCode;

    // 代售点名称
	@Column(name="gsdmName")
	@Type(value=Types.VARCHAR)
	private String gsdmName;

    // 收款方代售点code
	@Column(name="AcceptgsdmCode")
	@Type(value=Types.VARCHAR)
	private String acceptgsdmCode;

    // 收款方代售点名称
	@Column(name="AcceptgsdmName")
	@Type(value=Types.VARCHAR)
	private String acceptgsdmName;

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

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTicketDesk() {
		return ticketDesk;
	}

	public void setTicketDesk(String ticketDesk) {
		this.ticketDesk = ticketDesk;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
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

	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public BigDecimal getSafetyPrice() {
		return safetyPrice;
	}

	public void setSafetyPrice(BigDecimal safetyPrice) {
		this.safetyPrice = safetyPrice;
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

	public BigDecimal getBackPrice() {
		return backPrice;
	}

	public void setBackPrice(BigDecimal backPrice) {
		this.backPrice = backPrice;
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

	public Integer getIsCtripWalletUser() {
		return isCtripWalletUser;
	}

	public void setIsCtripWalletUser(Integer isCtripWalletUser) {
		this.isCtripWalletUser = isCtripWalletUser;
	}

	public String getCtripUid() {
		return ctripUid;
	}

	public void setCtripUid(String ctripUid) {
		this.ctripUid = ctripUid;
	}

	public String getCtripUserName() {
		return ctripUserName;
	}

	public void setCtripUserName(String ctripUserName) {
		this.ctripUserName = ctripUserName;
	}

	public Integer getAutoRefundStatus() {
		return autoRefundStatus;
	}

	public void setAutoRefundStatus(Integer autoRefundStatus) {
		this.autoRefundStatus = autoRefundStatus;
	}

	public String getQunarData() {
		return qunarData;
	}

	public void setQunarData(String qunarData) {
		this.qunarData = qunarData;
	}

	public Integer getAppendFlag() {
		return appendFlag;
	}

	public void setAppendFlag(Integer appendFlag) {
		this.appendFlag = appendFlag;
	}

	public String getInsuranceNos() {
		return insuranceNos;
	}

	public void setInsuranceNos(String insuranceNos) {
		this.insuranceNos = insuranceNos;
	}

	public String getGsdmCode() {
		return gsdmCode;
	}

	public void setGsdmCode(String gsdmCode) {
		this.gsdmCode = gsdmCode;
	}

	public String getGsdmName() {
		return gsdmName;
	}

	public void setGsdmName(String gsdmName) {
		this.gsdmName = gsdmName;
	}

	public String getAcceptgsdmCode() {
		return acceptgsdmCode;
	}

	public void setAcceptgsdmCode(String acceptgsdmCode) {
		this.acceptgsdmCode = acceptgsdmCode;
	}

	public String getAcceptgsdmName() {
		return acceptgsdmName;
	}

	public void setAcceptgsdmName(String acceptgsdmName) {
		this.acceptgsdmName = acceptgsdmName;
	}

}