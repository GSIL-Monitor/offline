<style>
.appendbtn-blue{
    float: right;
    margin-right: 20px;
    color: #fff;
    background: #19A0F0;
    border: 1px solid #19A0F0;
    width: 121px;
    height: 26px;
    box-sizing: border-box;
    font-size: 13px;
    line-height: 24px;
    text-align: center;
    border-radius: 2px;
    margin-top: -4px;
    text-decoration:none;
}
a:hover
{

}
</style>

<div class="cpBtm">
<#if append?has_content>
    <!--附属产品-->
    <#if append.appendPrice??&&append.appendPrice gt 0>
        <dl class="Sp" style="margin-top: 20px;">
            <dt>附属产品（${append.appendPrice!""}元）</dt>
        </dl>
    </#if>
    <#if append.appendPackagelist?has_content>
        <div class="Stit">附属产品
        <#if append.isCanRefAllGrab?has_content && append.isCanRefAllGrab== true>
            <a class="appendbtn-blue" href="javascript:appendInfo.refundBagVol()">批量退加速包</a>
        </#if>
        <#if append.isCanRefundGrab?has_content && append.isCanRefundGrab== true>
            <a class="appendbtn-blue" href="javascript:appendInfo.refundGrabProductId()">批量退默认加速包</a>
        </#if>
        </div>
        <div class="TabBox">
            <table>
                <tr>
                    <th width="80px">产品名称</th>
                    <th width="82px">产品单价</th>
                    <th width="73px">产品数量</th>
                    <th width="75px">总金额</th>
                    <th width="66px">状态</th>
                    <th width="96px">产品ID</th>
                    <th width="80px">操作</th>
                    <th width="200px">是否默认</th>
                </tr>
                <#list append.appendPackagelist as appendDetail>
                    <tr>
                        <td>
                            <a href="javascript:appendInfo.ShowAppendProductInfo('${appendDetail.productId!""}','infoUrl','${appendDetail.productType!""}')"
                               id='${appendDetail.productId!""}'>${appendDetail.productTitle!""}</a></td>
                        <td>${appendDetail.productPrice!""}</td>
                        <td>${appendDetail.productNum!""}</td>
                        <td>${appendDetail.totalPrice!""}</td>
                        <td>${appendDetail.appendState!""}</td>
                        <td>${appendDetail.productId!""}</td>
                        <td>${appendDetail.operate!""}</td>
                        <td>${appendDetail.isDefault!""}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </#if>

<#if append.appendPackageDtlist?has_content>
    <div style="margin-top:13px;" class="SeeBtn">
        <span id="spanDetail" style="z-index: 30;">查看详情</span>
    </div>

    <div id="divAppendInfo1" class="Stit" style="margin-top: -18px;display: none;">详情
    </div>
    <div id="divAppendInfo2" class="TabBox" style="display: none;">
        <table>
            <tr>
                <th width="80px">ID</th>
                <th width="82px">产品名称</th>
                <th width="73px">产品金额</th>
                <th width="66px">产品数量</th>
                <th width="96px">状态</th>
                <th width="100px">是否默认</th>
                <th width="80px">备注</th>
                <th width="80px">操作</th>
            </tr>
            <#list append.appendPackageDtlist as appendDetail>
                <tr>
                    <td>${appendDetail.appendID!""}</td>
                    <td>
                        <a href="javascript:appendInfo.ShowAppendProductInfo('${appendDetail.productId!""}','infoUrl','${appendDetail.productType!""}')"
                           id='${appendDetail.productId!""}'>${appendDetail.productTitle!""}</a></td>
                    <td>${appendDetail.productPrice!""}</td>
                    <td>${appendDetail.productNum!""}</td>
                    <td>${appendDetail.appendState!""}</td>
                    <td>${appendDetail.isDefault!""}</td>
                    <td>${appendDetail.respStr!""}</td>
                    <td>${appendDetail.operate!""}</td>
                </tr>
            </#list>
        </table>
    </div>
