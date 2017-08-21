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
	var actionName = "alumniCardAction";
	var actionUrl = "${pageContext.request.contextPath}/page/admin/alumniCard/";
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
		        {field:'name',title:'姓名',width:30,align:'center'},
		        {field:'accountNum',title:'状态',width:30,align:'center',
		        	formatter : function(value) {
						if(value!=''&&value!=undefined){
							return "<font style='color: green;'>校友</font>";
						}
						else{
							return "<font style='color: red;'>未登录用户</font>";
						}
					}	
		        }, 
		        {field:'sex',title:'性别',width:20,align:'center',
                    formatter : function(value) {
                        if(value == '1'){
                            return "女";
                        }
                        else if(value == "0"){
                            return "男";
                        }else{
                            return "";
						}
                    }
				},
		        {field:'address',title:'联系地址',width:50,align:'center'},
		        {field:'phone',title:'联系电话',width:50,align:'center'},
                {field:'workUnit',title:'工作单位',width:50,align:'center'},
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
		    			<authority:authority authorizationCode="查看校友卡" userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="viewData(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
		    			</authority:authority>
		    			<authority:authority authorizationCode="编辑校友卡" userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="editData(\''+row.id+'\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
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
			url : actionFullPath + '!doNotNeedSecurity_initUpdate.action?alumniCardId=' + id,
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
			url : actionFullPath + '!getById.action?alumniCardId=' + id
		});
	}
	
	/**--重置--**/
	function resetT(){

			$('#searchForm')[0].reset();

			
			$('#name').prop('value','');
			$('#sex').combobox('clear');
			$('#location').combobox('clear');
			
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
									姓名
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="name" name="formData.name" style="width: 150px;" />
								</td>
								
								<th>
									性别
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="sex" name="formData.sex" class="easyui-combobox" style="width:150px" data-options="editable:false">
									<option value="">全部</option>
									<option value="0">男</option>
									<option value="1">女</option>
									</select>	
								</td>
								
								<th>
									联系地址
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="formData.address">
								</td>
								<th>
									审核状态
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select name="formData.status" class="easyui-combobox" style="width:155px" 
										data-options="required:true, editable:false"
									>
									<option value="">全部</option>
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
							<authority:authority authorizationCode="删除校友卡" userRoles="${sessionScope.user.userRoles}">
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