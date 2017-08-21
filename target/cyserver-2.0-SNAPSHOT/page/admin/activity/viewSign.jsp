<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title></title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var eventGrid;
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/backschoolOnlineSign/backschoolOnlineSignAction!getList.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field:'id',checkbox : true},
                    {
                        width: '70',
                        title: '姓名',
                        field: 'name'
                    },
                    {
                        width: '40',
                        title: '性别',
                        field: 'sex',
                        formatter : function(value, row) {
                            if(row.sex == '0') {
                                return "男";
                            } else if(row.sex == '1') {
                                return "女";
                            } else {
                                return "";
                            }
                        }
                    },
                    {
                        width: '80',
                        title: '生日',
                        field: 'birthday'
                    },
                    {
                        width: '80',
                        title: '电话',
                        field: 'mobilePhone'
                    },
                    {
                        width: '80',
                        title: '工作单位',
                        field: 'unit'
                    },
                    {
                        width: '80',
                        title: '所在地',
                        field: 'location'
                    },
                    {
                        width: '120',
                        title: '邮箱',
                        field: 'postCode'
                    },
                    {
                        width: '120',
                        title: '所在地',
                        field: 'location'
                    },
                    {
                        width: '120',
                        title: '职务',
                        field: 'position'
                    },
                    {
                        width: '120',
                        title: '状态',
                        field: 'status',
                        formatter: function(value){
                            switch (value){
                                case 10 : return '待审核';
                                    break;
                                case 20 : return '已通过';
                                    break;
                                case 30 : return '未通过';
                                    break;
                                default : return '';
                            }
                        }
                    },
                    {field:'operator',title:'操作',width:120,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看校友卡" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewData(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.status == '10'){
                                <authority:authority authorizationCode="审核报名卡" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkData(\''+row.id+'\', 20)"><img class="iconImg ext-icon-note_edit"/>通过</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="审核报名卡" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkData(\''+row.id+'\', 30)"><img class="iconImg ext-icon-note_edit"/>不通过</a>&nbsp;';
                                </authority:authority>
                            }
                            return content;
                        }}
                ]],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess: function (data) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

        function viewData(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/activity/backschoolSign.jsp?id=' + id
            });
        }

        function checkData(id, status) {
            var content = '';
            if(status == 30)
                content = '不';
            $.messager.confirm('确认', '确定审核'+content+'通过吗？', function(r) {
                if(r){
                    var jsonStr =
                    {
                        "backschoolOnlineSign.id" : id,
                        "backschoolOnlineSign.status" : status
                    };

                    $.ajax({
                        url : "${pageContext.request.contextPath}/backschoolOnlineSign/backschoolOnlineSignAction!update.action",
                        data : jsonStr,
                        dataType : 'json',
                        success : function(result)
                        {
                            if (result.success){
                                $('#eventGrid').datagrid('reload');
                                $('#eventGrid').datagrid('unselectAll');
                                $.messager.alert('提示', result.msg, 'info');
                            }else{
                                $.messager.alert('提示', result.msg, 'error');
                            }
                        },
                        beforeSend : function()
                        {
                            parent.$.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function()
                        {
                            parent.$.messager.progress('close');
                        }
                    });
                }
            });
        }
        function removeData(){
            var rows = $("#eventGrid").datagrid('getChecked');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/backschoolOnlineSign/backschoolOnlineSignAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $('#eventGrid').datagrid('reload');
                                    $('#eventGrid').datagrid('unselectAll');
                                    $.messager.alert('提示',data.msg,'info');
                                }
                                else{
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            },
                            beforeSend:function(){
                                $.messager.progress({
                                    text : '数据提交中....'
                                });
                            },
                            complete:function(){
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
        }


        function searchActivity(){
            $('#eventGrid').datagrid('load',serializeObject($('#searchForm')));

        }

        /**--重置--**/
        function resetT(){
            $('input').prop('value', '');
            $('#status').combobox('setValue',0);
        }

    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <table>
        <tr>
            <td>
                <form id="searchForm">
                    <table>
                        <tr>
                            <th >
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="backschoolOnlineSign.name" style="width: 150px;" />
                            </td>

                            <th>
                                审核状态
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select name="backschoolOnlineSign.status" class="easyui-combobox" id="status" style="width:155px"
                                        data-options="required:true, editable:false"
                                >
                                    <option value=0>全部</option>
                                    <option value=10>待审核</option>
                                    <option value=20>已通过</option>
                                    <option value=30>未通过</option>
                                </select>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchActivity();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-huifu',plain:true"
                                   onclick="resetT();">重置</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <authority:authority authorizationCode="删除报名卡" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeData();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>