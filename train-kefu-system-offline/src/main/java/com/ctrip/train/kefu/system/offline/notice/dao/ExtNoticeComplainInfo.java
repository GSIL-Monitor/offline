package com.ctrip.train.kefu.system.offline.notice.dao;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.offline.notice.domain.*;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.RequestOnceSolveReport;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.NoticeComplainInfoDao;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.ScmSmallEnum;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExtNoticeComplainInfo extends NoticeComplainInfoDao{
    protected DalQueryDao baseDao;
    public ExtNoticeComplainInfo() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }

    /**
     * 获取通知数据
     * 产品线，渠道，操作人，类型，时间
     * return List
     * */
    public List<NoticeReportSum> getNoticeListTwo(Integer pageIndex, Integer pageSize, Integer productLine, String dataSource,
                                               String opUser, Integer evenType,
                                               Date startTime , Date endTime) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  OpCount,date_format(EnterDate, '%Y-%m-%d') as OpTime,NoticeState,OpUserName,OpUserNum,FirstDealTime,StartDealTIme,AppointedProcessTime ,FirstCallTime" +
                    ",EnvenType,SendTime,CompleteTime,OpUser,ChangeDutyTime  From notice_complain_info ");
            builder.combine(" where 1=1 ");
            if(productLine != null){
                builder.combine( " and Product_Line=?", Types.INTEGER, productLine);
            }else {
                builder.combine( " and Product_Line in (3,31,134,135,137,138) ");
            }
            builder.combine(evenType != null, " and EnvenType=?", Types.INTEGER, evenType);
            builder.combine(dataSource != null&&!dataSource.equals(""), String.format(" AND dataSource IN (%s) ", dataSource));
            builder.combine(opUser != null&&!opUser.equals(""), " and opUserName=?", Types.VARCHAR, opUser);
            builder.combine(startTime != null, " and EnterDate>=?", Types.DATE, startTime);
            builder.combine(endTime != null, " and EnterDate<?", Types.DATE, endTime);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeReportSum.class);

        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeList", ex);
            return null;
        }
    }
    /**
     * 获取通知数据
     * 产品线，渠道，操作人，类型，时间
     * return List
     * */
    public List<NoticeComplainInfo> GetNoticeList(Integer pageIndex, Integer pageSize, Integer productLine, String dataSource,
                                               String opUser, Integer evenType,
                                               Date startTime , Date endTime) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT *  From notice_complain_info ");
            builder.combine(" where 1=1 ");
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine(evenType != null, " and EnvenType=?", Types.INTEGER, evenType);
            builder.combine(dataSource != null&&dataSource.equals(""), String.format(" AND dataSource IN (%s) ", dataSource));
            builder.combine(opUser != null&&opUser.equals(""), " and opUserName=?", Types.VARCHAR, opUser);
            builder.combine(startTime != null, " and opTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and opTime<?", Types.DATE, endTime);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeComplainInfo.class);

        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeList", ex);
            return null;
        }
    }
    /**
     * 查询处理人
     * @param pageIndex
     * @param pageSize
     * @param productLine
     * @param dataSource
     * @param opUser
     * @param evenType
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> GetOpUserName (Integer pageIndex,Integer pageSize,Integer productLine, String dataSource,
                                       String opUser, Integer evenType,
                                       Date startTime ,Date endTime) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT  opUserName  From notice_complain_info ");
            builder.combine(" where 1=1 ");
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine(evenType != null, " and EnvenType=?", Types.INTEGER, evenType);
            builder.combine(dataSource != null&&dataSource.equals(""), String.format(" AND dataSource IN (%s) ", dataSource));
            builder.combine(opUser != null&&opUser.equals(""), " and opUserName=?", Types.VARCHAR, opUser);
            builder.combine(startTime != null, " and opTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and opTime<?", Types.DATE, endTime);
            builder.combine(" group by opUserName ");
            builder.combinePageLimit(pageIndex > 0 && pageSize > 0, pageIndex, pageSize);

            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, String.class);

        } catch (Exception ex) {
            CLogger.error("dao:GetOpUserName", ex);
            return null;
        }
    }

    /**
     * 查询通知的总条数
     * @param pageIndex
     * @param pageSize
     * @param productLine
     * @param dataSource
     * @param opUser
     * @param evenType
     * @param startTime
     * @param endTime
     * @return
     */
    public int countNotices(Integer pageIndex,Integer pageSize,Integer productLine, String dataSource,
                            String opUser, Integer evenType,
                            Date startTime ,Date endTime){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT COUNT(DISTINCT opUserName) FROM notice_complain_info");
            builder.combine(" where 1=1 ");
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine(evenType != null, " and EnvenType=?", Types.INTEGER, evenType);
            builder.combine(dataSource != null&&dataSource.equals(""), String.format(" AND dataSource IN (%s) ", dataSource));
            builder.combine(opUser != null&&opUser.equals(""), " and opUserName=?", Types.VARCHAR, opUser);
            builder.combine(startTime != null, " and opTime>?", Types.DATE, startTime);
            builder.combine(endTime != null, " and opTime<?", Types.DATE, endTime);
            DalHints hints = new DalHints();
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:countNotices", ex);
            return 0;
        }
    }

    /**
     * select date_format(n.EnterDate, '%Y-%m-%d') as Date,s1.Field_Name Name1,s2.Field_Name Name2,count(orderid) Total
     from notice_complain_info n
     left join scm_small_enum s1 on n.ComplainFirstType=s1.Tid
     left join scm_small_enum s2 on n.ComplainSecondType=s2.Tid
     where envenType=2
     and Product_Line in(1,3,32)
     group by EnterDate,s1.Field_Name,s2.Field_Name
     order by EnterDate desc,count(orderid) desc
     * @return
     */

    public List<ComplainReport> GetComplainList(Integer productLine, Integer channel, Date startDate, Date endDate, Integer envenType,
                                                Integer pageIndex, Integer pageSize){
        try{
            DalUtils.Builder builder=DalUtils.createBuilder();
            builder.combine(" select aaa.*,sumcount.allnum ,   FORMAT(aaa.numbyType/sumcount.allnum*100 ,2) as percentage   from ");
            builder.combine("   (select date_format(n.EnterDate, '%Y-%m-%d') as enterDate,s1.Field_Name as complainType,n.Product_Line as productLine,count(1) as numByType from notice_complain_info n ");
//            if(envenType==2)
//                builder.combine(" left join scm_small_enum s1 on  n.ComplainSecondType=s1.Tid where 1=1 ");
//            else {
                builder.combine(" left join scm_small_enum s1 on  n.NoticeType=s1.Tid where 1=1 ");
//            }
            builder.combine(envenType != null, " and EnvenType=?", Types.INTEGER, envenType);
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine( channel!= null, " and DataSource=?", Types.INTEGER, channel);
            builder.combine(startDate != null, " and enterDate>=?", Types.DATE, startDate);
            builder.combine(endDate != null, " and enterDate<=?", Types.DATE, endDate);
            builder.combine(" group by date_format(n.EnterDate, '%Y-%m-%d'),s1.Field_Name) aaa");
            builder.combine(" inner join (select  date_format(a.EnterDate, '%Y-%m-%d') as enterDate ,Count(1) as allnum from notice_complain_info a where 1=1 ");
            builder.combine(envenType != null, " and EnvenType=?", Types.INTEGER, envenType);
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine( channel!= null, " and DataSource=?", Types.INTEGER, channel);
            builder.combine("group by  date_format(a.EnterDate, '%Y-%m-%d') ) sumcount ");
            builder.combine("  on aaa.enterDate=sumcount.enterDate");
            if(pageIndex != -1 && pageSize != -1)
                builder.combinePageLimit(pageIndex > 0 && pageSize > 0, pageIndex, pageSize);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints,ComplainReport.class);
        } catch (SQLException ex) {
            CLogger.error("dao:GetComplainList", ex);
            return null;
        }

    }

    public int GetComplainCount(Integer productLine, Integer channel, Date startDate,Date endDate,Integer envenType){
        try{
            DalUtils.Builder builder=DalUtils.createBuilder();
            builder.combine(" select count(1) from ");
            builder.combine("   (select date_format(n.EnterDate, '%Y-%m-%d') as enterDate,s1.Field_Name as complainType,n.Product_Line as productLine,count(1) as numByType from notice_complain_info n ");
            if(envenType==2)
                builder.combine(" left join scm_small_enum s1 on  n.ComplainSecondType=s1.Tid where 1=1 ");
            else {
                builder.combine(" left join scm_small_enum s1 on  n.NoticeType=s1.Tid where 1=1 ");
            }
            builder.combine( " and EnvenType=?", Types.INTEGER, envenType);
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine( channel!= null, " and DataSource=?", Types.INTEGER, channel);
            builder.combine(startDate != null, " and enterDate>=?", Types.DATE, startDate);
            builder.combine(endDate != null, " and enterDate<=?", Types.DATE, endDate);
            builder.combine(" group by date_format(n.EnterDate, '%Y-%m-%d'),s1.Field_Name) aaa");
            builder.combine(" inner join (select  date_format(a.EnterDate, '%Y-%m-%d') as enterDate ,Count(1) as allnum from notice_complain_info a where 1=1 ");
            builder.combine( " and EnvenType=?", Types.INTEGER, envenType);
            builder.combine(productLine != null, " and Product_Line=?", Types.INTEGER, productLine);
            builder.combine( channel!= null, " and DataSource=?", Types.INTEGER, channel);
            builder.combine("group by  date_format(a.EnterDate, '%Y-%m-%d') ) sumcount ");
            builder.combine("  on aaa.enterDate=sumcount.enterDate");

            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);

            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (SQLException ex) {
            CLogger.error("dao:GetComplainList", ex);
            return 0;
        }

    }


    /**
     * 修改通知为待处理
     * @throws SQLException
     */
    public int updateNoticeState(List<Long> noticeIds,int noticeState) throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine(" UPDATE notice_complain_info")
                .combine("set NoticeState=?",noticeState)
                .combine(noticeState==80," , OpUser=null, OpTime=null" )
                .combine(String.format("WHERE ID IN (%s)",String.join(",",noticeIds.stream().map(String::valueOf).collect(Collectors.toList()))))
                .combine(String.format("limit %s",noticeIds.size()));
        return baseDao.getClient().update(builder.getSql(),builder.getParameters(),new DalHints());
    }

    /**
     * 获取通知枚举数据
     * */
    public List<ScmSmallEnum> GetNoticeTypeEnum(String EnumType) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT * FROM scm_small_enum ");
            builder.combine(" where is_deleted=0 ");
            builder.combine(EnumType != null, " and Field_Type=?", Types.VARCHAR, EnumType);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmSmallEnum.class);
        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeTypeEnum", ex);
            return null;
        }
    }


    /**
     * 获取通知枚举二级分类
     * */
    public List<ScmSmallEnum> GetSecondNoticeTypeEnum(Long noticetype) {
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT * FROM scm_small_enum ");
            builder.combine(" where 1=1  and Is_Deleted='0' ");
            builder.combine(noticetype >0, " and FK_Upper_Tid=?", Types.BIGINT, noticetype);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmSmallEnum.class);
        } catch (Exception ex) {
            CLogger.error("dao:GetNoticeTypeEnum", ex);
            return null;
        }
    }

    /**
     * 修改通知为待处理
     * @throws SQLException
     */
    public int updateNoticeStateById(long id,int noticeState) throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine(" UPDATE notice_complain_info")
                .combine("set NoticeState=?",noticeState)
                .combine("WHERE ID = ?",id);
        return baseDao.getClient().update(builder.getSql(),builder.getParameters(),new DalHints());
    }

    /**
     * 一次性解决明细客服
     * @param request
     * @return
     */
    public List<OnceSolveNotice> searchOncesolveNoticebyOpuser(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("  select  aaa.id,aaa.orderid, aaa.NoticeType, aaa.Product_Line, aaa.envenType from ");
            builder.combine("   (select a.*,b.count,b.opusername from ");
            builder.combine("   (select id, orderid,NoticeType,Product_Line ,envenType,(select opuserNum from notice_complain_info where ID=max(c.ID) limit 1 ) as lastopuserNum ");
            builder.combine("    from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState in(83,94,100) ");
            builder.combine("    and CompleteTime  >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, request.getStartDate());
            builder.combine("       and date_format(CompleteTime  ,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine(" AND opuserNum=? ",Types.VARCHAR, request.getOpUser());
            builder.combine("       group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) a  inner join ");
            builder.combine("         (select id, orderid,NoticeType,Product_Line ,envenType,opuserNum,count(1) count,opusername  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) ");
            builder.combine("                               and CompleteTime >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, request.getStartDate());
            builder.combine("                               and date_format(CompleteTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine(" AND opuserNum=? ",Types.VARCHAR, request.getOpUser());
            builder.combine("                               group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) b");
            builder.combine("         on a.orderid=b.orderid and a.NoticeType=b.NoticeType and a.lastopuserNum=b.opuserNum and a.Product_Line=b.product_Line and a.envenType=b.envenType");
            builder.combine("         where b.count=1  ) aaa inner join (");
            builder.combine("       select id, orderid,NoticeType,Product_Line,envenType,opuserNum,opusername,count(1) aa from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) and ");
            builder.combine("       date_format(CompleteTime,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine(" AND opuserNum=? ",Types.VARCHAR, request.getOpUser());
            builder.combine("       group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) bbb");
            builder.combine("  on aaa.orderid=bbb.orderid and aaa.NoticeType=bbb.NoticeType and aaa.lastopuserNum=bbb.opuserNum and aaa.product_Line=bbb.product_Line and aaa.envenType=bbb.envenType  where 1=1 ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OnceSolveNotice.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOncesolveNoticebyOpuser", ex);
            return null;
        }
    }

    public List<NoticeComplainInfo> searchOpuserAllSolve(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select * from notice_complain_info ");
            builder.combine(" WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) and CompleteTime is  NOT NULL and CompleteTime !='' ");
            builder.combine("       and date_format(CompleteTime  ,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine(request.getOpUser()!=null&&!request.getOpUser().equals("")," AND opuserNum=? ",Types.VARCHAR, request.getOpUser());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeComplainInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOpuserAllSolve", ex);
            return null;
        }
    }

    /**
     * 查询客服处理催过的单子
     * @param startTime
     * @param opUser
     * @return
     */
    public List<NoticeComplainInfo> searchOpuserUrgeNotice(Date startTime,String  opUser){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select * from notice_complain_info where 1=1 ");
//            已解决+暂缓+处理中+交班
            builder.combine("and NoticeState in(83,81,82,102) and Product_Line in (3,31,134,135,137,138) ");
            builder.combine(" and date_format(EnterDate,'%Y-%m-%d')= date_format(?,'%Y-%m-%d') ", Types.DATE, startTime);
            builder.combine(" AND opUser=? ",Types.VARCHAR, opUser);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeComplainInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOpuserAllSolve", ex);
            return null;
        }
    }

    /**
     * 订单一次性解决明细
     * @param request
     * @return
     */
    public List<OnceSolveNotice> searchOncesolveNoticeDetail(RequestOnceSolveReport request){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("  select aaa.* from ( select a.*,b.count from ");
            builder.combine(" (select id,orderid,NoticeType,Product_Line as productLine,envenType");
            builder.combine("  from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState=83 ");

            builder.combine("    and CompleteTime  >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, request.getStartDate());
            builder.combine("       and date_format(CompleteTime  ,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine("     group by orderid,NoticeType,Product_Line,envenType) a  inner join ");
            builder.combine("(select id,orderid,NoticeType,Product_Line as productLine,envenType,count(1) count  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState=83  ");

            builder.combine("    and CompleteTime  >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, request.getStartDate());
            builder.combine("       and date_format(CompleteTime  ,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine("      group by orderid,NoticeType,Product_Line,envenType) b");
            builder.combine("       on a.orderid=b.orderid and a.NoticeType=b.NoticeType  and a.productLine=b.productLine and a.envenType=b.envenType");
            builder.combine("       where b.count=1  )aaa inner join (");
            builder.combine("     select id,orderid,NoticeType,Product_Line,envenType,count(1) aa from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState=83 and ");
            builder.combine(" date_format(CompleteTime  ,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, request.getStartDate());
            builder.combine(request.getProductLine()!=null&&request.getProductLine()!=0,
                    " AND Product_Line=? ",Types.INTEGER, request.getProductLine());
            builder.combine(request.getEnvenType()!=null&&request.getEnvenType()!=0,
                    " AND envenType=? ",Types.INTEGER, request.getEnvenType());
            builder.combine("     group by orderid,NoticeType,Product_Line,envenType) bbb");
            builder.combine("       on aaa.orderid=bbb.orderid and aaa.NoticeType=bbb.NoticeType and aaa.productLine=bbb.product_Line and aaa.envenType=bbb.envenType");

            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, OnceSolveNotice.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchOncesolveNoticebyOpuser", ex);
            return null;
        }
    }


    /**
     * 修改通知为待处理
     * @throws SQLException
     */
    public int updateNoticeStateByNoticeId(long noticeId,int noticeState) throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine(" UPDATE notice_complain_info")
                .combine("set NoticeState=?",noticeState)
                .combine(" ,OpUser=null, OpTime=null " )
                .combine("WHERE ID=?",noticeId);
        return baseDao.getClient().update(builder.getSql(),builder.getParameters(),new DalHints());
    }

    public List<NoticeComplainInfo> searchNoticeByOrderId(String  orderId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select * from notice_complain_info where 1=1  and EnvenType in(1,2,4)");
            builder.combine(StringUtils.isNotBlank(orderId)," and orderId=? ", Types.VARCHAR,orderId);
            builder.combine(" ORDER BY SendTime ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeComplainInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchNoticeByOrderId", ex);
            return null;
        }
    }

    public List<ScmSmallEnum> searchScmSmallEnum(ScmSmallEnum req){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine("SELECT * FROM scm_small_enum  where 1=1 ");
            builder.combine(StringUtils.isNotBlank(req.getFieldType())," and Field_Type=? ", Types.VARCHAR,req.getFieldType());
            builder.combine(StringUtils.isNotBlank(req.getFieldValue())," and Field_Value=? ", Types.VARCHAR,req.getFieldValue());
            builder.combine(req.getFkUpperTid()!=null," and FK_Upper_Tid=? ", Types.VARCHAR,req.getFkUpperTid());
            builder.combine(req.getIsDeleted()!=null," and Is_Deleted=? ", Types.VARCHAR,req.getIsDeleted());
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, ScmSmallEnum.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchScmSmallEnum", ex);
            return null;
        }
    }

    public List<NoticeComplainInfo> search119Notices(String orderId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select * from notice_complain_info where 1=1  and EnvenType=5 ");
            builder.combine(StringUtils.isNotBlank(orderId)," and orderId =? ",Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), hints, NoticeComplainInfo.class);
        } catch (Exception ex) {
            CLogger.error("dao:search119Notices", ex);
            return null;
        }
    }
    public int search119NoticesCount(String orderId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select count(1) from notice_complain_info where 1=1  and EnvenType=5 ");
            builder.combine(StringUtils.isNotBlank(orderId)," and orderId =? ",Types.VARCHAR,orderId);
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:search119Notices", ex);
            return 0;
        }
    }

    public int searchNoticeByOrderIdCount(String  orderId){
        try {
            DalUtils.Builder builder = DalUtils.createBuilder();
            builder.combine(" select count(1) from notice_complain_info where 1=1  and EnvenType in(1,2,4)");
            builder.combine(StringUtils.isNotBlank(orderId)," and orderId=? ", Types.VARCHAR,orderId);
            builder.combine(" ORDER BY SendTime ");
            DalHints hints = new DalHints();
            hints.set(DalHintEnum.allowPartial);
            return baseDao.queryForObject(builder.getSql(), builder.getParameters(), hints, Integer.class);
        } catch (Exception ex) {
            CLogger.error("dao:searchNoticeByOrderIdCount", ex);
            return 0;
        }
    }

}
