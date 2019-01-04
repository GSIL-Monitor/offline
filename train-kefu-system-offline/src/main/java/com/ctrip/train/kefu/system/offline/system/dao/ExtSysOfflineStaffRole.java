package com.ctrip.train.kefu.system.offline.system.dao;


import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineStaffRoleDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;

@Repository
public class ExtSysOfflineStaffRole extends OfflineStaffRoleDao {
    protected DalQueryDao baseDao;
    public ExtSysOfflineStaffRole() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public void removeStaffRoleByRoleId(long rId){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("delete from offline_staff_role where roleId=?", Types.INTEGER,rId);
        hints.set(DalHintEnum.allowPartial);
        try {
            baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:removeStaffRoleByRoleId", e);
        }
    }
    public void removeStaffRoleByStaffNum(String staffNum){
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("delete from offline_staff_role where staffNum=?", Types.VARCHAR,staffNum);
        hints.set(DalHintEnum.allowPartial);
        try {
            baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:removeStaffRoleByRoleId", e);
        }
    }

}
