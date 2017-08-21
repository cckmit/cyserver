<!DOCTYPE html>
<html>
<head>
<#include "../../../include.ftl">
    <script type="text/javascript" src="${webRoot}/statics/base/js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${webRoot}/statics/base/js/DateUtil.js"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<table id="experienceDetailTable" class="easyui-datagrid"  pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
                url:'${webRoot}/hotAll/hotServicet',
				pagination:true,
				toolbar:'#toolbar',
				idField:'serviceTypeId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
    <thead>

    <tr>
        <th data-options="field:'serviceTypeId',checkbox:true"></th>
        <th data-options="field:'serviceName',width:150,align:'center'">服务名称</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <select class="easyui-combobox" id="hotServiceCombo" name="hotServiceCombo" panelHeight='auto'  style="height:20px;width:150px">
    </select>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addHotService();">添加热门服务</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteHotService();">删除热门服务</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeWindow();">关闭</a>
</div>
<script type="text/javascript">
    $(function(){
        $("#hotServiceCombo").combobox({
            url:'${webRoot}/hotAll/notHotService',
            valueField:'serviceTypeId',
            textField:'serviceName',
            value:'-1'
        });
    })
    //添加体验
    function addHotService() {
        if( $("#hotServiceCombo").combobox('getValue') == '-1'){
            $.messager.alert('提示','请选择需要添加的热门服务!','info');
            return;
        }
        $.ajax({
            type : "post",
            url : "${webRoot}/hotAll/addHotService?id="+$("#hotServiceCombo").combobox('getValue'),
            success: function (data) {
                $('#experienceDetailTable').datagrid('reload');
                $("#hotServiceCombo").combobox('reload');
                $("#hotServiceCombo").combobox('setValue','-1');
            }
        })
    }
    function deleteHotService(){
        var row = $('#experienceDetailTable').datagrid('getSelections');
        if(!row) {
            $.messager.alert('提示','请选择需要删除的热门服务!','info');
            return;
        }
        var ids = "";
        for(var i = 0; i < row.length; i++) {
            ids += row[i].serviceTypeId + ",";
        }
        $.ajax({
            type : "post",
            url : "${webRoot}/hotAll/deleteHotService?ids="+ids,
            success: function (data) {
                $("#hotServiceCombo").combobox('reload');
                $('#experienceDetailTable').datagrid('reload');
                $("#hotServiceCombo").combobox('setValue','-1');
                var frame = $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#' + 'experienceDetailTable');
                frame.datagrid('load');
            }
        })
    }
    function closeWindow() {
        parent.$("#dialog").dialog("close");
        parent.$('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#hotServiceCombo').combobox('reload');
        parent.$('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#hotServiceCombo').combobox('setValue','-1');
    }
</script>
</body>
</html>