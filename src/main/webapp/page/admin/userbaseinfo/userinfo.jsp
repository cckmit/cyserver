<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
		<%--<script type="text/javascript">
			var deptTree;
			$(function(){
				deptTree = $('#deptTree').tree({
					url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptTree.action',
					onClick : function(node) {
						$('#userFrame').attr('src','${pageContext.request.contextPath}/page/admin/userbaseinfo/userInfoList.jsp')
					},
					onBeforeLoad:function(node, param){
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess:function(node, data){
						parent.$.messager.progress('close')
					}
				});
			});
		</script>
	--%></head>

	<body>
	<body id="layout" class="easyui-layout" style="overflow-y: hidden"
		scroll="no">
		<!--  
		<div data-options="region:'west',split:true" style="width: 200px;">
			<ul id="deptTree"></ul>
		</div>
		-->
		<div data-options="region:'center'" style="overflow-y: hidden">
			<iframe id="userFrame" src="${pageContext.request.contextPath}/page/admin/userbaseinfo/userInfoList.jsp" allowTransparency="true"
				style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
		</div>
	</body>
	</body>
</html>
