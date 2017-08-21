<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
        $(function () {
            if ($('#activityId').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/activity/activityAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'activity.id': result.id,
                                'activity.department': result.department,
                                'activity.grade': result.grade,
                                'activity.clazz': result.clazz,
                                'activity.major': result.major,
                                'activity.backTime': result.backTime,
                                'activity.backNumber': result.backNumber==0?'':result.backNumber,
                                'activity.contactPerson': result.contactPerson,
                                'activity.contactPhone': result.contactPhone,
                                'activity.needMeeting': result.needMeeting + '',
                                'activity.meetingArea': result.meetingArea,
                                'activity.meetingTime': result.meetingTime,
                                'activity.meetingNumber': result.meetingNumber==0?'':result.meetingNumber,
                                'activity.needProjector': result.needProjector + '',
                                'activity.needVisit': result.needVisit + '',
                                'activity.visitPlace': result.visitPlace,
                                'activity.visitTime': result.visitTime,
                                'activity.visitNumber': result.visitNumber==0?'':result.visitNumber,
                                'activity.needDinner': result.needDinner + '',
                                'activity.dinnerArea': result.dinnerArea,
                                'activity.dinnerTime': result.dinnerTime,
                                'activity.dinnerNumber': result.dinnerNumber==0?'':result.dinnerNumber,
                                'activity.dinnerStandard': result.dinnerStandard==0?'':result.dinnerStandard,
                                'activity.needSubscription': result.needSubscription + '',
                                'activity.backStartime': result.backStartime,
                                'activity.status': result.status,
                                'activity.opinion': result.opinion,
                                'activity.remark': result.remark
                            });

                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
        });
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                var url;
                if ($('#activityId').val() > 0) {
                    url = '${pageContext.request.contextPath}/activity/activityAction!update.action';
                } else {
                    url = '${pageContext.request.contextPath}/activity/activityAction!save.action';
                }
                $.ajax({
                    url: url,
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据提交中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };
    </script>
</head>

<body>
<form method="post" id="activityForm" class="form">
    <input name="activity.id" type="hidden" id="activityId" value="${param.id}">
    <fieldset>
        <legend>
            基本信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    院系
                </th>
                <td >
                    <input name="activity.department" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    专业
                </th>
                <td>
                    <input name="activity.major" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    年级
                </th>
                <td>
                    <input name="activity.grade" class="easyui-validatebox" style="width: 150px;" />
                </td>

            </tr>
            <tr>
                <th>
                    班级
                </th>
                <td>
                    <input name="activity.clazz" class="easyui-validatebox" style="width: 150px;" />
                </td>

                <th>
                    返校时间
                </th>
                <td>
                    <input id="backStartime" name="activity.backStartime" class="easyui-datebox" style="width: 155px;" data-options="editable:false" />
                </td>
                <th>
                    返校人数
                </th>
                <td>
                    <input name="activity.backNumber" class="easyui-validatebox" style="width: 150px;" data-options="validType:'tel'"/>
                </td>
            </tr>
            <tr>

                <th>
                    联系人
                </th>
                <td>
                    <input name="activity.contactPerson" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    联系电话
                </th>
                <td colspan="3">
                    <input name="activity.contactPhone" class="easyui-validatebox" data-options="validType:'telePhone'" style="width: 150px;" />
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>聚会场地</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否需要聚会场地
                </th>
                <td>
                    <select name="activity.needMeeting" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    聚会校区
                </th>
                <td>
                    <input name="activity.meetingArea" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    借用时间
                </th>
                <td>
                    <input name="activity.meetingTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/>
                </td>
            </tr>
            <tr>
                <th>
                    参加人数
                </th>
                <td>
                    <input name="activity.meetingNumber" class="easyui-validatebox" style="width: 150px;" data-options="validType:'tel'"/>
                </td>
                <th>
                    是否需要投影
                </th>
                <td colspan="3">
                    <select name="activity.needProjector" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>

            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>参观信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否参观
                </th>
                <td>
                    <select name="activity.needVisit" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    参观地点
                </th>
                <td>
                    <input name="activity.visitPlace" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    参观时间
                </th>
                <td>
                    <input name="activity.visitTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/>
                </td>
            </tr>
            <tr>
                <th>
                    参观人数
                </th>
                <td colspan="5">
                    <input name="activity.visitNumber" class="easyui-validatebox" style="width: 150px;" data-options="validType:'tel'"/>
                </td>

            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>就餐信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否就餐
                </th>
                <td>
                    <select name="activity.needDinner" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    就餐地点
                </th>
                <td>
                    <input name="activity.dinnerArea" class="easyui-validatebox" style="width: 150px;" />
                </td>
                <th>
                    就餐时间
                </th>
                <td>
                    <input name="activity.dinnerTime" class="easyui-datetimebox" data-options="editable:false" style="width: 150px;" />
                </td>
            </tr>
            <tr>
                <th>
                    就餐人数
                </th>
                <td>
                    <input name="activity.dinnerNumber" class="easyui-validatebox" data-options="validType:'tel'" style="width: 150px;" />
                </td>
                <th>
                    就餐标准(元/人)
                </th>
                <td colspan="3">
                    <input name="activity.dinnerStandard" class="easyui-validatebox" data-options="validType:'price'" style="width: 150px;" />
                </td>

            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>
            审核信息
        </legend>

        <table class="ta001">
            <tr>
                <th>
                    审核状态
                </th>
                <td>
                    <select data-options="editable:false" name="activity.status" style="width: 150px;">
                        <option value="20">通过</option>
                        <option value="30">不通过</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    审核意见
                </th>
                <td>
                    <textarea rows="5" cols="100" name="activity.opinion"></textarea>
                </td>

            </tr>
        </table>
    </fieldset>

    <br>
    <fieldset>
        <legend>认捐信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否认捐
                </th>
                <td>
                    <select name="activity.needSubscription" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    备注
                </th>
                <td colspan="3">
                    <input name="activity.remark" class="easyui-validatebox" style="width: 400px;"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
