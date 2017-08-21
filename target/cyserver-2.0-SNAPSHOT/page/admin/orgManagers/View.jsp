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
        /*$(function() {
            if ($('#userId').val() > 0) {
                $('#tree').tree({
                    url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptTreeForUser.action',
                    parentField : 'pid',
                    checkbox : true,
                    cascadeCheck:false,
                    onLoadSuccess : function(node, data) {
                        $.ajax({
                            url : '${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_getUserByUserId.action',
                            data : $('form').serialize(),
                            dataType : 'json',
                            success : function(result) {
                                if (result.userId != undefined) {
                                    $('form').form('load', {
                                        'user.userName' : result.userName,
                                        'user.userAccount' : result.userAccount,
                                        'user.telephone':result.telephone,
                                        'user.email':result.email,
                                        'user.flag':result.flag,
                                        'user.deptId':result.deptId,
                                        'user.roleId':result.roleId
                                    });
                                    for (var i = 0; i < result.depts.length; i++) {
                                        var node = $('#tree').tree('find', result.depts[i].deptId);
                                        if (node) {
                                            $('#tree').tree('check', node.target);
                                        }
                                    }
                                }
                            },
                            complete:function(){
                                //alert(JSON.stringify(data));
                                parent.$.messager.progress('close');
                            }
                        });
                        $(this).find('span.tree-checkbox').unbind().click(function(){
                            return false;
                        });
                        $("#flag").combobox("disable");
                        $("#deptId").combobox("disable");
                        $("#role").combobox("disable");
                        $("#xrole").combobox("disable");
                    },onBeforeLoad:function(node, param){
                        parent.$.messager.progress({
                            text : '数据加载中....'
                        });
                    }
                });
            }
        });*/
        $(function () {
            $.ajax({
                url : '${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_getUserByUserId.action',
                data : $('form').serialize(),
                dataType : 'json',
                success : function(result) {
                    if (result.userId != undefined) {
                        $('form').form('load', {
                            'user.userName' : result.userName,
                            'user.userAccount' : result.userAccount,
                            'user.telephone':result.telephone,
                            'user.email':result.email,
                            'user.flag':result.flag,
                            'user.deptId':result.deptId,
                            'user.roleId':result.roleId
                        });
                        for (var i = 0; i < result.depts.length; i++) {
                            var node = $('#tree').tree('find', result.depts[i].deptId);
                            if (node) {
                                $('#tree').tree('check', node.target);
                            }
                        }
                    }
                },
                complete:function(){
                    //alert(JSON.stringify(data));
                    parent.$.messager.progress('close');
                }
            });
        });
    </script>
</head>

<body>
<form method="post" id="userForm">
    <input name="user.userId" type="hidden" id="userId" value="${param.id}">
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
                        <input name="user.userAccount" class="easyui-validatebox" disabled="disabled"
                               data-options="required:true,validType:'userAccount'" />
                    </td>
                    <th>
                        用户密码
                    </th>
                    <td>
                        <input name="user.userPassword" disabled="disabled" class="easyui-validatebox" data-options="required:true,validType:'passWord[6]'" type="password">
                    </td>
                </tr>
            </c:if>
            <tr>
                <th>
                    用户姓名
                </th>
                <td>
                    <input name="user.roleId" id="roleId" type="hidden">
                    <input name="user.userName" class="easyui-validatebox" disabled="disabled"
                           data-options="required:true,validType:'customRequired'" />
                </td>
            </tr>
            <tr>
                <th>
                    电话号码
                </th>
                <td>
                    <input name="user.telephone" class="easyui-validatebox" disabled="disabled"
                           data-options="validType:'telePhone'" />
                </td>
            </tr>
            <tr>
                <th>
                    电子邮箱
                </th>
                <td colspan="3">
                    <input name="user.email" class="easyui-validatebox" disabled="disabled"
                           data-options="validType:'email'" />
                </td>
            </tr>
            <tr id="roletr">
                <th>
                    角色
                </th>
                <td >
                    <input id="role" class="easyui-combobox" style="width: 200px;height: 50px;" name="role"
                           data-options="
												valueField: 'roleId',
												textField: 'roleName',
												editable:false,
												multiple:true,
												multiline:true,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#role').combobox('clear');
									                $('#roleId').prop('value','');
									                }
									            }],
												url: '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getNoAdmin.action',
												onLoadSuccess : function(data) {
													$.ajax({
													url : '${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_getUserRoleIdsByUserId.action?user.userId=${param.id}',
													dataType : 'json',
													success : function(result) {
														$('#role').combobox('setValues',eval('['+result+']'));
													}});
												},
												onSelect: function(rec){
												$('#roleId').prop('value',rec.roleId);
										}" disabled />
                </td>
            </tr>
            <tr id="depttr1">
                <th>
                    管理校友会
                </th>
                <td >
                   <%-- <input class="easyui-combobox" name="user.deptId" id="deptId" style="width: 150px;"
                           data-options="
					                    url:'${pageContext.request.contextPath}/alumni/alumniAction!doNotNeedSecurity_getAlumni2ComboBox.action',
					                    method:'post',
					                    valueField:'alumniId',
					                    textField:'alumniName',
					                    editable:false,
					                    prompt:'--请选择--',
					                    icons:[{
							                iconCls:'icon-clear',
							                handler: function(e){
							                	$('#deptId').combobox('clear');
							                }
							            }]
				                    	" disabled />--%>
                       <select id="deptId" name="user.deptId" class="easyui-combotree" disabled="disabled"
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
            <%--<tr id="depttr">
                <th>
                    所属院系
                </th>
                <td >
                    <ul id="tree"></ul>
                </td>
            </tr>--%>
        </table>
    </fieldset>
</form>
</body>
</html>
