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
			var submitForm = function($dialog, $grid, $pjq) {
				if ($('form').form('validate')) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!insertAlias.action',
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
			<input name="dept.level" type="hidden" value="${param.level}">
			<input name="dept.parentId" type="hidden" value="${param.pid}">
			<fieldset>
				<legend>
					基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							曾用名
						</th>
						<td>
							<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
						</td>
					</tr>
					<tr>
						<th>
							当年学校
						</th>
						<td>
							<select name="dept.schoolId" class="easyui-combobox" data-options="required:true,editable:false,valueField:'deptId',textField:'deptName',url:'${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptAlias.action'" style="width: 150px;"></select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
