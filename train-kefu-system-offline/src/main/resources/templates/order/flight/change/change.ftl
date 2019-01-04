
<!DOCTYPE html>
<html>
<#include "../../../common/header.ftl">
<@header pageTtle="机票改签：订单号 ${ordernumber}" headerVisible=true pageWidth=1200></@header>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css"/>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_global.css"/>
<#--<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>-->
<#--<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>-->
<script type="text/javascript" src="${ctx}/static/layer/theme/default/layer.css"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<body>
<div class="pubglobal_main" style="width: 1200px; margin-top: 20px;">
    <div >
        <ul class="nav nav-pills" style="position: relative;background-color: rgba(242, 242, 242, 1)">
            <li id="liman" role="presentation" class="active"  style="width: 33%;"><a href="javascript:void(0);">1.选择改签人程</a></li>
            <li id="lifightno" role="presentation" style="width: 33%;"><a href="javascript:void(0);">2.选择可改航班</a></li>
            <li id="liconfirm" role="presentation" style="width: 33%;"><a href="javascript:void(0);">3.核实改签信息</a></li>
        </ul>

    </div>

    <div id="divman">
        <div style="height: 30px;margin-top: 20px;text-align: left;background-color: #386f96;line-height: 1.75;">
            <span style="display:block;position: relative;margin-top: 35px;font-size: large;color: white; margin-left: 20px;">选择改签人程</span>
        </div>

            <table class="table table-bordered table-condensed" style="border-collapse: collapse;">
            <tbody id="noticeTBody">
            <#if flightChange?has_content>
                <#list  flightChange  as  flight>
                 <tr class="info" style="text-align: -webkit-center; ">
                     <th width="90px">航段</th>
                     <th width="90px">航程</th>
                     <th width="90px">航班</th>
                     <th width="90px">起飞时间</th>
                     <th width="90px">舱位</th>
                     <th width="90px">退改政策</th>
                 </tr>
                     <tr class="trhover" style="border-bottom: 1px;">
                         <td class="tdheight">
                             <#--<#switch flight.getFlightDetails().getTripType()>-->
                                 <#--<#case 1>单程<#break>-->
                                 <#--<#case 2>往返<#break>-->
                                 <#--<#case 4>多程<#break>-->
                                 <#--<#default >未配置</#switch>-->
                             ${flight.getFlightDetails().getSegmentName()}
                             <input data-orderId="${flight.getOrderId()}"
                                    data-departCityCode="${flight.getFlightDetails().getDepartCityCode()}"
                                    data-arriveCityCode="${flight.getFlightDetails().getArriveCityCode()}"
                                    data-departDate="${flight.getFlightDetails().getDepartDate()}"
                                    data-flightNumber="${flight.getFlightDetails().getFlightNo()}"
                                    data-cabinCode="${flight.getFlightDetails().getClassGrade()}"
                                    data-airlineCode="${flight.getFlightDetails().getAirlineCode()}"
                                    data-routeIndex="${flight.getFlightDetails().getTripType()}"
                                    data-rebookAble="${flight.getFlightDetails().getRebookAble()?string("true","false")}"
                                    data-segmentNo="${flight.getFlightDetails().getSegmentNo()}"
                                    data-sequence="${flight.getFlightDetails().getSequence()}"
                                    data-rebookMinDaysBefore="${flight.getFlightDetails().getRebookMinDaysBefore()}"
                                    data-rebookMinDaysAfter="${flight.getFlightDetails().getRebookMinDaysAfter()}"
                                    data-rebookDateAfter="${flight.getFlightDetails().getRebookDateAfter()}"
                                    data-rebookDateBefore="${flight.getFlightDetails().getRebookDateBefore()}"
                                    data-freeDateAfter="${flight.getFlightDetails().getFreeDateAfter()}"
                                    data-freeDateBefore="${flight.getFlightDetails().getFreeDateBefore()}"
                                    data-segmentName="${flight.getFlightDetails().getSegmentName()}"
                                    id="hidevalue${flight_index}" type="hidden"/>
                         </td>
                         <td style="border-left: 1px;">${flight.getFlightDetails().getFromPortName()} - ${flight.getFlightDetails().getToPortName()}</td>
                         <td>${flight.getFlightDetails().getFlightNo()}</td>
                         <td>${flight.getFlightDetails().getTakeoffTime()}</td>
                         <td>
                         <#--${ClassGradeEmum(flight.getFlightDetails().getClassGrade())}-->
                             <#switch flight.getFlightDetails().getClassGrade()>
                                 <#case 0>经济舱<#break>
                                 <#case 1>超级经济舱<#break>
                                 <#case 2>公务舱<#break>
                                 <#case 3>头等舱<#break>
                                 <#case 9>公务舱+头等舱<#break>
                                 <#default >未配置</#switch>
                         </td>
                         <td>
                             <a href="#" onclick="openpolicy(this)">查看</a>
                             <span style="display: none;">${flight.getFlightDetails().getRescheduleRefundRemark()} </span>
                         </td>
                     </tr>
                   <tr style="border-bottom:0px;"  >
                       <td colspan="12" style="align-content: center">
                           <table class="table table-bordered table-hover" style="border-collapse: collapse; width: 100%; " >
                               <tr class="info" style="text-align: -webkit-center; ">
                                   <th width="50px">选择</th>
                                   <th width="100px">乘机人</th>
                                   <th width="100px">乘机人类型</th>
                                   <th width="100px">备注</th>
                               </tr>
                               <tbody >
                                    <#if flight.getPassengers()?has_content>
                                        <#list flight.getPassengers() as passenger>
                                            <tr style="border-bottom: 1px;">
                                                <td>
                                                    <input data-index="${flight_index}" onchange="peopleChange.change(this)" name="chk${flight_index}" type="checkbox" value="${passenger.getPassengerName()}"
                                                         data-passengerName="${passenger.getPassengerName()}"
                                                         data-passengerType="${passenger.getPassengerType()}"
                                                         data-cardType="${passenger.getCardType()}"
                                                         data-cardNo="${passenger.getCardNo()}"
                                                         data-gender="${passenger.getGender()}"
                                                         data-rebookType="${passenger.getRebookType()}"
                                                         data-birthday="${passenger.getBirthday()}"
                                                    />
                                                </td>
                                                <td>${passenger.getPassengerName()}</td>
                                                <td>
                                                    <#switch passenger.getPassengerType()>
                                                        <#case 0>未知<#break>
                                                        <#case 1>成人<#break>
                                                        <#case 2>儿童<#break>
                                                        <#case 3>婴儿<#break>
                                                        <#case 4>老人<#break>
                                                        <#default >未配置</#switch>
                                                </td>
                                                <td>${passenger.getRemarks()}</td>
                                            </tr>
                                        </#list>
                                    </#if>
                               </tbody>
                           </table>
                       </td>
                   </tr>
                </#list>
            <tr >
                <td colspan="6" class="form-inline">
                    <span id="hintInfo" class="alert alert-warning" style="display:none;height: 10px;padding: 10px 20px;margin-right: 30px"></span>
                    <a class="btn btn-primary btn-sm w160" id="btnSendNotice" onclick="mannext()">下一步，选择可改航班</a>
                </td>
            </tr>
            <#else>
                <tr >
                    <td colspan="6" class="form-inline">
                        <span id="hintInfo" class="alert alert-warning" style="display:none;height: 10px;padding: 10px 20px;margin-right: 30px">

                        </span>
                        ${msgContent!"查询数据失败！"}
                    </td>
                </tr>
            </#if>

            </tbody>
        </table>

    </div>

    <div id="divfightno" style="display: none;">
        <div style="height: 30px;text-align: left;background-color: #386f96;line-height: 1.75;">
            <span style="display:block;position: relative;margin-top: 35px;font-size: large;color: white; margin-left: 20px;">选择新日期</span>

        </div>

        <div class="text-left form-inline" style="margin-top: 10px;text-align: left;">
            <span> 单程:</span>
            <div class='input-group date' id='inputStartTime'>
                <input id="textDate" type='text' style="width: 85px;margin-right: 0" class="form-control input-sm" value=""/>
                <span class="input-group-addon">
                             <span class="glyphicon glyphicon-calendar"></span>
                        </span>
            </div>
            <input type="button" value="查询" onclick="ticketChange.searchfight();">
        </div>

        <div style="height: 30px;text-align: left;background-color: #386f96;line-height: 1.75;">
            <span style="display:block;position: relative;margin-top: 20px;font-size: large;color: white; margin-left: 20px;">选择新航班</span>
        </div>

        <div style="margin-top: 10px;text-align: left;">
            <table id="fighttable" border="1" cellpadding="0px" cellspacing="0px" style="width: 99%; margin: 0; color: #003366; position: relative;">
                <tbody>
                <tr style="border-color: white;">
                    <td colspan="8" style="text-align: left;">
                        筛选
                        <input onchange="timeselect.timeselect();" type="checkbox" name="chktitle" value="1"><span class="sptitle">凌晨 (00:00~06:00)</span>
                        <input onchange="timeselect.timeselect();" type="checkbox" name="chktitle" value="2"><span class="sptitle">上午 (06:00~12:00)</span>
                        <input onchange="timeselect.timeselect();" type="checkbox" name="chktitle" value="3"><span class="sptitle">下午 (12:00~18:00)</span>
                        <input onchange="timeselect.timeselect();" type="checkbox" name="chktitle" value="4"><span class="sptitle">晚上 (18:00~24:00)</span>
                    </td>
                </tr>
                <tr style="background-color: #d9edf7;border: 1px solid #ddd;">
                    <td>
                        航班
                    </td>
                    <td>
                        航程
                    </td>
                    <td>
                        起降时间
                    </td>
                    <td>
                        改签费
                    </td>
                    <td>
                        选择
                    </td>
                </tr>
                </tbody>
                <tbody id="tbfight">
                </tbody>
            </table>
        </div>

        <div class="code_btn_0">
            <a class="btn btn-primary btn-sm w160" id="btnSendNotice" onclick="flightup()">上一步，选择改签人程</a>
                <a class="btn btn-primary btn-sm w160" id="btnSendNotice" onclick="flightnext()">下一步，核实改签信息</a>
        </div>
    </div>

    <div id="divconfirm" style="display: none;">
        <div style="height: 30px;margin-top: 20px;text-align: left;background-color: #386f96;line-height: 1.75;">
            <span style="display:block;position: relative;margin-top: 35px;font-size: large;color: white; margin-left: 20px;">核实改签信息</span>
        </div>

        <div style="margin-top: 10px;text-align: left;">

            <table cellspacing="0" cellpadding="3" rules="all" border="1"
                   style="margin-top:20px;background-color:White;border-color:#CCCCCC;border-width:1px;border-style:None;font-size:9pt;width:100%;border-collapse:collapse;">

                <tr style="background-color:#d9edf7;font-weight:bold;">
                    <th>乘客</th><th>航段</th><th>航班</th><th>航程</th><th>起降时间</th>
                </tr>

                <tbody id="tbconfirm">

                </tbody>
            </table>

            <div style="margin-top: 10px;">
                费用：<span id="spanadultChangeFee"></span><br />
                包括：改签手续费<span id="spanadultChangeHandlingFee"></span>；改签升舱费<span id="spanadultChangeUpgradeFee"></span>
            </div>

        </div>

        <#--<div style="margin-top: 10px;text-align: left;">-->
            <#--<table border="0" cellpadding="0px" cellspacing="0px" style="width: 800px; margin: 0 auto; color: #003366; position: relative;">-->
                <#--<tr>-->
                    <#--<td>航班</td><td>航程</td><td>起降时间</td><td>舱位</td><td>改签费</td><td>选择</td>-->
                <#--</tr>-->
            <#--</table>-->
            <#--费用：XXX元-->
            <#--包括-->
        <#--</div>-->

        <div class="code_btn_0">
            <a class="btn btn-primary btn-sm w160" id="btnSendNotice" onclick="confirmup()">上一步，选择可改航班</a>
            <a class="btn btn-primary btn-sm w160" id="btnSendNotice" onclick="confirmnext()">下一步，提交改签申请</a>
        </div>
    </div>

    <div class="modal fade" id="rebookFeeDiv" tabindex="-1" role="dialog" aria-labelledby="OutBoundModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 500px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">退改政策
                    </h4>
                </div>
                <div class="modal-body ">
                    <div class="input-group" style="text-align: left;padding-bottom: 3px;">
                        <span id="policyContent"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
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
        $('#inputStartTime').datetimepicker("setStartDate", "${datetimenow}")
        $("#inputStartTime").on("dp.change", function (e) {
            $('#inputEndTime').data("DateTimePicker").minDate(e.date);
        });
        //commonPaginationInitial(getUrl,showWait,onGetData,onNoData);//初始化分页组件
    });


    function mannext() {   //人程下一步
        if(peopleChange.check()){
            showDisplay("fightno");
        }else{
            layer.alert("请选择改签人程！");
        }
    }

    function flightup() {   //选择新航班上一步
        showDisplay("man");
    }

    function flightnext() {   //选择新航班下一步
        if(ticketChange.confirmInfo()){
            showDisplay("confirm");
        }
    }

    function confirmup() {   //确认改签信息上一步
        showDisplay("fightno");
    }

    function confirmnext() {   //确认改签
        btnconfirm.confirm();
    }

    function showDisplay(type){   //头部切换效果
        var arType = new Array("man","fightno","confirm");
        $.each(arType,function(index,value){
            if(value == type){
                $("#li" + type).addClass("active");
                $("#div" + type).show();
            }else{
                $("#li" + value).removeClass("active");
                $("#div" + value).hide();
            }
        })
    }

    var peopleChange = {   //改签人程相关
        change: function (obj) {
            $("#tbfight").html("");
            $("#textDate").val("");
            if ($(obj).prop('checked')) {
                var ckename = $(obj).attr("name");
                peopleChange.chkselect(ckename);
            }
        },
        chkselect:function(ckename){
            $("#noticeTBody").find("input[type=checkbox][name!='" + ckename + "']:checked").each(function () {
                $(this).prop("checked",false);
            });
        },
        check:function(){
            var rtnStatus = false;
            $("#noticeTBody").find("input[type=checkbox]").each(function () {
                if($(this).prop("checked")){
                    rtnStatus = true;
                }
            });
            return rtnStatus;
        }

    }

    //可改航班相关
    var ticketChange = {
        searchfight : function(){
            if(ticketChange.checktime()) {
                ticketChange.getfightNo();
            }
        },
        checktime:function(){
            var rtnStatus = false;
            if ($("#textDate").val() == "") {
                layer.alert("请选择日期！");
            }else{
                rtnStatus=true;
            }
            return rtnStatus;
        },
        getfightNo : function(){  //查询可改航班
            ticketChange.showWait($("#tbfight"));
            var vmFlightSerach = ticketChange.getSerachentity();
            ticketChange.serachFlight(vmFlightSerach);

        },
        getSerachentity :function (){   //组装改签人程页面的实体
            var index =  $("#noticeTBody").find("input[type=checkbox]:checked:first").attr("data-index");   //获取下标
            var vmFlightSerach = {};
            var segment = $("#hidevalue" + index);

            vmFlightSerach.orderid = segment.attr("data-orderid");
            vmFlightSerach.departDate = $("#textDate").val();
            vmFlightSerach.departCityCode = segment.attr("data-departCityCode");
            vmFlightSerach.arriveCityCode = segment.attr("data-arriveCityCode");

            vmFlightSerach.segments = ticketChange.getsegments(segment);
            vmFlightSerach.passengers = ticketChange.getpassenger();
            vmFlightSerach.rebookInformation = ticketChange.getpeopleEneity(segment);
            return vmFlightSerach;
        },
        getsegments :function(segment){
            var segmentsList = new Array();
            var tempsegments = {};
            tempsegments.departDate = segment.attr("data-departDate");
            tempsegments.departCityCode = segment.attr("data-departCityCode");
            tempsegments.arriveCityCode = segment.attr("data-arriveCityCode");
            tempsegments.flightNumber = segment.attr("data-flightNumber");
            tempsegments.cabinCode = segment.attr("data-cabinCode");
            tempsegments.cabinName = "";
            tempsegments.airlineCode = segment.attr("data-airlineCode");
            tempsegments.routeIndex = segment.attr("data-routeIndex");
            segmentsList.push(tempsegments);
            return segmentsList;

        },
        getpeopleEneity: function (segment) {  //获取人程页面数据
            var rebookInformation = {};
            rebookInformation.segmentNo = segment.attr("data-freeDateBefore");
            rebookInformation.sequence = segment.attr("data-sequence");
            rebookInformation.rebookMinDaysBefore = segment.attr("data-rebookMinDaysBefore");
            rebookInformation.rebookMinDaysAfter = segment.attr("data-rebookMinDaysAfter");
            rebookInformation.rebookAble = segment.attr("data-rebookAble");
            rebookInformation.rebookDateAfter = segment.attr("data-rebookDateAfter");
            rebookInformation.rebookDateBefore = segment.attr("data-rebookDateBefore");
            rebookInformation.freeDateAfter = segment.attr("data-freeDateAfter");
            rebookInformation.freeDateBefore = segment.attr("data-freeDateBefore");
            return rebookInformation;
        },
        getpassenger:function(){
            var passengersList = new Array();
            $("#noticeTBody").find("input[type=checkbox]:checked").each(function(){
                var vmFlightPassenger = {};
                vmFlightPassenger.passengerName = $(this).attr("data-passengername");
                vmFlightPassenger.passengerType = $(this).attr("data-passengertype");
                vmFlightPassenger.cardType = $(this).attr("data-cardtype");
                vmFlightPassenger.cardNo = $(this).attr("data-cardno");
                vmFlightPassenger.gender = $(this).attr("data-gender");
                vmFlightPassenger.birthday = $(this).attr("data-birthday");
                vmFlightPassenger.rebookType = $(this).attr("data-rebooktype");
                passengersList.push(vmFlightPassenger);
            });
            return passengersList;
        },
        combinetable : function(fightlist){   //组装航班信息
            var fighthtml = "";
            var passengertype = $("#noticeTBody").find("input[type=checkbox]:checked:first").attr("data-passengertype");
            $.each(fightlist,function(index,value){
                fighthtml += "<tr>";
                fighthtml += "<td>" + value.airlineName + value.flightNumber + "</td>";
                fighthtml += "<td>" + value.departCityName + value.departAirportName + value.departTerminal + "<br/>" + value.arriveCityName + value.arriveAirportName + value.arriveTerminal + "</td>";
                fighthtml += "<td><span name='departtime'>" + value.departTime + "</span><br >" + value.arriveTime + "</td>";
                if(passengertype == "3"){   //婴儿
                    fighthtml += "<td>改签手续费：0 <br/>改签升舱费：0</td>";
                }else if(passengertype == "2"){   //儿童
                    fighthtml += "<td>改签手续费：" + setPrice(value.childChangeHandlingFee) + "<br/>改签升舱费：" + setPrice(value.childChangeUpgradeFee) + "</td>";
                }else{   //成人
                    fighthtml += "<td>改签手续费：" + setPrice(value.adultChangeHandlingFee) + "<br/>改签升舱费：" + setPrice(value.adultChangeUpgradeFee) + "</td>";
                }
                var datapart = ticketChange.getdatapart(value);
                fighthtml += "<td><input type='radio' name='rdfight' " + datapart + " /> </td>";
                fighthtml += "</tr>";
            });
            $("#tbfight").html(fighthtml);
        },
        getdatapart :function(value){  //所有数据放到隐藏域
            var datapart = " data-airlineName='" + value.airlineName + "'";
            datapart += " data-flightNumber='" + value.flightNumber+ "'";
            datapart += " data-departCityName='" + value.departCityName+ "'";
            datapart += " data-departAirportName='" + value.departAirportName+ "'";
            datapart += " data-departTerminal='" + value.departTerminal+ "'";
            datapart += " data-arriveCityName='" + value.arriveCityName+ "'";
            datapart += " data-arriveAirportName='" + value.arriveAirportName+ "'";
            datapart += " data-arriveTerminal='" + value.arriveTerminal+ "'";
            datapart += " data-departTime='" + value.departTime+ "'";
            datapart += " data-arriveTime='" + value.arriveTime+ "'";
            datapart += " data-childChangeFee='" + value.childChangeFee+ "'";
            datapart += " data-childChangeHandlingFee='" + value.childChangeHandlingFee+ "'";
            datapart += " data-childChangeUpgradeFee='" + value.childChangeUpgradeFee+ "'";
            datapart += " data-subClassData='" + value.subClassData+ "'";
            datapart += " data-flightData='" + value.flightData+ "'";
            datapart += " data-adultChangeFee='" + value.adultChangeFee + "'";
            datapart += " data-adultChangeHandlingFee='" + value.adultChangeHandlingFee + "'";
            datapart += " data-adultChangeUpgradeFee='" + value.adultChangeUpgradeFee + "'";
            return datapart;
        },
        confirmInfo: function () {
            if(!$("input[name='rdfight']:checked").length)
            {
                layer.alert("请选择改签航班!");
                return false;
            }else{
                ticketChange.combineconfirm();
                return true;
            }
        },
        combineconfirm:function(){  //组装核实改签信息数据
            var index =  $("#noticeTBody").find("input[type=checkbox]:checked:first").attr("data-index");   //获取下
            //var tripType = $("#hidevalue" + index).attr("data-routeindex");
            var rdconfirm = $("input[name='rdfight']:checked");
            var confirmInfo = "";
            var changeFee = 0;
            var changeHandlingFee = 0;
            var changeUpgradeFee = 0;
            var number = 0;
            $("#noticeTBody").find("input[type=checkbox]:checked").each(function(){
                confirmInfo += "<tr><td>" + $(this).attr("data-passengername") + "</td>";
                //confirmInfo += "<td>" + ticketChange.getTripType(tripType) + "</td>";
                confirmInfo += "<td>" + $("#hidevalue" + index).attr("data-segmentName") + "</td>";
                confirmInfo += "<td>" + rdconfirm.attr("data-airlinename") + rdconfirm.attr("data-flightnumber") + "</td>";
                confirmInfo += "<td>" + rdconfirm.attr("data-departcityname") + rdconfirm.attr("data-departairportname") + rdconfirm.attr("data-departterminal") + "<br/>";
                confirmInfo += rdconfirm.attr("data-arrivecityname") + rdconfirm.attr("data-arriveairportname") + rdconfirm.attr("data-arriveterminal") + "</td>";
                confirmInfo += "<td>" + rdconfirm.attr("data-departtime") + "<br/>" + rdconfirm.attr("data-arrivetime") + "</td></tr>";
                if($(this).attr("data-passengertype") == 3){   //婴儿

                }else if($(this).attr("data-passengertype") == 2) { //儿童
                     changeFee += parseFloat(rdconfirm.attr("data-childChangeFee"));
                     changeHandlingFee = parseFloat(rdconfirm.attr("data-childChangeHandlingFee"));
                     changeUpgradeFee = parseFloat(rdconfirm.attr("data-childChangeUpgradeFee"));
                }else{   //成人
                    changeFee += parseFloat(rdconfirm.attr("data-adultChangeFee"));
                    changeHandlingFee = parseFloat(rdconfirm.attr("data-adultChangeHandlingFee"));
                    changeUpgradeFee = parseFloat(rdconfirm.attr("data-adultChangeUpgradeFee"));
                }
                number += 1;
            });

            if(changeFee < 0){
                $("#spanadultChangeFee").html("待计算");
            }else{
                $("#spanadultChangeFee").html(changeFee + "元");
            }

            if(changeHandlingFee < 0){
                $("#spanadultChangeHandlingFee").html("待计算");
            }else{
                $("#spanadultChangeHandlingFee").html(changeHandlingFee + "元" + ticketChange.getNumberInfo(number));
            }

            if(changeUpgradeFee < 0){
                $("#spanadultChangeUpgradeFee").html("待计算");
            }else{
                $("#spanadultChangeUpgradeFee").html(changeUpgradeFee + "元" + ticketChange.getNumberInfo(number));
            }

            $("#tbconfirm").html(confirmInfo);
        },
        getNumberInfo:function(number){
            if(number > 1){
                return "*" + number;
            }else{
                return "";
            }
        },
        getTripType : function(data){
            var tripType = "";
            switch(data)
            {
                case "1":
                    tripType = "单程";
                    break;
                case "2":
                    tripType = "往返";
                    break;
                case "4":
                    tripType = "多程";
                    break;
            }
            return tripType;
        },
        showWait: function (obj) {
            $(obj).html("<tr><td colspan=\"15\" style=\"height: 70px;text-align: center\"><i class=\"fa fa-cog fa-spin fa-lg fa-fw fa-3x\"></i><span class=\"sr-only\">Loading...</span></td></tr>");
        },
        onNoData: function (obj) {
            $(obj).html("<tr><td colspan=\"15\" style=\"height: 70px;text-align: center\">没有找到数据</td></tr>");
        },
        serachFlight :function(vmFlightSerach){
            var url = "${ctx}/order/flight/change/search";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(vmFlightSerach),
                success: function (data) {
                    if (data.message == "Success") {
                        if (data.data.response.length > 0) {
                            ticketChange.combinetable(data.data.response);
                        } else {
                            ticketChange.onNoData($("#tbfight"));
                        }
                    }else {
                        alert(data.data.msg);
                        ticketChange.onNoData($("#tbfight"));
                        console.log(data);
                    }
                },
                error:function(data){
                    ticketChange.onNoData($("#tbfight"));
                }
            });
        }
    }


    //金额小于0 显示待计算
    function setPrice(price) {
        if (!isNaN(price)) {
            var priceData = parseFloat(price);
            if (priceData < 0) {
                return "待计算";
            }
        }
        return price;
    }


    function openpolicy(obj) {   //退改签政策
        $("#policyContent").html($(obj).next().html());
        $('#rebookFeeDiv').modal();
        return false;
    }

    var timeselect = {   //航班筛选工具
        timeselect: function () {   //选择新航班筛选
            var arrdate = timeselect.dateslect();
            if ($("#tbfight").find("tr").length && $("#tbfight").find("tr").length > 0) {
                $("#tbfight").find("tr").each(function () {
                    if (timeselect.timeCompare($(this).find("span[name=departtime]").html(), arrdate)) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            }
        },
        timeCompare: function (datetime, arrDate) {
            var isData = false;
            var date = new Date(datetime);
            if (arrDate.length == 0 || arrDate.indexOf(date.getHours()) != -1) {
                isData = true;
            }
            return isData;
        },
        dateslect:function(){
            var arrdate =new Array();
            $('input[name=chktitle]').each(function () {
                if ($(this).prop('checked')) {
                    var chkvalue = $(this).val();
                    for (var i = (chkvalue - 1) * 6; i < chkvalue * 6; i++) {
                        arrdate.push(i);
                    }
                }
            });
            return arrdate;
        }
    }

    var btnconfirm = {
        confirm:function () {
            layer.confirm("确认提交改签申请？", {
                btn: ["确认","取消"] //按钮
            }, function(index){
                layer.close(index);
                btnconfirm.submit(layer.load(1));
            });
        },
        submit:function(index){
            var vmRebookRequest = btnconfirm.getRequest();
            btnconfirm.submitAjax(vmRebookRequest,index);
        },
        getRequest:function(){
            var index =  $("#noticeTBody").find("input[type=checkbox]:checked:first").attr("data-index");   //获取下标
            var segment = $("#hidevalue" + index);
            var rdconfirm = $("input[name='rdfight']:checked");  //航班信息
            var vmRebookRequest = {};
            vmRebookRequest.tyUserId = "${tyUserId!""}";
            vmRebookRequest.ctripUId = "${ctripUId!""}";
            vmRebookRequest.source = "offline";
            vmRebookRequest.flightData = rdconfirm.attr("data-flightData");
            //vmRebookRequest.orderId = "${ordernumber!""}";
            vmRebookRequest.orderId = segment.attr("data-orderid");
            vmRebookRequest.subClassData = rdconfirm.attr("data-subClassData");
            vmRebookRequest.passengers = ticketChange.getpassenger();
            vmRebookRequest.rebookInformation = ticketChange.getpeopleEneity(segment);
            return vmRebookRequest;
        },
        submitAjax :function(vmRebookRequest,index){
            var url = "${ctx}/order/flight/change/pushchange";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(vmRebookRequest),
                success: function (data) {
                    layer.close(index);
                    if (data.message == "Success") {
                        layer.alert("改签申请成功！");
                        window.close();
                    }else {
                        layer.alert("改签申请失败！：" + data.data.msg);
                    }
                },
                error:function(data){
                    layer.close(index);
                    layer.alert("改签申请失败！");
                }
            });
        }
    }




