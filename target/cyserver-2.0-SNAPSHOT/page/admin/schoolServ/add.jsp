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
	
	var actionName = "schoolServAction";
	var actionUrl = "${pageContext.request.contextPath}/mobile/schoolServ/";
	var actionFullPath = actionUrl + actionName;
	function submitForm($dialog, $grid, $pjq)
	{
		if ($('form').form('validate'))
		{
			$.ajax({
				url : actionFullPath + '!save.action',
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
	
	
	
	$(function() 
	{
		uploadPic("#servicePic_upload_button", "formData.servicePic", "#servicePic");
		
	});
	
	
	function uploadPic(upload_button_name, picName, picDivName)
	{
		var button = $(upload_button_name), interval;
		new AjaxUpload(button, 
		{
			action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
			name : 'upload',
			onSubmit : function(file, ext) 
			{
				if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) 
				{
					$.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
					return false;
				}
				$.messager.progress({text : '图片正在上传,请稍后....'});
			},
			onComplete : function(file, response) 
			{
				$.messager.progress('close');
				var resp = $.parseJSON(response);
				
				if (resp.error == 0) 
				{
					$(picDivName).append(
						'<div style="float:left;width:180px;">'+
						'<img src="'+resp.url+'" width="150px" height="150px"/>'+
						'<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
						'</div>'+
						'<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
					);

					
					$(upload_button_name).prop('disabled', 'disabled');
				} 
				else 
				{
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
	
	}

	function removePic(pic, upload_button_name) 
	{
		$(pic).parent().remove();
		$(upload_button_name).prop('disabled', false);
	}
	
	</script>

  </head>
  
  <body>
<form method="post" id="addForm">
	<%--<input type="hidden" id="systemService" name="formData.systemService" value="0" >--%>
	<fieldset>
    <legend>
            服务配置信息
    </legend>

	<table class="ta001">
		<tr>
			<th>
				服务名称
			</th>
			<td>
				<input type="text" id="serviceName" name="formData.serviceName">
				<input type="text" id="deptId" name="formData.deptId" value="${formData.deptId}" style="display:none" >
			</td>
			<th>
				服务状态
			</th>
			<td>		
				<select id="provideService" name="formData.provideService" class="easyui-combobox" style="width:155px"
					data-options="required:true, editable:false"
				>
				<option value="1">开启</option>
				<option value="0">停止</option>
				</select>

			</td>
		</tr>
		
		<tr>
			<th>
				服务的URL
			</th>
			<td colspan="3">
				<input type="text" id="serviceUrl" name="formData.serviceUrl" style="width: 75%" >
			</td>
		</tr>
		<tr>
			<th>
				服务类型
			</th>
			<td>
				<select id="systemService" name="formData.systemService" class="easyui-combobox" style="width:155px"
						data-options="required:true, editable:false" <c:if test="${formData.deptId!='0'}"> readonly="readonly"</c:if>
				>
					<option value="0"<c:if test="${formData.deptId!='0'}"> selected</c:if>>自定义服务</option>
					<option value="1">系统服务</option>
				</select>

			</td>
			<th>
				排序
			</th>
			<td colspan="3">
				<input type="text" id="sort" name="formData.sort" >
			</td>
		</tr>


		<tr>
			<th>
				分组标识
			</th>
			<td>
				<input type="text" id="group_name" name="formData.group_name" value="${formData.group_name}">
			</td>
			</td>



			<th>
				分组排序
			</th>
			<td>
				<input type="text" id="group_sort" name="formData.group_sort" value="${formData.group_sort}">
			</td>
		</tr>

		<tr>
			<th>
				查看是否需要认证
			</th>
			<td>
				<select id="need_authentication" name="formData.need_authentication" class="easyui-combobox" style="width:155px"
						data-options="required:true">
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
			</td>
			<th colspan="2" />
		</tr>





	</table>
	</fieldset>
	
	
	
	<br>
	
	<fieldset>
    <legend>
            图片信息
    </legend>

	<table class="ta001">
		
		
		
		<tr>
			<th>
				服务的图片
			</th>
			<td>
				<input type="button" id="servicePic_upload_button" value="服务的图片">
			</td>
			<td>
				<div id="servicePic"></div>
			</td>
			
		</tr>
		
		

	</table>
	</fieldset>
</form>
  </body>
</html>