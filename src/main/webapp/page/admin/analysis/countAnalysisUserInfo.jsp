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
    
    <title>校友统计分析</title>
    
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
		    url: actionFullPath + '!doNotNeedSecurity_countAnalysisUserInfoDataGrid.action',
		  	fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	idField : 'id',
		    columns:[[
		        {field:'schoolName',title:'学校',width:30,align:'center'},
		        {field:'collegeName',title:'学院',width:30,align:'center'},
		        {field:'gradeName',title:'年级',width:30,align:'center'},
		        {field:'className',title:'班级',width:30,align:'center'},
		        {field:'majorName',title:'专业',width:30,align:'center'},
//		        {field:'total',title:'正式校友数',width:30,align:'center'},
		        {field:'checkFlagCount',title:'正式校友数',width:30,align:'center'},
		        {field:'authCount',title:'被认证校友数',width:30,align:'center'},
		        {field:'miningCount',title:'被挖掘校友数',width:30,align:'center'}
		    ]],
		    toolbar : '#toolbar',
		    onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', pixel_0);
				var groupType = $("#groupType").val() ;
				if(groupType == '1'){
					$("#dataGrid").datagrid("hideColumn", "collegeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
				} else if(groupType == '2'){
					$("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
				} else if(groupType == '3'){
					$("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
					$("#dataGrid").datagrid("showColumn", "gradeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
				} else if(groupType == '4'){
					$("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
					$("#dataGrid").datagrid("showColumn", "gradeName"); // 设置隐藏列
					$("#dataGrid").datagrid("showColumn", "className"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
				} else if(groupType == '5'){
					$("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
					$("#dataGrid").datagrid("showColumn", "majorName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
					$("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
				}
				parent.$.messager.progress('close');
			}
		}); 
	});
	
	
	
	function searchData(){
		$('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
	}
	
	/**--重置--**/
	function resetT(){

		$('#deptId').prop('value','');
		$('#groupType').prop('value','1');

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
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<input name="analysisMap.groupType" id="groupType" type="hidden" value="1">
						<input name="analysisMap.deptId" id="deptId" type="hidden">
						<table>
							<tr>
								<th align="right">学校</th>
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
											prompt:'--请选择--',
											icons:[{
												iconCls:'icon-clear',
												handler: function(e){
												$('#deptId').prop('value','');
												$('#groupType').prop('value','1');

												$('#school').combobox('clear');
												$('#depart').combobox('clear');
												$('#grade').combobox('clear');
												$('#classes').combobox('clear');
												$('#major').combobox('clear');
												$('#classes').combobox('loadData',[]);
												$('#grade').combobox('loadData',[]);
												$('#major').combobox('loadData',[]);
												$('#depart').combobox('loadData',[]);
												$('#schoolId').prop('value','');
												$('#departId').prop('value','');
												$('#gradeId').prop('value','');
												$('#classId').prop('value','');
												}
											}],
											url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
											onSelect: function(rec){
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
												$('#deptId').prop('value',rec.deptId);
												$('#groupType').prop('value','2');

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
							
								<th align="right">院系</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="depart" class="easyui-combobox" style="width: 150px;"
										data-options="
											valueField: 'deptId',
											textField: 'deptName',
											editable:false,
											prompt:'--请选择--',
											icons:[{
												iconCls:'icon-clear',
												handler: function(e){
													$('#deptId').prop('value',$('#schoolId').val());
													$('#groupType').prop('value','2');

													$('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);
													$('#departId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
												}
											}],
											onSelect: function(rec){
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
												var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
												$('#deptId').prop('value',rec.deptId);
												$('#groupType').prop('value','3');

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

								<th align="right" width="30px;">专业</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="major" name="majorId" class="easyui-combobox" style="width: 150px;"
										data-options="
											valueField: 'majorId',
											textField: 'majorName',
											prompt:'--请选择--',
											icons:[{
												iconCls:'icon-clear',
												handler: function(e){
													$('#deptId').prop('value',$('#departId').val());
													$('#groupType').prop('value','3');
													$('#major').combobox('clear');
												}
											}],
											onSelect: function(rec){
												$('#groupType').prop('value','5');

												$('#gradeId').prop('value','');
												$('#classId').prop('value','');
												$('#grade').combobox('clear');
											},
											editable:false" />
								</td>
								<th align="right">年级</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="grade" class="easyui-combobox" style="width: 150px;"
										   data-options="
											valueField: 'deptId',
											textField: 'deptName',
											editable:false,
											prompt:'--请选择--',
											icons:[{
												iconCls:'icon-clear',
												handler: function(e){
													$('#deptId').prop('value',$('#departId').val());
													$('#groupType').prop('value','3');

													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
												}
											}],
											onSelect: function(rec){
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;

												$('#deptId').prop('value',rec.deptId);
												$('#groupType').prop('value','4');

												$('#classes').combobox('clear');
												$('#classes').combobox('reload', url);
												$('#gradeId').prop('value',rec.deptId);
												$('#classId').prop('value','');

												$('#major').combobox('clear');
									}" />
								</td>
								<td align="right" >
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-search',plain:true"
									   onclick="searchData();">查询</a>
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
									   onclick="resetT()">重置</a>
								</td>

							</tr>
							
							<tr>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:true">
		<table id="dataGrid"></table>
	</div>

</div>
  </body>
</html>