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
import java.sql.Time;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

@Entity
@Database(name="ctrainppsdb")
@Table(name="scm_two_citydelivery_table")
public class ScmTwoCitydeliveryTable implements DalPojo {

    // 主键
	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

    // 城市名称
	@Column(name="CityName")
	@Type(value=Types.VARCHAR)
	private String cityName;

    // 身份名称
	@Column(name="ProvinceName")
	@Type(value=Types.VARCHAR)
	private String provinceName;

    // 配送类型 0：其他 1:普通 2：无法配送 3：柜台配送
	@Column(name="DeliveryType")
	@Type(value=Types.VARCHAR)
	private String deliveryType;

    // 时间拐点
	@Column(name="TimeInflectionPoint")
	@Type(value=Types.TIME)
	private Time timeInflectionPoint;

    // 拐点前天数（如 3 表示3天之后的可以）
	@Column(name="PreInflectionDeliveryDay")
	@Type(value=Types.INTEGER)
	private Integer preInflectionDeliveryDay;

    // 拐点后天数
	@Column(name="AfterInflectionDeliveryDay")
	@Type(value=Types.INTEGER)
	private Integer afterInflectionDeliveryDay;

    // 备用字段 配送类型为柜台票的存areaId
	@Column(name="BackupField")
	@Type(value=Types.VARCHAR)
	private String backupField;

    // 最后更新时间
	@Column(name="DataChange_LastTime", insertable=false, updatable=false)
	@Type(value=Types.TIMESTAMP)
	private Timestamp datachangeLasttime;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Time getTimeInflectionPoint() {
		return timeInflectionPoint;
	}

	public void setTimeInflectionPoint(Time timeInflectionPoint) {
		this.timeInflectionPoint = timeInflectionPoint;
	}

	public Integer getPreInflectionDeliveryDay() {
		return preInflectionDeliveryDay;
	}

	public void setPreInflectionDeliveryDay(Integer preInflectionDeliveryDay) {
		this.preInflectionDeliveryDay = preInflectionDeliveryDay;
	}

	public Integer getAfterInflectionDeliveryDay() {
		return afterInflectionDeliveryDay;
	}

	public void setAfterInflectionDeliveryDay(Integer afterInflectionDeliveryDay) {
		this.afterInflectionDeliveryDay = afterInflectionDeliveryDay;
	}

	public String getBackupField() {
		return backupField;
	}

	public void setBackupField(String backupField) {
		this.backupField = backupField;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

}