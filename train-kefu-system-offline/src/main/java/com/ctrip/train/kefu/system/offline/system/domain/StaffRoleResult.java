package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class StaffRoleResult implements DalPojo {


    @Column(name="staffNum")
    @Type(value=Types.VARCHAR)
    private String staffNum;

    @Column(name="roleName")
    @Type(value=Types.VARCHAR)
    private String roleName;

    @Column(name="roleId")
    @Type(value=Types.INTEGER)
    private long roleId;

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
