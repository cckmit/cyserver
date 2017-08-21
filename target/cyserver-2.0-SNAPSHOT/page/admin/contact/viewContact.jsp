<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
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
	
	$(function () {			
        if ($('#contactId').val() > 0) {	
            $.ajax({
                url: '${pageContext.request.contextPath}/contact/contactAction!getById.action',
                data: $('form').serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result.id != undefined) {
	
                        $('form').form('load', {
                            'contact.id': result.id,
                            'contact.title': result.title,
                            'contact.content': result.content,
                            'contact.name': result.userProfile.name,
                            'contact.createTime': result.createTime,
                            'contact.replyContent': result.replyContent,
                            'contact.replyTime': result.replyTime
                        });
                    }
                },
                beforeSend: function () {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                complete: function () {
                	
                    parent.$.messager.progress('close');
                    
                    
                }
            });
        }
        
    });

	
</script>
</head>
  
<body>
<form method="post" id="addForm">
<input name="contact.id" type="hidden" id="contactId" value="${param.id}">
<fieldset>
		<legend>
			信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					标题
				</th>
				<td colspan="3">
					<input style="width: 700px;" name="contact.title" type="text" disabled="disabled">
				</td>
			</tr>
			<tr>
				<th>
					内容
				</th>
				<td colspan="3">
					<textarea name="contact.content"
						style="width: 700px; height: 150px;" disabled="disabled"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					发表人
				</th>
				<td colspan="3">
					<input style="width: 200px;" name="contact.name" type="text" disabled="disabled">
				</td>
			</tr>
			<tr>
				<th>
					发表时间
				</th>
				<td colspan="3">
					<input style="width: 200px;" name="contact.createTime" type="text" disabled="disabled">
				</td>
			</tr>

		</table>
</fieldset>
<fieldset>
		<legend>
			回复
		</legend>
		<table class="ta001">
			<tr>
				<th>
					回复内容
				</th>
				<td colspan="3">
					<textarea name="contact.replyContent"
						style="width: 700px; height: 150px;" disabled="disabled"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					回复时间
				</th>
				<td colspan="3">
					<input style="width: 200px;" name="contact.replyTime" type="text" disabled="disabled">
				</td>
			</tr>

		</table>
	</fieldset>
</form>
  </body>
</html>