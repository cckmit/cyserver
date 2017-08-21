<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
<#include "../../../include.ftl">
    <script type="text/javascript" src="${webRoot}/statics/base/js/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="${webRoot}/statics/base/js/DateUtil.js"></script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<table id="experienceDetailTable" class="easyui-datagrid"  pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
				pagination:true,
				toolbar:'#toolbar',
				idField:'experienceId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
    <thead>

    <tr>
        <th data-options="field:'experienceId',checkbox:true"></th>
        <th data-options="field:'title',width:150,align:'center'">标题</th>
        <th data-options="field:'subtitle',width:150,align:'center'">副标题</th>
        <th data-options="field:'destination',width:150,align:'center'">目的地</th>
        <th data-options="field:'rendezvous',width:150,align:'center'">集合地</th>
        <th data-options="field:'comment',width:150,align:'center'">备注</th>
        <th data-options="field:'peopleNumber',width:150,align:'center'">人数</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addExperienceDetail();">添加体验</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeWindow();">关闭</a>
</div>
    <script type="text/javascript">
        //获取变量
        var option = "${option}";
        var hotDestination = "${hotDestination}";
        var hotService = "${hotService}";
        $('#experienceDetailTable').datagrid({
            url:'${webRoot}/hotAll/listAdd?option='+option+"&hotDestination="+hotDestination+"&hotService="+hotService,
        });

        //添加体验
        function addExperienceDetail() {
            var row = $('#experienceDetailTable').datagrid('getSelections');
            if(!row) {
                $.messager.alert('提示','请选择需要添加的体验!','info');
                return;
            }
            var ids = "";
            for(var i = 0; i < row.length; i++) {
                ids += row[i].experienceId + ",";
            }
            if(option == 2){
                $.ajax({
                    type : "post",
                    url : "${webRoot}/hotAll/addHotExperience?ids="+ids,
                    success: function (data) {
                        $('#experienceDetailTable').datagrid('reload');
                        var frame = $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#' + 'experienceDetailTable');
                        frame.datagrid('load');
                    }
                })
            }
           if(option == 1){
               $.ajax({
                   type : "post",
                   url : "${webRoot}/hotAll/addHotDestinationExperience?ids="+ids+"&hotDestination="+hotDestination,
                   success: function (data) {
                       $('#experienceDetailTable').datagrid('reload');
                       var frame = $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#' + 'experienceDetailTable');
                       frame.datagrid('load');
                   }
               })
           }
           if(option == 3){
               $.ajax({
                   type : "post",
                   url : "${webRoot}/hotAll/addHotServiceExperience?ids="+ids+"&hotService="+hotService,
                   success: function (data) {
                       $('#experienceDetailTable').datagrid('reload');
                       var frame = $('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#' + 'experienceDetailTable');
                       frame.datagrid('load');
                   }
               })
           }
        }

        function closeWindow() {
            parent.$("#dialog").dialog("close");
            parent.$('#tabs').tabs('getSelected').find('iframe')[0].contentWindow.$('#experienceDetailTable').datagrid('load');
        }
    </script>
</body>
</html>