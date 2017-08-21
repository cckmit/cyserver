<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/16/17
  Time: 4:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
        <%@page import="com.cy.util.WebUtil"%>
        <%@ page import="com.cy.system.Global" %>
        <%@ taglib prefix="s" uri="/struts-tags"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
            <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    int isRichTextConvert = Global.IS_RICH_TEXT_CONVERT;
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
        var id = '${param.id}';
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#content',{
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

            var button = $("#pic_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
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
                        $('#posterPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removePic(this, \'#pic_upload_button\')"></div><input type="hidden" id="channelIcon" name="fxjh.poster" value="'+msg.url+'"/></div>');
                        $("#pic_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });
            if(id != '0'){
                $.ajax({
                    url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!getFxjhById.action',
                    data: { 'fxjh.id' : id },
                    dataType: 'json',
                    success: function (result) {

                        if (result.id != undefined) {
                            $('form').form('load', {
                                'fxjh.id': result.id,
                                'fxjh.topic': result.topic,
                                'fxjh.name': result.name,
                                'fxjh.place': result.place,
                                'fxjh.signupStartTime': result.signupStartTime,
                                'fxjh.signupEndTime': result.signupEndTime,
                                'fxjh.number': result.number,
                                'fxjh.time': result.time,
                                'fxjh.endTime': result.endTime,
                                'fxjh.needSignIn': result.needSignIn,
                                'fxjh.signInCode': result.signInCode
                            });
                            var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId=';
                            switch (result.classinfo.length){
                                case 6:
                                    $('#school').combobox('setValue', result.classinfo);
                                    var newUrl = url + result.classinfo;
                                    $('#depart').combobox('reload', newUrl);
                                    break;
                                case 10:
                                    $('#school').combobox('setValue', result.classinfo.substring(0,6));
                                    var newUrl = url + result.classinfo.substring(0,6);
                                    $('#depart').combobox('reload', newUrl);
                                    $('#depart').combobox('setValue', result.classinfo);
                                    newUrl = url + result.classinfo;
                                    $('#grade').combobox('reload', newUrl);
                                    break;
                                case 14:
                                    $('#school').combobox('setValue', result.classinfo.substring(0,6));
                                    var newUrl = url + result.classinfo.substring(0,6);
                                    $('#depart').combobox('reload', newUrl);
                                    $('#depart').combobox('setValue', result.classinfo.substring(0,10));
                                    newUrl = url + result.classinfo.substring(0,10);
                                    $('#grade').combobox('reload', newUrl);
                                    $('#grade').combobox('setValue', result.classinfo);
                                    newUrl = url + result.classinfo;
                                    $('#classes').combobox('reload',newUrl);
                                    break;
                                case 16:
                                    $('#school').combobox('setValue', result.classinfo.substring(0,6));
                                    var newUrl = url + result.classinfo.substring(0,6);
                                    $('#depart').combobox('reload', newUrl);
                                    $('#depart').combobox('setValue', result.classinfo.substring(0,10));
                                    newUrl = url + result.classinfo.substring(0,10);
                                    $('#grade').combobox('reload', newUrl);
                                    $('#grade').combobox('setValue', result.classinfo.substring(0, 14));
                                    newUrl = url + result.classinfo.substring(0, 14);
                                    $('#classes').combobox('reload',newUrl);
                                    $('#classes').combobox('setValue', result.classinfo);
                                    break;
                            }
                            $('#other').html(result.other?result.other:"");
                            editor.html(result.content);
                            var poster = result.poster;
                            uploadPic("#pic_upload_button", "fxjh.poster", "#poster");
                            var upload_button_name = "#pic_upload_button";
                            var pic_name = "fxjh.poster";
                            var pic_div_name = "#posterPic";
                            uploadPic(upload_button_name, pic_name, pic_div_name);
                            if(poster != null && poster != ''){
                                buildPicDIV(upload_button_name, pic_name, pic_div_name, poster);
                                $(upload_button_name).prop('disabled', 'disabled');
                            }

                            var arr=[];
                            var str;
                            var sercont = '<input class="sercont" name="fxjh.services" type="hidden"/>';
                            $('#serlist').empty();
                            str=result.services.split("|");

                            for(var i=0;i<str.length;i++){
                                arr.push(str[i].split("-"))
                            }
                            for(var k=0;k<arr.length;k++){
                                if(arr[k][2]=='true'){
                                    sercont+='<p><input onclick="checkser()" checked=true class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="'+arr[k][1]+'" /><span>'+arr[k][1]+'</span></p >'
                                }else{
                                    sercont+='<p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="'+arr[k][1]+'" /><span>'+arr[k][1]+'</span></p >'
                                }


                            }
                            sercont+='<p class="add-p"><input type="text" placeholder="请输入添加的服务"><span onclick="addser()" style="cursor:pointer">添加</span></p >'
                            $('#serlist').html(sercont?sercont:"");


                        }
                    },
                    complete: function () {

                        parent.$.messager.progress('close');

                    }
                });
            }
        });

        function submitForm($dialog, $grid, $pjq) {
            if($('#time').val() == ''){
                $pjq.messager.alert('提示', '请选择开始时间', 'error');
                return;
            }
            if($('#endTime').val() == ''){
                $pjq.messager.alert('提示', '请选择结束时间', 'error');
                return;
            }
            if($('#signupStartTime').val() == ''){
                $pjq.messager.alert('提示', '请选择报名开始时间', 'error');
                return;
            }
            if($('#signupEndTime').val() == ''){
                $pjq.messager.alert('提示', '请选报名择结束时间', 'error');
                return;
            }

            var start = new Date(Date.parse($('#time').val()));
            var end = new Date(Date.parse($('#endTime').val()));
            var signupStartTime = new Date(Date.parse($('#signupStartTime').val()));
            var signupEndTime = new Date(Date.parse($('#signupEndTime').val()));
            if(start < new Date()){
                $pjq.messager.alert('提示', '开始时间必须晚于当前时间', 'error');
                return;
            }
            if(start > end){
                $pjq.messager.alert('提示', '结束时间必须晚于开始时间', 'error');
                return;
            }
            if(signupStartTime > start) {
                $pjq.messager.alert('提示', '报名开始时间必须早于截止时间', 'error');
                return false;
            }
            if(signupStartTime > start) {
                $pjq.messager.alert('提示', '报名开始时间必须早于活动开始时间', 'error');
                return false;
            }
            if(signupEndTime > end) {
                $pjq.messager.alert('提示', '报名截止时间必须早于活动结束时间', 'error');
                return false;
            }
            var url;
            if(id == '0'){
                url = '${pageContext.request.contextPath}/mobile/serv/znfxAction!saveFxjh.action';
            }else{
                url = '${pageContext.request.contextPath}/mobile/serv/znfxAction!updateFxjh.action';
            }

            if ($('form').form('validate')) {
                //富文本是否转编码 lixun 2017.5.5
                if('<%=isRichTextConvert%>' == '1')
                {
                    $("#content").val(strToBase64($("#content").val()));
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
            $(picDivName).append(
                '<div style="float:left;width:180px;">'+
                '<img src="'+picURL+'" width="150px" height="150px"/>'+
                '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                '</div>'+
                '<input type="hidden" name="'+picName+'" value="'+picURL+'"/></div>'
            );
        }
        function uploadPic(upload_button_name, picName, picDivName)
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
                                '<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
                            );


                            $(upload_button_name).prop('disabled', 'disabled');
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
            $(upload_button_name).prop('disabled', false);
        }

        function checkser(){
            var result="";
            for(var i=0;i<$('.check').length;i++){
                var state = $('.check')[i].checked;
                result+=(i+1)+"-"+($('.check')[i].value)+"-"+state+"|";
            }
            result = result.substring(0,result.length-1).toString()
            $('.sercont').val(result);

        }
        function addser(){
            var val = $('.add-p input').val().trim();
            if(val==""){
                $.messager.alert('提示', '提供的服务内容不能为空', 'error');
            }else{
                var result = '<p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="'+val+'" /><span>'+val+'</span></p >';
                $('.add-p').before(result);
                $('.add-p input').val('')

            }
        }
    </script>
