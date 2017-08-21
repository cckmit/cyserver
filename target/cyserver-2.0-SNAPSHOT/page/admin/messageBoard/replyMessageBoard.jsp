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

  </head>
  
  <body>
<form method="post" id="replyMessageBoardForm">
	<input type="hidden" name="id" value="${messageBoard.messageId}">
	<input type="hidden" name="checkStatus" value="1">
	<table class="ta001">
		<tr>
			<th>
				信息标题
			</th>
			<td colspan="3">
				<input type="text" value="${messageBoard.messageTitle}" style="width: 500px;" disabled="disabled">
			</td>
		</tr>
		<tr>
			<th>
				信息内容
			</th>
			<td colspan="3">
				<textarea rows="10" cols="80" disabled="disabled">${messageBoard.messageContent}</textarea>
			</td>
		</tr>
		
		<tr>
			
			<th>
				管理员的回复内容
			</th>
			<td colspan="3">
				<textarea rows="10" cols="80" name="replyMessageContent">${messageBoard.replyMessageContent}</textarea>
			</td>
		
		</tr>
		
		<tr>
			<th>
				消息类型
			</th>
			<td>
				<c:if test="${messageBoard.messageType==1}">
					<input type="text" value="求职招聘" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==2}">
					<input type="text" value="项目合作" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==3}">
					<input type="text" value="互帮互助" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==404}">
					<input type="text" value="意见反馈" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==501}">
					<input type="text" value="联系会长" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==502}">
					<input type="text" value="联系学院" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.messageType==503}">
					<input type="text" value="联系总会" disabled="disabled">
				</c:if>
			</td>
			<th>
				消息时间
			</th>
			<td>
				<input type="text" value="<fmt:formatDate value='${messageBoard.messageTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="disabled">
			</td>
		</tr>
		<tr>
			<th>
				发送者姓名
			</th>
			<td>
				<input type="text" value="${messageBoard.messageUserName}" disabled="disabled">
			</td>
			<th>
				审核状态
			</th>
			<td>
				<c:if test="${messageBoard.checkStatus==0}">
					<input type="text" value="未审核" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.checkStatus==1}">
					<input type="text" value="已通过" disabled="disabled">
				</c:if>
				<c:if test="${messageBoard.checkStatus==2}">
					<input type="text" value="未通过" disabled="disabled">
				</c:if>
			</td>
		</tr>
		
		<tr>
			<th>
				浏览量
			</th>
			<td>
				<input type="text" value="${messageBoard.messageBrowseQuantity}" disabled="disabled">
			</td>
			<th>
				发布者的IP
			</th>
			<td>
				<input type="text" value="${messageBoard.messageUserIP}" disabled="disabled">
			</td>
		</tr>
		
		
		
		
	</table>
</form>
  </body>
</html>
