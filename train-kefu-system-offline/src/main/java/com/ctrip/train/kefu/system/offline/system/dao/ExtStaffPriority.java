package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineStaffPriorityNoticeDao;
import dao.ctrip.ctrainchat.entity.OfflinePermission;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class ExtStaffPriority extends OfflineStaffPriorityNoticeDao{
    @Autowired
    protected DalQueryDao baseDao;
    public ExtStaffPriority() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
//    public void searchStaffPriority(){
//        try {
//            DalUtils.Builder builder = DalUtils.createBuilder();
//            builder.combine(" SELECT  *  From offline_staff_priority_notice sp LEFT JOIN chat_staff_info op on s.PositionId=op.Id where isDelete=1");
////            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
//            DalHints hints = new DalHints();
//            hints.set(DalHintEnum.allowPartial);
//            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflinePermission.class);
//        } catch (Exception ex) {
//            CLogger.error("dao:searchPerm", ex);
//            return null;
//        }
//    }
}
