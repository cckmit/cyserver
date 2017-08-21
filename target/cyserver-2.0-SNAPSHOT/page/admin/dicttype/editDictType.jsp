<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
            var dictTypeId='${param.id}';
            $(function() {

                if (dictTypeId > 0) {
                    $.ajax({
                        url:'${pageContext.request.contextPath}/dicttype/dictTypeAction!getByDictTypeId.action',
                        data :{'dictTypeId':dictTypeId},
                        dataType:'json',
                        success : function(result){
                            if (result.dictTypeId != undefined) {
                                $('form').form('load', {
                                    'dictTypeObj.dictTypeId' : result.dictTypeId,
                                    'dictTypeObj.dictTypeName' : result.dictTypeName,
                                    'dictTypeObj.dictTypeValue' : result.dictTypeValue
                                });

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
                }
            })

			function submitForm($dialog, $grid, $pjq)
			{
				if ($('form').form('validate')) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dicttype/dictTypeAction!updateDictType.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result)
						{
							if (result.success)
							{
								$grid.tree('reload');
                                $dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else
							{
								$pjq.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend : function()
						{

                            $pjq.messager.progress({
								text : '数据提交中....'
							});
						},
						complete : function()
						{
                            $pjq.messager.progress('close');
						}
					});
				}
			};
		</script>
	</head>
	<body>
	<form method="post" class="form">
			<fieldset>
				<legend>
					字典类别
				</legend>
				<table class="ta001">
					<tr>
						<th>
							字典类别名称：
						</th>
						<td colspan="3">
							<input name="dictTypeObj.dictTypeId" type="hidden" value="${dictTypeObj.dictTypeId}">
							<input name="dictTypeObj.dictTypeName" id="dictTypeName"  class="easyui-validatebox" data-options="validType:'customRequired',required:'true'" />
						</td>
					</tr>
					<tr>
						<th>
							字典类别值：
						</th>
						<td colspan="3">
							<input name="dictTypeObj.dictTypeValue" id="dictTypeValue"  class="easyui-validatebox" data-options="validType:'customRequired',required:'true'" />
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
