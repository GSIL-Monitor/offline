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
@Database(name="CtrainChat")
@Table(name="chat_future_order_message")
public class ChatFutureOrderMessage implements DalPojo {

    // 主键
	@Id
	@Column(name="tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 订单号
	@Column(name="order_id")
	@Type(value=Types.VARCHAR)
	private String orderId;

    // 短信类型
	@Column(name="action")
	@Type(value=Types.INTEGER)
	private Integer action;

    // 0待发送  1发送成功 2已取消 3无需发送 4发送失败 5已发送且已读
	@Column(name="state")
	@Type(value=Types.SMALLINT)
	private Integer state;

    // 创建时间
	@Column(name="create_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 备注
	@Column(name="remark")
	@Type(value=Types.VARCHAR)
	private String remark;

    // 短信内容
	@Column(name="message_content")
	@Type(value=Types.VARCHAR)
	private String messageContent;

    // 手机号
	@Column(name="mobile")
	@Type(value=Types.VARCHAR)
	private String mobile;

    // 分销商
	@Column(name="partner_name")
	@Type(value=Types.VARCHAR)
	private String partnerName;

    // 更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 用户ID
	@Column(name="uid")
	@Type(value=Types.VARCHAR)
	private String uid;

    // 预定发送时间
	@Column(name="send_time")
	@Type(value=Types.TIMESTAMP)
	private Timestamp sendTime;

    // 消息通道   0 表示默认根据part_name决定消息通道  1 表示强制使用携程通道  2 表示强制使用铁友通道
	@Column(name="MsgChannel")
	@Type(value=Types.SMALLINT)
	private Integer msgChannel;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getMsgChannel() {
		return msgChannel;
	}

	public void setMsgChannel(Integer msgChannel) {
		this.msgChannel = msgChannel;
	}

}