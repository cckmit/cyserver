<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>窗友校友智能管理与社交服务平台</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">

    <meta http-equiv="X-UA-Compatible" content="IE=9,IE=10,IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/supersized.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">--%>

    <jsp:include page="../../inc.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/jslib/supersized.3.2.7.min.js"></script>
    <script src="${pageContext.request.contextPath}/jslib/supersized-init.js"></script>
    <script src="${pageContext.request.contextPath}/jslib/login.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/jslib/html5.js"></script>
    <![endif]-->

    <script type="text/javascript">
        function keyLogin(e) {
            var key = e.which || event.keyCode;
            if (key == 13) //回车键的键值为13
                $("#login1").click(); //调用登录按钮的登录事件
        }

        $(function() {
            // 用户名登录 - 初始化验证码
            $('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
        });

        /**
         * 用户名登录 - 更换验证码
         */
        function loadImage() {
            $('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
        }

        /**
         * 用户名登录 - 登录提交
         */
        function login() {
            var userAccount = $('#userAccount').val().trim();
            var userPassword = $('#userPassword').val().trim();
            var validCode = $('#validCode').val().trim();

            if (userAccount == '') {
                $('#note').text("请输入用户名");
                $('#userAccount').focus();
                return;
            }
            if (userPassword == '') {
                $('#note').text("请输入密码");
                $('#userPassword').focus();
                return;
            }
            /*if (validCode == '') {
                $('#note').text("请输入验证码");
                $('#validCode').focus();
                return;
            }*/

            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_login.action?agent='+getAgent(),
                data : {
                    'userAccount' : userAccount,
                    'userPassword' : userPassword,
                    'validCode' : validCode
                },
                dataType : 'json',
                success : function(result) {

                    if (result.success && result.returnId=="21") {
                        $('#note').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    }
                    else if (result.success && result.returnId=="200") {
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
                    else if (result.success && result.returnId=="500") {
                        $('#note4').text(result.msg);
                        $('#divLogin1').hide();
                        $('#divLoginShit').show();
                    }
                    else if (!result.success && result.returnId=="201") {
                        $('#note3').text(result.msg);
                        $('#note').text('');
                        $('#userPassword').val('');
                        $('#validCode').val('');
                        $('#userId').val(result.obj);

                        // 隐藏登录面板，显示绑定手机号面板
                        $('#divLogin1').hide();
                        $('#divNumBind').show();
                    }
                    else {
                        $('#login1').prop('disabled', false);
                        $('#validCode').val('');
                        $('#note').text(result.msg);
                        if(result.obj == true || result.obj == 'true') {
                            $("#validCodeDiv").show() ;
                        }
                        $('#kaptchaImage').attr('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action');
                    }
                },
                beforeSend : function() {
                    $('#note').text('登陆中....');
                    $('#login1').prop('disabled', 'disabled');
                }
            });
        }

        /**
         * 手机号登陆 - 登录提交
         */
        function loginByTelephone(){
            var telephone = $('#telephone').val().trim();
            var smscode = $('#smscode').val().trim();

            if (telephone == '') {
                $('#note2').text("请输入手机号");
                $('#smscode').focus();
                return;
            }
            if (smscode == '') {
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
                success : function(result) {
                    if (result.length>0) {
                        $("#divLogin1").hide();
                        $("#hide_login").append('<div id="jsadd_divLogin" class="login-form">'
                            + '<div class="login-tap">'
                            + '<span style="width: 100%;">'
                            + '<i class="login-tap-left">请选择要登录的账号</i>'
                            + '</span>'
                            + '<div class="login-input-top"></div>'
                            + '</div>'
                            + '<form id="jsadd_loginForm">'
                            + '<div class="js-login-input">'
                            + '<div id="jsadd_input" style="text-align:left;">'
                            + '</div>'
                            + '<div style="height: 12px">'
                            + '<span id="jsadd_note" style="color: red; font-size: 12px; text-align: center;padding: 5px; margin:0;"></span>'
                            + '<input type="button" class="login-btn" onclick="ChoiceUser()" value="登&nbsp;&nbsp;录">'
                            + '</div>'
                            + '</div>'
                            + '</form>'
                            + '</div>');

                        for(var i=0;i<result.length;i++){
                            $("#jsadd_input").append('<label style="width: 100%;margin-left: 50px;"><input type="radio" style="margin-right: 5px;" name="radio_user" ' +
                                'value='+result[i].userId+'/>' +
                                ''+result[i].userName+'' +
                                '-' +
                                ''+result[i].userAccount+'' +
                                '-'+result[i].deptShit+'</label>')
                        }
                    }
                    if (result.success && result.returnId=="21") {
                        $('#note2').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    }
                    else if (result.success && result.returnId=="200") {
                        $('#note2').text(result.msg);
                    }
                    else if (result.success && result.returnId=="500") {
                        $('#note2').text(result.msg);
                    }
                    else if (!result.success && result.returnId=="201") {
                        $('#note2').text(result.msg);

                        // 隐藏登录面板，显示绑定手机号面板
                        $('#divLogin1').hide();
                        $('#divNumBind').show();
                    }
                    else {
                        $('#login2').prop('disabled', false);
                        $('#smscode').val('');
                        $('#note2').text(result.msg);
                    }
                },
                beforeSend : function() {
                    $('#note2').text('登陆中....');
                    $('#login2').prop('disabled', 'disabled');
                }
            });
        }

        /*选择账号登录*/
        function ChoiceUser(){
            var radiouser=$('input:radio[name="radio_user"]:checked').val();
            if(radiouser!=null && radiouser!=""){
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_loginByUserId.action?date=' + lastLoginTime(),
                data : {
                    'userId' : radiouser
                },
                dataType : 'json',
                success : function(result) {
                    if (result.success && result.returnId=="21") {
                        $('#note2').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    }
                    else if (result.success && result.returnId=="200") {
                        $('#note2').text(result.msg);
                    }
                    else if (result.success && result.returnId=="500") {
                        $('#note2').text(result.msg);
                    }
                    else if (!result.success && result.returnId=="201") {
                        $('#note2').text(result.msg);

                        // 隐藏登录面板，显示绑定手机号面板
                        $('#divLogin1').hide();
                        $('#divNumBind').show();
                    }
                    else {
                        $('#login2').prop('disabled', false);
                        $('#smscode').val('');
                        $('#note2').text(result.msg);
                    }
                },
                beforeSend : function() {
                    $('#note2').text('登陆中....');
                    $('#login2').prop('disabled', 'disabled');
                }
            });
            }else{
                $('#jsadd_note').text('请选择账号');
            }
        }

        /**
         * 用户首次登录 - 绑定手机号，设置用户名和密码
         */
        function numBindAccount(){
            var userId = $('#userId').val().trim();
            var userName = $('#userName').val().trim();
            var userPassword = $('#userCPass').val().trim();
            var userPasswdConfirm = $('#userCPass2').val().trim();
            var userTelephone = $('#userCell').val().trim();
            var smscode = $('#smscode2').val().trim();

            if (userName == '') {
                $('#note3').text("请输入姓名 ");
                $('#userName').focus();
                return;
            }
            if (userPassword == '') {
                $('#note3').text("请输入密码");
                $('#userCPass').focus();
                return;
            }
            if (userPasswdConfirm == '') {
                $('#note3').text("请输入确认密码");
                $('#userCPass2').focus();
                return;
            }
            if ( userPassword!= userPasswdConfirm ) {
                $('#note3').text("请确认两次输入的密码相同");
                $('#userCPass2').focus();
                return;
            }
            if (userTelephone == '') {
                $('#note3').text("请输入手机号");
                $('#userCell').focus();
                return;
            }
            if (smscode == '') {
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
                success : function(result) {
                    if (result.success) {
                        $('#note1').text(result.msg);
                        $('#userPassword').val('');
                        $('#validCode').val('');

                        // 隐藏绑定面板，显示用户名登录面板
                        $('#divNumBind').hide();
                        $('#divLogin1').show();
                        $(".login-tap span").each(function(index, el){
                            if(index == 0){
                                $(this).removeClass("login-tap-no-current");
//                                $('#'+this.id+'-input').removeClass("hidden");
                            }
                            else{
                                $('.login-tap span:not(#'+this.id+')').addClass("login-tap-no-current");
//                                $('.login-input:not(#'+this.id+'-input)').addClass("hidden");
                            }
                        });
                        $('#login1').removeProp('disabled');
                    }
                    else {
                        $('#smscode2').val('');
                        $('#note3').text(result.msg);
                    }
                    $('#bindConfirm').removeProp('disabled');
                },
                beforeSend : function() {
                    $('#note3').text('保存中....');
                    $('#bindConfirm').prop('disabled', 'disabled');
                }
            });
        }

        /**
         * 用户首次登录，绑定手机号后 - 继续登录
         */
        function loginGo(){
            $('#note4').text("");
            var userAccount = $('#userAccount').val().trim();
            var userPassword = $('#userPassword').val().trim();
            var smscode = $('#smscodeShit').val().trim();

            if (smscode == '') {
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
                success : function(result) {
                    if (result.success) {
                        $('#note4').text("登录成功页面跳转中....");
                        location.href = "${pageContext.request.contextPath}/page/admin/main.jsp";
                    } else{
                        $('#note4').text(result.msg);
                        $('#loginShit').prop('disabled', false);
                        $('#sendsmsbtnShit').prop('disabled',false);
                    }
                },
                beforeSend : function() {
                    $('#note4').text('登陆中....');
                    $('#loginShit').prop('disabled', 'disabled');
                    $('#sendsmsbtnShit').prop('disabled', 'disabled');

                }
            });
        }

        /**
         * 用户手机号登录 - 获取验证码
         */
        function getSmsCode(){
            $('#note2').text("");
            var telephone = $('#telephone').val().trim();
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_getSmscode.action',
                data : {'userTelephone' : telephone},
                dataType : 'json',
                success : function(result) {

                    $('#note2').text(result.msg);
                    $('#login2').prop('disabled', false);

                    if (result.success) {
                        var aaa=60;
                        var ttt=setInterval(function(){
                            aaa -= 1;
                            $('#sendsmsbtn1').val(+aaa+'s后重新获取');
                            if(aaa<=0) {
                                clearInterval(ttt);
                                $('#sendsmsbtn1').val('重新获取验证码');
                                $('#sendsmsbtn1').prop('disabled', false);
                                $('#sendsmsbtn1').css({'background-color':'#96B9CC'});
                            }
                        },1000);
                    }else{
                        $('#sendsmsbtn1').prop('disabled', false);
                        $('#sendsmsbtn1').css({'background-color':'#96B9CC'});
                    }
                },
                beforeSend : function() {
                    $('#note2').text('正在发送中....');
                    $('#login2').prop('disabled', 'disabled');

                    $('#sendsmsbtn1').prop('disabled', 'disabled');
                    $('#sendsmsbtn1').css({'background-color':'#BCBCBC'});
                }
            });
        }

        /**
         * 用户首次登录 - 获取验证码
         */
        function getSmsBindCode(){
            $('#note3').text("");
            var userTelephone = $('#userCell').val().trim();
            $.ajax({
                method : 'POST',
                url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_getSmscode.action',
                data : {'userTelephone' : userTelephone},
                dataType : 'json',
                success : function(result) {

                    $('#note3').text(result.msg);
                    $('#bindConfirm').prop('disabled', false);

                    if (result.success) {

                        var aaa=60;
                        var ttt=setInterval(function(){
                            aaa -= 1;
                            $('#sendsmsbtn2').val(+aaa+'s后重新获取');
                            if(aaa<=0) {
                                clearInterval(ttt);
                                $('#sendsmsbtn2').val('重新获取验证码');
                                $('#sendsmsbtn2').prop('disabled', false);
                                $('#sendsmsbtn2').css({'background-color':'#96B9CC'});
                            }
                        },1000);
                    }else{
                        $('#sendsmsbtn2').prop('disabled', false);
                        $('#sendsmsbtn2').css({'background-color':'#96B9CC'});
                    }
                },
                beforeSend : function() {
                    $('#note3').text('正在发送中....');
                    $('#bindConfirm').prop('disabled', 'disabled');

                    $('#sendsmsbtn2').prop('disabled', 'disabled');
                    $('#sendsmsbtn2').css({'background-color':'#BCBCBC'});
                }
            });
        }

        /**
         * 用户名登录验证手机号安全登录 - 获取手机验证码
         */
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
                success : function(result) {
                    $('#note4').text(result.msg);
                    $('#loginShit').prop('disabled', false);

                    if (result.success) {
                        var aaa=60;
                        var ttt=setInterval(function(){
                            aaa -= 1;
                            $('#sendsmsbtnShit').val(+aaa+'s后重新获取');
                            if(aaa<=0) {
                                clearInterval(ttt);
                                $('#sendsmsbtnShit').val('重新获取验证码');
                                $('#sendsmsbtnShit').prop('disabled', false);
                                $('#sendsmsbtnShit').css({'background-color':'#96B9CC'});
                            }
                        },1000);

                    }else{
                        <%--location.href = "${pageContext.request.contextPath}/";--%>
                        $('#sendsmsbtnShit').prop('disabled', false);
                        $('#sendsmsbtnShit').css({'background-color':'#96B9CC'});
                    }
                },
                beforeSend : function() {
                    $('#note4').text('正在发送中....');
                    $('#loginShit').prop('disabled', 'disabled');

                    $('#sendsmsbtnShit').prop('disabled', 'disabled');
                    $('#sendsmsbtnShit').css({'background-color':'#BCBCBC'});
                }
            });
        }

        /**
         * 获取当前登录时间
         */
        function lastLoginTime(){
            var myDate = new Date();
            var llt = myDate.getFullYear()+'/';
            llt += myDate.getMonth()+'/';
            llt += myDate.getDate()+' ';
            llt += myDate.getHours()+':';
            llt += myDate.getMinutes();
            return llt;
        }

        /**
         * 获取当前浏览器的Agent信息
         */
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

