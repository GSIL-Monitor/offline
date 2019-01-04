package com.ctrip.train.kefu.system.offline.common.utils;

public class PageInfo {
    private int recordsTotal;

    private Object data;

    private Object recordsFiltered;

    public Object getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Object recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public PageInfo() {
    }

    public PageInfo(int total, Object data) {
        this.recordsTotal = total;
        this.recordsFiltered = total;
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
