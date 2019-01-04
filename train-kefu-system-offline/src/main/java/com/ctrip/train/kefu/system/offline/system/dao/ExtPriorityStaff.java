package com.ctrip.train.kefu.system.offline.system.dao;


import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.StaffPriorityResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmPriorityRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.OfflineStaffPriorityNoticeDao;
import dao.ctrip.ctrainchat.entity.OfflineStaffPriorityNotice;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtPriorityStaff extends OfflineStaffPriorityNoticeDao {
    
    protected DalQueryDao baseDao;
    
    public ExtPriorityStaff() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<StaffPriorityResult> searchPriority(VmPriorityRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  n.*,s.Staff_Name as staffName  From offline_staff_priority_notice n LEFT JOIN chat_staff_info s on n.staffNum=s.Staff_Number  where 1=1");
            builder.combine(!StringUtils.isBlank(request.getStaffName())," and s.Staff_Name=?",request.getStaffName());
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, StaffPriorityResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPriority", ex);
            return null;
        }
    }
    public int searchPriorityCount(VmPriorityRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  count(1)  From offline_staff_priority_notice n LEFT JOIN chat_staff_info s on n.staffNum=s.Staff_Number  where 1=1");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPriorityCount", ex);
            return 0;
        }
    }
    public List<StaffPriorityResult> searchPriorityByStaffNum(VmPriorityRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  n.*,s.Staff_Name as staffName  From offline_staff_priority_notice n LEFT JOIN chat_staff_info s on n.staffNum=s.Staff_Number  where n.isDelete=1");
            builder.combine(" and n.staffNum=?", Types.VARCHAR,request.getStaffNum());
            builder.combine(" and n.noticeProductLine=?", Types.INTEGER,Integer.valueOf(request.getNoticeProductLine()));
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, StaffPriorityResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPriority", ex);
            return null;
        }
    }

    public List<OfflineStaffPriorityNotice> searchPrioritys(OfflineStaffPriorityNotice request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT * from offline_staff_priority_notice where available=1");
            builder.combine(" and staffNum=?", Types.VARCHAR,request.getStaffNum());
            builder.combine(" and noticeProductLine=?", Types.VARCHAR,request.getNoticeProductLine());
            builder.combine(" and envenType=?", Types.VARCHAR,request.getEnvenType());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OfflineStaffPriorityNotice.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchPriority", ex);
            return null;
        }
    }

}
