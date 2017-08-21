<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        var associationId = "";
        $(function () {
           loadInfo();
        });


        var loadInfo = function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/association/associationAction!doNotNeedSecurity_getAssociationInfo.action',
                dataType : 'json',
                success : function (result) {
                    console.log(result);
                    if(result){
                        associationId = result.id?result.id:"";
                        $("#name").text(result.name?result.name:"");
                        $("#inputName").val(result.name?result.name:"");
                        $("#type").text(result.typeName?result.typeName:"");
                        $("#selectType").combobox('setValues',result.typeId?result.typeId:"");
                        $("#concatName").val(result.concatName?result.concatName:"");
                        $("#telephone").val(result.telephone?result.telephone:"");
                        $("#email").val(result.email?result.email:"");
                        $("#introduction").html(result.introduction?result.introduction:"");
                        var src = '${pageContext.request.contextPath}/association/associationAction!doNotNeedSessionAndSecurity_getErWeiMa.action?associationId='+ result.id;
                        $('#qrCodeUrl').attr('src', src );
                        if(result.historyList && result.historyList.length>0){
                            console.log(result.historyList.length);
                            var str = "<legend>社团历史</legend><table class='ta001'>";
                            for( var i in result.historyList){
                                str +="<tr><td>";
                                if(result.historyList[i].startTime){
                                    str += result.historyList[i].startTime;
                                }
                                str +="~";
                                if(result.historyList[i].endTime){
                                    str += result.historyList[i].endTime;
                                }
                                str += "</td><td>";
                                if(result.historyList[i].remarks){
                                    str += result.historyList[i].remarks
                                }
                                str +="</td><tr>";
                            }
                            str += "</table>";
                            $('#history').html(str);

                        }
                        var associationLogo = result.logo;
                        var associationPoster = result.poster;
                        uploadPic("#logo_upload_button", "association.logo", "#logoPic", "logo");
                        uploadPic("#poster_upload_button", "association.poster", "#posterPic", "poster");
                        var upload_button_name_logo = "#logo_upload_button";
                        var pic_name_logo = "association.logo";
                        var pic_div_name_logo = "#logoPic";
                        var pic_id_logo = "logo";

                        var upload_button_name_poster = "#poster_upload_button";
                        var pic_name_poster = "association.poster";
                        var pic_div_name_poster = "#posterPic";
                        var pic_id_poster = "poster";

                        uploadPic(upload_button_name_logo, pic_name_logo, pic_div_name_logo, pic_id_logo);
                        uploadPic(upload_button_name_poster, pic_name_poster, pic_div_name_poster, pic_id_poster);

                        if(associationLogo != null && associationLogo != '' && !$('#logo').val()){
                            buildPicDIV(upload_button_name_logo, pic_name_logo, pic_div_name_logo, associationLogo,pic_id_logo);
                            $(upload_button_name_logo).prop('disabled', 'disabled');
                        }
                        if(associationPoster != null && associationPoster != '' && !$('#poster').val()){
                            buildPicDIV(upload_button_name_poster, pic_name_poster, pic_div_name_poster, associationPoster, pic_id_poster);
                            $(upload_button_name_poster).prop('disabled', 'disabled');
                        }
                        if(result.currentChange){
                            if(result.currentChange.newName){
                                $("#changeName").show();
                                $("#inputName").val(result.currentChange.newName);
                            }
                            if(result.currentChange.newTypeId){
                                $("#changeType").show();
                                $("#selectType").combobox('setValues',result.currentChange.newTypeId?result.currentChange.newTypeId:"");
                            }
                            showBack();
                        }
                    }
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加載中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        var submitChange = function(){

            var name = $("#inputName").val();
            var typeId = $("#selectType").combobox('getValue');


            $.ajax({
                url: '${pageContext.request.contextPath}/association/associationHistoryAction!changeORCancel.action',
                data : {
                    'associationHistory.newName' : name,
                    "associationHistory.newTypeId": typeId,
                    "actionType":"1"
                },
                dataType : 'json',
                success : function (result) {
                    if(result.success){
                        parent.$.messager.alert('提示', result.msg, 'success');
                    }else{
                        parent.$.messager.alert('提示', result.msg, 'error');
                    }
                    loadInfo();
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加載中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        };

        var subInfo = function(pos){

            var json;

            if(pos == 1){
               json = {
                    'association.id': associationId,
                    'association.concatName' : $("#concatName").val(),
                    "association.telephone": $("#telephone").val(),
                    "association.email": $("#email").val()
                };
            }else{
                var logoUrl = '';
                var posterUrl = '';
                if($('#logo').val()){
                    logoUrl = $('#logo').val();
                }
                if($('#poster').val()){
                    posterUrl = $('#poster').val();
                }
                json = {
                    "association.id": associationId,
                    "association.introduction": $('#introduction').val(),
                    "association.logo":logoUrl,
                    "association.poster":posterUrl
                };
            }

            alert(JSON.stringify(json));
            $.ajax({
                url: '${pageContext.request.contextPath}/association/associationAction!doNotNeedSecurity_updateAssociationInfo.action',
                data : json,
                dataType : 'json',
                success : function (result) {
                    if(result.success){
                        parent.$.messager.alert('提示', result.msg, 'success');
                    }else{
                        parent.$.messager.alert('提示', result.msg, 'error');
                    }
                    loadInfo();
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加載中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        };

        var cancleChange = function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/association/associationHistoryAction!changeORCancel.action',
                data : {
                    "actionType":"2"
                },
                dataType : 'json',
                success : function (result) {
                    if(result.success){
                        parent.$.messager.alert('提示', result.msg, 'success');

                    }else{
                        parent.$.messager.alert('提示', result.msg, 'error');
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/association/associationHistoryAction!changeORCancel.action',
                        data : {
                            'associationHistory.newName' : name,
                            "associationHistory.newTypeId": typeId,
                            "actionType":"1"
                        },
                        dataType : 'json',
                        success : function (result) {
                            if(result.success){
                                parent.$.messager.alert('提示', result.msg, 'success');
                            }else{
                                parent.$.messager.alert('提示', result.msg, 'error');
                            }
                            loadInfo();
                        },
                        beforeSend : function() {
                            parent.$.messager.progress({
                                text : '数据加載中....'
                            });
                        },
                        complete:function(){
                            parent.$.messager.progress('close');
                        }
                    });
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加載中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        function viewPoster() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看海报',
                iconCls:'ext-icon-note_add',
                url : "${pageContext.request.contextPath}/page/admin/association/associationPoster.jsp",
            });
        }

        var change = function () {
            $("#changeName").show();
            $("#changeType").show();
            $("#btn01").hide();
            $("#btn02").show();
            $("#btn03").show();
            $("#btn04").hide();
        }

        var cancleEdit = function () {
            $("#changeName").hide();
            $("#changeType").hide();
            $("#btn01").show();
            $("#btn02").hide();
            $("#btn03").hide();
            $("#btn04").hide();
        }

        var showBack = function () {
            $("#btn01").hide();
            $("#btn02").hide();
            $("#btn03").hide();
            $("#btn04").show();
            $("#inputName").prop("readonly", true);
            $("#selectType").combobox("readonly", true);
        }

        function buildPicDIV(upload_button_name, picName, picDivName, picURL, picId)
        {
            $(picDivName).append(
                '<div style="float:left;width:180px;">'+
                '<img src="'+picURL+'" width="150px" height="150px"/>'+
                '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                '</div>'+
                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+picURL+'"/></div>'
            );
        }
        function uploadPic(upload_button_name, picName, picDivName, picId)
        {
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
                                '<div style="float:left;width:180px;">'+
                                '<img src="'+resp.url+'" width="150px" height="150px"/>'+
                                '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
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

        function removePic(pic, upload_button_name)
        {
            $(pic).parent().remove();
            $(upload_button_name).prop('disabled', false);
        }

    </script>
</head>

<body>
<form method="post" class="form">
    <fieldset>
        <legend>社团信息</legend>
        <table class="ta001">
            <tr>
                <th>社团名称</th>
                <td width="220" style="border-right: none;">
                    <span id="name"></span>
                </td>
                <td style="border-left: none">
                    <span id="changeName" style="display: none" >----->
                    <input type="text" id="inputName" />
                    </span>
                </td>
                <td  width="110" align="center" rowspan="2">
                    <a id="btn01" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-note_edit',plain:true" onclick="change()">变更</a>
                    <a id="btn02" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-cross',plain:true" onclick="cancleEdit()" style="float:right;display: none">取消</a>
                    <a id="btn03" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-arrow_up',plain:true" onclick="submitChange()" style="float:right;display: none">提交</a>
                    <a id="btn04" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="cancleChange()" style="display: none" >撤销变更</a>
                </td>
            </tr>
            <tr>
                <th>社团类型</th>
                <td style="border-right:none;">
                    <span id="type"></span>
                </td>
                <td style="border-left: none">
                    <span id="changeType" style="display: none">----->
                        <input  class="easyui-combobox" id="selectType" style="width: 150px;"
                                data-options="editable:false,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/association/associationAction!doNotNeedSecurity_getAssociationType.action'" />
                    </span>
                </td>
            </tr>
            <tr>
                <th>社团简介</th>
                <td colspan="2">
                    <textarea id="introduction" rows="8" cols="100" name="association.introduction" style="width: 700px; height: 150px;" ></textarea>
                </td>
                <td align="center" rowspan="3" style="border-bottom: none" >
                    <a onclick="subInfo(0)" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-save',plain:true"  id="saveIntroduction">保存</a>
                    <%--<a onclick="viewPoster()" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-note',plain:true"  >海报</a>--%>
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td colspan="2">
                    <input type="button" id="logo_upload_button" value="上传log">
                    <div id="logoPic">

                    </div>
                </td>
            </tr>
            <tr>
                <th>海报图片</th>
                <td colspan="2">
                    <input type="button" id="poster_upload_button" value="上传海报图片">
                    <div id="posterPic">

                    </div>
                </td>
            </tr>
            <tr>
                <th>二维码</th>
                <td colspan="3">
                    <img style="padding: 5px 5px 12px" src="" width="150px" height="150px" id="qrCodeUrl" />
                </td>
            </tr>
        </table>
    </fieldset>

    <fieldset>
        <legend>社团联系人信息</legend>
        <table class="ta001">
            <tr>
                <th>姓名</th>
                <td>
                    <input id="concatName" type="text" />
                </td>
                <td width="100" align="center" rowspan="3">
                    <a id="saveConcator" class="easyui-linkbutton"
                       data-options="iconCls:'ext-icon-save',plain:true" onclick="subInfo(1)">保存</a>
                </td>
            </tr>
            <tr>
                <th>手机号</th>
                <td>
                    <input id="telephone" type="text" />
                </td>
            </tr>
            <tr>
                <th>邮箱</th>
                <td>
                    <input id="email" type="text" />
                </td>
            </tr>
        </table>

    </fieldset>

    <fieldset id="history">


    </fieldset>

</form>
</body>
</html>