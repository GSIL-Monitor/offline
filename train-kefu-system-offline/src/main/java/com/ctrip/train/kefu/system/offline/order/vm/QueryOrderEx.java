package com.ctrip.train.kefu.system.offline.order.vm;

import com.ctrip.soa.train.ticketing.clickservice.v1.QueryOrderDetailRefundInfoDto;

import java.util.List;

/**
 * 12306订单详情
 */
public class QueryOrderEx {
    private List<QueryOrderDetailEx> queryOrderList;  //票信息

    private List<QueryOrderDetailRefundInfoDto> refundInfos;   //退款信息

    private int retCode;  //0 查询中 -1 查询失败  1 查询成功

    public List<QueryOrderDetailEx> getQueryOrderList() {
        return queryOrderList;
    }

    public void setQueryOrderList(List<QueryOrderDetailEx> queryOrderList) {
        this.queryOrderList = queryOrderList;
    }

    public List<QueryOrderDetailRefundInfoDto> getRefundInfos() {
        return refundInfos;
    }

    public void setRefundInfos(List<QueryOrderDetailRefundInfoDto> refundInfos) {
        this.refundInfos = refundInfos;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

}
