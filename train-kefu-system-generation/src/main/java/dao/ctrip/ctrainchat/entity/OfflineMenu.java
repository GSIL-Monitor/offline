package dao.ctrip.ctrainchat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ctrip.platform.dal.dao.annotation.Database;
import com.ctrip.platform.dal.dao.annotation.Sensitive;
import com.ctrip.platform.dal.dao.annotation.Type;
import java.sql.Types;
import java.sql.Timestamp;

import com.ctrip.platform.dal.dao.DalPojo;

/**
 * @author wql王全礼
 * @date 2018-08-30
 */
@Entity
@Database(name = "CtrainChat")
@Table(name = "offline_menu")
public class OfflineMenu implements DalPojo {

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getExternal() {
		return external;
	}

	public void setExternal(Integer external) {
		this.external = external;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Timestamp datachangeLasttime) {
		this.datachangeLasttime = datachangeLasttime;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}