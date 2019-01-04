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
 * @author wql王全礼
 * @date 2018-08-08
 */
@Entity
@Database(name = "ctrainppsdb")
@Table(name = "notice_once_solve_user")
public class NoticeOnceSolveUser implements DalPojo {

    /**
     * 主键
     */
    @Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.INTEGER)
	private Integer iD;

    /**
     * 处理人
     */
	@Column(name = "opuserName")
	@Type(value = Types.VARCHAR)
	private String opuserName;

    /**
     * 工号
     */
	@Column(name = "opuserNum")
	@Type(value = Types.VARCHAR)
	private String opuserNum;

    /**
     * 一次性解决的通知
     */
	@Column(name = "oncesovle")
	@Type(value = Types.INTEGER)
	private Integer oncesovle;

    /**
     * 处理日期
     */
	@Column(name = "opTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp opTime;

    /**
     * 当日处理的通知
     */
	@Column(name = "allnum")
	@Type(value = Types.INTEGER)
	private Integer allnum;

    /**
     * 百分比
     */
	@Column(name = "percentage")
	@Type(value = Types.REAL)
	private Float percentage;

    /**
     * 最后更新时间
     */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

    /**
     * 1通知 2投诉 4转领班
     */
	@Column(name = "envenType")
	@Type(value = Types.INTEGER)
	private Integer envenType;

    /**
     * 产线
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

	public String getOpuserName() {
		return opuserName;
	}

	public void setOpuserName(String opuserName) {
		this.opuserName = opuserName;
	}

	public String getOpuserNum() {
		return opuserNum;
	}

	public void setOpuserNum(String opuserNum) {
		this.opuserNum = opuserNum;
	}

	public Integer getOncesovle() {
		return oncesovle;
	}

	public void setOncesovle(Integer oncesovle) {
		this.oncesovle = oncesovle;
	}

	public Timestamp getOpTime() {
		return opTime;
	}

	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}

	public Integer getAllnum() {
		return allnum;
	}

	public void setAllnum(Integer allnum) {
		this.allnum = allnum;
	}

	public Float getPercentage() {
		return percentage;
	}

	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Integer getEnvenType() {
		return envenType;
	}

	public void setEnvenType(Integer envenType) {
		this.envenType = envenType;
	}

	public Integer getProductLine() {
		return productLine;
	}

	public void setProductLine(Integer productLine) {
		this.productLine = productLine;
	}

}