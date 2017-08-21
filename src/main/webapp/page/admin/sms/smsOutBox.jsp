<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="">
	<jsp:include page="../../../inc.jsp"></jsp:include>
	<script type="text/javascript">
		var smsGrid;
		$(function() {
			smsGrid = $('#smsGrid').datagrid({
				url : '${pageContext.request.contextPath}/msgSend/msgSendAction!getSmsOutBox.action',
				fit : true,
				border : false,
				striped : true,
				rownumbers : true,
				pagination : true,
				idField : 'msgId',
				columns : [ [ {
					width : '100',
					title : '电话号码',
					align : 'center',
					field : 'telphone'
				},
				{
					width : '640',
					title : '短信内容',
					align : 'center',
					field : 'content',
					formatter : function(value, row) {
						if (value != null) {
							return "<span title='"+value+"'>"
									+ value + "</span>"
						} else {
							return ""
						}
					}
				},
				{
					width : '100',
					title : '短信状态',
					align : 'center',
					field : 'statues',
					formatter : function(value, row) {
						if(value==9){
							return "<span class='label label-default'>待发送</span>";
						}else if(value==2){
							return "<span class='label label-primary'>已发送</span>";
						}
						else if(value==0){
							return "<span class='label label-success'>发送成功</span>";
						}else{
							return "<span class='label label-danger'>发送失败</span>";
						}
					}
				},
				{
					width : '150',
					title : '发送时间',
					align : 'center',
					field : 'sendtime'
				},
				{
					width : '80',
					title : '消息类型',
					align : 'center',
					field : 'msgType',
					formatter : function(value, row) {
						if(value==0){
							return "<span class='label label-default'>普通</span>";
						}else{
							return "<span class='label label-danger'>系统</span>";
						}
					}
				},
				{
					width : '100',
					title : '发送者账号',
					align : 'center',
					field : 'userAccount'
				}
				] ],
				toolbar : '#toolbar',
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
		
		function searchSms()
		{
			$('#smsGrid').datagrid('load', serializeObject($('#searchForm')));
		}
	</script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										电话号码
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input name="msgSend.telphone" style="width: 150px;" />
									</td>
								<th>
									短信状态
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<!-- <input id="sentStatus" name="email.sentStatus" style="width: 150px;" /> -->
									<select id="statues" name="msgSend.sentStatus" style="width: 150px;">
										<option value="">--全部--</option>
										<option value="0">发送成功</option>
										<option value="2">已发送</option>
										<option value="9">待发送</option>
										<option value="1">发送失败</option>
									</select>
								</td>
								<th>
									消息类型
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<!-- <input id="sentStatus" name="email.sentStatus" style="width: 150px;" /> -->
									<select id="statues" name="msgSend.msgTypeQuery" style="width: 150px;">
										<option value="">--全部--</option>
										<option value="0">普通</option>
										<option value="1">系统</option>
									</select>
								</td>
								<th>
									发送时间
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="msgSend.sentStartTime" readonly="readonly" style="width: 120px;" 
									onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"/>
									-
									<input name="msgSend.sentEndTime" readonly="readonly" style="width: 120px;" 
									onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"/>
								</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'icon-search',plain:true"
											onclick="searchSms();">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="smsGrid"></table>
		</div>
	</body>
</html>
