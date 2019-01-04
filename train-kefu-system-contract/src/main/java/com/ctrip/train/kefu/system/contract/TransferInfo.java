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
    "waitTimeSec",
    "outSourceGroupVdn",
    "transferType",
    "transferText",
    "transferStatus"
})
public class TransferInfo implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"TransferInfo\",\"namespace\":\"" + TransferInfo.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"waitTimeSec\",\"type\":[\"int\",\"null\"]},{\"name\":\"outSourceGroupVdn\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferType\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferText\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferStatus\",\"type\":[\"int\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public TransferInfo(
        Integer waitTimeSec,
        String outSourceGroupVdn,
        String transferType,
        String transferText,
        Integer transferStatus) {
        this.waitTimeSec = waitTimeSec;
        this.outSourceGroupVdn = outSourceGroupVdn;
        this.transferType = transferType;
        this.transferText = transferText;
        this.transferStatus = transferStatus;
    }

    public TransferInfo() {
    }

    @JsonProperty("waitTimeSec") 
    public Integer waitTimeSec;

    @JsonProperty("outSourceGroupVdn") 
    public String outSourceGroupVdn;

    @JsonProperty("transferType") 
    public String transferType;

    @JsonProperty("transferText") 
    public String transferText;

    @JsonProperty("transferStatus") 
    public Integer transferStatus;

    public Integer getWaitTimeSec() {
        return waitTimeSec;
    }

    public void setWaitTimeSec(final Integer waitTimeSec) {
        this.waitTimeSec = waitTimeSec;
    }
    public String getOutSourceGroupVdn() {
        return outSourceGroupVdn;
    }

    public void setOutSourceGroupVdn(final String outSourceGroupVdn) {
        this.outSourceGroupVdn = outSourceGroupVdn;
    }
    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(final String transferType) {
        this.transferType = transferType;
    }
    public String getTransferText() {
        return transferText;
    }

    public void setTransferText(final String transferText) {
        this.transferText = transferText;
    }
    public Integer getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(final Integer transferStatus) {
        this.transferStatus = transferStatus;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.waitTimeSec;
            case 1: return this.outSourceGroupVdn;
            case 2: return this.transferType;
            case 3: return this.transferText;
            case 4: return this.transferStatus;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.waitTimeSec = (Integer)fieldValue; break;
            case 1: this.outSourceGroupVdn = (String)fieldValue; break;
            case 2: this.transferType = (String)fieldValue; break;
            case 3: this.transferText = (String)fieldValue; break;
            case 4: this.transferStatus = (Integer)fieldValue; break;
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

        final TransferInfo other = (TransferInfo)obj;
        return 
            Objects.equal(this.waitTimeSec, other.waitTimeSec) &&
            Objects.equal(this.outSourceGroupVdn, other.outSourceGroupVdn) &&
            Objects.equal(this.transferType, other.transferType) &&
            Objects.equal(this.transferText, other.transferText) &&
            Objects.equal(this.transferStatus, other.transferStatus);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.waitTimeSec == null ? 0 : this.waitTimeSec.hashCode());
        result = 31 * result + (this.outSourceGroupVdn == null ? 0 : this.outSourceGroupVdn.hashCode());
        result = 31 * result + (this.transferType == null ? 0 : this.transferType.hashCode());
        result = 31 * result + (this.transferText == null ? 0 : this.transferText.hashCode());
        result = 31 * result + (this.transferStatus == null ? 0 : this.transferStatus.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("waitTimeSec", waitTimeSec)
            .add("outSourceGroupVdn", outSourceGroupVdn)
            .add("transferType", transferType)
            .add("transferText", transferText)
            .add("transferStatus", transferStatus)
            .toString();
    }
}
