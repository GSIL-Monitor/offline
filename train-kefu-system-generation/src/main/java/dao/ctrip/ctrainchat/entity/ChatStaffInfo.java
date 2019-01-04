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
@Table(name = "chat_staff_info")
public class ChatStaffInfo implements DalPojo {

    /**
     * 空
     */
    @Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.INTEGER)
	private Integer tid;

    /**
     * 员工姓名
     */
	@Column(name = "Staff_Name")
	@Type(value = Types.VARCHAR)
	private String staffName;

    /**
     * 工号
     */
	@Column(name = "Staff_Number")
	@Type(value = Types.VARCHAR)
	private String staffNumber;

    /**
     * Email
     */
	@Column(name = "Email")
	@Type(value = Types.VARCHAR)
	private String email;

    /**
     * -1:全部;1:火车票，2：汽车票，4：机场巴士
     */
	@Column(name = "ProductLine")
	@Type(value = Types.VARCHAR)
	private String productLine;

    /**
     * 通知投诉系统员工状态 0:离线,1：在线
     */
	@Column(name = "Staff_State")
	@Type(value = Types.CHAR)
	private String staffState;

    /**
     * 0:离线,1：在线
     */
	@Column(name = "TR_Staff_State")
	@Type(value = Types.CHAR)
	private String trStaffState;

    /**
     * 空
     */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    /**
     * 0:删除，1：未删除
     */
	@Column(name = "Is_Delete")
	@Type(value = Types.INTEGER)
	private Integer isDelete;

    /**
     * 处理通知类型：按位与0不处理通知、2^0处理通知、2^1处理投诉、2^2处理领班
     */
	@Column(name = "EventType")
	@Type(value = Types.INTEGER)
	private Integer eventType;

    /**
     * 处理的通知投诉类型 用,隔开
     */
	@Column(name = "NoticeType")
	@Type(value = Types.VARCHAR)
	private String noticeType;

    /**
     * 通知投诉系统产品线
     */
	@Column(name = "NoticeProductLine")
	@Type(value = Types.SMALLINT)
	private Integer noticeProductLine;

    /**
     * 通知待处理
     */
	@Column(name = "NoticeWaitLimit")
	@Type(value = Types.INTEGER)
	private Integer noticeWaitLimit;

    /**
     * 投诉待处理上限
     */
	@Column(name = "ComplainWaitLimit")
	@Type(value = Types.INTEGER)
	private Integer complainWaitLimit;

    /**
     * 职位id
     */
	@Column(name = "PositionId")
	@Type(value = Types.BIGINT)
	private Long positionId;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getStaffState() {
		return staffState;
	}

	public void setStaffState(String staffState) {
		this.staffState = staffState;
	}

	public String getTrStaffState() {
		return trStaffState;
	}

	public void setTrStaffState(String trStaffState) {
		this.trStaffState = trStaffState;
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

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public Integer getNoticeProductLine() {
		return noticeProductLine;
	}

	public void setNoticeProductLine(Integer noticeProductLine) {
		this.noticeProductLine = noticeProductLine;
	}

	public Integer getNoticeWaitLimit() {
		return noticeWaitLimit;
	}

	public void setNoticeWaitLimit(Integer noticeWaitLimit) {
		this.noticeWaitLimit = noticeWaitLimit;
	}

	public Integer getComplainWaitLimit() {
		return complainWaitLimit;
	}

	public void setComplainWaitLimit(Integer complainWaitLimit) {
		this.complainWaitLimit = complainWaitLimit;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

}