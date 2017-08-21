<%@ page language="java" pageEncoding="UTF-8" %>
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
        var eventGrid;
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/event/eventAction!getListx.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                idField: 'id',
                columns: [[
                	{field:'id',checkbox : true}, 
                	{
	                    width: '140',
	                    title: '标题',
	                    field: 'title'
                	},
                	{
	                    width: '140',
	                    title: '地点',
	                    field: 'place'
                	},
                    {
                        width: '50',
                        title: '类别',
                        field: 'category'
                    },
                    {
                        width: '110',
                        title: '开始时间',
                        field: 'startTime'
                    },
                    {
                        width: '110',
                        title: '结束时间',
                        field: 'endTime'
                    },
                    {
                        width: '50',
                        title: '是否签到',
                        field: 'needSignIn',
                        align:'center',
                        formatter : function(value, row) {
                        	return row.needSignIn?"是":"否";
						}
                    },
                    {
                        width: '40',
                        title: '签到码',
                        field: 'signInCode'
                    },
                    {
                        width: '60',
                        title: '审核状态',
                        field: 'auditStatus',
                        formatter : function(value, row) {
                        	if(row.auditStatus == 0 ) {
                        		return "<span style='color: red;'>待审核</span>";
                        	} else if(row.auditStatus == 1 ) {
                        		return "<span style='color: green;'>通过</span>";
                        	} else if(row.auditStatus == 2 ) {
                        		return "<span style='color: black;'>不通过</span>";
                        	}
						}
                    },
                    {
                        width: '60',
                        title: '活动状态',
                        field: 'nowStatus'
                    },
                    {
                        width: '50',
                        title: '报名人数',
                        field: 'signupNum',
                        align: 'center',
                        formatter: function (value, row) {
	                        var str = '0';
	                        if(row.signupNum > 0) {
	                        	str = '<a href="javascript:void(0)" onclick="signupFun(' + row.id + ');">' + row.signupNum + '</a>&nbsp;';
	                        }
	                        if(row.auditStatus != 1) {
	                        	str = '---';
	                        }						
	                        return str;
                    	}
                    },
                    {
                    title: '操作',
                    field: 'action',
                    width: '80',
                    formatter: function (value, row) {
                        var str = '';
                        <authority:authority authorizationCode="查看活动" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.id + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
						</authority:authority>
						
                        if(row.nowStatus == '已删除') {
                        	<authority:authority authorizationCode="恢复活动" userRoles="${sessionScope.user.userRoles}">
								str += '<a href="javascript:void(0)" onclick="undoDelete(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>恢复</a>&nbsp;';
							</authority:authority>
                        } else {
                        	if(row.auditStatus == 0) {
	                        	<authority:authority authorizationCode="编辑活动" userRoles="${sessionScope.user.userRoles}">
									str += '<a href="javascript:void(0)" onclick="editFun(' + row.id + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
								</authority:authority>
	                        } else if(row.auditStatus == 1) {
								<authority:authority authorizationCode="查看校友会活动花絮" userRoles="${sessionScope.user.userRoles}">
									str += '<a href="javascript:void(0)" onclick="boardFun(' + row.id + ');"><img class="iconImg ext-icon-activity"/>花絮</a>&nbsp;';
								</authority:authority>
							}
                        }
						
                        return str;
                    }
                }]],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess: function (data) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

		function searchEvent(){
			  if ($('#searchForm').form('validate')) {
				  $('#eventGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
		}
		
		/**--重置--**/
		function resetT(){				
			$('#searchForm')[0].reset();
			$('#category').combobox('clear');
			$('#auditStatus').combobox('clear');
			$('#startFrom').datetimebox('setValue', '');
			$('#startTo').datetimebox('setValue', '');
			$('#endFrom').datetimebox('setValue', '');
			$('#endTo').datetimebox('setValue', '');
		}
		
        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增校友会活动',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/alumni/event/add.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看校友会活动',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/event/view.jsp?id=' + id
            });
        };
        var editFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '编辑校友会活动',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/alumni/event/edit.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };
        var handleFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '投诉处理',
                iconCls: 'ext-icon-no',
                url: '${pageContext.request.contextPath}/page/alumni/event/complaintList.jsp'
            });
        };
        var boardFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看活动花絮',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/event/viewEventBoard.jsp?id=' + id
            });
        };
        var signupFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看报名人员',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/event/viewSignupPeople.jsp?id=' + id
            });
        };
        
        function undoDelete(id) {
        	$.messager.confirm('确认', '确定恢复该活动吗？', function(r) {
				if (r) {					
					$.ajax({
						url : '${pageContext.request.contextPath}/event/eventAction!undoDeletex.action',
						data : {
							id : id
						},
						dataType : 'json',
						success : function(data) {
							if(data.success){
								$("#eventGrid").datagrid('reload');
								$("#eventGrid").datagrid('unselectAll');
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
        }
        function removeData(){
			var rows = $("#eventGrid").datagrid('getChecked');
			var ids = [];
			
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/event/eventAction!deletex.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#eventGrid").datagrid('reload');
									$("#eventGrid").datagrid('unselectAll');
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
								标题
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="title" name="title" style="width: 155px;"/>
							</td>

                           
                            <th align="right">
								地点
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="place" name="place" style="width: 155px;"/>
							</td>
                            
                            <th align="right">
								类别
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								 <input id="category" name="category" class="easyui-combobox" style="width: 150px;" 
											data-options="  
											valueField: 'dictName',  
											textField: 'dictName',  
											editable:false,
											url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('活动类别') 
										" />
							</td>
							
							<th align="right">
                                	审核状态
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
			                    <select class="easyui-combobox" data-options="editable:false" name="auditStatus" id="auditStatus" style="width: 150px;">
									<option value="">&nbsp;&nbsp;&nbsp;</option>
									<option value="0">待审核</option>
									<option value="1">通过</option>
									<option value="2">不通过</option>
								</select>
                            </td>
                        </tr>
                        <tr>
                            <th align="right">
                                	开始时间
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td colspan="4">
			                    <input name="startFrom" id="startFrom" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" /> &nbsp;&nbsp; - &nbsp;&nbsp;
								<input name="startTo" id="startTo" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" />
                            </td>
                            
                            <th align="right">
                                	结束时间
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td colspan="4">
			                    <input name="endFrom" id="endFrom" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" /> &nbsp;&nbsp; - &nbsp;&nbsp;
								<input name="endTo" id="endTo" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" />
                            </td>

                           	<td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchEvent();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-huifu',plain:true"
                                   onclick="resetT();">重置</a>
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
                        	<authority:authority authorizationCode="新增活动" userRoles="${sessionScope.user.userRoles}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-note_add',plain:true"
                               onclick="addFun();">新增活动</a>
                            </authority:authority>
                        </td>
                        <td>
                        	<authority:authority authorizationCode="删除活动" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeData();">删除活动</a>
							</authority:authority>
						</td>
						<td>
                        	<authority:authority authorizationCode="校友会投诉处理" userRoles="${sessionScope.user.userRoles}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-no',plain:true"
                               onclick="handleFun();">投诉处理</a>
                            </authority:authority>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>
