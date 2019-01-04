package com.ctrip.train.kefu.system.offline.notice.domain;

import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class OperateNoticeTime {

    @Column(name="id")
    @Type(value= Types.INTEGER)
    private Long id;

    @Column(name="opUser")
    @Type(value=Types.VARCHAR)
    private String opUser;

    @Column(name="sendTime")
    @Type(value=Types.VARCHAR)
    private String sendTime;

    @Column(name="operateTime")
    @Type(value=Types.VARCHAR)
    private String operateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
