<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
		$(function() {
			$.ajax({
				url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getById.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.deptId != undefined) {
						$('form').form('load', {
							'dept.deptName':result.deptName,
							'dept.resourceName':result.deptName
						});
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
			var submitForm = function($dialog, $grid, $pjq) {
				if ($('form').form('validate')) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!update.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								if(${param.id.length()==1}){
									refrensh();
								}else{
									$grid.tree('reload');
								}
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else {
								$pjq.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend:function(){
							parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete:function(){
							parent.$.messager.progress('close');
						}
					});
				}
			}
			
			function refrensh(){
				var panel = parent.$('#mainTabs').tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}
			}
		</script>
	</head>

	<body>
		<form method="post" id="addDeptForm">
			<input name="dept.deptId" type="hidden" value="${param.id}">
			<input name="dept.parentId" type="hidden" value="${param.pid}">
			<fieldset>
				<legend>
					<c:if test="${param.id.length()==6}">
						学校基本信息
					</c:if>
					<c:if test="${param.id.length()==10}">
						院系基本信息
					</c:if>
					<c:if test="${param.id.length()==16}">
						班级基本信息
					</c:if>
				</legend>
				<table class="ta001">
					<c:if test="${param.id.length()==6}">
						<tr>
							<th>
								学校名称
							</th>
							<td>
								<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
								<input name="dept.resourceName" type="hidden">
							</td>
						</tr>
					</c:if>
					<c:if test="${param.id.length()==10}">
						<tr>
							<th>
								院系名称
							</th>
							<td>
								<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
								<input name="dept.resourceName" type="hidden">
							</td>
						</tr>
					</c:if>
					<c:if test="${param.id.length()==16}">
						<tr>
							<th>
								班级名称
							</th>
							<td>
								<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
								<input name="dept.resourceName" type="hidden">
							</td>
						</tr>
					</c:if>
				</table>
			</fieldset>
		</form>
	</body>
</html>
