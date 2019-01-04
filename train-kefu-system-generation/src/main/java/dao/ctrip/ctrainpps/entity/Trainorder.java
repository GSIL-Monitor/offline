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
@Database(name="ctrainfinancedb")
@Table(name="trainorder")
public class Trainorder implements DalPojo {

	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

	@Column(name="ShardId")
	@Type(value=Types.VARCHAR)
	private String shardId;

    // 订单号  ----高度注意，对迁移进来的数据携程的OrderID 不等于  PartnerOrderID
	@Column(name="orderId")
	@Type(value=Types.BIGINT)
	private Long orderId;

    // 订单类型：0：配送票、1：团购订单、2：电子票
	@Column(name="orderType")
	@Type(value=Types.INTEGER)
	private Integer orderType;

    // 终端类型 0:PC/携程高铁   1：手机/wap/携程无线    2：H5/mwap
	@Column(name="terminalType")
	@Type(value=Types.INTEGER)
	private Integer terminalType;

    // 合作方名称
	@Column(name="partnerName")
	@Type(value=Types.VARCHAR)
	private String partnerName;

    // 营销渠道
	@Column(name="channelName")
	@Type(value=Types.VARCHAR)
	private String channelName;

    // 合作方订单号
	@Column(name="partnerOrderId")
	@Type(value=Types.VARCHAR)
	private String partnerOrderId;

