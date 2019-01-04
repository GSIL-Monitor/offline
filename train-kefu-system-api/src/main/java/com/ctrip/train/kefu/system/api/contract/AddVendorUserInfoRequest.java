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
    "userInfo"
})
public class AddVendorUserInfoRequest implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"AddVendorUserInfoRequest\",\"namespace\":\"" + AddVendorUserInfoRequest.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"userInfo\",\"type\":[{\"type\":\"record\",\"name\":\"VendorUserInfo\",\"namespace\":\"" + VendorUserInfo.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"userName\",\"type\":[\"string\",\"null\"]},{\"name\":\"password\",\"type\":[\"string\",\"null\"]},{\"name\":\"userNum\",\"type\":[\"string\",\"null\"]},{\"name\":\"telephone\",\"type\":[\"string\",\"null\"]},{\"name\":\"email\",\"type\":[\"string\",\"null\"]},{\"name\":\"realName\",\"type\":[\"string\",\"null\"]},{\"name\":\"userType\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorName\",\"type\":[\"string\",\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public AddVendorUserInfoRequest(
        VendorUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public AddVendorUserInfoRequest() {
    }

    @JsonProperty("userInfo") 
    public VendorUserInfo userInfo;

    public VendorUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(final VendorUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.userInfo;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.userInfo = (VendorUserInfo)fieldValue; break;
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

        final AddVendorUserInfoRequest other = (AddVendorUserInfoRequest)obj;
        return 
            Objects.equal(this.userInfo, other.userInfo);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.userInfo == null ? 0 : this.userInfo.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("userInfo", userInfo)
            .toString();
    }
}
