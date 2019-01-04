<#include "../layout/header.ftl"/>
<div class="clearfix"></div>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">用户优先处理</li>
        </ol>
        <div class="x_panel">
            <div class="x_content">
                <div class="row">
                <#--<form class="form-horizontal form-label-left">-->
                    <div class="form-group">
                        <div class="col-md-2 col-sm-2 col-xs-12">
                            <input id="textStaffName" type="text" class="form-control"  placeholder="姓名">
                        </div>
                        <div class="col-md-1 col-sm-1 col-xs-10">
                            <button type="submit" class="btn btn-success" id="btn_search" >查询</button>
                        </div>
                        <div class="col-md-1 col-sm-1 col-xs-10">
                            <button id="btn_add" type="button" class="btn btn-default"  title="新增">
                                <i class="fa fa-plus"></i> 新增
                            </button>
                        </div>
                    </div>
                <#--</form>-->
                </div>
                <table id="pList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                    <thead>
                        <tr role="row">
                            <th width="50px">姓名</th>
                            <th width="50px">员工编号</th>
                            <th width="50px">产品线</th>
                            <th width="50px">通知类型</th>
                            <th width="50px">一级分类</th>
                            <th width="50px">状态</th>
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
<div class="modal fade bs-example-modal-sm" id="selectNoticeTypes" tabindex="-1" role="dialog" aria-labelledby="selectNoticeTypesLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="selectRoleLabel">修改分类</h4>
                <#--隐藏域-->
                <input id="priorityId" name="txtMethod" type="hidden" />
            </div>
            <div class="modal-body">
                <form id="boxRoleForm">
                    <div class="zTreeDemoBackground left">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="saveNoticeTypes()">确定</button>
            </div>
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
            sAjaxSource: "${ctx}/priority/list",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'staffName'
                },
                {
                    data: 'staffNum'
                },
                {
                    data: 'noticeProductLine',
                    render: function (data, type, row, meta) {
                        return ConvertProductLine(row.noticeProductLine);
                    }
                },
                {
                    data: 'envenType',
                    render: function (data, type, row, meta) {
                        return StaffGroupType(row.envenType);
                    }
                },
                {
                    data: 'noticeTypes'
                },
                {
                    data: 'available',
                    render:function (data, type, row, meta) {
                        if(row.available == 1){
                            return "可用";
                        }else {
                            return "停用";
                        }
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        if(row.available == 1){
                            return "<a class='btn btn-primary btn-xs' onclick='editPriorityAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +
                                    "<a class='btn btn-danger btn-xs' onclick='updatePriorityStatus(" + row.id + ")'>停用</a>";
                        }else {
                            return "<a class='btn btn-primary btn-xs' onclick='editPriorityAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +
                                    "<a class='btn btn-info btn-xs' onclick='updatePriorityStatus(" + row.id + ")'>启用</a>";
                        }
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
        params.staffName=$('#textStaffName').val();

        return params;
    }

    function saveNoticeTypes(){

        var treeNotice = $.fn.zTree.getZTreeObj("treeDemo");
        var nodesNotice = treeNotice.getCheckedNodes(true);

        var noticeTypes = new Array();
        if (!(nodesNotice.length===0)){
            for(var i = 0;i < nodesNotice.length;i++ ){
                 noticeTypes.push(nodesNotice[i].id);
            }
        }
        var request={};
        request.id=$('#priorityId').val();
        request.noticeTypes=noticeTypes.join(",");
        var url = "${ctx}/priority/edit.json";
        $.ajax({
            type : 'POST',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            data : JSON.stringify(request),
            success : function(data){
                console.log(data);
                if(data.status === 0){
                    layer.msg('修改成功！');
                }else{
                    layer.msg('修改失败！');
                }
                pTable.fnDestroy(false);
                bindData();
                $('#selectNoticeTypes').modal('hide');
            },
            error : function() {

            }
        });

    }

    $('#btn_search').on('click', function() {
        pTable.fnDestroy(false);
        bindData();
    });

    // 添加
    $('#btn_add').on('click', function () {
        window.location.href="${ctx}/prioritypre";
    });

    function editPriorityAgent(id) {
        var url = "${ctx}/priority/get.json"+"?tId="+id;

        $('#priorityId').val(id);
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
                onClick: function (e, treeId, treeNode, clickFlag) {
                    tree.checkNode(treeNode, !treeNode.checked, true);
                }
            }
        },tree;
        $.ajax({
            type : 'GET',
            url : url,
            contentType : 'application/json',
            dataType : 'json',
            success : function(data1){
                var data = data1.data.response;
                console.log(data);
                var tree = $.fn.zTree.init($("#treeDemo"), setting, data);

                tree.expandAll(true);//全部展开
                $('#selectNoticeTypes').modal({
                    keyboard: false,
                    backdrop: 'static'
                })
            },
            error : function() {
            }
        });
    }


    function updatePriorityStatus(id) {
        // layer.confirm("确定该？", {
        //     btn: ['确定','取消'] //按钮
        // }, function(){
            tools.ajax('${ctx}/priority/editstatus', {tId:id}, function (data) {
                if(data.status==0){
                    pTable.fnDestroy(false);
                    bindData();
                    layer.msg(data.message);
                }else {
                    bindData();
                }
            });
        // }, function(){
        // });
    }



</script>