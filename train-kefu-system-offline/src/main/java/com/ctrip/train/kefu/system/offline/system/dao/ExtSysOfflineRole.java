package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.PermRoleResult;
import com.ctrip.train.kefu.system.offline.system.domain.RoleMenuResult;
import com.ctrip.train.kefu.system.offline.system.domain.StaffRoleResult;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineRoleDao;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


/**
 * @author ying_zhou 2017/10/25 8:19
 */
@Repository
public class ExtSysOfflineRole extends OfflineRoleDao {
    protected DalQueryDao baseDao;
    public ExtSysOfflineRole() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public int getRoleIdInfo()  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" select IFNULL( MAX(roleId),0) as roleId from offline_role ");
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.queryForObjectNullable(builder.getSql(),builder.getParameters(),hints,Integer.class);
        } catch (SQLException e) {
            CLogger.error("dao:getRoleIdInfo", e);
            return 0;
        }
    }
    public List<OfflineRole> searchRoleList(int pageIndex, int pageSize) {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" select * from offline_role where 1=1 order by  available desc,roleName ASC,create_time desc ");
        builder.combinePageLimit(pageIndex > 0 && pageSize > 0, pageIndex, pageSize);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,OfflineRole.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchRoleList", e);
            return null;
        }
    }
    public int searchRoleListCount() {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select count(1) from offline_role where 1=1 ");
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.queryForObjectNullable(builder.getSql(),builder.getParameters(),hints,Integer.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchRoleListCount", e);
            return 0;
        }
    }

    public int deleteRole(int tId)  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("update offline_role set available=0  where roleId=? ", Types.INTEGER,tId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:deleteRole", e);
            return 0;
        }
    }
    public OfflineRole getRoleByRoleId(Long roleId) {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select * from offline_role where roleId=?",Types.INTEGER,roleId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.queryForObjectNullable(builder.getSql(),builder.getParameters(),hints,OfflineRole.class);
        } catch (SQLException e) {
            CLogger.error("dao:getRoleByRoleId", e);
            return null;
        }
    }

    /**
     *
         select om.*,(CASE WHEN EXISTS (
         SELECT 1 FROM offline_menu_role mr WHERE mr.menuId = om.menuId
         AND mr.roleId = #{rid}
         ) THEN 'true' ELSE 'false' END ) AS checked
         FROM offline_menu om ORDER BY om.sort ASC
     *
     *
     * @param roleId
     * @return
     */
    public List<RoleMenuResult> searchRoleMenu(Long roleId){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" select om.menuId,om.menuName,om.parent_id as parentId,om.url,om.type,om.icon,(CASE WHEN EXISTS ( " );
        builder.combine(" SELECT 1 FROM offline_menu_role mr WHERE mr.menuId = om.menuId "  );
        builder.combine(" AND mr.roleId =? ",Types.VARCHAR,roleId );
        builder.combine(" ) THEN 'true' ELSE 'false' END ) AS checked FROM offline_menu om ORDER BY om.sort ASC ");
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,RoleMenuResult.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchRoleMenu", e);
            return null;
        }
    }

    public List<OfflineRole> searchAllRoles(Integer available) {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select * from offline_role where 1=1 ");
        builder.combine(available!=null," AND available =? ",Types.INTEGER,available );
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,OfflineRole.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchAllRoles", e);
            return null;
        }
    }

    public List<StaffRoleResult> searchStaffRoles() {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("SELECT  ur.staffNum,r.roleId,r.roleName from offline_staff_role ur left join offline_role r ON  ur.roleid=r.roleId");
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,StaffRoleResult.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffRoles", e);
            return null;
        }
    }

}
