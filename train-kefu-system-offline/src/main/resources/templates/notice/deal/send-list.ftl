<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>火车票管理后台</title>

    <script type="text/javascript" src="${ctx}/static/jquery/jquery-3.2.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css"/>
    <script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>

    <link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_global.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/global/public_offline.css"/>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>

    <script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/css/order/css/style.css"/>


</head>

<body>
    <!--头部-->
    <div class="header">
        <dl>
            <dt><a href="###">订单管理</a></dt>
            <dd>
                <span><a href="###">返回首页</a></span>
                <span><a href="###">快速切换</a></span>
                <span><a href="###">口令</a></span>
                <span><a href="###">注销</a></span>
            </dd>
        </dl>
    </div>

    <div class="WrapDetails">
        <#--<div class="Activity"><a href="###">订单详情</a><span>&gt;</span>重要通知</div>-->
        <div class="InvoiceWrap">
            <ul class="TopNav">
                <@permTag method="hasButtonPermission" permissionCode="notice:addImportantNotice">
                    <#if hasPermissionFlag?? && hasPermissionFlag>
                        <li id="liSendNotice" class="on" onclick="sendNoticeBtn()">发送重要通知</li>
                    <#else>
                        <li>发送重要通知（无权限）</li>
                    </#if>
                </@permTag>
                <li id="liNoticeList" onclick="listNoticeBtn()">查看重要通知</li>
            </ul>

            <div id="divSendNotice" class="inpuList">
                <ul class="ddh">
                    <li><label class="label90"><small>*</small><span>订单号:</span></label><input class="w170" id="orderId" type="text" readonly value="${sendNoticePre.orderId}"></li>
                    <li><label class="label90"><small>*</small><span>联系人:</span></label><input class="w170" id="contactUser" type="text" value="${sendNoticePre.contactUser}"></li>
                    <li><label class="label90"><small>*</small><span>联系电话:</span></label><input class="w170" id="contactPhone" type="text" value="${sendNoticePre.contactPhone}"></li>
                </ul>
                <ul class="lfrt emer"  >
                    <li><label class="label90"><small>*</small><span>紧急情况:</span></label></li>
                    <li>
                        <#if sendNoticePre?has_content>
                            <#list sendNoticePre.emergency as emer>
                                <label class="checkbox">
                                    <#if emer_index==0>
                                        <em class="cb2" v="${emer.getState()}"></em>
                                    <#else >
                                        <em class="cb1" v="${emer.getState()}"></em>
                                    </#if>
                                    <span>${emer.getName()}</span>
                                </label>
                            </#list>
                        </#if>
                        <div class="yuyue">
                            <span>预约回电时间:</span>
                            <div class='input-group date' id='timeAppointment'>
                                <input type='text'  class="form-control input-sm" />
                                <span class="input-group-addon">
                                     <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                            <cite onclick="javascript:$('#timeAppointment .form-control').val('')">清除</cite>
                        </div>
                    </li>
                </ul>
                <ul class="lfrt eventType">
                    <li><label class="label90"><small>*</small><span>发送组别:</span></label></li>
                    <li>
                        <#if sendNoticePre?has_content>
                            <#list sendNoticePre.eventTypes as eventType>
                                <label class="checkbox" >
                                    <em class="cb1" v="${eventType.getValue()}"></em>
                                    <span>${eventType.getName()}</span>
                                </label>
                            </#list>
                            <#--<input id="productLine" type="hidden" value="${sendNoticePre.productLine}"/>-->
                            <input id="productLine" type="hidden" value="${productLine}"/>
                        </#if>
                    </li>
                </ul>
                <ul class="lfrt noticeSource">
                    <li><label class="label90"><small>*</small><span>来源:</span></label></li>
                    <li>
                        <#if sendNoticePre?has_content>
                            <#list sendNoticePre.noticeSources as noticeSource>
                                <label class="checkbox" >
                                    <#if noticeSource_index==0>
                                        <em class="cb2" v="${noticeSource.getTid()}"></em>
                                    <#else >
                                        <em class="cb1" v="${noticeSource.getTid()}"></em>
                                    </#if>
                                    <span>${noticeSource.getFieldName()}</span>
                                </label>
                            </#list>
                        </#if>
                    </li>
                </ul>
                <ul class="lfrt firstType">
                    <li><label class="label90"><small>*</small><span>一级分类:</span></label></li>
                    <li id="liFirstNotice">

                    </li>
                </ul>
                <ul class="lfrt secondType">
                    <li><label class="label90"><small></small><span>二级分类:</span></label></li>
                    <li id="liSecondNotice">

                    </li>
                </ul>
                <ul class="lfrt">
                    <li><label class="label90"><small>*</small><span>问题说明:</span></label></li>
                    <li>
                        <textarea id="contents"></textarea>
                        <div class="Btn">
                            <a  id="btnSendNotice" onclick="commitNotice()">发送</a>
                        </div>
                    </li>
                </ul>
            </div>

            <div id="divNoticeList" style="display: none;" class="TabBox">
                <table>
                    <tr>
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
                    <tbody>
                        <#if notices?has_content>
                             <#list  notices  as  notice>
                                <tr style="border-bottom: 1px;">
                                    <td>${notice_index+1}</td>
                                    <td>${notice.getOrderID()}</td>
                                    <td><#switch notice.getEnvenType()><#case 1>通知<#break><#case 2>投诉<#break ><#case 4>领班<#break ></#switch></td>
                                    <#--<td>${notice.getNoticeTypeStr()}</td>-->
                                    <td>1</td>
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
                                        <input type="button" class="btn btn-danger btn-xs" value="催单"  onclick="reminderNotice(${notice.getID()},'reminder')"/>
                                    </td>
                                    <td>
                                        <input type="button" class="btn btn-info btn-xs" value="备注"  onclick="reminderNotice(${notice.getID()},'remarks')"/>
                                    </td>
                                    <td>
                                        <input type="button" class="btn btn-success btn-xs" id="btnShowOpreta${notice.getID()}" value="查看处理" onclick="showOpretaInfo(${notice.getID()})"/>
                                    </td>
                                </tr>
                               <tr style="border-bottom:0 ;display: none" id="tr_${notice.getID()}" >
                                   <td colspan="12" style="align-content: center">

                                       <table class="table table-bordered table-hover" style="border-collapse: collapse; width: 100%; " >
                                           <tr class="success" style="text-align: -webkit-center; ">
                                               <th width="50px">序号</th>
                                               <th width="100px">处理类型</th>
                                               <th width="350px">处理内容 </th>
                                               <th width="100px">处理人 </th>
                                               <th width="100px">处理时间</th>
                                           </tr>
                                           <tbody >
                                             <#if notice.getOperates()?has_content>
                                                 <#list  notice.getOperates()  as  operate>
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
            </div >
        </div>
    </div>

    <!--添加备注弹框-->
    <div class="modal fade" id="addContents" tabindex="-1" role="dialog" aria-labelledby="addContent">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="addContent">备注</h4>
                </div>
                <div class="modal-body">
                    <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                        <div class="item form-group">
                            <label class="control-label col-md-2 col-sm-2 col-xs-12" >内容:</label>
                            <div class="col-md-9 col-sm-9 col-xs-12">
                                <textarea id="inputContentDesc" class="form-control"  rows="9"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <input type="hidden" id="hiddenNoticeId" value=""><input type="hidden" id="hiddenType" value="">
                    <button type="button" class="btn btn-primary" id="btn_save">确定</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
