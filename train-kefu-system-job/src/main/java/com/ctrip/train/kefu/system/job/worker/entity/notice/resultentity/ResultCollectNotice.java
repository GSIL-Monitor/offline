package com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class ResultCollectNotice {


    @Column(name="opUserName")
    @Type(value= Types.VARCHAR)
    private String opuserName;

    @Column(name="opUser")
    @Type(value= Types.VARCHAR)
    private String opUser;

    @Column(name="opUserNum")
    @Type(value= Types.VARCHAR)
    private String opUserNum;

    @Column(name="waitNoticeCount")
    @Type(value= Types.BIGINT)
    private long waitNoticeCount;

    @Column(name="deferNoticeCount")
    @Type(value=Types.BIGINT)
    private long deferNoticeCount;

    @Column(name="solvedNoticeCount")
    @Type(value=Types.BIGINT)
    private long solvedNoticeCount;

    @Column(name="solveingNoticeCount")
    @Type(value=Types.BIGINT)
    private long solveingNoticeCount;

    @Column(name="solveAbility")
    @Type(value=Types.BIGINT)
    private long solveAbility;

    @Column(name="envenType")
    @Type(value=Types.INTEGER)
    private int envenType;

    public int getEnvenType() {
        return envenType;
    }

    public void setEnvenType(int envenType) {
        this.envenType = envenType;
    }

    public String getOpUserNum() {
        return opUserNum;
    }

    public void setOpUserNum(String opUserNum) {
        this.opUserNum = opUserNum;
    }

    public String getOpuserName() {
        return opuserName;
    }

    public void setOpuserName(String opuserName) {
        this.opuserName = opuserName;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public long getWaitNoticeCount() {
        return waitNoticeCount;
    }

    public void setWaitNoticeCount(long waitNoticeCount) {
        this.waitNoticeCount = waitNoticeCount;
    }

    public long getDeferNoticeCount() {
        return deferNoticeCount;
    }

    public void setDeferNoticeCount(long deferNoticeCount) {
        this.deferNoticeCount = deferNoticeCount;
    }

    public long getSolvedNoticeCount() {
        return solvedNoticeCount;
    }

    public void setSolvedNoticeCount(long solvedNoticeCount) {
        this.solvedNoticeCount = solvedNoticeCount;
    }

    public long getSolveAbility() {
        return solveAbility;
    }

    public void setSolveAbility(long solveAbility) {
        this.solveAbility = solveAbility;
    }

    public long getSolveingNoticeCount() {
        return solveingNoticeCount;
    }

    public void setSolveingNoticeCount(long solveingNoticeCount) {
        this.solveingNoticeCount = solveingNoticeCount;
    }
}
