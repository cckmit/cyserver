<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<script type="text/javascript" charset="utf-8">
	var showMyInfoFun = function() {
		var dialog = parent.sy.modalDialog({
			title : '我的信息',
			url : sy.contextPath + '/securityJsp/userInfo.jsp'
		});
	};
	
	function logout(){
		$.ajax({
			method:'POST',
			url:'${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_logout.action',
			dataType:'json',
			success:function(result){
				if(result.success){
					location.href=contextPathx;
				}
			},
			beforeSend:function(){
				parent.$.messager.progress({
					text : '系统退出中....'
				});
			},
			complete:function(){
				parent.$.messager.progress('close');
			}
		});
	}
	
	function checkRealUser(){
		setTimeout(function(){
		$.ajax({
			method:'POST',
			url:'${pageContext.request.contextPath}/login/loginAction!doNotNeedSecurity_checkRealUser.action?userAccount=${sessionScope.user.userAccount}',
			dataType:'json'
		}).done(function (d){
			if(d.rs==1){
				window.location.href="${pageContext.request.contextPath}/page/alumni/loginx.jsp";
			}else if(d.rs==0){
				checkRealUser();
			}
		})},4000);
	}
	
//	$(document).ready(checkRealUser());
</script>
<img src="images/wztop.jpg">
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	<c:if test="${sessionScope.user!=null and sessionScope.user.userName!=null}">
		欢迎您，${sessionScope.user.userName}   角色:${sessionScope.user.role.roleName}
	</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeTheme('default');" title="default">default</div>
	<div onclick="changeTheme('gray');" title="gray">gray</div>
	<div onclick="changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="changeTheme('black');" title="black">black</div>
	<div onclick="changeTheme('metro');" title="metro">metro</div>
	<div onclick="changeTheme('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeTheme('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeTheme('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeTheme('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeTheme('metro-red');" title="metro-red">metro-red</div>
	<div onclick="changeTheme('ui-cupertino');" title="ui-cupertino">ui-cupertino</div>
	<div onclick="changeTheme('ui-dark-hive');" title="ui-dark-hive">ui-dark-hive</div>
	<div onclick="changeTheme('ui-pepper-grinder');" title="ui-pepper-grinder">ui-pepper-grinder</div>
	<div onclick="changeTheme('ui-sunny');" title="ui-sunny">ui-sunny</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-password'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<%--<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="">我的信息</div>
--%></div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logout()">退出系统</div>
</div>
