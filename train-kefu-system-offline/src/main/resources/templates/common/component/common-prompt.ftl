<#--通用用户弹窗,确认后回调响应方法完成后续操作，需要jquery支持-->
<#--传入会掉方法必须按照以下标准-->
<#--callBack的第一个参数为用户输入-->
<#--validateCallback为参数校验方法，返回值为空时参数验证正确-->
<#--Created by ying_zhou-->
<div id="commonUserInputModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="commonUserInputModalLabel"  aria-hidden="true">
    <div class="modal-dialog" style="width: 400px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3  id="commonUserInputModalLabel">请输入：</h3>
            </div>
            <div class="modal-body" style="min-height: 30px">
                  <input type="text" id="inputCommonPrompt" size="15" class="input-box" style="width:100%;height:25px;">
            </div>
            <div class="modal-footer">
                <div style="max-width: 70%;padding: 5px 20px 5px 20px;display: none;margin-bottom: 0" class="alert alert-warning pull-left "  id="alertCommonPrompt"></div>
                <a class="btn btn-sm w80" data-dismiss="modal" aria-hidden="true" >关闭</a>
                <a id="btnCommonPromptConfirm" onclick="doCallBack()" class="btn btn-primary btn-sm w80" >确定</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var CallBack;
    var ValidateCallBack;
    var Param1;
    var Param2;
    function commonPrompt2(callback,validateCallback,param1,param2,title) {
        CallBack=callback;
        ValidateCallBack=validateCallback;
        Param1=param1;
        Param2=param2;
        $("#alertCommonPrompt").hide();
        if(title!=null){
            $("#commonUserInputModalLabel").html(title);
        }
        $("#commonUserInputModal").modal();
    }
    function commonPrompt1(callback,validateCallback,param1,title) {
        commonPrompt2(callback,validateCallback,param1,null,title);
    }

    function doCallBack() {
        var userInput=$("#inputCommonPrompt").val();
        if(ValidateCallBack!=null){
            var validateStr=ValidateCallBack(userInput);
            if(validateStr){
                $("#alertCommonPrompt").html(validateStr).fadeIn().delay(1000).fadeOut();
                return;
            }
        }
        if (CallBack != null) {
            CallBack(userInput,Param1,Param2);
        }
        $("#commonUserInputModal").modal();
    }
</script>