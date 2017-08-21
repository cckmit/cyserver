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
	function submitForm($dialog, $grid, $pjq)
	{
		$('#toAddress').combogrid('setValue',$('#toAddress').combogrid('getText'));
		if ($('form').form('validate') && checkMail($('#toAddress').combogrid('getText')))
		{
			
			$.ajax({
				url : '${pageContext.request.contextPath}/page/admin/email/emailAction!update.action',
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
	
	
	function changeEmailTemplate(templateValue)
	{
		if(templateValue != null)
		{
			if(templateValue != '0')
			{
				//alert('${pageContext.request.contextPath}/page/emailTemplate/emailTemplateAction!doNotNeedSecurity_getTemplateById.action?id=' + templateValue);
				
				$.ajax({
				url : "${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!doNotNeedSecurity_getTemplateById.action?id=" + templateValue,
		        type: "POST",
	
		        success: function(data) 
		        {
		        	var json = eval('(' + data + ')'); 
		        	var templateContent = json.templateContent;
					$("#emailText").val(templateContent);
		        	$("#emailText").attr("readonly","readonly");
		        }
		    	});
		    	
			}
			else
			{
				$("#emailText").val("");
				$("#emailText").removeAttr("readonly");
			}
		}
	}
	
	
	function changeImmediate(obj)
	{
		if(obj != null)
		{
			if(obj.value == '1')
			{
				$("#sendDateTimeTr").show();
			}
			else
			{
				$("#sendDateTimeTr").hide();
			}
		}
	}
	
	function isMail(mail)
    {
    	return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
    }


	function checkMail(emailAddress)
	{
		if(emailAddress != null)
		{
			if(emailAddress.length == 0)
			{
				parent.$.messager.alert('错误', '邮件地址不能是空', 'error');
				return false;
			}
	  		var emailArray = emailAddress.split(",");
			for(var i = 0; i < emailArray.length; i++)
	  		{
	  			if(!isMail(emailArray[i].trim()))
	  			{
	  				parent.$.messager.alert('错误', '无效的邮件格式', 'error');
					return false;
	  			}
	  		}
		}
	
		return true;
	}
	
	String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g, "");};
	
	</script>

  </head>
  
  <body>
<form method="post" id="editForm">

	<input name="email.emailId" type="hidden" value="${email.emailId}">
	<table class="ta001">
		<tr>
			<th>
				标题
			</th>
			<td colspan="3">
				<input name="email.emailSubject" class="easyui-validatebox"
					style="width: 720px;"
					data-options="required:true,validType:'customRequired'"
					value="${email.emailSubject}"
					maxlength="100" />
			</td>
		</tr>
		
		<tr>
			<th>
				收件人
			</th>
			<td colspan="3">
				
				<select id="toAddress" name="email.toAddress"  class="easyui-combogrid" style="width:725px" data-options=" 
						required:true,
						validType:'customRequired',
						multiple: true,
						idField: 'email',
						textField: 'email',
						url: '${pageContext.request.contextPath}/page/admin/email/emailAction!doNotNeedSecurity_getAllUserList.action',
						method: 'get',
						columns: [[
							{field:'user_id',checkbox:true},
							{field:'user_name',title:'用户姓名'},
							{field:'birthday',title:'生日'},
							{field:'full_name',title:'所属'},
							{field:'email',title:'邮箱'}
						]],
						fit : true,
						border : false,
						fitColumns : true,
						striped : true,
						rownumbers : true,
						pagination : true,
						editable:true,
						onBeforeLoad : function(param) {
							parent.$.messager.progress({
								text : '数据加载中....'
							});
						},
						onLoadSuccess : function(data) {
							parent.$.messager.progress('close');
							$('#toAddress').combogrid('setValues', ${email.toAddressStr});
						}
						
					">
				</select><font color="red">（多地址逗号分隔）</font>
				
			</td>
		</tr>
		<tr>
			<th>
				邮件模板
			</th>
			<td colspan="3">
				<select id="emailTemplateId" name="email.emailTemplateId" class="easyui-combobox" style="width:150px" data-options=" 

						url: '${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!doNotNeedSecurity_getAllTemplate.action',
						valueField: 'templateId',
						idField: 'templateId',
    					textField: 'templateName',
    					editable: false,
						onLoadSuccess:function(data){
							$('#emailTemplateId').combobox('setValues', ${email.emailTemplateIdStr});
						}
					">
				</select>
				<script type="text/javascript">
				
				$('#emailTemplateId').combobox({
					onChange: function(newValue, oldValue){
						changeEmailTemplate(newValue);
					}
				});
				
				</script>
			</td>
		</tr>
		<tr>
			<th>
				邮件内容
			</th>
			<td colspan="3">
				<textarea id="emailText" rows="20" cols="100"
					class="easyui-validatebox" data-options="required:true,validType:'customRequired'"
					name="email.emailText">${email.emailText}</textarea>
			</td>
		</tr>
		<tr>
			<th>
				发送方式
			</th>
			<td colspan="3">
				<select id="immediate" name="email.immediate" onchange="changeImmediate(this)" >
				
				<option value="0"<c:if test="${email.immediate==0}"> selected</c:if>>立即发送</option>
				<option value="1"<c:if test="${email.immediate==1}"> selected</c:if>>定时发送</option>
				
				</select>
			</td>
		</tr>
		
		<tr id="sendDateTimeTr" style="display: <c:if test="${email.immediate==0}">none</c:if>">
			<th>
				发送日期
			</th>
			<td colspan="3">
				<input id="sendDateTime" name="email.sendDateTime" class="easyui-datebox" data-options="editable:false" 
				value="<fmt:formatDate value="${email.sendDateTime}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />"/>
			</td>
		</tr>
		
		
	</table>
</form>
  </body>
</html>
