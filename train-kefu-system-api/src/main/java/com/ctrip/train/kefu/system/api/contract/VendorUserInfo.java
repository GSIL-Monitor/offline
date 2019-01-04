package com.ctrip.train.kefu.system.api.contract;

import com.ctriposs.baiji.exception.BaijiRuntimeException;
import com.ctriposs.baiji.schema.*;
import com.ctriposs.baiji.specific.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "userName",
    "password",
    "userNum",
    "telephone",
    "email",
    "realName",
    "userType",
    "vendorCode",
    "vendorName"
})
public class VendorUserInfo implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"VendorUserInfo\",\"namespace\":\"" + VendorUserInfo.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"userName\",\"type\":[\"string\",\"null\"]},{\"name\":\"password\",\"type\":[\"string\",\"null\"]},{\"name\":\"userNum\",\"type\":[\"string\",\"null\"]},{\"name\":\"telephone\",\"type\":[\"string\",\"null\"]},{\"name\":\"email\",\"type\":[\"string\",\"null\"]},{\"name\":\"realName\",\"type\":[\"string\",\"null\"]},{\"name\":\"userType\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorName\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public VendorUserInfo(
        String userName,
        String password,
        String userNum,
        String telephone,
        String email,
        String realName,
        String userType,
        String vendorCode,
        String vendorName) {
        this.userName = userName;
        this.password = password;
        this.userNum = userNum;
        this.telephone = telephone;
        this.email = email;
        this.realName = realName;
        this.userType = userType;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
    }

    public VendorUserInfo() {
    }

    @JsonProperty("userName") 
    public String userName;

    @JsonProperty("password") 
    public String password;

    @JsonProperty("userNum") 
    public String userNum;

    @JsonProperty("telephone") 
    public String telephone;

    @JsonProperty("email") 
    public String email;

    @JsonProperty("realName") 
    public String realName;

    @JsonProperty("userType") 
    public String userType;

    @JsonProperty("vendorCode") 
    public String vendorCode;

    @JsonProperty("vendorName") 
    public String vendorName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(final String userNum) {
        this.userNum = userNum;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    public String getRealName() {
        return realName;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }
    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(final String vendorCode) {
        this.vendorCode = vendorCode;
    }
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.userName;
            case 1: return this.password;
            case 2: return this.userNum;
            case 3: return this.telephone;
            case 4: return this.email;
            case 5: return this.realName;
            case 6: return this.userType;
            case 7: return this.vendorCode;
            case 8: return this.vendorName;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.userName = (String)fieldValue; break;
            case 1: this.password = (String)fieldValue; break;
            case 2: this.userNum = (String)fieldValue; break;
            case 3: this.telephone = (String)fieldValue; break;
            case 4: this.email = (String)fieldValue; break;
            case 5: this.realName = (String)fieldValue; break;
            case 6: this.userType = (String)fieldValue; break;
            case 7: this.vendorCode = (String)fieldValue; break;
            case 8: this.vendorName = (String)fieldValue; break;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in put()");
        }
    }

    @Override
	public Object get(String fieldName) {
        Schema schema = getSchema();
        if (!(schema instanceof RecordSchema)) {
            return null;
        }
        Field field = ((RecordSchema) schema).getField(fieldName);
        return field != null ? get(field.getPos()) : null;
    }

    @Override
    public void put(String fieldName, Object fieldValue) {
        Schema schema = getSchema();
        if (!(schema instanceof RecordSchema)) {
            return;
        }
        Field field = ((RecordSchema) schema).getField(fieldName);
        if (field != null) {
            put(field.getPos(), fieldValue);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final VendorUserInfo other = (VendorUserInfo)obj;
        return 
            Objects.equal(this.userName, other.userName) &&
            Objects.equal(this.password, other.password) &&
            Objects.equal(this.userNum, other.userNum) &&
            Objects.equal(this.telephone, other.telephone) &&
            Objects.equal(this.email, other.email) &&
            Objects.equal(this.realName, other.realName) &&
            Objects.equal(this.userType, other.userType) &&
            Objects.equal(this.vendorCode, other.vendorCode) &&
            Objects.equal(this.vendorName, other.vendorName);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.userName == null ? 0 : this.userName.hashCode());
        result = 31 * result + (this.password == null ? 0 : this.password.hashCode());
        result = 31 * result + (this.userNum == null ? 0 : this.userNum.hashCode());
        result = 31 * result + (this.telephone == null ? 0 : this.telephone.hashCode());
        result = 31 * result + (this.email == null ? 0 : this.email.hashCode());
        result = 31 * result + (this.realName == null ? 0 : this.realName.hashCode());
        result = 31 * result + (this.userType == null ? 0 : this.userType.hashCode());
        result = 31 * result + (this.vendorCode == null ? 0 : this.vendorCode.hashCode());
        result = 31 * result + (this.vendorName == null ? 0 : this.vendorName.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("userName", userName)
            .add("password", password)
            .add("userNum", userNum)
            .add("telephone", telephone)
            .add("email", email)
            .add("realName", realName)
            .add("userType", userType)
            .add("vendorCode", vendorCode)
            .add("vendorName", vendorName)
            .toString();
    }
}
