package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.RequestOnceSolveReport;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import dao.ctrip.ctrainpps.dao.NoticeOnceSolveDao;
import dao.ctrip.ctrainpps.entity.NoticeOnceSolve;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


@Repository
public class ExtOnceSolveInfo extends NoticeOnceSolveDao {

    protected DalQueryDao baseDao;
    public ExtOnceSolveInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    /**
     * 查询 一次性解决
     * @param request
     * @return
     */
    public List<NoticeOnceSolve> SearchOnceSolve(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  *  from notice_once_solve ");
            builder.combine(" where 1=1 ");
            builder.combine(request.getStartDate() != null, " and opTime>=?", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and opTime<=?", Types.DATE, request.getEndDate());
            builder.combine(request.getEnvenType() != null, " and envenType=?", Types.INTEGER, request.getEnvenType());
            builder.combine(request.getProductLine() != null, " and productLine=? ", Types.INTEGER, request.getProductLine());
            builder.combine(request.getOpUser() != null &&!request.getOpUser().equals(""), " and opusername=?", Types.VARCHAR, request.getOpUser());
            if(request.getPageIndex() != -1 && request.getPageSize() != -1)
                builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeOnceSolve.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return null;
        }
    }
    /**
     * 查询 一次性解决汇总
     * @param request
     * @return
     */
    public int SearchOnceSolveCount(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  count(1)  From notice_once_solve ");
            builder.combine(" where 1=1 ");
            builder.combine(request.getStartDate() != null, " and opTime>=?", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and opTime<=?", Types.DATE, request.getEndDate());
            builder.combine(request.getEnvenType() != null, " and envenType=?", Types.INTEGER, request.getEnvenType());
            builder.combine(request.getProductLine() != null, " and ProductLine=?", Types.INTEGER, request.getProductLine());
            builder.combine(request.getOpUser() != null&&!request.getOpUser().equals(""), " and opusername=?", Types.VARCHAR, request.getOpUser());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:SearchOnceSolveCount", ex);
            return 0;
        }
    }

    /**
     * 订单一次性解决
     * @param request
     * @return
     */
    public List<NoticeOnceSolve> SearchOrderOnceSolve(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT opTime, envenType,productLine, SUM(oncesovle) as oncesovle ,SUM(allnum) as allnum, SUM(oncesovle)/SUM(allnum) as percentage from notice_once_solve ");
            builder.combine(" where 1=1 ");
            builder.combine(request.getStartDate() != null, " and opTime>=?", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and opTime<=?", Types.DATE, request.getEndDate());
            builder.combine(request.getEnvenType() != null, " and envenType=?", Types.INTEGER, request.getEnvenType());
            builder.combine(request.getProductLine() != null, " and ProductLine=?", Types.INTEGER, request.getProductLine());
            builder.combine(" GROUP BY opTime,envenType,productLine ");
            if(request.getPageIndex() != -1 && request.getPageSize() != -1)
                builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeOnceSolve.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return null;
        }
    }

    public int SearchOrderOnceSolveCount(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT Count(1) from notice_once_solve");
            builder.combine(" where 1=1 ");
            builder.combine(request.getStartDate() != null, " and opTime>=?", Types.DATE, request.getStartDate());
            builder.combine(request.getEndDate() != null, " and opTime<=?", Types.DATE, request.getEndDate());
            builder.combine(request.getEnvenType() != null, " and envenType=?", Types.INTEGER, request.getEnvenType());
            builder.combine(request.getProductLine() != null, " and ProductLine=?", Types.INTEGER, request.getProductLine());
            builder.combine(" GROUP BY opTime ,envenType,productLine ");
            if(request.getPageIndex() != -1 && request.getPageSize() != -1)
                builder.combinePageLimit(request.getPageIndex() > 0 && request.getPageSize() > 0, request.getPageIndex(), request.getPageSize());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOperateInfo", ex);
            return 0;
        }
    }


}
