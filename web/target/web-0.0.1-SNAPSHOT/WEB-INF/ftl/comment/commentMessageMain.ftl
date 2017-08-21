<!DOCTYPE html>
<html>
	<head>
	    <title>评论管理</title>
	    <#include "../../../include.ftl">
        <script type="text/javascript" src="${webRoot}/statics/base/js/DateUtil.js"></script>
	</head>
	<body style="overflow-y: hidden"  scroll="no">
	    <table id="commentMessageTable" class="easyui-datagrid"  pageSize="20" style="padding:30px;" fit=true
	            data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/commentMessage/list/${(experienceDetailVO.experienceDetailId)!''}',
				pagination:true,
				toolbar:'#toolbar',
				idField:'commentId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true 
	            ">
	        <thead>
	        
	        <tr>
            <th data-options="field:'commentId',checkbox:true"></th>
			<th data-options="field:'commentContent',width:150,align:'center',formatter:formateComment">评价内容</th>
			<!--
			<th data-options="field:'checkStatus',width:150,align:'center'">审核状态</th>
			<th data-options="field:'checkPeople',width:150,align:'center'">审核人</th>
			-->
            <th data-options="field:'account',width:150,align:'center'">评论人</th>
			<th data-options="field:'remark',width:150,align:'center'">备注</th>
            <th data-options="field:'createTime',width:150,align:'center',formatter:formatTimeYYYYMMDD">创建时间</th>
 </tr>
	        </thead>
	    </table>
	    <div id="toolbar" style="padding:5px;height:auto">
	    	<div style="margin-bottom:5px">
                评论人：<input type="text" class="easyui-textbox" style="width:120px;" id="account" name="account" maxlength="100"/>
                <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCommentMessage();">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delCommentMessage();">删除</a>
			</div>
	    </div>
	    <script type="text/javascript">
	        //添加
			function addCommentMessage() {
				var url = "${webRoot}/commentMessage/add/${(experienceDetailVO.experienceDetailId)!''}";
				parent.$("#edittwo").dialog({
					title:"添加评论",
					content:createFrame(url),
					width:450,    
    				height:310,
    				top:($(window).height()-310)*0.5,
    				left:($(window).width())*0.5
				});
				parent.$("#edittwo").dialog("open");
			}
			//格式化
			function formateComment(value,row,index){
			    return '<a href="#" onclick="commentMessageDescribe(\''+row.commentId+'\')">'+row.commentContent+'</a>';
			}
			
			//查看详情并修改
			function commentMessageDescribe(commentId){
				 var row = $('#commentMessageTable').datagrid('getSelected');
				if(!row&&!commentId) {
					$.messager.alert('提示','请选择需要查看的!','info');
					return;
				}
				if(!commentId) {
					commentId = row.commentId;
				}
			
				var url = "${webRoot}/commentMessage/modify/${(experienceDetailVO.experienceDetailId)!''}/"+commentId;
				parent.$("#edittwo").dialog({
					title:"修改评论",
					content:createFrame(url),
					width:450,
    				height:310,
    				top:($(window).height()-310)*0.5,
    				left:($(window).width())*0.5
				});
				parent.$("#edittwo").dialog("open");
			}

			//创建视图
			function createFrame(url,commentId) {
             var s = '<iframe name="commentMessageEditFrame" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:98%;"></iframe>';
				return s;
			}
			
			//删除角色（注意id的名字和数据库中的肯能不一样）
			function delCommentMessage() {
				var commentIds = "";
				var row = $('#commentMessageTable').datagrid('getChecked');
				if(!row||row.length==0) {
					$.messager.alert('提示','请选择需要删除的!','info');
					return;
				}
				for(var i = 0 ; i<row.length ; i ++) {
					commentIds += row[i].commentId + ",";
				}
				$.messager.confirm('提示','确认删除选择的角色？',function(r){
					if(r) {
						$.ajax({
			    			type:"get",
			    			url:"${webRoot}/commentMessage/delete",
			    			data:{
			    				"commentIds":commentIds
			    			},
			    			async:false,
			    			error:function(request) {
			    			},
			    			success:function(data) {
			    				if(data == "false"){
			    					$.messager.alert('提示','请勿删除！','info');
			    				}
			    				$('#commentMessageTable').datagrid('load');
			    			}
			    		});
					}
				});
			}
            //设置查询条件
            function queryCondition(){
                var account= $("#account").val();
                $('#commentMessageTable').datagrid({
                    queryParams: {
                        "account": account
                    }
                });
            }
	    </script>
	</body>
</html>
