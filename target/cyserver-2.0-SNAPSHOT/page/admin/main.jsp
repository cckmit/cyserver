<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>窗友校友智能管理与社交服务平台</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="">
		<meta http-equiv="description" content="">
		<jsp:include page="../../inc.jsp"></jsp:include>

		<style type="text/css">
			.msgSup {
				margin: 0;
				margin-left: 5px;
				padding: 2px 5px;
				display: inline-block;
				height: 12px;
				line-height: 12px;
				text-align: center;
				border-radius: 8px;
				background-color: red;
				font-size: 10px;
				color: #FFFFFF;
				font-weight: normal;
				overflow: hidden
			}
			.msgSup-small {
				margin: 0;
				margin-left: 5px;
				padding: 1px 4px;
				height: 10px;
				line-height: 10px;
				text-align: center;
				border-radius: 7px;
				background-color: red;
				font-size:10px;
				color: #FFFFFF;
				font-weight: normal;
				overflow: hidden
			}
		</style>
	</head>

	<script type="text/javascript">
		var mainMenu;
		var mainTabs;
		var menuTree;
		$(function(){
			$.ajax({
				url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSecurity_initMainMenu.action',
				dataType : 'json',
				success : function(result)
				{
					if(result=='您还没有登录或登录已超时，请重新登录！'){
						alert(result);
					}else{
						menuTree = result;
						var ul = $('<ul></ul>').addClass('main-header-nav');
						for(var obj in result){
							var iconCls = result[obj].iconCls;
							var menuName = result[obj].text;
							var menuId = result[obj].id;

							var li = $('<li></li>');
//							if(obj==0){
//								li.addClass("nav-current");
//							}
							var i = $('<i><i>').addClass('nav-icon').addClass(iconCls);
							var span = $('<span></span>').html(menuName).prop("id", "menu" + menuId);

							ul.append(li.append(i).append(span));
						}
						$('.main-top').append(ul);

						$('.main-top .main-header-nav li').each(function(index){

							$(this).click(function(){
								$('.main-top .main-header-nav li').removeClass('nav-current');
								$(this).addClass('nav-current');

								// 初始化左侧导航
								$('#mainMenu').tree('loadData',menuTree[index].children);

								// 刷新代办计数
								refreshMsgNum();

								// 默认打开第一个子栏目菜单
								if(menuTree[index].children.length>0){
									if(menuTree[index].children[0].attributes.url==""){
										if(menuTree[index].children[0].children.length>0){
											if(menuTree[index].children[0].children[0].attributes.url!=""){
												//console.log(menuTree[0].children[0].children[0].attributes.url);
												var node = menuTree[index].children[0].children[0];
												openMenu(node);
											}
										}
									}else{
										var node = menuTree[index].children[0];
										openMenu(node);
									}
								}
							});
						});
						mainMenu = $('#mainMenu').tree({
							data : menuTree[0].children,
							parentField : 'pid',
							onClick : function(node) {
								if (node.attributes.url) {
									var url = node.attributes.url;
									if (url!='') {
										var flag = node.text.indexOf('<span class="msgSup-small"');
										var nodeName = node.text;

										if(flag > -1){
											nodeName = node.text.substring(0,flag);
										}

										var tabs = $('#mainTabs');
										var opts = {
											title : nodeName,
											closable : true,
											iconCls : node.iconCls,
											content : '<iframe src="${pageContext.request.contextPath}'+url+'" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>',
											border : false,
											fit : true
										};
										if (tabs.tabs('exists', opts.title)) {
											tabs.tabs('select', opts.title);
										} else {
											tabs.tabs('add', opts);
										}
									}
								}
							}
						});
//						if(menuTree[0].children.length>0){
//							if(menuTree[0].children[0].attributes.url==""){
//								if(menuTree[0].children[0].children.length>0){
//									if(menuTree[0].children[0].children[0].attributes.url!=""){
//										//console.log(menuTree[0].children[0].children[0].attributes.url);
//										var node = menuTree[0].children[0].children[0];
//										openMenu(node);
//									}
//								}
//							}else{
//								var node = menuTree[0].children[0];
//								openMenu(node);
//							}
//						}

					}
				},
				beforeSend : function()
				{
					parent.$.messager.progress({
						text : '数据提交中....'
					});
				},
				complete : function()
				{
					refreshMsgNum();
					parent.$.messager.progress('close');
				}
			});

			function openMenu(node){
				var url = node.attributes.url;
				if (url!='') {
					var flag = node.text.indexOf('<span class="msgSup-small"');
					var nodeName = node.text;

					if(flag > -1){
						nodeName = node.text.substring(0,flag);
					}

					var tabs = $('#mainTabs');
					var opts = {
						title : nodeName,
						closable : true,
						iconCls : node.iconCls,
						content : '<iframe src="${pageContext.request.contextPath}'+url+'" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>',
						border : false,
						fit : true
					};
					if (tabs.tabs('exists', opts.title)) {
						tabs.tabs('select', opts.title);
					} else {
						tabs.tabs('add', opts);
					}
				}
			}

			$('#mainLayout').layout('panel', 'center').panel({
				onResize : function(width, height) {
					setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
				}
			});
			
			mainTabs = $('#mainTabs').tabs({
				fit : true,
				border : false,
				tools : [ {
					iconCls : 'ext-icon-arrow_up',
					handler : function() {
						mainTabs.tabs({
							tabPosition : 'top'
						});
					}
				}, {
					iconCls : 'ext-icon-arrow_left',
					handler : function() {
						mainTabs.tabs({
							tabPosition : 'left'
						});
					}
				}, {
					iconCls : 'ext-icon-arrow_down',
					handler : function() {
						mainTabs.tabs({
							tabPosition : 'bottom'
						});
					}
				}, {
					iconCls : 'ext-icon-arrow_right',
					handler : function() {
						mainTabs.tabs({
							tabPosition : 'right'
						});
					}
				}, {
					text : '刷新',
					iconCls : 'ext-icon-arrow_refresh',
					handler : function() {
						var panel = mainTabs.tabs('getSelected').panel('panel');
						var frame = panel.find('iframe');
						try {
							if (frame.length > 0) {
								for (var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
						}
					}
				}, {
					text : '关闭',
					iconCls : 'ext-icon-cross',
					handler : function() {
						var index = mainTabs.tabs('getTabIndex', mainTabs.tabs('getSelected'));
						var tab = mainTabs.tabs('getTab', index);
						if (tab.panel('options').closable) {
							mainTabs.tabs('close', index);
						} else {
							$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
						}
					}
				} ]
			});
			
			$('#passwordDialog').show().dialog({
				modal : true,
				closable : true,
				iconCls : 'ext-icon-password',
				buttons : [ {
					text : '修改',
					iconCls : 'ext-icon-note_edit',
					handler : function() {
						if ($('#passwordDialog form').form('validate')) {
							$.post('${pageContext.request.contextPath}/login/loginAction!doNotNeedSecurity_updateCurrentPwd.action', {
								'pwd' : $('#pwd').val()
							}, function(result) {
								if (result.success) {
									$.messager.alert('提示', result.msg, 'info');
									$('#passwordDialog').dialog('close');
								}else{
									$.messager.alert('提示', result.msg, 'error');
								}
							}, 'json');
						}
					}
				} ],
				onOpen : function() {
					$('#passwordDialog form :input').val('');
				}
			}).dialog('close');

		});

		/**
		 * 刷新快捷审批 待审批计数
		 *
		 * 在iframe子页面中，通过window.parent.refreshMsgNum();方式刷新待审批计数
		 */
		function refreshMsgNum(){
//			$(".msgSup").remove();
//			$(".msgSup-small").remove();

			// 异步获取计数
			$.ajax({
				url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_msgNum.action',
				dataType : 'json',
				success : function(result){

					var data = result;
					//alert(JSON.stringify(data));
					// data中是正确格式的数据对象，解析并添加计数
					if(data && data.total){
						// 初始化计数
						if($("#menu" + data.total.id).parent().hasClass("na" +
										"v-current")){
							// 分别刷新快捷审批事项的计数
							$('.main-top .main-header-nav li').each(function(index){
								if(menuTree[index].id == data.total.id){

									if(menuTree[index].children.length > 0){
										for(var i = 0; i < menuTree[index].children.length; i++){
											if(menuTree[index].children[i].attributes.url==""){
												if(menuTree[index].children[i].children.length > 0){
													for(var j = 0; j < menuTree[index].children[i].children.length; j++){
														if(menuTree[index].children[i].children[j].attributes.url != ""){
															var node = menuTree[index].children[i].children[j];

															for(var k = 0; k < data.children.length; k++){
																if(node.id == data.children[k].id){
																	var flag = node.text.indexOf('<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">');
																	if(flag > -1) {
																		node.text = node.text.substring(0, flag);
																		if(data.children[k].num > 0){
																			node.text += '<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">' + data.children[k].num + '</span>';
																		}
																	}
																	else if(data.children[k].num > 0){
																		node.text += '<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">' + data.children[k].num + '</span>';
																	}

																	break;
																}
															}
														}
													}
												}
											}else{
												var node = menuTree[index].children[i];

												for(var k = 0; k < data.children.length; k++){
													if(node.id == data.children[k].id){
														var flag = node.text.indexOf('<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">');
														if(flag > -1) {
															node.text = node.text.substring(0, flag);
															if(data.children[k].num > 0){
																node.text += '<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">' + data.children[k].num + '</span>';
															}
														}
														else if(data.children[k].num > 0){
															node.text += '<span class="msgSup-small" id="menu_span_' + data.children[k].id + '">' + data.children[k].num + '</span>';
														}

														break;
													}
												}
											}
										}

										$('#mainMenu').tree('loadData',menuTree[index].children);
									}
								}
							});
						}

						// 初始化总的计数
						if(data.total.num > 0){
							var oldTotal = $("#menu"+ data.total.id).find(".msgSup").text();
							if(oldTotal){
								if(data.total.num != parseInt(oldTotal)){
									$("#menu"+ data.total.id).find(".msgSup").text(data.total.num)
								}
							}
							else{
								$("#menu"+ data.total.id).append('<sup class="msgSup">' + data.total.num + '</sup>');
							}
						}
						else{
							$(".msgSup").remove();
						}
					}
				}
			});
		}
	</script>
	<body id="mainLayout" class="easyui-layout" style="overflow-y: hidden" scroll="no">
		<div data-options="region:'north',href:'${pageContext.request.contextPath}/page/admin/north.jsp'"  style="height: 70px; overflow: hidden;background-image: url('images/cctop.jpg');background-repeat: no-repeat;">
		</div>
		<div data-options="region:'south'" style="height: 30px;">
			<div align="center" style="margin-top: 5px;">
				Copyright © 2014 - 2017 <%=Global.schoolSign%>版权所有. All Rights Reserved [武汉窗友科技有限公司提供技术支持]

			</div>
		</div>
		<div data-options="region:'west',title:'导航菜单',split:true"
			style="width: 180px;">
			<ul id="mainMenu"></ul>
		</div>
		<div data-options="region:'center'" style="overflow-y: hidden">
			<div id="mainTabs">
				<div title="我的主页" data-options="iconCls:'ext-icon-homePage'">
					<iframe src="${pageContext.request.contextPath}/page/admin/welcome.jsp" allowTransparency="true"
						style="border: 0; width: 100%; height: 99%;background-image: url('images/tta.jpg');background-position: center;background-repeat: no-repeat;" frameBorder="0"></iframe>
				</div>
			</div>
		</div>
		<div id="passwordDialog" title="修改密码" style="display: none;">
			<form method="post" class="form" onsubmit="return false;">
				<table class="table">
					<tr>
						<th style="width:80px;">新密码</th>
						<td><input id="pwd" name="data.pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<th>重复密码</th>
						<td><input type="password" class="easyui-validatebox" data-options="required:true,validType:['eqPassword[\'#pwd\']','passWord[6]','customRequired']" /></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
