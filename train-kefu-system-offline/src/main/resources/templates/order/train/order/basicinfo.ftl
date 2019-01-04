
<#--<link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>-->
<#--<link type="text/css" rel="stylesheet" href="${ctx}/static/layer/theme/default/layer.css"/>-->
<#--<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>-->
<#--<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>-->

<#--<div class="WrapDetails" id="app">-->
    <#--<div class="lefter">-->
        <#--<!--订单基本信息&ndash;&gt;-->
        <div class="OrderInfo">
            <ul class="TopTit">
                <li class="title">订单基本信息</li>
                <li class="tjsj">提交时间：${basic.orderTime!""}</li>

                <#if basic.partherName == "qunarsync" || basic.partherName == "qunar">
                    <li class="tjsj">
                        <#if basic.partherName == "qunarsync">
                            <a href="http://kefu.train.ctripcorp.com/offline/order/train/detail?orderNumber=${basic.partnerOrderId!""}ofctrip">切换至双订单页面</a>
                        <#else >
                            <a href="javascript:void(0);" onclick="qunaerLocation();">切换至同步订单页面</a>
                        </#if>
                    </li>
                </#if>
            </ul>
            <a class="log" href="javascript:void(0)" onclick="openLog()">查看日志</a>
            <div class="InfoBox">
                <ul class="left">
                    <li><b>订单号</b><span style="position: absolute;">${basic.partnerOrderId!""}</span>
                        <#if basic.isIncludeCalculate?has_content && basic.isIncludeCalculate == true>
                        <i></i>
                        </#if>
                    </li>
                    <li><b>预定渠道</b>
                        <#if basic.partherName?has_content>
                            <#if basic.partherName == "Ctrip.Corp">
                                <span title='${basic.orderSourceNameD!""}' style='position: absolute;'>${basic.orderSourceName!""}<img style='margin-top: 10px;float: right;margin-right: -50px;'
                                         src='${ctx}/static/assets/images/xs.png'>
                                </span>
                            <#elseif basic.partherName == "tieyou.businesstravel">
                                <span title='${basic.orderSourceNameD!""}' style='position: absolute;'>${basic.orderSourceName!""}<img style='margin-top: 10px;float: right;margin-right: -50px;'
                                         src='${ctx}/static/assets/images/ts.png'>
                            </span>
                            <#elseif basic.partherName == "zhixing">
                                <span title='${basic.orderSourceNameD!""}' style='position: absolute;'>${basic.orderSourceName!""}<img src='${ctx}/static/assets/images/zhixing.png'
                                         style='height:25px;margin-top: 10px;float: right;margin-right: -50px;'>
                            </span>
                            <#else>
                                <span title='${basic.orderSourceNameD!""}' style='position: absolute;'>${basic.orderSourceName!""}</span>
                            </#if>
                        <#else>
                            <span title='${basic.orderSourceNameD!""}' >${basic.orderSourceName!""}</span>
                        </#if>
                    </li>
                    <li><b>车票类型</b><span>${basic.ticketTypeStr!""}</span></li>
                    <li><b>预订用户</b><span> <a href="http://service.sh.ctriptravel.com/cii/crm/memberdetail.asp?UID=${basic.uid!""}"
                                             target="_blank" style="text-decoration:underline;">${basic.userLoginName!""}</a></span></li>
                    <li><b>联系人姓名</b><span style="position: absolute;">${basic.contactName!""}</span></li>
                    <#--<li class="lipassword"><b>帐号类型</b><span id="spaccountType"></span></li>-->
                </ul>
                <ul class="right">
                    <li>
                        <b>支付方式</b>
                        <dl>
                            <dt>${basic.paymentType!""}
                            <#if basic.isCtripWalletUser?has_content && basic.isCtripWalletUser == true>
                                (钱包实名认证)
                            </#if>
                            <#if basic.payRemark?has_content>
                                <span class="offline-label-warning">${basic.payRemark!""}</span>
                            </#if>
                            </dt>
                            <dd><span><a href="javascript:" onclick="ViewPamentRecord('${basic.orderId!""}')">客户支付退款信息</a></span>
                                <span><a href="javascript:" onclick="ViewPamentRecord_V1('${basic.orderId!""}')">12306退款详情</a></span></dd>
                        </dl>
                    </li>
                    <li>
                        <b>会员等级</b>
                        <dl>
                            <dt>${basic.memberLevel!""}</dt>
                            <#if basic.isemployeeFamily?has_content && basic.isemployeeFamily == true >
                                <dd>
                                    <span style="color: red">员工家属</span>
                                </dd>
                            </#if>
                            <#--<dd><span><a href="###">查看会员详情</a></span></dd>-->
                        </dl>
                    </li>
                    <li>
                        <b>手机号码</b>
                        <dl>
                            <dt><a href="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/SearchOrderNew.aspx?mobile=${basic.contactMob!""}&showType=0&source=offline"
                                   target="_blank">${basic.contactMobSafe!""}</a></dt>
                            <dd><span>
                            <#if basic.outboundNum?has_content>
                                <a href="javascript:void(0)"
                                   onclick="window.open('ccDesk:$$${basic.outboundNum!""}${basic.countryCode!""}${basic.contactMobShen!""}##','_self')">外呼</a>
                            <#else >
                                外呼
                            </#if>
                                </span>
                                <span>
                                    <a href="javascript:void(0)" id="updatemobile" visible="false" runat="server" onclick="UpdateMobile()"
                                                                                               style="padding-left: 10px; text-align: right; font-size: 13px; color: #19a0f0; text-decoration: underline">修改号码</a>
                                </span>
                                <span>
                                    <a href="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/SendMessagePage.aspx?orderid=${basic.orderId!""}&tel=${basic.contactMob!""}&partnerName=${basic.partherName!""}&countryCode=${basic.countryCode!""}"
                                                                target="_blank" style="margin-right: 5px; text-align: right; font-size: 13px; color: #19a0f0; text-decoration: underline">短信</a>
                                </span>
                            </dd>
                        </dl>
                    </li>
                    <li>
                        <b>邮箱地址</b>
                        <dl>
                            <#if basic.contactEmail?has_content>
                                <dt>${basic.contactEmail!""}</dt>
                                <dd><a href="mailto:${basic.contactEmail!""}" class="icon"></a></dd>
                            <#else>
                                <dt></dt>
                                <dd><i class="icon"></i></dd>
                            </#if>
                        </dl>
                    </li>
                    <li>
                        <b>购票账户</b>
                        <dl>
                            <dt><span id="spaccountType"></span></dt>
                            <dd id="ddpassword">
                                <span>
                                    <#if basic.userAccount?has_content>
                                    <a href="javascript:void(0)" id="passwordslink">查看详情</a>
                                    </#if>
                                </span>
                                <div id="trainDetail" style="display:none;">
                                    <p style="height: 34px;padding: 6px 0 6px 0;margin: 0px 0 0px;">
                                        12306登录名: <span style="margin-top: 4px; position: absolute;">${basic.userAccount!""}</span>
                                    </p>
                                    <p style="height: 34px;padding: 6px 0 6px 0;margin: 0px 0 0px;">
                                        12306登密码:
                                        <span id="dtpassword" style="margin-top: 4px; position: absolute;"></span>
                                    </p>
                                </div>
                            </dd>
                        </dl>
                    </li>
                    <#--<li class="lipassword">-->
                        <#--<b>12306登录密码</b>-->
                        <#--<dl>-->
                            <#--<dt id="dtpassword"></dt>-->
                        <#--</dl>-->
                    <#--</li>-->
                </ul>
            </div>
        </div>
    <#--</div>-->
