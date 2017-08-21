<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title>查看微信新闻</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#newscontent',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                readonlyMode : true,
                afterChange:function(){
                    this.sync();
                }
            });
        });

        $(function () {
            if ($('#id').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/wechatNewsType/wechatNewsTypeAction!getWeChatNewsType.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {

                            $('form').form('load', {
                                'wechatNewsType.id': result.id,
                                'wechatNewsType.name': result.name,
                                'wechatNewsType.type': result.type,
                                'wechatNewsType.url': result.url,
                                'wechatNewsType.origin': result.origin,
                                'wechatNewsType.cityName': result.cityName,
                                'wechatNewsType.isNavigation': result.isNavigation,
                                'wechatNewsType.orderNum': result.orderNum
                            });

                            if(result.type == 2) {
                                $("#trUrl").css("display","");
                            }

                            if(result.origin == 2) {
                                $("#trArea").css("display","");
                            }

                            if(result.parentId == 0) {
                                $("#level").html('一级栏目');
                            } else if(result.parentId > 0) {
                                $("#level").html('二级栏目');
                                $("#trOrigin").css("display","none");
                                $("#trArea").css("display","none");
                            }

                            var address = "";
                            if(result.type == 1) {
                                address = "<%=basePath%>mobile/wechatNewsType/wechatNewsTypeList.jsp?category=" + result.id;
                            } else if(result.type == 2) {
                                address = result.url;
                            } else if(result.type == 3) {
                                address = "<%=basePath%>mobile/wechatNewsType/wechatNewsTypeList.jsp?category=" + result.id;
                            }
                            $("#address").val(address);
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function (result) {
                        parent.$.messager.progress('close');


                    }
                });
            }

        });


    </script>
</head>

<body>
<form method="post" id="addNewsForm">
    <input name="wechatNewsType.id" type="hidden" id="id" value="${param.id}">
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    栏目名称
                </th>
                <td colspan="3">
                    <input id="name" name="wechatNewsType.name" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    栏目级别
                </th>
                <td colspan="3" id="level">

                </td>
            </tr>
            <tr>
                <th>
                    栏目类型
                </th>
                <td colspan="3">
                    <select id="type" name="wechatNewsType.type" disabled="disabled">
                        <option value="1">新闻类别</option>
                        <option value="2">链接</option>
                        <option value="3">单页面</option>
                    </select>
                </td>
            </tr>
            <tr id="trUrl" style="display:none;">
                <th>
                    URL(可为空)
                </th>
                <td colspan="3">
                    <input id="url" style="width: 700px;" name="type.url" type="text" disabled="disabled">
                </td>
            </tr>
            <tr id="trOrigin" style="display:none;">
                <th>
                    新闻来源
                </th>
                <td colspan="3">
                    <select id="origin" name="wechatNewsType.origin" disabled="disabled">
                        <option value="1">总会</option>
                        <option value="2">地方</option>
                    </select>
                </td>
            </tr>

            <tr id="trArea" style="display:none;">
                <th>
                    地域
                </th>
                <td colspan="3">
                    <input name="wechatNewsType.cityName" style="width: 500px;" disabled="disabled" />
                </td>
            </tr>

            <tr>
                <th>
                    导航显示
                </th>
                <td colspan="3">
                    <select id="isNavigation" name="wechatNewsType.isNavigation" disabled="disabled">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    排序编号
                </th>
                <td colspan="3">
                    <input id="orderNum" name="wechatNewsType.orderNum" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'tel'"
                           maxlength="20" value="" disabled="disabled" />&nbsp;&nbsp;&nbsp;&nbsp;( 数字越小越靠前)
                </td>
            </tr>

            <tr>
                <th>
                    栏目地址
                </th>
                <td colspan="3">
                    <input id="address" class="easyui-validatebox"
                           style="width: 700px;" disabled="disabled"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
