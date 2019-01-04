package com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
public class ResultApportionNotice {

    @Column(name="id")
    @Type(value= Types.INTEGER)
    private Long id;

    @Column(name="noticeType")
    @Type(value=Types.INTEGER)
    private int noticeType;

    @Column(name="productLine")
    @Type(value=Types.VARCHAR)
    private String productLine;

    @Column(name="operateTime")
    @Type(value=Types.DATE)
    private Timestamp operateTime;

    @Column(name="envenType")
    @Type(value=Types.INTEGER)
    private int envenType;

    @Column(name="orderId")
    @Type(value=Types.VARCHAR)
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getEnvenType() {
        return envenType;
    }

    public void setEnvenType(int envenType) {
        this.envenType = envenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }
}
