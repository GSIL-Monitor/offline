package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.vm.VmPermissionRequest;
import com.ctrip.train.kefu.system.offline.system.vm.VmPostRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflinePositionDao;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import dao.ctrip.ctrainchat.entity.OfflinePosition;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtSysOfflinePosition extends OfflinePositionDao{
    protected DalQueryDao baseDao;

    public ExtSysOfflinePosition() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<OfflinePosition> searchPost(VmPostRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  *  From offline_position where 1=1 and isDelete=1");
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflinePosition.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPost", ex);
            return null;
        }
    }
    public int searchPostCount(VmPostRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT count(1)  From offline_position where 1=1 and isDelete=1 ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPostCount", ex);
            return 0;
        }
    }
    public List<OfflinePosition> searchAllPost(){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  *  From offline_position where isDelete=1");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflinePosition.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchAllPost", ex);
            return null;
        }
    }
}
