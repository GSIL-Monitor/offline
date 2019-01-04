package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Getter
@Setter
@Entity
public class StaffsResult implements DalPojo {
    /**
     * 空
     */
    @Id
    @Column(name="Tid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value=Types.INTEGER)
    private Integer tid;

    /**
     * 员工姓名
     */
    @Column(name="Staff_Name")
    @Type(value=Types.VARCHAR)
    private String staffName;

    /**
     * 工号
     */
    @Column(name="Staff_Number")
    @Type(value=Types.VARCHAR)
    private String staffNumber;

    /**
     * Email
     */
    @Column(name="Email")
    @Type(value=Types.VARCHAR)
    private String email;

    /**
     * -1:全部;1:火车票，2：汽车票，4：机场巴士
     */
    @Column(name="ProductLine")
    @Type(value=Types.VARCHAR)
    private String productLine;

    /**
     * 通知投诉系统员工状态 0:离线,1：在线
     */
    @Column(name="Staff_State")
    @Type(value=Types.CHAR)
    private String staffState;

    /**
     * 0:离线,1：在线
     */
    @Column(name="TR_Staff_State")
    @Type(value=Types.CHAR)
    private String trStaffState;

    /**
     * 空
     */
    @Column(name="DataChange_LastTime", insertable=false, updatable=false)
    @Type(value=Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    /**
     * 0:删除，1：未删除
     */
    @Column(name="Is_Delete")
    @Type(value=Types.INTEGER)
    private Integer isDelete;

    /**
     * 处理通知类型：按位与0不处理通知、2^0处理通知、2^1处理投诉、2^2处理领班
     */
    @Column(name="EventType")
    @Type(value=Types.INTEGER)
    private Integer eventType;

    /**
     * 处理的通知投诉类型 用,隔开
     */
    @Column(name="NoticeType")
    @Type(value= Types.VARCHAR)
    private String noticeType;

    /**
     * 通知投诉系统产品线
     */
    @Column(name="NoticeProductLine")
    @Type(value=Types.SMALLINT)
    private Integer noticeProductLine;

    /**
     * 通知待处理
     */
    @Column(name="NoticeWaitLimit")
    @Type(value=Types.INTEGER)
    private Integer noticeWaitLimit;

    /**
     * 投诉待处理上限
     */
    @Column(name="ComplainWaitLimit")
    @Type(value=Types.INTEGER)
    private Integer complainWaitLimit;
    /**
     * 职位
     */
    @Column(name="positionName")
    @Type(value=Types.VARCHAR)
    private String positionName;

    @Column(name="positionId")
    @Type(value=Types.INTEGER)
    private Integer positionId;
}
