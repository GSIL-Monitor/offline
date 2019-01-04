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
    "vendorCode",
    "opUserType"
})
public class GetVendorNoticeNumRequest implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetVendorNoticeNumRequest\",\"namespace\":\"" + GetVendorNoticeNumRequest.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"opUserType\",\"type\":[\"int\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetVendorNoticeNumRequest(
        String vendorCode,
        Integer opUserType) {
        this.vendorCode = vendorCode;
        this.opUserType = opUserType;
    }

    public GetVendorNoticeNumRequest() {
    }

    @JsonProperty("vendorCode") 
    public String vendorCode;

    @JsonProperty("opUserType") 
    public Integer opUserType;

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(final String vendorCode) {
        this.vendorCode = vendorCode;
    }
    public Integer getOpUserType() {
        return opUserType;
    }

    public void setOpUserType(final Integer opUserType) {
        this.opUserType = opUserType;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.vendorCode;
            case 1: return this.opUserType;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.vendorCode = (String)fieldValue; break;
            case 1: this.opUserType = (Integer)fieldValue; break;
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

        final GetVendorNoticeNumRequest other = (GetVendorNoticeNumRequest)obj;
        return 
            Objects.equal(this.vendorCode, other.vendorCode) &&
            Objects.equal(this.opUserType, other.opUserType);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.vendorCode == null ? 0 : this.vendorCode.hashCode());
        result = 31 * result + (this.opUserType == null ? 0 : this.opUserType.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("vendorCode", vendorCode)
            .add("opUserType", opUserType)
            .toString();
    }
}
