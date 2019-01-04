<!DOCTYPE html>

<html lang="en">

<head id="Head1" runat="server">
    <title>混合电子发票</title>
    <style type="text/css">
        table tr td {
            font-family: "微软雅黑";
            padding: 10px;
        }

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
            height: 40px;
            text-align: center;
        }

        .window_pop .window_pop_bd p
        {
            text-align: center;
        }

        /*.sptitle{*/
            /*vertical-align: 3px;*/
        /*}*/

        table input[type=checkbox]
        {
            margin-left: 20px;
        }
        table input[type=radio]
        {
            margin-left: 20px;
        }
        table input[type=text]
        {
            width: 220px;
        }
        .code_btn_0{
            margin-top: 20px;
            text-align: center;
            margin-bottom: 20px;
        }

        body div[role=tooltip]{
            max-width: 2000px;
        }

        #form1 th {
            text-align: center;
        }

        .tdinvoiceType {
            text-align: left;
        }
    </style>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/util/Watermark.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript">

        $(function(){
            $("[data-toggle='popover']").popover({
                trigger:'manual',
                placement : 'left', //placement of the popover. also can use top, bottom, left or right
                title : '<div style="text-align:center; color:red; text-decoration:underline; font-size:14px;"> Muah ha ha</div>', //this is the top title bar of the popover. add some basic css
                html: 'true', //needed to show html of course
                content : function () {
                            return content(this); //this.after().find("table").html();
                        },
                animation: false
            }).on("mouseenter", function () {
                var _this = this;
                $(this).popover("show");
                $(this).siblings(".popover").on("mouseleave", function () {
                    $(_this).popover('hide');
                });
            }).on("mouseleave", function () {
                var _this = this;
                setTimeout(function () {
                    if (!$(".popover:hover").length) {
                        $(_this).popover("hide")
                    }
                }, 1000);
            });
        });

        //模拟动态加载内容(真实情况可能会跟后台进行ajax交互)  
        function content(obj) {
           return  "<table>" + $(obj).next().find("table").html() + "</table>";
        }

        function Check() {
            if( $("input[name='rdinvoice']:checked").val() == "2" && $("#XPayerNumberTxt").val().trim() == ""){
                alert("抬头类型为公司时纳税人识别码不能为空!");
                return false;
            }
            if($("#XTitleTxt").val().trim() == "" || $("#XMailTxt").val().trim() == ""){
                alert("抬头且邮箱不能为空!");
                return false;
            }
            SaveData();
            return true;
        }

        function SaveData() {

            var vmInvoiceDetail = {};    //发票信息
            var msginfo = "";
            vmInvoiceDetail.address = $("#XAddressTxt").val();
            vmInvoiceDetail.bankNumber = $("#XBankNumberTxt").val();
            vmInvoiceDetail.mail = $("#XMailTxt").val();
            vmInvoiceDetail.payer = $("#XPayerNumberTxt").val();
            vmInvoiceDetail.tel = $("#XTelTxt").val();
            vmInvoiceDetail.title = $("#XTitleTxt").val();
            vmInvoiceDetail.openBank = $("#XOpenBankTxt").val();

            var orderList = new Array();
            $('input[name=CheckAlone]').each(function () {
                var vmInvoiceOrderInfo = {}
                if ($(this).prop('checked')) {
                    var trparent = $(this).parent().parent();
                    var chinvoicestate = 0;
                    trparent.find("input[name='chinvoice']").each(function () {
                        if ($(this).prop('checked')){
                            chinvoicestate = (chinvoicestate | $(this).val())
                        }
                    });
                    if(chinvoicestate == 0){

                        msginfo += trparent.find("span[name='spanordernumber']").html() + ",";
                    }
                    vmInvoiceOrderInfo.orderNumber = trparent.find("span[name='spanordernumber']").html();
                    vmInvoiceOrderInfo.partnerName = trparent.find("input[name='hidpartnerName']").val();
                    vmInvoiceOrderInfo.invoiceType = chinvoicestate;
                    orderList.push(vmInvoiceOrderInfo);
                }
            });
            vmInvoiceDetail.orderList = orderList;

            if (msginfo != "") {
                alert("订单：" + msginfo.substring(0,msginfo.length-1) + "请勾选补寄类型！");
                return false;
            }

            if ($("#chinvoicesuper").prop('checked')) {
                vmInvoiceDetail.superOrderNumber = "${superVipOrderNumber!""}";
                vmInvoiceDetail.superProductId = "${productId!""}";
                vmInvoiceDetail.superuid = "${superuid!""}";
            }

            var url = "${ctx}/train/order/einvoice/createinvoice";
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(vmInvoiceDetail),
                success: function (data) {
                    alert(data.data.msg);
                    console.log(data);
                    if(data.data.msg[0].indexOf("申请成功") != -1){
                        window.close();
                    }
                }
            });
        }

        function CheckAllClick(obj) {
            $('input[name=CheckAlone]').each(function () {
                $(this).prop('checked', $(obj).prop('checked'));
            });
        }

        function openlog() {
            $('#rebookFeeDiv').modal();
            return false;
        }

        function openlog2() {
            $('#rebookFeeDiv2').modal();
            return false;
        }
        function AllDelAddress() {
            var iWidth = 800; //弹出窗口的宽度;
            var iHeight = 600; //弹出窗口的高度;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
            var mobileNum = '${mobile!""}';
            var receiveName = '${delivery.addresseeName!""}';
            var address = encodeURI('${delivery.invoiceMailAddress!""}');
            var zipCode = '${delivery.zipCode!""}';
            window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/UserAllInvoice.aspx?mobileNum=' + mobileNum + '&receiveName=' + receiveName + '&address=' + address + '&zipCode=' + zipCode,
                    '新增混合配送地址', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth +
                    ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
        }
    </script>
