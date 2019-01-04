package com.ctrip.train.kefu.system.offline.notice.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;
@Entity
public class ComplainReport implements DalPojo {
    @Column(name="enterDate")
    @Type(value= Types.VARCHAR)
    private String enterDate;

    @Column(name="complainType")
    @Type(value=Types.VARCHAR)
    private String complainType;

    @Column(name="percentage")
    @Type(value=Types.VARCHAR)
    private String percentage;

    @Column(name="numByType")
    @Type(value=Types.INTEGER)
    private int numByType;

    @Column(name="allnum")
    @Type(value=Types.INTEGER)
    private int allnum;


    @Column(name="productLine")
    @Type(value=Types.VARCHAR)
    private String productLine;

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getComplainType() {
        return complainType;
    }

    public void setComplainType(String complainType) {
        this.complainType = complainType;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public int getNumByType() {
        return numByType;
    }

    public void setNumByType(int numByType) {
        this.numByType = numByType;
    }

    public int getAllnum() {
        return allnum;
    }

    public void setAllnum(int allnum) {
        this.allnum = allnum;
    }
}
