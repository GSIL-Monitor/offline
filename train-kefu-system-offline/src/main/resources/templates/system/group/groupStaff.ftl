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
                        <table class="table table-responsive">
                            <tr><td > <h4 class="h4"><strong>${group.groupName}</strong></h4>
                                <input type="hidden" id="hiddenGroupTid" value="${group.tid}"></td>
                                <td  style="padding: 5px 20px" class="text-left">
                                    <dl class="dl-horizontal">
                                        <dt>主管:</dt><dd class="text-left">${group.supervisorStaffNUmber}</dd>
                                        <dt>说明:</dt><dd class="text-left">${group.groupDesc}</dd>
                                    </dl>
                                </td>
                            </tr>
                            <#--<tr>-->
                                <#--<td width="30%">-->
                                    <#--<a class="btn btn-primary btn-sm w80 " id="btnEdit" onclick="startEdit()">编辑</a>-->
                                    <#--<a class="btn btn-success btn-sm w80 " style="display: none" id="btnSaveChange" onclick="saveChange()">保存</a>-->
                                    <#--<a class="btn btn-danger btn-sm w80" style="display: none" id="btnCancel" onclick="javascript: window.location.reload();">取消</a>-->
                                <#--</td>-->
                                <#--<td><input type="text" id="inputStaffName" style="display: none" oninput="quickSearch()" class="input-sm " placeholder="要查找的员工姓名"/></td>-->
                            <#--</tr>-->
                        </table>

                        <div class="btn-group hidden-xs" id="toolbar">
                            <button id="btn_edit" type="button" class="btn btn-default" title="编辑" onclick="editAgent()" style="display:block;">
                                <i class="fa fa-plus"></i> 编辑
                            </button>
                            <button id="btn_save" type="button" class="btn btn-default" title="保存" onclick="saveAgent()" style="display:none;">
                                <i class="fa fa-plus"></i> 保存
                            </button>
                        </div>
                        <table id="pList" class="table table-bordered table-hover table-condensed" style="border-collapse: collapse;" >
                            <thead>
                                <tr role="row">
                                    <th width="50px">操作</th>
                                    <th width="50px">员工编号</th>
                                    <th width="50px">员工姓名</th>
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
            sAjaxSource: "${ctx}/groupstaff",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'tid',
                    render: function (data, type, row, meta) {
                        return "<input type='checkbox' checked='checked' disabled >";
                    }
                },
                {
                    data: 'staffNumber'
                },
                {
                    data: 'staffName'
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

        params.groupId=$('#hiddenGroupTid').val();

        return params;
    }

    $('#btn_search').on('click', function() {
        pTable.fnDestroy(false);
        bindData();
    });

    // 添加
    $('#btn_edit').on('click', function () {
        $('#txtMethod').val('add');

        $('#positionName').val();
        $('#positionDesc').val();

        $('#postAgentModal').modal({
            keyboard: false,
            backdrop: 'static'
        })
    });

    function editAgent() {
        var btnsave = document.getElementById('btn_save');
        btnsave.style.display = "none";
        var currentBtn = document.getElementById('btn_edit');
        currentBtn.style.display = "block";
        $('#hiddenGroupTid').val();
        pTable.fnDestroy(false);
        bindEditData();
    }
    function bindEditData() {
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
            sAjaxSource: "${ctx}/groupstaff/editpre",
            aLengthMenu: [20],
            fnServerData: function(url, data, callBack) {
                var query = queryParams(data);
                tools.ajax(url, query, callBack)
            },
            columns: [
                {
                    data: 'checked',
                    render: function (data, type, row, meta) {
                        if(row.checked =='true')
                            return "<input name='id'  value='" + row.staffNumber + "' type='checkbox' checked='checked'>";
                        else
                            return "<input name='id'  value='" + row.staffNumber + "' type='checkbox'>";
                    }
                },
                {
                    data: 'staffNumber'
                },
                {
                    data: 'staffName'
                }
            ]
        })
    }


    function saveAgent() {
        debugger;
        var id_array=new Array();
        $('input[name="id"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
        if(id_array.length === 0){
            layer.msg('请勾选员工！');
            return;
        }
        var data = {
            groupId:$('#hiddenGroupTid').val(),
            staffNums:id_array.join(',')
        };
        var url = "${ctx}/groupstaff/edit";
        utils.post(url, data, function () {
            pTable.fnDestroy(false);
            bindData();
        })
    }

</script>