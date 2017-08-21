<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
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
    <jsp:include page="../../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var grid;
        $(function(){
            grid=$('#actActivityGrid').datagrid({
                url : '${pageContext.request.contextPath}/actActivity/actActivityAction!dataGraid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'活动名称',width:200,align:'center'},
                    {field:'type',title:'活动类型',width:90,align:'center',
                        formatter: function(value,row,index){
                            var str;
                            if(row.type==10 || row.type=="10"){
                                str = "抽奖活动"
                            }
                            return str;
                        }
                    },
                    {field:'startTime',title:'活动开始时间',width:150,align:'center'},
                    {field:'endTime',title:'活动结束时间',width:150,align:'center'},
                    {field:'status',title:'活动状态',width:150,align:'center'},
                    {field:'organizer',title:'主办单位',width:150,align:'center'},
                    {field:'applicantCount',title:'报名人数',width:50,align:'center',
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看活动人列表" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="activityApplicant(\'' + row.id  + '\')">'+row.applicantCount+'</a>&nbsp;';
                            </authority:authority>
                            return content;
                        }
                    },
                    {field:'winningCount',title:'获奖人数',width:50,align:'center',
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看中奖人信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="activityWinningList(\'' + row.id  + '\')">'+row.winningCount+'</a>&nbsp;';
                            </authority:authority>
                            return content;
                        }
                    },
                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看活动信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewActActivity(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改活动信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editActActivity(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="查询奖品列表" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editActivityPrize(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>奖品管理</a>&nbsp;';
                            </authority:authority>
                            return content;
                        }}
                ]],
                toolbar : '#newsToolbar',
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

        function searchActActivity(){
            if ($('#searchActActivityForm').form('validate')) {
                $('#actActivityGrid').datagrid('load',serializeObject($('#searchActActivityForm')));
            }
        }


        function addActActivity() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/lottery/actActivity/addActActivity.jsp',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 编辑
         */
        function editActActivity(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/lottery/actActivity/editActActivity.jsp?id='+id,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 查看中奖人
         */
        function activityWinningList(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/lottery/actActivity/activityWinningList.jsp?id=' + id
            });
        }
        /**
         * 查看报名人
         */
        function activityApplicant(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/activityApplicant/activityApplicantList.jsp?id=' + id
            });
        }

        /**
         * 查看奖品管理
         */
        function editActivityPrize(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/activityPrize/activityPrizeList.jsp?id=' + id
            });
        }

        /**
         * 查看
         */
        function viewActActivity(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/lottery/actActivity/viewActActivity.jsp?id=' + id
            });
        }


        function removeActActivity(){

            var rows = $("#actActivityGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/actActivity/actActivityAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#actActivityGrid").datagrid('reload');
                                    $("#actActivityGrid").datagrid('unselectAll');
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

        function resetT(){
            $('#name').val('');
            $('#startTime').datetimebox("setValue",'');
            $('#endTime').datetimebox("setValue",'');
            $('#signUpStartTime').datetimebox("setValue",'');
            $('#signUpEndTime').datetimebox("setValue",'');
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchActActivityForm">
                        <table>
                            <tr>

                                <th>
                                    活动名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="actActivity.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    活动开始时间
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="startTime" name="actActivity.startTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                                </td>
                                <th>
                                    活动结束时间
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="endTime" name="actActivity.endTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchActActivity();">查询</a>&nbsp;
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
                                <authority:authority authorizationCode="新增企业信息" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addActActivity();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除企业信息" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeActActivity();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="actActivityGrid" ></table>
    </div>
</div>
</body>
</html>