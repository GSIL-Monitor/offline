<#include "../layout/header.ftl"/>


<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">角色管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="<#--table-responsive-->">
                    <div class="btn-group hidden-xs" id="toolbar">
                        <button id="btn_add" type="button" class="btn btn-default"  title="新增">
                            <i class="fa fa-plus"></i> 新增
                        </button>
                    </div>
                    <table id="groupList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                        <thead>
                        <tr role="row">
                            <th width="50px">角色名</th>
                            <th width="50px">状态</th>
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
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--/弹框-->
<!--添加弹框-->
<div class="modal fade" tabindex="-1" role="dialog" id="roleAgentModal" aria-labelledby="roleAgentModal">
    <div class="modal-dialog " role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">x</span></button>
                <h4 class="modal-title" id="title">添加角色</h4>
            </div>
            <div class="modal-body">
                <label for="txtRoleName">角色名：</label>
                <input type="text" name="txtRoleName" id="txtRoleName" class="form-control input-sm" />
                <#--<label for="txtProductLine">产品线：</label>-->
                <#--<select id="selProductLine" class="form-control input-sm">-->
                    <#--<#list productLineEnums as productLine>-->
                        <#--<option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>-->
                    <#--</#list>-->
                <#--</select>-->
                <label for="available">是否可用：</label>
                <select id="available" class="form-control input-sm">
                    <option value="0">不可用</option>
                    <option value="1" selected="selected">可用</option>
                </select>
                <label for="inputRoleDesc">说明：</label>
                <textarea id="inputRoleDesc" class="form-control"  rows="3"></textarea>

                <input id="txtMethod" name="txtMethod" type="hidden" />
                <input id="roleId" name="roleId" type="hidden" />
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
            sAjaxSource: "${ctx}/role/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'roleName'
                },
                {
                    data: 'available',
                    render: function (data, type, row, meta) {
                        if (row.available == 1) {
                            return "有效";
                        } else if (row.available == 0) {
                            return "无效";
                        }
                    }
                },
                {
                    data: 'description'
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-primary btn-xs' onclick='editRoleAgent(" + row.roleId + ")'>编辑</a>&nbsp;&nbsp;" +
                               "<a class='btn btn-info btn-xs' onclick='editRoleMenu(" + row.roleId + ")'>设置菜单</a>&nbsp;&nbsp;" +
                                "<a class='btn btn-info btn-xs' onclick='editRolePerm(" + row.roleId + ")'>设置权限</a>";
                    }
                }
            ]
        })
    }

    function Search () {
        oTable.fnDestroy(false);
        bindData();
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
        params.name = $('#agentName').val();

        return params;
    }
    // 添加
    $('#btn_add').on('click', function () {
        $('#txtMethod').val('add');
        $('#title').html('添加角色');
        $('#roleAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    function editRoleAgent(roleId) {
        $('#txtMethod').val('edit');
        $('#title').html('编辑角色');
        tools.ajax('${ctx}/role/get', {roleId:roleId}, function (data) {
            console.log(data);
            var roleAgent = data;
            $('#txtRoleName').val(roleAgent.roleName);
            $('#available').val(roleAgent.available);
            $('#inputRoleDesc').val(roleAgent.description);
            $('#roleId').val(roleAgent.roleId);

            $('#roleAgentModal').modal({
                keyboard: false,
                backdrop: 'static'
            })
        })
    }

    $('#btn_save').on('click', function () {
        var data = {
            roleName: $('#txtRoleName').val(),
            roleId: $('#roleId').val(),
            available: $('#available').val(),
            inputRoleDesc: $('#inputRoleDesc').val()
        };
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/role/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/role/edit"
        }
        utils.post(url, data, function () {
            oTable.fnDestroy(false);
            bindData();
            $('#roleAgentModal').modal('hide');
        })
    });

    function editRoleMenu(rolesId) {
        var url = "${ctx}/role/roleMenu.json"+"?roleId="+rolesId;
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data1){

                var data = data1.data.response;
                if (data.length === 0){
                    layer.msg('暂无菜单数据，请先添加！');
                    return;
                }
                console.log(data);
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
                            console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
                            var treeObj = $.fn.zTree.getZTreeObj(treeId);
                            var nodes = treeObj.getCheckedNodes(true);
                            var ids = new Array();
                            for (var i = 0; i < nodes.length; i++) {
                                //获取选中节点的值
                                ids.push(nodes[i].id);
                            }
                            console.log(ids);
                            console.log(ids.join(","));
                            console.log(rolesId);
                            // $.post("/role/editRoleMenu", {"roleId": rolesId, "menuIds": ids.join(",")}, function (obj) { }, 'json');
                            var saveurl="${ctx}/role/editRoleMenu.json";
                            var request={};
                            request.rolesId=rolesId;
                            request.menuIds=ids.join(",")
                            $.ajax({
                                type : 'POST',
                                url : saveurl,
                                contentType : 'application/json',
                                dataType : 'json',
                                data : JSON.stringify(request),
                                // success : function(data){
                                //     layer.msg('请求成功！');
                                // },
                                // error : function() {
                                //     layer.msg('请求失败！');
                                // }
                            });
                        }
                    }
                };
                var tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                tree.expandAll(true);//全部展开
                $('#selectRoleLabel').html('设置菜单');
                $('#selectRole').modal({
                    keyboard: false,
                    backdrop: 'static'
                })
            },
            error : function() {
            }
        });
    }

    function editRolePerm(roleId) {
        var url = "${ctx}/role/roleperm.json"+"?roleId="+roleId;
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data1){
                var data = data1.data.response;
                console.log(data);
                if (data.length === 0){
                    layer.msg('暂无权限数据，请先添加！');
                    return;
                }
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
                            console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
                            var treeObj = $.fn.zTree.getZTreeObj(treeId);
                            var nodes = treeObj.getCheckedNodes(true);
                            var ids = new Array();
                            for (var i = 0; i < nodes.length; i++) {
                                //获取选中节点的值
                                ids.push(nodes[i].id);
                            }
                            var saveurl="${ctx}/role/editRolePerm.json";
                            var request={};
                            request.rolesId=roleId;
                            request.permId=ids.join(",")
                            $.ajax({
                                type : 'POST',
                                url : saveurl,
                                contentType : 'application/json',
                                dataType : 'json',
                                data : JSON.stringify(request),
                            });
                        }
                    }
                };
                var tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                tree.expandAll(true);//全部展开

                $('#selectRoleLabel').html('设置权限');

                $('#selectRole').modal({
                    keyboard: false,
                    backdrop: 'static'
                })
            },
            error : function() {
            }
        });
    }

</script>