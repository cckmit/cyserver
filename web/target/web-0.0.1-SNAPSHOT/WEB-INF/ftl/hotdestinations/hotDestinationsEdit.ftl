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
                url:'${webRoot}/hotAll/hotDestinationst',
				pagination:true,
				toolbar:'#toolbar',
				idField:'destinationId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
    <thead>

    <tr>
        <th data-options="field:'destinationId',checkbox:true"></th>
        <th data-options="field:'destination',width:150,align:'center'">目的地</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <select class="easyui-combobox" id="hotDestinationsCombo" name="hotDestinationsCombo" panelHeight='auto'  style="height:20px;width:150px">
    </select>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addHotDestination();">添加热门目的地</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delteHotDestination();">删除热门目的地</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeWindow();">关闭</a>
</div>
<script type="text/javascript">
    $(function(){
        $("#hotDestinationsCombo").combobox({
            url:'${webRoot}/hotAll/notHotDestinations',
            valueField:'destinationId',
            textField:'destination',
            value:'-1'
        });
    })

    //添加体验
    function addHotDestination() {
       if( $("#hotDestinationsCombo").combobox('getValue') == '-1'){
           $.messager.alert('提示','请选择需要添加的热门目的地!','info');
           return;
       }
        $.ajax({
            type : "post",
            url : "${webRoot}/hotAll/addHotDestination?id="+$("#hotDestinationsCombo").combobox('getValue'),
            success: function (data) {
                $('#experienceDetailTable').datagrid('reload');
                $("#hotDestinationsCombo").combobox('reload');
                $("#hotDestinationsCombo").combobox('setValue','-1');
            }
        })
    }
    function delteHotDestination(){
        var row = $('#experienceDetailTable').datagrid('getSelections');
        if(!row) {
            $.messager.alert('提示','请选择需要删除的热门目的地!','info');
            return;
        }
        var ids = "";
        for(var i = 0; i < row.length; i++) {
            ids += row[i].destinationId + ",";
        }
        $.ajax({
            type : "post",
            url : "${webRoot}/hotAll/deleteHotDestination?ids="+ids,
            success: function (data) {
                $("#hotDestinationsCombo").combobox('reload');
                $('#experienceDetailTable').datagrid('reload');
                $("#hotDestinationsCombo").combobox('setValue','-1');
                var frame = $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#' + 'experienceDetailTable');
                frame.datagrid('load');
            }
        })
    }
    function closeWindow() {
        parent.$("#dialog").dialog("close");
        parent.$('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#hotDestinationsCombo').combobox('reload');
        parent.$('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#hotDestinationsCombo').combobox('setValue','-1');
    }
</script>
</body>
</html>