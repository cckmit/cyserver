<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <%--<button class="ui-btn-lg" data-href="../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=${project.projectId}&accountNum=${accountNum}">返回</button>
    --%><c:if test="${project.projectName!=null}">
   		<button class="ui-btn-lg ui-btn-danger"  id="submitPay">支付</button>
    </c:if>
</footer>
<section class="ui-container donate-form">
    <div class="ui-form ui-border-t">
        <form id="form1" name="form1" action="../donation/donationAction!doNotNeedSessionAndSecurity_donationSave.action" method="post">
           	<input type="hidden" name="donation.projectId" value="${project.projectId}">
                <input type="hidden" name="donation.accountNum" value="${accountNum}">
                <input type="hidden" name="donation.payMethod" value="手机">
            <div class="ui-form-item ui-form-item-link ui-border-b" data-href="../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=${project.projectId}&accountNum=${accountNum}">
                <label>捐赠项目</label>
                <input type="text" value="${project.projectName}" readonly>
            </div>
            <div class="ui-form-item ui-border-b">
                <label>捐赠金额</label>
                <input type="text" id="money" name="donation.money" placeholder="请输入捐赠金额">
            </div>
            <div class="ui-form-item ui-form-item-switch ui-border-b">
                <p>
                	    匿名捐赠
                </p>
                <label class="ui-switch">
                    <input type="checkbox" id="anonymous"  onclick="changeA()">
                    <input type="hidden" id="anonymous0" name="donation.anonymous"/>
                </label>
            </div>
           <div class="ui-form-item ui-form-item-textarea ui-border-b">
                <label>捐赠留言</label>
                <textarea name="donation.message" placeholder="请输入您的留言"></textarea>
            </div>
            <div class="ui-form-item ui-form-item-textarea ui-border-b">
                <label>备注</label>
                <textarea name="donation.remark"></textarea>
            </div>
        </form>
    </div>
</section>
<script src="<%=basePath%>js/zepto.js"></script>
<script src="<%=basePath%>js/cy_core.js"></script>
<script src="<%=basePath%>js/custom_donate.js"></script>
</body>
<script type="text/javascript">
	function validate_form(thisform){
		with (thisform)
		{
			if (validate_money(money,"请输入正确的金额!")==false)
			{
				return false;
			}else{
				return true;
			}
		}
	}
	
	function validate_money(field,alerttxt)
	{
		with (field)
		{
			if (/^(\d+\.\d{1,2}|\d+)$/.test(value)) 
			{
				return true;
			}
			else 
			{
				//alert(alerttxt);
				return false;
			}
		}
	}
	
	$("#submitPay").tap(function(){
		if(validate_form(document.form1)){
			var da=$.dialog({
		        title:'温馨提示',
		        content:'确认提交订单吗？提交后无法修改！',
		        button:["确认","取消"]
		        
		    });
			
			da.on("dialog:action",function(e){
				if(e.index==0){
					document.form1.submit();
				}
		    });
			
			
		}else{
		    var dia=$.dialog({
		        title:'温馨提示',
		        content:'请输入正确的金额！',
		        button:["确认"]
		    });
		}
	});
	
	function changeA(){
    	if(document.getElementById("anonymous").checked){
    		document.getElementById("anonymous0").value='1';
    	}else{
    		document.getElementById("anonymous0").value='0';
    	}
    }
	
</script>
</html>