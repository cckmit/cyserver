
//加载顶部与底部信息
$.ajax({
    url: B.clientUrl+'public/public.html',
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
            //console.log(logout_tankuaung);
            var footHtml=$(data).find("#footer_line").html();
            $("#footer_line").html(footHtml);
            init(logoI,headColor,branchColor,branchColorBorder,listBg,userBg) ;
            classRz();
            navTestCookie();
        }
    }
});

//初始化函数
function init(logoI,headColor,branchColor,branchColorBorder,listBg,userBg) {
    //index----

       //logo图片
    $(".bannerPic").attr("src",logoI);
       //各位置背景颜色
    $("#top_nav_bg").css("background",headColor);
    $(".index_login").css("background",branchColor);
    $(".login_bt").css("background",headColor);
    $(".news_box_name").css({"color":headColor,"border-bottom":branchColorBorder});
    $(".fh").css("background",headColor);
    $(".wenxinLanMu").css("background",branchColor)
    $(".right-title").css({"color":headColor,"border-bottom":branchColorBorder});
    $(".shijieView").css("background",headColor);
    $(".shijieVived").css("background",branchColor);
    $(".news_box").css("background",listBg);
    $("#footer_line").css("background",headColor);
        //按钮颜色
    $(".user_center_btn").css({"background":headColor,"border":"1px solid "+headColor});
    $(".user_logout_btn").css({"border":"1px solid "+headColor});
    //list---
    $("#parentCategory").css("background",headColor);
    //$("#categoryList a").css("color",headColor);
    $(".position_border").css("background",headColor);
    //comtent-----
    $(".news .top").css("background",branchColor);
    $(".clickBtn").css("background",headColor);
    //登录弹出框----
    $(".login_title").css("background",headColor);
    $(".denglu_jiemian p .liji_login").css("background",headColor);
    //个人中心------
    $(".avatar-box").css("background",userBg);
    $(".side-nav").css("background",branchColor);
    $(".side-nav-style").css("background",headColor);
    $(".u-tab-content").css("background",userBg);
    $(".verifyChangeBtn").css("background",headColor);
    $(".p-head-title").css("color",headColor);
    $(".head-box .bjrz_p1").css("color",headColor);
    $(".user_bjrz_p1").css("color",headColor);
    $(".user_bjrz_nav").css("border-bottom","1px solid "+headColor);
    $(".user_bjrzi_nav_ul li").css({"background":headColor,"border":"1px solid "+headColor});
    $(".user_bjrzi_dq a").css("color",headColor);
    $("div.branch-tab-content h3").css("color",headColor);
    $(".blue_tab").css("border-bottom","1px solid "+headColor);
    $(".blue_tab li").css({"background":headColor,"border":"1px solid "+headColor});
    $(".blue_tab li.branch-tab-cur").css({"background":"#fff","border":"1px solid "+headColor});
    $(".blue_tab li a").css({"color":"#000"});
    $(".blue_tab li.branch-tab-cur a").css({"color":headColor});
    $(".blue_tab .add_branch").css({"color":headColor});
    $(".user_tjhy .user_tjhy_btn").css({"color":headColor});
    $(".inner-box .dl-list .blue-button").css({"background":headColor});
    $(".myDonateTable thead th").css({"color":headColor});
    $(".head-box .user_backrzclass").css({"background":headColor});
    $(".bjrz_p1 .zdy_class").css({"color":headColor});
   // $("#main a, a").css("color","#fff")
    $(".blue_tab li a").css("color","#fff");
    $("#tab_li a:first").css({"color":headColor});
    $(".xiaoyou-title").css({"color":headColor});
    $("#aa div:first").css({"color":headColor});
    $("#bb div:first").css({"color":headColor});
    $("#cc div:first").css({"color":headColor});
    $(".study_graden").css({"color":headColor});
    $(".branch-title").css({"color":headColor});
    $(".branch_apply_btn").css({"background":headColor});
    $(".confirmBtn").css({"background":headColor,"border":"1px solid "+headColor});
    $(".dcl_wenz").css({"color":headColor});
    $(".add_f_top span").css({"color":headColor});
    $(".shousuo_friend").css({"background":headColor});
    $(".add_f_xinxi span").css({"color":headColor});
    $(".friend_list_sqtj").css({"background":headColor});
    $(".sq_fenhuis_xzdangqian").css({"background":headColor});

    // $(".indexSrc").attr("href",B.clientUrl+"index.html");
    $(".indexSrc").attr("href",B.clientUrl+"index.html");
    $(".schoolZuzhi").attr("href",B.clientUrl+"alumni/xyh.html");
    $(".zonghuiXinxi").attr("href",B.clientUrl+"alumni/xyhi.html?alumniId=1");
    $(".user_center").attr("href" ,B.clientUrl+"users/user_personal.html");
    $(".zhuce_tk").attr("href",B.clientUrl+"users/user_register.html");

    //$(".user_avatar").attr("src",B.clientUrl+"image/user-avatar.png");
    $(".backSys").attr("href", B.serverUrl + "/index_back.jsp");

    $("#footer_line").html(footerText);
}

