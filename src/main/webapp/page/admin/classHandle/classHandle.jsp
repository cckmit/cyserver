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
        var deptTree;
        $(function() {
            deptTree = $('#deptTree').tree({
                url : '${pageContext.request.contextPath}/deptInfo/deptInfoAction!getDeptAttrTreeByUserDept.action',
                state: closed,
                animate : true,
                onClick : function(node){
                    //alert(JSON.stringify(node));
                    showList( node.id );
                },
                onBeforeLoad : function(node, param) {

                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(node, data)
                {
                    parent.$.messager.progress('close');
                }
            });
        });

        var classHandle;
        $(function () {
            classHandle = $('#classHandle').datagrid({
                url : '${pageContext.request.contextPath}/classHandle/classHandleAction!dataGridByDept.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns : [[
                    {width : '100',title : '修改姓名',field : 'name',align : 'center'},
                    {width : '100',title : '旧姓名',field : 'nameOld',align : 'center'},
                    /*{width : '120',title : '修改手机号',field : 'telephone',align : 'center'},
                    {width : '120',title : '旧手机号',field : 'telephoneOld',align : 'center'},*/
                    {width : '210',title : '班级名',field : 'className',align : 'center'},
                    {width : '100',title : '提交人',field : 'classAdmin',align : 'center'},
                    {width : '100',title : '类型',field : 'type',align : 'center',
                        formatter : function(value){
                            switch(value){
                                case '0' : return '修改';
                                case '1' : return '剔除';
                            }
                        }
                    },
                    {width : '100',title : '状态', field : 'status',align : 'center',
                        formatter : function(value){
                            switch(value){
                                case '10' : return '待审核';
                                case '20' : return '已通过';
                                case '30' : return '未通过';
                            }
                        }
                    },
                    {width : '250',title : '操作',field : 'action',

                        formatter : function( value, row){
                          var content="";
                            if( row.status == 10 ){
                                if( row.type == 0){
                                    <authority:authority authorizationCode="审核班级管理员修改申请" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="pass(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>允许修改</a>&nbsp;';
                                    </authority:authority>
                                    <authority:authority authorizationCode="审核班级管理员修改申请" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="drop(\''+row.id+'\')"><img class="iconImg ext-icon-note_delete"/>不许修改</a>&nbsp;';
                                    </authority:authority>
                                }
                                if( row.type == 1 ){
                                    <authority:authority authorizationCode="审核班级管理员修改申请" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="pass(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>允许剔除</a>&nbsp;';
                                    </authority:authority>
                                    <authority:authority authorizationCode="审核班级管理员修改申请" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="drop(\''+row.id+'\')"><img class="iconImg ext-icon-note_delete"/>阻止剔除</a>&nbsp;';
                                    </authority:authority>
                                }

                            }
                          return content;
                        }
                    }
                 ]],
                toolbar : '#Toolbar',
                onBeforeLoad : function(param)
                {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data)
                {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

        function showList(id){
            classHandle.datagrid('reload', {'deptId': id});
            $('#deptId').val(id);
        }

        function searchClassHandle(){
            if($('#searchForm').form('validate')){
                $('#classHandle').datagrid('reload', serializeObject($('#searchForm')));
            }
        }

        function resetT() {
            $('#classAdmin').val('');
            $('#status').combobox('clear');
            $('#type').combobox('clear');
        }

        function pass(id) {
            var json={'checkId': id, 'checkStatus':'20'};
            $.ajax({
                url : "${pageContext.request.contextPath}/classHandle/classHandleAction!checkChanges.action",
                data : json,
                dataType : 'json',
                success : function(result)
                {
                    if (result.success){
                        classHandle.datagrid('reload');
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
        }

        function drop(id) {
            var json={'checkId': id, 'checkStatus':'30'};
            $.ajax({
                url : "${pageContext.request.contextPath}/classHandle/classHandleAction!checkChanges.action",
                data : json,
                dataType : 'json',
                success : function(result)
                {
                    if (result.success){
                        classHandle.datagrid('reload');
                        classHandle.datagrid('unselectAll');
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
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="Toolbar">
    <form id="searchForm">
        <table>
            <tr>
                <input name="deptId" id="deptId" style="display: none">
                <th>
                    班级管理员姓名
                </th>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>
                <td>
                    <input name="classHandle.classAdmin" id="classAdmin" style="width: 120px;"/>
                </td>
                <th>
                    状态
                </th>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>
                <td>
                    <select name="classHandle.status" id="status" class="easyui-combobox" style="width: 120px;"
                            data-options="editable:false,panelHeight:80"
                    />
                    <option value=""></option>
                    <option value="10">待审核</option>
                    <option value="20">已通过</option>
                    <option value="30">未通过</option>
                </td>


                <th>
                    操作类型
                </th>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>
                <td>
                    <select name="classHandle.type" id="type" class="easyui-combobox" style="width: 120px;"
                            data-options="editable:false,panelHeight:80"
                    />
                    <option value=""></option>
                    <option value="0">修改操作</option>
                    <option value="1">移出操作</option>
                </td>

                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true"
                       onclick="searchClassHandle();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                       onclick="resetT()">重置</a>
                </td>
            </tr>

        </table>
    </form>
</div>
<div data-options="region:'west',border:1" width="18%">
    <ul id="deptTree"></ul>
</div>
<div  data-options="region:'center',fit:false,border:false" width="82%">
    <table id="classHandle"></table>
</div>
</body>
</html>
