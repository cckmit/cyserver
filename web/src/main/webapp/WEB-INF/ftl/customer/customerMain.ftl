<!DOCTYPE html>
<html>
	<head>
	    <title>客户管理</title>
	<#include "../../../include.ftl">
	</head>
	<body style="overflow-y: hidden"  scroll="no">
	    <table id="customerAccountTable" class="easyui-datagrid" title="信息列表" pageSize="20" style="padding:30px;" fit=true
	            data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/customerAccount/list',
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
				<th data-options="field:'customerId',checkbox:true"></th>
				<th data-options="field:'account',width:150,align:'center',formatter:formateAccountName">帐号</th>
				<th data-options="field:'mobile',width:150,align:'center'">手机号</th>
				<th data-options="field:'email',width:150,align:'center'">邮箱</th>
                <th data-options="field:'customerType',width:150,align:'center',formatter:formateCustomerType">客户类型</th>
				<th data-options="field:'_operate',width:130,align:'center',formatter:formateCustomerAccountApplicant">操作</th>
 </tr>
	        </thead>
	    </table>
	    <div id="toolbar" style="padding:5px;height:auto">
	    	<div style="margin-bottom:5px">
                账号：<input type="text" class="easyui-textbox" style="width:120px;" id="account" name="account" maxlength="100"/>
                手机号：<input type="text" class="easyui-textbox" style="width:120px;" id="mobile" name="mobile" maxlength="100"/>
                客户类型：<select class="easyui-combobox" id="customerType" data-options="required:true,editable:false,panelHeight:'auto'" style="width:120px;">
                <option value ="0">-全部-</option>
                <option value ="1">普通客户</option>
                <option value ="2">达人</option>
            	</select>
                <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCustomerAccount();">添加</a>
				 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delCustomerAccount();">删除</a>
			</div>
	    </div>
	    <script type="text/javascript">
			function formateCustomerType(value,row,index){
				if(value==1){
				    return "普通用户"
				}if(value==2){
                    return "达人"
                }
			}
            $(function(){
                $('#customerType').combobox({
                    onChange: function (newVal,oldVal) {
                        if(newVal == "") {
                            alert(newVal);
                            $('#customerType').combobox('setValue',"");
                        }
                        if(newVal == "1") {
                            $('#customerType').combobox('setValue',"1");
                        }
                        if(newVal == "2") {
                            $('#customerType').combobox('setValue',"2");
                        }
                        }
                });
                        });
	        //添加
			function addCustomerAccount() {
				var url = "${webRoot}/customerAccount/add";
				parent.$("#dialog").dialog({
					title:"添加客户",
					content:createFrame(url),
					width:850,
    				height:700,
    				top:($(window).height()-600)*0.5,
    				left:($(window).width()-500)*0.5
				});
				parent.$("#dialog").dialog("open");
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
			//格式化
			function formateCustomerAccountApplicant(value,row,index){
			    return '<a href="#" onclick="checkScore(\''+row.customerId+'\')">评分</a>';
			}
            //第三方账号信息
            function checkThirdAccount(customerId){
                var url = "${webRoot}/customerAccount/thirdAccount/"+customerId;
                parent.$("#dialog").dialog({
                    title:"第三方账号",
                    content:createFrame(url),
                    width:850,
                    height:600,
                    top:($(window).height()-600)*0.5,
                    left:($(window).width()-500)*0.5
                });
                parent.$("#dialog").dialog("open");
            }
            //评分信息
            function checkScore(customerId){
                var url = "${webRoot}/customerAccount/score/"+customerId;
                parent.$("#dialog").dialog({
                    title:"评分管理",
                    content:createFrame(url),
                    width:850,
                    height:600,
                    top:($(window).height()-600)*0.5,
                    left:($(window).width()-500)*0.5
                });
                parent.$("#dialog").dialog("open");
            }
			//修改客户信息
			function editCustomerAccount(customerId){
			  var url = "${webRoot}/customerAccount/modify/"+customerId;
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

			//删除客户（注意id的名字和数据库中的肯能不一样）
			function delCustomerAccount() {
				var customerIds = "";
				var row = $('#customerAccountTable').datagrid('getChecked');
				if(!row||row.length==0) {
					$.messager.alert('提示','请选择需要删除的!','info');
					return;
				}
				for(var i = 0 ; i<row.length ; i ++) {
					customerIds += row[i].customerId + ",";
				}
				$.messager.confirm('提示','确认删除选择的角色？',function(r){
					if(r) {
						$.ajax({
			    			type:"get",
			    			url:"${webRoot}/customerAccount/delete",
			    			data:{
			    				"customerIds":customerIds
			    			},
			    			async:false,
			    			error:function(request) {
			    			},
			    			success:function(data) {
			    				if(data == "false"){
			    					$.messager.alert('提示','请勿删除！','info');
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
                var customerType = $('#customerType').combobox("getValue");
                $('#customerAccountTable').datagrid({
                    queryParams: {
                        "account": account,
                        "mobile": mobile,
                        "customerType":customerType
                    }
                });
            }

		</script>
	</body>
</html>
