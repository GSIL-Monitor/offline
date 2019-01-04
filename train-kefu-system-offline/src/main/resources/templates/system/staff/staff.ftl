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
                <div class="<#--table-responsive-->">
                    <div class="x_content">
                        <div class="row">
                        <#--<form class="form-horizontal form-label-left">-->
                            <div class="form-group">
                                <div class="col-md-2 col-sm-2 col-xs-12">
                                    <input id="textStaffName" type="text" class="form-control" placeholder="姓名">
                                </div>
                                <div id="selPosition" class="col-md-2 col-sm-2 col-xs-12">
                                    <select id="selPost" class="form-control">
                                        <option value="">全部职位</option>
                                        <#list postsEnums as post>
                                            <option value="${post.getId()}">${post.getPositionName()}</option>
                                        </#list>
                                    </select>
                                </div>

                                <div class="col-md-2 col-sm-2 col-xs-12">
                                    <select  id="selProductLine" class="form-control">
                                        <option value="">全部产品线</option>
                                        <#list productLineEnums as productLine>
                                            <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                                        </#list>
                                    </select>
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
                        <table id="staffList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                            <thead>
                            <tr role="row">
                                <th width="50px">姓名</th>
                                <th width="50px">员工编号</th>
                                <th width="50px">产品线</th>
                                <th width="50px">职位</th>
                                <th width="50px">员工组别</th>
                                <#--<th width="50px">小组名称</th>-->
                                <th width="50px">角色</th>
                                <#--<th width="50px">备注</th>-->
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
<div class="modal fade" id="staffAgentModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="title">添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="username">姓名: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="username" id="username" required="required" placeholder="请输入用户名"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="usernum">员工编号: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" onchange="queryStaffbyStaffNum()"  class="form-control col-md-7 col-xs-12" name="usernum" id="usernum" required="required" placeholder="员工编号首字母大写"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="username">产品线: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <#--<input type="text" class="form-control col-md-7 col-xs-12" name="username" id="username" required="required" placeholder="请输入用户名"/>-->
                                <select id="selProductLine" class="form-control">
                                    <option value="">请选择</option>
                                <#list productLineEnums as productLine>
                                       <option value="${productLine.getProductLineCode()}">${productLine.getProductLineName()}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="username">职位: <span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select id="selPostId" class="form-control">
                                <option value="">请选择</option>
                                <#list postsEnums as post>
                                    <option value="${post.getId()}">${post.getPositionName()}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="email">邮箱:</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="email" class="form-control col-md-7 col-xs-12" name="email" id="email" placeholder="请输入邮箱"/>
                        </div>
                    </div>

                    <#--<div class="item form-group">-->
                        <#--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="nickname">职位:</label>-->
                        <#--<div class="col-md-6 col-sm-6 col-xs-12">-->
                            <#--<input type="text" class="form-control col-md-7 col-xs-12" name="nickname" id="nickname" placeholder="请输入昵称"/>-->
                        <#--</div>-->
                    <#--</div>-->
                    <#--<div class="item form-group">-->
                        <#--<label class="control-label col-md-3 col-sm-3 col-xs-12" for="mobile">组别:</label>-->
                        <#--<div class="col-md-6 col-sm-6 col-xs-12">-->
                            <#--<input type="text" class="form-control col-md-7 col-xs-12" name="mobile" id="mobile" data-validate-length-range="8,20" placeholder="请输入手机号"/>-->
                        <#--</div>-->
                    <#--</div>-->

                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" >角色:</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select style="width: 100px" id="selRole" class="selectpicker" multiple>
                                 <#list rolesEnums as role>
                                     <option  id="" value="${role.getRoleId()}">${role.getRoleName()}</option>
                                 </#list>
                            </select>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" >说明:</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <textarea id="inputRoleDesc" class="form-control"  rows="3"></textarea>
                        </div>
                    </div>
                    <input id="txtMethod" name="txtMethod" type="hidden" />
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
    var sTable;
    $(document).ready(function () {
        init();
    });

    function init() {
        bindData();
        sTable = $('#staffList').dataTable();
    }

    function bindData() {
        $('#staffList').DataTable({
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
            sAjaxSource: "${ctx}/staff/list",
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
                    data: 'productLine',
                    render: function (data, type, row, meta) {
                        return ConvertProductLine(row.productLine);
                    }
                },
                {
                    data: 'positionName'
                },
                {
                    data: 'eventType',
                    render: function (data, type, row, meta) {
                        return StaffGroupType(row.eventType);
                    }
                },
                {
                    data: 'rolesName'
                    // render: function (data, type, row, meta) {
                    //     // var arr = [];
                    //     // if (row.roles==null||row.roles===''||row.roles===undefined){
                    //     //     return "无";
                    //     // }else {
                    //     //     for(var i=1;row.roles.length>i;i++) {
                    //     //         arr.push(row.roles[i].roleName);
                    //     //     }
                    //     // }
                    //     // return arr.join();
                    //     return row.roles;
                    // }
                },
                // {
                //     data: 'id'
                // },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return "<a class='btn btn-info btn-xs' onclick='editStaffAgent(" + row.id + ")'>编辑</a>&nbsp;&nbsp;" +
                                "<a class='btn btn-danger btn-xs' onclick='deleteStaffAgent(" + row.id + ")' >删除</a>&nbsp;&nbsp;";
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
        params.staffName = $('#textStaffName').val();
        // params.position = $('#selPosition').val();
        // params.group = $('#selGroup').val();
        params.positionId = $('#selPost').val();
        params.productLine = $('#selProductLine').val();
        return params;
    }

    $('#btn_search').on('click', function() {
        sTable.fnDestroy(false);
        bindData();
    });

    // 添加
    $('#btn_add').on('click', function () {

        $('#txtMethod').val('add');

        $('#username').val();
        $('#usernum').val();
        $('#selProductLine').val();
        $('#selPostId').val();
        $('#email').val();
        $('#selRole').selectpicker();

        $('#staffAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    function editStaffAgent(id) {

        $('#txtMethod').val('edit');

        $('#title').html('编辑员工');
        tools.ajax('${ctx}/staff/get', {tId:id}, function (data) {
            console.log(data);
            var staffAgent = data.data.response;
            $('#username').val(staffAgent.staffName);
            $('#usernum').val(staffAgent.staffNum);
            $('#selProductLine').val(staffAgent.productLine);
            $('#selPostId').val(staffAgent.positionId);
            $('#email').val(staffAgent.email);
            var arr = [];
            var arr1 = staffAgent.roles;
            for (var i=0;arr1.length>i ;i++){
                var name=arr1[i].roleId;
                arr.push(name);
            }
            $('#selRole').selectpicker('val', arr);
            $('#staffAgentModal').modal({
                keyboard: false,
                backdrop: 'static'
            })

        })
    }

    $('#btn_save').on('click', function () {
        var staffName=$('#username').val();
        var staffNum=$('#usernum').val();
        var productLine=$('#selProductLine').val();
        var positionId=$('#selPostId').val();
        var email=$('#email').val();
        var roleId=$('#selRole').val().join();

        if(roleId == ''||roleId == null||roleId == undefined){
            layer.msg('角色不能为空！');
            return;
        }
        <#--//判断员工编号是不是唯一的-->
        <#--tools.ajax('${ctx}/staff/query', {staffNum:staffNum}, function (data) {-->
            <#--console.log(data);-->
            <#--if(data.status == 1){-->
                <#--layer.msg("员工编号已经存在！");-->


            <#--}-->
        <#--});-->
        var data = {
            staffName: staffName,
            staffNum: staffNum,
            productLine:productLine,
            positionId: positionId,
            email: email,
            roleId: roleId
        };
        var url;
        if ($('#txtMethod').val() === 'add') {
            url = "${ctx}/staff/add";
        }
        if ($('#txtMethod').val() === 'edit') {
            url = "${ctx}/staff/edit"
        }
        utils.post(url, data, function (response) {
            console.log(response);
            sTable.fnDestroy(false);
            bindData();
            $('#staffAgentModal').modal('hide');
        })
    });
    function editStaffNotice(id) {
        window.location.href="/prioritypre?id="+id;
    }

    function queryStaffbyStaffNum() {
        var staffNum=$('#usernum').val();
        tools.ajax('${ctx}/staff/query', {staffNum:staffNum}, function (data) {
            console.log(data);
            if(data.status === 0){
                layer.msg("员工编号已经存在！");
                $('#usernum').val("");
            }
        });
    }

    function deleteStaffAgent(id) {
        layer.confirm("确定该该员工么？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            tools.ajax('${ctx}/staff/delete', {tId:id}, function (data) {
                if(data.status==0){
                    sTable.fnDestroy(false);
                    bindData();
                    layer.msg("删除成功！");
                }else {
                    sTable.fnDestroy(false);
                    bindData();
                    layer.msg("删除失败！");
                }
            });
        }, function(){
        });
    }
</script>