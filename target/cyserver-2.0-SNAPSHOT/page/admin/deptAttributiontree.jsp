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
    <jsp:include page="../../inc.jsp"></jsp:include>
    <script type="text/javascript">

        var regionGrid;
        $(function(){
            regionGrid = $('#regionGrid').treegrid({
                url : '${pageContext.request.contextPath}/dept/deptAction!findbofreDeptTree.action',
//					fitColumns:true,
                fit:true,
                idField : 'id',
                parentField : 'pid',
                treeField : 'text',
                rownumbers : true,
                pagination : false,
                columns : [[
                    {
                        width : '400',
                        title : '学院',
                        field : 'text',
                    },{
                        width : '215',
                        align : 'center',
                        title : '是否现有',
                        field : 'children',
                        formatter : function(value,row) {
                            var obj = jQuery.extend({}, row.attributes);
                            var v = obj.isCurrent;
                            if(v=='1'){
                                return '是';
                            }
                            if(v=='0'){
                                return '否';
                            }
                        }
                    }
                    ,{
                        width : '300',
                        align : 'center',
                        title : '归属院系',
                        field : 'belongDeptName',
                        formatter : function(value,row) {
                            var obj = jQuery.extend({}, row.attributes);
                            var v = obj.belongDeptName;
                            if(v!=null){
                                return v;
                            }else{
                                return '';
                            }
                        }
                    }
                    ] ],
                onBeforeExpand:function(row, param){ //在树展开前执行方法

                },
                onBeforeLoad : function(row, param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(row, data) {
                    parent.$.messager.progress('close');
                }
            });
        });
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false">
    <table id="regionGrid" style="height: 500px;"></table>
</div>
</body>
</html>
