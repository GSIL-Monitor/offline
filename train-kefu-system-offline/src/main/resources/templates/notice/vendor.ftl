<!DOCTYPE html>
<html>
<#include "../common/header.ftl">
<@header pageTtle="发送供应商通知" headerVisible=true pageWidth=1000  ></@header>
<link type="text/css" rel="stylesheet" href="${ctx}/static/timepicker/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/timepicker/js/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.min.css"/>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.tmpl.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/js/timepicker-addon.js"></script>
<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
<style type="text/css" rel="stylesheet">
    tr{
        padding: 0 50px;
    }
</style>
<body >
<div class="pubglobal_main panel panel-default" style="width: 1000px;margin-top: 10px;padding: 10px 5px">
    <table class="table-style2" style="border-collapse:separate; border-spacing:0px 15px;">
        <tr>
            <th style="text-align: right;width: 5%"><font color="red">*</font>订单号：</th><td  class="text-left" style="width: 20%">
            <input class="form-control" style="height: 25px;width: 150px" disabled id="inputOrderid" type="text" value="${vendor.orderId}" /></td>
        </tr>
        <tr>
            <th style="text-align: right"><font color="red">*</font>紧急情况：</th>
            <td class="text-left">
                <#if emergencyStates?has_content>
                    <#list emergencyStates as emergencyState>
                     <label class="radio-inline">
                         <input type="radio" name="emergencyState" value="${emergencyState.getState()}" <#if emergencyState.getState()==vendor.emergencyState>checked</#if> />${emergencyState.getName()}
                     </label>
                    </#list>
                <#else>
                    <span style="color: red">没有找到对应的紧急情况</span>
                </#if>
            </td>
        </tr>
        <tr>
            <th style="text-align: right">预约回电时间：</th>
            <td colspan="4" class="text-left form-inline">
                <div class='input-group date' id='timeAppointment'>
                    <input type='text'  class="form-control input-sm" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
                <a class="btn btn-link btn-sm" style="margin: 0" onclick="javascript:$('#timeAppointment .form-control').val('')">清除</a>
            </td>
        </tr>
        <tr>
            <th style="text-align: right"><font color="red">*</font>供应商：</th>
            <td colspan="5" class="text-left">
                <div id="divNoticeSource">
                    <#if vendorInfo?has_content>
                    <#list vendorInfo as vendorModel>
                     <label class="radio-inline">
                         <input type="radio"  <#if vendor.getVerdorCode()==vendorModel.getCode()>checked</#if> name="vendorInfo"
                                value="${vendorModel.getCode()}"  /> ${vendorModel.getName()}
                     </label>
                    </#list>
                    <#else>
                    <span style="color: red">没有找到来源</span>
                    </#if>
                </div>
            </td>
        </tr>
        <tr>
            <th style="text-align: right"><font color="red">*</font>问题分类：</th>
            <td colspan="5" style="max-width: 600px" class="text-left"  align="justify">
                <div id="divNoticeType" >
                    <#if vendor.noticeTypeList?has_content>
                    <#list vendor.noticeTypeList as noticetype>
                        <label class="radio-inline">
                            <input type="radio" <#if vendor.noticeType==noticetype.tid>checked</#if> name="noticeType" onchange="getSecondType(${noticetype.tid})" value="${noticetype.tid}" >${noticetype.fieldName}
                        </label>
                    </#list>
                    <#else >
                    <span style="color: red">没有找到该产线对应的通知分类</span>
                    </#if>
                </div>
            </td>
        </tr>
        <tr>
            <th style="text-align: right"><font color="red">*</font>二级分类：</th>
            <td colspan=3" style="max-width: 600px" class="text-left" align="justify">
                <div id="divSecondTypes" >
                       <#if vendor.getNoticeSecondTypeList()?has_content>
                           <#list vendor.getNoticeSecondTypeList() as noticeSecondType>
                                <label class="radio-inline">
                                    <input type="radio" <#if vendor.noticeSecondType==noticeSecondType.tid>checked</#if>  name="noticeSecondType" value="${noticeSecondType.tid}">${noticeSecondType.fieldName}
                                </label>
                           </#list>
                       </#if>
                </div>
                <div id="hintWait" class="text-left" style="display: none">
                    <i class="fa fa-cog fa-spin fa-lg fa-fw"></i><span class="sr-only">Loading...</span>
                </div>
                <span id="hintNoSecondType" style="display: none">没有找到对应二级分类</span>
            </td>
        </tr>
        <tr>
            <th style="text-align: right">图片上传：</th>
            <td colspan="5" class="text-left">
                <p class="fileinput-button">
                    <input type="file" name="fileName" id="fileUpload">
                    <input type="button" value="上传" id="btn_uploadimg" onclick="imageUpload()"/>
                </p>
            </td>
        </tr>
        <tr>
            <th style="text-align: right"><font color="red">*</font>问题说明：</th>
            <td colspan="5" class="text-left" >
                <textarea aria-multiline="true" rows="4" style="width: 100%" id="inputContent"></textarea>
            </td>
        </tr>
        <tr >
            <td colspan="6" class="form-inline">
                <span id="hintInfo" class="alert alert-warning" style="display:none;height: 10px;padding: 10px 20px;margin-right: 30px"></span>
                <a class="btn btn-primary btn-sm w80" id="btnSendNotice" onclick="sendNotice()">发送</a>
            </td>
        </tr>
    </table>

