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
    "operate"
})
public class AddNoticeOperateRequest implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"AddNoticeOperateRequest\",\"namespace\":\"" + AddNoticeOperateRequest.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"operate\",\"type\":[{\"type\":\"record\",\"name\":\"Operate\",\"namespace\":\"" + Operate.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateComment\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateType\",\"type\":[\"int\",\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public AddNoticeOperateRequest(
        Operate operate) {
        this.operate = operate;
    }

    public AddNoticeOperateRequest() {
    }

    @JsonProperty("operate") 
    public Operate operate;

    public Operate getOperate() {
        return operate;
    }

    public void setOperate(final Operate operate) {
        this.operate = operate;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.operate;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.operate = (Operate)fieldValue; break;
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

        final AddNoticeOperateRequest other = (AddNoticeOperateRequest)obj;
        return 
            Objects.equal(this.operate, other.operate);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.operate == null ? 0 : this.operate.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("operate", operate)
            .toString();
    }
}
