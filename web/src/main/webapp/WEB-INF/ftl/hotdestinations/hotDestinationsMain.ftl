<!DOCTYPE html>
<html>
<head>
    <title>首页热门管理</title>
</head>
<#include "../../../include.ftl">
<body style="overflow-y: hidden"  scroll="no">
<table id="experienceDetailTable" class="easyui-datagrid" title="首页热门管理" pageSize="20" style="padding:30px;" fit=true
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
    <th data-options="field:'experienceDetailId',checkbox:true"></th>
    <th data-options="field:'title',width:150,align:'center'">标题</th>
    <th data-options="field:'subtitle',width:150,align:'center'">副标题</th>
    <th data-options="field:'destination',width:150,align:'center'">目的地</th>
    <th data-options="field:'rendezvous',width:150,align:'center'">集合地</th>
    <th data-options="field:'comment',width:100,align:'center'">备注</th>
    <th data-options="field:'peopleNumber',width:100,align:'center'">人数</th>
    </tr>
</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <table>
            <th> 首页热门管理：
                <select class="easyui-combobox" id="hotAll" name="hotAll" panelHeight='auto'  style="height:20px;width:100px">
                    <option value="2">热门体验</option>
                    <option value="1">热门目的地</option>
                    <option value="3">热门类型</option>
                </select>
            </th>
        <th>
            <div id="divDestination">
                -<select class="easyui-combobox" id="hotDestinationsCombo" name="hotDestinationsCombo" panelHeight='auto'
                        data-options=""
                        style="height:20px;width:150px">
                </select>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editHotDestination();">热门目的地修改</a>
            </div>
        </th>
        <th>
        <div id="divService">
            -<select class="easyui-combobox" id="hotServiceCombo" name="hotServiceCombo" panelHeight='auto'  style="height:20px;width:150px">
            </select>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editHotService();">热门服务类型修改</a>
        </div>
        </th>
        <th>
        <div id="commonCombo">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addExperienceDetail();">添加体验</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delExperienceDetail();">删除体验</a>
        </div>
        </th>
    </div>
    </table>
