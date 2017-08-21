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
	function submitForm($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			$
					.ajax({
						url : '${pageContext.request.contextPath}/alumni/alumniAction!insert.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$grid.datagrid('reload');
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else {
								$pjq.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend : function() {
							parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete : function() {
							parent.$.messager.progress('close');
						}
					});
		}
	};
</script>
</head>

<body>
	<form method="post">
		<fieldset>
			<legend>基本信息 </legend>
			<table class="ta001">
				<tr>
					<th>组织名称</th>
					<td><input name="alumni.alumniName" class="easyui-validatebox"
						style="width: 150px;"
						data-options="required:true,validType:'customRequired'" />
						<input name="alumni.status" type="hidden" value="1">
						<input name="alumni.checkFlag" type="hidden" value="1">	
					</td>
				</tr>
				<tr>
					<th>所属地区</th>
					<td><%--<input class="easyui-combobox" name="country" id="country"
						data-options="
	                    url:'${pageContext.request.contextPath}/country/countryAction!doNotNeedSecurity_getCountry2ComboBox.action',
	                    method:'post',
	                    valueField:'countryName',
	                    textField:'countryName',
	                    editable:false,
	                    prompt:'国家',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#country').combobox('clear');
			                	$('#province').combobox('clear');
			                	$('#province').combobox('loadData',[]);
			                	$('#city').combobox('clear');	
								$('#city').combobox('loadData',[]);
								$('#area').combobox('clear');	
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId='+rec.id; 
							$('#province').combobox('clear');	
							$('#province').combobox('reload', url);
							$('#city').combobox('clear');	
							$('#city').combobox('loadData',[]);
							$('#area').combobox('clear');	
							$('#area').combobox('loadData',[]);
						}
                    	">
						&nbsp; --%><input class="easyui-combobox" name="province" id="province"
						data-options="
						required:true,
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
						<textarea rows="8" cols="100" name="alumni.introduction"></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
