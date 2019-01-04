package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Type;
import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author wql王全礼
 * @date 2018-08-06
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "air_notice_excetion")
public class AirNoticeExcetion implements DalPojo {

    /**
     * 主键
     */
    @Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.INTEGER)
	private Integer iD;

    /**
     * 订单号
     */
	@Column(name = "orderId")
	@Type(value = Types.VARCHAR)
	private String orderId;

    /**
     * 供应商
     */
	@Column(name = "supplier")
	@Type(value = Types.VARCHAR)
	private String supplier;

    /**
     * 下单时间
     */
	@Column(name = "sendOrderTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp sendOrderTime;

    /**
     * 起飞时间
     */
	@Column(name = "takeoffTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp takeoffTime;

    /**
     * 最晚出票时间
     */
	@Column(name = "latestTicketingTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp latestTicketingTime;

    /**
     * 异常订单类型
     */
	@Column(name = "exOrderType")
	@Type(value = Types.INTEGER)
	private Integer exOrderType;

    /**
     * 备注
     */
	@Column(name = "processing_remark")
	@Type(value = Types.VARCHAR)
	private String processingRemark;

    /**
     * 最后更新时间
     */
	@Column(name = "DatachangeLastTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLastTime;

    /**
     * 更新时间
     */
	@Column(name = "datachange_lasttime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    /**
     * 输入人
     */
	@Column(name = "enterUser")
	@Type(value = Types.VARCHAR)
	private String enterUser;

    /**
     * 产品线
     */
	@Column(name = "productLine")
	@Type(value = Types.INTEGER)
	private Integer productLine;

	public Integer getID() {
		return iD;
	}

	public void setID(Integer iD) {
		this.iD = iD;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Timestamp getSendOrderTime() {
		return sendOrderTime;
	}

	public void setSendOrderTime(Timestamp sendOrderTime) {
		this.sendOrderTime = sendOrderTime;
	}

	public Timestamp getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(Timestamp takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public Timestamp getLatestTicketingTime() {
		return latestTicketingTime;
	}

	public void setLatestTicketingTime(Timestamp latestTicketingTime) {
		this.latestTicketingTime = latestTicketingTime;
	}

	public Integer getExOrderType() {
		return exOrderType;
	}

	public void setExOrderType(Integer exOrderType) {
		this.exOrderType = exOrderType;
	}

	public String getProcessingRemark() {
		return processingRemark;
	}

	public void setProcessingRemark(String processingRemark) {
		this.processingRemark = processingRemark;
	}

	public Timestamp getDatachangeLastTime() {
		return datachangeLastTime;
	}

	public void setDatachangeLastTime(Timestamp datachangeLastTime) {
		this.datachangeLastTime = datachangeLastTime;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public String getEnterUser() {
		return enterUser;
	}

	public void setEnterUser(String enterUser) {
		this.enterUser = enterUser;
	}

	public Integer getProductLine() {
		return productLine;
	}

	public void setProductLine(Integer productLine) {
		this.productLine = productLine;
	}

}