function classRz(){
    $(".user_bjrzi_dq").css({"background":"#ffffff","border-bottom":"1px solid #ffffff"});
}
$(".user_bjrzi_nav_ul li").click(function(){
    $(".user_bjrzi_nav_ul li a").css("color","#ffffff");
    $(this).find("a").css("color",headColor);
    $(".user_bjrzi_nav_ul li").css({"background":headColor,"border":"1px solid "+headColor});
    $(this).css({"background":"#ffffff","border-bottom":"1px solid #ffffff"});
});
$(".blue_tab li").click(function(){
    $(this).siblings().css({"background":headColor,"border":"1px solid "+headColor});
    $(this).css({"background":"#fff","border":"1px solid "+headColor});
    $(this).siblings().find("a").css({"color":"#fff"});
    $(this).find("a").css({"color":headColor});
});


$(".side-nav li a:not('.side-nav-style')").hover(function(){
        $(this).css({"background":headColor});
    },function(){
        $(this).css({"background":branchColor});
});








//初始化样式2
$(function(){
    $("title").html(titleText);
    $("body").css("background",bodyBackground);


    // alert(indexStyle.header.fontColor);
    $("#head").css("background",indexStyle.header.bgColor);
    $(".topNav a").css("color",indexStyle.header.fontColor);
    $(".login_bt").css("color",indexStyle.header.loginFontColor);
    $(".loginForm .login_btn").css({"background":indexStyle.header.loginBtnBackfround,"color":indexStyle.header.loginBtnColor});
    $(".loginForm a").css({"background":indexStyle.header.registerBtnBackfround,"color":indexStyle.header.registerBtnColor});
    $(".erweimaLeft").html(indexStyle.erWeiMa.leftContent);
    $(".erweimaCenter").html(indexStyle.erWeiMa.centerContent);
    $(".erweimaRight").html(indexStyle.erWeiMa.rightContent);
    $(".erweimaLeftTp").attr("src",indexStyle.erWeiMa.erweimaLeftTp);
    $(".erweimaCenterTp").attr("src",indexStyle.erWeiMa.erweimaCenterTp);
    $(".erweimaRightTp").attr("src",indexStyle.erWeiMa.erweimaRightTp);
    $(".user_logout_btn").css("background",loginoutbg);
    $(".user_logout_btn").css("color",loginoutft);


    $(".callMe1").css("display",indexStyle.contactUs.orDisplay1);
    $(".callMe1 td:eq(0) img").attr("src",indexStyle.contactUs.contactUsTp1);
    $(".callMe1 td:eq(1)").html(indexStyle.contactUs.contactUsText1);
    $(".callMe2").css("display",indexStyle.contactUs.orDisplay2);
    $(".callMe2 td:eq(0) img").attr("src",indexStyle.contactUs.contactUsTp2);
    $(".callMe2 td:eq(1)").html(indexStyle.contactUs.contactUsText2);
    $(".callMe3").css("display",indexStyle.contactUs.orDisplay3);
    $(".callMe3 td:eq(0) img").attr("src",indexStyle.contactUs.contactUsTp3);
    $(".callMe3 td:eq(1)").html(indexStyle.contactUs.contactUsText3);
    $(".callMe4").css("display",indexStyle.contactUs.orDisplay4);
    $(".callMe4 td:eq(0) img").attr("src",indexStyle.contactUs.contactUsTp4);
    $(".callMe4 td:eq(1)").html(indexStyle.contactUs.contactUsText4);
    $(".callMe5").css("display",indexStyle.contactUs.orDisplay5);
    $(".callMe5 td:eq(0) img").attr("src",indexStyle.contactUs.contactUsTp5);
    $(".callMe5 td:eq(1)").html(indexStyle.contactUs.contactUsText5);

    // 列表页面
    $("#position").css("color",listStyle.listContent.fontColor);
    $(".rightImg").attr("src",listStyle.listContent.rightImg);
    $(".lisrLeftBgcolor").css("background",listStyle.listContent.lisrLeftBgcolor);
    $("#newsTppl").css("background",listStyle.listContent.lisrLeftBgcolor);
    $(".right-wrap > ul").css("background",listStyle.listContent.lisrLeftBgcolor);

    $(".news_view").css("border","1px solid "+contentStyle.contentXiangqing.background);
    $("#bodyer").css("background",contentStyle.contentXiangqing.backgroundd);

    $("#body").css("background",contentStyle.contentXiangqing.backgroundd);

    $("#personal").css("background",positionStyle.userStyle.bgColor);
    $(".head-box").css("background",positionStyle.userStyle.bgColorcenter);
    $(".p-head-title").css("background",positionStyle.userStyle.bgColorcenter);
    $(".inner-box").css("background",positionStyle.userStyle.bgColorcenter);
    $(".studypaths").css("background",positionStyle.userStyle.bgColorcenter);
    $(".iner_box").css("background",positionStyle.userStyle.bgColorcenter);

    $(".li-name").css("color",headColor);
    //$(".quitBtn").css("background",loginOutBtn);loginOutBtnFont
    //$(".quitBtn").css("color",loginOutBtnFont);

})
$(".loginForm .login_btn").hover(function(){
    $(".loginForm .login_btn").css({"background":indexStyle.header.loginBtnBackfroundH,"color":indexStyle.header.loginBtnColorH});
},function(){
    $(".loginForm .login_btn").css({"background":indexStyle.header.loginBtnBackfround,"color":indexStyle.header.loginBtnColor});
});
$(".loginForm a").hover(function(){
    $(".loginForm a").css({"background":indexStyle.header.registerBtnBackfroundH,"color":indexStyle.header.registerBtnColorH});
},function(){
    $(".loginForm a").css({"background":indexStyle.header.registerBtnBackfround,"color":indexStyle.header.registerBtnColor});
});



