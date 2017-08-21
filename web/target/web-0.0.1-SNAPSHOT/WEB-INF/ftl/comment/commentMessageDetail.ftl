<!DOCTYPE html>
<html>
<head>
    <title>编辑</title>
<#include "../../../include.ftl">
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="commentMessageEditForm" action="${webRoot}/commentMessage/save" method="post">
    <table class="table-list">
        <tr>
            <td class="t-title" width="30%">体验标题：<font style="color:red">*</font></td>
            <input id="experienceId"  type="hidden"  name="experienceId"   value="${(commentMessageVO.experienceId)!''}">
            <input id="commentId"  type="hidden"  name="commentId"   value="${(commentMessageVO.commentId)!''}">
            <td>
                <input id="title" class="easyui-textbox"   name="title" style="width:150px;"  disabled="true " value="${(experienceDetailVO.title)!''}">
            </td>
        </tr>
        <!--
        <tr>
            <td class="t-title" width="30%">审核状态<font style="color:red">*</font></td>

            <td>
                <input id="checkStatus" class="easyui-textbox"  disabled="true "  name="checkStatus" style="width:150px;" value="${(commentMessageVO.checkStatus)!''}">
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">审核人：<font style="color:red">*</font></td>

            <td>
                <input id="checkPeople" class="easyui-textbox" disabled="true "  name="checkPeople" style="width:150px;" value="${(commentMessageVO.checkPeople)!''}">
            </td>

        </tr>
        -->
        <tr>
            <td class="t-title" width="30%">评价内容：<font style="color:red">*</font></td>
            <td colspan="3">
                <input name="commentContent" id="commentContent" class="easyui-textbox" data-options="required:true,novalidate:true,multiline:true,validType:['nospace','lengthCharacter[500,1000]']" style="height:90px;width:85%" value="${(commentMessageVO.commentContent)!''}"/>
            </td>
        </tr>
        <tr>
            <td class="t-title" width="30%">备注：<font style="color:red">*</font></td>

            <td>
                <input id="remark" class="easyui-textbox"   name="remark" style="width:150px;" value="${(commentMessageVO.remark)!''}">
            </td>

        </tr>
        <tr>
            <td colspan="2" align="center">
                <a href="#" class="easyui-linkbutton" id="save"  iconcls="icon-save" onclick="saveCommentMessage()">保存</a>
                <a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="closeWindow()">取消</a>
            </td>
        </tr>
    </table>
</form>
<script>
    $(function(){
        if(${(commentMessageVO.checkStatus)!''} == 0){
            $("#checkStatus").textbox('setValue', '未审核');;
        }
        else if(${(commentMessageVO.checkStatus)!''} == 1){
            $("#checkStatus").textbox('setValue', '审核通过');
        }else if(${(commentMessageVO.checkStatus)!''} == 2){
            $("#checkStatus").textbox('setValue', '审核失败');
        }
    });
    //保存
    function saveCommentMessage() {
        $("#commentMessageEditForm").form({
            url:"${webRoot}/commentMessage/save",
            success:function(data) {
                if(data == "false"){
                    $.messager.alert('提示','已存在!','info');
                }
                parent.window.frames["experienceDetailEditFrame"].$("#commentMessageTable").datagrid('reload');
                closeWindow();
            }
        });
        $("#commentMessageEditForm").submit();
    }

    //取消
    function closeWindow() {
        parent.$("#edittwo").dialog("close");
    }
</script>
</body>
</html>