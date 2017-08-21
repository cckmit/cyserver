<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
		<script type="text/javascript">
		$(function() {
			if ($('#roleId').val() > 0) {
				$.ajax({
					url : '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getById.action',
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.roleId != undefined) {
							$('form').form('load', {
								'role.roleName' : result.roleName,
								'role.roleId' : result.roleId,
								'role.flag' : result.flag
							});
						}
					},
					beforeSend:function(){
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					complete:function(){
						parent.$.messager.progress('close');
					}
				});
			}
		});
		var submitForm = function($dialog, $grid, $pjq) {
			if ($('form').form('validate')) {
				var url;
				if ($('#roleId').val() > 0) {
					url = '${pageContext.request.contextPath}/role/roleAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/role/roleAction!save.action';
				}
				$.ajax({
					url : url,
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$grid.datagrid('reload');
							$dialog.dialog('destroy');
							$pjq.messager.alert('提示', result.msg, 'info');
						} else {
							$pjq.messager.alert('提示', result.msg, 'error');
						}
					},
					beforeSend:function(){
						parent.$.messager.progress({
							text : '数据提交中....'
						});
					},
					complete:function(){
						parent.$.messager.progress('close');
					}
				});
			}
		};
		</script>
	</head>

	<body>
		<form method="post" class="form">
			<fieldset>
				<legend>
					角色基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							角色名称
						</th>
						<td>
							<input name="role.roleName" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'" />
							<input name="role.roleId" type="hidden" id="roleId"
								value="${param.id}">
							<input name="role.flag" type="hidden" value="0" />
						</td>
					</tr>
					<tr style="display: none">
						<th>
							系统
						</th>
						<td>
							<%--<select class="easyui-combobox" data-options="editable:false" name="role.flag" style="width: 150px;">
								<option value="0">WEB后台系统</option>
								<option value="1">校友会系统</option>
							</select>--%>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
