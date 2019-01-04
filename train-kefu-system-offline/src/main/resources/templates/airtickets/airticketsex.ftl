
<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<@header pageTtle="异常单处理" headerVisible=true pageWidth=1053></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/js/timepicker-addon.js"></script>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<body>
<div class="pubglobal_main" style="width: 1053px;">
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
                <th>异常订单类型:</th>
                <td>
                    <select id="selEOrderType" style="width: 100px" class="form-control input-sm">
                        <option value="">全部</option>
                     <#list eXOrderTypeEnums as OrderType>
                        <option value="${OrderType.getState()}">${OrderType.getName()}</option>
                     </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <th>订单号:</th>
                <td><input type="text" style="width: 100px" class="form-control input-sm" id="orderId"/></td>
                <th>供应商:</th>
                <td>
                    <select id="selSupplier" style="width: 100px" class="form-control input-sm">
                        <option value="">全部</option>
                     <#list supplierEnums as supplier>
                        <option value="${supplier.getValue()}">${supplier.getName()}</option>
                     </#list>
                    </select>
                </td>
                <th>产品线:</th>
                <td>
                    <select id="selProductLine" style="width: 100px" class="form-control input-sm">
                        <option value="">全部</option>
                     <#list productLineEnums as productLine>
                        <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                     </#list>
                    </select>
                </td>
            </tr>
        </table>
        <hr class="divider" >
        <div class="form-inline">
            <a onclick="searchNotice()" class="btn btn-primary btn-sm w80">查询</a>
        </div>
        <div >
            <div id="divPageNav">
            <#include "common/component/common-pagination.ftl" >
            </div>
            <table class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;">
                <tr class="success" style="text-align: -webkit-center; ">
                    <th width="60px">序号</th>
                    <th width="60px">订单号</th>
                    <th width="80px">供应商</th>
                    <th width="80px">下单时间</th>
                    <th width="80px">起飞时间</th>
                    <th width="80px">最晚出票时间</th>
                    <th width="80px">类型</th>
                    <th width="80px">备注</th>
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
         <td>${id}</td>
         <td>${orderId}</td>
         <td>${supplierEnums(supplier)}</td>
         <td>${sendOrderTime}</td>
         <td>${takeoffTime}</td>
         <td>${latestTicketingTime}</td>
         <td>${eXOrderTypeEnums(exOrderType)}</td>
         <td>${processingRemark}</td>
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
        function getUrl() {
            var url="${ctx}/airOrder/searchAirNotice.json";
            var startDate=$("#inputStartTime input").val();
            var endDate=$("#inputEndTime input").val();
            var eOrderType=$("#selEOrderType").val();
            var productLine=$("#selProductLine").val();
            var supplier=$("#selSupplier").val();
            var orderId=$("#orderId").val();
            url+="?startDate="+startDate
                    +"&endDate="+endDate
                    +"&supplier="+supplier
                    +"&eOrderType="+eOrderType
                    +"&productLine="+productLine
                    +"&orderId="+orderId;
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
            commonPaginationStartSearch();
        }
        function supplierEnums(supplier){
            switch(supplier){
                case "ctrip":
                    return "携程"
                case "qunar":
                    return '去哪儿';
                case "19e":
                    return '19e';
                case "jiulx":
                    return '就旅行';
            }
        }
        function eXOrderTypeEnums(eXOrderType){
            switch(eXOrderType){
                case "71":
                    return "超时未改签";
                case "72":
                    return '超时未退票';
                case "73":
                    return '票号异常';
                case "74":
                    return '出票失败';
                case "75":
                    return "改签失败";
                case "76":
                    return '退票失败';
                case "70":
                    return '超时未出票';
                case "77":
                    return '票号校验不符';
                default:
                    return "未配置";
            }
        }
    </script>
    <#include "common/footer.ftl">
</body>
</html>


