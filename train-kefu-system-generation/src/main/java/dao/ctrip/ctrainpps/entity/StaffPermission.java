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
@Table(name="staff_permission")
public class StaffPermission implements DalPojo {

    // 主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 权限名称
	@Column(name="PermissionName")
	@Type(value=Types.VARCHAR)
	private String permissionName;

    // 0:删除，1：未删除
	@Column(name="IsDelete")
	@Type(value=Types.INTEGER)
	private Integer isDelete;

    // 更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 创建者员工编号
	@Column(name="StaffNumber")
	@Type(value=Types.VARCHAR)
	private String staffNumber;

    // 权限类型：0默认   1操作权限  2页面权限
	@Column(name="PermissionType")
	@Type(value=Types.SMALLINT)
	private Integer permissionType;

    // 权限描述
	@Column(name="PermissionDesc")
	@Type(value=Types.VARCHAR)
	private String permissionDesc;

    // 权限唯一标识
	@Column(name="PermissionCode")
	@Type(value=Types.VARCHAR)
	private String permissionCode;

    // 权限所针对人群    0默认，公共    1客服  2财务  3产品   6Boss  8开发  10管理员
	@Column(name="RoleForPermission")
	@Type(value=Types.SMALLINT)
	private Integer roleForPermission;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
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

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public Integer getRoleForPermission() {
		return roleForPermission;
	}

	public void setRoleForPermission(Integer roleForPermission) {
		this.roleForPermission = roleForPermission;
	}

}