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
	
	var actionName = "campusCardAction";
	var actionUrl = "${pageContext.request.contextPath}/page/admin/campusCard/";
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
				商户名称
			</th>
			<td>
				<input name="formData.name" class="easyui-validatebox"
					
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业范围
			</th>
			<td>					
				<input name="formData.businessScope" class="easyui-validatebox"
					
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				所在地
			</th>
			<td>
				<select id="location" name="formData.location"  class="easyui-combogrid" style="width:155px" data-options=" 
						required:true,
						panelWidth: 180,
						multiple: false,
						idField: 'cityName',
						textField: 'cityName',
						url: '${pageContext.request.contextPath}/page/admin/alumniCard/alumniCardAction!doNotNeedSecurity_getNationalOfCity.action',
						method: 'get',
						columns: [[
							{field:'cityName',title:'城市名称'}
						]],
						fitColumns: true,
						editable:false
					">
				</select>
			</td>
			
		</tr>
		
		<tr>
			<th>
				所属行业
			</th>
			<td>					
				<input name="formData.industry" class="easyui-validatebox"
					
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				法人代表
			</th>
			<td>
				<input name="formData.legal" class="easyui-validatebox"

					data-options="required:true,validType:'userName'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData.unitTel" class="easyui-validatebox"

					data-options="required:true,validType:'telePhone'"
					maxlength="11" />
			</td>
			
			
		</tr>
		
		
		<tr>
			
			<th>
				登记机关
			</th>
			<td>
				<input name="formData.registrationAuthority" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业执照号
			</th>
			<td>
				<input name="formData.businessLicenseNo" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				联系地址
			</th>
			<td>
				<input name="formData.unitAddress" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
		</tr>
		
		
		
		
		
	</table>
	</fieldset>
	
	<br>
	
	<fieldset>
    <legend>
       	 优惠计划信息
    </legend>

	<table class="ta001">
	
	<tr>
			
			<th>
				折扣优惠
			</th>
			<td>
				<input name="formData.discountPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'price'"
					maxlength="100" />
			</td>
			<th>
				礼品优惠
			</th>
			<td>
				<input name="formData.giftPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				其它优惠
			</th>
			<td>
				<input name="formData.otherPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
	</tr>
	
	<tr>
			<th>
				会员优惠
			</th>
			<td colspan="5">
				<input name="formData.vipPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
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
				联系人
			</th>
			<td>
				<input name="formData.contact" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData.contactTel" class="easyui-validatebox"

					data-options="required:true,validType:'telePhone'"
					maxlength="11" />
			</td>
			
			<th>
				传真
			</th>
			<td>
				<input name="formData.fax" class="easyui-validatebox"

					data-options="required:true,validType:'tel'"
					maxlength="13" />
			</td>
	</tr>
	
	<tr>
			
			
			<th>
				邮箱
			</th>
			<td colspan="5">
				<input name="formData.emailBox" class="easyui-validatebox"

					data-options="required:true,validType:'email'"
					maxlength="100" />
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
  </body>
</html>