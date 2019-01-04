package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.annotation.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
@Getter
@Setter
public class GroupStaffName {

    @Column(name="tid")
    @Type(value=Types.INTEGER)
    private long tid;

    //员工编号
    @Column(name="staffNumber")
    @Type(value=Types.VARCHAR)
    private String staffNumber;

    //员工姓名
    @Column(name="staffName")
    @Type(value=Types.VARCHAR)
    private String staffName;

    //是否
    @Column(name="checked")
    @Type(value= Types.VARCHAR)
    private String checked;
}
