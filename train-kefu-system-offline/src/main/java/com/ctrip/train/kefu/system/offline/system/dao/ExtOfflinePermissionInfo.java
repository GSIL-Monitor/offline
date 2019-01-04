package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.OfflinePermissionDao;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtOfflinePermissionInfo extends OfflinePermissionDao {
    protected DalQueryDao baseDao;
    public ExtOfflinePermissionInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<OfflinePermission> searchPerm(VmPermissionRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  *  From offline_permission where isDelete=1 order by permType Asc ");
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflinePermission.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPerm", ex);
            return null;
        }
    }
    public int searchPermCount(VmPermissionRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT count(1)  From offline_permission where isDelete=1 ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPerm", ex);
            return 0;
        }
    }
    public int searchPermissionByStaffNum(String staffNum){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT op.permCode from offline_staff_role osr INNER JOIN offline_role_perm orp ON orp.roleId=osr.roleId ");
            builder.combine(" INNER JOIN offline_permission op ON op.id= orp.permId ");
            builder.combine(StringUtils.isNotBlank(staffNum),"where osr.staffNum=? ", Types.VARCHAR,staffNum);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPerm", ex);
            return 0;
        }
    }

}
