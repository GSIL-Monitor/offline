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
    "vendorNotice"
})
public class SendVendorNoticeRequest implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"SendVendorNoticeRequest\",\"namespace\":\"" + SendVendorNoticeRequest.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"vendorNotice\",\"type\":[{\"type\":\"record\",\"name\":\"VendorNotice\",\"namespace\":\"" + VendorNotice.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"emergeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"orderId\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeSecondType\",\"type\":[\"int\",\"null\"]},{\"name\":\"noticeSecondTypeName\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeState\",\"type\":[\"int\",\"null\"]},{\"name\":\"opCount\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUseTyper\",\"type\":[\"int\",\"null\"]},{\"name\":\"opUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"opTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"vendorName\",\"type\":[\"string\",\"null\"]},{\"name\":\"contents\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendType\",\"type\":[\"int\",\"null\"]},{\"name\":\"sendCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendName\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"appointedProcessTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"completeTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateInfos\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Operate\",\"namespace\":\"" + Operate.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"id\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"noticeId\",\"type\":[\"long\",\"null\"]},{\"name\":\"operateUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateComment\",\"type\":[\"string\",\"null\"]},{\"name\":\"operateType\",\"type\":[\"int\",\"null\"]}]}},\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public SendVendorNoticeRequest(
        VendorNotice vendorNotice) {
        this.vendorNotice = vendorNotice;
    }

    public SendVendorNoticeRequest() {
    }

    @JsonProperty("vendorNotice") 
    public VendorNotice vendorNotice;

    public VendorNotice getVendorNotice() {
        return vendorNotice;
    }

    public void setVendorNotice(final VendorNotice vendorNotice) {
        this.vendorNotice = vendorNotice;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.vendorNotice;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.vendorNotice = (VendorNotice)fieldValue; break;
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

        final SendVendorNoticeRequest other = (SendVendorNoticeRequest)obj;
        return 
            Objects.equal(this.vendorNotice, other.vendorNotice);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.vendorNotice == null ? 0 : this.vendorNotice.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("vendorNotice", vendorNotice)
            .toString();
    }
}
