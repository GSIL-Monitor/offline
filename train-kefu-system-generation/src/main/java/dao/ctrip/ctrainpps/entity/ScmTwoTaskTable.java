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
@Table(name="scm_two_task_table")
public class ScmTwoTaskTable implements DalPojo {

    // 自增长列
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 合作方订单号
	@Column(name="partnerOrderId")
	@Type(value=Types.VARCHAR)
	private String partnerOrderId;

    // 订单号
	@Column(name="orderid")
	@Type(value=Types.BIGINT)
	private Long orderid;

    // 合作方名称
	@Column(name="partnerName")
	@Type(value=Types.VARCHAR)
	private String partnerName;

    // 终端类型 0:PC/携程高铁 1：手机/wap/携程无线 2：H5/mwap
	@Column(name="terminalType")
	@Type(value=Types.INTEGER)
	private Integer terminalType;

    // 订单金额
	@Column(name="orderprice")
	@Type(value=Types.DECIMAL)
	private BigDecimal orderprice;

    // 附加产品金额
	@Column(name="addonProductsPrice")
	@Type(value=Types.DECIMAL)
	private BigDecimal addonProductsPrice;

    // 订单类型：0：配送票、1：团购订单、2：电子票
	@Column(name="orderType")
	@Type(value=Types.INTEGER)
	private Integer orderType;

    // 车票类型 -0单程票 1 往返票 2 联程票
	@Column(name="ticketType")
	@Type(value=Types.INTEGER)
	private Integer ticketType;

    // 是否有购买附加产品(二进制) 00无，01保险 10 游票
	@Column(name="addonProductsFlag")
	@Type(value=Types.INTEGER)
	private Integer addonProductsFlag;

    // userid
	@Column(name="userid")
	@Type(value=Types.VARCHAR)
	private String userid;

    // 联系人手机
	@Column(name="ContactMobile")
	@Type(value=Types.VARCHAR)
	private String contactMobile;

    // 最后更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 创建时间
	@Column(name="create_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 处理人
	@Column(name="operator")
	@Type(value=Types.VARCHAR)
	private String operator;

    // 处理状态：0：系统初选过滤，无效，1：未处理,2：可推荐,3：推荐中,4：推荐成功,5：推荐失败,6：暂缓,7：系统释放,8：人工释放,9：已分配,10：释放失败，11：待释放，12：等待自助
	@Column(name="processing_state")
	@Type(value=Types.VARCHAR)
	private String processingState;

    // 推荐类型：1，人工推荐配送票，2，自助配送票
	@Column(name="processing_type")
	@Type(value=Types.VARCHAR)
	private String processingType;

    // 人工转自助
	@Column(name="artificialChangeSelf")
	@Type(value=Types.INTEGER)
	private Integer artificialChangeSelf;

    // 转自助时间
	@Column(name="changeSelfTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp changeSelfTime;

    // 是否取消订单或者退款，1无退款，2有
	@Column(name="IsCancelOrReturn")
	@Type(value=Types.INTEGER)
	private Integer isCancelOrReturn;

    // 开始处理时间
	@Column(name="start_processing_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp startProcessingTime;

    // 处理结束时间
	@Column(name="end_processing_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp endProcessingTime;

    // 处理备注
	@Column(name="processing_remark")
	@Type(value=Types.VARCHAR)
	private String processingRemark;

    // 推荐类型1：只可柜台票，2只可配送票，3既可柜台又可配送票
	@Column(name="DeliverType")
	@Type(value=Types.INTEGER)
	private Integer deliverType;

    // 推荐类型：0 电子票、单程 1私人订制 2电子票联程
	@Column(name="RecommendType")
	@Type(value=Types.SMALLINT)
	private Integer recommendType;

    // 操作人 0 默认 1客服 2 自助 3 系统
	@Column(name="OpSource")
	@Type(value=Types.SMALLINT)
	private Integer opSource;

    // 操作来源0:未知 10人工拒绝 11人工释放12人工接受 20自助拒绝21自助接受  30超时释放31余票不足释放32配送时效不满足释放 33超出工作时间释放34自助超时释放
	@Column(name="OpReason")
	@Type(value=Types.SMALLINT)
	private Integer opReason;

    // 拉单客服（第一次拉单即计入，后不更改）
	@Column(name="OpStaff")
	@Type(value=Types.VARCHAR)
	private String opStaff;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public BigDecimal getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(BigDecimal orderprice) {
		this.orderprice = orderprice;
	}

	public BigDecimal getAddonProductsPrice() {
		return addonProductsPrice;
	}

	public void setAddonProductsPrice(BigDecimal addonProductsPrice) {
		this.addonProductsPrice = addonProductsPrice;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getAddonProductsFlag() {
		return addonProductsFlag;
	}

	public void setAddonProductsFlag(Integer addonProductsFlag) {
		this.addonProductsFlag = addonProductsFlag;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getProcessingState() {
		return processingState;
	}

	public void setProcessingState(String processingState) {
		this.processingState = processingState;
	}

	public String getProcessingType() {
		return processingType;
	}

	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}

	public Integer getArtificialChangeSelf() {
		return artificialChangeSelf;
	}

	public void setArtificialChangeSelf(Integer artificialChangeSelf) {
		this.artificialChangeSelf = artificialChangeSelf;
	}

	public Timestamp getChangeSelfTime() {
		return changeSelfTime;
	}

	public void setChangeSelfTime(Timestamp changeSelfTime) {
		this.changeSelfTime = changeSelfTime;
	}

	public Integer getIsCancelOrReturn() {
		return isCancelOrReturn;
	}

	public void setIsCancelOrReturn(Integer isCancelOrReturn) {
		this.isCancelOrReturn = isCancelOrReturn;
	}

	public Timestamp getStartProcessingTime() {
		return startProcessingTime;
	}

	public void setStartProcessingTime(Timestamp startProcessingTime) {
		this.startProcessingTime = startProcessingTime;
	}

	public Timestamp getEndProcessingTime() {
		return endProcessingTime;
	}

	public void setEndProcessingTime(Timestamp endProcessingTime) {
		this.endProcessingTime = endProcessingTime;
	}

	public String getProcessingRemark() {
		return processingRemark;
	}

	public void setProcessingRemark(String processingRemark) {
		this.processingRemark = processingRemark;
	}

	public Integer getDeliverType() {
		return deliverType;
	}

	public void setDeliverType(Integer deliverType) {
		this.deliverType = deliverType;
	}

	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public Integer getOpSource() {
		return opSource;
	}

	public void setOpSource(Integer opSource) {
		this.opSource = opSource;
	}

	public Integer getOpReason() {
		return opReason;
	}

	public void setOpReason(Integer opReason) {
		this.opReason = opReason;
	}

	public String getOpStaff() {
		return opStaff;
	}

	public void setOpStaff(String opStaff) {
		this.opStaff = opStaff;
	}

}