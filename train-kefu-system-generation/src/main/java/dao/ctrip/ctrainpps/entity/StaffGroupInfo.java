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
@Table(name="staff_group_info")
public class StaffGroupInfo implements DalPojo {

    // Tid
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 组别名称
	@Column(name="GroupName")
	@Type(value=Types.VARCHAR)
	private String groupName;

    // 产品线
	@Column(name="ProductLine")
	@Type(value=Types.VARCHAR)
	private String productLine;

    // 0:删除，1：未删除
	@Column(name="IsDelete")
	@Type(value=Types.INTEGER)
	private Integer isDelete;

    // 更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 主管员工编号
	@Column(name="SupervisorStaffNUmber")
	@Type(value=Types.VARCHAR)
	private String supervisorStaffNUmber;

    // 组说明
	@Column(name="GroupDesc")
	@Type(value=Types.VARCHAR)
	private String groupDesc;

    // 组类型：0默认  1通知投诉处理组
	@Column(name="GroupType")
	@Type(value=Types.SMALLINT)
	private Integer groupType;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
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

	public String getSupervisorStaffNUmber() {
		return supervisorStaffNUmber;
	}

	public void setSupervisorStaffNUmber(String supervisorStaffNUmber) {
		this.supervisorStaffNUmber = supervisorStaffNUmber;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

}