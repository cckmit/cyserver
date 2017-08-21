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
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/event/eventAction!getListAssociation.action',
                fit: true,
                border: false,
//                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field:'id',checkbox : true},
                    {
                        width: '140',
                        title: '标题',
                        field: 'title'
                    },
                    {
                        width: '120',
                        title: '地点',
                        field: 'place'
                    },
                    {
                        width: '100',
                        title: '主办方',
                        field: 'organizer'
                    },
                    {
                        width: '100',
                        title: '社团名',
                        field: 'associationName'
                    },
                    {
                        width: '50',
                        title: '类别',
                        field: 'category'
                    },
                    {
                        width: '110',
                        title: '开始时间',
                        field: 'startTime'
                    },
                    {
                        width: '110',
                        title: '结束时间',
                        field: 'endTime'
                    },
                    {
                        width: '80',
                        title: '是否签到',
                        field: 'needSignIn',
                        align:'center',
                        formatter : function(value, row) {
                            return row.needSignIn?"是":"否";
                        }
                    },
                    {
                        width: '80',
                        title: '校验码',
                        field: 'signInCode'
                    },
                    {
                        width: '80',
                        title: '活动状态',
                        field: 'nowStatus'
                    },
                    {
                        width: '80',
                        title: '报名人数',
                        field: 'signupNum',
                        align: 'center',
                        formatter: function (value, row) {
                            var str = '0';
                            if(row.signupNum > 0) {
                                str = '<a href="javascript:void(0)" onclick="signupFun(\'' + row.id + '\');">' + row.signupNum + '</a>&nbsp;';
                            }
                            return str;
                        }
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '300',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看社团活动" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.nowStatus == '已删除') {
                                <authority:authority authorizationCode="恢复官方活动" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="undoDelete(\'' + row.id + '\');"><img class="iconImg ext-icon-export_customer"/>恢复</a>&nbsp;';
                                </authority:authority>
                            } else {
                                <authority:authority authorizationCode="编辑社团活动" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                </authority:authority>
                                <authority:authority authorizationCode="查看活动花絮" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="boardFun(\'' + row.id + '\');"><img class="iconImg ext-icon-activity"/>花絮</a>&nbsp;';
                                </authority:authority>
                            }
                            return str;
                        }
                    }]],
                toolbar: '#toolbar',
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



        function searchEvent(){
            if ($('#searchForm').form('validate')) {
                $('#eventGrid').datagrid('load',serializeObject($('#searchForm')));
            }
        }

        /**--重置--**/
        function resetT(){
            $('#searchForm')[0].reset();
            $('#category').combobox('clear');
            $('#startFrom').datetimebox('setValue', '');
            $('#startTo').datetimebox('setValue', '');
            $('#endFrom').datetimebox('setValue', '');
            $('#endTo').datetimebox('setValue', '');
        }

        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增社团活动',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/association/associationActivityForm.jsp?pos=0',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看社团活动',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/association/associationActivityForm.jsp?pos=1&id=' + id
            });
        };
        var editFun = function (id) {

            var dialog = parent.WidescreenModalDialog({
                title: '编辑社团活动',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/association/associationActivityForm.jsp?pos=2&id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };
        var boardFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看活动花絮',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/event/viewEventBoard.jsp?id=' + id
            });
        };
        var signupFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看报名人员',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/event/viewSignupPeople.jsp?id=' + id
            });
        };

        function removeData(){
            var rows = $("#eventGrid").datagrid('getChecked');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/event/eventAction!deletea.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#eventGrid").datagrid('reload');
                                    $("#eventGrid").datagrid('unselectAll');
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

    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <table>
        <tr>
            <td>
                <form id="searchForm">
                    <table>
                        <tr>
                            <th align="right">
                                标题
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="title" name="title" style="width: 155px;"/>
                            </td>


                            <th align="right">
                                地点
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="place" name="place" style="width: 155px;"/>
                            </td>

                            <th align="right">
                                主办方
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="organizer" name="organizer" style="width: 155px;"/>
                            </td>

                            <th align="right">
                                类别
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="category" name="category" class="easyui-combobox" style="width: 150px;"
                                       data-options="
											valueField: 'dictName',
											textField: 'dictName',
											editable:false,
											url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('活动类别')
										" />
                            </td>

                        </tr>
                        <tr>
                            <th align="right">
                                开始时间
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="4">
                                <input name="startFrom" id="startFrom" class="easyui-datetimebox "
                                       data-options="editable:false" style="width: 150px;" /> &nbsp;&nbsp; - &nbsp;&nbsp;
                                <input name="startTo" id="startTo" class="easyui-datetimebox "
                                       data-options="editable:false" style="width: 150px;" />
                            </td>

                            <th align="right">
                                结束时间
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td colspan="4">
                                <input name="endFrom" id="endFrom" class="easyui-datetimebox "
                                       data-options="editable:false" style="width: 150px;" /> &nbsp;&nbsp; - &nbsp;&nbsp;
                                <input name="endTo" id="endTo" class="easyui-datetimebox "
                                       data-options="editable:false" style="width: 150px;" />
                            </td>

                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchEvent();">查询</a>
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
                            <authority:authority authorizationCode="创建社团活动" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">创建社团活动</a>
                            </authority:authority>
                        </td>
                        <td>
                            <authority:authority authorizationCode="删除社团活动" userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_delete',plain:true"
                                   onclick="removeData();">删除社团活动</a>
                            </authority:authority>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>
