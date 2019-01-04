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
@Table(name="staff_permission_relation")
public class StaffPermissionRelation implements DalPojo {

    // 主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.INTEGER)
	private Integer tid;

    // 员工编号
	@Column(name="StaffNumber")
	@Type(value=Types.VARCHAR)
	private String staffNumber;

    // 权限ID
	@Column(name="PermissionTid")
	@Type(value=Types.VARCHAR)
	private String permissionTid;

    // 更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 0:删除，1：未删除
	@Column(name="IsDelete")
	@Type(value=Types.SMALLINT)
	private Integer isDelete;

    // 有效时间 默认为永久
	@Column(name="EffectiveTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp effectiveTime;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getPermissionTid() {
		return permissionTid;
	}

	public void setPermissionTid(String permissionTid) {
		this.permissionTid = permissionTid;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Timestamp effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

}