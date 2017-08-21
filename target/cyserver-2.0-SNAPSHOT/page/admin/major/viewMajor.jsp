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
		<script type="text/javascript">
		$(function() {
			$('#tree').tree({
				url : '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getDeptTree.action',
				parentField : 'pid',
				checkbox : true,
				onLoadSuccess : function(node, data) {
					$.ajax({
						url : '${pageContext.request.contextPath}/major/majorAction!getById.action',
						data : {
							id : $('#majorId').val()
						},
						dataType : 'json',
						success : function(result) {
							if (result) {
								$('form').form('load', {
	                                'major.majorId': result.majorId,
	                                'major.majorName': result.majorName
	                            });
	                            
								for (var i = 0; i < result.deptList.length; i++) {
									var node = $('#tree').tree('find', result.deptList[i].deptId);
									if (node) {
										var isLeaf = $('#tree').tree('isLeaf', node.target);
										if (isLeaf) {
											$('#tree').tree('check', node.target);
										}
									}
								}
							}
						},
						complete:function(){
							parent.$.messager.progress('close');
						}
					});
					
					$(this).find('span.tree-checkbox').unbind().click(function(){
						return false;
					});
					var node = $('#tree').tree('getRoots');
					for(var i=0;i<node.length;i++){
						if(node[i].id.length==6){
							$('#tree').tree('disableCheck',node[i].id);//禁用   
						}
					}
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
		<form method="post" class="form">
			<input name="major.majorId" value="${param.id}" type="hidden" id="majorId">
			<fieldset>
				<legend>
					专业信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							专业名称
						</th>
						<td>
							<input name="major.majorName" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'" disabled="disabled" />
						</td>
					</tr>
					
					<tr>
						<th valign="top">
							所属院系
						</th>
						<td>
							<ul id="tree"></ul>							
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
