<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
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
	var grid;
	$(function(){
	grid=$('#newsGrid').datagrid({
		url : '${pageContext.request.contextPath}/mobile/news/newsAction!dataGrid.action',
		fit : true,
		border : false,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'newsId',
		 columns:[[
				{field:'newsId',checkbox : true},
				{field:'title',title:'标题',width:150,align:'center'},
				{field:'channelName',title:'频道',width:60,align:'center'},
				{field:'type',title:'兴趣标签',width:80,align:'center'},
			 	{field:'mainName',title:'新闻来源',width:60,align:'center'},
				/*{field:'origin',title:'新闻来源',width:60,align:'center',
					formatter: function(value,row,index){
							if(row.origin==1 || row.originP==1 || row.originWeb==1 || row.originWebP==1){
								return "总会";
							}else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
							}else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
							}else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
								return "地方";
							}
						}
				},*/
				{
					width: '80',
					title: '所属组织',
					field: 'dept_name',
					align: 'center',
					formatter : function(value, row) {
						if(value == undefined || value =='' || value == null){
							return "---";
						}
						else{
							return value;
						}
						/*if(row.origin==1 || row.originP==1 || row.originWeb==1 || row.originWebP==1) {
							return row.dept_name;
						} else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2) {
							return "---";
						}*/
					}
				},
				{
					width: '80',
					title: '审核组织',
					field: 'approveDeptName',
					align: 'center',
				},
				{field:'channels',title:'新闻栏目',width:80,align:'left',

					formatter: function(value,row,index){
						if( value == undefined ) return "";
						return "(" + value.replace( /_/g, ")[").replace( /,/g, "]<br>(" ) + "]";
						//alert( value + typeof(value) );

					}
				},
				/*{field:'categoryWeb',title:'网页栏目',width:80,align:'center',
					formatter: function(value,row,index){
						if(row.categoryWeb==null || row.categoryWeb==0){
							return "无";
						}else if(row.pCategoryWeb!=null && row.pCategoryWeb==0){
							return row.categoryWebName;
						}else if(row.pCategoryWeb!=null && row.pCategoryWeb!=0){
							return row.pCategoryWebName + " -- " + row.categoryWebName;
						}
					}
				},*/
				/*{field:'cityName',title:'所属城市',width:80,align:'center'},*/
				{field:'topnews',title:'手机幻灯片',width:60,align:'center',
					formatter: function(value,row,index){
						if(value==100){
							return "√";
						}else{
							return "×";
						}
					}
				},
				{field:'topnewsWeb',title:'网页幻灯片',width:60,align:'center',
					formatter: function(value,row,index){
						if(value==100){
							return "√";
						}else{
							return "×";
						}
					}
				},
				{field:'updateDate',title:'时间',width:130,align:'center'},
			 	{field:'status', title:'状态', width:100, align:'center',
					formatter: function (value) {
						switch (value){
							case '10' : return "审核中";
							case '15' : return "重新审核";
							case '20' : return "已通过";
							case '30' : return "<span style='color:red;'>未通过</span>";
							case '40' : return "<span style='color:red;'>已下线</span>";
							default: return "未知状态";
						}
					}
				},
				{field:'operator',title:'操作',width:"200px",
						formatter: function(value,row,index){
							var content="";
							<authority:authority authorizationCode="查看新闻" userRoles="${sessionScope.user.userRoles}">
								content+='<a href="javascript:void(0)" onclick="viewNews('+row.newsId+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							</authority:authority>
							<%--<authority:authority authorizationCode="编辑新闻" userRoles="${sessionScope.user.userRoles}">
							if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
								content+='<a href="javascript:void(0)" onclick="convertNews('+row.newsId+')"><img class="iconImg ext-icon-note_edit"/>转总会</a>&nbsp;';
							}else{
								content+='<a href="javascript:void(0)" onclick="editNews('+row.newsId+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
							}
							</authority:authority>--%>
							if(row.dept_id == row.uDeptId){
								<authority:authority authorizationCode="编辑新闻" userRoles="${sessionScope.user.userRoles}">
								content+='<a href="javascript:void(0)" onclick="editNews('+row.newsId+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
								</authority:authority>
							}
							if((row.status==10 || row.status==15) && row.approveDeptId == row.uDeptId){
								<authority:authority authorizationCode="新闻审核" userRoles="${sessionScope.user.userRoles}">
								content+='<a href="javascript:void(0)" onclick="checkNews('+row.newsId+')"><img class="iconImg ext-icon-note"/>审核</a>&nbsp;';
								</authority:authority>
							}

							if(row.status==20){
								<authority:authority authorizationCode="新闻审核" userRoles="${sessionScope.user.userRoles}">
								content+='<a href="javascript:void(0)" onclick="dropDown('+row.newsId+')"><img class="iconImg ext-icon-note_delete"/>下线</a>&nbsp;';
								</authority:authority>
							}
							return content;
					}}
				]],
				toolbar : '#newsToolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', pixel_0);
				parent.$.messager.progress('close');

			}
		});
	});

	function searchNews(){
	  if ($('#searchNewsForm').form('validate')) {
		  $('#newsGrid').datagrid('load',serializeObject($('#searchNewsForm')));
	  }
	}


	function addNews() {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 640,
			title : '新增',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/news/addNews.jsp',
			buttons : [/* {
				text : '预览',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.previewMobNew();
				}
			},*/{
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	
	/**
	 * 编辑新闻
	 */
	function editNews(id) {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 640,
			title : '编辑',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSecurity_initNewsUpdate.action?id='+id,
			buttons : [ {
				text : '预览',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.previewMobNew();
				}
			},{
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
	
	/**--将地方新闻转为总会新闻--**/
	function convertNews(id){
		var dialog = parent.modalDialog({
			width : 1000,
			height : 640,
			title : '转为总会新闻',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSecurity_initNewsUpdate.action?id='+id+"&convert=1",
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
	 * 审核新闻
	 * @param id
     */
	function checkNews(id) {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 640,
			title : '审核',
			iconCls:'ext-icon-note_add',
			url : "${pageContext.request.contextPath}/mobile/news/newsAction!getById.action?id=" + id/* + "&check=1"*/,
			buttons : [ {
				text : '审核通过',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, "20");
				}
			}, {
				text : '审核不通过',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, "30");
				}
			}]
		});
	}

	/**
	 * 下线
	 */
	function dropDown(id) {
		/*var dialog = parent.modalDialog({
			width : 800,
			height : 230,
			title : '下线新闻',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/page/admin/news/checkNews.jsp?id='+id+'&drop=40',
			buttons : [ {
				text : '保存',
				iconCls : 'ext-icon-save',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});*/
		bulkCheckOrDropNews(id,'40','1') ;
	}


	function bulkCheckOrDropNews( ids ,status ,drop){
		//批量审批
		if(drop == '0' && (status == '20' || status == '30')){
			var json = {"ids":ids,
				"status":status};

			$.ajax({
				url : "${pageContext.request.contextPath}/mobile/news/newsAction!multiLineCheck.action",
				data : json,
				dataType : 'json',
				success : function(result)
				{
					if (result.success){
						grid.datagrid('reload');
						grid.datagrid('unselectAll');
						window.parent.refreshMsgNum();
						parent.$.messager.alert('提示', result.msg, 'info');
					}else{
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
		}else if(drop == '1'){
			var json = {"bussId":ids,
				"status": '40'};

			$.ajax({
				url : "${pageContext.request.contextPath}/mobile/news/newsAction!saveCheck.action",
				data : json,
				dataType : 'json',
				success : function(result){
					if (result.success){
						grid.datagrid('reload');
						grid.datagrid('unselectAll');
						parent.$.messager.alert('提示', '下线成功', 'info');
					}else{
						parent.$.messager.alert('提示', '下线失败', 'error');
					}
				},
				beforeSend : function(){
					parent.$.messager.progress({
						text : '数据提交中....'
					});
				},
				complete : function(){
					parent.$.messager.progress('close');
				}
			});
		}else if(drop == '0' && status == '40'){
			var json = {"ids":ids,
				"status": '40'};

			$.ajax({
				url : "${pageContext.request.contextPath}/mobile/news/newsAction!multiLineCheck.action",
				data : json,
				dataType : 'json',
				success : function(result)
				{
					if (result.success){
						grid.datagrid('reload');
						grid.datagrid('unselectAll');
						window.parent.refreshMsgNum();
						parent.$.messager.alert('提示', '批量下线成功', 'info');
					}else{
						parent.$.messager.alert('提示', '批量下线失败', 'error');
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

	}
	/**
	 * 查看新闻
	 */
	function viewNews(id) {
		var dialog = parent.modalDialog({
			width : 1000,
			height : 640,
			title : '查看',
			iconCls:'ext-icon-note_add',
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!getById.action?id=' + id
		});
	}


	function removeNews(){
	
	var rows = $("#newsGrid").datagrid('getChecked');
	var ids = [];
	if (rows.length > 0) {
		$.messager.confirm('确认', '确定删除吗？', function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].newsId);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/mobile/news/newsAction!delete.action',
					data : {
						ids : ids.join(',')
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							$("#newsGrid").datagrid('reload');
							$("#newsGrid").datagrid('unselectAll');
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
	} else {
		 $.messager.alert('提示', '请选择要删除的记录！', 'error');
	}
}

	function setMobTypeList(isRmv,pageType){
	var rows = $("#newsGrid").datagrid('getChecked');
	var str1="";
	var str2="";
		
	if(isRmv){
		str1="设置";		
	}else{
		str1="取消";
	}
	if(pageType==1) {
		str2="手机";
	} else if(pageType==2) {
		str2="网页";
	}
	
	var tmpAlert = "确定要"+str1+"所选记录为"+str2+"幻灯片新闻吗?";
	var ids = [];
	if (rows.length > 0) {
		$.messager.confirm('确认', tmpAlert, function(r) {
			if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].newsId);
				}
				$.ajax({
					url : '${pageContext.request.contextPath}/mobile/news/newsAction!setMobTypeList.action',
					data : {
						ids : ids.join(','),
						isRmv : isRmv,
						pageType : pageType
					},
					dataType : 'json',
					success : function(data) {
						if(data.success){
							$("#newsGrid").datagrid('reload');
							$("#newsGrid").datagrid('unselectAll');
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
	} else {
		 $.messager.alert('提示', '请选择要设置的记录！', 'error');
	}
}

	function sendList(){
	  var rows = $("#newsGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '确定批量推送吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].newsId);
					}
					if(ids.length>10){
						parent.$.messager.alert('提示', '每批消息不能超过10条！', 'error');
					}else{
						$.ajax({
							url : '${pageContext.request.contextPath}/mobile/news/newsAction!sendList.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#newsGrid").datagrid('reload');
									$("#newsGrid").datagrid('unselectAll');
									parent.$.messager.alert('提示',data.msg,'info');
								}
								else{
									parent.$.messager.alert('错误', data.msg, 'error');
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
			});
		} else {
			 parent.$.messager.alert('提示', '请选择要批量推送的记录！', 'error');
		}
}

	/**--推送数据到网站后台--**/
	function addToWebPageDB() {
	var rs =window.confirm("您确定要推送新闻数据到网站后台数据库吗？");
	if(rs){
		
		$.ajax({
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!addToWebpageDB.action',
			dataType : 'json',
			success : function(data) {
				if(data.success){
					$.messager.alert('提示',data.msg,'info');
				}
				else{
					$.messager.alert('错误', data.msg, 'error');
				}
			},
			beforeSend:function(){
				$.messager.progress({
					text : '正在推送新闻数据到网站后台数据库......'
				});
			},
			complete:function(){
				$.messager.progress('close');
			}
		});
	}
}


	/*lixun 2016-8-10*/
	function multiLineCheck(status){
		var rows = $("#newsGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			for ( var i = 0; i < rows.length; i++) {
				ids.push( rows[i].newsId );
				if(rows[i].status != 10 && rows[i].status != 15){
					$.messager.alert('提示', '您选择了已审核或已下线的新闻', 'error');
					return;
				}
			}

			//弹框
			ids = ids.join( ',' );
			var msg = '' ;
			if (status == '30') {
				msg = "是否批量批准新闻通过?" ;
			} else {
				msg = "是否批量"
			}
			parent.$.messager.confirm('确认', '确定下线这些新闻吗？', function(r) {
				if (r) {
					bulkCheckOrDropNews(ids,status,'0') ;
				}
			}) ;

			/*var dialog = parent.modalDialog({

				width : 800,
				height : 250,
				title : '批量审核',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/news/checkNews.jsp?ids=' + ids,
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});*/

		}
		else
		{
			$.messager.alert('提示', '请选择要批量审批的新闻', 'error');
		}

	}
	function multiLineOffline(){
		var rows = $("#newsGrid").datagrid('getChecked');
		var ids = [];
		if (rows.length > 0)
		{
			for ( var i = 0; i < rows.length; i++) {
				ids.push( rows[i].newsId );
				if(rows[i].status != '20'){
					$.messager.alert('提示', '您选择了未审核或已下线的新闻', 'error');
					return;
				}
			}

			//弹框
			ids = ids.join( ',' );
			parent.$.messager.confirm('确认', '确定下线这些新闻吗？', function(r) {
				if (r) {
					bulkCheckOrDropNews(ids, '40', '0');
				}
			}) ;
			/*var dialog = parent.modalDialog({

				width : 800,
				height : 230,
				title : '批量审核',
				iconCls:'ext-icon-note_add',
				url : '${pageContext.request.contextPath}/page/admin/news/checkNews.jsp?ids=' + ids + '&drop=40',
				buttons : [ {
					text : '保存',
					iconCls : 'ext-icon-save',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
					}
				} ]
			});*/
		}else{
			$.messager.alert('提示', '请选择批量下线的新闻', 'error');
		}
	}

	function addToWeiXin() {
				var rows = $("#newsGrid").datagrid('getChecked');
				var ids = [];
				if (rows.length > 0)
				{
					for ( var i = 0; i < rows.length; i++) {
						if (i>7){
							$.messager.alert('提示', '推送新闻不能超过八条', 'error');
							return;
						}
						ids.push( rows[i].newsId );
						if(rows[i].status != '20'){
							$.messager.alert('提示', '您选择了未审核或已下线的新闻', 'error');
							return;
						}
					}
                    parent.$.messager.confirm('确认', '确定推送这些新闻吗？', function(r) {
                        if (r) {
                            var dialog = parent.modalDialog({
                                title : '请选择公众号',
                                iconCls:'ext-icon-note_add',
                                url : '${pageContext.request.contextPath}/page/admin/news/addWechat.jsp?ids='+ids.join(','),
                                buttons : [ {
                                    text : '推送',
                                    iconCls : 'ext-icon-save',
                                    handler : function() {
                                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ids.join(','), parent.$);
                                    }
                                } ]
                            });
                        }
                    }) ;
				}else{
					$.messager.alert('提示', '请选择推送的新闻', 'error');
				}
	};

	function resetT(){
		$('#title').val('');
		$('#deptName').val('');
		$('#qudao').combobox("clear");
		$('#newsType').combotree("clear");
		$('#newsType').combotree("loadData",[]);
	}

</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="newsToolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchNewsForm">
						<table>
							<tr>
								<th>
									标题
								</th>
								<td>
								<div class="datagrid-btn-separator"></div>
							   </td>
								<td>
									<input name="news.title"  id="title" style="width: 100px;" />
								</td>


								<th>
									所属组织
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="news.dept_name" id="deptName" style="width: 100px;" />
								</td>

								<th align="right" width="30px;">新闻渠道</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="qudao"  name="news.channel" class="easyui-combobox" style="width: 150px;"
										   data-options="
												valueField: 'dictValue',
												textField: 'dictName',
												editable:false,
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#qudao').combobox('clear');
									                $('#newsType').combotree('clear');
													  $('#newsType').combotree('loadData',[]);

									                }
									            }],
												url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 110,
												onSelect: function(rec){
												    var sChannel = rec.dictValue;
													var url =  '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+sChannel;
													$('#newsType').combotree('clear');
													$('#newsType').combotree('reload', url);
										}" >
									</select>
								</td>



								<th align="right" width="30px;">新闻栏目</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<select id="newsType" name="news.category"  class="easyui-combotree" style="width: 100px;"
										   data-options="
												editable:false,
												idField:'id',
												state:'open',
												textField:'text',
												parentField:'pid'
							                    " >
									</select>
								</td>

								<th align="right" width="30px;">手机幻灯片</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="newsTopnews" name="news.topnews"  class="easyui-combobox" style="width: 100px;"
										   data-options="
										   			editable:false,
													valueField: 'value',
													textField: 'label',
													icons:[{
														iconCls:'icon-clear',
														handler: function(e){
														$('#newsTopnews').combobox('clear');
														}
													}],
													data: [{
														label: '是',
														value: '100'
													},{
														label: '否',
														value: '0'
													}]" />
								</td>

								<th align="right" width="30px;">审核状态</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input id="newsStatus" name="news.status"  class="easyui-combobox" style="width: 100px;"
										   data-options="
										   			editable:false,
													valueField: 'value',
													textField: 'label',
													icons:[{
														iconCls:'icon-clear',
														handler: function(e){
														$('#newsStatus').combobox('clear');
														}
													}],
													data: [{
														label: '审核中',
														value: '10'
													},{
														label: '已通过',
														value: '20'
													},{
														label: '未通过',
														value: '30'
													},{
														label: '已下线',
														value: '40'
													}]" />
								</td>




















							<%--	<th>
									新闻渠道
								</th>
								<td>
									<select id="qudao" class="easyui-combobox" style="width: 150px"  name="news.channel" data-options="
					              	editable:false,panelHeight:80,
						          onSelect: function(value){
							       $('#newsType').combotree('clear');
							         sChannel = value.value;
						        	var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+sChannel;
						          	$('#newsType').combotree('reload',url);
					               },
					               onLoadSuccess : function() {
										$('#qudao').combobox('clear');
									}
					                   	" >

									</select>

								</td>--%>





								<%--<th>
									新闻渠道
								</th>
								<td>
									<select id="qudao" class="easyui-combobox" style="width: 150px" name="news.channel" data-options="
					              	editable:false,panelHeight:80,
						          onSelect: function(value){
							       $('#newsType').combotree('clear');
							         sChannel = value.value;
						        	var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+sChannel;
						          	$('#newsType').combotree('reload',url);
					               },
					               onLoadSuccess : function() {
										$('#qudao').combobox('clear');
									}
					                   	" >
										<option value="10">手机</option>
										<option value="20">网页</option>
										<option value="30">微信</option>
									</select>

								</td>



								<th>
									新闻栏目
								</th>
								<td colspan="3" >
									<select name="news.category"  id="newsType" class="easyui-combotree"
											data-options="
								editable:false,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								"
											style="width: 150px;">
									</select>

								</td>--%>


								<td>
								<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchNews();">查询</a>&nbsp;
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
							<authority:authority authorizationCode="新增新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addNews();">新增</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="删除新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_delete',plain:true"
									onclick="removeNews();">删除</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="发送新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-redo',plain:true"
									onclick="sendList();">推送新闻到APP</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="手机幻灯片新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-standard-film-add',plain:true"
									onclick="setMobTypeList(true,1);">设置手机幻灯片</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="手机幻灯片新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-standard-film-delete',plain:true"
									onclick="setMobTypeList(false,1);">取消手机幻灯片</a>
							</authority:authority>
							
							<authority:authority authorizationCode="手机幻灯片新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-standard-film-add',plain:true"
									onclick="setMobTypeList(true,2);">设置网页幻灯片</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="手机幻灯片新闻" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'icon-standard-film-delete',plain:true"
									onclick="setMobTypeList(false,2);">取消网页幻灯片</a>
							</authority:authority>
							</td>
							<td>
							<authority:authority authorizationCode="推送新闻数据到网站后台" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-note_add',plain:true"
									onclick="addToWebPageDB();">推送新闻数据到网站后台</a>
							</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="新闻批量审核" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_edit',plain:true"
									   onclick="multiLineCheck('20');">批量通过</a>
								</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="新闻批量审核" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_edit',plain:true"
									   onclick="multiLineCheck('30');">批量不通过</a>
								</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="新闻批量审核" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'ext-icon-note_delete',plain:true"
									   onclick="multiLineOffline();">批量下线</a>
								</authority:authority>
							</td>
							<td>
								<authority:authority authorizationCode="推送新闻到微信" userRoles="${sessionScope.user.userRoles}">
									<a href="javascript:void(0);" class="easyui-linkbutton"
									   data-options="iconCls:'icon-redo',plain:true"
									   onclick="addToWeiXin();">推送新闻到微信</a>
								</authority:authority>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="newsGrid" ></table>
	</div>
</div>
  </body>
</html>