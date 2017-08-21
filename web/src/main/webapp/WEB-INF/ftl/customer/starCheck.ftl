<!DOCTYPE html>
<html>
	<head>
	    <title>客户管理</title>
	<#include "../../../include.ftl">
	</head>
	<body style="overflow-y: hidden"  scroll="no">
	    <table id="customerAccountTable" class="easyui-datagrid" title="信息列表" pageSize="20" style="padding:30px;" fit=true
	            data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/starAccount/list',
				pagination:true,
				toolbar:'#toolbar',
				idField:'customerId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
	        <thead>

	        <tr>
				<th data-options="field:'customerId',hidden:'hidden' "></th>
				<th data-options="field:'account',width:150,align:'center',formatter:formateAccountName">帐号</th>
				<th data-options="field:'mobile',width:150,align:'center'">手机号</th>
				<th data-options="field:'email',width:150,align:'center'">邮箱</th>
                <th data-options="field:'wechat',width:150,align:'center'">微信</th>
                <th data-options="field:'QQ',width:150,align:'center'">QQ</th>
 </tr>
	        </thead>
	    </table>
	    <div id="toolbar" style="padding:5px;height:auto">
	    	<div style="margin-bottom:5px">
                账号：<input type="text" class="easyui-textbox" style="width:120px;" id="account" name="account" maxlength="100"/>
                手机号：<input type="text" class="easyui-textbox" style="width:120px;" id="mobile" name="mobile" maxlength="100"/>
                <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="checkPass();">审核通过</a>
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="checkFail();">审核不通过</a>
			</div>
	    </div>
	    <script type="text/javascript">
            //审核不通过（注意id的名字和数据库中的肯能不一样）
            function checkFail() {
                var row = $('#customerAccountTable').datagrid('getChecked');
                if(!row||row.length==0) {
                    $.messager.alert('提示','请选择需要审核的!','info');
                    return;
                };
                var customerId = row[0].customerId;
                $.messager.confirm('提示','确认审核不通过？',function(r){
                    if(r) {
                        $.ajax({
                            type:"get",
                            url:"${webRoot}/starAccount/checkFail",
                            data:{
                                "customerId":customerId
                            },
                            async:false,
                            error:function(request) {
                            },
                            success:function(data) {
                                if(data == "false"){
                                    $.messager.alert('提示','请勿审核！','info');
                                }
                                $('#customerAccountTable').datagrid('load');
                            }
                        });
                    }
                });
            }
            //格式化用户名
            function formateAccountName(value,row,index) {
                var s = '<a href="#" onclick="editCustomerAccount(\''+row.customerId+'\')">'+row.account+'</a>';
                return s;
            }
            $('#customerAccountTable').datagrid({
                onClickCell:function(rowIndex, field, value){
                    if(field == "account"){
                        $(this).datagrid('clearSelections');
                    }
                }
            });
			//查看客户信息
			function editCustomerAccount(customerId){
			  var url = "${webRoot}/starAccount/modify/"+customerId;
				parent.$("#dialog").dialog({
					title:"修改客户",
					content:createFrame(url),
					width:850,
    				height:700,
    				top:($(window).height()-600)*0.5,
    				left:($(window).width()-500)*0.5
				});
				parent.$("#dialog").dialog("open");
			}

			//创建视图
			function createFrame(url,customerId) {
             var s = '<iframe name="customerAccountEditFrame" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:98%;"></iframe>';
				return s;
			}

			//审核通过（注意id的名字和数据库中的肯能不一样）
			function checkPass() {
				var row = $('#customerAccountTable').datagrid('getChecked');
				if(!row||row.length==0) {
					$.messager.alert('提示','请选择需要审核的!','info');
					return;
				}
                var customerId = row[0].customerId;
				$.messager.confirm('提示','确认审核通过？',function(r){
					if(r) {
						$.ajax({
			    			type:"get",
			    			url:"${webRoot}/starAccount/checkPass",
			    			data:{
			    				"customerId":customerId
			    			},
			    			async:false,
			    			error:function(request) {
			    			},
			    			success:function(data) {
			    				if(data == "false"){
			    					$.messager.alert('提示','请勿审核！','info');
			    				}
			    				$('#customerAccountTable').datagrid('load');
			    			}
			    		});
					}
				});
			}

            //回車鍵調用查詢事件
            $("#account").keyup(function(event){
                var key = event.which;
                if(key==13){
                    $("#query").click();
                }
            });
            $("#mobile").keyup(function(event){
                var key = event.which;
                if(key==13){
                    $("#query").click();
                }
            });
            //设置查询条件
            function queryCondition(){
                var account= $("#account").val();
                var mobile= $("#mobile").val();
                $('#customerAccountTable').datagrid({
                    queryParams: {
                        "account": account,
                        "mobile": mobile
                    }
                });
            }

		</script>
	</body>
</html>
