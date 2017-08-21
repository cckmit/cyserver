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
</head>
  
  <body>
<form method="post">
	<table class="ta001">
		<input name="newsChannel.channelId" type="hidden" id="channelId"
			value="${newsChannel.channelId}">
		<tr>
			<th>
				频道名称
			</th>
			<td colspan="3">
				<input name="newsChannel.channelName" class="easyui-validatebox"
					disabled="disabled"
					value="${newsChannel.channelName}" />
			</td>
		</tr>
		<tr>
			<th>
				频道简介
			</th>
			<td colspan="3">
				<textarea name="newsChannel.channelRemark" rows="6" cols="95" disabled="disabled">${newsChannel.channelRemark}</textarea>
			</td>
		</tr>
		<tr>
			<th>
				频道图标
			</th>
			<td colspan="3">
				<div id="pic">
					<c:if test="${newsChannel.channelIcon!=null and newsChannel.channelIcon!=''}">
						<div style="float:left;width:180px;"><img src="${newsChannel.channelIcon}" width="150px" height="150px"/><div class="bb001"></div><input type="hidden" name="newsChannel.channelIcon" value="${newsChannel.channelIcon}"/></div>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
</form>
  </body>
</html>