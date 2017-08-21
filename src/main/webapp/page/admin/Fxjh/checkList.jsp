<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority" %>
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
                url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!dataGraid.action?status=10',
                fit: true,
                border: false,
//                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {field: 'id', checkbox: true},
                    {
                        width: '150',
                        title: '组织主题',
                        field: 'topic'
                    },
                    {
                        width: '150',
                        title: '计划人数',
                        field: 'number'
                    },
                    {
                        width: '110',
                        title: '返校时间',
                        field: 'time'
                    },
                    {
                        width: '110',
                        title: '结束时间',
                        field: 'endTime'
                    },
                    {
                        width: '110',
                        title: '班级信息',
                        field: 'classInfoName'
                    },
                    {
                        width: '80',
                        title: '审核状态',
                        field: 'status',
                        formatter : function(value, row) {
                            if(row.status == 10 ) {
                                return "<span style='color: black;'>待审核</span>";
                            } else if(row.status == 20 ) {
                                return "<span style='color: green;'>通过</span>";
                            } else if(row.status == 30 ) {
                                return "<span style='color: red;'>不通过</span>";
                            }else if(row.status == 40 ) {
                                return "<span style='color: red;'>下线</span>";
                            }else if(row.status == 50){
                                return "<span style='color: red'>取消</span>";
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '200',
                        formatter: function (value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看审核计划" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>


                            <authority:authority authorizationCode="审核状态" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="auditFun(\'' + row.id + '\');"><img class="iconImg ext-icon-note_edit"/>审核</a>&nbsp;';
                            </authority:authority>

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
            $('#status').combobox('clear');
        }

        var showFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看个人活动',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/Fxjh/viewAduit.jsp?id=' + id
            });
        };

        var auditFun = function (id) {
            var dialog = parent.WidescreenModalDialog({
                title: '审核个人活动',
                iconCls: 'ext-icon-note_edit',
                url: '${pageContext.request.contextPath}/page/admin/Fxjh/aduit.jsp?id=' + id,
                buttons: [{
                    text: '审核',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
    <table>
        <tr>
            <td>
                <form id="searchForm">
                    <input id="userInfoId" name="userInfoId" type="hidden"/>
                    <table>
                        <tr>
                            <th align="right">
                                组织主题
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="topic" name="topic" style="width: 155px;"/>
                            </td>
                            <th align="right">
                                审核状态
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select class="easyui-combobox" data-options="editable:false" name="status" id="status"
                                        style="width: 150px;">
                                    <option value="">&nbsp;&nbsp;&nbsp;</option>
                                    <option value="10">待审核</option>
                                    <option value="20">通过</option>
                                    <option value="30">不通过</option>
                                    <option value="40">下线</option>
                                </select>
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
    </table>
</div>

<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>
