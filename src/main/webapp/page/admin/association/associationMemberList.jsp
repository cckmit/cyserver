<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 12/13/16
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var grid;
        $(function () {
            grid=$('#dataGrid').datagrid({
                url : '${pageContext.request.contextPath}/association/associationMemberAction!dataGrid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'姓名',width:100,align:'center'},
                    {field:'joinTime',title:'加入时间',width:100,align:'center'},
                    {field:'quitTime',title:'退出时间',width:100,align:'center'},
                    {field:'telephone',title:'联系电话',width:100,align:'center'},
                    {field:'allPath',title:'社团职位',width:100,align:'center'},
                    {field:'status',title:'审核状态',width:100,align:'center',
                        formatter: function(value) {
                            var status = "";
                            switch (value){
                                case '10' : status = "待审核";
                                    break;
                                case '15' : status = "邀请中";
                                    break;
                                case '20' : status = "正式社员";
                                    break;
                                case '25' : status = "申请退出中";
                                    break;
                                case '30' : status = "审核不通过";
                                    break;
                                case '35' : status = "拒绝邀请";
                                    break;
                                case '40' : status = "已退出";
                                    break;
                                case '50' : status = "已剔除";
                                    break;
                                case '55' : status = "重新申请加入";
                                    break;
                                default : status = "未知类型";
                            }
                            return status;
                        }
                    },
                    {field:'operator',title:'操作',width:180,
                        formatter: function(value,row,index){
                            var content = "";
                            <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewMember(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.status == '20'){
                                <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="changePosition(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>设置职务</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="kickOut(\''+row.id+'\')"><img class="iconImg ext-icon-note_delete"/>剔除</a>&nbsp;';
                                </authority:authority>
                            }
                            if(row.status == '10'||row.status == '55'){
                                <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkMember(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>加入审核</a>&nbsp;';
                                </authority:authority>
                            }
                            if(row.status == '25'){
                                <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkOutMember(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>退出审核</a>&nbsp;';
                                </authority:authority>
                            }
                            return content;
                        }
                    }
                ]],
                toolbar : '#Toolbar',
                onBeforeLoad : function(param)
                {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data)
                {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

        function searchMember() {
            if ($('#searchForm').form('validate')) {
                $('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
            }
        }

        function resetT() {
            $('#name').val("");
            $('#associationName').val("");
            $('#position').combobox("setValue", "");
            $('#status').combobox("setValue","");
        }

        function checkMember(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '审核',
                iconCls:'ext-icon-note_add',
                url : "${pageContext.request.contextPath}/page/admin/association/associationMemberInfo.jsp?id=" + id,
                buttons : [ {
                    text : '同意加入',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitCheck(dialog, grid, parent.$, "20");
                    }
                }, {
                    text : '拒绝加入',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitCheck(dialog, grid, parent.$, "30");
                    }
                }]
            });
        }

        function checkOutMember(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '审核',
                iconCls:'ext-icon-note_add',
                url : "${pageContext.request.contextPath}/page/admin/association/associationMemberInfo.jsp?id=" + id,
                buttons : [ {
                    text : '同意退出',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitCheck(dialog, grid, parent.$, "40");
                    }
                }, {
                    text : '拒绝退出',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitCheck(dialog, grid, parent.$, "20");
                    }
                }]
            });
        }

        function viewMember(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看社员',
                iconCls:'ext-icon-note_add',
                url : "${pageContext.request.contextPath}/page/admin/association/associationMemberInfo.jsp?setPos=0&id=" + id,
            });
        }

        function changePosition(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '设置职位',
                iconCls:'ext-icon-note_add',
                url : "${pageContext.request.contextPath}/page/admin/association/associationMemberInfo.jsp?setPos=1&id=" + id,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.subPosition(dialog, grid, parent.$);
                    }
                }]
            });
        }

        var kickOut = function (id) {
            $.messager.confirm('确认', '确定剔除该成员吗？', function(r) {
                if(r){
                    var json = {"associationMember.id":id,
                        "associationMember.status": "50"
                    };
                    $.ajax({
                        url : "${pageContext.request.contextPath}/association/associationMemberAction!update.action",
                        data : json,
                        dataType : 'json',
                        success : function(result)
                        {
                            if (result.success){
                                grid.datagrid('reload');
                                grid.datagrid('unselectAll');
                                $.messager.alert('提示', '操作成功', 'info');
                            }else{
                                $.messager.alert('提示', '操作失败', 'error');
                            }
                        },
                        beforeSend : function()
                        {
                            $.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function()
                        {
                            $.messager.progress('close');
                        }
                    });
                }
            });
        }

        /**--短信发送--**/
        function messageSend(){

            var rows = $("#dataGrid").datagrid('getChecked');
            var ids = [] ;
            if (rows.length <= 0) {
                $.messager.alert('提示', '请选择你发送的社员', 'info');
                return ;
            }

            for(var i = 0 ; i < rows.length ; i++) {
                if(rows[i].status != '20' && rows[i].status != '25' ){
                    $.messager.alert('提示', '请选择正式社员', 'info');
                    return ;
                }
                ids.push(rows[i].id) ;
            }

            var params = "ids="+ids ;
            var url = "<%=path %>/page/admin/association/sendForAssociation.jsp?"+params;
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
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="Toolbar">
    <table>
        <tr>
            <td>
                <form id="searchForm">
                    <table>
                        <tr>
                            <th>
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="associationMember.name" id="name" style="width: 150px;" />
                            </td>
                            <th>
                                社团名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="associationMember.associationId" id="associationName" style="width: 150px;" />
                            </td>
                            <th>
                                成员类型
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input  class="easyui-combobox" name="associationMember.position" id="position" style="width: 150px;" value=""
                                        data-options="editable:false,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/association/associationMemberAction!doNotNeedSecurity_getMemberType.action'" />
                            </td>
                            <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                <th>
                                    审核状态
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select name="associationMember.status" class="easyui-combobox" id="status" style="width: 150px;" data-options="editable:false">
                                        <option value=""></option>
                                        <option value="10">待审核</option>
                                        <option value="15">邀请中</option>
                                        <option value="20">正式社员</option>
                                        <option value="25">申请退出中</option>
                                        <option value="30">审核不通过</option>
                                        <option value="35">拒绝邀请</option>
                                        <option value="40">已退出</option>
                                        <option value="50">已剔除</option>
                                        <option value="55">重新申请加入</option>
                                    </select>
                                </td>
                            </authority:authority>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchMember();">查询</a>&nbsp;
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                   onclick="resetT()">重置</a>
                            </td>
                            <td>
                                <authority:authority authorizationCode="修改社团成员" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-export_customer',plain:true" onclick="messageSend();">发送短信</a>
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
    <table id="dataGrid" ></table>
</div>
</body>
</html>
