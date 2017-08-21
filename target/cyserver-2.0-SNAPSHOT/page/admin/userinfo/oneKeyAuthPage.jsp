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
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_dataGridForOneKey.action',
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
                    }
                    , {
                        title : '操作',
                        field : 'action',
                        width : '120',
                        formatter : function(value, row)
                        {
                            var str = '';
                            <authority:authority authorizationCode="查看校友" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>

                            <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="oneKeyAuth(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_edit"/>一键认证</a>';
                            </authority:authority>

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

        var mOneKeyAuth = function(){
            var rows = $("#userInfoGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0){
                parent.parent.$.messager.confirm('确认', '确定为您选择的校友添加认证？', function(r){
                    if (r)
                    {
                        for ( var i = 0; i < rows.length; i++)
                        {
                            ids.push(rows[i].userId);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_mOneKeyAuth.action',
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
            }else{
                parent.parent.$.messager.alert('提示', '请选择校友！', 'error');
            }
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
            $('#deptId').prop('value','');
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
                                <input name="deptId" id="deptId" type="hidden">
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
													$('#deptId').prop('value','');
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
													$('#deptId').prop('value',rec.deptId);
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
                                                    if($('#deptId').val() != '' && $('#deptId').val().length > 6){
                                                        $('#deptId').prop('value',$('#deptId').val().substring(0,6));
                                                    }else{
                                                        $('#deptId').prop('value','');
                                                    }
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
													$('#deptId').prop('value',rec.deptId);
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
													if($('#deptId').val() != '' && $('#deptId').val().length > 10){
                                                        $('#deptId').prop('value',$('#deptId').val().substring(0,10));
                                                    }else{
                                                        $('#deptId').prop('value','');
                                                    }
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);
													$('#deptId').prop('value',rec.deptId);
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
													if($('#deptId').val() != '' && $('#deptId').val().length > 14){
                                                        $('#deptId').prop('value',$('#deptId').val().substring(0,14));
                                                    }else{
                                                        $('#deptId').prop('value','');
                                                    }
									                }
									            }],
												onSelect: function(rec){
													$('#deptId').prop('value',rec.deptId);
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
                            <td colspan="6" align="right">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="mOneKeyAuth();" onclick="">一键批量认证</a>
                            </td>
                            <td colspan="9" style="color: red; text-align: center">
                                通过一键认证创建的用户，账号是校友的手机号、密码是校友手机号后6位
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="userInfoGrid"></table>
</div>
</body>
</html>
