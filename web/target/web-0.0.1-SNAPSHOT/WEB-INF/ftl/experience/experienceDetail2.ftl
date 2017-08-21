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
        .table-list td{ border-right:1px solid #DFE0E5; border-bottom:1px solid #DFE0E5; background:#fff; padding:4px 4px 4.5px 4.5px;}
</style>
<body style="overflow-x: hidden" scroll="yes">
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" style="border:5px solid #fff;" src="" /></div></div>
<form id="experienceDetailEditForm" action="${webRoot}/customerDetail/save" method="post">
    <div class="easyui-panle"> <img  src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>体验服务信息</strong></div>
    <table class="table-list" id="datetimebox">
        <tr>
            <td class="t-title" width="30%">标题：<font style="color:red">*</font></td>
            <input id="experienceDetailId"  type="hidden"  name="experienceDetailId"   value="${(experienceDetailVO.experienceDetailId)!''}">
            <input id="experienceId"  type="hidden"  name="experienceId"   value="${(experienceVO.experienceId)!''}">
            <input id="destinationId"  type="hidden"  name="destinationId"   value="${(destinationVO.destinationId)!''}">
            <input id="serviceTimeId"  type="hidden"  name="serviceTimeId"   value="${(serviceTimeVO.serviceTimeId)!''}">
            <input id="serviceTypeId"  type="hidden"  name="serviceTypeId"   value="${(serviceTypeVO.serviceTypeId)!''}">
            <td>
                <input id="title" class="easyui-textbox"   name="title"  disabled="true " value="${(experienceDetailVO.title)!''}">
            </td>
            <td class="t-title" width="30%" align="center">副标题：</td>
            <td>
                <input id="subtitle" class="easyui-textbox"  data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="subtitle"  value="${(experienceDetailVO.subtitle)!''}">
            </td>
        </tr>
     <tr>
            <td class="t-title" width="30%" align="center">服务类型名称：<font style="color:red">*</font></td>
            <td>
            <input class="easyui-combobox" id="serviceName"  value="${(serviceTypeVO.serviceName)!'-请选择-'}" name="serviceTypeId" data-options="url:'${webRoot}/experienceDetail/serviceNameCombobox',valueField:'serviceTypeId',textField:'serviceName',panelMaxHeight:100,panelHeight:'auto',required:true,novalidate:true,editable:false"/>
            </td>
            <td class="t-title" width="30%" align="center">体验状态：<font style="color:red">*</font></td>
            <td>
                <input id="enabled" class="easyui-textbox"  disabled="true "  name="enabled"  value="${(experienceVO.enabled)!''}">
            </td>
     </tr>
        <tr>
            <td class="t-title" width="30%" align="center">货币类型：</td>
            <td>
                <input id="currencyType" class="easyui-textbox" data-options="required:true,novalidate:true,validType:['specialCharacters','lengthCharacter[25,50]']"  name="currencyType" value="${(experienceVO.currencyType)!''}">
            </td>
            <td class="t-title" width="30%" align="center">价格：<font style="color:red">*</font></td>
            <td>
                <input id="price" class="easyui-textbox" name="price" data-options="validType:'priceRex',required:true,novalidate:true" value="${(experienceVO.price)!''}">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%" align="center">人数：<font style="color:red">*</font></td>
            <td>
                <input id="peopleNumber"  maxlength="10"  class="easyui-textbox" name="peopleNumber" data-options="validType:'numberRex',required:true,novalidate:true"  value="${(experienceDetailVO.peopleNumber)!''}">
            </td>
            <td class="t-title" width="30%" align="center">排序：<font style="color:red">*</font></td>
            <td>
                <input id="sortNo" name="sortNo" maxlength="8"  class="easyui-textbox"  data-options="validType:'number2Rex',required:true,novalidate:true"    value="${(experienceDetailVO.sortNo)!''}">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">内容描述：</td>
            <td colspan="3">
                <input id="contentDescription" name="contentDescription" class="easyui-textbox" data-options="novalidate:true,multiline:true,validType:['specialCharacters','lengthCharacter[125,250]']" style="height:45px;width:85%" value="${(experienceDetailVO.contentDescription)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">目的地：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整（精确到乡/镇）" id="destination" width=150px maxlength="200"  class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="destination" value="${(experienceDetailVO.destination)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">集合地：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整（精确到乡/镇）" id="rendezvous" width=150px maxlength="200"   class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="rendezvous" value="${(experienceDetailVO.rendezvous)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">体验内容明细：</td>
            <td colspan="3">
                <input name="contentDetails" id="contentDetails" class="easyui-textbox" data-options="novalidate:true,multiline:true,validType:['specialCharacters','lengthCharacter[500,1000]']" style="height:75px;width:85%" value="${(experienceDetailVO.contentDetails)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">要求：</td>
            <td colspan="3">
                <input style="width:85%" prompt="请尽量填写完整" id="requirement" width=150px maxlength="200"  class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[25,50]']" name="requirement" value="${(experienceDetailVO.requirement)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">备注：</td>
            <td colspan="3">
                <input style="width:85%"  id="comment" width=150px   class="easyui-textbox" data-options="novalidate:true,validType:['specialCharacters','lengthCharacter[125,250]']" name="comment" value="${(experienceDetailVO.comment)!''}"/>
            </td>
        </tr>
        <!--
        <tr>
            <td class="t-title" width="30%" align="center">服务日期：<font style="color:red">*</font></td>
            <td>
                <input id="subtitle" class="easyui-datebox"   name="subtitle"  value="">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%" align="center">开始时间：<font style="color:red">*</font></td>
            <td>
                <input id="startTime"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVO.startTime)!''}">
            </td>
            <td class="t-title" width="30%" align="center">结束时间：<font style="color:red">*</font></td>
            <td>
                <input id="endTime"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVO.endTime)!''}">
            </td>
        </tr>
       -->
        <tr >
            <td colspan="4" align="center">
                开始时间：<input id="startTime"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVOList[0].startTime)!''}">
                <input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="${(serviceTimeVOList[0].serviceTimeId)!''}">
                结束时间：<input id="endTime"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVOList[0].endTime)!''}">
                &nbsp&nbsp&nbsp<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addDateTimeBox();">添加</a>
            </td>
        </tr>
    </table>
        <div class="easyui-panle"><img src="${webRoot}/statics/images/icon1.png" width="10" height="10"><strong>图片信息</strong></div>
      <table class="table-list">
         <tr>
            <td class="t-title" style="width:15%;" >图片信息：<div style="float:right;padding:3px 0px 0px 0px;color:red">*</div></td>
            <td style="width:250px;">
                <input id="imageUrl" type="hidden" maxlength="200" name="imageId" value="${(experienceDetailVO.imageId)!''}"/>
                <div style="float:left; margin:15px;">
                    <div id="attachmentEdit2"> </div>
                    <img class="pimg" src="${webRoot}/statics/images/youcai.jpg" width="35" height="35">
                    <span>查看样例</span>
                </div>
                <div style="margin-top:14px; border:1px solid #c0c0c0">
                    <img class="pimg" id = "imgUrl" src="${webRoot}/file/filedownload?downloadfiles=${(experienceDetailVO.imageId)!''}" width="70" height="70">
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
                <a href="#" class="easyui-linkbutton" id="save"  iconcls="icon-save" onclick="saveExperienceDetail()">保存</a>
                <a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="closeWindow()">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
