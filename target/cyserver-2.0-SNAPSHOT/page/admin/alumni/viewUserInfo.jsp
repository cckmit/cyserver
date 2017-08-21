<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		$(function() {
				$.ajax({
					url : '${pageContext.request.contextPath}/userInfo/userInfoAction!getUserInfoByUserIdForAlumni.action',
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result[0].userId != undefined) {
							$('form').form('load', {
								'userInfo.userName' : result[0].userName,
								'userInfo.aliasname' : result[0].aliasname,
								'userInfo.sex':result[0].sex,
								'userInfo.birthday':$.format.date(result[0].birthday,'yyyy-MM-dd'),
								'userInfo.telId':result[0].telId,
								'userInfo.email':result[0].email,
								'userInfo.qq':result[0].qq,
								'userInfo.weibo':result[0].weibo,
								'userInfo.personalWebsite':result[0].personalWebsite,
								'userInfo.mailingAddress':result[0].mailingAddress,
								'userInfo.residentialArea':result[0].residentialArea,
								'userInfo.workUnit':result[0].workUnit,
								'userInfo.workTel':result[0].workTel,
								'userInfo.workAddress':result[0].workAddress,
								'userInfo.position':result[0].position,
								'userInfo.industryType':result[0].industryType,
								'userInfo.enterprise':result[0].enterprise,
								'userInfo.remarks':result[0].remarks,
								'userInfo.residentialTel':result[0].residentialTel,
								'userInfo.resourceArea':result[0].resourceArea
							});
							$.each(result, function(i, value) {
								var entranceTime='';
								var graduationTime = '';
								var majorName='';
								if(value.entranceTime!=undefined){
									entranceTime = $.format.date(value.entranceTime,'yyyy-MM-dd')
								}
								if(value.graduationTime!=undefined){
									graduationTime = $.format.date(value.graduationTime,'yyyy-MM-dd')
								}
								if(value.majorName!=undefined){
									majorName = value.majorName
								}
								var txt=
								"<table  class='ta001'>"+
								"<tr>"+
								"<th>"+
									"学校"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.schoolName+"'/>"+
								"</td>"+
								"<th>"+
									"院系"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.departName+"'/>"+
								"</td>"+
								"</tr>"+
								"<tr>"+
								"<th>"+
									"年级"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.gradeName+"'/>"+
								"</td>"+
								"<th>"+
									"班级"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.className+"'/>"+
								"</td>"+
								"</tr>"+
								"<tr>"+
								"<th>"+
									"专业"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+majorName+"'/>"+
								"</td>"+
								"<th>"+
									"学历"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.studentType+"'/>"+
								"</td>"+
								"</tr>"+
								"<tr>"+
								"<th>"+
									"学制"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.programLength+"'/>"+
								"</td>"+
								"<th>"+
									"学号"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+value.studentnumber+"'/>"+
								"</td>"+
								"</tr>"+
								"<tr>"+
								"<th>"+
									"入学时间"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+entranceTime+"'/>"+
								"</td>"+
								"<th>"+
									"毕业时间"+
								"</th>"+
								"<td>"+
									"<input type='text' disabled='disabled' value='"+graduationTime+"'/>"+
								"</td>"+
								"</tr>"+
								"</table><br/>"
								$('#stuExp').append(txt);
							});
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
			<fieldset>
				<legend>
					个人信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							姓名
						</th>
						<td>
							<input name="userInfo.userId" type="hidden" value="${param.id}">
							<input name="userInfo.userName" class="easyui-validatebox" disabled="disabled" disdata-options="required:true,validType:'customRequired'"/>
						</td>
						<th>
							性别
						</th>
						<td>
							<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="userInfo.sex" style="width: 150px;">
								<option value="男">男</option>
								<option value="女">女</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							曾用名
						</th>
						<td>
							<input name="userInfo.aliasname" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
							生日
						</th>
						<td>
							<input id="birthday" disabled="disabled" name="userInfo.birthday" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
						</td>
					</tr>
					<tr>
						<th>
							电话号码
						</th>
						<td>
							<input name="userInfo.telId" disabled="disabled" class="easyui-validatebox" data-options="validType:'telePhone'"/>
						</td>
						<th>
							邮箱
						</th>
						<td>
							<input name="userInfo.email" disabled="disabled" class="easyui-validatebox" data-options="validType:'email'"/>
						</td>
					</tr>
					<tr>
						<th>
							QQ
						</th>
						<td>
							<input name="userInfo.qq" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
							微博
						</th>
						<td>
							<input name="userInfo.weibo" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
					</tr>
					<tr>
						<th>
							通讯地址
						</th>
						<td>
							<input name="userInfo.mailingAddress" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							居住地区
						</th>
						<td>
							<input name="userInfo.residentialArea" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							个人网站
						</th>
						<td>
							<input name="userInfo.personalWebsite" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
							家庭电话
						</th>
						<td>
							<input name="userInfo.residentialTel" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							生源
						</th>
						<td>
							<input name="userInfo.resourceArea" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
						</th>
						<td>
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset>
				<legend>
					工作经历
				</legend>
				<table class="ta001">
					<tr>
						<th>
							工作单位
						</th>
						<td>
							<input name="userInfo.workUnit" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							工作电话
						</th>
						<td>
							<input name="userInfo.workTel" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							工作地址
						</th>
						<td>
							<input name="userInfo.workAddress" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							职务
						</th>
						<td>
							<input name="userInfo.position" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							行业类别
						</th>
						<td>
							<input name="userInfo.industryType" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							企业性质
						</th>
						<td>
							<input name="userInfo.enterprise" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							备注
						</th>
						<td colspan="3">
							<textarea rows="5" cols="90" disabled="disabled" name="userInfo.remarks"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<br>
			<fieldset>
				<legend>
					学习经历
				</legend>
				<div id="stuExp">
					
				</div>
			</fieldset>
		</form>
	</body>
</html>
