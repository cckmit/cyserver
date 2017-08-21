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
			$('#tree').tree({
				url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptTreeForView.action?deptId=${param.id}',
				parentField : 'pid',
				checkbox : true,
				onLoadSuccess : function(node, data) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!getByAliasName.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result) {
							if (result) {
								$('form').form('load', {
	                                'dept.deptName': result.deptName
	                            });
								for (var i = 0; i < result.depts.length; i++) {
									var node = $('#tree').tree('find', result.depts[i].deptId);
									if (node) {
										var isLeaf = $('#tree').tree('isLeaf', node.target);
										if (isLeaf) {
											$('#tree').tree('check', node.target);
										}
									}
								}
								var node = $('#tree').tree('getRoots');
								for(var i=0;i<node.length;i++){
										$('#tree').tree('disableCheck',node[i].id);//禁用   
								}
							}
						},
						complete:function(){
							parent.$.messager.progress('close');
						}
					});
				},onBeforeLoad:function(node, param){
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				}
			});
	});
		</script>
	</head>

	<body>
		<form method="post" id="addDeptForm">
			<input name="dept.deptId" type="hidden" value="${param.id}">
			<fieldset>
				<legend>
					<c:if test="${param.id.length()==6}">
						学校基本信息
					</c:if>
					<c:if test="${param.id.length()==10}">
						院系基本信息
					</c:if>
				</legend>
				<table class="ta001">
					<c:if test="${param.id.length()==6}">
						<tr>
							<th>
								学校名称
							</th>
							<td>
								<input name="dept.deptName" class="easyui-validatebox"  disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th>
								属于我的学校
							</th>
							<td>
								<ul id="tree"></ul>	
							</td>
						</tr>
					</c:if>
					<c:if test="${param.id.length()==10}">
						<tr>
							<th>
								院系名称
							</th>
							<td>
								<input name="dept.deptName" class="easyui-validatebox" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<th>
								属于我的院系
							</th>
							<td>
								<ul id="tree"></ul>	
							</td>
						</tr>
					</c:if>
				</table>
			</fieldset>
		</form>
	</body>
</html>
