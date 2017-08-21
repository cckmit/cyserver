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
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
		var projectGrid;
		$(function() {
			projectGrid = $('#projectGrid').datagrid({
				url : '${pageContext.request.contextPath}/project/projectAction!dataGrid.action',
				fit : true,
				border : false,
				striped : true,
				rownumbers : true,
				pagination : true,
				idField : 'projectId',
				columns : [ [ {
						field : 'donationId',
						checkbox : true
					},{
						width : '200',
						title : '项目名称',
						field : 'projectName',
						align : 'center'
					},
                    {
                        width : '100',
                        title : '项目类型',
                        field : 'projectType',
                        align : 'center'
                    },
                    {
                        width : '100',
                        title : '目标金额',
                        field : 'target',
                        align : 'center',
                        formatter: function (value, row) {
                            if(row.hasTarget && row.hasTarget == '1'){
                                return value;
                            }else{
                                return '未设目标金额';
                            }
                        }
                    },
					{
						width : '100',
						title : '已募金额',
						field : 'donationMoney',
						align : 'center',
						formatter: function (value) {
							if(value){
							    return value;
							}else{
							    return '0';
							}
                        }
					},
                    {
                        width : '100',
                        title : '使用金额',
                        field : 'costMoney',
                        align : 'center',
                        formatter: function (value) {
                            if(value){
                                return value;
                            }else{
                                return '0';
                            }
                        }
                    },
                    {
                        width : '100',
                        title : '剩余金额',
                        field : 'restMoney',
                        align : 'center',
                        formatter: function (value) {
                            if(value){
                                return value;
                            }else{
                                return '0';
                            }
                        }
                    },
                    {
                        width : '100',
                        title : '募集进度',
                        field : 'rateOfProgress',
                        align : 'center',
                        formatter: function (value, row) {
                            if(row.hasTarget && row.hasTarget == '1' && value){
                                return value+'%';
                            }else{
                                return '0.00%';
                            }
                        }
                    },
                    {
                        width : '100',
                        title : '捐赠人数',
                        field : 'countPeople',
                        align : 'center',
                        formatter: function (value, row) {
                            var str = '0';
                            if(value > 0) {
                                str = '<a href="javascript:void(0)" onclick="donatePeople(\'' + row.projectId + '\');">' + value + '</a>&nbsp;';
                            }
                            return str;
                        }
                    },
					{
						width : '100',
						title : '创建人',
						field : 'createId',
						align : 'center',
						formatter : function(value, row) {
							if(row.user!=undefined){
								return row.user.userName;
							}else{
								return "";
							}
						}
					},
					{
						width : '150',
						title : '开始时间',
						field : 'startTime',
						align : 'center'
					},
                    {
                        width : '150',
                        title : '结束时间',
                        field : 'endTime',
                        align : 'center',
						formatter: function (value, row) {
							if(row.hasEndTime && row.hasEndTime == '1'){
							    return value;
							}else{
							    return '永久有效';
							}
                        }
                    },
                    {
                        width : '70',
                        title : '状态',
                        field : 'timeStatus',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '是否推荐项目',
                        field : 'isCommand',
                        align : 'center',
						formatter: function (value) {
							if(value == '1'){
							    return '<font color="green">是</font>'
							}else{
							    return '否'
							}
                        }
                    },
                    {
                        width : '70',
                        title : '排序',
                        field : 'seq',
                        align : 'center'
                    },
					{
						title : '操作',
						field : 'action',
						width : '240',
						formatter : function(value, row) {
							var str = '';
                            <authority:authority authorizationCode="查看项目进度" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showCost(' + row.projectId + ');"><img class="iconImg ext-icon-note"/>项目进度</a>&nbsp;';
                            </authority:authority>
							<authority:authority authorizationCode="查看捐赠项目" userRoles="${sessionScope.user.userRoles}">
								str += '<a href="javascript:void(0)" onclick="showFun(' + row.projectId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							if(row.status == '0'){
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(' + row.projectId + ','+ row.status +');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="checkProject(' + row.projectId + ', 1);"><img class="iconImg icon-redo"/>发布</a>&nbsp;';
                                </authority:authority>
							}else if(row.status=='1'){
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(' + row.projectId + ','+ row.status +');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="checkProject(' + row.projectId + ', 2);"><img class="iconImg ext-icon-note_delete"/>下线</a>&nbsp;';
                                </authority:authority>
							}else if(row.status=='2') {
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(' + row.projectId + ','+ row.status +');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="checkProject(' + row.projectId + ', 1);"><img class="iconImg icon-redo"/>发布</a>&nbsp;';
                                </authority:authority>
							}
							return str;
						}
					} ] ],
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

	 function addFun() {
		var dialog = parent.modalDialog({
			title : '新增捐赠项目',
			iconCls : 'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/project/addProject.jsp',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, projectGrid, parent.$, 0);
				}
			},{
                text : '保存并发布',
                iconCls : 'ext-icon-save',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, projectGrid, parent.$, 1);
                }
            } ]
		});
	};

	 function showCost(id) {
         var dialog = parent.modalDialog({
             title : '项目进度',
             iconCls : 'ext-icon-note',
             url : '${pageContext.request.contextPath}/page/admin/project/projectCostList.jsp?projectId=' + id
         });
     }

	function showFun(id) {
		var dialog = parent.modalDialog({
			title : '查看捐赠项目',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/admin/project/viewProject.jsp?id=' + id
		});
	}
	
	function editFun(id,status) {

		var dialog = parent.modalDialog({
			title : '编辑捐赠项目',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/project/editProject.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, projectGrid, parent.$,(status && status=='')?0:status);
				}
			},{
                text : '保存并发布',
                iconCls : 'ext-icon-save',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, projectGrid, parent.$, 1);
                }
            }]
		});
	}

	function donatePeople(projectId) {
        var dialog = parent.modalDialog({
            title: '查看捐赠人员',
            iconCls: 'ext-icon-note',
            url: '${pageContext.request.contextPath}/page/admin/project/viewDonatePeople.jsp?projectId=' + projectId
        });
    }

	function removeFun()
	{
		var rows = $('#projectGrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			parent.$.messager.confirm('确认', '确定删除吗？', function(r)
			{
				if (r)
				{
					for ( var i = 0; i < rows.length; i++)
					{
						ids.push(rows[i].projectId);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/project/projectAction!delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(data)
						{
							if (data.success)
							{
								$("#projectGrid").datagrid('reload');
								$("#projectGrid").datagrid('unselectAll');
								parent.$.messager.alert('提示', data.msg, 'info');
							} else
							{
								parent.$.messager.alert('错误', data.msg, 'error');
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
					});
				}
			});
		} else
		{
			parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}

        function checkProject(id, checkFlag)
        {
            var content = '';
            if(checkFlag == 1){
                content = '发布';
            }else if(checkFlag == 2){
                content = '下线';
            }

			parent.$.messager.confirm('确认', '确定'+content+'吗？', function(r)
			{
				if(r){
					$.ajax({
						url : '${pageContext.request.contextPath}/project/projectAction!update.action',
						data : {
							'ids': id,
							'checkFlag': checkFlag
						},
						dataType : 'json',
						success : function(data)
						{
							if (data.success)
							{
								$("#projectGrid").datagrid('reload');
								$("#projectGrid").datagrid('unselectAll');
								parent.$.messager.alert('提示', data.msg, 'info');
							} else
							{
								parent.$.messager.alert('错误', data.msg, 'error');
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
					});
				}
			});
		}
		function checkProjects(checkFlag) {

            var content = '';
            if(checkFlag == 1){
                content = '发布';
            }else if(checkFlag == 2){
                content = '下线';
            }else if(checkFlag == 3){
				content = '推荐';
			}else if(checkFlag == 4){
				content = '取消推荐';
			}


            var rows = $('#projectGrid').datagrid('getChecked');

            console.log(rows);

            for(var i in rows){
                if((rows[i].status == '1' || rows[i].status == '2') && checkFlag == 1){
                    parent.$.messager.alert('提示', '您的选择了已发布或已下线的项目！', 'error');
                    return;
				}else if(checkFlag == 2 && (rows[i].status == '0' || rows[i].status == '2') ){
                    parent.$.messager.alert('提示', '您的选择了已发布或已下线的项目！', 'error');
                    return;
				}
//				else if(checkFlag == 3 && (rows[i].status == '0' || rows[i].status == '2') ){
//					parent.$.messager.alert('提示', '您的选择了已发布或已下线的项目！', 'error');
//					return;
//				}
			}

            var ids = [];

            if (rows.length > 0)
            {
                parent.$.messager.confirm('确认', '确定'+content+'吗？', function(r)
                {
                    if(r){
                        for ( var i = 0; i < rows.length; i++)
                        {
                            ids.push(rows[i].projectId);
                        }

                        $.ajax({
                            url : '${pageContext.request.contextPath}/project/projectAction!update.action',
                            data : {
                                'ids': ids.join(','),
                                'checkFlag': checkFlag
                            },
                            dataType : 'json',
                            success : function(data)
                            {
                                if (data.success)
                                {
                                    $("#projectGrid").datagrid('reload');
                                    $("#projectGrid").datagrid('unselectAll');
                                    parent.$.messager.alert('提示', data.msg, 'info');
                                } else
                                {
                                    parent.$.messager.alert('错误', data.msg, 'error');
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
                        });
                    }
                });
            }else{
                parent.$.messager.alert('提示', '请选择要'+content+'的记录！', 'error');
            }
        }

        function resetT(){
            $('#projectName').val('');
            $('#projectType').combobox('clear');
            $('#timeStatus').combobox("setValue", "");
			$('#isCommand').combobox("setValue", "");
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
										项目名称
									</th>
									<td>
									<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="projectName" name="project.projectName" style="width: 150px;" />
									</td>
									<th>
										项目类型
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
									<td>
										<input id="projectType" name="project.projectType" class="easyui-combobox" style="width: 150px;"
											   data-options="editable:false,
									        valueField: 'one',
									        textField: 'two',
									        prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#projectType').combobox('clear');
									                }
									            }],
									        url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getALLDonateType.action'"/>
									</td>
									<th>
										状态
									</th>
									<td>
										<select id="timeStatus" class="easyui-combobox" data-options="editable:false" name="project.timeStatus" style="width: 150px;">
											<option value="">全部</option>
											<option value="0">未发布</option>
											<option value="1">进行中</option>
											<option value="2">已结束</option>
											<option value="3">已下线</option>
										</select>

									</td>
									<th>
										是否推荐项目
									</th>
									<td>
										<select id="isCommand" class="easyui-combobox" data-options="editable:false" name="project.isCommand" style="width: 150px;">
											<option value="">全部</option>
											<option value="1">是</option>
											<option value="0">否</option>
										</select>

									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="projectGrid.datagrid('load',serializeObject($('#searchForm')));">查询</a>
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
								<td>
									<authority:authority authorizationCode="新增捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun();">新增</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="删除捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeFun();">删除</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="checkProjects(1);">批量发布</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="checkProjects(2);">批量下线</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="checkProjects(3);">批量推荐</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority authorizationCode="编辑捐赠项目" userRoles="${sessionScope.user.userRoles}">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="checkProjects(4);">批量取消推荐</a>
									</authority:authority>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="projectGrid"></table>
		</div>
	</body>
</html>
