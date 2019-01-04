package com.ctrip.train.kefu.system.offline.system.domain;

import com.ctrip.platform.dal.dao.annotation.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Types;
@Getter
@Setter
@Entity
public class MenusNodeResult {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(value = Types.BIGINT)
    private Long id;

    /**
     * 菜单名称
     */
    @Column(name = "menuName")
    @Type(value = Types.VARCHAR)
    private String menuName;

    /**
     * 菜单类型
     */
    @Column(name = "type")
    @Type(value = Types.VARCHAR)
    private String type;

    /**
     * 菜单URL
     */
    @Column(name = "url")
    @Type(value = Types.VARCHAR)
    private String url;

    /**
     * 权限标识
     */
    @Column(name = "permission")
    @Type(value = Types.VARCHAR)
    private String permission;

    /**
     * 父类菜单
     */
    @Column(name = "parent_id")
    @Type(value = Types.BIGINT)
    private Long parentId;

    /**
     * 菜单排序
     */
    @Column(name = "sort")
    @Type(value = Types.INTEGER)
    private Integer sort;

    /**
     * 是否有外部链接
     */
    @Column(name = "external")
    @Type(value = Types.TINYINT)
    private Integer external;

    /**
     * 可用
     */
    @Column(name = "available")
    @Type(value = Types.TINYINT)
    private Integer available;

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    @Type(value = Types.VARCHAR)
    private String icon;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Type(value = Types.TIMESTAMP)
    private Timestamp createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "DataChange_LastTime", insertable = false, updatable = false)
    @Type(value = Types.TIMESTAMP)
    private Timestamp datachangeLasttime;

    /**
     * 菜单id
     */
    @Column(name = "menuId")
    @Type(value = Types.BIGINT)
    private Long menuId;

    /**
     * 菜单id
     */
    @Column(name = "parentName")
    @Type(value = Types.VARCHAR)
    private String parentName;

}
