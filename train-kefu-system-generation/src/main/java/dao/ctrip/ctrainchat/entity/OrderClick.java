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
 * @author jj季健(IT)
 * @date 2018-11-23
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "order_click")
public class OrderClick implements DalPojo {

	/**
	 * 主键
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
	 * 操作员工号
	 */
	@Column(name = "Oper_Id")
	@Type(value = Types.VARCHAR)
	private String operId;

	/**
	 * 点击类型 1：查询12306密码  2：修改二次推荐配置
	 */
	@Column(name = "Click_Type")
	@Type(value = Types.VARCHAR)
	private String clickType;

	/**
	 * 点击备注
	 */
	@Column(name = "Click_Remark")
	@Type(value = Types.VARCHAR)
	private String clickRemark;

	/**
	 * 最后更新时间
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

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getClickType() {
		return clickType;
	}

	public void setClickType(String clickType) {
		this.clickType = clickType;
	}

	public String getClickRemark() {
		return clickRemark;
	}

	public void setClickRemark(String clickRemark) {
		this.clickRemark = clickRemark;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}
