<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../../inc.jsp"></jsp:include>
    <style>
        .ta001 th{
            width: 107px;
        }
        .ta001 td{
            height: 33px;
        }
        #bdMap{
            height: 394px;
        }
    </style>
    <script type="text/javascript">
        var picCount = 0;//计算页面图片数量
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#introduction',{
                items : [
                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
                    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                    'anchor', 'link', 'unlink', '|', 'about'
                ],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
        });

        $(function () {
            var id = $('#actActivityId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/actActivity/actActivityAction!getById.action',
                data: { 'actActivityId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {


                        $('form').form('load', {

                            'actActivity.name': result.name,
                            'actActivity.type': result.type,
                            'actActivity.startTime': result.startTime,
                            'actActivity.endTime': result.endTime,
                            'actActivity.signUpStartTime': result.signUpStartTime,
                            'actActivity.signUpEndTime': result.signUpEndTime,
                            'actActivity.organizer': result.organizer,
                            'actActivity.sort': result.sort,
                        });

                        for(var i=0;i<result.activityMusicList.length;i++){
                            var data = result.activityMusicList[i];
                            createMusic(data.type,data.filePathSyc);
                        }

                        editor.html(result.introduction);

                        var activityLogo = result.picSny;

                        uploadPic("#logo_upload_button", "actActivity.picSny", "#logoPic", "picSny");
                        var upload_button_name_logo = "#logo_upload_button";
                        var pic_name_logo = "actActivity.picSny";
                        var pic_div_name_logo = "#logoPic";
                        var pic_id_logo = "picSny";

                        uploadPic(upload_button_name_logo, pic_name_logo, pic_div_name_logo, pic_id_logo);

                        if(activityLogo != null && activityLogo != '' && !$('#pic').val()){
                            buildPicDIV(upload_button_name_logo, pic_name_logo, pic_div_name_logo, activityLogo,pic_id_logo);
                            $(upload_button_name_logo).prop('disabled', 'disabled');
                        }

                        if(result.fileList != null || $.trim(result.fileList) != '' ) {
                            var pics = result.fileList
                            for (var i in pics) {
                                $('#pagePic').append('<div style="float:left;width:180px;"><img src="' + pics[i].picUrl + '" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="actActivity.pictureUrls" value="' + pics[i].picUrl + '"/></div>');
                                picCount++;
                            }
                        }

                    }
                },
                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });

        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                var str = "";
                var musics = $("input[name=filePathSyc]");
                $.each(musics,function(){
                    var id = $(this).attr('id');
                    str += $('input[name='+id+'type]').val()+'%&:'+$('select[name='+id+'isRepeatPlay] option:selected').val()+'%&:'+$(this).val()+'%,&';
                });
                $("input[name=musics]").val(str);
                if($('input[name="actActivity.pictureUrls"]').val()==undefined){
                    parent.$.messager.alert('提示', '请上传图片', 'error');
                    return false;
                }
                if (picCount<3||picCount>6){
                    parent.$.messager.alert('提示', '页面图片最少3张，最多6张', 'error');
                    return false;
                }
                $.ajax({
                    url : '${pageContext.request.contextPath}/actActivity/actActivityAction!update.action',
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
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        parent.$.messager.progress('close');
                    }
                });
            }
        }
        function buildPicDIV(upload_button_name, picName, picDivName, picURL, picId)
        {
            var w = 200;
            var h = 80;

            $(picDivName).append(
                '<div style="float:left;margin-bottom: 5px; margin-left: 5px; position:relative">'+
                '<img src="'+picURL+'" width="'+w+'px" height="'+h+'px"/>'+
                '<div class="bb001" style="left:'+(w-15)+'px; top:0; position:absolute" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                '</div>'+
                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+picURL+'"/></div>'
            );

        }

        //图片上传
        $(function() {
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
                        picCount++;//
                        $('#pagePic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="actActivity.pictureUrls" value="'+msg.url+'"/></div>');
                        if (picCount>=6) {
                            $("#pic_upload_button").prop('disabled', 'disabled');
                        }
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });
        });

        function uploadPic(upload_button_name, picName, picDivName, picId)
        {
            var w = 200;
            var h = 80;
            var button = $(upload_button_name), interval;
            new AjaxUpload(button,
                {
                    action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
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
                            $(picDivName).append(
                                '<div style="float:left; margin-bottom: 5px; margin-left: 5px; position:relative">'+
                                '<img src="'+resp.url+'" width="'+w+'px" height="'+h+'px"/>'+
                                '<div class="bb001" style="left:'+(w-15)+'px; top:0; position:absolute" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                                '</div>'+
                                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+resp.url+'"/></div>'
                            );


                            $(upload_button_name).prop('disabled', 'disabled');
                        }
                        else
                        {
                            $.messager.alert('提示', response.message, 'error');
                        }
                    }
                });

        }

        function removeSchoolLogo(pagePic) {
            $(pagePic).parent().remove();
            picCount--;
            $("#pic_upload_button").prop('disabled', false);
        }

        function removePic(pic, upload_button_name)
        {
            $(pic).parent().remove();
            $(upload_button_name).prop('disabled', false);
        }

        function updateMusic(str){
            var button = $("#"+str+'File'), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action?dir=media',
                name : 'upload',
                onSubmit : function(file, ext)
                {
                    if (!(ext && /^(swf|flv|mp3|wav|wma|wmv|mid|avi|mpg|asf|rm|rmvb)$/.test(ext)))
                    {
                        $.messager.alert('提示', '您上传的文件格式不对，请重新选择！', 'info');
                        return false;
                    }
                    $.messager.progress({
                        text : '文件正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response)
                {
                    $.messager.progress('close');
                    if (response.indexOf('您还没有登录或登录已超时，请重新登录！') == 0)
                    {
                        $.messager.alert('提示', '您还没有登录或登录已超时，请重新登录！', 'error');
                    } else
                    {
                        var msg = $.parseJSON(response);
                        if (msg.error == 0)
                        {
                            $('#'+str+'').html(
                                "<div id='div"+str+"'><a href='"+msg.url+"'>" + msg.url
                                + "</a>&nbsp;<img id='img' class='bb001' onclick='clearUrl(\""+str+"\")' /><input id='"+str+"'type='hidden' name='filePathSyc' value='"+msg.url+"'/></div>");
                            button.prop('disabled', 'disabled');
                        } else
                        {
                            $.messager.alert('提示', msg.message, 'error');
                        }
                    }
                }
            });
        }

        function createMusic(type,url){
            var str;
            if(type == 10 || type == '10'){
                str='beforeMusic';
            }else if(type == 20 || type == '20'){
                str='onMusic';
            }else if(type == 23 || type == '23'){
                str='winMusic';
            }else if(type == 30 || type == '30'){
                str='endMusic';
            }
            var button = $("#"+str+'File');
            $('#'+str+'').html(
                "<div id='div"+str+"'><a href='"+url+"'>" + url
                + "</a>&nbsp;<img id='img' class='bb001' onclick='clearUrl(\""+str+"\")' /><input id='"+str+"'type='hidden' name='filePathSyc' value='"+url+"'/></div>");
            button.prop('disabled', 'disabled');
        }

        function clearUrl(str)
        {
            $('#div'+str).remove();
            $("#"+str+'File').prop('disabled', false);
        }

        $(document).ready(function () {

        });

    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            企业信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    活动名称
                </th>
                <td colspan="3">
                    <input id="actActivityId" name="actActivity.id" type="hidden" value="${param.id}"/>
                    <input name="actActivity.name" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>活动类型</th>
                <td>
                    <input id="type" name="actActivity.type" class="easyui-combobox" style="width: 250px;" value="10" readonly/>
                </td>
            </tr>

            <tr>
                <th>活动开始时间</th>
                <td>
                    <input name="actActivity.startTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                </td>
            </tr>

            <tr>
                <th>
                    活动结束时间
                </th>
                <td colspan="3">
                    <input name="actActivity.endTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                </td>
            </tr>

            <tr>
                <th>报名开始时间</th>
                <td colspan="3">
                    <input name="actActivity.signUpStartTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                </td>
            </tr>


            <tr>
                <th>
                    报名结束时间
                </th>
                <td colspan="3">
                    <input name="actActivity.signUpEndTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="date()"/>
                </td>

            </tr>

            <tr>
                <th>
                    活动介绍
                </th>
                <td colspan="3">
                    <textarea id="introduction" rows="10" cols="100" name="actActivity.introduction" style="width: 700px; height: 250px;" ></textarea>
                </td>
            </tr>


            <tr>
                <th>
                    主办单位
                </th>
                <td colspan="3">
                    <input  name="actActivity.organizer" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true"
                            maxlength="40" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    排序
                </th>
                <td colspan="3">
                    <input id="sort" name="actActivity.sort" class="easyui-validatebox" data-options="required:true" style="width: 250px;" value="" />
                </td>
            </tr>
            <tr>
                <th>活动图标</th>
                <td>
                    <input type="button" id="logo_upload_button" value="上传活动图片">(Logo大小为：80px x 80px)

                    <div id="logoPic"></div>
                </td>
            </tr>
            <tr>
                <th>图片上传</th>
                <td>
                    <input type="button"  id="pic_upload_button" value="请选择图片" />
                    <span style="color: red">(最少3张，最多6张)</span>
                </td>
            </tr>
            <tr>
                <th>
                    轮播图片
                </th>
                <td colspan="3">
                    <div id="pagePic" class="container" style="padding: 20px"></div>
                    <%--<input type="hidden" name="share.pagePictures" id="pagePic" value="" />--%>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset>
        <legend>
            活动音乐信息(<span style="color: red">附件大小：20M之内</span>，附件格式：swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb)
        </legend>
        <table class="ta001">
            <thead>
            <th></th>
            <th >是否重播</th>
            <th >文件上传</th>
            <th >文件路径</th>
            </thead>
            <tbody>
            <tr>
                <th>
                    活动开始前音乐
                </th>
                <td >
                    <input type="hidden" name="beforeMusictype" value="10"/>
                    <select name="beforeMusicisRepeatPlay">
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
                <td>
                    <input type="button" id="beforeMusicFile" value="上传文件" onclick="updateMusic('beforeMusic')">
                </td>
                <td>
                    <span id="beforeMusic"></span>
                </td>
            </tr>

            <tr>
                <th>活动进行时音乐</th>
                <td >
                    <input type="hidden" name="onMusictype" value="20"/>
                    <select name="onMusicisRepeatPlay">
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
                <td>
                    <input type="button" id="onMusicFile"  value="上传文件" onclick="updateMusic('onMusic')">
                </td>
                <td>
                    <span id="onMusic"></span>
                </td>
            </tr>

            <tr>
                <th>活动中奖时音乐</th>
                <td >
                    <input type="hidden" name="winMusictype" value="23"/>
                    <select  name="winMusicisRepeatPlay">
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
                <td>
                    <input type="button" id="winMusicFile" value="上传文件" onclick="updateMusic('winMusic')">
                </td>
                <td>
                    <span id="winMusic"></span>
                </td>
            </tr>

            <tr>
                <th>
                    活动结束时音乐
                </th>
                <td >
                    <input type="hidden" name="endMusictype" value="30"/>
                    <select  name="endMusicisRepeatPlay">
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
                <td>
                    <input type="button" id="endMusicFile" value="上传文件" onclick="updateMusic('endMusic')">
                </td>
                <td>
                    <span id="endMusic"></span>
                </td>
            </tr>
            <input type="hidden" name="musics" />
            </tbody>
        </table>
    </fieldset>
</form>
</body>
</html>
