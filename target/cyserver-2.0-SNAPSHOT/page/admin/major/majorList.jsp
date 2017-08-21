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
        var majorGrid;
        $(function () {
            majorGrid = $('#majorGrid').datagrid({
                url: '${pageContext.request.contextPath}/major/majorAction!getList.action',
                fit: true,
                border: false,
//                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                nowrap: false,
                idField: 'majorId',
                columns: [[
                	{field:'majorId',checkbox : true}, 
                	{
	                    width: '200',
	                    title: '专业',
	                    field: 'majorName'
                	},
                    {
                        width: '450',
                        title: '院系',
                        field: 'deptList',
                        formatter : function(value, row) {
							if(row.deptList!=undefined){
								var depts = "";
								for (var i = 0; i < row.deptList.length; i++) {
									var deptName = row.deptList[i].fullName;
									depts += deptName + "<br />";									
								}
								return depts;
							}else{
								return "";
							}
						}
                    },
                    {
                    title: '操作',
                    field: 'action',
                    width: '200',
                    formatter: function (value, row) {
                        var str = '';
                        <authority:authority authorizationCode="查看专业" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.majorId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
						</authority:authority>
						<authority:authority authorizationCode="编辑专业" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="editFun(' + row.majorId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
						</authority:authority>
                        //str += '<a href="javascript:void(0)" onclick="removeFun(' + row.id + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
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

		function searchActivity(){
			  if ($('#searchForm').form('validate')) {
				  $('#majorGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
		}
		
        var addFun = function () {
            var dialog = parent.modalDialog({
                title: '新增专业',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/major/addMajor.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, majorGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看专业',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/major/viewMajor.jsp?id=' + id
            });
        };
        var editFun = function (id) {
            var dialog = parent.modalDialog({
                title: '编辑专业',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/major/editMajor.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, majorGrid, parent.$);
                    }
                }]
            });
        };
        
        
        function removeData(){
			var rows = $("#majorGrid").datagrid('getChecked');
			var ids = [];
			
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].majorId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/major/majorAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#majorGrid").datagrid('reload');
									$("#majorGrid").datagrid('unselectAll');
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
			} else {
				parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
			}
	}
        
        function resetT(){
    		$('#school').combobox('clear');
    		$('#depart').combobox('clear');
    		$('#depart').combobox('loadData',[]);
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
                            <th align="right">
                                	专业名称
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
			                    <input name="majorName" style="width: 150px;"/>
                            </td>

                           
                            
                            
                            <th align="right">
									学校
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>					
									<input id="school" name="school" style="width: 155px;" class="easyui-combobox"
										data-options="  
											
											valueField: 'deptId',  
											textField: 'deptName',  
											editable:false,
											url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',  
											onSelect: function(rec){
												var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
												$('#depart').combobox('clear');
												$('#depart').combobox('reload', url);  
											}
									" />
								</td >
								
								
								<th align="right">
									院系
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="depart" name="department" style="width:155px;" class="easyui-combobox" 
										data-options="  
											
											valueField: 'deptId',  
											textField: 'deptName',  
											editable:false" />
								</td>
								

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchActivity();">查询</a>&nbsp;
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
                        	<authority:authority authorizationCode="新增专业" userRoles="${sessionScope.user.userRoles}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'ext-icon-note_add',plain:true"
                               onclick="addFun();">新增专业</a>
                            </authority:authority>
                        </td>
                        <td>
                        	<authority:authority authorizationCode="删除专业" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeData();">删除专业</a>
							</authority:authority>
						</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="majorGrid"></table>
</div>
</body>
</html>
