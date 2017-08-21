<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<% MessageBoard messageBoard = (MessageBoard)request.getAttribute("messageBoard"); %>

<!DOCTYPE html>
<html>
<head>

<title>意见反馈</title>
<jsp:include page="../head.jsp"/>
</head>
<body>
<form method="post" >
<input type="hidden" name="messageBoard.messageType" id="messageType" value="<%= messageBoard.getMessageType() %>" >
<input type="hidden" name="messageBoard.messageUserId" id="messageUserId" value="<%= messageBoard.getMessageUserId() %>" >
<div id="posts">
  <div id="header">

      <%--<span style="float:left" onclick="close()" id="me"><img src="<%= request.getContextPath()%>\images\arrow_mark.png">&nbsp;我</span>--%>

      <h1 align="center">意见反馈</h1>

      <a href="javascript:sendFeedBack('<%= messageBoard.getMessageType() %>','<%= messageBoard.getMessageUserId() %>');" class="btn-post">发送</a>
  </div>
  <div class="reply-input">
    <input name="messageBoard.messageTitle" id="messageTitle" type="text" placeholder="请输入主题">
    <textarea placeholder="请输入内容" name="messageBoard.messageContent" id="messageContent"></textarea>
  </div>
</div>
<script>
$(document).ready(function(){
  $(".reply-input textarea").height($(window).height()/2-97);
});
/*function close() {
  $("#me").hide();
}*/
</script>
</form>
</body>
</html>