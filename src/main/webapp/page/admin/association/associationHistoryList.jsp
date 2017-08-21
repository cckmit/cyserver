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
                url : '${pageContext.request.contextPath}/association/associationHistoryAction!dataGrid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'type',title:'变更类型',width:100,align:'center',
                        formatter: function(value) {
                            var type = "";
                            switch (value){
                                case '10' : type = "社名变更";
                                    break;
                                case '20' : type = "社团合并";
                                    break;
                                case '30' : type = "社团解散";
                                    break;
                                default : type = "未知类型";
                            }
                            return type;
                        }
                    },
                    {field:'oldName',title:'原社团名称',width:100,align:'center'},
                    {field:'newName',title:'新社团名称',width:100,align:'center'},
                    {field:'oldTypeId',title:'原社团类型',width:100,align:'center',
                        formatter: function (value) {
                            var type = "";
                            switch (value){
                                case '1': type = "社会公益类";
                                    break;
                                case '2': type = "理论学习类";
                                    break;
                                case '3': type = "兴趣爱好类";
                                    break;
                                case '4': type = "科学教育类";
                                    break;
                                default : type = "未知类型";
                            }
                            return type;
                        }
                    },
                    {field:'newTypeId',title:'新社团类型',width:100,align:'center',
                        formatter: function (value) {
                            var type = "";
                            switch (value){
                                case '1': type = "社会公益类";
                                    break;
                                case '2': type = "理论学习类";
                                    break;
                                case '3': type = "兴趣爱好类";
                                    break;
                                case '4': type = "科学教育类";
                                    break;
                                default : type = "未知类型";
                            }
                            return type;
                        }
                    },
                    {field:'startTime',title:'开始时间',width:100,align:'center'},
                    {field:'endTime',title:'结束时间',width:100,align:'center'},
                    {field:'status',title:'变更请求状态',width:100,align:'center',
                        formatter: function(value) {
                            var status = "";
                            switch (value){
                                case '10' : status = "申请变更";
                                    break;
                                case '20' : status = "已变更";
                                    break;
                                case '30' : status = "变更未通过";
                                    break;
                                default : status = "未知类型";
                            }
                            return status;
                        }
                    },
                    {field:'operator',title:'操作',width:180,
                        formatter: function(value,row,index){
                            var content = "";
                            if(row.status == '10'){
                                <authority:authority authorizationCode="修改社团变动" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkChanges(\''+row.id+'\', 20)"><img class="iconImg ext-icon-note"/>允许修改</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="修改社团变动" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="checkChanges(\''+row.id+'\', 30)"><img class="iconImg ext-icon-note_delete"/>拒绝修改</a>&nbsp;';
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

        function searchHistory() {
            if ($('#searchForm').form('validate')) {
                $('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
            }
        }
        
        function resetT() {
            $('#oldName').val("");
            $('#type').combobox("setValue","");
            $('#status').combobox("setValue","");
        }
        
        function remove() {
            var rows = $("#dataGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if(r){
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].newsId);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/association/associationHistoryAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#dataGrid").datagrid('reload');
                                    $("#dataGrid").datagrid('unselectAll');
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
        }

        function checkChanges( id, status ) {
            var msg = '';
            if(status == 20){
                msg = '确定通过更改？';
            }else{
                msg = '确定拒绝更改？';
            }
            $.messager.confirm('确认', msg, function(r) {
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/association/associationHistoryAction!update.action',
                        data : {
                            'associationHistory.id' : id,
                            'associationHistory.status': status
                        },
                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                $("#dataGrid").datagrid('reload');
                                $("#dataGrid").datagrid('unselectAll');
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
                                    旧社团名
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="associationHistory.oldName" id="oldName" style="width: 150px;" />
                                </td>
                                <th>
                                    修改类型
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select name="associationHistory.type" id="type" class="easyui-combobox" value="" style="width: 150px;" data-options="editable:false">
                                        <option value=""></option>
                                        <option value="10">社名变更</option>
                                        <option value="20">社团合并</option>
                                        <option value="30">社团解散</option>
                                    </select>
                                </td>
                                <th>
                                    状态
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select name="associationHistory.status" id="status" class="easyui-combobox" value="" style="width: 150px;" data-options="editable:false">
                                        <option value=""></option>
                                        <option value="10">申请变更</option>
                                        <option value="20">已变更</option>
                                        <option value="30">变更未通过</option>
                                    </select>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchHistory();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                       onclick="resetT()">重置</a>
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
