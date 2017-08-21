<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        function submitForm($dialog, $grid, $pjq)
		{		
			if ($('form').form('validate'))
			{
				if($('#content').val().trim()==''){
					parent.$.messager.alert('提示', '请输入内容', 'error');
					return false;
				}			
				
				$.ajax({
					url : '${pageContext.request.contextPath}/serv/servAction!saveReplyx.action',
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

<body class="easyui-layout" data-options="fit:true,border:false">
<form method="post">
<input name="serv.id" type="hidden" id="serviceId" value="${param.id}">

<table class="ta001">
<tr>
	<th>
		内容
	</th>
	<td colspan="3">
		<textarea id="content" name="serv.content"
			style="width: 700px; height: 300px;"></textarea>
		</td>
	</tr>
</table>

</form>
</body>
</html>