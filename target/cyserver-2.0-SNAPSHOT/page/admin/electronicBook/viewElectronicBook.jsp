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
            var id = $('#enterpriseId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/electronicBook/electronicBookAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'electronicBook.id': result.id,
                                'electronicBook.name': result.name,
                                'electronicBook.author': result.author,
                                'electronicBook.version': result.version,
                                'electronicBook.upload': result.upload,
                                'electronicBook.alumniName': result.alumniName,
                                'electronicBook.logo': result.logo,
                                'electronicBook.introduction': result.introduction,
                                'electronicBook.createDate': result.createDate,
                            });
                            if(result.logo){
                                $('#logoUrl').append('<img src="'+ result.logo +'" width="150px" style="margin:10px" height="150px"/>');
                            }
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


        });
    </script>
</head>

<body>
<form method="post" id="activityForm" class="form">
    <input name="electronicBook.id" type="hidden"  id="electronicBookId" value="${param.id}">
    <fieldset>
        <legend>
            书籍信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    书籍名称
                </th>
                <td >
                    <input id="name" name="electronicBook.name"  disabled="disabled" style="width: 200px;" />
                </td>
            </tr>
            <tr>
                <th>
                    作者
                </th>
                <td>
                    <input name="electronicBook.author" style="width: 200px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    版本
                </th>
                <td>
                    <input name="electronicBook.version"  style="width: 200px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    发布时间
                </th>
                <td>
                    <input name="electronicBook.createDate"  style="width: 200px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    上传机构
                </th>
                <td>
                    <input name="electronicBook.alumniName"  style="width: 200px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    书籍简介
                </th>
                <td colspan="3">
					<textarea  rows="7" cols="100" name="electronicBook.introduction" disabled="disabled"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    下载路径
                </th>
                <td>
                    <input name="electronicBook.upload"  style="width: 300px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td>
                    <div style="float:left;"width="200px" height="260px" id="logoUrl">
                    </div>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>