</head>

<body>
<form method="post" id="viewAssociationForm">
    <fieldset>
        <legend>
            返校计划
        </legend>
        <table class="ta001">

            <tr>
                <th>
                    组织主题
                </th>
                <td colspan="3">
                    <input name="fxjh.id" type="hidden" >
                    <input name="fxjh.topic" class="easyui-validatebox" data-options="required:true"  id="topic"/>
                </td>
            </tr>

            <tr>
                <th>
                    地点
                </th>
                <td colspan="3">
                    <input name="fxjh.place" class="easyui-validatebox" data-options="required:true"  id="place"/>
                </td>
            </tr>

            <tr>
                <th>
                    主办方
                </th>
                <td colspan="3">
                    <input name="fxjh.name" class="easyui-validatebox" data-options="required:true"  id="name"/>
                </td>
            </tr>

            <tr>
                <th>
                    计划人数
                </th>
                <td colspan="3">
                    <input name="fxjh.number" class="easyui-validatebox" data-options="required:true" type="number" step="1" id="number"/>(0为不设限)
                </td>
            </tr>
            <tr>
                <th>
                    返校范围
                </th>
                <td colspan="3">
                    <input id="school" name="fxjh.classinfo" class="easyui-combobox" style="width: 150px;"
                           data-options="
												valueField: 'deptId',
												textField: 'deptName',
												required:true,
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#school').combobox('clear');
									                $('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#depart').combobox('loadData',[]);
									                }
									            }],
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getDepart.action',
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#depart').combobox('reload', url);
										}" />
                    <input id="depart" name="fxjh.classinfo" class="easyui-combobox" style="width: 150px;"
                           data-options="
												valueField: 'deptId',
												textField: 'deptName',
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('reload', url);
										}" />
                    <input id="grade" name="fxjh.classinfo" class="easyui-combobox" style="width: 150px;"
                           data-options="
												valueField: 'deptId',
												textField: 'deptName',
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);
										}" />
                    <input id="classes" name="fxjh.classinfo" class="easyui-combobox" style="width: 150px;"
                           data-options="
												editable:false,
												valueField:'deptId',
												textField:'deptName',
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#classes').combobox('clear');
									                }
									            }],
												onSelect: function(rec){
												}
												"/>
                </td>

            </tr>
            <tr>
                <th>
                    报名开始
                </th>
                <td colspan="3">
                    <input name="fxjh.signupStartTime" id="signupStartTime" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
                </td>
            </tr>
            <tr>
                <th>
                    报名截止
                </th>
                <td colspan="3">
                    <input name="fxjh.signupEndTime" id="signupEndTime" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
                </td>
            </tr>
            <tr>
                <th>
                    返校时间
                </th>
                <td colspan="3">
                    <input name="fxjh.time" id="time" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
                </td>
            </tr>
            <tr>
                <th>
                    结束时间
                </th>
                <td colspan="3">
                    <input name="fxjh.endTime" id="endTime" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()" />
                </td>
            </tr>

            <tr>
                <th>
                    需要签到
                </th>
                <td colspan="3">
                    <select name="fxjh.needSignIn" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>

            <%--<tr>--%>
                <%--<th>--%>
                    <%--签到码--%>
                <%--</th>--%>
                <%--<td colspan="3">--%>
                    <%--<input name="fxjh.signInCode" class="easyui-validatebox" data-options="required:true"  id="signInCode"/>--%>
                <%--</td>--%>
            <%--</tr>--%>

            <tr>
                <th>
                    描述
                </th>
                <td colspan="3">
                    <textarea id="other" name="fxjh.other" style="width: 700px; height: 120px;"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    详情
                </th>
                <td colspan="3">
                    <textarea id="content" name="fxjh.content" style="width: 700px; height: 300px;"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    计划海报上传
                </th>
                <td colspan="3">
                    <input type="button" id="pic_upload_button" value="上传图片">
                </td>
            </tr>
            <tr>
                <th>
                    计划海报图片
                </th>
                <td colspan="3">
                    <div id="posterPic"></div>
                </td>
            </tr>
            <tr>
                <th>
                    提供的服务
                </th>
                <td colspan="3" id="serlist">
                    <input class="sercont" name="fxjh.services" type="hidden"/>
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="预约附近的酒店" /><span>预约附近的酒店</span></p >
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="返校期间车辆免费进出校园" /><span>返校期间车辆免费进出校园</span></p >
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="食堂餐饮券" /><span>食堂餐饮券</span></p >
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="提供教室" /><span>提供教室</span></p >
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="帮助联系相间的老师/辅导员" /><span>帮助联系想见的老师/辅导员</span></p >
                    <p><input onclick="checkser()" class="check" style="width: 12px;height: 12px;margin: 10px" type="checkbox"  name="" value="浏览校园" /><span>浏览校园</span></p >
                    <p class="add-p"><input type="text" placeholder="请输入添加的服务"><span onclick="addser()" style="cursor:pointer">添加</span></p >
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
