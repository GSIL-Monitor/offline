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

/**
 * @author sy苏勇
 * @date 2018-07-02
 */
@Entity
@Database(name="ctrainppsdb")
@Table(name="order_asyncoperate")
public class OrderAsyncoperate implements DalPojo {

    /**
     * 主键
     */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long id;

    /**
     * 1，退套餐 2，退加速包
     */
	@Column(name="type")
	@Type(value=Types.INTEGER)
	private Integer type;

    /**
     * 请求体json格式
     */
	@Column(name="request")
	@Type(value=Types.VARCHAR)
	private String request;

    /**
     * 请求次数
     */
	@Column(name="requestcout")
	@Type(value=Types.INTEGER)
	private Integer requestcout;

    /**
     * 返回内容 json 格式
     */
	@Column(name="respose")
	@Type(value=Types.VARCHAR)
	private String respose;

    /**
     * 请求契约名称
     */
	@Column(name="contactname")
	@Type(value=Types.VARCHAR)
	private String contactname;

    /**
     * 请求结果 等待处理 3，成功 1 ，失败 2
     */
	@Column(name="state")
	@Type(value=Types.INTEGER)
	private Integer state;

    /**
     * 发送时间
     */
	@Column(name="sendtime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp sendtime;

    /**
     * 更新数据时间
     */
	@Column(name="DatachangeLastTime")
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLastTime;

    /**
     * 更新时间
     */
	@Column(name="datachange_lasttime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Integer getRequestcout() {
		return requestcout;
	}

	public void setRequestcout(Integer requestcout) {
		this.requestcout = requestcout;
	}

	public String getRespose() {
		return respose;
	}

	public void setRespose(String respose) {
		this.respose = respose;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
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

}