var count=0;
 $(function(){
      if("${(serviceTimeVOList[1].startTime)!''}" != "") {
          count++;
            var targetObj = $('<tr id="' + count + '"> <td colspan="4" align="center">开始时间：<input id="startTime' + count + '"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVOList[1].startTime)!''}"> <input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="${(serviceTimeVOList[1].serviceTimeId)!''}"> 结束时间：<input id="endTime' + count + '"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVOList[1].endTime)!''}"> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox(' + count + ');">删除</a> </td></tr>').appendTo("#datetimebox");
            $.parser.parse(targetObj);
        }
     if("${(serviceTimeVOList[2].startTime)!''}" != "") {
         count++;
         var targetObj = $('<tr id="' + count + '"> <td colspan="4" align="center">开始时间：<input id="startTime' + count + '"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVOList[2].startTime)!''}"><input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="${(serviceTimeVOList[2].serviceTimeId)!''}">结束时间：<input id="endTime' + count + '"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVOList[2].endTime)!''}"> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox(' + count + ');">删除</a> </td></tr>').appendTo("#datetimebox");
         $.parser.parse(targetObj);
     }
     if("${(serviceTimeVOList[3].startTime)!''}" != "") {
         count++;
         var targetObj = $('<tr id="' + count + '"> <td colspan="4" align="center">开始时间：<input id="startTime' + count + '"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVOList[3].startTime)!''}"><input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="${(serviceTimeVOList[3].serviceTimeId)!''}">结束时间：<input id="endTime' + count + '"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVOList[3].endTime)!''}"> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox(' + count + ');">删除</a> </td></tr>').appendTo("#datetimebox");
         $.parser.parse(targetObj);
     }
     if("${(serviceTimeVOList[4].startTime)!''}" != "") {
         count++;
         var targetObj = $('<tr id="' + count + '"> <td colspan="4" align="center">开始时间：<input id="startTime' + count + '"  class="easyui-datetimebox"   name="startTime"  value="${(serviceTimeVOList[4].startTime)!''}"><input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="${(serviceTimeVOList[4].serviceTimeId)!''}">结束时间：<input id="endTime' + count + '"  class="easyui-datetimebox"   name="endTime"  value="${(serviceTimeVOList[4].endTime)!''}"> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox(' + count + ');">删除</a> </td></tr>').appendTo("#datetimebox");
         $.parser.parse(targetObj);
     }
    });
