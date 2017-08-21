<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String messageType = request.getParameter("messageType");
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
	$(function(){
		$('#messageBoardGrid').datagrid({  
		    url:'${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!dataGrid.action?messageBoard.messageType=<%= messageType %>', 
		  	fit : true,
//		  	fitColumns:true,
		  	pagination:true,
		  	border:false,
		  	nowrap : true,
		  	rownumbers:true,
		  	idField : 'messageId',
		    columns:[[  
				{field:'messageId',checkbox : true}, 
		        {field:'messageTitle',title:'信息标题',width:150,align:'center'}, 
		        /*
		        {field:'messageType',title:'信息类别',width:150,align:'center',
		        	formatter: function(value,row,index){
		    			var content="";
		    			if(value==1){
		    				content=" 招聘信息";
		    			}
		    			if(value==2){
		    				content="求职信息";
		    			}
		    			if(value==3){
		    				content="资金信息";
		    			}
		    			if(value==4){
		    				content="项目信息";
		    			}
		    			if(value==99){
		    				content="其它信息";
		    			}
		    			return content;
					}	
		        }, 
		        */
		        {field:'messageTime',title:'信息时间',width:130,align:'center'},
		        {field:'messageUserName',title:'信息发送者姓名',width:100,align:'center'},
		        {field:'messageBrowseQuantity',title:'信息浏览量',width:100,align:'center'},
		        {field:'checkStatus',title:'审核状态',width:150,align:'center',
					formatter : function(value, row) {

						if(row.checkStatus == 0 ) {
							return "<span style='color: red;'>未审核</span>";
						} else if(row.checkStatus == 1 ) {
							return "<span style='color: green;'>已通过</span>";
						} else if(row.checkStatus == 2 ) {
							return "<span style='color: black;'>未通过</span>";
						}
					}
		        },
		    	{field:'operator',title:'操作',width:120,
		    		formatter: function(value,row,index){
		    			var content="";
		    			
		    				content+="<a href='javascript:void(0)' onclick='viewMessageBoard("+row.messageId+")'><img class='iconImg ext-icon-note'/>查看</a>&nbsp;";
		    				
		    				if(row.checkStatus!=2 && row.messageType != 1 && row.messageType != 2 && row.messageType != 3){
		    					content+="<a href='javascript:void(0)' onclick='replyMessageBoard("+row.messageId+")'><img class='iconImg ext-icon-note'/>回复</a>&nbsp;";
		    				}
		    				
		    				
		    			
		    				if(row.checkStatus==0){
		    					content+="<a href='javascript:void(0)' onclick='checkMessageBoard("+row.messageId+",1)'><img class='iconImg ext-icon-note_edit'/>通过</a>&nbsp;";
		    					content+="<a href='javascript:void(0)' onclick='checkMessageBoard("+row.messageId+",2)'><img class='iconImg ext-icon-note_edit'/>不通过</a>&nbsp;";
		    				}
		    			
		    			return content;
				}}
		    ]],
		    toolbar : '#toolbar',
		    onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				parent.$.messager.progress('close');
				
				
			}
		}); 
	});
	
	function removeMessageBoardGrid(){
			var rows = $("#messageBoardGrid").datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].messageId);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#messageBoardGrid").datagrid('reload');
									$("#messageBoardGrid").datagrid('unselectAll');
									window.parent.refreshMsgNum();
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
	function checkMessageBoard(id,status){
		if(status==1){
			$.ajax({
				url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
				data : {id:id,checkStatus:status},
				dataType : 'json',
				success : function(data) {
					if(data.success){
						$("#messageBoardGrid").datagrid('reload');
						$("#messageBoardGrid").datagrid('unselectAll');
						window.parent.refreshMsgNum();
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
		}else{
			var checkMessageBoard=$('<div/>').dialog({
				title : '消息审核',
				iconCls : 'icon-add',
				href : '${pageContext.request.contextPath}/page/admin/messageBoard/checkMessageBoard.jsp?id='+id+'&status='+status,
				width : 700,
				height : 400,
				modal: true ,
				buttons : [ {
					text : '保存',
					iconCls:'icon-save',
					handler : function() {
						$('#checkMessageBoardForm').form('submit', {
							url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
						  	onSubmit: function(){  
						  		return $("#checkMessageBoardForm").form('validate');
						  		$.messager.progress({
									text : '数据提交中....'
								});
					    	},
							success : function(data) {
								$.messager.progress('close');
								var json = $.parseJSON(data);
								if(json.success){
									checkMessageBoard.dialog('close');
									$("#messageBoardGrid").datagrid('reload');
									window.parent.refreshMsgNum();
									$.messager.alert('提示',json.msg,'info');
								}else{
									$.messager.alert('错误', json.msg, 'error');
								}
							}
						});
					}
				} ],onClose:function(){
					$(this).dialog('destroy');
				}
			});
		}
	}
	function viewMessageBoard(id){
			var viewMessageBoard=$('<div/>').dialog({
				title : '查看消息审核',
				iconCls : 'icon-edit',
				href : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!getById.action?id='+id,
				width : 700,
				height : 400,
				modal: true ,
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
	}
	function searchMessageBoard(){
		if ($('#searchNewsForm').form('validate')) {
			//alert(JSON.stringify(serializeObject($('#searchNewsForm'))));
			$('#messageBoardGrid').datagrid('load',serializeObject($('#searchNewsForm')));
		}
	}

	/**--重置--**/
	function resetT(){
		//$("#cityName").attr("disabled",true);
		$('#searchNewsForm')[0].reset();
		$('#type').combobox('clear');
		//('#origin').combobox('clear');
	}
	
	function replyMessageBoard(id){
			var replyMessageBoard=$('<div/>').dialog({
				title : '回复消息',
				iconCls : 'icon-edit',
				href : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!initReplyMessageById.action?id='+id,
				width : 700,
				height : 400,
				modal: true ,
				
				buttons : [ {
					text : '保存',
					iconCls:'icon-save',
					handler : function() {
						$('#replyMessageBoardForm').form('submit', {
							url : '${pageContext.request.contextPath}/mobile/messageBoard/messageBoardAction!updateCheck.action',
						  	onSubmit: function(){  
						  		return $("#replyMessageBoard").form('validate');
						  		$.messager.progress({
									text : '数据提交中....'
								});
					    	},
							success : function(data) {
								$.messager.progress('close');
								var json = $.parseJSON(data);
								if(json.success){
									replyMessageBoard.dialog('close');
									$("#messageBoardGrid").datagrid('reload');
									window.parent.refreshMsgNum();
									$.messager.alert('提示',json.msg,'info');
								}else{
									$.messager.alert('错误', json.msg, 'error');
								}
							}
						});
					}
				} ],
				
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	</div>
		<div id="toolbar" style="display: none;">
			<table>
				<form id="searchNewsForm">
				<tr>
					<th>
						标题
					</th>
					<td>
						<div class="datagrid-btn-separator"></div>
					</td>
					<td>
						<input name="messageBoard.messageTitle" style="width: 150px;" />
					</td>
					<th>
						信息发送者姓名
					</th>
					<td>
						<div class="datagrid-btn-separator"></div>
					</td>
					<td>
						<input name="messageBoard.messageUserName" style="width: 150px;" />
					</td>
					<th>
						审核状态
					</th>
					<td>
						<div class="datagrid-btn-separator"></div>
					</td>
					<td>
						<select id="checkStatus" name="messageBoard.checkStatus" style="width: 150px;">
							<option value="">&nbsp;</option>
							<option value="1">已通过</option>
							<option value="2">未通过</option>
							<option value="-1">未审核</option>
						</select>
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton"
						   data-options="iconCls:'icon-search',plain:true"
						   onclick="searchMessageBoard();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton"
						   data-options="iconCls:'ext-icon-huifu',plain:true"
						   onclick="resetT();">重置</a>
					</td>
				<tr>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeMessageBoardGrid();">删除</a>
					</td>
				</tr>
				</tr>
				</form>
			</table>
		</div>
		<div data-options="region:'center',fit:true,border:false">
			<table id="messageBoardGrid"></table>
		</div>
	</body>
</html>
