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
                url: '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseTeamAction!getTeamDataGrid.action?enterpriseId='+enterpriseId,
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
                        title: '姓名',
                        field: 'fullName'
                    },
                    {
                        width: '140',
                        title: '职位',
                        field: 'position'
                    },
                    {
                        width: '260',
                        title: '企业名称',
                        field: 'enterpriseName'
                    },
                    {
                        width: '140',
                        title: '校友类型',
                        field: 'status',
                        formatter:function (value) {
                            var content = '' ;
                            if (value == 20 || value == '20') {
                                content = "<span style='color: #00a510'>正式校友</span>" ;
                            } else if (value == 25 || value == '25') {
                                content = "<span style='color: #ffed08'>名誉校友</span>" ;
                            }else if (value == 30 || value == '30') {
                                content = "<span style='color: red'>审核不通过</span>" ;
                            }else if(value == 10 || value == '10'){
                                content = "<span style='color:grey'>待审核</span>" ;
                            }
                            return content ;
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
            $('#fullName').val('');
            $('#isAlumni').combobox('clear');
            $('#enterpriseId').combobox('clear');

        }

        function showFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看企业成员',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/cloudEnterprise/cloudEnterpriseTeamForm.jsp?action=1&id=' + id
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
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="fullName" name="cloudEnterpriseTeam.fullName" style="width: 155px;"/>
                            </td>
                            <th  align="right" >
                                校友企业
                            </th>
                            <td >
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="enterpriseId" name="cloudEnterpriseTeam.enterpriseId" style="width: 155px;"/>
                            </td>
<%--
                            <th>
                                是否为校友
                            </th>
                            <td>
                                <select id="isAlumni" class="easyui-combobox" data-options="editable:false" name="cloudEnterpriseTeam.isAlumni" style="width: 150px;">
                                    <option value=""></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>

                            </td>--%>
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
