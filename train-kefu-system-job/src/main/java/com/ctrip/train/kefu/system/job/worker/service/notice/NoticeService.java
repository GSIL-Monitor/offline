package com.ctrip.train.kefu.system.job.worker.service.notice;

import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtNotice;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtOnceSolve;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtOnceSolveUser;
import com.ctrip.train.kefu.system.job.worker.dao.notice.ExtOperateInfo;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultApportionNotice;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultCollectNotice;
import com.ctrip.train.kefu.system.job.enums.notice.NoticeStateEnum;
import com.ctrip.train.kefu.system.job.worker.entity.notice.resultentity.ResultOnceNotice;
import common.log.CLogger;
import common.util.DateUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeOnceSolve;
import dao.ctrip.ctrainpps.entity.NoticeOnceSolveUser;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoticeService {

    @Autowired
    private ExtNotice notices;

    @Autowired
    private ExtOnceSolve extOnceSolve;

    @Autowired
    private ExtOnceSolveUser extOnceSolveUser;

    @Autowired
    private ExtOperateInfo extOperateInfo;

    /**
     * 获取到预约时间的通知
     * @param min
     */
    public List<NoticeComplainInfo>  getAppointedNotice(Integer min) {
        try {
            List<Integer> noticeStates=new ArrayList<>();
            noticeStates.add(NoticeStateEnum.Assigned.getState());
            noticeStates.add(NoticeStateEnum.Deferred.getState());
            String startAppointedTime=DateUtils.format(DateUtils.currentTime(),DateUtils.YMDHMS_UNDERLINED);
            String endAppointedTime=DateUtils.format(DateUtils.addMinutes(DateUtils.currentTime(),min),DateUtils.YMDHMS_UNDERLINED);
            List<NoticeComplainInfo> lst=notices.getNotices("",noticeStates,startAppointedTime ,endAppointedTime);
            return  lst;
        }
        catch (Exception ex)
        {
            CLogger.error("getAppointedNotice",ex);
            return  null;
        }
    }

    /**
     * 获取交班通知的通知
     * @param day
     */
    public List<NoticeComplainInfo>  getDutyChangedNotice(Integer day) {
        try {
            List<Integer> noticeStates=new ArrayList<>();
            noticeStates.add(NoticeStateEnum.ChangeDuty.getState());

            //查七天的数据，保证所有预约通知被拉
            String startAppointedTime=DateUtils.format(DateUtils.addDays(DateUtils.currentTime(),-7*day),DateUtils.YMDHMS_UNDERLINED);
            String endAppointedTime=DateUtils.format(DateUtils.currentTime(),DateUtils.YMDHMS_UNDERLINED);
            List<NoticeComplainInfo> lst=notices.getNotices("",noticeStates,startAppointedTime ,endAppointedTime);
            return  lst;
        }
        catch (Exception ex)
        {
            CLogger.error("getDutyChangedNotice",ex);
            return  null;
        }
    }

    /**
     * 批量更新通知状态
     * @param noticeIds
     * @return
     */
    public int updateNoticeStates(List<Long> noticeIds,NoticeStateEnum noticeStateEnum) {
        if(noticeIds!=null&&noticeIds.size()>0&&noticeStateEnum!=null){
            try {
                return notices.updateNoticeState(noticeIds,noticeStateEnum.getState());
            } catch (SQLException e) {
                CLogger.error("updateNoticeState",e);
                return -1;
            }
        }
        return 0;
    }

    /**
     * 查询所有待分配的订单
     * @return
     */
    public List<ResultApportionNotice> searchApportionNoticeByWorker(){
        try {
            //有预约时间的
            List<ResultApportionNotice> apportionNotices= notices.searchApportionNoticeByWorker();
            //没有预约时间
            List<ResultApportionNotice> anotices= notices.searchApportionNotice();
            if(apportionNotices!=null&&apportionNotices.size()!=0){
                if(anotices!=null&&anotices.size()!=0){
                    apportionNotices.addAll(anotices);
                }
                return apportionNotices;
            }else {
                if(anotices!=null&&anotices.size()!=0)
                    return anotices;
            }
            return null;
        } catch (Exception e){
            CLogger.error("searchApportionNoticeByWorker",e);
            return  null;
        }
    }

    /**
     * 按人员统计通知当天客服处理的通知量
     * @return
     */
    public List<ResultCollectNotice> searchCollectNotice(){
        try {
            List<NoticeComplainInfo> noticeList=notices.searchNoticesforCount();
            if(noticeList!=null&&noticeList.size()>0){
                Map<String, List<NoticeComplainInfo>> noticeMap =noticeList.stream().collect(Collectors.groupingBy(NoticeComplainInfo::getOpUserNum));
                List<ResultCollectNotice> returnList=new ArrayList<>();
                for (Map.Entry<String, List<NoticeComplainInfo>> itemFather:noticeMap.entrySet()) {

                    long solveingNoticeCount=0;
                    long deferNoticeCount=0;
                    long solvedNoticeCount=0;
                    long waitNoticeCount=0;

                    ResultCollectNotice temp=new ResultCollectNotice();
                    temp.setOpUserNum(itemFather.getKey());
                    if(itemFather.getValue()!=null&&itemFather.getValue().size()>0){
                        solveingNoticeCount=itemFather.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Assigned.getState()).count();
                        deferNoticeCount=itemFather.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Deferred.getState()).count();
                        solvedNoticeCount=itemFather.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Solved.getState()).count();
                        waitNoticeCount=itemFather.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.WaitDeal.getState()).count();

                        temp.setOpUser(itemFather.getValue().get(0).getOpUser());
                        temp.setOpuserName(itemFather.getValue().get(0).getOpUserName());
                    }
                    temp.setSolveingNoticeCount(solveingNoticeCount);
                    temp.setDeferNoticeCount(deferNoticeCount);
                    temp.setSolvedNoticeCount(solvedNoticeCount);
                    temp.setWaitNoticeCount(waitNoticeCount);

                    //解决能力
                    long solveAbility=itemFather.getValue().stream().filter(o->o.getNoticeState()==94
                            ||o.getNoticeState()==100 ||o.getNoticeState()==104).count()+solveingNoticeCount+deferNoticeCount+solvedNoticeCount;
                    temp.setSolveAbility(solveAbility);
                    returnList.add(temp);
                }
                //            return notices.searchCollectNotice();
                return returnList;
            }
            return  null;
        } catch (Exception e){
            CLogger.error("searchCollectNotice",e);
            return  null;
        }
    }

    /**
     * 查询当天已分配
     * @return
     */
    public List<NoticeComplainInfo> searchNoticeNotWait(){
        try {
            return notices.searchNoticeNotWait();
        } catch (Exception e){
            CLogger.error("searchNoticeNotWait",e);
            return  null;
        }
    }
    /**
     * 分配通知
     */
    public int updatePulledNoticeById(String opUserName,String opUserNum, long noticeId){
        try {
            return notices.updatePulledNoticeById(opUserName,opUserNum,noticeId);
        } catch (Exception e){
            CLogger.error("searchNoticeNotWait",e);
            return  0;
        }
    }

    /**
     * 一次性解决率按日汇总
     */
    public void queryOnceSolveNotice(){
        try {
            Calendar c = Calendar.getInstance();
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            //开始时间必须小于结束时间
//            Date beginDate = dateFormat1.parse("2018-01-01");
            Date beginDate = DateUtils.addDays(dateFormat1.parse(notices.searchLastDateOnceSovle()),1);
            Date endDate =  DateUtils.parseDate(dateFormat1.format(c.getTime()),DateUtils.YMD_UNDERLINED);
            Date date = beginDate;
            while (!date.equals(endDate)) {
//                SimpleDateFormat sdf1 = new SimpleDateFormat(DateUtils.YMDHMS_UNDERLINED);
                List<ResultOnceNotice> noticesList=notices.searchOnceSovleNoticeCount(date);
                List<ResultOnceNotice> noticesUserList=notices.searchOnceSovleNoticeUserCount(date);
                //订单一次性解决lv
                if(noticesList!=null&&noticesList.size()!=0){
                    List<NoticeOnceSolve> rns=new ArrayList<>();
                    for(ResultOnceNotice ron:noticesList){
                        NoticeOnceSolve model=new NoticeOnceSolve();
                        //跑数据更改
                        model.setOpTime(new Timestamp(date.getTime()));
                        model.setOpuserName(ron.getOpuserName());
                        model.setOpuserNum(ron.getOpUserNum());
                        model.setEnvenType(ron.getEnvenType());
                        model.setProductLine(Integer.valueOf(ron.getProductLine()));
                        model.setOncesovle(ron.getOncesovle());
                        model.setAllnum(ron.getAllsovle());
                        model.setPercentage(Float.valueOf(ron.getPercentage()));
                        rns.add(model);
                    }
                    //清表
//                    extOnceSolve.delectOnceSolve(date);
                    int[] result=extOnceSolve.insert(rns);
                    if (result.length!=rns.size())
                        CLogger.error("insertExtOnceSolve","数据落地时有丢失");
                }
                //员工一次性解决lv
                if(noticesUserList!=null&&noticesUserList.size()!=0){
                    List<NoticeOnceSolveUser> rnu=new ArrayList<>();
                    for(ResultOnceNotice ron:noticesUserList){
                        NoticeOnceSolveUser model=new NoticeOnceSolveUser();
                        //跑数据更改
                        model.setOpTime(new Timestamp(date.getTime()));
                        model.setOpuserName(ron.getOpuserName());
                        model.setOpuserNum(ron.getOpUserNum());
                        model.setEnvenType(ron.getEnvenType());
                        model.setProductLine(Integer.valueOf(ron.getProductLine()));
                        model.setOncesovle(ron.getOncesovle());
                        model.setAllnum(ron.getAllsovle());
                        model.setPercentage(Float.valueOf(ron.getPercentage()));
                        rnu.add(model);
                    }
                    //清表
//                    extOnceSolveUser.delectOnceSolveUser(date);
                    int[] result=extOnceSolveUser.insert(rnu);
                    if (result.length!=rnu.size())
                        CLogger.error("insertExtOnceSolveUser","数据落地时有丢失");
                }
                c.setTime(date);
                c.add(Calendar.DATE, 1); // 日期加1天
                date = c.getTime();
            }
        } catch (Exception e) {
            CLogger.error("insertExtOnceSolve",e);
        }
    }

    public List<ResultCollectNotice> searchCollectNoticeGroup(){
        try {
            List<NoticeComplainInfo> noticeList=notices.searchNoticesforCount();
            if (noticeList!=null&&noticeList.size()>0){
                Map<String, Map<Integer, List<NoticeComplainInfo>>> noticeMap =noticeList.stream()
                        .collect(Collectors.groupingBy(NoticeComplainInfo::getOpUserNum,Collectors.groupingBy(NoticeComplainInfo::getEnvenType)));

                List<ResultCollectNotice> returnList=new ArrayList<>();
                for (Map.Entry<String, Map<Integer, List<NoticeComplainInfo>>> itemFather:noticeMap.entrySet()) {
                    for (Map.Entry<Integer, List<NoticeComplainInfo>> itemSon: itemFather.getValue().entrySet()) {
                        ResultCollectNotice temp=new ResultCollectNotice();
                        temp.setOpUserNum(itemFather.getKey());
                        temp.setEnvenType(itemSon.getKey());

                        long solveingNoticeCount=0;
                        long deferNoticeCount=0;
                        long solvedNoticeCount=0;
                        long waitNoticeCount=0;
                        if(itemSon.getValue()!=null&&itemSon.getValue().size()>0){
                            temp.setOpUser(itemSon.getValue().get(0).getOpUser());
                            temp.setOpuserName(itemSon.getValue().get(0).getOpUserName());
                            solveingNoticeCount=itemSon.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Assigned.getState()).count();
                            deferNoticeCount=itemSon.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Deferred.getState()).count();
                            solvedNoticeCount=itemSon.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.Solved.getState()).count();
                            waitNoticeCount=itemSon.getValue().stream().filter(o->o.getNoticeState()==NoticeStateEnum.WaitDeal.getState()).count();
                        }
                        temp.setSolveingNoticeCount(solveingNoticeCount);
                        temp.setDeferNoticeCount(deferNoticeCount);
                        temp.setSolvedNoticeCount(solvedNoticeCount);
                        temp.setWaitNoticeCount(waitNoticeCount);

                        returnList.add(temp);
                    }
                }
                return returnList;
            }
            return null;
        } catch (Exception e){
            CLogger.error("searchCollectNotice",e);
            return  null;
        }
    }

    /**
     * 查询半小时还分配的通知
     * @return
     */
    public List<NoticeComplainInfo> searchNoticeAssigned(){
        try {
            Date time=DateUtils.addMinutes( new Date(),-30);
            List<NoticeComplainInfo> list = notices.searchNoticeAssigned(time);
            //交班的以交班时间判断
            List<NoticeComplainInfo> listChange = notices.searchNoticeAssignedChange(time);
            if (list!=null&&list.size()>0){
                if (listChange!=null&&listChange.size()>0)
                    list.addAll(listChange);
                return list;
            }else {
                if (listChange!=null&&listChange.size()>0)
                    return listChange;
            }
            return null;
        } catch (SQLException e) {
            CLogger.error("searchNoticeAssigned",e);
            return null;
        }
    }

    /**
     * 添加备注
     */
    public int insertOperateInfo(OperateInfo operateInfo){
        try {
            return extOperateInfo.insert(operateInfo);
        } catch (SQLException e) {
            CLogger.error("insertOperateInfo",e);
            return 0;
        }
    }

}
