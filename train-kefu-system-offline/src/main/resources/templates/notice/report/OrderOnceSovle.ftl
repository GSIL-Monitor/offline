<!DOCTYPE html>
<html>
<#include "../../common/header.ftl">
<@header pageTtle="订单一次性解决率报表" headerVisible=true pageWidth=1200></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/js/timepicker-addon.js"></script>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<body>
<div class="pubglobal_main" style="width: 1200px;">
    <div class="panel panel-default" style="padding: 5px 10px;margin: 10px 0">
        <table width="100%"  class="table-style2">
            <tr>
                <th>日期:</th>
                <td class="text-left form-inline" colspan="3">
                    <div class='input-group date' id='inputStartTime'>
                        <input type='text' style="width: 85px;margin-right: 0" class="form-control input-sm" value="${startDate}"/>
                        <span class="input-group-addon">
                             <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                    -
                    <div class='input-group date' id='inputEndTime'>
                        <input type='text'  style="width: 85px;margin-right: 0"  class="form-control input-sm" value="${endDate}"/>
                        <span class="input-group-addon">
                             <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </td>
                <th>产品线:</th>
                <td><select id="selProductLine" style="width: 100px" class="form-control input-sm">
                    <option value="">全部</option>
                     <#list productLineEnums as productLine>
                        <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                     </#list>
                </select></td>
                <th>消息组别:</th>
                <td><select style="width: 100px" id="selEventType" class="form-control input-sm">
                    <option value="">全部</option>
                     <#list eventTypeEnum as eventType>
                        <option value="${eventType.getValue()}">${eventType.getName()}</option>
                     </#list>
                </select></td>
            </tr>
        </table>
        <hr class="divider" >
        <div class="form-inline">
            <a onclick="searchNotice()" class="btn btn-primary btn-sm w80">查询</a>
            <a onclick="exportNotice()" class="btn btn-link btn-sm">导出</a>
        </div>
    </div>
    <div >
        <div id="divPageNav">
            <#include "common/component/common-pagination.ftl" >
        </div>
        <table class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;">
            <tr class="success" style="text-align: -webkit-center; ">
                <th width="60px">日期</th>
                <th width="60px">消息组别</th>
                <th width="60px">产品线</th>
                <th width="60px">一次性解决量</th>
                <th width="80px">总订单量</th>
                <th width="80px">一次性解决率</th>
                <th width="80px">导出明细</th>
            </tr>
            <tbody id="noticeTBody">
            <tr><td colspan="15" style="height: 70px;text-align: center">没有找到数据</td></tr>
            </tbody>
        </table>
    </div>
</div>
<#noparse >
    <script id="tplNotice" type="text/x-jquery-tmpl">
    <tr style="border-bottom:0">
         <td>${opTime}</td>
         <td>${ConvertEventType(envenType)}</td>
         <td>${ConvertProductLine(productLine)}</td>
         <td>${oncesovle}</td>
         <td>${allsovle}</td>
         <td>${percentage}</td>
         <td> <a id="btnExport" onclick="exportNoticeDetail('${opTime}','${envenType}','${productLine}')" >导出</a> </td>
    </tr>
</script>
<script id="tplRemarks" type="text/x-jquery-tmpl">
        【${operateComment}】<font color="gray" size='1' >[${operateUser}][${operateTime}]</font></br><br>
</script>
</#noparse>
<script type="text/javascript">
    $(function () {
        var dateTimerSetting={
            format: "yyyy-mm-dd",
            language: 'zh-CN',
            startView:2,
            minView:2,
            autoclose:true
        };
        $('#inputStartTime').datetimepicker(dateTimerSetting);
        dateTimerSetting.useCurrent=false;
        $('#inputEndTime').datetimepicker(dateTimerSetting);
        $("#inputStartTime").on("dp.change", function (e) {
            $('#inputEndTime').data("DateTimePicker").minDate(e.date);
        });
        $("#inputEndTime").on("dp.change", function (e) {
            $('#inputStartTime').data("DateTimePicker").maxDate(e.date);
        });
        commonPaginationInitial(getUrl,showWait,onGetData,onNoData);//初始化分页组件
    });
    var selectedProductLine=-1;
    var selectedEventType=-1;
    function exportNotice() {
        var url="exportordersovle";
        var startDate=$("#inputStartTime input").val();
        var endDate=$("#inputEndTime input").val();
        var productLine=$("#selProductLine").val();
        var eventType=$("#selEventType").val();
        url+="?startDate="+startDate
                +"&endDate="+endDate
                +"&productLine="+productLine
                +"&eventType="+eventType;
        window.open(url,"_blank");
    }
    function getUrl() {
        var url="${ctx}/notice/searchordersovle.json";
        var startDate=$("#inputStartTime input").val();
        var endDate=$("#inputEndTime input").val();
        var productLine=$("#selProductLine").val();
        var eventType=$("#selEventType").val();

        url+="?startDate="+startDate
                +"&endDate="+endDate
                +"&productLine="+productLine
                +"&eventType="+eventType;
        return url;
    }
    function showWait() {
        $("#noticeTBody").html("<tr><td colspan=\"15\" style=\"height: 70px;text-align: center\"><i class=\"fa fa-cog fa-spin fa-lg fa-fw fa-3x\"></i><span class=\"sr-only\">Loading...</span></td></tr>");
    }
    function onGetData(data) {
        $("#noticeTBody").html('');
        $("#tplNotice").tmpl(data.data.response.list).appendTo('#noticeTBody');
        if(!(data.data.response.list.length)){
            $("#noticeTBody").html("<tr><td colspan=\"15\" style=\"height: 70px;text-align: center\">没有找到数据</td></tr>");
        }
    }
    function onNoData(data) {
        $("#noticeTBody").html("<tr><td colspan=\"15\" style=\"height: 70px;text-align: center\">没有找到数据</td></tr>");
    }
    function searchNotice() {
        var startDate=$("#inputStartTime input").val();
        var endDate=$("#inputEndTime input").val();
        if(DateDiff(startDate,endDate)<=31){
            commonPaginationStartSearch();
        }else{
            layer.msg('查询时间不要超过一个月');
        }
    }
    function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式
        var  aDate,  oDate1,  oDate2,  iDays
        aDate  =  sDate1.split("-")
        oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式
        aDate  =  sDate2.split("-")
        oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
        return  iDays
    }
    function exportNoticeDetail(opTime,envenType,productLine) {
        var url="exportonceOrderdetail";
        url+="?startDate="+opTime
                +"&productLine="+productLine
                +"&eventType="+envenType;
        window.open(url,"_blank");
    }
</script>
<#include "common/footer.ftl">
</body>
</html>