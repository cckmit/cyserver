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
		url : '${pageContext.request.contextPath}/alumni/alumniAction!getByAlumniId.action',
		data : $('form').serialize(),
		dataType : 'json',
		success : function(result) {
			if (result.alumniId != undefined) {
				$('form').form('load', {
					'alumni.alumniName' : result.alumniName
				});
				$('#region').text(result.region);
				$('#introduction').text(result.introduction);
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
			<legend>基本信息 </legend>
			<table class="ta001">
				<tr>
					<th>组织名称</th>
					<td><input disabled="disabled" name="alumni.alumniName" class="easyui-validatebox"
						style="width: 150px;"
						data-options="required:true,validType:'customRequired'" />
						<input name="alumni.alumniId" value="${param.id}" type="hidden">
					</td>
				</tr>
				<tr>
					<th>所属地区</th>
					<td>
						<span id="region" name="region"></span>
					</td>
				</tr>
				<!--  
				<tr>
					<th>所属行业</th>
					<td><input class="easyui-combobox" name="industry1" id="industry1"
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
			                	$('#industry1').combobox('clear');
			                	$('#industry2').combobox('clear');
			                	$('#industry2').combobox('loadData',[]);
			                	$('#industry3').combobox('clear');	
								$('#industry3').combobox('loadData',[]);
								$('#industry4').combobox('clear');	
								$('#industry4').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code; 
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
					<th>兴趣爱好</th>
					<td>
						<input name="alumni.hobby" style="width: 700px;" class="easyui-validatebox">
					</td>
				</tr>
				-->
				<tr>
					<th>简介</th>
					<td>
						<textarea disabled="disabled" id="introduction"  rows="8" cols="100" name="alumni.introduction"></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
