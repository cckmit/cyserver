<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
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
	var userInfoGrid;
	$(function()
	{
		userInfoGrid = $('#userInfoGrid').datagrid({
			url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action?classId=${param.classId}',
			fit : true,
			//title : fullName,
			//queryParams : {
			//	'deptId' : deptId
			//},
			method : 'post',
			border : false,
			striped : true,
			pagination : true,
			sortName:'userName',
			sortOrder:'asc',
			columns : [ [
			{
				field : 'userId',
				checkbox : true
			},
			{
				width:'20',
				align:'center',
				field:'isAdmin',
				formatter:function(value,row)
				{
					var isAdmin = row.isClassAdmin ;
					var str = "<img src='${pageContext.request.contextPath}/images/icons/star.png' width='15' height='15' style='margin-top:3px;'>";
					if(isAdmin != '1') {
						str = "" ;
					}
					return str ;
				}
			},
			{
				width : '180',
				title : '姓名',
				field : 'userName'
			},
			{
				width : '100',
				title : '性别',
				field : 'sex'
			},
			{
				width : '180',
				title : '手机号',
				field : 'telId'
			},
			{
				width : '180',
				title : '学号',
				field : 'studentnumber'
			},
			{
				width : '80',
				title : '是否注册',
				field : 'accountNum',
				formatter : function(value, row){
					if(value!=''&&value!=undefined){
						return "<span style='color: green;'>已注册</span>"
					}else{
						return "<span>未注册</span>"
					}
				}
			},
			{
				title : '状态',
				field : 'checkFlag',
				formatter : function(value, row){
					if(value==1){
						return "<span style='color: green;'>正式校友</span>"
					}else{
						return "<span style='color: red;'>待核校友</span>"
					}
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param)
			{
				parent.parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data)
			{
				$('.iconImg').attr('src', pixel_0);
				parent.parent.$.messager.progress('close');
			}
		});


		var classId = '${param.classId}' ;
		var deptId = classId.substring(0,10) ;
		var url= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+deptId;
		$('#major').combobox('clear');
		$('#major').combobox('reload', url);
	});

	function searchUserInfo()
	{
		$('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
	}

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
		$('#province').combobox('clear');
		$('#city').combobox('clear');
		$('#area').combobox('clear');
		$('#city').combobox('loadData',[]);
		$('#area').combobox('loadData',[]);
	}

	/**
	 * 设置班级管理员
     */
	var updateClassAdmin = function ($dialog, $grid, $pjq, isAdmin) {

		var rows = $("#userInfoGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			parent.parent.$.messager.confirm('确认', '确定设置吗？', function(r)
			{
				if (r)
				{
					for ( var i = 0; i < rows.length; i++)
					{
						ids.push(rows[i].userId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!updateClassAdmin.action',
						data : {
							userIds : ids.join(','),
							isClassAdmin : isAdmin
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$grid.datagrid('reload');
								$grid.datagrid('unselectAll');
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else {
								$pjq.messager.alert('提示', result.msg, 'error');
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
		} else
		{
			parent.parent.$.messager.alert('提示', '请选择要设置的记录！', 'error');
		}
	}

</script>
	</head>

	<input type="hidden" id="classId" value="${param.classId}" >
	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
			<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th align="right">
										姓名
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="userName" name="userInfo.userName" style="width: 150px;" />
									</td>
									<th align="right">
										学号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.studentnumber" style="width: 150px;" />
									</td>
									<th align="right">
										手机号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.telId" style="width: 150px;" />
									</td>
									<th align="right">是否注册</td>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select class="easyui-combobox" data-options="editable:false" name="regflag" style="width: 150px;">
											<option value="">--请选择--</option>
											<option value="1">是</option>
											<option value="0">否</option>
										</select>
									</td>
								</tr>
								<tr>

									<td colspan="8">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="userInfoGrid"></table>
		</div>
	</body>
</html>
