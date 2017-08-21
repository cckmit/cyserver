

// var deptId=B.getUrlParamByName("alumniId");
// var deptId=11;


//加载顶部与底部信息
$.ajax({
    url: B.clientUrl+'/fenhui/fenhuiPublic/public.html',
    // url: headerUrl,
    // data:{rootUrl:rootUrl},
    type: 'get',
    dataType: 'html',
    success: function (data){
        if (data) {
            var topLinesHtml=$(data).find(".lines").html();
            var navHtml=$(data).find(".header_nav").html();
            var navContentHtml=$(data).find(".header_content").html();
            var login_tk=$(data).find(".dengluTank").html();
            var logout_tankuaung=$(data).find(".logout_tankuaung").html();
            $(".lines").html(topLinesHtml);
            $(".header_nav").html(navHtml);
            $(".header_content").html(navContentHtml);
            $(".dengluTankuang").html(login_tk);
            $(".logoutTankuaung").html(logout_tankuaung);

            // console.log(login_tk);
            var footHtml=$(data).find("#footer_line").html();
            $("#footer_line").html(footHtml);
            init(logoI,headColor,branchColor,branchColorBorder,listBg,userBg) ;
            navTestCookie();
        }
    }
});

//初始化函数
function init() {
    $(".bannerPic").attr("src",logoI);
    //各位置背景颜色
    $("#top_nav_bg").css("background",headColor);
    $(".index_login").css("background",branchColor);
    $(".login_bt").css("background",headColor);
    $(".news_box_name").css({"color":headColor,"border-bottom":branchColorBorder});
    $(".fh").css("background",headColor);
    $(".wenxinLanMu").css("background",branchColor)
    $(".right-title").css({"color":headColor,"border-bottom":"4px solid "+headColor});
    $(".shijieView").css("background",headColor);
    $(".shijieVived").css("background",branchColor);
    $(".tongzhi").css("background",listBg);
    $(".news_box").css("background",listBg);
    $("#footer_line").css("background",headColor);
    $(".shijie_title").css("background",headColor);
    $(".picMarquee-left .hd").css("background",branchColor);
    //按钮颜色
    $(".loginForm .login_btn").css("background",headColor);
    $(".loginForm a").css("border","1px solid "+headColor);
    $(".user_center_btn").css({"background":headColor,"border":"1px solid "+headColor});
    $(".user_logout_btn").css({"border":"1px solid "+headColor});

    $(".personAnNiu").css("background",headColor);
    $(".personTuiChu").css("background",headColor);
    //list---
    $(".list_name_nav").css("background",headColor);
    $("#categoryList a").css("color",headColor);
    $(".break_list").css("border-bottom","2px solid "+headColor);

    //comtent-----
    $(".news .top").css("background",branchColor);
    $(".clickBtn").css("background",headColor);
    //登录弹出框----
    $(".login_title").css("background",headColor);
    $(".denglu_jiemian p .liji_login").css("background",headColor);


    $(".bannerPic").attr("src",B.clientUrl+"image/logo_gzkjxy.png");
    //判断是否为首页
    $(".indexSrc").attr("href",B.clientUrl+"fenhui/funhui_index.html?alumniId="+deptId);
    $(".schoolZuzhi").attr("href",B.clientUrl+"alumni/xyh.html");
    $(".zonghuiXinxi").attr("href",B.clientUrl+"alumni/xyhi.html?alumniId=deptId");
    $(".zonghuiXinxi").attr("href",B.clientUrl+"alumni/xyhi.html?alumniId="+deptId);

    $(".user_center").attr("href",B.clientUrl+"users/user_personal.html");

    $(".zhuce_tk").attr("href",B.clientUrl+"users/user_register.html");

    $(".xiaoyou_zhuzu").attr("href",B.clientUrl+"alumni/xyh.html");
    $(".shoouye").attr("href",'funhui_index.html?alumniId='+deptId+'');
    $(".gengduo").attr("href",'fenhui_list.html?categoryId=65&&deptId='+deptId+'');
    $(".fenhuiXinxi").attr("href",'../alumni/xyhi.html?alumniId='+deptId+'');

    // $(".user_avatar").attr("src",B.clientUrl+"image/user-avatar.png");
    $(".gonggao_more").attr("href",B.clientUrl+"fenhui/fenhui_list.html?categoryId=37&&deptId="+deptId);

}
$(".list_name_nav1  li a:not('.side-nav-style')").hover(function(){
    $(this).css("background",headColor);
},function(){
    $(this).css("background",branchColor);
});

// 全局变量-网站栏目（包括子栏目）
var categoryTreeData = [];

