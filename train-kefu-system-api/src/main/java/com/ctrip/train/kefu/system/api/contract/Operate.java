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
    "id",
    "operateTime",
    "noticeId",
    "operateUser",
    "operateComment",
    "operateType"
})
public class Operate implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"Operate\",\"namespace\":\"" + Operate.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateComment\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateType\",\"type\":[\"int\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public Operate(
        Long id,
        String operateTime,
        Long noticeId,
        String operateUser,
        String operateComment,
        Integer operateType) {
        this.id = id;
        this.operateTime = operateTime;
        this.noticeId = noticeId;
        this.operateUser = operateUser;
        this.operateComment = operateComment;
        this.operateType = operateType;
    }

    public Operate() {
    }

    @JsonProperty("id") 
    public Long id;

    @JsonProperty("operateTime") 
    public String operateTime;

    @JsonProperty("noticeId") 
    public Long noticeId;

    @JsonProperty("operateUser") 
    public String operateUser;

    @JsonProperty("operateComment") 
    public String operateComment;

    @JsonProperty("operateType") 
    public Integer operateType;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(final String operateTime) {
        this.operateTime = operateTime;
    }
    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(final Long noticeId) {
        this.noticeId = noticeId;
    }
    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(final String operateUser) {
        this.operateUser = operateUser;
    }
    public String getOperateComment() {
        return operateComment;
    }

    public void setOperateComment(final String operateComment) {
        this.operateComment = operateComment;
    }
    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(final Integer operateType) {
        this.operateType = operateType;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.id;
            case 1: return this.operateTime;
            case 2: return this.noticeId;
            case 3: return this.operateUser;
            case 4: return this.operateComment;
            case 5: return this.operateType;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.id = (Long)fieldValue; break;
            case 1: this.operateTime = (String)fieldValue; break;
            case 2: this.noticeId = (Long)fieldValue; break;
            case 3: this.operateUser = (String)fieldValue; break;
            case 4: this.operateComment = (String)fieldValue; break;
            case 5: this.operateType = (Integer)fieldValue; break;
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

        final Operate other = (Operate)obj;
        return 
            Objects.equal(this.id, other.id) &&
            Objects.equal(this.operateTime, other.operateTime) &&
            Objects.equal(this.noticeId, other.noticeId) &&
            Objects.equal(this.operateUser, other.operateUser) &&
            Objects.equal(this.operateComment, other.operateComment) &&
            Objects.equal(this.operateType, other.operateType);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.operateTime == null ? 0 : this.operateTime.hashCode());
        result = 31 * result + (this.noticeId == null ? 0 : this.noticeId.hashCode());
        result = 31 * result + (this.operateUser == null ? 0 : this.operateUser.hashCode());
        result = 31 * result + (this.operateComment == null ? 0 : this.operateComment.hashCode());
        result = 31 * result + (this.operateType == null ? 0 : this.operateType.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("operateTime", operateTime)
            .add("noticeId", noticeId)
            .add("operateUser", operateUser)
            .add("operateComment", operateComment)
            .add("operateType", operateType)
            .toString();
    }
}
