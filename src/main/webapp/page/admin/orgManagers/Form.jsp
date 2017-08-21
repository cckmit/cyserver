<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        $(function() {
            if ($('#userId').val() > 0) {
                $.ajax({
                    url : '${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_getUserByUserId.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.userId != undefined) {
                            $('#userAccount').text(result.userAccount);
                            $('form').form('load', {
                                'user.userName' : result.userName,
                                'user.telephone':result.telephone,
                                'user.email':result.email,
                                'user.flag':result.flag,
                                'user.deptId':result.deptId
                            });
                        }},
                    complete:function(){
                        parent.$.messager.progress('close');
                    },
                    onBeforeLoad:function(node, param) {
                        parent.$.messager.progress({text: '数据加载中....'});
                    }
                });
            }
        });

        var submitForm = function($dialog, $grid, $pjq) {
            if($('#roleId').val()==''){
                $pjq.messager.alert('提示', '请设置用户角色', 'info');
                return false;
            }
            if($('#deptId').combobox('getValue')==''){
                $pjq.messager.alert('提示', '请设置管理校友会', 'info');
                return false;
            }
            var nodes = $('#tree').tree('getChecked', [ 'checked', 'checked' ]);
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            }

            $('#ids').prop('value',ids);
            if ($('form').form('validate')) {
                var url;
                if ($('#userId').val() > 0) {
                    url = '${pageContext.request.contextPath}/user/userAction!update.action?isAlumni=1';
                } else {
                    url = '${pageContext.request.contextPath}/user/userAction!save.action?isAlumni=1';
                }
                $.ajax({
                    url : url,
                    data :$('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            $dialog.dialog('destroy');
                            $grid.datagrid('reload');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend:function(){
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete:function(){
                        parent.$.messager.progress('close');

                    }
                });
            }
        };

        var resetPassWord = function () {
            $.messager.confirm('确认', '密码将重置为"111111"，确认此操作？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_resetPassword.action',
                        data :{'user.userId' : '${param.id}'},
                        dataType : 'json',
                        success : function(result) {
                            if (result.success) {
                                parent.$.messager.alert('提示', result.msg, 'info');
                            } else {
                                parent.$.messager.alert('提示', result.msg, 'error');
                            }
                        },
                        beforeSend:function(){
                            parent.$.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete:function(){
                            parent.$.messager.progress('close');

                        }
                    });
                }
            });

        }
    </script>
</head>

<body>
<form method="post" id="userForm">
    <input name="ids" id="ids" type="hidden"/>
    <input name="user.userId" type="hidden" id="userId" value="${param.id}">
    <input type="hidden" id="flag" name="user.flag" value="0" />
    <input type="text" style="border:none; outline:medium; position: absolute; top: -100000px"/>
    <input type="password" style="border:none; outline:medium; position: absolute; top: -100000px"/>
    <fieldset>
        <legend>
            用户基本信息
        </legend>
        <table class="ta001">
            <c:if test="${param.id==0}">
                <tr>
                    <th>
                        用户帐号
                    </th>
                    <td>
                        <input name="user.userAccount" class="easyui-validatebox"
                               data-options="required:true" />
                    </td>
                </tr>
                <tr>
                    <th>
                        用户密码
                    </th>
                    <td>
                        <input name="user.userPassword" class="easyui-validatebox" data-options="required:true,validType:'passWord[6]'" type="password">
                    </td>
                </tr>
            </c:if>
            <c:if test="${param.id!=0}">
                <tr>
                    <th>
                        用户帐号
                    </th>
                    <td style="position: relative">
                        <span id="userAccount"></span>
                        <span style="position: absolute; right: 0; top:3px"><a href="javascript:void(0);" class="easyui-linkbutton"
                                 data-options="iconCls:'ext-icon-huifu',plain:true"
                                 onclick="resetPassWord();">重置密码</a>(密码重置后为"111111")</span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <th>
                    用户姓名
                </th>
                <td>
                    <input name="user.userName" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'" />
                </td>
            </tr>
            <tr>
                <th>
                    电话号码
                </th>
                <td>
                    <input name="user.telephone" class="easyui-validatebox"
                           data-options="validType:'telePhone'" />
                </td>
            </tr>
            <tr>
                <th>
                    电子邮箱
                </th>
                <td>
                    <input name="user.email" class="easyui-validatebox"
                           data-options="validType:'email'" />
                </td>
            </tr>

            <tr id="depttr1">
                <th>
                    管理校友会
                </th>
                <td >
                    <input id="mainType" name="mainType" value="0" type="hidden"/>
                    <select id="deptId" name="user.deptId" class="easyui-combotree"
                            data-options="
                                editable:false,
                                idField:'id',
                                state:'open',
                                textField:'text',
                                parentField:'pid',
                                url:'${pageContext.request.contextPath}/alumni/alumniAction!getAlumniTreeDrop.action',
                                onLoadSuccess : function(node, data)  //lixun 2016-7-28
                                {
                                    //alert( JSON.stringify( data ) );
                                    var nodeId = ${param.nodeId};
                                    if( nodeId == null || nodeId == undefined || nodeId == 0 ) return;
                                    node = $('#deptId' ).combotree( 'tree' ).tree( 'find', nodeId );
                                    if( node != null && node != undefined )
                                    $('#deptId' ).combotree( 'setValue', nodeId );
                                },
                                onChange : function( id )
                                {
                                    node = $('#deptId' ).combotree( 'tree' ).tree( 'find', id );

                                    $('#mainType' ).val( node.attributes.mainType );
                                    //alert( $('#mainType' ).val() );
                                }
                                " style="width: 200px;">
                    </select>
                </td>
            </tr>
            </table>
        </fieldset>
    </form>
    </body>
    </html>
