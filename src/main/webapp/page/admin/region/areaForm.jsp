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
							$('#areaName').textbox('setValue',result.obj.name);
							$('#postCode').textbox('setValue',result.obj.postCode);
						} else {
							alert('提示', result.msg, 'error');
						}
					}
				});
			}
			//view
			if ($('#action').val() == 'view') {
				$('#areaName').textbox('readonly',true);
				$('#postCode').textbox('readonly',true);
			}
		});

		var submitForm = function($dialog, $grid, $pjq) {
			if($('#areaName').val()==''){
				$pjq.messager.alert('提示', '请设置县区名称', 'info');
				return false;
			}
			if($('#postCode').val()==''){
				$pjq.messager.alert('提示', '请设置邮政编码', 'info');
				return false;
			}
			if($('#cityId').combobox('getValue')==''){
				$pjq.messager.alert('提示', '请设置所属城市', 'info');
				return false;
			}
			if ($('form').form('validate')) {
				var url;
				if ($('#id').val() != '') {
					url = '${pageContext.request.contextPath}/area/areaAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/area/areaAction!save.action';
				}
				$.ajax({
					url : url,
					data :$('form').serialize(),
					dataType : 'json',
					success : function(result) {
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
     <form method="post" id="areaForm">
		<input type="hidden" id="id" value="${param.id}" />
		<input type="hidden" id="level" name="region.level" value="4" />
		 <input type="hidden" id="action" value="${param.action}" />
     	<fieldset>
				<legend>
					县区信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							县区名称
						</th>
						<td>
							<%--<input id="areaName" name="area.areaName" class="easyui-validatebox easyui-textbox" />--%>
							<input id="areaName" name="region.areaName" class="easyui-validatebox easyui-textbox" />
						</td>
						<th>
							邮政编码
						</th>
						<td>
							<%--<input id="postCode" name="area.postCode" class="easyui-textbox"  data-options="min:0" />--%>
							<input id="postCode" name="region.postCode" class="easyui-textbox"  data-options="min:0" />
						</td>
					</tr>
					<tr>
						<th align="right">
                            所属城市
                        </th>
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
                                        $('#cityId').combobox('clear');
                                    }
                                }],
                                onSelect: function(rec){
                                    var url = '${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId='+rec.id;
                                    $('#provinceId').combobox('clear');
                                    $('#cityId').combobox('clear');
                                    $('#provinceId').combobox('reload', url);
                                }
                                ">
                         <input class="easyui-combobox" id="provinceId" style="width: 150px;"
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
                                            $('#cityId').combobox('clear');
                                        }
                                    }],
                                    onSelect: function(rec){
                                          var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
                                          $('#cityId').combobox('clear');
                                          $('#cityId').combobox('reload', url);
                                      }
                                    ">
                          <input class="easyui-combobox" name="area.cityId" id="cityId" style="width: 150px;"
                                    data-options="
                                    method:'post',
                                    valueField:'id',
                                    textField:'cityName',
                                    editable:false,
                                    prompt:'城市',
                                    icons:[{
                                        iconCls:'icon-clear',
                                        handler: function(e){
                                            $('#cityId').combobox('clear');
                                        }
                                    }]
                                    ">
                                    </td>
                                </td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>