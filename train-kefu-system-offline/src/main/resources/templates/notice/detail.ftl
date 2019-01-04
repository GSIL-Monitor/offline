
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
<div class="pubglobal_main" style="width: 1200px;">
    <div >
        <table class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;">
            <tr class="success" style="text-align: -webkit-center; ">
                <th width="50px">序号</th>
                <th width="40px">订单号</th>
                <th width="50px">接收人类别</th>
                <th width="50px">通知类别</th>
                <th width="50px">紧急状况</th>
                <th width="250px">通知内容</th>
                <th width="50px">发送人</th>
                <th width="80px">发送时间</th>
                <th width="60px">当前状态</th>
                <th width="50px">催次数</th>
                <th width="60px">催</th>
                <th width="60px">备注</th>
                <th width="80px">查看处理</th>
            </tr>
            <tbody id="noticeTBody">
            <#if notices?has_content>
             <#list  notices  as  notice>
                <tr style="border-bottom: 1px;">
                <td>${notice_index+1}</td>
                <td>${notice.getOrderID()}</td>
                <td><#switch notice.getEnvenType()><#case 1>通知<#break><#case 2>投诉<#break ><#case 4>领班<#break ></#switch></td>
                <td>${notice.getNoticeTypoStr()}</td>
                <td><#if notice.getEmergeState()==1>紧急 <#else >一般</#if></td>
                <td>${notice.getContents()}</td>
                <td>
                    <#if notice.getEnterUser()??>
                        ${notice.getEnterUser()}
                    <#else>
                        空
                    </#if>
                </td>
                <td>${notice.getSendTime()}</td>
                <td><#switch notice.getNoticeState()>
                    <#case 80>待分配<#break>
                    <#case 81>处理中<#break >
                    <#case 82>暂缓<#break >
                    <#case 83>已解决<#break >
                    <#case 84>员工待处理<#break >
                    <#case 94>已转投诉<#break >
                    <#case 100>已转领班<#break >
                    <#case 102>已交班<#break >
                    </#switch>
                </td>
                <td>${notice.getOpCount()}</td>
                <td>
                    <input type="button" value="催"  onclick="reminderNotice(${notice.getID()},'reminder')"/>
                </td>
                    <td>
                        <input type="button" value="备注"  onclick="reminderNotice(${notice.getID()},'remarks')"/>
                    </td>
                <td>
                    <input type="button" id="btnShowOpreta${notice.getID()}" value="查看处理" onclick="showOpretaInfo(${notice.getID()})"/>
                </td>
                </tr>
               <tr style="border-bottom:0 ;display: none" id="tr_${notice.getID()}" >
                   <td colspan="13" style="align-content: center">
                   <table class="table table-bordered table-hover" style="border-collapse: collapse; width: 100%; " >
                       <tr class="success" style="text-align: -webkit-center; ">
                           <th width="50px">序号</th>
                           <th width="100px">处理类型</th>
                           <th width="350px">处理内容 </th>
                           <th width="100px">处理人 </th>
                           <th width="100px">处理时间</th>
                       </tr>
                       <tbody >
                     <#if notice.getOperateInfos()?has_content>
                         <#list  notice.getOperateInfos()  as  operate>
                            <tr style="border-bottom: 0px;">
                                <td>${operate_index+1}</td>
                                <td><#switch operate.getOperateType()>
                                        <#case 80>待分配<#break>
                                        <#case 81>备注<#break >
                                        <#case 82>暂缓<#break >
                                        <#case 83>解决<#break >
                                        <#case 94>转投诉<#break >
                                        <#case 100>转领班<#break >
                                        <#case 102>已交班<#break >
                                        <#case 103>外呼<#break >
                                        <#case 104>催处理<#break >
                                        <#case 105>回复供应商<#break >
                                        <#case 106>转供应商<#break >
                                </#switch>
                                </td>
                                <td>${operate.getOperateComment()}</td>
                                <td>${operate.getOperateUser()}</td>
                                <td>${operate.getOperateTime()}</td>
                            </tr>
                         </#list>
                     <#else >
                       <tr><td colspan="15" style="height: 70px;text-align: center">没有找到数据</td></tr>
                     </#if>
                       </tbody>
                   </table>
                   </td>
               </tr>
             </#list>
            <#else >
                <tr><td colspan="15" style="height: 70px;text-align: center">没有找到数据</td></tr>
            </#if>
            </tbody>
        </table>
    </div>
