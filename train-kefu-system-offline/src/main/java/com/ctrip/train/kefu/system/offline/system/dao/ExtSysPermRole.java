package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.PermRoleResult;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineRolePermDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtSysPermRole extends OfflineRolePermDao{
    protected DalQueryDao baseDao;
    public ExtSysPermRole() throws SQLException {
         super();
         baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<PermRoleResult> searchRolePerm(Long roleId){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine(" select p.id as permId,p.permName as permName,p.permCode as permCode,p.permType as permType,(CASE WHEN EXISTS ( " );
        builder.combine(" SELECT 1 FROM offline_role_perm rp WHERE rp.permId = p.id "  );
        builder.combine(" AND rp.roleId =? ", Types.VARCHAR,roleId );
        builder.combine(" ) THEN 'true' ELSE 'false' END ) AS checked  FROM offline_permission p ORDER BY p.id ASC ");
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,PermRoleResult.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchRolePerm", e);
            return null;
        }
    }

    public void removePermByRoleId(long rId){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("delete from offline_role_perm where roleId=?", Types.INTEGER,rId);
        hints.set(DalHintEnum.allowPartial);
        try {
            baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:removePermByRoleId", e);
        }
    }
}
