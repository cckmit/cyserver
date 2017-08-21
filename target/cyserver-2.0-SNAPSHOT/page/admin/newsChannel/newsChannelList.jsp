<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
var grid;
		$(function(){
			grid=$('#newsChannelGrid').datagrid({  
			    url:'${pageContext.request.contextPath}/newsChannel/newsChannelAction!dataGrid.action', 
				fit : true,
//			  	fitColumns:true,
			  	pagination:true,
			  	border:false,
			  	nowrap : true,
			  	rownumbers:true,
			  	idField : 'channelId',
			    columns:[[  
					{field:'channelId',checkbox : true}, 
			        {field:'channelName',title:'频道名称',width:100,align:'center'}, 
			        {field:'channelRemark',title:'频道简介',width:150,align:'center'},
			        {field:'channelIcon',title:'频道图标',width:500,align:'center'},
			    	{field:'operator',title:'操作',width:300,align:'center',
			    		formatter: function(value,row,index){
							var content="";
							<authority:authority authorizationCode="查看频道" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="viewNewsChannel('+row.channelId+')"><span class="iconImg ext-icon-note"></span>查看</a>&nbsp;';
			    			</authority:authority>
			    			<authority:authority authorizationCode="编辑频道" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="editNewsChannel('+row.channelId+')"><span class="iconImg ext-icon-note_edit"></span>编辑</a>&nbsp;';
			    			</authority:authority>
			    			return content;
					}}
			    ]],
			    toolbar:'#newsChannelToolbar',
			    onBeforeLoad : function(param) {
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				},
				onLoadSuccess : function(data) {
					parent.$.messager.progress('close');
				}
			});
		});
		
		
		function searchNews(){
			  if ($('#searchNewsChannelForm').form('validate')) {
				  $('#newsChannelGrid').datagrid('load',serializeObject($('#searchNewsChannelForm')));
			  }
		}
		
		
		
		
		
		function addNewsChannel() {
			var dialog = parent.modalDialog({
				width : 1000,
				height : 600,
				title : '新增频道',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/newsChannel/newsChannelForm.jsp',
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		}
	
	
	function editNewsChannel(id) {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 600,
			title : '编辑频道',
			iconCls:'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/newsChannel/newsChannelAction!doNotNeedSessionAndSecurity_initNewsChannelUpdate.action?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}




	function viewNewsChannel(id) {
		
		var dialog = parent.modalDialog({
			width : 1000,
			height : 600,
			title : '查看频道',
			iconCls:'ext-icon-note',
			url : '${pageContext.request.contextPath}/newsChannel/newsChannelAction!getById.action?id=' + id
		});
	}
		
		
		function removeNewsChannel(){
			var rows = $("#newsChannelGrid").datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].channelId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/newsChannel/newsChannelAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#newsChannelGrid").datagrid('reload');
									$("#newsChannelGrid").datagrid('unselectAll');
									parent.$.messager.alert('提示',data.msg,'info');
								}
								else{
									parent.$.messager.alert('错误', data.msg, 'error');
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
			} else {
				 parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
			}
		}
		
		function sendNewsChannel(){
			var rows = $("#newsChannelGrid").datagrid('getChecked');
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定批量发送吗？', function(r) {
					if (r) {
						$.ajax({
							url:'${pageContext.request.contextPath}/newsChannel/newsChannelAction!send.action',
							dataType:'json',
							success : function(result){
								if(result.success){
									$('#newsChannelGrid').datagrid('reload');
									$.messager.alert('提示', result.msg, 'info');
								}else{
									$.messager.alert('提示', result.msg, 'error');
								}
							},
							beforeSend:function(){
								$.messager.progress({
									text : '数据提交中....'
								});
							},
							complete:function(){
								$.messager.progress('close');
							}
						});
					}
				});
			}else{
				$.messager.alert('提示', '请选择要批量发送的记录！', 'error');
			}
	    }
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="newsChannelToolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchNewsChannelForm">
						<table>
							<tr>
								<th>
									频道名称
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="channelName" name="newsChannel.channelName" style="width: 150px;" />
								</td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchNews();">查询</a>
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
							<authority:authority authorizationCode="新增频道" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addNewsChannel();">新增</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="删除频道" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeNewsChannel();">删除</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="发送频道" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-redo',plain:true"
									onclick="sendNewsChannel();">发送</a>
							</authority:authority>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="newsChannelGrid"></table>
	</div>
</div>
  </body>
</html>