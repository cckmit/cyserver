<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

  </head>
  
  <body>
<form method="post" id="checkMessageBoardForm">
	<table class="ta001">
		<tr>
			<th>
				备注
			</th>
			<td colspan="3">
				<input type="hidden" name="id" value="${param.id}">
				<input type="hidden" name="checkStatus" value="${param.status}">
				<textarea rows="7" cols="80" name="checkRemark"></textarea>
			</td>
		</tr>
	</table>
</form>
  </body>
</html>
