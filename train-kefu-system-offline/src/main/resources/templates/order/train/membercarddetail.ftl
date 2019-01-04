<!DOCTYPE html>

<html lang="en">

<head id="Head1"><title>
    会员详情
</title>
    <link id="lnkGlobal" rel="stylesheet" type="text/css" href="http://webresint.sh.ctriptravel.com/styles/template/public_global.css?releaseno=2018_6_29">
    <link id="lnkOffline" rel="stylesheet" type="text/css" href="http://webresint.sh.ctriptravel.com/styles/template/public_offline.css?releaseno=2018_6_29">
    <link id="lnkMult" rel="stylesheet" type="text/css" href="http://webresint.sh.ctriptravel.com/ResTrainOffline/R1/OrderOffline/CSS/jquery.multiselect.css?releaseno=2018_6_29">
</head>
<body style="margin: 0 auto; width: 1170px; padding-top: 5px;">
<form id="form1">
    <div id="pubglobal_header" style=" width:1170px">
            <span class="topbar">
            </span><strong>
        会员详情
    </strong>
    </div>
    <div class="master_content">
        <div class="master_contentplaceholder">
            <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
            <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>

            <style>
                br {display:none; }

                .overlay {
                    position: fixed;
                    top: 0;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    background: rgba(0, 0, 0, 0.7);
                    transition: opacity 500ms;
                    visibility: hidden;
                    opacity: 0;
                }

                .overlay {
                    position: fixed;
                    top: 0;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    background: rgba(0, 0, 0, 0.7);
                    transition: opacity 500ms;
                    visibility: hidden;
                    opacity: 0;
                }
                .overlay:target {
                    visibility: visible;
                    opacity: 1;
                }

                .popup {
                    margin: 70px auto;
                    padding: 20px;
                    background: #fff;
                    border-radius: 5px;
                    width: 30%;
                    position: relative;
                    transition: all 5s ease-in-out;
                }

                .popup h2 {
                    margin-top: 0;
                    color: #333;
                    font-family: Tahoma, Arial, sans-serif;
                }
                .popup .close {
                    position: absolute;
                    top: 20px;
                    right: 30px;
                    transition: all 200ms;
                    font-size: 30px;
                    font-weight: bold;
                    text-decoration: none;
                    color: #333;
                }
                .popup .close:hover {
                    color: #06D85F;
                }
                .popup .content {
                    max-height: 30%;
                    overflow: auto;
                }

                .orderRelated a {
                    margin-left :20px;
                }

                table tr td {
                    font-family: "微软雅黑";
                    font-size: 14px;
                    padding: 10px;
                }

                table tr td:not(input){
                    text-align: left;
                }

                table tr th {
                    font-family: "微软雅黑";
                    font-weight: bold;
                    text-align: center;
                }

                .CPadding strong{
                    margin-left :12px;
                }

                input[type=text] {
                    border-bottom: 1px solid #dbdbdb;
                    border-top: 0px;
                    border-left: 0px;
                    border-right: 0px;
                    width:30px;
                    text-align:center;
                }

                .spantitle{
                    font-family: "微软雅黑";
                    font-size: 20px;
                    padding: 10px;
                    text-align:center;
                    display: block;
                    font-weight: bold;
                }

                .spandetail{
                    font-family: "微软雅黑";
                    font-size: 14px;
                    padding: 10px;
                    text-align:center;
                    display: block;
                }

                #btncancelmembercard {
                    height: 24px;
                    line-height: 22px;
                    padding: 2px 11px;
                    border: 1px #7AA4AD solid;
                    border-radius: 3px;
                    text-decoration: none;
                    font-size: 13px;
                    color: #000000;
                }

                .refundFoot input{
                    font-family: "微软雅黑";
                    border-radius: 3px;
                }

            </style>
            <table class="table table-hover table-condensed" style="border-collapse: collapse;">
                <tr>
                    <td colspan="4" style="float: initial; padding-left: 10px;">
                        <table cellspacing="0">
                            <tr style="height: 30px">
                                <td bgcolor="#fff" style="width: 200px; margin-left: 100px;text-align: left;">
                                    <strong>会员类型：</strong>超级会员
                                </td>

                                <#if ismemberCard>
                                <td bgcolor="#fff" class="auto-style2" style="text-align: right;width: 880px;">
                                    <a id="btncancelmembercard" href="#cancelmembercard">退会员</a>
                                </td>
                                <#else>
                                <td bgcolor="#fff" class="auto-style2" style="text-align: right;">
                                    <span style="color: red;"> ${useInfo} 已使用会员权益，无法退 </span>
                                </td>
                                </#if>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <table border="1" style="padding: 10px;">
                            <tr>
                                <th style="width:180px;">特权名称</th>
                                <th>特权说明</th>
                            </tr>
                            <tbody>
                            <#if memberCardlist?has_content>
                                <#list  memberCardlist  as  memberCard>
                                <tr style="border-bottom: 1px;">
                                    <td><p>${memberCard.rightsName}<p><span style=" <#if (memberCard.useCount>0)> color:red; </#if> font-size: 10px;">使用次数：${memberCard.useCount}</span>
                                        <#if (memberCard.RightsBuCode == "TRAINS" && listorderid?has_content)>
                                            <#list  listorderid  as  orderid>
                                                <p style="font-size: 10px;"><a href="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ShowOrderDetailNew.aspx?orderID=${orderid}" target="_blank">${orderid}</a></p>
                                            </#list>
                                        </#if>
                                    </td>
                                    <td class="tdcontent">${memberCard.useDesctription}</td>
                                </tr>
                                </#list>
                            <#else >
                            <tr>
                                <td colspan="15" style="height: 70px;text-align: center">没有找到数据</td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>
                    </td>
                </tr>

            </table>
            <div id="cancelmembercard" class="overlay">
                <div class="popup">
                    <#--<h3 style="background-color: #0099FF; height: 35px; line-height: 35px; padding: 0 10px 0 18px; font-size: 14px; color: #fff; margin: 0; text-align: left; position: relative;">-->
                        <#--<a href="#" title="关闭" style="font-size: 14px; color: #fff; position: absolute; right: 10px; display: inline-block; text-decoration: none;">X</a>-->
                    <#--</h3>-->
                    <div class="refundContent" style="margin-top: 10px;">
                        <span class="spantitle">确认退超级会员？</span>
                        <span class="spandetail">确认后退款将在1～3个工作日内退回您的帐户</span>
                    </div>
                    <div class="refundFoot" style="margin-bottom: 15px; margin-top: 10px; text-align: center;">
                        <input type="button" value="确认" style="width: 65px;margin-right: 50px;" id="dClose" onclick="CancelMemberCard();" />
                        <input type="button" value="取消" style="width: 65px;" id="dClose" onclick="javascript: location.href = '#';" />
                    </div>
                </div>
            </div>
        </div>
    </div>