<script type="text/javascript">
    $(function () {
        //初始化页面
        if(${pageFlag} === 1){
            listNoticeBtn();
        }else if (${PermissionFlag} === 1){
            sendNoticeBtn();
        }else{
            $("#liNoticeList" ).addClass("on");
            $("#liSendNotice").removeClass("on");
            listNoticeBtn();
        }
        bindSearchEvent();
    });

    $("#timeAppointment").datetimepicker({
                format: "yyyy-mm-dd hh:ii:00",
                language: 'zh-CN',
                startView:1,
                minView:0,
                minuteStep:10,
                startDate:new Date(),
                autoclose:true
            });

    function sendNoticeBtn() {

        $("#liSendNotice" ).addClass("on");
        $("#divSendNotice").show();

        $("#liNoticeList").removeClass("on");
        $("#divNoticeList").hide();

    }

    function listNoticeBtn() {

        $("#liNoticeList" ).addClass("on");
        $("#divNoticeList").show();

        $("#liSendNotice").removeClass("on");
        $("#divSendNotice").hide();

    }

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

    //绑定复选框事件
    // function  bindSearchEvent() {
    //     $("#divSendNotice .lfrt").click(function (ev) {
    //         var ev = ev || window.event;
    //         var target = ev.target || ev.srcElement;
    //         if (target.nodeName.toLowerCase() == "em" ||target.nodeName.toLowerCase() == "span") {
    //             var parentNode=$(target).parent();
    //             if(parentNode&&$(parentNode).attr("class")=="checkbox") {
    //                 var lfrtel=$(target).closest(".lfrt");
    //                 if(lfrtel) {
    //                     $(lfrtel).find("em").attr("class", "cb1");
    //                     $(parentNode).find("em").attr("class", "cb2");
    //                 }
    //             }
    //         }
    //
    //     })
    // }

    function  bindSearchEvent() {
        $("#divSendNotice .lfrt").click(function (ev) {
            var ev = ev || window.event;
            var target = ev.target || ev.srcElement;
            if (target.nodeName.toLowerCase() == "em" ||target.nodeName.toLowerCase() == "span") {
                var parentNode=$(target).parent();
                if(parentNode&&$(parentNode).attr("class")=="checkbox") {
                    var lfrtel=$(target).closest(".lfrt");
                    if(lfrtel) {
                        $(lfrtel).find("em").attr("class", "cb1");
                        $(parentNode).find("em").attr("class", "cb2");
                    }
                    if ($(lfrtel).hasClass("eventType")){
                        //事件类型监控
                        var evenType= $(parentNode).find("em").attr("v");
                        searchFirstNoticeType(evenType);
                    } else if ($(lfrtel).hasClass("firstType")){
                        //一级分类监控
                        var firstId= $(parentNode).find("em").attr("v");
                        //渲染二级分类
                        searchSecondNoticeType(firstId);
                    }
                }
            }
        })
    }

    function searchFirstNoticeType(evenType) {
        var productLine=$('#productLine').val();
        var url="${ctx}/notice/firstNoticeTypes?evenType="+evenType+"&productLine="+productLine;
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                $('#liFirstNotice').empty();
                $('#liSecondNotice').empty();
                if(data.status === 0){
                    for(var i=0;i<data.data.response.length;i++){
                        var noticeType=data.data.response[i];
                        var str="<label class=\"checkbox\" ><em class=\"cb1\" v=\""+ noticeType.tid + "\" ></em><span>"+noticeType.fieldName+"</span></label>";
                        $('#liFirstNotice').append(str);
                    }
                }else{
                    layer.msg("未查到一级分类！");
                }
            },
            error : function() {

            }
        });
    }

    function searchSecondNoticeType(firstId) {
        var url="${ctx}/notice/secondNoticeTypes?firstId="+firstId;
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                $('#liSecondNotice').empty();
                if(data.status === 0){
                    for(var i=0;i<data.data.response.length;i++){
                        var noticeType=data.data.response[i];
                        var str="<label class=\"checkbox\" ><em class=\"cb1\" v=\""+noticeType.tid+"\" ></em><span>"+noticeType.fieldName+"</span></label>";
                        $('#liSecondNotice').append(str);
                    }
                }else{
                    layer.msg("未查到二级分类！");
                }
            },
            error : function() {

            }
        });
    }

    function commitNotice() {
        //隐藏按钮
        $("#btnSendNotice").hide();
        var orderId=$("#orderId").val();
        var contactUser=$("#contactUser").val();
        var contactPhone=$("#contactPhone").val();
        var productLine=$("#productLine").val();
        var contents=$("#contents").val();
        var emergency;
        var noticeSource;
        var eventType;
        var firstType;
        var secondType;
        var appointedProcessTime=$("#timeAppointment .form-control").val();
        //紧急
        if($("#divSendNotice .emer .cb2").length==1){
            emergency=$("#divSendNotice .emer .cb2").attr("v");
        }
        //来源
        if($("#divSendNotice .noticeSource .cb2").length==1){
            noticeSource=$("#divSendNotice .noticeSource .cb2").attr("v");
        }
        //组别
        if($("#divSendNotice .eventType .cb2").length==1){
            eventType=$("#divSendNotice .eventType .cb2").attr("v");
        }
        //一级分类
        if($("#divSendNotice .firstType .cb2").length==1){
            firstType=$("#divSendNotice .firstType .cb2").attr("v");
        }
        //二级分类
        if($("#divSendNotice .secondType .cb2").length==1){
            secondType=$("#divSendNotice .secondType .cb2").attr("v");
        }
        var validStr=validate(contactUser,firstType,contents,eventType,contactPhone,orderId,secondType);
        if(validStr){
            layer.msg(validStr);
            $("#btnSendNotice").show();
            return ;
        }
        var request={};
        request.orderId=orderId;
        request.contactUser=contactUser;
        request.contactPhone=contactPhone;
        request.noticeSource=noticeSource;
        request.productLine=productLine;
        request.contents=contents;
        request.emergency=emergency;
        request.eventType=eventType;
        request.firstType=firstType;
        request.secondType=secondType;
        if(appointedProcessTime){
            request.appointedProcessTime=appointedProcessTime;
        }
        request.channelSource=${channelSource};
        request.orderType=${orderType};
        var url="${ctx}/notice/commitImportantNotice";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                layer.msg(data.message);
            },
            error : function() {

            }
        });
    }

    function validate(contactName,firstType,contents,eventType,mobile,orderID,secondType){
        if(!contactName){
            return '请填写联系人！'
        }
        if(!eventType){
            return '请选择发送组别！'
        }
        if(!firstType){
            return '请选择一级问题分类！'
        }
        if(!secondType){
            return '请选择二级问题分类！'
        }
        if(!contents){
            return '请填写通知内容！'
        }
        if(!mobile||mobile.length<8||mobile.length>11){
            return '联系电话不符合要求！';
        }
        if(!orderID||orderID.length<3){
            return '请填写订单号！';
        }
        return '';
    }

    //唤出催处理弹窗
    function reminderNotice (noticeId,type) {
        $('#hiddenNoticeId').val(noticeId);
        $('#hiddenType').val(type);
        if (type=="reminder"){
            $('#addContent').html('催处理');
        } else {
            $('#addContent').html('备注');
        }
        $('#addContents').modal({
            keyboard: false,
            backdrop: 'static'
        })
    }

    //提交备注 催处理
    $('#btn_save').on('click', function () {
        var url='${ctx}/notice/operate';

        var noticeid=$('#hiddenNoticeId').val();
        var type=$('#hiddenType').val();
        var text=$('#inputContentDesc').val();

        $.ajax({
            url:url,
            type:"Post",
            data:{noticeid:noticeid,content:text,type:type},
            dataType : 'json',
            success : function(data){
                if (data.status === 1){
                    layer.msg(data.message);
                } else {
                    // $("#txtContent").val("");
                    // layer.msg('提交成功！');
                    // $('#addContents').modal('hide');
                    // location.reload();
                    window.opener=null;
                    window.open('','_self');
                    window.close();
                }
            },
            error : function() {
                layer.msg('提交失败！');
            }
        });
    });

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


</script>