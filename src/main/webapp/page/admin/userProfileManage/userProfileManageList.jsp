<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
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
		var userProfileGrid;
		$(function(){
			userProfileGrid = $('#userProfileGrid').datagrid({
				url : '${pageContext.request.contextPath}/userProfilesss/userProfilesssAction!dataGrid.action',
				fit : true,
				border : false,
				striped : true,
				rownumbers : true,
				pagination : true,
				singleSelect : true,
				idField : 'accountNum',
				columns : [ [
					{
						width : '100',
						title : '帐号',
						field : 'accountNum',
						align:'center'
					},
					{
						width : '180',
						title : '姓名',
						field : 'name',
						align:'center'
					}, {
						width : '150',
						title : '电话号码',
						field : 'phoneNum',
						align:'center'
					},{
						width : '150',
						title : '是否认证',
						field : 'baseInfoId',
						align:'center',
						formatter: function(value,row,index){

							if(value != null){
								return "是";
							}else{
								return "否";
							}
						}
					},
					{
						width : '150',
						title : '认证数',
						field : 'number',
						align:'center',
						formatter: function(value,row,index){

						if(value != null){
							return value;
						}else{
							return 0;
						}
					}
					},
					{
						title : '操作',
						field : 'action',
						width : '120',
						formatter : function(value, row) {
							var str = '';
							str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.accountNum + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							<%--<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除帐号">
								str += '<a href="javascript:void(0)" onclick="removeFun(' + row.accountNum + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
							</authority:authority>--%>
							return str;
						}
					}
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
				});
		});
		function searchUserProfile(){
			  if ($('#searchForm').form('validate')) {
				  $('#userProfileGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
		}
		  function removeFun(id){
					parent.$.messager.confirm('确认', '确定删除吗？', function(r) {
						if (r) {
							$.ajax({
								url : '${pageContext.request.contextPath}/userProfilesss/userProfilesssAction!delete.action',
								data : {
									ids : id
								},
								dataType : 'json',
								success : function(data) {
									if(data.success){
										$("#userProfileGrid").datagrid('reload');
										$("#userProfileGrid").datagrid('unselectAll');
										parent.$.messager.alert('提示',data.msg,'info');
									}
									else{
										parent.$.messager.alert('错误', data.msg, 'error');
									}
								},
								beforeSend:function(){
									parent.$.messager.progress({
										text : '数据提交中....'
									});
								},
								complete:function(){
									parent.$.messager.progress('close');
								}
							});
						}
					});
			}
			
			
	var viewFun = function(accountNum)
	{
		var dialog = parent.parent.WidescreenModalDialog({
			title : '查看帐号',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/admin/userProfileManage/viewUserProfile.jsp?accountNum=' + accountNum
		});
	}

	function resetT()
	{
		$('#searchForm')[0].reset();
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
									<th>
										用户帐号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userProfile.accountNum" style="width: 150px;" />
									</td>
									<th>
										用户姓名
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userProfile.name" style="width: 150px;" />
									</td>
									<th>
										电话号码
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userProfile.phoneNum" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="searchUserProfile();">查询</a>
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
										   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
										   onclick="resetT();">重置</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="userProfileGrid"></table>
		</div>
	</body>
</html>
