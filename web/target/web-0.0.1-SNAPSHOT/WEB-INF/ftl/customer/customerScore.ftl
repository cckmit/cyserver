<!DOCTYPE html>
<html>
<head>
    <title>评分信息</title>
<#include "../../../include.ftl">
</head>
<body style="overflow-y: hidden"  scroll="no">
<table id="scoreTable" class="easyui-datagrid"  pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/score/list/${(customerAccountVO.customerId)!''}',
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
        <th data-options="field:'score',width:150,align:'center'">分数</th>
        <th data-options="field:'account',width:150,align:'center'">评分人</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        评分人：<input type="text" class="easyui-textbox" style="width:120px;" id="account" name="account" maxlength="100"/>
        <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
    </div>
</div>
<script type="text/javascript">
//设置查询条件
function queryCondition(){
var account = $('#account').val();
$('#scoreTable').datagrid({
queryParams: {
"account": account
}
});
}
</script>
</body>
</html>
