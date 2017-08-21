<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String parent_id = request.getParameter("parent_id");
String origin = request.getParameter("origin");

%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新闻子栏目管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
var grid;
$(function(){
	grid=$('#newsGrid').datagrid({
		url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!dataGridNewsType.action?type.parent_id=<%=parent_id %>',
		fit : true,
		border : false,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'id',
		columns:[[  
				{field:'id',checkbox : true}, 
			    {field:'name',title:'名称',width:150,align:'center'}, 
			    {field:'parent_id',title:'级别',width:60,align:'center',
			    	formatter: function(value,row,index){
						if(value==0){
							return "一级";
						}else{
							return "二级";
						}
					}	
			    },
			    {field:'parent_name',title:'父栏目',width:100,align:'center'},
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
			    {field:'isNavigation',title:'导航显示',width:50,align:'center',
			    	formatter: function(value,row,index){
						if(row.isNavigation==0){
							return "×";
						}else if(row.isNavigation==1){
							return "√";
						}
					}
			    },
			    {field:'orderNum',title:'排序编号',width:50,align:'center'}, 
			    {field:'operator',title:'操作',width:150,align:'center',
			    		formatter: function(value,row,index){
							var content="";
							<authority:authority authorizationCode="查看手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="viewType1('+row.id+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
			    			</authority:authority>
			    			<authority:authority authorizationCode="编辑手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
			    			content+='<a href="javascript:void(0)" onclick="editType('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
			    			</authority:authority>
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
		  $('#newsGrid').datagrid('load',serializeObject($('#searchNewsForm')));
	  }
}

	/**--新增类型--**/
	function addType() {
		var dialog = parent.modalDialog({
			title : '新增二级栏目',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/newsType/add.jsp?parent_id=<%=parent_id%>&origin=<%=origin%>',
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
	



	/**--删除--**/
	function removeType(){
		var rows = $("#newsGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '确定删除吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!deleteNewsType.action?parent_id=<%=parent_id %>',
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
	}

	
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="newsToolbar" style="display: none;">
		<table>
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
							<td>
							<authority:authority authorizationCode="删除手机新闻栏目" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeType();">删除</a>
							</authority:authority>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="newsGrid"></table>
	</div>
</div>
  </body>
</html>