<#include "../layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">增加优先处理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <table class="table-style2" style="border-collapse:separate; border-spacing:0px 15px;">
                    <tr>
                        <th style="text-align: right;width: 10%">客服姓名：</th>
                        <td class="text-left" style="width: 20%"><input  class="form-control" style="height: 25px;width: 100px"  id="staffName" type="text" value="" onblur="getStaffNum()"/></td>
                        <th style="text-align: right;width: 10%">客服编号：</th>
                        <td class="text-left" style="width: 20%"><input  class="form-control" style="height: 25px;width: 100px"  id="staffNum" type="text" value=""/></td>
                        <th style="text-align: right;width: 10%">产品线：</th>
                        <td class="text-left" style="width: 20%">
                            <select  id="selProductLine" class="form-control" onchange="onSelChange()">
                                <option value="">请选择</option>
                                <#list productLineEnums as productLine>
                                     <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                                </#list>
                            </select>
                        </td>
                    </tr>
                    <tr valign="top">
                        <th style="text-align: right" ><font color="red" >*</font>优先处理：</th>
                        <td style="max-width: 600px" class="text-left"  align="justify" colspan="5">
                            <table >
                                <tr>
                                    <td  style="max-width: 600px;width: 33%;" class="text-left"  align="justify" valign="top" >
                                        <div id="divNoticeSelect" >

                                        </div>
                                    </td>

                                    <td  style="max-width: 600px;width: 33%;" class="text-left"  align="justify" valign="top" >
                                        <div id="divComplainSelect" >

                                        </div>
                                    </td>

                                    <#--<td style="max-width: 600px" class="text-left"  align="justify" valign="top" >-->
                                        <#--<div id="divLeaderSelect" >-->

                                        <#--</div>-->
                                    <#--</td>-->
                                </tr>

                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" >
                            <a class="btn btn-primary btn-sm w80" id="btnSendNotice" onclick="addNoticePriority()">发送</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<#include "../layout/footer.ftl"/>

<#noparse>
    <script id="secondType" type="text/x-jquery-tmpl">
        <label class="checkbox-inline">
        <input type="checkbox"  value="{{= tid}}">{{= fieldName}}</label>
    </script>
</#noparse>

<!--弹框-->
<div class="modal fade bs-example-modal-sm" id="selectRole" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="selectRoleLabel">分配角色</h4>
            </div>
            <div class="modal-body">
                <form id="boxRoleForm">
                    <div class="zTreeDemoBackground left">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!--/弹框-->
<script type="text/javascript">

    function onSelChange() {
        var productLine=$("#selProductLine").val();
        var staffNum=$("#staffNum").val();
        var eventType=1;
        if(productLine!="-1"||eventType!="-1"){
            loadFirstType(productLine,staffNum);
        }
    }
    function getStaffNum(){
        var staffName=$("#staffName").val();
        var url="${ctx}/staff/queryByName.json?staffName="+staffName;
        $.ajax({
            url:url,
            method:"GET",
            success: function (data1) {
                if (data1.status === 0){
                    $("#staffNum").val(data1.data.response.staffNumber);
                }else {
                    $("#staffNum").val("");
                    layer.msg("未查到该客服！");
                }
            },
            error: function () {

            }
        })
    }


    function loadFirstType(productLine,staffNum) {
        var url="${ctx}/priority/getFirstType.json?productLine="+productLine+"&staffNum="+staffNum;
        $.ajax({
            url:url,
            method:"GET",
            success: function (data1) {
                var data = data1.data.response;
                var strNotice= " <div class='zTreeDemoBackground left'><ul id='treeNotice' class='ztree'></ul></div> ";
                var strComplain= " <div class='zTreeDemoBackground left'><ul id='treeComplain' class='ztree'></ul></div> ";
                // var strLeader= " <div class='zTreeDemoBackground left'><ul id='treeLeader' class='ztree'></ul></div> ";
                $("#divNoticeSelect").html(strNotice);
                $("#divComplainSelect").html(strComplain);
                // $("#divLeaderSelect").html(strLeader);

                var setting = {
                    check: {
                        enable: true,
                        chkboxType: {"Y": "ps", "N": "ps"},
                        chkStyle: "checkbox"
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onCheck: function (event, treeId, treeNode) {
                        }
                    }
                };
                var noticeTree = $.fn.zTree.init($("#treeNotice"), setting, data.noticeEnums);
                var complainTree = $.fn.zTree.init($("#treeComplain"), setting, data.complainEnums);
                // var leaderTree = $.fn.zTree.init($("#treeLeader"), setting, data.leaderEnums);

                noticeTree.expandAll(true);//全部展开
                complainTree.expandAll(true);//全部展开
                // leaderTree.expandAll(true);//全部展开
            },
            error: function () {
                $("#divNoticeSelect").html("没有找到问题分类")
            }
        });
    }

    function addNoticePriority(){
        var staffName= $("#staffName").val();
        var staffNum= $("#staffNum").val();

        if(staffName == ''||staffName == null||staffName == undefined){
            layer.msg("请输入员工姓名！");
            return;
        }

        if(staffNum == ''||staffNum == null||staffNum == undefined){
            layer.msg("请输入员工编号！");
            return;
        }

        var treeNotice = $.fn.zTree.getZTreeObj("treeNotice");
        var nodesNotice = treeNotice.getCheckedNodes(true);
        var treeComplain = $.fn.zTree.getZTreeObj("treeComplain");
        var nodesComplain = treeComplain.getCheckedNodes(true);

        // var treeLeader = $.fn.zTree.getZTreeObj("treeLeader");
        // var nodesLeader = treeLeader.getCheckedNodes(true);

        var idsNotice = new Array();
        if (!(nodesNotice.length===0)){
            for(var i = 0;i < nodesNotice.length;i++ ){
                if (!(nodesNotice[i].id === "notice")){
                    idsNotice.push(nodesNotice[i].id);
                }
            }
        }

        var idsComplain = new Array();
        if (!(nodesComplain.length===0)){
            for(var i = 0;i < nodesComplain.length;i++ ){
                if (!(nodesComplain[i].id === "complain")){
                    idsComplain.push(nodesComplain[i].id);
                }
            }
        }

        // var idsLeader = new Array();
        // if (!(nodesLeader.length===0)){
        //     for(var i = 0;i < nodesLeader.length;i++ ){
        //         if (!(nodesLeader[i].id === "leader")){
        //             idsLeader.push(nodesLeader[i].id);
        //         }
        //     }
        // }
        var request={};
        request.staffName=$("#staffName").val();
        request.staffNum=$("#staffNum").val();
        request.noticeProductLine=$("#selProductLine").val();
        request.noticeTypes=idsNotice.join(",");
        request.complainTypes=idsComplain.join(",");
        // request.leaderTypes=idsLeader.join(",");

        var url = "${ctx}/priority/add.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                console.log(data);
                layer.msg(data.message);
            },
            error : function() {

            }
        });
    }

</script>