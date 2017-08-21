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

        var enterpriseId = '';
        var enterpriseName = '';
        if('${param.enterpriseName}'){
            enterpriseId = '${param.enterpriseId}';
            enterpriseName = '${param.enterpriseName}';
            $('.enterSelect').hide();
        }
        var productGrid;
        $(function () {
            productGrid = $('#productGrid').datagrid({
                url: '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!getProductDataGrid.action?enterpriseId='+enterpriseId,
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
                        title: '产品名称',
                        field: 'name'
                    },
                    {
                        width: '120',
                        title: '企业名称',
                        field: 'enterpriseName'
                    },
                    {
                        width: '100',
                        title: '产品类型',
                        field: 'typeName'
                    },
                    {
                        width: '100',
                        title: '点击数量',
                        field: 'clickNumber'
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '120',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看企业产品" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑企业产品" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
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
            $('#productName').val('');
            $('#type').combobox('clear');
            $('#enterpriseId').combobox('clear');
        }

        function showFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看企业产品',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseProductForm.jsp?action=1&id=' + id
            });
        }

        function addFun() {
            var dialog = parent.WidescreenModalDialog({
                title: '新增企业产品',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseProductForm.jsp?action=0&enterpriseId='+enterpriseId,
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
                url: '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseProductForm.jsp?action=2&id=' + id,
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
                                产品名称
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="productName" name="enterpriseProduct.name" style="width: 155px;"/>
                            </td>
                            <th  align="right" class="enterSelect">
                                校友企业
                            </th>
                            <td class="enterSelect">
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td class="enterSelect">
                                <input id="enterpriseId" name="enterpriseProduct.enterpriseId" class="easyui-combobox" style="width: 150px;" value=""
                                       data-options="editable:false,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getEnterpriseList.action'" />
                            </td>
                            <th  align="right">
                                产品类型
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="type" name="enterpriseProduct.type" class="easyui-combobox" style="width: 150px;" value=""
                                       data-options="editable:false,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!doNotNeedSecurity_getProductType.action'" />
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
                        <td>
                            <authority:authority authorizationCode="新增企业产品" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增企业产品</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="删除企业产品" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_delete',plain:true"
                                   onclick="removeData();">删除企业产品</a>
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
