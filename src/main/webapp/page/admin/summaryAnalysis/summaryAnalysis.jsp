<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title>汇总分析</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
</head>
<script type="text/javascript">
    $(function(){
        $('#summaryAnalysis').datagrid({
            url : '${pageContext.request.contextPath}/userInfo/userInfoAction!countEveryThing.action',
            fit : true,
            border : false,
            fitColumns : true,
            striped : true,
            singleSelect : true,
            columns:[[
                {field:'name',title:'汇总项目',width:180,align:'center'},
                {field:'count',title:'人数',width:180,align:'center',
                    formatter:function(value,row,index){ return value+"人"; }
                }
            ]],
            onBeforeLoad : function(param) {
                parent.$.messager.progress({
                    text : '数据加载中....'
                });
            },
            onLoadSuccess : function(data) {
                $('.iconImg').attr('src', pixel_0);
                //alert(data);
                parent.$.messager.progress('close');
            }
        });
    });
</script>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false">
    <table id="summaryAnalysis"></table>
</div>
</body>
</html>