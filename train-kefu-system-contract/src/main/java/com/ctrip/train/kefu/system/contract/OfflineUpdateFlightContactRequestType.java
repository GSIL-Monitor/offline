package com.ctrip.train.kefu.system.contract;

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
    "orderId",
    "mobilePhone",
    "eid"
})
public class OfflineUpdateFlightContactRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"OfflineUpdateFlightContactRequestType\",\"namespace\":\"" + OfflineUpdateFlightContactRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"mobilePhone\",\"type\":[\"string\",\"null\"]},{\"name\":\"eid\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public OfflineUpdateFlightContactRequestType(
        String orderId,
        String mobilePhone,
        String eid) {
        this.orderId = orderId;
        this.mobilePhone = mobilePhone;
        this.eid = eid;
    }

    public OfflineUpdateFlightContactRequestType() {
    }

    @JsonProperty("orderId") 
    public String orderId;

    @JsonProperty("mobilePhone") 
    public String mobilePhone;

    @JsonProperty("eid") 
    public String eid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(final String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getEid() {
        return eid;
    }

    public void setEid(final String eid) {
        this.eid = eid;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.orderId;
            case 1: return this.mobilePhone;
            case 2: return this.eid;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.orderId = (String)fieldValue; break;
            case 1: this.mobilePhone = (String)fieldValue; break;
            case 2: this.eid = (String)fieldValue; break;
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

        final OfflineUpdateFlightContactRequestType other = (OfflineUpdateFlightContactRequestType)obj;
        return 
            Objects.equal(this.orderId, other.orderId) &&
            Objects.equal(this.mobilePhone, other.mobilePhone) &&
            Objects.equal(this.eid, other.eid);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.orderId == null ? 0 : this.orderId.hashCode());
        result = 31 * result + (this.mobilePhone == null ? 0 : this.mobilePhone.hashCode());
        result = 31 * result + (this.eid == null ? 0 : this.eid.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("orderId", orderId)
            .add("mobilePhone", mobilePhone)
            .add("eid", eid)
            .toString();
    }
}
