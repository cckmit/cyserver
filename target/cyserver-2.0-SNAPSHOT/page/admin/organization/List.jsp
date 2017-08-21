<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
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
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	var aluName = '校友总会';
	var aluId = 1;
	var aluMain = 99;
	var aluXueyuan=0;
	var aluAcade='undefined';
	var aluLevel = 0;
	var aluMix = 'aluId='+aluId+'&aluName='+aluName+'&aluMain='+aluMain+'&aluXueyuan='+aluXueyuan+'&aluAcade='+aluAcade+'&level=0';
	var alumniTree;
	var nodeList;	//所有树节点
	var isOldData = true;	//只重载一次tree
	var currNode;			//当前行Node
	var currAlumniId;		//当前用户所在分会的ID
	$(function() {
		alumniTree = $('#alumniTree').tree({
			url : '${pageContext.request.contextPath}/alumni/alumniAction!getAlumniTree.action?withOldAlumni=1',
			animate : true,
			onClick : function(node){
				if(node.pid != '-1') {
					aluMain = node.attributes.mainType;
					aluName = node.text;
					aluId = node.id;
					aluXueyuan = node.attributes.xueyuanfenhui;
					aluAcade = node.attributes.academyid;
					aluLevel = node.attributes.level;
				}else{
					aluMain = 99;
					aluLevel = 0;
				}
				aluMix = 'aluId='+aluId+'&aluName='+aluName+'&aluMain='+aluMain+'&aluXueyuan='+aluXueyuan+'&aluAcade='+aluAcade+'&level='+aluLevel;
				currNode = node;	//lixun 给当前Node赋值
				//alert(aluMix);
				/*$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
				node.state = node.state === 'closed' ? 'open' : 'closed';*/
				showList(aluId);
			},
			onBeforeLoad : function(node, param)
			{
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(node, data)
			{
//				alert( JSON.stringify(data) );
				parent.$.messager.progress('close');

				//遍历寻找院系
				if( isOldData ) {
					isOldData = false;
					//data[0].text="<span style='color:red;'>" + data[0].text + "</span>";
					var xueyuan = getXueYuanFenHui( data );
					if( xueyuan != null )
					{
						setSubXueyuan( xueyuan, 1 );
					}
					$('#alumniTree').tree( 'loadData', data );
					//获取当前分会ID
					currAlumniId = data[0].attributes.userAlumniId;
				}
			}
		});
	});
	/*递归获取学院分会节点,参数:节点list,返回学院分会节点*/
	function getXueYuanFenHui( data )
	{
		if( data == undefined )
			return null;
		for( var i = 0; i < data.length; ++i )
		{
			if( data[i].attributes.xueyuanfenhui != undefined && data[i].attributes.xueyuanfenhui == "1" )
				return data[i];	//获取成功
			var aChildren = data[i].children;
			if( aChildren == undefined )
				return null;
			var re = getXueYuanFenHui(aChildren);
			if( re != null )
				return re;
		}
	}
	/*递归给所有子节点赋值,参数1:节点,参数2:xueyuanfenhui的值,无返回值*/
	function setSubXueyuan( data, value )
	{
		if( data == undefined ) return;
		var attr = data.attributes;
		if( attr.academyid == undefined  ){
			data.attributes = { "level":attr.level, "fullName":attr.fullName, "mainType":attr.mainType, "xueyuanfenhui":value };
			if( value == 2 )
				data.text = "<span style='color:red;'>" + data.text + "</span>";
		}
		else
			data.attributes = { "level":attr.level, "fullName":attr.fullName, "mainType":attr.mainType, "xueyuanfenhui":value, "academyid":attr.academyid };
		var aChildren = data.children;
		if( aChildren == undefined ) return;
		for( var i = 0; i < aChildren.length; ++i )
		{
			setSubXueyuan( aChildren[i], value + 1 );
		}
	}
	/*递归查询所有子节点,获取目标节点与本节点相差的级别*/
	function getSubLevel( data, value )
	{
		if( data == undefined ) return;
		if( data.id == value )
			return 1;
		var aChildren = data.children;
		if( aChildren == undefined ) return;
		for( var i = 0; i < aChildren.length; ++i )
		{
			var re = getSubLevel( aChildren[i], value );
			if( re > 0 ) return re+1;
		}
		return 0;
	}

	var alumniGrid;
	$(function() {
		alumniGrid = $('#alumniGrid').datagrid({
			url : '${pageContext.request.contextPath}/alumni/alumniAction!dataGrid.action',
			singleSelect:true,
			fit : true,
			rownumbers : true,
			nowrap : false,
			method : 'post',
			border : false,
			striped : true,
			pagination : true,
			columns : [ [
					{
						width : '12%',
						title : '机构名称',
						field : 'alumniName',
						align : 'center',
                        formatter : function(value, row) {
						    if("院系分会" != row.mainType || (row.xueyuanId && row.xueyuanId != '')){
                                return value;
							}else{
						        return '<font color="red">'+value+'</font>'
							}

						}
					},
					/*{
						width : '12%',
						title : '上级机构',
						field : 'parent',
						align : 'center',
					},*/
					{
						width : '10%',
						title : '机构类型',
						field : 'mainType',
						align : 'center'
					},
					{
						width : '15%',
						title : '地区',
						field : 'region',
						align : 'center',
						formatter : function(value) {
							if (value != null) {
								return "<span title='"+value+"'>"
										+ value + "</span>";
							} else {
								return "";
							}
						}
					},{
						width : '10%',
						title : '行业',
						field : 'industryCode',
						align : 'center'
					},
				{
					width : '15%',
					title : '联系人电话',
					field : 'telephone',
					align : 'center'
				},
				/*{
						width : '15%',
						title : '机构管理员',
						field : 'admin',
						align : 'center'
					},*/
//					{
//						width : '10%',
//						title : '是否开通',
//						field : 'isUsed',
//						align : 'center'
//					},
					{
						title : '操作',
						field : 'action',
						width : '15%',
						align : 'center',
						formatter : function(value, row) {
							var str = '';
							<authority:authority authorizationCode="查看校友组织" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="viewFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							<authority:authority authorizationCode="编辑校友组织" userRoles="${sessionScope.user.userRoles}">
							str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							</authority:authority>
							if(row.mainType != '院系分会' && row.alumniName != '校友总会'){
								<authority:authority authorizationCode="删除校友组织" userRoles="${sessionScope.user.userRoles}">
								str += '<a href="javascript:void(0)" onclick="removeFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
								</authority:authority>
								/*if(row.isUsed=="审核中"){
									<authority:authority authorizationCode="审批校友组织" userRoles="${sessionScope.user.userRoles}">
									str += '<a href="javascript:void(0)"" onclick="checkFun(\'' + row.alumniId + '\');"><img class="iconImg ext-icon-note_edit"/>审批</a>&nbsp;';
									</authority:authority>
								}*/
							}
							return str;
					}
                }]],
			toolbar : '#toolbar',
			onBeforeLoad : function() {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				//alert(JSON.stringify(data));
				$('.iconImg').attr('src', pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
	function showList(id) {
		var json = {'aluid': aluId};
		$('#aluid').val(id);
		$('#alumniGrid').datagrid('load', json);
		if((aluMain==2 || aluMain==3) && aluLevel == 2)
		{
			$('#addAlu').show();
		}else{
			$('#addAlu').hide();
		}
	}

	function viewFun(id) {
		var dialog = parent.WidescreenModalDialog({
			title : '查看用户信息',
			iconCls : 'ext-icon-note',
			url : '${pageContext.request.contextPath}/page/admin/organization/check.jsp?id='+id+'&view=view'
		});
	};
	function searchAlumni() {
		//alert(JSON.stringify(serializeObject($('#searchForm'))));
		$('#alumniGrid').datagrid('load', serializeObject($('#searchForm')));
	}
	function addFun() {
		/*判断权限*/
		if( currNode == undefined )
		{
			$.messager.alert('警告', '无法添加,请先在左边的树选择机构', 'warning');
			return;
		}
		level = getSubLevel( currNode, currAlumniId);
//		alert( currNode + ',' + currAlumniId + ',' + level );
		if( level > 1 )
		{
			$.messager.alert('警告', '不能在您的上级机构新增子机构,请选择您的下级机构', 'warning');
			return;
		}
		/**/
		if( aluName == '学院分会')
		{
			$.messager.alert('警告', '不能添加学院分会', 'warning');
			return;
		}
		var dialog = parent
				.WidescreenModalDialog({
					title : '新增校友组织',
					iconCls : 'ext-icon-note_add',
					url : '${pageContext.request.contextPath}/page/admin/organization/add.jsp?'+aluMix,
					buttons : [ {
						text : '保存',
						iconCls : 'ext-icon-save',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.submitForm(dialog, alumniGrid, alumniTree, parent.$);
						}
					} ]
				});
	}
	function removeFun(id){
		$.messager.confirm('确认', '确定删除吗？', function(r) {
			if (r) {
				$.ajax({
					url : '${pageContext.request.contextPath}/alumni/alumniAction!delete.action',
					data : {
						ids : id
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							alumniTree.tree('reload');
							alumniGrid.datagrid('reload');
							$.messager.alert('提示',data.msg,'info');
						}
						else{
							$.messager.alert('错误', data.msg, 'error');
						}
					},
					beforeSend:function(){
						$.messager.progress({
							text : '数据提交中....'
						});
					},
					complete:function(){
						$.messager.progress('close');
					}
				});
			}
		});
	}
	function checkFun(id)
	{
		var dialog = parent.parent.WidescreenModalDialog({
			title : '机构审核',
			iconCls : 'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/organization/check.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, alumniGrid, parent.$);
				}
			} ]
		});
	}
	function editFun(id)
	{
		var dialog = parent.WidescreenModalDialog({
			title : '编辑校友组织',
			iconCls : 'ext-icon-note_edit',
			url : '${pageContext.request.contextPath}/page/admin/organization/edit.jsp?id=' + id,
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function()
				{
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, alumniGrid,alumniTree, parent.$);
				}
			} ]
		});
	}

	function resetT() {
		$('#alumniName').val('');
		$('#withoutOldAlumni').combobox('setValue', '');
		alumniGrid.datagrid('load', serializeObject($('#searchForm')));
	}
