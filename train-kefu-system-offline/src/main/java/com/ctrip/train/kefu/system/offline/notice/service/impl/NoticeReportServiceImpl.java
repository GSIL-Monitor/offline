package com.ctrip.train.kefu.system.offline.notice.service.impl;

import com.ctrip.train.kefu.system.offline.notice.dao.ExtNoticeComplainInfo;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtOnceSolveInfo;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtOnceSolveUserInfo;
import com.ctrip.train.kefu.system.offline.notice.dao.ExtOperateInfo;
import com.ctrip.train.kefu.system.offline.notice.domain.*;
import com.ctrip.train.kefu.system.offline.notice.enums.EventTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.NoticeOperateTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.NoticeStateEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeReportService;
import com.ctrip.train.kefu.system.offline.notice.vm.VmComplainReport;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeReport;
import com.ctrip.train.kefu.system.offline.notice.vm.VmOnceSolveReport;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.*;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.ExcelUtils;
import dao.ctrip.ctrainpps.entity.NoticeComplainInfo;
import dao.ctrip.ctrainpps.entity.NoticeOnceSolve;
import dao.ctrip.ctrainpps.entity.NoticeOnceSolveUser;
import dao.ctrip.ctrainpps.entity.OperateInfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoticeReportServiceImpl implements NoticeReportService {

    @Autowired
    private ExtNoticeComplainInfo noticeComplainInfo;

    @Autowired
    private ExtOperateInfo operateInfo;

    @Autowired
    private ExtOnceSolveInfo extOnceSolve;
    @Autowired
    private ExtOnceSolveUserInfo extOnceSolveUserInfo;
    /**
     * 获取通知报表数据
    **/
    public ResponseNoticeReport GetNoticeReport(RequestNoticeReport request) {
        if (request == null)
            return null;
        //转化下时间用户查询
        Date endTime = DateUtils.addDays(request.getEndTime(), 1);
        int totalCount=0;
        List<String> listName = new ArrayList<>();
        if(request.getOpUser()==null||request.getOpUser().isEmpty()){
             listName = noticeComplainInfo.GetOpUserName(request.getPageIndex(),request.getPageSize(),request.getProductLine(),
                    request.getDataSource(), request.getOpUser(), request.getEvenType(), request.getStartTime(), endTime);

             totalCount=noticeComplainInfo.countNotices(request.getPageIndex(),request.getPageSize(),request.getProductLine(),
                    request.getDataSource(), request.getOpUser(), request.getEvenType(), request.getStartTime(), endTime);
        }else {
            listName.add(request.getOpUser());
        }
        ResponseNoticeReport rnr=new ResponseNoticeReport();
        if (listName==null||listName.size()==0)
            return rnr;
        //返回模型
        List<VmNoticeReport> noticeReports = new ArrayList<>();
        for (String ss : listName) {
            if (ss != null&&!ss.isEmpty()) {
                //通知数据
                List<NoticeComplainInfo> notice = noticeComplainInfo.GetNoticeList(request.getPageIndex(), request.getPageSize(), request.getProductLine(),
                        request.getDataSource(), ss,
                        request.getEvenType(), request.getStartTime(),
                        endTime);
                //通知总量
                long noitceCount = notice.size();
                //关闭量
                long close = notice.stream().filter(p -> p.getNoticeState() == NoticeStateEnum.Solved.getState()).count();
                //转投诉
                long turnComplain = notice.stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnComplain.getState()).count();
                //转领班
                long turnLeaderNotice = notice.stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnLeaderNotice.getState()).count();
                //被催量
                long opCount = notice.stream().filter(p -> p.getOpCount() > 0).count();
                //预约量
                long appointedCount = notice.stream().filter(p ->
                        p.getAppointedProcessTime() != null
                                && !(DateUtils.format(DateUtils.getDateFromTimestamp(p.getAppointedProcessTime()))
                                .equals("0001-01-01"))).count();
                //交班量
                long changeDeal = notice.stream().filter(p -> p.getChangeDutyTime() != null).count();
                Map<String,BigDecimal> map = getResponseTime(notice);
                VmNoticeReport model = new VmNoticeReport();
                model.setOpUser(ss);
                //通知总量
                model.setNoticeCount(String.valueOf(noitceCount));
                //关闭率
                model.setCloseRate(String.valueOf(close / noitceCount * 100) + "%");
                //被催率
                model.setPromotedRate(String.valueOf(opCount / noitceCount * 100) + "%");
                //转投诉量
                model.setTransferComplaints(String.valueOf(turnComplain));
                //转领班
                model.setTransferForeman(String.valueOf(turnLeaderNotice));
                //预约回电次数
                model.setReservationTimes(String.valueOf(appointedCount));

                model.setAvgResponseAging(String.valueOf(map.get("responseSum").divide(BigDecimal.valueOf(noitceCount), 2, BigDecimal.ROUND_HALF_EVEN)));

                model.setAvgProcessingAging(String.valueOf(map.get("processingSum").divide(BigDecimal.valueOf(noitceCount), 2, BigDecimal.ROUND_HALF_EVEN)));
                //交班量
                model.setTurnOver(String.valueOf(changeDeal));
                noticeReports.add(model);
            }
        }
        rnr.setList(noticeReports);
        rnr.setPageIndex(request.getPageIndex());
        rnr.setPageSize(request.getPageSize());
        rnr.setTotalRecord(totalCount);
        return rnr;
    }
    public ResponseNoticeReport GetNoticeReportExport(RequestNoticeReport request) {
        if (request == null)
            return null;
        //转化下时间用户查询
        Date endTime = DateUtils.addDays(request.getEndTime(), 1);
        //通知数据
        List<NoticeComplainInfo> notice = noticeComplainInfo.GetNoticeList(request.getPageIndex(),request.getPageSize(),request.getProductLine(),
                request.getDataSource(), request.getOpUser(),
                request.getEvenType(), request.getStartTime(),
                endTime);
        if (notice==null||notice.size()==0)
            return null;
        //获取外呼相关结果
        List<OperateInfo> oList=operateInfo.searchOperateInfo(request.getStartTime(),endTime,NoticeOperateTypeEnum.Call.getState());
        ResponseNoticeReport rnr=new ResponseNoticeReport();
        //返回模型
        List<VmNoticeReport> noticeReports = new ArrayList<>();
        //根据人名分组
        Map<String,List<NoticeComplainInfo>>  noticeMap= notice.stream().filter(p->p.getOpUser()!=null).collect(Collectors.groupingBy(NoticeComplainInfo::getOpUser));
        for (Map.Entry<String,List<NoticeComplainInfo>> item:noticeMap.entrySet()) {
            if (item.getKey() != null&&!item.getKey().isEmpty()) {
                //通知总量
                long noitceCount = item.getValue().size();

                //关闭量
                long close = item.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.Solved.getState()).count();

                //转投诉
                long turnComplain = item.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnComplain.getState()).count();

                //转领班
                long turnLeaderNotice = item.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnLeaderNotice.getState()).count();

                //被催量
                long opCount = item.getValue().stream().filter(p -> p.getOpCount() > 0).count();

                //预约量
                long appointedCount = item.getValue().stream().filter(p ->
                        p.getAppointedProcessTime() != null
                                && !(DateUtils.format(DateUtils.getDateFromTimestamp(p.getAppointedProcessTime()))
                                .equals("0001-01-01"))).count();
                //外呼次数
                long OperateCount = oList.stream().filter(o -> o.getOperateUser().split("\\(")[1].equals(item.getKey() + ")")).count();

                Map<String, BigDecimal> map = getResponseTime(item.getValue());

                VmNoticeReport model = new VmNoticeReport();
                model.setOpUser(item.getKey());
                //通知总量
                model.setNoticeCount(String.valueOf(noitceCount));
                //关闭率
                model.setCloseRate(String.valueOf(close / noitceCount * 100) + "%");
                //被催率
                model.setPromotedRate(String.valueOf(opCount / noitceCount * 100) + "%");
                //转投诉量
                model.setTransferComplaints(String.valueOf(turnComplain));
                //转领班
                model.setTransferForeman(String.valueOf(turnLeaderNotice));
                //预约回电次数
                model.setReservationTimes(String.valueOf(appointedCount));

                model.setAvgResponseAging(String.valueOf(map.get("responseSum").divide(BigDecimal.valueOf(noitceCount), 2, BigDecimal.ROUND_HALF_EVEN)));

                model.setAvgProcessingAging(String.valueOf(map.get("processingSum").divide(BigDecimal.valueOf(noitceCount), 2, BigDecimal.ROUND_HALF_EVEN)));
                //交班量
                long changeDeal = item.getValue().stream().filter(p -> p.getAppointedProcessTime() != null).count();
                //交班量
                model.setTurnOver(String.valueOf(changeDeal));
                model.setCallTimes(String.valueOf(OperateCount));
                noticeReports.add(model);
            }
        }
        rnr.setList(noticeReports);
        rnr.setPageIndex(request.getPageIndex());
        rnr.setPageSize(request.getPageSize());
        return rnr;
    }

    private BigDecimal duration(int eventType, Date endTime, Date startTime) {
        Date start = DateUtils.formatDate(startTime, DateUtils.YMD_UNDERLINED);
        Date end = DateUtils.formatDate(endTime, DateUtils.YMD_UNDERLINED);
        Date noticeStart;
        Date noticeEnd;
        if (eventType == 1) {
            //通知
            noticeStart = DateUtils.addHours(start, 9);
            noticeEnd = DateUtils.addHours(end, 23);
        } else {
            //投诉和领班
            noticeStart = DateUtils.addHours(start, 9);
            noticeEnd = DateUtils.addHours(end, 18);
        }
        long startSpan = 0;
        long endSpan = 0;
        long span = DateUtils.getDateSpanMin(endTime, startTime);
        //开始时间和结束时间同一天
        if (start.getTime() == end.getTime()) {
            //开始时间和工作时间都不在工作时间
            if ((startTime.getTime() < noticeStart.getTime() && endTime.getTime() < noticeStart.getTime()
                    || (startTime.getTime() > noticeEnd.getTime() && endTime.getTime() > noticeEnd.getTime()))) {
                return BigDecimal.ZERO;
            }
            if (startTime.getTime() < noticeStart.getTime()) {
                startSpan = DateUtils.getDateSpanMin(noticeStart, startTime);
            }
            if (endTime.getTime() > noticeEnd.getTime())
                endSpan = DateUtils.getDateSpanMin(endTime, noticeEnd);
        } else {
            Date startHms = DateUtils.formatDate(startTime, DateUtils.HMS);
            Date endHms = DateUtils.formatDate(endTime, DateUtils.HMS);
            //结束时间不满一天
            if (endHms.getTime() < startHms.getTime()) {
                if (startTime.getTime() < noticeStart.getTime()) {
                    startSpan = DateUtils.getDateSpanMin(noticeStart, startTime);
                }
                if (startTime.getTime() > DateUtils.addHours(noticeStart, 9).getTime())
                    startSpan = DateUtils.getDateSpanMin(DateUtils.addHours(start, 24), startTime);
                if (endTime.getTime() < DateUtils.addHours(noticeEnd, -9).getTime())
                    endSpan = DateUtils.getDateSpanMin(endTime, end);
                if (endTime.getTime() > DateUtils.addHours(noticeEnd, -9).getTime()
                        && endTime.getTime() < noticeEnd.getTime()) {
                    endSpan = DateUtils.getDateSpanMin(DateUtils.addHours(noticeEnd, -9), end);
                }
                if (endTime.getTime() > noticeEnd.getTime()) {
                    endSpan = DateUtils.getDateSpanMin(DateUtils.addHours(noticeEnd, -9), end);
                    endSpan += DateUtils.getDateSpanMin(endTime, noticeEnd);
                }
            } else {
                if (startTime.getTime() > DateUtils.addHours(noticeStart, 9).getTime())
                    endSpan = DateUtils.getDateSpanMin(endHms, startHms);
            }
            //相差天数
            long day =DateUtils.getTimeSpanDay(endTime,startTime);
            if (eventType==1) {
                span = span - day * 10 * 60 * 60;
            }
            else {
                span = span - day * 15 * 60 * 60;
            }
        }
        //总计分数
        double time = (double) (span - startSpan - endSpan) / 60.0;
        BigDecimal bd = new BigDecimal(time);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd;
    }

    private Map<String,BigDecimal> getResponseTime(List<NoticeComplainInfo> item){
        //响应时效和
        BigDecimal responseSum=new BigDecimal(0);
        //处理时效和
        BigDecimal processingSum=new BigDecimal(0);
        if(!item.isEmpty()) {
            for (NoticeComplainInfo p : item) {
                BigDecimal span;
                BigDecimal compele;
                if (p.getChangeDutyTime() != null && p.getAppointedProcessTime() != null
                        && !p.getAppointedProcessTime().equals("0001-01-01 00:00:00")
                        && p.getEnvenType() != null){
                    span = duration(p.getEnvenType(), Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getOpTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED)
                            , Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getAppointedProcessTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED));
                }
                else {
                    if (p.getOpTime() != null && p.getEnvenType() != null) {
                        span = duration(p.getEnvenType(), Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getOpTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED)
                                , Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getSendTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED));
                    } else {
                        span = new BigDecimal(0);
                    }
                }
                if (p.getCompleteTime() != null && p.getOpTime() != null && p.getEnvenType() != null) {
                    compele = duration(p.getEnvenType(), Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getCompleteTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED)
                            , Objects.requireNonNull(DateUtils.parseDate(DateUtils.dateToString(DateUtils.getDateFromTimestamp(p.getOpTime()),DateUtils.YMDHMS_UNDERLINED)),DateUtils.YMDHMS_UNDERLINED));
                } else {
                    compele = new BigDecimal(0);
                }
                responseSum = responseSum.add(span);
                processingSum = processingSum.add(compele);
            }
        }
        Map<String,BigDecimal> map =new HashMap<>();
        map.put("responseSum",responseSum);
        map.put("processingSum",processingSum);
        return map;
    }

    public ResponseComplainReport getComplainReport(RequestComplainReport request){

        int totalCount=noticeComplainInfo.GetComplainCount(request.getProductLine(),request.getChannel(),request.getStartDate()
                ,request.getEndDate(),request.getEnvenType());
        List<ComplainReport> rcrList= noticeComplainInfo.GetComplainList(request.getProductLine(),request.getChannel(),request.getStartDate()
                ,request.getEndDate(),request.getEnvenType(),request.getPageIndex(),request.getPageSize());
        ResponseComplainReport rcr=new ResponseComplainReport();
        ModelMapper mapper =new ModelMapper();
        List<VmComplainReport> vmList= mapper.map(rcrList,new TypeToken<List<VmComplainReport>>() {}.getType());
        rcr.setList(vmList);
        rcr.setPageIndex(request.getPageIndex());
        rcr.setPageSize(request.getPageSize());
        rcr.setTotalRecord(totalCount);
        return rcr;
    }

    @Override
    public ResponseOnceSolveReport SearchOnceSolve(RequestOnceSolveReport request) {
        List<NoticeOnceSolve> nList=extOnceSolve.SearchOnceSolve(request);
        List<VmOnceSolveReport> reportList=new ArrayList<>();
        if (nList!=null&&nList.size()!=0){
            int totalCount=extOnceSolve.SearchOnceSolveCount(request);
            for(NoticeOnceSolve n:nList){
                VmOnceSolveReport rosr=new VmOnceSolveReport();
                rosr.setOpUserName(n.getOpuserName());
                rosr.setOpTime(DateUtils.format(DateUtils.getDateFromTimestamp(n.getOpTime()),DateUtils.YMD_UNDERLINED) );
                rosr.setEnvenType(Integer.valueOf(EventTypeEnum.convertEventType(n.getEnvenType()).getName()));
//                rosr.setProductLine(ProductLineEnum.convertByCode(String.valueOf(n.getProductLine().intValue())).getProductLineName() );
                rosr.setProductLine(ProductLineEnum.convertByCode(n.getProductLine())==null?"未识别":
                        ProductLineEnum.convertByCode(String.valueOf(n.getProductLine().intValue())).getProductLineName());

                rosr.setPercentage(String.format("%.2f",n.getPercentage()*100));
                rosr.setOncesovle(n.getOncesovle());
                rosr.setAllsovle(n.getAllnum());
                reportList.add(rosr);
            }
            ResponseOnceSolveReport response=new ResponseOnceSolveReport();
            response.setPageIndex(request.getPageIndex());
            response.setPageSize(request.getPageSize());
            response.setList(reportList);
            response.setTotalRecord(totalCount);
            return response;
        }
        return null;
    }

    @Override
    public ResponseOnceSolveReport SearchOrderSolve(RequestOnceSolveReport request) {
        List<NoticeOnceSolve> nList=extOnceSolve.SearchOrderOnceSolve(request);
        List<VmOnceSolveReport> reportList=new ArrayList<>();
        if (nList!=null&&nList.size()!=0){
            int totalCount=extOnceSolve.SearchOrderOnceSolveCount(request);
            for(NoticeOnceSolve n:nList){
                VmOnceSolveReport rosr=new VmOnceSolveReport();
                rosr.setOpTime(DateUtils.format(DateUtils.getDateFromTimestamp(n.getOpTime()),DateUtils.YMD_UNDERLINED) );
                rosr.setEnvenType(n.getEnvenType());
                rosr.setProductLine(String.valueOf(n.getProductLine()));
                rosr.setPercentage(String.format("%.2f",n.getPercentage()*100));
                rosr.setOncesovle(n.getOncesovle());
                rosr.setAllsovle(n.getAllnum());
                reportList.add(rosr);
            }
            ResponseOnceSolveReport response=new ResponseOnceSolveReport();
            response.setPageIndex(request.getPageIndex());
            response.setPageSize(request.getPageSize());
            response.setList(reportList);
            response.setTotalRecord(totalCount);
            return response;
        }
        return null;
    }

    public ResponseNoticeReport getNoticeReportExportTwo(RequestNoticeReport request) {
        if (request == null)
            return null;
        //转化下时间用户查询
        Date endTime = DateUtils.addDays(request.getEndTime(), 1);
        //通知数据
        List<NoticeReportSum> notice = noticeComplainInfo.getNoticeListTwo(request.getPageIndex(),request.getPageSize(),request.getProductLine(),
                request.getDataSource(), request.getOpUser(),
                request.getEvenType(), request.getStartTime(),
                endTime);
        if (notice==null||notice.size()==0)
            return null;
//        //获取外呼与通知相关结果集 计算平均外呼时间
//        List<OperateNoticeTime> onList=operateInfo.searchOperateNotice(request.getStartTime(),endTime);
        ResponseNoticeReport rnr=new ResponseNoticeReport();
        //根据时间 人名分组
        Map<String, Map<String, List<NoticeReportSum>>> noticeMap= notice.stream().filter(p->p.getOpUser()!=null)
                .collect(Collectors.groupingBy(NoticeReportSum::getOpTime,Collectors.groupingBy(NoticeReportSum::getOpUser)));

        List<VmNoticeReport> noticeReportslist = getVmNoticeReportList(noticeMap.entrySet(),request.getStartTime(),request.getEndTime());

        if(request.getPageIndex()!=-2&&noticeReportslist.size()>20){
            rnr.setList(noticeReportslist.stream().sorted(Comparator.comparing(VmNoticeReport::getStarTime)).collect(Collectors.toList()).subList(
                    request.getPageIndex(),request.getPageIndex()+20));
        }else {
            rnr.setList(noticeReportslist.stream().sorted(Comparator.comparing(VmNoticeReport::getStarTime)).collect(Collectors.toList()));
        }
        rnr.setPageIndex(request.getPageIndex());
        rnr.setPageSize(request.getPageSize());
        return rnr;
    }
    private List<VmNoticeReport> getVmNoticeReportList(Set<Map.Entry<String, Map<String, List<NoticeReportSum>>>> mapEntry, Date startTime , Date endTime ){
        //获取外呼相关结果
        List<OperateInfo> oList=operateInfo.searchOperateInfo(startTime,endTime, NoticeOperateTypeEnum.Call.getState());
        //返回模型
        List<VmNoticeReport> noticeReportslist = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<NoticeReportSum>>> itemFather:mapEntry) {
            for (Map.Entry<String, List<NoticeReportSum>>  itemSon: itemFather.getValue().entrySet()) {
                if(itemSon.getKey()!=null&&!itemSon.getKey().equals("")){

                    VmNoticeReport model = getVmNoticeReport(itemSon,itemFather,oList);

                    // "平均响应时效（m）",总和
                    BigDecimal answerDuration=BigDecimal.ZERO;
                    // "平均处理时效（m）",总和
                    BigDecimal handleDuration=BigDecimal.ZERO;
                    // "平均处理时效（m）",总和
                    BigDecimal firstCallTimeSum=BigDecimal.ZERO;
                    int firstCall=0;
                    for (NoticeReportSum p:itemSon.getValue()){
                        BigDecimal san=BigDecimal.ZERO;
                        if (p.getFirstDealTime()!=null&&p.getEnvenType()!=null&&p.getSendTime()!=null) {
                            san = durationtwo(p.getEnvenType(),
                                    DateUtils.getDateFromTimestamp(p.getFirstDealTime()),
                                    DateUtils.parseDate(DateUtils.format(p.getSendTime(),  DateUtils.YMDHMS_UNDERLINED),DateUtils.YMDHMS_UNDERLINED));
                        }
                        answerDuration=answerDuration.add(san);
                        //处理时效
                        if (p.getCompleteTime()!=null&&p.getFirstDealTime()!=null&&p.getEnvenType()!=null) {
                            san = durationtwo(p.getEnvenType(),
                                    DateUtils.parseDate(DateUtils.format(p.getCompleteTime(), DateUtils.YMDHMS_UNDERLINED),DateUtils.YMDHMS_UNDERLINED),
                                    DateUtils.getDateFromTimestamp(p.getFirstDealTime()));
                            handleDuration=handleDuration.add(san);
                        }
                        // "平均响应时长（外呼m）",
                        if(p.getFirstCallTime()!=null){
                            san=durationtwo(p.getEnvenType(),p.getFirstCallTime(),p.getSendTime());
                            firstCallTimeSum=firstCallTimeSum.add(san);
                            firstCall++;
                        }
                    }
                    String averageAnswerDuration="0";
                    String averageHandleDuration="0";
                    String averageFirstCallTime="0";
                    if(firstCall!=0)
                        averageFirstCallTime=firstCallTimeSum.divide(new BigDecimal(firstCall),2,BigDecimal.ROUND_HALF_UP).toString();

                    model.setFristCallTimes(averageFirstCallTime);
                    //通知处理总量 已解决+暂缓+处理中+交班
                    long noticeDealCount=itemSon.getValue().stream().filter(p->
                            p.getNoticeState()==NoticeStateEnum.Solved.getState()
                                    ||p.getNoticeState()==NoticeStateEnum.Deferred.getState()
                                    ||p.getNoticeState()==NoticeStateEnum.ChangeDuty.getState()
                                    ||p.getNoticeState()==NoticeStateEnum.Assigned.getState()).count();

                    if(noticeDealCount!=0){
                        // "平均响应时效（m）"
                        averageAnswerDuration=answerDuration.divide(new BigDecimal(noticeDealCount),2,BigDecimal.ROUND_HALF_UP).toString();
                        // "平均处理时效（m）"
                        averageHandleDuration=handleDuration.divide(new BigDecimal(noticeDealCount),2,BigDecimal.ROUND_HALF_UP).toString();
                    }
                    model.setAvgResponseAging(averageAnswerDuration);
                    model.setAvgProcessingAging(averageHandleDuration);
                    noticeReportslist.add(model);
                }
            }
        }
        return noticeReportslist;
    }
    private VmNoticeReport getVmNoticeReport(Map.Entry<String, List<NoticeReportSum>> itemSon,
                                             Map.Entry<String, Map<String, List<NoticeReportSum>>> itemFather,
                                             List<OperateInfo> oList ){
            VmNoticeReport model = new VmNoticeReport();
            //转投诉
            long turnComplain = itemSon.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnComplain.getState()).count();
            //转领班
            long turnLeaderNotice = itemSon.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.TurnLeaderNotice.getState()).count();
            //被催量
            long opCount = itemSon.getValue().stream().filter(p -> p.getOpCount() > 0).count();
            //预约量
            long appointedCount = itemSon.getValue().stream().filter(p ->p.getAppointedProcessTime() != null
                    &&!"0001-01-01 00:00:00".equals(DateUtils.format(p.getAppointedProcessTime(),DateUtils.YMDHMS_UNDERLINED))).count();
            //外呼次数
            long operateCount = oList.stream().filter(o->{
                String oopuserNum=o.getOperateUser().replaceAll("[\\u4e00-\\u9fa5|(|)]", "");
//                                    String oopuserName=o.getOperateUser().replaceAll("^[\\u4e00-\\u9fa5|(|)]", "");
                String opuserNum=itemSon.getKey().replaceAll("[\\u4e00-\\u9fa5|(|)]", "");
//                                    String opuserName=itemSon.getKey().replaceAll("^[\\u4e00-\\u9fa5|(|)]", "");
                if(oopuserNum.equals(opuserNum)&&
                        itemFather.getKey().equals(DateUtils.format(o.getOperateTime(),DateUtils.YMD_UNDERLINED))){
                    return true;
                }
                return  false;
            }).count();
            //通知处理总量 已解决+暂缓+处理中+交班
            long noticeDealCount=itemSon.getValue().stream().filter(p->
                    p.getNoticeState()==NoticeStateEnum.Solved.getState()
                            ||p.getNoticeState()==NoticeStateEnum.Deferred.getState()
                            ||p.getNoticeState()==NoticeStateEnum.ChangeDuty.getState()
                            ||p.getNoticeState()==NoticeStateEnum.Assigned.getState()).count();

            //关闭量
            long close = itemSon.getValue().stream().filter(p -> p.getNoticeState() == NoticeStateEnum.Solved.getState()).count();

            model.setOpUser(itemSon.getKey());
            //通知总量
            model.setNoticeCount(String.valueOf(noticeDealCount));
            if (noticeDealCount>0){
                //关闭率
                model.setCloseRate(new BigDecimal(close/(double)noticeDealCount*100).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "%");
                //被催率
                model.setPromotedRate(new BigDecimal(opCount/(double)noticeDealCount*100).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+ "%");
            }else{
                //关闭率
                model.setCloseRate(0 + "%");
                //被催率
                model.setPromotedRate(0 + "%");
            }
            //转投诉量
            model.setTransferComplaints(String.valueOf(turnComplain));
            //转领班
            model.setTransferForeman(String.valueOf(turnLeaderNotice));
            //预约回电次数
            model.setReservationTimes(String.valueOf(appointedCount));
            model.setCallTimes(String.valueOf(operateCount));
            //交班量
            model.setTurnOver(String.valueOf(itemSon.getValue().stream().filter(p -> p.getChangeDutyTime() != null).count()));
            model.setStarTime(itemFather.getKey());
        return model;
    }

