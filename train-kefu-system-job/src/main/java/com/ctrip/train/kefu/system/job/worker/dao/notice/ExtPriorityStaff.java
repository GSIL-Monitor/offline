package com.ctrip.train.kefu.system.job.worker.dao.notice;


import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.train.kefu.system.job.worker.domain.StaffPriorityResult;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainchat.dao.OfflineStaffPriorityNoticeDao;
import dao.ctrip.ctrainchat.entity.OfflineStaffPriorityNotice;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExtPriorityStaff extends OfflineStaffPriorityNoticeDao {
    
    protected DalQueryDao baseDao;
    
    public ExtPriorityStaff() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_CHAT);
    }
    public List<StaffPriorityResult> searchPrioritys(List<String> staffNums){

        StringBuffer buf = new StringBuffer();
        if (staffNums!=null&&!staffNums.isEmpty()){
            for (String item:staffNums)  {
                buf.append("'");
                buf.append(item);
                buf.append("'");
                buf.append(",");
            }
        }
        String staffNum = buf.toString();
        staffNum=staffNum.substring(0,staffNum.length()-1);

        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("  SELECT  n.*,s.Staff_Name as staffName,s.noticeWaitLimit,s.complainWaitLimit From offline_staff_priority_notice n " +
                    " LEFT JOIN chat_staff_info s on n.staffNum=s.Staff_Number  where n.available=1");
            builder.combine( String.format(" and n.staffNum in (%s) ",staffNum));
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, StaffPriorityResult.class);

        } catch (Exception ex) {
            CLogger.error("dao:searchPriority", ex);
            return null;
        }
    }
}