</head>
<body>
<form id="form1" runat="server">
    <div style="margin: 0 auto; width: 1400px; font-size: 12px;">
        <table border="0" cellpadding="0px" cellspacing="0px" style="width: 800px; margin: 0 auto; color: #003366; position: relative;">
            <tr>
                <td colspan="3" style="font-size: large;font-weight: bold;text-align: center;">
                    寄送电子发票
                </td>
            </tr>
            <tr>
                <td colspan="3" style="text-align: left; font-weight: bold; color: Black;">
                    <span>发票金额：
                    <#if invoiceInfoList?has_content && invoiceInfoList[0].totalAmount??>
                    ${invoiceInfoList[0].totalAmount}
                    </#if>
                    </span>
                    <span style="margin-left: 100px;">电子发票状态：
                    <#if invoiceInfoList?has_content && invoiceInfoList[0].invoiceState??>
                    ${invoiceInfoList[0].invoiceState}
                    </#if>
                    </span>
                        <#if invoiceInfoList?has_content>
                            <#if invoiceInfoList[0].pdfUrl?? && invoiceInfoList[0].pdfUrl!= "">
                                <a style="margin-left: 100px;" href="${invoiceInfoList[0].pdfUrl}"
                                   target="_blank">查看发票详情</a>
                            <#else>
                                <span style="margin-left: 100px;font-weight: normal;" >查看发票详情</span>
                                <#--<a style="margin-left: 100px;" href="javascript:void();">查看发票详情</a>-->
                            </#if>
                        <#else>
                            <span style="margin-left: 100px;font-weight: normal;" >查看发票详情</span>
                        </#if>
                    <a style="margin-left: 50px;" href="javascript:void();" onclick="javascript:openlog();">查看寄送详情</a>
                    <a style="margin-left: 40px;" href="javascript:void();" onclick="AllDelAddress()">混合补寄发票</a>
                </td>
            </tr>
            <tr>
                <td style="width: 120px; text-align: right; border: 1px solid #91AFDD; border-right: 0;">
                    抬头类型：
                </td>
                <td style="text-align: left; border: 1px solid #91AFDD; padding-left: 5px;" colspan="2">
                    <input type="radio" name="rdinvoice" value="1" /><span class="sptitle">个人</span>
                    <input type="radio" name="rdinvoice" value="2" /><span class="sptitle">公司</span>
                    <input type="radio" name="rdinvoice" value="3" /><span class="sptitle">政府机关</span>
                </td>
            </tr>

        <#if invoiceInfoList?has_content>
            <tr>
                <td class="tdleft">
                    发票抬头：
                </td>
                <td class="tdright">
                    <input type="text" id="XTitleTxt" value="${invoiceInfoList[0].invoiceTitle!""}" placeholder="填写个人姓名或者公司名称">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    电子邮箱：
                </td>
                <td class="tdright">
                    <input type="text" id="XMailTxt" value="${invoiceInfoList[0].receiveEmail!""}" placeholder="用于接收电子发票的邮箱">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    纳税人识别号：
                </td>
                <td class="tdright">
                    <input type="text" id="XPayerNumberTxt" value="${invoiceInfoList[0].taxpayNumber!""}" placeholder="15、17、18或20位纳税人识别号">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                    <span style="color: grey;">抬头为公司时必填</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    公司地址：
                </td>
                <td class="tdright">
                    <input type="text" id="XAddressTxt" value="${invoiceInfoList[0].receiveAddress!""}">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    电话：
                </td>
                <td class="tdright">
                    <input type="text" id="XTelTxt" value="${invoiceInfoList[0].receiverMobile!""}">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    开户行：
                </td>
                <td class="tdright">
                    <input type="text" id="XOpenBankTxt" value="">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    帐号：
                </td>
                <td class="tdright">
                    <input type="text" id="XBankNumberTxt" value="">
                </td>
            </tr>
        <#else >
            <tr>
                <td class="tdleft">
                    发票抬头：
                </td>
                <td class="tdright">
                    <input type="text" id="XTitleTxt" value="" placeholder="填写个人姓名或者公司名称">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    电子邮箱：
                </td>
                <td class="tdright">
                    <input type="text" id="XMailTxt" value="" placeholder="用于接收电子发票的邮箱">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    纳税人识别号：
                </td>
                <td class="tdright">
                    <input type="text" id="XPayerNumberTxt" value="" placeholder="15、17、18或20位纳税人识别号">
                    <span style="color: Red; vertical-align: bottom; margin-left: 5px;">*</span>
                    <span style="color: grey;">抬头为公司时必填</span>
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    公司地址：
                </td>
                <td class="tdright">
                    <input type="text" id="XAddressTxt" value="">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    电话：
                </td>
                <td class="tdright">
                    <input type="text" id="XTelTxt" value="">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    开户行：
                </td>
                <td class="tdright">
                    <input type="text" id="XOpenBankTxt" value="">
                </td>
            </tr>
            <tr>
                <td class="tdleft">
                    帐号：
                </td>
                <td class="tdright">
                    <input type="text" id="XBankNumberTxt" value="">
                </td>
            </tr>
        </#if>
        </table>


        <table cellspacing="0" cellpadding="3" rules="all" border="1" id="ctl00_MainContentPlaceHolder_GridView1"
               style="margin-top:20px;background-color:White;border-color:#CCCCCC;border-width:1px;border-style:None;font-size:9pt;width:100%;border-collapse:collapse;">
            <tbody>
            <tr style="color:White;background-color:#006699;font-weight:bold;">
                <th scope="col" width="4%">
                    <input id="CheckAll" type="checkbox"
                           name="CheckAll"
                           onclick="javascript: CheckAllClick(this);">
                </th>
                <th scope="col" width="10%">订单号</th>
                <th scope="col" width="12%">出行时间</th>
                <th scope="col" width="10%">出发城市</th>
                <th scope="col" width="10%">到达城市</th>
                <th scope="col" width="40%">补寄类型</th>
            </tr>
            <#if orderInfoList?has_content>
                <#list orderInfoList as orderInfo>
                <tr>
                    <td>
                        <input id="CheckAlone" type="checkbox" name="CheckAlone">
                    </td>
                    <td>
                        <a href="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ShowOrderDetailNew.aspx?OrderID=${orderInfo.orderNumber!""}" target="_blank" class="A1">
                        <span id="spanordernumber" name="spanordernumber">${orderInfo.orderNumber!""}</span></a>
                        <input type="hidden" id="hidpartnerName" name="hidpartnerName" value="${orderInfo.partnerName!""}">
                    </td>
                    <td>${orderInfo.ticketTime!""}</td>
                    <td>${orderInfo.fromStationName!""}</td>
                    <td>${orderInfo.toStationName!""}</td>
                    <td class="tdinvoiceType">
                    ${orderInfo.invoiceTypebtn!""}
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>

        <div class="code_btn_0">
            <input type="button" id="BtnOK" value="开电子发票" Style="width: 80px; height: 25px;" onclick="return Check();">
            <input id="BtnCancel" type="button" value="取消" style="width: 80px; height: 25px;
                        margin-left: 50px;" onclick="WindowClose();"/>
        </div>

        <div class="modal fade" id="rebookFeeDiv" tabindex="-1" role="dialog" aria-labelledby="OutBoundModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width: 850px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">操作日志
                        </h4>
                    </div>
                    <div class="modal-body ">
                        <div class="input-group" style="padding-bottom: 3px;">
                            <table border="1" width="800px;">
                                <tbody>
                                <tr>
                                    <td>序号</td>
                                    <td style="min-width: 40px;">订单号</td>
                                    <td>操作人</td>
                                    <td>操作时间</td>
                                    <td style="max-width: 500px;">内容</td>
                                    <td style="min-width: 100px;">发票类型</td>
                                    <td style="min-width: 150px;">状态</td>
                                    <#--<td style="min-width: 180px;">备注</td>-->
                                </tr>
                                <#if invoiceInfoList?has_content>
                                    <#list invoiceInfoList as invoiceInfo>
                                    <tr>
                                        <td>${invoiceInfo_index+1}</td>
                                        <td>${invoiceInfo.orderNumber!""}</td>
                                        <td>${invoiceInfo.operator!""}</td>
                                        <td>${invoiceInfo.invoiceTime!""}</td>
                                        <td>申请电子发票</td>
                                        <td>${invoiceInfo.invoiceType!""}
                                            </td>
                                        <td>${invoiceInfo.invoiceState!""}
                                            <#if invoiceInfo?has_content>
                                                <button style="margin-top: -3px;" type="button" class="btn btn-xs btn-link" title="发票详情"
                                                        data-container="body" data-toggle="popover">
                                                    详细
                                                </button>
                                                <div style="display: none;">
                                                <table>
                                                    <tr><td style="width: 110px">发票金额</td><td>${invoiceInfo.totalAmount!""}</td></tr>
                                                    <tr><td>发票类型</td><td>${invoiceInfo.invoiceType!""}</td></tr>
                                                    <tr><td>抬头类型</td><td></td></tr>
                                                    <tr><td>发票抬头</td><td>${invoiceInfo.invoiceTitle!""}</td></tr>
                                                    <tr><td>电子邮箱</td><td>${invoiceInfo.receiveEmail!""}</td></tr>
                                                    <tr><td>纳税人识别号</td><td>${invoiceInfo.taxpayNumber!""}</td></tr>
                                                    <tr><td>公司地址</td><td>${invoiceInfo.receiveAddress!""}</td></tr>
                                                    <tr><td>电话</td><td>${invoiceInfo.receiverMobile!""}</td></tr>
                                                    <tr><td>开户行</td><td>${invoiceInfo.openBank!""}</td></tr>
                                                </table>
                                            </div>
                                            </#if>
                                        </td>
                                        <#--<td>${invoiceInfo.operator!""}</td>-->
                                    </tr>
                                    </#list>
                                </#if>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" language="javascript">
        <#--$(document).ready(function () {-->
            <#--watermark({watermark_txt: "${eid}"}); //传入动态水印内容-->
        <#--}); 浮层不会调，会有水印遮盖按钮，先注释-->
    </script>

</form>
</body>
</html>