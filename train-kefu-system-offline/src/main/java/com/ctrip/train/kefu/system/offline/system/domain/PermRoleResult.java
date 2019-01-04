package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class PermRoleResult implements DalPojo {
    @Column(name="permId")
    @Type(value= Types.INTEGER)
    private int permId;

    @Column(name="permName")
    @Type(value=Types.VARCHAR)
    private String permName;

    @Column(name="permType")
    @Type(value=Types.VARCHAR)
    private String permType;

    @Column(name="checked")
    @Type(value=Types.VARCHAR)
    private String checked;

    public int getPermId() {
        return permId;
    }

    public void setPermId(int permId) {
        this.permId = permId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermType() {
        return permType;
    }

    public void setPermType(String permType) {
        this.permType = permType;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
