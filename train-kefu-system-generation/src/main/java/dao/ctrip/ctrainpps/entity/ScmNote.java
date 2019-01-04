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

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author wql王全礼
 * @date 2018-11-16
 */
@Entity
@Database(name = "ctrainppsdb")
@Table(name = "scm_note")
public class ScmNote implements DalPojo {

    /**
     * 主键ID
     */
    @Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long tid;

    /**
     * 订单号
     */
	@Column(name = "OrderID")
	@Type(value = Types.VARCHAR)
	private String orderID;

    /**
     * 备注内容
     */
	@Column(name = "Note")
	@Type(value = Types.VARCHAR)
	private String note;

    /**
     * 操作人
     */
	@Column(name = "Operator")
	@Type(value = Types.VARCHAR)
	private String operator;

    /**
     * 操作时间
     */
	@Column(name = "OperateTime")
	@Type(value = Types.VARCHAR)
	private String operateTime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

}