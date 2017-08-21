<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "../../../include.ftl">
</head>
<body>
<a id ="query" onclick="wap_pay()" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-search'">支付</a>
<script type="text/javascript">
            function wap_pay(channel) {
                var amount = 100;
                var pay_url = "http://localhost:8087/web-interface/pay/pay/149916548747537787"; // 改成自己的url,就行了~其他的不用改.
                var xhr = new XMLHttpRequest();
                xhr.open("POST", pay_url, true);
                    xhr.setRequestHeader("Content-type", "application/json");
                xhr.send(JSON.stringify({
                    channel: channel,
                    amount: amount
                }));

                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        console.log(xhr.responseText);
                        pingpp.createPayment(xhr.responseText, function(result, err) {
                            console.log(result);
                            console.log(err);
                        });
                    }
                }
            }
</script>
</body>
</html>