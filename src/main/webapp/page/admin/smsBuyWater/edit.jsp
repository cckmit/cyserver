<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
			$(function () {
				var id = $('#smsBuyWaterId').val();

				$.ajax({
					url: '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!getSmsBuyWaterById.action',
					data: { 'smsBuyWaterId' : id },
					dataType: 'json',
					success: function (result) {

						if (result.id != undefined) {

							$('form').form('load', {

								'smsBuyWater.accountId': result.accountId,
								'smsBuyWater.orderNo': result.orderNo,
								'smsBuyWater.buyPrice': result.buyPrice,
								'smsBuyWater.surplusNum': result.surplusNum
							});
							editor.html(result.content);

						}
					},

					complete: function () {

						parent.$.messager.progress('close');

					}
				});
			});

			function submitForm($dialog, $grid, $pjq)
			{
				if ($('form').form('validate'))
				{
					$.ajax({
						url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!updateSmsBuyWater.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result)
						{
							if (result.success)
							{
								$grid.datagrid('reload');
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else
							{
								$pjq.messager.alert('提示', result.msg, 'error');
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
							parent.$.messager.progress('close');
						}
					});
				}
			};
</script>
	</head>

	<body>
		<form method="post" class="form">
			<fieldset>
				<legend>
					流水信息
				</legend>
				<table class="ta001">

					<tr>
						<th>
							应用帐号
						</th>
						<td colspan="3">
							<input name="smsBuyWater.id"  id="smsBuyWaterId" type="hidden" value="${param.id}">
							<input name="smsBuyWater.accountId" class="easyui-validatebox"
								   style="width: 700px;"
								   maxlength="30" value=""/>
						</td>
					</tr>



					<tr>
						<th>
							订单号
						</th>
						<td colspan="3">
							<input name="smsBuyWater.orderNo" class="easyui-validatebox"
								   style="width: 700px;"
								   maxlength="30" value=""/>
						</td>
					</tr>

					<tr>
						<th>
							购买金额
						</th>
						<td colspan="3">

							<input name="smsBuyWater.buyPrice" class="easyui-validatebox"
								   style="width: 700px;"
								   maxlength="30" value=""/>
						</td>
					</tr>

					<tr>
						<th>
							剩余条数
						</th>
						<td colspan="3">

							<input name="smsBuyWater.surplusNum" class="easyui-validatebox"
								   style="width: 700px;"
								   maxlength="30" value=""/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
