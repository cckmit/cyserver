<!DOCTYPE html>
<html>
<head>
    <title>编辑客户信息</title>
    <link rel="stylesheet" type="text/css" href="${webRoot}/statics/base/css/uploader/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${webRoot}/statics/base/css/uploader/webuploader.css">
<#include "../../../include.ftl">
    <script type="text/javascript" src="${webRoot}/statics/base/js/uploader/webuploader.min.js"></script>
    <script type="text/javascript" src="${webRoot}/statics/base/js/uploader/fileUpload.js"></script>
    <link href="${webRoot}/statics/base/css/area.css" rel="stylesheet" type="text/css" />
</head>
<style type="text/css">
    #accountmessage{ border-right:1px solid #DFE0E5; padding:1px 1px 1px 1px;font-size:12px}
    .easyui-panle{border-right:1px solid #DFE0E5; padding:1px 1px 1px 1px;font-size:12px}
    .table-list .t-title{background:#F7F7FF; padding:8px 2px 8px 5px; text-align:right; width:15%}
    .head{font-size:15px}
    .table-list td{ border-right:1px solid #DFE0E5; border-bottom:1px solid #DFE0E5; background:#fff; padding:4.5px 4px 5px 4px;}
</style>
<body style="overflow-x: hidden" scroll="yes">
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
<form id="experienceDetailEditForm" action="" method="post">
    <div class="easyui-panle"> <img  src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>体验服务信息</strong></div>
    <table class="table-list" id="datetimebox">
        <tr>
            <td class="t-title" width="30%">标题：<font style="color:red">*</font></td>
            <td>
                <input id="title" class="easyui-textbox"   name="title" data-options="required:true,novalidate:true,validType:['specialCharacters','nospace','lengthCharacter[50,100]']" value="">
            </td>
            <td class="t-title" width="30%" align="center">副标题：</td>
            <td>
                <input id="subtitle" class="easyui-textbox"  data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="subtitle"  value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%" align="center">服务类型名称：<font style="color:red">*</font></td>
            <td>
                <input class="easyui-combobox" id="serviceName"  value="${(serviceType.serviceName)!'-请选择-'}" name="serviceTypeId" data-options="url:'${webRoot}/experienceDetail/serviceNameCombobox',valueField:'serviceTypeId',textField:'serviceName',panelMaxHeight:100,panelHeight:'auto',required:true,novalidate:true,editable:false"/>
            </td>
            <!--
            <td class="t-title" width="30%" align="center">激活标识符：<font style="color:red">*</font></td>
            <td>
                <input id="enabled" class="easyui-textbox"   name="enabled"  value="">
            </td>    -->
            <td class="t-title" width="30%" align="center">货币类型：</td>
            <td>
                <input id="currencyType" class="easyui-textbox"  data-options="required:true,novalidate:true,validType:['specialCharacters','lengthCharacter[25,50]']" name="currencyType"  value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%" align="center">价格：<font style="color:red">*</font></td>
            <td>
                <input id="price" class="easyui-numberbox" min="0.01" max="100000000" precision="2" name="price"  data-options="required:true,novalidate:true,validType:['nospace']" value="">
            </td>
            <td class="t-title" width="30%" align="center">人数：<font style="color:red">*</font></td>
            <td>
                <input id="peopleNumber" class="easyui-numberbox" min="1" max="10000000000" name="peopleNumber"  data-options="required:true,novalidate:true,validType:['specialCharacters','nospace']" precision="0" value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%" align="center">排序：<font style="color:red">*</font></td>
            <td>
                <input id="sortNo" class="easyui-numberbox" min="1" max="10000000000" precision="0"  data-options="required:true,novalidate:true,validType:['specialCharacters','nospace']" name="sortNo"  value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">内容描述：</td>
            <td colspan="3">
                <input id="contentDescription" name="contentDescription" class="easyui-textbox" data-options="novalidate:true,multiline:true,validType:['lengthCharacter[125,250]']" style="height:45px;width:85%" value=""/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">目的地：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整（精确到乡/镇）" id="destination" width=150px maxlength="200"  class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="destination" value=""/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">集合地：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整（精确到乡/镇）" id="rendezvous" width=150px maxlength="200"   class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="rendezvous" value=""/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">体验内容明细：</td>
            <td colspan="3">
                <input name="contentDetails" id="contentDetails" class="easyui-textbox" data-options="novalidate:true,multiline:true,validType:['lengthCharacter[500,1000]']" style="height:75px;width:85%" value=""/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">要求：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整" id="requirement" width=150px maxlength="200"  class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[25,50]']" name="requirement" value=""/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">备注：</td>
            <td colspan="3">
                <input style="width:85%"  id="comment" width=150px maxlength="200"  class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="comment" value=""/>
            </td>
        </tr>
        <!--
        <tr>
            <td class="t-title" width="30%" align="center">服务日期：<font style="color:red">*</font></td>
            <td>
                <input id="subtitle" class="easyui-datebox"   name="subtitle"  value="">
            </td>
        </tr>
        -->
        <tr >
            <td colspan="4" align="center">
                开始时间：<input id="startTime"  class="easyui-datetimebox"   name="startTime"  value="">
                结束时间：<input id="endTime"  class="easyui-datetimebox"   name="endTime"  value="">
                &nbsp&nbsp&nbsp<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDateTimeBox();">添加</a>
            </td>
        </tr>
    </table>
    <div class="easyui-panle"><img src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>图片信息</strong></div>
    <table class="table-list">
        <tr>
            <td class="t-title" style="width:15%;padding: 0px 4px;" >图片信息：<div style="float:right;padding:3px 0px 0px 0px;color:red">*</div></td>
            <td style="width:250px;padding: 0px 4px;">
                <input id="imageUrl" type="hidden" maxlength="200" name="imageId" value=""/>
                <div style="float:left; margin:15px;">
                    <div id="attachmentEdit2"> </div>
                    <img class="pimg" src="${webRoot}/statics/images/youcai.jpg" width="35" height="35">
                    <span>查看样例</span>
                </div>
                <div style="margin-top:14px; border:1px solid #c0c0c0">
                    <img class="pimg" id = "imgUrl" src="${webRoot}/statics/images/youcai.jpg" width="70" height="70">
                </div>
            </td>
            <td style="padding: 0px 4px;">
                <span>照片所有信息需清晰可见，内容真实有效，不得做任何修改。照片支持.jpg .jpeg .bmp .gif .png格式，大小不超过8M。</span>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span style="size:7px;">注：标<font style="color:red">*</font>的为必填项！</span>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="center">
                <a href="#" class="easyui-linkbutton" id="save"  iconcls="icon-save" onclick="saveCustomerDetail()">保存</a>
                <a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="closeWindow()">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
    var count=0;
    $.extend($.fn.validatebox.defaults.rules, {
        equalTo: {
            validator:function(value,param){
                return $(param[0]).val() == value;
            },
            message:'两次密码输入不匹配'
        }
    });
    $.extend($.fn.validatebox.defaults.rules, {
        phoneRex: {
            validator: function (value) {
                var rex = /^1[3-8]+\d{9}$/;
                var rex2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
                if (rex.test(value) || rex2.test(value)) {
                    return true;
                } else {
                    return false;
                }
            },
            message: '手机号码格式错误'
        }
    });
    function addDateTimeBox(){
        if(count>3){
            $.messager.alert('提示','最多上传五个时间段!','info');
            return ;
        }
        count++;
        var  targetObj =$('<tr id="'+count +'"> <td colspan="4" align="center">开始时间：<input id="startTime'+count+'"  class="easyui-datetimebox"   name="startTime"  value="">结束时间：<input id="endTime'+count+'"  class="easyui-datetimebox"   name="endTime"  value=""> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox('+count+');">删除</a> </td></tr>').appendTo("#datetimebox");
        $.parser.parse(targetObj);
    }
    function delDateTimeBox(a){
        count--;
        $("tr[id="+a+"]").remove();
    }
    /*
    $(function(){
        if(${(customerAccountVO.customerType)!''} == 1){
            $("#customerType").textbox('setValue', '普通用户');;
        }
        else {
            $("#customerType").textbox('setValue', '达人');;
        }
    });
    $(function(){
        if(${(customerDetailVO.idcardCheckStatus)!''} == 0){
            $("#idcardCheckStatus").textbox('setValue', '未验证');;
        }
        else if(${(customerDetailVO.idcardCheckStatus)!''} == 1){
            $("#idcardCheckStatus").textbox('setValue', '验证中');
        }else if(${(customerDetailVO.idcardCheckStatus)!''} == 9){
            $("#idcardCheckStatus").textbox('setValue', '验证通过');
        }
    });
    //性别
    $(function(){
        if(${(customerDetailVO.gender)!''} == 1){
            $("#men").attr('checked', 'checked');
        }
        else{
            $("#women").attr('checked', 'checked');
        }
    });
    */
    $(function(){
        $(".pimg").click(function(){
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgPreview("#outerdiv", "#innerdiv", "#bigimg", _this);
        });
    });
    function powerWebUpload(attachment,fieldId,id) {
        attachment.powerWebUpload({
            fileNumLimit:1,
            fileSingleSizeLimit: 8*1024*1024,
            accept:{
                title:'Images',
                extensions:'jpg,jpeg,png,gif,bmp',
                mimeTypes:'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
            },
            innerOptions:{
                onComplete: function (event) {
                    $(".item").hide();
                    var  attachmentId =attachment.GetFilesAddress();
                    var fileName = $(".webuploadinfo").html();
                    var fileId = attachmentId[0];
                    $(".webuploadDelbtn").click();
                    fieldId.val(fileId);
                    id.attr("src","${webRoot}/file/filedownload?downloadfiles="+fileId);

                }
            }
        });
    }
    function powerWebUploadOther(attachment,fieldId,id) {
        attachment.powerWebUpload({
            fileNumLimit:1,
            fileSingleSizeLimit: 8*1024*1024,
            accept:{
                title:'Images',
                extensions:'jpg,jpeg,png,gif,bmp',
                mimeTypes:'image/*'
            },
            innerOptions:{
                onComplete: function (event) {
                    $(".item").hide();
                    var  attachmentId =attachment.GetFilesAddress();
                    var fileName = $(".webuploadinfo").html();
                    var fileId = attachmentId[0];
                    $(".webuploadDelbtn").click();
                    fieldId.val(fileId);
                    id.attr("src","${webRoot}/file/filedownload?downloadfiles="+fileId);

                }
            }
        });
    }
    $(function(){
        var attachment = $("#attachmentEdit2");
        var   fieldId = $("#imageUrl");
        var id = $("#imgUrl");
        if(myBrowser()=="Chrome") {
            powerWebUpload(attachment,fieldId,id);
        } else {
            powerWebUploadOther(attachment,fieldId,id);
        }
    });
    //保存
    function saveCustomerDetail() {
        var  startTimeArray=new Array();
        var  endTimeArray=new Array();
        var x ="";
        $('input[name="startTime"]').each(function(i){
            startTimeArray[i]=$(this).val();
        });
        $('input[name="endTime"]').each(function(i) {
            endTimeArray[i] = $(this).val();
        });
        $("#experienceDetailEditForm").form({
            url:"${webRoot}/experienceDetail/save?startTimeArray="+startTimeArray+"&endTimeArray="+endTimeArray,
            data:{"startTimeArray":startTimeArray,"endTimeArray":endTimeArray},
            onSubmit:function(){
                var startTime = $('#startTime').datetimebox('getValue');
                var endTime = $('#endTime').datetimebox('getValue');
//                if($("#imageUrl").val() == '' ){
//                    $.messager.alert('提示','请上传图片!','info');
//                    return false;
//                }
                if($('#serviceName').combobox('getText') == '-请选择-'){
                    $.messager.alert('提示','请选择服务类型!','info');
                    return false;
                }if(startTime==''||endTime==""){
                    $.messager.alert('提示','请选择开始结束时间!','info');
                    return false;
                }
                $('input[name="startTime"]').each(function(i){
                    if($(this).val()==""||$(this).val()==null) {
                        x="a";
                    }
                });
                $('input[name="endTime"]').each(function(i) {
                    if($(this).val()==""||$(this).val()==null) {
                        x="a";
                    }
                });
                if(x=="a"){
                    $.messager.alert('提示','请选择开始结束日期！','info');
                    return false;
                }
//                if(startTimeArray=="" || endTimeArray==""){
//                    $.messager.alert('提示','请选择开始结束日期！','info');
//                    return false;
//                }
                return $(this).form('validate');
            },
            success:function(data) {
                if(data == "false"){
                    $.messager.alert('提示','已存在!','info');
                }
                parent.reloadData("experienceDetailTable");
                closeWindow();
            }
        });
        $("#experienceDetailEditForm").submit();
    }

    //取消
    function closeWindow() {
        parent.$("#dialog").dialog("close");
    }
</script>
<script type="text/javascript" src="${webRoot}/statics/base/js/component/imagePreview.js"></script>
<script type="text/javascript" src="${webRoot}/statics/base/js/component/BrowserUtil.js"></script>
</body>
</html>