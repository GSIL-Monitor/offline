package com.ctrip.train.kefu.system.offline.common.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflinePermissionDao;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtOfflinePermission extends OfflinePermissionDao {

    protected DalQueryDao baseDao;
    public ExtOfflinePermission() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }


    /**
     * 根据角色获取权限
     */
    public List<OfflinePermission> getStaffPermission(List<Long> roleIds){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select offline_permission.* from offline_role_perm");
            builder.combine(" inner join offline_permission on offline_role_perm.permId=offline_permission.id");
            builder.combine(" where 1=1");
            builder.combineIn(roleIds !=null, " and roleId in (?)", Types.BIGINT,  roleIds);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflinePermission.class);
        } catch (Exception ex) {
            CLogger.error("dao:getStaffPermission", ex);
            return null;
        }
    }
}
