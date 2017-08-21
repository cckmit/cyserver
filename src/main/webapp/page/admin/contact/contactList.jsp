<%@ page language="java" pageEncoding="UTF-8" %>
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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var eventGrid;
        var categoryStr;
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/contact/contactAction!getList.action?category='+$('#category').val(),
                nowrap: true,
                fit: true,
                border: false,
//                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                	{field:'id',checkbox : true}, 
                	{
	                    width: '150',
	                    title: '标题',
	                    field: 'title'
                	},
                	{
	                    width: '250',
	                    title: '内容',
	                    field: 'content'
                	},
                    {
                        width: '80',
                        title: '发表人',
                        field: 'accountNum',
                        formatter : function(value, row) {
							if(row.userProfile!=undefined){
								return row.userProfile.name;
							}else{
								return "";
							}
						}    
                    },
                    {
                        width: '120',
                        title: '发表时间',
                        field: 'createTime'
                    },
                    {
                        width: '100',
                        title: '状态',
                        field: 'status',
                        formatter : function(value, row)
						{
							if(row.status != null) {
								if(row.status == '0') {
									return '正常';
								} else if(row.status == '1') {
									return '用户已删除';
								} else if(row.status == '2') {
									return '管理员已删除';
								}
							}
						}
                    },
                    {
	                    title: '操作',
	                    field: 'action',
	                    width: '150',
	                    formatter: function (value, row) {			        	
	                        var str = '';                       
							str += '<a href="javascript:void(0)" onclick="showFun(' + row.id + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
							
							if(row.status == 1 || row.status == 2) {                        	
								//str += '<a href="javascript:void(0)" onclick="undoDelete(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>恢复</a>&nbsp;';
	                        } else {
								str += '<a href="javascript:void(0)" onclick="replyFun(' + row.id + ');"><img class="iconImg ext-icon-micro"/>回复</a>&nbsp;';
	                        }
	                          
	                        return str;
	                    }
	                 },
	                 {
                        width: '300',
                        title: '回复内容',
                        field: 'replyContent'
                    },
                ]],
                toolbar: '#toolbar',
                onBeforeLoad: function (param) {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess: function (data) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
            
            var category = $('#category').val();
        	if(category == 1) {
        		categoryStr = '联系总会';
        	} else if(category == 2) {
        		categoryStr = '联系学院';
        	} else if(category == 3) {
        		categoryStr = '联系会长';
        	}
        });
        
        
        var replyFun = function (id) {
            var dialog = parent.modalDialog({
                title: '官方回复',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/contact/replyContact.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看' + categoryStr,
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/contact/viewContact.jsp?id=' + id
            });
        };

        function removeData(){
			var rows = $("#eventGrid").datagrid('getChecked');
			var ids = [];
			
			if (rows.length > 0) {
				$.messager.confirm('确认', '确定删除吗？', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						}
						$.ajax({
							url : '${pageContext.request.contextPath}/contact/contactAction!delete.action',
							data : {
								ids : ids.join(',')
							},
							dataType : 'json',
							success : function(data) {
								if(data.success){
									$("#eventGrid").datagrid('reload');
									$("#eventGrid").datagrid('unselectAll');
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
        
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <input id="category" name="category" type="hidden" value="${param.category}"/>
	<table>
		<tr>
            <td>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-note_delete',plain:true"
					onclick="removeData();">删除</a>
			</td>
		</tr>
	</table>           
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>
