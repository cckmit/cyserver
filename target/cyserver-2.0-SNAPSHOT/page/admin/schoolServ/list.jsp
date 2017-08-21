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
	var actionName = "schoolServAction";
	var actionUrl = "${pageContext.request.contextPath}/mobile/schoolServ/";
	
	var actionFullPath = actionUrl + actionName;
	
	/** 
	 * 时间对象的格式化; 
	 */  
	Date.prototype.format = function(format) 
	{
	    /* 
	     * eg:format="yyyy-MM-dd hh:mm:ss"; 
	     */  
	    var o = 
	    {  
	        "M+" : this.getMonth() + 1, // month  
	        "d+" : this.getDate(), // day  
	        "h+" : this.getHours(), // hour  
	        "m+" : this.getMinutes(), // minute  
	        "s+" : this.getSeconds(), // second  
	        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
	        "S" : this.getMilliseconds()  
	        // millisecond  
	    };  
	  
	    if (/(y+)/.test(format)) 
	    {  
	        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
	    }  
	  
	    for (var k in o) 
	    {
	        if (new RegExp("(" + k + ")").test(format)) 
	        {  
	            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
	                            ? o[k]  
	                            : ("00" + o[k]).substr(("" + o[k]).length));  
	        }  
	    }
	    return format;
	};
	
	var systemService = new Array();
	
	var grid;
	$(function(){
		grid=$('#dataGrid').datagrid({  
		    url: actionFullPath + '!dataGrid.action', 
		  	fit : true,
			border : false,
//			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	idField : 'id',
		    columns:[[
				{field:'id',checkbox : true}, 
		        {field:'serviceName',title:'服务名称',width:100,align:'center'},
		        {field:'systemService',title:'服务类型',width:100,align:'center',
		        
		        formatter: function(value,row,index){
		    			var content="";
		    			if(value==0){
		    				content='自定义服务';
		    			}
		    			if(value==1){
		    				systemService.push(row.id);
		    				content='<font color="red">系统服务</font>';
		    			}
		    			
		    			return content;
					}	
		        
		        },
		        {field:'provideService',title:'服务状态',width:100,align:'center',
		        
		        formatter: function(value,row,index){
		    			var content="";
		    			if(value==0){
		    				content="停止";
		    			}
		    			if(value==1){
		    				content="开启";
		    			}
		    			
		    			return content;
					}	
		        
		        },
		        {field:'createBy',title:'创建者',width:150,align:'center'},
		        {field:'createDate',title:'创建日期',width:200,align:'center',
		        formatter:function(value,row,index){
		        		
		        		if (value == undefined || value == '') {
				            return "";
				        }
						
				        var dateValue = new Date(value);
				        if (dateValue.getFullYear() < 1900) {
				            return "";
				        }
				
				        return dateValue.format("yyyy-MM-dd hh:mm:ss");
                        }  
		        
		        },
				{field:'sort',title:'排序',width:80,align:'center'},
				{field:'deptId',title:'当前部门',width:80,align:'center',hidden:'true'},

		        
		    	{field:'operator',title:'操作',width:150,
		    		formatter: function(value,row,index){
		    			var content="";
		    			<authority:authority authorizationCode="查看配置" userRoles="${sessionScope.user.userRoles}">
		    			content+='<a href="javascript:void(0)" onclick="viewData('+row.id+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
		    			</authority:authority>
						if(row.deptId == 0){
							<authority:authority authorizationCode="编辑配置" userRoles="${sessionScope.user.userRoles}">
							content+='<a href="javascript:void(0)" onclick="editData('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							</authority:authority>
						}else if(row.systemService == 0){
							<authority:authority authorizationCode="编辑配置" userRoles="${sessionScope.user.userRoles}">
							content+='<a href="javascript:void(0)" onclick="editData('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							</authority:authority>
						}
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
				//alert(JSON.stringify(data));
				parent.$.messager.progress('close');
				
				
			}
		}); 
	});



	function searchData(){
			  if ($('#searchForm').form('validate')) {
			  	 
				  $('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
	}

	/**--重置--**/
	function resetT(){
		$('#searchForm')[0].reset();
		$('#type').combobox('clear');
	}
	

	
	function addData() {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 600,
			title : '新建',
			iconCls:'ext-icon-note_add',
			url : actionFullPath + '!initUpdate.action',
			<%--url : '${pageContext.request.contextPath}/page/admin/schoolServ/add.jsp',--%>
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
		
		/*if(isSystemService(id))
		{
			$.messager.alert('提示', '不允许对系统服务进行操作', 'info');
			return;
		}*/
	
		var dialog = parent.modalDialog({
			width : 1000,
			height : 600,
			title : '编辑',
			iconCls:'ext-icon-note_add',
			url : actionFullPath + '!initUpdate.action?id=' + id,
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
							
							if(isSystemService(rows[i].id))
							{
								$.messager.alert('提示', '不允许对系统服务进行操作', 'info');
								return;
							}
						
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
	
	
	
	
	
	function isSystemService(id)
	{
		for(var i = 0; i < systemService.length; i++)
		{
			if(id == systemService[i])
			{
				return true;
			}
		}
		
		return false;
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
									服务名称
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="formData.serviceName" style="width: 150px;" />
								</td>
								<th>
									服务类型
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="systemService" name="formData.systemService" style="width: 150px;">
										<option value="">&nbsp;</option>
										<option value="0">自定义服务</option>
										<option value="1">系统服务</option>
									</select>
								</td>
								<th>
									服务状态
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="provideService" name="formData.provideService" style="width: 150px;">
										<option value="">&nbsp;</option>
										<option value="0">停止</option>
										<option value="1">开启</option>

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
							<authority:authority authorizationCode="新增服务" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addData();">新增服务</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="删除服务" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeData();">删除服务</a>
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