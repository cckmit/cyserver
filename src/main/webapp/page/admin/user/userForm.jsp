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
	var hasCheck=0;
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
						//'user.flag':result.flag,
						'user.deptId':result.deptId
					});
				}
			},
			complete:function(){
				parent.$.messager.progress('close');
			}
		});
	});
	/*$(function() {
		if ($('#userId').val() > 0) {
			$('#tree').tree({
				url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptTreeForUser.action',
				parentField : 'pid',
				checkbox : true,
				cascadeCheck:false,
				onCheck:function(node,checked){
					if(node.id.length==10){
						//获取所有根节点
						if(checked){
							hasCheck=hasCheck+1;
						}else{
							hasCheck=hasCheck-1;
						}
						var roots = $('#tree').tree('getRoots');
						for(var i=0;i<roots.length;i++){
							if(roots[i].id.length==6){
								$('#tree').tree('disableCheck',roots[i].id);//禁用   
								if(hasCheck==0){
									$('#tree').tree('enableCheck',roots[i].id);//启用   
								}
							}
						}
					}else{
						if(checked){
							hasCheck=hasCheck+1;
						}else{
							hasCheck=hasCheck-1;
						}
						var roots = $('#tree').tree('getRoots');
						for(var j=0;j<roots.length;j++){
							var isLeaf = $('#tree').tree('isLeaf', roots[j].target);
							if(!isLeaf){
								var children = $('#tree').tree('getChildren', roots[j].target);
								for(var i=0;i<children.length;i++){
									$('#tree').tree('disableCheck',children[i].id);//禁用   
									if(hasCheck==0){
										$('#tree').tree('enableCheck',children[i].id);//启用
									}
								}
							}
						}
					}
					
				},
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
									'user.deptId':result.deptId
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
					$("#flag").combobox("disable"); 
				},onBeforeLoad:function(node, param){
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				}
			});
		}else{
			$('#tree').tree({
				url : '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDeptTreeForUser.action',
				parentField : 'pid',
				checkbox : true,
				cascadeCheck:false,
				onCheck:function(node,checked){
					if(node.id.length==10){
						//获取所有根节点
						if(checked){
							hasCheck=hasCheck+1;
						}else{
							hasCheck=hasCheck-1;
						}
						var roots = $('#tree').tree('getRoots');
						for(var i=0;i<roots.length;i++){
							if(roots[i].id.length==6){
								$('#tree').tree('disableCheck',roots[i].id);//禁用   
								if(hasCheck==0){
									$('#tree').tree('enableCheck',roots[i].id);//启用   
								}
							}
						}
					}else{
						if(checked){
							hasCheck=hasCheck+1;
						}else{
							hasCheck=hasCheck-1;
						}
						var roots = $('#tree').tree('getRoots');
						for(var j=0;j<roots.length;j++){
							var isLeaf = $('#tree').tree('isLeaf', roots[j].target);
							if(!isLeaf){
								var children = $('#tree').tree('getChildren', roots[j].target);
								for(var i=0;i<children.length;i++){
									$('#tree').tree('disableCheck',children[i].id);//禁用   
									if(hasCheck==0){
										$('#tree').tree('enableCheck',children[i].id);//启用   
									}
								}
							}
						}
					}
					
				},
				onLoadSuccess : function(node, data) {
					parent.$.messager.progress('close');
				},onBeforeLoad:function(node, param){
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				}
			});
		}
	});*/
		var submitForm = function($dialog, $grid, $pjq) {
			/*if($('#roleId').val()==''){
				$pjq.messager.alert('提示', '请设置用户角色', 'info');
				return false;
			}*/
			/*if($('#flag').combobox('getValue')==1){
				if($('#deptId').combobox('getValue')==''){
					$pjq.messager.alert('提示', '请设置管理校友会', 'info');
					return false;
				}
			}else{
				var nodes = $('#tree').tree('getChecked', [ 'checked', 'checked' ]);
				var ids = [];
				for (var i = 0; i < nodes.length; i++) {
					ids.push(nodes[i].id);
				}
				if(ids.length==0){
					$pjq.messager.alert('提示', '请设置管理院系', 'info');
					return false;
				}
				$('#ids').prop('value',ids);
			}*/
			if ($('form').form('validate')) {
				var url;
				if ($('#userId').val() > 0) {
					url = '${pageContext.request.contextPath}/user/userAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/user/userAction!save.action';
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
	</script>
  </head>
  
  <body>
     <form method="post" id="userForm">
		<input name="ids" id="ids" type="hidden"/>
		<input name="user.userId" type="hidden" id="userId" value="${param.id}">
		 <!--不要理会下面这两行，这只是为了屏蔽Chrome自动填充的无奈之举 16-08-11 QPF-->
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
					</c:if>
					<%--<c:if test="${param.id!=0}">
						<tr>
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
						</tr>
					</c:if>--%>
					<tr>
						<th>
							用户姓名
						</th>
						<td>
							<input name="user.userName" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'" />
							<input name="user.flag" value="0" type="hidden" />
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
					<tr id="roletr">
						<th>
							角色
						</th>
						<td >
							<input id="role" class="easyui-combobox" style="width: 200px;height: 50px;" name="user.roleIds"
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
									                $('#role').prop('value','');
									                }
									            }],
												url: '${pageContext.request.contextPath}/role/roleAction!doNotNeedSecurity_getNoAdmin.action',  
												onLoadSuccess : function(data) {
													$.post('${pageContext.request.contextPath}/user/userAction!doNotNeedSecurity_getUserRoleIdsByUserId.action?user.userId=${param.id}',
														function(d){
														if(d.length>0){
															d = d.substring(1,d.length-1);
															$('#role').combobox('setValues',eval('['+d+']'));
														}
													});
												},
												onSelect: function(rec){
												$('#role').prop('value',$('#role').combobox('getValues'));
										}" />
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
