<!DOCTYPE html>

<html lang="en">
<title>退票</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/layer/theme/default/layer.css"/>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/order/detail.js"></script>
<script type="text/javascript" src="${ctx}/static/util/Watermark.js"></script>
<style>
    a{
        z-index: 5;
        position: relative;
    }
</style>
<body>
<!--阴影层-->
<div id="divshadow" class=""></div>
<!--退票说明-->
<div id="applybtn" class="tpsm" style="display:none;">
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
        <a href="javascript:void(0);" id="applycancle">取消</a>
        <a href="###">确定退票</a>
    </div>
</div>

<dl id="btncheck" class="tp"  style="display:none;">
    <dt>请选择乘客后进行退票</dt>
    <dd><a href="###">确定退票</a></dd>
</dl>

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

<div class="WrapDetails">
    <div class="Activity"><a href="###">订单详情</a><span>&gt;</span>退票申请</div>
    <div class="OrderBox">
        <div class="OrderTit">订单号：${orderNumber!""}</div>
        <div class="OrderWrap">

            <dl class="TopSummary">
                <dt>退票（请勾选需退人员）</dt>
                <dd>
                    <span>预计退款：280元</span>
                    <span>手续费总金额：50元</span>
                    <span>已勾选乘客人数：2人</span>
                </dd>
            </dl>

<#--改签信息-->
<#if tickets.changeTickets?? && tickets.changeTickets?size gt 0 >
    <#list tickets.changeTickets as ticket>
        <table class="OrderTable">
            <tr>
                <th width="66px">行程类型</th>
                <th width="68px">类型</th>
                <th width="66px">乘车日期</th>
                <th width="62px">车次</th>
                <th width="104px">出发站/时间</th>
                <th width="104px">到达站/时间</th>
                <th width="104px">耗时/经停</th>
                <th width="111px">取票号</th>
                <th width="40px">人数</th>
                <th width="120px">检票口</th>
                <th width="105px">备注</th>
            </tr>
            <tr>
                <td>
                                         <span style="margin-top:-18px;display:block;">
                                         ${ticket.getSectionStr()}
                                         </span>
                </td>
                <td>改签票</td>
                <td><span style="margin-top:-18px;display:block;">${ticket.vmTicketDate!""}</span></td>
                <td><span style="margin-top:-18px;display:block;">${ticket.trainNumber!""}</span></td>
                <td><a href="#" onclick="copyUrl2('${ticket.departStationName!""}')">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}</td>
                <td><a href="#" onclick="copyUrl2('${ticket.departStationName!""}')">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}</td>
                <td style="position: relative;"> ${ticket.timeConsuming!""}<br/>
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
                <td><span style="margin-top:-18px;display:block;">${ticket.electronicNum!""}</span></td>
                <td><span style="margin-top:-18px;display:block;">${ticket.ticketCount!""}</span></td>
                <td style="text-align:left;padding:0 10px;">${ticket.ticketCheck!""}</td>
                <td><span style="margin-top:-18px;display:block;"><a href="###">查看12306详情</a></span></td>
            </tr>
        </table>
        <dl class=" class="PI GrayList">
        <dt>
            <span>乘客姓名</span>
            <span>票类型</span>
            <span>证件类型</span>
            <span>证件号</span>
            <span>坐席</span>
            <span>票价</span>
            <span>票状态</span>
            <span>备注</span>
        </dt>
        <#if ticket.trainTickets?? && ticket.trainTickets?size gt 0 >
            <#list ticket.trainTickets as trainTicket>
                <dd>
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
                    <span>
                        <#if trainTicket.ticketPrice??>
                        ${trainTicket.ticketPrice!""} 元<small>（${trainTicket.seatName!""}）</small>
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
                    <span>
                    ${ticket.acceptSeat!""}
                                        </span>
                </dd>
            </#list>
        </#if>
        </dl>
    </#list>
</#if>