<div class="page-container" id="hide_login">
    <div class="login-logo"></div>
    <div id="divLogin1" class="login-form">
        <div class="login-tap">
            <span id="login-user">
                <i class="login-tap-left">用户名登录</i>
            </span>
            <span id="login-mobil" class="login-tap-no-current">
                <i class="login-tap-right">手机号登录</i>
            </span>
            <div class="login-input-top"></div>
        </div>
        <form id="loginForm">
            <div class="login-input" id="login-user-input">
                <div>
                    <input type="text" id="userAccount" tabindex="1" required placeholder="用户名">
                </div>
                <div>
                    <input type="password" id="userPassword" tabindex="2" required placeholder="密码">
                </div>
                <div id="validCodeDiv" style="display: none;">
                    <input type="text" id="validCode" tabindex="3" class="vcodeinput login-input-code" required placeholder="验证码">
                    <img title="点击刷新验证码"  id="kaptchaImage" onclick="loadImage()" width="96px" height="24px"/>
                    <div style="height: 0;clear: both;margin:0;padding:0;"></div>
                </div>

                <div id="note" style="color: red; font-size: 12px; text-align: right;padding-top: 10px; margin:0;height: auto;"></div>
                <div style="padding-top: 10px;">
                    <input type="button" id="login1" class="login-btn" onclick="login()" value="登&nbsp;&nbsp;录">
                </div>
            </div>
        </form>
        <form id="loginForm2">
            <div class="login-input hidden" id="login-mobil-input">
                <div>
                    <input type="text" id="telephone" tabindex="10" required placeholder="手机号">
                </div>
                <div>
                    <input type="text" id="smscode" tabindex="12" required placeholder="短信验证码" class="login-input-code">
                    <input type="button" id="sendsmsbtn1" class="login-btn login-input-msg" onclick="getSmsCode()" value="获取验证码">

                    <div id="note2" style="color: red; font-size: 12px; text-align: center;padding: 5px; margin:0;"></div>
                </div>
                <div>
                    <input type="button" id="login2" class="login-btn" onclick="loginByTelephone()" value="登&nbsp;&nbsp;录">
                </div>
            </div>
        </form>
    </div>
    <div id="divNumBind" class="login-form" style="display:none">
        <div class="login-tap">
            <p style="height: 50px; line-height: 50px;text-align: center;font-style: normal;font-size: 18px;color: #909090;">绑定手机号</p>
        </div>
        <form id="numBind">
            <div class="login-input" id="login-user-input2">
                <div>
                    <input type="text" id="userName" tabindex="21" placeholder="用户姓名">
                    <input type="hidden" id="userId" />
                </div>
                <div>
                    <input type="password" id="userCPass" tabindex="22" placeholder="修改密码">
                </div>
                <div>
                    <input type="password" id="userCPass2" tabindex="23" placeholder="确认密码">
                </div>
                <div>
                    <input type="text" id="userCell" tabindex="24" placeholder="手机号码">
                </div>
                <div>
                    <input type="text" id="smscode2" tabindex="25" required placeholder="验证码" class="login-input-code">
                    <input type="button" id="sendsmsbtn2" class="login-btn login-input-msg" onclick="getSmsBindCode()" value="获取验证码">

                    <div id="note3" style="color: red; font-size: 12px; text-align: center;padding: 5px; margin:0;"></div>
                </div>
                <div style="marign:0;padding:0;height:10px;"></div>
                <div>
                    <input type="button" id="bindConfirm" class="login-btn" onclick="numBindAccount()" value="确&nbsp;&nbsp;认">
                </div>
            </div>
        </form>
    </div>
    <div id="divLoginShit" class="login-form" style="display:none">
        <div class="login-tap">
            <p style="height: 50px; line-height: 50px;text-align: center;font-style: normal;font-size: 18px;color: #909090;">短信验证码</p>
        </div>
        <form id="loginForm4">
            <div class="login-input">
                <div>
                    <input type="text" id="smscodeShit" tabindex="31" required placeholder="验证码" class="login-input-code">
                    <input type="button" id="sendsmsbtnShit" class="login-btn login-input-msg" onclick="sendSmsCode()" value="重新获取验证码">

                    <div id="note4" style="color: red; font-size: 12px; text-align: center;padding: 5px; margin:0;"></div>
                </div>
                <div>
                    <input type="button" id="loginShit" class="login-btn" onclick="loginGo()" value="继续登录">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="page-footer">
    Copyright © 2014 - 2017 <%=Global.schoolSign%>版权所有. All Rights Reserved<br>武汉窗友科技有限公司提供技术支持 网址：www.cymobi.com 咨询电话：400-027-1816
</div>

</body>

</html>