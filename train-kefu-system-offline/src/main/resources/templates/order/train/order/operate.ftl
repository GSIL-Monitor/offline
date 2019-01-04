<#--<!DOCTYPE html>-->

<#--<html lang="en">-->
<#--<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
<#--<link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>-->


    <div class="righter">
        <!--搜索订单号-->
        <div class="search">
            <input placeholder="请输入订单号" id="txtOrderId" onclick="" style="width: 69%">
            <span class="input-group-btn" style="float: right;margin-right: 75px;">
            <button class="btn btn-default" type="button" onclick="funSearchOrder()" style="margin-left: -1px">
            快速查询
            </button>
            </span>
        </div>

        <!--备注-->
        <div class="notes" id="divnotes" style="margin-top:7px;max-height: 305px;min-height: 100px;overflow-y: auto;">
            <dl class="Top">
                <dt>备注</dt>
                <dd><a href="javascript:void(0)" onclick="addRemarks()">添加备注</a></dd>
            </dl>

        <#if operate??>
            <#if operate.operates?? && operate.operates?size gt 0>
                    <#if operate.operates?size gt 3>
                        <#list operate.operates[0..2] as op>
                            <#if op?has_content>
                                <ul class="noteslist">
                                    <li>${op.node!""}</li>
                                    <li>备注人:${op.operator!""}</li>
                                    <li>备注时间:${op.operatorTime!""}</li>
                                </ul>
                            </#if>
                        </#list>
                    <#else>
                        <#list operate.operates as op>
                            <#if op?has_content>
                                <ul class="noteslist">
                                    <li>${op.node!""}</li>
                                    <li>备注人:${op.operator!""}</li>
                                    <li>备注时间:${op.operatorTime!""}</li>
                                </ul>
                            </#if>
                        </#list>
                    </#if>
            </#if>
        </#if>

            <#if operate??>
                <#if operate.operates?size gt 3>
                    <div class="SeeMore"><a href="javascript:void(0);" onclick="openRemarkModal();">查看更多></a></div>
                </#if>
            </#if>
        </div>

        <!--服务后处理-->
        <div class="notes">
            <dl class="Top">
                <dt>服务后处理</dt>
            </dl>
            <ul class="NoticeList">
                <li onclick="buttonImportantNotice()" class="disabled" >重要通知
                    <#if operate.buttonsFlag.importantNoticeCount gt 0>
                        <span>${operate.buttonsFlag.importantNoticeCount!""}</span>
                    </#if>
                </li>
                <li onclick="buttonExcePrice()">异常件</li>
                <li onclick="buttonEvidence()">凭证</li>
                <li onclick="button119()">119</li>
                <li onclick="ShowEInvoiceNew()">发票</li>

            <#if basic.twoRecommendType?has_content && basic.twoRecommendType gt 0>
                <#if basic.twoRecommendType == 1>
                    <li onclick="twoPush()">二推</li>
                <#else>
                    <li onclick='TwoRecommend(${operate.twoRecommendType!""})'>二推</li>
                </#if>
            <#else >
                <li class="offline_disable">二推</li>
            </#if>



                <li onclick="buttonAgentNotice()">代售点通知</li>
                <#if basic.isNeedCancelTwoRec?has_content && basic.isNeedCancelTwoRec == true>
                    <li onclick="twoRecommend.cancle()">申请取消二推</li>
                </#if>
            </ul>
            <ul class="contact">
                <#if operate.buttonsFlag.chatCount gt 0>
                    <li onclick="ViewOnlineChat('new')">查看在线咨询
                    <span>(${operate.buttonsFlag.chatCount!""})</span>
                    </li>
                <#else>
                    <li class="">查看在线咨询</li>
                </#if>
                <#if operate.buttonsFlag.ivrPhoneNumber gt 0>
                    <li onclick="ViewPhoneRecord()">查看电话记录
                        <span>(${operate.buttonsFlag.ivrPhoneNumber!""})</span>
                    </li>
                <#else>
                    <li class="" disabled="true">查看电话记录</li>
                </#if>
            </ul>
        </div>

        <!--其他功能-->
        <div class="notes">
            <dl class="Top">
                <dt>服务后处理</dt>
            </dl>
            <ul class="NoticeList">
                <li onclick="AdvancePage()">添加垫付</li>
                <li onclick="into12306()" >进入12306</li>
                <li onclick="ReleaseUserInfo()" >解绑账号</li>
                <#--按钮权限判断模板-->
                <#--<@permTag method="hasButtonPermission" permCode="notice:noticelist">-->
                    <#--<#if flag?? && flag gt 0>-->
                        <#--&lt;#&ndash;有 notice:noticelist 权限&ndash;&gt;-->

                    <#--<#else>-->
                        <#--&lt;#&ndash;没有 notice:noticelist&ndash;&gt;-->

                    <#--</#if>-->
                <#--</@permTag>-->

            </ul>
        </div>

        <!--通知类 TODO 暂不开发-->
        <#--<div class="notes">-->
            <#--<dl class="Top">-->
                <#--<dt>通知类</dt>-->
            <#--</dl>-->
            <#--<ul class="NotifyList">-->
                <#--<li>玩吧健身仓推出第三代产品<i></i></li>-->
                <#--<li>玩吧健身仓推出第三代产品</li>-->
                <#--<li>玩吧健身仓推出第三代产品</li>-->
            <#--</ul>-->
        <#--</div>-->

    </div>

