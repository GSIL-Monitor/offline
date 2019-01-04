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

/**
 * @author wql王全礼
 * @date 2018-09-10
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "offline_staff_priority_notice")
public class OfflineStaffPriorityNotice implements DalPojo {

    /**
     * 主键
     */
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long id;

    /**
     * 员工编号
     */
	@Column(name = "staffNum")
	@Type(value = Types.VARCHAR)
	private String staffNum;

    /**
     * 通知产线
     */
	@Column(name = "noticeProductLine")
	@Type(value = Types.INTEGER)
	private Integer noticeProductLine;

    /**
     * 通知的一级分类
     */
	@Column(name = "noticeTypes")
	@Type(value = Types.VARCHAR)
	private String noticeTypes;

    /**
     * 1,通知 2,投诉 4,领班
     */
	@Column(name = "envenType")
	@Type(value = Types.INTEGER)
	private Integer envenType;

    /**
     * 可用
     */
	@Column(name = "available")
	@Type(value = Types.TINYINT)
	private Integer available;

    /**
     * 创建时间
     */
	@Column(name = "create_time")
	@Type(value = Types.TIMESTAMP)
	private Timestamp createTime;

    /**
     * 最后更新时间
     */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStaffNum() {
		return staffNum;
	}

	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	public Integer getNoticeProductLine() {
		return noticeProductLine;
	}

	public void setNoticeProductLine(Integer noticeProductLine) {
		this.noticeProductLine = noticeProductLine;
	}

	public String getNoticeTypes() {
		return noticeTypes;
	}

	public void setNoticeTypes(String noticeTypes) {
		this.noticeTypes = noticeTypes;
	}

	public Integer getEnvenType() {
		return envenType;
	}

	public void setEnvenType(Integer envenType) {
		this.envenType = envenType;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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