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
//        alert(enterpriseId)
        $(function () {
            productGrid = $('#productGrid').datagrid({
                url: '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseProductAction!getProductDataGrid.action?enterpriseId='+enterpriseId,
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
                url: '${pageContext.request.contextPath}/page/admin/cloudEnterprise/cloudEnterpriseProductForm.jsp?action=1&id=' + id
            });
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
                                <input id="productName" name="cloudEnterpriseProduct.name" style="width: 155px;"/>
                            </td>
                            <th  align="right" >
                                校友企业
                            </th>
                            <td>
                                <input id="enterpriseId" name="cloudEnterpriseProduct.enterpriseId" style="width: 155px;"/>
                            </td>
                            <th  align="right">
                                产品类型
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="type" name="cloudEnterpriseProduct.type" style="width: 155px;"/>
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
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="productGrid"></table>
</div>
</body>
</html>