// 全局变量-网站栏目（包括子栏目）----------------------------------------------------------
var categoryTreeData = [];
$(function(){
    //个人中心内容的最小高度
    $(".u-tab-content>div").css("min-height","500px");

    B.ready(function(){
        loaderNav(headColor,mouseColor);
    })


})

function loaderNav(headColor,mouseColor){
    $.ajax({
        type: 'post',
        url: B.serverUrl + interface.loaderNav.url,
        data: {
            jsonStr: JSON.stringify(interface.loaderNav.jsonData)
        },
        dataType: 'json',
        success: function(data) {
            //console.log("------" + JSON.stringify(data));
            if(data && data.obj && data.obj.length > 0) {
                categoryTreeData = data.obj;

                var list = '';
                var categoryIndex = 0;

                var serviceList = '';
                var serviceCategoryIndex = 0;

                for(var i=0; i<categoryTreeData.length; i++) {
                    if(categoryTreeData[i].checked){
                        // 初始化网站顶部导航
                        $("#nav").append('<li><a href="'+B.clientUrl+'news/list.html?categoryId=' + categoryTreeData[i].id + '">' + categoryTreeData[i].text + '</a></li>');
                        $("#top_nav #nav li a").css("color",indexStyle.header.navFontColor)
                    }
                }
            }
            $("#top_nav #nav li a").hover(function(){
                $(this).css({"background":mouseColor,"color":indexStyle.header.navFontColorH})
            },function(){
                $(this).css({"background":headColor,"color":indexStyle.header.navFontColor})
            });
            //頂部導航欄添加校友分會列表
            //$("#nav").append('<li><a class="schoolFriend" href="'+B.clientUrl+'alumni/xyh.html">校友组织</a></li>');
        },
        error: function(xhr, type) {

        }
    });
}

