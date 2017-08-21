<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var grid;
        $(function () {
            grid = $('#countGrid').datagrid({
                url : '${pageContext.request.contextPath}/smsCount/smsCountAction!dataGrid.action',
                fit : true,
                method : 'post',
                border : true,
                striped : true,
                pagination : false,
                singleSelect: true,
                columns:[[
                    {field:'title',width:200,align:'center'},
                    {field:'value',width:300,align:'center'}
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
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="toolbar">
            <table>
                <tr>
                    <th align="right">时间范围</th>
                    <td>
                        <div class="datagrid-btn-separator"></div>
                    </td>
                    <td>
                        <select id="timeLine" class="easyui-combobox" data-options="
                            editable:false,panelHeight:150,
                            onSelect: function(value){
                                var json = {'timeLine': value.value};
                                $('#countGrid').datagrid('load', json);
						}
                        ">
                            <option value="">全部</option>
                            <option value="1">最近一周</option>
                            <option value="2">最近一个月</option>
                            <option value="3">最近三个月</option>
                            <option value="4">最近六个月</option>
                            <option value="5">最近一年</option>
                        </select>
                    </td>
                </tr>
            </table>
    </div>
    <div  data-options="region:'center',fit:false,border:false">
        <table id="countGrid"></table>
    </div>
</div>
</body>
</html>
