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
				url : '${pageContext.request.contextPath}/page/admin/email/emailAction!getById.action?id='+${param.id},
				dataType : 'json',
				success : function(result) {
					if (result.emailId != undefined) {
						$('form').form('load', {
							'email.emailSubject':result.emailSubject,
							'email.fromAddress':result.fromAddress,
							'email.toAddress':result.toAddress,
							'email.ccAddress':result.ccAddress,
							'email.bccAddress':result.bccAddress,
							'email.createDateTime':result.createDateTime
						});
						KindEditor.ready(function(K) {
							K.insertHtml('#content', result.emailText);
						});
						if(result.immediate!=undefined){
							var text="";
							var array=result.immediate.split(',');
							for(var i=0;i<array.length;i++){
								text+="<a href='"+array[i]+"' target='_blank'>"+array[i]+"</a><br><br>"
							}
							$('#fj').append(text);
						}
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
					基本信息
				</legend>
	<table class="ta001">
		<tr>
			<th>
				标题
			</th>
			<td >
				<input name="email.emailSubject" class="easyui-validatebox" disabled="disabled"
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
				<input name="email.fromAddress" class="easyui-validatebox" disabled="disabled"
					style="width: 720px;"
					data-options="required:true,validType:'email'"
					maxlength="100" />
			</td>
		</tr>
		<tr>
			<th>
				收件人
			</th>
			<td >
				<textarea id="toAddress" disabled="disabled" name="email.toAddress"  style="width:725px;height: 100px"></textarea>
				<br>
				<font color="red">（多地址逗号分隔）</font>
			</td>
		</tr>
		<tr>
			<th>
				抄送
			</th>
			<td >
				<textarea id="ccAddress" disabled="disabled" name="email.ccAddress"  style="width:725px;height: 100px"></textarea>
				<br>
				<font color="red">（多地址逗号分隔）</font>
			</td>
		</tr>
		<tr>
			<th>
				密送
			</th>
			<td >
				<textarea id="bccAddress" disabled="disabled" name="email.bccAddress"  style="width:725px;height: 100px"></textarea>
				<br>
				<font color="red">（多地址逗号分隔）</font>
			</td>
		</tr>
		<tr>
			<th>
				邮件内容
			</th>
			<td >
				<textarea id="content" rows="20" cols="100"
					data-options="required:true,validType:'customRequired'" disabled="disabled"
					name="email.emailText"></textarea>
			</td>
		</tr>
		<tr>
			<th>
				发送时间
			</th>
			<td >
				<input name="email.createDateTime" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<th>
				附件
			</th>
			<td >
				<div id="fj">
					
				</div>
			</td>
		</tr>
	</table>
	</fieldset>
</form>
  </body>
</html>
