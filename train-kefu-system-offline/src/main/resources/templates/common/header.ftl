<#setting  classic_compatible=true > <#--解决字段为空问题的同时，“<include>”标签的路径参数将被作为绝对路径处理-->
<#macro header
pageTtle="火车票Offline"
pageWidth=1200
headerHeight=60
headerVisible=true
navVisible=true
>
 
<header>
    <head>
        <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>

        <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
        <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css"/>
        <script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>

        <link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_global.css"/>
        <link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>
        <title>${pageTtle}</title>
    </head>
    <#if headerVisible>
        <div id="pubglobal_header" style="width: ${pageWidth}px;line-height: ${headerHeight}px;">
            <strong>${pageTtle}</strong>
            <#if navVisible>
                <span class="topbar" >
                <#if empEntity??>
                    <span>Welcome, ${empEntity.getEid()} ${empEntity.getEmpName()}(${empEntity.getDeptName()})</span>
                </#if>
                    <a href="${baseUrl}/offlinehomepage/index.aspx">返回首页</a>
                    <a href="javascript:OnShortcut();">快速切换</a>
                    <a id="btnLogout" href="${ctx}/user/logout">注销</a>
                </span>
            </#if>
        </div>
    </#if>
    <script type="text/javascript">
        function OnShortcut() {
            var baseUrl= '${baseUrl}';
            commonPrompt1(switchModel,validateModuleCode,baseUrl,"请输入模块号");
        }
        function validateModuleCode(moduleCode) {
            if (moduleCode==""|| moduleCode==null)
                return "模块号不能为空";
            if (moduleCode.search(/[^0123456789]/) >= 0) {
                return "模块代号为一串数字";
            }
            return null;
        }
        function switchModel(modelCode,baseUrl) {
            window.location = baseUrl + "/modulejump/tNetv.aspx?module=" + modelCode;
        }
    </script>
    <#include "common/component/common-prompt.ftl">
</header>

</#macro>
