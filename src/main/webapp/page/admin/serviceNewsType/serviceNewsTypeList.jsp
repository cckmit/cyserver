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

    <title>服务新闻栏目管理</title>

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
                url : '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!serviceNewsTypeTree.action',
                idField : 'id',
                treeField : 'name',
                parentField : 'parentId',
                rownumbers : true,
                pagination : false,
                frozenColumns : [ [ {
                    width : '180',
                    title : '名称',
                    field : 'name'
                } ] ],
                columns:[[
                    {field:'isNavigation',title:'导航显示',width:150,align:'center',
                        formatter: function(value,row,index){
                            if(row.isNavigation==0){
                                return "×";
                            }else if(row.isNavigation==1){
                                return "√";
                            }
                        }
                    },
                    {field:'serviceName',title:'所属服务',width:150,align:'center'},
                    {field:'orderNum',title:'排序编号',width:150,align:'center'},
                    {field:'operator',title:'操作',width:300,
                        formatter: function(value,row,index){
                            var content = "";
                            <authority:authority authorizationCode="查看服务新闻栏目" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="viewType(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改服务新闻栏目" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editType(\''+row.id+'\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="删除服务新闻栏目" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="removeType(\''+row.id+'\');"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
                            </authority:authority>
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

        /**--查询新闻栏目--**/
        function searchTypes(){
            if ($('#searchNewsForm').form('validate')) {
                $('#treeGrid').treegrid('load',serializeObject($('#searchNewsForm')));
            }
        }

        /**--清空查询条件--**/
        function resetT() {
            $('#name').val('');
            $('#serviceId').combobox('clear');
        }

        /**--查看类型详情--**/
        function viewType(id) {
            var dialog = parent.modalDialog({
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNewsType/view.jsp?id='+id
            });
        }

        /**--新增类型--**/
        function addType() {
            var dialog = parent.modalDialog({
                title : '新增栏目',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNewsType/add.jsp',
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
        function editType(id) {
            var dialog = parent.modalDialog({
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNewsType/edit.jsp?id='+id,
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
                        url : '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!delete.action',
                        data : { 'serviceNewsTypeId' : id },
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
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="type.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    所属服务
                                </th>
                                <td>
                                    <input name="type.serviceId" id="serviceId" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
							        valueField: 'id',
							        textField: 'serviceName',
							        url: '${pageContext.request.contextPath}/mobile/schoolServ/schoolServAction!doNotNeedSessionAndSecurity_getServiceList.action'" />
                                </td>
                                <th>
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
                                <authority:authority authorizationCode="新增服务新闻栏目" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addType();">新增</a>
                                </authority:authority>
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
