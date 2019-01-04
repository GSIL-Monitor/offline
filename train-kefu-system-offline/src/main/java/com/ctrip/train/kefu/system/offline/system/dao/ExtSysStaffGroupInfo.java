package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.StaffGroupResult;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.StaffGroupInfoDao;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtSysStaffGroupInfo extends StaffGroupInfoDao {
    protected DalQueryDao baseDao;
    public ExtSysStaffGroupInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    /**
     e.g. SELECT * FROM staff_group_info
     where tid=1
     and groupname like '%二推%'
     and productline=1
     and IsDelete=1
     and supervisorstaffnumber='S61122'
     */
    public List<StaffGroupResult> searchStaffGroupList(int pageIndex, int pageSize, String productLine, String groupName)  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select g.*,s.Staff_Name as staffName from staff_group_info g INNER JOIN chat_staff_info s ON g.SupervisorStaffNUmber=s.Staff_Number WHERE 1=1 and g.isDelete=1");
        builder.combine(groupName!=null&&groupName.isEmpty()," and groupname like ?",Types.VARBINARY,"%"+groupName+"%");
        builder.combine(productLine!=null&&productLine.isEmpty()," and ProductLine=?",Types.INTEGER,productLine);
        builder.combinePageLimit(pageIndex > 0 && pageSize > 0, pageIndex, pageSize);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.query(builder.getSql(),builder.getParameters(),hints,StaffGroupResult.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffGroupList", e);
            return null;
        }
    }

    public int searchStaffGroupListCount(String productLine,String groupName)  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("SELECT count(1) FROM staff_group_info where 1=1  ");
        builder.combine(groupName!=null&&groupName.isEmpty()," and groupname like ?",Types.VARBINARY,"%"+groupName+"%");
        builder.combine(productLine!=null&&productLine.isEmpty()," and productline=?",Types.INTEGER,productLine);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.queryForObject(builder.getSql(),builder.getParameters(),hints,Integer.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffGroupListCount", e);
            return 0;
        }
    }

    public int deleteGroup(int tId)  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("update staff_group_info set IsDelete=0  where tid=? ", Types.INTEGER,tId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffGroupListCount", e);
            return 0;
        }
    }

   public StaffGroupResult getGroup(long tId)  {
        DalHints hints=new DalHints();
        DalUtils.Builder builder = DalUtils.createBuilder();
       builder.combine("select g.*,s.Staff_Name as staffName from staff_group_info g INNER JOIN chat_staff_info s ON g.SupervisorStaffNUmber=s.Staff_Number WHERE 1=1  ");
       builder.combine(" and g.tid=?",Types.INTEGER,tId);
        hints.set(DalHintEnum.allowPartial);
        try {
            return  baseDao.queryForObjectNullable(builder.getSql(),builder.getParameters(),hints,StaffGroupResult.class);
        } catch (SQLException e) {
            CLogger.error("dao:searchStaffGroupListCount", e);
            return null;
        }
    }

}