    // 订单金额
	@Column(name="orderPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal orderPrice;

    // 下单时间
	@Column(name="orderTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp orderTime;

    // 车票类型 -0单程票 1 往返票 2 联程票
	@Column(name="ticketType")
	@Type(value=Types.INTEGER)
	private Integer ticketType;

    // 联系人姓名
	@Column(name="ContactName")
	@Type(value=Types.VARCHAR)
	private String contactName;

    // 联系人电话
	@Column(name="ContactPhone")
	@Type(value=Types.VARCHAR)
	private String contactPhone;

    // 联系人手机
	@Column(name="ContactMobile")
	@Type(value=Types.VARCHAR)
	private String contactMobile;

    // 用户Id
	@Column(name="userID")
	@Type(value=Types.VARCHAR)
	private String userID;

    // 登陆名称
	@Column(name="loginName")
	@Type(value=Types.VARCHAR)
	private String loginName;

    // 订单标记位（二进制）01已删除
	@Column(name="orderFlag")
	@Type(value=Types.INTEGER)
	private Integer orderFlag;

    // 是否有购买附加产品(二进制) 00无，01保险 10 游票
	@Column(name="addonProductsFlag")
	@Type(value=Types.INTEGER)
	private Integer addonProductsFlag;

    // 是否参加活动 0无， 1有
	@Column(name="activityState")
	@Type(value=Types.INTEGER)
	private Integer activityState;

    // 活动名称
	@Column(name="Activity")
	@Type(value=Types.VARCHAR)
	private String activity;

    // 优惠券金额
	@Column(name="ticketOffsetFee")
	@Type(value=Types.DECIMAL)
	private BigDecimal ticketOffsetFee;

    // 是否经过有无票校验 0无   1有
	@Column(name="ticketCheckState")
	@Type(value=Types.VARCHAR)
	private String ticketCheckState;

    // 订单状态  1：待付款、2：待出票、3：已提取；4、待配送、5：已配送、6：已签收、7：已取消
	@Column(name="orderState")
	@Type(value=Types.INTEGER)
	private Integer orderState;

    // 创建时间
	@Column(name="createTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 支付状态 1：待付款,2：已付款,3：待退款,5：支付失败,6：已退款,7：支付处理中
	@Column(name="PayState")
	@Type(value=Types.INTEGER)
	private Integer payState;

    // 备注
	@Column(name="Comment")
	@Type(value=Types.VARCHAR)
	private String comment;

    // 语言
	@Column(name="Language")
	@Type(value=Types.VARCHAR)
	private String language;

    // 是否快速预订
	@Column(name="IsQuickBooking")
	@Type(value=Types.INTEGER)
	private Integer isQuickBooking;

    // 预订系统ID
	@Column(name="BookSourceID")
	@Type(value=Types.INTEGER)
	private Integer bookSourceID;

    // 预订系统-- 域名
	@Column(name="ServerFrom")
	@Type(value=Types.VARCHAR)
	private String serverFrom;

    // 是否邮寄发票
	@Column(name="InsuranceInvoice")
	@Type(value=Types.INTEGER)
	private Integer insuranceInvoice;

    // 预计出票时间
	@Column(name="ExpectedTicketTime")
	@Type(value=Types.VARCHAR)
	private String expectedTicketTime;

	@Column(name="expectedOperateTime")
	@Type(value=Types.VARCHAR)
	private String expectedOperateTime;

    // 支付成功的bill_no -----供携程使用 ：支付1.0 是transactionID,  支付2.0是BillNO
	@Column(name="billNo")
	@Type(value=Types.VARCHAR)
	private String billNo;

    // 联系人邮箱
	@Column(name="ContactEmail")
	@Type(value=Types.VARCHAR)
	private String contactEmail;

    // 12306账户
	@Column(name="electricAccount")
	@Type(value=Types.VARCHAR)
	private String electricAccount;

    // 12306密码
	@Column(name="electricPassword")
	@Type(value=Types.VARCHAR)
	private String electricPassword;

    // 12306cookie
	@Column(name="cookie12306")
	@Type(value=Types.VARCHAR)
	private String cookie12306;

    // 支付成功的支付版本
	@Column(name="PayVersion")
	@Type(value=Types.INTEGER)
	private Integer payVersion;

    // 商旅用户ID
	@Column(name="CorpId")
	@Type(value=Types.VARCHAR)
	private String corpId;

    // 商旅名称
	@Column(name="CorpName")
	@Type(value=Types.VARCHAR)
	private String corpName;

    // 是否客服已经联系过客户
	@Column(name="CommunicationState")
	@Type(value=Types.INTEGER)
	private Integer communicationState;

    // 买家IP--无线埋点，PC肯定要加
	@Column(name="buyerIP")
	@Type(value=Types.VARCHAR)
	private String buyerIP;

    // clientID-无线（发贴硬件特征）
	@Column(name="issueHardware")
	@Type(value=Types.VARCHAR)
	private String issueHardware;

    // 经度、维度无线
	@Column(name="GPS")
	@Type(value=Types.VARCHAR)
	private String gPS;

    // 系统型号（苹果，安卓,其他）-无线
	@Column(name="Mobile_Model")
	@Type(value=Types.VARCHAR)
	private String mobileModel;

    // 无线版本号
	@Column(name="AppVersion")
	@Type(value=Types.VARCHAR)
	private String appVersion;

    // 退票状态 1部分退票 2全部退票
	@Column(name="ReturnTicketState")
	@Type(value=Types.INTEGER)
	private Integer returnTicketState;

    // 订单标记
	@Column(name="orderFlagV2")
	@Type(value=Types.BIGINT)
	private Long orderFlagV2;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getShardId() {
		return shardId;
	}

	public void setShardId(String shardId) {
		this.shardId = shardId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	public Integer getAddonProductsFlag() {
		return addonProductsFlag;
	}

	public void setAddonProductsFlag(Integer addonProductsFlag) {
		this.addonProductsFlag = addonProductsFlag;
	}

	public Integer getActivityState() {
		return activityState;
	}

	public void setActivityState(Integer activityState) {
		this.activityState = activityState;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public BigDecimal getTicketOffsetFee() {
		return ticketOffsetFee;
	}

	public void setTicketOffsetFee(BigDecimal ticketOffsetFee) {
		this.ticketOffsetFee = ticketOffsetFee;
	}

	public String getTicketCheckState() {
		return ticketCheckState;
	}

	public void setTicketCheckState(String ticketCheckState) {
		this.ticketCheckState = ticketCheckState;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getIsQuickBooking() {
		return isQuickBooking;
	}

	public void setIsQuickBooking(Integer isQuickBooking) {
		this.isQuickBooking = isQuickBooking;
	}

	public Integer getBookSourceID() {
		return bookSourceID;
	}

	public void setBookSourceID(Integer bookSourceID) {
		this.bookSourceID = bookSourceID;
	}

	public String getServerFrom() {
		return serverFrom;
	}

	public void setServerFrom(String serverFrom) {
		this.serverFrom = serverFrom;
	}

	public Integer getInsuranceInvoice() {
		return insuranceInvoice;
	}

	public void setInsuranceInvoice(Integer insuranceInvoice) {
		this.insuranceInvoice = insuranceInvoice;
	}

	public String getExpectedTicketTime() {
		return expectedTicketTime;
	}

	public void setExpectedTicketTime(String expectedTicketTime) {
		this.expectedTicketTime = expectedTicketTime;
	}

	public String getExpectedOperateTime() {
		return expectedOperateTime;
	}

	public void setExpectedOperateTime(String expectedOperateTime) {
		this.expectedOperateTime = expectedOperateTime;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getElectricAccount() {
		return electricAccount;
	}

	public void setElectricAccount(String electricAccount) {
		this.electricAccount = electricAccount;
	}

	public String getElectricPassword() {
		return electricPassword;
	}

	public void setElectricPassword(String electricPassword) {
		this.electricPassword = electricPassword;
	}

	public String getCookie12306() {
		return cookie12306;
	}

	public void setCookie12306(String cookie12306) {
		this.cookie12306 = cookie12306;
	}

	public Integer getPayVersion() {
		return payVersion;
	}

	public void setPayVersion(Integer payVersion) {
		this.payVersion = payVersion;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public Integer getCommunicationState() {
		return communicationState;
	}

	public void setCommunicationState(Integer communicationState) {
		this.communicationState = communicationState;
	}

	public String getBuyerIP() {
		return buyerIP;
	}

	public void setBuyerIP(String buyerIP) {
		this.buyerIP = buyerIP;
	}

	public String getIssueHardware() {
		return issueHardware;
	}

	public void setIssueHardware(String issueHardware) {
		this.issueHardware = issueHardware;
	}

	public String getGPS() {
		return gPS;
	}

	public void setGPS(String gPS) {
		this.gPS = gPS;
	}

	public String getMobileModel() {
		return mobileModel;
	}

	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Integer getReturnTicketState() {
		return returnTicketState;
	}

	public void setReturnTicketState(Integer returnTicketState) {
		this.returnTicketState = returnTicketState;
	}

	public Long getOrderFlagV2() {
		return orderFlagV2;
	}

	public void setOrderFlagV2(Long orderFlagV2) {
		this.orderFlagV2 = orderFlagV2;
	}

}