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
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../inc.jsp"></jsp:include>
	<script type="text/javascript">
		var regionGrid;
		$(function(){
				regionGrid = $('#regionGrid').treegrid({
					url : '${pageContext.request.contextPath}/region/regionAction!dataGrid.action?pid=-1',
//					fitColumns:true,
					fit:true,
					idField : 'id',
					parentField : 'pid',
					treeField : 'text',
					rownumbers : true,
					pagination : false,
					columns : [[
					{
						width : '300',
						title : '地区',
						field : 'text',
					},
					{
						width : '100',
						title : '类别',
						field : 'attributes',
						formatter : function(value,row) {
							var obj = jQuery.extend({}, row.attributes);
							var v = obj.level;
							if(v=='1'){
								return '国家';
							}
							if(v=='2'){
								return '省份';
							}
							if(obj.level=='3'){
								return '城市';
							}
							if(obj.level='4'){
								return '区县';
							}
						}
					},
					{
						width : '100',
						title : '城市区号',
						field : 'areaCode',
						formatter : function(value,row) {
							var obj = jQuery.extend({}, row.attributes);
							return obj.areaCode;
						}
					},
					{
						width : '100',
						title : '邮政编码',
						field : 'postCode',
						formatter : function(value,row) {
							var obj = jQuery.extend(true,{}, row.attributes);
							return obj.postCode;
						}
					},
					{
					    title : '操作',
						field : 'action',
						width : '200',
						formatter : function(value,row) {
								var obj = jQuery.extend({}, row.attributes);
								var v = obj.level;
                                var str = '';
                                <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看地区">
                                    str += '<a href="javascript:void(0)" onclick="showFun(\''+ row.id + '\',\''+v+'\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                                </authority:authority>
                                <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑地区">
                                    str += '<a href="javascript:void(0)" onclick="editFun(\''+ row.id + '\',\''+v+'\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>

                                <%--<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除用户">--%>
                                    <%--str += '<a href="javascript:void(0)" onclick="removeFun(\''+ param + '\');"><img class="iconImg ext-icon-note_delete"/>删除</a>';--%>
                                <%--</authority:authority>--%>
								return str;
                            }
						  }] ],
					toolbar : '#toolbar',
					onBeforeExpand:function(row, param){ //在树展开前执行方法
						//动态设置展开查询的url
						var url = '${pageContext.request.contextPath}/region/regionAction!dataGrid.action?pid='+row.id;
						regionGrid.treegrid('options').url = url;
						return true;
					},
					onBeforeLoad : function(row, param) {
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					onLoadSuccess : function(row, data) {
//						alert(JSON.stringify(data));
						$('.iconImg').attr('src', pixel_0);
						parent.$.messager.progress('close');
					}
				});
		});

		function addFun(level){
			var title = '';
			var url = '';
			if(level==1){
				title = '新增国家信息';
				url = '${pageContext.request.contextPath}/page/admin/region/countryForm.jsp';
			 } else if(level==2){
				 title = '新增省份信息';
				 url = '${pageContext.request.contextPath}/page/admin/region/provinceForm.jsp';
			 } else if(level==3){
				 title = '新增城市信息';
				 url = '${pageContext.request.contextPath}/page/admin/region/cityForm.jsp';
			 } else if(level==4){
				 title = '新增区县信息';
				 url = '${pageContext.request.contextPath}/page/admin/region/areaForm.jsp';
			 }
			var dialog = parent.WidescreenModalDialog({
				title : title,
				iconCls : 'ext-icon-note_add',
				url :  url,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, regionGrid, parent.$);
					}
				} ]
			});
		};

        function editFun(id,level) {
			var title = '';
			var url = '';
			if(level==1){
				title = '国家信息编辑';
				url = '${pageContext.request.contextPath}/page/admin/region/countryForm.jsp?id='+id+'&action=edit';
			} else if(level==2){
				title = '省份信息编辑';
				url = '${pageContext.request.contextPath}/page/admin/region/provinceForm.jsp?id='+id+'&action=edit';
			} else if(level==3){
				title = '城市信息编辑';
				url = '${pageContext.request.contextPath}/page/admin/region/cityForm.jsp?id='+id+'&action=edit';
			} else if(level==4){
				title = '区县信息编辑';
				url = '${pageContext.request.contextPath}/page/admin/region/areaForm.jsp?id='+id+'&action=edit';
			}
			var dialog = parent.WidescreenModalDialog({
				title : title,
				iconCls : 'ext-icon-note_edit',
				url : url,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, regionGrid, parent.$);
						reload();
					}
				} ]
			});
		};

//		查看 add by jiangling
		function showFun(id,level) {
//			alert("region.id= "+id);
			var title = "";
			var url = "";
			//根据不同的层级(level)来判断国家1,省份2,城市3,区县4,分别展示不同的查看页面
			//CountryId=3.id ; ProvinceId=2.id; 城市: 1.id; 区县: id)
			if(level==1){
				title = "查看国家信息";
				url = '${pageContext.request.contextPath}/page/admin/region/countryForm.jsp?id='+id+'&action=view';
			}else if(level==2){
				title = "查看省份信息";
				url = '${pageContext.request.contextPath}/page/admin/region/provinceForm.jsp?id='+id+'&action=view';
			}else if(level==3){
				title = "查看城市信息";
				url = '${pageContext.request.contextPath}/page/admin/region/cityForm.jsp?id='+id+'&action=view';
			}else if(level==4){
				title = "查看区县信息";
				url = '${pageContext.request.contextPath}/page/admin/region/areaForm.jsp?id='+id+'&action=view';
			}
			var dialog = parent.WidescreenModalDialog({
                title : title,
                iconCls : 'icon-search',
                url : url
            });
		};

		function removeFun(code) {
			parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
				if (r) {
					$.ajax({
						url : '${pageContext.request.contextPath}/region/regionAction!delete.action',
						data : {
							'region.code' : code
						},
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								regionGrid.treegrid('reload');
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
		function resetT(){
			$('#searchForm')[0].reset();
		}
		function reload(){
			regionGrid.treegrid('options').url = '${pageContext.request.contextPath}/region/regionAction!dataGrid.action?pid=-1';
			regionGrid.treegrid('load', {
				'strRegion': $('#text').val()
			});
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
										地区名称
									</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="text" name="strRegion" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" 	onclick="reload()">查询</a>
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
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun(1);">新增国家</a>
									</authority:authority> 
								</td>
								<td>
									 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增用户">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun(2);">新增省份</a>
									</authority:authority> 
								</td>
								<td>
									 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增用户">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun(3);">新增城市</a>
									</authority:authority> 
								</td>
								<td>
									 <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增用户">
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-note_add',plain:true"
											onclick="addFun(4);">新增县区</a>
									</authority:authority> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="regionGrid" style="height: 500px;"></table>
		</div>
	</body>
</html>
