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
    <link href="${pageContext.request.contextPath}/css/stylePay.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript">
        $(function () {
            var id = "${param.id}";
            $.ajax({
                url: '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!getSmsBuyWaterById.action',
                data: { 'smsBuyWaterId' : id },
                dataType: 'json',
                success: function (result) {
                    if (result.id != undefined) {
                        (result.orderNo)?$('#orderNo').text(result.orderNo):"";
                        (result.account)?$('#account').text(result.account):"";
                        (result.buyNum)?$('#buyNum').text(result.buyNum):"";
                        (result.buyPrice)?$('#buyPrice').text(result.buyPrice):"";
                        (result.singlePrice)?$('#singlePrice').text(result.singlePrice):"";
                        (result.buyTime)?$('#buyTime').text(result.buyTime):"";
                        if(result.remarks){
                            $('#payYes').click(function () {
                                        window.location.href=result.remarks;
                            });
                        }
                    }
                },
                complete: function () {
                    parent.$.messager.progress('close');
                }
            });
        });
    </script>
</head>
<body>
    <div class="orderDetail">
        <ul class="orderList">
            <li>
                <label style="color:#005ABF;font-size:15px;">订单详情</label>
            </li>
            <li>
                <table align="center" valign="middle" style="line-height: 30px; font-size: 15px">
                    <tr>
                        <th align="right">
                            订单号
                        </th>
                        <td align="left">
                            <span id="orderNo"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            应用账号
                        </th>
                        <td align="left">
                            <span id="account"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            购买条数
                        </th>
                        <td align="left">
                            <span id="buyNum"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            购买金额
                        </th>
                        <td align="left">
                            <span id="buyPrice"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            单条金额
                        </th>
                        <td align="left">
                            <span id="singlePrice"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            购买时间
                        </th>
                        <td align="left">
                            <span id="buyTime"></span>
                        </td>
                    </tr>
                    <tr>
                        <th align="right">
                            支付方式
                        </th>
                        <td align="left">
                            <span id="pic"><img src="${pageContext.request.contextPath}/images/alipay.png" alt="" title=""
                                       style="height: 25px; width: 75px;vertical-align: middle"></span>
                        </td>
                    </tr>
                </table>
            </li>

            <li>
                <input type="button" id="payYes" class="clickBtn" value="前往支付"/>
            </li>
        </ul>

    </div>

</body>
</html>
