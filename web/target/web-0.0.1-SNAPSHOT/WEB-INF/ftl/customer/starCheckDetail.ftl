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
    .table-list td{ border-right:1px solid #DFE0E5; border-bottom:1px solid #DFE0E5; background:#fff; padding:6px 6px 6px 5px;}
</style>
<body style="overflow-y: hidden" scroll="no">
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
<form id="customerDetailEditForm" action="${webRoot}/customerDetail/save" method="post">
    <div class="easyui-panle"> <img  src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>客户账号信息</strong></div>
    <table class="table-list">
        <tr>
            <td class="t-title" width="30%">帐号：<font style="color:red">*</font></td>
            <input id="customerId" type="hidden"  name="customerId"   value="${(customerAccountVO.customerId)!''}">
            <td>
                <input id="account" class="easyui-textbox"   name="account"  disabled="true " value="${(customerAccountVO.account)!''}">
            </td>
            <td class="t-title" width="30%" align="center">客户类型：<font style="color:red">*</font></td>
            <td>
                <input id="customerType" class="easyui-textbox"  disabled="true " name="customerType"  value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">密码：<font style="color:red">*</font></td>

            <td>
                <input data-options="novalidate:true,validType:['noChinese','nospace','specialCharactersComma','length[0,100]']" id="password" name="password" maxlength="100" maxlength="100" type="password" value=""
                       class="easyui-textbox"   />
            </td>
            <td class="t-title" width="30%">确认密码：<font style="color:red">*</font></td>

            <td>
                <input data-options="novalidate:true" id="repassword" maxlength="100"  name="repassword" type="password" value=""
                       class="easyui-textbox" validType="equalTo['#password']" />
            </td>

        </tr>
        <tr>
            <td class="t-title" width="30%">手机号：<font style="color:red">*</font></td>

            <td>
                <input id="mobile" class="easyui-textbox"  data-options="required:true,novalidate:true,validType:'phoneRex'" title="请输入正确的手机号码！~"  name="mobile"  value="${(customerAccountVO.mobile)!''}">
            </td>
            <td class="t-title" width="30%">邮箱：<font style="color:red">*</font></td>

            <td>
                <input id="email" maxlength="100" maxlength="100" name="email" value="${(customerAccountVO.email)!''}"
                       class="easyui-textbox" data-options="required:true,novalidate:true,validType:['email','length[0,25]']" title="邮箱格式不对"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">QQ：</td>

            <td>
                <input id="QQ" class="easyui-textbox"   name="QQ"  value="${(customerAccountVO.QQ)!''}">
            </td>
            <td class="t-title" width="30%">微信：</td>

            <td>
                <input id="wechat" class="easyui-textbox"   name="wechat"  value="${(customerAccountVO.wechat)!''}">
            </td>

        </tr>
        <tr>
            <td colspan="4" id="accountmessage">
                <img  src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>客户基本信息</strong>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">昵称：</td>

            <td>
                <input id="nickName" class="easyui-textbox"   name="nickName"  value="${(customerDetailVO.nickName)!''}">
            </td>
            <td class="t-title" width="30%"  align="left">性别：<font style="color:red">*</font></td>
            <td>
                <input type="hidden" id="gender" />
                <input   type="radio" id="men" name="gender" value="1" />男
                <input   type="radio" id="women" name="gender" value="0" />女
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">IMTOKEN：</td>

            <td>
                <input id="imToken" class="easyui-textbox"  disabled="true " name="imToken"  value="${(customerDetailVO.imToken)!''}">
            </td>
            <td class="t-title" width="30%">工作：</td>

            <td>
                <input id="job" class="easyui-textbox"   name="job"  value="${(customerDetailVO.job)!''}">
            </td>

        </tr>
        <tr>
            <td class="t-title" width="30%">学校：</td>

            <td>
                <input id="school" class="easyui-textbox"   name="school"  value="${(customerDetailVO.school)!''}">
            </td>
            <td class="t-title" width="30%">语言：</td>

            <td>
                <input id="language" class="easyui-textbox"   name="language"  value="${(customerDetailVO.language)!''}">
            </td>

        </tr>
        <tr>
            <td class="t-title" width="30%">收款帐号：</td>
            <td >
                <input  id="accountNo" maxlength="200"  class="easyui-textbox" data-options="validType:'stringRexTwo',novalidate:true" name="accountNo" value="${(customerDetailVO.accountNo)!''}"/>
            </td>
            <td class="t-title" width="30%">身份验证状态：</td>
            <td>
                <input id="idcardCheckStatus" class="easyui-textbox"   name="idcardCheckStatus"  disabled="true " value="${(customerDetailVO.idcardCheckStatus)!''}">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">所在地：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整（精确到乡/镇）" id="area" width=150px maxlength="200"  class="easyui-textbox" data-options="validType:'stringRexTwo',novalidate:true" name="area" value="${(customerDetailVO.area)!''}"/>
            </td>
        </tr>
    </table>
    <div class="easyui-panle"><img src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>附件信息</strong></div>
    <table class="table-list">
        <tr>
            <td class="t-title" style="width:15%;"  >法人身份证扫描件（正反面）：<div style="float:right;padding:3px 0px 0px 0px;color:red">*</div></td>
            <td style="width:250px;padding: 0px 4px;">
                <input id="idcardImg" type="hidden" maxlength="200" name="idcardImg" value=""/>
                <div style="float:left; margin:15px;padding: 0px 4px;">
                    <div id="attachmentEdit1" style="z-index:2;"> </div>
                    <img class="pimg" src="${webRoot}/statics/images/sfz.png" width="35" height="35" >
                    <span>查看样例</span>
                </div>
                <div style="margin-top:14px; border:1px solid #c0c0c0">
                    <img id = "idcardImage" class="pimg" src="${webRoot}/file/filedownload?downloadfiles=${(customerDetailVO.idcardImg)!''}" width="70" height="70">
                </div>

            </td>
            <td>
                <span>照片所有信息需清晰可见，内容真实有效，不得做任何修改。照片支持.jpg .jpeg .bmp .gif .png格式，大小不超过8M。</span>
            </td>
        </tr>
        <tr>
            <td class="t-title" style="width:15%;"  >客户头像：<div style="float:right;padding:3px 0px 0px 0px;color:red">*</div></td>
            <td style="width:250px;padding: 0px 4px;">
                <input id="headImg" type="hidden" maxlength="200" name="headImg" value=""/>
                <div style="float:left; margin:15px;padding: 0px 4px;">
                    <div id="attachmentEdit2"> </div>
                    <img class="pimg" src="${webRoot}/statics/images/youcai.jpg" width="35" height="35">
                    <span>查看样例</span>
                </div>
                <div style="margin-top:14px; border:1px solid #c0c0c0">
                    <img class="pimg" id = "headImage" src="${webRoot}/file/filedownload?downloadfiles=${(customerDetailVO.headImg)!''}" width="70" height="70">
                </div>
            </td>
            <td>
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
                <a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="closeWindow()">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
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
            $("#idcardCheckStatus").textbox('setValue', '未审核');;
        }
        else if(${(customerDetailVO.idcardCheckStatus)!''} == 1){
            $("#idcardCheckStatus").textbox('setValue', '审核中');
        }else if(${(customerDetailVO.idcardCheckStatus)!''} == 2){
            $("#idcardCheckStatus").textbox('setValue', '审核不通过');
        }
        else if(${(customerDetailVO.idcardCheckStatus)!''} == 9){
            $("#idcardCheckStatus").textbox('setValue', '审核通过');
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
        var attachment = $("#attachmentEdit1");
        var   fieldId = $("#idcardImg");
        var id = $("#idcardImage");
        if(myBrowser()=="Chrome") {
            powerWebUpload(attachment,fieldId,id);
        } else {
            powerWebUploadOther(attachment,fieldId,id);
        }
    });
    $(function(){
        var attachment = $("#attachmentEdit2");
        var   fieldId = $("#headImg");
        var id = $("#headImage");
        if(myBrowser()=="Chrome") {
            powerWebUpload(attachment,fieldId,id);
        } else {
            powerWebUploadOther(attachment,fieldId,id);
        }
    });
    //取消
    function closeWindow() {
        parent.$("#dialog").dialog("close");
    }
</script>
<script type="text/javascript" src="${webRoot}/statics/base/js/component/imagePreview.js"></script>
<script type="text/javascript" src="${webRoot}/statics/base/js/component/BrowserUtil.js"></script>
</body>
</html>