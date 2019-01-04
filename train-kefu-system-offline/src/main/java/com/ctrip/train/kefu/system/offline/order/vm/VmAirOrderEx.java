package com.ctrip.train.kefu.system.offline.order.vm;


import java.util.Date;

public class VmAirOrderEx {

    private String ID;

    /**
     * 订单号
     */

    private String orderId;

    /**
     * 供应商
     */

    private String supplier;

    /**
     * 下单时间
     */

    private String sendOrderTime;

    /**
     * 起飞时间
     */

    private String takeoffTime;

    /**
     * 最晚出票时间
     */

    private String latestTicketingTime;

    /**
     * 异常订单类型
     */

    private String exOrderType;

    /**
     * 备注
     */

    private String processingRemark;

    /**
     * 最后更新时间
     */
    private String datachangeLastTime;

    /**
     * 更新时间
     */
    private String datachangeLasttime;

    /**
     * 输入人
     */

    private String enterUser;

    /**
     * 产品线
     */
    private Integer productLine;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSendOrderTime() {
        return sendOrderTime;
    }

    public void setSendOrderTime(String sendOrderTime) {
        this.sendOrderTime = sendOrderTime;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(String takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public String getLatestTicketingTime() {
        return latestTicketingTime;
    }

    public void setLatestTicketingTime(String latestTicketingTime) {
        this.latestTicketingTime = latestTicketingTime;
    }

    public String getExOrderType() {
        return exOrderType;
    }

    public void setExOrderType(String exOrderType) {
        this.exOrderType = exOrderType;
    }

    public String getProcessingRemark() {
        return processingRemark;
    }

    public void setProcessingRemark(String processingRemark) {
        this.processingRemark = processingRemark;
    }

    public String getDatachangeLastTime() {
        return datachangeLastTime;
    }

    public void setDatachangeLastTime(String datachangeLastTime) {
        this.datachangeLastTime = datachangeLastTime;
    }

    public String getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(String datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }

    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterUser(String enterUser) {
        this.enterUser = enterUser;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }
}
