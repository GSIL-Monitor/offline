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
    "noticeSource",
    "noticeType",
    "noticeSecondType",
    "enterUser"
})
public class NoticeExisRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"NoticeExisRequestType\",\"namespace\":\"" + NoticeExisRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"orderID\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeSource\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeSecondType\",\"type\":[\"int\",\"null\"]},{\"name\":\"enterUser\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public NoticeExisRequestType(
        String orderID,
        Integer noticeSource,
        Integer noticeType,
        Integer noticeSecondType,
        String enterUser) {
        this.orderID = orderID;
        this.noticeSource = noticeSource;
        this.noticeType = noticeType;
        this.noticeSecondType = noticeSecondType;
        this.enterUser = enterUser;
    }

    public NoticeExisRequestType() {
    }

    @JsonProperty("orderID") 
    public String orderID;

    @JsonProperty("noticeSource") 
    public Integer noticeSource;

    @JsonProperty("noticeType") 
    public Integer noticeType;

    @JsonProperty("noticeSecondType") 
    public Integer noticeSecondType;

    @JsonProperty("enterUser") 
    public String enterUser;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }
    public Integer getNoticeSource() {
        return noticeSource;
    }

    public void setNoticeSource(final Integer noticeSource) {
        this.noticeSource = noticeSource;
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
            case 1: return this.noticeSource;
            case 2: return this.noticeType;
            case 3: return this.noticeSecondType;
            case 4: return this.enterUser;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.orderID = (String)fieldValue; break;
            case 1: this.noticeSource = (Integer)fieldValue; break;
            case 2: this.noticeType = (Integer)fieldValue; break;
            case 3: this.noticeSecondType = (Integer)fieldValue; break;
            case 4: this.enterUser = (String)fieldValue; break;
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

        final NoticeExisRequestType other = (NoticeExisRequestType)obj;
        return 
            Objects.equal(this.orderID, other.orderID) &&
            Objects.equal(this.noticeSource, other.noticeSource) &&
            Objects.equal(this.noticeType, other.noticeType) &&
            Objects.equal(this.noticeSecondType, other.noticeSecondType) &&
            Objects.equal(this.enterUser, other.enterUser);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.orderID == null ? 0 : this.orderID.hashCode());
        result = 31 * result + (this.noticeSource == null ? 0 : this.noticeSource.hashCode());
        result = 31 * result + (this.noticeType == null ? 0 : this.noticeType.hashCode());
        result = 31 * result + (this.noticeSecondType == null ? 0 : this.noticeSecondType.hashCode());
        result = 31 * result + (this.enterUser == null ? 0 : this.enterUser.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("orderID", orderID)
            .add("noticeSource", noticeSource)
            .add("noticeType", noticeType)
            .add("noticeSecondType", noticeSecondType)
            .add("enterUser", enterUser)
            .toString();
    }
}
