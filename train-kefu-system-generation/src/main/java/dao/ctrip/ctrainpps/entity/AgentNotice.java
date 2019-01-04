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
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

@Entity
@Database(name="ctrainppsdb")
@Table(name="agent_notice")
public class AgentNotice implements DalPojo {

    // 主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long iD;

    // 订单号
	@Column(name="OrderID")
	@Type(value=Types.VARCHAR)
	private String orderID;

    // 通知类别
	@Column(name="NoticeType")
	@Type(value=Types.INTEGER)
	private Integer noticeType;

    // 客服主动发送通知类别
	@Column(name="ServiceNoticeType")
	@Type(value=Types.INTEGER)
	private Integer serviceNoticeType;

    // 紧急情况(一般：0；紧急：1；)
	@Column(name="EmergeState")
	@Type(value=Types.INTEGER)
	private Integer emergeState;

    // 客服处理状态，待处理，暂缓，处理中，已解决
	@Column(name="ServiceNoticeState")
	@Type(value=Types.INTEGER)
	private Integer serviceNoticeState;

    // 代售点处理状态，待处理0，已处理10
	@Column(name="AgentNoticeState")
	@Type(value=Types.INTEGER)
	private Integer agentNoticeState;

    // 代售点完成时间
	@Column(name="AgentEndTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp agentEndTime;

    // 处理人
	@Column(name="OpUser")
	@Type(value=Types.VARCHAR)
	private String opUser;

    // 处理完成时间
	@Column(name="OpEndTIme")
	@Type(value=Types.TIMESTAMP)
	private Timestamp opEndTIme;

    // 处理时间
	@Column(name="OpTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp opTime;

    // 催次数
	@Column(name="OpCount")
	@Type(value=Types.INTEGER)
	private Integer opCount;

    // 暂缓次数
	@Column(name="DeferCount")
	@Type(value=Types.INTEGER)
	private Integer deferCount;

    // 备注
	@Column(name="Remark")
	@Type(value=Types.VARCHAR)
	private String remark;

    // 创建时间
	@Column(name="CreateTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 创建人
	@Column(name="CreateOpr")
	@Type(value=Types.VARCHAR)
	private String createOpr;

    // 代售点代码
	@Column(name="AgentCode")
	@Type(value=Types.VARCHAR)
	private String agentCode;

    // 是否电联客户, 0 未选择 1是 2否
	@Column(name="NeedPhoneCall")
	@Type(value=Types.INTEGER)
	private Integer needPhoneCall;

    // 最后更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Integer getServiceNoticeType() {
		return serviceNoticeType;
	}

	public void setServiceNoticeType(Integer serviceNoticeType) {
		this.serviceNoticeType = serviceNoticeType;
	}

	public Integer getEmergeState() {
		return emergeState;
	}

	public void setEmergeState(Integer emergeState) {
		this.emergeState = emergeState;
	}

	public Integer getServiceNoticeState() {
		return serviceNoticeState;
	}

	public void setServiceNoticeState(Integer serviceNoticeState) {
		this.serviceNoticeState = serviceNoticeState;
	}

	public Integer getAgentNoticeState() {
		return agentNoticeState;
	}

	public void setAgentNoticeState(Integer agentNoticeState) {
		this.agentNoticeState = agentNoticeState;
	}

	public Timestamp getAgentEndTime() {
		return agentEndTime;
	}

	public void setAgentEndTime(Timestamp agentEndTime) {
		this.agentEndTime = agentEndTime;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Timestamp getOpEndTIme() {
		return opEndTIme;
	}

	public void setOpEndTIme(Timestamp opEndTIme) {
		this.opEndTIme = opEndTIme;
	}

	public Timestamp getOpTime() {
		return opTime;
	}

	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}

	public Integer getOpCount() {
		return opCount;
	}

	public void setOpCount(Integer opCount) {
		this.opCount = opCount;
	}

	public Integer getDeferCount() {
		return deferCount;
	}

	public void setDeferCount(Integer deferCount) {
		this.deferCount = deferCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateOpr() {
		return createOpr;
	}

	public void setCreateOpr(String createOpr) {
		this.createOpr = createOpr;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public Integer getNeedPhoneCall() {
		return needPhoneCall;
	}

	public void setNeedPhoneCall(Integer needPhoneCall) {
		this.needPhoneCall = needPhoneCall;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}