<#--车票信息-->
    <#if tickets.trainRoutes?? && tickets.trainRoutes?size gt 0 >
        <#list tickets.getTrainRoutes() as ticket>
            <table class="OrderTable" style="margin-top:20px;" >
                <tr>
                    <th width="66px">行程类型</th>
                    <th width="68px">类型</th>
                    <th width="66px">乘车日期</th>
                    <th width="62px">车次</th>
                    <th width="104px">出发站/时间</th>
                    <th width="104px">到达站/时间</th>
                    <th width="104px">耗时/经停</th>
                    <th width="111px">取票号</th>
                    <th width="40px">人数</th>
                    <th width="120px">检票口</th>
                    <th width="105px">备注</th>
                </tr>
                <tr>
                    <td>
                                         <span style="margin-top:-18px;display:block;">
                                         ${ticket.getSectionStr()}
                                         </span>
                    </td>
                    <td>原票</td>
                    <td><span style="margin-top:-18px;display:block;">${ticket.vmTicketDate!""}</span></td>
                    <td><span style="margin-top:-18px;display:block;">${ticket.trainNumber!""}</span></td>
                    <td><a href="###" onclick="copyUrl2('${ticket.departStationName!""}')">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}</td>
                    <td><a href="###" onclick="copyUrl2('${ticket.departStationName!""}')">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}</td>
                    <td style="position: relative;">${ticket.timeConsuming!""}<br/><a class="searchStation" href="javascript:void(0);">查经停</a>
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
                    <td><span style="margin-top:-18px;display:block;">${ticket.electronicNum!""}</span></td>
                    <td><span style="margin-top:-18px;display:block;">${ticket.ticketCount!""}</span></td>
                    <td style="text-align:left;padding:0 10px;">${ticket.ticketCheck!""}</td>
                    <td><span style="margin-top:-18px;display:block;"><a href="###">查看12306详情</a></span></td>
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
                    <span>备注</span>
                </dt>
                <#if ticket.trainTickets?? && ticket.trainTickets?size gt 0 >
                    <#list ticket.trainTickets as trainTicket>
                        <dd>
                            <em></em>
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
                            <span>
                                <#if trainTicket.ticketPrice??>
                                ${trainTicket.ticketPrice!""} 元<small>（${trainTicket.seatName!""}）</small>
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
                                                    <#switch trainTicket.ticketState!"">
                                                        <#case 0>出票成功<#break>
                                                        <#case 1>已退<#break >
                                                        <#case 2>退票中<#break >
                                                        <#case 3>退票中<#break >
                                                        <#case 4>改签中<#break >
                                                        <#case 5>改签成功<#break >
                                                    </#switch>
                                        </span>
                            <span>
                            ${ticket.acceptSeat!""}
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
                <dt>注：改签至春运期间车票，退票收取手续费为票价的20%</dt>
                <dd><a href="javascript:void(0)" onclick="applyefund.apply()">申请退票</a></dd>
            </dl>

        </div>
    </div>
</div>

</body>


<script>

    $(function(){
        $("#divshadow,#applycancle").bind("click",function(){
            applyefund.shadowclick();
            $("#divshadow").attr("class","");
        })

    });

    var applyefund = {
        shadowshow:function () {   //隐藏阴影
            $("#divshadow").attr("class","shadow");
        },
        shadowhide:function () {   //显示阴影
            $("#divshadow").attr("class","");
        },
        shadowclick:function(){   //点击阴影层，关闭
            $("#applybtn").fadeOut();
            $("#btncheck").fadeOut();
        },
        apply:function(){  //申请退票
            if (applyefund.check()) {
                applyefund.applyConfirm();
            }
        },
        check: function () {   //申请校验
            return true;
        },
        applyConfirm:function(){
            applyefund.shadowshow();
            $("#applybtn").fadeIn();
        }
    }
    
    
    
    
    $(document).ready(function () {
        watermark({watermark_txt: "${empEntity.getEid()!""}"}); //传入动态水印内容
    });
</script>

</html>