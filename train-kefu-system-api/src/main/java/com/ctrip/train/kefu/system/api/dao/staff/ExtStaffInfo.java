package com.ctrip.train.kefu.system.api.dao.staff;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainchat.dao.ChatStaffInfoDao;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class ExtStaffInfo extends ChatStaffInfoDao {
    protected DalQueryDao baseDao;
    public ExtStaffInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取上班的员工数据
     */
    public ChatStaffInfo getStaffInfoByNum(String staffNum) {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select * from  chat_staff_info ");
        builder.combine("where is_delete = 1  and  NoticeProductLine in(31,134,135,137)");
        builder.combine(StringUtils.isNotBlank(staffNum)," And   Staff_Number =?",staffNum);
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);

        try {
            return baseDao.queryForObjectNullable(builder.getSql(), builder.getParameters(), dalHints, ChatStaffInfo.class);
        }
        catch (Exception ex){
            CLogger.error("getStaffInfoByNum",ex);
        }
        return null;
    }
}
