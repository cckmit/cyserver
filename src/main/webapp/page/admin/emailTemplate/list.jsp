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
			grid=$('#dataGrid').datagrid({  
			    url:'${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!dataGrid.action', 
			  	fit : true,
				border : false,
				striped : true,
				rownumbers : true,
				pagination : true,
			  	idField : 'templateId',
			    columns:[[
					{field:'templateId',checkbox : true}, 
			        {field:'templateName',title:'模板名称',width:250,align:'center'}, 
			        {field:'createDate',title:'创建日期',width:150,align:'center'},
			    	{field:'operator',title:'操作',width:150,
			    		formatter: function(value,row,index){
			    			var content="";
			    			<authority:authority authorizationCode="查看邮件模板"  userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="viewData('+row.templateId+')"><span class="iconImg ext-icon-note"></span>查看</a>&nbsp;';
			    			</authority:authority>
			    			<authority:authority authorizationCode="编辑邮件模板"  userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="editData('+row.templateId+')"><span class="iconImg ext-icon-note_edit"></span>编辑</a>&nbsp;';
			    			</authority:authority>
			    			return content;
					}}
			    ]],
			    toolbar : '#toolbar',
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
	
	
	
	function searchData(){
		$('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
	}
	
	
	
	
	
	
	function addData() {
		var dialog = parent.modalDialog({
			title : '新建邮件模板',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/emailTemplate/add.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	
	
	function editData(id) {
		var dialog = parent.modalDialog({
			title : '编辑邮件模板',
			iconCls:'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/emailTemplate/edit.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}


	
	function removeData(){
			var rows = $("#dataGrid").datagrid('getChecked');
			var ids = [];
			
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].templateId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/page/admin/emailTemplate/emailTemplateAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#dataGrid").datagrid('reload');
									$("#dataGrid").datagrid('unselectAll');
									$.messager.alert('提示',data.msg,'info');
								}
								else{
									$.messager.alert('错误', data.msg, 'error');
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
			} else {
				 $.messager.alert('提示', '请选择要删除的记录！', 'error');
			}
	}
	
	
	
	function viewData(id) {
		var dialog = parent.modalDialog({
			title : '查看邮件模板',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/emailTemplate/view.jsp?id=' + id
		});
	}
	
	
	
	
	function isMail(mail)
    {
    	return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
    }


	function checkMail(emailAddress)
	{
		if(emailAddress != null)
		{
			if(emailAddress.length == 0)
			{
				$.messager.alert('错误', '邮件地址不能是空', 'error');
				return false;
			}
	  		var emailArray = emailAddress.split(";");
			for(var i = 0; i < emailArray.length; i++)
	  		{
	  			if(!isMail(emailArray[i]))
	  			{
	  				$.messager.alert('错误', '无效的邮件格式', 'error');
					return false;
	  			}
	  		}
		}
	
		return true;
	}
	
	
	
	
	
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<th>
									模板名称
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="templateName" name="emailTemplate.templateName" style="width: 150px;" />
								</td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchData();">查询</a>
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
							<authority:authority authorizationCode="新增邮件模板"  userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addData();">新建</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="删除邮件模板"  userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeData();">删除</a>
							</authority:authority>
							</td>
							
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid"></table>
	</div>
</div>
  </body>
</html>