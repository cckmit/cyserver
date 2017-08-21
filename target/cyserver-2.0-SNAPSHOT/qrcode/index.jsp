<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>二维码地址</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  
  
  <body>
    
    <div align="center">
    用户信息<br>
    <table>
    	<tr>
    		<td>姓名:</td>
    		<td>${item.name }</td>
    	</tr>
    	<tr>
    		<td>性别:</td>
    		<td>${item.sex }</td>
    	</tr>
    </table>
    <table border="1">
    	<tr>
    		<td>学校</td>
    		<td>院系</td>
    		<td>年级</td>
    		<td>班级</td>
    		<td>专业</td>
    	</tr>
    	
    	<c:forEach var="model" items="${list}">
    	<tr>
    		<td>${model.schoolName }</td>
    		<td>${model.departName}</td>
    		<td>${model.gradeName }</td>
    		<td>${model.className }</td>
    		<td>${model.majorName }</td>
    	</tr>
    	</c:forEach>
    </table>
    </div>
    
  </body>
</html>
