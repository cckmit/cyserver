<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
    
    <title>新闻栏目管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function(){
	grid=$('#newsGrid').treegrid({
		url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!dataGridNewsType.action',
	//	fitColumns:true,
		idField : 'id',
		fit : true,
		singleSelect:false,
		treeField : 'name',
		parentField : 'parent_id',
		border : false,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		pagination : false,

		/*frozenColumns : [ [ {
			width : '180',
			title : '名称',
			field : 'name'
		} ] ],*/
		
		columns:[[ 
				{field:'deptId',checkbox:true},
				//{field:'id',checkbox : true},
			   {field:'name',title:'名称',width:150,
					formatter: function(value,row,index){
						if(row.parent_id==0){
							return value;
						}else{
							return value;
						}
					}
			    },
			   /* {field:'parent_id',title:'级别',width:60,align:'center',
			    	formatter: function(value,row,index){
						if(value==0){
							return "一级";
						}else{
							return "二级";
						}
					}
			    },*/
			    {field:'type',title:'类型',width:100,align:'center',
			    	formatter: function(value,row,index){
						if(value==1){
							return "新闻类别";
						}else if(value==2){
							return "链接";
						}else if(value==3){
							return "单页面";
						}
					}
			    },
				/*lixun 添加*/
				{field:'channelName',title:'渠道',width:100,align:'center'
				},
			    /*
			    {field:'origin',title:'来源',width:100,align:'center',
			    	formatter: function(value,row,index){
						if(value==1){
							return "总会";
						}else if(value==2){
							return "地方";
						}
					}
			    },
			    {field:'cityName',title:'地域',width:100,align:'center',
			    	formatter: function(value,row,index){
						if(row.origin==1){
							return "---";
						}else if(row.origin==2){
							return value;
						}
					}
			    },*/
			    {field:'isNavigation',title:'导航显示',width:80,align:'center',
			    	formatter: function(value,row,index){
						if(row.isNavigation==0){
							return "×";
						}else if(row.isNavigation==1){
							return "√";
						}
					}
			    },
			{field:'isMain',title:'是否上首页',width:80,align:'center',
				formatter: function(value,row,index){
					if(row.isMain==0){
						return "×";
					}else if(row.isMain==1){
						return "√";
					}
				}
			},
			    {field:'deptName',title:'创建组织',width:80,align:'center'},
			    {field:'orderNum',title:'排序编号',width:80,align:'center'},
			    {field:'operator',title:'操作',width:300,
			    		formatter: function(value,row,index){

							var content="";
							<authority:authority authorizationCode="查看手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="viewType1('+row.id+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
			    			</authority:authority>
			    			<authority:authority authorizationCode="编辑手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="editType('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
			    			</authority:authority>
			    			<authority:authority authorizationCode="删除手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="removeType('+row.id+')"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
			    			</authority:authority>
			    			if( row.parent_id == 0 &&  row.type==1){
			    				<authority:authority authorizationCode="新增手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
				    			content+='<a href="javascript:void(0)" onclick="addType2('+row.id+','+row.origin+',\''+row.name+'\','+row.channel+',\''+row.channelName+'\')"><img class="iconImg ext-icon-note"/>添加子栏目</a>&nbsp;';
				    			</authority:authority>
			    			}
			    			return content;
					}}
			    ]],
				toolbar : '#newsToolbar',
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

	function searchNews(){
		  if ($('#searchNewsForm').form('validate')) {
			  $('#newsGrid').treegrid('load',serializeObject($('#searchNewsForm')));
		  }
	}
	
	/**--重置--**/
	function resetT(){
		$('#channel').combobox('clear');
		$('#channel').combobox("loadData",[]);
		$("#cityName").attr("disabled",true);	
		$('#searchNewsForm')[0].reset();
		$('#type').combobox('clear');
		$('#origin').combobox('clear');

	}

	/**--新增类型--**/
	function addType() {
		var dialog = parent.modalDialog({
			title : '新增栏目',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/add.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	/**--新增子栏目--**/
	function addType2(id,origin,name,channel,channelName) {
		var dialog = parent.modalDialog({
			title : '新增二级栏目',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/add.jsp?parent_id=' + id+'&origin='+origin+'&parent_name='+name+'&channel='+channel+'&channelName='+channelName,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	/**--编辑类型--**/
	function editType(id) {
		var dialog = parent.modalDialog({
			title : '编辑',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/edit.jsp?id='+id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}




	/**--查看类型详情--**/
	function viewType1(id) {
		var dialog = parent.modalDialog({
			title : '查看',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/view.jsp?id=' + id
		});
	}
	
	/**--查看子类型--**/
	function viewType2(id,origin) {
		var dialog = parent.modalDialog({
			title : '子栏目',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/list2.jsp?parent_id=' + id+'&origin='+origin,
		});
	}
	
	/**--推送栏目数据到网站后台--**/
	function addToWebPageDB() {
		var rs =window.confirm("您确定要推送新闻的栏目数据到网站后台数据库吗？");
		if(rs){
			
			$.ajax({
				url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!addToWebpageDB.action',
				dataType : 'json',
				success : function(data) {
					if(data.success){
						$.messager.alert('提示',data.msg,'info');
					}
					else{
						$.messager.alert('错误', data.msg, 'error');
					}
				},
				beforeSend:function(){
					$.messager.progress({
						text : '正在推送栏目数据到网站后台数据库......'
					});
				},
				complete:function(){
					$.messager.progress('close');
				}
			});
		}
	}


/**--删除--**/
/*function removeType(){
	
	var rows = $("#newsGrid").datagrid('getChecked');
	var ids = [];
	if (rows.length > 0) {
		$.messager.confirm('确认', '确定删除吗？', function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!deleteNewsType.action',
					data : {
						ids : ids.join(',')
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							$("#newsGrid").datagrid('reload');
							$("#newsGrid").datagrid('unselectAll');
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
}*/
//改为单行删除
function removeType(id){
	$.messager.confirm('确认', '确定删除吗？', function(r) {
		if (r) {
			$.ajax({
				url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!deleteNewsType.action',
				data : {
					ids : id
				},
				dataType : 'json',
				success : function(data) {
					if(data.success){
						grid.treegrid('reload');
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
}

function setMobTypeList(isRmv){
	var rows = $("#newsGrid").datagrid('getChecked');
	var str1="";
	var str2="";

	if(isRmv){
		str1="设置";
	}else{
		str1="取消";
	}
	var tmpAlert = "确定要"+str1+"所选记录"+str2+"上导航吗?";
	var ids = [];
	if (rows.length > 0) {
		$.messager.confirm('确认', tmpAlert, function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!setMobTypeList.action',
					data : {
						ids : ids.join(','),
						isRmv : isRmv
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							grid.treegrid('reload');
							$("#newsGrid").treegrid('unselectAll');
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
		$.messager.alert('提示', '请选择要设置的记录！', 'error');
	}
}

function setMobTypePage(isUp){
	var rows = $("#newsGrid").datagrid('getChecked');
	var str1="";
	var str2="";

	if(isUp){
		str1="设置";
	}else{
		str1="取消";
	}
	var tmpAlert = "确定要"+str1+"所选记录"+str2+"上首页吗?";
	var ids = [];
	if (rows.length > 0) {
		$.messager.confirm('确认', tmpAlert, function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!setMobTypePage.action',
					data : {
						ids : ids.join(','),
						isUp : isUp
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							grid.treegrid('reload');
							$("#newsGrid").treegrid('unselectAll');
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
		$.messager.alert('提示', '请选择要设置的记录！', 'error');
	}
}

	function selectOrigin(){
		var value = $("#origin").val();
		if(value=="2"){
			$("#cityName").attr("disabled",false);
		}else{
			$("#cityName").attr("disabled",true);
			$("#cityName").val("");
		}
	}
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="newsToolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchNewsForm">
						<table>
							<tr>
								<th>
									名称
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="type.name" style="width: 150px;" />
								</td>



								<%--<th>
									类型
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="type" name="type.type" style="width: 150px;">
										<option value="">&nbsp;</option>
										<option value="1">新闻类别</option>
										<option value="2">链接</option>
										<option value="3">单页面</option>
									</select>
								</td>--%>

								<th >
									类型
								</th>

								<td>
									<select id="type" name="type.type"  class="easyui-combobox" style="width: 150px;"
											data-options="
												valueField: 'dictValue',
												textField: 'dictName',
												editable:false,
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#qudao').combobox('clear');
									                $('#newsType').combotree('clear');
													  $('#newsType').combotree('loadData',[]);

									                }
									            }],
												url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 119,
												" />
								</td>



								<%--<th>
									渠道
								</th>
								<td>
									<select id="channel" name="type.channel" style="width: 150px;">
										<option value="">&nbsp;</option>
										<option value="10">手机</option>
										<option value="20">网页</option>
										<option value="30">微信</option>
									</select>
								</td>
--%>


								<th >
									渠道
								</th>

								<td>
									<select id="channel" name="type.channel" class="easyui-combobox" style="width: 150px;"
											data-options="
												valueField: 'dictValue',
												textField: 'dictName',
												editable:false,
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#qudao').combobox('clear');
									                $('#newsType').combotree('clear');
													  $('#newsType').combotree('loadData',[]);

									                }
									            }],
												url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 110,
												" />
								</td>











































								<!-- 
								<th>
									来源
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="origin" name="type.origin"  style="width: 150px;" onchange="selectOrigin();">
										<option value="">&nbsp;</option>
										<option value="1">总会</option>
										<option value="2">地方</option>
									</select>
								</td>
								
								<th>
									地域
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="cityName" name="type.cityName" style="width: 150px;" disabled="disabled" />
								</td>
								 -->
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchNews();">查询</a>
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
							<authority:authority authorizationCode="新增手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addType();">新增</a>
							</authority:authority>
							</td>
							<!--td>
							<authority:authority authorizationCode="删除手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeType();">删除</a>
							</authority:authority>
							</td-->
							<%--<td>
							<authority:authority authorizationCode="推送栏目数据到网站后台" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addToWebPageDB();">推送栏目数据到网站后台</a>
							</authority:authority>
							</td>--%>
							<td>
								<authority:authority authorizationCode="导航显示" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-standard-film-add',plain:true"
									   onclick="setMobTypeList(true);">设置导航显示</a>
								</authority:authority>

							</td>
							<td>
								<authority:authority authorizationCode="导航显示" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-standard-film-delete',plain:true"
									   onclick="setMobTypeList(false);">取消导航显示</a>
								</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="设置首页" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-standard-film-add',plain:true"
									   onclick="setMobTypePage(true);">设置上首页</a>
								</authority:authority>

							</td>
							<td>
								<authority:authority authorizationCode="设置首页" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-standard-film-delete',plain:true"
									   onclick="setMobTypePage(false);">取消上首页</a>
								</authority:authority>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="newsGrid" data-options="fit:true,border:false"></table>
	</div>
	
</div>
  </body>
</html>