<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String notifyId = request.getParameter("notifyId");
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
            grid=$('#notifyGrid').datagrid({
                url : '${pageContext.request.contextPath}/notify/notifyAction!dataGridUser.action?notifyId=<%=notifyId %>',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,

                columns : [ [    {
                    width : '80',
                    title : '姓名',
                    field : 'name',
                    sortable : true
                },

                    {
                        width : '100',
                        title : '电话号',
                        field : 'phoneNum',
                        sortable : true
                    },
                    {
                        width : '300',
                        title : '分组',
                        field : 'groupName',
                        sortable : true,
                        formatter: function(value,row,index){
                            //alert(value + " , " + index + JSON.stringify(row));
                            if(value){
                                return value.replace('_','<br>');
                            }
                        }

                    },
                    {
                        width : '80',
                        title : '是否已读',
                        field : 'isRead',
                        sortable : true,
                        formatter: function(value,row,index){
                            if(value==1){
                                return "是";
                            }
                            if(value==0){
                                return "否";
                            }
                        }
                    },
                    {
                        width : '70',
                        title : '已读时间',
                        field : 'date',
                        sortable : true
                    },



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

        function resetT(){
            $('#title').val('');
            $('#serviceId').combobox("clear");
            $('#channel').combobox("clear");
            $('#channel').combobox("loadData",[]);
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">

    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="notifyGrid" ></table>
    </div>
</div>
</body>
</html>
