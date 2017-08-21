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
		<script type="text/javascript">
	function submitForm($dialog, $grid, $pjq)
	{
		if ($('form').form('validate'))
		{
			$.ajax({
				url : '${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!save.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result)
				{
					if (result.success)
					{
						$grid.datagrid('reload');
						$dialog.dialog('destroy');
						$pjq.messager.alert('提示', result.msg, 'info');
					} else
					{
						$pjq.messager.alert('提示', result.msg, 'error');
					}
				},
				beforeSend : function()
				{
					parent.$.messager.progress({
						text : '数据提交中....'
					});
				},
				complete : function()
				{
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
					短信模板
				</legend>
				<table class="ta001">
					<tr>
						<th>
							模板名称
						</th>
						<td colspan="3">
							<input name="msgTemplate.msgTemplateTitle" class="easyui-validatebox"  data-options="required:true,validType:'customRequired'" maxlength="1000" />
						</td>
					</tr>

					<tr>
						<th>
							审核状态
						</th>
						<td colspan="3">
						<%--	<input name="msgTemplate.msgTemplateStatus" class="easyui-combobox" style="width: 150px;" value=""
								   data-options="editable:false,required:true,
							        valueField: 'msgTemplateStatus',
							        textField: 'msgTemplateStatus'" />
--%>
							<select id="status" name="msgTemplate.msgTemplateStatus"   class="easyui-combobox" >
								<option value="10">待审核</option>
								<option value="20">审核通过</option>
								<option value="30">审核未通过</option>
							</select>
						</td>
					</tr>

					<tr>
						<th>
							短信内容
						</th>
						<td colspan="3">
							<textarea name="msgTemplate.msgTemplateContent" rows="6" cols="70" class="easyui-validatebox" data-options="required:true,validType:'customRequired'"></textarea>
							<br>
							<em style="color: red;">注意:请按照示例编辑短信模板，示例:文字,文字\${@}文字,\${@}文字</em>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