</#if>

    <#if append.appendCouponlist?has_content>
        <div class="default">
            <div class="Stit">优惠券信息</div>
            <div class="TabBox">
                <table>
                    <tr>
                        <th>产品名称</th>
                        <th>优惠券码</th>
                        <th>优惠券金额</th>
                        <th>优惠券使用金额</th>
                        <th>优惠券出成功时间</th>
                        <th>优惠券状态</th>
                        <th>抵扣类型</th>
                        <th>操作</th>
                    </tr>
                    <#list append.appendCouponlist as appendCoupon>
                        <tr>
                            <td><a name="appendPackage" style="cursor:pointer;">${appendCoupon.couponTypeName!""}</a></td>
                            <td>${appendCoupon.couponCode!""}</td>
                            <td>${appendCoupon.couponPrice!""}</td>
                            <td>${appendCoupon.couponRealPrice!""}</td>
                            <td>${appendCoupon.couponUsageTime!""}</td>
                            <td>${appendCoupon.couponStateName!""}</td>
                            <td>${appendCoupon.couponTicketFeedesc!""}</td>
                            <td>
                                <#if appendCoupon.isCanRefund = true>
                                    <a href="javascript:void(0);"
                                       onclick="appendInfo.refundCoupon('${basic.partherName!""}','${basic.orderId!""}','${appendCoupon.couponCode!""}','${appendCoupon.couponId!""}')">退优惠券</a>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                </table>
            </div>

            <#if (append.packageName?has_content && append.packageInfo?has_content)>
                <div id="packageName" class="Stit" style="display: none;">套餐详情(${append.packageName})</div>
                <div id="packageInfo" class="TabBox" style="display: none;">
                    <table>
                        <tr>
                            <td style="padding: 12px;text-align: left;font-size: 12px;">${append.packageInfo!""}
                            </td>
                        </tr>
                    </table>
                </div>
            </#if>
        </div>
    </#if>

    <#if append.appendInsurancelist?has_content>
        <div class="default">
            <div class="Stit">乘客附加信息</div>
            <div class="TabBox">
                <table>
                    <tr>
                        <th>行程类型</th>
                        <th>乘客名</th>
                        <th>证件类型</th>
                        <th>证件号码</th>
                        <th>出生日期</th>
                        <th>保险名称</th>
                        <th>保单号</th>
                        <th>保险份数</th>
                        <th>保险状态</th>
                        <th>操作</th>
                    </tr>
                    <#list append.appendInsurancelist as insuranceInfo>
                        <tr>
                            <td>单程</td>
                            <td>${insuranceInfo.passengerName!""}</td>
                            <td>${insuranceInfo.passportType!""}</td>
                            <td>${insuranceInfo.passportNumber!""}</td>
                            <td>${insuranceInfo.birthDay!""}</td>
                            <td><a href="javascript:appendInfo.ShowAppendProductInfo('${insuranceInfo.productId!""}','infoUrl','${insuranceInfo.productType!""}')"
                                   id='${insuranceInfo.productId!""}'>${insuranceInfo.productTitle!""}</a></td>
                            <td>${insuranceInfo.insuranceId!""}</td>
                            <td>${insuranceInfo.productNum!""}</td>
                            <td>${insuranceInfo.productState!""}</td>
                            <td><a href="javascript:void(0);" onclick="appendInfo.queryInsuceanceState('${basic.orderId!""}','${insuranceInfo.insuranceId!""}')">查看理赔状态</a></td>
                        </tr>
                    </#list>
                </table>
            </div>
        </div>
    </#if>

</#if>


