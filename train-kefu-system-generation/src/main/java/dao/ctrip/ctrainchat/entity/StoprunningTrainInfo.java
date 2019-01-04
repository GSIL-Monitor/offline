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
 * @author jj季健(IT)
 * @date 2018-08-31
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "stoprunning_train_info")
public class StoprunningTrainInfo implements DalPojo {

    /**
     * 主键
     */
    @Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long tid;

    /**
     * 创建时间
     */
	@Column(name = "Create_time")
	@Type(value = Types.TIMESTAMP)
	private Timestamp createTime;

    /**
     * 更新时间
     */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    /**
     * 0初始状态   1...更新次数
     */
	@Column(name = "State")
	@Type(value = Types.SMALLINT)
	private Integer state;

    /**
     * 备注信息
     */
	@Column(name = "Remark")
	@Type(value = Types.VARCHAR)
	private String remark;

    /**
     * 出发站
     */
	@Column(name = "FromStation")
	@Type(value = Types.VARCHAR)
	private String fromStation;

    /**
     * 到达站
     */
	@Column(name = "ToStation")
	@Type(value = Types.VARCHAR)
	private String toStation;

    /**
     * 出发日期
     */
	@Column(name = "DepartDate")
	@Type(value = Types.TIMESTAMP)
	private Timestamp departDate;

    /**
     * 车次
     */
	@Column(name = "TrainNo")
	@Type(value = Types.VARCHAR)
	private String trainNo;

    /**
     * 0停运   1恢复
     */
	@Column(name = "IsRecovery")
	@Type(value = Types.SMALLINT)
	private Integer isRecovery;

    /**
     * 涉及订单数_停运
     */
	@Column(name = "OrderCount")
	@Type(value = Types.INTEGER)
	private Integer orderCount;

    /**
     * 审核状态 0 初始状态，1通过，2拒绝
     */
	@Column(name = "StatusSms")
	@Type(value = Types.SMALLINT)
	private Integer statusSms;

    /**
     * 拒绝原因
     */
	@Column(name = "RemarkReject")
	@Type(value = Types.VARCHAR)
	private String remarkReject;

    /**
     * 操作人编号
     */
	@Column(name = "Operator")
	@Type(value = Types.VARCHAR)
	private String operator;

    /**
     * 订单数量_恢复
     */
	@Column(name = "OrderCountRecover")
	@Type(value = Types.INTEGER)
	private Integer orderCountRecover;

    /**
     * 停运短信数量
     */
	@Column(name = "SmsCountStop")
	@Type(value = Types.INTEGER)
	private Integer smsCountStop;

    /**
     * 恢复短信数量
     */
	@Column(name = "SmsCountRecover")
	@Type(value = Types.INTEGER)
	private Integer smsCountRecover;

    /**
     * 校验停运信息更新时间
     */
	@Column(name = "Check_time")
	@Type(value = Types.TIMESTAMP)
	private Timestamp checkTime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public Timestamp getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Timestamp departDate) {
		this.departDate = departDate;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public Integer getIsRecovery() {
		return isRecovery;
	}

	public void setIsRecovery(Integer isRecovery) {
		this.isRecovery = isRecovery;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getStatusSms() {
		return statusSms;
	}

	public void setStatusSms(Integer statusSms) {
		this.statusSms = statusSms;
	}

	public String getRemarkReject() {
		return remarkReject;
	}

	public void setRemarkReject(String remarkReject) {
		this.remarkReject = remarkReject;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getOrderCountRecover() {
		return orderCountRecover;
	}

	public void setOrderCountRecover(Integer orderCountRecover) {
		this.orderCountRecover = orderCountRecover;
	}

	public Integer getSmsCountStop() {
		return smsCountStop;
	}

	public void setSmsCountStop(Integer smsCountStop) {
		this.smsCountStop = smsCountStop;
	}

	public Integer getSmsCountRecover() {
		return smsCountRecover;
	}

	public void setSmsCountRecover(Integer smsCountRecover) {
		this.smsCountRecover = smsCountRecover;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

}