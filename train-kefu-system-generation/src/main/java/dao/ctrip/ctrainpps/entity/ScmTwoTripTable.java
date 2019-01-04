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
@Table(name="scm_two_trip_table")
public class ScmTwoTripTable implements DalPojo {

    // 自增长列
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 任务id
	@Column(name="task_tid")
	@Type(value=Types.BIGINT)
	private Long taskTid;

    // 车票时间
	@Column(name="tickettime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp tickettime;

    // 车票张数
	@Column(name="ticketCount")
	@Type(value=Types.INTEGER)
	private Integer ticketCount;

    // 出发站
	@Column(name="departStationName")
	@Type(value=Types.VARCHAR)
	private String departStationName;

    // 到达站
	@Column(name="arriveStationName")
	@Type(value=Types.VARCHAR)
	private String arriveStationName;

    // 车次
	@Column(name="trainNumber")
	@Type(value=Types.VARCHAR)
	private String trainNumber;

    // 座席
	@Column(name="ticketSeat")
	@Type(value=Types.VARCHAR)
	private String ticketSeat;

    // 行程编号， 单程1，去程1，返程2，联程按顺序编号
	@Column(name="sectionNumber")
	@Type(value=Types.INTEGER)
	private Integer sectionNumber;

    // 最后更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getTaskTid() {
		return taskTid;
	}

	public void setTaskTid(Long taskTid) {
		this.taskTid = taskTid;
	}

	public Timestamp getTickettime() {
		return tickettime;
	}

	public void setTickettime(Timestamp tickettime) {
		this.tickettime = tickettime;
	}

	public Integer getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(Integer ticketCount) {
		this.ticketCount = ticketCount;
	}

	public String getDepartStationName() {
		return departStationName;
	}

	public void setDepartStationName(String departStationName) {
		this.departStationName = departStationName;
	}

	public String getArriveStationName() {
		return arriveStationName;
	}

	public void setArriveStationName(String arriveStationName) {
		this.arriveStationName = arriveStationName;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTicketSeat() {
		return ticketSeat;
	}

	public void setTicketSeat(String ticketSeat) {
		this.ticketSeat = ticketSeat;
	}

	public Integer getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(Integer sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}