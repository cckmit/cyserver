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
			$('#menuTree').tree({
				url : '${pageContext.request.contextPath}/resource/resourceAction!doNotNeedSecurity_getMenuTree.action',
				parentField : 'pid',
				checkbox : true,
				//cascadeCheck:false,
				onLoadSuccess : function(node, data) {
					$.ajax({
						url : '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getHasMenu.action',
						data : {
							id : $('#roleId').val()
						},
						dataType : 'json',
						success : function(result) {
							if (result) {
								for (var i = 0; i < result.length; i++) {
									var node = $('#menuTree').tree('find', result[i].id);
									if (node) {
										var isLeaf = $('#menuTree').tree('isLeaf', node.target);
										if (isLeaf) {
											$('#menuTree').tree('check', node.target);
										}
									}
								}
							}
						},
						error: function(e) { console.log(e) },
						complete:function(){
							initResourTree();
						}
					});
				},onBeforeLoad:function(node, param){
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				}
			});


			function initResourTree(){
				$('#resTree').tree({
					url : '${pageContext.request.contextPath}/resource/resourceAction!doNotNeedSecurity_getGrantTree.action',
					parentField : 'pid',
					checkbox : true,
					//cascadeCheck:false,
					onLoadSuccess : function(node, data) {
						$.ajax({
							url : '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getHasAction.action',
							data : {
								id : $('#roleId').val()
							},
							dataType : 'json',
							success : function(result) {
								if (result) {
									for (var i = 0; i < result.length; i++) {
										var node = $('#resTree').tree('find', result[i].id);
										if (node) {
											var isLeaf = $('#resTree').tree('isLeaf', node.target);
											if (isLeaf) {
												$('#resTree').tree('check', node.target);
											}
										}
									}
								}
							},
							error: function(e) { console.log(e) },
							complete:function(){
								parent.$.messager.progress('close');
							}
						});
					}
				});
			}
		});
		var submitForm = function($dialog, $grid, $pjq) {
			var nodes = $('#menuTree').tree('getChecked', [ 'checked', 'indeterminate' ]);
			var ids = [];
			for (var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}

			var actionNodes = $('#resTree').tree('getChecked', [ 'checked', 'indeterminate' ]);
			var actionIds = [];
			for (var i = 0; i < actionNodes.length; i++) {
				actionIds.push(actionNodes[i].id);
			}

			$.ajax({
				url : '${pageContext.request.contextPath}/role/roleAction!grant.action',
				data : {
					id : $('#roleId').val(),
					ids : ids.join(','),
					actionIds:actionIds.join(',')
				},
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						$grid.datagrid('reload');
						$dialog.dialog('destroy');
						$pjq.messager.alert('提示', result.msg, 'info');
					} else {
						$pjq.messager.alert('提示', result.msg, 'error');
					}
				},
				beforeSend:function(){
					$pjq.messager.progress({
						text : '数据提交中....'
					});
				},
				complete:function(){
					$pjq.messager.progress('close');
				}
			});
		};
		</script>
	</head>

	<body>
		<input name="role.roleId" value="${param.id}" type="hidden" id="roleId">
		<div style="width:50%;height:500px;float: left;overflow: auto;">
			<fieldset>
				<legend>
					菜单授权
				</legend>
				<ul id="menuTree"></ul>
			</fieldset>
		</div>
		<div style="width:50%;height:500px;float:left;overflow: auto;">
			<fieldset>
				<legend>
					功能授权
				</legend>
				<ul id="resTree"></ul>
			</fieldset>
		</div>
	</body>
</html>
