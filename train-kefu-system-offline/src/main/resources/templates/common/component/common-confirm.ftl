<#--通用确认弹窗,确认后回调响应方法完成后续操作，需要jquery支持-->
<#--Created by ying_zhou-->
<style type="text/cs"  rel="stylesheet">

</style>
<div id="commonConfirmModalDiv" style="z-index: 9999; position: fixed !important; left: 45%; top: 50%;display: none">
    <div id="commonConfirmContent"  style="width: 400px;background-color: #d9edf7;border-radius: 4px;border: 1px solid #bce8f1;padding: 20px 40px">
        <div>
            <span id="confirmMessage" class="pull-left h5 text-left"></span>
        </div>
        <div class="text-right">
            <a class="btn btn-danger btn-sm w80" onclick="doCallback()">确认</a>
            <a class="btn btn-info btn-sm w80" onclick="javascript:$('#commonConfirmModalDiv').hide();">取消</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    var globalCallBack;
    var globalParam1;
    var globalParam2;
//    两个参数
    function commonConfirm2(param1,param2,callback,message){
        if(typeof callback !="function"){
            return;
        }
        if(!message){
            message="确认？";
        }
        //注册callback到确认按钮上
        globalCallBack=callback;
        globalParam1=param1;
        globalParam2=param2;
        $("#confirmMessage").html(message);
        $("#commonConfirmModalDiv").fadeIn();
    }
//    一个参数
    function commonConfirm1(param,callback,message){
        commonConfirm2(param,null,callback,message);
    }
    function doCallback() {
        $("#commonConfirmModalDiv").hide();
        if(globalCallBack!=null){
            globalCallBack(globalParam1,globalParam2);
        }
    }
</script>
