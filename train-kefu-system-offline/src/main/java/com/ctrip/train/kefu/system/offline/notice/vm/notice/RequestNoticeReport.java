package com.ctrip.train.kefu.system.offline.notice.vm.notice;

import java.util.Date;

public class RequestNoticeReport {

    /**
     * 产品线
     * */
    private Integer productLine;

    /**
     * 渠道
     * */
    private String dataSource;

    /**
     * 操作人
     * */
    private String opUser;

    /**
     * 通知类型
     * */
    private Integer evenType;

    /**
     * 开始时间
     * */
    private Date startTime;

    /**
     * 结束时间
     * */
    private Date endTime;

    private int pageIndex;
    private int pageSize;

    private String [] channels;

    public String[] getChannel() {
        return channels;
    }

    public void setChannel(String[] channels) {
        this.channels = channels;
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

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public Integer getEvenType() {
        return evenType;
    }

    public void setEvenType(Integer evenType) {
        this.evenType = evenType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
