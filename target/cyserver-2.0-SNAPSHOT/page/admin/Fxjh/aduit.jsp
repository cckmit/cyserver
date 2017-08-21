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
            var aultId=$('#eventId').val();
            if ($('#eventId').val() != null && $.trim($('#eventId').val()) != '') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!getFxjhById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'fxjh.id': result.id,
                                'fxjh.topic': result.topic,
                                'fxjh.number': result.number,
                                'fxjh.time': result.time,
                                'fxjh.classinfo': result.classinfo,
                                'fxjh.other': result.other,
                                'fxjh.status':result.status
                            });

                            /*if(result.pic != null && result.pic != '') {
                                $('#eventPic').append('<div style="float:left;width:180px;"><img src="'+result.pic+'" width="150px" height="150px"/><input type="hidden" name="event.pic" id="eu" value="'+result.pic+'"/></div>');
                            }*/
                            /*if(result.qrCodeUrl != null && $.trim(result.qrCodeUrl) != '') {
                             $("#event_qrCodeUrl").attr("src",result.qrCodeUrl) ;
                             }*/
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

            $('#eventForm .ta001 :input[name^=event]').attr('disabled', true);
        });


        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!audit.action',
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
<form method="post" id="eventForm" class="form">
    <input name="fxjh.id" type="hidden" id="eventId" value="${param.id}">
    <input name="fxjh.type" type="hidden" value="">
    <fieldset>
        <legend>
            审核信息
        </legend>
        <table class="ta001">
            <tr>
                <th>审核状态</th>
                <td>
                    <select id="status" class="easyui-combobox" data-options="editable:false" name="fxjh.status" style="width: 150px;">
                        <option value="10">待审核</option>
                        <option value="20">通过</option>
                        <option value="30">不通过</option>
                    </select>
                </td>
            </tr>
            <%--<tr>
                <th>审核意见</th>
                <td>
                    <textarea id="auditOpinion" rows="5" cols="80" name="fxjh.auditOpinion"></textarea>
                </td>
            </tr>--%>
        </table>
    </fieldset>

    <fieldset>
        <legend>
            计划基本信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    组织主题
                </th>
                <td colspan="3">
                    <input name="fxjh.topic" readonly="readonly" style="width: 500px;"/>
                </td>
            </tr>

            <tr>
                <th>
                    计划人数
                </th>
                <td colspan="3">
                    <input name="fxjh.number" readonly="readonly"  style="width: 500px;"/>
                </td>
            </tr>

            <tr>
                <th>
                    返校时间
                </th>
                <td colspan="3">
                    <input name="fxjh.time" readonly="readonly"  style="width: 500px;"/>
                </td>
            </tr>

            <tr>
                <th>
                    班级信息
                </th>
                <td colspan="3">
                    <input name="fxjh.classinfo" readonly="readonly"  style="width: 500px;"/>
                </td>
            </tr>

            <tr>
                <th>
                    描述
                </th>
                <td colspan="3">
					<textarea id="other" rows="7" readonly="readonly"  cols="100"
                              name="fxjh.other"></textarea>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
