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

    <title>微信菜单</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var grid;
        $(function () {
            grid = $('#treeGrid').treegrid({
                url : '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!weiXinMenuTree.action',
                idField : 'id',
                treeField : 'name',
                parentField : 'parentId',
                rownumbers : true,
                pagination : false,
                columns:[[
                    {field:'deptId',checkbox:true},
                    //{field:'id',checkbox : true},
                    {field:'name',title:'名称',width:250,
                        formatter: function(value,row,index){
                            if(row.parent_id==0){
                                return value;
                            }else{
                                return value;
                            }
                        }
                    },
                    {field:'accountName',title:'公众号名称',width:250,align:'center'},
                    {field:'type',title:'类型',width:250,align:'center',
                        formatter: function(value,row,index){
                            if(value==10){
                                return "消息触发类";
                            }else if(value==20){
                                return "网页链接类";
                            }else if(value==30){
                                return "父级菜单类";
                            }
                        }
                    },
                    {field:'menuKey',title:'唯一标识',width:250,align:'center'},
                    {field:'operator',title:'操作',width:300,
                        formatter: function(value,row,index){
                            var content = "";
                            <authority:authority authorizationCode="查看微信菜单" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewType(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改微信菜单" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editType(\''+row.id+'\',\''+row.parentId+'\',\''+row.accountId+'\',\''+row.accountName+'\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="删除微信菜单" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="removeType(\''+row.id+'\');"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
                            </authority:authority>
                            if( row.parentId == 0 && row.type== 30){
                                <authority:authority authorizationCode="新增微信菜单" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="addType1(\''+row.id+'\',\''+row.name+'\',\''+row.accountId+'\',\''+row.accountName+'\')"><img class="iconImg ext-icon-note"/>添加子菜单</a>&nbsp;';
                                </authority:authority>
                            }
                            return content;
                        }
                    }
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

        /**--查询--**/
        function searchTypes(){
            if ($('#searchNewsForm').form('validate')) {
                $('#treeGrid').treegrid('load',serializeObject($('#searchNewsForm')));
            }
        }

        /**--清空查询条件--**/
        function resetT() {
            $('#name').val('');
            $('#accountId').combobox('clear');
        }

        /**--查看菜单详情--**/
        function viewType(id) {
            var dialog = parent.modalDialog({
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/weiXin/view.jsp?id='+id
            });
        }

        /**--新增菜单--**/
        function addType() {
            var dialog = parent.modalDialog({
                title : '新增微信菜单',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/weiXin/add.jsp?parentId=0',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**--新增子菜单--**/
        function addType1(id,name,accountId,accountName) {
            var dialog = parent.modalDialog({
                title : '新增二级菜单',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/weiXin/add.jsp?parentId=' + id+'&parentName='+name+'&accountId='+accountId+'&accountName='+accountName,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }
        /**--编辑类型--**/
        function editType(id,parentId,accountId,accountName) {
            var dialog = parent.modalDialog({
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/weiXin/edit.jsp?id='+id+'&parentId='+parentId+'&accountId='+accountId+'&accountName='+accountName,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }
        /**--删除类型--**/
        function removeType(id) {
            $.messager.confirm('确认', '确定删除吗？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!delete.action',
                        data : { 'weiXinMenuId' : id },
                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                grid.treegrid('reload');
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
        }

        /**
         * 同步微信菜单
         */
        function toWeiXinMenu(type) {
            $.ajax({
                url : '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!toWeiXinMenu.action',
                data : { 'accountType' : type },
                dataType : 'json',
                success : function(data) {
                    if(data.success){
                        grid.treegrid('reload');
                        $.messager.alert('提示',data.msg,'info');
                    }
                    else{
                        $.messager.alert('错误', data.msg, 'error');
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
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar">
        <table>
            <tr>
                <td>
                    <form id="searchNewsForm">
                        <table>
                            <tr>
                                <th>
                                    名称
                                </th>
                                <%--<td>--%>
                                    <%--<div class="datagrid-btn-separator"></div>--%>
                                <%--</td>--%>
                                <td>
                                    <input name="weiXinMenu.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    公众号
                                </th>
                                <td>
                                    <select id="accountId" class="easyui-combobox" name="weiXinMenu.accountId"  data-options="
                                editable:false,
                                valueField:'id',
                                state:'open',
                                textField:'accountName',
                                url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!doNotNeedSessionAndSecurity_getList.action',
                                " style="width: 150px;">
                                    </select>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchTypes();">查询</a>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-huifu',plain:true"
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
                                <authority:authority authorizationCode="新增微信菜单" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addType();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-export_customer',plain:true"
                                   onclick="toWeiXinMenu('10');">同步校友会公众号菜单</a>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-export_customer',plain:true"
                                   onclick="toWeiXinMenu('20');">同步基金会公众号菜单</a>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="treeGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
</body>
</html>
