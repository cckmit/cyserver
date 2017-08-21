<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<a href="api/fileUpload.jsp">1.文件上传接口</a>
	<br>
	<br>
	<a href="api/baseid.jsp">2.获取baseInfoId接口</a>
	<br>
	<br>
	<a href="api/registerCode.jsp">3.发送验证码接口</a>
	<br>
	<br>
	<a href="api/register.jsp">4.用户注册接口</a>
	<br>
	<br>
	<a href="api/updateUserProfile.jsp">5(23).用户信息修改</a>
	<br>
	<br>
	<a href="api/photoUpload.jsp">6.个人头像上传接口</a>
	<br>
	<br>
	<a href="api/getClassmates.jsp">8.获取某个班级所有名单接口</a>
	<br>
	<br>
	<a href="api/appLogin.jsp">10.手机登陆接口</a>
	<br>
	<br>
	<a href="api/searchUser.jsp">22.根据姓名和其它信息查询用户接口</a>
</body>
</html>
