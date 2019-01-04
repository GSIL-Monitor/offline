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

/**
 * @author sy苏勇
 * @date 2018-07-25
 */
@Entity
@Database(name="ctrainppsdb")
@Table(name="notice_vendor")
public class NoticeVendor implements DalPojo {

    /**
     * 主键
     */
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long iD;

    /**
     * 通知表主键
     */
	@Column(name="NoticeId")
	@Type(value=Types.BIGINT)
	private Long noticeId;

    /**
     * 供应商code
     */
	@Column(name="VerdorCode")
	@Type(value=Types.VARCHAR)
	private String verdorCode;

    /**
     * 供应商编号
     */
	@Column(name="VerdorName")
	@Type(value=Types.VARCHAR)
	private String verdorName;

    /**
     * 问题内容
     */
	@Column(name="Contents")
	@Type(value=Types.VARCHAR)
	private String contents;

    /**
     * 发送人类型 1，客服，2，供应商
     */
	@Column(name="SendType")
	@Type(value=Types.INTEGER)
	private Integer sendType;

	/**
	 * 催次数
	 */
	@Column(name="OpCount")
	@Type(value=Types.INTEGER)
	private Integer opCount;

	/**
	 * 处理人
	 */
	@Column(name="OpUser")
	@Type(value=Types.VARCHAR)
	private String opUser;

	/**
	 * 处理人类型
	 */
	@Column(name="opUserType")
	@Type(value=Types.SMALLINT)
	private Integer opUserType;

	/**
	 * 最新处理时间
	 */
	@Column(name="OpTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp opTime;

    /**
     * 发送人编号
     */
	@Column(name="SendCode")
	@Type(value=Types.VARCHAR)
	private String sendCode;

    /**
     * 发送人姓名
     */
	@Column(name="SendName")
	@Type(value=Types.VARCHAR)
	private String sendName;

    /**
     * 发送时间
     */
	@Column(name="SendTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp sendTime;

    /**
     * 要求供应商在该时间内处理
     */
	@Column(name="AppointedProcessTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp appointedProcessTime;

    /**
     * 是否删除
     */
	@Column(name="IsDelete")
	@Type(value=Types.INTEGER)
	private Integer isDelete;

    /**
     * 更新时间
     */
	@Column(name="datachange_lasttime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getVerdorCode() {
		return verdorCode;
	}

	public void setVerdorCode(String verdorCode) {
		this.verdorCode = verdorCode;
	}

	public String getVerdorName() {
		return verdorName;
	}

	public void setVerdorName(String verdorName) {
		this.verdorName = verdorName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getAppointedProcessTime() {
		return appointedProcessTime;
	}

	public void setAppointedProcessTime(Timestamp appointedProcessTime) {
		this.appointedProcessTime = appointedProcessTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Integer getOpCount() {
		return opCount;
	}

	public void setOpCount(Integer opCount) {
		this.opCount = opCount;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Timestamp getOpTime() {
		return opTime;
	}

	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}

	public Integer getOpUserType() {
		return opUserType;
	}

	public void setOpUserType(Integer opUserType) {
		this.opUserType = opUserType;
	}
}