<#--<!--备注弹框&ndash;&gt;-->
<div class="modal fade" id="postAgentModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title">添加备注</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <div class="item form-group">
                        <label class="control-label col-md-2 col-sm-2 col-xs-12" >备注:</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                            <textarea id="remarksDesc" class="form-control"  rows="9"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btn_save">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!--查看备注-->
<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="OutBoundModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">备注
                </h4>
            </div>
            <div class="modal-body ">
                <div class="input-group" style="padding-bottom: 3px;padding-left: 10px;">
                    <table class="tableleft" border="1" width="800px;">
                        <tbody>
                        <tr style="height: 32px;font-size: 13px;">
                            <td style="width:20%;">备注人</td>
                            <td style="width:40%;">备注内容</td>
                            <td style="width:20%;">备注时间</td>
                        </tr>
                        <#if operate.operates?has_content>
                            <#list operate.operates as op>
                            <tr style="height: 32px;font-size: 13px;">
                                <td>${op.operator!""}</td>
                                <td>${op.node!""}</td>
                                <td>${op.operatorTime!""}</td>
                            </tr>
                            </#list>
                        <#else>
                        <tr style="height: 32px;font-size: 13px;">
                            <td colspan="3">暂无数据</td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<#--<script type="text/javascript" src="${ctx}/static/vue/vue.js"></script>-->
