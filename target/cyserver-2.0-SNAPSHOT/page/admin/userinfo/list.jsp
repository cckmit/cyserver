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
        var start = true ;
        $(function() {
            userInfoGrid = $('#userInfoGrid').datagrid({
                <%--url : '${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action',--%>
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
                    },
                    {
                        width : '130',
                        title : '是否已加入',
                        field : 'alumniStatus',
                        align : 'center',
                        formatter : function(value, row){

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
                            <authority:authority authorizationCode="邀请入会" userRoles="${sessionScope.user.userRoles}">
                            if(row.accountNum != null && $.trim(row.accountNum) != '' && (row.alumniStatus == null || row.alumniStatus == '')) {
                                str += '<a href="javascript:void(0)" onclick="invite(\'' + row.accountNum + '\')"><img class="iconImg ext-icon-note"/>邀请入会</a>&nbsp;';
                            }
                            if(row.alumniStatus != '20' && row.isOneKeyJoin != '0'){
                                str += '<a href="javascript:void(0)" onclick="oneKeyJoin(\'' + row.userId + '\')"><img class="iconImg ext-icon-note_edit"/>一键入会</a>&nbsp;';
                            }else{
                                str += '<a href="javascript:void(0)" onclick="kickOutMember(\'' + row.userId + '\')"><img class="iconImg ext-icon-note_delete"/>剔除成员</a>&nbsp;';
                            }
                            </authority:authority>
                            return str;
                        }
                    } ] ],
                toolbar : '#toolbar',
                onBeforeLoad : function(param)//在请求载入数据之前触发，如果返回false将取消载入
                {
                    if(!start) {
                        parent.parent.$.messager.progress({
                            text : '数据加载中....'
                        });
                    }
                },
                onLoadSuccess : function(data)//当数据载入成功时触发
                {
                    if(!start) {
                        $('.iconImg').attr('src', pixel_0);
                        parent.parent.$.messager.progress('close');
                    }
                }
            });
        });


        function searchUserInfo(){
            start = false ;
            if ($('#searchForm').form('validate'))
            {
                $('#userInfoGrid').datagrid({url:'${pageContext.request.contextPath}/userInfo/userInfoAction!dataGrid.action?checkFlag=1&searchPage=1','queryParams': serializeObject($('#searchForm'))});
            }
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
                                userInfoGrid.datagrid('reload');
                                userInfoGrid.datagrid('unselectAll');
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
                                userInfoGrid.datagrid('reload');
                                userInfoGrid.datagrid('unselectAll');
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
                                userInfoGrid.datagrid('reload');
                                userInfoGrid.datagrid('unselectAll');
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
                            <th align="right" style="padding-left: 0" width="64px;">
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator" ></div>
                            </td>
                            <td colspan="10">
                                <input id="userName" name="userNames" class="easyui-validatebox" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
                            </td>
                        </tr>
                        <tr>
                            <th align="right" style="padding-left: 0" width="64px;">
                                手机号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="10">
                                <input name="telIds" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
                            </td>
                        </tr>
                        <tr>
                            <th align="right" style="padding-left: 0" width="64px;">
                                学号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="10">
                                <input name="studentNumbers" placeholder="查询多个以英文半角逗号(,)分隔" style="width: 800px;" />
                            </td>
                        </tr>
                        <tr>
                            <th align="right" style="padding-left: 0" width="64px;">学校</th>
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

                            <th align="right" style="padding-left: 0" width="64px;">院系</th>
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
                            <th align="right" style="padding-left: 0" width="64px;">年级</th>
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
                            <th align="right" style="padding-left: 0" width="64px;">班级</th>
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
                            <th align="right" style="padding-left: 0" width="64px;">专业</th>
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
                            <th align="right" style="padding-left: 0" width="64px;">学历</th>
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
                            <th align="right" style="padding-left: 0" width="64px;">
                                工作单位
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="userInfo.workUnit" style="width: 150px;" />
                            </td>
                            <th align="right" style="padding-left: 0" width="64px;">
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
                                <th align="right" style="padding-left: 10px" width="60px;">
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
                                <td colspan="4" align="right">
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
