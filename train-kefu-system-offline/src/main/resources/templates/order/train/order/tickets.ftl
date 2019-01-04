
        <ul class="TopTit">
            <li class="title">产品详细信息</li>
            <li class="tjsj">订单总金额：&yen;${orderPriceSum!""}
                <small>(总票价：${tickets.totalFare!""}元
                    <#if append.appendPrice??&&append.appendPrice gt 0>
                        附属产品：${append.appendPrice!""}元
                    </#if>
                    <#if tickets.getOrderDelivery()??&&tickets.getOrderDelivery().deliveryPrice??&&tickets.getOrderDelivery().deliveryPrice gt 0>
                        配送费：${tickets.getOrderDelivery().deliveryPrice!""}元
                    </#if>
                    <#if tickets.saleFeePrice??&&tickets.saleFeePrice != "0.00">
                        服务费：${tickets.saleFeePrice!""}元
                    </#if>
                    )</small>
            </li>
        </ul>
        <!--车票信息-->
        <div class="cpTop">
            <dl class="TicketInfo">
                <dt>车票信息</dt>
                <dd>
                <#if basic.cancelBtn?has_content>
                    ${basic.cancelBtn!""}
                </#if>
                    <#--<a class="a" href="javascript:void(0)" onclick="">取消订单</a>-->
                    <a class="a" href="javascript:void(0)" onclick="deleteTrainOrder()">删除订单</a>
                    <#--<#if tickets.getOrderDelivery()??&&tickets.getOrderDelivery().agentName??>-->
                        <#--&lt;#&ndash; 配送票不可退 &ndash;&gt;-->
                        <#--&lt;#&ndash;TODO置灰&ndash;&gt;-->
                        <#--<a class="b" href="javascript:void(0)" >退票（不可退）</a>-->
                    <#--<#else>-->
                        <a class="b" href="javascript:void(0)" onclick="trainOrderRefund()">退票</a>
                        <a class="b" href="javascript:void(0)" onclick="changeTrainTicket()">改签</a>
                </dd>
            </dl>
            <#--改签信息-->
            <#if tickets.changeTickets?? && tickets.changeTickets?size gt 0 >
                    <div class="Stit">改签车票信息</div>
                    <div class="TabBox">
                    <#list tickets.changeTickets as ticket>
                         <div class="TabBox">
                             <table>
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
                                         <span style="display:block;">
                                             ${ticket.getSectionStr()}
                                         </span>
                                     </td>
                                     <td>改签票</td>
                                     <td><span style="display:block;">${ticket.vmTicketDate!""}</span></td>
                                     <td><span style="display:block;">${ticket.trainNumber!""}</span></td>
                                     <td><a id="${ticket_index}1" href="javascript:void(0)" class="clipboard" onmousemove="Show_StopOver('${ticket_index}1')" onmouseout="Close_StopOut('${ticket_index}1')" data-clipboard-target="#StopOver_${ticket_index}1">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}
                                         <p style="overflow-y: auto; overflow-x: hidden; max-height: 145px; height: auto; width: 150px; position: absolute; display: none; z-index: 40;margin-top: -20px; margin-left: -28px; background-color: White; border: 1px solid;" id="StopOver_${ticket_index}1">
                                               <span>
                                               ${ticket.departAddress!""}</span>
                                     </p></td>
                                     <td><a id="${ticket_index}2" href="javascript:void(0)" class="clipboard" onmousemove="Show_StopOver('${ticket_index}2')" onmouseout="Close_StopOut('${ticket_index}2')" data-clipboard-target="#StopOver_${ticket_index}2">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}
                                         <p style="overflow-y: auto; overflow-x: hidden; max-height: 145px; height: auto; width: 150px; position: absolute; display: none; z-index: 40;margin-top: -20px; margin-left: -28px; background-color: White; border: 1px solid;" id="StopOver_${ticket_index}2">
                                               <span>
                                               ${ticket.arriveAddress!""}</span>
                                         </p>
                                     </td>
                                     <td style="position: relative;"> ${ticket.timeConsuming!""}<br/>
                                         <a href="javascript:void(0);" class="searchStation">查经停</a>
                                         <#--经停-->
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
                                     <td><span style="display:block;">${ticket.electronicNum!""}</span></td>
                                     <td><span style="display:block;">${ticket.ticketCount!""}</span></td>
                                     <td>${ticket.ticketCheck!""}</td>
                                     <td>
                                         <#if ticket.electronicNum??>
                                             <span style="display:block;"><a href="javascript:void(0)" onclick="search12306Details('${ticket.electronicNum!""}')" >查看12306详情</a>
                                             </span>
                                         </#if>
                                     </td>
                                 </tr>
                             </table>
                         </div>
                         <dl class="PassengerInfo">
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
                                    <#if trainTicket.ticketState==20||trainTicket.ticketState==40
                                    ||trainTicket.ticketState==60||trainTicket.ticketState==70 >
                                    <dd class="spanRed" >
                                    <#else >
                                    <dd >
                                    </#if>
                                        <span>${trainTicket.realName!""}</span>
                                        <span>
                                             <#switch trainTicket.getPassengerType()!"">
                                                 <#case 1>成人<#break>
                                                 <#case 2>儿童<#break >
                                                 <#case 3>学生<#break >
                                                 <#case 4>残军<#break >
                                                 <#default >未配置
                                             </#switch>
                                        </span>
                                        <span>
                                            ${trainTicket.passportType!""}
                                        </span>
                                        <span>${trainTicket.passportNumber!""}
                                            <#if trainTicket.quickPass == 1>
                                                <i></i>
                                            </#if>
                                        </span>
                                        <span>
                                            ${trainTicket.seatName!"-"}
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
                                             <#if trainTicket.ticketState?has_content && trainTicket.ticketState == 30
                                                    &&(trainTicket.rescheduleType == 1||trainTicket.rescheduleType == 2||trainTicket.rescheduleType == 6 )>
                                                 改签抢票中
                                             <#else >
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
                                             </#if>
                                        </span>
                                        <span>
                                            ${ticket.returnRemark!""}
                                        </span>
                                    </dd>
                                </#list>
                            </#if>
                         </dl>
                    </#list>
                  </div>
                <#--改签票的抢票抢票任务-->
                <#if basic.orderType == 6>
                    <#include "../order/grabtask.ftl">
                </#if>
            </#if>
            <#--车票信息-->
            <#if tickets.trainRoutes?? && tickets.trainRoutes?size gt 0 >
                <#--<div class="Stit" style="display: none;">车票信息</div>-->
                <#if tickets.changeTickets?? && tickets.changeTickets?size gt 0 >
                    <div class="Stit">原票信息</div>
                </#if>
                <div class="TabBox">
                    <#list tickets.getTrainRoutes() as ticket>
                         <div class="TabBox">
                             <table>
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
                                     <th width="120px">预售时间</th>
                                     <th width="105px">备注</th>
                                 </tr>
                                 <tr>
                                     <td>
                                         <span style="display:block;">
                                             ${ticket.getSectionStr()}
                                         </span>
                                     </td>
                                     <td>原票</td>
                                     <td><span style="display:block;">${ticket.vmTicketDate!""}</span></td>
                                     <td><span style="display:block;">${ticket.trainNumber!""}</span></td>
                                     <td><a id="${ticket_index}3" href="javascript:void(0)" class="clipboard" onmousemove="Show_StopOver('${ticket_index}3')" onmouseout="Close_StopOut('${ticket_index}3')" data-clipboard-target="#StopOver_${ticket_index}3">${ticket.departStationName!""}</a><br/>${ticket.ticketTime!""}
                                         <p style="overflow-y: auto; overflow-x: hidden; max-height: 145px; height: auto; width: 150px; position: absolute; display: none; z-index: 40;margin-top: -20px; margin-left: -28px; background-color: White; border: 1px solid;" id="StopOver_${ticket_index}3">
                                               <span>
                                               ${ticket.departAddress!""}</span>
                                         </p>
                                     </td>
                                     <td><a id="${ticket_index}4" href="javascript:void(0)" class="clipboard" onmousemove="Show_StopOver('${ticket_index}4')" onmouseout="Close_StopOut('${ticket_index}4')" data-clipboard-target="#StopOver_${ticket_index}4">${ticket.arriveStationName!""}</a><br/>${ticket.arrivalDateTime!""}
                                         <p style="overflow-y: auto; overflow-x: hidden; max-height: 145px; height: auto; width: 150px; position: absolute; display: none; z-index: 40;margin-top: -20px; margin-left: -28px; background-color: White; border: 1px solid;" id="StopOver_${ticket_index}4">
                                               <span>
                                               ${ticket.arriveAddress!""}</span>
                                         </p>

                                     </td>

                                     <td style="position: relative;">${ticket.timeConsuming!""}<br/><a class="searchStation" href="javascript:void(0);">查经停</a>
                                         <div class="time" style="width: 424px;display: none;">
                                             <div class="Top">
                                                 <span>车站</span>
                                                 <span>到达</span>
                                                 <span>出发</span>
                                                 <span>停留</span>
                                             </div>
                                             <#--经停-->
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
                                     <td><span style="display:block;">${ticket.electronicNum!"-"}</span></td>
                                     <td><span style="display:block;">${ticket.ticketCount!""}</span></td>
                                     <td>${ticket.ticketCheck!""}</td>
                                     <td>${ticket.preSaleTime!"-"}</td>
                                     <td>
                                        <#if ticket.electronicNum??>
                                            <span style="display:block;"><a href="javascript:void(0)" onclick="search12306Details('${ticket.electronicNum!""}')">查看12306详情</a>
                                            </span>
                                        </#if>
                                     </td>
                                 </tr>
                             </table>
                         </div>
                         <dl class="PassengerInfo">
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
                                    <dd <#if trainTicket.ticketState==0||trainTicket.ticketState==3 > <#else>class="spanRed"</#if> >
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
                                        <span>${trainTicket.passportNumber!""}
                                            <#if trainTicket.quickPass == 1>
                                                <i></i>
                                            </#if>
                                        </span>
                                        <span>
                                            ${trainTicket.seatName!"-"}
                                        </span>
                                        <span>
                                            <#if trainTicket.realTicketPrice??>
                                                ${trainTicket.realTicketPrice!""} 元<small>（支付:${trainTicket.ticketPrice!""}元）</small>
                                            <#else>
                                                -
                                            </#if>
                                        </span>
                                        <span>
                                            <#--订单状态-->
                                            <#if tickets.orderStatus != 9>
                                                <#--退票状态-->
                                                <#if trainTicket.realTicketPrice??>
                                                    <#--ticketStatus 0未退 1已退 2退票中 3退票失败 4改签中 5 改签成功-->
                                                    <#switch trainTicket.ticketState!"">
                                                        <#case 0>出票成功<#break>
                                                        <#case 1>退票成功<#break >
                                                        <#case 2>退票中<#break >
                                                        <#case 3>退票失败<#break >
                                                        <#case 4>改签中<#break >
                                                        <#case 5>改签成功<#break >
                                                    </#switch>
                                                <#else>
                                                    -
                                                </#if>
                                            <#else >
                                                已取消
                                            </#if>
                                        </span>
                                        <span>
                                            ${ticket.returnRemark!""}
                                        </span>
                                    </dd>
                                </#list>
                            </#if>
                        </dl>
                    </#list>
                </div>
                <#--原票抢票详情 改签票不显示-->
                <#if tickets.changeTickets?? && tickets.changeTickets?size gt 0 >

                <#else >
                    <#if basic.orderType == 6 >
                        <#include "../order/grabtask.ftl">
                    </#if>
                </#if>
            </#if>
        </div>
        <#--配送信息-->
        <#if tickets.getOrderDelivery()??&&tickets.getOrderDelivery().agentName??>
              <div class="JsBtm">
                  <div class="Stit">车票寄送信息</div>
                  <div class="TabBox">
                      <table>
                          <tr>
                              <th width="240px">邮寄地址</th>
                              <th width="113px">配送物流公司</th>
                              <th width="124px">物流单号</th>
                              <th width="167px">代售点</th>
                              <th width="127px">当前状态</th>
                              <th width="93px">配送费</th>
                              <th width="90px">服务费</th>

                          </tr>
                          <tr>
                              <td>
                                  <dl>
                                      <dt>${tickets.getOrderDelivery().areaName!""} ${tickets.getOrderDelivery().deliveryAddress!""}</dt>
                                      <dd>
                                          <span>
                                              <#if tickets.coutnerTicket>
                                                    <a href="javascript:void(0)" onclick="ShowCounterAddressPIC()">查看地址图片</a></span>
                                              </#if>
                                              <span><a href="" onclick="changeDeliveryAddress()">修改配送地址</a></span>
                                      </dd>
                                  </dl>
                              </td>
                              <td><div><a href="javascript:void(0)">${tickets.getOrderDelivery().deliverCompany!""}</a></div></td>
                              <td><div>${tickets.getOrderDelivery().deliveryNumber!""}</div></td>
                              <td><div><a href="javascript:void(0)" onclick="ShowAgentInfo('${tickets.getOrderDelivery().agentName!""}')" >${tickets.getOrderDelivery().agentName!""}</a></div></td>
                              <td><div>
                                    <#switch tickets.getOrderDelivery().deliveryState!"">
                                        <#case 1>未配送<#break>
                                        <#case 2>配送中<#break >
                                        <#case 3>派件中<#break >
                                        <#case 4>签收失败<#break >
                                        <#case 5>已签收<#break >
                                    </#switch>
                              </div></td>
                              <td><div>${tickets.getOrderDelivery().deliveryPrice!""} </div></td>
                          <#--服务费-->
                              <td><div>${tickets.saleFeePrice!"-"}</div></td>
                          </tr>
                      </table>
                  </div>
              </div>
        </#if>
        <#-- 多程 改签选择行程 -->
        <div class="modal fade bs-example-modal-sm" id="selectRoute" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="selectRoleLabel">改签行程</h4>
                    </div>
                    <div class="modal-body">
                        <form id="boxRoleForm">
                            <input type="radio" id="radFirstSeq" name="radGqSeq" value="1" checked="checked" />
                            <label id="labfirstSeq">去程</label>
                            <input type="radio" id="radSecondSeq" name="radGqSeq" value="2" />
                            <label id="labSecondSeq">返程</label>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="btn_save_change">确定</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>

