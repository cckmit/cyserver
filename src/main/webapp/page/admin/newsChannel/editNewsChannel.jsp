<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	$(function() {
			var button = $("#pic_upload_button"), interval;
			new AjaxUpload(button, {
				action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
				name : 'upload',
				onSubmit : function(file, ext) {
					if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
						$.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
						return false;
					}
					$.messager.progress({
						text : '图片正在上传,请稍后....'
					});
				},
				onComplete : function(file, response) {
					$.messager.progress('close');
					var msg = $.parseJSON(response);
					if (msg.error == 0) {
						$('#pic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeFront(this)"></div><input type="hidden" id="channelIcon" name="newsChannel.channelIcon" value="'+msg.url+'"/></div>');
						$("#pic_upload_button").prop('disabled', 'disabled');
					} else {
						$.messager.alert('提示', msg.message, 'error');
					}
				}
			});
	});

	function removeFront(pic) {
		$(pic).parent().remove();
		$("#pic_upload_button").prop('disabled', false);
	}
	
	
	function submitForm($dialog, $grid, $pjq)
	{
		if($('#channelIcon').val()==undefined){
			parent.$.messager.alert('提示', '请上传频道图标！', 'info');
			return false;
		}
		if ($('form').form('validate'))
		{
			$.ajax({
				url : '${pageContext.request.contextPath}/newsChannel/newsChannelAction!update.action',
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
<form method="post" id="editNewsChannelForm">
	<table class="ta001">
		<input name="newsChannel.channelId" type="hidden" value="${newsChannel.channelId}">
		<tr>
			<th>
				频道名称
			</th>
			<td colspan="3">
				<input name="newsChannel.channelName" class="easyui-validatebox"
					style="width: 700px;"
					data-options="required:true,validType:'customRequired'"
					maxlength="1000" value="${newsChannel.channelName}"/>
			</td>
		</tr>
		<tr>
			<th>
				频道简介
			</th>
			<td colspan="3">
				<textarea name="newsChannel.channelRemark" rows="6" cols="95">${newsChannel.channelRemark}</textarea>
			</td>
		</tr>
		<tr>
			<th>
				频道图标上传
			</th>
			<td colspan="3">
				<c:choose>
					<c:when test="${newsChannel.channelIcon!=null and newsChannel.channelIcon!=''}">
						<input type="button" id="pic_upload_button" value="上传图片" disabled="disabled">
					</c:when>
					<c:otherwise>
						<input type="button" id="pic_upload_button" value="上传图片">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th>
				频道图标
			</th>
			<td colspan="3">
				<div id="pic">
					<c:if test="${newsChannel.channelIcon!=null and newsChannel.channelIcon!=''}">
						<div style="float:left;width:180px;"><img src="${newsChannel.channelIcon}" width="150px" height="150px"/><div class="bb001" onclick="removeFront(this)"></div><input type="hidden" id="channelIcon" name="newsChannel.channelIcon" value="${newsChannel.channelIcon}"/></div>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</form>
  </body>
</html>