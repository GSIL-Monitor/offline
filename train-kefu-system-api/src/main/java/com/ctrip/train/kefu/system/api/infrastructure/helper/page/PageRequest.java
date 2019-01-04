package com.ctrip.train.kefu.system.api.infrastructure.helper.page;

public class PageRequest<T> {

    /**
     * start from 1,this should be rectified in data layer
     */
    private int pageIndex;
    private int pageSize;

    private T condition;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        if (pageSize==0){
            return 20;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }
}
