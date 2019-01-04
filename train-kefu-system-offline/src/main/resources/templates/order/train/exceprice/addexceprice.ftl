<!DOCTYPE html>

<html lang="en">

<head id="Head1" runat="server">
    <title>添加异常款项</title>
    <style type="text/css">
        .tdleft
        {
            text-align: right;
            border: 1px solid #91AFDD;
            border-right: 0;
            border-top: 0;
        }
        .tdright
        {
            text-align: left;
            border: 1px solid #91AFDD;
            border-top: 0;
            padding-left: 5px;
        }
        table tr
        {
            height: 31px;
        }
        .selectClass
        {
            width: 100px;
            height: 21px;
        }
        .window_pop .window_pop_bd p
        {
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var type = "${type!""}";
            if (type == "update") {

                var selectResponsibility = "${excepricedata.getResponsibility()!""}";
                var selectResponsQuestion = "${excepricedata.getResponsQuestion()!""}";
                var selectResponsReason = "${excepricedata.getResponsReason()!""}";
                $("#ResponsibilitySelectText").val(selectResponsibility);
                ChangeValue($("#ResponsibilitySelectText"));
                $("#ResponsQuestionText").val(selectResponsQuestion);
                ChangeValue($("#ResponsQuestionText"));
                $("#ResponsiReasonText").val(selectResponsReason);
                var operatingType = "${excepricedata.getOperatingType()!""}";
                if(operatingType=="1"){
                    $("#RefunPriceSelect").attr("checked","checked");
                    $("#BeiPriceSelect").removeAttr("checked");
                }else{
                    $("#RefunPriceSelect").removeAttr("checked");
                    $("#BeiPriceSelect").attr("checked","checked");
                }

                SetHtml(operatingType);
            }else{
                ChangeValue($("#ResponsibilitySelectText"));
            }

            $("button").bind("click",function(){
                $("p").slideToggle();
            });
        });

        function Check() {
            var questionDesc = $("#QuestionDesc").val();
            if (questionDesc == "") {
                alert("请输入问题描述!");
                return false;
            }

            var orderPrice = ${zhixingCarResponse.payPrice!0};
//            if($("#TicketPrice").val() > 0){
//                if($("#TicketPrice").val() > orderPrice){
//                    alert("退款金额不可超出订单支付金额!");
//                    return false;
//                }
//            }

            if (!($("#TotalPrice").val() > 0) && !($("#OtherPrice").val() > 0)) {
                alert("退款金额和额外赔偿金额不能同时为空!");
                return false;
            }

            if ($("#OtherPrice").val() > 0) {  //线下银行卡线下必填
                var bankAccountName = $("#BankAccountNameTB").val().trim();
                var bankAddress = $("#BankAddressTB").val().trim();
                var bankName = $("#BankNameTB").val().trim();
                var bankNo = $("#BankNoTB").val().trim();
                if (bankAccountName == "" || bankAddress == "" || bankName == "" || bankNo == "") {
                    alert("额外赔偿银行卡信息必填！");
                    return false;
                }
            }

            var responsQuestion = $("#ResponsQuestionText").val();
            var responsReason = $("#ResponsiReasonText").val();
            if (responsQuestion == null || responsQuestion.length == 0) {
                alert("责任方问题必填！");
                return false;
            }
            if (responsReason == null || responsReason.length == 0) {
                alert("责任方原因必填！");
                return false;
            }
            SaveData();
            return true;
        }

        //保存数据
        function SaveData() {

            var excePrice = {};
            var type = "${type!""}";
            if (type == "update") {
                excePrice.exceID = "${excepricedata.getExceID()!""}";
            }else{
                excePrice.exceID = "0";
            }
            excePrice.channel = "${zhixingCarResponse.channel!""}";
            excePrice.productLine = "${productLine!""}";
            excePrice.totalPrice = $("#TotalPrice").val();
            excePrice.orderDate = $("#LabOrderDate").html();
            excePrice.insertName = $("#LabName").html();
            excePrice.orderNumber = $("#OrderNumberText").val();
            excePrice.responsibility = $("#ResponsibilitySelectText").val();
            excePrice.responsQuestion = $("#ResponsQuestionText").val();
            excePrice.responsReason = $("#ResponsiReasonText").val();
            excePrice.questionDesc = $("#QuestionDesc").val();
            excePrice.bankAccountName = $("#BankAccountNameTB").val();
            excePrice.bankAddress = $("#BankAddressTB").val();
            excePrice.bankName = $("#BankNameTB").val();
            excePrice.bankNo = $("#BankNoTB").val();
            // 退款
            if ($("#TotalPrice").val() > 0) {
                excePrice.operatingType = "1";
                excePrice.ticketPrice = $("#TicketPrice").val();
                excePrice.otherPrice = $("#OtherPrice").val();
                excePrice.totalPrice = $("#TotalPrice").val();
            }

            if (excePrice.exceID == "0") {
                // 赔款
                excePrice.accpetCompany = "0";
                excePrice.accpetName = "0";
                excePrice.backReason = "0";
            }
            if ($("#RepaPrice").val() > 0) {
                excePrice.operatingType = "3";
                excePrice.repaPrice = $("#RepaPrice").val();
            }

            var url = "${ctx}/train/order/exceprice/addupdate";
            $.ajax({
                type: 'POST',
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(excePrice),
                success: function (data) {
                    if(data.message != "Fail") {
                        if (data.status == 0) {
                            if (type == "update") {
                                setTimeout(WindowClose, 100);
                                window.opener.LoadData(1, 'go');
                            }
                            else {
                                alert("添加异常件成功！");
                                setTimeout(WindowClose, 1000);
                            }
                        }
                        else {
                            alert(data.data.msg + "失败！");
                        }
                    }else{
                       alert(data.data.msg);
                    }
                },
                error: function (data) {
                    alert(data.data.msg);
                }
            });
        }


        function ChangeValue(obj) {
            if($(obj).val() == ""){
                return false;
            }
            var url = "${ctx}/train/order/exceprice/getResponsibility";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                async: false,
                data: {fkUpperTid: $(obj).find("option:selected").attr("ref")},
                success: function (data) {
                    var superStr = "";
                    var info = data.data.response;
                    if (info.length >= 1) {
                        var tragetid = "ResponsiReasonText";
                        if ($(obj).attr('id') == "ResponsibilitySelectText") {   //责任方
                            $("#ResponsQuestionText").html("");
                            tragetid = "ResponsQuestionText";
                        }
                        $("#ResponsiReasonText").html("");
                        superStr += "<option value=\"\"></option>";
                        for (var i = 0; i < info.length; i++) {
                            superStr += "<option value=\"" + data.data.response[i].fieldValue + "\" ref=\"" + data.data.response[i].tid + "\">" + data.data.response[i].fieldName + "</option>";
                        }
                        $("#" + tragetid).html(superStr);
                    }
                }
            });
        }

        function AddValue(val) {
            $("#ResponsiReasonHidden").value = val;
        }

        function CloseStopStation() {
            $("#stopstationalert").unmask();
        }

        function SetHtml(type) {
            if (type == "1") {
                $("#tbrefund").show();
                $("#tblosrprice").hide();
            } else {
                $("#tbrefund").hide();
                $("#tblosrprice").show();
            }
        }

        function AddPrice() {
            var otherPrice = $("#OtherPrice").val() == "" ? "0" : $("#OtherPrice").val();
            var ticketPrice = $("#TicketPrice").val() == "" ? "0" : $("#TicketPrice").val();
            var total = parseFloat(ticketPrice)  + parseFloat(otherPrice);
            $("#TotalPrice").val(total);
        }

        function WindowClose() {
            window.close();
        }

        function AddAcceptValue(type, val) {
            if (type == "1") {
                $("#HiddenAcceptNameSelect").value = val;
                $("#HiddenAcceptNameText").value = $('#acceptNameSelect option:selected').text();
            } else if (type == "2") {
                $("#HiddenBackReason").value = val;
            } else if (type == "3") {
                $("#ResponsiQuestionHidden").value = val;
                $("#ResponsiQuestionNameHidden").value = $('#ResponsQuestionText option:selected').text();
            }
        }

        function selectOne(myselectid, keywords) {
            keywords = keywords.replace(/ /g, "");    //去掉空格
            var myselect = document.getElementById(myselectid);  //得到select对象
            for (i = 0; i < myselect.length; i++)// 循环option
            {
                if (myselect.options[i].text.indexOf(keywords) != -1) //判断option的text是否包含keyword
                {
                    myselect.options[i].selected = true; //选中
                    $("#txtQuestionId")[0].value = "";
                    document.getElementById(myselectid).onchange();
                    break;
                }
                else {
                    myselect.options[i].selected = false; //不包含keywords的取消选中
                }
            }
        }
        function checkamount(obj) {
            if (!(/^\d+$/).test(obj.value)) {
                obj.value = obj.value.replace(/[^0-9\-\.]/g, '');
            }
        }
    </script>
