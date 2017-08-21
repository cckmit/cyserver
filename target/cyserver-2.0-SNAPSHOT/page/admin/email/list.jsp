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
		    url:'${pageContext.request.contextPath}/page/admin/email/emailAction!dataGrid.action', 
		  	fit : true,
			border : false,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	idField : 'emailId',
		    columns:[[
				//{field:'emailId',checkbox : true}, 
		        {field:'emailSubject',title:'标题',width:200,align:'center',
		        	formatter : function(value, row) {
						if (value != null) {
							return "<span title='"+value+"'>"
									+ value + "</span>"
						} else {
							return ""
						}
					}	
		        }, 
		        {field:'fromAddress',title:'发件人',width:200,align:'center'},
		        {field:'toAddress',title:'收件人',width:200,align:'center',
		        	formatter : function(value, row) {
						if (value != null) {
							return "<span title='"+value+"'>"
									+ value + "</span>"
						} else {
							return ""
						}
					}	
		        },
		        {field:'createDateTime',title:'发送时间',width:150,align:'center'},
		        {
					width : '80',
					title : '邮件状态',
					align : 'center',
					field : 'sent',
					formatter : function(value, row) {
						if(value==1){
							return "<span class='label label-primary'>已发送</span>";
						}
						else{
							return "<span class='label label-danger'>发送失败</span>";
						}
					}
				},
				{
					width : '80',
					title : '发送者账号',
					align : 'center',
					field : 'userAccount'
				},
		    	{field:'operator',title:'操作',width:150,
		    		formatter: function(value,row,index){
		    			var content="";
		    			<authority:authority authorizationCode="查看邮件"  userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="viewData('+row.emailId+')"><span class="iconImg ext-icon-note"></span>查看</a>&nbsp;';
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
			  if ($('#searchForm').form('validate')) {
			  	
				  $('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
	}
	
	function addData() {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 600,
			title : '新建',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/email/add.jsp',
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
			width : 1000,
			height : 600,
			title : '编辑',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/email/emailAction!initUpdate.action?id=' + id,
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
							ids.push(rows[i].emailId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/page/admin/email/emailAction!delete.action',
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
			width : 1000,
			height : 600,
			title : '查看邮件',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/email/view.jsp?id=' + id
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
	  		var emailArray = emailAddress.split(",");
			for(var i = 0; i < emailArray.length; i++)
	  		{
	  			if(!isMail(emailArray[i].trim()))
	  			{
	  				$.messager.alert('错误', '无效的邮件格式', 'error');
					return false;
	  			}
	  		}
		}
	
		return true;
	}
	
	
	String.prototype.trim=function(){return this.replace(/(^\s*)|(\s*$)/g, "");};
	

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
									标题
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="emailSubject" name="email.emailSubject" style="width: 150px;" />
								</td>
								<th>
									邮件状态
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<!-- <input id="sentStatus" name="email.sentStatus" style="width: 150px;" /> -->
									<select id="sentStatus" name="email.sentStatus" style="width: 150px;">
										<option value="">--全部--</option>
										<option value="0">发送失败</option>
										<option value="1">发送成功</option>
									</select>
								</td>
								<th>
									发送时间
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="email.sentStartTime" readonly="readonly" style="width: 120px;" 
									onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"/>
									-
									<input name="email.sentEndTime" readonly="readonly" style="width: 120px;" 
									onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"/>
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
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid"></table>
	</div>
</div>
  </body>
</html>