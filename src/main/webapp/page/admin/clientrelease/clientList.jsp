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
<!--



var grid;
	$(function(){
		grid=$('#client').datagrid({  
		    url:'${pageContext.request.contextPath}/client/clientAction!getClient.action', 
		  	fit : true,
//		  	fitColumns:true,
		  	pagination:true,
		  	border:false,
		  	nowrap : true,
		  	rownumbers:true,
		  	idField : 'id',
			singleSelect:true,
		    columns:[[  
			{field:'id',hidden : true}, 
	        {field:'version',title:'版本号',width:150,align:'center'},
	        {field:'url',title:'资源地址',width:500,align:'center'},
	        {field:'downloads',title:'下载量',width:130,align:'center'},
			{field:'createTime',title:'发布时间',width:130,align:'center'},
	    	{field:'operator',title:'操作',width:200,align:'center',
	    		formatter: function(value,row,index){
	    			var content="";
	    			<authority:authority authorizationCode="编辑版本" userRoles="${sessionScope.user.userRoles}">
	    			content+='<a href="javascript:void(0)" onclick="editClient('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
	    			</authority:authority>
	    			return content;
			}}
	    ]],toolbar : [ {
				text : '增加',
				iconCls : 'ext-icon-note_add',
				handler : function() {
					addClient();
				}
			}, '-',{
				text : 'APP统一下载路径:<%=basePath%>client/clientAction!doNotNeedSessionAndSecurity_downloadApp.action',
				iconCls : 'ext-icon-arrow_down',
				handler : function() {
					download();
				}
			}],
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




function addClient(){

	var dialog = parent.modalDialog({
			width : 650,
			height : 500,
			title : '发布手机客户端',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/clientrelease/addClient.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
}
function download(){

	location.href = '${pageContext.request.contextPath}/client/clientAction!doNotNeedSessionAndSecurity_downloadApp.action' ;
}


function editClient(id){

	var dialog = parent.modalDialog({
			width : 650,
			height : 500,
			title : '版本发布编辑',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/client/clientAction!showUpdate.action?id='+id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
}

//-->
</script>
</head>

	<body>
		<table id="client"></table>
	</body>
</html>
