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
    var alumniTree;
    var currNodeId = 0; //lixun 当前行ID
    $(function() {
        alumniTree = $('#alumniTree').tree({
            url : '${pageContext.request.contextPath}/alumni/alumniAction!getAlumniTree.action',
            state: closed,
            animate : true,
            onClick : function(node){
                currNodeId = node.id;   //设置当前行ID
               /* $(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);
                node.state = node.state === 'closed' ? 'open' : 'closed';*/
                showList(node.id);

            },
            onBeforeLoad : function(node, param)
            {

                parent.$.messager.progress({
                    text : '数据加载中....'
                });
            },
            onLoadSuccess : function(node, data)
            {
                //alert( JSON.stringify(data) );
                parent.$.messager.progress('close');
            }
        });
    });
        var userGrid;
        $(function(){
            userGrid = $('#userGrid').datagrid({
                url : '${pageContext.request.contextPath}/alumni/alumniAction!dataGridAdmin.action',
                fit : true,
                border : true,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                singleSelect : true,
                idField : 'userId',
                columns : [ [
                    {
                        width : '12%',
                        title : '用户姓名',
                        field : 'userName',
                        align:'center'
                    },{
                        width : '12%',
                        title : '用户帐号',
                        field : 'userAccount',
                        align:'center'
                    },{
                        width : '9%',
                        title : '电话号码',
                        field : 'userTelephone',
                        align:'center'
                    },{
                        width : '15%',
                        title : '邮箱',
                        field : 'userEmail',
                        align:'center'
                    },
                    {
                        width : '12%',
                        title : '角色',
                        field : 'roleName',
                        align:'center'
                    }
                    ,{
                        width : '12%',
                        title : '所属机构',
                        field : 'alumniName',
                        align : 'center'
                    }/*,{
                        width : '12%',
                        title : '所属院系',
                        field : 'diFullName',
                        align : 'center'
                    }*/
                    ,{
                        width : '20%',
                        title : '操作',
                        field : 'action',
                        align : 'center',
                        formatter : function(value, row) {
                            var str = '';
                            <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="查看用户">
                            str += '<a href="javascript:void(0)" onclick="showFun('+ row.userId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="编辑用户">
                            str += '<a href="javascript:void(0)" onclick="editFun('+ row.userId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            if(row.userAccount != 'zonghui'){
                                <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="删除用户">
                                str += '<a href="javascript:void(0)" onclick="removeFun('+ row.userId + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
                                </authority:authority>
                            }
                            return str;
                        }
                    }] ],
                toolbar : '#toolbar',
                onBeforeLoad : function(param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data) {
                    $('.iconImg').attr('src', pixel_0);
                    //alert(JSON.stringify(data));
                    parent.$.messager.progress('close');
                }
            });
        });

        function showList(id) {

            var json = {'aluid': id};
            $('#userGrid').datagrid('load', json);
        }
        function searchAccount() {
            //alert($('#searchForm').serialize());
            userGrid.datagrid('load',serializeObject($('#searchForm')));
        }
        //datagrid数据格式过滤器
        function loadFilter(data){
            var value = {
                total:data.total,
                rows:[]
            };
            for (var i = 0; i < data.rows.length; i++) {
                var o = {};
                if(rows[i].flag == 1){
                    value.rows.push(o);
                }
            }
            return value;
        }

        function addFun(){
            var dialog = parent.WidescreenModalDialog({
                title : '新增用户信息',
                iconCls : 'ext-icon-note_add',
                url :  '${pageContext.request.contextPath}/page/admin/orgManagers/Form.jsp?id=0&nodeId='+currNodeId,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, userGrid, parent.$);
                    }
                } ]
            });
        }
        var showFun = function(id) {
            var dialog = parent.WidescreenModalDialog({
                title : '查看用户信息',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/orgManagers/View.jsp?id=' + id+ '&nodeId=0'
            });
        };
        var editFun = function(id) {
            var dialog = parent.WidescreenModalDialog({
                title : '编辑用户信息',
                iconCls : 'ext-icon-note_edit',
                url : '${pageContext.request.contextPath}/page/admin/orgManagers/Form.jsp?id=' + id + '&nodeId=0',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, userGrid, parent.$);
                    }
                } ]
            });
        };
        var removeFun = function(id) {
            parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/user/userAction!delete.action',
                        data : {
                            id : id
                        },
                        dataType : 'json',
                        success : function(result) {
                            if (result.success) {
                                userGrid.datagrid('reload');
                                parent.$.messager.alert('提示', result.msg, 'info');
                            } else {
                                parent.$.messager.alert('提示', result.msg, 'error');
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
            });
        };
    function resetT() {
        $('#userName').val('');
        $('#userAccount').val('');
        userGrid.datagrid('load', serializeObject($('#searchForm')));
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
                            <th>
                                用户姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                            <td>
                                <input name="userName" id="userName" style="width: 150px;" />
                            </td>
                            <th>
                                用户帐号
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="userAccount" id="userAccount" style="width: 150px;" />
                            </td>
                            <td>
                                <input name="aluid" id="aluid" value="1" type="hidden" />
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchAccount();">查询</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                   onclick="resetT();">重置</a>
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
                            <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="新增用户">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增</a>
                            </authority:authority>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:false,border:false" width="82%">
    <table id="userGrid"></table>
</div>
</body>
</html>
