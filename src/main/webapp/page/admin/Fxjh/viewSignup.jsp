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
        var id="${param.id}";
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!doNotNeedSessionAndSecurity_getSignPeople.action?fxjh.id='+id,
                fit: true,
                border: false,
                fitColumns: false,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                columns: [[
                    {
                        width: '50',
                        title: '姓名',
                        field: 'name',
                        align:'center'
                    },
                    {
                        width: '40',
                        title: '性别',
                        field: 'sex',
                        align:'center',
                        formatter : function(value, row) {
                            if(row.sex == '0') {
                                return "男";
                            } else if(row.sex == '1') {
                                return "女";
                            } else {
                                return "";
                            }
                        }
                    },
                    {
                        width: '80',
                        title: '电话',
                        field: 'phoneNum',
                        align:'center'
                    },
                    {
                        width: '120',
                        align:'center',
                        title: '电子邮箱',
                        field: 'email'
                    },
                    {
                        width : '240',
                        title : '学习经历',
                        field : 'groupName',
                        align : 'center',
                        formatter : function(value, row)
                        {
                            var text='';
                            if(value != null) {
                                var array = value.split('_');
                                for(var i=0;i<array.length;i++){
                                    if(i==array.length-1){
                                        text+=array[i];
                                    }
                                    else{
                                        text+=array[i]+ "<br />";
                                    }
                                }
                            }
                            return text;
                        }
                    },
                    {field:'isSignIn',title:'是否签到',width:60,align:'center',
                        formatter:function (value) {
                            if(value == '1')
                                return '是';
                            else
                                return '否';}
                    },
                    {field:'signInTime',title:'签到时间',width:120,align:'center'},
                    {field:'services',title:'返校服务',width:300,align:'center'},
                    {field:'servicesRemarks',title:'服务备注',width:600,align:'center'}
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

<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>