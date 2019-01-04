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
    "orderID",
    "contactUser",
    "contactPhone",
    "noticeSource",
    "appointedProcessTime",
    "emergeState",
    "envenType",
    "noticeType",
    "noticeSecondType",
    "contents",
    "orderType",
    "productLine",
    "channelSource",
    "enterUser"
})
public class SendNoticeRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"SendNoticeRequestType\",\"namespace\":\"" + SendNoticeRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"orderID\",\"type\":[\"string\",\"null\"]},{\"name\":\"contactUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"contactPhone\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeSource\",\"type\":[\"int\",\"null\"]},{\"name\":\"appointedProcessTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"emergeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"envenType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeSecondType\",\"type\":[\"int\",\"null\"]},{\"name\":\"contents\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderType\",\"type\":[\"int\",\"null\"]},{\"name\":\"productLine\",\"type\":[\"string\",\"null\"]},{\"name\":\"channelSource\",\"type\":[\"string\",\"null\"]},{\"name\":\"enterUser\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public SendNoticeRequestType(
        String orderID,
        String contactUser,
        String contactPhone,
        Integer noticeSource,
        String appointedProcessTime,
        Integer emergeState,
        Integer envenType,
        Integer noticeType,
        Integer noticeSecondType,
        String contents,
        Integer orderType,
        String productLine,
        String channelSource,
        String enterUser) {
        this.orderID = orderID;
        this.contactUser = contactUser;
        this.contactPhone = contactPhone;
        this.noticeSource = noticeSource;
        this.appointedProcessTime = appointedProcessTime;
        this.emergeState = emergeState;
        this.envenType = envenType;
        this.noticeType = noticeType;
        this.noticeSecondType = noticeSecondType;
        this.contents = contents;
        this.orderType = orderType;
        this.productLine = productLine;
        this.channelSource = channelSource;
        this.enterUser = enterUser;
    }

    public SendNoticeRequestType() {
    }

    @JsonProperty("orderID") 
    public String orderID;

    @JsonProperty("contactUser") 
    public String contactUser;

    @JsonProperty("contactPhone") 
    public String contactPhone;

    @JsonProperty("noticeSource") 
    public Integer noticeSource;

    @JsonProperty("appointedProcessTime") 
    public String appointedProcessTime;

    @JsonProperty("emergeState") 
    public Integer emergeState;

    @JsonProperty("envenType") 
    public Integer envenType;

    @JsonProperty("noticeType") 
    public Integer noticeType;

    @JsonProperty("noticeSecondType") 
    public Integer noticeSecondType;

    @JsonProperty("contents") 
    public String contents;

    @JsonProperty("orderType") 
    public Integer orderType;

    @JsonProperty("productLine") 
    public String productLine;

    @JsonProperty("channelSource") 
    public String channelSource;

    @JsonProperty("enterUser") 
    public String enterUser;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }
    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(final String contactUser) {
        this.contactUser = contactUser;
    }
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public Integer getNoticeSource() {
        return noticeSource;
    }

    public void setNoticeSource(final Integer noticeSource) {
        this.noticeSource = noticeSource;
    }
    public String getAppointedProcessTime() {
        return appointedProcessTime;
    }

    public void setAppointedProcessTime(final String appointedProcessTime) {
        this.appointedProcessTime = appointedProcessTime;
    }
    public Integer getEmergeState() {
        return emergeState;
    }

    public void setEmergeState(final Integer emergeState) {
        this.emergeState = emergeState;
    }
    public Integer getEnvenType() {
        return envenType;
    }

    public void setEnvenType(final Integer envenType) {
        this.envenType = envenType;
    }
    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(final Integer noticeType) {
        this.noticeType = noticeType;
    }
    public Integer getNoticeSecondType() {
        return noticeSecondType;
    }

    public void setNoticeSecondType(final Integer noticeSecondType) {
        this.noticeSecondType = noticeSecondType;
    }
    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(final Integer orderType) {
        this.orderType = orderType;
    }
    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(final String productLine) {
        this.productLine = productLine;
    }
    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(final String channelSource) {
        this.channelSource = channelSource;
    }
    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(final String enterUser) {
        this.enterUser = enterUser;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.orderID;
            case 1: return this.contactUser;
            case 2: return this.contactPhone;
            case 3: return this.noticeSource;
            case 4: return this.appointedProcessTime;
            case 5: return this.emergeState;
            case 6: return this.envenType;
            case 7: return this.noticeType;
            case 8: return this.noticeSecondType;
            case 9: return this.contents;
            case 10: return this.orderType;
            case 11: return this.productLine;
            case 12: return this.channelSource;
            case 13: return this.enterUser;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.orderID = (String)fieldValue; break;
            case 1: this.contactUser = (String)fieldValue; break;
            case 2: this.contactPhone = (String)fieldValue; break;
            case 3: this.noticeSource = (Integer)fieldValue; break;
            case 4: this.appointedProcessTime = (String)fieldValue; break;
            case 5: this.emergeState = (Integer)fieldValue; break;
            case 6: this.envenType = (Integer)fieldValue; break;
            case 7: this.noticeType = (Integer)fieldValue; break;
            case 8: this.noticeSecondType = (Integer)fieldValue; break;
            case 9: this.contents = (String)fieldValue; break;
            case 10: this.orderType = (Integer)fieldValue; break;
            case 11: this.productLine = (String)fieldValue; break;
            case 12: this.channelSource = (String)fieldValue; break;
            case 13: this.enterUser = (String)fieldValue; break;
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

        final SendNoticeRequestType other = (SendNoticeRequestType)obj;
        return 
            Objects.equal(this.orderID, other.orderID) &&
            Objects.equal(this.contactUser, other.contactUser) &&
            Objects.equal(this.contactPhone, other.contactPhone) &&
            Objects.equal(this.noticeSource, other.noticeSource) &&
            Objects.equal(this.appointedProcessTime, other.appointedProcessTime) &&
            Objects.equal(this.emergeState, other.emergeState) &&
            Objects.equal(this.envenType, other.envenType) &&
            Objects.equal(this.noticeType, other.noticeType) &&
            Objects.equal(this.noticeSecondType, other.noticeSecondType) &&
            Objects.equal(this.contents, other.contents) &&
            Objects.equal(this.orderType, other.orderType) &&
            Objects.equal(this.productLine, other.productLine) &&
            Objects.equal(this.channelSource, other.channelSource) &&
            Objects.equal(this.enterUser, other.enterUser);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.orderID == null ? 0 : this.orderID.hashCode());
        result = 31 * result + (this.contactUser == null ? 0 : this.contactUser.hashCode());
        result = 31 * result + (this.contactPhone == null ? 0 : this.contactPhone.hashCode());
        result = 31 * result + (this.noticeSource == null ? 0 : this.noticeSource.hashCode());
        result = 31 * result + (this.appointedProcessTime == null ? 0 : this.appointedProcessTime.hashCode());
        result = 31 * result + (this.emergeState == null ? 0 : this.emergeState.hashCode());
        result = 31 * result + (this.envenType == null ? 0 : this.envenType.hashCode());
        result = 31 * result + (this.noticeType == null ? 0 : this.noticeType.hashCode());
        result = 31 * result + (this.noticeSecondType == null ? 0 : this.noticeSecondType.hashCode());
        result = 31 * result + (this.contents == null ? 0 : this.contents.hashCode());
        result = 31 * result + (this.orderType == null ? 0 : this.orderType.hashCode());
        result = 31 * result + (this.productLine == null ? 0 : this.productLine.hashCode());
        result = 31 * result + (this.channelSource == null ? 0 : this.channelSource.hashCode());
        result = 31 * result + (this.enterUser == null ? 0 : this.enterUser.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("orderID", orderID)
            .add("contactUser", contactUser)
            .add("contactPhone", contactPhone)
            .add("noticeSource", noticeSource)
            .add("appointedProcessTime", appointedProcessTime)
            .add("emergeState", emergeState)
            .add("envenType", envenType)
            .add("noticeType", noticeType)
            .add("noticeSecondType", noticeSecondType)
            .add("contents", contents)
            .add("orderType", orderType)
            .add("productLine", productLine)
            .add("channelSource", channelSource)
            .add("enterUser", enterUser)
            .toString();
    }
}
