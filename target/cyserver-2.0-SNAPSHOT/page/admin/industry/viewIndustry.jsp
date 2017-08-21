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
    
    <title>窗友校友智能管理与社交服务平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="窗友校友智能管理与社交服务平台">
	<meta http-equiv="description" content="窗友校友智能管理与社交服务平台">
	<jsp:include page="../../../inc.jsp"></jsp:include>
  </head>
<script type="text/javascript">
	$(function() {
		$.post('${pageContext.request.contextPath}/industry/industryAction!view.action?industry.code=${param.code}',
			function(data) {
			data = eval('('+data+')');
			$('#industryForm').form('load',data);
		});
	});
</script>
<body>
     <form method="post" id="industryForm">
     	<fieldset>
				<legend>
					行业基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							行业代码
						</th>
						<td>
							<input name="code" class="easyui-textbox" maxlength="20" data-options="readonly:true" />
						</td>
						<th>
							行业名称
						</th>
						<td>
							<input name="value" class="easyui-textbox" maxlength="20" data-options="readonly:true" />
						</td>
					</tr>
					<tr>
						<th>
							上级节点
						</th>
						<td>
							<input name="parentCode" class="easyui-textbox" maxlength="20" data-options="readonly:true" />
						</td>
						<th>
							排序
						</th>
						<td>
							<input name="orderNo" class="easyui-textbox" maxlength="20" data-options="readonly:true" />
						</td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>
