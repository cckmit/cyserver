<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
			var submitForm = function($dialog, $grid, $pjq) {
				if ($('form').form('validate')) {
					$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!setBelongDept.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$grid.datagrid('reload');
								$grid.datagrid('unselectAll');
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else {
								$pjq.messager.alert('提示', result.msg, 'error');
							}
						},
						beforeSend:function(){
							parent.$.messager.progress({
								text : '数据提交中....'
							});
						},
						complete:function(){
							parent.$.messager.progress('close');
						}
					});
				}
			}
			
			function refrensh(){
				var panel = parent.$('#mainTabs').tabs('getSelected').panel('panel');
				var frame = panel.find('iframe');
				try {
					if (frame.length > 0) {
						for (var i = 0; i < frame.length; i++) {
							frame[i].contentWindow.document.write('');
							frame[i].contentWindow.close();
							frame[i].src = frame[i].src;
						}
						if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
							try {
								CollectGarbage();
							} catch (e) {
							}
						}
					}
				} catch (e) {
				}
			}
		</script>
	</head>

	<body>
		<form method="post" id="addDeptForm">
			<input name="dept.deptId" type="hidden" value="${param.id}">
			<input name="dept.deptIds" type="hidden" value="${param.deptIds}">
			<fieldset>
				<legend>
					设置归属
				</legend>
				<table class="ta001">
					<tr>
						<th>
							院系名称
						</th>
						<td>
							<select id="dept_belongDeptId" name="dept.belongDeptId"
									class="easyui-combotree"
									data-options="
                                        required:true,
                                        editable:false,idField:'id',state:'open',textField:'text',
                                        parentField:'pid',
                                        url:'${pageContext.request.contextPath}/dept/deptAction!getCurrDeptTree.action',
                                        onBeforeSelect: function(node) {
                                            // 判断是否是叶子节点
                                            var isLeaf = $(this).tree('isLeaf', node.target);
                                            if (!isLeaf) {
                                                $.messager.show({
                                                    msg: '请选择学院！'
                                                });
                                                // 返回false表示取消本次选择操作
                                                return false;
                                            }
                                        }
                                    "
									style="width: 200px;">
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>
