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
    "noticeId",
    "contents"
})
public class ReminderKefuNoticeRequestType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"ReminderKefuNoticeRequestType\",\"namespace\":\"" + ReminderKefuNoticeRequestType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"contents\",\"type\":[\"string\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public ReminderKefuNoticeRequestType(
        Long noticeId,
        String contents) {
        this.noticeId = noticeId;
        this.contents = contents;
    }

    public ReminderKefuNoticeRequestType() {
    }

    @JsonProperty("noticeId") 
    public Long noticeId;

    @JsonProperty("contents") 
    public String contents;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(final Long noticeId) {
        this.noticeId = noticeId;
    }
    public String getContents() {
        return contents;
    }

    public void setContents(final String contents) {
        this.contents = contents;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.noticeId;
            case 1: return this.contents;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.noticeId = (Long)fieldValue; break;
            case 1: this.contents = (String)fieldValue; break;
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

        final ReminderKefuNoticeRequestType other = (ReminderKefuNoticeRequestType)obj;
        return 
            Objects.equal(this.noticeId, other.noticeId) &&
            Objects.equal(this.contents, other.contents);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.noticeId == null ? 0 : this.noticeId.hashCode());
        result = 31 * result + (this.contents == null ? 0 : this.contents.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("noticeId", noticeId)
            .add("contents", contents)
            .toString();
    }
}