</div>
<script type="text/javascript">

    //flag位控制请选择下的点击
    //flag位控制请选择下的点击
    //初始化隐藏热门服务类型热门目的地修改按钮
    $(function() {
        $("#divDestination").hide();
        $("#divService").hide();
        $("#commonCombo").show;
        $("#hotAll").combobox('setValue', '2');

        //热门目的地
        $("#hotDestinationsCombo").combobox({
            url:'${webRoot}/hotAll/hotDestinations',
            valueField:'destinationId',
            textField:'destination',
            value:'-1'
        });
        //热门类型
        $("#hotServiceCombo").combobox({
            url:'${webRoot}/hotAll/hotService',
            valueField:'serviceTypeId',
            textField:'serviceName',
            value:'-1'
        });
        $("#hotDestinationsCombo").combobox({
            onChange: function(newValue,oldValue) {
                $('#experienceDetailTable').datagrid({
                    url:'${webRoot}/hotAll/listHot?option='+$("#hotAll").combobox('getValue')+"&hotDestination="+newValue+"&hotService="+$("#hotServiceCombo").combobox('getValue'),
                });
                $('#experienceDetailTable').datagrid('reload');
            }
        })
        $("#hotServiceCombo").combobox({
            onChange: function(newValue,oldValue) {
                $('#experienceDetailTable').datagrid({
                    url:'${webRoot}/hotAll/listHot?option='+$("#hotAll").combobox('getValue')+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+newValue,
                });
                $('#experienceDetailTable').datagrid('reload');
            }
        })
        //热门管理事件
        $("#hotAll").combobox({
            onChange: function(newValue,oldValue) {
                if(newValue == 1){
                    $("#divDestination").show();
                    $("#divService").hide();
                    $("#commonCombo").hide;
                    $('#experienceDetailTable').datagrid({
                        url:'${webRoot}/hotAll/listHot?option='+newValue+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+$("#hotServiceCombo").combobox('getValue'),
                    });
                    $('#experienceDetailTable').datagrid('reload');
                }
                if(newValue == 3){
                    $("#divService").show();
                    $("#divDestination").hide();
                    $("#commonCombo").hide;
                    $('#experienceDetailTable').datagrid({
                        url:'${webRoot}/hotAll/listHot?option='+newValue+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+$("#hotServiceCombo").combobox('getValue'),
                    });
                    $('#experienceDetailTable').datagrid('reload');
                }
                if(newValue == 2){
                    $("#divService").hide();
                    $("#divDestination").hide();
                    $("#commonCombo").show;
                    $('#experienceDetailTable').datagrid({
                        url:'${webRoot}/hotAll/listHot?option='+newValue+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+$("#hotServiceCombo").combobox('getValue'),
                    });
                    $('#experienceDetailTable').datagrid('reload');
                }
            }
        })
        $('#experienceDetailTable').datagrid({
            url:'${webRoot}/hotAll/listHot?option='+$("#hotAll").combobox('getValue')+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+$("#hotServiceCombo").combobox('getValue'),
        });
    })

    //添加体验
    function addExperienceDetail() {
        if($("#hotAll").combobox('getValue') == '1' && $("#hotDestinationsCombo").combobox('getValue') == '-1'){
            $.messager.alert('提示','请选择热门目的地!','info');
            return;
        }
        if($("#hotAll").combobox('getValue') == '3' && $("#hotServiceCombo").combobox('getValue') == '-1'){
            $.messager.alert('提示','请选择热门服务类型!','info');
            return;
        }
        var url = "${webRoot}/hotAll/add?option="+$("#hotAll").combobox('getValue')+"&hotDestination="+$("#hotDestinationsCombo").combobox('getValue')+"&hotService="+$("#hotServiceCombo").combobox('getValue');
        parent.$("#dialog").dialog({
            title:"添加体验",
            content:createFrame(url),
            width:850,
            height:700,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }


    //创建视图
    function createFrame(url,experienceDetailId) {
        var s = '<iframe name="experienceDetailEditFrame" scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:98%;"></iframe>';
        return s;
    }

    //删除角色
    function delExperienceDetail() {
        var experienceDetailIds = "";
        var row = $('#experienceDetailTable').datagrid('getChecked');
        if(!row||row.length==0) {
            $.messager.alert('提示','请选择需要删除的!','info');
            return;
        }
        for(var i = 0 ; i<row.length ; i ++) {
            experienceDetailIds += row[i].experienceId + ",";
        }
        $.messager.confirm('提示','确认删除选择的体验？',function(r){
            if(r) {
                $.ajax({
                    type:"get",
                    url:"${webRoot}/hotAll/delete?option="+$("#hotAll").combobox('getValue')+"&experienceIds="+experienceDetailIds,
                    async:false,
                    error:function(request) {
                    },
                    success:function(data) {
                        if(data == "false"){
                            $.messager.alert('提示','请勿删除！','info');
                        }
                        $('#experienceDetailTable').datagrid('load');
                    }
                });
            }
        });
    }
    //热门目的地修改
    function editHotDestination(){
        var url = "${webRoot}/hotAll/editHotDestination";
        parent.$("#dialog").dialog({
            closable:false,
            title:"修改热门目的地",
            content:createFrame(url),
            width:500,
            height:300,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }

    //热门服务修改
    function editHotService(){
//        if('-1' == $("#hotServiceCombo").combobox('getValue')) {
//            $.messager.alert('提示','请选择需要删除的!','info');
//            return;
//        }
        var url = "${webRoot}/hotAll/editHotService";
        parent.$("#dialog").dialog({
            closable:false,
            title:"修改热门服务类型",
            content:createFrame(url),
            width:500,
            height:300,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }
</script>
</body>
</html>
