<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        var alumniTree;
        $(function() {
            alumniTree = $('#alumniTree').tree({
                url : '${pageContext.request.contextPath}/alumni/alumniAction!getAlumniTree.action',
                state: closed,
                animate : true,
                onClick : function(node){
//                    $(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
//                    node.state = node.state === 'closed' ? 'open' : 'closed';
                    showList(node.id);
                },
                onBeforeLoad : function(node, param)
                {

                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(node, data)
                {
                    parent.$.messager.progress('close');
                }
            });
        });
        var membersGrid;
        $(function () {
            membersGrid = $('#membersGrid').datagrid({
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!alumniMemebers.action?aluid=1',
                fit : true,
                method : 'post',
                border : true,
                singleSelect : true,
                striped : true,
                pagination : true,
                nowrap : false,
                sortName:'name',
                sortOrder:'asc',
                columns : [ [
                    {
                        width : '60',
                        title : '姓名',
                        field : 'name',
                        align : 'center',
                        sortable : true,
                        /*//lixun
                        formatter : function( value, row )
                        {
                            var str = row.userId;
                            str = "" + str;
                            str = str.substring( 0, str.search(",") == -1 ? str.length : str.search(",") );
                            return '<a href="javascript:void(0)" onclick="viewFun(\'' + str + '\');">'+value+'</a>&nbsp;';
                        }
                        //lixun*/
                    },{
                        width : '80',
                        title : '所属分会',
                        field : 'alumniName',
                        align : 'center'
                    },{
                        width : '30',
                        title : '性别',
                        field : 'sex',
                        align : 'center',
                        formatter : function(value){
                            if(value == '0'){
                                return '男';
                            }else if(value == '1'){
                                return '女';
                            }else{
                                return '';
                            }
                        }
                    },
                    {
                        width : '100',
                        title : '电话号码',
                        field : 'phoneNum',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '住址',
                        field : 'address',
                        align : 'center'
                    },
                    {
                        width : '100',
                        title : 'E-mail',
                        field : 'email',
                        align : 'center'
                    },
                    {
                        width : '80',
                        title : '工作单位',
                        field : 'workUtil',
                        align : 'center'
                    },
                    {
                        width : '250',
                        title : '学习经历',
                        field : 'groupName',
                        align : 'center',
                        formatter: function (value) {
                            var result = '';
                            if(value){
                                var tmp = value.split('_');
                                for(var i in tmp){
                                    result += tmp[i]+'</br>';
                                }
                            }
                            return result;
                        }

                    },
                    {
                        width : '80',
                        title : '专业',
                        field : 'profession',
                        align : 'center'

                    },{
                        width : '60',
                        title : '审核信息',
                        field : 'status',
                        align : 'center',
                        formatter : function(value){
                            switch(value){
                                case '10': return '待审核';
                                case '20': return '正式会员';
                                case '30': return '未过审';
                                case '40': return '已拒绝';
                            }
                        }
                    },{
                        width : '150',
                        title : '操作',
                        field : 'action',
                        align : 'center',
                        formatter : function(value, row) {
                            var str = '';
                            if(row.currAlumniId==row.alumni_id){
                                if(row.status == '10'){
                                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="入会审核">
                                    str += '<a href="javascript:void(0)" onclick="checkPass('+ row.userAlumniId+');"><img class="iconImg ext-icon-yes"/>同意加入</a>&nbsp;';
                                    </authority:authority>
                                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="入会审核">
                                    str += '<a href="javascript:void(0)" onclick="checkFail('+ row.userAlumniId+');"><img class="iconImg ext-icon-note_delete"/>拒绝加入</a>&nbsp;';
                                    </authority:authority>
                                }else if(row.status == '20'){
                                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="入会审核">
                                    str += '<a href="javascript:void(0)" onclick="kickOutMember('+ row.accountNum+');"><img class="iconImg ext-icon-note_delete"/>剔除成员</a>&nbsp;';
                                    </authority:authority>
                                }
                            }
                            return str;
                        }
                    }
                     ] ],
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
                    //alert( JSON.stringify( data ) );
                }
            });
        });
        function searchUserInfo()
        {
            $('#membersGrid').datagrid('load', serializeObject($('#searchForm')));
        }

        function showList(userDeptId) {
            $('#userDeptId').val(userDeptId);
            $('#membersGrid').datagrid('load', {'userDeptId': userDeptId});
        }

        var checkPass = function (userAlumniId) {
            $.ajax({
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!checkInitiate.action',
                data : {
                    'userAlumniId' : userAlumniId,
                    'status' : '20'
                },
                dataType : 'json',
                success : function(result) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    $('#membersGrid').datagrid('reload');
                    window.parent.refreshMsgNum();
                }
            });
        }

        var checkFail = function (userAlumniId) {
            $.ajax({
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!checkInitiate.action',
                data : {
                    'userAlumniId' : userAlumniId,
                    'status' : '30'
                },
                dataType : 'json',
                success : function(result) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    $('#membersGrid').datagrid('reload');
                    window.parent.refreshMsgNum();
                }
            });
        }
        // 剔除成员
        function kickOutMember(accountNum) {
            $.messager.confirm('确认', '确定从本会剔除该成员吗？', function(r) {
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_kickOutAlumni.action',
                        data : {
                            accountNum : accountNum
                        },
                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                $("#membersGrid").datagrid('reload');
                                $("#membersGrid").datagrid('unselectAll');
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


        function exportFun(){
            $.ajax({
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!exportData.action',
                data : $('#searchForm').serialize(),
                dataType : 'json',
                success : function(result) {
                    if (result.success) {
                        if(result.msg!=""){
                            $('#exportResult').html("<a id='mf' href='"+result.msg+"'>导出结果下载</a>")
                            parent.parent.$.messager.alert('提示', "导出成功,请在导出结果处下载导出结果", 'info');
                        }else{
                            parent.parent.$.messager.alert('提示', "无数据导出", 'info');
                        }
                    } else {
                        parent.parent.$.messager.alert('提示', result.msg, 'error');
                    }
                },
                beforeSend:function(){
                    $('#mf').remove();
                    parent.parent.$.messager.progress({
                        text : '数据导出中....'
                    });
                },
                complete:function(){
                    parent.parent.$.messager.progress('close');
                }
            });
        }

        /**--短信发送--**/
        function messageSend(){
            //查询的条件
            var userName = $("#userName").val();
            var schoolId = $("#schoolId").val();
            var departId = $("#departId").val();
            var classId = $("#classId").val();
            var majorId = $("#major").combobox('getValue');

            var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
            var url = "<%=path %>/page/admin/sms/send.jsp?"+params;
            var dialog = parent.parent.WidescreenModalDialog({
                title : '短信发送',
                iconCls : 'ext-icon-export_customer',
                url : url,
                buttons : [ {
                    text : '发送',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.doSend();
                    }
                } ]
            });
        }

        /**--邮件发送--**/
        function emailSend(){
            //查询的条件
            var userName = $("#userName").val();
            var schoolId = $("#schoolId").val();
            var departId = $("#departId").val();
            var classId = $("#classId").val();
            var majorId = $("#major").combobox('getValue');

            var params = "userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&majorId="+majorId;
            var url = "<%=path %>/page/admin/email/send.jsp?"+params;
            var dialog = parent.parent.WidescreenModalDialog({
                title : '邮件发送',
                iconCls : 'ext-icon-export_customer',
                url : url,
                buttons : [ {
                    text : '发送',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.submitForm();
                    }
                } ]
            });
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'west',border:1" width="18%">
    <ul id="alumniTree"></ul>
</div>

<div id="toolbar" style="display: none;" width="82%">
    <table>
        <tr>
            <td>
                <form id="searchForm">
                    <table>

                        <tr>
                            <input name="isAlumni" value="2" style="width: 130px;" type="hidden" />
                            <input name="userDeptId" id="userDeptId" type="hidden"/>
                            <th align="right">
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="userName" name="userInfo.userName" style="width: 130px;" />
                            </td>

                            <th align="right">
                                状态
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select id="userAlStatus" name="userInfo.userAlStatus" class="easyui-combobox" style="width:150px;"
                                    data-options="editable:false"
                                >
                                    <option value="">--请选择--</option>
                                    <option value="10">待审核</option>
                                    <option value="20">正式会员</option>
                                    <option value="30">未过审</option>
                                </select>

                            </td>
                           <%-- <th align="right" width="30px;">专业</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="major" name="userInfo.majorId" class="easyui-combobox" style="width: 130px;"
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
                                <input id="studentType" class="easyui-combobox" style="width: 130px;" name="userInfo.studentType"
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

                            <!--
                            <th align="right">
                                所在城市
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td  colspan="7">
                                <input class="easyui-combobox" name="province" id="province" style="width: 110px;"
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
                                &nbsp; <input class="easyui-combobox" name="city" id="city" style="width: 110px;"
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
                                &nbsp; <input class="easyui-combobox" name="area" id="area" style="width: 110px;"
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
                            -->
                            <!--
                            </tr>
                            <tr>

                            <td>是否注册</td>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select class="easyui-combobox" data-options="editable:false" name="regflag" style="width: 130px;">
                                    <option value="">--请选择--</option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </td>
                            -->

                            <td colspan="3">
                                　　
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </td>
        </tr>
       <%-- <tr>
            <td>
                <table>
                    <tr>

                        <td>
                            <authority:authority authorizationCode="导出校友" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="exportFun();">按搜索条件导出</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="短信发送" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend();">按搜索条件发送短信</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="邮件发送" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="emailSend();">按搜索条件发送邮件</a>
                            </authority:authority>
                        </td>
                        <td>
                            <span id="exportResult"></span>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>--%>
    </table>
</div>
<div  data-options="region:'center',fit:false,border:false" width="82%">
    <table id="membersGrid"></table>
</div>
</body>
</html>