<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>火车票管理后台</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>

</head>
<style type="text/css">
    .spanRed span {
        color: #999 !important;
    }
</style>

<body>
<!--阴影层-->
<div class="shadow" style="display: none;" ></div>
<!--退票说明-->
<div class="tpsm" style="display:none;">
    <h4>退票说明</h4>
    <ul>
        <li>
            <span>车票</span>
            <small>&yen;&nbsp;78.5</small>
        </li>
        <li>
            <span>预计手续费</span>
            <small>&yen;&nbsp;0</small>
        </li>
        <li>
            <span>预计退款金额</span>
            <small>&yen;&nbsp;78.5</small>
        </li>
    </ul>
    <div class="txt"><span>最终退款金额以铁路局实退为准</span></div>
    <div class="btnbox">
        <a href="###">取消</a>
        <a href="###">确定退票</a>
    </div>
</div>

<#--<dl class="tp">-->
    <#--<dt>请选择乘客后进行退票</dt>-->
    <#--<dd><a href="###">确定退票</a></dd>-->
<#--</dl>-->

<!--头部-->
<div class="header">
    <dl>
        <dt><a href="###">订单管理</a></dt>
        <dd>
            <span><a href="###">返回首页</a></span>
            <span><a href="###">快速切换</a></span>
            <span><a href="###">口令</a></span>
            <span><a href="###">注销</a></span>
        </dd>
    </dl>
</div>

