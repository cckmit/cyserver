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
        var membersGrid;
        $(function () {
            membersGrid = $('#membersGrid').datagrid({
                url : '${pageContext.request.contextPath}/userInfo/userInfoAction!alumniMemebers.action?aluid=1&checkPage=1',
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
                        sortable : true
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
                        field : 'classes',
                        align : 'center'

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
                            }
                        }
                    },{
                        width : '150',
                        title : '操作',
                        field : 'action',
                        align : 'center',
                        formatter : function(value, row) {
                            var str = '';
                            if(row.status == '10' && row.currAlumniId==row.alumni_id){
                                <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="入会审核">
                                str += '<a href="javascript:void(0)" onclick="checkPass('+ row.userAlumniId+');"><img class="iconImg ext-icon-yes"/>同意加入</a>&nbsp;';
                                </authority:authority>
                                <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="入会审核">
                                str += '<a href="javascript:void(0)" onclick="checkFail('+ row.userAlumniId+');"><img class="iconImg ext-icon-note_delete"/>拒绝加入</a>&nbsp;';
                                </authority:authority>
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

        function resetT(){
            $('#userName').val('');
        }


    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">

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

                            <td colspan="3">
                                　　
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
<div  data-options="region:'center',fit:false,border:false">
    <table id="membersGrid"></table>
</div>
</body>
</html>