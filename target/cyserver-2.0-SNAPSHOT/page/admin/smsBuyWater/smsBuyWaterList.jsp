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
		$(function(){
			grid=$('#smsBuyWaterGrid').datagrid({
				url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!dataGraidSmsBuyWater.action',
				fit : true,
				border : false,
				fitColumns : true,
				striped : true,
				rownumbers : true,
				pagination : true,
				idField : 'id',
				columns:[[
					{field:'id',checkbox : true},
					{field:'account',title:'应用账号',width:150,align:'center'},
					{field:'orderNo',title:'订单号',width:100,align:'center'},
					{field:'buyTime',title:'购买时间',width:100,align:'center'},
					{field:'buyNum',title:'购买条数',width:100,align:'center'},

					{
						width: '100',
						title: '购买金额',
						field: 'buyPrice',
						align: 'center'
					},
					{
						width: '100',
						title: '剩余条数',
						field: 'surplusNum',
						align: 'center'
					},
					{field:'surplusPrice',title:'剩余金额',width:100,align:'left'

					},
					/*{field:'cityName',title:'所属城市',width:80,align:'center'},*/
					{field:'startTime',title:'开始使用时间',width:100,align:'center'
					},
					{field:'endTime',title:'短信用完时间',width:100,align:'center'

					},
					{field:'operator',title:'操作',width:180,
						formatter: function(value,row,index){
							var content="";
							<authority:authority authorizationCode="查看流水" userRoles="${sessionScope.user.userRoles}">
							content+='<a href="javascript:void(0)" onclick="viewSmsBuyWater(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							<%--<authority:authority authorizationCode="编辑新闻" userRoles="${sessionScope.user.userRoles}">
							if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
								content+='<a href="javascript:void(0)" onclick="convertNews('+row.newsId+')"><img class="iconImg ext-icon-note_edit"/>转总会</a>&nbsp;';
							}else{
								content+='<a href="javascript:void(0)" onclick="editNews(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							}
							</authority:authority>--%>

								<authority:authority authorizationCode="编辑流水" userRoles="${sessionScope.user.userRoles}">
								content+='<a href="javascript:void(0)" onclick="editSmsBuyWater(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
								</authority:authority>
								<authority:authority authorizationCode="查看流水" userRoles="${sessionScope.user.userRoles}">
									if(row.payStatus == '0'){
										content+='<a href="javascript:void(0)" onclick="payPage(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>前往支付</a>&nbsp;';
									}
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

		function searchSmsBuyWater(){
			if ($('#searchNewsForm').form('validate')) {
				$('#smsBuyWaterGrid').datagrid('load',serializeObject($('#searchSmsBuyWaterForm')));
			}
		}

		function addSmsBuyWater() {
			var dialog = parent.modalDialog({
				width : 1000,
				height : 600,
				title : '新增',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/smsBuyWater/addSmsBuyWater.jsp',
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
		 * 编辑流水
		 */
		function editSmsBuyWater(id) {
			var dialog = parent.modalDialog({
				width : 1000,
				height : 600,
				title : '编辑',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/smsBuyWater/edit.jsp?id='+id,
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
		 * 查看流水
		 */
		function viewSmsBuyWater(id) {
			var dialog = parent.modalDialog({
				width : 1000,
				height : 600,
				title : '查看',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/smsBuyWater/view.jsp?id=' + id
			});
		}

		function orderSmsBuyWater() {
            var dialog = parent.WidescreenModalDialog ({
                title : '充值',
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/sms/smsPlaceAnOrder.jsp',
                buttons : [ {
                    text : '提交订单',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, parent.$);
                    }
                } ]
            });
		}

        function removeSmsBuyWater(){

			var rows = $("#smsBuyWaterGrid").datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!deleteSmsBuyWater.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#smsBuyWaterGrid").datagrid('reload');
									$("#smsBuyWaterGrid").datagrid('unselectAll');
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

		function payPage(id) {
			window.open('${pageContext.request.contextPath}/page/admin/sms/smsOrderInfo.jsp?id=' + id);
		}


		function resetT(){
			$('#title').val('');
			$('#deptName').val('');
		}
	</script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="newsToolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchSmsBuyWaterForm">
						<table>
							<tr>
								<th>
									应用帐号
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="smsBuyWater.account" id="account" style="width: 150px;" />
								</td>

								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-search',plain:true"
									   onclick="searchSmsBuyWater();">查询</a>&nbsp;
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
							<%--<td>
								<authority:authority authorizationCode="新增流水" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_add',plain:true"
									   onclick="addSmsBuyWater();">新增</a>
								</authority:authority>
							</td>--%>
							<td>
								<authority:authority authorizationCode="新增流水" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_add',plain:true"
									   onclick="orderSmsBuyWater();">充值</a>
								</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="删除流水" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_delete',plain:true"
									   onclick="removeSmsBuyWater();">删除</a>
								</authority:authority>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="smsBuyWaterGrid" ></table>
	</div>
</div>
</body>
</html>