<#--<script type="text/javascript" src="${ctx}/static/js/order/detail.js"></script>-->
<script type="text/javascript">
    //确定按钮
    $('#btn_save').on('click', function () {
        var request={};
        request.orderId=${basic.orderId!""};
        request.remarks=$('#remarksDesc').val();
        var url="${ctx}/order/train/addRemarks.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                $('#postAgentModal').modal('hide');
                location.reload();
            },
            error : function() {
                layer.msg('操作失败！');
            }
        });
    });

    function twoPush(){
        var url="http://kefu.train.ctripcorp.com/offline/two/personal?orderId="+${basic.orderId!""};
        window.open(url,"_blank");
    }

    function addRemarks(){
        $('#postAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    }

    function openRemarkModal(){
        $("#remarkModal").modal();
    }

    function buttonImportantNotice() {
        var name = "${basic.contactName!""}";
        var contactName = encodeURI(name);
        var partnerName = "${basic.partherName!""}";
        var orderType = "${basic.orderType!""}";
        var url="${ctx}/notice/importantNotice?orderId=${basic.orderId!""}&contactPhone=${basic.contactMob!""}&contactUser="
                +contactName+"&partnerName="+partnerName+"&productLine=134&orderType="+orderType;
        window.open(url,"_blank");
    }
    function buttonEvidence() {
        alert("待开发");
        // var url="";
        // window.open(url,"_blank");
    }
    //119
    function button119() {
        //如果发送过进入直接展示查看原内容页面；未发送过，进入是发送页面
        var url="${ctx}/notice/search119Count?orderId="+${basic.orderId!""};
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.status === 0){
                    //跳转列表页面
                    View119Notice();
                }else{
                    //跳转添加页
                    Add119Notice();
                }
            },
            error : function() {
                layer.msg('操作失败！');
            }
        });
    }
    //代售点
    function buttonAgentNotice() {
        //如果发送过进入直接展示查看原内容页面；未发送过，进入是发送页面
        var url="${ctx}/notice/searchAgentNoticeCount?orderId="+${basic.orderId!""};
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.status === 0){
                    //跳转列表页面
                    ShowMoreAgentNotice();
                }else{
                    //跳转添加页
                    ShowAddAgentNotice();
                }
            },
            error : function() {
                layer.msg('操作失败！');
            }
        });
    }
    //异常件
    function buttonExcePrice() {
        //如果发送过进入直接展示查看原内容页面；未发送过，进入是发送页面
        var url="${ctx}/train/order/searchExcePriceCount?orderId="+${basic.orderId!""};
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.status === 0){
                    //跳转列表页面
                    SearchExce();
                }else{
                    //跳转添加页
                    AddExce();
                }
            },
            error : function() {
                layer.msg('操作失败！');
            }
        });
    }

    function into12306(){
        var url="https://www.12306.cn/index/";
        window.open(url,"_blank");
    }

    //添加垫付
    function AdvancePage() {
        var iWidth = 700; //弹出窗口的宽度;
        var iHeight = 500; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/AdvancePage.aspx?orderid=${basic.orderId!""}',
                '添加垫付', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    function View119Notice() {
        var iWidth = 1030; //弹出窗口的宽度;
        var iHeight = 400; //弹出窗口的高度;

        var contactName = "${basic.contactName!""}";
        if(Object.keys(contactName).length === 0){
            contactName="";
        }
        <#--var url="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/Important119NoticeList.aspx?orderid=${basic.orderId!""}&ProductLine=1";-->
        var url="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/Important119NoticeList.aspx?orderid=${basic.orderId!""}&telenumber=${basic.contactMob!""}"+
                "&contactUser="+ contactName+"&addType=1&OrderFrom=${basic.partherName!""}&ProductLine=1";
        openWindow(url,iWidth,iHeight);
    }

    function Add119Notice() {
        var iWidth = 1030; //弹出窗口的宽度;
        var iHeight = 400; //弹出窗口的高度;
        var contactName = "${basic.contactName!""}";
        if(Object.keys(contactName).length === 0){
            contactName="";
        }
        var url="http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/Important119Notice.aspx?orderid=${basic.orderId!""}&telenumber=${basic.contactMob!""}"+
                "&contactUser="+ contactName+"&addType=1&OrderFrom=${basic.partherName!""}&ProductLine=1";
        openWindow(url,iWidth,iHeight);
    }
    //添加代售点通知 TODO 增加代售点名称
    function ShowAddAgentNotice() {
        var orderid = "${basic.orderId!""}";
        var partnername = "${basic.partherName!""}";
        var agentName = "${basic.agentName!""}";
        var iWidth = 600; //弹出窗口的宽度;
        var iHeight = 300; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/NoticePage/AddAgentNotice.aspx?orderid=' + orderid + '&partnername='+partnername+'&AgentName='+agentName , 'add', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=auto, resizable=no,location=no, status=no');
    }

    //查看代售点通知
    function ShowMoreAgentNotice() {
        var orderid = "${basic.orderId!""}";
        var partnername = "${basic.partherName!""}";
        var agentName = "${basic.agentName!""}";
        var iWidth = 600; //弹出窗口的宽度;
        var iHeight = 300; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;

        // window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/NoticePage/ShowMoreNotice.aspx?orderid=' + orderid + '&partnername='+partnername+'&display=none', 'add', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=auto, resizable=no,location=no, status=no');
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/NoticePage/ShowMoreNotice.aspx?orderid=' + orderid + '&partnername='+partnername+'&AgentName='+agentName +'&display=none', 'add', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=auto, resizable=no,location=no, status=no');
    }
    //添加异常件 TODO
    function AddExce(type) {
        //TODO
        //ticketType=1 多程票  billid = 乘客个数*（0,|）+乘客个数*（1,|）
        //billid=0,|0,|0,|1,|1,|1,| 三张去程票 三张返程票
        //ticketType=2 单程票  billid 为空
        //orderdate 预定时间
        var partnername = "${basic.partherName!""}";
        //订单类型单程往返
        var ticketType = "${basic.ticketType!""}";
        //_UserLoginName
        var partnerUid = "${basic.userAccount!""}";
        var billid = "${tickets.childBillId!""}";
        //预定日期
        var orderDate = "${basic.orderDate!""}";
        var isCWalletUser = "${basic.isCWalletUser!""}";
        var iWidth = 880; //弹出窗口的宽度;
        var iHeight = 710; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        var url = "http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/AddExcePrice.aspx?orderid=${basic.orderId!""}&orderdate="+ orderDate+"&ordertype="+ticketType+"&channel="+partnername
                +"&billid="+billid+"&type=" + type + "&exceid=0" + "&insertName=" + "&isCWalletUser="+ isCWalletUser + "&CtripUid="+partnerUid;
        window.open(url, 'AddExce', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    //查看异常件
    function SearchExce() {
        var orderid = "${basic.orderId!""}";
        var iWidth = 1090; //弹出窗口的宽度;
        var iHeight = 510; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        // var url = 'http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ExceListPage.aspx?orderid=' + orderid;
        var url = 'http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ExceListPage.aspx?orderid=' + orderid;
        window.open(url, 'search', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }

    function openWindow(url,iWidth,iHeight){
        if(!iWidth){
            iWidth=1000;
        }
        if(!iHeight){
            iHeight=700;
        }
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;

        window.open(url,'add','height='+iHeight +', top=' +iTop +',left='+iLeft +
                ',width=' +  iWidth +',toolbar=no,menubar=no,scrollbars=auto, resizable=no,location=no, status=no');
    }
    //新版电子发票 TODO  url 增加超级会员 参考.net $("#hideinvoice").val()
    function ShowEInvoiceNew() {
        var iWidth = 1450; //弹出窗口的宽度;
        var iHeight = 800; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('${ctx}/train/order/einvoice/detail?orderNumber=${basic.orderId!""}&mobile=${basic.contactMob!""}', 'add', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=auto, resizable=no,location=no, status=no');
    }

    //查看电话录音
    function ViewPhoneRecord(){
        var phoneNumber="${basic.contactMob!""}";
        var iWidth = 600; //弹出窗口的宽度;
        var iHeight = 300; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/OrderRecordList.aspx?orderid=${basic.orderId!""}&phoneNumber='+ phoneNumber +'&Operator=${empEntity.getEid()!""}', '查看电话录音', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
    //查看铁小二在线咨询
    function ViewOnlineChat(type) {
        var orderid = "${basic.orderId!""}";
        var iWidth = 720; //弹出窗口的宽度;
        var iHeight = 700; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        var url ="http://offline.kefu.ctripcorp.com/livechat-service/LiveChatKB/OrderOC.aspx?orderid="+orderid;
        window.open(url, "ViewOnlineChat",
                'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    //解绑账号
    function ReleaseUserInfo() {
        var partnername = "${basic.partherName!""}";
        var partnerUid = "${basic.userAccount!""}";
        var iWidth = 320; //弹出窗口的宽度;
        var iHeight = 150; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        window.open('http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ReleaseUserInfo.aspx?PartnerName='+partnername+'&Operator=${empEntity.getEid()!""}&PartnerUid='+partnerUid, '解绑手机号', 'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    var funSearchOrder = function () {
        var orderid = $("#txtOrderId").val();
        orderid = $.trim(orderid);
        if (orderid == "" || orderid == "undefined") {
            alert("请输入订单号!");
        }
        else {
            location.href = "http://kefu.train.ctripcorp.com/offline/order/train/detail?orderNumber=" + orderid;
        }
        return false;
    }

    function TwoRecommend(type) {
        var iWidth = 800; //弹出窗口的宽度;
        var iHeight = 600; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
    <#if tickets.trainRoutes?has_content>
        var originStation = '${tickets.trainRoutes[0].departStationName!""}';
    <#else >
        var originStation = '';
    </#if>
        var url;
        if (type=="3") {
            url = 'http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/ChangeSeatInfo.aspx';
        } else {

            url = 'http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/AddDeliverInfo.aspx';
        }
        window.open(url+'?OrderId=${basic.orderId!""}&Operator=${empEntity.getEid()!""}&DepartureStation=' + originStation, '开始推荐',
                'height=' + iHeight + ', top=' + iTop + ',left=' + iLeft + ',width=' + iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    }

    var twoRecommend ={
        cancle:function(){
            layer.confirm("确定申请取消？", {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                appendInfo.ajaxCancle();
            });
        },
        ajaxCancle: function () {
            var orderid = '${basic.orderId!""}';
            var url = "${ctx}/order/train/append/refundGrabBags";
            $.ajax({
                type: "POST",
                url: url,
                data: {orderNumber: orderid},
                dataType: "json",
                success: function (data) {
                    if (!data.data.msg) {
                        layer.alert("没有获取到信息！");
                    } else {
                        layer.alert(data.data.msg);
                    }
                },
                error:function(){
                    layer.alert("未知错误！");
                }
            });
        },
    }
</script>