</div>
<script>

    $(function(){
        $("#spanDetail").bind("click",function(){
            appendInfo.flipAppendInfoShow();
        });
        $("a[name='appendPackage']").bind("click",function () {
            appendInfo.flipPackageInfo();
        });
    });


    var appendInfo = {
        iWidth: 1050,
        iHeight: 500, //弹出窗口的高度;
        iTop: (window.screen.availHeight - 30 - 500) / 2, //获得窗口的垂直位置;
        iLeft: (window.screen.availWidth - 10 - 1050) / 2, //获得窗口的水平位置;
        ShowAppendProductInfo: function (aid, action, productSubtype) {
            if (productSubtype == "Disney") {
                var url = "http://admin.train.ctripcorp.com/TrainOrderProcess/OrderPageNew/DisneyRules.aspx";
                window.open(url, '附加产品信息', 'height=' + appendInfo.iHeight + ', top=' + appendInfo.iTop + ',left=' + appendInfo.iLeft + ',width=' + appendInfo.iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
            }
            else {
                if (action == "infoUrl") {
                    appendInfo.ajaxSearch(action, aid);
                }
            }
        },
        ajaxSearch: function (action, aid) {
            $.ajax({
                type: "POST",
                url: "${ctx}/order/train/append/appendProductInfo",
                data: {action: action, productID: aid},
                dataType: "json",
                success: function (data) {
                    if (!data.data.msg) {
                        layer.alert("没有获取到信息！");
                    } else {
                        window.open(data.data.msg, '附加产品信息', 'height=' + appendInfo.iHeight + ', top=' + appendInfo.iTop + ',left=' + appendInfo.iLeft + ',width=' + appendInfo.iWidth + ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
                    }
                }
            });
        },
        refundCoupon: function (partnerName, orderId, couponCode, couponId) {   //退优惠券套餐
            var confirmMsg = "确认退" + couponCode + "优惠券吗？";
            var diffPrice = ${basic.diffPrice!0};
            if (diffPrice > 0) {
                confirmMsg = "车票价钱不足，已使用附加产品抵用" + diffPrice + "元, " + confirmMsg;
            }
            layer.confirm(confirmMsg, {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                appendInfo.ajaxRefundCoupon(orderId, partnerName, couponId);
            });
        },
        ajaxRefundCoupon: function (orderId, partnerName, couponId) {
            $.ajax({
                type: "POST",
                url: "${ctx}/order/train/append/refundCoupon",
                data: {orderNumber: orderId, partnerName: partnerName, couponId: couponId},
                dataType: "json",
                success: function (data) {
                    if (!data.data.msg) {
                        layer.alert("没有获取到信息！");
                    } else {
                        layer.alert(data.data.msg);
                        //window.location.reload();
                    }
                },
                error: function (data) {
                    layer.alert("未知错误!");
                }
            });
        },
        flipPackageInfo: function () {
            $("#packageInfo").fadeIn();
            $("#packageName").fadeIn();
            var mao2 = $("#packageName"); //获得锚点
            var mao = $("#packageInfo"); //获得锚点
            //判断对象是否存在
            if (mao.length > 0) {
                //var pos = mao.offset().top;
                var poshigh = mao.height() +  mao2.height() + 40;
                $("html,body").animate({ scrollTop:$(window).scrollTop()+ poshigh }, 1000);   //获取滑动条位置，在下滑两个div的高度
            }
            $("a[name='appendPackage']").unbind();
            $("a[name='appendPackage']").bind("click",function () {
                appendInfo.flipPackageInfoHide();
            });
        },
        flipPackageInfoHide:function () {

            $("#packageInfo").fadeOut();
            $("#packageName").fadeOut();

            $("a[name='appendPackage']").unbind();
            $("a[name='appendPackage']").bind("click",function () {
                appendInfo.flipPackageInfo();
            });
        },
        refundGrabProductId:function () {
            layer.confirm("确认退默认加速包？", {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                appendInfo.ajaxrefundGrabProductId();
            });
        },
        ajaxrefundGrabProductId: function () {
            var orderid = '${basic.orderId!""}';
            var url = "${ctx}/order/train/append/refundGrabProductId";
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                data: {orderNumber: orderid},
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
        refundBagVol : function () {
            layer.confirm("确认退加速包？", {
                btn: ["确认", "取消"] //按钮
            }, function (index) {
                layer.close(index);
                appendInfo.ajaxrefundBagVol();
            });
        },
        ajaxrefundBagVol: function () {
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
        flipAppendInfoShow: function () {   //查看附属产品详情
            $("#spanDetail").html("隐藏详情");
            $("#divAppendInfo1").fadeIn();
            $("#divAppendInfo2").fadeIn();
            var mao2 = $("#divAppendInfo1"); //获得锚点
            var mao = $("#divAppendInfo2"); //获得锚点
            //判断对象是否存在
            if (mao.length > 0) {
                //var pos = mao.offset().top;
                var poshigh = mao.height() +  mao2.height() + 40;
                $("html,body").animate({ scrollTop:$(window).scrollTop()+ poshigh }, 1000);   //获取滑动条位置，在下滑两个div的高度
            }
            $("#spanDetail").unbind();
            $("#spanDetail").bind("click",function(){
                appendInfo.flipAppendInfoHide();
            });
        },
        flipAppendInfoHide: function () {   //查看附属产品详情
            $("#spanDetail").html("查看详情");
            $("#divAppendInfo1").fadeOut();
            $("#divAppendInfo2").fadeOut();
            $("#spanDetail").unbind();
            $("#spanDetail").bind("click",function(){
                appendInfo.flipAppendInfoShow();
            });
        },
        queryInsuceanceState: function (orderId, insuranceId) { //查看抢票险理赔状态
            var url = "${ctx}/order/train/append/queryinsuceance";
            $.ajax({
                type: "POST",
                url: url,
                data: {insuranceNos: insuranceId, orderNumber: orderId},
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
        }
    }
</script>
