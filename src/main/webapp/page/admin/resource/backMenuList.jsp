<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
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
		<meta http-equiv="keywords" content="">
		<meta http-equiv="description" content="">
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
			var grid;
			$(function() {
				grid = $('#grid').treegrid({
					url :'${pageContext.request.contextPath}/resource/resourceAction!backTreeGrid.action',
//					fitColumns:true,
					idField : 'id',
					treeField : 'menuName',
					parentField : 'pid',
					rownumbers : true,
					pagination : false,
					
					frozenColumns : [ [ {
						width : '250',
						title : '资源名称',
						field : 'menuName'
					} ] ],
					columns : [ [{
							width : '150',
							title : '资源编码',
							field : 'name',
							align:'center'
					},{
						width : '150',
						title : '图标名称',
						field : 'iconCls',
						align:'center'
					}, {
						width : '400',
						title : '资源路径',
						field : 'url',
						align:'center'
					}, {
						width : '60',
						title : '资源类型',
						field : 'type',
						align:'center'
					}, {
						width : '50',
						title : '排序',
						field : 'seq',
						align:'center'
					},
					/*{
						width : '100',
						title : '系统',
						field : 'flag',
						align:'center',
						formatter : function(value, row) {
							if(value==0){
								return "<span class='label label-danger'>WEB后台系统</span>";
							}else{
								return "<span class='label label-default'>校友会系统</span>";
							}
						}
					}
					,*/
						{
						title : '操作',
						field : 'action',
						align:'center',
						width : '250',
						formatter : function(value, row) {
							var str = '';
							<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看菜单">
								str += '<a href="javascript:void(0)" onclick="showFun('+row.id+');"><img class="iconImg ext-icon-note" />查看</a>&nbsp;';
							</authority:authority>
							<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑菜单">
								str += '<a href="javascript:void(0)" onclick="editFun('+row.id+');"><img class="iconImg ext-icon-note_edit" />编辑</a>&nbsp;';
							</authority:authority>
							<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除菜单">
								str += '<a href="javascript:void(0)" onclick="removeFun('+row.id+');"><img class="iconImg ext-icon-note_delete" />删除</a>';
							</authority:authority>
							return str;
						}
					} ] ],
					toolbar : '#toolbar',
					onBeforeLoad : function(row, param) {
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess : function(row, data) {
						$('.iconImg').attr('src', pixel_0);
						parent.$.messager.progress('close');
					}
				});
			});
			function addFun() {
				var dialog = parent.modalDialog({
					title : '新增菜单',
					iconCls:'ext-icon-note_add',
					url : '${pageContext.request.contextPath}/page/admin/resource/addBackMenu.jsp',
					buttons : [ {
						text : '保存',
						iconCls : 'ext-icon-save',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
						}
					} ]
				});
			};
			function showFun(id) {
				var dialog = parent.modalDialog({
					title : '查看菜单',
					iconCls:'ext-icon-note',
					url : '${pageContext.request.contextPath}/page/admin/resource/viewBackMenu.jsp?id=' + id
				});
			};
			function editFun(id) {
				var dialog = parent.modalDialog({
					title : '编辑菜单',
					iconCls:'ext-icon-note_edit',
					url : '${pageContext.request.contextPath}/page/admin/resource/editBackMenu.jsp?id=' + id,
					buttons : [ {
						text : '保存',
						iconCls : 'ext-icon-save',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
						}
					} ]
				});
			};
			function removeFun(id) {
				parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
					if (r) {
						$.ajax({
							url : '${pageContext.request.contextPath}/resource/resourceAction!delete.action',
							data : {
								id : id
							},
							dataType : 'json',
							success : function(result) {
								if (result.success) {
									grid.treegrid('reload');
								//	parent.mainMenu.tree('reload');
									parent.$.messager.alert('提示', result.msg, 'info');
								} else {
									parent.$.messager.alert('提示', result.msg, 'error');
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
				});
			};
			
			 function exportFun(){
					$.ajax({
						url : '${pageContext.request.contextPath}/resource/resourceAction!exportData.action',
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								if(result.msg!=""){
									$('#exportResult').html("<a id='mf' href='"+result.msg+"'>导出结果下载</a>")
									parent.parent.$.messager.alert('提示', "导出成功,请在导出结果处下载导出结果", 'info');
								}else{
									parent.parent.$.messager.alert('提示', "无数据导出", 'info');
								}
							} else {
								parent.parent.$.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend:function(){
							$('#mf').remove();
							parent.parent.$.messager.progress({
								text : '数据导出中....'
							});
						},
						complete:function(){
							parent.parent.$.messager.progress('close');
						}
					});
				}
					function importFun(){
		  var dialog = parent.parent.modalDialog({
				title : '导入菜单',
				iconCls : 'ext-icon-import_customer',
				url : '${pageContext.request.contextPath}/page/admin/resource/importResource.jsp',
				buttons : [ {
					text : '确定',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
	  }
		</script>
	</head>

	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增菜单">
							<a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'ext-icon-note_add',plain:true"
								onclick="addFun();">新增</a>
						</authority:authority>
					</td>
					<td>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="菜单导入">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun();" style="margin-left: 5px; margin-top: 1px;">菜单导入</a>
						</authority:authority>
					</td>
					<td>
						<authority:authority authorizationCode="菜单导出" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="exportFun();">菜单导出</a>
						</authority:authority>
					</td>
					<td>
						<span id="exportResult"></span>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="grid" data-options="fit:true,border:false"></table>
		</div>
	</body>
</html>
