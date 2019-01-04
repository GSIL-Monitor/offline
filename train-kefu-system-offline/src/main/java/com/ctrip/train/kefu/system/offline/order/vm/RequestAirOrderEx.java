package com.ctrip.train.kefu.system.offline.order.vm;

public class RequestAirOrderEx {
    private int pageIndex;
    private int pageSize;
    private String startDate;
    private String endDate;
    private String supplier;
    private String eXOrderType;
    private Integer productLine;
    private String orderId;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String geteXOrderType() {
        return eXOrderType;
    }

    public void seteXOrderType(String eXOrderType) {
        this.eXOrderType = eXOrderType;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
