<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/authority" prefix="authority" %>
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
        var schoolConfigGrid;
        $(function () {
            schoolConfigGrid = $('#schoolConfigGrid').datagrid({
                url: '${pageContext.request.contextPath}/logs/logsAction!dataGrid.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field: 'id', checkbox: true},
                    {
                        width: '150',
                        title: '登录帐号',
                        field: 'account',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '登录IP',
                        field: 'ip',
                        align : 'center'
                    },
                    {
                        width: '200',
                        title: '浏览器',
                        field: 'browser',
                        align : 'center'
                    },
                    {
                        width: '200',
                        title: '日志内容',
                        field: 'content',
                        align : 'center'
                    },
                    {
                        width: '100',
                        title: '所属模块',
                        field: 'menuName',
                        align : 'center'
                    },
                    {
                        width: '200',
                        title: '请求URL',
                        field: 'url',
                        align : 'center'
                    },
                    {
                        width: '119',
                        title: '创建时间',
                        field: 'create_time',
                        formatter:function (value, row){
                        	var str = '';
                        	var create_time = row['create_time'];
                        	//str = create_time.substring(0,19);
                        	str = create_time;
                        	return str;
                        },
                        align : 'center'
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

        function schoolConfigSearch() {
            if ($('#searchForm').form('validate')) {
                schoolConfigGrid.datagrid('load', serializeObject($('#searchForm')));
            }
        }

		/**--删除--**/
        function removeData() {
            var rows = schoolConfigGrid.datagrid('getSelections');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function (r) {
                    if (r) {
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i]['id']);
                        }
                        $.ajax({
                            url: '${pageContext.request.contextPath}/logs/logsAction!delete.action',
                            data: {
                                ids: ids.join(',')
                            },
                            dataType: 'json',
                            success: function (data) {
                                if (data.success) {
                                    $("#schoolConfigGrid").datagrid('reload');
                                    $("#schoolConfigGrid").datagrid('unselectAll');
                                    $.messager.alert('提示', data.msg, 'info');
                                }
                                else {
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            },
                            beforeSend: function () {
                                $.messager.progress({
                                    text: '数据提交中....'
                                });
                            },
                            complete: function () {
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
        }
        
        
        /**--重置--**/
        function resetT(){
			$('#account').prop('value','');
			$('#startTime').prop('value','');
			$('#endTime').prop('value','');
			$('#pid').combobox('clear');
			
			/*
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
			*/
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
                                帐号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="account" name="logs.account" style="width: 150px;"/>
                            </td>
                            
                            <th align="right" width="30px;">时间范围</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="startTime" name="logs.startTime" style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/> - <input id="endTime" name="logs.endTime"  style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
							</td>

							<th>
							模块
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select id="pid" name="logs.pid" class="easyui-combotree" data-options="idField:'id',textField:'text',parentField:'pid',url:'${pageContext.request.contextPath}/resource/resourceAction!doNotNeedSecurity_initMenu.action'" style="width: 150px;"></select>
							</td>
							

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="schoolConfigSearch();">查询</a>
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
                            <authority:authority authorizationCode="删除日志" userRoles="${sessionScope.user.userRoles}">
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
    <table id="schoolConfigGrid"></table>
</div>
</body>
</html>
