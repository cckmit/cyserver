<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
	var roleGrid;
	$(function() {
		roleGrid = $('#roleGrid').datagrid({
			url : '${pageContext.request.contextPath}/role/roleAction!getList.action',
			fit : true,
			border : false,
//			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'roleId',
			columns : [ [ {
				width : '200',
				title : '角色名称',
				field : 'roleName'
			},/*{
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
			},*/
			{
				title : '操作',
				field : 'action',
				width : '300',
				formatter : function(value, row) {
					if(row.can_del_flag==0){
						var str = '';
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看角色">
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.roleId + ','+row.flag+');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
						</authority:authority>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑角色">
							str += '<a href="javascript:void(0)" onclick="editFun(' + row.roleId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
						</authority:authority>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="权限授权">
							str += '<a href="javascript:void(0)" onclick="grantFun(' + row.roleId + ','+row.flag+');"><img class="iconImg ext-icon-key"/>授权</a>&nbsp;';
						</authority:authority>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除角色">
							str += '<a href="javascript:void(0)" onclick="removeFun(' + row.roleId + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
						</authority:authority>
						return str;
					}
					if(row.can_del_flag==1) {
						var str = '';
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看角色">
						str += '<a href="javascript:void(0)" onclick="showFun(' + row.roleId + ','+row.flag+');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
						</authority:authority>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑角色">
						str += '<a href="javascript:void(0)" onclick="editFun(' + row.roleId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
						</authority:authority>
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="权限授权">
						str += '<a href="javascript:void(0)" onclick="grantFun(' + row.roleId + ','+row.flag+');"><img class="iconImg ext-icon-key"/>授权</a>&nbsp;';
						</authority:authority>
						return str;
					}
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});

	var addFun = function() {
		var dialog = parent.modalDialog({
			title : '新增角色信息',
			iconCls : 'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/role/roleForm.jsp?id=0',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, roleGrid, parent.$);
				}
			} ]
		});
	};

	var showFun = function(id,flag) {
		var dialog = parent.modalDialog({
			title : '查看角色信息',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/admin/role/viewRole.jsp?id=' + id+'&flag='+flag
		});
	};
	var editFun = function(id) {
		var dialog = parent.modalDialog({
			title : '编辑角色信息',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/role/roleForm.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, roleGrid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/role/roleAction!delete.action',
					data : {
						id : id
					},
					dataType : 'json',
					success : function(result) {
//						alert(JSON.stringify(result));
						if (result.success) {
							roleGrid.datagrid('reload');
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
	var grantFun = function(id,flag) {
		var dialog = parent.modalDialog({
			title : '权限授权',
			iconCls : 'ext-icon-key',
			url : '${pageContext.request.contextPath}/page/admin/role/roleGrant.jsp?id=' + id+'&flag='+flag,
			buttons : [ {
				text : '授权',
				iconCls : 'ext-icon-key',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, roleGrid, parent.$);
				}
			} ]
		});
	};
</script>
	</head>

	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										角色名称
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="roleName" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="roleGrid.datagrid('load',{roleName:$('#roleName').val()});">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td>
									<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增角色">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun();">新增</a>
									</authority:authority>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="roleGrid"></table>
		</div>
	</body>
</html>
