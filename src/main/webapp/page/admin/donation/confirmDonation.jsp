<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
        var donationGrid;
        $(function() {
            donationGrid = $('#donationGrid').datagrid({
                url : '${pageContext.request.contextPath}/donation/donationAction!dataGrid.action?checkPage=1',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'donationId',
                columns : [[
                    {
                        field : 'userId',
                        checkbox : true
                    },
                    {
                        width : '100',
                        title : '姓名',
                        field : 'x_name',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '是否是校友',
                        field : 'flag',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value==1&&row.userId!=''){
                                return "<font style='color: green;'>校友</font>";
                            }else if(value==1&&row.userId==''){
                                return "<font style='color: red;'>待核校友</font>";
                            }
                            else{
                                return "<font style='color: gray;'>非校友</font>";
                            }
                        }
                    },
                    {
                        width : '250',
                        title : '班级',
                        field : 'x_fullname',
                        align : 'center'
                    },
                    {
                        width : '100',
                        title : '专业',
                        field : 'x_major',
                        align : 'center'
                    },
                    {
                        width : '180',
                        title : '订单编号',
                        field : 'orderNo',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '捐赠项目',
                        field : 'projectName',
                        align : 'center',
                        formatter : function(value, row) {
                            if(row.project!=undefined){
                                return row.project.projectName;
                            }else{
                                return "";
                            }
                        }
                    },
                    {
                        width : '80',
                        title : '捐赠金额',
                        field : 'money',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '捐赠时间',
                        field : 'donationTime',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '支付金额',
                        field : 'payMoney',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '支付时间',
                        field : 'payTime',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '支付状态',
                        field : 'payStatus',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value==1){
                                return "已支付";
                            }else{
                                return "未支付";
                            }
                        }
                    },
                    {
                        title : '操作',
                        field : 'action',
                        width : '120',
                        formatter : function(value, row) {
                            var str = '';
                            <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="confirmDonation(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>确认</a>&nbsp;';
                            </authority:authority>
                            return str;
                        }
                    }
                ]],
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

        function confirmDonation(id)
        {
            $.ajax({
                url : '${pageContext.request.contextPath}/donation/donationAction!update.action',
                data : {
                    'donation.donationId':id,
                    'donation.confirmStatus':'30'
                },
                dataType : 'json',
                success : function(result)
                {
                    if (result.success)
                    {
                        donationGrid.datagrid('reload');
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

        /**--查询--**/
        function searchFun(){
            $('#donationGrid').datagrid('load',serializeObject($('#searchForm')));
        }


        /**--重置--**/
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
                           <%-- <th align="right" width="30px;">学校</th>
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

                            <!-- 这里添加院系（程辉） -->
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
                            <th align="right" width="30px;">专业</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="major" name="donation.majorId" class="easyui-combobox" style="width: 150px;"
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
                            <th align="right" width="30px;">学历</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="studentType" class="easyui-combobox" style="width: 150px;" name="donation.userInfo.studentType"
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
                            </td>--%>
                            <th align="right" width="30px;">金额范围</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="4">
                                <input name="startMoney" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input> - <input name="endMoney" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
                            </td>
                            <th align="right" width="30px;">时间范围</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="5">
                                <input name="startTime" style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/> - <input name="endTime"  style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
                            </td>
                        </tr>
                        <tr>
                            <th align="right" width="30px;">捐赠项目</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="projectId" name="donation.projectId" class="easyui-combobox" style="width: 150px;"
                                       data-options="editable:false,
									        valueField: 'projectId',
									        textField: 'projectName',
									        prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#projectId').combobox('clear');
									                }
									            }],
									        url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getAll.action'"/>
                            </td>

                            <th align="right" width="30px;">支付状态</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select class="easyui-combobox" data-options="editable:false" name="donation.payStatus" style="width: 150px;">
                                    <option value="">全部</option>
                                    <option value="0">未支付</option>
                                    <option value="1">已支付</option>
                                </select>
                            </td>
                            <th align="right" width="30px;">姓名</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="donation.userInfo.userName" style="width: 150px;" class="easyui-validatebox"></input>
                            </td>
                            <td colspan="3">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchFun();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="donationGrid"></table>
</div>
</body>
</html>
