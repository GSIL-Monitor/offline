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
    "tid",
    "fieldType",
    "fieldName",
    "fieldValue",
    "fkUpperTid"
})
public class ScmEnum implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"ScmEnum\",\"namespace\":\"" + ScmEnum.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"tid\",\"type\":[\"long\",\"null\"]},{\"name\":\"fieldType\",\"type\":[\"string\",\"null\"]},{\"name\":\"fieldName\",\"type\":[\"string\",\"null\"]},{\"name\":\"fieldValue\",\"type\":[\"string\",\"null\"]},{\"name\":\"fkUpperTid\",\"type\":[\"long\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public ScmEnum(
        Long tid,
        String fieldType,
        String fieldName,
        String fieldValue,
        Long fkUpperTid) {
        this.tid = tid;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fkUpperTid = fkUpperTid;
    }

    public ScmEnum() {
    }

    @JsonProperty("tid") 
    public Long tid;

    @JsonProperty("fieldType") 
    public String fieldType;

    @JsonProperty("fieldName") 
    public String fieldName;

    @JsonProperty("fieldValue") 
    public String fieldValue;

    @JsonProperty("fkUpperTid") 
    public Long fkUpperTid;

    public Long getTid() {
        return tid;
    }

    public void setTid(final Long tid) {
        this.tid = tid;
    }
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(final String fieldType) {
        this.fieldType = fieldType;
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(final String fieldValue) {
        this.fieldValue = fieldValue;
    }
    public Long getFkUpperTid() {
        return fkUpperTid;
    }

    public void setFkUpperTid(final Long fkUpperTid) {
        this.fkUpperTid = fkUpperTid;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.tid;
            case 1: return this.fieldType;
            case 2: return this.fieldName;
            case 3: return this.fieldValue;
            case 4: return this.fkUpperTid;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.tid = (Long)fieldValue; break;
            case 1: this.fieldType = (String)fieldValue; break;
            case 2: this.fieldName = (String)fieldValue; break;
            case 3: this.fieldValue = (String)fieldValue; break;
            case 4: this.fkUpperTid = (Long)fieldValue; break;
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

        final ScmEnum other = (ScmEnum)obj;
        return 
            Objects.equal(this.tid, other.tid) &&
            Objects.equal(this.fieldType, other.fieldType) &&
            Objects.equal(this.fieldName, other.fieldName) &&
            Objects.equal(this.fieldValue, other.fieldValue) &&
            Objects.equal(this.fkUpperTid, other.fkUpperTid);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.tid == null ? 0 : this.tid.hashCode());
        result = 31 * result + (this.fieldType == null ? 0 : this.fieldType.hashCode());
        result = 31 * result + (this.fieldName == null ? 0 : this.fieldName.hashCode());
        result = 31 * result + (this.fieldValue == null ? 0 : this.fieldValue.hashCode());
        result = 31 * result + (this.fkUpperTid == null ? 0 : this.fkUpperTid.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("tid", tid)
            .add("fieldType", fieldType)
            .add("fieldName", fieldName)
            .add("fieldValue", fieldValue)
            .add("fkUpperTid", fkUpperTid)
            .toString();
    }
}
