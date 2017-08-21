<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.core.alumnicard.entity.AlumniCardExt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		$(function () {
			if ($('#backschoolOnlineSignId').val()!='') {
				$.ajax({
					url: '${pageContext.request.contextPath}/backschoolOnlineSign/backschoolOnlineSignAction!doNotNeedSecurity_getById.action',
					data: $('form').serialize(),
					dataType: 'json',
					success: function (result) {
						if (result&&result.id != undefined) {
							$('form').form('load', {
								'backschoolOnlineSign.id': result.id,
								'backschoolOnlineSign.name': result.name,
								'backschoolOnlineSign.sex': result.sex,
								'backschoolOnlineSign.location': result.location,
								'backschoolOnlineSign.unit': result.unit,
								'backschoolOnlineSign.position': result.position,
								'backschoolOnlineSign.industry': result.industry,
								'backschoolOnlineSign.contactAddress': result.contactAddress,
								'backschoolOnlineSign.postCode': result.postCode,
								'backschoolOnlineSign.officePhone': result.officePhone,
								'backschoolOnlineSign.homePhone': result.homePhone,
								'backschoolOnlineSign.mobilePhone': result.mobilePhone,
								'backschoolOnlineSign.emailBox': result.emailBox,
								'backschoolOnlineSign.cardExtList': result.cardExtList,
								'backschoolOnlineSign.needProjector': result.needProjector,
								'backschoolOnlineSign.needVisit': result.needVisit
							});

						}
					},
					beforeSend: function () {
						parent.$.messager.progress({
							text: '数据加载中....'
						});
					},
					complete: function () {
						parent.$.messager.progress('close');
					}
				});
			}
		});
	</script>
</head>

<body>
<form method="post" id="form" class="form">
	<input name="backschoolOnlineSign.id" type="hidden" id="backschoolOnlineSignId" value="${param.id}" >
	<fieldset>
		<legend>
			基本信息
		</legend>

		<table class="ta001">
			<tr>
				<th>
					姓名
				</th>
				<td>
					<input name="backschoolOnlineSign.name" disabled="disabled">
				</td>
				<th>
					性别
				</th>
				<td>
					<select id="sex" readonly="readonly" name="backschoolOnlineSign.sex" class="easyui-combobox" style="width:155px"  disabled="disabled"
							data-options="required:true, editable:false"
					>
						<option value="0" selected>男</option>
						<option value="1" selected>女</option>
					</select>

				</td>
			</tr>

			<tr>
				<th>
					所在地
				</th>
				<td>
					<input name="backschoolOnlineSign.location" disabled="disabled">
				</td>
				<th>
					工作单位
				</th>
				<td>
					<input readonly="readonly" name="backschoolOnlineSign.unit" class="easyui-validatebox" disabled="disabled"/>
				</td>

			</tr>


			<tr>
				<th>
					职务
				</th>
				<td>
					<input readonly="readonly" name="backschoolOnlineSign.position" class="easyui-validatebox" disabled="disabled"
						   data-options="validType:'customRequired'"
						   maxlength="100" />
				</td>
				<th>
					所属行业
				</th>
				<td>
					<input readonly="readonly" name="backschoolOnlineSign.industry" class="easyui-validatebox" disabled="disabled"
						   data-options="validType:'customRequired'"
						   maxlength="100" />
				</td>
			</tr>
		</table>
	</fieldset>

	<br>
	<fieldset>
		<legend>
			联系信息
		</legend>

		<table class="ta001">
			<tr>
				<th>
					通讯地址
				</th>
				<td>
					<input class="easyui-validatebox" name="backschoolOnlineSign.contactAddress" disabled="disabled"/>
				</td>
				<th>
					邮编
				</th>
				<td>
					<input readonly="readonly" name="backschoolOnlineSign.postCode" class="easyui-validatebox" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<th>
					办公电话
				</th>
				<td>
					<input class="easyui-validatebox" name="backschoolOnlineSign.officePhone" disabled="disabled"/>
				</td>
				<th>
					家庭电话
				</th>
				<td>
					<input readonly="readonly" name="backschoolOnlineSign.homePhone" class="easyui-validatebox" disabled="disabled"
					/>
				</td>

			</tr>
			<th>
				电话号码
			</th>
			<td>
				<input class="easyui-validatebox" name="backschoolOnlineSign.mobilePhone" disabled="disabled"/>
			</td>
			<th>
				电子邮箱
			</th>
			<td>
				<input readonly="readonly" name="backschoolOnlineSign.emailBox" class="easyui-validatebox" disabled="disabled"
				/>
			</td>

			</tr>
		</table>
	</fieldset>

	<br>

	<legend>
		教育信息
	</legend>

	<table class="ta001">
		<tr>
			<th>
				学位
			</th>
			<td>
				<input name="backschoolOnlineSign.degree" disabled="disabled">
			</td>
			<th>
				院系
			</th>
			<td>
				<input name="backschoolOnlineSign.depart" disabled="disabled">
			</td>
		</tr>

		<tr>
			<th>
				年级
			</th>
			<td>
				<input name="backschoolOnlineSign.location" disabled="disabled">
			</td>
			<th>
				专业
			</th>
			<td>
				<input readonly="readonly" name="backschoolOnlineSign.major" class="easyui-validatebox" disabled="disabled"/>
			</td>

		</tr>
		<tr>
			<th>
				学号
			</th>
			<td>
				<input name="backschoolOnlineSign.studentNumber" disabled="disabled">
			</td>
			<th>
				班级
			</th>
			<td>
				<input name="backschoolOnlineSign.clazz" disabled="disabled">
			</td>
		</tr>
	</table>
	</fieldset>
	<br>


	<fieldset>
		<legend>
			活动信息
		</legend>

		<table class="ta001">
			<tr>
				<th>
					是否参观
				</th>
			<tr>
				<th>
					钱币博物馆
				</th>
				<td>

					<select name="backschoolOnlineSign.needMeeting" class="easyui-combobox" style="width: 155px;" disabled="disabled" data-options="editable:false">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
				<th>
					校史馆
				</th>
				<td>

					<select name="backschoolOnlineSign.needMeeting" class="easyui-combobox" style="width: 155px;" disabled="disabled" data-options="editable:false">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>

			</tr>

			</tr>
			<tr>
				<th>
					是否浏览校园
				</th>
				<td>

					<select name="backschoolOnlineSign.needLookSchool" class="easyui-combobox" style="width: 155px;" disabled="disabled" data-options="editable:false">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>

				<th>
					是否自驾车
				</th>
				<td>

					<select name="backschoolOnlineSign.needDriving" class="easyui-combobox" style="width: 155px;" disabled="disabled" data-options="editable:false">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>

			</tr>

			<tr>

				<th>
					是否有家人随行
				</th>
				<td>

					<select name="backschoolOnlineSign.needCarryingFamily" class="easyui-combobox" style="width: 155px;" disabled="disabled" data-options="editable:false">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>