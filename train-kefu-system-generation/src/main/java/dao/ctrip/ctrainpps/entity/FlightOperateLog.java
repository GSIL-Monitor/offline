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
@Table(name="flight_operate_log")
public class FlightOperateLog implements DalPojo {

    // 主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 携程订单号
	@Column(name="VendorOrderId")
	@Type(value=Types.VARCHAR)
	private String vendorOrderId;

    // 操作代号：10支付，20退票，21航变退票，30改签，31，航变改签，40航变RR，50修改补寄，51新增补寄，60出保，61退保，
	@Column(name="OpType")
	@Type(value=Types.SMALLINT)
	private Integer opType;

    // 备注
	@Column(name="Remark")
	@Type(value=Types.VARCHAR)
	private String remark;

    // 操作来源 1 Offline，2 APP，3 IVR，4 短信
	@Column(name="Source")
	@Type(value=Types.SMALLINT)
	private Integer source;

    // 操作人
	@Column(name="OpUser")
	@Type(value=Types.VARCHAR)
	private String opUser;

    // 操作状态  0成功
	@Column(name="OpStatus")
	@Type(value=Types.SMALLINT)
	private Integer opStatus;

    // 创建时间
	@Column(name="CreateTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp createTime;

    // 渠道
	@Column(name="Partner")
	@Type(value=Types.VARCHAR)
	private String partner;

    // 更新时间
	@Column(name="Datachange_Lasttime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    // 订单号
	@Column(name="OrderId")
	@Type(value=Types.VARCHAR)
	private String orderId;

    // 操作名称
	@Column(name="OpName")
	@Type(value=Types.VARCHAR)
	private String opName;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getVendorOrderId() {
		return vendorOrderId;
	}

	public void setVendorOrderId(String vendorOrderId) {
		this.vendorOrderId = vendorOrderId;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Integer getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

}