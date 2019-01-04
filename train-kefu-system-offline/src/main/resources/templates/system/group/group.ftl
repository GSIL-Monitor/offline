<#include "../layout/header.ftl"/>


<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">组别管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="<#--table-responsive-->">
                    <div class="row">
                            <form class="form-horizontal form-label-left">
                                    <div class="form-group">
                                        <#--<label class="control-label col-md-1 col-sm-1 col-xs-2"> 产品线 </label>-->
                                        <#--<div class="col-md-1 col-sm-1 col-xs-3">-->
                                            <#--<select id="selProductLine"  class="form-control ">-->
                                                <#--<option value="-1">全部</option>-->
                                                 <#--<#list productLineEnums as productLine>-->
                                                    <#--<option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>-->
                                                 <#--</#list>-->
                                            <#--</select>-->
                                        <#--</div>-->
                                        <div class="col-md-2 col-sm-2 col-xs-3">
                                            <button type="submit" class="btn btn-success" onclick="Search()">查询</button>
                                            <button id="btn_add" type="button" class="btn btn-default"  title="新增">
                                                <i class="fa fa-plus"></i> 新增
                                            </button>
                                        </div>
                                    </div>
                            </form>
                    </div>

                        <div class="x_content">
                            <table id="groupList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                                <thead>
                                    <tr role="row">
                                        <th width="50px">产品线</th>
                                        <th width="50px">员工组别</th>
                                        <th width="50px">员工编号</th>
                                        <th width="50px">领班</th>
                                        <th width="50px">说明</th>
                                        <th width="50px">操作</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>

                </div>
            </div>
        </div>
    </div>
</div>
<#include "../layout/footer.ftl"/>
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
<!--添加弹框-->
<div class="modal fade" tabindex="-1" role="dialog" id="groupAgentModal" aria-labelledby="groupAgentModal">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">x</span></button>
                <h4 class="modal-title" id="selectGroupLabel">添加分组</h4>
            </div>
            <div class="modal-body">
                <label for="txtGroupName">组名：</label>
                <input type="text" name="txtGroupName" id="txtGroupName" class="form-control input-sm" />
                <label for="txtProductLine">产品线：</label>
                <select id="inputProductLine" class="form-control input-sm">
                    <#list productLineEnums as productLine>
                           <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                    </#list>
                </select>
                <label for="txtStaffNum">领班编号：</label>
                <input type="text" name="txtStaffNum" id="txtStaffNum" class="form-control input-sm" onblur="getStaffName()"/>
                <#--<label for="foremanName">领班姓名：</label>-->
                <#--<input type="text" name="foremanName" id="foremanName" class="form-control input-sm" />-->
                <label for="inputGroupDesc">说明：</label>
                <textarea id="inputGroupDesc" class="form-control"  rows="3"></textarea>
                <input id="txtMethod" name="txtMethod" type="hidden" />
                <input id="groupId" name="isDelete" type="hidden" />
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btn_save">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var oTable;

    $(document).ready(function () {
        init();
    });

    function init() {
        bindData();
        oTable = $('#groupList').dataTable();
    }

    function bindData() {
        $('#groupList').DataTable({
            sPaginationType: 'full_numbers',
            bPaginate: true,
            // bLengthChange: true,
            lengthChange: false,
            bInfo: true,
            bSort: false,
            bAutoWidth: false,
            serverSide: true,
            bFilter: false,
            language: tools.tableLang(),
            stripeClasses: ["odd", "even"],
            processing: true,
            sAjaxSource: "${ctx}/group/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'productLine',
                    render: function (data, type, row, meta) {
                        return ConvertProductLine(row.productLine);
                    }
                },
                {
                    data: 'groupName'
                },
                {
                    data: 'supervisorStaffNUmber'
                },
                {
                    data: 'staffName'
                },
                {
                    data: 'groupDesc'
                },
                {
                    data: 'tId',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-info btn-xs' onclick='editGroupAgent(" + row.tid + ")'>编辑</a>&nbsp;&nbsp;" +
                                "<a class='btn btn-primary btn-xs' onclick='groupStaff(" + row.tid + ")' >组员</a>&nbsp;&nbsp;"+
                                "<a class='btn btn-danger btn-xs' onclick='deleteGroupAgent(" + row.tid + ")'>删除</a>";
                    }
                }
            ]
        })
    }
    function queryParams(data) {
        var params = {};
        data.map(function (item) {
            if (item.name === "iDisplayLength") {
                params.pageSize = item.value;
            } else if (item.name === "iDisplayStart") {
                params.pageIndex = item.value;
            }
        });

        params.pageIndex = params.pageIndex / params.pageSize + 1;
        params.groupName = $('#groupName').val();
        params.productLine = $('#selProductLine').val();

        return params;
    }
    // 添加
    $('#btn_add').on('click', function () {

        $('#txtGroupName').val("");
        $('#txtStaffNum').val("");
        $('#inputGroupDesc').val("");
        $('#inputProductLine').val("");

        $('#txtMethod').val('add');
        $('#selectGroupLabel').html('添加分组');
        $('#groupAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })

    });

    function editGroupAgent(tid) {
        $('#txtMethod').val('edit');
        $('#selectGroupLabel').html('修改分组');

        tools.ajax('${ctx}/group/get', {tid:tid}, function (data) {
            var groupAgent = data.data.response;
            $('#txtGroupName').val(groupAgent.groupName);
            $('#txtStaffNum').val(groupAgent.supervisorStaffNUmber);
            $('#inputGroupDesc').val(groupAgent.groupDesc);
            $('#inputProductLine').val(groupAgent.productLine);
            $('#groupId').val(groupAgent.groupId);
            $('#groupAgentModal').modal({
                keyboard: false,
                backdrop: 'static'
            })
        })
    }
    function groupStaff(tid) {
        window.location.href="${ctx}/groupstaffpre?tid="+tid;
    }

    $('#btn_save').on('click', function () {
        var data = {
            groupName: $('#txtGroupName').val(),
            productLine: $('#inputProductLine').val(),
            supervisorStaffNUmber: $('#txtStaffNum').val(),
            groupId: $('#groupId').val(),
            groupDesc:$('#inputGroupDesc').val()
        };
        console.log(data);
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/group/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/group/edit";
        }
        utils.post(url, data, function () {
            oTable.fnDestroy(false);
            bindData();
            $('#groupAgentModal').modal('hide');
        })
    });


    function getStaffName() {
        var staffNum=$('#txtStaffNum').val();
        var url = "${ctx}/staff/get.json"+"?staffNum="+staffNum;
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data){
                console.log(data);
                if(data.status==0){
                    $('#foremanName').val(data.data.response.staffName);
                    $('#btn_save').removeAttr("disabled");
                }else {
                    layer.msg("员工不存在！");
                    $('#foremanName').val('');
                    $('#btn_save').attr('disabled',"true");
                }
            },
            error : function() {
            }
        });
    }

    function deleteGroupAgent(id) {
        layer.confirm("确定该组别么？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            tools.ajax('${ctx}/group/remove', {tid:id}, function (data) {

                if(data.status==0){
                    oTable.fnDestroy(false);
                    bindData();
                    layer.msg("删除成功！");
                }else {
                    oTable.fnDestroy(false);
                    bindData();
                    layer.msg("删除失败！");
                }
            });
        }, function(){
        });
    }
</script>