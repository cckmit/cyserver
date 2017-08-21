<%@ page language="java" pageEncoding="UTF-8"%>
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
					url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGridForAlumnix.action',
					fit : true,
					method : 'post',
					border : false,
					striped : true,
					pagination : true,
					singleSelect : true,
					columns : [ [
					{
						width : '80',
						title : '姓名',
						field : 'userName',
						align : 'center'
					}, {
						width : '50',
						title : '性别',
						field : 'sex',
						align : 'center'
					}, {
						width : '100',
						title : '电话号码',
						field : 'telId',
						align : 'center'
					}, {
						width : '100',
						title : '邮箱',
						field : 'email',
						align : 'center'
					}, {
						width : '150',
						title : '工作单位',
						field : 'workUnit',
						align : 'center'
					},{
						width : '100',
						title : '职务',
						field : 'position',
						align : 'center'
					},
					{
						width : '150',
						title : '所在城市',
						field : 'residentialArea',
						align : 'center'
					},
					{
						width : '350',
						title : '学习经历',
						field : 'fullName',
						align : 'center',
						formatter : function(value, row)
						{
							var text='';
							if(value!=undefined){
								var array = value.split(',');
								for(var i=0;i<array.length;i++){
									if(i==array.length-1){
										text+=array[i];
									}
									else{
										text+=array[i]+ "<br />";
									}
								}
							}
							return text;
						}
					},
					{
						title : '操作',
						field : 'action',
						width : '80',
						formatter : function(value, row)
						{
							var str = '';
							<authority:authority authorizationCode="查看校友会学生" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							return str;
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

	});

	function searchUserInfo()
	{
		$('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
	}


	var viewFun = function(id)
	{
		var dialog = parent.parent.WidescreenModalDialog({
			title : '查看学生',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/alumni/roster/view.jsp?id=' + id
		});
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
			$('#alumni').combobox('clear');
			$('#sex').combobox('clear');
		}
	  
	  /**--短信发送--**/
		function messageSend(){
			//查询的条件
			var userName = $("#userName").val();
			var schoolId = $("#schoolId").val();
			var departId = $("#departId").val();
			var classId = $("#classId").val();
			var majorId = $("#major").combobox('getValue');
			
			var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
			var url = "<%=path %>/page/admin/sms/send.jsp?"+params;
			var dialog = parent.parent.WidescreenModalDialog({
				title : '短信发送',
				iconCls : 'ext-icon-export_customer',
				url : url,
				buttons : [ {
					text : '发送',
					iconCls : 'ext-icon-save',
					handler : function()
					{
						dialog.find('iframe').get(0).contentWindow.doSend();
					}
				} ]
			});
		}
		
		/**--邮件发送--**/
		function emailSend(){
			//查询的条件
			var userName = $("#userName").val();
			var schoolId = $("#schoolId").val();
			var departId = $("#departId").val();
			var classId = $("#classId").val();
			var majorId = $("#major").combobox('getValue');
			
			var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
			var url = "<%=path %>/page/admin/email/send.jsp?"+params;
			var dialog = parent.parent.WidescreenModalDialog({
			title : '邮件发送',
			iconCls : 'ext-icon-export_customer',
			url : url,
			buttons : [ {
				text : '发送',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.submitForm();
				}
			} ]
			});
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
												prompt:'--请选择--',
												icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
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
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getDepart.action',  
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='+rec.deptId; 
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
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
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
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
									                }
									            }],
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
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#classes').combobox('clear');
													$('#classId').prop('value','');
									                }
									            }],
												onSelect: function(rec){  
													$('#classId').prop('value',rec.deptId)  
												}
												"/>
									</td>
								</tr>
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
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#major').combobox('clear');
									                }
									            }],  
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
											prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#studentType').combobox('clear');
									                }
									            }],  
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
										<input name="deptId" id="deptId" type="hidden" />
										<input id="userName" name="userInfo.userName" style="width: 150px;" />
									</td>
								</tr>
								<tr>
									<th align="right">
										行业
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.industryType" style="width: 150px;" />
									</td>
									<th align="right">
										性别
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input class="easyui-combobox" name="userInfo.sex" style="width: 150px;" id="sex" data-options="
										editable:false,
										prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#sex').combobox('clear');
									                }
									            }],
										valueField: 'label',
										textField: 'value',
										data: [{
											label: '男',
											value: '男'
										},{
											label: '女',
											value: '女'
										}]" />
									</td>
									<th align="right">
										出生年份
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="birthday" style="width: 150px;" readonly="readonly" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
									</td>
									<th align="right">
										入学年份
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="entranceTime" readonly="readonly" style="width: 150px;" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
									</td>
									<td colspan="3">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>
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
									<authority:authority authorizationCode="校友会短信发送" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend();">按搜索条件发送短信</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="校友会邮件发送" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="emailSend();">按搜索条件发送邮件</a>
									</authority:authority>
								</td>
								<td>
									<span id="exportResult"></span>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="userInfoGrid"></table>
		</div>
	</body>
</html>
