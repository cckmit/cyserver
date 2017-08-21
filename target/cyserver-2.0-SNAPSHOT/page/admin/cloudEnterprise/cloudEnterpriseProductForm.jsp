<%@ page import="com.cy.system.Global" %><%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/6/17
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    int isRichTextConvert = Global.IS_RICH_TEXT_CONVERT;
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title></title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var picCount = 0;//计算页面图片数量
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#description',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
        });

        var action = '${param.action}';
        var id = '0';
        var enterpriseId = '${param.enterpriseId}';

        $(function(){
            if(action != '0'){
                id = '${param.id}';
                $('#clickTr').show();
                if(action == '1'){
                    $('input').attr('readonly', true);
                    $('.easyui-combobox').combobox({'readonly': true});
                }else{
                    $('#enterpriseId').combobox({'readonly': true});
                }

            }else{
                if(enterpriseId){
                    $('#enterpriseId').combobox({'readonly': true});
                    $('#enterpriseId').combobox('setValue', enterpriseId);
                }
            }


            if(id != '0'){
                $.ajax({
                    url: '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseProductAction!getById.action',
                    data: {
                        'cloudEnterpriseProduct.id': id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'cloudEnterpriseProduct.id': result.id,
                                'cloudEnterpriseProduct.name': result.name,
                                'cloudEnterpriseProduct.slogan': result.slogan,
                                'cloudEnterpriseProduct.type': result.type,
                                'cloudEnterpriseProduct.summary': result.summary,
                                'cloudEnterpriseProduct.enterpriseId': result.enterpriseId
                            });

                            $('#clickNumber').text(result.clickNumber);

                            editor.html(result.description);
                            if(action == '1'){
                                editor.readonly();
                                $(".bb001").removeAttr("onclick");
                            }
                            var posters = result.posterIds.split(",")
                            for (var i in posters){
                                if(result.posterIds !=null){
                                    $('#posterPic').append('<img src="'+ result.posterPic +'" width="200px" style="margin:10px" height="80px"/>');
                                }
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
            }
        });

    </script>
</head>
<body>
<form method="post" id="editProcutForm">
    <table class="ta001">
        <input type="hidden" name="cloudEnterpriseProduct.id" />
        <tr>
            <th>产品名称</th>
            <td>
                <input id="name" name="cloudEnterpriseProduct.name" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>校友企业</th>
            <td>
                <input id="enterpriseId" name="cloudEnterpriseProduct.enterpriseId" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>企业标语</th>
            <td>
                <input id="slogan" name="cloudEnterpriseProduct.slogan" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>产品类型</th>
            <td>
                <input id="type" name="cloudEnterpriseProduct.type" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>产品简介</th>
            <td>
                <textarea id="summary" rows="5" cols="100" maxlength="500" name="cloudEnterpriseProduct.summary" style="width: 700px; height: 50px;" ></textarea>
            </td>
        </tr>
        <tr>
            <th>产品详情</th>
            <td>
                <textarea id="description" rows="5" cols="100" name="cloudEnterpriseProduct.description" style="width: 700px; height: 300px;" ></textarea>
            </td>
        </tr>
        <tr>
            <th>产品图片</th>
            <td>
                <div style="float:left;width:180px;" id="posterPic">
                </div>
            </td>
        </tr>
        <tr id="clickTr" style="display: none">
            <th>点击量</th>
            <td id="clickNumber">

            </td>
        </tr>
    </table>
</form>
</body>
</html>
