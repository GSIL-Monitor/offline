package com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;
import java.util.Date;

@Entity
public class ResultOnceNotice {

    @Column(name="opTime")
    @Type(value= Types.VARCHAR)
    private String opTime;

    @Column(name="opUserName")
    @Type(value= Types.VARCHAR)
    private String opuserName;

    @Column(name="opUserNum")
    @Type(value= Types.VARCHAR)
    private String opUserNum;

    @Column(name="oncesovle")
    @Type(value=Types.INTEGER)
    private int oncesovle;

    @Column(name="allsovle")
    @Type(value=Types.INTEGER)
    private int allsovle;

    @Column(name="percentage")
    @Type(value=Types.VARCHAR)
    private String percentage;

    @Column(name="envenType")
    @Type(value=Types.INTEGER)
    private int envenType;

    @Column(name="productLine")
    @Type(value=Types.VARCHAR)
    private String productLine;

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getOpuserName() {
        return opuserName;
    }

    public void setOpuserName(String opuserName) {
        this.opuserName = opuserName;
    }

    public String getOpUserNum() {
        return opUserNum;
    }

    public void setOpUserNum(String opUserNum) {
        this.opUserNum = opUserNum;
    }

    public int getOncesovle() {
        return oncesovle;
    }

    public void setOncesovle(int oncesovle) {
        this.oncesovle = oncesovle;
    }

    public int getAllsovle() {
        return allsovle;
    }

    public void setAllsovle(int allsovle) {
        this.allsovle = allsovle;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getEnvenType() {
        return envenType;
    }

    public void setEnvenType(int envenType) {
        this.envenType = envenType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }
}
