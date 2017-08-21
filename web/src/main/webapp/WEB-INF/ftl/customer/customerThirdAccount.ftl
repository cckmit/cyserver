<!DOCTYPE html>
<html>
<head>
    <title>第三方账号信息</title>
<#include "../../../include.ftl">
</head>
<body style="overflow-y: hidden"  scroll="no">
<table id="thirdAccountTable" class="easyui-datagrid"  pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/thirdAccount/list',
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
        <th data-options="field:'qq',width:150,align:'center'">QQ</th>
        <th data-options="field:'wechant',width:150,align:'center'">微信</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        账号：<input type="text" class="easyui-textbox" style="width:120px;" id="qq" name="qq" maxlength="100"/>
        <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
    </div>
</div>
<script type="text/javascript">
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
