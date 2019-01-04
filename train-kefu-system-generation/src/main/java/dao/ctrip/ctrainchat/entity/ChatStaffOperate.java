package dao.ctrip.ctrainchat.entity;

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
@Table(name="chat_staff_operate")
public class ChatStaffOperate implements DalPojo {

    // 主键
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long iD;

    // 员工编号
	@Column(name="Staff_Number")
	@Type(value=Types.VARCHAR)
	private String staffNumber;

    // 员工编号
	@Column(name="Staff_Name")
	@Type(value=Types.VARCHAR)
	private String staffName;

    // 操作类型1,在线，2离线
	@Column(name="OperateType")
	@Type(value=Types.INTEGER)
	private Integer operateType;

    // 离线/在线时长（分钟）
	@Column(name="LiveTime")
	@Type(value=Types.REAL)
	private Float liveTime;

    // 创建时间
	@Column(name="CreateTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 最后更新时间
	@Column(name="dataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getID() {
		return iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Float getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Float liveTime) {
		this.liveTime = liveTime;
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

}