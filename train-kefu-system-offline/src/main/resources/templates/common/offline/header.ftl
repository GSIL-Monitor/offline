<!DOCTYPE html>

<html lang="en">
<title>订单详情</title>
<link href="${ctx}/static/bootstrap/css/bootstrap-offline.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/layer/theme/default/layer.css"/>
<script type="text/javascript" src="${ctx}/static/js/clipboard.min.js"></script>
<#--<script type="text/javascript" src="${ctx}/static/js/order/detail.js"></script>-->
<script type="text/javascript" src="${ctx}/static/util/Watermark.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>

<body>
<style type="text/css">
    .spanRed span {
        color: #999 !important;
    }
</style>
<!--头部-->
<div class="header">
    <dl>
        <dt><a href="###">订单管理</a></dt>
        <dd>
            <span><a href="###">返回首页</a></span>
            <span><a href="###">快速切换</a></span>
            <span><a href="###">口令</a></span>
            <span><a href="###">注销</a></span>
            <#if empEntity??>
                <span><a href="###">${empEntity.getEid()} (${empEntity.getEmpName()}) </a></span>
            </#if>
        </dd>
    </dl>
</div>