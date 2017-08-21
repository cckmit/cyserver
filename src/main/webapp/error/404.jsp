<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<style type="text/css">
.error {
	width: 745px;
	height: 333px;
	display: block;
	margin-right: auto;
	margin-left: auto;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -373px;
	margin-top: -167px;
}

.error img {
	width: 745px;
	height: 333px;
}
</style>
	</head>
	<body>
		<div class="error">
			<img src="${pageContext.request.contextPath}/images/404.jpg" width="745" height="333">
		</div>
	</body>
</html>