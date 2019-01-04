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
    "operTag",
    "media",
    "dTMF",
    "orderNo",
    "transferInfo"
})
public class BroadcastMedia implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"BroadcastMedia\",\"namespace\":\"" + BroadcastMedia.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"operTag\",\"type\":[\"string\",\"null\"]},{\"name\":\"media\",\"type\":[{\"type\":\"record\",\"name\":\"Media\",\"namespace\":\"" + Media.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"mediaType\",\"type\":[\"string\",\"null\"]},{\"name\":\"text\",\"type\":[\"string\",\"null\"]}]},\"null\"]},{\"name\":\"dTMF\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderNo\",\"type\":[\"int\",\"null\"]},{\"name\":\"transferInfo\",\"type\":[{\"type\":\"record\",\"name\":\"TransferInfo\",\"namespace\":\"" + TransferInfo.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"waitTimeSec\",\"type\":[\"int\",\"null\"]},{\"name\":\"outSourceGroupVdn\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferType\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferText\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferStatus\",\"type\":[\"int\",\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public BroadcastMedia(
        String operTag,
        Media media,
        String dTMF,
        Integer orderNo,
        TransferInfo transferInfo) {
        this.operTag = operTag;
        this.media = media;
        this.dTMF = dTMF;
        this.orderNo = orderNo;
        this.transferInfo = transferInfo;
    }

    public BroadcastMedia() {
    }

    @JsonProperty("operTag") 
    public String operTag;

    @JsonProperty("media") 
    public Media media;

    @JsonProperty("dTMF") 
    public String dTMF;

    @JsonProperty("orderNo") 
    public Integer orderNo;

    @JsonProperty("transferInfo") 
    public TransferInfo transferInfo;

    public String getOperTag() {
        return operTag;
    }

    public void setOperTag(final String operTag) {
        this.operTag = operTag;
    }
    public Media getMedia() {
        return media;
    }

    public void setMedia(final Media media) {
        this.media = media;
    }
    public String getDTMF() {
        return dTMF;
    }

    public void setDTMF(final String dTMF) {
        this.dTMF = dTMF;
    }
    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(final Integer orderNo) {
        this.orderNo = orderNo;
    }
    public TransferInfo getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(final TransferInfo transferInfo) {
        this.transferInfo = transferInfo;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.operTag;
            case 1: return this.media;
            case 2: return this.dTMF;
            case 3: return this.orderNo;
            case 4: return this.transferInfo;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.operTag = (String)fieldValue; break;
            case 1: this.media = (Media)fieldValue; break;
            case 2: this.dTMF = (String)fieldValue; break;
            case 3: this.orderNo = (Integer)fieldValue; break;
            case 4: this.transferInfo = (TransferInfo)fieldValue; break;
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

        final BroadcastMedia other = (BroadcastMedia)obj;
        return 
            Objects.equal(this.operTag, other.operTag) &&
            Objects.equal(this.media, other.media) &&
            Objects.equal(this.dTMF, other.dTMF) &&
            Objects.equal(this.orderNo, other.orderNo) &&
            Objects.equal(this.transferInfo, other.transferInfo);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.operTag == null ? 0 : this.operTag.hashCode());
        result = 31 * result + (this.media == null ? 0 : this.media.hashCode());
        result = 31 * result + (this.dTMF == null ? 0 : this.dTMF.hashCode());
        result = 31 * result + (this.orderNo == null ? 0 : this.orderNo.hashCode());
        result = 31 * result + (this.transferInfo == null ? 0 : this.transferInfo.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("operTag", operTag)
            .add("media", media)
            .add("dTMF", dTMF)
            .add("orderNo", orderNo)
            .add("transferInfo", transferInfo)
            .toString();
    }
}
