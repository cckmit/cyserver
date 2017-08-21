<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
long check = WebUtil.toLong(request.getParameter("check")) ;
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

  $(function(){
	  if($('#drop').val() == '')
	  {
		  $('#dropTh').hide();
	  }else {
		  $('#dropTh').show();
		  $('#checkTh').hide();
		  $('#checkTr').hide();
	  }
  });

  function submitForm($dialog, $grid, $pjq){
	  //批量审批
	  if($('#drop').val() == '' && $('#id').val() == '' && $('#ids').val() != ''){
		  var json = {"ids":"${param.ids}",
			  "status":$('#status').combobox('getValue'),
			  "handleOpinion": $('#handleOpinion').val()};

		  $.ajax({
			  url : "${pageContext.request.contextPath}/mobile/news/newsAction!multiLineCheck.action",
			  data : json,
			  dataType : 'json',
			  success : function(result)
			  {
				  if (result.success){
					  $grid.datagrid('reload');
					  window.parent.refreshMsgNum();
					  $grid.datagrid('unselectAll');
					  $dialog.dialog('destroy');
					  $pjq.messager.alert('提示', result.msg, 'info');
				  }else{
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
	  }else if($('#drop').val() != '' && $('#id').val() != '' && $('#ids').val() == ''){
		  var json = {"bussId":"${param.id}",
			  "status": '40',
			  "handleOpinion": $('#handleOpinion').val()};

		  $.ajax({
			  url : "${pageContext.request.contextPath}/mobile/news/newsAction!saveCheck.action",
			  data : json,
			  dataType : 'json',
			  success : function(result){
				  if (result.success){
					  $grid.datagrid('reload');
					  window.parent.refreshMsgNum();
					  $grid.datagrid('unselectAll');
					  $dialog.dialog('destroy');
					  parent.$.messager.alert('提示', '下线成功', 'info');
				  }else{
					  parent.$.messager.alert('提示', '下线失败', 'error');
				  }
			  },
			  beforeSend : function(){
				  parent.$.messager.progress({
					  text : '数据提交中....'
				  });
			  },
			  complete : function(){
				  parent.$.messager.progress('close');
			  }
		  });
	  }else if($('#drop').val() != '' && $('#id').val() == '' && $('#ids').val() != ''){
		  var json = {"ids":"${param.ids}",
			  "status": '40',
			  "handleOpinion": $('#handleOpinion').val()};

		  $.ajax({
			  url : "${pageContext.request.contextPath}/mobile/news/newsAction!multiLineCheck.action",
			  data : json,
			  dataType : 'json',
			  success : function(result)
			  {
				  if (result.success){
					  $grid.datagrid('reload');
					  window.parent.refreshMsgNum();
					  $grid.datagrid('unselectAll');
					  $dialog.dialog('destroy');
					  $pjq.messager.alert('提示', '批量下线成功', 'info');
				  }else{
					  $pjq.messager.alert('提示', '批量下线失败', 'error');
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
</script>
</head>
  
  <body>
  <form method="post">
	  <input id="id" value="${param.id}" type="hidden" />
	  <input id="ids" value="${param.ids}" type="hidden" />
	  <input id="drop" value="${param.drop}" type="hidden" />
	<fieldset>
		<table class="ta001">
			<tr id="checkTr">
				<th>审核状态</th>
				<td>
					<select class="easyui-combobox" data-options="editable:false" name="checkNews.status" id = "status" style="width: 150px;">
						<option value="20">通过</option>
						<option value="30">不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<th id="checkTh">审核意见</th>
				<th id="dropTh" style="display:none">下线原因</th>
				<td>
					<textarea rows="5" cols="80" name="checkNews.handleOpinion" id="handleOpinion"></textarea>
				</td>
			</tr>

		</table>
	</fieldset>
  </form>
  </body>
</html>