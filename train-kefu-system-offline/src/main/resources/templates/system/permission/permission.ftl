<#include "../layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">权限管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="<#--table-responsive-->">
                    <div class="x_content">
                        <div class="btn-group hidden-xs" id="toolbar">
                            <button id="btn_add" type="button" class="btn btn-default" title="新增">
                                <i class="fa fa-plus"></i> 新增
                            </button>
                        </div>
                        <table id="pList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                            <thead>
                            <tr role="row">
                                <th width="50px">权限名称</th>
                                <th width="50px">权限code</th>
                                <th width="50px">权限类别</th>
                                <th width="50px">权限描述</th>
                                <#--<th width="50px">是否删除</th>-->
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
<!--添加用户弹框-->
<div class="modal fade" id="permAgentModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title">添加权限</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="permName">权限名称: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="permName" id="permName" required="required" placeholder="请输入权限名"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="permCode">权限code: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="permCode" id="permCode" required="required" placeholder="请输入权限code"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="permType">权限类型: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="permType" id="permType" required="required" placeholder="请输入权限类型"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" >说明:</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <textarea id="permDesc" class="form-control"  rows="3"></textarea>
                        </div>
                    </div>
                    <input id="txtMethod" name="txtMethod" type="hidden" />
                    <input id="isDelete" name="isDelete" type="hidden" />
                    <input id="id" name="id" type="hidden" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btn_save">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var pTable;
    $(document).ready(function () {
        init();
    });

    function init() {
        bindData();
        pTable = $('#pList').dataTable();
    }

    function bindData() {
        $('#pList').DataTable({
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
            sAjaxSource: "${ctx}/permission/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'permName'
                },
                {
                    data: 'permCode'
                },
                {
                    data: 'permType'
                },
                {
                    data: 'permDesc'
                },
                // {
                //     data: 'isDelete'
                // },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-info btn-xs' onclick='editPermAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +

                                "<a class='btn btn-danger btn-xs' onclick='deletePermAgent(" + row.id + ")'>删除</a>";
                    //    onclick=\"confirmTrain(\'"+time+"\',\'"+searchByNum+"\',\'"+startStation+"\',\'"+endStation+"\',\'"+TrainNo+"\')\"
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

        params.permName=$('#permName').val();
        params.permCode=$('#permCode').val();
        params.permType=$('#permType').val();
        params.permDesc=$('#permDesc').val();

        return params;
    }

    $('#btn_search').on('click', function() {
        pTable.fnDestroy(false);
        bindData();
    });

    // 添加
    $('#btn_add').on('click', function () {

        $('#txtMethod').val('add');

        $('#permName').val();
        $('#permCode').val();
        $('#permType').val();
        $('#permDesc').val();

        $('#permAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    function editPermAgent(id) {
        $('#txtMethod').val('edit');

        $('#title').html('编辑权限');
        tools.ajax('${ctx}/permission/get', {tId:id}, function (data) {
            console.log(data);

            var permAgent = data.data.response;
            $('#permName').val(permAgent.permName);
            $('#permCode').val(permAgent.permCode);
            $('#permType').val(permAgent.permType);
            $('#permDesc').val(permAgent.permDesc);
            $('#isDelete').val(permAgent.isDelete);
            $('#id').val(permAgent.id);


            $('#permAgentModal').modal({
                keyboard: false,
                backdrop: 'static'
            })

        })
    }
    function deletePermAgent(permId) {

        layer.confirm("确定删除该权限？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            tools.ajax('${ctx}/permission/remove', {tId:permId}, function (data) {
                console.log(data);
                if(data.data.response===1){
                    layer.msg('操作成功！');
                }else {
                    layer.msg('操作失败！');
                }
            });
        }, function(){

        });
    }

    $('#btn_save').on('click', function () {
        var data = {

            permName:$('#permName').val(),
            permCode:$('#permCode').val(),
            permType:$('#permType').val(),
            id:$('#id').val(),
            permDesc:$('#permDesc').val()

        };
        console.log(data);
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/permission/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/permission/edit"
        }
        utils.post(url, data, function () {
            pTable.fnDestroy(false);
            bindData();
            $('#permAgentModal').modal('hide');
        })
    });
    

</script>