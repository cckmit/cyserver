<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jslib/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	function serFrom() {
		var command = $('input[name=command]').val();
		var name = $('input[name=name]').val();
		var accountNum = $('input[name=accountNum]').val();
		var jsonStr = "{command:'" + command + "',content:{name:'" + name
				+ "',accountNum:'" + accountNum + "'}}"
		$('#jsonStr').text(jsonStr);
	}
</script>
</head>

<body>
	<table align="center">
		<tr>
			<td><label style="font-size: 50px;">获取baseInfoId接口</label> <br>
				<font color="red">说明：有accountNum以accountNum为准，无accountNum则使用name</font></td>
		</tr>
	</table>
	<table>
		<tr>
			<td align="right">command:</td>
			<td><input name="command" value="2" readonly="readonly">
			</td>
		</tr>
		<tr>
			<td align="right">accountNum:</td>
			<td><input name="accountNum" value="">
			</td>
		</tr>
		<tr>
			<td align="right">name:</td>
			<td><input name="name" value="">
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="jsonStr生成"
				onclick="serFrom()">
			</td>
		</tr>
	</table>
	<br>
	<form method="post"
		action="./userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action">
		<table>
			<tr>
				<td align="right">jsonStr:</td>
				<td><textarea rows="5" cols="50" id="jsonStr" name="jsonStr"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交">
				</td>
			</tr>
		</table>

	</form>
</body>
</html>
