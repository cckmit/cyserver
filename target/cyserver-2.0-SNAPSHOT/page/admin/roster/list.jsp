<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/authority" prefix="authority" %>
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
        var schoolConfigGrid;
        $(function () {
            schoolConfigGrid = $('#schoolConfigGrid').datagrid({
                url: '${pageContext.request.contextPath}/roster/rosterAction!dataGrid.action',
                fit: true,
                border: false,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field: 'id', checkbox: true},
                    {
                        width: '150',
                        title: '参数',
                        field: 'ref_id',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '所属类型',
                        field: 'dict_name',
                        align : 'center'
                    },
                    {
                        width: '150',
                        title: '加黑时间',
                        field: 'create_time',
                        formatter:function (value, row){
                        	var str = '';
                        	var create_time = row['create_time'];
                        	//str = create_time.substring(0,19);
                        	return create_time;
                        },
                        align : 'center'
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width : '150',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="删除黑白名单" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="removeFun(' + row.id + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
                            </authority:authority>
                            return str;
                        },
                        align : 'center'
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

        function schoolConfigSearch() {
            if ($('#searchForm').form('validate')) {
                schoolConfigGrid.datagrid('load', serializeObject($('#searchForm')));
            }
        }

		/**--新增黑名单--**/
        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增黑白名单',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/roster/add.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, schoolConfigGrid, parent.$);
                    }
                }]
            });
        };

		/**--删除--**/
        function removeData() {
            var rows = schoolConfigGrid.datagrid('getSelections');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function (r) {
                    if (r) {
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i]['id']);
                        }
                        $.ajax({
                            url: '${pageContext.request.contextPath}/roster/rosterAction!delete.action?type='+$("#type").val(),
                            data: {
                                ids: ids.join(',')
                            },
                            dataType: 'json',
                            success: function (data) {
                                if (data.success) {
                                    $("#schoolConfigGrid").datagrid('reload');
                                    $("#schoolConfigGrid").datagrid('unselectAll');
                                    $.messager.alert('提示', data.msg, 'info');
                                }
                                else {
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            },
                            beforeSend: function () {
                                $.messager.progress({
                                    text: '数据提交中....'
                                });
                            },
                            complete: function () {
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
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
                            <th align="right">
                                对象ID
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="ref_id" style="width: 150px;"/>
                            </td>
                            
                            <th align="right">
                            	名单类型
                            </th>
                            <td>
                                <select id="type" name="type" style="width: 155px;" class="easyui-combobox"  data-options="editable:false">
			                        <option value="1">黑名单</option>
			                        <option value="2">白名单</option>
			                    </select>
                            </td>
                            
                            
                            
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="schoolConfigSearch();">查询</a>
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
                            <authority:authority authorizationCode="新增黑白名单" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="删除黑白名单" userRoles="${sessionScope.user.userRoles}">
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
    <table id="schoolConfigGrid"></table>
</div>
</body>
</html>
