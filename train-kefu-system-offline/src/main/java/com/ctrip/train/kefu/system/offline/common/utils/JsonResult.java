package com.ctrip.train.kefu.system.offline.common.utils;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A domain return for the ajax notice
 * format is :
 * {"data":{},"message":"Success","status":0}
 * @author ying_zhou 2017/10/26 16:03
 */
public final class JsonResult implements Serializable {
    /**
     * 0 for success
     * 1 for error
     * and it can be customized ,in this case it should
     * be defined in a constant class or enumeration
     */
    private int status;
    /**
     * message that illustrates the status code
     */
    private String message;
    /**
     * data the response body take
     */
    private Map<String,Object> data;

    private JsonResult(int status, String message) {
        this.status = status;
        this.message = message;
        data=new HashMap<>();
    }

    public static JsonResult ok(){
        return  new JsonResult(0,"Success");
    }
    public static JsonResult ok(String message){
        return  new JsonResult(0,message);
    }
    public static JsonResult fail(){
        return new JsonResult(1,"Fail");
    }
    public static JsonResult fail(String message){
        return new JsonResult(1,message);
    }
    public static JsonResult fail(int status,String message){
        return new JsonResult(status,message);
    }

    /**
     * add data to the body
     * @param key
     * @param value
     * @return
     */
    public JsonResult putData(String key,Object value){
        data.put(key,value);
        return  this;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public JsonResult setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
