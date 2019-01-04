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
    "supplier",
    "sendOrderTime",
    "takeoffTime",
    "latestTicketingTime",
    "exOrderType",
    "processingRemark",
    "enterUser",
    "productLine"
})
public class FlightExOrderRequsetType implements SpecificRecord {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"FlightExOrderRequsetType\",\"namespace\":\"" + FlightExOrderRequsetType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"orderID\",\"type\":[\"string\",\"null\"]},{\"name\":\"supplier\",\"type\":[\"string\",\"null\"]},{\"name\":\"sendOrderTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"takeoffTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"latestTicketingTime\",\"type\":[\"string\",\"null\"]},{\"name\":\"exOrderType\",\"type\":[\"int\",\"null\"]},{\"name\":\"processingRemark\",\"type\":[\"string\",\"null\"]},{\"name\":\"enterUser\",\"type\":[\"string\",\"null\"]},{\"name\":\"productLine\",\"type\":[\"int\",\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public FlightExOrderRequsetType(
        String orderID,
        String supplier,
        String sendOrderTime,
        String takeoffTime,
        String latestTicketingTime,
        Integer exOrderType,
        String processingRemark,
        String enterUser,
        Integer productLine) {
        this.orderID = orderID;
        this.supplier = supplier;
        this.sendOrderTime = sendOrderTime;
        this.takeoffTime = takeoffTime;
        this.latestTicketingTime = latestTicketingTime;
        this.exOrderType = exOrderType;
        this.processingRemark = processingRemark;
        this.enterUser = enterUser;
        this.productLine = productLine;
    }

    public FlightExOrderRequsetType() {
    }

    @JsonProperty("orderID") 
    public String orderID;

    @JsonProperty("supplier") 
    public String supplier;

    @JsonProperty("sendOrderTime") 
    public String sendOrderTime;

    @JsonProperty("takeoffTime") 
    public String takeoffTime;

    @JsonProperty("latestTicketingTime") 
    public String latestTicketingTime;

    @JsonProperty("exOrderType") 
    public Integer exOrderType;

    @JsonProperty("processingRemark") 
    public String processingRemark;

    @JsonProperty("enterUser") 
    public String enterUser;

    @JsonProperty("productLine") 
    public Integer productLine;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(final String orderID) {
        this.orderID = orderID;
    }
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(final String supplier) {
        this.supplier = supplier;
    }
    public String getSendOrderTime() {
        return sendOrderTime;
    }

    public void setSendOrderTime(final String sendOrderTime) {
        this.sendOrderTime = sendOrderTime;
    }
    public String getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(final String takeoffTime) {
        this.takeoffTime = takeoffTime;
    }
    public String getLatestTicketingTime() {
        return latestTicketingTime;
    }

    public void setLatestTicketingTime(final String latestTicketingTime) {
        this.latestTicketingTime = latestTicketingTime;
    }
    public Integer getExOrderType() {
        return exOrderType;
    }

    public void setExOrderType(final Integer exOrderType) {
        this.exOrderType = exOrderType;
    }
    public String getProcessingRemark() {
        return processingRemark;
    }

    public void setProcessingRemark(final String processingRemark) {
        this.processingRemark = processingRemark;
    }
    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(final String enterUser) {
        this.enterUser = enterUser;
    }
    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(final Integer productLine) {
        this.productLine = productLine;
    }

    // Used by DatumWriter. Applications should not call.
    public Object get(int fieldPos) {
        switch (fieldPos) {
            case 0: return this.orderID;
            case 1: return this.supplier;
            case 2: return this.sendOrderTime;
            case 3: return this.takeoffTime;
            case 4: return this.latestTicketingTime;
            case 5: return this.exOrderType;
            case 6: return this.processingRemark;
            case 7: return this.enterUser;
            case 8: return this.productLine;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.orderID = (String)fieldValue; break;
            case 1: this.supplier = (String)fieldValue; break;
            case 2: this.sendOrderTime = (String)fieldValue; break;
            case 3: this.takeoffTime = (String)fieldValue; break;
            case 4: this.latestTicketingTime = (String)fieldValue; break;
            case 5: this.exOrderType = (Integer)fieldValue; break;
            case 6: this.processingRemark = (String)fieldValue; break;
            case 7: this.enterUser = (String)fieldValue; break;
            case 8: this.productLine = (Integer)fieldValue; break;
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

        final FlightExOrderRequsetType other = (FlightExOrderRequsetType)obj;
        return 
            Objects.equal(this.orderID, other.orderID) &&
            Objects.equal(this.supplier, other.supplier) &&
            Objects.equal(this.sendOrderTime, other.sendOrderTime) &&
            Objects.equal(this.takeoffTime, other.takeoffTime) &&
            Objects.equal(this.latestTicketingTime, other.latestTicketingTime) &&
            Objects.equal(this.exOrderType, other.exOrderType) &&
            Objects.equal(this.processingRemark, other.processingRemark) &&
            Objects.equal(this.enterUser, other.enterUser) &&
            Objects.equal(this.productLine, other.productLine);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.orderID == null ? 0 : this.orderID.hashCode());
        result = 31 * result + (this.supplier == null ? 0 : this.supplier.hashCode());
        result = 31 * result + (this.sendOrderTime == null ? 0 : this.sendOrderTime.hashCode());
        result = 31 * result + (this.takeoffTime == null ? 0 : this.takeoffTime.hashCode());
        result = 31 * result + (this.latestTicketingTime == null ? 0 : this.latestTicketingTime.hashCode());
        result = 31 * result + (this.exOrderType == null ? 0 : this.exOrderType.hashCode());
        result = 31 * result + (this.processingRemark == null ? 0 : this.processingRemark.hashCode());
        result = 31 * result + (this.enterUser == null ? 0 : this.enterUser.hashCode());
        result = 31 * result + (this.productLine == null ? 0 : this.productLine.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("orderID", orderID)
            .add("supplier", supplier)
            .add("sendOrderTime", sendOrderTime)
            .add("takeoffTime", takeoffTime)
            .add("latestTicketingTime", latestTicketingTime)
            .add("exOrderType", exOrderType)
            .add("processingRemark", processingRemark)
            .add("enterUser", enterUser)
            .add("productLine", productLine)
            .toString();
    }
}
