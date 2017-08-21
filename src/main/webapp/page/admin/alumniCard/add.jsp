<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	
	var actionName = "alumniCardAction";
	var actionUrl = "${pageContext.request.contextPath}/page/admin/alumniCard/";
	var actionFullPath = actionUrl + actionName;
	
	function submitForm($dialog, $grid, $pjq)
	{
		if ($('form').form('validate'))
		{
			$.ajax({
				url : actionFullPath + '!save.action',
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
	}
	
	
	
	$(function() 
	{
		uploadPic("#personal_upload_button", "formData.personalPic", "#personalPic");
		uploadPic("#credentials_upload_button", "formData.credentialsPic", "#credentialsPic");
	});
	
	
	function uploadPic(upload_button_name, picName, picDivName)
	{
		var button = $(upload_button_name), interval;
		new AjaxUpload(button, 
		{
			action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
			name : 'upload',
			onSubmit : function(file, ext) 
			{
				if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) 
				{
					$.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
					return false;
				}
				$.messager.progress({text : '图片正在上传,请稍后....'});
			},
			onComplete : function(file, response) 
			{
				$.messager.progress('close');
				var resp = $.parseJSON(response);
				
				if (resp.error == 0) 
				{
					$(picDivName).append(
						'<div style="float:left;width:180px;">'+
						'<img src="'+resp.url+'" width="150px" height="150px"/>'+
						'<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
						'</div>'+
						'<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
					);

					
					$(upload_button_name).prop('disabled', 'disabled');
				} 
				else 
				{
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
	
	}

	function removePic(pic, upload_button_name) 
	{
		$(pic).parent().remove();
		$(upload_button_name).prop('disabled', false);
	}
	
	function searchFun(){
		$('#userName').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));
	}
	
	</script>

  </head>
  
  <body>
<form method="post" id="addForm">
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
				<%-- 
				<input name="formData.name" class="easyui-validatebox"
					
					data-options="required:true,validType:'userName'"
					maxlength="100" />
				--%>
				<input type="hidden" id="userId" name="formData.userId" >
				<select class="easyui-combogrid" id="userName" name="formData.name" style="width:155px;"
						data-options="
						        	required:true,
						        	editable:false,
						            panelWidth:600,
						            idField:'userName',
						            textField:'userName',
						            pagination : true,
						            url:'${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_dataGridFor.action',
						            columns:[[
						                {field:'userName',title:'姓名',width:100,align:'center'},
						                {field:'fullName',title:'所属机构',width:500,align:'center'}
						            ]],
						            toolbar: $('#toolbar'),
						            onSelect:function(rowIndex, rowData){
						            	/*
						            	$('#sex').combobox('setValue',rowData.sex);
						            	$('#birthday').datebox('setValue',rowData.birthday);
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            
						            
						            	$('#departName').prop('value',rowData.departName);
						            	$('#gradeName').prop('value',rowData.gradeName);
						            	$('#className').prop('value',rowData.className);
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#userId').prop('value',rowData.userId);
						            	$('#studentId').prop('value',rowData.studentnumber);
						            	
						            	*/
						            	
						            	//$('#userName').prop('value',rowData.userName);
						            	
						            	
						            	//基本信息
						            	$('#sex').combobox('setValue', rowData.sex);
						            	$('#birthday').datebox('setValue',rowData.birthday);
						            	$('#provice').prop('value',rowData.residentialArea);
						            	$('#industry').prop('value',rowData.industryType);
						            	$('#work').prop('value',rowData.workUnit);
						            	$('#post').prop('value',rowData.position);
						            	$('#location').prop('value',rowData.resourceArea);
						            	//教育信息
						            	$('#school').prop('value',rowData.schoolName);
						            	$('#depart').prop('value',rowData.departName);
						            	$('#grade').prop('value',rowData.entranceTime);
						            	$('#major').prop('value',rowData.majorName);
						            	$('#studentnumber').prop('value',rowData.studentnumber);
						            	$('#leaveYear').prop('value',rowData.graduationTime);
						            	$('C').prop('value',rowData.studentType);
						            	//联系信息
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#officePhone').prop('value',rowData.workTel);
						            	$('#familyPhone').prop('value',rowData.residentialTel);
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
						            	$('#userId').prop('value',rowData.userId);
						            }
				"></select>
				
				
			</td>
			<th>
				性别
			</th>
			<td>		
				

				<select id="sex" readonly="readonly" name="formData.sex" class="easyui-combobox" style="width:155px" 
					data-options="required:true, editable:false"
				>
				<option value="男">男</option>
				<option value="女">女</option>
				</select>	

			</td>
			<th>
				生日
			</th>
			<td>
				<input id="birthday" readonly="readonly" name="formData.birthday" class="easyui-datebox" data-options="required:true, editable:false" />
				
			</td>
			
		</tr>
		
		<tr>
			<th>
				所在地
			</th>
			<td>
				<input id="location" readonly="readonly" name="formData.location" class="easyui-validatebox" style="width:155px" maxlength="100" /> 
			</td>
			<th>
				所属行业
			</th>
			<td>
				<input id="industry" readonly="readonly" name="formData.industry" class="easyui-validatebox"

					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				工作单位
			</th>
			<td>
				<input id="work"  readonly="readonly" name="formData.unit" class="easyui-validatebox"

					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			
		</tr>
		
		
		<tr>
			<th>
				职务/昵称
			</th>
			<td>
				<input id="post" readonly="readonly" name="formData.position" class="easyui-validatebox"
					
					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				取卡方式
			</th>
			<td>
				<select name="formData.takeWay" class="easyui-combobox" style="width:155px" 
					data-options="required:true, editable:false"
				>
				<option value="自取">自取</option>
				<option value="邮寄">邮寄</option>
				</select>	
				
			</td>
			<th>
				申请时间
			</th>
			<td>
				<input name="formData.applyTime" class="easyui-datebox" data-options="required:true, editable:false" />
				
			</td>
			
		</tr>
		
		<%-- 
		<tr>
		
		<th>
				审核状态
			</th>
			<td>
				<select name="formData.status" class="easyui-combobox" style="width:155px" 
					data-options="required:true, editable:false"
				>
				<option value="0">未审核</option>
				<option value="1">通过</option>
				<option value="2">未通过</option>
				</select>	

			</td>
			<th>
				审核人
			</th>
			<td>
				<input name="formData.check" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				审核时间
			</th>
			<td>
				<input name="formData.checkTime" class="easyui-datebox" data-options="required:true, editable:false" />
				
			</td>
		
		</tr>
		--%>
		
		<tr>
			<th>
				建议
			</th>
			<td colspan="5">
				<textarea id="templateContent" rows="5" cols="100"
					data-options="required:true,validType:'customRequired'"
					name="formData.suggest"></textarea>
			</td>
			
		</tr>
		<%-- 
		<tr>
			<th>
				审核意见
			</th>
			<td colspan="5">
				<textarea id="templateContent" rows="5" cols="100"
					data-options="required:true,validType:'customRequired'"
					name="formData.opinion"></textarea>
			</td>
		</tr>
		--%>
	</table>
	</fieldset>
	
	<br>
	
	<fieldset>
    <legend>
            教育信息
    </legend>

	<table class="ta001">
	
	<tr>
			
			<th>
				所在院系
			</th>
			<td>					
				<input id="depart" name="formData.department" readonly="readonly" type="text" />
				
				<%--
				
				<input id="depart" name="formData.department" style="width: 155px;" class="easyui-combobox"
					data-options="  
						required:true,
						valueField: 'deptId',  
						textField: 'deptName',  
						editable:false,
						url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',  
						onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;  
							$('#grade').combobox('clear');
							$('#classes').combobox('clear');
							$('#classes').combobox('loadData',[]);
							$('#grade').combobox('reload', url);  
				}" />
				--%>
			</td>
			
			<th>
				入学年份
			</th>
			<td>
				<input id="grade" readonly="readonly" name="formData.joinYear" readonly="readonly" type="text" />
			</td>
			
			<th>
				专业
			</th>
			<td>
				<input id="major" readonly="readonly" name="formData.specialty" readonly="readonly" type="text">
			</td>
	</tr>
	
	<tr>
			<th>
				学号
			</th>
			<td>
				<input id="studentnumber" readonly="readonly" name="formData.studentId" class="easyui-validatebox"

					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				离校年份
			</th>
			<td>
				<select id="leaveYear" readonly="readonly" name="formData.outYear" class="easyui-combobox" style="width:155px" 
					data-options="required:true, editable:false"
				>
				<c:forEach var="i" begin="1900" end="2100" varStatus="status">
				<option value="${status.index}">${status.index}年</option>
				</c:forEach>
				</select>	
				
				
			</td>
			
			<th>
				学历层次
			</th>
			<td>
				<select id="leaveYear" readonly="readonly" name="formData.background" class="easyui-combobox" style="width:155px" 
					data-options="required:true, editable:false">
				<option value="专科">专科</option>
				<option value="本科">本科</option>
				<option value="硕士">硕士</option>
				<option value="博士">博士</option>
				</select>	
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
				<input id="mailingAddress" readonly="readonly" name="formData.contactAddress" class="easyui-validatebox" />
				<%--
				<input name="formData.contactAddress" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
				--%>
			</td>
			<th>
				邮编
			</th>
			<td>
				<input readonly="readonly" name="formData.postCode" class="easyui-validatebox"

					data-options="validType:'tel'"
					maxlength="6" />
			</td>
			
			<th>
				办公电话
			</th>
			<td>
				<input id="officePhone" readonly="readonly" name="formData.officePhone" class="easyui-validatebox"

					data-options="validType:'tel'"
					maxlength="13" />
			</td>
	</tr>
	
	<tr>
			<th>
				家庭电话
			</th>
			<td>
				<input id="familyPhone" readonly="readonly" name="formData.homePhone" class="easyui-validatebox"

					data-options="validType:'tel'"
					maxlength="13" />
			</td>
			<th>
				手机号
			</th>
			<td>
				<input id="telId" readonly="readonly" name="formData.mobilePhone" class="easyui-validatebox" />
				<%--
				<input name="formData.mobilePhone" class="easyui-validatebox"

					data-options="required:true,validType:'telePhone'"
					maxlength="11" />
				--%>
			</td>
			
			<th>
				邮箱
			</th>
			<td>
				<input id="email" readonly="readonly" name="formData.emailBox" class="easyui-validatebox" />
				<%--
				<input name="formData.emailBox" class="easyui-validatebox"

					data-options="required:true,validType:'email'"
					maxlength="100" />
				--%>
			</td>
	</tr>
	
	</table>
	</fieldset>
	
	<br>
	
	<fieldset>
    <legend>
            图片信息
    </legend>

	<table class="ta001">
		
		
		
		<tr>
			<th>
				个人照片
			</th>
			<td>
				<input type="button" id="personal_upload_button" value="上传个人照片">
			</td>
			<td>
				<div id="personalPic"></div>
			</td>
			
		</tr>
		
		
		<tr>
			<th>
				证件照片
			</th>
			<td>
				<input type="button" id="credentials_upload_button" value="上传证件照片">
			</td>
			<td>
				<div id="credentialsPic"></div>
			</td>
		</tr>

	</table>
	</fieldset>
</form>

	<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										姓名：
									</th>
									<td>
										<input name="userInfo.userName" style="width: 150px;" />
									</td>
									<th>
										电话号码：
									</th>
									<td>
										<input name="userInfo.telId" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-zoom',plain:true"
											onclick="searchFun();">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>

</body>
</html>