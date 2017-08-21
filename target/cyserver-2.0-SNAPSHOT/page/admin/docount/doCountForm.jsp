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
			url : '${pageContext.request.contextPath}/docount/doCountAction!getList.action',
			fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			//idField : 'deptId',
			columns : [ [
			{title : '入学年份',	field : 'ageName',align:'center'}, 
			{title : '学院',	field : 'collegeName',align:'center'},
			//{title : '专业',	field : 'noname'},
			{title : '班级',	field : 'className',align:'center'},
			{title : '人数',	field : 'countUsers',align:'center',
				formatter: function(value,row,index){
	    			return value + "人";
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
			/* onLoadError : function(data){
				$('.iconImg').attr('src', pixel_0);
				parent.$.messager.progress('close');
			} */
		});
	});

	
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
										所在地
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
					                      <select name="formData.location" id="findProvince" class="easyui-combogrid" style="width:155px;"
											data-options="  
												valueField:'provinceName',textField:'provinceName',
												columns: [[
													{field:'provinceName',title:'省份名称'}
												]],
												fitColumns: true,
												editable:true,
												url: '${pageContext.request.contextPath}/docount/doCountAction!doNotNeedSessionAndSecurity_getAllProvince.action',  
												" ></select>
												
										<!-- <img class="iconImg ext-icon-cross" onclick="$('#findProvince').combobox('clear');" title="清空" /> -->
									</td>
									
									<!-- <th align="right">
										所在地
									</th>
								
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select id="location" name="formData.location"  class="easyui-combogrid" style="width:155px" data-options=" 
												
												panelWidth: 180,
												multiple: false,
												idField: 'cityName',
												textField: 'cityName',
												url: '${pageContext.request.contextPath}/page/admin/alumniCard/alumniCardAction!doNotNeedSecurity_getNationalOfCity.action',
												method: 'get',
												columns: [[
													{field:'cityName',title:'城市名称'}
												]],
												fitColumns: true,
												editable:true
											">
										</select>
									</td> -->
									
									<th align="right">
										学历层次
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select name="formData.background" class="easyui-combobox" style="width:155px" 
											data-options="editable:false"
										>
										<option value="">全部</option>
										<option value="专科">专科</option>
										<option value="本科">本科</option>
										<option value="硕士">硕士</option>
										<option value="博士">博士</option>
										</select>	
									</td>
									
									<th align="right">
										性别
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select name="formData.sex" class="easyui-combobox" style="width:155px" 
											data-options="editable:false"
										>
										<option value="">全部</option>
										<option value="男">男</option>
										<option value="女">女</option>
										</select>	
									</td>
									
									<th align="right">
										出生年份
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select id="birth" name="formData.birthDate" style="width:155px;" class="easyui-combobox" 
											data-options="  
												
												valueField: 'birthDate',  
												textField: 'deptName',  
												editable:false,
												url: '${pageContext.request.contextPath}/docount/doCountAction!doNotNeedSessionAndSecurity_getAllBirthDate.action',  
												" >
										</select>
										
									</td>
									
								</tr>
								
								<tr>
								
								<th align="right">学校</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<!-- <input name="schoolId" id="schoolId" type="hidden">
										<input name="departId" id="departId" type="hidden">
										<input name="gradeId" id="gradeId" type="hidden">
										<input name="classId" id="classId" type="hidden"> -->
										<input id="school" name="formData.school" class="easyui-combobox" style="width: 155px;"
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
													$('#school').attr('value',rec.deptId)
										}" />
									</td>
									<th align="right" >院系</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="depart" name="formData.department" class="easyui-combobox" style="width: 155px;"
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
													$('#departId').attr('value',rec.deptId)
													$('#major').combobox('clear');
													$('#major').combobox('reload', url1);
										}" />
									</td>
									<th align="right">年级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="grade" name="formData.joinYear" class="easyui-combobox" style="width: 155px;"
											data-options="  
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
												onSelect: function(rec){  
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);
													$('#gradeId').attr('value',rec.deptId)  
										}" />
									</td>
									<th align="right">班级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="classes" name="formData.specialty" class="easyui-combobox" style="width: 155px;"
											data-options="
												editable:false,
												valueField:'deptId',
												textField:'deptName',
												onSelect: function(rec){  
													$('#classId').attr('value',rec.deptId)  
												}
												"/>
									</td>
								
								
								<%-- <th align="right">
									所在院系
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>					
									<input id="depart" name="formData.department" style="width: 155px;" class="easyui-combobox"
										data-options="  
											
											valueField: 'deptId',  
											textField: 'deptName',  
											editable:false,
											url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',  
											onSelect: function(rec){
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
												$('#grade').combobox('clear');
												$('#classes').combobox('clear');
												$('#classes').combobox('loadData',[]);
												$('#grade').combobox('reload', url);  
									}" />
								</td >
								
								<th align="right">
									入学年份
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="grade" name="formData.joinYear" style="width:155px;" class="easyui-combobox" 
										data-options="  
											
											valueField: 'deptId',  
											textField: 'deptName',  
											editable:false,
											onSelect: function(rec){  
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
												$('#classes').combobox('clear');
												$('#classes').combobox('reload', url);  
									}" />
									
								</td>
								
								<th align="right">
									专业
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="classes" name="formData.specialty" style="width:155px;" class="easyui-combobox" 
										data-options="
											
											editable:false,
											valueField:'deptId',
											textField:'deptName'"/>  
									
								</td> --%>
								
							</tr>
							
								
								<tr>
								
									<th align="right" >专业</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="major" name="formData.majorId" class="easyui-combobox" style="width: 155px;"
											data-options="  
												valueField: 'majorId',  
												textField: 'majorName',  
												editable:false" />
									</td>
								
									<th align="right">
										统计方式
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
					                    <select sessionScope.user.role" data-options="editable:false" style="width:155px;" name="formData.countType">
											<option value="1">按班级统计</option>
					                        <option value="2" selected="selected">按学院统计</option>
										</select>
									</td>
									<td>
									
										<%-- <authority:authority authorizationCode="校友统计" role="${sessionScope.user.list}">
							    			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
										onclick="searchResult();">查询</a>
						    			</authority:authority> --%>
									
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchResult();">查询</a>
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
