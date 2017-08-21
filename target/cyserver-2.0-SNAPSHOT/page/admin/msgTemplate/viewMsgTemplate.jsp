<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<form method="post" class="form">
			<fieldset>
				<legend>
					短信模板
				</legend>
				<table class="ta001">
					<tr>
						<th>
							模板名称
						</th>
						<td colspan="3">
							<input name="msgTemplate.msgTemplateTitle" disabled="disabled" class="easyui-validatebox"  data-options="required:true,validType:'customRequired'" maxlength="1000" value="${msgTemplate.msgTemplateTitle}"/>
						</td>
					</tr>
					<tr>
						<th>
							短信内容
						</th>
						<td colspan="3">
							<textarea name="msgTemplate.msgTemplateContent" disabled="disabled" rows="6" cols="70" class="easyui-validatebox" data-options="required:true,validType:'customRequired'">${msgTemplate.msgTemplateContent}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
