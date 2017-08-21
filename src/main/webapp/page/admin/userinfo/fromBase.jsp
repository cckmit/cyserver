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
			url : '${pageContext.request.contextPath}/userBaseInfo/userBaseInfoAction!doNotNeedSecurity_dataGridFromBase.action',
			fit : true,
			method : 'post',
			border : false,
			striped : true,
			pagination : true,
			columns : [ [
			{
				width : '120',
				title : '学号',
				field : 'studentnumber',
				align : 'center'
			},
			{
				width : '80',
				title : '姓名',
				field : 'userName',
				align : 'center'
			}, {
				width : '100',
				title : '学校',
				field : 'schoolName',
				align : 'center'
			},
			{
				width : '100',
				title : '院系',
				field : 'departName',
				align : 'center'
			},
			{
				width : '100',
				title : '年级',
				field : 'gradeName',
				align : 'center'
			},
			{
				width : '100',
				title : '班级',
				field : 'className',
				align : 'center'
			},
			{
				width : '100',
				title : '专业',
				field : 'majorName',
				align : 'center'
			}
			] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param)
			{
				parent.parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data)
			{
				parent.parent.$.messager.progress('close');
			}
		});
	});

	function searchUserInfo()
	{
		$('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
	}
	
	function importFromBase(){
		$.ajax({
			url : '${pageContext.request.contextPath}/userInfo/userInfoAction!saveFromBase.action',
			data : $('form').serialize(),
			dataType : 'json',
			success : function(result)
			{
				if (result.success)
				{
					$("#userInfoGrid").datagrid('reload');
					parent.$.messager.alert('提示', result.msg, 'info');
				} else
				{
					parent.$.messager.alert('提示', result.msg, 'error');
				}
			},
			beforeSend : function()
			{
				parent.$.messager.progress({
					text : '数据提交中....'
				});
			},
			complete : function()
			{
				parent.$.messager.progress('close');
			}
		})
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
					<td>
						<form id="searchForm">
							<table>
								<tr>
								<th align="right" width="30px;">学校</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="schoolId" id="schoolId" type="hidden">
										<input name="departId" id="departId" type="hidden">
										<input name="gradeId" id="gradeId" type="hidden">
										<input name="classId" id="classId" type="hidden">
										<input id="school" class="easyui-combobox" style="width: 150px;"
											data-options="  
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',  
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId; 
													$('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);
													$('#depart').combobox('reload', url);  
													$('#schoolId').prop('value',rec.deptId);
													$('#departId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
										}" />
									</td>
									<th align="right" width="30px;">院系</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="depart" class="easyui-combobox" style="width: 150px;"
											data-options="  
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
													var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('reload', url);  
													$('#major').combobox('clear');
													$('#major').combobox('reload', url1);
													$('#departId').prop('value',rec.deptId);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
										}" />
									</td>
									<th align="right" width="30px;">年级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="grade" class="easyui-combobox" style="width: 150px;"
											data-options="  
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
												onSelect: function(rec){  
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);
													$('#gradeId').prop('value',rec.deptId);
													$('#classId').prop('value','');  
										}" />
									</td>
									<th align="right" width="30px;">班级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="classes" class="easyui-combobox" style="width: 150px;"
											data-options="
												editable:false,
												valueField:'deptId',
												textField:'deptName',
												onSelect: function(rec){  
													$('#classId').attr('value',rec.deptId)  
												}
												"/>
									</td>
								<tr>
									<th align="right" width="30px;">专业</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="major" name="userInfo.majorId" class="easyui-combobox" style="width: 150px;"
											data-options="  
												valueField: 'majorId',  
												textField: 'majorName',  
												editable:false" />
									</td>
									<th align="right">学历</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="studentType" class="easyui-combobox" style="width: 150px;" name="userInfo.studentType"
											data-options="  
											valueField: 'dictName',  
											textField: 'dictName',  
											editable:false,
											url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历') 
										" />
									</td>
									<th align="right">
										姓名
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.userName" style="width: 150px;" />
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
								</tr>
								<tr>
									<th align="right">
										工作地区
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.residentialArea" style="width: 150px;" />
									</td>
									<th align="right">
										工作单位
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.workUnit" style="width: 150px;" />
									</td>
									<th align="right">
										联系地址
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.mailingAddress" style="width: 150px;" />
									</td>
									<td colspan="3">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>&nbsp;
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFromBase()">按搜索条件导入</a>
									</td>
								</tr>
							</table>
						</form>
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
