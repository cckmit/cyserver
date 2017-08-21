<!DOCTYPE html>
<html>
<head>
    <title>体验管理</title>
	<#include "../../../include.ftl">
</head>
<body style="overflow-y: hidden"  scroll="no">
<table id="experienceDetailTable" class="easyui-datagrid" title="信息列表" pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/experienceDetail/list',
				pagination:true,
				toolbar:'#toolbar',
				idField:'experienceDetailId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
    <thead>

    <tr>
        <th data-options="field:'experienceDetailId',checkbox:true"></th>
        <th data-options="field:'title',width:150,align:'center',formatter:formateTitle">标题</th>
        <th data-options="field:'rendezvous',width:150,align:'center'">集合地</th>
        <th data-options="field:'comment',width:150,align:'center'">备注</th>
        <th data-options="field:'peopleNumber',width:150,align:'center'">人数</th>
        <th data-options="field:'serviceName',width:150,align:'center'">服务类型</th>
        <th data-options="field:'enabled',width:150,align:'center',formatter:formateEnabled">体验状态</th>
        <th data-options="field:'_operate',width:130,align:'center',formatter:formateCommentApplicant">操作</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        服务类型：  <input class="easyui-combobox" id="serviceName"  value="${(serviceType.serviceName)!'-全部-'}"  data-options="url:'${webRoot}/experienceDetail/serviceNameCombobox2',valueField:'serviceTypeId',textField:'serviceName',panelMaxHeight:100,panelHeight:'auto',required:true,novalidate:true,editable:false"/>
        体验状态：<select class="easyui-combobox" id="enabled" data-options="required:true,editable:false,panelHeight:'auto'" style="width:120px;">
        <option value ="-1">-全部-</option>
        <option value ="0">未发布</option>
        <option value ="1">已发布</option>
        <option value ="2">已取消</option>
    </select>
        <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addExperienceDetail();">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delExperienceDetail();">删除</a>
    </div>
</div>
<script type="text/javascript">
    function formateEnabled(value,row,index){
        if(value==0){
            return "未发布"
        }if(value==1){
            return "已发布"
        }if(value==2){
            return "已取消"
        }
    }
    //添加体验
    function addExperienceDetail() {
        var url = "${webRoot}/experienceDetail/add";
        parent.$("#dialog").dialog({
            title:"添加体验",
            content:createFrame(url),
            width:850,
            height:690,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }

    //格式化
    function formateTitle(value,row,index){
        return '<a href="#" onclick="editExperienceDetail(\''+row.experienceDetailId+'\')">'+row.title+'</a>';
    }

    //格式化评论管理
    function formateCommentApplicant(value,row,index){
        return '<a href="#" onclick="commentMessage(\''+row.experienceDetailId+'\')">评论管理</a>';
    }
    //评论管理
    function commentMessage(experienceDetailId){
        var url = "${webRoot}/commentMessage/init/"+experienceDetailId;
        parent.$("#dialog").dialog({
            title:"评论管理",
            content:createFrame(url),
            width:850,
            height:600,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }
    //修改体验
    function editExperienceDetail(experienceDetailId){
        var url = "${webRoot}/experienceDetail/modify/"+experienceDetailId;
        parent.$("#dialog").dialog({
            title:"修改体验",
            content:createFrame(url),
            width:850,
            height:690,
            top:($(window).height()-600)*0.5,
            left:($(window).width()-500)*0.5
        });
        parent.$("#dialog").dialog("open");
    }

    //创建视图
    function createFrame(url,experienceDetailId) {
        var s = '<iframe name="experienceDetailEditFrame" scrolling="yes" frameborder="0"  src="'+url+'" style="width:100%;height:98%;"></iframe>';
        return s;
    }

    //删除角色（注意id的名字和数据库中的肯能不一样）
    function delExperienceDetail() {
        var experienceDetailIds = "";
        var row = $('#experienceDetailTable').datagrid('getChecked');
        if(!row||row.length==0) {
            $.messager.alert('提示','请选择需要删除的!','info');
            return;
        }
        for(var i = 0 ; i<row.length ; i ++) {
            experienceDetailIds += row[i].experienceDetailId + ",";
        }
        $.messager.confirm('提示','确认删除选择的角色？',function(r){
            if(r) {
                $.ajax({
                    type:"get",
                    url:"${webRoot}/experienceDetail/delete",
                    data:{
                        "experienceDetailIds":experienceDetailIds
                    },
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
    //设置查询条件
    function queryCondition(){
        var serviceName = $('#serviceName').combobox("getValue");
        var enabled = $('#enabled').combobox("getValue");
        $('#experienceDetailTable').datagrid({
            queryParams: {
                "serviceName": serviceName,
                "enabled": enabled
            }
        });
    }
</script>
</body>
</html>
