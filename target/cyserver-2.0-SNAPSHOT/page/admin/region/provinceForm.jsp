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

		$(function() {
		$('#countryId').combobox({
			valueField: 'id',
		    textField: 'countryName',
		    editable: false,
			url : '${pageContext.request.contextPath}/country/countryAction!doNotNeedSecurity_getCountry2ComboBox.action',
//			onLoadSuccess : function(data) {
//				alert(JSON.stringify(data));
//			}

		});

		if ($('#id').val() != '') {
            $.ajax({
				url : '${pageContext.request.contextPath}/region/regionAction!view.action?sid='+$('#id').val(),
                dataType : 'json',
                success : function(result) {
                    if (result.success) {
                        $('#provinceName').textbox('setValue',result.obj.name);
                        $('#orderId').textbox('setValue',result.obj.orderId);
//						$('#countryId').combobox('setValue',result.obj.countryId);
                        $('#countryId').combobox('setValue',result.obj.pid.substring(2)); //jiangling
                    } else {
                        alert('提示', result.msg, 'error');
                    }
                }
            });
        }
        if ($('#action').val() == 'view') {
            $('#provinceName').textbox('readonly',true);
            $('#orderId').textbox('readonly',true);
            $('#countryId').combobox('readonly',true);
        }
	});
		var submitForm = function($dialog, $grid, $pjq) {
			if($('#countryName').val()==''){
				$pjq.messager.alert('提示', '请设置省份名称', 'info');
				return false;
			}
			if($('#orderId').val()==''){
				$pjq.messager.alert('提示', '请设置排序', 'info');
				return false;
			}
			if($('#countryId').combobox('getValue')==''){
				$pjq.messager.alert('提示', '请设置所属国家', 'info');
				return false;
			}
			if ($('form').form('validate')) {
				var url;
				if ($('#id').val() != '') {
					url = '${pageContext.request.contextPath}/province/provinceAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/province/provinceAction!save.action';
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
     <form method="post" id="provinceForm">
		<input type="hidden" id="id" name="region.id" value="${param.id}" />
		<input type="hidden" id="hidden_countryId" value="${param.countryId}" />
		<input type="hidden" id="level" name="region.level" value="2" />
		<input type="hidden" id="action" value="${param.action}" />
     	<fieldset>
				<legend>
					省份信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							省份名称
						</th>
						<td>
							<%--<input id="provinceName" name="province.provinceName" class="easyui-validatebox easyui-textbox" />--%>
							<input id="provinceName" name="region.name" class="easyui-validatebox easyui-textbox" />
						</td>
						<th>
							排序
						</th>
						<td>
							<%--<input id="orderId" name="province.orderId" class="easyui-numberbox"  data-options="min:0" />--%>
							<input id="orderId" name="region.orderId" class="easyui-numberbox"  data-options="min:0" />
						</td>
					</tr>
					<tr>
						<th>
							所属国家
						</th>
						<td>

							<%--<input id="countryId" name="province.countryId" class="easyui-combobox"  data-options="required:true" />--%>
							<input id="countryId" name="region.pid" class="easyui-combobox"/>
						</td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>