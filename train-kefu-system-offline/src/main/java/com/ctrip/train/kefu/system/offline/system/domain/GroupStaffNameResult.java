package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.annotation.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
@Getter
@Setter
public class GroupStaffNameResult {
    //主键
    @Id
    @Column(name="Tid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value= Types.BIGINT)
    private Long tid;

    //组ID
    @Column(name="GroupTid")
    @Type(value=Types.INTEGER)
    private Integer groupTid;

    //员工编号
    @Column(name="StaffNumber")
    @Type(value=Types.VARCHAR)
    private String staffNumber;

    //更新时间
    @Column(name="DataChange_LastTime", insertable=false, updatable=false)
    @Type(value=Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    //0:删除，1：未删除
    @Column(name="Is_Delete")
    @Type(value=Types.INTEGER)
    private Integer isDelete;

    //员工姓名
    @Column(name="staffName")
    @Type(value=Types.VARCHAR)
    private String staffName;

    //是否
    @Column(name="checked")
    @Type(value=Types.VARCHAR)
    private String checked;

}
