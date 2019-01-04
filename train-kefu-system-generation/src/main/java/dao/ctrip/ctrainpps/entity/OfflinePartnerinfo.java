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
 * @author jj季健(IT)
 * @date 2018-11-22
 */
@Entity
@Database(name = "ctrainppsdb")
@Table(name = "offline_partnerinfo")
public class OfflinePartnerinfo implements DalPojo {

	/**
	 * 主键
	 */
	@Id
	@Column(name = "Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.INTEGER)
	private Integer tid;

	/**
	 * 合作方类型 1：分销商
	 */
	@Column(name = "PartnerType")
	@Type(value = Types.INTEGER)
	private Integer partnerType;

	/**
	 * 合作方原始名称
	 */
	@Column(name = "PartnerSourceName")
	@Type(value = Types.VARCHAR)
	private String partnerSourceName;

	/**
	 * 合作方转义后名称
	 */
	@Column(name = "PartnerTargetName")
	@Type(value = Types.VARCHAR)
	private String partnerTargetName;

	/**
	 * 联系电话
	 */
	@Column(name = "ContactMoblie")
	@Type(value = Types.VARCHAR)
	private String contactMoblie;

	/**
	 * 联系人
	 */
	@Column(name = "ContactName")
	@Type(value = Types.VARCHAR)
	private String contactName;

	/**
	 * 联系邮件
	 */
	@Column(name = "ContactEmail")
	@Type(value = Types.VARCHAR)
	private String contactEmail;

	/**
	 * 备注
	 */
	@Column(name = "Remark")
	@Type(value = Types.VARCHAR)
	private String remark;

	/**
	 * 创建时间
	 */
	@Column(name = "CreateTime")
	@Type(value = Types.TIMESTAMP)
	private Timestamp createTime;

	/**
	 * 最后更新时间
	 */
	@Column(name = "DataChange_LastTime", insertable = false, updatable = false)
	@Type(value = Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(Integer partnerType) {
		this.partnerType = partnerType;
	}

	public String getPartnerSourceName() {
		return partnerSourceName;
	}

	public void setPartnerSourceName(String partnerSourceName) {
		this.partnerSourceName = partnerSourceName;
	}

	public String getPartnerTargetName() {
		return partnerTargetName;
	}

	public void setPartnerTargetName(String partnerTargetName) {
		this.partnerTargetName = partnerTargetName;
	}

	public String getContactMoblie() {
		return contactMoblie;
	}

	public void setContactMoblie(String contactMoblie) {
		this.contactMoblie = contactMoblie;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
