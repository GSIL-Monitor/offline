package com.ctrip.train.kefu.system.job.worker.dao.notice;

import com.ctrip.platform.dal.dao.DalHintEnum;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalQueryDao;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultApportionNotice;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultCollectNotice;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultOnceNotice;
import common.constants.DatabaseName;
import common.log.CLogger;
import common.util.DalUtils;
import common.util.DateUtils;
import common.util.StringUtils;
import dao.ctrip.ctrainpps.dao.NoticeComplainInfoDao;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExtNotice extends NoticeComplainInfoDao {
    protected DalQueryDao baseDao;
    public ExtNotice() throws SQLException {
        super();
        baseDao=new DalQueryDao(DatabaseName.CTRAIN_PPS_DB);
    }


    /**
     * 获取通知数据
     * @throws SQLException
     */
    public List<NoticeComplainInfo> getNotices(String orderId,List<Integer> noticeStates,
                                               String startAppointedTime, String endAppointedTime )throws SQLException {
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("WHERE n.Product_Line in(3,31,134,135,136,137,138)  and EnvenType in(1,2,4)")
                .combine(StringUtils.isNotEmpty(startAppointedTime), "and n.AppointedProcessTime>= ?", startAppointedTime)
                .combine(StringUtils.isNotEmpty(endAppointedTime), "AND n.AppointedProcessTime< ?", endAppointedTime)
                .combine(StringUtils.isNotEmpty(orderId), "AND n.OrderID= ? ", orderId)
                .combineIn(noticeStates != null && noticeStates.size() > 0,
                        "AND n.NoticeState in(?)", Types.INTEGER, noticeStates);
        builder.insertFirst("SELECT * FROM notice_complain_info n");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, NoticeComplainInfo.class);
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
     * 获取待分配的通知信息 有预约时间的
     *
     *(SELECT ID as id,noticeType as noticeType,Product_Line as productLine,EnvenType as envenType,orderId,opuser,AppointedProcessTime as operateTime,NoticeState,DatachangeLastTime,sendtime
     FROM notice_complain_info n
     WHERE DatachangeLastTime <= date_format(now(),'%y-%m-%d')
     AND
     NoticeState=80
     and Product_Line in(3,31,134,135,137)
     -- and Product_Line in(31,135)
     and EnvenType in (1,2)
     and AppointedProcessTime is  NOT NULL AND AppointedProcessTime != '0001-01-01'
     ORDER BY AppointedProcessTime, EmergeState DESC,OpCount DESC ,SendTime ASC) union all
     (SELECT ID as id,noticeType as noticeType,Product_Line as productLine,EnvenType as envenType,orderId,opuser,AppointedProcessTime as operateTime,NoticeState,DatachangeLastTime,sendtime
     FROM notice_complain_info n
     WHERE  DatachangeLastTime <= date_format(now(),'%y-%m-%d')
     AND
     NoticeState=80
     and Product_Line in(3,31,134,135,137)
     -- and Product_Line in(31,135)
     and EnvenType in (1,2)
     and (AppointedProcessTime IS NULL OR AppointedProcessTime='0001-01-01')
     ORDER BY AppointedProcessTime, EmergeState DESC,OpCount DESC ,SendTime ASC)
     *
     *
     *
     * @return
     */
    public List<ResultApportionNotice> searchApportionNoticeByWorker() throws SQLException{
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("SELECT ID as id,noticeType as noticeType,Product_Line as productLine,EnvenType as envenType,orderId,opuser,AppointedProcessTime as operateTime,NoticeState\n" +
                "       FROM notice_complain_info n\n" +
                "       WHERE NoticeState=80\n" +
                "    and Product_Line in(31,134,135,137,138)\n" +
                "    and EnvenType in (1,2)\n" +
                "    and AppointedProcessTime is  NOT NULL AND AppointedProcessTime != '0001-01-01'\n" +
                "       ORDER BY AppointedProcessTime, EmergeState DESC,OpCount DESC ,SendTime ASC");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultApportionNotice.class);
    }
    /**
     * 获取待分配的通知信息 么有预约时间的
     * @return
     */
    public List<ResultApportionNotice> searchApportionNotice() throws SQLException{
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("SELECT ID as id,noticeType as noticeType,Product_Line as productLine,EnvenType as envenType,orderId,opuser,AppointedProcessTime as operateTime,NoticeState\n" +
                "       FROM notice_complain_info n\n" +
                "       WHERE NoticeState=80\n" +
                "    and Product_Line in(31,134,135,137,138)\n" +
                "    and EnvenType in (1,2)\n" +
                "    and (AppointedProcessTime IS NULL OR AppointedProcessTime='0001-01-01')\n" +
                "       ORDER BY EmergeState DESC,OpCount DESC ,SendTime ASC");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultApportionNotice.class);
    }


    /**
     * 按人员统计通知当天客服处理的通知量
     * @return
     * @throws SQLException
     */
    public List<ResultCollectNotice> searchCollectNotice() throws SQLException{
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("   SELECT opUserName,opUser,opUserNum\n" +
                "     ,count(CASE NoticeState=84 WHEN TRUE THEN 1 ELSE NULL END ) as waitNoticeCount\n" +
                "     ,count(CASE NoticeState=82 WHEN TRUE THEN 1 ELSE NULL END ) as deferNoticeCount \n" +
                "     ,count(CASE NoticeState=83 WHEN TRUE THEN 1 ELSE NULL END ) as solvedNoticeCount\n" +
                "     ,count(CASE NoticeState=81 WHEN TRUE THEN 1 ELSE NULL END ) as solveingNoticeCount\n" +

                "     ,count(CASE NoticeState=81 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     +count(CASE NoticeState=82 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     +count(CASE NoticeState=83 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     +count(CASE NoticeState=94 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     +count(CASE NoticeState=100 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     +count(CASE NoticeState=102 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "     as solveAbility\n" +
                "  from notice_complain_info\n" +
                "  WHERE date_format(optime,'%y-%m-%d')=date_format(now(),'%y-%m-%d')\n" +
                "  and opUser IS NOT NULL and opUserNum!=''\n" +
                "     group by opUserNum ");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultCollectNotice.class);
    }
    //只统计通知投诉
    public List<ResultCollectNotice> searchCollectNoticeGroup() throws SQLException{
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("  SELECT opUserName,opUser,opUserNum,envenType\n" +
                "                     ,count(CASE NoticeState=84 WHEN TRUE THEN 1 ELSE NULL END ) as waitNoticeCount\n" +
                "                     ,count(CASE NoticeState=82 WHEN TRUE THEN 1 ELSE NULL END ) as deferNoticeCount \n" +
                "                     ,count(CASE NoticeState=83 WHEN TRUE THEN 1 ELSE NULL END ) as solvedNoticeCount\n" +
                "                     ,count(CASE NoticeState=81 WHEN TRUE THEN 1 ELSE NULL END ) as solveingNoticeCount\n" +
                "                     ,count(CASE NoticeState=81 WHEN TRUE THEN 1 ELSE NULL END )\n" +
                "                     +count(CASE NoticeState=82 WHEN TRUE THEN 1 ELSE NULL END )\n" +
                "                     +count(CASE NoticeState=83 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "                     +count(CASE NoticeState=94 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "                     +count(CASE NoticeState=100 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "                     +count(CASE NoticeState=102 WHEN TRUE THEN 1 ELSE NULL END ) \n" +
                "                     as solveAbility\n" +
                "                  from notice_complain_info\n" +
                "                  WHERE date_format(optime,'%y-%m-%d')=date_format(now(),'%y-%m-%d')\n" +
                "                  and opUser IS NOT NULL and opUserNum!='' and EnvenType in(1,2)\n" +
                "        group by opUserNum ,EnvenType ");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultCollectNotice.class);
    }

    /**
     * 查询已分配的订单
     * @return
     * @throws SQLException
     */
    public List<NoticeComplainInfo> searchNoticeNotWait() throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("  select * from notice_complain_info where dataChangeLastTime >= date_format(now(),'%y-%m-%d') and NoticeState !=80");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, NoticeComplainInfo.class);

    }

    /**
     * 分配订单
     * @param opUserName
     * @param opUserNum
     * @param noticeId
     * @return
     * @throws SQLException
     */
    public int updatePulledNoticeById(String opUserName,String opUserNum, long noticeId) throws SQLException{

        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("UPDATE notice_complain_info")
                .combine("SET NoticeState=84,OpUser=?,OpTime=now()", String.format("%s(%s)",opUserNum,opUserName))
                .combine(",opUserName=?", opUserName)
                .combine(",opUserNum=?",opUserNum)
//                .combine("WHERE NoticeState=80 and (OpUser is null or  OpUser='') and id=?", noticeId);
                .combine("WHERE NoticeState=80 and id=?", noticeId);
        return baseDao.getClient().update(builder.getSql(), builder.getParameters(), new DalHints());
    }

    /**
     * 统计一次性解决率
     *
     *
     * select onceSolve.lastopuserNum as opUserNum,onceSolve.opUsername as opUserName,onceSolve.productLine as productLine,onceSolve.envenType,onceSolve.oncesovle as oncesovle ,allSolve.allNum  as allsovle ,FORMAT(onceSolve.oncesovle/allSolve.allNum*100 ,2) as percentage
     from   (
     select lastopuserNum,opusername,productLine,envenType,count(1) oncesovle from
     (select a.*,b.count,b.opusername from
     (select orderid,NoticeSecondType,Product_Line as productLine,envenType,(select opuserNum from notice_complain_info where ID=max(c.ID) limit 1 ) as lastopuserNum
     from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState=83
     and opTime>= DATE_ADD(date_format("2018-08-07",'%Y-%m-%d'),INTERVAL -3 DAY)
     and date_format(opTime,'%Y-%m-%d')= date_format("2018-08-07",'%Y-%m-%d')
     group by orderid,NoticeSecondType,Product_Line,envenType) a  inner join
     (select orderid,NoticeSecondType,Product_Line as productLine,envenType,opuserNum,count(1) count,opusername  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState=83
     and opTime>= DATE_ADD(date_format("2018-08-07",'%Y-%m-%d'),INTERVAL -3 DAY)
     and date_format(opTime,'%Y-%m-%d')= date_format("2018-08-07",'%Y-%m-%d')
     group by orderid,NoticeSecondType,Product_Line,envenType,opuserNum,opusername) b
     on a.orderid=b.orderid and a.NoticeSecondType=b.NoticeSecondType and a.lastopuserNum=b.opuserNum and a.productLine=b.productLine and a.envenType=b.envenType
     where b.count=1 and a.orderId in (
     select orderId from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100)
     -- and opTime>= DATE_ADD(date_format("2018-08-07",'%Y-%m-%d'),INTERVAL -3 DAY)
     and date_format(opTime,'%Y-%m-%d')= date_format("2018-08-07",'%Y-%m-%d')
     group by orderid,NoticeSecondType,Product_Line,envenType,opuserNum,opusername)
     ) aa group by lastopuserNum,productLine,envenType,opusername) onceSolve inner join (
     select opuserNum,opusername,count(1) allNum,Product_Line as productLine,envenType from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100)
     -- and opTime>= DATE_ADD(date_format("2018-08-07",'%Y-%m-%d'),INTERVAL -3 DAY)
     and date_format(opTime,'%Y-%m-%d')= date_format("2018-08-07",'%Y-%m-%d')
     group by opuserNum,Product_Line,envenType,opusername
     ) allSolve on onceSolve.lastopuserNum=allSolve.opuserNum  and onceSolve.productLine=allSolve.productLine  and onceSolve.envenType=allSolve.envenType
     * @return
     */
    public List<ResultOnceNotice> searchOnceSovleNoticeCount(Date startTime) throws SQLException{
        DalUtils.Builder builder = DalUtils.createBuilder();

        builder.combine(" select ? as opTime, onceSolve.productLine as productLine,onceSolve.envenType,onceSolve.oncesovle as oncesovle ,allSolve.allNum ",Types.DATE, startTime);
        builder.combine(" as allsovle ,FORMAT(onceSolve.oncesovle/allSolve.allNum*100 ,2) as percentage from ( ");
        builder.combine(" select productLine,envenType,count(1) oncesovle from (      ");
        builder.combine(" select aaa.* from ( select a.*,b.count from ");
        builder.combine("  (select orderid,NoticeType,Product_Line as productLine,envenType");
        builder.combine("   from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState=83 ");
        builder.combine("      and CompleteTime>= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, startTime);
        builder.combine("         and date_format(CompleteTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("      group by orderid,NoticeType,Product_Line,envenType) a  inner join ");
        builder.combine(" (select orderid,NoticeType,Product_Line as productLine,envenType,count(1) count  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState=83        ");
        builder.combine("      and CompleteTime>= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, startTime);
        builder.combine("         and date_format(CompleteTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("       group by orderid,NoticeType,Product_Line,envenType) b");
        builder.combine("        on a.orderid=b.orderid and a.NoticeType=b.NoticeType  and a.productLine=b.productLine and a.envenType=b.envenType");
        builder.combine("        where b.count=1  )aaa inner join (");
        builder.combine("      select orderid,NoticeType,Product_Line,envenType,count(1) aa from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState=83 and ");
        builder.combine("   date_format(CompleteTime,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("      group by orderid,NoticeType,Product_Line,envenType) bbb");
        builder.combine("        on aaa.orderid=bbb.orderid and aaa.NoticeType=bbb.NoticeType and aaa.productLine=bbb.product_Line and aaa.envenType=bbb.envenType) ccc");
        builder.combine("        group by productLine,envenType ) onceSolve inner join ");
        builder.combine(" (select count(1) allNum,Product_Line as productLine,envenType from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState =83 ");
        builder.combine("  and date_format(CompleteTime,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine(" group by Product_Line,envenType) ");
        builder.combine(" allSolve on  onceSolve.productLine=allSolve.productLine  and onceSolve.envenType=allSolve.envenType");

        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultOnceNotice.class);
    }
    /**
     * select date_format(optime,'%Y-%m-%d') as optime from notice_once_solve order by optime DESC limit 1
     * 最后一次统计时间
     */
    public String searchLastDateOnceSovle() throws SQLException{
        DalUtils.Builder builder = DalUtils.createBuilder();
        builder.combine("select date_format(optime,'%Y-%m-%d') as optime from notice_once_solve order by optime DESC limit 1");
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.queryForObject(builder.getSql(), builder.getParameters(), dalHints, String.class);
    }
    /*
              select  now() as opTime,onceSolve.lastopuserNum as opUserNum,onceSolve.opUsername as opUserName,onceSolve.productLine as productLine,onceSolve.envenType,onceSolve.oncesovle as oncesovle ,allSolve.allNum
          as allsovle ,FORMAT(onceSolve.oncesovle/allSolve.allNum*100 ,2) as percentage from (
            select lastopuserNum,opusername,productLine,envenType,count(1) oncesovle from (select aaa.* from
            (select a.*,b.count,b.opusername from
            (select orderid,NoticeType,Product_Line as productLine,envenType,(select opuserNum from notice_complain_info where ID=max(c.ID) limit 1 ) as lastopuserNum
             from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState in(83,94,100)
             and CompleteTime  >= DATE_ADD(date_format(now(),'%Y-%m-%d'),INTERVAL -3 DAY)
                and date_format(CompleteTime  ,'%Y-%m-%d')<= date_format(now(),'%Y-%m-%d')
                group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) a  inner join
                  (select orderid,NoticeType,Product_Line as productLine,envenType,opuserNum,count(1) count,opusername  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100)
                                        and CompleteTime >= DATE_ADD(date_format(now(),'%Y-%m-%d'),INTERVAL -3 DAY)
                                        and date_format(CompleteTime,'%Y-%m-%d')<= date_format(now(),'%Y-%m-%d')
                                        group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) b
                  on a.orderid=b.orderid and a.NoticeType=b.NoticeType and a.lastopuserNum=b.opuserNum and a.productLine=b.productLine and a.envenType=b.envenType
                  where b.count=1  ) aaa inner join (
                select orderid,NoticeType,Product_Line,envenType,opuserNum,opusername,count(1) aa from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) and
                date_format(CompleteTime,'%Y-%m-%d')= date_format(now(),'%Y-%m-%d')
                group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) bbb
                  on aaa.orderid=bbb.orderid and aaa.NoticeType=bbb.NoticeType and aaa.lastopuserNum=bbb.opuserNum and aaa.productLine=bbb.product_Line and aaa.envenType=bbb.envenType) ccc
                  group by lastopuserNum,productLine,envenType,opusername ) onceSolve inner join
         (select opuserNum,opusername,count(1) allNum,Product_Line as productLine,envenType from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100)
         and date_format(CompleteTime,'%Y-%m-%d')= date_format(now(),'%Y-%m-%d')
         group by opuserNum,Product_Line,envenType,opusername)
         allSolve on onceSolve.lastopuserNum=allSolve.opuserNum  and onceSolve.productLine=allSolve.productLine  and onceSolve.envenType=allSolve.envenType
     */
    public List<ResultOnceNotice> searchOnceSovleNoticeUserCount(Date startTime) throws SQLException{
        DalUtils.Builder builder = DalUtils.createBuilder();

        builder.combine("   select  ? as opTime,onceSolve.lastopuserNum as opUserNum,onceSolve.opUsername as opUserName,onceSolve.productLine as productLine,onceSolve.envenType,onceSolve.oncesovle as oncesovle ,allSolve.allNum ",Types.DATE, startTime);
        builder.combine("   as allsovle ,FORMAT(onceSolve.oncesovle/allSolve.allNum*100 ,2) as percentage from ( ");
        builder.combine("     select lastopuserNum,opusername,productLine,envenType,count(1) oncesovle from (select aaa.* from ");
        builder.combine("     (select a.*,b.count,b.opusername from ");
        builder.combine("     (select orderid,NoticeType,Product_Line as productLine,envenType,(select opuserNum from notice_complain_info where ID=max(c.ID) limit 1 ) as lastopuserNum ");
        builder.combine("      from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !=''and NoticeState in(83,94,100) ");
        builder.combine("      and CompleteTime  >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, startTime);
        builder.combine("         and date_format(CompleteTime  ,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("         group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) a  inner join ");
        builder.combine("           (select orderid,NoticeType,Product_Line as productLine,envenType,opuserNum,count(1) count,opusername  from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) ");
        builder.combine("                                 and CompleteTime >= DATE_ADD(date_format(?,'%Y-%m-%d'),INTERVAL -3 DAY)",Types.DATE, startTime);
        builder.combine("                                 and date_format(CompleteTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("                                 group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) b");
        builder.combine("           on a.orderid=b.orderid and a.NoticeType=b.NoticeType and a.lastopuserNum=b.opuserNum and a.productLine=b.productLine and a.envenType=b.envenType");
        builder.combine("           where b.count=1  ) aaa inner join (");
        builder.combine("         select orderid,NoticeType,Product_Line,envenType,opuserNum,opusername,count(1) aa from notice_complain_info c WHERE opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) and ");
        builder.combine("         date_format(CompleteTime,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("         group by orderid,NoticeType,Product_Line,envenType,opuserNum,opusername) bbb");
        builder.combine("           on aaa.orderid=bbb.orderid and aaa.NoticeType=bbb.NoticeType and aaa.lastopuserNum=bbb.opuserNum and aaa.productLine=bbb.product_Line and aaa.envenType=bbb.envenType) ccc");
        builder.combine("           group by lastopuserNum,productLine,envenType,opusername ) onceSolve inner join ");
        builder.combine("  (select opuserNum,opusername,count(1) allNum,Product_Line as productLine,envenType from notice_complain_info c WHERE   opuserName is  NOT NULL and opuserName !='' and NoticeState in(83,94,100) ");
        builder.combine("  and date_format(CompleteTime,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')",Types.DATE, startTime);
        builder.combine("  group by opuserNum,Product_Line,envenType,opusername) ");
        builder.combine("  allSolve on onceSolve.lastopuserNum=allSolve.opuserNum  and onceSolve.productLine=allSolve.productLine  and onceSolve.envenType=allSolve.envenType");

        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, ResultOnceNotice.class);
    }
    /**
     * 查询待分配的订单 未交班的通知
     * @return
     * @throws SQLException
     */
    public List<NoticeComplainInfo> searchNoticeAssigned(Date startTime) throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("  select * from notice_complain_info where EnvenType in (1,2) and Product_Line in(31,134,135,137,138)");
        builder.combine(" and NoticeState=80 and (ChangeDutyTime IS NULL OR ChangeDutyTime='0001-01-01')   and EnterDate <=?  order by SendTime ",Types.DATE, startTime);
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, NoticeComplainInfo.class);
    }

    /**
     * 查询待分配的订单 交班的通知
     * @return
     * @throws SQLException
     */
    public List<NoticeComplainInfo> searchNoticeAssignedChange(Date startTime) throws SQLException {
        DalUtils.Builder builder=DalUtils.createBuilder();
        builder.combine("  select * from notice_complain_info where EnvenType in (1,2) and Product_Line in(31,134,135,137,138)");
        builder.combine(" and NoticeState=80 and (ChangeDutyTime IS Not NULL OR ChangeDutyTime != '0001-01-01')   and ChangeDutyTime <=?  order by SendTime ",Types.DATE, startTime);
        DalHints dalHints = new DalHints();
        dalHints.set(DalHintEnum.allowPartial);
        return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, NoticeComplainInfo.class);
    }

    public List<NoticeComplainInfo> searchNoticesforCount(){
        DalUtils.Builder builder=DalUtils.createBuilder();
        try {
            builder.combine(" SELECT * from notice_complain_info where opUser IS NOT NULL and opUserNum!='' and  EnvenType in(1,2) ");
            builder.combine(" and DatachangeLastTime between DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s') ",Types.DATE, DateUtils.formatDate(new Date(),DateUtils.YMD_UNDERLINED));
            builder.combine(" and DATE_FORMAT(?,'%Y-%m-%d %H:%i:%s')  ",Types.DATE, DateUtils.formatDate(DateUtils.addDays(new Date(),1),DateUtils.YMD_UNDERLINED));
            DalHints dalHints = new DalHints();
            dalHints.set(DalHintEnum.allowPartial);
            return baseDao.query(builder.getSql(), builder.getParameters(), dalHints, NoticeComplainInfo.class);
        } catch (SQLException e) {
            CLogger.error("searchNoticesforCount",e);
            return null;
        }
    }
}
