<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/6/17
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
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

        var bussId = '';
        if('${param.id}'){
            bussId = '${param.id}';
            $('.enterSelect').hide();
        }
        var productGrid;
        $(function () {
            productGrid = $('#productGrid').datagrid({
                url: '${pageContext.request.contextPath}/report/reportAction!getReportDataGrid.action?bussId='+bussId,
                fit: true,
                border: false,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field:'id',checkbox : true},
                    {
                        width: '140',
                        title: '举报人',
                        field: 'reportPerson'
                    },
                    {
                        width: '120',
                        title: '活动名称',
                        field: 'bussName'
                    },
                    {
                        width: '100',
                        title: '举报内容',
                        field: 'content'
                    },
                    {
                        width: '100',
                        title: '举报类型',
                        field: 'bussType',
                    formatter: function (value, row) {
                        if(value == 10){
                        return "活动";
                        }else if(value == 15){
                            return "花絮";
                        }else if(value == 20){
                            return "社团";
                        }else if(value == 30){
                            return "返校计划";
                        }else if(value == 40){
                            return "校友企业";
                        }else if(value == 50){
                            return "校友产品";
                        }
                    }
                    },
                    {
                        width: '100',
                        title: '处理状态',
                        field: 'handleStatus',
                        formatter: function (value, row) {
                            if(value == 10){
                                return '<font color="red">处理中</font>';
                            }else if(value == 20){
                                return '<font color="green">已处理</font>';
                            }else{
                                return "举报不实";
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '120',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看举报详情" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.handleStatus == 10){
                                <authority:authority authorizationCode="处理举报内容" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>处理</a>&nbsp;';
                                </authority:authority>
                            }
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

        function searchProducts(){
            if ($('#searchForm').form('validate')) {
                $('#productGrid').datagrid('load',serializeObject($('#searchForm')));
            }
        }

        function resetT() {
            $('#reportPerson').val('');
            $('#handleStatus').val('clear');
        }

        function showFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看举报详情',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/event/reportForm.jsp?action=1&id=' + id
            });
        }

        function addFun() {
            var dialog = parent.WidescreenModalDialog({
                title: '新增企业产品',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseProductForm.jsp?action=0',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, productGrid, parent.$);
                    }
                }]
            });
        }
        function editFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '修改企业产品',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/event/reportForm.jsp?action=2&id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, productGrid, parent.$);
                    }
                }]
            });
        }
        function removeData() {
            var rows = $("#productGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#productGrid").datagrid('reload');
                                    $("#productGrid").datagrid('unselectAll');
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
                                举报人名称
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="reportPerson" name="report.reportPerson" style="width: 155px;"/>
                            </td>

                            <th align="right">
                                处理状态
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>

                            <td>
                                <select id="handleStatus"  name="report.handleStatus" style="width: 155px;">
                                    <option  value=""></option>
                                    <option  value="10">处理中</option>
                                    <option  value="20">已处理</option>
                                    <option  value="30">举报不实</option>
                                </select>
                            </td>

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchProducts();">查询</a>
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
                       <%-- <td>
                            <authority:authority authorizationCode="新增企业产品" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增企业产品</a>
                            </authority:authority>
                        </td>--%>
                        <td>
                            <authority:authority authorizationCode="删除举报" userRoles="${sessionScope.user.userRoles}">
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
    <table id="productGrid"></table>
</div>
</body>
</html>
