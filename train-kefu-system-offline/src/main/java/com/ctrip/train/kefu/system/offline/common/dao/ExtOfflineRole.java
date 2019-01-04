package com.ctrip.train.kefu.system.offline.common.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineRoleDao;
import dao.ctrip.ctrainchat.entity.OfflineRole;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtOfflineRole extends OfflineRoleDao{

    protected DalQueryDao baseDao;
    public ExtOfflineRole() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取员工角色
     */
    public List<OfflineRole> getStaffRoles(String staffNum){

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("select offline_role.* from offline_staff_role");
            builder.combine(" inner join offline_role on offline_staff_role.roleId=offline_role.id");
            builder.combine(" where 1=1");
            builder.combine(staffNum != null, " and staffNum=?", Types.VARCHAR, staffNum);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflineRole.class);
        } catch (Exception ex) {
            CLogger.error("dao:getStaffRoles", ex);
            return null;
        }

    }
}