//    private VmNoticeReport getVmNoticeReport(Map.Entry<String, Map<String, List<NoticeReportSum>>> itemFather, Map.Entry<String, List<NoticeReportSum>> itemSon, long turnComplain, long turnLeaderNotice, long opCount, long appointedCount, long operateCount, BigDecimal answerDuration, BigDecimal handleDuration, long noticeDealCount, long close, String averageAnswerDuration, String averageHandleDuration, String averageFirstCallTime) {
//        VmNoticeReport model = new VmNoticeReport();
//        //外呼
//        model.setFristCallTimes(averageFirstCallTime);
//        if(noticeDealCount!=0){
//            // "平均响应时效（m）"
//            averageAnswerDuration=answerDuration.divide(new BigDecimal(noticeDealCount),2,BigDecimal.ROUND_HALF_UP).toString();
//            // "平均处理时效（m）"
//            averageHandleDuration=handleDuration.divide(new BigDecimal(noticeDealCount),2,BigDecimal.ROUND_HALF_UP).toString();
//        }
//        model.setOpUser(itemSon.getKey());
//        //通知总量
//        model.setNoticeCount(String.valueOf(noticeDealCount));
//        if (noticeDealCount>0){
//            //关闭率
//            model.setCloseRate(new BigDecimal(close/(double)noticeDealCount*100).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "%");
//            //被催率
//            model.setPromotedRate(new BigDecimal(opCount/(double)noticeDealCount*100).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+ "%");
//        }else{
//            //关闭率
//            model.setCloseRate(0 + "%");
//            //被催率
//            model.setPromotedRate(0 + "%");
//        }
//        //转投诉量
//        model.setTransferComplaints(String.valueOf(turnComplain));
//        //转领班
//        model.setTransferForeman(String.valueOf(turnLeaderNotice));
//        //预约回电次数
//        model.setReservationTimes(String.valueOf(appointedCount));
//        model.setAvgResponseAging(averageAnswerDuration);
//        model.setAvgProcessingAging(averageHandleDuration);
//        model.setCallTimes(String.valueOf(operateCount));
//        //交班量
//        model.setTurnOver(String.valueOf(itemSon.getValue().stream().filter(p -> p.getChangeDutyTime() != null).count()));
//        model.setStarTime(itemFather.getKey());
//        return model;
//    }


    private BigDecimal durationtwo(int eventType, Date endTime, Date startTime) {
        Date start = DateUtils.formatDate(startTime, DateUtils.YMD_UNDERLINED);
        Date end = DateUtils.formatDate(endTime, DateUtils.YMD_UNDERLINED);
        Date noticeStart;
        Date noticeEnd;
        if (eventType == 1) {
            //通知
            noticeStart = DateUtils.addHours(start, 9);
            noticeEnd = DateUtils.addHours(end, 23);
        } else {
            //投诉和领班
            noticeStart = DateUtils.addHours(start, 9);
            noticeEnd = DateUtils.addHours(end, 18);
        }
        long startSpan = 0;
        long endSpan = 0;
        long span = DateUtils.getDateSpanMin(endTime, startTime);

        //开始时间和结束时间同一天
        if (start.getTime() == end.getTime()) {
            //开始时间和工作时间都不在工作时间
            if ((startTime.getTime() < noticeStart.getTime() && endTime.getTime() < noticeStart.getTime()
                    || (startTime.getTime() > noticeEnd.getTime() && endTime.getTime() > noticeEnd.getTime()))) {
                return BigDecimal.ZERO;
            }
            if (startTime.getTime() < noticeStart.getTime()) {
                startSpan = DateUtils.getDateSpanMin(noticeStart, startTime);
            }
            if (endTime.getTime() > noticeEnd.getTime()) {
                endSpan = DateUtils.getDateSpanMin(endTime, noticeEnd);
            }
        } else {
            Date startHms = DateUtils.formatDate(startTime, DateUtils.HMS);
            Date endHms = DateUtils.formatDate(endTime, DateUtils.HMS);
            //结束时间不满一天
            if (endHms.getTime() < startHms.getTime()) {
                if (startTime.getTime() < noticeStart.getTime()) {
                    startSpan = DateUtils.getDateSpanMin(noticeStart, startTime);
                }
                if (startTime.getTime() > DateUtils.addHours(noticeStart, 9).getTime()) {
                    startSpan = DateUtils.getDateSpanMin(DateUtils.addHours(start, 24), startTime);
                }

                if (endTime.getTime() < DateUtils.addHours(noticeEnd, -9).getTime()) {
                    endSpan = DateUtils.getDateSpanMin(endTime, end);
                }

                if (endTime.getTime() > DateUtils.addHours(noticeEnd, -9).getTime()
                        && endTime.getTime() < noticeEnd.getTime()) {
                    endSpan = DateUtils.getDateSpanMin(DateUtils.addHours(noticeEnd, -9), end);
                }
                if (endTime.getTime() > noticeEnd.getTime()) {
                    endSpan = DateUtils.getDateSpanMin(DateUtils.addHours(noticeEnd, -9), end);
                    endSpan += DateUtils.getDateSpanMin(endTime, noticeEnd);
                }
            } else {
                if (startTime.getTime() > DateUtils.addHours(noticeStart, 9).getTime()) {
                    endSpan = DateUtils.getDateSpanMin(endHms, startHms);
                }
            }
            //相差天数
            long day =DateUtils.getTimeSpanDay(endTime,startTime);
            if (eventType==1) {
                span = span - day * 10 * 60 * 60;
            }
            else {
                span = span - day * 15 * 60 * 60;
            }

        }
        //总计分数
        double time = (double) (span - startSpan - endSpan) / 60.0;
        BigDecimal bd = new BigDecimal(time);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd;
    }
    @Override
    public ResponseOnceSolveReport SearchOnceSolveUser(RequestOnceSolveReport request) {

        List<NoticeOnceSolveUser> nList=extOnceSolveUserInfo.SearchOnceSolveUser(request);
        List<VmOnceSolveReport> reportList=new ArrayList<>();
        if (nList!=null&&nList.size()!=0){
            int totalCount=extOnceSolveUserInfo.SearchOnceSolveUserCount(request);
            for(NoticeOnceSolveUser n:nList){
                VmOnceSolveReport rosr=new VmOnceSolveReport();
                rosr.setOpTime(DateUtils.format(DateUtils.getDateFromTimestamp(n.getOpTime()),DateUtils.YMD_UNDERLINED) );
                rosr.setOpuserNum(n.getOpuserNum());
//                rosr.setEnvenType(EventTypeEnum.convertEventType(n.getEnvenType()).getName());
//                rosr.setProductLine(ProductLineEnum.convertByCode(n.getProductLine())==null?"未识别":
//                        ProductLineEnum.convertByCode(String.valueOf(n.getProductLine().intValue())).getProductLineName());
                rosr.setEnvenType(n.getEnvenType());
                rosr.setProductLine(String.valueOf(n.getProductLine()));
//                rosr.setProductLine(ProductLineEnum.convertByCode(String.valueOf(n.getProductLine().intValue())).getProductLineName() );
                rosr.setPercentage(n.getPercentage().toString());
//                rosr.setPercentage(String.format("%.2f",n.getPercentage()*100));
                rosr.setOncesovle(n.getOncesovle());
                rosr.setAllsovle(n.getAllnum());
                rosr.setOpUserName(n.getOpuserName());
                reportList.add(rosr);
            }
            ResponseOnceSolveReport response=new ResponseOnceSolveReport();
            response.setPageIndex(request.getPageIndex());
            response.setPageSize(request.getPageSize());
            response.setList(reportList);
            response.setTotalRecord(totalCount);
            return response;
        }
        return null;
    }


    @Override
    public byte[] SearchOnceSolveUserDetail(RequestOnceSolveReport request) {
        //一次性解决单子
        List<OnceSolveNotice> reportList=noticeComplainInfo.searchOncesolveNoticebyOpuser(request);
        Map<Long, List<OnceSolveNotice>> tempMap=reportList.stream().collect(Collectors.groupingBy(OnceSolveNotice::getId));
        //总处理量
        List<NoticeComplainInfo> alllist=noticeComplainInfo.searchOpuserAllSolve(request);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //表头标题
        String[] headers = new String[]{
                "订单号",
                "消息组别",
                "产品线",
                "一次性解决",
        };
        List<String[]> dataTable=new ArrayList<>();
        try {
            //设置数据
            if(alllist!=null&&alllist.size()>0){
                //生成dataTable
                for (NoticeComplainInfo notice : alllist) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=notice.getOrderID();
                    dataRow[index++]=String.valueOf(EventTypeEnum.convertEventType(notice.getEnvenType()).getName());
                    dataRow[index++]=String.valueOf(ProductLineEnum.convertByCode(notice.getProductLine())==null?"未识别":
                        ProductLineEnum.convertByCode(String.valueOf(notice.getProductLine())).getProductLineName());
                    dataRow[index++]=tempMap.containsKey(notice.getID())?"是":"否";
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("客服一次性解决率明细数据导出")
                    .setDataTable(dataTable)
                    .build();
                exportBuilder.writeToStream(bout);
                return bout.toByteArray();
            } catch (IOException e) {
                CLogger.error("客服一次性解决率明细数据导出出错",e);
                return null;
        }
    }

    @Override
    public byte[] SearchUrgeUserDetail(Date enterDate,String opUser) {

        List<NoticeComplainInfo> reportList=noticeComplainInfo.searchOpuserUrgeNotice(enterDate,opUser);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //表头标题
        String[] headers = new String[]{
                "日期",
                "订单号",
                "消息组别",
                "产品线",
                "崔次数",
                "处理人",
        };
        List<String[]> dataTable=new ArrayList<>();
        try {
            //设置数据
            if(reportList!=null&&reportList.size()>0){
                //生成dataTable
                for (NoticeComplainInfo notice : reportList) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=DateUtils.format(notice.getEnterDate());
                    dataRow[index++]=notice.getOrderID();
                    dataRow[index++]=String.valueOf(EventTypeEnum.convertEventType(notice.getEnvenType()).getName());
                    dataRow[index++]=String.valueOf(ProductLineEnum.convertByCode(notice.getProductLine())==null?"未识别":
                            ProductLineEnum.convertByCode(String.valueOf(notice.getProductLine())).getProductLineName());
                    dataRow[index++]=String.valueOf(notice.getOpCount());
                    dataRow[index++]=String.valueOf(notice.getOpUser());
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("客服催通知汇总")
                    .setDataTable(dataTable)
                    .build();
            exportBuilder.writeToStream(bout);
            return bout.toByteArray();
        } catch (IOException e) {
            CLogger.error("客服催通知汇总错误",e);
            return null;
        }
    }

    @Override
    public byte[] SearchOnceSolveOrderDetail(RequestOnceSolveReport request) {

        //一次性解决通知
        List<OnceSolveNotice> reportList=noticeComplainInfo.searchOncesolveNoticeDetail(request);
        Map<Long, List<OnceSolveNotice>> tempMap=reportList.stream().collect(Collectors.groupingBy(OnceSolveNotice::getId));
        //总处理量
        List<NoticeComplainInfo> alllist=noticeComplainInfo.searchOpuserAllSolve(request);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        //表头标题
        String[] headers = new String[]{
                "订单号",
                "消息组别",
                "产品线",
                "一次性解决",
        };
        List<String[]> dataTable=new ArrayList<>();
        try {
            //设置数据
            if(alllist!=null&&alllist.size()>0){
                //生成dataTable
                for (NoticeComplainInfo notice : alllist) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=notice.getOrderID();
                    dataRow[index++]=String.valueOf(EventTypeEnum.convertEventType(notice.getEnvenType()).getName());
                    dataRow[index++]=String.valueOf(ProductLineEnum.convertByCode(notice.getProductLine())==null?"未识别":
                            ProductLineEnum.convertByCode(String.valueOf(notice.getProductLine())).getProductLineName());
                    dataRow[index++]=tempMap.containsKey(notice.getID())?"是":"否";
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("订单一次性解决率明细数据导出")
                    .setDataTable(dataTable)
                    .build();
            exportBuilder.writeToStream(bout);
            return bout.toByteArray();
        } catch (IOException e) {
            CLogger.error("订单一次性解决率数据导出出错",e);
            return null;
        }
    }
}
