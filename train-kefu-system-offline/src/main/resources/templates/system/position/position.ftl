<#include "../layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">职位管理</li>
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
                                    <th width="50px">职位名称</th>
                                    <th width="50px">职位描述</th>
                                    <th width="50px">是否可用</th>
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
<!--添加用户弹框-->
<div class="modal fade" id="postAgentModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title">添加职位</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="positionName">职位名称: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="positionName" id="positionName" required="required" placeholder="请输入职位名称"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" >说明:</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <textarea id="positionDesc" class="form-control"  rows="3"></textarea>
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
            sAjaxSource: "${ctx}/position/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'positionName'
                },
                {
                    data: 'positionDesc'
                },
                {
                    data: 'isDelete',
                    render:function (data, type, row, meta) {
                        if(row.isDelete == 1){
                            return "可用";
                        }else {
                            return "不可用";
                        }
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-info btn-xs' onclick='editPostAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +
                                "<a class='btn btn-danger btn-xs' onclick='deletePostAgent(" + row.id + ")'>删除</a>";
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

        params.positionName=$('#positionName').val();
        params.positionDesc=$('#positionDesc').val();

        return params;
    }

    $('#btn_search').on('click', function() {
        pTable.fnDestroy(false);
        bindData();
    });

    // 添加
    $('#btn_add').on('click', function () {
        $('#txtMethod').val('add');

        $('#positionName').val();
        $('#positionDesc').val();

        $('#postAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    function editPostAgent(id) {
        $('#txtMethod').val('edit');

        $('#title').html('编辑权限');
        tools.ajax('${ctx}/position/get', {tId:id}, function (data) {
            console.log(data);
            var postAgent = data.data.response;
            $('#positionName').val(postAgent.positionName);
            $('#positionDesc').val(postAgent.positionDesc);
            $('#id').val(postAgent.id);

            $('#postAgentModal').modal({
                keyboard: false,
                backdrop: 'static'
            })

        })
    }
    function deletePostAgent(postId) {
        layer.confirm("确定删除该职位？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            tools.ajax('${ctx}/position/remove', {tId:postId}, function (data) {
                if(data.data.response===1){
                    layer.msg('操作成功！');
                    pTable.fnDestroy(false);
                    bindData();
                }else {
                    layer.msg('操作失败！');
                }
            });
        }, function(){

        });
    }

    $('#btn_save').on('click', function () {
        var data = {
            positionName:$('#positionName').val(),
            positionDesc:$('#positionDesc').val(),
            id:$('#id').val()
        };
        console.log(data);
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/position/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/position/edit"
        }
        utils.post(url, data, function () {
            pTable.fnDestroy(false);
            bindData();
            $('#postAgentModal').modal('hide');
        })
    });
    

</script>