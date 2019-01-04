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
    "pageIndex",
    "pageSize",
    "condition"
})
public class GetVendorNoticeListRequest implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetVendorNoticeListRequest\",\"namespace\":\"" + GetVendorNoticeListRequest.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"pageIndex\",\"type\":[\"int\",\"null\"]},{\"name\":\"pageSize\",\"type\":[\"int\",\"null\"]},{\"name\":\"condition\",\"type\":[{\"type\":\"record\",\"name\":\"Condition\",\"namespace\":\"" + Condition.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendName\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendType\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUserType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeStatus\",\"type\":[\"int\",\"null\"]},{\"name\":\"startTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"endTime\",\"type\":[\"string\",\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetVendorNoticeListRequest(
        Integer pageIndex,
        Integer pageSize,
        Condition condition) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.condition = condition;
    }

    public GetVendorNoticeListRequest() {
    }

    @JsonProperty("pageIndex") 
    public Integer pageIndex;

    @JsonProperty("pageSize") 
    public Integer pageSize;

    @JsonProperty("condition") 
    public Condition condition;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(final Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(final Condition condition) {
        this.condition = condition;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.pageIndex;
            case 1: return this.pageSize;
            case 2: return this.condition;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.pageIndex = (Integer)fieldValue; break;
            case 1: this.pageSize = (Integer)fieldValue; break;
            case 2: this.condition = (Condition)fieldValue; break;
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

        final GetVendorNoticeListRequest other = (GetVendorNoticeListRequest)obj;
        return 
            Objects.equal(this.pageIndex, other.pageIndex) &&
            Objects.equal(this.pageSize, other.pageSize) &&
            Objects.equal(this.condition, other.condition);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.pageIndex == null ? 0 : this.pageIndex.hashCode());
        result = 31 * result + (this.pageSize == null ? 0 : this.pageSize.hashCode());
        result = 31 * result + (this.condition == null ? 0 : this.condition.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("pageIndex", pageIndex)
            .add("pageSize", pageSize)
            .add("condition", condition)
            .toString();
    }
}