</head>
<body>
<form id="form1" runat="server">
    <div style="margin: 0 auto; width: 800px; font-size: 12px;">
        <table border="0" cellpadding="0px" cellspacing="0px" style="width: 800px; height: 520px;
            margin: 0 auto; color: #003366; position: relative;">
            <tr>
                <td colspan="3" style="text-align: left; font-weight: bold; color: Black;">
                    <span>日期：<span id="LabOrderDate">${orderDate}</span></span>
                    <span style="margin-left: 100px;">录入人：<span id="LabName">${insertName}</span></asp:Label></span>
                    <span style="margin-left: 100px;">
                        产品线：${productname}</span>
                    <span style="margin-left: 100px;">
                        供应商：<span id="spanpartnerName">${zhixingCarResponse.partnerName!""}</span></span>
                </td>
            </tr>
            <tr>
                <td style="width: 80px; text-align: right; border: 1px solid #91AFDD; border-right: 0;">
                    订单号：
                </td>
                <td style="text-align: left; border: 1px solid #91AFDD; padding-left: 5px;" colspan="2">
                    <input type="text" id="OrderNumberText"  ReadOnly="true" value="${orderid!""}"/>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    责任方：
                </td>
                <td class="tdright" colspan="2">
                    <select name="ResponsibilitySelectText" id="ResponsibilitySelectText" class="selectClass" onchange="ChangeValue(this);">
                    <#if responsibilitylist?has_content>
                        <#list  responsibilitylist  as  responsibility>
                            <option value="${responsibility.getFieldValue()}" ref="${responsibility.getTid()}">${responsibility.getFieldName()}</option>
                        </#list>
                    </#if>
                    </select>
                    &nbsp;问题：
                    <input type="text" id="txtQuestionId" runat="server" style="position: absolute; width: 79px; border: 0;
                        margin-top: 1px; margin-left: 2px; height: 17px; background: transparent;" onkeyup="selectOne('ResponsQuestionText',this.value);" />
                    <select name="ResponsQuestionText" id="ResponsQuestionText" runat="server" class="selectClass" onchange="ChangeValue(this);AddAcceptValue(3,this.value);">
                    </select>
                    &nbsp;原因：
                    <select name="ResponsiReasonText" id="ResponsiReasonText"  runat="server" style="width: 170px;
                        height: 21px;" onchange="AddValue(this.value)">
                </select>
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    问题描述：
                </td>
                <td class="tdright" colspan="2">
                    <textarea name="QuestionDesc" rows="2" cols="20" id="QuestionDesc" style="height: 110px; width: 400px;">${excepricedata.getQuestionDesc()!""}</textarea>
                    <in
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    退款金额：
                </td>
                <td class="tdright">
                    <input name="TicketPrice" type="text" id="TicketPrice" value="${excepricedata.getTicketPrice()!""}" onkeyup="checkamount(this);AddPrice();"/>
                    <span style="color: grey;"> 线上，不可超出订单支付金额</span>
                </td>
                <td class="tdright">
                    开户银行：<input id="BankNameTB" placeholder="x银行x分行x支行" value="${excepricedata.getBankName()!""}" >
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    额外赔偿：
                </td>
                <td class="tdright">
                    <input type="text" id="OtherPrice" value="${excepricedata.getOtherPrice()!""}"  onkeyup="checkamount(this);AddPrice();">
                    <span style="color: grey;"> 线下</span>
                </td>
                <td class="tdright">
                    开户姓名：<input id="BankAccountNameTB" value="${excepricedata.getBankAccountName()!""}" />
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    公司损失：
                </td>
                <td class="tdright">
                    <input type="text" id="RepaPrice" value="${excepricedata.getRepaPrice()!""}"  onkeyup="checkamount(this);">
                    <span style="color: grey;"> 仅记录公司损失金额不会发起退款</span>
                </td>
                <td class="tdright">
                    开户帐号：<input id="BankNoTB" value="${excepricedata.getBankNo()!""}"/>
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;"><span id="BitianFlag" style="color: Red"></span></span>
                    <span id="AccountChceck" style="color: Red"></span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    合计金额：
                </td>
                <td class="tdright">
                    <input type="text" style="border: 1px solid #C0C0C0; background-color: #D3D3D3;" id="TotalPrice" readonly="readonly" value="${excepricedata.getTotalPrice()!""}" onkeyup="checkamount(this);">
                </td>
                <td class="tdright">
                    开户地址：<input type="text" id="BankAddressTB" value="${excepricedata.getBankAddress()!""}" placeholder="开户行所在省、地级市">
                </td>
            </tr>
            <tr style="height: 50px; text-align: center;">
                <td colspan="3">
                <#if isSave == "1">
                    <input type="button" id="BtnOK" disabled="disabled" value="保存" Style="width: 80px; height: 25px;"
                           onclick="return Check();">
                <#else>
                    <input type="button" id="BtnOK" value="保存" Style="width: 80px; height: 25px;"
                           onclick="return Check();">
                </#if>
                    <input id="BtnCancel" type="button" value="取消" style="width: 80px; height: 25px;
                        margin-left: 50px;" onclick="WindowClose();" />
                </td>
            </tr>
        </table>
        <input type="hidden" name="HiddenExceID" id="HiddenExceID" value="0">
    </div>
</form>
</body>
</html>