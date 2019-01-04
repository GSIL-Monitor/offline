package com.ctrip.train.kefu.system.offline.system.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.system.domain.StaffsResult;
import com.ctrip.train.kefu.system.offline.system.vm.VmStaffRequest;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.ChatStaffInfoDao;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class ExtSysChatStaffInfo extends ChatStaffInfoDao{

    protected DalQueryDao baseDao;
    public ExtSysChatStaffInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<StaffsResult> searchStaff(VmStaffRequest request){

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" SELECT  s.*,op.positionName  From chat_staff_info s LEFT JOIN offline_position op on s.PositionId=op.Id where 1=1 and s.is_delete=1 ");
            builder.combine(!StringUtils.isBlank(request.getStaffName()), " and s.staff_Name like ?", Types.VARCHAR, request.getStaffName()+"%");
            builder.combine(request.getPositionId()!=null, " and s.positionId=?", Types.INTEGER, request.getPositionId());
            builder.combine(request.getProductLine() != null&&!request.getProductLine().equals(""), " and s.ProductLine=?", Types.VARCHAR, request.getProductLine());
            builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, StaffsResult.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchStaff", ex);
            return null;
        }
    }
    public int searchStaffCount(VmStaffRequest request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  count(1)  From chat_staff_info s LEFT JOIN offline_position op on s.PositionId=op.Id where 1=1 ");
            builder.combine(!StringUtils.isBlank(request.getStaffName()), " and s.staff_Name=?", Types.VARCHAR, request.getStaffName());
            builder.combine(request.getPositionId()!=null," and s.positionId=?", Types.INTEGER, request.getPositionId());
//            builder.combine(request.getProductLine() != null, " and ProductLine=?", Types.VARCHAR, request.getProductLine());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchStaffCount", ex);
            return 0;
        }
    }

    public ChatStaffInfo queryStaffByStaffNum(String staffNum,int isdelete){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  From chat_staff_info where Staff_Number=?",Types.VARCHAR, staffNum);
            builder.combine(" and Is_Delete=?", Types.VARCHAR, isdelete);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, ChatStaffInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:queryStaffByStaffNum", ex);
            return null;
        }
    }

    public ChatStaffInfo queryStaffBytId(Long tId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  From chat_staff_info where tid=?",Types.VARCHAR, tId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, ChatStaffInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:queryStaffByStaffNum", ex);
            return null;
        }
    }

    /**
     * 更改员工状态
     */
    public int deleteStaffByStaffNum(String staffNum,int isDelete){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("update chat_staff_info set Is_Delete=? where 1=1 " ,Types.INTEGER, isDelete);
            builder.combine(" and Staff_Number=? ", Types.VARCHAR,staffNum);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return  baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
        } catch (Exception ex) {
            CLogger.error("dao:deleteStaffByStaffNum", ex);
            return 0;
        }
    }

    public ChatStaffInfo queryStaffByStaffName(String staffName ){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  From chat_staff_info where Staff_Name=?",Types.VARCHAR, staffName);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), hints, ChatStaffInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:queryStaffByStaffName", ex);
            return null;
        }
    }
}
