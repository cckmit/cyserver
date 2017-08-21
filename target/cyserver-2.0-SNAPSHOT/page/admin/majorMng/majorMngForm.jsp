<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
	var countGrid;
	$(function() {
		countGrid = $('#countGrid').datagrid({
			url : '${pageContext.request.contextPath}/majormng/majorMngAction!getList.action',
			fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			idField: 'id',
			columns : [ [
			{field:'id',checkbox : true},      
			{title : '专业名称',	field : 'specialtyName',align:'center'}, 
			{title : '学校名称',	field : 'schoolName',align:'center'},
			{title : '学院名称',	field : 'facultyName',align:'center'},
			{title : '年级名称',	field : 'gradeName',align:'center'}
			] ],
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
			/* onLoadError : function(data){
				$('.iconImg').attr('src', pixel_0);
				parent.$.messager.progress('close');
			} */
		});
	});
	
	function removeData(){
		var rows = $("#countGrid").datagrid('getChecked');
		var ids = [];
		
		if (rows.length > 0) {
			$.messager.confirm('确认', '确定删除吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/majormng/majorMngAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(data) {
							if(data.success){
								$("#countGrid").datagrid('reload');
								$("#countGrid").datagrid('unselectAll');
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

	
	function searchResult(){
		if ($('#searchForm').form('validate')) {
		  	 
			$('#countGrid').datagrid('load',serializeObject($('#searchForm')));
		}
	}

</script>
	</head>

	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									
									<th align="right">
										专业名称
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
					                    <input name="formData.specialtyName" style="width: 150px;"/>
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="searchResult();">查询</a>
								sessionScope.user.role						</tr>
								<tr>
									<td colspan="3">
										<authority:authority authorizationCode="删除专业" userRoles="${sessionScope.user.userRoles}">
											<a href="javascript:void(0);" class="easyui-linkbutton"
												data-options="iconCls:'ext-icon-note_delete',plain:true"
												onclick="removeData();">删除专业</a>
										</authority:authority>
									</td>
								</tr>
								
							</table>
						</form>
					</td>
				</tr>
				
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="countGrid"></table>
		</div>
	</body>
</html>
