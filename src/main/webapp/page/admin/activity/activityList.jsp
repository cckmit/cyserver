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
        var activityGrid;
        $(function () {
            activityGrid = $('#activityGrid').datagrid({
                url: '${pageContext.request.contextPath}/activity/activityAction!getList.action',
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field:'id',checkbox : true},
                    {
                        width: '120',
                        title: '院系',
                        field: 'department',
                        align: 'center'
                    },
                    {
                        width: '80',
                        title: '年级',
                        field: 'grade',
                        align: 'center'
                    },
                    {
                        width: '150',
                        title: '班级',
                        field: 'clazz',
                        align: 'center'
                    },
                    {
                        width: '100',
                        title: '专业',
                        field: 'major',
                        align: 'center'
                    },
                    {
                        width: '80',
                        title: '是否需要场地',
                        field: 'needMeeting',
                        align:'center',
                        formatter : function(value, row) {
                            return row.needMeeting?"是":"否";
                        }
                    },
                    {
                        width: '50',
                        title: '是否参观',
                        field: 'needVisit',
                        align:'center',
                        formatter : function(value, row) {
                            return row.needVisit?"是":"否";
                        }
                    },
                    {
                        width: '50',
                        title: '是否就餐',
                        field: 'needDinner',
                        align:'center',
                        formatter : function(value, row) {
                            return row.needDinner?"是":"否";
                        }
                    },
                    {
                        width: '50',
                        title: '是否认捐',
                        field: 'needSubscription',
                        align:'center',
                        formatter : function(value, row) {
                            return row.needSubscription?"是":"否";
                        }
                    },
                    {
                        width: '80',
                        title: '联系人',
                        field: 'contactPerson',
                        align: 'center'
                    },
                    {
                        width: '80',
                        title: '联系电话',
                        field: 'contactPhone',
                        align: 'center'
                    },

                    {
                        width: '80',
                        title: '审核状态',
                        field: 'status',
                        align: 'center',
                        formatter : function(value, row) {
                            if(row.status == 10 ) {
                                return "<span >待审核</span>";
                            } else if(row.status == 20 ) {
                                return "<span >通过</span>";
                            } else if(row.status == 30 ) {
                                return "<span >不通过</span>";
                            }
                        }

                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '80',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看返校聚会" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改返校聚会" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            //str += '<a href="javascript:void(0)" onclick="removeFun(' + row.id + ');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
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

        function searchActivity(){
                $('#activityGrid').datagrid('load',serializeObject($('#searchForm')));

        }

        var addFun = function () {
            var dialog = parent.WidescreenModalDialog({
                title: '新增返校聚会信息',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/activity/addActivity.jsp',
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, activityGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看返校聚会信息',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/activity/viewActivity.jsp?id=' + id
            });
        };
        var editFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '编辑返校聚会信息',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/activity/editActivity.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, activityGrid, parent.$);
                    }
                }]
            });
        };
        var signupFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看报名人员',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/activity/viewSign.jsp?id=' + id
            });
        };


        function removeData(){
            var rows = $("#activityGrid").datagrid('getChecked');
            var ids = [];

            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/activity/activityAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#activityGrid").datagrid('reload');
                                    $("#activityGrid").datagrid('unselectAll');
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

        /**--重置--**/
        function resetT(){

            $('#searchForm')[0].reset();


            $('#contactPerson').prop('value','');
            $('#contactPhone').prop('value','');
            $('#status').combobox('clear');

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
                            <th >
                                联系人
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="activity.contactPerson" style="width: 150px;" />
                            </td>

                            <th align="right">
                                联系电话
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td><input name="activity.contactPhone" style="width: 150px;" />
                            </td>
                            <th>
                                审核状态
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select name="activity.status" class="easyui-combobox" style="width:155px"
                                        data-options="required:true, editable:false"
                                >
                                    <option value="">全部</option>
                                    <option value="10">待审核</option>
                                    <option value="20">已通过</option>
                                    <option value="30">未通过</option>
                                </select>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchActivity();">查询</a>
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
                            <authority:authority authorizationCode="新增返校聚会"
                                                 userRoles="${sessionScope.user.userRoles}">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'ext-icon-note_add',plain:true"
                                   onclick="addFun();">新增</a>
                            </authority:authority>
                            <authority:authority authorizationCode="删除返校聚会" userRoles="${sessionScope.user.userRoles}">
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
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="activityGrid"></table>
</div>
</body>
</html>
