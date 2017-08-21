<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 2/14/17
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
            var projectId = '${param.projectId}';
            var costGrid;
            $(function () {
                costGrid = $('#costGrid').datagrid({
                    url : '${pageContext.request.contextPath}/project/projectAction!costDataGrid.action?projectCost.projectId='+projectId,
                    fit : true,
                    border : false,
                    striped : true,
                    rownumbers : true,
                    pagination : true,
                    idField : 'id',
                    columns : [[{
                        field : 'id',
                        checkbox : true
                    },{
                        width : '200',
                        title : '支出时间',
                        field : 'costTime',
                        align : 'center'
                    },{
                        width : '200',
                        title : '支出金额',
                        field : 'costMoney',
                        align : 'center'
                    },{
                        width : '200',
                        title : '支出描述',
                        field : 'description',
                        align : 'center'
                    },{
                        title : '操作',
                        field : 'action',
                        width : '150',
                        formatter : function(value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看项目进度" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑项目进度" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            return str;
                        }
                    }
                    ]],
                    toolbar : '#toolbar',
                    onBeforeLoad : function(param) {
                        parent.$.messager.progress({
                            text : '数据加载中....'
                        });
                    },
                    onLoadSuccess : function(data) {
                        $('.iconImg').attr('src', pixel_0);
                        parent.$.messager.progress('close');
                    }
                });

            });

            function addFun() {
                var dialog = parent.modalDialog({
                    title : '新增项目进度',
                    iconCls : 'ext-icon-note_add',
                    url : '${pageContext.request.contextPath}/page/admin/project/projectCostForm.jsp?type=0&id=0&projectId='+projectId,
                    buttons : [ {
                        text : '保存',
                        iconCls : 'ext-icon-save',
                        handler : function() {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, costGrid, parent.$);
                        }
                    } ]
                });
            };

            function showFun(id) {
                var dialog = parent.modalDialog({
                    title : '查看项目进度',
                    iconCls : 'ext-icon-note',
                    url : '${pageContext.request.contextPath}/page/admin/project/projectCostForm.jsp?type=1&id=' + id +'&projectId='+projectId,
                });
            }

            function editFun(id) {
                var dialog = parent.modalDialog({
                    title : '编辑项目进度',
                    iconCls : 'ext-icon-note_edit',
                    url : '${pageContext.request.contextPath}/page/admin/project/projectCostForm.jsp?type=2&id=' + id +'&projectId='+projectId,
                    buttons : [ {
                        text : '保存',
                        iconCls : 'ext-icon-save',
                        handler : function() {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, costGrid, parent.$);
                        }
                    }]
                });
            }

            function removeFun()
            {
                var rows = $('#costGrid').datagrid('getChecked');
                var ids = [];
                if (rows.length > 0)
                {
                    parent.$.messager.confirm('确认', '确定删除吗？', function(r)
                    {
                        if (r)
                        {
                            for ( var i = 0; i < rows.length; i++)
                            {
                                ids.push(rows[i].id);
                            }
                            $.ajax({
                                url : '${pageContext.request.contextPath}/project/projectAction!deleteCost.action',
                                data : {
                                    ids : ids.join(',')
                                },
                                dataType : 'json',
                                success : function(data)
                                {
                                    if (data.success)
                                    {
                                        $("#costGrid").datagrid('reload');
                                        $("#costGrid").datagrid('unselectAll');
                                        parent.$.messager.alert('提示', data.msg, 'info');
                                    } else
                                    {
                                        parent.$.messager.alert('错误', data.msg, 'error');
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
                } else
                {
                    parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
                }
            }
        </script>
    </head>
    <body class="easyui-layout" data-options="fit:true,border:false">
        <div id="toolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <table>
                        <tr>
                            <td>
                                <authority:authority authorizationCode="新增项目进度" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addFun();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除项目进度" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeFun();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
        <div data-options="region:'center',fit:true,border:false">
        <table id="costGrid"></table>
    </div>
    </body>
</html>