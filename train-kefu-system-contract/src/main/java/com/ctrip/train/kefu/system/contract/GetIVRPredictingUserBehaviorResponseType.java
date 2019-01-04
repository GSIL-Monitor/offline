package com.ctrip.train.kefu.system.contract;

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
    "broadcastMediaList",
    "operType",
    "resultCode",
    "resultMessage",
    "responseStatus"
})
public class GetIVRPredictingUserBehaviorResponseType implements SpecificRecord, HasResponseStatus {
    private static final long serialVersionUID = 1L;

	@JsonIgnore
    public static final transient Schema SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"GetIVRPredictingUserBehaviorResponseType\",\"namespace\":\"" + GetIVRPredictingUserBehaviorResponseType.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"broadcastMediaList\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"BroadcastMedia\",\"namespace\":\"" + BroadcastMedia.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"operTag\",\"type\":[\"string\",\"null\"]},{\"name\":\"media\",\"type\":[{\"type\":\"record\",\"name\":\"Media\",\"namespace\":\"" + Media.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"mediaType\",\"type\":[\"string\",\"null\"]},{\"name\":\"text\",\"type\":[\"string\",\"null\"]}]},\"null\"]},{\"name\":\"dTMF\",\"type\":[\"string\",\"null\"]},{\"name\":\"orderNo\",\"type\":[\"int\",\"null\"]},{\"name\":\"transferInfo\",\"type\":[{\"type\":\"record\",\"name\":\"TransferInfo\",\"namespace\":\"" + TransferInfo.class.getPackage().getName() + "\",\"doc\":null,\"fields\":[{\"name\":\"waitTimeSec\",\"type\":[\"int\",\"null\"]},{\"name\":\"outSourceGroupVdn\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferType\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferText\",\"type\":[\"string\",\"null\"]},{\"name\":\"transferStatus\",\"type\":[\"int\",\"null\"]}]},\"null\"]}]}},\"null\"]},{\"name\":\"operType\",\"type\":[\"string\",\"null\"]},{\"name\":\"resultCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"resultMessage\",\"type\":[\"string\",\"null\"]},{\"name\":\"ResponseStatus\",\"type\":[{\"type\":\"record\",\"name\":\"ResponseStatusType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Timestamp\",\"type\":[\"datetime\",\"null\"]},{\"name\":\"Ack\",\"type\":[{\"type\":\"enum\",\"name\":\"AckCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Success\",\"Failure\",\"Warning\",\"PartialFailure\"]},\"null\"]},{\"name\":\"Errors\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorDataType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Message\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"StackTrace\",\"type\":[\"string\",\"null\"]},{\"name\":\"SeverityCode\",\"type\":[{\"type\":\"enum\",\"name\":\"SeverityCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"Error\",\"Warning\"]},\"null\"]},{\"name\":\"ErrorFields\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ErrorFieldType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"FieldName\",\"type\":[\"string\",\"null\"]},{\"name\":\"ErrorCode\",\"type\":[\"string\",\"null\"]},{\"name\":\"Message\",\"type\":[\"string\",\"null\"]}]}},\"null\"]},{\"name\":\"ErrorClassification\",\"type\":[{\"type\":\"enum\",\"name\":\"ErrorClassificationCodeType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"symbols\":[\"ServiceError\",\"ValidationError\",\"FrameworkError\",\"SLAError\",\"SecurityError\"]},\"null\"]}]}},\"null\"]},{\"name\":\"Build\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"Extension\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ExtensionType\",\"namespace\":\"com.ctriposs.baiji.rpc.common.types\",\"doc\":null,\"fields\":[{\"name\":\"Id\",\"type\":[\"string\",\"null\"]},{\"name\":\"Version\",\"type\":[\"string\",\"null\"]},{\"name\":\"ContentType\",\"type\":[\"string\",\"null\"]},{\"name\":\"Value\",\"type\":[\"string\",\"null\"]}]}},\"null\"]}]},\"null\"]}]}");

    @Override
    @JsonIgnore
    public Schema getSchema() { return SCHEMA; }

    public GetIVRPredictingUserBehaviorResponseType(
        List<BroadcastMedia> broadcastMediaList,
        String operType,
        String resultCode,
        String resultMessage,
        ResponseStatusType responseStatus) {
        this.broadcastMediaList = broadcastMediaList;
        this.operType = operType;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.responseStatus = responseStatus;
    }

    public GetIVRPredictingUserBehaviorResponseType() {
    }

    @JsonProperty("broadcastMediaList") 
    public List<BroadcastMedia> broadcastMediaList;

    @JsonProperty("operType") 
    public String operType;

    @JsonProperty("resultCode") 
    public String resultCode;

    @JsonProperty("resultMessage") 
    public String resultMessage;

    @JsonProperty("ResponseStatus") 
    public ResponseStatusType responseStatus;

    public List<BroadcastMedia> getBroadcastMediaList() {
        return broadcastMediaList;
    }

    public void setBroadcastMediaList(final List<BroadcastMedia> broadcastMediaList) {
        this.broadcastMediaList = broadcastMediaList;
    }
    public String getOperType() {
        return operType;
    }

    public void setOperType(final String operType) {
        this.operType = operType;
    }
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(final String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(final String resultMessage) {
        this.resultMessage = resultMessage;
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
            case 0: return this.broadcastMediaList;
            case 1: return this.operType;
            case 2: return this.resultCode;
            case 3: return this.resultMessage;
            case 4: return this.responseStatus;
            default: throw new BaijiRuntimeException("Bad index " + fieldPos + " in get()");
        }
    }

    // Used by DatumReader. Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int fieldPos, Object fieldValue) {
        switch (fieldPos) {
            case 0: this.broadcastMediaList = (List<BroadcastMedia>)fieldValue; break;
            case 1: this.operType = (String)fieldValue; break;
            case 2: this.resultCode = (String)fieldValue; break;
            case 3: this.resultMessage = (String)fieldValue; break;
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

        final GetIVRPredictingUserBehaviorResponseType other = (GetIVRPredictingUserBehaviorResponseType)obj;
        return 
            Objects.equal(this.broadcastMediaList, other.broadcastMediaList) &&
            Objects.equal(this.operType, other.operType) &&
            Objects.equal(this.resultCode, other.resultCode) &&
            Objects.equal(this.resultMessage, other.resultMessage) &&
            Objects.equal(this.responseStatus, other.responseStatus);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (this.broadcastMediaList == null ? 0 : this.broadcastMediaList.hashCode());
        result = 31 * result + (this.operType == null ? 0 : this.operType.hashCode());
        result = 31 * result + (this.resultCode == null ? 0 : this.resultCode.hashCode());
        result = 31 * result + (this.resultMessage == null ? 0 : this.resultMessage.hashCode());
        result = 31 * result + (this.responseStatus == null ? 0 : this.responseStatus.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("broadcastMediaList", broadcastMediaList)
            .add("operType", operType)
            .add("resultCode", resultCode)
            .add("resultMessage", resultMessage)
            .add("responseStatus", responseStatus)
            .toString();
    }
}
