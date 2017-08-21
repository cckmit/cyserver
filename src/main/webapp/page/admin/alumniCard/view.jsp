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
		$(function(){
			$('#status').combobox("setValue", "${formData.status}");
		});
	</script>
  </head>
  
  <body>
<form method="post" id="viewForm">
	<input name="formData.id" type="hidden" value="${formData.id}" >
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
				<input value="${formData.name}" disabled="disabled">
			</td>
			<th>
				性别
			</th>
			<td>		
				<select id="sex" readonly="readonly" name="formData.sex" class="easyui-combobox" style="width:155px"  disabled="disabled"
					data-options="required:true, editable:false"
				>
				<option value="男"<c:if test="${formData.sex=='0'}"> selected</c:if>>男</option>
				<option value="女"<c:if test="${formData.sex=='1'}"> selected</c:if>>女</option>
				</select>	

			</td>
		</tr>
		
		<tr>
			<th>
				所在地
			</th>
			<td>
				<input value="${formData.address}" disabled="disabled">
			</td>
			<th>
				工作单位
			</th>
			<td>
				<input readonly="readonly" name="formData.unit" class="easyui-validatebox" disabled="disabled"
					value="${formData.workUnit}"
					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			
		</tr>
		
		
		<tr>
			<th>
				职务
			</th>
			<td>
				<input readonly="readonly" name="formData.position" class="easyui-validatebox" disabled="disabled"
					value="${formData.position}"
					data-options="validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				取卡方式
			</th>
			<td>
				<select name="formData.takeWay" class="easyui-combobox" style="width:155px"  disabled="disabled"
					data-options="required:true, editable:false"
				>
				<option value="0"<c:if test="${formData.takeWay=='0'}"> selected</c:if>>自取</option>
				<option value="1"<c:if test="${formData.takeWay=='1'}"> selected</c:if>>邮寄</option>
				</select>	
				
			</td>
		</tr>
		<tr>
			<th>
				申请时间
			</th>
			<td colspan="3">
				<input name="formData.applyTime" class="easyui-datebox" value="${formData.applyTime}"  disabled="disabled"/>
				
			</td>
		</tr>
		<tr>
			<th>
				建议
			</th>
			<td colspan="3">
				<textarea id="templateContent" rows="5" cols="100" disabled="disabled"
					data-options="required:true,validType:'customRequired'"
					name="formData.suggest">${formData.suggest}</textarea>
			</td>
		</tr>
	</table>
	</fieldset>
	
	<br>
	
	<fieldset>
        <legend>教育信息</legend>
		<c:forEach var="x" items="${formData.cardExtList}">
			<table class="ta001">
				<tr>
					<th>学位</th>
					<td>${x.degree }</td>
					<th>院系</th>
					<td>${x.depart }</td>
				</tr>
				<tr>
					<th>年级</th>
					<td>${x.startTime.substring(0, 4) }</td>
					<th>专业</th>
					<td>${x.major }</td>
				</tr>
				<tr>
					<th>学号</th>
					<td>${x.studentNumber }</td>
					<th>班级</th>
					<td>${x.clazz }</td>
				</tr>
			</table>
		</c:forEach>

    </fieldset>
	
	<br>
	
	<fieldset>
    <legend>
            联系信息
    </legend>

	<table class="ta001">
	<tr>
			<th>
				电话号码
			</th>
			<td>
				<input class="easyui-validatebox"
					value="${formData.phone}" disabled="disabled"/>
			</td>
			<th>
				电子邮箱
			</th>
			<td>
				<input readonly="readonly" name="formData.postCode" class="easyui-validatebox" disabled="disabled"
					value="${formData.email}"
					/>
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
	<tr>
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
				<input value="${formData.checkTime}" disabled="disabled">
			</td>
			</tr>
			<tr>
			<th>
				审核意见
			</th>
			<td>
				<textarea cols="100" name="formData.opinion" disabled="disabled">${formData.opinion}</textarea>
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