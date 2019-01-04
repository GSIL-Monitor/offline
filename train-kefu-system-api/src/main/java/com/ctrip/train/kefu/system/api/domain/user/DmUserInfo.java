package com.ctrip.train.kefu.system.api.domain.user;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class DmUserInfo {

    /**
     * 用户名
     */
    @Column(name="userName")
    @Type(value= Types.VARCHAR)
    private String userName;

    /**
     * 密码
     */
    @Column(name="password")
    @Type(value= Types.VARCHAR)
    private String password;

    /**
     * 用户编号
     */
    @Column(name="userNum")
    @Type(value= Types.VARCHAR)
    private String userNum;

    /**
     * 手机号
     */
    @Column(name="telephone")
    @Type(value= Types.VARCHAR)
    private String telephone;

    /**
     * 邮箱
     */
    @Column(name="email")
    @Type(value= Types.VARCHAR)
    private String email;

    /**
     * 真实姓名
     */
    @Column(name="realName")
    @Type(value= Types.VARCHAR)
    private String realName;

    /**
     * 用户类型 1 携程客服 2 供应商
     */
    @Column(name="userType")
    @Type(value= Types.VARCHAR)
    private Integer userType;

    /**
     * 供应商code
     */
    @Column(name="vendorCode")
    @Type(value= Types.VARCHAR)
    private String vendorCode;

    /**
     * 供应商姓名
     */
    @Column(name="vendorName")
    @Type(value= Types.VARCHAR)
    private String vendorName;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}

