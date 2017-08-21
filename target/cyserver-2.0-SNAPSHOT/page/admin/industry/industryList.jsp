<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority" %>
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
		var grid;
		$(function(){
				grid = $('#grid').treegrid({
					url : '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode=-1',
//					fitColumns:true,//自动使列适应表格宽度
					idField : 'id',//树主键ID
					treeField : 'text',//树字段
					parentField : 'pid',//树父节点
					rownumbers : true,//是否显示行号
					pagination : false,//是否分页
					columns : [[//列定义
					{
						width : '300',//宽度
						title : '行业名称',//列名
						field : 'text',//绑定字段名
					},
					{
						width : '150',
						title : '行业代码',
						field : 'id',
					},{
					    title : '操作',
						field : '     ',
						width : '200',
						formatter : function(value, row) { //字段值格式化
							 var str = '';
							 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看用户">
								str += '<a href="javascript:void(0)" onclick="showFun(\''+ row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							 </authority:authority>
							 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑用户">
								str += '<a href="javascript:void(0)" onclick="editFun(\''+ row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							 </authority:authority>
							 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除用户">
								str += '<a href="javascript:void(0)" onclick="removeFun(\''+ row.id + '\');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
							 </authority:authority>
								return str;
						}
					} ] ],
					toolbar : '#toolbar',//工具栏
					onBeforeExpand:function(row, param){ //在树展开前执行方法
						//动态设置展开查询的url
						var url = '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode='+row.id;
						grid.treegrid('options').url = url;
						return true;
					},
					onBeforeLoad : function(row, param) {
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess : function(row, data) {
						//alert(JSON.stringify(data));
						$('.iconImg').attr('src', pixel_0);
						parent.$.messager.progress('close');
					}
				});
		});
		/**
		* 新增方法
		**/
		function addFun(){
			var dialog = parent.WidescreenModalDialog({
				title : '新增行业信息',
				iconCls : 'ext-icon-note_add',
				url :  '${pageContext.request.contextPath}/page/admin/industry/industryForm.jsp?id=0',
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		}
		/**
		 * 查看方法
		 **/
		function showFun(code) {
			var dialog = parent.WidescreenModalDialog({
				title : '查看行业信息',
				iconCls : 'ext-icon-note_edit',
				url : '${pageContext.request.contextPath}/page/admin/industry/viewIndustry.jsp?code=' + code
			});
		};
		/**
		 * 编辑方法
		 **/
		function editFun(code) {
			var dialog = parent.WidescreenModalDialog({
				title : '编辑行业信息',
				iconCls : 'ext-icon-note_edit',
				url : '${pageContext.request.contextPath}/page/admin/industry/industryForm.jsp?code=' + code,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});
		};

        /**
		 * author chensheng
         * 导入方法
         **/
        function importFun(){
            var dialog = parent.parent.modalDialog({
                title : '导入行业基础信息',
                iconCls : 'ext-icon-import_customer',
                url : '${pageContext.request.contextPath}/page/admin/industry/importIndustry.jsp',
                buttons : [ {
                    text : '确定',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, parent.parent.$);
                    }
                } ]
            });
        }
		/**
		 * 删除方法
		 **/
		function removeFun(code) {
			parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/industry/industryAction!delete.action',
						data : {
							'industry.code' : code
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								grid.treegrid('options').url= '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode=-1';
								grid.treegrid('reload');
								parent.$.messager.alert('提示', result.msg, 'info');
							} else {
								parent.$.messager.alert('提示', result.msg, 'error');
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
			});
		};
		/**
		 * 重新加载列表数据
		 **/
		function reload(){
			grid.treegrid('options').url= '${pageContext.request.contextPath}/industry/industryAction!dataGrid.action?parentCode=-1';
			grid.treegrid('load', {
				'industry.code': $('#code').val(),
				'industry.value': $('#value').val()
			});
		}

		function resetT(){
			$('#searchForm')[0].reset();
		}

	</script>
  </head>
  
  <body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										行业名称
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="value" style="width: 150px;" />
									</td>
									<th>
										行业代码
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="code" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="reload()">查询</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td>
									 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增用户">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">新增</a>
									</authority:authority>
								</td>
								<td>
									<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="行业导入">
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun();" style="margin-left: 5px; margin-top: 1px;">导入</a>
									</authority:authority>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="grid"></table>
		</div>
	</body>
</html>
