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
	<script type="text/javascript">
		$(function() {
			$('#parentCode').combotree({
				url : '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode=-1',
				onBeforeExpand:function(node){
					console.log(node);
					//动态设置展开查询的url
					var url = '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode='+node.id;
					$('#parentCode').combotree('tree').tree('options').url = url;
					return true;
				}
			});

			if ($('#id').val() !='') {
				$.post('${pageContext.request.contextPath}/industry/industryAction!view.action?industry.code=${param.code}',
						function(data) {
							data = eval('('+data+')');
							$('#code').textbox('setValue',data.code);
							$('#value').textbox('setValue',data.value);
							$('#orderNo').numberbox('setValue',data.orderNo);
							$('#level').combobox('setValue',data.level);
							$('#parentCode').combotree('setValue',data.parentCode);
							$('#industryId').val(data.id);
						});
			}
		});

		var submitForm = function($dialog, $grid, $pjq) {
			if($('#code').val()==''){
				$pjq.messager.alert('提示', '请设置行业代码', 'info');
				return false;
			}
			if($('#value').val()==''){
				$pjq.messager.alert('提示', '请设置行业名称', 'info');
				return false;
			}
			if ($('form').form('validate')) {
				var url = '${pageContext.request.contextPath}/industry/industryAction!save.action';
				$.ajax({
					url : url,
					data :$('form').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$dialog.dialog('destroy');
							$grid.treegrid('options').url= '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode=-1';
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
     <form method="post" id="industryForm">
		<input type="hidden" id="id" value="${param.code}" />
		<input name="industry.id" type="hidden" id="industryId" />
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
							<input id="code" name="industry.code" class="easyui-validatebox easyui-textbox"  data-options="required:true,validType:['length[0,25]']" />
						</td>
						<th>
							行业名称
						</th>
						<td>
							<input id="value" name="industry.value" class="easyui-validatebox easyui-textbox"  data-options="required:true,validType:['length[0,25]']" />
						</td>
					</tr>
					<tr>
						<th>
							上级节点
						</th>
						<td>
							<input id="parentCode" name="industry.parentCode" class="easyui-combotree" />
						</td>
						<th>
							排序
						</th>
						<td>
							<input id="orderNo" name="industry.orderNo" class="easyui-numberbox"  data-options="min:0" />
						</td>
					</tr>
				</table>
			</fieldset>
     </form>
  </body>
</html>
