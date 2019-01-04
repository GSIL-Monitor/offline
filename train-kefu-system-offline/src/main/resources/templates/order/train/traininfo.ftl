<!DOCTYPE html>

<html lang="en">

<head id="Head1" runat="server">
    <title>12306订单详情</title>
    <style type="text/css">
        table tr
        {
            height: 31px;
        }
        .window_pop .window_pop_bd p
        {
            text-align: center;
        }

        td {
            border-right: solid 1px #add9c0;
            border-bottom: solid 1px #add9c0;
        }
        li{
            font-size: 18px;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        var total=0;
        var totallimit = 0;
        $(document).ready(function () {
            var eOrderNumber = "${eOrderNumber}";
            var eOrderNumberArr = eOrderNumber.split(",");
            var username12306 = "${username12306}";
            var username12306Arr = username12306.split(",");
            if (eOrderNumberArr.length > 0 && username12306Arr.length > 0) {
                for (var i = 0; i < eOrderNumberArr.length; i++) {
                    for (var j = 0; j < username12306Arr.length; j++) {
                        totallimit += 5;
                        get12306Info("${orderNumber}", username12306Arr[j], eOrderNumberArr[i]);
                    }
                }
            }else {
                alert("参数有误！");
            }
        });

        //获取12306 订单详情
        function get12306Info(orderNumber, username12306, eOrderNumber) {
            if(total > totallimit){
                alert("读取数据超时，请稍候刷新页面重试！");
                return false;
            }
            total++;
            var url = "${ctx}/train/order/12306info/detail";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                async:false,
                data: {orderNumber:orderNumber,username12306:username12306,eOrderNumber:eOrderNumber},
                success: function (data) {
                    console.log(data);
                    if(data.message != "Fail") {
                        set12306InfoHtml(data.data.response,eOrderNumber);
                    }else{
                        if(data.data.status == 0){  //进入查询队列
                            setTimeout(function(){get12306Info(orderNumber, username12306, eOrderNumber)}, 5000);
                        }else{
//                            $("#trOrder").hide();
//                            $("#trRefund").hide();
//                            alert("参数错误!");
                        }

                    }
                },
                error: function (data) {
                    alert(data.data.msg);
                }
            });
        }

        //返回table
        function set12306InfoHtml(queryOrderInfo,eOrderNumber) {
            console.log(eOrderNumber);
            $("#divdetail").hide();
            var tableHtml;
            for(var i=0;i<queryOrderInfo.queryOrderList.length;i++)
            {
                var detail = queryOrderInfo.queryOrderList[i];
                tableHtml +="<tr>" ;
                tableHtml +="<td>" + (i+1) + "</td>" ;
                tableHtml +="<td>" + detail.trianInfo + "</td>" ;
                tableHtml +="<td>" + detail.seatInfo + "</td>" ;
                tableHtml +="<td>" + detail.passengerInfo + "</td>" ;
                tableHtml +="<td>" + detail.tickprice + "</td>" ;
                tableHtml +="<td>" + detail.ticketstate + "</td>" ;
                tableHtml +="<td>" + detail.orderTrace + "</td>" ;
                tableHtml +="</tr>" ;
            }
            $("#exceData").empty();
            $("#exceData").append(tableHtml);

            var tablerefundHtml;
            if (queryOrderInfo.refundInfos.length > 0) {
                for (var i = 0; i < queryOrderInfo.refundInfos.length; i++) {
                    var detail = queryOrderInfo.refundInfos[i];
                    tablerefundHtml += "<tr>";
                    tablerefundHtml += "<td>" + (i + 1) + "</td>";
                    tablerefundHtml += "<td>" + detail.PassengerName + " " + detail.Status + "</td>";
                    tablerefundHtml += "<td>" + detail.RefundAmount + "</td>";
                    tablerefundHtml += "<td>" + detail.Origin + "</td>";
                    tablerefundHtml += "<td>" + detail.OperateTime + "</td>";
                    tablerefundHtml += "<td>" + detail.TransNo + "</td>";
                    tablerefundHtml += "<td>" + detail.RefundStatus + "</td>";
                    tablerefundHtml += "</tr>";
                }
            }
            $("#excerefundData").empty();
            $("#excerefundData").append(tablerefundHtml);
            $("#divdetail").find("span[name='spaneordernumber']").html("取票号：" + eOrderNumber);
            $("#divInfo").append($("#divdetail").html());

        }
    </script>
</head>
<body>
<form id="form1" runat="server">
    <div style="width: 800px; font-size: 12px;" id="divInfo">
        <div id="divdetail">
            <ul class="navList">
                <li class="navitem"><a>12306订单详情</a>&nbsp;&nbsp;<span style="font-size: 12px;font-weight: normal;" name="spaneordernumber"/></li>
            </ul>
            <table cellpadding="0px" cellspacing="0px" style=" border-right:none;border-bottom:none;width:1075px; border:1px solid #71AFDD;font-size:13px; text-align:center;" id="detailGrid">
                <tr style=" background-color:#ECF4FF">
                    <td style=" width:23px;">序号</td>
                    <td style=" width:60px;">车次信息</td>
                    <td style=" width:75px;">座席信息</td>
                    <td style=" width:51px;">旅客信息</td>
                    <td style=" width:60px;">票款金额</td>
                    <td style=" width:60px;">车票状态</td>
                    <td style=" width:160px;">订单追踪</td>
                </tr>
                <tbody id="exceData">
                <tr id="trOrder">
                <td colspan="9" style=" border-right:0px;" class="exceDataTd">Loading....</td>
                </tr>
                </tbody>
            </table>
            <br><br>
            <ul class="navList">
                <li class="navitem"><a>退款详情</a>&nbsp;&nbsp;<span style="font-size: 12px;font-weight: normal;" name="spaneordernumber"/></li>
            </ul>
            <table id="tbrefund" cellpadding="0px" cellspacing="0px" style="border-right:none;border-bottom:none;width:1075px; border:1px solid #71AFDD;font-size:13px; text-align:center;" id="detailGrid">
                <tr style=" background-color:#ECF4FF">
                    <td style=" width:23px;">序号</td>
                    <td style=" width:60px;">明细</td>
                    <td style=" width:51px;">退款金额</td>
                    <td style=" width:60px;">来源</td>
                    <td style=" width:60px;">申请时间</td>
                    <td style=" width:60px;">流水号</td>
                    <td style=" width:120px;">状态</td>
                </tr>
                <tbody id="excerefundData">
                <tr id="trRefund">
                    <td colspan="9" style=" border-right:0px;" class="exceDataTd">Loading....</td>
                </tr>
                </tbody>
            </table>
            <br><br>
        </div>
    </div>
</form>
</body>
</html>