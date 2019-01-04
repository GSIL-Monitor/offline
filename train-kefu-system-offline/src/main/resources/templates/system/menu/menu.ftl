<#include "../layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">用户管理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="btn-group hidden-xs" id="toolbar">
                    <button id="btn_add" type="button" class="btn btn-default" title="新增">
                        <i class="fa fa-plus"></i> 新增
                    </button>
                </div>
                <table id="menuList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                    <thead>
                    <tr role="row">
                        <th width="50px">菜单名称</th>
                        <th width="50px">类型</th>
                        <th width="50px">资源地址</th>
                        <th width="50px">父级菜单</th>
                        <th width="50px">菜单图标</th>
                        <th width="50px">排序</th>
                        <th width="50px">可用</th>
                        <th width="50px">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<#include "../layout/footer.ftl"/>
<!--弹框-->
<div class="modal fade" id="addOrUpdateModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addroleLabel">添加菜单链接</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="name">菜单名称: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="name" id="name" required="required" placeholder="请输入菜单名称"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">菜单类型: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select name="type" id="type" required="required" class="form-control col-md-7 col-xs-12">
                                <#--<option value="">请选择</option>-->
                                <option value="menu">菜单</option>
                                <#--<option value="button">按钮</option>-->
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">菜单状态: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select name="available" id="available" required="required" class="form-control col-md-7 col-xs-12">
                                <option value="1">启用</option>
                                <option value="0">停用</option>
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="type">父级菜单: </label>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <select id="parentId" name="parentId" class="form-control col-md-5 col-xs-5">
                                <option value="">请选择</option>
                                <#if availableMenus?? && availableMenus?size gt 0>
                                    <#list availableMenus as item>
                                        <option value="${item.menuId}">${item.menuName}</option>
                                        <#if item.nodes?? && item.nodes?size gt 0>
                                            <#list item.nodes as node>
                                                <option value="${node.menuId}">&nbsp;&nbsp;|- ${node.menuName}</option>
                                            </#list>
                                        </#if>
                                    </#list>
                                <#else>
                                    <option value="">无</option>
                                </#if>
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="url">菜单链接: </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="url" id="url" placeholder="请输入菜单链接"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="sort">菜单排序: </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="sort" id="sort" placeholder="请输入菜单排序"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="icon">菜单图标: </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="icon" id="icon" placeholder="请输入菜单图标"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="btn_save" class="btn btn-primary addOrUpdateBtn">保存</button>
            </div>
            <input id="txtMethod" name="txtMethod" type="hidden" />
            <input id="tid" name="tid" type="hidden" />
        </div>
    </div>
</div>
<!--/弹框-->

<script type="text/javascript">
    var pTable;
    $(document).ready(function () {
        init();
    });

    function init() {
        bindData();
        pTable = $('#menuList').dataTable();
    }

    function bindData() {
        $('#menuList').DataTable({
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
            sAjaxSource: "${ctx}/menu/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'menuName'
                },
                {
                    data: 'type',
                    render:function (data, type, row, meta) {
                        return row.type == 'menu' ? '菜单' : '按钮';
                    }
                },
                {
                    data: 'url',
                    render:function (data, type, row, meta) {
                        return row.url == null||row.url == '' ? '-' : row.url;
                    }
                },
                {
                    data: 'parentName',
                    render:function (data, type, row, meta) {
                        return row.parentName == null ? '-' : row.parentName;
                    }
                },
                {
                    data: 'icon',
                    render:function (data, type, row, meta) {
                        return row.icon == '' ? '-' : row.icon;
                    }
                },
                {
                    data: 'sort'
                },
                {
                    data: 'available',
                    render:function (data, type, row, meta) {
                        return row.available == 1 ? '可用' : '停用';
                    }
                },
                {
                    data: 'menuId',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-info btn-xs' onclick='editMenuAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +
                                "<a class='btn btn-danger btn-xs' onclick='deleteMenuAgent(" + row.id + ")'>删除</a>";
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

        return params;
    }

    function editMenuAgent(id) {
        $('#txtMethod').val('edit');

        $('#addroleLabel').html('编辑菜单');
        tools.ajax('${ctx}/menu/get', {tId:id}, function (data) {
            console.log(data);

            var menuAgent = data.data.response;
            $('#name').val(menuAgent.menuName);
            $('#type').val(menuAgent.type);
            $('#url').val(menuAgent.url);
            $('#sort').val(menuAgent.sort);
            $('#icon').val(menuAgent.icon);
            $('#tid').val(menuAgent.id);
            $('#parentId').val(menuAgent.parentId);
            $('#available').val(menuAgent.available);

            $('#addOrUpdateModal').modal({
                keyboard: false,
                backdrop: 'static'
            })

        })
    }

    function deleteMenuAgent(postId) {
        layer.confirm("确定删除该菜单？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            tools.ajax('${ctx}/menu/remove', {tId:postId}, function (data) {
                if(data.data.response===1){
                    pTable.fnDestroy(false);
                    bindData();
                    layer.msg('操作成功！');
                }else {
                    pTable.fnDestroy(false);
                    bindData();
                    layer.msg('操作失败！');
                }
            });
        }, function(){

        });
    }

    // 添加
    $('#btn_add').on('click', function () {
        $('#addroleLabel').html('添加菜单');
        $('#txtMethod').val('add');

        $('#name').val("");
        $('#type').val("");
        $('#url').val("");
        $('#sort').val("");
        $('#icon').val("");
        $('#tid').val("");
        $('#parentId').val("");

        $('#addOrUpdateModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    $('#btn_save').on('click', function () {
        var data = {
            menuName:$('#name').val(),
            type:$('#type').val(),
            parentId:$('#parentId').val(),
            url:$('#url').val(),
            sort:$('#sort').val(),
            icon:$('#icon').val(),
            id:$('#tid').val()
        };
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/menu/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/menu/edit"
        }
        utils.post(url, data, function () {
            pTable.fnDestroy(false);
            bindData();
            $('#addOrUpdateModal').modal('hide');
        })
    });




</script>