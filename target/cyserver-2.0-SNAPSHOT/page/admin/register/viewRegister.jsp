<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            if ($('#registerId').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/register/registerAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'register.id': result.id,
                                'register.x_name': result.x_name,
                                'register.x_sex': result.x_sex,
                                'register.x_workUnit': result.x_workUnit,
                                'register.x_position': result.x_position,
                                'register.x_school': result.x_school,
                                'register.x_depart': result.x_depart,
                                'register.x_major': result.x_major,
                                'register.x_grade': result.x_grade,
                                'register.x_clazz': result.x_clazz,
                                'register.x_address': result.x_address,
                                'register.x_phone': result.x_phone,
                                'register.x_email': result.x_email,
                                'register.visitHistoryMuseum': result.visitHistoryMuseum + '',
                                'register.visitMoneyMuseum': result.visitMoneyMuseum + '',
                                'register.visitCollege': result.visitCollege + '',
                                'register.selfDrive': result.selfDrive + '',
                                'register.licensePlate': result.licensePlate,
                                'register.withFamily': result.withFamily + '',
                                'register.adultNumber': result.adultNumber==0?'':result.adultNumber,
                                'register.childrenNumber': result.childrenNumber==0?'':result.childrenNumber
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
            
            $('#registerForm .ta001 :input[name^=register]').attr('disabled', true);
        });
    </script>
</head>

<body>
<form method="post" id="registerForm" class="form">
    <input name="register.id" type="hidden" id="registerId" value="${param.id}">
    <fieldset>
        <legend>
            基本信息
        </legend>
        <table class="ta001">
        <tr>
                <th>
                    姓名
                </th>
                <td>
                    <input name="register.x_name" class="easyui-validatebox"/>
                </td>
                <th>
                    性别
                </th>
                <td>
                    <input name="register.x_sex" class="easyui-validatebox"/>
                </td>
                <th>
                    所在地
                </th>
                <td>
					<input name="register.x_address" class="easyui-validatebox"/>
                </td>
        </tr>
       <tr>
                <th>
                    工作单位
                </th>
                <td>
                    <input name="register.x_workUnit" class="easyui-validatebox"/>
                </td>
                <th>
                    职务
                </th>
                <td>
                    <input name="register.x_position" class="easyui-validatebox"/>
                </td>
                <th>
                    联系电话
                </th>
                <td>
                    <input name="register.x_phone" class="easyui-validatebox"/>
                </td>
       </tr>
       <tr>
                <th>
                   电子邮箱
                </th>
                <td colspan="5">
                    <input name="register.x_email" class="easyui-validatebox"/>
                </td>
                
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>教育信息</legend>
        <table class="ta001">
            <tr>
                <th>
					学校
                </th>
                <td>
                    <input id="school" name="register.x_school" class="easyui-validatebox"/>
                </td>
                <th>
                   	 院系
                </th>
                <td>
                	<input name="register.x_depart" class="easyui-validatebox"/>
                </td>
                <th>
                   	 专业
                </th>
                <td>
                    <input name="register.x_major" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
      			<th>
                   	 年级
                </th>
                <td>
                    <input name="register.x_grade" class="easyui-validatebox"/>
                </td>
                <th>
                   	 班级
                </th>
                <td colspan="3">
                    <input name="register.x_clazz" class="easyui-validatebox"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>活动信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否参观校史馆
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitHistoryMuseum" disabled="disabled" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    是否参观博物馆
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitMoneyMuseum" disabled="disabled" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    是否游园
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitCollege" disabled="disabled" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    是否自驾车
                </th>
                <td>
                    <select class="easyui-combobox" name="register.selfDrive" disabled="disabled" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    车牌号
                </th>
                <td>
                    <input name="register.licensePlate" class="easyui-validatebox"/>
                </td>

            
            	<th>
                    是否有家人随行
                </th>
                <td>
                    <select class="easyui-combobox" name="register.withFamily" disabled="disabled" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
         </tr>
            <tr>
                <th>
                    成人人数
                </th>
                <td>
                    <input name="register.adultNumber" class="easyui-validatebox"
                           data-options="validType:'tel'"/>
                </td>
                <th>
                    儿童人数
                </th>
                <td colspan="3">
                    <input name="register.childrenNumber" class="easyui-validatebox"
                           data-options="validType:'tel'"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
