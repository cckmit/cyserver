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
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/serv/servAction!getServReplyx.action?id='+$('#serviceId').val(),
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
                        title: '回复人',
                        field: 'accountNum',
                        formatter : function(value, row) {
                        	if(row.type == 0 ) {
                        		return "<span style='color: red;'>官方回复</span>";
                        	} else if(row.type == 5 ) {
                        		return "<span style='color: blue;'>校友会回复</span>";
                        	}
							if(row.userProfile!=undefined){
								return row.userProfile.name;
							}else{
								return "";
							}
						}                
                    },
                    {
                        width: '450',
                        title: '回复内容',
                        field: 'content'
                    },
                    {
                        width: '110',
                        title: '发表时间',
                        field: 'createTime'                        
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
        
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<input name="serv.id" type="hidden" id="serviceId" value="${param.id}">

<div data-options="region:'center',fit:true,border:false" style="height: 700px;">
    <table id="eventGrid"></table>
</div>

</body>
</html>