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
@Table(name = "user_basic_info")
public class UserBasicInfo implements DalPojo {

    /**
     * 主键id
     */
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(value = Types.BIGINT)
	private Long id;

    /**
     * 用户名
     */
	@Column(name = "UserName")
	@Type(value = Types.VARCHAR)
	private String userName;

    /**
     * 密码
     */
	@Column(name = "Password")
	@Type(value = Types.VARCHAR)
	private String password;

    /**
     * 用户编号
     */
	@Column(name = "UserNum")
	@Type(value = Types.VARCHAR)
	private String userNum;

    /**
     * 手机号
     */
	@Column(name = "Telephone")
	@Type(value = Types.VARCHAR)
	private String telephone;

    /**
     * 邮箱
     */
	@Column(name = "Email")
	@Type(value = Types.VARCHAR)
	private String email;

	/**
	 * 真实姓名
	 */
	@Column(name = "RealName")
	@Type(value = Types.VARCHAR)
	private String realName;

    /**
     * 用户类型 1 携程客服 2 供应商
     */
	@Column(name = "UserType")
	@Type(value = Types.INTEGER)
	private Integer userType;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}