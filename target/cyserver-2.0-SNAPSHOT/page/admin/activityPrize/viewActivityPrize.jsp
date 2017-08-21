<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=j5bQGIAXNd5rrnfu83is90HhX1n3xvMd"></script>
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <style>
        .ta001 th {
            width: 107px;
        }

        .ta001 td {
            height: 33px;
        }

        #bdMap {
            height: 394px;
        }

    </style>
    <script type="text/javascript">

        var id =  '${param.id}';

        $(function () {
            if (id && id !='' && id !='null') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/activityPrize/activityPrizeAction!getById.action?activityPrize.id=' +id,
                    dataType: 'json',
                    success: function (result) {

                        $('form').form('load', {
                            'activityPrize.activityId': result.activityId,
                            'activityPrize.name': result.name,
                            'activityPrize.num': result.num,
                            'activityPrize.prizeName': result.prizeName,
                            'activityPrize.prizeSrcPic': result.prizeSrcPic,
                            'activityPrize.isRepeat': result.isRepeat,
                            'activityPrize.sort': result.sort,
                            'activityPrize.surplusNum': result.surplusNum
                        });


                        if (result.prizeSrcPic != null || $.trim(result.prizeSrcPic) != '') {
                            var upload_button_name_poster = "#poster_upload_button";
                            var pic_name_poster = "activityPrize.prizeSrcPic";
                            var pic_div_name_poster = "#posterPic";
                            var pic_id_poster = "poster";
//                            uploadPic(upload_button_name_poster, pic_name_poster, pic_div_name_poster, pic_id_poster);
                            buildPicDIV(upload_button_name_poster, pic_name_poster, pic_div_name_poster, result.prizeSrcPic, pic_id_poster);
//                            $(upload_button_name_poster).prop('disabled', 'disabled');
                        }


//                        alert(JSON.stringify(result.news));


                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function () {
                        // window.location.reload();
                        parent.$.messager.progress('close');

                    }
                });
            }
        })



        $(function () {
            uploadPic("#poster_upload_button", "activityPrize.prizeSrcPic", "#posterPic", "poster");
        });
        function buildPicDIV(upload_button_name, picName, picDivName, picURL, picId)
        {

                w = 150;
                h = 150;


            $(picDivName).append(
                '<div style="float:left;margin-bottom: 5px; margin-left: 5px; position:relative">'+
                '<img src="'+picURL+'" width="'+w+'px" height="'+h+'px"/>'+
//                '<div class="bb001" style="left:'+(w-15)+'px; top:0; position:absolute" onclick="removePic(this,\'' + upload_button_name + '\')">'+
//                '</div>'+
                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+picURL+'"/></div>'
            );

        }



        function removePic(pic, upload_button_name) {
            $(pic).parent().remove();
            $(upload_button_name).prop('disabled', false);
        }

    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            奖项信息
        </legend>
        <table class="ta001">


            <tr  >
                <th>
                    抽奖活动
                </th>
                <td colspan="3">
                    <input  name="activityPrize.activityId" class="easyui-combobox" disabled
                           data-options="editable:false,
									        required:true,
									        valueField: 'id',
									        textField: 'name',
									        url: '${pageContext.request.contextPath}/actActivity/actActivityAction!doNotNeedSessionAndSecurity_findList.action'"/>
                </td>
            </tr>

            <tr>
                <th>
                    奖项名称
                </th>
                <td colspan="3">
                    <input type="hidden"  name="activityPrize.id" value="${param.id}">
                    <input name="activityPrize.name" class="easyui-validatebox" disabled
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    奖品名称
                </th>
                <td colspan="3">
                    <input name="activityPrize.prizeName" class="easyui-validatebox" disabled
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           value=""/>
                </td>
            </tr>

            <tr>
                <th>奖品数量</th>
                <td colspan="3">
                    <input name="activityPrize.num" class="easyui-validatebox" disabled
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           value=""/>
                </td>
            </tr>
            <tr>
                <th>排序</th>
                <td colspan="3">
                    <input name="activityPrize.sort" class="easyui-validatebox" disabled
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    中奖人重复
                </th>
                <td>
                    <input type="radio" name="activityPrize.isRepeat" value="1" style="width: 20px;" disabled>允许
                    <input type="radio" name="activityPrize.isRepeat" value="0" checked style="width: 20px;" disabled>不允许
                </td>
            </tr>
            <tr>
                <th>奖品图片</th>
                <td>
                    <%--<input type="button" id="poster_upload_button" value="上传奖品图片">(图片大小为：150px x 150px)--%>
                    <div id="posterPic"></div>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>
