<!DOCTYPE html>
<html>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css"/>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_global.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>
<script type="text/javascript" src="${ctx}/static/js/train-enum-helper.js?t=20180512"></script>
<script type="text/javascript" src="${ctx}/static/layer/theme/default/layer.css"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<body>
<div>
    <table border="0" cellpadding="0px" cellspacing="0px"
           style=" width:1200px; height:800px; margin:0 auto;border:1px solid #71AFDD">
        <tr>
            <td style=" width:145px;border-right:1px solid #71AFDD; vertical-align:top; text-align:left; padding-left:5px; padding-top:10px;">
                <style>
                    p {
                        margin: 10px 0
                    }
                </style>
                <p><h4>机票异常单处理</h4></p>
                <p><a href="${TieyouFlightPrd}" target="frName">订单查询</a></p>
                <p><a href="${ctx}/airOrder/airOrderList" target="frName">异常单处理</a></p>
                <p><a href="${NoticePagePrd}" target="frName">航变通知</a></p>
                <p><a href="${NoticeDealPrd}" target="_blank">待处理通知</a></p>
                <p><a href="${NoticeListPrd}" target="_blank">领班查看通知</a></p>
            </td>
            <td style=" vertical-align:top;width:1050px;height:800px">
                <iframe src="" name="frName" style=" width:100%; height:100%; border:0px;" id="iFrame1"></iframe>
            </td>
        </tr>
    </table>
</div>
</body>
</html>

<script type="text/javascript">

</script>
