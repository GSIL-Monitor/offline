

<#include "../../common/offline/header.ftl">
<style type="text/css">
    .offline_disable{
        cursor: not-allowed;
    }

    a{
        z-index: 20;
        position: relative;
    }
    li{
        z-index: 20;
        position: relative;
    }
    #rebookFeeDiv table tr td {
        font-family: "微软雅黑";
        padding: 10px;
    }
    .PassengerInfo {
        margin-bottom: 30px;
    }
    .tableleft td{
        padding-left: 10px;
    }
    .offline-label-warning{
        background-color: #ff9913;
        display: inline;
        padding: .2em .6em .3em;
        font-size: 75%;
        font-weight: 700;
        line-height: 1;
        color: #fff;
        text-align: center;
        white-space: nowrap;
        vertical-align: baseline;
        border-radius: .25em;
    }
</style>
<div class="WrapDetails">
    <!--左侧内容-->
    <div class="lefter">
        <!--状态-->
        <ul class="state">
            <li>当前状态：${basic.orderState!""}</li>
        <#if basic.orderStateInfoRed?has_content>
            <#list basic.orderStateInfoRed as orderInfoRed>
                <li class="red">${orderInfoRed!""}</li>
            </#list>
        </#if>
        <#if basic.orderStateInfo?has_content>
            <li class="gray">
                <#list basic.orderStateInfo as orderInfo>
                    <span>${orderInfo!""}</span>
                </#list>
            </li>
        </#if>
        </ul>
        <!--订单基本信息-->
        <#include "order/basicinfo.ftl">

        <!--产品详细信息-->
        <div class="ProductInfo">
            <#-- 车票信息 -->
            <#include "order/tickets.ftl">
            <!--车票信息-->
            <#include "order/append.ftl">
        </div>
    </div>
    <!--右侧内容-->
    <#include "order/operate.ftl" />
</div>
<#include "../../common/offline/footer.ftl">
