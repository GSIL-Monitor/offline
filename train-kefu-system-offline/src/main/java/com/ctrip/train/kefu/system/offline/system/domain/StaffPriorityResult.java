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
    @Column(name = "envenType")
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
}
