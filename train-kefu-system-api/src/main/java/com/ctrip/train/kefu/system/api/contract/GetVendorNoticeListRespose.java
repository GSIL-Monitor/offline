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
import java.util.List;

@SuppressWarnings("all")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE) 
@JsonPropertyOrder({
    "pageIndex",
    "pageSize",
    "count",
    "vendorNotice",
    "responseStatus"
})
public class GetVendorNoticeListRespose implements SpecificRecord, HasResponseStatus {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetVendorNoticeListRespose\",\"namespace\":\"" + GetVendorNoticeListRespose.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"pageIndex\",\"type\":[\"int\",\"null\"]},{\"name\":\"pageSize\",\"type\":[\"int\",\"null\"]},{\"name\":\"count\",\"type\":[\"int\",\"null\"]},{\"name\":\"vendorNotice\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"VendorNotice\",\"namespace\":\"" + VendorNotice.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"emergeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeSecondType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeSecondTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"opCount\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUseTyper\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"opTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorName\",\"type\":[\"string\",\"null\"]},{\"name\":\"contents\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendType\",\"type\":[\"int\",\"null\"]},{\"name\":\"sendCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendName\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"appointedProcessTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"completeTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateInfos\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Operate\",\"namespace\":\"" + Operate.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateComment\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateType\",\"type\":[\"int\",\"null\"]}]}},\"null\"]}]}},\"null\"]},{\"name\":\"ResponseStatus\",\"type\":[{\"type\":\"record\",\"name\":\"ResponseStatusType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Timestamp\",\"type\":[\"datetime\",\"null\"]},{\"name\":\"Ack\",\"type\":[{\"type\":\"enum\",\"name\":\"AckCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Success\",\"Failure\",\"Warning\",\"PartialFailure\"]},\"null\"]},{\"name\":\"Errors\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorDataType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Message\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"StackTrace\",\"type\":[\"string\",\"null\"]},{\"name\":\"SeverityCode\",\"type\":[{\"type\":\"enum\",\"name\":\"SeverityCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Error\",\"Warning\"]},\"null\"]},{\"name\":\"ErrorFields\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorFieldType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"FieldName\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"Message\",\"type\":[\"string\",\"null\"]}]}},\"null\"]},{\"name\":\"ErrorClassification\",\"type\":[{\"type\":\"enum\",\"name\":\"ErrorClassificationCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"ServiceError\",\"ValidationError\",\"FrameworkError\",\"SLAError\",\"SecurityError\"]},\"null\"]}]}},\"null\"]},{\"name\":\"Build\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"Extension\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ExtensionType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Id\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"ContentType\",\"type\":[\"string\",\"null\"]},{\"name\":\"Value\",\"type\":[\"string\",\"null\"]}]}},\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetVendorNoticeListRespose(
        Integer pageIndex,
        Integer pageSize,
        Integer count,
        List<VendorNotice> vendorNotice,
        ResponseStatusType responseStatus) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.count = count;
        this.vendorNotice = vendorNotice;
        this.responseStatus = responseStatus;
    }

    public GetVendorNoticeListRespose() {
    }

    @JsonProperty("pageIndex") 
    public Integer pageIndex;

    @JsonProperty("pageSize") 
    public Integer pageSize;

    @JsonProperty("count") 
    public Integer count;

    @JsonProperty("vendorNotice") 
    public List<VendorNotice> vendorNotice;

    @JsonProperty("ResponseStatus") 
    public ResponseStatusType responseStatus;

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
    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }
    public List<VendorNotice> getVendorNotice() {
        return vendorNotice;
    }

    public void setVendorNotice(final List<VendorNotice> vendorNotice) {
        this.vendorNotice = vendorNotice;
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
            case 0: return this.pageIndex;
            case 1: return this.pageSize;
            case 2: return this.count;
            case 3: return this.vendorNotice;
            case 4: return this.responseStatus;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.pageIndex = (Integer)fieldValue; break;
            case 1: this.pageSize = (Integer)fieldValue; break;
            case 2: this.count = (Integer)fieldValue; break;
            case 3: this.vendorNotice = (List<VendorNotice>)fieldValue; break;
            case 4: this.responseStatus = (ResponseStatusType)fieldValue; break;
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

        final GetVendorNoticeListRespose other = (GetVendorNoticeListRespose)obj;
        return 
            Objects.equal(this.pageIndex, other.pageIndex) &&
            Objects.equal(this.pageSize, other.pageSize) &&
            Objects.equal(this.count, other.count) &&
            Objects.equal(this.vendorNotice, other.vendorNotice) &&
            Objects.equal(this.responseStatus, other.responseStatus);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.pageIndex == null ? 0 : this.pageIndex.hashCode());
        result = 31 * result + (this.pageSize == null ? 0 : this.pageSize.hashCode());
        result = 31 * result + (this.count == null ? 0 : this.count.hashCode());
        result = 31 * result + (this.vendorNotice == null ? 0 : this.vendorNotice.hashCode());
        result = 31 * result + (this.responseStatus == null ? 0 : this.responseStatus.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("pageIndex", pageIndex)
            .add("pageSize", pageSize)
            .add("count", count)
            .add("vendorNotice", vendorNotice)
            .add("responseStatus", responseStatus)
            .toString();
    }
}
