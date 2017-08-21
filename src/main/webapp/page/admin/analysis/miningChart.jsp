<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">

        <title>每月挖掘校友数</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!-- ECharts单文件引入 -->
        <jsp:include page="../../../inc.jsp"></jsp:include>
        <script src="${pageContext.request.contextPath}/jslib/echarts/dist/echarts.js"></script>
        <script src="${pageContext.request.contextPath}/page/admin/analysis/js/miningChart.js"></script>
        <script>
            miningEchart("miningChartMain") ;
        </script>
    </head>
<body>
<input type="button" value="柱状图" onclick="init1();" >
<input type="button" value="饼状图" onclick="init2();" >
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="miningChartMain" style="height:400px;"></div>

</body>
</html>