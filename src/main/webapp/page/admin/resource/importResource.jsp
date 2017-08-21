<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String url = (String)getServletContext().getAttribute("URL_DOMAIN"); %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
		$(function()
				{
			var button = $("#file_upload_button"), interval;
			new AjaxUpload(button, {
				action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action?dir=file',
				name : 'upload',
				onSubmit : function(file, ext)
				{
					if (!(ext && /^(xls|xlsx)$/.test(ext)))
					{
						$.messager.alert('提示', '您上传的文件格式不对，请重新选择！', 'info');
						return false;
					}
					$.messager.progress({
						text : '文件正在上传,请稍后....'
					});
				},
				onComplete : function(file, response)
				{
					$.messager.progress('close');
					if (response.indexOf('您还没有登录或登录已超时，请重新登录！') == 0)
					{
						$.messager.alert('提示', '您还没有登录或登录已超时，请重新登录！', 'error');
					} else
					{
						var msg = $.parseJSON(response);
						if (msg.error == 0)
						{
							$('#file').html(
									"<div id='div'><a href='"+msg.url+"'>" + msg.url
											+ "</a>&nbsp;<img id='img' class='iconImg ext-icon-cross' onclick='clearUrl()'/><input name='url' id='url' type='hidden' value='"+msg.url+"'/></div>");
							$('#file_upload_button').prop('disabled', 'disabled')
						} else
						{
							$.messager.alert('提示', msg.message, 'error');
						}
					}
				}
			});
		});
		function clearUrl()
		{
			$('#div').remove();
			$('#file_upload_button').prop('disabled', false);
		}
		function submitForm($dialog,$grid, $pjq) {
			if($('#url').val()==undefined){
				parent.parent.$.messager.alert('提示','请上传文件','error');
				return false;
			}
			if ($('form').form('validate')) {
				$.ajax({
					url : '${pageContext.request.contextPath}/resource/resourceAction!importData.action',
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$grid.treegrid('reload');
							$dialog.dialog('destroy');
							$pjq.messager.alert('提示', "数据导入成功", 'info');
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
		<form id="deptImport" method="post" enctype="multipart/form-data">
			<input name="dept.parentId" type="hidden" value="${param.id}">
			<fieldset>
				<legend>
					菜单导入
				</legend>
				<table class="ta001">
				
					<tr>
						<th>
							文件上传
						</th>
						<td>
							<input type="button" id="file_upload_button" value="文件上传">
						</td>
					</tr>
					<tr>
						<th>
							上传的文件
						</th>
						<td>
							<span id="file"></span>
						</td>
					</tr>
					<tr>
						<th>
							注:
						</th>
						<td>
							<span style="color: red;">菜单导入,会将已有的所有菜单和权限给删除</span>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
