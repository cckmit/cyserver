<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String certificateImageUrl = request.getParameter("certificateImageUrl");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>查看证书</title>

		<style>
			html{
				height: 100%;
			}
			body{
				background-color:#fff;
				height: 100%;
			}
			.img_bg {
				height: 100%;
				width: 100%;
				padding: 0;
				text-align: center;
			}


		</style>
	</head>

<body>
 	<div class="img_bg">
		<img src="<%=certificateImageUrl%>"  style="height:100%"/>
	</div>
</body>

</html>