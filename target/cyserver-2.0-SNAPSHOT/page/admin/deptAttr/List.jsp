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
                url : '${pageContext.request.contextPath}/deptInfo/deptInfoAction!getDeptAttrTreeByUserDept.action',
                state: closed,
                animate : true,
                onClick : function(node){
                    //alert(JSON.stringify(node));
                    showList( node.id );
                },
                onBeforeLoad : function(node, param) {

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
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGridByDept.action',
                fit : true,
                method : 'post',
                border : true,
                striped : true,
                pagination : true,
                nowrap : false,
                sortName:'userName',
                sortOrder:'asc',
                columns : [ [
                        {
                            field : 'userId',
                            checkbox : true
                        },
                        {
                            width : '100',
                            title : '姓名',
                            field : 'userName',
                            align : 'center',
                            sortable : true,
                            //lixun
                            formatter : function( value, row )
                            {
                                var str = row.userId;
                                str = "" + str;
                                str = str.substring( 0, str.search(",") == -1 ? str.length : str.search(",") );
                                return '<a href="javascript:void(0)" onclick="viewFun(\'' + str + '\');">'+value+'</a>&nbsp;';
                            }
                            //lixun
                        }, {
                            width : '50',
                            title : '性别',
                            field : 'sex',
                            align : 'center'
                        },
                        {
                            width : '100',
                            title : '电话号码',
                            field : 'telId',
                            align : 'center',
                            formatter : function( value, row )
                            {
                                var str = value;
                                if(value != null && value != '' && value.length > 4) {
                                    str = value.substring(value.length - 4) ;
                                    for(var i = 0 ; i < value.length - 4 ; i++) {
                                        str = "*" + str ;
                                    }
                                }
                                return str;
                            }
                        },
                        {
                            width : '150',
                            title : '工作地点',
                            field : 'residentialArea',
                            align : 'center'
                        },
                        {
                            width : '120',
                            title : '工作单位',
                            field : 'workUnit',
                            align : 'center'
                        },
                        {
                            width : '200',
                            title : '学习经历',
                            field : 'schoolName',
                            align : 'center',
                            formatter : function(value, row)
                            {
                                var text = '' + value;
                                if( row.departName != undefined && row.departName != "" )
                                    text = text + ' ' + row.departName;
                                if( row.className != undefined && row.className != "" )
                                    text = text + ' ' + row.className;
                                return text;
                            }
                        },
                        {
                            width : '80',
                            title : '是否注册',
                            field : 'accountNum',
                            align : 'center',
                            formatter : function(value, row){
                                if(value!=''&&value!=undefined){
                                    return "<span style='color: green;'>是</span>"
                                }else{
                                    return "<span>否</span>"
                                }
                            }
                        },
                        {
                            width : '100',
                            title : '是否已加入',
                            field : 'alumniStatus',
                            align : 'center',
                            formatter : function(value, row){
                                /*if(value!=''&&value!=undefined){
                                    return "<span style='color: green;'>已注册</span>"
                                }else{
                                    return "<span>未注册</span>"
                                }*/

                                var str = '' ;
                                var status = row.alumniStatus ;
                                var accountNum = row.accountNum ;

                                if(accountNum && accountNum != null && accountNum != '') {
                                    if (status == null || status == '') {
                                        str = "<span style='color: grey;'>未加入</span>";
                                    } else if (status == '5') {
                                        str = "<span style='color: #01b1cc;'>邀请中</span>";
                                    } else if (status == '10') {
                                        str = "<span style='color: #cc9f4c;'>申请中</span>";
                                    } else if (status == '20') {
                                        str = "<span style='color: green;'>已加入</span>";
                                    } else if (status == '30') {
                                        str = "<span style='color: red;'>审核不通过</span>";
                                    }
                                }else if(row.isOneKeyJoin == '0'){
                                    str = "<span style='color: #024f80;'>等待校友注册/认证后即可入会</span>"
                                }else{
                                    str = "<span style='color: grey;'>未加入</span>";
                                }

                                return str ;
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
                                }else{
                                    return "<span style='color: red;'>待核校友</span>"
                                }
                            }
                        },
                    {
                        field:'operator',
                        title:'操作',
                        width:180,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="邀请入会" userRoles="${sessionScope.user.userRoles}">
                                if(row.accountNum != null && $.trim(row.accountNum) != '' && (row.alumniStatus == null || row.alumniStatus == '')) {
                                    content += '<a href="javascript:void(0)" onclick="invite(\'' + row.accountNum + '\')"><img class="iconImg ext-icon-note"/>邀请入会</a>&nbsp;';
                                }
                                if(row.alumniStatus != '20' && row.isOneKeyJoin != '0'){
                                    content += '<a href="javascript:void(0)" onclick="oneKeyJoin(\'' + row.userId + '\')"><img class="iconImg ext-icon-note_edit"/>一键入会</a>&nbsp;';
                                }else{
                                    content += '<a href="javascript:void(0)" onclick="kickOutMember(\'' + row.userId + '\')"><img class="iconImg ext-icon-note_delete"/>剔除成员</a>&nbsp;';
                                }
                            </authority:authority>
                            return content;
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
//                    alert( JSON.stringify( data ) );
                }
            });
        });
        function searchUserInfo()
        {
            $('#membersGrid').datagrid('load', serializeObject($('#searchForm')));
        }

        /**
         * 邀请加入分会
         */
        function invite(accountNum)
        {
            $.messager.confirm('确认', '确定邀请加入分会吗？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/userInfo/userInfoAction!invite.action',
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

        function showList(id) {
            <%--var turl = '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGridByDept.action?userDeptId=' + id;--%>
            $("#userDeptId").val(id) ;
            $('#membersGrid').datagrid('load', serializeObject($('#searchForm')));
//            $('#membersGrid').datagrid('reload', turl);
            //alert( "333" );
        }

        // 剔除成员
        function kickOutMember(userId) {
            $.messager.confirm('确认', '确定从本会剔除该成员吗？', function(r) {
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_kickOutAlumni.action',
                        data : {
                            userId : userId
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

        // 一键入会
        function oneKeyJoin(userId) {
            $.messager.confirm('确认', '确定直接使校友入会吗？', function(r) {
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_oneKeyJoin.action',
                        data : {
                            userId : userId
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

        var viewFun = function(id)
        {
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


        /**--短信发送--**/
        function messageSend(){

            var rows = $("#membersGrid").datagrid('getChecked');
            var ids = [] ;
            if (rows.length <= 0) {
                $.messager.alert('提示', '请选择你发送的校友', 'info');
                return ;
            }

            for(var i = 0 ; i < rows.length ; i++) {
                ids.push(rows[i].userId) ;
            }

            var params = "userIds="+ids ;
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
            var rows = $("#membersGrid").datagrid('getChecked');
            var ids = [] ;
            if (rows.length <= 0) {
                $.messager.alert('提示', '请选择你发送的校友', 'info');
                return ;
            }

            for(var i = 0 ; i < rows.length ; i++) {
                ids.push(rows[i].userId) ;
            }

            var params = "userIds="+ids ;
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
                    <input name="userDeptId" id="userDeptId" value="1" type="hidden" />

                    <table>
                        <tr>
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
                                <input id="school" class="easyui-combobox" style="width: 130px;"
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
                                <input id="depart" class="easyui-combobox" style="width: 130px;"
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
                                <input id="grade" class="easyui-combobox" style="width: 130px;"
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
                                <input id="classes" class="easyui-combobox" style="width: 130px;"
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
                            <%--<input name="aluid" id="aluid" value="1" type="hidden" />--%>
                            <input name="isAlumni" value="2" style="width: 130px;" type="hidden" />
                            <th align="right">
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="userName" name="userInfo.userName" style="width: 130px;" />
                            </td>
                            <th align="right" width="30px;">专业</th>
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
                            </td>
                            <td colspan="3">
                                　　
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchUserInfo();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
                            <authority:authority authorizationCode="短信发送" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend()">发送短信</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="邮件发送" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="emailSend()">发送邮件</a>
                            </authority:authority>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div  data-options="region:'center',fit:false,border:false" width="82%">
    <table id="membersGrid"></table>
</div>
</body>
</html>