</div>
<div id="divHidden">

</div>
<script id="secondType" type="text/x-jquery-tmpl">
    <label class="radio-inline">
        <input type="radio" name="noticeSecondType" value="{{= tid}}">{{= fieldName}}
    </label> &nbsp;
</script>
</body>
</html>

<style>
    .fileinput-button {
        position: relative;
        display: inline-block;
    }

    .fileinput-button input[type=button]{
        position: absolute;
        right: 0px;
        top: 0px;
    }
</style>
<script type="text/javascript">
    $("#timeAppointment")
            .datetimepicker({
                format: "yyyy-mm-dd hh:ii:00",
                language: 'zh-CN',
                startView:1,
                minView:0,
                minuteStep:10,
                startDate:new Date(),
                autoclose:true
            });

    function imageUpload() {
        var fileObj = document.getElementById("fileUpload").files[0]; // js 获取文件对象
        if (typeof (fileObj) === "undefined" || fileObj.size <= 0) {
            layer.alert("请选择图片");
            return;
        }
        var formFile = new FormData();
        formFile.append("action", "fileUpload");
        formFile.append("file", fileObj); //加入文件对象

        $.ajax({
            url: "${ctx}/notice/fileUpload.json",
            data: formFile,
            type: "Post",
            dataType: "json",
            cache: false,//上传文件无需缓存
            processData: false,//用于对data参数进行序列化处理 这里必须false
            contentType: false, //必须
            success: function (data) {
                if(data.status===0){
                    $("div#divHidden").append("<input type='hidden' name='hiddenImage' style='display: none;' data-url='"+ data.data.result.url+"' data-name='"+data.data.result.file_name+"'/>")
                    layer.alert("图片上传成功");
                }
                else{
                    layer.alert(data.data.result.message)
                }
            }
        });
    }

    function getSecondType(firstLevelValue) {
        $('#divSecondTypes').html('');
        $("#hintWait").show();
        var url = "${ctx}/notice/getSecondType.json?firstLevel="+firstLevelValue;
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                if(data.status===0){
                    var enumList=data.data.noticeTypes;
                    $("#secondType").tmpl(enumList).appendTo('#divSecondTypes');
                    $("#hintNoSecondType").hide();
                }
                else{
                    $("#hintNoSecondType").removeClass().addClass("alert alert-wanring").html(data.message).fadeIn().delay(2000).fadeOut();
                }
            },
            error : function() {
                $("#hintNoSecondType").removeClass().addClass("alert alert-wanring").html(data.message).fadeIn().delay(2000).fadeOut();
            }
        });
        $("#hintWait").hide();
    }


    function sendNotice(){
        var validStr=validate($("#inputContent").val(),$("input[name=vendorInfo]:checked").val());
        if(validStr){
            layer.alert(validStr);
            return;
        }

        var imageList = [];
        var image = $("input[name=hiddenImage]");
        if (image) {
            if (image.length > 0) {
                image.each(function (index, element) {
                    var imageItem = {};
                    imageItem.imageUrl = element.getAttribute("data-url");
                    imageItem.imageName = element.getAttribute("data-name");
                    imageList.push(imageItem)
                });
            }
        }
        var notice={};
        notice.noticeId=${vendor.noticeId}
        notice.orderID= $("#inputOrderid").val();
        notice.emergeState=$("input[name=emergencyState]:checked").val();
        notice.appointedProcessTime=$("#timeAppointment .form-control").val();
        notice.noticeType=$("input[name=noticeType]:checked").val();
        notice.noticeSecondType=$("input[name=noticeSecondType]:checked").val();
        notice.contents=$("#inputContent").val();
        notice.verdorCode=$("input[name=vendorInfo]:checked").val();
        notice.vmNoticeImages=imageList;

        var url = "${ctx}/notice/vendor/send.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(notice),
            success : function(data){
                if(data.status==0){
                    $("#btnSendNotice").hide();
                    layer.alert(data.message)
                    setInterval(function(){
                        window.close();
                    },2000);
                    window.opener.location.href=window.opener.location.href;
                }
                else{
                    layer.alert(data.message)
                }
            },
            error : function() {
                layer.alert(data.message)
            }
        });
    }

    function validate(contents,vendor){
        if(!contents){
            return '请填写通知内容！'
        }
        if(!vendor){
            return '请选择供应商！'
        }
    }
</script>
