<%@page import="com.cy.system.Global"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userName = WebUtil.isEmpty(request.getParameter("userName"))?"":request.getParameter("userName") ;
String schoolId = WebUtil.isEmpty(request.getParameter("schoolId"))?"":request.getParameter("schoolId") ;
String departId = WebUtil.isEmpty(request.getParameter("departId"))?"":request.getParameter("departId") ;
String classId = WebUtil.isEmpty(request.getParameter("classId"))?"":request.getParameter("classId") ;
String majorId = WebUtil.isEmpty(request.getParameter("majorId"))?"":request.getParameter("majorId") ;
String email_account = Global.email_account;
String email_password= Global.email_password;

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
	<script type="text/javascript" src="<%=path %>/jslib/valiDate.js"></script>
	<jsp:include page="../../../inc.jsp"></jsp:include>
	<script type="text/javascript">
	var userName = "<%=userName %>";
	var schoolId = "<%=schoolId %>";
	var departId = "<%=departId %>";
	var classId = "<%=classId %>";
	var majorId = "<%=majorId %>";
	//通过ajax查询
	var params = "userInfo.userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&userInfo.majorId="+majorId;
	var url ="<%=path%>/userInfo/userInfoAction!doNotNeedSecurity_getAllUserEmailList_dataGrid.action";
	$.ajax({
		type: "post",
		url:url,
		data: params,
		dataType: "json",
		success: function (data) {
	    	// Play with returned data in JSON format
	    	var list = data;
	    	var phoneNumText = "";

	    	for(var i=0;i<list.length;i++){
	    		var obj = list[i];
	    		if(list.length == 1){
	    			phoneNumText = phoneNumText + obj.email;
	    		}else if(list.length > 1 && i<list.length-1 ){
	    			phoneNumText = phoneNumText + obj.email+",";
	    		}else{
	    			phoneNumText = phoneNumText + obj.email;
	    		}
	    	}
	    	$("#toAddress").val(phoneNumText);
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
	
	KindEditor.ready(function(K) {
		K.create('#content1',{
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
	
	
	var fNumber=0;
	
	$(function(){
		var emailType=$("input[name='emailType']:checked").val();
		$("#emailType1").val(emailType);
		if(emailType=='普通邮件'){
			if(!$("#template").is(":hidden")) 
			{ 
				$("#template").hide();
			}
			if($("#general").is(":hidden")) 
			{ 
				$("#general").show();
			}
		}else{
			if($("#template").is(":hidden")) 
			{ 
				$("#template").show();
			}
			if(!$("#general").is(":hidden")) 
			{ 
				$("#general").hide();
			}
		}
		
		var button = $("#file_upload_button"), interval;
		new AjaxUpload(button, {
			action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload2Email.action',
			name : 'upload',
			onSubmit : function(file, ext) {
				$.messager.progress({
					text : '文件正在上传,请稍后....'
				});
			},
			onComplete : function(file, response) {
				$.messager.progress('close');
				var msg = $.parseJSON(response);
				if (msg.error == 0) {
					fNumber++;
					$('#ffile').append("<div id='fj"+fNumber+"'><input name='fj' style='width:725px;' readonly='readonly' value='"+msg.url+"'/>&nbsp;<a href='javascript:void(0)' onclick='removefj("+fNumber+")'>删除</a><br/></div>");
				} else {
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
	})
	
	function removefj(fNumber){
		$('#fj'+fNumber).remove();
	}
	
	
	
	
	
	/**--发送邮件--**/
	function submitForm()
	{
		var emailType=$("input[name='emailType']:checked").val();
		var phoneNumText = $("#toAddress").val();
		if(Validate.isNull(phoneNumText)){
			parent.$.messager.alert('错误', '收件人不能为空', 'error');
			return false;
		}
		if(emailType=='普通邮件'){
    		if($('#content1').val()==''){
    			parent.$.messager.alert('错误', '请填写邮件内容', 'error');
    			return false;
    		}
    	}else{
    		if($('#emailTemplate').combobox('getValue')==''){
    			parent.$.messager.alert('错误', '请选择邮件模板', 'error');
    			return false;
    		}
    		var flag= true
    		$("input[name='emailParam']").each(function(){
				if ($(this).val() == '')
					{
						flag = false;
					}
				});
				if (!flag)
				{
					parent.$.messager.alert('错误', '请填写邮件模板参数', 'error');
					return false;
				}
		}
		
		var phoneList = phoneNumText.split(",");
		for(var i=0;i<phoneList.length;i++){
			var phone = phoneList[i];
			if(!Validate.isEmail(phone)){
				parent.$.messager.alert('错误', phone+'邮箱格式非法', 'error');
				return;
			}
		}
		
		if($('#ccAddress')[0]!=undefined&&!Validate.isNull($('#ccAddress').val())){
			var phoneList = $('#ccAddress').val().split(",");
			for(var i=0;i<phoneList.length;i++){
				var phone = phoneList[i];
				if(!Validate.isEmail(phone)){
					parent.$.messager.alert('错误', '抄送'+phone+'邮箱格式非法', 'error');
					return;
				}
			}
		}
		
		if($('#bccAddress')[0]!=undefined&&!Validate.isNull($('#bccAddress').val())){
			var phoneList = $('#bccAddress').val().split(",");
			for(var i=0;i<phoneList.length;i++){
				var phone = phoneList[i];
				if(!Validate.isEmail(phone)){
					parent.$.messager.alert('错误', '密送'+phone+'邮箱格式非法', 'error');
					return;
				}
			}
		}
		
		if ($('form').form('validate'))
		{
			$.ajax({
				url : '${pageContext.request.contextPath}/page/admin/email/emailAction!saveOld.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result)
				{
					if (result.success)
					{
						parent.$.messager.alert('提示', result.msg, 'info');
					} else
					{
						parent.$.messager.alert('提示', result.msg, 'error');
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
	
	function changeType()
	{
		var emailType = $('input[name="emailType"]:checked').val();
		$("#emailType1").val(emailType);
		if (emailType == '普通邮件')
		{
			if (!$("#template").is(":hidden"))
			{
				$("#template").hide();
			}
			if ($("#general").is(":hidden"))
			{
				$("#general").show();
			}
			$('#emailTemplate').combobox('clear');
			KindEditor.ready(function(K) {
				K.insertHtml('#content', '');
			});
			removeParam();
		} else
		{
			if ($("#template").is(":hidden"))
			{
				$("#template").show();
			}
			if (!$("#general").is(":hidden"))
			{
				$("#general").hide();
			}
			$('#comment').val('');
		}
	}
	
	String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g, "");};
	function addcc(){
		$(".ta001 tr:eq(3)").after("<tr id='cctr'><th>抄送</th><td colspan='3'><textarea id='ccAddress' name='ccAddress' style='width:725px;height: 100px'></textarea></td></tr>");
		$('#cca').remove();
		$('#ccdiv').append('<a id="cca" href="javascript:void(0)" onclick="removecc()">删除抄送</a>');
	}
	
	function addbcc(){
		if($('#cctr')[0]!=undefined){
			$(".ta001 tr:eq(4)").after("<tr id='bcctr'><th>密送</th><td colspan='3'><textarea id='bccAddress' name='bccAddress' style='width:725px;height: 100px'></textarea></td></tr>");
		}else{
			$(".ta001 tr:eq(3)").after("<tr id='bcctr'><th>密送</th><td colspan='3'><textarea id='bccAddress' name='bccAddress' style='width:725px;height: 100px'></textarea></td></tr>");
		}
		$('#bcca').remove();
		$('#bccdiv').append('<a id="bcca" href="javascript:void(0)" onclick="removebcc()">删除密送</a>');
	}
	
	function removecc(){
		$('#cca').remove();
		$('#cctr').remove();
		$('#ccdiv').append('<a id="cca" href="javascript:void(0)" onclick="addcc()">添加抄送</a>');
	}
	
	function removebcc(){
		$('#bcca').remove();
		$('#bcctr').remove();
		$('#bccdiv').append('<a id="bcca" href="javascript:void(0)" onclick="addbcc()">添加密送</a>');
	}
	
	function addParam(number)
	{
		removeParam();
		if ($("#templateContent").is(":hidden"))
		{
			$("#templateContent").show();
		}
		if (number != 0)
		{
			if ($("#templateParam").is(":hidden"))
			{
				$("#templateParam").show();
			}
			text = '';
			for ( var i = 0; i < number; i++)
			{
				text += "<input name='emailParam' type='text' value=''/><br/><br/>"
			}
			$('#emailParam').html(text);
		} else
		{
			if (!$("#templateParam").is(":hidden"))
			{
				$("#templateParam").hide();
			}
		}
	}
	function removeParam()
	{
		if (!$("#templateContent").is(":hidden"))
		{
			$("#templateContent").hide();
		}
		if (!$("#templateParam").is(":hidden"))
		{
			$("#templateParam").hide();
		}
		$("input[name='emailParam']").remove();
	}
	</script>
  </head>
  
  <body>

<form method="post" id="addForm">
	<fieldset>
				<legend>
					基本信息
				</legend>
	<table class="ta001">
		<tr>
			<th>
				主题
			</th>
			<td >
				<input name="email.emailSubject" class="easyui-validatebox"
					style="width: 720px;"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
		</tr>
		<tr>
			<th>
				发件人
			</th>
			<td > 
				<input name="email.fromAddress" class="easyui-validatebox"
					style="width: 720px;"
					data-options="required:true"
					maxlength="100" value="<%=email_account%>" readonly="readonly"/>
			</td>
		</tr>
		<%-- <tr>
			<th>
				密码
			</th>
			<td >
				<input name="fromPassword" class="easyui-validatebox" type="password"
					style="width: 720px;"
					data-options="required:true,validType:'customRequired'"
					maxlength="100"  value="<%=email_password%>"/>
			</td>
		</tr> --%>
		<tr>
			<th>
				收件人
				<div id="ccdiv">
					<a id="cca" href="javascript:void(0)" onclick="addcc()">添加抄送</a>
				</div>
				<div id="bccdiv">
					<a id="bcca" href="javascript:void(0)" onclick="addbcc()">添加密送</a>
				</div>
			</th>
			<td >
				<textarea id="toAddress" name="email.toAddress"  style="width:725px;height: 100px"></textarea>
				<br>
				<font color="red">（多地址逗号分隔）</font>
			</td>
		</tr>
		<tr >
			<th >
						邮件类型：
					</th>
					<td >
						<input name="emailType" type="radio" value="普通邮件" checked="checked" onchange="changeType()" style="width: 15px;"/>普通邮件
						<input name="emailType" type="radio" value="模板邮件" onchange="changeType()" style="width: 15px;"/>模板邮件
						<input name="emailType1" id="emailType1" type="hidden">
					</td>
				</tr>
		<tr id="template">
					<th >
						邮件模板：
					</th>
					<td >
							<input id="emailTemplate" class="easyui-combobox"
								data-options="editable:false,valueField:'templateId',textField:'templateName',url:'${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!doNotNeedSecurity_getAllTemplate.action'
	    					,onSelect:function(rec){
	    						KindEditor.ready(function(K) {
									K.html('#content', rec.templateContent);
								});
	    						addParam(rec.templateType);
	    					}
	    					,prompt:'--请选择--',
	    					icons:[{
								iconCls:'icon-clear',
								handler: function(e){
									$('#emailTemplate').combobox('clear');
									KindEditor.ready(function(K) {
										K.html('#content', '');
									});
									removeParam();
								}
							}]
	    					">
					</td>
				</tr>
		<tr id="templateContent" style="display: none;">
			<th>
				模板内容
			</th>
			<td>
				<textarea id="content" rows="20" cols="100" style="width: 750px;height: 350px;"
					name="emailTemplateContent"></textarea>
			</td>
		</tr>
		<tr  id="templateParam" style="display: none">
					<th>
						短信参数：
					</th>
					<td >
						<div id="emailParam" style="margin-left: 5px; margin-bottom: 5px; margin-top: 5px;">

						</div>
					</td>
				</tr>
				<tr id="general">
					<th >
						邮件内容：
					</th>
					<td height="80" >
							<textarea id="content1" rows="20" cols="100" name="email.emailText"></textarea>
					</td>
				</tr>
		<tr>
			<th>
				附件上传
			</th>
			<td>
				<input type="button" id="file_upload_button" value="文件上传">
			</td>
		</tr>
		<tr>
			<th>
				附件
			</th>
			<td>
				<div id="ffile"></div>
			</td>
		</tr>
	</table>
	</fieldset>
</form>
  </body>
</html>