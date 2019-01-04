package dao.ctrip.ctrainchat.entity;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Database(name="CtrainChat")
@Table(name="group_staff_relation")
public class GroupStaffRelation implements DalPojo {

    //主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    //组ID
	@Column(name="GroupTid")
	@Type(value=Types.INTEGER)
	private Integer groupTid;

    //员工编号
	@Column(name="StaffNumber")
	@Type(value=Types.VARCHAR)
	private String staffNumber;

    //更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    //0:删除，1：未删除
	@Column(name="Is_Delete")
	@Type(value=Types.INTEGER)
	private Integer isDelete;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getGroupTid() {
		return groupTid;
	}

	public void setGroupTid(Integer groupTid) {
		this.groupTid = groupTid;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
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

}