</script>

<style type="text/css">
    <style type="text/css">

    .tdheight {
        　　 line-height: 28px;
    }

    .table th, .table td {
        text-align: center;
        vertical-align: middle !important;
    }

    table tr td {
        font-family: "微软雅黑";
        padding: 10px;
    }

    table tr
    {
        height: 40px;
        text-align: center;
    }

    th{
        font-weight: normal;
    }

    table input[type=checkbox]
    {
        margin-left: 20px;
    }
    table input[type=radio]
    {
        margin-left: 20px;
    }

    .nav-pills>li.active>a, .nav-pills>li.active>a:focus, .nav-pills>li.active>a:hover {
        color: #fff;
        background-color: #386f96;
    }

    .code_btn_0{
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .btn-blue{
        background: #19a0f0;
        height: 32px;
        border-radius: 4px;
        color: #fff;
        border: 0;
        cursor: pointer;
    }

    .sptitle{
        margin-left: 10px;
    }

    table input.chktitle {
        margin: -1px 0 0;
        margin-top: 1px\9;
        line-height: normal;
    }

    #tbfight tr:hover {
        background-color: #FFF5EE;
    }

    .fa-gear:before, .fa-cog:before {
        content: "\f013";
    }

    .trhover:hover {
        background-color: #f5f5f5;
    }
</style>
