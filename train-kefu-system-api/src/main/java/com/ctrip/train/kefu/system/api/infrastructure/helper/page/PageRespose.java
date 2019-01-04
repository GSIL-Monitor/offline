package com.ctrip.train.kefu.system.api.infrastructure.helper.page;


import java.util.List;

public class PageRespose<T> {

    private static final int DEFAULT_PAGE_SIZE=20;
    /**
     * start from 1,this should be rectified in data layer
     */
    private int pageIndex;
    private int pageSize;
    private int totalRecord=DEFAULT_PAGE_SIZE;

    private List<T> list;

    public int getTotalPage(){
        if(totalRecord==0||pageSize==0){
            return 1;
        }
        return (int) Math.ceil((double) totalRecord/pageSize);
    }
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

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
