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
public class MenusResult {

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
     * 菜单id
     */
    @Column(name = "menuId")
    @Type(value = Types.BIGINT)
    private Long menuId;

    /**
     * 菜单id
     */
    @Column(name = "nodeId")
    @Type(value = Types.BIGINT)
    private Long nodeId;

    /**
     * 菜单
     */
    @Column(name = "nodeName")
    @Type(value = Types.VARCHAR)
    private String nodeName;

    /**
     * 菜单id
     */
    @Column(name = "parentId")
    @Type(value = Types.BIGINT)
    private Long parentId;
}
