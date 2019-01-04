package com.ctrip.train.kefu.system.job.worker.entity.order.train;


import java.util.List;

public class DMCancelCoupons {

    /**
     * 订单编号
     */
    private  String orderid;

    /**
     * 用户编号
     */
    private  String uid;

    /**
     * 套餐编号
     */
    private List<String> couponIds;

    /**
     * parterName
     */
    private  String orderForm;


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<String> couponIds) {
        this.couponIds = couponIds;
    }

    public String getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(String orderForm) {
        this.orderForm = orderForm;
    }
}
