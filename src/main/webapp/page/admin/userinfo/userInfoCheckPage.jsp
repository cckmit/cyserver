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
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action?checkPage=1',
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
                        width : '180',
                        title : '姓名',
                        field : 'userName',
                        align : 'center',
                        sortable : true
                    }, {
                        width : '150',
                        title : '学校',
                        field : 'schoolName',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '院系',
                        field : 'departName',
                        align : 'center'
                    },
                    {
                        width : '100',
                        title : '年级',
                        field : 'gradeName',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '班级',
                        field : 'className',
                        align : 'center'
                    },
                    {
                        width : '180',
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
                        formatter : function(value){
                            if(value!=''&&value!=undefined){
                                return "<span style='color: green;'>已注册</span>"
                            }else{
                                return "<span>未注册</span>"
                            }
                        }
                    }
                    , {
                        title : '操作',
                        field : 'action',
                        width : '200',
                        formatter : function(value, row)
                        {
                            var str = '';
                                <authority:authority authorizationCode="查看校友" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="checkFun(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_edit"/>审核</a>';
                                </authority:authority>
                                <%--<authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="pass(\'' + row.userId + '\');"><img class="iconImg ext-icon-yes"/>通过</a>';
                                </authority:authority>
                                <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="noPass(\'' + row.userId + '\');"><img class="iconImg ext-icon-note_delete"/>不通过</a>';
                                </authority:authority>--%>
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

        function searchUserInfo(){
            $('#userInfoGrid').datagrid('load', serializeObject($('#searchForm')));
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


        function multiLineCheck(checkFlag){
            var rows = $("#userInfoGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0)
            {
                for ( var i = 0; i < rows.length; i++) {
                    if(rows[i].checkFlag == 1 || rows[i].checkFlag == 2){
                        $.messager.alert('提示', '您选择了已审核的校友', 'error');
                        return;
                    }
                    ids.push( rows[i].userId );
                }
                //弹框
                ids = ids.join( ',' );
                var msg = '' ;
                if (checkFlag == 1) {
                    msg = "通过" ;
                } else {
                    msg = "不通过"
                }
                parent.$.messager.confirm('确认', '确定'+msg+'这些校友吗？', function(r) {
                    if (r) {
                        check(ids,checkFlag) ;
                    }
                }) ;

            }
            else
            {
                $.messager.alert('提示', '请选择要审核的校友', 'error');
            }
        }

        function check(ids, checkFlag){
            $.ajax({
                 url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_updateIdea.action',
                 data : {
                 'ids': ids,
                 'checkFlag': checkFlag
                },
                dataType : 'json',
                success : function(result)
                {
                    if (result.success)
                    {
                        userInfoGrid.datagrid('reload');
                        window.parent.refreshMsgNum();
                        parent.$.messager.alert('提示', result.msg, 'info');
                    } else
                    {
                        parent.$.messager.alert('提示', result.msg, 'error');
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

        var viewFun = function(id) {
            var dialog = parent.parent.WidescreenModalDialog({
                title : '查看校友',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/userinfo/viewUserInfo.jsp?id=' + id
            });
        }

        var checkFun = function(id){
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
            $('#province').combobox('clear');
            $('#city').combobox('clear');
            $('#area').combobox('clear');
            $('#city').combobox('loadData',[]);
            $('#area').combobox('loadData',[]);
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
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="userName" name="userInfo.userName" style="width: 150px;" />
                            </td>
                            <th align="right" width="30px;">学校</th>
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
													$('#classId').prop('value',rec.deptId);
												}
												"/>
                            </td>
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
                            <th align="right">
                                学号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="userInfo.studentnumber" style="width: 150px;" />
                            </td>
                            <th align="right">
                                工作单位
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="userInfo.workUnit" style="width: 150px;" />
                            </td>
                            <th align="right">
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
                            <td>是否注册</td>
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
                        </tr>
                        <tr>
                            <td colspan="3">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                            </td>
                            <td colspan="3">
                                <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-thaw',plain:true"
                                       onclick="multiLineCheck(1);">批量通过</a>
                                </authority:authority>
                                <authority:authority authorizationCode="校友审核" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="multiLineCheck(2);">批量不通过</a>
                                </authority:authority>
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
