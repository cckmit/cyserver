<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
        if ($('#serviceId').val() > 0) {	
            $.ajax({
                url: '${pageContext.request.contextPath}/serv/servAction!getByIdx.action',
                data: $('form').serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result.id != undefined) {
                        $('form').form('load', {
                            'serv.id': result.id,
                            'serv.content': result.content,
                            'serv.region': result.region,
                            'serv.auditStatus': result.auditStatus,
                            'serv.auditOpinion': result.auditOpinion
                        });
                        
                        for(var i=0;i<result.picList.length;i++){ 
                        	var p = result.picList[i];
                        	$('#servPic').append('<div style="float:left;width:180px;"><img src="'+p.pic+'" width="150px" height="150px"/><input type="hidden" name="pics" id="eu" value="'+p.pic+'"/></div>');
                        }
                        
                        if(result.type==0) {
                        	$('#auditDiv').hide();
                        }
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
<form method="post" id="addServForm">
<input name="serv.id" type="hidden" id="serviceId" value="${param.id}">
	<fieldset>
		<legend>
			基本信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					内容
				</th>
				<td colspan="3">
					<textarea id="content" name="serv.content"
						style="width: 700px; height: 160px;" disabled="disabled"></textarea>
				</td>
			</tr>
			
			<tr>
				<th>
					图片
				</th>
				<td colspan="3">
					<div id="servPic"></div>
				</td>
			</tr>
			
			<tr>
				<th>
					地域
				</th>
				<td colspan="3">
					<input name="serv.region" style="width: 500px;" disabled="disabled" />&nbsp;&nbsp;&nbsp;&nbsp;( 地域为空表示全国 )
				</td>
			</tr>
		</table>
	</fieldset>
	
	<div id="auditDiv">
	<fieldset>
		<legend>
			审核信息
		</legend>
		<table class="ta001">
			<tr>
				<th>审核状态</th>
				<td>
					<select class="easyui-combobox" data-options="editable:false" name="serv.auditStatus" style="width: 150px;" disabled="disabled">
						<option value="0">待审核</option>
						<option value="1">通过</option>
						<option value="2">不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>审核意见</th>
				<td>
					<textarea rows="3" cols="80" name="serv.auditOpinion" disabled="disabled"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	</div>
</form>
  </body>
</html>