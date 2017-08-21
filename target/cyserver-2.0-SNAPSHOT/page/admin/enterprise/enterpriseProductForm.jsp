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

            var button = $("#pic_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        picCount++;
                        $('#productPic').append('<div style="float:left; margin-bottom: 5px; margin-left: 14px; position:relative"">'+
                                '<img src="'+msg.url+'" width="200px" height="80px"/>'+
                                '<div class="bb001" style="top:-10px; left:180px; position:absolute"  onclick="removePic(this, \'#pic_upload_button\')"></div>'+
                                '<input type="hidden" id="channelIcon" name="enterpriseProduct.posterPic" value="'+msg.url+'"/></div>');
                        if (picCount>=6) {
                            $("#pic_upload_button").prop('disabled', 'disabled');
                        }
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });


            if(id != '0'){
                $.ajax({
                    url: '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!getById.action',
                    data: {
                        'enterpriseProduct.id': id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'enterpriseProduct.id': result.id,
                                'enterpriseProduct.name': result.name,
                                'enterpriseProduct.slogan': result.slogan,
                                'enterpriseProduct.type': result.type,
                                'enterpriseProduct.summary': result.summary,
                                'enterpriseProduct.enterpriseId': result.enterpriseId
                            });

                            $('#clickNumber').text(result.clickNumber);
                            var productPic = result.fileList;
                            uploadPic("#pic_upload_button", "enterpriseProduct.posterPic", "#productPic");
                            var upload_button_name = "#pic_upload_button";
                            var pic_name = "enterpriseProduct.posterPic";
                            var pic_div_name = "#productPic";
                            uploadPic(upload_button_name, pic_name, pic_div_name);
                            if(productPic != null && productPic != ''){
                                buildPicDIV(upload_button_name, pic_name, pic_div_name, productPic);
                                $(upload_button_name).prop('disabled', 'disabled');
                            }

                            editor.html(result.description);
                            if(action == '1'){
                                editor.readonly();
                                $(".bb001").removeAttr("onclick");
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

        function submitForm($dialog, $grid, $pjq) {
           /* if (picCount<1||picCount>6){
                parent.$.messager.alert('提示', '页面图片最少1张，最多6张', 'error');
                return false;
            }*/
            var url;
            if(id != '0'){
                url = '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!update.action'
            }else {
                url = '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!save.action'
            }


            if ($('form').form('validate')) {
                //富文本是否转编码 lixun 2017.5.5
                if( '<%=isRichTextConvert%>' == '1' )
                {
                    $("#description").val(strToBase64($("#description").val()));
                }

                $.ajax({
                    url : url,
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else
                        {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function()
                    {
                        $pjq.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        $pjq.messager.progress('close');
                    }
                });
            }
        }
        
        function buildPicDIV(upload_button_name, picName, picDivName, picURL)
        {
            for(var i in picURL){
                $(picDivName).append(
                        '<div style="float:left;margin-bottom: 5px; margin-left: 14px; position:relative">'+
                        '<img src="'+picURL[i].picUrl+'" width="200px" height="80px"/>'+
                        '<div class="bb001" style="top:-10px; left:180px ; position: absolute" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                        '</div>'+
                        '<input type="hidden" name="'+picName+'" value="'+picURL[i].picUrl+'"/></div>'
                );
            }

        }
        function uploadPic(upload_button_name, picName, picDivName)
        {
            var button = $(upload_button_name), interval;
            new AjaxUpload(button,
                {
                    action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                    name : 'upload',
                    onSubmit : function(file, ext)
                    {
                        if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)))
                        {
                            $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                            return false;
                        }
                        $.messager.progress({text : '图片正在上传,请稍后....'});
                    },
                    onComplete : function(file, response)
                    {
                        $.messager.progress('close');
                        var resp = $.parseJSON(response);

                        if (resp.error == 0)
                        {
                            picCount++;
                            $(picDivName).append(
                                    '<div style="float:left;margin-bottom: 5px; margin-left: 15px; position:relative">'+
                                    '<img src="'+resp.url+'" width="200px" height="80px"/>'+
                                    '<div class="bb001" style="left: 180px; top:-10px;   position:absolute" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                                    '</div>'+
                                    '<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
                            );

                            if (picCount>=6) {
                                $("#pic_upload_button").prop('disabled', 'disabled');
                            }
                        }
                        else
                        {
                            $.messager.alert('提示', response, 'error');
                        }
                    }
                });

        }


        function removePic(pic, upload_button_name)
        {
            $(pic).parent().remove();
            picCount--;
            $(upload_button_name).prop('disabled', false);
        }
    </script>
</head>
<body>
<form method="post" id="editProcutForm">
    <table class="ta001">
        <input type="hidden" name="enterpriseProduct.id" />
        <tr>
            <th>产品名称</th>
            <td>
                <input id="name" name="enterpriseProduct.name" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>校友企业</th>
            <td>
                <input id="enterpriseId" name="enterpriseProduct.enterpriseId" class="easyui-combobox" style="width: 150px;" value=""
                       data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getEnterpriseList.action'" />
            </td>
        </tr>
        <tr>
            <th>企业标语</th>
            <td>
                <input id="slogan" name="enterpriseProduct.slogan" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>产品类型</th>
            <td>
                <input id="type" name="enterpriseProduct.type" class="easyui-combobox" style="width: 150px;" value=""
                       data-options="editable:false,required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!doNotNeedSecurity_getProductType.action'" />
            </td>
        </tr>
        <tr>
            <th>产品简介</th>
            <td>
                <textarea id="summary" rows="5" cols="100" maxlength="500" name="enterpriseProduct.summary" style="width: 700px; height: 50px;" ></textarea>
            </td>
        </tr>
        <tr>
            <th>产品详情</th>
            <td>
                <textarea id="description" rows="5" cols="100" name="enterpriseProduct.description" style="width: 700px; height: 300px;" ></textarea>
            </td>
        </tr>
        <tr>
            <th>产品图片</th>
            <td>
                <input type="button" id="pic_upload_button" value="上传产品图片">(图片大小为：200px x 80px)
                <div id="productPic" class="container" style="padding: 20px">
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
