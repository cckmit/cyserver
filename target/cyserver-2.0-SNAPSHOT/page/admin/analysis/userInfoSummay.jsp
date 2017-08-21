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
    
    <title>校友统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var actionName = "analysisAction";
	var actionUrl = "${pageContext.request.contextPath}/analysis/";
	var actionFullPath = actionUrl + actionName;
	var grid;
	$(function(){
		grid=$('#dataGrid').datagrid({  
		    url: actionFullPath + '!userInfoSummary.action',
		  	fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	idField : 'id',
		    columns:[[
		        {field:'name',title:'统计',width:30,align:'center'},
		        {field:'count',title:'总人数',width:30,align:'center'}
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
		$('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
	}
	
	/**--重置--**/
	function resetT(){
			$('#school').combobox('clear');
			$('#depart').combobox('clear');
			$('#grade').combobox('clear');
			$('#classes').combobox('clear');
			
			$('#major').combobox('clear');
			$('#studentType').combobox('clear');
			$('#classes').combobox('loadData',[]);
			$('#grade').combobox('loadData',[]);
			$('#major').combobox('loadData',[]);
			$('#depart').combobox('loadData',[]);
			$('#searchForm')[0].reset();
			
			
			$('#schoolId').prop('value','');
			$('#departId').prop('value','');
			$('#gradeId').prop('value','');
			$('#classId').prop('value','');
			
			$('#name').prop('value','');
			$('#sex').combobox('clear');
			$('#location').combobox('clear');
			
	}
	
	
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid"></table>
	</div>
</div>
  </body>
</html>