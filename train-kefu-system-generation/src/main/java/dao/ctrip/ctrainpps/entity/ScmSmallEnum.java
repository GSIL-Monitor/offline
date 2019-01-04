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

@Entity
@Database(name="ctrainppsdb")
@Table(name="scm_small_enum")
public class ScmSmallEnum implements DalPojo {

	@Id
	@Column(name="Tid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value=Types.BIGINT)
	private Long tid;

	@Column(name="Field_Type")
	@Type(value=Types.VARCHAR)
	private String fieldType;

	@Column(name="Field_Name")
	@Type(value=Types.VARCHAR)
	private String fieldName;

	@Column(name="Field_Value")
	@Type(value=Types.VARCHAR)
	private String fieldValue;

	@Column(name="FK_Upper_Tid")
	@Type(value=Types.BIGINT)
	private Long fkUpperTid;

	@Column(name="Is_Deleted")
	@Type(value=Types.VARCHAR)
	private String isDeleted;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Long getFkUpperTid() {
		return fkUpperTid;
	}

	public void setFkUpperTid(Long fkUpperTid) {
		this.fkUpperTid = fkUpperTid;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}