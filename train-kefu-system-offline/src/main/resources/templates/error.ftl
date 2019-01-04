<!DOCTYPE html>
<html>
<#include "common/header.ftl">
<@header pageTtle="出错了" headerVisible=true></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/css/errorPage.css"/>
<body>
<div class="pubglobal_main" style="width: 1200px;text-align: center">
    <div style="text-align: center;padding-top: 10%;padding-bottom:10%;width: 750px;padding-left: 35%;">
        <div class="cartoon"></div>
        <div >
            <h1 class="h1" style="font-family: 'Microsoft YaHei UI'"><#if code??>${code}<#else>500</#if></h1>
            <p><font color="orange"  ><#if msg??>${msg}<#else>服务器内部错误</#if></font></p>
        </div>
    </div>
</div>
<#include "common/footer.ftl">
<@footer footerVisible=true></@footer>
</body>
</html>
