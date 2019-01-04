package com.ctrip.train.kefu.system.job.worker.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;


@Entity
public class StaffPriorityResult implements DalPojo {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private Long id;

    /**
     * 员工编号
     */
    @Column(name = "staffNum")
    @Type(value = Types.VARCHAR)
    private String staffNum;

    /**
     * 通知产线
     */
    @Column(name = "noticeProductLine")
    @Type(value = Types.INTEGER)
    private Integer noticeProductLine;

    /**
     * 通知的一级分类
     */
    @Column(name = "noticeTypes")
    @Type(value = Types.VARCHAR)
    private String noticeTypes;

    /**
     * 1,通知 2,投诉 4,领班
     */
    @Column(name = "")
    @Type(value = Types.INTEGER)
    private Integer envenType;

    /**
     * 可用
     */
    @Column(name = "available")
    @Type(value = Types.TINYINT)
    private Integer available;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Type(value = Types.TIMESTAMP)
    private Timestamp createTime;

    @Column(name = "staffName")
    @Type(value = Types.VARCHAR)
    private String staffName;

    @Column(name = "complainWaitLimit")
    @Type(value = Types.INTEGER)
    private int complainWaitLimit;

    @Column(name = "noticeWaitLimit")
    @Type(value = Types.INTEGER)
    private int noticeWaitLimit;

    public int getComplainWaitLimit() {
        return complainWaitLimit;
    }

    public void setComplainWaitLimit(int complainWaitLimit) {
        this.complainWaitLimit = complainWaitLimit;
    }

    public int getNoticeWaitLimit() {
        return noticeWaitLimit;
    }

    public void setNoticeWaitLimit(int noticeWaitLimit) {
        this.noticeWaitLimit = noticeWaitLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public Integer getNoticeProductLine() {
        return noticeProductLine;
    }

    public void setNoticeProductLine(Integer noticeProductLine) {
        this.noticeProductLine = noticeProductLine;
    }

    public String getNoticeTypes() {
        return noticeTypes;
    }

    public void setNoticeTypes(String noticeTypes) {
        this.noticeTypes = noticeTypes;
    }

    public Integer getEnvenType() {
        return envenType;
    }

    public void setEnvenType(Integer envenType) {
        this.envenType = envenType;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
