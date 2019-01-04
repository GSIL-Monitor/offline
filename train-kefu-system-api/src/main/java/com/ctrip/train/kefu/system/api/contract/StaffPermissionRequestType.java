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
    "eid",
    "permissionCode"
})
public class StaffPermissionRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"StaffPermissionRequestType\",\"namespace\":\"" + StaffPermissionRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"eid\",\"type\":[\"string\",\"null\"]},{\"name\":\"permissionCode\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public StaffPermissionRequestType(
        String eid,
        String permissionCode) {
        this.eid = eid;
        this.permissionCode = permissionCode;
    }

    public StaffPermissionRequestType() {
    }

    @JsonProperty("eid") 
    public String eid;

    @JsonProperty("permissionCode") 
    public String permissionCode;

    public String getEid() {
        return eid;
    }

    public void setEid(final String eid) {
        this.eid = eid;
    }
    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(final String permissionCode) {
        this.permissionCode = permissionCode;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.eid;
            case 1: return this.permissionCode;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.eid = (String)fieldValue; break;
            case 1: this.permissionCode = (String)fieldValue; break;
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

        final StaffPermissionRequestType other = (StaffPermissionRequestType)obj;
        return 
            Objects.equal(this.eid, other.eid) &&
            Objects.equal(this.permissionCode, other.permissionCode);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.eid == null ? 0 : this.eid.hashCode());
        result = 31 * result + (this.permissionCode == null ? 0 : this.permissionCode.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("eid", eid)
            .add("permissionCode", permissionCode)
            .toString();
    }
}
