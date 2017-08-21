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

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var alumniGrid;
	$(function() {
		alumniGrid = $('#alumniGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/alumni/alumniAction!dataGrid.action',
							fit : true,
							method : 'post',
							border : false,
							striped : true,
							pagination : true,
							columns : [ [
									{
										field : 'alumniId',
										checkbox : true
									},
									{
										width : '200',
										title : '组织名称',
										field : 'alumniName',
										align : 'center'
									},
									{
										width : '250',
										title : '所在地区',
										field : 'region',
										align : 'center',
										formatter : function(value, row) {
											if (value != null) {
												return "<span title='"+value+"'>"
														+ value + "</span>"
											} else {
												return ""
											}
										}
									}
									, {
										title : '操作',
										field : 'action',
										width : '150',
										formatter : function(value, row) {
											var str = '';
											<authority:authority authorizationCode="查看校友组织" userRoles="${sessionScope.user.userRoles}">
											str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
											</authority:authority>
											<authority:authority authorizationCode="编辑校友组织" userRoles="${sessionScope.user.userRoles}">
											str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
											</authority:authority>
											return str;
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

	function searchAlumni() {
		$('#alumniGrid').datagrid('load', serializeObject($('#searchForm')));
	}

	function addFun() {
		var dialog = parent
				.WidescreenModalDialog({
					title : '新增校友组织',
					iconCls : 'ext-icon-note_add',
					url : '${pageContext.request.contextPath}/page/admin/alumni/add.jsp',
					buttons : [ {
						text : '保存',
						iconCls : 'ext-icon-save',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow
									.submitForm(dialog, alumniGrid,
											parent.$);
						}
					} ]
				});
	}
	
	function removeFun()
	{
		var rows = $("#alumniGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			parent.$.messager.confirm('确认', '确定删除吗？', function(r)
			{
				if (r)
				{
					for ( var i = 0; i < rows.length; i++)
					{
						ids.push(rows[i].alumniId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/alumni/alumniAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(data)
						{
							if (data.success)
							{
								$("#alumniGrid").datagrid('reload');
								$("#alumniGrid").datagrid('unselectAll');
								parent.$.messager.alert('提示', data.msg, 'info');
							} else
							{
								parent.$.messager.alert('错误', data.msg, 'error');
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
			});
		} else
		{
			parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}
	
	function viewFun(id)
	{
		var dialog = parent.WidescreenModalDialog({
			title : '查看校友组织',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/admin/alumni/view.jsp?id=' + id
		});
	}
	
	function editFun(id)
	{
		var dialog = parent.WidescreenModalDialog({
			title : '编辑校友组织',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/alumni/edit.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, alumniGrid, parent.$);
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
					<form id="searchForm">
						<table>
							<tr>
								<th align="right" width="30px;">组织名称</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td><input name="alumni.alumniName" style="width: 150px;" />
								</td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-search',plain:true"
									onclick="searchAlumni();">查询</a>&nbsp;</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td><authority:authority authorizationCode="新增校友组织"
									userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'ext-icon-note_add',plain:true"
										onclick="addFun();">新增</a>
								</authority:authority>
							</td>
							<td><authority:authority authorizationCode="删除校友组织"
									userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'ext-icon-note_delete',plain:true"
										onclick="removeFun();">删除</a>
								</authority:authority>
							</td>
						</tr>
						<tr>
							<td><span id="exportResult"></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="alumniGrid"></table>
	</div>
</body>
</html>
