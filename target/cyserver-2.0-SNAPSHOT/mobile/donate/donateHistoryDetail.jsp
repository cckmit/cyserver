<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/mobile/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>捐赠</title>
    <meta name="Description" content="捐赠" />
    <meta name="Keywords" content="窗友,捐赠" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=basePath%>css/cy_core.css">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/donation.css">
</head>
<body>
<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
    <button class="ui-btn-lg" onclick="history.go(-1)">返回</button>
    <c:if test="${donation.payStatus!=1}">
	    <button class="ui-btn-lg ui-btn-primary" id="toPay">去支付</button>
    </c:if>
</footer>
<section class="ui-container donate-show">
    <div class="ui-form ui-border-t">
        <form action="../alipay/wapalipay.jsp" id="form1" name="form1" method="post">
        	 <input type="hidden" name="out_trade_no" id="out_trade_no" value="${donation.orderNo}">
        	 <input type="hidden" name="subject" id="subject" value="${donation.x_name}">
        	 <input type="hidden" name="total_fee" id="total_fee" value="${donation.money}">
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">订单号</label>
                <input type="text" readonly value="${donation.orderNo}">
            </div>
            <div class="ui-form-item ui-form-item-show ui-form-item-link ui-border-b" data-href="../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=${donation.project.projectId}&accountNum=${donation.accountNum}">
                <label for="#">捐赠项目</label>
                <input type="text" readonly value="${donation.project.projectName}">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">捐赠金额</label>
                <input type="text" readonly value="${donation.money}元">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">捐赠时间</label>
                <input type="text" readonly value='<fmt:formatDate value="${donation.donationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
            </div>
            <div class="ui-form-item ui-form-item-content ui-border-b">
                <label>捐赠留言</label>
                <p class="form-content">${donation.message}</p>
            </div>
            <div class="ui-form-item ui-form-item-content ui-border-b">
                <label>备注</label>
                <p class="form-content">${donation.remark}</p>
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">支付方式</label>
                <input type="text" readonly value="支付宝">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">捐赠状态</label>
                <c:choose>
                	<c:when test="${donation.payStatus==1}">
		                <input type="text" readonly value="已完成">
                	</c:when>
                	<c:otherwise>
                		 <input type="text" readonly value="未支付">
                	</c:otherwise>
                </c:choose>
            </div>
        </form>
    </div>
</section>
<script src="<%=basePath%>js/zepto.js"></script>
<script src="<%=basePath%>js/cy_core.js"></script>
<script src="<%=basePath%>js/custom_donate.js"></script>
<script type="text/javascript">
$("#toPay").tap(function(){
	function validate_form(thisform){
		with (thisform)
		{
			if (validate_out_trade_no(out_trade_no)==false||validate_subject(subject)==false||validate_total_fee(total_fee)==false)
			{
				return false;
			}else{
				return true;
			}
		}
	}
	
	function validate_out_trade_no(field)
	{
		with (field)
		{
			if (value!='') 
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
	}
	
	function validate_subject(field)
	{
		with (field)
		{
			if (value!='') 
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
	}
	
	function validate_total_fee(field)
	{
		with (field)
		{
			if (value!='') 
			{
				return true;
			}
			else 
			{
				return false;
			}
		}
	}
	
	if(validate_form(document.form1)){
		document.form1.submit();
	}else{
	    var dia=$.dialog({
	        title:'温馨提示',
	        content:'捐赠信息有误！',
	        button:["确认"]
	    });
	}
});
</script>
</body>
</html>