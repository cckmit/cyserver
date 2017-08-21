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
			var industry_value;
		function submitForm($dialog, $grid, $pjq)
		{
            $('#industry').val(industry_value);
			if($("#inputs").is(":hidden")&&$("#select").is(":visible")){
				if($('#schoolId').val()==''){
					parent.$.messager.alert('提示', '请选择学校', 'info');
					return false;
				}
				if($('#departId').val()==''){
					parent.$.messager.alert('提示', '请选择院系', 'info');
					return false;
				}
				if($('#gradeId').val()==''){
					parent.$.messager.alert('提示', '请选择年级', 'info');
					return false;
				}
				if($('#classId').val()==''){
					parent.$.messager.alert('提示', '请选择班级', 'info');
					return false;
				}
			}
			if($("#inputs").is(":visible")&&$("#select").is(":hidden")){
				if($('#schoolName').val()==''){
					parent.$.messager.alert('提示', '请输入学校名称', 'info');
					return false;
				}
				if($('#departName').val()==''){
					parent.$.messager.alert('提示', '请输入院系名称', 'info');
					return false;
				}
				if($('#gradeName').val()==''){
					parent.$.messager.alert('提示', '请输入年级名称', 'info');
					return false;
				}
				if($('#className').val()==''){
					parent.$.messager.alert('提示', '请输入班级名称', 'info');
					return false;
				}
			}
			if ($('form').form('validate'))
			{
				$.ajax({
					url : '${pageContext.request.contextPath}/userBaseInfo/userBaseInfoAction!save.action',
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
	
		function changeDept(){
			if($("#inputs").is(":hidden")&&$("#select").is(":visible")){
				$("#inputs").show();
				$("#select").hide();
				//$('#schoolId').prop('value','');
				//$('#departId').prop('value','');
				//$('#gradeId').prop('value','');
				//$('#classId').prop('value','');
				//$('#school').combobox('clear');
				//$('#depart').combobox('clear');
				//$('#grade').combobox('clear');
				//$('#classes').combobox('clear');
				//$('#major').combobox('clear');
				//$('#classes').combobox('loadData',[]);
				//$('#grade').combobox('loadData',[]);
				//$('#major').combobox('loadData',[]);
				//$('#depart').combobox('loadData',[]);
				$('#isInput').prop('value','1');
			}
			else if($("#inputs").is(":visible")&&$("#select").is(":hidden")){
				$("#inputs").hide();
				$("#select").show();
				$('#isInput').prop('value','0');
				//$('#schoolName').prop('value','');
				//$('#departName').prop('value','');
				//$('#gradeName').prop('value','');
				//$('#className').prop('value','');
			}
		}
	</script>
	</head>

	<body>
		<form method="post">
			<input id="isInput" name="isInput" type="hidden">
			<fieldset>
				<legend>
					院系信息
				</legend>
				<table class="ta001" id="select">
					<tr>
						<th>
							学校
							                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
						</th>
						<td>
							<input name="userInfo.schoolId" id="schoolId" type="hidden">
							<input name="userInfo.departId" id="departId" type="hidden">
							<input name="userInfo.gradeId" id="gradeId" type="hidden">
							<input name="userInfo.classId" id="classId" type="hidden">
							<input id="school" class="easyui-combobox" style="width: 150px;" 
											data-options="
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',  
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId; 
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
							<input id="depart" class="easyui-combobox" style="width: 150px;"
											data-options=" 
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
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
							<input id="grade" class="easyui-combobox" style="width: 150px;"
											data-options="  
												valueField: 'deptId',  
												textField: 'deptName',  
												editable:false,
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
								<input id="classes" class="easyui-combobox" style="width: 150px;"
												data-options="
													editable:false,
													valueField:'deptId',
													textField:'deptName',
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
							<input id="major" class="easyui-combobox" style="width: 150px;" name="userInfo.majorId"
											data-options="  
											valueField: 'majorId',  
											textField: 'majorName',  
											editable:false
										" />
						</td>
						<th></th>
						<td><a href="javascript:void(0)" onclick="changeDept()">找不到院系信息?切换至手动输入</a></td>				
					</tr>
				</table>
				<table class="ta001" id="inputs" style="display: none;">
					<tr>
						<th>
							学校
							                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
						</th>
						<td>
							<input id="schoolName" name="userInfo.schoolName" class="easyui-validatebox" style="width: 150px;"/>
						</td>
						<th>
							院系
						</th>
						<td>
							<input id="departName" name="userInfo.departName" class="easyui-validatebox" style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th>
							年级
						</th>
						<td>
							<input id="gradeName" name="userInfo.gradeName" class="easyui-validatebox" style="width: 150px;" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" readonly="readonly"/>级
						</td>
						<th>
							班级
						</th>
						<td>
								<input id="className" name="userInfo.className" class="easyui-validatebox" style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th>
							专业
						</th>
						<td>
							<input id="majorName" name="userInfo.majorName" class="easyui-validatebox" style="width: 150px;"/>
						</td>
						<th></th>
						<td><a href="javascript:void(0)" onclick="changeDept()">切换至自动选择</a></td>				
					</tr>
				</table>
			</fieldset>
			<br/>
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
							<input name="userInfo.userName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'"/>
						</td>
						<th>
							性别
						</th>
						<td>
							<select class="easyui-combobox" data-options="editable:false" name="userInfo.sex" style="width: 150px;">
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
							<input class="easyui-combobox" style="width: 150px;" name="userInfo.studentType"
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
							<input class="easyui-combobox" style="width: 150px;" name="userInfo.programLength"
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
							<input name="userInfo.studentnumber" class="easyui-validatebox" />
						</td>
						<th>
							籍贯
						</th>
						<td>
							<input name="userInfo.resourceArea" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							入学时间
						</th>
						<td>
							<input name="userInfo.entranceTime" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
						</td>
						<th>
							毕业时间
						</th>
						<td>
							<input name="userInfo.graduationTime" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>							
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
						<th>
							所属行业
						</th>

						<td colspan="3">
							<input name="userInfo.industryType" id="industry" type="hidden">
							<input class="easyui-combobox" name="industry1" id="industry1"
								   data-options="
	                    url:'${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode=-1',
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	industry_code = '';
			                	industry_value = '';
			                	$('#industry1').combobox('clear');
			                	$('#industry2').combobox('clear');
			                	$('#industry2').combobox('loadData',[]);
			                	$('#industry3').combobox('clear');
								$('#industry3').combobox('loadData',[]);
								$('#industry4').combobox('clear');
								$('#industry4').combobox('loadData',[]);			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code;
							industry_code = rec.code;
							industry_value = rec.value;
							$('#industry2').combobox('clear');
							$('#industry2').combobox('reload', url);
							$('#industry3').combobox('clear');
							$('#industry3').combobox('loadData',[]);
							$('#industry4').combobox('clear');
							$('#industry4').combobox('loadData',[]);
						}
                    	">
							&nbsp; <input class="easyui-combobox" name="industry2" id="industry2"
										  data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry2').combobox('clear');
			                	$('#industry3').combobox('clear');
			                	$('#industry3').combobox('loadData',[]);
			                	$('#industry4').combobox('clear');
								$('#industry4').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code;
							industry_code = rec.code;
							industry_value = rec.value;
							$('#industry3').combobox('clear');
							$('#industry3').combobox('reload', url);
							$('#industry4').combobox('clear');
							$('#industry4').combobox('loadData',[]);
						}
                    	">
							&nbsp; <input class="easyui-combobox" name="industry3" id="industry3"
										  data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry3').combobox('clear');
			                	$('#industry4').combobox('clear');
								$('#industry4').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code;
							industry_code = rec.code;
							industry_value = rec.value;
							$('#industry4').combobox('clear');
							$('#industry4').combobox('reload', url);
						}
                    	">
							&nbsp; <input class="easyui-combobox" name="industry4" id="industry4"
										  data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry4').combobox('clear');
			                }
			            }]
                    	">
						</td>
					</tr>


					<tr>
						<th>常驻城市</th>
						<td colspan="3">
							<input class="easyui-combobox" name="province" id="province"
								   data-options="
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province').combobox('clear');
			                	$('#city').combobox('clear');
			                	$('#city').combobox('loadData',[]);
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city').combobox('clear');
							$('#city').combobox('reload', url);
							$('#area').combobox('clear');
							$('#area').combobox('loadData',[]);
						}
                    	">
							&nbsp; <input class="easyui-combobox" name="city" id="city"
										  data-options="
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city').combobox('clear');
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/area/areaAction!doNotNeedSecurity_getArea2ComboBox.action?cityId='+rec.id;
							$('#area').combobox('clear');
							$('#area').combobox('reload', url);
						}
                    	">
							&nbsp; <input class="easyui-combobox" name="area" id="area"
										  data-options="
	                    method:'post',
	                    valueField:'areaName',
	                    textField:'areaName',
	                    editable:false,
	                    prompt:'县(区)',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#area').combobox('clear');
			                }
			            }]
                    	">
						</td>
					</tr>




					<tr>
						<th>
							曾用名
						</th>
						<td>
							<input name="userInfo.aliasname" class="easyui-validatebox"/>
						</td>
						<th>
							生日
						</th>
						<td>
							<input id="birthday" name="userInfo.birthday" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
						</td>
					</tr>
					<tr>
						<th>
							邮箱
						</th>
						<td>
							<input name="userInfo.email" class="easyui-validatebox" data-options="validType:'email'"/>
						</td>
						<th>
							QQ
						</th>
						<td>
							<input name="userInfo.qq" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							手机号码
						</th>
						<td>
							<input name="userInfo.telId" class="easyui-validatebox" data-options="validType:'telePhone'"/>
						</td>
						<th>
							国籍
						</th>
						<td>
							<input name="userInfo.nationality" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							证件类型
						</th>
						<td>
							<input class="easyui-combobox" style="width: 150px;" name="userInfo.cardType"
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
							<input name="userInfo.card" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<th>
							民族
						</th>
						<td>
							<input name="userInfo.nation" class="easyui-validatebox"/>
						</td>
						<th>
							政治面貌
						</th>
						<td>
							<input name="userInfo.political" class="easyui-validatebox"/>
						</td>
					</tr>
					
					<tr>
						<th>
							微博
						</th>
						<td>
							<input name="userInfo.weibo" class="easyui-validatebox"/>
							
						</td>
						<th>
							个人网站
						</th>
						<td>
							<input name="userInfo.personalWebsite" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<th>
							通讯地址
						</th>
						<td>
							<input name="userInfo.mailingAddress" class="easyui-validatebox"/>
							
						</td>
						<th>
							单位地址
						</th>
						<td>
							<input name="userInfo.workAddress" class="easyui-validatebox"/>

						</td>
						<%--<th>
							居住城市
						</th>
						<td>
							<input name="userInfo.residentialArea" class="easyui-validatebox"/>
						</td>--%>
					</tr>
					<tr>
						<th>
							工作单位
						</th>
						<td>
							<input name="userInfo.workUnit" class="easyui-validatebox"/>
							
						</td>
						<th>
							单位电话
						</th>
						<td>
							<input name="userInfo.workTel" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>

						<th>
							职务
						</th>
						<td>
							<input name="userInfo.position" class="easyui-validatebox"/>
						</td>
						<th>
							企业性质
						</th>
						<td>
							<input name="userInfo.enterprise" class="easyui-validatebox"/>
						</td>
					</tr>
					<%--<tr>
						&lt;%&ndash;<th>
							所属行业
						</th>
						<td>
							<input name="userInfo.industryType" class="easyui-validatebox"/>
							
						</td>&ndash;%&gt;

					</tr>--%>
					<tr>
						<th>
							固定电话
						</th>
						<td>
							<input name="userInfo.residentialTel" class="easyui-validatebox"/>
						</td>
						<%--<th>
							状态
						</th>
						<td>
							<input name="userInfo.status" class="easyui-validatebox"/>
						</td>--%>
					</tr>
					<tr>
						<th>
							备注
						</th>
						<td colspan="3">
							<textarea rows="5" cols="90" name="userInfo.remarks"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
