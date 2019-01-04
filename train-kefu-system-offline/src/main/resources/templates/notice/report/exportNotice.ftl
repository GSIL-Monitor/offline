<!DOCTYPE html>
<html>
<#include "../../common/header.ftl">
<@header pageTtle="通知报表" headerVisible=true pageWidth=1200></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/js/timepicker-addon.js"></script>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-select.min.css">
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/i18n/defaults-*.min.js"></script>
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
                <th>处理人:</th>
                <td><input type="text" style="width: 100px" class="form-control input-sm" id="inputOpUser"/></td>
            </tr>
            <tr>
                <th>通知类型:</th>
                <td width="90px"><select id="selEventType" style="width: 100px" onchange="changeEventType()" class="form-control input-sm">
                    <option value="">全部</option>
                    <#list eventTypeEnums as eventType>
                        <option value="${eventType.getValue()}">${eventType.getName()}</option>
                    </#list>
                </select></td>
                <th>产品线:</th>
                <td><select id="selProductLine" style="width: 100px" class="form-control input-sm">
                    <option value="">全部</option>
                     <#list productLineEnums as productLine>
                        <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                     </#list>
                </select></td>
                <th>渠道:</th>
                <td style="float: left;">
                    <#--<select style="width: 100px" id="selChannel" class="form-control input-sm">-->
                    <select style="width: 100px" id="selChannel" class="selectpicker" multiple>
                     <#list channelSourceEnums as channel>
                         <option  id="" value="${channel.getValue()}">${channel.getName()}</option>
                     </#list>
                    </select>
                </td>
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
                <th width="50px">时间</th>
                <th width="50px">处理人</th>
                <th width="50px">通知处理总量</th>
                <th width="50px">平均响应时效（m）</th>
                <th width="50px">平均响应时长（外呼m）</th>
                <th width="50px">外呼次数</th>
                <th width="50px">平均处理时效（m）</th>
                <th width="50px">当天关闭率</th>
                <th width="50px">被催率</th>
                <th width="50px">操作</th>
                <th width="50px">转投诉量</th>
                <th width="50px">转领班单数</th>
                <th width="50px">预约回电次数</th>
                <th width="50px">交班量</th>
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
         <td>${starTime}</td>
         <td>${opUser}</td>
         <td>${noticeCount}</td>
         <td>${avgResponseAging}</td>
         <td>${fristCallTimes}</td>
         <td>${callTimes}</td>
         <td>${avgProcessingAging}</td>
         <td>${closeRate}</td>
         <td>${promotedRate}</td>
         <td>
            <a id="btnExport" onclick="exportNoticeDetail('${opUser}','${starTime}')" >导出</a>
         </td>
         <td>${transferComplaints}</td>
         <td>${transferForeman}</td>
         <td>${reservationTimes}</td>
         <td>${turnOver}</td>
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
        var url="export";
        var startDate=$("#inputStartTime input").val();
        var endDate=$("#inputEndTime input").val();
        var opUser=$("#inputOpUser").val();
        var eventType=$("#selEventType").val();
        var productLine=$("#selProductLine").val();
        var channel=$("#selChannel").val();
        url+="?startDate="+startDate
                +"&endDate="+endDate
                +"&opUser="+opUser
                +"&eventType="+eventType
                +"&productLine="+productLine
                +"&channel="+channel;
        window.open(url,"_blank");
    }
    function getUrl() {
        var url="${ctx}/notice/searchNoticeList.json";
        var startDate=$("#inputStartTime input").val();
        var endDate=$("#inputEndTime input").val();
        var opUser=$("#inputOpUser").val();
        var eventType=$("#selEventType").val();
        var productLine=$("#selProductLine").val();
        var channel=$("#selChannel").val();
        url+="?startDate="+startDate
                +"&endDate="+endDate
                +"&opUser="+opUser
                +"&eventType="+eventType
                +"&channel="+channel
                +"&productLine="+productLine;
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
    function showMoreInfos(ob,orderid) {
        var url='${ctx}/notice/opInfos?orderid='+orderid;
        $.ajax({
            url:url,
            method:"GET",
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                var infos=data.data.opInfos;
                var content=$("#tplRemarks").tmpl(infos);
                $(ob).popover({
                    content:content,
                    html:true
                });
                $(ob).popover("toggle");
            },
            error : function() {
                $(ob).popover({
                    content:"系统错误"
                });
                $(ob).popover("toggle");
            }
        });
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
    function exportNoticeDetail(opUser,starTime) {
        var url="exportUrgedetail";
        url+="?startDate="+starTime
                +"&opUser="+opUser;
        window.open(url,"_blank");
    }

</script>
<#include "common/footer.ftl">
</body>
</html>