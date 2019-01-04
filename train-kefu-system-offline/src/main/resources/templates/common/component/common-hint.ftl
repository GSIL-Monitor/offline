<#--通用悬浮提示 需要Jquery支持-->
<#--Created By ying_zhou-->
<div id="commonHintDiv" style="z-index: 9999; position: fixed !important; left: 50%; top: 50%">
    <div id="commonHintContent" style="padding-left: 50px;padding-right: 50px">
    </div>
</div>
<script type="text/javascript">
    function showCommonHint(hintHtml,time,classStr){
        $("#commonHintContent").attr("class","").attr("class",classStr).html(hintHtml);
        $("#commonHintDiv").fadeIn(500).delay(time).fadeOut(500);
    }
</script>