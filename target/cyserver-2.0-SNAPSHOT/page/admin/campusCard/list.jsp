<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
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
	var actionName = "campusCardAction";
	var actionUrl = "${pageContext.request.contextPath}/page/admin/campusCard/";
	var actionFullPath = actionUrl + actionName;
	var grid;
	$(function(){
		grid=$('#dataGrid').datagrid({  
		    url: actionFullPath + '!dataGrid.action', 
		  	fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	idField : 'id',
		    columns:[[
				{field:'id',checkbox : true}, 
		        {field:'name',title:'商户名称',width:150,align:'center'}, 
		        {field:'industry',title:'所属行业',width:50,align:'center'},
		        {field:'location',title:'所在地',width:50,align:'center'},
		        {field:'contact',title:'联系人',width:50,align:'center'},
		        {field:'contactTel',title:'联系电话',width:50,align:'center'},
		        {field:'fax',title:'传真',width:50,align:'center'},
		        {field:'emailBox',title:'邮箱',width:50,align:'center'},
		        {field:'status',title:'审核状态',width:50,align:'center',
			        formatter: function(value,row,index){
			    			var content="";
			    			if(value==0){
			    				content="未审核";
			    			}
			    			if(value==1){
			    				content="已通过";
			    			}
			    			if(value==2){
			    				content="未通过";
			    			}
			    			return content;
					}	
		        },
		    	{field:'operator',title:'操作',width:50,
		    		formatter: function(value,row,index){
		    			var content="";
		    			<authority:authority authorizationCode="查看校园卡" userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="viewData('+row.id+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
		    			</authority:authority>
		    			<authority:authority authorizationCode="编辑校园卡" userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="editData('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
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
				$('.iconImg').attr('src', pixel_0);
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
			url : actionUrl + 'add.jsp',
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
			url : actionFullPath + '!doNotNeedSecurity_initUpdate.action?id=' + id,
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
							ids.push(rows[i].id);
						}
						$.ajax({
							url : actionFullPath + '!delete.action',
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
			title : '查看',
			iconCls:'ext-icon-note_add',
			url : actionFullPath + '!getById.action?id=' + id
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

	/**--重置--**/
	function resetT(){

		$('#searchForm')[0].reset();


		$('#name').prop('value','');
		$('#industry').prop('value','');
		$('#city').combobox('clear');
		$('#provience').combobox('clear');
		$('#status').combobox('clear');

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
								<th align="right">
									商户名称
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="formData.name" style="width: 150px;" />
								</td>
								
								<th align="right">
									所属行业
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="formData.industry" style="width: 150px;" />
								</td>
								
								<th align="right">
									所在地
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input class="easyui-combobox" name="province" id="province" style="width: 150px"
										data-options="
					                    method:'post',
					                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSessionAndSecurity_getProvince2ComboBox.action?countryId=1',
					                    valueField:'provinceName',
					                    textField:'provinceName',
					                    editable:false,
					                    prompt:'省',
					                    icons:[{
							                iconCls:'icon-clear',
							                handler: function(e){
							                	$('#province').combobox('clear');
							                	$('#city').combobox('clear');
							                	$('#city').combobox('loadData',[]);
							                }
							            }],
							            onSelect: function(rec){
											var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSessionAndSecurity_getCity2ComboBox.action?provinceId='+rec.id; 
											$('#city').combobox('clear');	
											$('#city').combobox('reload', url);
										}
				                    	">
				                    	&nbsp; <input class="easyui-combobox" name="city" id="city" style="width: 150px"
										data-options="
					                    method:'post',
					                    valueField:'cityName',
					                    textField:'cityName',
					                    editable:false,
					                    prompt:'市',
					                    icons:[{
							                iconCls:'icon-clear',
							                handler: function(e){
							                	$('#city').combobox('clear');
							                }
							            }]
				                    	">
								</td>
								<td>
									审核状态
								</td>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select name="formData.status" class="easyui-combobox" style="width:155px" 
										data-options="required:true, editable:false"
									>
									<option value="-1">全部</option>
									<option value="0">未审核</option>
									<option value="1">已通过</option>
									<option value="2">未通过</option>
									</select>	
								</td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchData();">查询</a>
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-huifu',plain:true"
									   onclick="resetT();">重置</a>
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
							<authority:authority authorizationCode="删除校园卡" userRoles="${sessionScope.user.userRoles}">
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