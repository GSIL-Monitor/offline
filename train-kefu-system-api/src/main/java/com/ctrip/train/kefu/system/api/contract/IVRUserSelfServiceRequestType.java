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
    "ani",
    "callId",
    "operTag",
    "version"
})
public class IVRUserSelfServiceRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"IVRUserSelfServiceRequestType\",\"namespace\":\"" + IVRUserSelfServiceRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"ani\",\"type\":[\"string\",\"null\"]},{\"name\":\"callId\",\"type\":[\"string\",\"null\"]},{\"name\":\"operTag\",\"type\":[\"string\",\"null\"]},{\"name\":\"version\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public IVRUserSelfServiceRequestType(
        String ani,
        String callId,
        String operTag,
        String version) {
        this.ani = ani;
        this.callId = callId;
        this.operTag = operTag;
        this.version = version;
    }

    public IVRUserSelfServiceRequestType() {
    }

    @JsonProperty("ani") 
    public String ani;

    @JsonProperty("callId") 
    public String callId;

    @JsonProperty("operTag") 
    public String operTag;

    @JsonProperty("version") 
    public String version;

    public String getAni() {
        return ani;
    }

    public void setAni(final String ani) {
        this.ani = ani;
    }
    public String getCallId() {
        return callId;
    }

    public void setCallId(final String callId) {
        this.callId = callId;
    }
    public String getOperTag() {
        return operTag;
    }

    public void setOperTag(final String operTag) {
        this.operTag = operTag;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.ani;
            case 1: return this.callId;
            case 2: return this.operTag;
            case 3: return this.version;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.ani = (String)fieldValue; break;
            case 1: this.callId = (String)fieldValue; break;
            case 2: this.operTag = (String)fieldValue; break;
            case 3: this.version = (String)fieldValue; break;
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

        final IVRUserSelfServiceRequestType other = (IVRUserSelfServiceRequestType)obj;
        return 
            Objects.equal(this.ani, other.ani) &&
            Objects.equal(this.callId, other.callId) &&
            Objects.equal(this.operTag, other.operTag) &&
            Objects.equal(this.version, other.version);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.ani == null ? 0 : this.ani.hashCode());
        result = 31 * result + (this.callId == null ? 0 : this.callId.hashCode());
        result = 31 * result + (this.operTag == null ? 0 : this.operTag.hashCode());
        result = 31 * result + (this.version == null ? 0 : this.version.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("ani", ani)
            .add("callId", callId)
            .add("operTag", operTag)
            .add("version", version)
            .toString();
    }
}
