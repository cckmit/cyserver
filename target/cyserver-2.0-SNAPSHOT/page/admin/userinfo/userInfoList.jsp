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
		$(function() {
		userInfoGrid = $('#userInfoGrid').datagrid({
			url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action',
			fit : true,
			method : 'post',
			border : false,
			striped : true,
			pagination : true,
			sortName:'userName',
			sortOrder:'asc',
			columns : [ [ {
				field : 'userId',
				checkbox : true
			},
			{
				width : '100',
				title : '姓名',
				field : 'userName',
				align : 'center',
				sortable : true
			}, {
				width : '120',
				title : '学校',
				field : 'schoolName',
				align : 'center'
			},
			{
				width : '120',
				title : '院系',
				field : 'departName',
				align : 'center'
			},
			{
				width : '80',
				title : '年级',
				field : 'gradeName',
				align : 'center'
			},
			{
				width : '120',
				title : '班级',
				field : 'className',
				align : 'center'
			},
			{
				width : '120',
				title : '专业',
				field : 'majorName',
				align : 'center'
			},
			{
				width : '100',
				title : '手机号',
				field : 'telId',
				align : 'center'
			},
			{
				width : '120',
				title : '邮箱',
				field : 'email',
				align : 'center'
			},
			{
				width : '80',
				title : '是否注册',
				field : 'accountNum',
				align : 'center',
				formatter : function(value, rows){
				    var content;
					if(value!=''&&value!=undefined){
                        content = "<span style='color: green;'>已注册"
						if(rows.isOneKeyAuth == "1"){
							content += "(一键认证)";
                        }
                        content += "</span>";
					}else{
                        content = "<span>未注册</span>"
					}
					return content;
				}
			},
			{
				width : '80',
				title : '状态',
				field : 'checkFlag',
				align : 'center',
				formatter : function(value, row){
					if(value==1){
						return "<span style='color: green;'>正式校友</span>"
					}else if(value ==0){
						return "<span >待核校友</span>"
					}else{
                        return "<span style='color: red;'>未通过</span>"
					}
				}
			}
			, {
				title : '操作',
				field : 'action',
				width : '250',
				formatter : function(value, row)
				{
					var str = '';
					<authority:authority authorizationCode="查看校友" userRoles="${sessionScope.user.userRoles}">
					str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
					</authority:authority>
					<authority:authority authorizationCode="编辑校友" userRoles="${sessionScope.user.userRoles}">
					str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.userId + '\','+row.checkFlag+');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
					</authority:authority>
					if(row.checkFlag==0){
						<authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
						str += '<a href="javascript:void(0)" onclick="checkFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_edit"/>审核</a>';
						</authority:authority>
					}else if(row.checkFlag == 1){
                        <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                        str += '<a href="javascript:void(0)" onclick="moveUserToOtherClass(\'' + row.userId + '\', \''+ row.userName +'\', \''+ row.fullName +'\');"><img class="iconImg ext-icon-note_edit"/>迁移班级</a>';
                        </authority:authority>
                        if(row.accountNum && row.accountNum!=''&&row.accountNum!=undefined){
                            <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="cancleAuth(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_delete"/>解除认证</a>';
                            </authority:authority>
						}else if(row.telId && row.telId != ''){
                            <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="oneKeyAuth(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_edit"/>一键认证</a>';
                            </authority:authority>
						}
                    }

					return str;
				}
			} ] ],
			toolbar : '#toolbar',
            viewFunonBeforeLoad : function(param)
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

		function searchUserInfo(){
		$('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
		}

		function removeFun(){
		var rows = $("#userInfoGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			parent.parent.$.messager.confirm('确认', '确定删除吗？', function(r)
			{
				if (r)
				{
					for ( var i = 0; i < rows.length; i++)
					{
						ids.push(rows[i].userId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/userInfo/userInfoAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(data)
						{
							if (data.success)
							{
								$("#userInfoGrid").datagrid('reload');
								$("#userInfoGrid").datagrid('unselectAll');
								parent.parent.$.messager.alert('提示', data.msg, 'info');
							} else
							{
								parent.parent.$.messager.alert('错误', data.msg, 'error');
							}
						},
						beforeSend : function()
						{
							parent.parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete : function()
						{
							parent.parent.$.messager.progress('close');
						}
					});
				}
			});
		} else
		{
			parent.parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
		}

		function addFun(){
		var dialog = parent.parent.WidescreenModalDialog({
			title : '新增校友',
			iconCls : 'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/userinfo/addUserInfo.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.checkTelePhone(dialog, userInfoGrid, parent.parent.$);
				}
			} ]
		});
		}

		function checkFun(id){
		var dialog = parent.parent.WidescreenModalDialog({
			title : '校友审核',
			iconCls : 'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/userinfo/checkUserInfo.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, userInfoGrid, parent.parent.$);
				}
			} ]
		});
		}

		var editFun = function(id,checkFlag){
		if(checkFlag==undefined){
			checkFlag=0;
		}
		var dialog = parent.parent.WidescreenModalDialog({
			title : '编辑校友',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/userinfo/editUserInfo.jsp?id=' + id+'&checkFlag='+checkFlag,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.checkTelePhone(dialog, userInfoGrid, parent.parent.$);
				}
			} ]
		});
		}

		var cancleAuth = function(id){
			parent.parent.$.messager.confirm('确认', '确定解除认证？', function(r){
				if (r){
					$.ajax({
						url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_cancleAuth.action',
						data : {'userId' : id},
						dataType : 'json',
						success : function(data){
							if (data.success){
								$("#userInfoGrid").datagrid('reload');
								parent.parent.$.messager.alert('提示', data.msg, 'info');
							} else{
								parent.parent.$.messager.alert('错误', data.msg, 'error');
							}
						},
						beforeSend : function(){
							parent.parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete : function(){
							parent.parent.$.messager.progress('close');
						}
					});
				}
			});
		}

		var oneKeyAuth = function (id) {
            parent.parent.$.messager.confirm('确认', '确定为该校友添加认证？', function(r){
                if (r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_oneKeyAuth.action',
                        data : {'userId' : id},
                        dataType : 'json',
                        success : function(data){
                            if (data.success){
                                $("#userInfoGrid").datagrid('reload');
                                parent.parent.$.messager.alert('提示', data.msg, 'info');
                            } else{
                                parent.parent.$.messager.alert('错误', data.msg, 'error');
                            }
                        },
                        beforeSend : function(){
                            parent.parent.$.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function(){
                            parent.parent.$.messager.progress('close');
                        }
                    });
                }
			});
        }

		var viewFun = function(id) {
			var dialog = parent.parent.WidescreenModalDialog({
				title : '查看校友',
				iconCls : 'ext-icon-note',
				url : '${pageContext.request.contextPath}/page/admin/userinfo/viewUserInfo.jsp?id=' + id
			});
		}

		function importFun1(){
		  var dialog = parent.parent.WidescreenModalDialog({
				title : '从基础库导入',
				iconCls : 'ext-icon-import_customer',
				url : '${pageContext.request.contextPath}/page/admin/userinfo/fromBase.jsp'
			});
		}

		function importFun(){
			parent.parent.$.messager.confirm('功能确认', '此导入功能只适用于非正式数据的导入。大量数正式数据请在"校友档案"模块中导入。是否坚持使用此导入功能？', function(r)
			{
				if (r)
				{
					var dialog = parent.parent.modalDialog({
						title : '导入校友',
						iconCls : 'ext-icon-import_customer',
						url : '${pageContext.request.contextPath}/page/admin/userinfo/importUserInfo.jsp',
						buttons : [ {
							text : '确定',
							iconCls : 'ext-icon-save',
							handler : function() {
								dialog.find('iframe').get(0).contentWindow.submitForm(dialog, parent.parent.$);
							}
						} ]
					});
				}
			});

		}

        var goOneKeyAuthPage = function() {
            var dialog = parent.parent.WidescreenModalDialog({
                title : '一键认证',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/userinfo/oneKeyAuthPage.jsp'
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
            $('#regflag').prop('value','');
            $('#checkFlag').prop('value','');
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

		function clearFun(){
			parent.parent.$.messager.confirm('确认', '确定清空校友库吗？', function(r)
			{
				if (r){
					$.ajax({
						url : '${pageContext.request.contextPath}/userInfo/userInfoAction!deleteAll.action',
						dataType : 'json',
						data : $('#searchForm').serialize(),
						success : function(data)
						{
							if (data.success)
							{
								$("#userInfoGrid").datagrid('reload');
								$("#userInfoGrid").datagrid('unselectAll');
								parent.parent.$.messager.alert('提示', data.msg, 'info');
							} else
							{
								parent.parent.$.messager.alert('错误', data.msg, 'error');
							}
						},
						beforeSend : function()
						{
							parent.parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete : function()
						{
							parent.parent.$.messager.progress('close');
						}
					});
				}
			});
		}

		function exportFun(){
			$.ajax({
				url : '${pageContext.request.contextPath}/userInfo/userInfoAction!exportData.action',
				data : $('#searchForm').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						if(result.msg!=""){
							$('#exportResult').html("<a id='mf' href='"+result.msg+"'>导出结果下载</a>")
							parent.parent.$.messager.alert('提示', "导出成功,请在导出结果处下载导出结果", 'info');
						}else{
							parent.parent.$.messager.alert('提示', "无数据导出", 'info');
						}
					} else {
						parent.parent.$.messager.alert('提示', result.msg, 'error');
					}
				},
				beforeSend:function(){
					$('#mf').remove();
					parent.parent.$.messager.progress({
						text : '数据导出中....'
					});
				},
				complete:function(){
					parent.parent.$.messager.progress('close');
				}
			});
		}
		
		/**--短信发送--**/
		function messageSend(){
		
			var rows = $("#userInfoGrid").datagrid('getChecked');
			var ids = [] ;
//			if (rows.length <= 0) {
//				$.messager.alert('提示', '请选择你发送的校友', 'info');
//				return ;
//			}

			if(rows.length > 0){
                for(var i = 0 ; i < rows.length ; i++) {
                    ids.push(rows[i].userId) ;
                }
			}

			var params = "userIds="+ids ;
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
			//var userName = $("#userName").val();
			//var schoolId = $("#schoolId").val();
			//var departId = $("#departId").val();
			//var classId = $("#classId").val();
			//var majorId = $("#major").combobox('getValue');
			
			//var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
			
			
			var rows = $("#userInfoGrid").datagrid('getChecked');
			var ids = [] ;
//			if (rows.length <= 0) {
//				$.messager.alert('提示', '请选择你发送的校友', 'info');
//				return ;
//			}

			if(rows.length > 0){
                for(var i = 0 ; i < rows.length ; i++) {
                    ids.push(rows[i].userId) ;
                }
			}

			var params = "userIds="+ids ;
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
		
		/**--短信发送(旧)--**/
		function messageSend_Old(){
			//查询的条件
			var userName = $("#userName").val();
			var schoolId = $("#schoolId").val();
			var departId = $("#departId").val();
			var classId = $("#classId").val();
			var majorId = $("#major").combobox('getValue');
			
			var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
			var url = "<%=path %>/page/admin/sms/send_Old.jsp?"+params;
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
		
		/**--邮件发送【旧】--**/
		function emailSend_Old(){
			//查询的条件
			var userName = $("#userName").val();
			var schoolId = $("#schoolId").val();
			var departId = $("#departId").val();
			var classId = $("#classId").val();
			var majorId = $("#major").combobox('getValue');
			
			var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
			var url = "<%=path %>/page/admin/email/send_Old.jsp?"+params;
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
        function moveUserToOtherClass(userId, userName, oldClassName)
        {
			var dialog = parent.parent.WidescreenModalDialog({
				title : '迁移班级',
				iconCls : 'ext-icon-note_edit',
				url : '${pageContext.request.contextPath}/page/admin/userinfo/moveUserToOtherClass.jsp?userId=' + userId+ '&userName=' + userName + '&oldClassName=' + oldClassName,
				buttons : [ {
					text : '迁移班级',
					iconCls : 'ext-icon-save',
					handler : function()
					{
						dialog.find('iframe').get(0).contentWindow.changeClass(dialog, userInfoGrid, parent.$,this);
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
									<th align="right" style="padding-left: 10px" width="60px;">
										姓名
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td colspan="10">
										<input id="userName" name="userNames" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
									</td>
								</tr>
								<tr>
									<th align="right" style="padding-left: 0">
										手机号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td colspan="10">
										<input id="telId" name="telIds" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
									</td>
								</tr>
								<tr>
									<th align="right" style="padding-left: 0">
										学号
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td colspan="10">
										<input id="studentnumber" name="studentNumbers" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
									</td>
								</tr>
								<tr>
									<th align="right" style="padding-left: 10px" width="60px;">学校</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="schoolId" id="schoolId" type="hidden">
										<input name="departId" id="departId" type="hidden">
										<input name="gradeId" id="gradeId" type="hidden">
										<input name="classId" id="classId" type="hidden">
										<input name="majorId" id="majorId" type="hidden">
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
									<th align="right" style="padding-left: 10px" width="60px;">院系</th>
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
									<th align="right" style="padding-left: 10px" width="60px;">年级</th>
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
									<th align="right" style="padding-left: 10px" width="60px;">班级</th>
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
													$('#classId').prop('value',rec.deptId);  
												}
												"/>
									</td>
								</tr>
								<tr>
									<th align="right" style="padding-left: 10px" width="60px;">专业</th>
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
									<th align="right" style="padding-left: 10px" width="60px;">学历</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="studentType" class="easyui-combobox" style="width: 150px;" name="userInfo.studentType"
											data-options="  
											valueField: 'dictName',  
											textField: 'dictName',  
											prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#studentType').combobox('clear');
									                }
									            }],  
											editable:false,
											url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历') 
										" />
									</td>

									<th align="right" style="padding-left: 10px" width="60px;">是否注册</th>
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

									<th align="right" style="padding-left: 10px" width="60px;">审核状态</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<select class="easyui-combobox" data-options="editable:false" name="checkFlag" style="width: 150px;">
											<option value="">--请选择--</option>
											<option value="1">正式校友</option>
											<option value="0">未审核</option>
										</select>
									</td>

								</tr>
								<tr>
									<th align="right" style="padding-left: 10px" width="60px;">
										行业
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.industryType" style="width: 150px;" />
									</td>

									<th align="right" style="padding-left: 10px" width="60px;">
										工作单位
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.workUnit" style="width: 150px;" />
									</td>
									<th align="right" style="padding-left: 10px" width="60px;">
										联系地址
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="userInfo.mailingAddress" style="width: 150px;" />
									</td>
								</tr>
								<tr>
									<th align="right" style="padding-left: 10px" width="60px;">
										所在城市
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td  colspan="7">
									<input class="easyui-combobox" name="province" id="province" style="width: 150px;"
						data-options="
	                    method:'post',
						url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province').combobox('clear');
			                	$('#city').combobox('clear');
			                	$('#city').combobox('loadData',[]);
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city').combobox('clear');
							$('#city').combobox('reload', url);
							$('#area').combobox('clear');
							$('#area').combobox('loadData',[]);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="city" id="city" style="width: 150px;"
						data-options="
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city').combobox('clear');
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/area/areaAction!doNotNeedSecurity_getArea2ComboBox.action?cityId='+rec.id;
							$('#area').combobox('clear');
							$('#area').combobox('reload', url);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="area" id="area" style="width: 150px;"
						data-options="
	                    method:'post',
	                    valueField:'areaName',
	                    textField:'areaName',
	                    editable:false,
	                    prompt:'县(区)',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#area').combobox('clear');
			                }
			            }]
                    	">
									</td>

									<td colspan="3" align="right">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
								<td id="addTd">
									<authority:authority authorizationCode="新增校友" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">新增</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="校友导入">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun();" style="margin-left: 5px; margin-top: 1px;">导入</a>
									</authority:authority>
								</td>
								<%--<td>
									<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="从基础库导入">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun1();" style="margin-left: 5px; margin-top: 1px;">从基础库导入</a>
									</authority:authority>
								</td>
								--%><td>
									<authority:authority authorizationCode="删除校友" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeFun();">删除</a>
									</authority:authority>
								</td>
								<%--<td>--%>
									<%--<authority:authority authorizationCode="按搜索条件清空校友库" userRoles="${sessionScope.user.userRoles}">--%>
										<%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="clearFun();">按搜索条件清空</a>--%>
									<%--</authority:authority>--%>
								<%--</td>--%>
								<td>
									<authority:authority authorizationCode="导出校友" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="exportFun();">按搜索条件导出</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="短信发送" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend();">发送短信</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="邮件发送" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="emailSend();">发送邮件</a>
									</authority:authority>
								</td>
								<%-- <td>
									<authority:authority authorizationCode="短信发送" role="${sessionScope.user.role}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend_Old();">按搜索条件发送短信</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="邮件发送" role="${sessionScope.user.role}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="emailSend_Old();">按搜索条件发送邮件</a>
									</authority:authority>
								</td> --%>
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
