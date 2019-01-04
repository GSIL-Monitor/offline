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
@Table(name="operate_info")
public class OperateInfo implements DalPojo {

	@Id
	@Column(name="Oid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long oid;

	@Column(name="OperateTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp operateTime;

    // 通知或投诉ID
	@Column(name="Tid")
	@Type(value=Types.BIGINT)
	private Long tid;

	@Column(name="OperateUser")
	@Type(value=Types.VARCHAR)
	private String operateUser;

	@Column(name="OperateComment")
	@Type(value=Types.VARCHAR)
	private String operateComment;

    // 备注类型： 81仅备注、82暂缓、83解决、94转投诉、100转领班、101交班
	@Column(name="OperateType")
	@Type(value=Types.SMALLINT)
	private Integer operateType;

	@Column(name="OperateSource")
	@Type(value=Types.SMALLINT)
	private Integer operateSource;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOperateComment() {
		return operateComment;
	}

	public void setOperateComment(String operateComment) {
		this.operateComment = operateComment;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getOperateSource() {
		return operateSource;
	}

	public void setOperateSource(Integer operateSource) {
		this.operateSource = operateSource;
	}
}