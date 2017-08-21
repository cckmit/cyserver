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
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid').datagrid({
                url: '${pageContext.request.contextPath}/log/logAction!dataGrid.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field: 'id', checkbox: true},
                    {
                        width: '150',
                        title: '日志标题',
                        field: 'title',
                        align : 'center'
                    },{
                        width: '150',
                        title: '用户账号',
                        field: 'userAccount',
                        align : 'center'
                    },{
                        width: '150',
                        title: '用户名',
                        field: 'userName',
                        align : 'center'
                    },{
                        width: '150',
                        title: '时间',
                        field: 'createDate',
                        align : 'center'
                    },{
                        width: '150',
                        title: '日志类型',
                        field: 'type',
                        align : 'center',
                        formatter: function (value) {
                            switch (value){
                                case '1' : return "接入日志";
                                case '2' : return "错误日志";
                                default: return "";
                            }
                        }
                    },{
                        width: '150',
                        title: '操作用户的IP地址',
                        field: 'remoteAddr',
                        align : 'center'
                    },{
                        width: '150',
                        title: '操作的URI',
                        field: 'requestUri',
                        align : 'center'
                    },{
                        width: '150',
                        title: '操作的方式',
                        field: 'method',
                        align : 'center'
                    },{
                        width: '150',
                        title: '操作提交的数据',
                        field: 'params',
                        align : 'center'
                    },{
                        width: '150',
                        title: '操作用户代理信息',
                        field: 'userAgent',
                        align : 'center'
                    },{field:'operator',title:'操作',width:200,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看系统日志" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="viewLog(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            return content;
                        }
                    }
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

        function searchLogs() {
            if ($('#searchForm').form('validate')) {
                dataGrid.datagrid('load', serializeObject($('#searchForm')));
            }
        }

        function viewLog(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/log/logInfo.jsp?id=' + id
            });
        }

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
                            url: '${pageContext.request.contextPath}/log/logAction!delete.action',
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


        /**--重置--**/
        function resetT(){
            $('#title').prop('value','');
            $('#type').combobox('clear');
            $('#beginDate').prop('value','');
            $('#endDate').prop('value','');
            $('#userName').prop('value','');
            $('#userAccount').prop('value','');
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
                                日志标题
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="title" name="log.title" style="width: 150px;"/>
                            </td>

                            <th align="right">
                                日志类型
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select name="log.type" id="type" class="easyui-combobox" data-options="editable:false"style="width:150px;">
                                    <option value=""></option>
                                    <option value="1">接入日志</option>
                                    <option value="2">错误日志</option>
                                </select>
                            </td>

                            <th align="right">
                                用户名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="userName" name="log.userName" style="width: 150px;"/>
                            </td>

                            <th align="right">
                                帐号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="userAccount" name="log.userAccount" style="width: 150px;"/>
                            </td>

                        </tr>
                        <tr>
                            <th align="right" width="30px;">时间范围</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="4">
                                <input id="beginDate" name="log.beginDate" style="width: 150px;" class="easyui-datetimebox" data-options="editable:false" /> - <input id="endDate" name="log.endDate"  style="width: 150px;" class="easyui-datetimebox"/>
                            </td>

                            <th></th><td></td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchLogs();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>
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
                            <authority:authority authorizationCode="删除系统日志" userRoles="${sessionScope.user.userRoles}">
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
    <table id="dataGrid"></table>
</div>
</body>
</html>