<#--</div>-->

<!--查看日志-->
<div class="modal fade" id="rebookFeeDiv" tabindex="-1" role="dialog" aria-labelledby="OutBoundModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">订单操作日志
                </h4>
            </div>
            <div class="modal-body ">
                <div class="input-group" style="padding-bottom: 3px;">
                    <table border="1" width="800px;">
                        <tbody>
                        <tr>
                            <td style="width:20%;">操作类型</td>
                            <td style="width:40%;">说明</td>
                            <td style="width:10%;">操作人</td>
                            <td style="width:20%;">操作时间</td>
                        </tr>
                        <#if loginfoList?has_content>
                            <#list loginfoList as loginfo>
                            <tr>
                                <td>${loginfo.actionType!""}</td>
                                <td>${loginfo.comment!""}</td>
                                <td>${loginfo.operator!""}</td>
                                <td>${loginfo.operateTime!""}</td>
                            </tr>
                            </#list>
                        <#else>
                        <tr>
                            <td colspan="5">暂无数据</td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    #ddpassword .right{
        padding: 0 0 0 0;
        font-size: 12px;
    }
    #ddpassword .popover-title{
        background-color: #E0FFFF;
    }
</style>
<script>

    $(function(){
        $("#passwordslink").bind("click",function(){
            Search12306Password.search();
        })
        Search12306Password.onload();   //加载帐号
    });

    //修改手机号
    function UpdateMobile() {
        var iWidth = 320; //弹出窗口的宽度;
        var iHeight = 150; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/UpdateMobile.aspx?orderid=${basic.orderId!""}', '修改手机号', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    function qunaerLocation(){
        var orderid = '${basic.partnerOrderId}';
        var url = "http://kefu.train.ctripcorp.com/offline/order/train/detail?orderNumber=" + orderid.substr(0,orderid.length-7);
        window.location.href = url;
    }

    //查看支付记录
    function ViewPamentRecord(t0) {
        var partherName = '${basic.partherName!""}';
        if (partherName == "tieyou" || partherName == "zhixing") {
            var paymentType = '{basic.payType!""}<';
            var iWidth = 900; //弹出窗口的宽度;
            var iHeight = 300; //弹出窗口的高度;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
            window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/PayInfoList.aspx?orderid=' + t0 + '&orderType=' + partherName + '&paymentType=' + paymentType + '&orderFrom=' + partherName, '查看支付记录', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
        } else if (partherName == "Ctrip.Train") {
            var iWidth = 1200; //弹出窗口的宽度;
            var iHeight = 600; //弹出窗口的高度;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
            window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/TrainPayInfoList.aspx?OrderNo=' + t0 + '', '查看支付记录', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
        }
        else  {//
            var iWidth = 1200; //弹出窗口的宽度;
            var iHeight = 600; //弹出窗口的高度;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
            window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/PayInfoListQunar.aspx?OrderNo=' + t0 + '', '查看支付记录', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
        }
    }

    //查看12306支付记录
    function ViewPamentRecord_V1(t0) {
        var iWidth = 900; //弹出窗口的宽度;
        var iHeight = 300; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/finance-settlement/TicketPayInfo.aspx?OrderID=' + t0, '查看12306支付记录', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }

    //查询12306帐号密码
    var Search12306Password = {
        onload:function(){
            var username = '${basic.userAccount!""}';
            if(!username) {
            }else{
                Search12306Password.ajaxsubmit("load");
            }
        },
        search:function() {
            layer.confirm("该查询操作将会被记录，是否确定？", {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                Search12306Password.ajaxsubmit("search");
            });
        },
        bindTips:function(){   //绑定浮层
            //浮层
            $("#passwordslink").popover({
                trigger: 'click',
                placement: 'right', //placement of the popover. also can use top, bottom, left or right
                title: '<div style="text-align:center; font-size:14px;">账户详情</div>', //this is the top title bar of the popover. add some basic css
                html: 'true', //needed to show html of course
                content: function () {
                    return $("#trainDetail").html(); //this.after().find("table").html();
                },
                animation: false
            });
        },
        ajaxsubmit:function(type) {
            $.ajax({
                type: "POST",
                url: "${ctx}/order/train/basic/searchPassword",
                data: {
                    username: '${basic.userAccount!""}',
                    orderId: '${basic.orderId!""}'
                },
                dataType: "json",
                success: function (data) {
                    if (!data.data.msg){
                        if(type=="search") {
                            layer.alert("没有获取到对应帐号信息！");
                        }
                    } else {
                        var result = data.data.msg.split("|");
                        if(type=="load"){
                            $("#spaccountType").html(result[1]);
                        }else{
                            $("#dtpassword").html(result[0]);
                            $("#spaccountType").html(result[1]);
                            $("#passwordslink").unbind();
                            Search12306Password.bindTips();
                            $("#passwordslink").click();
                        }
                    }
                }
            });
        }
    }

    function openLog(){
        $("#rebookFeeDiv").modal();
    }
</script>
