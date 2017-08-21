<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
			var dictTree;
			$(function(){
				dictTree = $('#dictTree').tree({
					animate : true,
					url : '${pageContext.request.contextPath}/dicttype/dictTypeAction!initDictKey.action',
					onClick : function(node) {
						$('#dictFrame').attr('src','${pageContext.request.contextPath}/page/admin/dict/dictList.jsp')
					},
					onContextMenu : function(e, node)
					{
						e.preventDefault();
						$(this).tree('select', node.target);
						$('#mm').menu('show', {
							left : e.pageX,
							top : e.pageY
						});
					},
					onBeforeLoad:function(node, param){
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess:function(node, data){
						parent.$.messager.progress('close')
					}
				});
			});
			function addRoot()
			{
				var dialog = parent.modalDialog({
					title : '新增字典类别',
					iconCls : 'ext-icon-note_add',
					url : '${pageContext.request.contextPath}/page/admin/dicttype/addDictType.jsp',
					buttons : [ {
						text : '保存',
						iconCls : 'ext-icon-save',
						handler : function()
						{
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dictTree, parent.$);
						}
					} ]
				});
			}

            function editFun()
            {
                var id = $('#dictTree').tree('getSelected').id;
                var dialog = parent.WidescreenModalDialog({
                    title : '编辑字典类别',
                    iconCls : 'ext-icon-note_edit',
                    url : '${pageContext.request.contextPath}/page/admin/dicttype/editDictType.jsp?id='+id,
                    buttons : [ {
                        text : '保存',
                        iconCls : 'ext-icon-save',
                        handler : function()
                        {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dictTree, parent.$);
                        }
                    } ]
                });
            }
			
			function removeFun()
			{
				var id = $('#dictTree').tree('getSelected').id;
				parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r)
				{
					if (r)
					{
						$.ajax({
							url : '${pageContext.request.contextPath}/dicttype/dictTypeAction!deleteDictType.action',
							data : {
								id : id
							},
							dataType : 'json',
							success : function(result)
							{
								if (result.success)
								{
									$('#dictTree').tree('reload');
									$('#dictFrame').attr('src','')
									parent.$.messager.alert('提示', result.msg, 'info');
								} else
								{
									parent.$.messager.alert('提示', result.msg, 'error');
								}
							},
							beforeSend : function()
							{
								parent.$.messager.progress({
									text : '数据提交中....'
								});
							},
							complete : function()
							{
								parent.$.messager.progress('close');
							}
						});
					}
				});
			}
		</script>
	</head>
	<body id="layout" class="easyui-layout" style="overflow-y: hidden"
		scroll="no">
		<div data-options="region:'west',split:true" style="width: 150px;">
			<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增字典类别">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addRoot();" style="margin-left: 5px; margin-top: 1px;">新增</a>
			</authority:authority>
			<ul id="dictTree"></ul>
		</div>
		<div data-options="region:'center'" style="overflow-y: hidden">
			<iframe id="dictFrame" src="" allowTransparency="true"
				style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
		</div>
		<div id="mm" class="easyui-menu" style="width: 120px;">
			<authority:authority authorizationCode="编辑字典" userRoles="${sessionScope.user.userRoles}">
				<div onclick="editFun()" data-options="iconCls:'ext-icon-note_edit'">
					编辑
				</div>
			</authority:authority>
			<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除字典类别">
				<div onclick="removeFun()" data-options="iconCls:'ext-icon-note_delete'">
					删除
				</div>
			</authority:authority>
		</div>
	</body>
</html>