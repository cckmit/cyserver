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
            grid=$('#associationGrid').datagrid({
                url : '${pageContext.request.contextPath}/association/associationAction!dataGraidAssociation.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'社团名称',width:150,align:'center'},
                    {field:'alumniName',title:'所属院系',width:100,align:'center'},
                    {field:'memberCount',title:'成员人数',width:100,align:'center',
                        formatter: function(value){
                            return value?value:"0";
                        }
                    },
                    {field:'concatName',title:'会长姓名',width:100,align:'center'},
                    {field:'telephone',title:'联系电话',width:100,align:'center'},
                    {field:'userAccount',title:'后台管理员账号',width:100,align:'center'},
                    {field:'changeTime',title:'变更时间',width:100,align:'center'},
                    {field:'top',title:'是否重点社团',width:100,align:'center',
                        formatter: function(value){
                            var content = "否";
                            if(value && value == '100'){
                                content = '是';
                            }
                            return content;
                        }
                    },
                    {field: 'remarks',title: '备注',width: '100',align: 'center'},
                    {field:'operator',title:'操作',align:'center',width:"120px",
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看组织" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewAssociation(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改组织" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editAssociation(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            return content;
                        }}
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

        function searchAssociation(){
            if ($('#searchNewsForm').form('validate')) {
                $('#associationGrid').datagrid('load',serializeObject($('#searchAssociationForm')));
            }
        }


        function addAssociation() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/association/addAssociation.jsp',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 编辑流水
         */
        function editAssociation(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/association/edit.jsp?id='+id,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 查看流水
         */
        function viewAssociation(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/association/view.jsp?id=' + id
            });
        }


        function removeAssociation(){

            var rows = $("#associationGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/association/associationAction!deleteAssociation.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#associationGrid").datagrid('reload');
                                    $("#associationGrid").datagrid('unselectAll');
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
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
        }


        function setTop(top){

            var rows = $("#associationGrid").datagrid('getChecked');
            var ids = [];
            var content  = '确定设置重点社团？';
            if(top == 0){
                content = '确定取消重点社团?';
            }
            if (rows.length > 0) {
                $.messager.confirm('确认', content, function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/association/associationAction!setTopAssociation.action',
                            data : {
                                'ids' : ids.join(','),
                                'top' : top
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#associationGrid").datagrid('reload');
                                    $("#associationGrid").datagrid('unselectAll');
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
            } else {
                $.messager.alert('提示', '请选择要设置社团的记录！', 'error');
            }
        }

        function resetT(){
            $('#name').val('');
            $('#type').combobox('clear');
            $('#top').combobox('clear');
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchAssociationForm">
                        <table>
                            <tr>

                                <th>
                                    社团名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="association.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    社团类型
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="association.typeId" id="type" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/association/associationAction!doNotNeedSecurity_getAssociationType.action'" />

                                </td>
                                <th>
                                    是否重点社团
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select name="association.top" id="top" class="easyui-combobox"
                                            style="width: 150px;" data-options="editable:false">
                                        <option value="" selected></option>
                                        <option value="0">否</option>
                                        <option value="100">是</option>
                                    </select>
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchAssociation();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
                                <authority:authority authorizationCode="新增组织" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addAssociation();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除组织" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeAssociation();">删除</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="设置社团上幻灯片" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_edit',plain:true"
                                       onclick="setTop(100);">设置重点社团</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="设置社团上幻灯片" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_edit',plain:true"
                                       onclick="setTop(0);">取消重点社团</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="associationGrid" ></table>
    </div>
</div>
</body>
</html>