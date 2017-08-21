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
		var roleIds = '${sessionScope.user.roleIds}';
		var isHD = false;
		if(roleIds && roleIds != ''){
			var roleIDs = roleIds.split(',');
			for(var i in roleIDs){
				if(roleIDs[i] == '15'){
					isHD = true;
					break;
				}
			}
		}
		$(function() {
				$.ajax({
					url : '${pageContext.request.contextPath}/userInfo/userInfoAction!getUserInfoByUserId.action',
					data : $('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.userId != undefined) {
							$('b').append(result.fullName);
                            if(result.checkFlag==1){
                                $('#st').html('<span style="color:green;">正式校友</span>');
                            }else if(result.checkFlag==0){
                                $('#st').html('<span>待核校友</span>');
                            }else{
                                $('#st').html('<span style="color:red;">未通过</span>');
                            }
							$('form').form('load', {
								'userInfo.userName' : result.userName,
								'userInfo.aliasname' : result.aliasname,
								'userInfo.sex':result.sex,
								'userInfo.birthday':$.format.date(result.birthday,'yyyy-MM-dd'),
								'userInfo.card':result.card,
								'userInfo.email':result.email,
								'userInfo.qq':result.qq,
								'userInfo.cardType':result.cardType,
								'userInfo.studentnumber':result.studentnumber,
								'userInfo.nation':result.nation,
								'userInfo.political':result.political,
								'userInfo.nationality':result.nationality,
								'userInfo.entranceTime':$.format.date(result.entranceTime,'yyyy-MM-dd'),
								'userInfo.graduationTime':$.format.date(result.graduationTime,'yyyy-MM-dd'),
								'userInfo.programLength':result.programLength,
								'userInfo.studentType':result.studentType,
								'userInfo.resourceArea':result.resourceArea,
								'userInfo.status':result.status,
								'userInfo.weibo':result.weibo,
								'userInfo.personalWebsite':result.personalWebsite,
								'userInfo.mailingAddress':result.mailingAddress,
								'userInfo.residentialArea':result.residentialArea,
								'userInfo.workUnit':result.workUnit,
								'userInfo.workTel':result.workTel,
								'userInfo.workAddress':result.workAddress,
								'userInfo.position':result.position,
								'userInfo.industryType':result.industryType,
								'userInfo.enterprise':result.enterprise,
								'userInfo.remarks':result.remarks,
								'userInfo.majorId':result.majorId==0?'':result.majorId,
								'userInfo.checkIdea':result.checkIdea,
								'userInfo.residentialTel':result.residentialTel,
                                'schoolName':result.schoolName,
                                'departName':result.departName,
                                'gradeName':result.gradeName,
                                'className':result.className,
                                'userInfo.attributionInquiry':result.attributionInquiry
							});
                            $('#industryCode').text(result.industryType?result.industryType:'');
                            $('#residentialArea').text(result.residentialArea?result.residentialArea:'');
                            var telephone = result.telId;
                            if(telephone && telephone != ''){
                                if(isHD){
                                    if(telephone.length > 4){
                                        var tel = telephone.substring(telephone.length-4);
                                        for(var i = 0 ; i < telephone.length-4; i++){
                                            tel = "*" + tel ;
                                        }
                                        telephone = tel;
                                    }
                                }
                                $('#telId').val(telephone);
							}


                            if(result.attributionInquiry && result.attributionInquiry !=''){
                                $('#attributionInquiry').html("("+result.attributionInquiry+")");
                            }else {
                                $('#attributionInquiry').html("(未获取到归属地)");
                            }
//							alert(JSON.stringify(result.dataMiningList[0].phone));
//							jiangling 展示挖掘人的信息
							var str = "";
							for(var i=0; i<result.dataMiningList.length;i++){
								str += "<tr>\
										<th>挖掘人</th>\
										<td>"+result.dataMiningList[i].miningUserName+"</td>\
										<th>挖掘的手机号</th>\
										<td>"+result.dataMiningList[i].phone+"</td>\
										<th>挖掘时间</th>\
										<td>"+result.dataMiningList[i].createDate+"</td>\
										</tr>";
							}
							$("#dataMining").html(str);
//							alert(JSON.stringify(str));
							if(str != "") {$('#dataMiningId').show()};

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
							学历
						</th>
						<td>
							<input class="easyui-combobox" disabled="disabled" style="width: 150px;" name="userInfo.studentType"
								data-options="  
								valueField: 'dictName',  
								textField: 'dictName',  
								editable:false,
								url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历') 
							" />
						</td>
						<th>
							学制
						</th>
						<td>
							<input class="easyui-combobox" disabled="disabled" style="width: 150px;" name="userInfo.programLength"
								data-options="  
								valueField: 'dictName',  
								textField: 'dictName',  
								editable:false,
								url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学制') 
							" />
						</td>
					</tr>
					<tr>
						<th>
							学号
						</th>
						<td>
							<input name="userInfo.studentnumber" disabled="disabled" class="easyui-validatebox" />
						</td>
						<th>
							籍贯
						</th>
						<td>
							<input name="userInfo.resourceArea" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							入学时间
						</th>
						<td>
							<input name="userInfo.entranceTime" disabled="disabled" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
						</td>
						<th>
							毕业时间
						</th>
						<td>
							<input name="userInfo.graduationTime" disabled="disabled" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
							
						</td>
					</tr>
					<tr>
						<th>
							学校

						</th>
						<td>
							<input name="userInfo.schoolId" id="schoolId" type="hidden">
							<input name="userInfo.departId" id="departId" type="hidden">
							<input name="userInfo.gradeId" id="gradeId" type="hidden">
							<input name="userInfo.classId" id="classId" type="hidden">
							<input id="school" disabled="disabled" name="schoolName" class="easyui-combobox" style="width: 150px;"
								   data-options="
												valueField: 'deptName',
												textField: 'deptName',
												editable:false,
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);
													$('#depart').combobox('reload', url);
													$('#schoolId').prop('value',rec.deptId);
													$('#departId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
										}" />
						</td>
						<th>
							院系
						</th>
						<td>
							<input id="depart" disabled="disabled" name="departName" class="easyui-combobox" disabled="disabled" style="width: 150px;"
								   data-options="
												valueField: 'deptName',
												textField: 'deptName',
												editable:false,
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId=${param.id.substring(0,6)}',
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('reload', url);
													$('#major').combobox('clear');
													$('#major').combobox('reload', url1);
													$('#departId').prop('value',rec.deptId);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');
										}" />
						</td>
					</tr>
					<tr>
						<th>
							年级
						</th>
						<td>
							<input id="grade" disabled="disabled" name="gradeName" class="easyui-combobox" disabled="disabled" style="width: 150px;"
								   data-options="
												valueField: 'deptName',
												textField: 'deptName',
												editable:false,
												url:'${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId=${param.id.substring(0,10)}',
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);
													$('#gradeId').prop('value',rec.deptId);
													$('#classId').prop('value','');
										}" />
						</td>
						<th>
							班级
						</th>
						<td>
							<input id="classes" disabled="disabled" name="className" class="easyui-combobox" disabled="disabled" style="width: 150px;"
								   data-options="
													editable:false,
													valueField:'deptName',
													textField:'deptName',
													url:'${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId=${param.id.substring(0,14)}',
													onSelect: function(rec){
														$('#classId').prop('value',rec.deptId)
													}
													"/>
						</td>
					</tr>
					<tr>
						<th>
							专业
						</th>
						<td>
							<input id="majorId" class="easyui-combobox" disabled="disabled" style="width: 150px;" name="userInfo.majorId"
											data-options="  
											valueField: 'majorId',  
											textField: 'majorName',  
											editable:false,
											url: '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId=${param.id.substring(0,10)}' 
										" />
						</td>		
						<th>状态</th>
						<td id="st">
							
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset>
				<legend>
					其它信息
				</legend>
				<table class="ta001">
					<tr>
						<th>所属行业</th>
						<td colspan="3">
							<span id="industryCode"></span>
						</td>
					</tr>
					<tr>
						<th>常驻城市</th>
						<td colspan="3">
							<span id="residentialArea"></span>
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
							邮箱
						</th>
						<td>
							<input name="userInfo.email" disabled="disabled" class="easyui-validatebox" data-options="validType:'email'"/>
						</td>
						<th>
							QQ
						</th>
						<td>
							<input name="userInfo.qq" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							证件类型
						</th>
						<td>
							<input class="easyui-combobox" disabled="disabled" style="width: 150px;" name="userInfo.cardType"
											data-options="  
												valueField: 'dictName',  
												textField: 'dictName',  
												editable:false,
												url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('证件类型') 
												" />
						</td>
						<th>
							证件号码
						</th>
						<td>
							<input name="userInfo.card" disabled="disabled" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>
							手机号码
						</th>
						<td>
							<input name="userInfo.telId" id="telId" disabled="disabled" class="easyui-validatebox" data-options="validType:'telePhone'"/>
							<span id="attributionInquiry" style="color: red"></span>
						</td>
						<th>
							国籍
						</th>
						<td>
							<input name="userInfo.nationality" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							民族
						</th>
						<td>
							<input name="userInfo.nation" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
							政治面貌
						</th>
						<td>
							<input name="userInfo.political" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							微博
						</th>
						<td>
							<input name="userInfo.weibo" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							个人网站
						</th>
						<td>
							<input name="userInfo.personalWebsite" disabled="disabled" class="easyui-validatebox"/>
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
							单位地址
						</th>
						<td>
							<input name="userInfo.workAddress" disabled="disabled" class="easyui-validatebox"/>

						</td>
						<%--<th>
							居住城市
						</th>
						<td>
							<input name="userInfo.residentialArea" disabled="disabled" class="easyui-validatebox"/>
						</td>--%>
					</tr>
					<tr>
						<th>
							工作单位
						</th>
						<td>
							<input name="userInfo.workUnit" disabled="disabled" class="easyui-validatebox"/>
							
						</td>
						<th>
							单位电话
						</th>
						<td>
							<input name="userInfo.workTel" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>

						<th>
							职务
						</th>
						<td>
							<input name="userInfo.position" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<th>
							企业性质
						</th>
						<td>
							<input name="userInfo.enterprise" disabled="disabled" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<%--<th>
							所在行业
						</th>
						<td>
							<input name="userInfo.industryType" disabled="disabled" class="easyui-validatebox"/>
							
						</td>--%>

					</tr>
					<tr>
						<th>
							固定电话
						</th>
						<td>
							<input name="userInfo.residentialTel" disabled="disabled" class="easyui-validatebox"/>
						</td>
						<%--<th>
							状态
						</th>
						<td>
							<input name="userInfo.status" disabled="disabled" class="easyui-validatebox"/>
						</td>--%>
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
			<br/>
			<fieldset>
				<legend>
					审核信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							审核意见
						</th>
						<td colspan="3">
							<textarea rows="5" cols="90" disabled="disabled" name="userInfo.checkIdea"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset id="dataMiningId" style="display:none">
				<legend>挖掘手机号</legend>
				<table class="ta001" id="dataMining">
				</table>
			</fieldset>
		</form>
	</body>
</html>
