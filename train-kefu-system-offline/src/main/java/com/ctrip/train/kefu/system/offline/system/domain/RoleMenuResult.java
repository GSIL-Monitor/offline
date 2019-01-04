package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Types;

@Entity
public class RoleMenuResult implements DalPojo {


    @Column(name="menuId")
    @Type(value=Types.INTEGER)
    private int menuId;

    @Column(name="menuName")
    @Type(value=Types.VARCHAR)
    private String menuName;

    @Column(name="parentId")
    @Type(value=Types.INTEGER)
    private long parentId;

    @Column(name="url")
    @Type(value=Types.VARCHAR)
    private String url;

    @Column(name="type")
    @Type(value=Types.VARCHAR)
    private String type;

    @Column(name="icon")
    @Type(value=Types.VARCHAR)
    private String icon;

    @Column(name="checked")
    @Type(value=Types.VARCHAR)
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
