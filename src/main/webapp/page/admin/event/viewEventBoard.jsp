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
        $(function () {
            //alert($('#eventId').val());
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/event/eventAction!getEventBoard.action?event.id='+$('#eventId').val(),
                nowrap: false,
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                columns: [[
                    {
                        width: '70',
                        title: '发表人',
                        field: 'userInfoId',
                        formatter : function(value, row) {
							if(row.userProfile!=undefined){
								return row.userProfile.name;
							}else{
								return "";
							}
						}                
                    },
                    {
                        width: '400',
                        title: '花絮内容',
                        field: 'comment'
                    },
                    {
                        width: '50',
                        title: '花絮图片',
                        align: 'center',
                        field: 'pic',
                        formatter: function (value, row) {
	                        var str = '';
							str += '<a href="javascript:void(0)" onclick="showPicFun(' + row.id + ');">查看</a>&nbsp;';
	                        return str;
                    	}  
                    },
                    {
                        width: '110',
                        title: '发表时间',
                        field: 'createTime'                        
                    },
                    {
                        width: '40',
                        title: '评论',
                        align: 'center',
                        field: 'commentNum',
                        formatter: function (value, row) {
	                        var str = '0';
	                        if(row.commentNum > 0) {
	                        	str = '<a href="javascript:void(0)" onclick="commentFun(' + row.id + ');">' + row.commentNum + '</a>&nbsp;';
	                        }						
	                        return str;
                    	}                       
                    },
                    {
                        width: '40',
                        title: '点赞',
                        align: 'center',
                        field: 'praiseNum'                        
                    },
                    {
                        width: '40',
                        title: '投诉',
                        align: 'center',
                        field: 'complaintNum'                        
                    },
                    {
						width : '100',
						title : '状态',
						field : 'status',
						formatter : function(value, row)
						{
							if(row.status != null) {
								if(row.status == '0') {
									return '正常';
								} else if(row.status == '1') {
									return '投诉处理-正常';
								} else if(row.status == '2') {
									return '投诉处理-违规';
								} else if(row.status == '3') {
									return '用户已删除';
								} else if(row.status == '4') {
									return '管理员已删除';
								}
							}
						}
					},
					{
	                    title: '操作',
	                    field: 'action',
	                    width: '80',
	                    formatter: function (value, row) {
	                        var str = '';
							if(row.status == '0' || row.status == '1' || row.status == '2') {
	                        	<authority:authority authorizationCode="处理活动花絮" userRoles="${sessionScope.user.userRoles}">
									str += '<a href="javascript:void(0)" onclick="del(' + row.id + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
								</authority:authority>
	                        } else if(row.status == '4'){
	                        	<authority:authority authorizationCode="处理活动花絮" userRoles="${sessionScope.user.userRoles}">
									str += '<a href="javascript:void(0)" onclick="undoDelete(' + row.id + ');"><img class="iconImg ext-icon-export_customer"/>恢复</a>&nbsp;';
								</authority:authority>
	                        }						
	                        return str;
	                    }
	                 }
                    ]],
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
        });

		var showPicFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看花絮图片',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/event/viewEventBoardPic.jsp?id=' + id
            });
        };
        
		var commentFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看评论',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/event/viewEventBoardComment.jsp?id=' + id
            });
        };
        
        function del (boardId){
        	$.messager.confirm('确认', '确定删除该花絮吗？', function(r) {
				if (r) {
					handleEventBoard(boardId,4);
				}
			});
        }
        function undoDelete (boardId){
        	$.messager.confirm('确认', '确定恢复该花絮吗？恢复后花絮为正常状态。', function(r) {
				if (r) {
					handleEventBoard(boardId,0);
				}
			});
        }
        
        function handleEventBoard(boardId, status){			
			$.ajax({
				url : '${pageContext.request.contextPath}/event/eventAction!handleEventBoard.action',
				data : {
					id : boardId,
					handleStatus: status
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
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">

<input name="event.id" type="hidden" id="eventId" value="${param.id}">

<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>