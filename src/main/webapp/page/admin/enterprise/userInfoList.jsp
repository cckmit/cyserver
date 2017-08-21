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
        var href = location.href;
        var enterpriseTeamId = "";
        if (href.indexOf("?")!=-1){
            enterpriseTeamId = href.split("?")[1].split("enterpriseTeamId=")[1].split("&")[0];
        }
        var userInfoGrid;
        var dialog ;

        function closeDiaLog($dialog) {
            $dialog.dialog('close');
            dialog = $dialog;
        }

        function searchUserInfo(){
            var flag = validataForm();
            if(!flag){
                $.messager.alert('提示', '请输入或选择查询条件！', 'error');
                return;
            }
            if(userInfoGrid == null){
                userInfoGrid = $('#userInfoGrid').datagrid({
                    url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action',
                    fit : false,
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
                            width : '100',
                            title : '学校',
                            field : 'schoolName',
                            align : 'center'
                        },
                        {
                            width : '80',
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
                            width : '80',
                            title : '班级',
                            field : 'className',
                            align : 'center'
                        },
                        {
                            width : '100',
                            title : '专业',
                            field : 'majorName',
                            align : 'center'
                        },
                        {
                            width : '80',
                            title : '手机号',
                            field : 'telId',
                            align : 'center'
                        },
                        {
                            width : '100',
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
                            width : '100',
                            formatter : function(value, row)
                            {
                                var str = '';
                                str += '<a href="javascript:void(0)" onclick="relation(\'' + row.userId + '\');"><img class="iconImg ext-icon-note"/>关联</a>&nbsp;';
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
            }
            $('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
        }

        function validataForm(){
            var flag = false;
            if($('#userName').val() != ""){
                flag = true;
                return flag;
            }
            if($('#schoolId').val() != ""){
                flag = true;
                return flag;
            }
            if($('#gradeId').val() != ""){
                flag = true;
                return flag;
            }
            if($('#classId').val() != ""){
                flag = true;
                return flag;
            }
            if($('#majorId').val() != ""){
                flag = true;
                return flag;
            }
            return flag;
        }

        var relation = function(id){
            parent.parent.$.messager.confirm('确认', '确定关联校友？', function(r){
                if (r){
                    var url;
                    if(enterpriseTeamId != '0'){
                        url = '${pageContext.request.contextPath}/enterprise/enterpriseTeamAction!update.action'
                    }
                    $.ajax({
                        url : url,
                        data : {'enterpriseTeam.userId' : id,"enterpriseTeam.id" : enterpriseTeamId,"enterpriseTeam.isAlumni":"1"},
                        dataType : 'json',
                        success : function(data){
                            if (data.success){
                                $("#userInfoGrid").datagrid('reload');
                                parent.parent.$.messager.alert('提示', data.msg, 'info',function(){
                                    $('#closeDialog', parent.document).click();
                                    dialog.datagrid("reload");
                                    dialog.dialog('close');
                                });
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

    </script>
</head>

<body>
<div id="toolbar" style="display: block;">
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
                            <td colspan="3" align="right">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
