<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
<#include "../../../include.ftl">
</head>
<body style="overflow-y: hidden"  scroll="no">
<table id="orderInfoTable" class="easyui-datagrid" title="订单列表" pageSize="20" style="padding:30px;" fit=true
       data-options="singleSelect:false,collapsible:false,
				url:'${webRoot}/paymanage/list',
				pagination:true,
				toolbar:'#toolbar',
				idField:'orderId',
				method:'get',
				remoteSort:false,
				multiSort:true,
				rownumbers:true
	            ">
    <thead>

    <tr>
        <th data-options="field:'orderId',checkbox:true"></th>
        <th data-options="field:'tradeAmount',width:150,align:'center'">交易金额</th>
        <th data-options="field:'tradeStatus',width:150,align:'center',formatter:formatTradeStatus">交易状态</th>
        <th data-options="field:'tradeTime',width:150,align:'center',formatter:formatExpireDate">交易时间</th>
        <th data-options="field:'createTime',width:150,align:'center',formatter:formatExpireDate">订单生成时间</th>
        <th data-options="field:'title',width:200,align:'center'">体验名称</th>
        <th data-options="field:'startTime',width:200,align:'center',formatter:formatExpireDate">服务开始时间</th>
        <th data-options="field:'endTime',width:200,align:'center',formatter:formatExpireDate">服务结束时间</th>
    </tr>
    </thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        账号：<input type="text" class="easyui-textbox" style="width:120px;" id="account" name="account" maxlength="100"/>
        <a id ="query" onclick="queryCondition()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">查询</a>
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


    //回車鍵調用查詢事件
    $("#account").keyup(function(event){
        var key = event.which;
        if(key==13){
            $("#query").click();
        }
    });
    //设置查询条件
    function queryCondition(){
        var account= $("#account").val();
        $('#orderInfoTable').datagrid({
            queryParams: {
                "account": account,
            }
        });
    }
    //格式化时间
    function formatExpireDate(val,row){
        return format1(val, 'yyyy-MM-dd HH:ss');
    }
    function formatTradeStatus(val, row){
        if(val = 0){
            return "交易未开始";
        }
        if(val = 1){
            return "交易进行中";
        }
        if(val = 2){
            return "交易成功";
        }
        if(val = 3){
            return "支付失败";
        }
        if(val = 3){
            return "退款成功";
        }

    }
    var format1 = function(time, format){
        var t = new Date(time);
        var tf = function(i){return (i < 10 ? '0' : '') + i};
        return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
            switch(a){
                case 'yyyy':
                    return tf(t.getFullYear());
                    break;
                case 'MM':
                    return tf(t.getMonth() + 1);
                    break;
                case 'mm':
                    return tf(t.getMinutes());
                    break;
                case 'dd':
                    return tf(t.getDate());
                    break;
                case 'HH':
                    return tf(t.getHours());
                    break;
                case 'ss':
                    return tf(t.getSeconds());
                    break;
            };
        });
    };
</script>
</body>
</html>
