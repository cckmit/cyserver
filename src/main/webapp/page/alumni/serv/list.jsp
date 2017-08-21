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
                url: '${pageContext.request.contextPath}/serv/servAction!getListx.action',
                nowrap: true,
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
	                    width: '300',
	                    title: '内容',
	                    field: 'content'
                	},
                	{
	                    width: '120',
	                    title: '地域',
	                    field: 'region'
                	},
                    {
                        width: '80',
                        title: '类别',
                        field: 'category',
                        align: 'center',
                        formatter : function(value, row) {
                        	if(row.category == 1 ) {
                        		return "互帮互助";
                        	} else if(row.category == 2 ) {
                        		return "项目合作";
                        	} else if(row.category == 3 ) {
                        		return "求职招聘";
                        	}
						}
                    },
                    {
                        width: '120',
                        title: '创建时间',
                        field: 'createTime'
                    },
                    {
                        width: '40',
                        title: '回复',
                        align: 'center',
                        field: 'replyNum',
                        formatter: function (value, row) {
	                        var str = '0';
	                        if(row.replyNum > 0) {
	                        	str = '<a href="javascript:void(0)" onclick="showReplyFun(' + row.id + ');">' + row.replyNum + '</a>&nbsp;';
	                        }						
	                        return str;
                    	}                       
                    },
                    {
                        width: '40',
                        title: '投诉',
                        align: 'center',
                        field: 'complaintNum',
                        formatter: function (value, row) {
	                        var str = '0';
	                        if(row.complaintNum > 0) {
	                        	str = '<a href="javascript:void(0)" onclick="showComplaintFun(' + row.id + ');">' + row.complaintNum + '</a>&nbsp;';
	                        }						
	                        return str;
                    	}                       
                    },
                    {
                        width: '60',
                        title: '审核',
                        field: 'auditStatus',
                        align: 'center',
                        formatter : function(value, row) {
                        	if(row.type == 0 ) {
                        		return "---";
                        	}
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
                        width: '100',
                        title: '状态',
                        field: 'status',
                        formatter : function(value, row)
						{							
							if(row.status != null) {
								if(row.status == '3') {
									return '用户已删除';
								} else if(row.status == '4') {
									return '管理员已删除';
								}
							}
                       		if(row.auditStatus == 0 ) {
                       			return "未审核";
                        	} else if(row.auditStatus == 2 ) {
                        		return "审核未通过";
                        	}                        	
							if(row.status != null) {
								if(row.status == '0') {
									return '正常';
								} else if(row.status == '1') {
									return '投诉处理-正常';
								} else if(row.status == '2') {
									return '投诉处理-违规';
								}
							}
						}
                    },
                    {
	                    title: '操作',
	                    field: 'action',
	                    width: '120',
	                    formatter: function (value, row) {			        	
	                        var str = '';                       
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.id + ',' + row.category + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							
							if(row.status == 3 || row.status == 4) {                        	
								str += '<a href="javascript:void(0)" onclick="undoDelete(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>恢复</a>&nbsp;';
	                        } else {
	                        	if(row.auditStatus == 0) {
	                        		str += '<a href="javascript:void(0)" onclick="editFun(' + row.id + ',' + row.category + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
	                        	} else if(row.auditStatus == 1) {
	                        		str += '<a href="javascript:void(0)" onclick="replyFun(' + row.id + ');"><img class="iconImg ext-icon-micro"/>回复</a>&nbsp;';
	                        	}
	                        }						

	                        return str;
	                    }
	                 },
	                 {
	                    title: '投诉处理',
	                    field: 'action2',
	                    width: '100',
	                    formatter: function (value, row) {			        	
	                        var str = '';                       
							if(row.status == 0 && row.auditStatus == 1 && row.complaintNum > 0) {
								str += '<a href="javascript:void(0)" onclick="handleYes(' + row.id + ');"><img class="iconImg ext-icon-yes"/>正常</a>&nbsp;';
								str += '<a href="javascript:void(0)" onclick="handleNo(' + row.id + ');"><img class="iconImg ext-icon-recyle"/>违规</a>&nbsp;';
	                        } else if(row.status == 1) {
								str += '<a href="javascript:void(0)" onclick="handleUndo(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>撤销处理</a>&nbsp;';
	                        } else if(row.status == 2) {
								str += '<a href="javascript:void(0)" onclick="handleUndo(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>撤销处理</a>&nbsp;';
	                        }
	                        return str;
	                    }
	                 }
                ]],
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
			$('#createFrom').datetimebox('setValue', '');
			$('#createTo').datetimebox('setValue', '');
			$('#auditStatus').combobox('clear');
		}
		
        var showReplyFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看回复',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/serv/viewServReply.jsp?id=' + id
            });
        };
        
        var showComplaintFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看投诉',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/serv/viewServComplaint.jsp?id=' + id
            });
        };
        
        var replyFun = function (id) {
            var dialog = parent.modalDialog({
                title: '添加校友会回复',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/alumni/serv/servReply.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };
		
        var addFun = function (category) {
        	var categoryStr;
        	if(category == 1) {
        		categoryStr = '互帮互助';
        	} else if(category == 2) {
        		categoryStr = '项目合作';
        	} else if(category == 3) {
        		categoryStr = '求职招聘';
        	}
            var dialog = parent.modalDialog({
                title: '新增' + categoryStr,
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/alumni/serv/add.jsp?category=' + category,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id,category) {
        	var categoryStr;
        	if(category == 1) {
        		categoryStr = '互帮互助';
        	} else if(category == 2) {
        		categoryStr = '项目合作';
        	} else if(category == 3) {
        		categoryStr = '求职招聘';
        	}
            var dialog = parent.modalDialog({
                title: '查看' + categoryStr,
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/alumni/serv/view.jsp?id=' + id
            });
        };
        
        var editFun = function (id,category) {
        	var categoryStr;
        	if(category == 1) {
        		categoryStr = '互帮互助';
        	} else if(category == 2) {
        		categoryStr = '项目合作';
        	} else if(category == 3) {
        		categoryStr = '求职招聘';
        	}
            var dialog = parent.modalDialog({
                title: '编辑' + categoryStr,
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/alumni/serv/edit.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };
        
        function undoDelete(id) {
        	$.messager.confirm('确认', '确定恢复该服务吗？', function(r) {
				if (r) {					
					handleStatus(id,0);
				}
			});
        }
        
        function handleYes (id){
        	$.messager.confirm('确认', '确定该服务正常吗？', function(r) {
				if (r) {
					handleStatus(id,1);
				}
			});
        }
        function handleNo (id){
        	$.messager.confirm('确认', '确定该服务违规吗？', function(r) {
				if (r) {
					handleStatus(id,2);
				}
			});
        }
        function handleUndo (id){
        	$.messager.confirm('确认', '确定撤销处理吗？撤销后服务恢复为投诉未处理状态。', function(r) {
				if (r) {
					handleStatus(id,0);
				}
			});
        }
        
        function handleStatus(serviceId, status){			
			$.ajax({
				url : '${pageContext.request.contextPath}/serv/servAction!handleStatusx.action',
				data : {
					id : serviceId,
					handleStatus: status
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
							url : '${pageContext.request.contextPath}/serv/servAction!deletex.action',
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
								地域
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="region" name="region" style="width: 155px;"/>
							</td>
                            
                            
							
                       
                            <th align="right">
                                	发表时间
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td colspan="4">
			                    <input name="createFrom" id="createFrom" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" /> &nbsp;&nbsp; - &nbsp;&nbsp;
								<input name="createTo" id="createTo" class="easyui-datetimebox " 
									data-options="editable:false" style="width: 150px;" />
                            </td>
                            
                        </tr>
                        <tr>
                        	<th align="right">
								类别
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								 <select class="easyui-combobox" data-options="editable:false" name="category" id="category" style="width: 150px;">
									<option value="">&nbsp;&nbsp;&nbsp;</option>
									<option value="1">互帮互助</option>
									<option value="2">项目合作</option>
									<option value="3">求职招聘</option>
								</select>
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
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-note_add',plain:true"
                               onclick="addFun(1);">新增互帮互助</a>
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-note_add',plain:true"
                               onclick="addFun(2);">新增项目合作</a>
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-note_add',plain:true"
                               onclick="addFun(3);">新增求职招聘</a>
                        </td>
                        <td>
							<a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'ext-icon-note_delete',plain:true"
								onclick="removeData();">删除</a>
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