<script type="text/javascript">

    $(function(){
        $(".searchStation").bind("click",function(){
            search.showStation(this);
        })

        var clipboard = new Clipboard('.clipboard');
        clipboard.on('success', function (e) {
            alert("地址已复制到剪贴板!");
            e.clearSelection();
        });
    });

    //******鼠标移到显示******//
    function Show_StopOver(id) {
        //$('#StopOver_' + id).css({ 'left': $('#' + id).offset().left - 255, 'top': $('#' + id).offset().top - 33, 'display': '' });
        $('#StopOver_' + id).css({'display': '' });
        $('#StopOver_' + id).bind('mouseover',
                function(e) {
                    $("#StopOver_" + id).css("display", "");
                });
    }
    //******鼠标移走消失*******//
    function Close_StopOut(id) {
        $("#StopOver_" + id).css("display", "none");
        $("#StopOver_" + id).bind('mouseout',
                function(e) {
                    $("#StopOver_" + id).css("display", "none");
                });
    }

    /**
     *
     */
    $('#btn_save_change').on('click', function () {
        var rio = $("input[name='radGqSeq']:checked").val();
        if (rio == "1") {
            $('#selectRoute').modal('hide');
            ChangeTrainTicket('${basic.firstSeqGQToken!""}');
        } else if (rio == "2") {
            $('#selectRoute').modal('hide');
            ChangeTrainTicket('${basic.secondSeqGQToken!""}');
        }

    });
    /**
     * 代售点详情
     */
    function ShowAgentInfo(agentName) {
        var iWidth = 540; //弹出窗口的宽度;
        var iHeight = 350; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        var name = encodeURI(agentName);
        var orderId = "${basic.orderId!""}";
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ShowAgentInfo.aspx?agentName='+name+'&orderid='+orderId, '查看代售点信息', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    /**
     * 12306详情
     */
    function search12306Details(electronicNum) {
        var orderId="${basic.orderId!""}";
        var userAccount="${basic.userAccount!""}";
        var iWidth = 1090; //弹出窗口的宽度;
        var iHeight = 510; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;,String ,String
        var url = "http://kefu.train.ctripcorp.com/offline/train/order/12306info?orderNumber="+orderId+"&username12306="+userAccount+"&eOrderNumber="+electronicNum;
        window.open(url, 'search', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }

    /**
     * 单程和往返票可改签 联程票不可改签
     */
    function changeTrainTicket() {
        // 去哪儿订单不能改签
        var parnterName = '${basic.partherName!""}';
        if(parnterName=='qunarsync')
        {
            alert('去哪儿同步订单不能改签！');
            return;
        }

        var ticketType = '${basic.ticketType!""}';
        //单程
        if (ticketType == '0') {
            ChangeTrainTicket('${basic.firstSeqGQToken!""}');
        } else if (ticketType == '1')//往返
        {
            $('#selectRoute').modal({
                keyboard: false,
                backdrop: 'static'
            });
        } else if (ticketType == '2')//联程
        {
            alert("联程票不支持改签！");
        }
    }

    function ChangeTrainTicket(token) {
        var url = 'http://trains.ctrip.com/OrderService/OrderPage/OrderMain.aspx?token=' + token + '&channel=offline#change';
        window.open(url);
    }
    var search={
        showStation:function(obj){
            $(obj).next().fadeIn();
            $(obj).unbind();
            $(obj).bind("click",function(){
                search.hideStation(this);
            });
        },
        hideStation:function (obj) {
            $(obj).next().fadeOut();
            $(obj).unbind();
            $(obj).bind("click",function(){
                search.showStation(this);
            });
        }
    };

    //更改配送票地址票
    function changeDeliveryAddress() {
        var partnername = "${basic.partherName!""}";
        var url="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/UpdateDelAddress.aspx?orderid=${basic.orderId!""}&PartnerName="+partnername;
        var iWidth = 540; //弹出窗口的宽度;
        var iHeight = 250; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open(url, '修改配送地址', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }
    //查看地址图片
    function ShowCounterAddressPIC() {
        window.open("${tickets.getOrderDelivery().addressPicUrl!''}");
    }
    /**
     * 删除火车票订单
     */
    function deleteTrainOrder() {

        layer.confirm('确定删除火车票订单？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                url: "${ctx}/order/train/deletetrainorder",
                dataType: "json",
                data: {
                    orderNumber: '${basic.orderId!""}',
                    partnerName: '${basic.partherName!""}',
                    userAccount: '${basic.userLoginName!""}'
                },
                success: function (data) {
                    layer.alert(data.data.msg);
                }
            });
        }, function(){

        });
    }

    /**
     * 删除火车票订单
     */
    function trainOrderRefund() {
        var url="${ctx}/order/train/refund/getRefundInfo?orderId=${basic.orderId!""}";
        window.open(url,"_blank");
    }

    //复制车站地址
    function copyUrl2(address) {
        //TODO
        alert('复制成功');
    }

    /**
     * 取消订单
     */
    function CancelOrderPage(t0, partnerName) {
        var iWidth = 320; //弹出窗口的宽度;
        var iHeight = 150; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/CancelOrderPage.aspx?PartnerName='+partnerName+'&orderid=' + t0, '取消订单', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    //配送票申请取消已提取订单
    var tickagent = {
        applyCancelOrder:function(){
            layer.confirm("确定要申请取消订单？", {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                tickagent.ajaxsearch();
            });
        },
        ajaxsearch:function(){
            $.ajax({
                type: "POST",
                url: "${ctx}/order/train/tickets/cancelOrder",
                data: {
                    orderNumber: '${basic.orderId!""}'
                },
                dataType: "json",
                success: function (data) {
                    if (!data.data.msg){
                        layer.alert("没有获取到对应帐号信息！");
                    } else {
                        layer.alert(data.data.msg);
                    }
                },
                error:function(){
                    layer.alert("系统异常！");
                }
            });
        }
    }

</script>


