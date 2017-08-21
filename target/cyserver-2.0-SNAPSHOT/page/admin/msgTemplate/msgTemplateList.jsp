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
		var msgTemplateGrid;
		var systemTemplate = new Array();
		$(function(){
			msgTemplateGrid=$('#msgTemplateGrid').datagrid({
				url : '${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!dataGrid.action',
				fit : true,
//				fitColumns : true,
				pagination : true,
				border : false,
				striped : true,
				rownumbers : true,
				idField : 'msgTemplateId',
				columns : [ [{
						field : 'msgTemplateId',
						checkbox : true
					},
					{
						field : 'msgTemplateTitle',
						title : '模板名称',
						width : 100,
						align : 'center'
					},
					{
						field : 'msgTemplateContent',
						title : '模板内容',
						width : 300,
						align : 'center'
					},
					{
						field : 'msgTemplateParamNumber',
						title : '参数个数',
						width : 80,
						align : 'center'
					},
					{
						field : 'msgTemplateStatus',
						title : '审核状态',
						width : 100,
						align : 'center',
						formatter: function(value,row,index){
							var content="";
							if(value==10){
								content="待审核";
							}
							if(value==20){
								content="审核通过";
							}
							if(value==30){
								content="审核未通过";
							}
							return content;
						}
					},
					{
						field : 'msgTemplateType',
						title : '模板类型',
						width : 100,
						align : 'center',
						formatter : function(value, row) {
							if(value==1){
								systemTemplate.push(row.msgTemplateId);
								return "<span class='label label-danger'>系统</span>";
							}else{
								return "<span class='label label-default'>普通</span>";
							}
						}
					},
					{
						field : 'operator',
						title : '操作',
						width : 300,
						align : 'center',
						formatter : function(value, row, index){
							var str = '';
							<authority:authority authorizationCode="查看短信模板" userRoles="${sessionScope.user.userRoles}">
								str += '<a href="javascript:void(0)" onclick="viewMsgTemplate(' + row.msgTemplateId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							<authority:authority authorizationCode="编辑短信模板" userRoles="${sessionScope.user.userRoles}">
								str += '<a href="javascript:void(0)" onclick="editMsgTemplate(' + row.msgTemplateId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							</authority:authority>
							return str;
						}
					} ] ],
					toolbar : '#msgTemplateToolbar',
					onBeforeLoad : function(param)
					{
						systemTemplate = new Array();
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess : function(data)
					{
						$('.iconImg').attr('src', pixel_0);
						parent.$.messager.progress('close');
					}
				});
	});

	function searchMsgTemplate()
	{
		if ($('#searchMsgTemplateForm').form('validate'))
		{
			$('#msgTemplateGrid').datagrid('load', serializeObject($('#searchMsgTemplateForm')));
		}
	}
	
	function addMsgTemplate() {
		var dialog = parent.modalDialog({
			title : '新增短信模板',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/msgTemplate/msgTemplateForm.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, msgTemplateGrid, parent.$);
				}
			} ]
		});
	}
	
	var viewMsgTemplate = function(id) {
		var dialog = parent.modalDialog({
			title : '查看短信模板',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!getById.action?id=' + id
		});
	}
	
	var editMsgTemplate = function(id) {
		var dialog = parent.modalDialog({
			title : '编辑短信模板',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!doNotNeedSessionAndSecurity_initMsgTemplateUpdate.action?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, msgTemplateGrid, parent.$);
				}
			} ]
		});
	};
	function removeMsgTemplate()
	{
		var rows = $("#msgTemplateGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			parent.$.messager.confirm('确认', '确定删除吗？', function(r)
			{
				if (r)
				{
					for ( var i = 0; i < rows.length; i++)
					{
						if(isSystemTemplate(rows[i].msgTemplateId))
						{
							$.messager.alert('提示', '不允许删除系统模板', 'info');
							return;
						}
						ids.push(rows[i].msgTemplateId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(data)
						{
							if (data.success)
							{
								$("#msgTemplateGrid").datagrid('reload');
								$("#msgTemplateGrid").datagrid('unselectAll');
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
	
	/**
	 * 判断是否是系统模板
	 **/
	function isSystemTemplate(id)
	{
//		alert("id = " + id + ", systemTemplate = " + systemTemplate) ;
		for(var i = 0; i < systemTemplate.length; i++)
		{
			if(id == systemTemplate[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
</script>
	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="msgTemplateToolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchMsgTemplateForm">
							<table>
								<tr>
									<th>
										模板名称
									</th>
									<td>
									<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="msgTemplateTitle" name="msgTemplate.msgTemplateTitle" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchMsgTemplate();">查询</a>
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
									<authority:authority authorizationCode="新增短信模板" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addMsgTemplate();">新增</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="删除短信模板" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeMsgTemplate();">删除</a>
									</authority:authority>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="msgTemplateGrid"></table>
		</div>
	</body>
</html>