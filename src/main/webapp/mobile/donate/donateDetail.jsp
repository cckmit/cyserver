<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/mobile/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>捐赠</title>
    <meta name="Description" content="捐赠" />
    <meta name="Keywords" content="窗友,捐赠" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath%>css/cy_core.css">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/donation.css">
</head>
<body>
<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
    <button class="ui-btn-lg" onclick="history.go(-1)">返回</button>
    <c:if test="${project.projectName!=null}">
	    <button class="ui-btn-lg ui-btn-primary" data-href="../project/projectAction!doNotNeedSessionAndSecurity_getByIdForm.action?id=${project.projectId}&accountNum=${accountNum}">捐赠该项目</button>
    </c:if>
</footer>
<section class="ui-container donate-show">
    <header>
        <h2>${project.projectName}</h2>
        <div class="meta">
            <span class="time"><fmt:formatDate value="${project.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            <%--<span class="author">捐赠</span>
        --%></div>
    </header>
    <article>
       ${project.content}
    </article>
</section>
<script src="<%=basePath%>js/zepto.js"></script>
<script src="<%=basePath%>js/cy_core.js"></script>
<script src="<%=basePath%>js/custom_donate.js"></script>
</body>
</html>