function addDateTimeBox(){
    if(count>3){
        $.messager.alert('提示','最多上传五个时间段!','info');
        return
    }
    count++;
    var  targetObj =$('<tr id="'+count +'"> <td colspan="4" align="center">开始时间：<input id="startTime'+count+'"  class="easyui-datetimebox"   name="startTime"  value=""><input id="serviceTimeId"  name="serviceTimeId" type="hidden" value="new">结束时间：<input id="endTime'+count+'"  class="easyui-datetimebox"   name="endTime"  value=""> &nbsp&nbsp&nbsp <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delDateTimeBox('+count+');">删除</a> </td></tr>').appendTo("#datetimebox");
    $.parser.parse(targetObj);
}
function delDateTimeBox(a){
    count--;
    $("tr[id="+a+"]").remove();
}
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
    //只允许输入最多10位数字
    			 $.extend($.fn.validatebox.defaults.rules, {
      				numberRex: {
        			validator: function(value){
        			value=value.replace(/,/g,'');
       				 var rex= /^[1-9]\d{0,9}$/;
        				if(rex.test(value))
       			 			{
          						return true;
        					}else
        					{
           						return false;
        					}
        					},
        				    message: '只允许输入最多10位数字'
      				}
    			});
      //只允许输入最多8位数字
           			 $.extend($.fn.validatebox.defaults.rules, {
             				number2Rex: {
               			validator: function(value){
               			value=value.replace(/,/g,'');
              				 var rex= /^[1-9]\d{0,7}$/;
               				if(rex.test(value))
              			 			{
                 						return true;
               					}else
               					{
                  						return false;
               					}
               					},
               				    message: '只允许输入最多8位数字'
             				}
           			});
      //只允许输入最多两位小数
                     $.extend($.fn.validatebox.defaults.rules, {
                            priceRex: {
                        validator: function(value){
                        value=value.replace(/,/g,'');
                        var reg =  /\d{1,8}\.{0,1}\d{0,2}/;
                            if(reg.test(value))
                                    {
                                        return true;
                                }else
                                {
                                        return false;
                                }
                                },
                                message: '请输入正确的金额'
                            }
                    });
    $(function(){
        if(${(experienceVO.enabled)!''} == 0){
            $("#enabled").textbox('setValue', '未发布');;
        }
        else if(${(experienceVO.enabled)!''} == 1){
            $("#enabled").textbox('setValue', '已发布');
        }
        else if(${(experienceVO.enabled)!''} == 2){
            $("#enabled").textbox('setValue', '已取消');
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
    function saveExperienceDetail() {
        var  startTimeArray=new Array();
        var  endTimeArray=new Array();
        var serviceTimeIdArray= new Array();
        var x ="";
        $('input[name="startTime"]').each(function(i){
            startTimeArray[i]=$(this).val();
        });
        $('input[name="endTime"]').each(function(i){
            endTimeArray[i]=$(this).val();
        });
        $('input[name="serviceTimeId"]').each(function(i){
            serviceTimeIdArray[i]=$(this).val();
        });
        $("#experienceDetailEditForm").form({
            url:"${webRoot}/experienceDetail/save?startTimeArray="+startTimeArray+"&endTimeArray="+endTimeArray+"&serviceTimeIdArray="+serviceTimeIdArray,
            data:{"startTimeArray":startTimeArray,"endTimeArray":endTimeArray,"serviceTimeIdArray":serviceTimeIdArray},
            onSubmit:function(){
//                if($("#imageUrl").val() == '' ){
//                    $.messager.alert('提示','请上传图片!','info');
//                    return false;
//                }
                if($('#serviceName').combobox('getText') == '-请选择-'){
                    $.messager.alert('提示','请选择服务类型!','info');
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