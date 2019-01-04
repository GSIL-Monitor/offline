
<!DOCTYPE html>
<html>
<#include "../../../common/header.ftl">
<@header pageTtle="机票退票：订单号 ${refundDetail.getOrderid()}" headerVisible=true pageWidth=1200></@header>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css"/>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_global.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<script type="text/javascript" src="${ctx}/static/layer/theme/default/layer.css"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<body>
<div class="pubglobal_main" style="width: 1200px;">
    <div >
        <table class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;">

            <tbody id="noticeTBody">
            <#if refundDetail.getVmSegmentsList()?has_content>
             <#list  refundDetail.getVmSegmentsList()  as  segment>
             <tr class="info" style="text-align: -webkit-center; ">
                 <th width="90px">航段</th>
                 <th width="90px">航班号</th>
                 <th width="90px">航程</th>
                 <th width="90px">起飞时间</th>
             </tr>
                <tr style="border-bottom: 1px;">
                    <td>${segment.getSegmentTag()}</td>
                    <td>${segment.getFilghtNumber()}</td>
                    <td>${segment.getDepartCityName()}-${segment.getArriveCityName()}</td>
                    <td>${segment.getDepartDateTime()}</td>
                </tr>
               <tr style="border-bottom:0" id="tr_${segment.getVendorOrderNumber()}" >
                   <td colspan="12" style="align-content: center">
                       <table class="table table-bordered table-hover" style="border-collapse: collapse; width: 100%; " >
                           <tr class="info" style="text-align: -webkit-center; ">
                               <th width="50px">选择</th>
                               <th width="100px">退票类型</th>
                               <th width="100px">乘客</th>
                               <th width="80px">乘客类型</th>
                               <th width="100px">机票款</th>
                               <th width="80px">机建燃油</th>
                               <th width="100px">保险</th>
                               <th width="100px">代金券</th>
                               <th width="100px">返现</th>
                               <th width="100px">退票费</th>
                               <th width="100px">不可退原因</th>
                           </tr>
                           <tbody >
                     <#if segment.getVmTickets()?has_content>
                         <#list  segment.getVmTickets()  as  ticket>
                            <tr style="border-bottom: 0px;">
                                <td>
                                 <label>
                                    <input name="CkTickets" type="checkbox" value="${ticket.getPassengerName()}"
                                           data-id="${ticket.getVendorOrderNumber()}"
                                           data-num="${segment.getSegmentNo()}"
                                           data-type="${ticket.getPassengerType()}"
                                           data-price="${ticket.getTotalPrice()}"
                                           data-value="${ticket.getRefundPrice()}"
                                           data-reason="${segment.getReasonId()}"
                                           data-passenger="${ticket.getPassengerId()?c}"
                                           data-voucher="${ticket.getVoucherPrice()}"
                                           <#if segment.getHasDepart()>  data-back="${ticket.getCashBackPrice()}"</#if>
                                           <#if  !ticket.getCanRefund()> disabled="disabled" defautDisabled="1"</#if> />
                                 </label>
                                </td>
                                <td>自愿</td>
                                <td>${ticket.getPassengerName()!}</td>
                                <td>${ticket.getPassengerType()!}</td>
                                <td>${ticket.getTicketsPrice()!}</td>
                                <td>${ticket.getBuildingFuelPrice()!}</td>
                                <td><#if ticket.getInsurances()?has_content>
                                        <#list   ticket.getInsurances() as  item>
                                            ${item.getInsuranceName()}: ${item.getInsurancePrice()}<br/>
                                        </#list>
                                     </#if>
                                </td>
                                <td>${ticket.getVoucherPrice()!}</td>
                                <td>${ticket.getCashBackPrice()!}</td>
                                <td>${ticket.getRefundPrice()!}</td>
                                <td>${ticket.getStateDesc()!}</td>
                            </tr>
                         </#list>
                     <#else >
                       <tr><td colspan="15" style="height: 70px;text-align: center">没有找到数据</td></tr>
                     </#if>
                           </tbody>
                       </table>
                   </td>

               </tr>
             </#list>
            <#else >
                <tr><td colspan="15" style="height: 70px;text-align: center">${refundDetail.getMessage()}</td></tr>
            </#if>

            <#if refundDetail.getHasPaidDelivery()>
            <tr>
                <td colspan="15" style="text-align: left;color: red">注：订单已支付20元退票凭据邮寄费用
                </td>
            </tr>
            </#if>
            <#if  refundDetail.getHasRefundPrice()&& refundDetail.getIsFirstRefund()>
                <tr >
                    <td colspan="6" class="form-inline" style="text-align: left">
                        <label class="checkbox-inline" >
                            <input id="cbTurnComplain" type="checkbox"  name="CkDelivery" onclick="showDelivery()" />退票凭据
                        </label>
                    </td>
                </tr>
            </#if>
            <tr>
                <td colspan="6" class="form-inline">
                <div  class="form-content"  id="divDelivery" style="display: none" >
                    <div class="row">
                        <div class="col-xs-2 title">
                            收件人：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtContactName">
                        </div>
                        <div class="col-xs-2 title">
                            手机号码：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtContactPhone">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-2 title">
                            省：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtProvince">
                        </div>
                        <div class="col-xs-2 title">
                            市：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtCity">
                        </div>
                        <div class="col-xs-2 title">
                            区：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtDistrict">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-2 title">
                            配送地址：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtAddress">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-2 title">
                            邮箱：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtEmail">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-2 title">
                            抬头信息：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtInvoiceTitle">
                        </div>
                        <div class="col-xs-4">
                            <label class="radio-inline">
                                <input type="radio" name="deliveryRadio"  id="optionsRadios1" value="1" checked>个人
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="deliveryRadio"   id="optionsRadios2" value="2">公司
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="deliveryRadio"  id="optionsRadios3" value="3">政府机构
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-2 title">
                            税号：
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control" id="txtTaxNumber">
                        </div>
                    </div>

                </div>
                </td>
            </tr>
            <tr >
                <td colspan="6" class="form-inline">
                    <span id="hintInfo" class="alert alert-warning" style="display:none;height: 10px;padding: 10px 20px;margin-right: 30px"></span>
                    <a class="btn btn-primary btn-sm w80" id="btnSendNotice" onclick="sendRefund()">申请退款</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
