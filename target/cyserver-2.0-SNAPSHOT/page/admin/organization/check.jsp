<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('#introduction',{
				fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
				uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
				readonlyMode : true,
				afterChange:function(){
					this.sync();
				}
			});
		});

		$(function() {
			$.ajax({
				url : '${pageContext.request.contextPath}/alumni/alumniAction!doNotNeedSecurity_getByAlumniId.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					//alert(JSON.stringify(result));
					if (result.alumniId != undefined) {
						$('#alumni_Name').val(result.alumniName);
						$('#alumniName').text(result.alumniName);
						$('#parent').text(result.parent);
						$('#industryCode').text(result.industry);
						$('#region').text(result.region);
						$('#mainType').text(result.mainType);
						$('#presidentName').text(result.presidentName);
						$('#telephone').text(result.telephone);
						$('#address').text(result.address);
						$('#email').text(result.email);
						editor.html(result.introduction);
					}
					if ($('#view').text() != 'view') $('#checkAlu').show();
					if(result.parent == '学院分会'){
						$('#deptr').show();
						if(result.academyName != undefined){
							$('#aluAcade').text(result.academyName);
						}
					}
				},
				beforeSend:function(){
					parent.$.messager.progress({
						text : '数据加载中....'
					});
				},
				complete:function(){
					parent.$.messager.progress('close');
				}
			});
		});
		var industry_code;
		function submitForm($dialog, $grid, $pjq) {
			if ($('form').form('validate')) {
				//alert($('form').serialize());
				$.ajax({
					url : '${pageContext.request.contextPath}/alumni/alumniAction!update.action',
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$grid.datagrid('reload');
							$dialog.dialog('destroy');
							$pjq.messager.alert('提示', result.msg, 'info');
						} else {
							$pjq.messager.alert('提示', result.msg, 'error');
						}
					},
					beforeSend : function() {
						parent.$.messager.progress({
							text : '数据提交中....'
						});
					},
					complete : function() {
						parent.$.messager.progress('close');
					}
				});
			}
		}
	</script>
</head>
<body>
<form method="post">
	<fieldset id="checkAlu" style='display: none'>
		<legend>审核信息</legend>
		<table class="ta001">
			<tr>
				<th>审核状态</th>
				<td>
					<input type="radio" name="alumni.isUsed" value="2" checked/>通过
					<input type="radio" name="alumni.isUsed" value="0" />不通过
					<input name="alumni.alumniId" value="${param.id}" type="hidden">
					<input name="alumni.alumniName" id="alumni_Name" type="hidden">
					<span id="view" hidden>${param.view}</span>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
	<fieldset>
		<legend>组织信息</legend>
		<table class="ta001">
			<tr>
				<th>分会类型</th>
				<td >
					<span id="mainType"></span>
				</td>
			</tr>
			<tr id='pd'>
				<th>上级分会</th>
				<td>
					<span id="parent"></span>
				</td>
			</tr>
			<tr>
				<th>组织名称</th>
				<td>
					<span id="alumniName"></span>
				</td>
			</tr>
			<tr id="deptr" style="display: none">
				<th>所属院系</th>
				<td>
					<span id="aluAcade"></span>
				</td>
			</tr>
			<tr id="rg">
				<th>所属地区</th>
				<td>
					<span id="region"></span>
				</td>
			</tr>
			<tr id="is">
				<th>行业</th>
				<td>
					<span id="industryCode"></span>
				</td>
			</tr>
			<tr>
				<th>管理院系</th>
				<td>

				</td>
			</tr>
			<tr id="pn">
				<th>联系人名称</th>
				<td>
					<span id="presidentName"></span>
				</td>
			</tr>

			<tr id="tele">
				<th>联系人电话</th>
				<td>
					<span id="telephone"></span>
				</td>
			</tr>

			<tr id="add">
				<th>联系人地址</th>
				<td>
					<span id="address"></span>
				</td>
			</tr>

			<tr id="em">
				<th>联系人邮箱</th>
				<td>
					<span id="email"></span>
				</td>
			</tr>
			<tr>
				<th>简介</th>
				<td>
					<textarea id="introduction"  rows="8" cols="100" name="alumni.introduction" style="width: 700px; height: 300px;" disabled="disabled"></textarea>
				</td>
			</tr>


		</table>
	</fieldset>
</body>
</html>
