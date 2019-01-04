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
    "mobileNumber",
    "ani",
    "operType",
    "orderDateString",
    "partnerName",
    "callId",
    "version"
})
public class GetIVRPredictingUserBehaviorRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetIVRPredictingUserBehaviorRequestType\",\"namespace\":\"" + GetIVRPredictingUserBehaviorRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"mobileNumber\",\"type\":[\"string\",\"null\"]},{\"name\":\"ani\",\"type\":[\"string\",\"null\"]},{\"name\":\"operType\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderDateString\",\"type\":[\"string\",\"null\"]},{\"name\":\"partnerName\",\"type\":[\"string\",\"null\"]},{\"name\":\"callId\",\"type\":[\"string\",\"null\"]},{\"name\":\"version\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetIVRPredictingUserBehaviorRequestType(
        String mobileNumber,
        String ani,
        String operType,
        String orderDateString,
        String partnerName,
        String callId,
        String version) {
        this.mobileNumber = mobileNumber;
        this.ani = ani;
        this.operType = operType;
        this.orderDateString = orderDateString;
        this.partnerName = partnerName;
        this.callId = callId;
        this.version = version;
    }

    public GetIVRPredictingUserBehaviorRequestType() {
    }

    @JsonProperty("mobileNumber") 
    public String mobileNumber;

    @JsonProperty("ani") 
    public String ani;

    @JsonProperty("operType") 
    public String operType;

    @JsonProperty("orderDateString") 
    public String orderDateString;

    @JsonProperty("partnerName") 
    public String partnerName;

    @JsonProperty("callId") 
    public String callId;

    @JsonProperty("version") 
    public String version;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getAni() {
        return ani;
    }

    public void setAni(final String ani) {
        this.ani = ani;
    }
    public String getOperType() {
        return operType;
    }

    public void setOperType(final String operType) {
        this.operType = operType;
    }
    public String getOrderDateString() {
        return orderDateString;
    }

    public void setOrderDateString(final String orderDateString) {
        this.orderDateString = orderDateString;
    }
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(final String partnerName) {
        this.partnerName = partnerName;
    }
    public String getCallId() {
        return callId;
    }

    public void setCallId(final String callId) {
        this.callId = callId;
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
            case 0: return this.mobileNumber;
            case 1: return this.ani;
            case 2: return this.operType;
            case 3: return this.orderDateString;
            case 4: return this.partnerName;
            case 5: return this.callId;
            case 6: return this.version;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.mobileNumber = (String)fieldValue; break;
            case 1: this.ani = (String)fieldValue; break;
            case 2: this.operType = (String)fieldValue; break;
            case 3: this.orderDateString = (String)fieldValue; break;
            case 4: this.partnerName = (String)fieldValue; break;
            case 5: this.callId = (String)fieldValue; break;
            case 6: this.version = (String)fieldValue; break;
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

        final GetIVRPredictingUserBehaviorRequestType other = (GetIVRPredictingUserBehaviorRequestType)obj;
        return 
            Objects.equal(this.mobileNumber, other.mobileNumber) &&
            Objects.equal(this.ani, other.ani) &&
            Objects.equal(this.operType, other.operType) &&
            Objects.equal(this.orderDateString, other.orderDateString) &&
            Objects.equal(this.partnerName, other.partnerName) &&
            Objects.equal(this.callId, other.callId) &&
            Objects.equal(this.version, other.version);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.mobileNumber == null ? 0 : this.mobileNumber.hashCode());
        result = 31 * result + (this.ani == null ? 0 : this.ani.hashCode());
        result = 31 * result + (this.operType == null ? 0 : this.operType.hashCode());
        result = 31 * result + (this.orderDateString == null ? 0 : this.orderDateString.hashCode());
        result = 31 * result + (this.partnerName == null ? 0 : this.partnerName.hashCode());
        result = 31 * result + (this.callId == null ? 0 : this.callId.hashCode());
        result = 31 * result + (this.version == null ? 0 : this.version.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("mobileNumber", mobileNumber)
            .add("ani", ani)
            .add("operType", operType)
            .add("orderDateString", orderDateString)
            .add("partnerName", partnerName)
            .add("callId", callId)
            .add("version", version)
            .toString();
    }
}
