package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.GroupStaffName;
import com.ctrip.train.kefu.system.offline.system.domain.GroupStaffNameResult;
import com.ctrip.train.kefu.system.offline.system.domain.MenusNodeResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmMenuRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.GroupStaffRelationDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtSysGroupStaffRelationDao extends GroupStaffRelationDao {
    protected DalQueryDao baseDao;
    public ExtSysGroupStaffRelationDao() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    public List<GroupStaffNameResult> searchGroupStaff(int pageIndex, int pageSize, long groupId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT gsr.*,c.Staff_Name as staffName from group_staff_relation gsr LEFT JOIN chat_staff_info c ON gsr.StaffNumber=c.Staff_Number ");
            builder.combine(" where gsr.is_delete=1 " );
            builder.combine(  "AND gsr.groupTId=? ", Types.INTEGER,groupId);
            builder.combinePageLimit(pageIndex > 0 && pageSize > 0, pageIndex, pageSize);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, GroupStaffNameResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchGroupStaff", ex);
            return null;
        }
    }
    public int searchGroupStaffCount(Long groupId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT count(1) from group_staff_relation gsr LEFT JOIN chat_staff_info c ON gsr.StaffNumber=c.Staff_Number ");
            builder.combine(" where gsr.is_delete=1 ");
            builder.combine(  "AND gsr.groupTId=? ", Types.INTEGER,groupId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchGroupStaffCount", ex);
            return 0;
        }
    }

    public List<GroupStaffName> searchGroupStaffCheck(Long groupId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT s.tid,s.staff_Number as staffNumber,s.staff_name as staffName, (CASE WHEN EXISTS (SELECT 1 FROM group_staff_relation gs WHERE ");
            builder.combine("gs.staffNumber = s.staff_Number AND gs.groupTId = ? ",Types.INTEGER,groupId );
            builder.combine(" )THEN 'true' ELSE 'false' END ) AS checked  from  chat_staff_info s ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, GroupStaffName.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchGroupStaffCount", ex);
            return null;
        }
    }
    public  List<String> searchStaffGroupByGroupId(long groupId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT staffNumber FROM group_staff_relation WHERE  is_delete=1");
            builder.combine("and groupTid=?",Types.INTEGER,groupId );
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, String.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchGroupStaffCount", ex);
            return null;
        }
    }
    public int deleteStaffGroupByGroupId(long groupId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("delete FROM group_staff_relation WHERE ");
            builder.combine(" groupTid=?",Types.INTEGER,groupId );
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.getClient().update(builder.getSql(), builder.getParameters(),new DalHints());
        } catch (Exception ex) {
            CLogger.error("dao:searchGroupStaffCount", ex);
            return 0;
        }
    }
}
