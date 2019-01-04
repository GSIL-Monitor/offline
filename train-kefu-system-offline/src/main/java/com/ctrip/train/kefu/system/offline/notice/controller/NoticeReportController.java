package com.ctrip.train.kefu.system.offline.notice.controller;

import com.ctrip.train.kefu.system.offline.common.controller.BaseController;
import com.ctrip.train.kefu.system.offline.common.utils.JsonResult;
import com.ctrip.train.kefu.system.offline.notice.enums.ChannelSourceEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.EventTypeEnum;
import com.ctrip.train.kefu.system.offline.notice.enums.ProductLineEnum;
import com.ctrip.train.kefu.system.offline.notice.service.NoticeReportService;
import com.ctrip.train.kefu.system.offline.notice.vm.VmComplainReport;
import com.ctrip.train.kefu.system.offline.notice.vm.VmNoticeReport;
import com.ctrip.train.kefu.system.offline.notice.vm.VmOnceSolveReport;
import com.ctrip.train.kefu.system.offline.notice.vm.notice.*;
import common.log.CLogger;
import common.util.DateUtils;
import common.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notice")
public class NoticeReportController extends BaseController {

    @Autowired
    private NoticeReportService noticeReportService;

    @ResponseBody
    @RequestMapping(value = "/export",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportNotice(String startDate,String endDate,String opUser,
                               Integer eventType,Integer productLine,Integer channel,HttpServletResponse httpServletResponse) throws IOException {

        RequestNoticeReport request= new RequestNoticeReport();
        request.setStartTime(DateUtils.parseDate(startDate));
        request.setEndTime(DateUtils.parseDate(endDate));
        request.setOpUser(opUser); //胡城(N24873)
        request.setDataSource(channel==null?null:String.valueOf(channel));
        request.setEvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(-2);
        ResponseNoticeReport notice=noticeReportService.getNoticeReportExportTwo(request);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            //表头标题
            String[] headers = new String[]{
                    "日期",
                    "处理人",
                    "通知处理总量",
                    "平均响应时效（m）",
                    "平均响应时长（外呼m）",
                    "外呼次数",
                    "平均处理时效（m）",
                    "当天关闭率",
                    "被催率",
                    "转投诉量",
                    "转领班单数",
                    "预约回电次数",
                    "交班量",
            };
            List<String[]> dataTable=new ArrayList<>();
            //设置数据
            if(notice!=null&&notice.getList()!=null){
                List<VmNoticeReport> notices=notice.getList();
                //生成dataTable
                for (VmNoticeReport vmnotice : notices) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=vmnotice.getStarTime();
                    dataRow[index++]=vmnotice.getOpUser();
                    dataRow[index++]=vmnotice.getNoticeCount();
                    dataRow[index++]=vmnotice.getAvgResponseAging();
                    dataRow[index++]=vmnotice.getFristCallTimes();
                    dataRow[index++]=vmnotice.getCallTimes();
                    dataRow[index++]=vmnotice.getAvgProcessingAging();
                    dataRow[index++]=vmnotice.getCloseRate();
                    dataRow[index++]=vmnotice.getPromotedRate();
                    dataRow[index++]=vmnotice.getTransferComplaints();
                    dataRow[index++]=vmnotice.getTransferForeman();
                    dataRow[index++]=vmnotice.getReservationTimes();
                    dataRow[index++]=vmnotice.getTurnOver();
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("通知数据导出")
                    .setDataTable(dataTable)
                    .build();

            exportBuilder.writeToStream(bout);
            CLogger.info("导出通知结束",String.format("操作人：%s",getOpUser()));
            return bout.toByteArray();
        } catch (Exception e) {
            CLogger.error("通知导出出错",e);
            throw  e;
        }
    }
    @RequestMapping("/noticeList")
    public String noticeList(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();
        EventTypeEnum[] eventTypeEnums=EventTypeEnum.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        model.put("eventTypeEnums", Arrays.stream(eventTypeEnums).collect(Collectors.toList()));
        return "notice/report/exportNotice";
    }
    @GetMapping("/searchNoticeList")
    @ResponseBody
    public JsonResult searchNotice(int pageIndex,int pageSize,String startDate,String endDate,String opUser,
                                   Integer eventType,String channel,Integer productLine) throws IOException{
        RequestNoticeReport request= new RequestNoticeReport();
        request.setStartTime(DateUtils.parseDate(startDate));
        request.setEndTime(DateUtils.parseDate(endDate));
        request.setOpUser(opUser);

        request.setDataSource(channel==null?null:String.valueOf(channel));
        request.setEvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        ResponseNoticeReport ss=noticeReportService.getNoticeReportExportTwo(request);
        if (ss!=null&&ss.getList()!=null&&ss.getList().size()>0) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail().putData("response",ss);
    }
    @GetMapping("/complainReport")
    public String noticeComplain(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        return "notice/report/complainReport";
    }

    @GetMapping("/searchComplain")
    @ResponseBody
    public JsonResult SearchComplain(int pageIndex,int pageSize,String startDate,String endDate,Integer productLine,Integer channel,Integer eventType) throws IOException{

        RequestComplainReport request= new RequestComplainReport();
        request.setChannel(channel==null?null:Integer.valueOf(channel));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        //设置类型为投诉或通知
        request.setEnvenType(eventType);
        ResponseComplainReport ss=noticeReportService.getComplainReport(request);
        if (ss!=null&&ss.getList()!=null&&ss.getList().size()>0) {
            return JsonResult.ok().putData("response",ss);
        }
        return JsonResult.fail().putData("response",ss);
    }

    @ResponseBody
    @RequestMapping(value = "/exportComplain",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] ComplainExport(String startDate,String endDate,Integer productLine,Integer channel,Integer eventType) throws IOException{

        RequestComplainReport request= new RequestComplainReport();
        request.setChannel(channel==null?null:Integer.valueOf(channel));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setPageIndex(-1);
        request.setPageSize(-1);
        //设置类型为投诉
        request.setEnvenType(eventType);
        //表头标题
        String[] headers = new String[]{
                "日期",
                "投诉类型",
                "订单量",
                "总订单量",
                "占比",
        };
        return getNoticeOrComplainByte(request, headers);
    }
    @ResponseBody
    @RequestMapping(value = "/NoticeReportExport",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] NoticeExport(String startDate,String endDate,Integer productLine,Integer channel,Integer eventType) throws IOException{

        RequestComplainReport request= new RequestComplainReport();
        request.setChannel(channel==null?null:Integer.valueOf(channel));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setPageIndex(-1);
        request.setPageSize(-1);
        //设置类型为投诉
        request.setEnvenType(eventType);
        //表头标题
        String[] headers = new String[]{
                "日期",
                "通知类型",
                "订单量",
                "总订单量",
                "占比",
        };
        return getNoticeOrComplainByte(request, headers);
    }

    private byte[] getNoticeOrComplainByte(RequestComplainReport request, String[] headers) throws IOException {
        ResponseComplainReport notice = noticeReportService.getComplainReport(request);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            List<String[]> dataTable=new ArrayList<>();
            //设置数据
            if(notice!=null&&notice.getList()!=null){
                List<VmComplainReport> notices=notice.getList();
                //生成dataTable
                for (VmComplainReport vmnotice : notices) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=vmnotice.getEnterDate();
                    dataRow[index++]=vmnotice.getComplainType();
                    dataRow[index++]= String.valueOf(vmnotice.getNumByType());
                    dataRow[index++]=String.valueOf(vmnotice.getAllnum());
                    dataRow[index++]=vmnotice.getPercentage();
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("投诉数据导出")
                    .setDataTable(dataTable)
                    .build();

            exportBuilder.writeToStream(bout);
            CLogger.info("导出投诉结束",String.format("操作人：%s",getOpUser()));
            return bout.toByteArray();
        } catch (Exception e) {
            CLogger.error("投诉导出出错",e);
            throw  e;
        }
    }

    /**
     * 通知报表
     * @param model
     * @return
     */
    @GetMapping("/noticeReport")
    public String noticeReport(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        return "notice/report/noticeReport";
    }

    /**
     * 一次性解决率
     * @return
     */
    @GetMapping("/oncesolve")
    public String onceSolveNotice(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();
        EventTypeEnum[] eventTypeEnum=EventTypeEnum.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        model.put("eventTypeEnum", Arrays.stream(eventTypeEnum).collect(Collectors.toList()));
        return "notice/report/noticeOnceSovle";
    }

    @GetMapping("/searchoncesovle")
    @ResponseBody
    public JsonResult SearchOnceSolve(String startDate,String endDate,Integer productLine,Integer eventType,String opUser ,int pageIndex,int pageSize){

        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setEnvenType(eventType);
        request.setOpUser(opUser);
        ResponseOnceSolveReport reportList= noticeReportService.SearchOnceSolveUser(request);
        if (reportList!=null&&reportList.getList()!=null&&reportList.getList().size()>0) {
            return JsonResult.ok().putData("response",reportList);
        }
        return JsonResult.fail().putData("response",reportList);
    }
    @ResponseBody
    @RequestMapping(value = "/exportoncesolve",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportOnceSolve(String startDate,String endDate,Integer productLine,Integer eventType,String opUser,
                                  HttpServletResponse httpServletResponse) throws IOException {
        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setOpUser(opUser); //胡城(N24873)
        request.setEnvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(-1);
        request.setPageSize(-1);
        ResponseOnceSolveReport reportList= noticeReportService.SearchOnceSolveUser(request);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            //表头标题
            String[] headers = new String[]{
                    "日期",
                    "员工",
                    "消息组别",
                    "产品线",
                    "一次性解决量",
                    "总订单量",
                    "一次性解决率",
            };
            List<String[]> dataTable=new ArrayList<>();
            //设置数据
            if(reportList!=null&&reportList.getList()!=null){
                List<VmOnceSolveReport> onceSolve=reportList.getList();
                //生成dataTable
                for (VmOnceSolveReport vmonce : onceSolve) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=vmonce.getOpTime();
                    dataRow[index++]=vmonce.getOpUserName();
                    dataRow[index++]=EventTypeEnum.convertEventType(vmonce.getEnvenType()).getName();
                    dataRow[index++]=ProductLineEnum.convertByCode(vmonce.getProductLine())==null?"未识别": ProductLineEnum.convertByCode(vmonce.getProductLine()).getProductLineName();
                    dataRow[index++]=String.valueOf(vmonce.getOncesovle());
                    dataRow[index++]=String.valueOf(vmonce.getAllsovle());
                    dataRow[index++]=String.valueOf(vmonce.getPercentage());
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("一次性解决率数据导出")
                    .setDataTable(dataTable)
                    .build();
            exportBuilder.writeToStream(bout);
            CLogger.info("一次性解决率数据导出",String.format("操作人：%s",getOpUser()));
            return bout.toByteArray();
        } catch (Exception e) {
            CLogger.error("通知导出出错",e);
            throw  e;
        }
    }

    /**
     * 订单一次性解决率
     * @return
     */
    @GetMapping("/onceordersolve")
    public String onceSolveOrder(Map<String,Object> model){
        String startDate= DateUtils.getCurDateStr();
        String endDate= DateUtils.getCurDateStr();
        ProductLineEnum[] productLineEnums=ProductLineEnum.values();
        ChannelSourceEnum[] channelSourceEnums=ChannelSourceEnum.values();
        EventTypeEnum[] eventTypeEnum=EventTypeEnum.values();
        model.put("startDate",startDate);
        model.put("endDate",endDate);
        model.put("productLineEnums", Arrays.stream(productLineEnums).collect(Collectors.toList()));
        model.put("channelSourceEnums", Arrays.stream(channelSourceEnums).collect(Collectors.toList()));
        model.put("eventTypeEnum", Arrays.stream(eventTypeEnum).collect(Collectors.toList()));
        return "notice/report/OrderOnceSovle";
    }

    @GetMapping("/searchordersovle")
    @ResponseBody
    public JsonResult SearchOrderSolve(String startDate,String endDate,Integer productLine,Integer eventType ,int pageIndex,int pageSize){
        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setEnvenType(eventType);
        ResponseOnceSolveReport reportList= noticeReportService.SearchOrderSolve(request);
        if (reportList!=null&&reportList.getList()!=null&&reportList.getList().size()>0) {
            return JsonResult.ok().putData("response",reportList);
        }
        return JsonResult.fail().putData("response",reportList);
    }


    @ResponseBody
    @RequestMapping(value = "/exportordersovle",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportOrderSolve(String startDate,String endDate,Integer productLine,Integer eventType,
                                  HttpServletResponse httpServletResponse) throws IOException {
        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEndDate(DateUtils.parseDate(endDate));
        request.setEnvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setPageIndex(-1);
        request.setPageSize(-1);
        ResponseOnceSolveReport reportList= noticeReportService.SearchOrderSolve(request);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            //表头标题
            String[] headers = new String[]{
                    "日期",
                    "消息组别",
                    "产品线",
                    "一次性解决量",
                    "总订单量",
                    "一次性解决率",
            };
            List<String[]> dataTable=new ArrayList<>();
            //设置数据
            if(reportList!=null&&reportList.getList()!=null){
                List<VmOnceSolveReport> onceSolve=reportList.getList();
                //生成dataTable
                for (VmOnceSolveReport vmonce : onceSolve) {
                    int index=0;
                    String[] dataRow=new String[headers.length];
                    dataRow[index++]=vmonce.getOpTime();
                    dataRow[index++]=EventTypeEnum.convertEventType(vmonce.getEnvenType()).getName();
                    dataRow[index++]=vmonce.getProductLine();
                    dataRow[index++]=String.valueOf(vmonce.getOncesovle());
                    dataRow[index++]=String.valueOf(vmonce.getAllsovle());
                    dataRow[index++]=String.valueOf(vmonce.getPercentage());
                    dataTable.add(dataRow);
                }
            }
            ExcelUtils.ExportBuilder exportBuilder = ExcelUtils
                    .createExportBuilder()
                    .setHeaders(headers)
                    .setTitle("订单一次性解决率数据导出")
                    .setDataTable(dataTable)
                    .build();
            exportBuilder.writeToStream(bout);
            CLogger.info("订单一次性解决率数据导出",String.format("操作人：%s",getOpUser()));
            return bout.toByteArray();
        } catch (Exception e) {
            CLogger.error("通知导出出错",e);
            throw  e;
        }
    }
    @ResponseBody
    @RequestMapping(value = "/exportoncedetail",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportOnceSolveDetail(String startDate,Integer productLine,Integer eventType,String opUser,
                                   HttpServletResponse httpServletResponse){
        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEnvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        request.setOpUser(opUser);
        return noticeReportService.SearchOnceSolveUserDetail(request);
    }

    @ResponseBody
    @RequestMapping(value = "/exportUrgedetail",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportUrgeDetail(String startDate,String opUser, HttpServletResponse httpServletResponse){
        return noticeReportService.SearchUrgeUserDetail(DateUtils.parseDate(startDate),opUser);
    }


    @ResponseBody
    @RequestMapping(value = "/exportonceOrderdetail",produces = {"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8;"})
    public byte[] exportOnceSolveOrderDetail(String startDate,Integer productLine,Integer eventType, HttpServletResponse httpServletResponse){
        RequestOnceSolveReport request= new RequestOnceSolveReport();
        request.setStartDate(DateUtils.parseDate(startDate));
        request.setEnvenType(eventType==null?null:Integer.valueOf(eventType));
        request.setProductLine(productLine==null?null:Integer.valueOf(productLine));
        return noticeReportService.SearchOnceSolveOrderDetail(request);
    }

}
