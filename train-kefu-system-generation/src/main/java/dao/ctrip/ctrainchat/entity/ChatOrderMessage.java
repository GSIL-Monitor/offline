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
 * @date 2018-11-23
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "chat_order_message")
public class ChatOrderMessage implements DalPojo {

	/**
	 * 自增长列
	 */
	@Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long tid;

	/**
	 * 订单号
	 */
	@Column(name = "Order_Id")
	@Type(value = Types.VARCHAR)
	private String orderId;

	/**
	 * 订单记录唯一号
	 */
	@Column(name = "OrderOnlyId")
	@Type(value = Types.VARCHAR)
	private String orderOnlyId;

	/**
	 * 订单行为
	 */
	@Column(name = "Action")
	@Type(value = Types.INTEGER)
	private Integer action;

	/**
	 * 状态:0待处理，1已如队列，2已发送，3异常，4无需处理，5延迟处理
	 */
	@Column(name = "State")
	@Type(value = Types.INTEGER)
	private Integer state;

	/**
	 * 创建时间
	 */
	@Column(name = "CreateTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp createTime;

	/**
	 * 备注
	 */
	@Column(name = "Remark")
	@Type(value = Types.VARCHAR)
	private String remark;

	/**
	 * 最后修改时间
	 */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderOnlyId() {
		return orderOnlyId;
	}

	public void setOrderOnlyId(String orderOnlyId) {
		this.orderOnlyId = orderOnlyId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}
