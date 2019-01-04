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
@Table(name="agent_notice_info")
public class AgentNoticeInfo implements DalPojo {

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

    // 通知内容
	@Column(name="NoticeContent")
	@Type(value=Types.VARCHAR)
	private String noticeContent;

    // 通知来源，代售点10 客服20
	@Column(name="SourceFrom")
	@Type(value=Types.INTEGER)
	private Integer sourceFrom;

    // 创建人
	@Column(name="CreateOpr")
	@Type(value=Types.VARCHAR)
	private String createOpr;

    // 创建时间
	@Column(name="CreateTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 查看时间
	@Column(name="ReadTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp readTime;

    // 最后更新时间
	@Column(name="Datachange_LastTime", insertable=false, updatable=false)
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

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(Integer sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getCreateOpr() {
		return createOpr;
	}

	public void setCreateOpr(String createOpr) {
		this.createOpr = createOpr;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}