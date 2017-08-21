<%@page import="com.cy.system.Global"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String school = Global.schoolSign.substring(0, Global.schoolSign.length()-2);
String downloadUrl = "";//Global.DOWNLOAD_APP_URL;
%>
<!DOCTYPE html>
<html>
<head>
  <title>用户信息</title>
  <meta name="Description" content="用户信息" />
  <meta name="Keywords" content="窗友,活动,讲座,聚会,班会,交友" />
  <meta name="author" content="Rainly" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="format-detection" content="telephone=no">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="<%=path %>/qrcode/css/cy_core.css">
  <link rel="stylesheet" href="<%=path %>/qrcode/css/font-awesome.min.css">
  <link rel="stylesheet" href="<%=path %>/qrcode/css/user_info.css">
  <script type="text/javascript">
  		function download(){
  			location.href='<%=downloadUrl%>';
  		}
  	
  </script>
</head>
<body>
<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
  <button class="ui-btn-lg ui-btn-primary" onclick="download()"><li class="fa fa-user-plus">下载<%=school%>APP</li></button>
</footer>
<section class="ui-container">
  <div class="ui-center">
    <div class="ui-avatar-one"><span style="background-image:url(${item.picUrl})"></span></div>
    <h2>${item.userName}</h2>
    <h4>${item.sex}</h4>
  </div>
  <ul class="ui-list ui-list-text ui-border-tb"><%--
    <li class="ui-border-t">
      <div class="ui-list-info">
        <h4>毕业学校</h4>
      </div>
      <div class="ui-list-action">${schoolName}</div>
    </li>
    --%><li class="ui-border-t">
      <div class="ui-list-info">
        <h4>所在地</h4>
      </div>
      <div class="ui-list-action">${item.residentialArea}</div>
    </li>
  </ul>
  <h2 class="title-desc">学习经历</h2>
  <ul class="ui-list ui-list-text ui-border-tb">
  	<c:forEach var="model" items="${list}">
    <li class="ui-border-t">
      <div class="ui-list-info">
        <p>${model.fullName}</p><%--${model.departName}<br>${model.majorName}<br>${model.gradeName} ${model.className}--%>
      </div>
    </li>
    </c:forEach>
  </ul>
</section>
</body>
</html>