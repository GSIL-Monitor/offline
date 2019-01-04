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
import java.util.List;

@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "id",
    "noticeId",
    "emergeState",
    "orderId",
    "noticeType",
    "noticeTypeName",
    "noticeSecondType",
    "noticeSecondTypeName",
    "noticeState",
    "opCount",
    "opUseTyper",
    "opUser",
    "opTime",
    "vendorCode",
    "vendorName",
    "contents",
    "sendType",
    "sendCode",
    "sendName",
    "sendTime",
    "appointedProcessTime",
    "completeTime",
    "operateInfos"
})
public class VendorNotice implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"VendorNotice\",\"namespace\":\"" + VendorNotice.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"emergeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeSecondType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeSecondTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"opCount\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUseTyper\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"opTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorName\",\"type\":[\"string\",\"null\"]},{\"name\":\"contents\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendType\",\"type\":[\"int\",\"null\"]},{\"name\":\"sendCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendName\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"appointedProcessTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"completeTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateInfos\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Operate\",\"namespace\":\"" + Operate.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateComment\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateType\",\"type\":[\"int\",\"null\"]}]}},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public VendorNotice(
        Long id,
        Long noticeId,
        Integer emergeState,
        String orderId,
        Integer noticeType,
        String noticeTypeName,
        Integer noticeSecondType,
        String noticeSecondTypeName,
        Integer noticeState,
        Integer opCount,
        Integer opUseTyper,
        String opUser,
        String opTime,
        String vendorCode,
        String vendorName,
        String contents,
        Integer sendType,
        String sendCode,
        String sendName,
        String sendTime,
        String appointedProcessTime,
        String completeTime,
        List<Operate> operateInfos) {
        this.id = id;
        this.noticeId = noticeId;
        this.emergeState = emergeState;
        this.orderId = orderId;
        this.noticeType = noticeType;
        this.noticeTypeName = noticeTypeName;
        this.noticeSecondType = noticeSecondType;
        this.noticeSecondTypeName = noticeSecondTypeName;
        this.noticeState = noticeState;
        this.opCount = opCount;
        this.opUseTyper = opUseTyper;
        this.opUser = opUser;
        this.opTime = opTime;
        this.vendorCode = vendorCode;
        this.vendorName = vendorName;
        this.contents = contents;
        this.sendType = sendType;
        this.sendCode = sendCode;
        this.sendName = sendName;
        this.sendTime = sendTime;
        this.appointedProcessTime = appointedProcessTime;
        this.completeTime = completeTime;
        this.operateInfos = operateInfos;
    }

    public VendorNotice() {
    }

    @JsonProperty("id") 
    public Long id;

    @JsonProperty("noticeId") 
    public Long noticeId;

    @JsonProperty("emergeState") 
    public Integer emergeState;

    @JsonProperty("orderId") 
    public String orderId;

    @JsonProperty("noticeType") 
    public Integer noticeType;

    @JsonProperty("noticeTypeName") 
    public String noticeTypeName;

    @JsonProperty("noticeSecondType") 
    public Integer noticeSecondType;

    @JsonProperty("noticeSecondTypeName") 
    public String noticeSecondTypeName;

    @JsonProperty("noticeState") 
    public Integer noticeState;

    @JsonProperty("opCount") 
    public Integer opCount;

    @JsonProperty("opUseTyper") 
    public Integer opUseTyper;

    @JsonProperty("opUser") 
    public String opUser;

    @JsonProperty("opTime") 
    public String opTime;

    @JsonProperty("vendorCode") 
    public String vendorCode;

    @JsonProperty("vendorName") 
    public String vendorName;

    @JsonProperty("contents") 
    public String contents;

    @JsonProperty("sendType") 
    public Integer sendType;

    @JsonProperty("sendCode") 
    public String sendCode;

    @JsonProperty("sendName") 
    public String sendName;

    @JsonProperty("sendTime") 
    public String sendTime;

    @JsonProperty("appointedProcessTime") 
    public String appointedProcessTime;

    @JsonProperty("completeTime") 
    public String completeTime;

    @JsonProperty("operateInfos") 
    public List<Operate> operateInfos;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(final Long noticeId) {
        this.noticeId = noticeId;
    }
    public Integer getEmergeState() {
        return emergeState;
    }

    public void setEmergeState(final Integer emergeState) {
        this.emergeState = emergeState;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }
    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(final Integer noticeType) {
        this.noticeType = noticeType;
    }
    public String getNoticeTypeName() {
        return noticeTypeName;
    }

    public void setNoticeTypeName(final String noticeTypeName) {
        this.noticeTypeName = noticeTypeName;
    }
    public Integer getNoticeSecondType() {
        return noticeSecondType;
    }

    public void setNoticeSecondType(final Integer noticeSecondType) {
        this.noticeSecondType = noticeSecondType;
    }
    public String getNoticeSecondTypeName() {
        return noticeSecondTypeName;
    }

    public void setNoticeSecondTypeName(final String noticeSecondTypeName) {
        this.noticeSecondTypeName = noticeSecondTypeName;
    }
    public Integer getNoticeState() {
        return noticeState;
    }

    public void setNoticeState(final Integer noticeState) {
        this.noticeState = noticeState;
    }
    public Integer getOpCount() {
        return opCount;
    }

    public void setOpCount(final Integer opCount) {
        this.opCount = opCount;
    }
    public Integer getOpUseTyper() {
        return opUseTyper;
    }

    public void setOpUseTyper(final Integer opUseTyper) {
        this.opUseTyper = opUseTyper;
    }
    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(final String opUser) {
        this.opUser = opUser;
    }
    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(final String opTime) {
        this.opTime = opTime;
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
    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }
    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(final Integer sendType) {
        this.sendType = sendType;
    }
    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(final String sendCode) {
        this.sendCode = sendCode;
    }
    public String getSendName() {
        return sendName;
    }

    public void setSendName(final String sendName) {
        this.sendName = sendName;
    }
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(final String sendTime) {
        this.sendTime = sendTime;
    }
    public String getAppointedProcessTime() {
        return appointedProcessTime;
    }

    public void setAppointedProcessTime(final String appointedProcessTime) {
        this.appointedProcessTime = appointedProcessTime;
    }
    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(final String completeTime) {
        this.completeTime = completeTime;
    }
    public List<Operate> getOperateInfos() {
        return operateInfos;
    }

    public void setOperateInfos(final List<Operate> operateInfos) {
        this.operateInfos = operateInfos;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.id;
            case 1: return this.noticeId;
            case 2: return this.emergeState;
            case 3: return this.orderId;
            case 4: return this.noticeType;
            case 5: return this.noticeTypeName;
            case 6: return this.noticeSecondType;
            case 7: return this.noticeSecondTypeName;
            case 8: return this.noticeState;
            case 9: return this.opCount;
            case 10: return this.opUseTyper;
            case 11: return this.opUser;
            case 12: return this.opTime;
            case 13: return this.vendorCode;
            case 14: return this.vendorName;
            case 15: return this.contents;
            case 16: return this.sendType;
            case 17: return this.sendCode;
            case 18: return this.sendName;
            case 19: return this.sendTime;
            case 20: return this.appointedProcessTime;
            case 21: return this.completeTime;
            case 22: return this.operateInfos;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.id = (Long)fieldValue; break;
            case 1: this.noticeId = (Long)fieldValue; break;
            case 2: this.emergeState = (Integer)fieldValue; break;
            case 3: this.orderId = (String)fieldValue; break;
            case 4: this.noticeType = (Integer)fieldValue; break;
            case 5: this.noticeTypeName = (String)fieldValue; break;
            case 6: this.noticeSecondType = (Integer)fieldValue; break;
            case 7: this.noticeSecondTypeName = (String)fieldValue; break;
            case 8: this.noticeState = (Integer)fieldValue; break;
            case 9: this.opCount = (Integer)fieldValue; break;
            case 10: this.opUseTyper = (Integer)fieldValue; break;
            case 11: this.opUser = (String)fieldValue; break;
            case 12: this.opTime = (String)fieldValue; break;
            case 13: this.vendorCode = (String)fieldValue; break;
            case 14: this.vendorName = (String)fieldValue; break;
            case 15: this.contents = (String)fieldValue; break;
            case 16: this.sendType = (Integer)fieldValue; break;
            case 17: this.sendCode = (String)fieldValue; break;
            case 18: this.sendName = (String)fieldValue; break;
            case 19: this.sendTime = (String)fieldValue; break;
            case 20: this.appointedProcessTime = (String)fieldValue; break;
            case 21: this.completeTime = (String)fieldValue; break;
            case 22: this.operateInfos = (List<Operate>)fieldValue; break;
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

        final VendorNotice other = (VendorNotice)obj;
        return 
            Objects.equal(this.id, other.id) &&
            Objects.equal(this.noticeId, other.noticeId) &&
            Objects.equal(this.emergeState, other.emergeState) &&
            Objects.equal(this.orderId, other.orderId) &&
            Objects.equal(this.noticeType, other.noticeType) &&
            Objects.equal(this.noticeTypeName, other.noticeTypeName) &&
            Objects.equal(this.noticeSecondType, other.noticeSecondType) &&
            Objects.equal(this.noticeSecondTypeName, other.noticeSecondTypeName) &&
            Objects.equal(this.noticeState, other.noticeState) &&
            Objects.equal(this.opCount, other.opCount) &&
            Objects.equal(this.opUseTyper, other.opUseTyper) &&
            Objects.equal(this.opUser, other.opUser) &&
            Objects.equal(this.opTime, other.opTime) &&
            Objects.equal(this.vendorCode, other.vendorCode) &&
            Objects.equal(this.vendorName, other.vendorName) &&
            Objects.equal(this.contents, other.contents) &&
            Objects.equal(this.sendType, other.sendType) &&
            Objects.equal(this.sendCode, other.sendCode) &&
            Objects.equal(this.sendName, other.sendName) &&
            Objects.equal(this.sendTime, other.sendTime) &&
            Objects.equal(this.appointedProcessTime, other.appointedProcessTime) &&
            Objects.equal(this.completeTime, other.completeTime) &&
            Objects.equal(this.operateInfos, other.operateInfos);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.noticeId == null ? 0 : this.noticeId.hashCode());
        result = 31 * result + (this.emergeState == null ? 0 : this.emergeState.hashCode());
        result = 31 * result + (this.orderId == null ? 0 : this.orderId.hashCode());
        result = 31 * result + (this.noticeType == null ? 0 : this.noticeType.hashCode());
        result = 31 * result + (this.noticeTypeName == null ? 0 : this.noticeTypeName.hashCode());
        result = 31 * result + (this.noticeSecondType == null ? 0 : this.noticeSecondType.hashCode());
        result = 31 * result + (this.noticeSecondTypeName == null ? 0 : this.noticeSecondTypeName.hashCode());
        result = 31 * result + (this.noticeState == null ? 0 : this.noticeState.hashCode());
        result = 31 * result + (this.opCount == null ? 0 : this.opCount.hashCode());
        result = 31 * result + (this.opUseTyper == null ? 0 : this.opUseTyper.hashCode());
        result = 31 * result + (this.opUser == null ? 0 : this.opUser.hashCode());
        result = 31 * result + (this.opTime == null ? 0 : this.opTime.hashCode());
        result = 31 * result + (this.vendorCode == null ? 0 : this.vendorCode.hashCode());
        result = 31 * result + (this.vendorName == null ? 0 : this.vendorName.hashCode());
        result = 31 * result + (this.contents == null ? 0 : this.contents.hashCode());
        result = 31 * result + (this.sendType == null ? 0 : this.sendType.hashCode());
        result = 31 * result + (this.sendCode == null ? 0 : this.sendCode.hashCode());
        result = 31 * result + (this.sendName == null ? 0 : this.sendName.hashCode());
        result = 31 * result + (this.sendTime == null ? 0 : this.sendTime.hashCode());
        result = 31 * result + (this.appointedProcessTime == null ? 0 : this.appointedProcessTime.hashCode());
        result = 31 * result + (this.completeTime == null ? 0 : this.completeTime.hashCode());
        result = 31 * result + (this.operateInfos == null ? 0 : this.operateInfos.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("noticeId", noticeId)
            .add("emergeState", emergeState)
            .add("orderId", orderId)
            .add("noticeType", noticeType)
            .add("noticeTypeName", noticeTypeName)
            .add("noticeSecondType", noticeSecondType)
            .add("noticeSecondTypeName", noticeSecondTypeName)
            .add("noticeState", noticeState)
            .add("opCount", opCount)
            .add("opUseTyper", opUseTyper)
            .add("opUser", opUser)
            .add("opTime", opTime)
            .add("vendorCode", vendorCode)
            .add("vendorName", vendorName)
            .add("contents", contents)
            .add("sendType", sendType)
            .add("sendCode", sendCode)
            .add("sendName", sendName)
            .add("sendTime", sendTime)
            .add("appointedProcessTime", appointedProcessTime)
            .add("completeTime", completeTime)
            .add("operateInfos", operateInfos)
            .toString();
    }
}
