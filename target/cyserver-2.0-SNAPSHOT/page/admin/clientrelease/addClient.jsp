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
<!--
	$(function(){
		var button = $("#apk_upload_button"), interval; 
		new AjaxUpload(button, {  
	        action: '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_apkFileUpload.action',  
	        name: 'upload',
	        data:{'dir':'release_apk_file'},
	        onSubmit: function(file, ext) {  
	            if (!(ext && /^(apk)$/.test(ext))) {  
	            	$.messager.alert('提示','您上传的文件格式不对，请重新选择！','info');
	                return false;  
	            }
	            $.messager.progress({text : '文件正在上传,请稍后....'});
	        },  
	        onComplete: function(file, response) { 
	        	 $.messager.progress('close');
	        	 if(response.indexOf('您还没有登录或登录已超时，请重新登录！')==0){
						$.messager.alert('提示', '您还没有登录或登录已超时，请重新登录！', 'error');
				}else{
		        	 var msg = $.parseJSON(response);
		        	 if(msg.error==0){
		        		 $('#clientUrl').prop('value',msg.url);
		        		 $('#apk_upload_button').prop('disabled','disabled');
		        	 }else{
		        		 $.messager.alert('提示',msg.message,'error');
		        	 }
				}
	        }  
	    });
	});
	
	function clearClientUrl(){
		$('#clientUrl').prop('value','');
		$('#apk_upload_button').prop('disabled',false);
	}
	
	
	function submitForm($dialog, $grid, $pjq)
	{
		if($('#clientUrl').val()==''){
				  			$.messager.alert('提示','请上传手机客户端文件','info');
				  			return false;
				  		}
				  		
		else if ($('form').form('validate'))
		{
			$.ajax({
				url : '${pageContext.request.contextPath}/client/clientAction!addClient.action',
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
//-->
</script>

</head>
  
  <body>
<form id="addclient" method="post" action="">
	<table cellpadding="0" cellspacing="0" class="ta001">
		<tr>
			<td style="width: 120px;" align="right">
				版本号：
			</td>
			<td>
				<input name="clientModel.version" class="easyui-validatebox"
					data-options="required:'true',validType:'customRequired'" />
			</td>
		</tr>
		<tr>
			<td align="right">
				手机客户端下载地址：
			</th>
			<td>
				<input name="clientModel.url" class="easyui-validatebox" id="clientUrl" style="width: 400px;" readonly="readonly"/>&nbsp;<a href="javascript:void(0)" onclick="clearClientUrl()">清空</a>
			</td>
		</tr>
		<tr>
			<td align="right">
				手机客户端文件上传：
			</th>
			<td>
				<input type="button" id="apk_upload_button" value="上传文件">
			</td>
		</tr>
	</table>
</form>
  </body>
</html>