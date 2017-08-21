<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String accountNum = request.getParameter("accountNum");
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
		var accountNum = "<%=accountNum%>";
		$(function() {
				$.ajax({
					url : '${pageContext.request.contextPath}/userProfilesss/userProfilesssAction!doNotNeedSecurity_view.action',
					data : "accountNum="+accountNum,
					dataType : 'json',
					success : function(result) {
						$("#accountNum").val(result.accountNum);
						$("#phoneNum").val(result.phoneNum);
						$("#name").val(result.name);
						if(result.sex=="0"){
							$("#sex").val("男");
						}else if(result.sex=="1"){
							$("#sex").val("女");
						}else{
							$("#sex").val("-");
						}
						$("#address").val(result.address);
						$("#workUtil").val(result.workUtil);
						$("#position").val(result.position);
						$("#profession").val(result.profession);
						$("#hobby").val(result.hobby);
						$("#intrestType").val(result.intrestType);
						//教育经历赋值
						var departName = result.departName;
						if(departName!=null && departName!=""){
							var array = departName.split("_");
							var html = "";
							for(var i=0;i<array.length;i++){
								html += "<tr>";
								html += "<th>";
								html += ""+(i+1);
								html += "</th>";
								html += "<td>";
								html += array[i];
								html += "</td>";
								html += "</tr>";
							}
							$("#table").html(html);
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
		
</script>
	</head>

	<body>
		<form method="post">
			<div style="text-align: center;"><b></b></div>
			<fieldset>
				<legend>
					基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							帐号
						</th>
						<td>
							<input id="accountNum" class="easyui-validatebox" disabled="disabled" />
						</td>
						<th>
							手机帐号
						</th>
						<td>
							<input id="phoneNum" class="easyui-validatebox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<th>
							姓名
						</th>
						<td>
							<input id="name" class="easyui-validatebox" disabled="disabled" />
						</td>
						<th>
							性别
						</th>
						<td>
							<input id="sex" class="easyui-validatebox" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<th>
							所在地
						</th>
						<td>
							<input id="address" class="easyui-validatebox" disabled="disabled" style="width: 150px;" />
						</td>
						<th>
							工作单位
						</th>
						<td>
							<input id="workUtil" class="easyui-validatebox" disabled="disabled" style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<th>
							职务
						</th>
						<td>
							<input id="position" disabled="disabled" class="easyui-validatebox" />
						</td>
						<th>
							行业
						</th>
						<td>
							<input id="profession" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							兴趣
						</th>
						<td>
							<input id="hobby" disabled="disabled" class="easyui-validatebox" />
						</td>
						<th>
							兴趣类型
						</th>
						<td>
							<input id="intrestType" disabled="disabled" class="easyui-validatebox" />
							
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset>
				<legend>
					教育经历
				</legend>
				<table id="table" class="ta001">
					
				</table>
			</fieldset>
		</form>
	</body>
</html>
