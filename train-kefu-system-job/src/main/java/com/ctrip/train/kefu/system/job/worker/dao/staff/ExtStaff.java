package com.ctrip.train.kefu.system.job.worker.dao.staff;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import common.constants.DatabaseName;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.ChatStaffInfoDao;
import dao.ctrip.ctrainchat.entity.ChatStaffInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ExtStaff extends ChatStaffInfoDao {
    protected DalQueryDao baseDao;
    public ExtStaff() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }

    /**
     * 获取上班的员工数据
     * @throws SQLException
     */
    public List<ChatStaffInfo> searchWorkingStaff()throws SQLException {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select * from  chat_staff_info where is_delete = 1 and Staff_State='1' and  NoticeProductLine in(31,134,135,137)");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ChatStaffInfo.class);
    }
}
