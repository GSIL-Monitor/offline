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
    "vendorCode",
    "orderId",
    "sendName",
    "sendType",
    "noticeState",
    "noticeType",
    "opUserType",
    "noticeStatus",
    "startTime",
    "endTime"
})
public class Condition implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"Condition\",\"namespace\":\"" + Condition.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendName\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendType\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUserType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeStatus\",\"type\":[\"int\",\"null\"]},{\"name\":\"startTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"endTime\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public Condition(
        String vendorCode,
        String orderId,
        String sendName,
        String sendType,
        Integer noticeState,
        Integer noticeType,
        Integer opUserType,
        Integer noticeStatus,
        String startTime,
        String endTime) {
        this.vendorCode = vendorCode;
        this.orderId = orderId;
        this.sendName = sendName;
        this.sendType = sendType;
        this.noticeState = noticeState;
        this.noticeType = noticeType;
        this.opUserType = opUserType;
        this.noticeStatus = noticeStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Condition() {
    }

    @JsonProperty("vendorCode") 
    public String vendorCode;

    @JsonProperty("orderId") 
    public String orderId;

    @JsonProperty("sendName") 
    public String sendName;

    @JsonProperty("sendType") 
    public String sendType;

    @JsonProperty("noticeState") 
    public Integer noticeState;

    @JsonProperty("noticeType") 
    public Integer noticeType;

    @JsonProperty("opUserType") 
    public Integer opUserType;

    /**
     * 1 催单 2 紧急工单 3 一般工单
     */
    @JsonProperty("noticeStatus") 
    public Integer noticeStatus;

    @JsonProperty("startTime") 
    public String startTime;

    @JsonProperty("endTime") 
    public String endTime;

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(final String vendorCode) {
        this.vendorCode = vendorCode;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    public String getSendName() {
        return sendName;
    }

    public void setSendName(final String sendName) {
        this.sendName = sendName;
    }
    public String getSendType() {
        return sendType;
    }

    public void setSendType(final String sendType) {
        this.sendType = sendType;
    }
    public Integer getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(final Integer noticeState) {
        this.noticeState = noticeState;
    }
    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(final Integer noticeType) {
        this.noticeType = noticeType;
    }
    public Integer getOpUserType() {
        return opUserType;
    }

    public void setOpUserType(final Integer opUserType) {
        this.opUserType = opUserType;
    }

    /**
     * 1 催单 2 紧急工单 3 一般工单
     */
    public Integer getNoticeStatus() {
        return noticeStatus;
    }

    /**
     * 1 催单 2 紧急工单 3 一般工单
     */
    public void setNoticeStatus(final Integer noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.vendorCode;
            case 1: return this.orderId;
            case 2: return this.sendName;
            case 3: return this.sendType;
            case 4: return this.noticeState;
            case 5: return this.noticeType;
            case 6: return this.opUserType;
            case 7: return this.noticeStatus;
            case 8: return this.startTime;
            case 9: return this.endTime;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.vendorCode = (String)fieldValue; break;
            case 1: this.orderId = (String)fieldValue; break;
            case 2: this.sendName = (String)fieldValue; break;
            case 3: this.sendType = (String)fieldValue; break;
            case 4: this.noticeState = (Integer)fieldValue; break;
            case 5: this.noticeType = (Integer)fieldValue; break;
            case 6: this.opUserType = (Integer)fieldValue; break;
            case 7: this.noticeStatus = (Integer)fieldValue; break;
            case 8: this.startTime = (String)fieldValue; break;
            case 9: this.endTime = (String)fieldValue; break;
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

        final Condition other = (Condition)obj;
        return 
            Objects.equal(this.vendorCode, other.vendorCode) &&
            Objects.equal(this.orderId, other.orderId) &&
            Objects.equal(this.sendName, other.sendName) &&
            Objects.equal(this.sendType, other.sendType) &&
            Objects.equal(this.noticeState, other.noticeState) &&
            Objects.equal(this.noticeType, other.noticeType) &&
            Objects.equal(this.opUserType, other.opUserType) &&
            Objects.equal(this.noticeStatus, other.noticeStatus) &&
            Objects.equal(this.startTime, other.startTime) &&
            Objects.equal(this.endTime, other.endTime);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.vendorCode == null ? 0 : this.vendorCode.hashCode());
        result = 31 * result + (this.orderId == null ? 0 : this.orderId.hashCode());
        result = 31 * result + (this.sendName == null ? 0 : this.sendName.hashCode());
        result = 31 * result + (this.sendType == null ? 0 : this.sendType.hashCode());
        result = 31 * result + (this.noticeState == null ? 0 : this.noticeState.hashCode());
        result = 31 * result + (this.noticeType == null ? 0 : this.noticeType.hashCode());
        result = 31 * result + (this.opUserType == null ? 0 : this.opUserType.hashCode());
        result = 31 * result + (this.noticeStatus == null ? 0 : this.noticeStatus.hashCode());
        result = 31 * result + (this.startTime == null ? 0 : this.startTime.hashCode());
        result = 31 * result + (this.endTime == null ? 0 : this.endTime.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("vendorCode", vendorCode)
            .add("orderId", orderId)
            .add("sendName", sendName)
            .add("sendType", sendType)
            .add("noticeState", noticeState)
            .add("noticeType", noticeType)
            .add("opUserType", opUserType)
            .add("noticeStatus", noticeStatus)
            .add("startTime", startTime)
            .add("endTime", endTime)
            .toString();
    }
}
