<%--
  Created by IntelliJ IDEA.
  User: jiangling
  Date: 8/9/16
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>

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
    <base href="<%=basePath%>"/>
    <title>微信新闻栏目管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>

    <script type="text/javascript">
        var grid;
        $(function(){
            grid=$("#wechatNewsTreeGrid").treegrid({
                url : '${pageContext.request.contextPath}/wechatNewsType/wechatNewsTypeAction!treeGrid.action',
//                fitColumns:true,
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
                    /*{field:'id',checkbox : true},
                    {field:'name',title:'名称',width:150,align:'center'},*/
                    {field:'parentId',title:'级别',width:60,align:'center',
                        formatter: function(value){
                            if(value==0){
                                return "一级";
                            }else{
                                return "二级";
                            }
                        }
                    },
                    {field:'type',title:'类型',width:100,align:'center',
                        formatter: function(value,row,index){
                            if(value==1){
                                return "新闻类别";
                            }else if(value==2){
                                return "链接";
                            }else if(value==3){
                                return "单页面";
                            }
                        }
                    },
                    {field:'deptName',title:'创建组织',width:100,align:'center'},
                    /*{field:'origin',title:'来源',width:100,align:'center',
                        formatter: function(value,row,index){
                            if(value==1){
                                return "总会";
                            }else if(value==2){
                                return "地方";
                            }
                        }
                    },
                    {field:'cityName',title:'地域',width:100,align:'center',
                        formatter: function(value,row,index){
                            if(row.origin==1){
                                return "---";
                            }else if(row.origin==2){
                                return value;
                            }
                        }
                    },*/
                    {field:'isNavigation',title:'导航显示',width:80,align:'center',
                        formatter: function(value,row,index){
                            if(row.isNavigation==0){
                                return "×";
                            }else if(row.isNavigation==1){
                                return "√";
                            }
                        }
                    },
                    {field:'orderNum',title:'排序编号',width:80,align:'center'},
                    {field:'operator',title:'操作',width:300,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看微信新闻栏目" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewType('+row.id+')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑微信新闻栏目" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editType('+row.id+')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="删除微信新闻栏目" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="removeType('+row.id+')"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
                            </authority:authority>
                            if( row.parentId == 0 ){
                                <authority:authority authorizationCode="新增微信新闻栏目" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="addType2('+row.id+','+row.origin+')"><img class="iconImg ext-icon-note"/>添加子栏目</a>&nbsp;';
                                </authority:authority>
                            }
                            return content;
                        }}
                        ]],

                toolbar : '#wechatNewsToolbar',
                onBeforeLoad : function() {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(result) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');

                }

            });
        });

        /**--查询--**/
        function searchWechatNewsType() {
            if ($('#searchWechatNewsForm').form('validate')) {
                $('#wechatNewsTreeGrid').treegrid('load',serializeObject($('#searchWechatNewsForm')));
            }
        }

        /**--重置--**/
        function resetT(){
            $('#searchWechatNewsForm')[0].reset();
            $('#type').combobox('clear');
            $('#origin').combobox('clear');
        }

        /**--查看类型详情--**/
        function viewType(id) {
            var dialog = parent.modalDialog({
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/wechatNewsType/viewWechatNewsType.jsp?id=' + id
            });
        }
        /**--新增类型--**/
        function addType() {
            var dialog = parent.modalDialog({
                title : '新增栏目',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/wechatNewsType/addWechatNewsType.jsp',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**--新增子栏目--**/
        function addType2(id,origin) {
            var dialog = parent.modalDialog({
                title : '新增二级栏目',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/wechatNewsType/addWechatNewsType.jsp?parent_id=' + id+'&origin='+origin,
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
                url : '${pageContext.request.contextPath}/page/admin/wechatNewsType/editWechatNewsType.jsp?id='+id,
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
        function removeType(id){
            $.messager.confirm('确认', '确定删除吗？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/wechatNewsType/wechatNewsTypeAction!deleteWeChatNewsType.action',
                        data : {"weId" : id},
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
<div id="wechatNewsToolbar" style="display:none;">
    <table>
        <tr>
            <td>
                <form id="searchWechatNewsForm">
                    <table>
                        <tr>
                            <th>名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="name" name="wechatNewsType.name" style="width: 150px;">
                            </td>
                            <th>
                                类型
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select id="type" name="wechatNewsType.type" style="width: 150px;">
                                    <option value="">&nbsp;</option>
                                    <option value="1">新闻类别</option>
                                    <option value="2">链接</option>
                                    <option value="3">单页面</option>
                                </select>
                            </td>
                            <th>
                                来源
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select id="origin" name="wechatNewsType.origin"  style="width: 150px;" onchange="selectOrigin();">
                                    <option value="">&nbsp;</option>
                                    <option value="1">总会</option>
                                    <option value="2">地方</option>
                                </select>
                            </td>

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchWechatNewsType();">查询</a>
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
                            <authority:authority authorizationCode="新增微信新闻栏目" userRoles="${sessionScope.user.userRoles}">
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
    <table id="wechatNewsTreeGrid" data-options="fit:true,border:false"></table>
</div>
</div>
</body>
</html>
