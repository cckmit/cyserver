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
        var electronicBookGrid;
        $(function () {
            electronicBookGrid = $('#electronicBookGrid').datagrid({
                url: '${pageContext.request.contextPath}/electronicBook/electronicBookAction!getList.action',
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
                        width: '120',
                        title: '书籍名称',
                        field: 'name',
                        align: 'center'
                    },
                    {
                        width: '80',
                        title: '作者',
                        field: 'author',
                        align: 'center'
                    },
                    {
                        width: '150',
                        title: '版本',
                        field: 'version',
                        align: 'center'
                    },
                    {
                        width: '150',
                        title: '上传机构',
                        field: 'alumniName',
                        align: 'center'
                    },
                    {
                        width: '150',
                        title: '发布时间',
                        field: 'createDate',
                        align: 'center'
                    },
                    {
                        width: '400',
                        title: '下载路径',
                        field: 'upload',
                        align: 'center'
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '120px',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看电子书刊" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改电子书刊" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            //str += '<a href="javascript:void(0)" onclick="removeFun(' + row.id + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
                            return str;
                        }
                    }]],
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

        function searchElectronicBook(){
                $('#electronicBookGrid').datagrid('load',serializeObject($('#searchForm')));

        }

        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增电子书刊',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/electronicBook/addElectronicBook.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, electronicBookGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看电子书刊',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/electronicBook/viewElectronicBook.jsp?id=' + id
            });
        };
        var editFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '编辑电子书刊',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/electronicBook/editElectronicBook.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, electronicBookGrid, parent.$);
                    }
                }]
            });
        };



        function removeData(){
            var rows = $("#electronicBookGrid").datagrid('getChecked');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/electronicBook/electronicBookAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#electronicBookGrid").datagrid('reload');
                                    $("#electronicBookGrid").datagrid('unselectAll');
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

        /**--重置--**/
        function resetT(){

            $('#searchForm')[0].reset();


            $('#contactPerson').prop('value','');
            $('#contactPhone').prop('value','');
            $('#status').combobox('clear');

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
                                书籍名称
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="electronicBook.name" style="width: 150px;" />
                            </td>

                            <th >
                                作者
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="electronicBook.author" style="width: 150px;" />
                            </td>
                            <th>
                                版本
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="electronicBook.version" style="width: 150px;" />
                            </td>

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchElectronicBook();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-huifu',plain:true"
                                   onclick="resetT();">重置</a>
                            </td>

                        </tr>
                    </table>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <table>
                    <tr>
                        <td>
                            <authority:authority authorizationCode="新增电子书刊"
                                                 userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增</a>
                            </authority:authority>
                            <authority:authority authorizationCode="删除电子书刊" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_delete',plain:true"
                                   onclick="removeData();">删除</a>
                            </authority:authority>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="electronicBookGrid"></table>
</div>
</body>
</html>
