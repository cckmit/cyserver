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
                    <input id="experienceId"  type="hidden"  name="experienceId"   value="${(experienceVO.experienceId)!''}">
                    <input id="customerId"  type="hidden"  name="customerId"   value="${(experienceDetailVO.customerId)!''}">
                    <td>
                        <input id="title" class="easyui-textbox"   name="title" style="width:150px;"  disabled="true " value="${(experienceDetailVO.title)!''}">
                    </td>
                </tr>
                <tr>
                    <td class="t-title" width="30%">评价内容：<font style="color:red">*</font></td>
                    <td colspan="3">
                        <input name="commentContent" id="commentContent" class="easyui-textbox" data-options="required:true,novalidate:true,multiline:true,validType:['nospace','lengthCharacter[500,1000]']" style="height:90px;width:85%" value=""/>
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