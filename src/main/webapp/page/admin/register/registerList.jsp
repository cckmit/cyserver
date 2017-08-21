<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        var registerGrid;
        $(function () {
            registerGrid = $('#registerGrid').datagrid({
                url: '${pageContext.request.contextPath}/register/registerAction!getList.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'roleId',
                columns: [[
                	{field:'id',checkbox : true}, 
	                {
	                    width: '100',
	                    title: '姓名',
	                    field: 'x_name',
	                    align : 'center'
	                },
	                {field:'userId',title:'状态',width:80,align:'center',
			        	formatter : function(value, row) {
							if(value!=''&&value!=undefined){
								return "<font style='color: green;'>校友</font>";
							}
							else{
								return "<font style='color: red;'>待核校友</font>";
							}
						}	
			        },
                    {
                        width: '50',
                        title: '性别',
                        field: 'x_sex',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '所在地',
                        field: 'x_address',
                        align : 'center'
                    },
                    {
                        width: '150',
                        title: '学校',
                        field: 'x_school',
                        align : 'center'
                    },
                    {
                        width: '150',
                        title: '院系',
                        field: 'x_depart',
                        align : 'center'
                    },
                    {
                        width: '80',
                        title: '年级',
                        field: 'x_grade',
                        align : 'center'
                    },
                    {
                        width: '150',
                        title: '班级',
                        field: 'x_clazz',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '手机号',
                        field: 'x_phone',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '专业',
                        field: 'x_major',
                        align : 'center'
                    },
                    {
                    title: '操作',
                    field: 'action',
                    width: '100',
                    formatter: function (value, row) {
                        var str = '';
                        <authority:authority authorizationCode="查看返校报名" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.id + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
						</authority:authority>
						//<authority:authority authorizationCode="编辑返校报名" userRoles="${sessionScope.user.userRoles}">
							//str += '<a href="javascript:void(0)" onclick="editFun(' + row.id + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
						//</authority:authority>
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

		function searchRegister(){
			  if ($('#searchForm').form('validate')) {
				  $('#registerGrid').datagrid('load',serializeObject($('#searchForm')));
			  }
		}
		
		/**--重置--**/
		 function resetT(){
				
				$('#searchForm')[0].reset();
				$('#gender').combobox('clear');
				$('#smallAge').combobox('clear');
				$('#largeAge').combobox('clear');
				$('#schoolId').combobox('clear');
				$('#depart').combobox('clear');
				$('#grade').combobox('clear');
				$('#classes').combobox('clear');
				$('#major').combobox('clear');
				$('#in_year').combobox('clear');
				$('#studentType').combobox('clear');
				$('#location').combobox('clear');
				
		}
		
		
		
        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增报名信息',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/register/addRegister.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, registerGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看报名信息',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/register/viewRegister.jsp?id=' + id
            });
        };
        var editFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '编辑报名信息',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/register/editRegister.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, registerGrid, parent.$);
                    }
                }]
            });
        };
        
        function removeData(){
			var rows = $("#registerGrid").datagrid('getChecked');
			var ids = [];
			
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/register/registerAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#registerGrid").datagrid('reload');
									$("#registerGrid").datagrid('unselectAll');
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
        
        /**
        var removeFun = function (id) {
            parent.$.messager.confirm('确认', '您确定要删除此记录？', function (r) {
                if (r) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/register/registerAction!delete.action',
                        data: {
                            id: id
                        },
                        dataType: 'json',
                        success: function (result) {
                            if (result.success) {
                                registerGrid.datagrid('reload');
                                parent.$.messager.alert('提示', result.msg, 'info');
                            } else {
                                parent.$.messager.alert('提示', result.msg, 'error');
                            }
                        },
                        beforeSend: function () {
                            parent.$.messager.progress({
                                text: '数据提交中....'
                            });
                        },
                        complete: function () {
                            parent.$.messager.progress('close');
                        }
                    });
                }
            });
        };
        */
        
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
                             <th align="right" width="30px">
                                 学校
                             </th>
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

								<th align="right" width="30px">院系</th>
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
								
								
							<th align="right" width="30px">年级</th>
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
								
								
							<th align="right" width="30px">班级</th>
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
													$('#major').combobox('clear');
									                }
									            }],  
												editable:false" />
									</td>
                        </tr>
                        <tr>
                            <th align="right" width="30px">
                               	姓名
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
                                <input name="name" style="width: 155px;"/>
                            </td>

                            <th align="right" width="30px">
                               	性别
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
                                <select name="gender" style="width: 155px;" class="easyui-combobox"  data-options="editable:false">
			                        <option value="">全部</option>
			                        <option value="男">男</option>
			                        <option value="女">女</option>
			                    </select>
                            </td><%--

                            <th align="right" width="30px">
                                	年龄
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
                                <select id="smallAge" name="smallAge" style="width:65px" class="easyui-combobox"  
									data-options="editable:false">
								<option value="">&nbsp;</option>
								<c:forEach var="i" begin="16" end="200" varStatus="status">
								<option value="${status.index}">${status.index}岁</option>
								</c:forEach>
								</select> - 
								<select id="largeAge" name="largeAge" style="width:65px" class="easyui-combobox"  
									data-options="editable:false">
								<option value="">&nbsp;</option>
								<c:forEach var="i" begin="16" end="200" varStatus="status">
								<option value="${status.index}">${status.index}岁</option>
								</c:forEach>
								</select>
                            </td>
                         --%>
                           <th align="right" width="30px">
									所在地
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="location" name="x_address" style="width: 155px;"/>
							</td>
							<td colspan="3">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchRegister();">查询</a>
                            </td>
                         </tr>
                         
                         <tr>
							<!--
							<th align="right" width="30px">
									所在地
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="location" name="city"  class="easyui-combogrid" style="width:155px" data-options=" 
											
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
											editable:false
										">
									</select>
							</td>
							-->
                            
                          <%--
                            
                            
                            <th align="right" width="30px">
                                	学历层次
                            </th>
                            <td>
								<div class="datagrid-btn-separator"></div>
							</td>
                            <td>
			                    <input id="studentType" name="degree" class="easyui-combobox" style="width: 155px;" 
											data-options="  
											valueField: 'dictName',  
											textField: 'dictName',  
											editable:false,
											url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历') 
										" />
                            </td>

							--%>
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
                        	<%--<authority:authority authorizationCode="新增返校报名" userRoles="${sessionScope.user.userRoles}">
	                            <a href="javascript:void(0);" class="easyui-linkbutton"
	                               data-options="iconCls:'ext-icon-note_add',plain:true"
	                               onclick="addFun();">新增报名</a>
	                        </authority:authority>
                        --%></td>
                        <td>
                        	<authority:authority authorizationCode="删除返校报名" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeData();">删除</a>
							</authority:authority>
						</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="registerGrid"></table>
</div>
</body>
</html>