</div>

<div id="divReminderNotice"   style="display: none">
        <table border="0" cellspacing="1" cellpadding="1" id="">
            <tr style="height: 120px">
                <td align="center" style="width: 60px;">
                    处理内容
                </td>
                <td align="center">
                    <textarea  ID="txtContent" style="margin: 0px; width: 300px; height: 150px;"></textarea>
                </td>
            </tr>
            <tr id="trReminderNotice">
            </tr>
        </table>
</div>
</body>
</html>
<script type="text/javascript">
    var reminderLayer=null;
    function showOpretaInfo(noticeid) {
        if ($("#btnShowOpreta"+noticeid).val()=="查看处理"){
            $("#tr_"+noticeid).show(500);
            $("#btnShowOpreta"+noticeid).val("关闭处理")
        }
        else {
            $("#tr_" + noticeid).hide(500);
            $("#btnShowOpreta" + noticeid).val("查看处理")
        }
    }
    
    function reminderNotice(noticeid,type) {
        $("#divReminderNotice table tr#trReminderNotice").html('');
        $("#divReminderNotice table tr#trReminderNotice")
                .append(' <td align="center" colspan="2"><input onclick="SubmitNoteFun(' + noticeid+'\,\''+type+'\')" id="submit" type="button" value="提交" ' +
                        ' style="font-family: Arial;' +
                        ' color: #000000; width: 100px; height: 25px; font-size: 13px" />  </td> ');
        var  title;
        if (type=="reminder"){
            title="催处理";
        }
        else {
            title="备注";
        }
        reminderLayer=layer.open({
            type: 1,
            title: title,
            content: $("#divReminderNotice"),
            btn: [],
            btn2: function (index, layero) {
                $("#divReminderNotice").hide();
                $("#txtContent").val("")
                layer.close(index);
                window.location.reload();
                return false;
            },
            cancel: function (index, layero) {
                $("#divReminderNotice").hide();
                layer.close(index);
                window.location.reload();
                return window.str_empty;
            },
            end: function () {

            },
            success: function (layero, index) {
                $("#divReminderNotice").show();
            },
            area: ['400px', '300px']
        });
    }

    function SubmitNoteFun(noticeid,type) {
        var text= $("#txtContent").val();
        if (!text||text===""){
            layer.msg('备注信息不能为空！');
            return;
        }
        layer.closeAll();
        var url='${ctx}/notice/operate';
        $.ajax({
            url:url,
            type:"Post",
            data:{noticeid:noticeid,content:text,type:type},
            dataType : 'json',
            success : function(data){
                if (data.status==1){
                    layer.msg(data.message);
                }
                else {
                    $("#txtContent").val("")
                    layer.msg('提交成功！');
                }
            },
            error : function() {
                layer.msg('提交失败！');
            }
        });
    }
</script>

<style type="text/css">
    div#divReminderNotice table
    {
        width: 400px;
        background-color: #91AFDD;
    }
    div#divReminderNotice table th
    {
        background: none no-repeat scroll 0 0 #CAE8EA;
        color: #4F6B72;
        font: bold 11px "Trebuchet MS" ,Verdana,Arial,Helvetica,sans-serif;
        letter-spacing: 2px;
        text-align: center;
        text-transform: uppercase;
    }
    div#divReminderNotice table td
    {
        background: none repeat scroll 0 0 #FFFFFF;
        color: #4F6B72;
        font-size: 13px;
        height: 28px;
        padding: 6px 6px 6px 12px;
    }
</style>