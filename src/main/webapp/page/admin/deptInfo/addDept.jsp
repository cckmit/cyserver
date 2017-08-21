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
                    url : '${pageContext.request.contextPath}/dept/deptAction!insert.action',
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
	<input name="dept.parentId" type="hidden" value="${param.id}">
	<fieldset>
		<legend>
			<c:if test="${param.id.length()==1}">
				学校基本信息
			</c:if>
			<c:if test="${param.id.length()==6}">
				院系基本信息
			</c:if>
			<c:if test="${param.id.length()==10}">
				年级基本信息
			</c:if>
			<c:if test="${param.id.length()==14}">
				班级基本信息
			</c:if>
		</legend>
		<table class="ta001">
			<c:if test="${param.id.length()==6 }">
				<tr>
					<th>
						院系名称
					</th>
					<td>
						<input name="dept.level" class="easyui-validatebox" value="2" type="hidden">
						<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
					</td>
				</tr>
				<tr>
					<th>
						排序序号
					</th>
					<td>
						<input name="dept.sort" class="easyui-numberbox" data-options="required:true,validType:'customRequired'" />
					</td>
				</tr>
			</c:if>
			<c:if test="${param.id.length()==10}">
				<tr>
					<th>
						年级名称
					</th>
					<td>
						<input name="dept.level" class="easyui-validatebox" value="3" type="hidden">
						<input name="dept.deptName" readonly="readonly" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" />
						级
					</td>
				</tr>
			</c:if>
			<c:if test="${param.id.length()==14}">
				<tr>
					<th>
						班级名称
					</th>
					<td>
						<input name="dept.level" class="easyui-validatebox" value="4" type="hidden">
						<input name="dept.deptName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'" />
					</td>
				</tr>
				<tr>
					<th>
						排序序号
					</th>
					<td>
						<input name="dept.sort" class="easyui-numberbox" data-options="required:true,validType:'customRequired'" />
					</td>
				</tr>
			</c:if>
		</table>
	</fieldset>
</form>
</body>
</html>