<script type="text/javascript">
    function showDelivery()  {
        if($("input[name='CkDelivery']").is(":checked")) {
            $("#divDelivery").show();
        }
        else {
            $("#divDelivery").hide();
        }
    }

    $("#noticeTBody").bind("click", function (event) {
        var ev = event || window.event;
        var target = ev.target || ev.srcElement; 
        if("CkTickets" === target.name) {
            var id = $(target).data("id");
            var value = $(target).val();
            //  $("input[type=checkbox][data-id="+id+"][value="+value+"]").checked=true;

            if ($("input[name=CkTickets][data-id=" + id + "]:checked").length > 0) {
                $("input[name=CkTickets][data-id!=" + id + "][defautDisabled!=1]").attr("disabled", "true");
            } else {
                $("input[name=CkTickets][data-id!=" + id + "][defautDisabled!=1]").removeAttr("disabled");
            }

            // var checkboxList = $("input[name=CkTickets][data-id=" + id + "][value=" + value + "][defautDisabled!=1]");
            // if (checkboxList && checkboxList.length > 0) {
            //     $(checkboxList).each(function (n,v) {
            //         v.checked = target.checked;
            //     })
            // }
        }
    });

    function sendRefund() {

        var totalPrice = "0";
        var refundPrice = "0";
        var cashBackPrice = "0";
        var voucherPrice  = "0";
        var ticketList = [];
        var checks = $("input[name='CkTickets']:checked");
        if (checks) {
            if (checks.length === 0) {
                layer.alert("请选择乘客");
                return;
            }
            checks.each(function (index, element) {
                var ticket = {};
                ticket.passengerName = element.getAttribute("value");
                ticket.passengerType=element.getAttribute("data-type");
                ticket.vendorOrderNumber = element.getAttribute("data-id");
                ticket.segmentNo=element.getAttribute("data-num");
                ticket.reasonId=element.getAttribute("data-reason");
                ticket.passengerId=element.getAttribute("data-passenger");

                var dataValue=element.getAttribute("data-value");
                var dataPrice=element.getAttribute("data-price");
                var dataBack=element.getAttribute("data-back");
                var dataVoucher=element.getAttribute("data-voucher");
                refundPrice =(parseFloat(refundPrice)+ parseFloat(dataValue)).toFixed(2);
                totalPrice =(parseFloat(totalPrice)+ parseFloat(dataPrice)).toFixed(2);
                cashBackPrice =(parseFloat(cashBackPrice)+ parseFloat(dataBack)).toFixed(2);
                voucherPrice =parseFloat(dataVoucher).toFixed(2);
                ticketList.push(ticket)
            });

            //加上之前付过的20块钱邮寄费
            if ('${refundDetail.getHasPaidDelivery()}'
                    &&!'${refundDetail.getHasRefundInvoice()}'){
                totalPrice=(parseFloat(totalPrice)+ parseFloat(20)).toFixed(2);
            }
        }
        else {
            layer.alert("请选择乘客");
            return;
        }

        if(!(refundPrice>0)&&$("input[name='CkDelivery']").is(":checked")){
            layer.alert("退票费为0不支持索要退票凭证");
            return;
        }

        var flightRefund = {};
        var refund = {};
        refund.orderid = '${refundDetail.orderid}';
        refund.tyUerid = '${refundDetail.tyUerid}';
        refund.ctripUid = '${refundDetail.ctripUid}';

        flightRefund.refund = refund;
        flightRefund.tickets = ticketList;
        if ($("input[name='CkDelivery']").is(":checked")) {
            flightRefund.deliveryType = 1;
            flightRefund.address = $("#txtAddress").val();
            flightRefund.contactName = $("#txtContactName").val();
            flightRefund.contactPhone = $("#txtContactPhone").val();
            flightRefund.province = $("#txtProvince").val();
            flightRefund.city = $("#txtCity").val();
            flightRefund.district = $("#txtDistrict").val();
            flightRefund.email = $("#txtEmail").val();
            flightRefund.invoiceTitle = $("#txtInvoiceTitle").val();
            flightRefund.invoiceType = $("input[name=noticeSecondType]:checked").val();
            flightRefund.taxNumber =$("#txtTaxNumber").val();

            if(!flightRefund.address||!flightRefund.contactName||!flightRefund.contactPhone
                    ||!flightRefund.province||!flightRefund.city||!flightRefund.district
                    ||!flightRefund.invoiceTitle){
                layer.alert("请完善配送信息。");
                return
            }
            if(!flightRefund.taxNumber && $("input[name=noticeSecondType]:checked").val() === "3"){
                layer.alert("请填写发票税号。");
                return
            }
        }
        var deliveryPrice=0;
        if('${refundDetail.getHasRefundPrice()}'==='true'&&'${refundDetail.getIsFirstRefund()}'==='true'
                &&$("input[name='CkDelivery']").is(":checked")){
            deliveryPrice=20;
        }
        var  message;
        if ('${refundDetail.getIsNonComfirmOrder()}'==='true'){
            message="退票费待确认，请联系供应商或航司"
        }
        else {
            if (voucherPrice>0){
                totalPrice=(parseFloat(totalPrice) - parseFloat(voucherPrice)).toFixed(2);
            }
            message = "支付总金额:" + totalPrice + "元";
            var actualPrice = (parseFloat(totalPrice) - parseFloat(refundPrice)).toFixed(2);
            if (deliveryPrice > 0) {
                message += "<br>邮寄费用：" + deliveryPrice + "元";
                actualPrice = (parseFloat(actualPrice) - parseFloat(20)).toFixed(2);
            }
            if (cashBackPrice > 0) {
                message += "<br>返现：" + cashBackPrice + "元";
                actualPrice = (parseFloat(actualPrice) - parseFloat(cashBackPrice)).toFixed(2);
            }
            message += "<br>实退金额：" + actualPrice + "元";
        }

        //询问框
        layer.confirm(message, {
            btn: ['退款', '取消'] //按钮
        }, function () {
            layer.closeAll();
            layer.load(3);
            var url = "${ctx}/order/flight/refund.json";
            $.ajax({
                type: 'POST',
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(flightRefund),
                success: function (data) {
                    if (data.status === 0) {
                        layer.alert(data.data.result, {}, function () {
                            window.location.href = window.location.href;
                            layer.closeAll();
                        });
                    }
                    else {
                        layer.alert(data.data.result)
                    }
                },
                error: function () {
                    layer.alert(data.message)
                }
            });
        }, function () {
        });
    }
</script>

<style type="text/css">
    div#divReminderNotice table
    {
        width: 400px;
        background-color: #91AFDD;
    }
    div#divReminderNotice table th
    {
        background: none no-repeat scroll 0 0 #CAE8EA;
        color: #4F6B72;
        font: bold 11px "Trebuchet MS" ,Verdana,Arial,Helvetica,sans-serif;
        letter-spacing: 2px;
        text-align: center;
        text-transform: uppercase;
    }
    div#divReminderNotice table td
    {
        background: none repeat scroll 0 0 #FFFFFF;
        color: #4F6B72;
        font-size: 13px;
        height: 28px;
        padding: 6px 6px 6px 12px;
    }

    .form-content
    {
        text-align: left;
    }
    .form-content .row {
        margin-bottom:2px;
    }
    .form-content .title {
        background-color: #D9EDF7;
        height: 34px;
        line-height: 34px;
    }

</style>