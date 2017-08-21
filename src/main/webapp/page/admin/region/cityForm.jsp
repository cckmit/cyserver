<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
		<%--add by jiangling--%>
		$(function() {

			if ($('#id').val() != '') {
				$.ajax({
					url : '${pageContext.request.contextPath}/region/regionAction!view.action?sid='+$('#id').val(),
					dataType : 'json',
					success : function(result) {
//						alert(JSON.stringify(result));
						if (result.success) {
							$('#cityName').textbox('setValue',result.obj.name);
							$('#areaCode').textbox('setValue',result.obj.areaCode);
						} else {
							alert('提示', result.msg, 'error');
						}
					}
				});
			}
			//view
			if ($('#action').val() == 'view') {
				$('#cityName').textbox('readonly',true);
				$('#areaCode').textbox('setValue',result.obj.areaCode);
			}
		});

			var submitForm = function($dialog, $grid, $pjq) {
			if($('#cityName').val()==''){
				$pjq.messager.alert('提示', '请设置城市名称', 'info');
				return false;
			}

			if($('#areaCode').val()==''){
				$pjq.messager.alert('提示', '请设置城市区号', 'info');
				return false;
			}

			if($('#provinceId').combobox('getValue')==''){
				$pjq.messager.alert('提示', '请设置所属省份', 'info');
				return false;
			}

			if($('#type').combobox('getValue')==''){
				$pjq.messager.alert('提示', '请设置城市级别', 'info');
				return false;
			}

			if ($('form').form('validate')) {
				var url;
				if ($('#id').val() != '') {
					url = '${pageContext.request.contextPath}/city/cityAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/city/cityAction!save.action';
				}
				$.ajax({
					url : url,
					data :$('form').serialize(),
					dataType : 'json',
					success : function(result) {
						alert(JSON.stringify(result));
						if (result.success) {
							$dialog.dialog('destroy');
							$grid.treegrid('options').url = '${pageContext.request.contextPath}/region/regionAction!dataGrid.action?pid=-1'
							$grid.treegrid('reload');
							$pjq.messager.alert('提示', result.msg, 'info');
						} else {
							$pjq.messager.alert('提示', result.msg, 'error');
						}
					}
				});
			}
		};
	</script>
  </head>
  
  <body>
     <form method="post" id="cityForm">
		<input type="hidden" id="id" value="${param.id}" />
		<input type="hidden" id="level" name="city.level" value="3" />
		 <input type="hidden" id="action" value="${param.action}" />
     	<fieldset>
				<legend>
					城市信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							城市名称
						</th>
						<td>
							<input id="cityName" name="region.name" class="easyui-validatebox easyui-textbox"  />
						</td>
						<th>
							城市区号
						</th>
						<td>
							<input id="areaCode" name="region.areaCode" class="easyui-textbox"  data-options="min:0" />
						</td>
					</tr>
					<tr>
					<th align="right">
                        所在省份
                    </th>
						<%--?????--%>
                    <td>
                    <input class="easyui-combobox" id="countryId" style="width: 150px;"
						data-options="
	                    method:'post',
						url:'${pageContext.request.contextPath}/country/countryAction!doNotNeedSecurity_getCountry2ComboBox.action',
	                    valueField:'id',
	                    textField:'countryName',
	                    editable:false,
	                    prompt:'国家',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#countryId').combobox('clear');
			                	$('#provinceId').combobox('clear');
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId='+rec.id;
							<%--alert(rec.id);--%>
							$('#provinceId').combobox('clear');
							$('#provinceId').combobox('reload', url);
						}
                    	">
                    	<input class="easyui-combobox" name="city.provinceId" id="provinceId" style="width: 150px;"
                            data-options="
                            method:'post',
                            valueField:'id',
                            textField:'provinceName',
                            editable:false,
                            prompt:'省份',
                            icons:[{
                                iconCls:'icon-clear',
                                handler: function(e){
                                    $('#provinceId').combobox('clear');
                                }
                            }]
                            ">
                    	    </td>
						</td>
						<th>
                            级别
                        </th>
                        <td>
                            <select id="type" class="easyui-combobox" name="city.type">
                                <option value="">--请选择级别--</option>
                                <option value="0">普通城市</option>
                                <option value="1">省会城市</option>
                                <option value="2">热门城市</option>
                            </select>
                        </td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>