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
	
	<script type="text/javascript">
		KindEditor.ready(function(K) {
			K.create('#content',{
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link','preview','fullscreen'
				 ],
		    	 uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
		         afterChange:function(){
			        	this.sync();
			        }
		    });
		});
		function submitForm($dialog, $grid, $pjq)
		{
			if ($('form').form('validate'))
			{
				$.ajax({
					url : '${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!save.action',
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
		}
	</script>

  </head>
  
  <body>
	<form method="post" id="addForm">
		<fieldset>
			<legend>
				邮件模板
			</legend>
			<table class="ta001">
				<tr>
					<th>
						模板名称
					</th>
					<td colspan="3">
						<input name="emailTemplate.templateName" class="easyui-validatebox"
							style="width: 720px;"
							data-options="required:true,validType:'customRequired'"
							maxlength="100" />
					</td>
				</tr>
				<tr>
					<th>
						模板内容
					</th>
					<td colspan="3">
						<textarea id="content" rows="20" cols="120"
							data-options="required:true,validType:'customRequired'"
							name="emailTemplate.templateContent"></textarea>
							<br>
							<em style="color: red;">注意:请按照示例编辑短信模板，示例:文字,文字\${@}文字,\${@}文字</em>
							<%-- <em style="color: red;">注意:请按照示例编辑短信模板，示例:文字,文字\${0}文字,\${1}文字</em> --%>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
  </body>
</html>