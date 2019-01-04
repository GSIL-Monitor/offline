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
 * @author sy苏勇
 * @date 2018-08-08
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "user_vendor_info")
public class UserVendorInfo implements DalPojo {

    /**
     * 主键id
     */
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long id;

    /**
     * 用户基础信息表主键
     */
	@Column(name = "UserId")
	@Type(value = Types.BIGINT)
	private Long userId;

    /**
     * 供应商code
     */
	@Column(name = "VendorCode")
	@Type(value = Types.VARCHAR)
	private String vendorCode;

    /**
     * 供应商名称
     */
	@Column(name = "VendorName")
	@Type(value = Types.VARCHAR)
	private String vendorName;

    /**
     * 处理通知上线
     */
	@Column(name = "DealLimit")
	@Type(value = Types.INTEGER)
	private Integer dealLimit;

    /**
     * 当前状态，1上班 2休息， 3下班
     */
	@Column(name = "State")
	@Type(value = Types.INTEGER)
	private Integer state;

    /**
     * 是否被删除
     */
	@Column(name = "IsDelete")
	@Type(value = Types.SMALLINT)
	private Integer isDelete;

    /**
     * 更新时间
     */
	@Column(name = "datachange_lasttime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getDealLimit() {
		return dealLimit;
	}

	public void setDealLimit(Integer dealLimit) {
		this.dealLimit = dealLimit;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}