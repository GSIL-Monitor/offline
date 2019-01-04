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
@Table(name="chat_staff_state_log")
public class ChatStaffStateLog implements DalPojo {

    // 主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 员工编号
	@Column(name="StaffNumber")
	@Type(value=Types.VARCHAR)
	private String staffNumber;

    // 操作时间
	@Column(name="CreateDttm")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createDttm;

    // 类型 0未定义  1上班 2休息  3下班
	@Column(name="type")
	@Type(value=Types.INTEGER)
	private Integer type;

    // 更新时间
	@Column(name="DataChange_Lasttime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Timestamp getCreateDttm() {
		return createDttm;
	}

	public void setCreateDttm(Timestamp createDttm) {
		this.createDttm = createDttm;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}