// $(function() {
//
//     //个人中心内容的最小高度
//     $(".u-tab-content>div").css("min-height","500px")
//
//     B.ready(function() {
//         // 获取网站所有栏目（包括子栏目和不在导航上显示的校友服务）
//         $.ajax({
//             type: 'post',
//             url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
//             data: {
//                 jsonStr: '{"command": "235","content": {}}'
//             },
//             dataType: 'json',
//             success: function(data) {
//                 //console.log("------" + JSON.stringify(data));
//                 alert(headColor+"234234"+branchColor)
//                 if(data && data.obj && data.obj.length > 0) {
//                     categoryTreeData = data.obj;
//
//                     var list = '';
//                     var categoryIndex = 0;
//
//                     var serviceList = '';
//                     var serviceCategoryIndex = 0;
//
//                     for(var i=0; i<categoryTreeData.length; i++) {
//                         if(categoryTreeData[i].checked){
//                             // 初始化网站顶部导航
//                             $("#nav").append('<li><a href="'+B.clientUrl+'news/list.html?categoryId=' + categoryTreeData[i].id + '">' + categoryTreeData[i].text + '</a></li>');
//
//                         }
//                     }
//                 }
//                 $("#top_nav #nav li a").hover(function(branchColor){
//                     alert(branchColor)
//                     $(this).css("background",branchColor)
//                 },function(headColor){
//                     $(this).css("background",headColor)
//                 });
//                 //頂部導航欄添加校友分會列表
//                 //$("#nav").append('<li><a class="schoolFriend" href="'+B.clientUrl+'alumni/xyh.html">校友组织</a></li>');
//             },
//             error: function(xhr, type) {
//
//             }
//         });
//
//     });
// });

//检查用户登录信息cookie
function navTestCookie(){
    if(getCookie("phoneNum")==""||getCookie("phoneNum")==null || getCookie("phoneNum")=='undefined'){
        addCookie("phoneNum","",-1);
        addCookie("accountNum","",-1);
        addCookie("picture","",-1);
        addCookie("name","",-1);
        $(".login_tk_dlh").hide();
        $(".login_tk_dl").show();

        $(".topNav").css("color",indexStyle.header.fontColor);
        $(".topNav a").css("color",indexStyle.header.fontColor);
        $(".topNav a").hover(function(){
            $(this).css("color",indexStyle.header.fontColorH)
        },function(){
            $(this).css("color",indexStyle.header.fontColor)
        });
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


        $(".topNav").css("color",indexStyle.header.fontColor);
        $(".topNav a").css("color",indexStyle.header.fontColor);
        $(".dl_user_name").css("color",indexStyle.header.nameColor);
        $(".topNav a").hover(function(){
            $(this).css("color",indexStyle.header.fontColorH)
        },function(){
            $(this).css("color",indexStyle.header.fontColor)
        });
        return 1;

    };

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
    $('.theme-pop1').slideUp(200);
    $('.theme-pop2').slideUp(200);
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
        interface.loginIn.jsonData.content.accountNum =accountNum.toString();
        interface.loginIn.jsonData.content.password =password.toString();
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




