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
//		alert("countryId= "+$('#id').value);
//		alert("action= "+$('#action').value);
            if ($('#id').val() != '') {
                $.ajax({
                    <%--url : '${pageContext.request.contextPath}/country/countryAction!view.action?sid='+$('#id').val(),--%>
					url : '${pageContext.request.contextPath}/region/regionAction!view.action?sid='+$('#id').val(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            $('#countryName').textbox('setValue',result.obj.name);
                            $('#orderId').textbox('setValue',result.obj.orderId);
                        } else {
							$pjq.messager.alert('提示', result.msg, 'error');
                        }
                    }
                });
    		}
//    		view
    		if ($('#action').val() == 'view') {
    		     $('#countryName').textbox('readonly',true);
    		     $('#orderId').textbox('readonly',true);
    		}
    	});
		var submitForm = function($dialog, $grid, $pjq) {
			if($('#countryName').val()==''){
				$pjq.messager.alert('提示', '请设置国家名称', 'info');
				return false;
			}
			if($('#orderId').val()==''){
				$pjq.messager.alert('提示', '请设置排序', 'info');
				return false;
			}
			if ($('form').form('validate')) {
				var url;
				if ($('#id').val() != '') {
					url = '${pageContext.request.contextPath}/country/countryAction!update.action';
				} else {
					url = '${pageContext.request.contextPath}/country/countryAction!save.action';
				}
				$.ajax({
					url : url,
					data :$('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$dialog.dialog('destroy');
							$grid.treegrid('options').url = '${pageContext.request.contextPath}/region/regionAction!dataGrid.action?pid=-1';
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
     <form method="post" id="countryForm">
		<input type="hidden" id="id" name="region.id" value="${param.id}" />
		<input type="hidden" id="level" name="region.level" value="1" />
		<input type="hidden" id="action" value="${param.action}" />
     	<fieldset>
				<legend>
					国家信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							国家名称
						</th>
						<td>
							<input id="countryName" name="country.countryName" class="easyui-validatebox easyui-textbox"/>
						</td>
						<th>
							排序
						</th>
						<td>
							<input id="orderId" name="country.orderId" class="easyui-numberbox"  data-options="min:0" />
						</td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>
