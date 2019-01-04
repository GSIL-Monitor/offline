package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.DalPojo;
import com.ctrip.platform.dal.dao.annotation.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;

@Entity
public class StaffMenuResult implements DalPojo {


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

    @Column(name="permission")
    @Type(value=Types.VARCHAR)
    private String permission;

    @Column(name="icon")
    @Type(value=Types.VARCHAR)
    private String icon;

    @Column(name="external")
    @Type(value=Types.INTEGER)
    private Integer external;

    @Column(name="available")
    @Type(value=Types.INTEGER)
    private Integer available;


    @Column(name="nodeMenuId")
    @Type(value=Types.INTEGER)
    private int nodeMenuId;

    @Column(name="nodeMenuName")
    @Type(value=Types.VARCHAR)
    private String nodeMenuName;

    @Column(name="nodeType")
    @Type(value=Types.VARCHAR)
    private String nodeType;

    @Column(name="nodeParentId")
    @Type(value=Types.INTEGER)
    private long nodeParentId;

    @Column(name="nodeUrl")
    @Type(value=Types.VARCHAR)
    private String nodeUrl;

    @Column(name="nodePermission")
    @Type(value=Types.VARCHAR)
    private String nodePermission;

    @Column(name="nodeIcon")
    @Type(value=Types.VARCHAR)
    private String nodeIcon;

    @Column(name="nodeExternal")
    @Type(value=Types.INTEGER)
    private Integer nodeExternal;

    @Column(name="nodeAvailable")
    @Type(value=Types.INTEGER)
    private Integer nodeAvailable;

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public int getNodeMenuId() {
        return nodeMenuId;
    }

    public void setNodeMenuId(int nodeMenuId) {
        this.nodeMenuId = nodeMenuId;
    }

    public String getNodeMenuName() {
        return nodeMenuName;
    }

    public void setNodeMenuName(String nodeMenuName) {
        this.nodeMenuName = nodeMenuName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public long getNodeParentId() {
        return nodeParentId;
    }

    public void setNodeParentId(long nodeParentId) {
        this.nodeParentId = nodeParentId;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getNodePermission() {
        return nodePermission;
    }

    public void setNodePermission(String nodePermission) {
        this.nodePermission = nodePermission;
    }

    public String getNodeIcon() {
        return nodeIcon;
    }

    public void setNodeIcon(String nodeIcon) {
        this.nodeIcon = nodeIcon;
    }

    public Integer getExternal() {
        return external;
    }

    public void setExternal(Integer external) {
        this.external = external;
    }

    public Integer getNodeExternal() {
        return nodeExternal;
    }

    public void setNodeExternal(Integer nodeExternal) {
        this.nodeExternal = nodeExternal;
    }

    public Integer getNodeAvailable() {
        return nodeAvailable;
    }

    public void setNodeAvailable(Integer nodeAvailable) {
        this.nodeAvailable = nodeAvailable;
    }
}
