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
			var editor=K.create('#content',{
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
			editor.readonly();
		});
		$(function() {
			$.ajax({
				url : '${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!getById.action?id='+${param.id},
				dataType : 'json',
				success : function(result) {
					if (result.templateId != undefined) {
						$('form').form('load', {
							'emailTemplate.templateName' : result.templateName,
							'emailTemplate.templateType' : result.templateType
						});
						KindEditor.ready(function(K) {
							K.insertHtml('#content', result.templateContent);
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
		});
	</script>
  </head>
  
  <body>
	<form method="post" id="viewForm">
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
							style="width: 650px;"
							data-options="required:true,validType:'customRequired'"
							value=""
							maxlength="100" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<th>
						模板内容
					</th>
					<td colspan="3">
						<textarea id="content" rows="20" cols="100"
							data-options="required:true,validType:'customRequired'"
							name="emailTemplate.templateContent"  disabled="disabled"></textarea>
					</td>
				</tr>
				<tr>
					<th>
						参数个数
					</th>
					<td colspan="3">
						<input name="emailTemplate.templateType" class="easyui-validatebox"
							style="width: 650px;"
							data-options="required:true,validType:'customRequired'"
							value=""
							maxlength="100" disabled="disabled" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
  </body>
</html>
