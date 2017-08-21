<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String messageType = request.getParameter("messageType");
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
        $(function(){
            $('#messageBoardGrid').datagrid({
                url:'${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!dataGrid.action?messageBoard.messageType=404&checkPage=1',
                fit : true,
                pagination:true,
                border:false,
                nowrap : true,
                rownumbers:true,
                singleSelect:true,
                idField : 'messageId',
                columns:[[
                    {field:'messageTitle',title:'信息标题',width:150,align:'center'},
                    {field:'messageTime',title:'信息时间',width:130,align:'center'},
                    {field:'messageUserName',title:'信息发送者姓名',width:100,align:'center'},
                    {field:'messageBrowseQuantity',title:'信息浏览量',width:100,align:'center'},
                    {field:'checkStatus',title:'审核状态',width:150,align:'center',
                        formatter: function(value,row,index){
                            var content="";
                            if(value==0){
                                content="未审核";
                            }
                            if(value==1){
                                content="已通过";
                            }
                            if(value==2){
                                content="未通过";
                            }
                            return content;
                        }
                    },
                    {
                        field: 'operator', title: '操作', width: 120,
                        formatter: function(value,row,index){
                            var content="";

                            if(row.checkStatus==0){
                                content+="<a href='javascript:void(0)' onclick='checkMessageBoard("+row.messageId+",1)'><img class='iconImg ext-icon-note_edit'/>通过</a>&nbsp;";
                                content+="<a href='javascript:void(0)' onclick='checkMessageBoard("+row.messageId+",2)'><img class='iconImg ext-icon-note_edit'/>不通过</a>&nbsp;";
                            }

                            return content;
                        }}
                ]],
                onBeforeLoad : function(param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data) {
                    parent.$.messager.progress('close');
                }
            });
        });

        function checkMessageBoard(id,status){
            if(status==1){
                //点击通过后弹出提示框提示是否确认审核通过
                $.messager.confirm('提示', '确认审核通过吗？', function (r) {
                    if(r){
                        $.ajax({
                            url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
                            data : {id:id,checkStatus:status},
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#messageBoardGrid").datagrid('reload');
                                    $("#messageBoardGrid").datagrid('unselectAll');
                                    window.parent.refreshMsgNum();
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
                        })
                    }
                });
            }else{
                //点击不通过后弹出提示框提示是否确认不通过审核
                $.messager.confirm('提示', '确认审核不通过吗？', function (r) {
                    if(r){
                        var checkMessageBoard=$('<div/>').dialog({
                            title : '消息审核',
                            iconCls : 'icon-add',
                            href : '${pageContext.request.contextPath}/page/admin/messageBoard/checkMessageBoard.jsp?id='+id+'&status='+status,
                            width : 700,
                            height : 400,
                            modal: true ,
                            buttons : [ {
                                text : '保存',
                                iconCls:'icon-save',
                                handler : function() {
                                    $('#checkMessageBoardForm').form('submit', {
                                        url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
                                        onSubmit: function(){
                                            return $("#checkMessageBoardForm").form('validate');
                                            $.messager.progress({
                                                text : '数据提交中....'
                                            });
                                        },
                                        success : function(data) {
                                            $.messager.progress('close');
                                            var json = $.parseJSON(data);
                                            if(json.success){
                                                checkMessageBoard.dialog('close');
                                                $("#messageBoardGrid").datagrid('reload');
                                                window.parent.refreshMsgNum();
                                                $.messager.alert('提示',json.msg,'info');
                                            }else{
                                                $.messager.alert('错误', json.msg, 'error');
                                            }
                                        }
                                    });
                                }
                            } ],onClose:function(){
                                $(this).dialog('destroy');
                            }
                        });
                    }
                });

            }
        }
        function viewMessageBoard(id){
            var viewMessageBoard=$('<div/>').dialog({
                title : '查看消息审核',
                iconCls : 'icon-edit',
                href : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!getById.action?id='+id,
                width : 700,
                height : 400,
                modal: true ,
                onClose:function(){
                    $(this).dialog('destroy');
                }
            });
        }

        function replyMessageBoard(id){
            var replyMessageBoard=$('<div/>').dialog({
                title : '回复消息',
                iconCls : 'icon-edit',
                href : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!initReplyMessageById.action?id='+id,
                width : 700,
                height : 400,
                modal: true ,

                buttons : [ {
                    text : '保存',
                    iconCls:'icon-save',
                    handler : function() {
                        $('#replyMessageBoardForm').form('submit', {
                            url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
                            onSubmit: function(){
                                return $("#replyMessageBoard").form('validate');
                                $.messager.progress({
                                    text : '数据提交中....'
                                });
                            },
                            success : function(data) {
                                $.messager.progress('close');
                                var json = $.parseJSON(data);
                                if(json.success){
                                    replyMessageBoard.dialog('close');
                                    $("#messageBoardGrid").datagrid('reload');
                                    window.parent.refreshMsgNum();
                                    $.messager.alert('提示',json.msg,'info');
                                }else{
                                    $.messager.alert('错误', json.msg, 'error');
                                }
                            }
                        });
                    }
                } ],

                onClose:function(){
                    $(this).dialog('destroy');
                }
            });
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false">
    <table id="messageBoardGrid"></table>
</div>
</body>
</html>
