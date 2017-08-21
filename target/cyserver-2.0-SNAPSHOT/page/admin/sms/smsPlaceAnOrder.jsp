<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">

        $(function () {
            // 获取短信账号信息
            getSmsAccountInfo();
        });

        // 获取短信账号信息
        function getSmsAccountInfo() {
            $('#accountId').prop('disable', true);

            $.ajax({
                url : '${pageContext.request.contextPath}/smsAccount/smsAccountAction!getAccountInfoByCurrentDept.action',
                dataType : 'json',
                success : function(result) {
                    if(result && result.success && result.obj){
                        $('#accountId').val(result.obj.account);

                        // 根据购买条数，以及后台设置的梯度单价，计算出总金额
                        countTotalAmount();
                    }else {
                        $('#accountId').val("短信账号不存在，请先开通！");
                    }
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        // 根据购买条数，以及短信云后台设置的梯度单价，计算出总金额
        function countTotalAmount () {
            var buyNum = 1;   // 购买条数
            if($('#buyNum').val() && $('#buyNum').val() != '') {
                buyNum = $('#buyNum').val();
            }

            $.ajax({
                url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!doNotNeedSecurity_countTotalAmount.action?buyNum=' + buyNum*10000,
                dataType : 'json',
                success : function(result) {
                    if(result){
                        $('form').form('load', {
                            'smsBuyWater.singlePrice' : result.singlePrice,
                            'smsBuyWater.buyPrice' : result.totalPrice,
                            'smsBuyWater.flowPacketDetailId' : result.flowPacketDetailId
                        });
                    }
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });

                },
                complete : function() {
                    parent.$.messager.progress('close');
                }
            });
        }

        // 计算金额所对应的条数
        function count() {
            var totalPrice = 0;
            if($('#buyPrice').val() && $('#buyPrice').val() != '') {
                totalPrice = $('#buyPrice').val();
            }

            $.ajax({
                url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!doNotNeedSecurity_getTotal.action?price=' + totalPrice,
                dataType : 'json',
                success : function(result) {
                    if(result && result.success){
                        $('form').form('load', {
                            'smsBuyWater.singlePrice' : result.singlePrice,
                            'smsBuyWater.buyNum' : result.total,
                            'smsBuyWater.flowPacketDetailId' : result.flowPacketDetailId
                        });
                    }
                }
            });
        }
        
        // 提交表单
        function submitForm( $dialog, $pjq ) {
            if ($('form').form('validate')) {
                $.ajax({
                    url : '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!saveSmsBuyWater.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result && result.success && result.obj) {
                            $pjq.messager.confirm('提示', "下单成功，前往支付？", function (r) {
                                if (r) {
                                    window.open('${pageContext.request.contextPath}/page/admin/sms/smsOrderInfo.jsp?id=' + result.obj.id);
                                }
                                else {
                                    $pjq.messager.alert('提示', "未支付订单可从短信流水完成支付", 'info');
                                }
                                $dialog.dialog('destroy');
                            });

                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function() {
                        $pjq.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function() {
                        $pjq.messager.progress('close');
                    }
                });
            }
        }
    </script>
</head>
<body>
<form method="post" class="form">

        <table class="ta001">
            <tr>
                <th>应用账号</th>
                <td>
                    <input type="text" id="accountId" readonly />
                </td>
            </tr>
            <tr>
                <th>购买条数</th>
                <td>
                    <input type="number" id="buyNum" class="easyui-validatebox" name="smsBuyWater.buyNum" value="1" min="1" step="1" data-options="required:true" onchange="countTotalAmount()" />万条
                </td>
            </tr>
            <tr>
                <th>单价</th>
                <td>
                    <input type="text" id="singlePrice" name="smsBuyWater.singlePrice" readonly />元
                </td>
            </tr>
            <tr>
                <th>总额</th>
                <td>
                    <input type="number" id="buyPrice" name="smsBuyWater.buyPrice" readonly />元
                </td>
            </tr>
        </table>
</form>
</body>
</html>
