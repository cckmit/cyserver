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
	
	$(function(){
			$('#status').combobox('setValue',${formData.status})
		})
	
	</script>

  </head>
  
  <body>
<form method="post" id="viewForm">
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
					disabled="disabled"
					value="${formData.name}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业范围
			</th>
			<td>					
				<input name="formData.businessScope" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.businessScope}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				所在地
			</th>
			<td>
					${formData.location}
			</td>
			
		</tr>
		
		<tr>
			<th>
				所属行业
			</th>
			<td>					
				<input name="formData.industry" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.industry}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				法人代表
			</th>
			<td>
				<input name="formData.legal" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.legal}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData.unitTel" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.unitTel}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			
		</tr>
		
		
		<tr>
			
			<th>
				登记机关
			</th>
			<td>
				<input name="formData.registrationAuthority" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.registrationAuthority}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业执照号
			</th>
			<td>
				<input name="formData.businessLicenseNo" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.businessLicenseNo}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				联系地址
			</th>
			<td>
				<input name="formData.unitAddress" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.unitAddress}"
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
					disabled="disabled"
					value="${formData.discountPreferential}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				礼品优惠
			</th>
			<td>
				<input name="formData.giftPreferential" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.giftPreferential}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				其它优惠
			</th>
			<td>
				<input name="formData.otherPreferential" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.otherPreferential}"
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
					disabled="disabled"
					value="${formData.vipPreferential}"
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
					disabled="disabled"
					value="${formData.contact}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData.contactTel" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.contactTel}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				传真
			</th>
			<td>
				<input name="formData.fax" class="easyui-validatebox"
					disabled="disabled"
					value="${formData.fax}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
	</tr>
	
	<tr>
			
			
			<th>
				邮箱
			</th>
			<td colspan="5">
				<input name="formData.emailBox" class="easyui-validatebox"	
					disabled="disabled"
					value="${formData.emailBox}"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
	</tr>
	
	</table>
	</fieldset>
	
	<br>
		<fieldset>
	 <legend>
          审核信息
    </legend>
    <table class="ta001">
    		<th>
				审核状态
			</th>
			<td>
				<select class="easyui-combobox" disabled="disabled" data-options="editable:false" id="status" name="formData.status" style="width: 150px;">
					<option value="0">未审核</option>
					<option value="1">通过</option>
					<option value="2">不通过</option>
				</select>
			</td>
			</tr>
			<tr>
				<th>
				审核时间
			</th>
			<td>
				<input value="<fmt:formatDate value="${formData.checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" disabled="disabled">
			</td>
			</tr>
			<tr>
			<th>
				审核意见
			</th>
			<td>
				<textarea cols="100" name="formData.opinion" disabled="disabled">${formData.opinion}</textarea>
			</td>
    </table>
	</fieldset>
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
				<div id="personalPic">
					<img src="${formData.personalPic}" width="150px" height="150px">
				</div>
			</td>
		</tr>
		
		
		<tr>
			<th>
				证件照片
			</th>
			<td>
				<div id="credentialsPic">
					<img src="${formData.credentialsPic}"  width="150px" height="150px">
				</div>
			</td>
		</tr>

	</table>
	</fieldset>
</form>
  </body>
</html>