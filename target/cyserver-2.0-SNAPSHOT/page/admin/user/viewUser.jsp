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
								if(result.flag==0){
									/!* $('form').form('load', {
										'role':result.roleId
									}); *!/
									$('#roletr').show();
									$('#depttr').show();
									$('#roletr1').hide();
									$('#depttr1').hide();
								}else{
									$('form').form('load', {
										'xrole':result.roleId
									});
									$('#roletr').hide();
									$('#depttr').hide();
									$('#roletr1').show();
									$('#depttr1').show();
								}
								for (var i = 0; i < result.depts.length; i++) {
									var node = $('#tree').tree('find', result.depts[i].deptId);
									if (node) {
										$('#tree').tree('check', node.target);
									}
								}
							}
						},
						complete:function(){
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
		$(function(){
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
					}
				},
				complete:function(){
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
						<%--<tr>
							<th>
								系统
							</th>
							<td>
								<select class="easyui-combobox" data-options="editable:false,onSelect:function(record){
									if($('#flag').combobox('getValue')==1){
										$('#roletr').hide();
										$('#depttr').hide();
										$('#roletr1').show();
										$('#depttr1').show();
										$('#roleId').prop('value','');
										$('#role').combobox('clear');
									}else{
										$('#roletr').show();
										$('#depttr').show();
										$('#roletr1').hide();
										$('#depttr1').hide();
										$('#roleId').prop('value','');
										$('#xrole').combobox('clear');
										$('#deptId').combobox('clear');
									}
								}" id="flag" name="user.flag" style="width: 150px;">
									<option value="0">WEB后台系统</option>
									<option value="1">校友会系统</option>
								</select>
							</td>
						</tr>--%>
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
										}" disabled="disabled"/>
						</td>
					</tr>
					<%--<tr id="depttr">
						<th>
							管理院系
						</th>
						<td >
							<ul id="tree"></ul>
						</td>
					</tr>
					<tr id="roletr1" style="display: none;">
						<th>
							角色
						</th>
						<td >
							<input id="xrole" class="easyui-combobox" style="width: 150px;" name="user.alumniRoleId"
											data-options="  
												valueField: 'roleId',  
												textField: 'roleName',  
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#xrole').combobox('clear');
									                $('#roleId').prop('value','');
									                }
									            }],
												url: '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getxNoAdmin.action',  
												onLoadSuccess : function(data) {
													$('#xrole').combobox('setValue',data[0].roleId);
												},
												onSelect: function(rec){
												$('#xrole').prop('value','');
												$('#xrole').prop('value',rec.roleId);
										}" />
						</td>
					</tr>
					<tr id="depttr1" style="display: none;">
						<th>
							管理校友会
						</th>
						<td >
							<input class="easyui-combobox" name="user.deptId" id="deptId" style="width: 150px;"
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
				                    	">
						</td>
					</tr>--%>
				</table>
			</fieldset>
     </form>
  </body>
</html>
