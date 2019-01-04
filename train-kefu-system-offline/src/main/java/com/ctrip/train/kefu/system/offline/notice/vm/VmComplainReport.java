package com.ctrip.train.kefu.system.offline.notice.vm;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class VmComplainReport implements DalPojo {

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