</script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west',border:1" width="18%">
		<ul id="alumniTree"></ul>
	</div>
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<th align="right" width="50px;">分会名称</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td><input name="alumni.alumniName" id="alumniName" style="width: 150px;" />
								</td>
								<td>
									<input name="aluid" id="aluid" value="1" type="hidden" />
								</td>
								<th align="right" width="120px;">是否显示已取消分会</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select class="easyui-combobox" data-options="editable:false" name="withoutOldAlumni" id="withoutOldAlumni" style="width: 150px;">
										<option value="">是</option>
										<option value="1">否</option>
									</select>
								</td>
								<%--
								<th align="right" width="50px;">机构类型</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select class="easyui-combobox" data-options="editable:false" name="mainType" id="mainType" style="width: 150px;">
									<option value="">&nbsp;</option>
									<option value="1">院系分会</option>
									<option value="2">地方分会</option>
									<option value="3">行业分会</option>
									<option value="4">兴趣分会</option>
									</select>
								</td>
								--%>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-search',plain:true"
									onclick="searchAlumni();">查询</a>&nbsp;
								<a href="javascript:void(0)" class="easyui-linkbutton"
								   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
								   onclick="resetT();">重置</a>&nbsp;</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr id="addAlu" style="display: none">
							<td><authority:authority authorizationCode="新增校友组织"
									userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'ext-icon-note_add',plain:true"
										onclick="addFun();">新增</a>
								</authority:authority>
							</td>
						</tr>
						<tr>
							<td><span id="exportResult"></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:false,border:1" width="82%">
		<table id="alumniGrid"></table>
	</div>
</body>
</html>
