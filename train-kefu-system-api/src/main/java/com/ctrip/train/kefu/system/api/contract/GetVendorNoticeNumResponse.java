package com.ctrip.train.kefu.system.api.contract;

import com.ctriposs.baiji.exception.BaijiRuntimeException;
import com.ctriposs.baiji.rpc.common.*;
import com.ctriposs.baiji.rpc.common.types.ResponseStatusType;
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
    "pendingNum",
    "repliedNum",
    "opCountNum",
    "emergeStateNum",
    "numalNum",
    "responseStatus"
})
public class GetVendorNoticeNumResponse implements SpecificRecord, HasResponseStatus {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetVendorNoticeNumResponse\",\"namespace\":\"" + GetVendorNoticeNumResponse.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"pendingNum\",\"type\":[\"int\",\"null\"]},{\"name\":\"repliedNum\",\"type\":[\"int\",\"null\"]},{\"name\":\"opCountNum\",\"type\":[\"int\",\"null\"]},{\"name\":\"emergeStateNum\",\"type\":[\"int\",\"null\"]},{\"name\":\"numalNum\",\"type\":[\"int\",\"null\"]},{\"name\":\"ResponseStatus\",\"type\":[{\"type\":\"record\",\"name\":\"ResponseStatusType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Timestamp\",\"type\":[\"datetime\",\"null\"]},{\"name\":\"Ack\",\"type\":[{\"type\":\"enum\",\"name\":\"AckCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Success\",\"Failure\",\"Warning\",\"PartialFailure\"]},\"null\"]},{\"name\":\"Errors\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorDataType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Message\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"StackTrace\",\"type\":[\"string\",\"null\"]},{\"name\":\"SeverityCode\",\"type\":[{\"type\":\"enum\",\"name\":\"SeverityCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Error\",\"Warning\"]},\"null\"]},{\"name\":\"ErrorFields\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorFieldType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"FieldName\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"Message\",\"type\":[\"string\",\"null\"]}]}},\"null\"]},{\"name\":\"ErrorClassification\",\"type\":[{\"type\":\"enum\",\"name\":\"ErrorClassificationCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"ServiceError\",\"ValidationError\",\"FrameworkError\",\"SLAError\",\"SecurityError\"]},\"null\"]}]}},\"null\"]},{\"name\":\"Build\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"Extension\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ExtensionType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Id\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"ContentType\",\"type\":[\"string\",\"null\"]},{\"name\":\"Value\",\"type\":[\"string\",\"null\"]}]}},\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetVendorNoticeNumResponse(
        Integer pendingNum,
        Integer repliedNum,
        Integer opCountNum,
        Integer emergeStateNum,
        Integer numalNum,
        ResponseStatusType responseStatus) {
        this.pendingNum = pendingNum;
        this.repliedNum = repliedNum;
        this.opCountNum = opCountNum;
        this.emergeStateNum = emergeStateNum;
        this.numalNum = numalNum;
        this.responseStatus = responseStatus;
    }

    public GetVendorNoticeNumResponse() {
    }

    @JsonProperty("pendingNum") 
    public Integer pendingNum;

    @JsonProperty("repliedNum") 
    public Integer repliedNum;

    @JsonProperty("opCountNum") 
    public Integer opCountNum;

    @JsonProperty("emergeStateNum") 
    public Integer emergeStateNum;

    @JsonProperty("numalNum") 
    public Integer numalNum;

    @JsonProperty("ResponseStatus") 
    public ResponseStatusType responseStatus;

    public Integer getPendingNum() {
        return pendingNum;
    }

    public void setPendingNum(final Integer pendingNum) {
        this.pendingNum = pendingNum;
    }
    public Integer getRepliedNum() {
        return repliedNum;
    }

    public void setRepliedNum(final Integer repliedNum) {
        this.repliedNum = repliedNum;
    }
    public Integer getOpCountNum() {
        return opCountNum;
    }

    public void setOpCountNum(final Integer opCountNum) {
        this.opCountNum = opCountNum;
    }
    public Integer getEmergeStateNum() {
        return emergeStateNum;
    }

    public void setEmergeStateNum(final Integer emergeStateNum) {
        this.emergeStateNum = emergeStateNum;
    }
    public Integer getNumalNum() {
        return numalNum;
    }

    public void setNumalNum(final Integer numalNum) {
        this.numalNum = numalNum;
    }
    public ResponseStatusType getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(final ResponseStatusType responseStatus) {
        this.responseStatus = responseStatus;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.pendingNum;
            case 1: return this.repliedNum;
            case 2: return this.opCountNum;
            case 3: return this.emergeStateNum;
            case 4: return this.numalNum;
            case 5: return this.responseStatus;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.pendingNum = (Integer)fieldValue; break;
            case 1: this.repliedNum = (Integer)fieldValue; break;
            case 2: this.opCountNum = (Integer)fieldValue; break;
            case 3: this.emergeStateNum = (Integer)fieldValue; break;
            case 4: this.numalNum = (Integer)fieldValue; break;
            case 5: this.responseStatus = (ResponseStatusType)fieldValue; break;
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

        final GetVendorNoticeNumResponse other = (GetVendorNoticeNumResponse)obj;
        return 
            Objects.equal(this.pendingNum, other.pendingNum) &&
            Objects.equal(this.repliedNum, other.repliedNum) &&
            Objects.equal(this.opCountNum, other.opCountNum) &&
            Objects.equal(this.emergeStateNum, other.emergeStateNum) &&
            Objects.equal(this.numalNum, other.numalNum) &&
            Objects.equal(this.responseStatus, other.responseStatus);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.pendingNum == null ? 0 : this.pendingNum.hashCode());
        result = 31 * result + (this.repliedNum == null ? 0 : this.repliedNum.hashCode());
        result = 31 * result + (this.opCountNum == null ? 0 : this.opCountNum.hashCode());
        result = 31 * result + (this.emergeStateNum == null ? 0 : this.emergeStateNum.hashCode());
        result = 31 * result + (this.numalNum == null ? 0 : this.numalNum.hashCode());
        result = 31 * result + (this.responseStatus == null ? 0 : this.responseStatus.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("pendingNum", pendingNum)
            .add("repliedNum", repliedNum)
            .add("opCountNum", opCountNum)
            .add("emergeStateNum", emergeStateNum)
            .add("numalNum", numalNum)
            .add("responseStatus", responseStatus)
            .toString();
    }
}
