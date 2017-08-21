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
                url : '${pageContext.request.contextPath}/mobile/serv/znfxAction!dataGraid.action?status=20,30,40,50',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'topic',title:'组织主题',width:120,align:'center'},
                    {field:'place',title:'地点',width:100,align:'center'},
                    {field:'name',title:'发起人',width:100,align:'center'},
                    {field:'type',title:'类型',width:60,align:'center',
                        formatter:function (value) {
                            if(value == '0')
                                return '个人';
                            else
                                return '官方';
                    }},
                    {field:'number',title:'计划人数',width:60,align:'center'},
                    {field:'time',title:'返校时间',width:100,align:'center'},
                    {width: '110',title: '结束时间',field: 'endTime',align:'center'},
                    {field:'needSignIn',title:'是否签到',width:60,align:'center',
                        formatter:function (value) {
                            if(value == '1')
                                return '是';
                            else
                                return '否';
                        }},
                    {field:'signInCode',title:'签到码',width:60,align:'center'},
                    {field:'classInfoName',title:'班级信息',width:250,align:'center',
                        formatter: function (value) {
                            var content = '';
                            if(value){
                                var array = value.split(',');
                                for(var i = 0; i < array.length; i++){
                                    content += array[i] +' '
                                }
                            }
                            return content;
                        }
                    },
                    {field:'countMember', title:'报名人数', width:60,align:'center',
                        formatter: function (value, row) {
                            return '<a href="javascript:void(0)" onclick="signupFun(\'' + row.id + '\');">' + value + '</a>&nbsp;';
                        }
                    },
                    {field:'status',title:'状态',width:100,align:'center',
                        formatter: function (value, row) {
                            var result = '';
                            switch(value){
                                case '10': result = '审核中';
                                    break;
                                case '20': result = '审核通过';
                                    break;
                                case '30': result = '审核不通过';
                                    break;
                                case '40': result = '下线';
                                    break;
                                case '50': result = '取消';

                            }
                            return result;
                        }
                    },
                    {field:'operator',title:'操作',width:'150px',
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看审核计划" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="view(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.type=='1'){
                                <authority:authority authorizationCode="修改返校计划" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="edit(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>
                            }
                            if(row.status=='20'){
                                <authority:authority authorizationCode="查看审核计划" userRoles="${sessionScope.user.userRoles}">
                                    content+='<a href="javascript:void(0)" onclick="dropDown(\'' + row.id + '\')"><img class="iconImg ext-icon-note_delete"/>下线</a>&nbsp;';
                                </authority:authority>
                            }

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

        var signupFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看报名人员',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/Fxjh/viewSignup.jsp?id=' + id
            });
        };

        /**
         * 查看返校计划
         */
        function view(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/Fxjh/view.jsp?id=' + id
            });
        }

        function add() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增返校计划',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/Fxjh/form.jsp?id=' + 0,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        function edit(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑返校计划',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/Fxjh/form.jsp?id=' + id,
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
         * 下线
         * @param id
         */
        function dropDown(id){

            $.messager.confirm('确认', '确定下线吗？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/mobile/serv/znfxAction!audit.action?fxjh.id='+id+'&fxjh.status=40',

                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                $('#associationGrid').datagrid('reload');
                                $('#associationGrid').datagrid('unselectAll');
                                $.messager.alert('提示','下线成功','info');
                            }
                            else{
                                $.messager.alert('错误', '下线失败', 'error');
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


        function removeData(){
            var rows = $("#associationGrid").datagrid('getChecked');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/mobile/serv/znfxAction!deleteFxjh.action',
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



        function resetT(){
            $('#topic').val('');
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
                                    组织主题
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="topic" id="topic" style="width: 150px;" />
                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchAssociation();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table>
                                        <tr>

                                            <td>
                                                <authority:authority authorizationCode="新增返校计划" userRoles="${sessionScope.user.userRoles}">
                                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                                   onclick="add();">新增</a>
                                                </authority:authority>
                                            </td>
                                            <td>
                                                <authority:authority authorizationCode="删除返校计划" userRoles="${sessionScope.user.userRoles}">
                                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                                       onclick="removeData();">删除</a>
                                                </authority:authority>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </form>
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