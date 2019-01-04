<!DOCTYPE html>

<html lang="en">
<#include "../common/header.ftl">
<@header pageTtle="私人定制二推" headerVisible=true pageWidth=1200></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>

<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/js/timepicker-addon.js"></script>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>


<body>
    <div class="master_content">
        <div class="master_contentplaceholder">
            <div class="pubglobal_main" style="width: 1200px; background-color:#f5f5f5;margin-bottom:20px">
                <div style="background-color: #FFFFFF; margin:10px 10px 0;">
                    <table style="background-color: #FFFFFF; width: 1180px;" class="table-style3">
                        <tr>
                            <td style="height: 35px; width: 122px; text-align: right;">
                                订单号：
                            </td>
                            <td style="height: 35px; width: 177px; text-align: left;">
                                <a href="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ShowOrderDetailNew.aspx?OrderID=${orderId}">${orderId}</a>
                                <input id="orderId" type="hidden" value="${orderId}">
                                <input id="ticketNum" type="hidden" value="${ticketNum}">
                                <input id="partnerOrderId" type="hidden" value="${partnerOrderId}">
                                <input id="partnerName" type="hidden" value="${partnerName}">
                                <input id="seatName" type="hidden" value="${seatNo}">
                            </td>
                            <td>
                                ${ticketTime} &nbsp;${trainNumber} &nbsp;${from} - ${to} &nbsp;${seatNo}&nbsp;${ticketNum}张
                            </td>
                            <td style="height: 35px; width: 100px; text-align: right;">
                                联系人姓名：
                            </td>
                            <td style="height: 35px; width: 120px; text-align: left;">
                                ${contactName}
                            </td>
                            <td style="height: 35px; width: 100px; text-align: right;">
                                联系人电话：
                            </td>
                            <td style="height: 35px; width: 120px; text-align: left;">
                                ${contactMobile}
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="display: block">
                    <table style="background-color: #FFFFFF; width: 1180px;" align="center" class="table-style3">
                        <tbody>
                        <tr align="center">
                            <td style="height: 35px; width: 122px; text-align: right;">
                                配送地址：
                            </td>
                            <td colspan="5" style="height: 35px; text-align: left;">
                                ${contactAddress}
                                <#--<input name="ctl00$MainContentPlaceHolder$txtContactMobile" type="text" value="" id="txtContactMobile" class="input-box" style="width:650px;height:24px;">-->
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: right; height: 35px; width: 122px">
                                取消原因：
                            </td>
                            <td style="width: 190px; text-align: left">
                                <input type="text" id="cancelReason" style="width: 160px">
                            </td>
                            <td style="width: 150px; height: 35px; text-align: left;">
                                <input type="submit" name="" id="cancelbutton" value="取消推荐" onclick="twoPushNoTicket()" class="btn-blue w100" >
                            </td>
                            <td style="text-align: right; height: 35px; width: 122px">
                                暂缓原因：
                            </td>
                            <td style="width: 190px; text-align: left">
                                <input type="text" id="cachingReason" style="width: 160px">
                            </td>
                            <td style="height: 35px; text-align: left;">
                                <input type="submit" name="" id="cachingbutton" value="暂缓" onclick="cachingTrain()" class="btn-blue w100" >
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div style="background-color: #FFFFFF; width: 1180px; margin:10px 10px 0 10px;padding: 10px 0;text-align:left;">
                    <table style="background-color: #FFFFFF; width: 1180px;" cellspacing="0" cellpadding="0" class="table-style3">
                        <tr>
                            <td style="text-align: right;width: 122px">自检结果：</td>
                            <td style="text-align: left;padding-bottom: 15px;">
                                <table style="background: #f5f5f5;width: 900px;" cellspacing="1" cellpadding="1">
                                    <tr>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">发车时间</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">车次</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">${seatNo}下</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">${seatNo}中</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">${seatNo}上</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">自检时间</th>
                                        <th style="height:30px;background: #fff;text-align: center;font-weight: 400">操作</th>
                                    </tr>
                                    <#list checkTrains as train>
                                        <#--<#if (checkTrains?size - 3) <= train_index&&train_index < (checkTrains?size) >-->
                                            <tr>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.ticketDate}</td>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.trainNo}</td>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.downNumber}</td>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.midNumebr}</td>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.upNumer}</td>
                                                <td style="height: 30px;background: #fff;text-align: center">${train.lastModifyTime}</td>
                                                <td style="height: 30px;background: #fff" align="center">
                                                    <#if (train.downNumber =='扣位失败!'&&train.midNumebr =='扣位失败!'&&train.upNumer =='扣位失败!')||(train.downNumber =='票已售完!'&&train.midNumebr =='票已售完!'&&train.upNumer =='票已售完!')||(train.downNumber =='票已售完!'&&train.midNumebr == 0 &&train.upNumer =='票已售完!')||(train.downNumber =='扣位失败!'&&train.midNumebr == 0 &&train.upNumer =='扣位失败!') >
                                                        <a class="qp_btn2no" onclick="return false;">推荐</a>
                                                    <#else>
                                                        <a class="qp_btn2" onclick="tryTwoPush('${train.ticketDate} ${train.ticketTime}','${train.trainNo}','${train.downNumber}','${train.midNumebr}','${train.upNumer}','${train.startStation}','${train.endStation}')">推荐</a>
                                                    </#if>

                                                    <#--<a class="qp_btn2" onclick="addCheckTrain('${train.ticketDate} ${train.ticketTime}','${train.trainNo}','${train.startStation}','${train.endStation}','searchByNum')">推荐</a>-->
                                                </td>
                                            </tr>
                                        <#--</#if>-->
                                    </#list>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: right; height: 35px; width: 122px;border-top:1px solid #f5f5f5;">铺位选择：</td>
                            <td style="text-align: left;border-top:1px solid #f5f5f5;">
                                <table style="background: #ffffff;width: 1000px;" id="optionContainer">

                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                <#--<div style="background-color: #FFFFFF; width: 1180px; margin:10px 10px 0 10px;text-align:left;" id="trains">-->
                        <#--<table style="background-color: #FFFFFF; width: 1180px;" cellspacing="0" cellpadding="0" class="table-style3" id="timeTable">-->
                            <#--<tr bgcolor="#386f96" id="timetrId">-->
                                    <#--<td style="width:100px;height:35px;text-align:center" >-->
                                        <#--<a href="#" style="color:#fff;text-decoration: none" onclick="changeDateForS2S('${date}','${from}','${to}','before')">&lt;</a>-->
                                    <#--</td>-->
                                    <#--<td style="color:#fff;text-align: center;font-size: 16px;" id="timechangeid" ><i class="icon-calendar"></i> ${date} &nbsp;&nbsp;${from} - ${to}</td>-->
                                    <#--<td style="width:100px;height:35px;text-align:center">-->
                                        <#--<a href="#" style="color:#fff;text-decoration: none" onclick="changeDateForS2S('${date}','${from}','${to}','after')">&gt;</a>-->
                                    <#--</td>-->
                            <#--</tr>-->
                        <#--</table>-->
                    <#--<div id="div1">-->
                        <#--<table id="tableTicket" style="background-color: #FFFFFF; width: 1160px;margin: 0 10px" cellspacing="0" cellpadding="0" class="table-checi" >-->
                            <#--<tr bgcolor="#f4f4f4">-->
                                <#--<th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">车次信息</th>-->
                                <#--<th style="width: 120px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">发/到时间</th>-->
                                <#--<th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">发/到站</th>-->
                                <#--<th style="width: 200px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">运行时长</th>-->
                                <#--<th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">参考价</th>-->
                                <#--<th style="width: 130px;height:30px;text-align: right;color:#666;font-weight: 400;padding: 0 25px;">余票</th>-->
                                <#--<th style="height:30px;text-align: center;color:#666;font-weight: 400;padding: 0 25px">操作</th>-->
                            <#--</tr>-->
                                <#--<div id="dateTime">-->
                                    <#--<#list searchS2Ss as train>-->
                                    <#--<#list train.Seats as seat>-->
                                     <#--<tr id="testid">-->
                                        <#--<#if seat_index==0>-->
                                            <#--<td style="text-align:center;" valign="top" rowspan="${train.Seats?size}" >-->
                                                <#--<strong>${train.trainNo}</strong>-->
                                            <#--</td>-->
                                            <#--<td style="text-align:center;" valign="top" rowspan="${train.Seats?size}">-->
                                                <#--<strong>${train.StartTime}</strong>-->
                                                <#--<p class="time">${train.ArriveTime}-->
                                                    <#--<#if train.DayDif gt 0>-->
                                                        <#--<em>+${train.DayDiff}</em>-->
                                                    <#--</#if>-->
                                                <#--</p>-->
                                            <#--</td>-->
                                            <#--<td style="text-align:center;" valign="top" rowspan="${train.Seats?size}">-->
                                                <#--<p class="from"><i class="shi">始</i> ${train.FromStationName}</p>-->
                                                <#--<p class="to"><i class="guo">过</i> ${train.ToStationName}</p>-->
                                            <#--</td>-->
                                            <#--<td style="text-align:center;" valign="top" rowspan="${train.Seats?size}">-->
                                                <#--${train.DurationMinutes}-->
                                            <#--</td>-->
                                        <#--</#if>-->
                                        <#--<td style="text-align:center;" valign="top">-->
                                            <#--<p>-->
                                                <#--<span class="seat">${seat.SeatName}</span>-->
                                                <#--<span class="price">-->
                                                    <#--<dfn>&yen;</dfn>-->
                                                    <#--<strong>${seat.Price}</strong>-->
                                                <#--</span>-->
                                            <#--</p>-->
                                        <#--</td>-->
                                        <#--<td style="text-align:center;" valign="top" >-->
                                            <#--<p>余${seat.TicketLeft}张</p>-->
                                        <#--</td>-->
                                        <#--<td valign="middle" align="center">-->
                                            <#--<#if seat.TicketLeft gt ticketNum>-->
                                                <#--<a class="qp_btn2" onclick="recommendCheck('${date+" "+train.StartTime}','${train.trainNo}','${train.FromStationName}','${train.ToStationName}','${seat.SeatName}')">推荐</a>-->
                                            <#--<#else>-->
                                                <#--<a class="qp_btn2" disabled="true" >推荐</a>-->
                                            <#--</#if>-->
                                        <#--</td>-->
                                    <#--</tr>-->
                                    <#--</#list>-->
                                    <#--</#list>-->
                                <#--</div>-->
                        <#--</table>-->
                    <#--</div>-->
                    <div style="background-color: #FFFFFF; margin:0 10px 10px;"></div>
                <#--</div>-->
        </div>
    </div>
    <style>
        .icon-calendar{width: 16px;height: 16px;display: inline-block;vertical-align: middle;margin-top: -5px;background: url(http://images3.c-ctrip.com/chuangxin/service/icon-calendar.png) no-repeat;}
        .table-checi td{padding:10px 25px;border-bottom: 1px solid #ececec;font-size: 12px;font-family: "microsoft yaHei";line-height: 30px;}
        .table-checi  strong{font-size: 16px;}
        .table-checi .time{color:#666;}
        .table-checi .time em{color:#999;font-size: 10px;font-style: normal}
        .table-checi i{display: inline-block;vertical-align: middle;width: 14px;height: 14px;line-height: 14px;text-align: center;border: 1px solid #e0e0e0;font-size: 12px;-moz-border-radius: 4px;-webkit-border-radius: 4px;border-radius: 4px;margin-top: -3px;font-style: normal}
        .table-checi .shi{color:#2577e3;}
        .table-checi .zhong{color:#ff9913;}
        .table-checi .guo{color:#0fa400;}
        .table-checi .seat{width: 80px;margin: 0 20px 0 -100px;display: inline-block;text-align: right}
        .table-checi .price{color:#FA6700}
        .table-checi dfn{font-size: 12px;font-style:normal;margin-right: 2px;}
        .table-checi .btn-orage{height: 26px;margin: 2px 0}
        .btn-orage:disabled{background:#ccc;cursor:default;}
        .table-checi .less{color: #ff9913;font-style: normal}
        .qp_btn2 {
            display: block;
            width: 60px;
            height: 24px;
            line-height: 24px;
            line-height: 23px\0;
            -moz-border-radius: 5px;
            -webkit-border-radius: 5px;
            border-radius: 5px;
            text-align: center;
            color: #fff;
            background: #ff9913;
        }
        .qp_btn2no {
            display: block;
            width: 60px;
            height: 24px;
            line-height: 24px;
            line-height: 23px\0;
            -moz-border-radius: 5px;
            -webkit-border-radius: 5px;
            border-radius: 5px;
            text-align: center;
            color: #fff;
            background: #D8D8D8;
        }

        .layui-layer-content{ text-align:left;}

    </style>
</body>

<script type="text/javascript">

    function tryTwoPush(time,TrainNo,downNumber,midNumber,upNumber,startStation,endStation) {

        var newRow1='<td id="newRow1" style="width: 150px;"><strong style="color:#f00;">'+time+'</strong></td>';
        // var timetemp=train.ticketDate+' '+train.ticketTime;
        var searchByNum='searchByNum';
        var newRow5="<td id='newRow5'><input type='button' onclick=\"confirmTrain(\'"+time+"\',\'"+searchByNum+"\',\'"+startStation+"\',\'"+endStation+"\',\'"+TrainNo+"\')\"  value='接受推荐' class='btn-orage w80' ></td>";
        for(var i=1;i<=8;i++){
            $("#newRow"+i).remove();
        }
        var seatName=$('#seatName').val();
        // $("#s2STrains").remove();
        /*判断是站站推荐还是*/
        // if(seat === 'searchByNum' ){
        var upNum=upNumber.replace(/[^0-9]/ig,"");
        var midNum=midNumber.replace(/[^0-9]/ig,"");
        var downNum=downNumber.replace(/[^0-9]/ig,"");
        <#--console.log(${ctx});-->
        var newRow2='<td id="newRow2" style="width: 120px;text-align: right">'+seatName+'下('+downNumber+')</td><td id="newRow6" style="width: 100px;"><select style="width: 70px" id="xiaSeat"><option value="0">请选择</option></select></td>';
        var newRow3='<td id="newRow3" style="width: 120px;text-align: right">'+seatName+'中('+midNumber+')</td><td id="newRow7" style="width: 100px;"><select style="width: 70px" id="zhongSeat"><option value="0">请选择</option></select></td>';
        var newRow4='<td id="newRow4" style="width: 120px;text-align: right">'+seatName+'上('+upNumber+')</td><td id="newRow8" style="width: 100px;"><select style="width: 70px" id="shangSeat"><option value="0">请选择</option></select></td>';
        $('#optionContainer').append(newRow1);
        $('#optionContainer').append(newRow2);
        $('#optionContainer').append(newRow3);
        $('#optionContainer').append(newRow4);
        $('#optionContainer').append(newRow5);

        if((downNumber==='扣位失败!')||(downNumber==='票已售完!')){
            // $("#newRow"+6).remove();
        }else{
            for(var i = 1;i<=downNum;i++){
                $("#xiaSeat").append("<option value='"+i+"'>"+i+"</option>");
            }
        }
        if((midNumber==='扣位失败!')||(midNumber==='票已售完!')){
            // $("#newRow7").remove();
        }else{
            for (var i = 1; i <= midNum; i++) {
                $("#zhongSeat").append("<option value='" + i + "'>" + i + "</option>");
            }
        }
        if((upNumber==='扣位失败!')||(upNumber==='票已售完!')){
            // $("#newRow8").remove();
        }else{
            for(var i = 1;i<=upNum;i++){
                $("#shangSeat").append("<option value='"+i+"'>"+i+"</option>");
            }
        }

    }

    function addCheckTrain(time,TrainNo,fromCity,toCity,seat) {
        var request={};
        request.trainNo=TrainNo;
        request.ticketTime=time;
        request.fromCity=fromCity;
        request.to=toCity;
        request.seat=seat;
        request.partnerOrderId=$('#orderId').val();
        var url = "${ctx}/two/addCheckTrain.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                console.log(data);
                if(data.data.response.ret==0){
                    checkId=data.data.response.checkId;
                    //每隔五秒发一次
                    clearTimeout(meter);//关掉上次请求
                    //loading层
                    var ii = layer.load();
                    meter=setInterval(function CheckTrainTicket() {
                        if(currentIndex>=10){
                            currentIndex = 0;
                            clearTimeout(meter);
                            layer.msg('请求超时！！！');
                            return;
                        }
                        var request={};
                        var orderId=$('#orderId').val();
                        request.partnerOrderId=orderId;
                        request.checkId=checkId;
                        var url = "${ctx}/two/CheckTrainTicket.json";
                        $.ajax({
                            type : 'POST',
                            url : url,
                            contentType : 'application/json',
                            dataType : 'json',
                            data : JSON.stringify(request),
                            success : function(data){
                                console.log(request);
                                console.log(data);
                                currentIndex++;
                                if(data.data.response.ret==0){
                                    //关闭循环
                                    layer.close(ii);
                                    currentIndex = 0;
                                    clearTimeout(meter);
                                    var train=data.data.response.checkResultInfo[0];
                                    var newRow1='<td id="newRow1" style="width: 150px;"><strong style="color:#f00;">'+train.ticketDate+' '+train.trainNo+'</strong></td>';
                                    // var newRow5="<td id='newRow5'><input type='button' onclick=\"confirmTrain(\'"+time+"\',\'"+seat+"\',\'"+toCity+"\',\'"+TrainNo+"\',\'"+fromCity+"\')\"  value='接受推荐' class='btn-orage w80' ></td>";
                                    var timetemp=train.ticketDate+' '+train.ticketTime;
                                    var searchByNum='searchByNum';
                                    var newRow5="<td id='newRow5'><input type='button' onclick=\"confirmTrain(\'"+timetemp+"\',\'"+searchByNum+"\',\'"+train.startStation+"\',\'"+train.endStation+"\',\'"+train.trainNo+"\')\"  value='接受推荐' class='btn-orage w80' ></td>";
                                    for(var i=1;i<=8;i++){
                                        $("#newRow"+i).remove();
                                    }
                                    // $("#s2STrains").remove();
                                    /*判断是站站推荐还是*/
                                    // if(seat === 'searchByNum' ){
                                    var upNumer=train.upNumer;
                                    var midNumebr=train.midNumebr;
                                    var downNumber=train.downNumber;

                                    var newRow2='<td id="newRow2" style="width: 120px;text-align: right">'+ train.seatName+'下('+train.downNumber+')</td><td id="newRow6" style="width: 100px;"><select style="width: 70px" id="xiaSeat"><option value="0">请选择</option></select></td>';
                                    var newRow3='<td id="newRow3" style="width: 120px;text-align: right">'+ train.seatName+'中('+train.midNumebr+')</td><td id="newRow7" style="width: 100px;"><select style="width: 70px" id="zhongSeat"><option value="0">请选择</option></select></td>';
                                    var newRow4='<td id="newRow4" style="width: 120px;text-align: right">'+ train.seatName+'上('+train.upNumer+')</td><td id="newRow8" style="width: 100px;"><select style="width: 70px" id="shangSeat"><option value="0">请选择</option></select></td>';
                                    $('#optionContainer').append(newRow1);
                                    $('#optionContainer').append(newRow2);
                                    $('#optionContainer').append(newRow3);
                                    $('#optionContainer').append(newRow4);
                                    $('#optionContainer').append(newRow5);

                                    if((downNumber==='扣位失败!')){
                                        $("#newRow"+6).remove();
                                    }else{
                                        for(var i=1;i<=train.downNumber.substring(2,3);i++){
                                            $("#xiaSeat").append("<option value='"+i+"'>"+i+"</option>");
                                        }
                                    }
                                    if((midNumebr==='扣位失败!')){
                                        $("#newRow7").remove();
                                    }else{
                                        for (var i = 1; i <= train.midNumebr.substring(2,3); i++) {
                                            $("#zhongSeat").append("<option value='" + i + "'>" + i + "</option>");
                                        }
                                    }
                                    if((upNumer==='扣位失败!')){
                                        $("#newRow8").remove();
                                    }else{
                                        for(var i=1;i<=train.upNumer.substring(2,3);i++){
                                            $("#shangSeat").append("<option value='"+i+"'>"+i+"</option>");
                                        }
                                    }
                                    // }
                                    // else{
                                    //     var newRow2='<td id="newRow2" style="width: 100px;text-align: right">'+ vms2s.s2sSeat.seatName+'('+vms2s.s2sSeat.ticketLeft+')'+'</td><td id="newRow3" style="width: 100px;"><select style="width: 80px" id="s2sSeat"><option value="">请选择</option></select></td>';
                                    //     $('#optionContainer').append(newRow1);
                                    //     $('#optionContainer').append(newRow2);
                                    //     $('#optionContainer').append(newRow5);
                                    //     for(var i=1;i<=vms2s.s2sSeat.ticketLeft;i++){
                                    //         $("#s2sSeat").append("<option value='"+i+"'>"+i+"</option>");
                                    //     }
                                    // }
                                }

                            },
                            error : function() {
                                currentIndex++;
                                layer.msg('操作失败！');
                            }
                        });
                    },5000);
                }
                console.log(11);
            },
            error : function() {
                layer.msg('操作失败！');
            }
        });
    }


    currentIndex = 0;
    checkId = '';
    meter=0;
    //二推 二期
    // function CheckTrainTicket() {
    //
    //     if(currentIndex>=10){
    //         currentIndex = 0;
    //         clearTimeout(meter);
    //         layer.msg('请求超时！');
    //         return;
    //     }
    //     var request={};
    //     var orderId=$('#orderId').val();
    //     request.partnerOrderId=orderId;
    //     request.checkId=checkId;
    //     // request.checkId=219;
    //
    //     var url = "/two/CheckTrainTicket.json";
    //     $.ajax({
    //         type : 'POST',
    //         url : url,
    //         contentType : 'application/json',
    //         dataType : 'json',
    //         data : JSON.stringify(request),
    //         success : function(data){
    //             console.log(request);
    //             console.log(data);
    //             currentIndex++;
    //             if(data.data.response.ret==0){
    //                 //关闭循环
    //                 currentIndex = 0;
    //                 clearTimeout(meter);
    //                 var train=data.data.response.checkResultInfo[0];
    //                     var newRow1='<td id="newRow1" style="width: 150px;"><strong style="color:#f00;">'+train.ticketDate+' '+train.trainNo+'</strong></td>';
    //                     // var newRow5="<td id='newRow5'><input type='button' onclick=\"confirmTrain(\'"+time+"\',\'"+seat+"\',\'"+toCity+"\',\'"+TrainNo+"\',\'"+fromCity+"\')\"  value='接受推荐' class='btn-orage w80' ></td>";
    //                     var timetemp=train.ticketDate+' '+train.ticketTime;
    //                     var searchByNum='searchByNum';
    //                     var newRow5="<td id='newRow5'><input type='button' onclick=\"confirmTrain(\'"+timetemp+"\',\'"+searchByNum+"\',\'"+train.startStation+"\',\'"+train.endStation+"\',\'"+train.trainNo+"\')\"  value='接受推荐' class='btn-orage w80' ></td>";
    //                     for(var i=1;i<=8;i++){
    //                         $("#newRow"+i).remove();
    //                     }
    //                     // $("#s2STrains").remove();
    //                     /*判断是站站推荐还是*/
    //                     // if(seat === 'searchByNum' ){
    //                         var upNumer=train.upNumer;
    //                         var midNumebr=train.midNumebr;
    //                         var downNumber=train.downNumber;
    //
    //                         var newRow2='<td id="newRow2" style="width: 120px;text-align: right">'+ train.seatName+'下('+train.downNumber+')</td><td id="newRow6" style="width: 100px;"><select style="width: 70px" id="xiaSeat"><option value="0">请选择</option></select></td>';
    //                         var newRow3='<td id="newRow3" style="width: 120px;text-align: right">'+ train.seatName+'中('+train.midNumebr+')</td><td id="newRow7" style="width: 100px;"><select style="width: 70px" id="zhongSeat"><option value="0">请选择</option></select></td>';
    //                         var newRow4='<td id="newRow4" style="width: 120px;text-align: right">'+ train.seatName+'上('+train.upNumer+')</td><td id="newRow8" style="width: 100px;"><select style="width: 70px" id="shangSeat"><option value="0">请选择</option></select></td>';
    //                         $('#optionContainer').append(newRow1);
    //                         $('#optionContainer').append(newRow2);
    //                         $('#optionContainer').append(newRow3);
    //                         $('#optionContainer').append(newRow4);
    //                         $('#optionContainer').append(newRow5);
    //
    //                         if((downNumber==='扣位失败!')){
    //                             $("#newRow"+6).remove();
    //                         }else{
    //                             for(var i=1;i<=train.downNumber.substring(2,3);i++){
    //                                 $("#xiaSeat").append("<option value='"+i+"'>"+i+"</option>");
    //                             }
    //                         }
    //                         if((midNumebr==='扣位失败!')){
    //                             $("#newRow7").remove();
    //                         }else{
    //                             for (var i = 1; i <= train.midNumebr.substring(2,3); i++) {
    //                                 $("#zhongSeat").append("<option value='" + i + "'>" + i + "</option>");
    //                             }
    //                         }
    //                         if((upNumer==='扣位失败!')){
    //                             $("#newRow8").remove();
    //                         }else{
    //                             for(var i=1;i<=train.upNumer.substring(2,3);i++){
    //                                 $("#shangSeat").append("<option value='"+i+"'>"+i+"</option>");
    //                             }
    //                         }
    //                     // }
    //                     // else{
    //                     //     var newRow2='<td id="newRow2" style="width: 100px;text-align: right">'+ vms2s.s2sSeat.seatName+'('+vms2s.s2sSeat.ticketLeft+')'+'</td><td id="newRow3" style="width: 100px;"><select style="width: 80px" id="s2sSeat"><option value="">请选择</option></select></td>';
    //                     //     $('#optionContainer').append(newRow1);
    //                     //     $('#optionContainer').append(newRow2);
    //                     //     $('#optionContainer').append(newRow5);
    //                     //     for(var i=1;i<=vms2s.s2sSeat.ticketLeft;i++){
    //                     //         $("#s2sSeat").append("<option value='"+i+"'>"+i+"</option>");
    //                     //     }
    //                     // }
    //                 }
    //
    //         },
    //         error : function() {
    //             currentIndex++;
    //             layer.msg('请求超时！');
    //         }
    //     });
    // }

    function changeDateForS2S(time,fromCity,toCity,flag) {

        var request={};
        request.trainNo='';
        request.ticketTime=time;
        request.fromCity=fromCity;
        request.to=toCity;
        request.seat='searchByS2S';
        request.time=flag;
        console.log(request);

        var url = "${ctx}/two/queryS2SByDate.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                var vms2s=data.data.response;
                // var selectTime=vms2s.ticketTime;
                var selectTime =escape(vms2s.ticketTime);

                console.log(data.data.response);
                $("#timetrId").remove();
                $("#div1").remove();
                var title='<div id="div1">\n' +
                        '                        <table id="tableTicket" style="background-color: #FFFFFF; width: 1160px;margin: 0 10px" cellspacing="0" cellpadding="0" class="table-checi" >\n' +
                        '                            <tr bgcolor="#f4f4f4">\n' +
                        '                                <th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">车次信息</th>\n' +
                        '                                <th style="width: 120px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">发/到时间</th>\n' +
                        '                                <th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">发/到站</th>\n' +
                        '                                <th style="width: 200px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">运行时长</th>\n' +
                        '                                <th style="width: 130px;height:30px;text-align: left;color:#666;font-weight: 400;padding: 0 25px">参考价</th>\n' +
                        '                                <th style="width: 130px;height:30px;text-align: right;color:#666;font-weight: 400;padding: 0 25px;">余票</th>\n' +
                        '                                <th style="height:30px;text-align: center;color:#666;font-weight: 400;padding: 0 25px">操作</th>\n' +
                        '                            </tr>\n' +
                        '                        </table>\n' +
                        '                    </div>';
                $('#trains').append(title);
                var before='before';
                var after='after';

                // $('#timeTable').append(temp);
                var timeTablediv="<tr bgcolor='#386f96' id='timetrId'>" +
                        "<td style='width:100px;height:35px;text-align:center'><a href='#' style='color:#fff;text-decoration: none' onclick=\"changeDateForS2S(\'"+selectTime+"\',\'"+fromCity+"\',\'"+toCity+"\',\'"+before+"\')\">&lt;</a></td>"+
                        "<td style='color:#fff;text-align: center;font-size: 16px;' id='timechangeid' ><i class='icon-calendar'></i>"+selectTime+"&nbsp;&nbsp;"+fromCity+" - "+toCity+"</td>" +
                        "<td style='width:100px;height:35px;text-align:center'><a href='#' style='color:#fff;text-decoration: none' onclick=\"changeDateForS2S(\'"+selectTime+"\',\'"+fromCity+"\',\'"+toCity+"\',\'"+after+"\')\">&gt;</a></td></tr>";

                $('#timeTable').append(timeTablediv);

                for(var i=0;i<vms2s.searchS2S.length;i++){
                    var train=vms2s.searchS2S[i];
                    var Seats=train.Seats;
                    for(var j=0;j<Seats.length;j++){
                        var Seat=Seats[j];
                        // console.log(Seat);
                        var statTime=vms2s.ticketTime+' '+train.StartTime;
                        if(j==0){
                            var tds1="<tr><td style='text-align:center;' valign='top' rowspan='"+Seats.length +"'> <strong>"+ train.TrainNo+"</strong> </td>"+
                             "<td style='text-align:center;' valign='top' rowspan='"+Seats.length+"'> <strong>"+train.StartTime+"</strong> <p class='time'>"+train.ArriveTime+"<em>"+train.DayDiff+"</em></p></td>"+
                                    "<td style='text-align:center;' valign='top' rowspan='"+Seats.length+"'> <p class='from'><i class='shi'>始</i>"+train.FromStationName+"</p><p class='to'><i class='guo'>过</i>"+train.ToStationName+"</p></td>"+
                                    "<td style='text-align:center;' valign='top' rowspan='"+Seats.length+"'>"+train.DurationMinutes+"</td> " +
                                    "<td style='text-align:center;' valign='top'><p><span class='seat'>"+Seat.SeatName+"</span><span class='price'><dfn>&yen;</dfn><strong>"+Seat.Price+"</strong></span></p></td> " +
                                    "<td style='text-align:center;' valign='top' ><p>余"+Seat.TicketLeft+"张</p></td>"+
                                    "<td valign='middle' align='center'><a class='qp_btn2' onclick=\"recommendCheck(\'"+statTime+"\',\'"+train.TrainNo+"\',\'"+fromCity+"\',\'"+toCity+"\',\'"+Seat.SeatName+"\')\">推荐</a></td></tr>";
                            $('#tableTicket').append(tds1);
                        }else {
                            var tds2="<tr><td style='text-align:center;' valign='top'><p><span class='seat'>"+Seat.SeatName+"</span><span class='price'><dfn>&yen;</dfn><strong>"+Seat.Price+"</strong></span></p></td> " +
                                    "<td style='text-align:center;' valign='top' ><p>余"+Seat.TicketLeft+"张</p></td>"+
                                    "<td valign='middle' align='center'><a class='qp_btn2' onclick=\"recommendCheck(\'"+statTime+"\',\'"+train.TrainNo+"\',\'"+fromCity+"\',\'"+toCity+"\',\'"+Seat.SeatName+"\')\">推荐</a></td></tr></tr>";
                            $('#tableTicket').append(tds2);
                        }
                        // console.log(Seats)
                    }
                }
            },
            error : function() {
                showCommonHint("操作失败", 2000, "alert alert-warning");
            }
        });
    }

    function confirmTrain(time,seat,fromCity,toCity,trainNo){
        var request={};
        request.partnerOrderId=$('#partnerOrderId').val();
        request.orderId=$('#orderId').val();
        request.ticketDate=time;
        var ticketNum=$('#ticketNum').val();
        var partnerName=$('#partnerName').val();
        request.ticketNum=ticketNum;
        request.trainNo=trainNo;
        request.partnerName=partnerName;
        request.falg="1";
        request.seatName=$('#seatName').val();

        var shangSeat=$('#shangSeat').val();
        var zhongSeat=$('#zhongSeat').val();
        var xiaSeat=$('#xiaSeat').val();
        var tnum=parseInt(shangSeat) + parseInt(zhongSeat) + parseInt(xiaSeat);
            if(seat === 'searchByNum'){
                if( tnum <= ticketNum&&tnum > 0){
                    request.shangSeat=shangSeat;
                    request.zhongSeat=zhongSeat;
                    request.xiaSeat=xiaSeat;
                }else if(ticketNum <= tnum){
                    layer.msg('车票张数超出原订单，请仔细检查！！！');
                    return;
                } else if (tnum==0){
                    layer.msg('车票张数不能为零，请仔细检查！！！');
                    return;
                }
            }else {
                request.falg="2";
                var xiaSeat=$('#s2sSeat').val();

                if(xiaSeat==ticketNum){
                    request.ticketNum=xiaSeat;
                    request.seats=seat;
                }else {
                    layer.msg('车票张数不正确，请仔细检查！！！');
                    return;
                }
            }
        layer.confirm("确定推荐 "+time+"  "+trainNo+"车次\n"+fromCity+"  到  "+toCity+"  "+ticketNum+"张？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            console.log(request);
            var url = "${ctx}/two/tryTwoPush.json";
            $.ajax({
                type : 'POST',
                url : url,
                contentType : 'application/json',
                dataType : 'json',
                data : JSON.stringify(request),
                success : function(data){
                    if(data.data.response.retCode==0){
                        layer.msg('操作成功！');
                    }else{
                        layer.msg(data.data.response.msg);
                    }
                },
                error : function() {
                    layer.msg('操作失败！');
                }
            });
        }, function(){

        });

    }

    function twoPushNoTicket() {
        var request={};
        request.partnerOrderId=$('#partnerOrderId').val();
        request.orderId=$('#orderId').val();
        var cancelReason=$('#cancelReason').val();
        request.cancelReason=cancelReason;
        if(cancelReason==""||cancelReason==null||cancelReason==undefined){
            layer.msg("请输入原因！");
            return;
        }
        layer.confirm('确定取消推荐吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var url = "${ctx}/two/twoPushNoTicket.json";
            $.ajax({
                type : 'POST',
                url : url,
                contentType : 'application/json',
                dataType : 'json',
                data : JSON.stringify(request),
                success : function(data){
                    if(data.data.response.retCode==0){
                        $('#cachingbutton').attr('disabled',true);
                        layer.msg('操作成功！');
                    }else{
                        layer.msg(data.data.response.msg);
                    }
                },
                error : function() {
                    layer.msg('操作失败！');
                }
            });
        }, function(){

        });
    }

    function cachingTrain() {
        var request={};
        var cachingReason=$('#cachingReason').val();
        request.processingState="6";
        request.orderId=$('#orderId').val();
        request.partnerOrderId=$('#partnerOrderId').val();
        if(cachingReason==''||cachingReason==null||cachingReason==undefined){
            layer.msg("请输入原因！");
            return;
        }
        request.processingRemark=cachingReason;
        layer.confirm('确定暂存该订单吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var url = "${ctx}/two/cachingOrder.json";
            $.ajax({
                type : 'POST',
                url : url,
                contentType : 'application/json',
                dataType : 'json',
                data : JSON.stringify(request),
                success : function(data){
                    if(data.data.response==1){
                        $('#cancelbutton').attr('disabled',true);
                        layer.msg('操作成功！');
                    }else{
                        layer.msg(data.data.response);
                    }
                },
                error : function() {
                    layer.msg('请求失败！');
                }
            });
        }, function(){

        });
    }
</script>
</html>