$(function() {
    B.ready(function() {
        // 获取网站所有栏目（包括子栏目和不在导航上显示的校友服务）
        interface.FenhuiLoaderNav.jsonData.content.deptId = deptId.toString();
        $.ajax({
            type: 'post',
            url: B.serverUrl + interface.FenhuiLoaderNav.url,
            data: {
                jsonStr: JSON.stringify(interface.FenhuiLoaderNav.jsonData)
            },
            dataType: 'json',
            success: function(data) {
               // console.log("------" + JSON.stringify(data));

                if(data && data.obj && data.obj.length > 0) {
                    categoryTreeData = data.obj;

                        for (var x=0; x<data.obj.length; x++) {
                            var list = '<li>' +
                                '<a href="fenhui_list.html?categoryId=' + data.obj[x].id + '&&deptId=' + deptId + '">' + data.obj[x].text + '</a>' +
                                '</li>';
                            $("#nav").append(list);
                        }


                }

            },
            error: function(xhr, type) {

            }
        });
    });
    fenhuiDq();
});
//当前分会
function fenhuiDq(){
    B.ready(function() {
        $('.gonggao').empty()
        interface.FenhuiDangQian.jsonData.content.alumniId = deptId.toString();
        $.ajax({
            type: 'post',
            url: B.serverUrl + interface.FenhuiDangQian.url,
            data: {
                jsonStr: JSON.stringify(interface.FenhuiDangQian.jsonData)
            },
            dataType: 'json',
            success: function(data) {
                var fenhui_dq=data.obj.alumniName;
                $(".fenhui_dq").html(fenhui_dq)
            },
            error: function(xhr, type) {
            }
        });
    })
}


//检查用户登录信息cookie
function navTestCookie(){
    if(getCookie("phoneNum")==""||getCookie("phoneNum")==null||getCookie("phoneNum")=="undefined"){
        addCookie("phoneNum","",-1);
        addCookie("accountNum","",-1);
        addCookie("picture","",-1);
        addCookie("name","",-1);
        $(".login_tk_dlh").hide();
        $(".login_tk_dl").show();
        return 0;
    }else {
        $(".login_tk_dlh").show();
        $(".login_tk_dl").hide();
        var uname=getCookie("name");
        var upic=getCookie("picture");
        $(".dl_user_name").html(uname);
        //如果用户没有上传头像,则默认上传一张头像
        if(!upic || upic==""){
            $(".user_center img").attr("src",B.clientUrl+"image/user-avatar.png"); //顶部登录条 用户头像
        }else{
            $(".user_center img").attr("src",upic); //顶部登录条 用户头像
        }
        return 1;
    }
}

//打开登录弹框
function open_tkLogin(){
    navTestCookie();
    if(navTestCookie()==0){
        $(".login_tankuang").show()
    }

}
//关闭登录弹框
function close_tkLogin(){
    $(".login_tankuang").hide()
}

<!--退出登录+++弹出框效果-->
function logoutConfirm(){
    $('.theme-pop-mask').fadeIn(100);
    $('.theme-pop').slideDown(200);
}

function closeConfirm() {
    $('.theme-pop-mask').fadeOut(100);
    $('.theme-pop').slideUp(200);
}

//登陸
function login_tk(){

    var accountNum = '', password = '';
    accountNum = $('#UserName1').val();
    password = $('#PassWord1').val();
    if(accountNum == undefined || accountNum == '' ){
        $('#loginMsg').text('请填写手机号');
        return;
    }
    var mobileReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if(accountNum.length != 11 || !mobileReg.test(accountNum)){
        $('#loginMsg').text('请填写正确的手机号');
        return;
    }

    if(password == undefined || password == ''){
        $('#loginMsg').text('请填写密码');
        return;
    }

    B.ready(function() {
        interface.loginIn.jsonData.content.accountNum = accountNum.toString();
        interface.loginIn.jsonData.content.password = password.toString();
        $.ajax({
            type: 'post',
            url: B.serverUrl + interface.loginIn.url,
            data: {
                jsonStr: JSON.stringify(interface.loginIn.jsonData)
            },
            dataType: 'json',
            success: function(data) {
                if(data.success){
                    //$('#loginMsg').text('登陆成功');
                    //alert(data.success)
                    if(data.success==true){
                        //$('#loginMsg').text('登陆成功');
                        addCookie("password", password, 0);
                        addCookie("phoneNum",data.obj.phoneNum ,0) ;
                        addCookie("accountNum",data.obj.accountNum ,0) ;
                        addCookie("picture",data.obj.picture ,0) ;
                        addCookie("name",data.obj.name ,0) ;
                        window.location.href = B.clientUrl+'users/user_personal.html';
                    }

                }else{
                    $('#loginMsg').text(data.msg);
                }
            },
            error: function(xhr, type) {}
        });
    });
}