</form>
</body>
</html>
<script type="text/javascript">
    function CancelMemberCard() {

        var url='${ctx}/train/order/cancelmembercard';
        $.ajax({
            url:url,
            type:"Post",
            data:{productid:getUrlParam("productid"),operator:encodeURI(getUrlParam("operator")),uid:getUrlParam("uid"),orderid:getUrlParam("orderid"),cardcode:getUrlParam("cardcode")},
            dataType : 'json',
            success: function (data) {
                alert(data.data.msg);
                window.close();
            },
            error: function (data) {
                alert(data.data.msg);
            }
        });
    }

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return decodeURI(r[2]); return null; //返回参数值
    }

    function show() {
        var box = $(".tdcontent");
        box.each(function () {
            var text = $(this).html();               //text是所有的文本
            var newBox = document.createElement("div"); //截取要一开始先显示的一部分放入newBox里面
            var btn = document.createElement("a");
            newBox.innerHTML = text.substring(0, 200);
            btn.innerHTML = text.length > 200 ? "...显示全部" : "";
            btn.href = "###";
            btn.onclick = function () {
                if (btn.innerHTML == "...显示全部") {
                    btn.innerHTML = "收起";
                    newBox.innerHTML = text;
                } else {
                    btn.innerHTML = "...显示全部";
                    newBox.innerHTML = text.substring(0, 200);
                }
            }
            $(this).html("");
            $(this).append(newBox);
            $(this).append(btn);
        });
    }
    show();

</script>