<div class="WrapDetails" id="orderrefundapplybox" >
    <div class="Activity"><a href="###">订单详情</a><span>&gt;</span></div>
    <div class="OrderBox">
        <div class="OrderTit">订单号：${orderId}</div>
        <div class="OrderWrap">
            <dl class="TopSummary">
                <dt>退票（请勾选需退人员）</dt>
                <dd id="priceText">
                    <span id="total" v="0">预计退款：0元</span>
                    <span id="handlingFee" v="0">手续费总金额：0元</span>
                    <span id="passengerNum" v="0">已勾选乘客人数：0人</span>
                </dd>
            </dl>
            <#--//改签票-->
            <#if tickets.changeTickets?? && tickets.changeTickets?size gt 0 >
                <#list tickets.changeTickets as ticket>
                    <table class="OrderTable">
                        <tr>
                            <th style="width:66px;">行程类型</th>
                            <th style="width:68px;">类型</th>
                            <th style="width:66px;">乘车日期</th>
                            <th style="width:62px;">车次</th>
                            <th style="width:104px;">出发站/时间</th>
                            <th style="widrth:104px;">到达站/时间</th>
                            <th style="width:104px;">耗时/经停</th>
                            <th style="width:111px;">取票号</th>
                            <th style="width:40px;">人数</th>
                            <th style="width:120px;">检票口</th>
                            <th style="width:105px;">状态</th>
                        </tr>
                        <tr>
                            <td><i>${ticket.sectionStr!""}</i></td>
                            <td>改签票</td>
                            <td><i>${ticket.vmTicketDate!""}</i></td>
                            <td><i>${ticket.trainNumber!""}</i></td>
                            <td><a href="###">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}</td>
                            <td><a href="###">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}</td>
                            <td>${ticket.timeConsuming!""}<br/>
                                <a href="javascript:void(0);" class="searchStation">查经停</a>
                                <div class="time" style="width: 424px;display: none;">
                                    <div class="Top">
                                        <span>车站</span>
                                        <span>到达</span>
                                        <span>出发</span>
                                        <span>停留</span>
                                    </div>
                                    <div class="list">
                                        <#if ticket.stopStations?? && ticket.stopStations?size gt 0>
                                            <#list ticket.stopStations as stopStation>
                                                <#if stopStation.stationFlag == 0||stopStation.stationFlag == 2||stopStation.stationFlag == 3 >
                                                    <div class="blue" style="margin-top: 27px;">
                                                        <#if stopStation.stationFlag == 2>
                                                            <span class="blueTxt">${stopStation.stationName}</span>
                                                            <span>${stopStation.arrivalTime}</span>
                                                            <span class="blueTxt">${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        <#elseif stopStation.stationFlag == 3>
                                                            <span class="blueTxt">${stopStation.stationName}</span>
                                                            <span class="blueTxt">${stopStation.arrivalTime}</span>
                                                            <span>${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        <#elseif stopStation.stationFlag == 0>
                                                            <span>${stopStation.stationName}</span>
                                                            <span>${stopStation.arrivalTime}</span>
                                                            <span>${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        </#if>
                                                    </div>
                                                <#elseif stopStation.stationFlag == 1>
                                                    <div class="chufa" style="margin-top: 27px;">
                                                        <span>${stopStation.stationName}</span>
                                                        <span>${stopStation.arrivalTime}</span>
                                                        <span>${stopStation.startTime}</span>
                                                        <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                            <span> ${stopStation.stopMinutes}</span>
                                                        <#else>
                                                            <span>${stopStation.stopMinutes}分钟</span>
                                                        </#if>
                                                    </div>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </td>
                            <td><i>${ticket.electronicNum!""}</i></td>
                            <td><i>${ticket.ticketCount!""}</i></td>
                            <td style="text-align:left;padding:0 5px;">${ticket.ticketCheck!""}</td>
                            <td><i class="red">--</i></td>
                        </tr>
                    </table>
                    <dl class="PI">
                        <dt>
                            <span>乘客姓名</span>
                            <span>票类型</span>
                            <span>证件类型</span>
                            <span>证件号</span>
                            <span>坐席</span>
                            <span>票价</span>
                            <span>票状态</span>
                            <span>退票手续费</span>
                        </dt>
                        <#if ticket.trainTickets?? && ticket.trainTickets?size gt 0 >
                            <#list ticket.trainTickets as trainTicket>
                            <#--改签表状态 未知-0 待支付补款-10 待推送到退改签系统-20 改签中-30 改签成功-40 改签失败-50 退票中-60 退票成功-70 退票失败-80"-->
                                 <dd <#if trainTicket.ticketState==20||trainTicket.ticketState==30||trainTicket.ticketState==60||trainTicket.ticketState==70>class="spanRed"</#if> >
                                    <em v="${trainTicket.longTrainNo}"
                                    <#if trainTicket.ticketState==20||trainTicket.ticketState==30||trainTicket.ticketState==60||trainTicket.ticketState==70> <#else>class="cb1"</#if>
                                    ></em>
                                    <span>${trainTicket.realName!""}</span>
                                    <span>
                                             <#switch trainTicket.getPassengerType()!"">
                                                 <#case 1>成人<#break>
                                                 <#case 2>儿童<#break >
                                                 <#case 3>学生<#break >
                                             </#switch>
                                    </span>
                                    <span>
                                        ${trainTicket.passportType!""}
                                    </span>
                                    <span>${trainTicket.passportNumber!""}<i></i></span>
                                    <span name="spanTicketPrice" price="${trainTicket.ticketPrice!""}">
                                            <#if trainTicket.seatName??>
                                                ${trainTicket.seatName!""}
                                            <#else>
                                                -
                                            </#if>
                                    </span>
                                    <span>
                                            <#if trainTicket.realTicketPrice??>
                                                ${trainTicket.realTicketPrice!""} 元<small>（支付:${trainTicket.ticketPrice!""}元）</small>
                                            <#else>
                                                -
                                            </#if>
                                    </span>
                                    <span>
                                                <#--ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功-->
                                                <#--改签表状态 未知-0 待支付补款-10 待推送到退改签系统-20 改签中-30 改签成功-40 改签失败-50 退票中-60 退票成功-70 退票失败-80"-->
                                                <#switch trainTicket.ticketState!"">
                                                    <#case 0>未知<#break>
                                                    <#case 10>待支付补款<#break >
                                                    <#case 20>待推送到退改签系统<#break >
                                                    <#case 30>改签中<#break >
                                                    <#case 40>改签成功<#break >
                                                    <#case 50>改签失败<#break >
                                                    <#case 60>退票中<#break >
                                                    <#case 70>退票成功<#break >
                                                    <#case 80>退票失败<#break >
                                                </#switch>
                                        </span>
                                    <span name="spanHandlingFee" fee="${trainTicket.handlingFee!""}" refundPrice="${trainTicket.refundPrice!""}">
                                        <#if trainTicket.handlingFee??&&trainTicket.handlingFee != "0">
                                            ${trainTicket.handlingFee!""}（票价的${trainTicket.handlingPercentage!""}%）
                                        <#else>
                                            0
                                        </#if>
                                    </span>
                                </dd>
                            </#list>
                        </#if>
                    </dl>
                </#list>
            </#if>

            <#--//原票-->
            <#if tickets.trainRoutes?? && tickets.trainRoutes?size gt 0 >
                <#list tickets.getTrainRoutes() as ticket>
                    <table style="margin-top:20px;" class="OrderTable">
                        <tr>
                            <th style="width:66px;">行程类型</th>
                            <th style="width:68px;">类型</th>
                            <th style="width:66px;">乘车日期</th>
                            <th style="width:62px;">车次</th>
                            <th style="width:104px;">出发站/时间</th>
                            <th style="widrth:104px;">到达站/时间</th>
                            <th style="width:104px;">耗时/经停</th>
                            <th style="width:111px;">取票号</th>
                            <th style="width:40px;">人数</th>
                            <th style="width:120px;">检票口</th>
                            <th style="width:105px;">状态</th>
                        </tr>

                        <tr>
                            <td><i>${ticket.sectionStr!""}</i></td>
                            <td>原票</td>
                            <td><i>${ticket.vmTicketDate!""}</i></td>
                            <td><i>${ticket.trainNumber!""}</i></td>
                            <td><a href="###">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}</td>
                            <td><a href="###">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}</td>
                            <td>${ticket.timeConsuming!""}<br/>
                                <a href="javascript:void(0);" class="searchStation">查经停</a>
                                <div class="time" style="width: 424px;display: none;">
                                    <div class="Top">
                                        <span>车站</span>
                                        <span>到达</span>
                                        <span>出发</span>
                                        <span>停留</span>
                                    </div>
                                    <div class="list">
                                        <#if ticket.stopStations?? && ticket.stopStations?size gt 0>
                                            <#list ticket.stopStations as stopStation>
                                                <#if stopStation.stationFlag == 0||stopStation.stationFlag == 2||stopStation.stationFlag == 3 >
                                                    <div class="blue" style="margin-top: 27px;">
                                                        <#if stopStation.stationFlag == 2>
                                                            <span class="blueTxt">${stopStation.stationName}</span>
                                                            <span>${stopStation.arrivalTime}</span>
                                                            <span class="blueTxt">${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        <#elseif stopStation.stationFlag == 3>
                                                            <span class="blueTxt">${stopStation.stationName}</span>
                                                            <span class="blueTxt">${stopStation.arrivalTime}</span>
                                                            <span>${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        <#elseif stopStation.stationFlag == 0>
                                                            <span>${stopStation.stationName}</span>
                                                            <span>${stopStation.arrivalTime}</span>
                                                            <span>${stopStation.startTime}</span>
                                                            <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                                <span> ${stopStation.stopMinutes}</span>
                                                            <#else>
                                                                <span>${stopStation.stopMinutes}分钟</span>
                                                            </#if>
                                                        </#if>
                                                    </div>
                                                <#elseif stopStation.stationFlag == 1>
                                                    <div class="chufa" style="margin-top: 27px;">
                                                        <span>${stopStation.stationName}</span>
                                                        <span>${stopStation.arrivalTime}</span>
                                                        <span>${stopStation.startTime}</span>
                                                        <#if stopStation_index == 0|| stopStation_index == ticket.stopStations?size-1>
                                                            <span> ${stopStation.stopMinutes}</span>
                                                        <#else>
                                                            <span>${stopStation.stopMinutes}分钟</span>
                                                        </#if>
                                                    </div>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </td>
                            <td><i>${ticket.electronicNum!""}</i></td>
                            <td><i>${ticket.ticketCount!""}</i></td>
                            <td style="text-align:left;padding:0 5px;">${ticket.ticketCheck!""}</td>
                            <td><i class="red">${ticket.trainStatus!"--"}</i></td>
                        </tr>

                    </table>
                    <dl class="PI">
                        <dt>
                            <span>乘客姓名</span>
                            <span>票类型</span>
                            <span>证件类型</span>
                            <span>证件号</span>
                            <span>坐席</span>
                            <span>票价</span>
                            <span>票状态</span>
                            <span>退票手续费</span>
                        </dt>
                        <#if ticket.trainTickets?? && ticket.trainTickets?size gt 0 >
                            <#list ticket.trainTickets as trainTicket>
                                <#--ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功-->
                               <dd <#if trainTicket.ticketState==0||trainTicket.ticketState==3 > <#else>class="spanRed"</#if> >
                                   <em v="${trainTicket.longTrainNo}"
                                        <#if trainTicket.ticketState==0||trainTicket.ticketState==3 > class="cb1"</#if> ></em>
                                   <span>${trainTicket.realName!""}</span>
                                   <span>
                                            <#switch trainTicket.getPassengerType()!"">
                                                <#case 1>成人<#break>
                                                <#case 2>儿童<#break >
                                                <#case 3>学生<#break >
                                            </#switch>
                                   </span>
                                   <span>
                                       ${trainTicket.passportType!""}
                                   </span>
                                   <span >${trainTicket.passportNumber!""}<i></i></span>
                                   <span name="spanTicketPrice" price="${trainTicket.ticketPrice!""}">
                                           <#if trainTicket.seatName??>
                                               ${trainTicket.seatName!""}
                                           <#else>
                                               -
                                           </#if>
                                   </span>
                                   <span>
                                           <#if trainTicket.realTicketPrice??>
                                               ${trainTicket.realTicketPrice!""} 元<small>（支付:${trainTicket.ticketPrice!""}元）</small>
                                           <#else>
                                               -
                                           </#if>
                                   </span>
                                   <span>
                                         <#--ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功-->
                                         <#--改签表状态 未知-0 待支付补款-10 待推送到退改签系统-20 改签中-30 改签成功-40 改签失败-50 退票中-60 退票成功-70 退票失败-80"-->

                                         <#switch trainTicket.ticketState!"">
                                             <#case 0>出票成功<#break>
                                             <#case 1>退票成功<#break >
                                             <#case 2>退票中<#break >
                                             <#case 3>退票失败<#break >
                                             <#case 4>改签中<#break >
                                             <#case 5>已改签(作废)<#break >
                                         </#switch>
                                       </span>
                                   <span name="spanHandlingFee" fee="${trainTicket.handlingFee!""}" refundPrice="${trainTicket.refundPrice!""}">
                                       <#if trainTicket.handlingFee??&&trainTicket.handlingFee != "0">
                                           ${trainTicket.handlingFee!""}（票价的${trainTicket.handlingPercentage!""}%）
                                       <#else >
                                           0
                                       </#if>
                                   </span>
                               </dd>
                            </#list>
                        </#if>
                    </dl>
                </#list>
            </#if>

            <dl class="TopSummary">
                <dt>退票规则</dt>
            </dl>
            <table class="Algorithm">
                <tr>
                    <th>申请退票距离发车时间</th>
                    <th>手续费</th>
                </tr>
                <tr>
                    <td>&gt;15天</td>
                    <td>0</td>
                </tr>
                <tr>
                    <td>48小时≤时间≤15天</td>
                    <td>5%</td>
                </tr>
                <tr>
                    <td>24小时≤时间&lt;48小时</td>
                    <td>10%</td>
                </tr>
                <tr>
                    <td>&lt;24小时</td>
                    <td>20%</td>
                </tr>
            </table>
            <dl class="tabTips">
                <dt>改签至春运期间（2月21号-3月1号）车票，退票收取手续费为票价的20%  </dt>
                <dd>
                    <a href="javascript:void(0)" onclick="applyRefund()">申请退票</a>
                </dd>
            </dl>

        </div>
    </div>
</div>

</body>

<script  type="text/javascript" src="${ctx}/static/vue/vue.js" ></script>
<script  type="text/javascript" src="${ctx}/static/vue/vue-resource.js" ></script>
<script  type="text/javascript" src="${ctx}/static/js/order/ol_refundapply.js" ></script>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>

<script type="text/javascript" >

    $(function (){
        bindSearchEvent();
    });

    /**
     * 绑定复选框事件
     */
    function  bindSearchEvent() {
        $(".PI").click(function (ev) {
            var ev = ev || window.event;
            var target = ev.target || ev.srcElement;
            if (target.nodeName.toLowerCase() == "em") {
                var parentNode=$(target).parent();
                if(parentNode&&$(parentNode).attr("class")=="spanRed") {
                    //置灰不可选
                }else {
                    var dd=$(target).closest("dd");
                    if(dd) {
                        var totalNum=$("#total").attr("v");
                        var handlingFee=$("#handlingFee").attr("v");;
                        var passengerNum=$("#passengerNum").attr("v");;

                        var spanTicketPrice=$(dd).find("span[name='spanTicketPrice']").attr("price");
                        var spanHandlingFee=$(dd).find("span[name='spanHandlingFee']").attr("fee");
                        var refundPrice=$(dd).find("span[name='spanHandlingFee']").attr("refundPrice");

                        var newtotalNum;
                        var newhandlingFee;
                        var newpassengerNum;

                        if($(dd).find("em").attr("class")=="cb2"){
                            $(dd).find("em").attr("class", "cb1");
                            //减少
                            if(totalNum != ""){
                                newtotalNum = totalNum - refundPrice;
                                newhandlingFee = handlingFee - spanHandlingFee;
                                newpassengerNum = passengerNum - 1;
                            }
                        }else {
                            $(dd).find("em").attr("class", "cb2");
                            //增加 Number(value)
                            newtotalNum =  Number(totalNum) + Number(refundPrice);
                            newhandlingFee = Number(handlingFee) + Number(spanHandlingFee);
                            newpassengerNum = Number(passengerNum) + 1;
                        }
                        var html='<span id="total" v="'+newtotalNum+'">预计退款：'+newtotalNum+'元</span><span id="handlingFee" v="'
                                +newhandlingFee+'"> 手续费总金额：'+ newhandlingFee
                                +'元</span><span id="passengerNum" v="'+newpassengerNum+'"> 已勾选乘客人数：'+newpassengerNum+'人</span>';
                        $("#priceText").html("");
                        $("#priceText").html(html);
                    }
                }
            }
        })
    }

    /**
     * 申请退票
     */
    function applyRefund() {
        var request={};
        request.partnerName="${partnerName}";
        request.orderNumber="${orderId}";
        var longTrainNos = new Array();
        $(".cb2").each(function(n,v){
            longTrainNos.push($(v).attr("v"))
        });
        if(longTrainNos.length === 0){
            alert("请选择需退票的乘客");
            return;
        }
        request.longElecNums=longTrainNos.join();
        $.ajax({
            type: "POST",
            url: "${ctx}/order/train/refund/applyRefundTicket",
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(request),
            success: function (data) {
                if(data.status === 0){
                    alert(data.message);
                    location.reload();
                }
            },
            error:function(data){

            }
        });
    }

</script>

</html>
