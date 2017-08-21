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

    <title>窗友校友智能管理与社交服务平台</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <jsp:include page="../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        function keyLogin(e)
        {
            var key = e.which || event.keyCode;
            if (key == 13) //回车键的键值为13
                $("#login1").click(); //调用登录按钮的登录事件
        }
        $(function()
        {
            $('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
        });
        function loadImage()
        {
            $('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
        }
        function login()
        {
            var userAccount = $('#userAccount').val().trim();
            var userPassword = $('#userPassword').val().trim();
            var validCode = $('#validCode').val().trim();
            if (userAccount == '')
            {
                $('#note').text("请输入帐号");
                $('#userAccount').focus();
                return;
            }
            if (userPassword == '')
            {
                $('#note').text("请输入密码");
                $('#userPassword').focus();
                return;
            }
            if (validCode == '')
            {
                $('#note').text("请输入验证码");
                $('#validCode').focus();
                return;
            }
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_login.action?agent='+getAgent(),
                data : {
                    'userAccount' : userAccount,
                    'userPassword' : userPassword,
                    'validCode' : validCode
                },
                dataType : 'json',
                success : function(result)
                {
                    //alert(JSON.stringify(result));
                    if (result.success && result.returnId=="21")
                    {
                        $('#note').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    }
                    else if (result.success && result.returnId=="200")
                    {
                        $('#note4').text(result.msg);
                        $('#divLogin1').hide();
                        $('#divLoginShit').show();
                        $('#sendsmsbtnShit').prop('disabled', 'disabled');
                        $('#sendsmsbtnShit').css({'color':'#BCBCBC'});
                        var aaa=60;
                        var ttt=setInterval(function(){
                            aaa -= 1;
                            $('#sendsmsbtnShit').val(+aaa+'s后重新发送');
                            if(aaa<=0) {
                                clearInterval(ttt);
                                $('#sendsmsbtnShit').val('重新获取验证码');
                                $('#sendsmsbtnShit').prop('disabled', false);
                                $('#sendsmsbtnShit').css({'color':'#000'});
                            }
                        },1000);
                    }
                    else if (result.success && result.returnId=="500")
                    {
                        $('#note4').text(result.msg);
                        $('#divLogin1').hide();
                        $('#divLoginShit').show();
                    }
                    else if (!result.success && result.returnId=="201")
                    {
                        $('#note3').text(result.msg);
                        $('#note').text('');
                        $('#userPassword').val('');
                        $('#validCode').val('');
                        $('#userId').val(result.obj);
                        onBindClick() ;
                    }
                    else
                    {
                        $('#login1').prop('disabled', false);
                        $('#validCode').val('');
                        $('#note').text(result.msg);
                        $('#kaptchaImage').attr('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action');
                    }
                },
                beforeSend : function()
                {
                    $('#note').text('登陆中....');
                    $('#login1').prop('disabled', 'disabled');
                }
            });
        }
        //手机号登陆
        function loginByTelephone(){
            var telephone = $('#telephone').val().trim();
            var smscode = $('#smscode').val().trim();
			if (telephone == '')
			{
				$('#note2').text("请输入手机号");
				$('#smscode').focus();
				return;
			}
			if (smscode == '')
			{
				$('#note2').text("请输入验证码");
				$('#smscode').focus();
				return;
			}
			$.ajax({
				method : 'POST',
				url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_loginByTelephone.action?date=' + lastLoginTime(),
				data : {
					'userTelephone' : telephone,
					'smscode' : smscode,
				},
				dataType : 'json',
				success : function(result)
				{
					if (result.success && result.returnId=="21")
					{
						$('#note2').text("登录成功页面跳转中....");
						location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
					}
					else if (result.success && result.returnId=="200")
					{
						$('#note2').text(result.msg);
					}
					else if (result.success && result.returnId=="500")
					{
						$('#note2').text(result.msg);
					}
					else if (!result.success && result.returnId=="201")
					{
						$('#note2').text(result.msg);
						onBindClick() ;
					}
					else
					{
						$('#login2').prop('disabled', false);
						$('#smscode').val('');
						$('#note2').text(result.msg);
					}
				},
				beforeSend : function()
				{
					$('#note1').text('登陆中....');
					$('#login1').prop('disabled', 'disabled');
				}
			});
		}
		//手机号绑定
		function numBindAccount(){
            var userId = $('#userId').val().trim();
			var userName = $('#userName').val().trim();
			var userPassword = $('#userCPass').val().trim();
			var userPasswdConfirm = $('#userCPass2').val().trim();
			var userTelephone = $('#userCell').val().trim();
			var smscode = $('#smscode2').val().trim();
			if (userName == '')
			{
				$('#note3').text("请输入姓名 ");
				$('#userName').focus();
				return;
			}
			if (userPassword == '')
			{
				$('#note3').text("请输入密码");
				$('#userCPass').focus();
				return;
			}
			if (userPasswdConfirm == ''){
				$('#note3').text("请输入确认密码");
				$('#userCPass2').focus();
				return;
			}
			if ( userPassword!= userPasswdConfirm ){
				$('#note3').text("请确认两次输入的密码相同");
				$('#userCPass2').focus();
				return;
			}
			if (userTelephone == '')
			{
				$('#note3').text("请输入手机号");
				$('#userCell').focus();
				return;
			}
			if (smscode == '')
			{
				$('#note3').text("请输入验证码");
				$('#smscode2').focus();
				return;
			}
			$.ajax({
				method : 'POST',
				url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_binding.action',
				data : {
                    'userId' : userId,
					'userName' : userName,
					'userPassword' : userPassword,
					'userTelephone' : userTelephone,
					'smscode' : smscode
				},
				dataType : 'json',
				success : function(result)
				{
					//alert(JSON.stringify(result));
					if (result.success)
					{
						$('#note1').text(result.msg);
						$('#userPassword').val('');
                        $('#validCode').val('');
						onBackClick();
						//location.href =;
					}
					else
					{
						$('#smscode2').val('');
						$('#note3').text(result.msg);
					}
				},
				beforeSend : function()
				{
					$('#note3').text('保存中....');
					$('#login1').prop('disabled', 'disabled');
				}
			});
		}


        function sendSmsCode(){
            $('#note2').text("");
            var userAccount = $('#userAccount').val().trim();
            var userPassword = $('#userPassword').val().trim();
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_sendSmscode.action',
                data : {
                    'userAccount' : userAccount,
                    'userPassword' : userPassword
                },
                dataType : 'json',
                success : function(result)
                {
                    if (result.success)
                    {
                        $('#note2').text(result.msg);
                        $('#login2').prop('disabled', false);
                        $('#sendsmsbtn1').prop('disabled',false);
                    }else{
                        location.href = "${pageContext.request.contextPath}/";
                    }
                },
                beforeSend : function()
                {
                    $('#note2').text('正在发送中....');
                    $('#login2').prop('disabled', 'disabled');
                    $('#sendsmsbtn1').prop('disabled', 'disabled');
                }
            });
        }

        function loginGo(){
            $('#note4').text("");
            var userAccount = $('#userAccount').val().trim();
            var userPassword = $('#userPassword').val().trim();
            var smscode = $('#smscodeShit').val().trim();
            if (smscode == '')
            {
                $('#note4').text("请输入短信验证码");
                $('#smscodeShit').focus();
                return;
            }
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_loginGo.action',
                data : {
                    'userAccount' : userAccount,
                    'userPassword' : userPassword,
                    'smscode' : smscode
                },
                dataType : 'json',
                success : function(result)
                {
                    if (result.success)
                    {
                        $('#note4').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    } else{
                        $('#note4').text(result.msg);
                        $('#loginShit').prop('disabled', false);
                        $('#sendsmsbtnShit').prop('disabled',false);
                    }
                },
                beforeSend : function()
                {
                    $('#note4').text('登陆中....');
                    $('#loginShit').prop('disabled', 'disabled');
                    $('#sendsmsbtnShit').prop('disabled', 'disabled');

                }
            });
        }
        function onTab1Click() {
            $('#divLogin1').show();
            $('#divLogin2').hide();
        }
        function onTab2Click() {
            $('#divLogin1').hide();
            $('#divLogin2').show();
        }
        function onBindClick(){
            $('#divLogin1').hide();
            $('#divLogin2').hide();
            $('#divNumBind').show();
        }
        function onBackClick(){
            $('#divNumBind').hide();
            $('#divLogin1').show();
            $('#login1').prop('disabled', false);
        }

        function onShitClick(){
            $('#divLogin1').hide();
            $('#divLoginShit').show();
        }
        function onShitBack(){
            $('#divLoginShit').hide();
            $('#divLogin1').show();
        }

        //用户登陆获取验证码
        function getSmsCode(){
            $('#note2').text("");
            var telephone = $('#telephone').val().trim();
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_getSmscode.action',
                data : {'userTelephone' : telephone},
                dataType : 'json',
                success : function(result)
                {if (result.success)
                {
                    $('#note2').text(result.msg);
                    $('#login2').prop('disabled', false);
                    var aaa=60;
                    var ttt=setInterval(function(){
                        aaa -= 1;
                        $('#sendsmsbtn1').val(+aaa+'s后重新发送');
                        if(aaa<=0) {
                            clearInterval(ttt);
                            $('#sendsmsbtn1').val('重新发送验证码');
                            $('#sendsmsbtn1').prop('disabled', false);
                            $('#sendsmsbtn1').css({'color':'#000'});
                        }
                    },1000);
                }else{
                    $('#note2').text(result.msg);
                    $('#sendsmsbtn1').prop('disabled', false);
                    $('#sendsmsbtn1').css({'color':'#000'});
                }
                },
                beforeSend : function()
                {
                    $('#note2').text('正在发送中....');
                    $('#login2').prop('disabled', 'disabled');
                    $('#sendsmsbtn1').prop('disabled', 'disabled');
                    $('#sendsmsbtn1').css({'color':'#BCBCBC'});
                }
            });
        }
		//绑定手机获取验证码
		function getSmsBindCode(){
			$('#note3').text("");
			var userTelephone = $('#userCell').val().trim();
			$.ajax({
				method : 'POST',
				url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_getSmscode.action',
				data : {'userTelephone' : userTelephone},
				dataType : 'json',
				success : function(result)
				{if (result.success)
				{
					$('#note3').text(result.msg);
					$('#bindConfirm').prop('disabled', false);
					var aaa=60;
					var ttt=setInterval(function(){
						aaa -= 1;
						$('#sendsmsbtn2').val(+aaa+'s后重新发送');
						if(aaa<=0) {
							clearInterval(ttt);
							$('#sendsmsbtn2').val('重新发送验证码');
							$('#sendsmsbtn2').prop('disabled', false);
							$('#sendsmsbtn2').css({'color':'#000'});
						}
					},1000);
				}else{
					$('#note3').text(result.msg);
					$('#sendsmsbtn2').prop('disabled', false);
					$('#sendsmsbtn2').css({'color':'#000'});
				}
				},
				beforeSend : function()
				{
					$('#note3').text('正在发送中....');
					$('#bindConfirm').prop('disabled', 'disabled');
					$('#sendsmsbtn2').prop('disabled', 'disabled');
					$('#sendsmsbtn2').css({'color':'#BCBCBC'});
				}
			});
		}
        function lastLoginTime(){
            var myDate = new Date();
            var llt = myDate.getFullYear()+'/';
            llt += myDate.getMonth()+'/';
            llt += myDate.getDate()+' ';
            llt += myDate.getHours()+':';
            llt += myDate.getMinutes();
            return llt;
        }
        //获取当前浏览器的Agent信息
        function getAgent() {
            var ua = navigator.userAgent.toLowerCase();
            var os;
            if (ua.indexOf("windows nt 5.1") != -1) os= "Windows XP";
            else if (ua.indexOf("windows nt 6.0") != -1) os= "Windows Vista";
            else if (ua.indexOf("windows nt 6.1") != -1) os= "Windows 7";
            else if (ua.indexOf("windows nt 6.2") != -1) os= "Windows 8";
            else if (ua.indexOf("windows nt 6.3") != -1) os= "Windows 8.1";
            else if (ua.indexOf("windows nt 10.0") != -1) os= "Windows 10";
            else if (ua.indexOf("iphone") != -1) os= "iPhone";
            else if (ua.indexOf("ipad") != -1) os= "iPad";
            else if (ua.indexOf("linux") != -1) {
                var index = ua.indexOf("android");
                if (index != -1) {
                    //os以及版本
                    var os = ua.slice(index, index+13);

                    //手机型号
                    var index2 = ua.indexOf("build");
                    var type = ua.slice(index1+1, index2);
                    os= type + os;
                } else {
                    os= "Linux";
                }
            }
            else os= "未知操作系统";

            var browser;
            var s;
            var Sys = {};
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                            (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                                    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
            if (Sys.ie) browser = 'IE';
            if (Sys.firefox) browser = 'Firefox';
            if (Sys.chrome) browser = 'Chrome';
            if (Sys.opera) browser = 'Opera';
            if (Sys.safari) browser = 'Safari';

            var ob = os+'设备上的'+ browser;
            return ob;
        }
    </script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/jslib/html5.js"></script>
    <![endif]-->
</head>
<body onkeydown="keyLogin(event)">
<!--[if lt IE 9]>
<div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 75px; position: relative;'>
    <div style='position: absolute; right: 3px; top: 3px; font-family: courier new; font-weight: bold;'><a href='#' onclick='javascript:this.parentNode.parentNode.style.display="none"; return false;'><img src='images/ie6nomore-cornerx.jpg' style='border: none;' alt='Close this notice'/></a></div>
    <div style='width: 640px; margin: 0 auto; text-align: left; padding: 0; overflow: hidden; color: black;'>
        <div style='width: 75px; float: left;'><img src='images/ie6nomore-warning.jpg' alt='Warning!'/></div>
        <div style='width: 275px; float: left; font-family: Arial, sans-serif;'>
            <div style='font-size: 14px; font-weight: bold; margin-top: 12px;'>您正在使用已经过时的浏览器！</div>
            <div style='font-size: 12px; margin-top: 6px; line-height: 12px;'>由于IE的安全问题以及对互联网标准的支持问题，建议您升级您的浏览器，以达到更好的浏览效果！</div>
        </div>
        <div style='width: 75px; float: left;'><a href='https://www.mozilla.org/zh-CN/firefox/new/' target='_blank'><img src='images/ie6nomore-firefox.png' style='border: none;' alt='下载Firefox'/></a></div>
        <div style='width: 75px; float: left;'><a href='http://windows.microsoft.com/zh-cn/internet-explorer/download-ie' target='_blank'><img src='images/ie6nomore-ie.png' style='border: none;' alt='下载 Internet Explorer 11'/></a></div>
        <div style='width: 73px; float: left;'><a href='http://se.360.cn/' target='_blank'><img src='images/ie6nomore-360.png' style='border: none;' alt='下载360浏览器'/></a></div>
        <div style='float: left;'><a href='http://www.google.com/chrome' target='_blank'><img src='images/ie6nomore-chrome.png' style='border: none;' alt='下载Google Chrome'/></a></div>
    </div>
</div>
<![endif]-->
<div class="main">
    <div class="logo" style="background-color: #3C8DE1">
        <img src="images/logo.png" width="332" alt="">
    </div>
    <div id="divLogin1" class="box login">
        <div style="height: 10%; width: 50%; text-align: center; position: absolute; left: 0; padding: 0; margin:0">
            <div style="height: 70%; width: 80%;top:30%;left:10%; text-align: center;position: absolute;color:#000000;font-size: 15px;">
                用户名登陆
            </div>
        </div>
        <div style="height: 10%; width: 50%; text-align: center;background-color:#F2F2F2; position: absolute; right:0;padding: 0; margin:0; cursor:pointer" onclick="onTab2Click();">
            <div style="height: 70%; width: 80%; color: #666666;top:30%;left:10%; text-align: center;position: absolute;color:#000000;font-size: 15px;">
                手机登录
            </div>
        </div>
        <form id="loginForm" style="height:90%; width:100%; position: absolute; top:10%">
            <fieldset class="boxBody" style="border:0;">
                <label  style="width: 25%;height:9%; font-size: 14px;position: absolute;top:4%">
                    用户名
                </label>
                <input type="text" id="userAccount" tabindex="1" placeholder="请输入用户名" required  style="width: 80%;height:5%;position: absolute;top:12%">
                <label style="width: 25%;height:9%; font-size: 14px;position: absolute;top:26%">
                    密码
                </label>
                <input type="password" id="userPassword" tabindex="2" required placeholder="请输入密码" style="width: 80%;height:5%;position: absolute;top:34%">
                <label style="width: 25%;height:9%; font-size: 14px;position: absolute;top:48%">
                    验证码
                </label>
                <input type="text" id="validCode" tabindex="3" class="vcodeinput" required placeholder="请输入验证码"  style="width: 80%;height:5%;position: absolute;top:56%"/>
                <div style="width: 30%;height:9%;position: absolute;top:60%;right:15%">
                    <img title="点击刷新验证码"  id="kaptchaImage" onclick="loadImage()" width="96px" height="24px" />
                </div>
                <div id="note" style="height: 12%;width:80%; left:10%;color: red; font-size: 13px; text-align: right;position: absolute;padding: 0; top: 73%;margin:0;"></div>
            </fieldset>
            <footer style="margin: 0; padding: 0; height: 19%; width:100%;position: absolute;bottom:0" >
                <input type="button" class="btnLogin" id="login1" onclick="login()" value="登 录" tabindex="4" style="position: absolute; width:40%; height:70%; left:30%; top:15%"/>
                <%--<a href="javascript:void(0)" onclick="onBindClick()" style="position: absolute;right:4%; bottom:15%" >首次登陆</a>--%>
               <%-- <a href="javascript:void(0)" onclick="onShitClick()" style="position: absolute;right:4%; bottom:15%" >获取验证码页面</a>--%>
            </footer>
        </form>
    </div>
    <div id="divLogin2" class="box login" style="display:none;">
        <div style="height: 10%; width: 50%; text-align: center;background-color:#F2F2F2; position: absolute; left: 0; padding: 0; margin:0;cursor:pointer" onclick="onTab1Click();">
            <div style="height: 70%; width: 80%;top:30%;left:10%; text-align: center;position: absolute;color:#000000;font-size: 15px;">
                用户名登陆
            </div>
        </div>
        <div style="height: 10%; width: 50%; text-align: center; position: absolute; right:0;padding: 0; margin:0">
            <div style="height: 70%; width: 80%; color: #666666;top:30%;left:10%; text-align: center;position: absolute;color:#000000;font-size: 15px;">
                手机登录
            </div>
        </div>
        <form id="loginForm2" style="height:90%; width:100%; position: absolute; top:10%">
            <fieldset class="boxBody" style="border:0">
                <label>
                    手机号码
                </label>
                <input type="text" id="telephone" tabindex="1" placeholder="请输入手机号码" required>
                <input type="button" id="sendsmsbtn1" style="font-size: 10px;top:40%;right:8%;position: absolute; border: 0;background: #F2F2F2;cursor: hand;height:10%; width:28%;" value="获取验证码" onclick="getSmsCode()"/>
                <label>
                    短信验证码
                </label>
                <input type="text" id="smscode" tabindex="2" style="width:120px;" required placeholder="请输入短信验证码">
                <div id="note2" style="height: 12%;width:90%; left:5%;color: red; font-size: 13px; text-align: right;position: absolute;padding: 0; top: 68%;margin:0;"></div>
            </fieldset>
            <footer style="margin: 0; padding: 0; height: 19%; width:100%;position: absolute; bottom:0" >
                <input type="button" class="btnLogin" id="login2" onclick="loginByTelephone();" value="登 录" tabindex="3" style="position: absolute; width:40%; height:70%; left:30%; top:15%"/>
            </footer>
        </form>
    </div>
    <div id="divNumBind" class="box login" style="display: none;">
        <div style="height: 10%; width: 60%; text-align: center; font-size: 16px; position: absolute; left: 20%; top: 3%; padding: 0; margin:0">
            绑定手机号
        </div>
        <form id="numBind" style="height:90%; width:100%; position: absolute; top:10%">
            <fieldset class="boxBody" style="border:0">

                <label style="width: 25%;height:9%; font-size: 14px;position: absolute; top:0;">用户姓名：</label>
                <input type="text" id="userName" tabindex="1" placeholder="请输入姓名" required style="width: 55%;height:4% ;left:30%;top:0;position: absolute" />

                <input type="text" id="userId" style="width: 5%;height:4% ;right:0;top:-11%;position: absolute; display: none" />

                <label style="width: 25%;height:9%; font-size: 14px;position: absolute;top:14%">修改密码：</label>
                <input type="password" id="userCPass" tabindex="2" placeholder="请输入新密码" required style="width: 55%;height:4% ;left:30%;top:14%;position: absolute">

                <label style="width: 25%;height:9%; font-size: 14px;position: absolute;top:28%">确认密码：</label>
                <input type="password" id="userCPass2" tabindex="3" placeholder="请再次输入新密码" required style="width: 55%;height:4% ;left:30%;top:28%;position: absolute">

                <label style="width: 25%;height:17%; font-size: 14px;position: absolute;top:42%;">手机号码：</label>
                <input type="text" id="userCell" tabindex="4" placeholder="请输入手机号码" required style="width: 55%;height:4% ;left:30%;top:42%;position: absolute">
                <input type="button" id="sendsmsbtn2" style="font-size: 10px;position:absolute;top:58%;right: 8%;border: 0;background: #F2F2F2;cursor: hand;height:10%; width:28%;" onclick="getSmsBindCode();" value="获取验证码" />
                <label style="width: 25%;height:9%; font-size: 14px;position: absolute;top:56%">验证码：</label>
                <input type="text" id="smscode2" tabindex="5" placeholder="请输入验证码" required style="width: 25%;height:4% ;left:30%;top:56%; position: absolute">
				<div id="note3" style="height: 12%;width:85%; left:5%;color: red; font-size: 13px; text-align: right; position: absolute;padding: 0; top: 73%;margin:0;"></div>
			</fieldset>
            <footer style="margin: 0; padding: 0; height: 19%; width:100%;position: absolute; bottom:0" >
                <input type="button" class="btnLogin" id="bindConfirm" onclick="numBindAccount();" value="确 认" tabindex="6" style="position: absolute; width:40%; height:70%; left:30%; top:15%"/>
                <%--<a href="javascript:void(0)" onclick="onBackClick()" style="position: absolute;right:4%; bottom:15%" >返回登陆页</a>--%>
            </footer>
        </form>
    </div>
    <div id="divLoginShit" class="box login" style="display:none">
        <form id="loginFormShit" style="height:100%; width:100%; position: absolute;">
            <fieldset class="boxBody" style="border:0">
                <label>
                    短信验证码
                </label>
                <input type="text" id="smscodeShit" tabindex="1" style="width: 80%;height:4% ;left:6%;top:16%; position: absolute;" required placeholder="请输入短信验证码">
                <input type="button" id="sendsmsbtnShit" style="font-size: 10px;position:absolute;top:33%;right: 9%;border: 0;background: #F2F2F2;cursor: hand;height:10%; width:35%;" onclick="sendSmsCode()" value="重新获取验证码"/>
                <div id="note4" style="height: 12%;width:88%; left:6%;color: red; font-size: 13px; text-align: right; position: absolute;padding: 0; top: 70%;margin:0"></div>
            </fieldset>
            <footer style="margin: 0; padding: 0; height: 17%; width:100%;position: absolute; bottom:0" >
                <input type="button" class="btnLogin" id="loginShit" onclick="loginGo();" value="继续登录" tabindex="2" style="position: absolute; width:40%; height:70%; left:30%; top:15%"/>
                <%--<a href="javascript:void(0)" onclick="onShitBack()" style="position: absolute;right:4%; bottom:15%" >返回登陆页</a>--%>
            </footer>
        </form>
    </div>
</div>
</body>
</html>