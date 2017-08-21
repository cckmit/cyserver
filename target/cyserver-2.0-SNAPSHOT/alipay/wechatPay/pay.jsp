<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String payDate = request.getParameter("payDate");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>JFinal-weixin支付测试</title>
		<script src="../../mobile/js/jquery-1.11.1.min.js" type="text/javascript"></script>
		<script src="../../mobile/js/B.js" type="text/javascript"></script>
	</head>
<body>
	JFinal-weixin支付测试<br>
	微信支付v3官方文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6


</body>
<script type="text/javascript">
    var json;
//    json = json.replace(/\%22/g, '"');

	alert('----><%=payDate%>');
//    alert(B.serverUrl + "/alipay/wechatPayAction!doNotNeedSessionAndSecurity_getBrandWCPayDate.action");
//    var openId = "oXha8v31eAlIv3-YO9pNJUXu3jpM" ;
    function getBrandWCPayDate(openId) {
        var data ;

		$.ajax({
			url:B.serverUrl + "/alipay/wechatPayAction!doNotNeedSessionAndSecurity_getBrandWCPayDate.action",
			data:{openId:openId},
            type:"post",
            async: false,
            dataType: 'json',
			success:function (result) {
			    alert("-------> result : "+JSON.stringify(result));
                data = result ;
            },
			err:function (e) {
				alert("-------> e : "+JSON.stringify(e));
            }
        }) ;
		return data;
	}

	function onBridgeReady(){
//        alert(json);
		WeixinJSBridge.invoke(
			'getBrandWCPayRequest',
            <%=payDate%>,
			function(res){
			  /*  alert("-------> 支付结果res : "+JSON.stringify(res));*/
				// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
				if(res.err_msg == "get_brand_wcpay_request:ok" ) {

				}
			}
		);
	}
	if (typeof WeixinJSBridge == "undefined"){
//        var json = getBrandWCPayDate(openId) ;
		if( document.addEventListener ){
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		}else if (document.attachEvent){
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	}else{
		onBridgeReady();
	}
</script>
</html>