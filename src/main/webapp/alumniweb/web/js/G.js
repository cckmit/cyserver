/**
 * Created by mcz on 2016/11/17.
 */
//public。js之后
//logo图片
var logoI=B.clientUrl+"image/top_logo.png";
//主色调（导航与底部的背景颜色）
var headColor="#165AA1";
// var headColor="red";
//次色调（登陆窗口的背静颜色）
var branchColor="#DAE7F2";
// var branchColor="yellow";
//导航鼠标悬停颜色（导航）
var mouseColor="#3B74C4";
// var mouseColor="#000000";
//列表下边框
var branchColorBorder="4px solid #165AA1";
// var branchColorBorder="4px solid  red";
//index列表背景色
var listBg="#ffffff";
// var listBg="#dddddd";
//个人中心的背景色
// var userBg="#DAE7F2";
var userBg="#fbfbfb";





var titleText= "天津职业大学校友会";  // title的内容
var footerText="天津职业大学 版权所有"          //底部的文字
var bodyBackground= "#ffffff"      //body的背景颜色



var loginoutbg="#ffffff"    //退出按钮背景颜色
var loginoutft="#165AA1"    //退出按钮文字颜色
var juanzeng="#DAE7F2"   //我的捐赠

var indexStyle= { } ;
// 1 首页样式
// 1.1 顶部
indexStyle.header={
    bgColor : "#165AA1",   //背景颜色
    nameColor: "#ffff00",   //欢迎的名字颜色
    fontColor: "#ffffff",   //最顶部的字体颜色
    fontColorH: "#cccccc",   //最顶部的字体鼠标悬浮颜色
    navFontColor: "#ffffff",   //导航字体颜色
    navFontColorH: "#ffffff",   //导航鼠标悬浮字体颜色
    loginFontColor: "#ffffff",   //登陆框文字提示颜色
    loginBtnBackfround: "#165AA1",  //登陆按钮背景颜色
    loginBtnColor: "#ffffff",  //登陆按钮背文字颜色
    loginBtnBackfroundH: "#3B74C4",  //登陆按钮背景颜色(悬浮)
    loginBtnColorH: "#ffffff",  //登陆按钮背文字颜色(悬浮)
    registerBtnBackfround: "#ffffff",  //注册按钮背景颜色
    registerBtnColor: "#165AA1",  //注册按钮背文字颜色
    registerBtnBackfroundH: "#3B74C4",  //注册按钮背景颜色(悬浮)
    registerBtnColorH: "#ffffff",  //注册按钮背文字颜色(悬浮)
}  ;
indexStyle.imgLunbo={
    tpHeight : "300px"    //轮播图的高度
}
indexStyle.listLoad={
    fontColor : "#000000",    //首页列表文字颜色
}
indexStyle.erWeiMa={
    leftContent : "校友APP",    //二维码文字{左侧}
    rightContent : "微信公众号",    //二维码文字{右侧}
    erweimaLeftTp : B.clientUrl+"image/cy_qr.png",    //二维码图片{左侧}
    erweimaRightTp : B.clientUrl+"image/qrcode_tjzy.png"    //二维码图片{右侧}
}
indexStyle.contactUs={
    orDisplay1 : "grid",    //联系方式的显示和隐藏-------grid:格子用来表示显示， none表示隐藏
    contactUsText1 : "022-6058 5051",  //电话--
    contactUsTp1 : B.clientUrl+"image/icon_phone.png",   //小图片

    orDisplay2 : "grid",    //联系方式的显示和隐藏
    contactUsText2 : "022-60585050",  //传真--
    contactUsTp2 : B.clientUrl+"image/icon_fax.png",   //小图片

    orDisplay3 : "grid",    //联系方式的显示和隐藏
    contactUsText3 : "tjzydxxyh@126.com",  //邮箱--
    contactUsTp3 : B.clientUrl+"image/icon_email.png",   //小图片

    orDisplay4 : "grid",    //联系方式的显示和隐藏
    contactUsText4 : "天津市北辰区洛河道2号",  //地址--
    contactUsTp4 : B.clientUrl+"image/icon_location.png",   //小图片

    orDisplay5 : "grid",    //联系方式的显示和隐藏
    contactUsText5 : "300410",  //邮编--
    contactUsTp5 : B.clientUrl+"image/icon_postcode.png",   //小图片
}


//列表页面

var listStyle= { };

listStyle.listContent={
    fontColor : "#165AA1",    //面包屑文字的颜色
    listTitleColor : "#165AA1",   //列表标题的字体颜色
    listContainer : "#000000", //列表内容的文字颜色
    listTimeColor : "#165AA1", //列表内容的时间显示的文字颜色 (捐赠)
    rightImg : "../image/listrighttop.jpg",     //右侧上边第一张图片
    nameStudy : "#000000",            //捐赠人名字的字体颜色
    list_body : "#000000",    //列表页面背景颜色 165AA1
    lisrLeftBgcolor : "#ffffff",    //列表页面左侧导航的背景
    lisrZhongBgcolor : "#000000",    //列表页面中间内容的背景
    lisrZhongBlockgcolor : "#ffffff",    //列表页面中间内容块的背景
    list_lines : B.clientUrl+"image/listLine.jpg"               //列表页面中间内容块的线条
}

//详情页面

var contentStyle={ };

contentStyle.contentXiangqing={
    fontColor : "#165AA1",      //详情页面包屑文字颜色
    titleColor : "#000",      //详情页标题的文字颜色
    xinxiColor : "#165AA1",      //查看信息文字颜色
    background : "#ffffff",       //中间大盒子内的边框
    backgroundd : "#ffffff"         //中间大盒子的背景
}

var positionStyle={};
positionStyle.userStyle={
    bgColor : "#ffffff",      //个人详情页背景颜色
    bgColorcenter : "#ffffff",      //个人详情中心页背景颜色
    fenyeColor : "#000000",    //分页文字颜色
    fenyeNumColor : "#ec2727",   //分页数目文字颜色
    fenyeDisBgColor : "#000000",   //分页翻页禁止点击的颜色
    fenyeDisBgFontColor : "#ffffff",   //分页翻页禁止点击的文字颜色
    fenyeAbleBgColor : "#ffffff",   //分页翻页可以点击的颜色
    fenyeAbleBgFontColor : "#000000",   //分页翻页可以点击的文字颜色

    fenyeBorBgColor : "#cccccc",   //分页数字可以点击的背景颜色
    fenyeBorwenzColor : "#ffffff",   //分页数字可以点击的文字颜色
    fenyeBorBgFontColor : "#000000",   //分页数字边框的颜色
    fenyeBorBgFontColorH : "#dddddd",   //分页数字边框的颜色

    fenyeClickColorH : "#165AA1",   //分页数字背景颜色
    fenyeClickColorHz : "#000000",   //分页数字字体颜色
    fenyeClickColorHk : "#165AA1"   //分页数字边框颜色



}
