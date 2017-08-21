<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath();
    String code_url = request.getParameter("code_url");
    String out_trade_no = request.getParameter("out_trade_no");
    String body = request.getParameter("body");
    String total_fee = request.getParameter("total_fee");
    String WIDbody = request.getParameter("WIDbody");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>微信支付</title>

    <style>
        html{color:#000;background:#fff;}
        body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,input,button,textarea,p,blockquote,th,td{margin:0;padding:0;}
        body{font:12px/1 Tahoma,Helvetica,Arial,"\5b8b\4f53",sans-serif;}
        img{border:none;}
        em,strong{font-style:normal;font-weight:normal;}
        li{list-style:none;}
        table {border-collapse:collapse;border-spacing:0;}
        h1{font-size:18px;}
        h2{font-size:16px;}
        h3{font-size:14px;}
        h4, h5, h6{font-size:100%;}
        q:before,q:after{content:'';}/* 消除q前后的内容 */
        button,input,select,textarea{font-size:100%;}/* 使得表单元素在 ie 下能继承字体大小 */
        input,button,textarea,select,optgroup,option{font-family:inherit;font-size:inherit;font-style:inherit;font-weight:inherit;}
        address,cite,dfn,em,var{font-style:normal;} /* 将斜体扶正 */
        /* link */
        a{color:#36c;text-decoration:none;}
        a:hover{color:#f60;text-decoration:underline;}


        .top{
            width:1100px;
            border:1px solid gray;
            margin: auto;
        }
        .top_log{
            font-size: 20px;
            font-weight: bold;
            padding: 20px;
        }
        .xinxi{
            background: #f2f2f2;
            border-top:1px solid #fff;
            padding: 10px 33px;
            overflow: hidden;
        }
        .left_xx{
            float: left;
        }
        .left_xx p{
            font-size: 12px;
            line-height: 20px;
        }
        .right_xx{
            float: right;
            margin-right: 100px;
            font-size: 16px;
        }
        .right_xx p{
            font-size: 12px;
            line-height: 44px;
        }
        .right_xx p span{
            font-weight: bold;
        }
        .zong_box{
            padding:20px 10px;
        }
        .center{
            border: 2px solid #ff6600;
            overflow: hidden;
            padding: 6px;
            line-height: 44px;
        }
        .left_we{
            float: left;
            font-size: 12px;
            margin: 0px 12px;
        }
        .right_we{
            float: right;
            font-size: 14px;
            margin-right: 50px;
        }
        .right_we span{
            color: red;
        }
        .left_we img{
            vertical-align: middle;
        }
        .left_we tuiJian{
            vertical-align: middle;
            width: 61px;
            height: 30px;
        }
        .tubao{
            padding: 2px 5px;
            background: #e0942f;
            border-radius: 2px;
            font-size: 12px;
            color: #fff;
        }
        .ewm{
            margin:auto 25px;
            padding: 10px;
        }
        .ewm_bor{
            width: 260px;
            height:260px;
            overflow: hidden;
            margin-top: 13px;
        }
        .ewm_bor img{
            width: 260px;
            height:260px;
            vertical-align: middle;
        }
        .hezi{
            margin-top: 20px;
            width: 260px;
            height:70px;
            overflow: hidden;
            vertical-align: middle;
        }
        .hezi img{
            width: 260px;
            height:70px;
            vertical-align: middle;
        }
    </style>

    <script language="JavaScript">
        try
        {
            window.onerror = function(){return true;}
        }
        catch(err)
        {
            window.onerror = function(){return true;}
        }
    </script>

    <script type="text/javascript" src="../../jslib/jquery-1.11.1.min.js"></script>
    <script src="../../jslib/xdomain.min.js" type="text/javascript"></script>
    <script src="../../jslib/B.js" type="text/javascript"></script>
    <script src="../../jslib/jquery.qrcode.min.js" type="text/javascript"></script>
    <%--<script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>--%>

    <!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
    <!--[if lt IE 9]>
    <script src="../../jslib/html5shiv.js"></script>
    <script src="../../jslib/respond.js"></script>
    <![endif]-->
</head>
<body>
<div class="top">
    <div class="top_log">
         收银台
    </div>
    <div class="xinxi">
        <div class="left_xx">
            <p>商品名称：<span><%=body%></span></p>
            <p>订单号：<span><%=out_trade_no%></span></p>
        </div>
        <div class="right_xx">
            <p>应付金额:  <span> ¥ <%=total_fee%></span></p>
        </div>
    </div>

    <!--    <div class="zong_box">

        </div>-->
    <div class="ewm">

        <div class="center">
            <div class="left_we">
                <img src="../images/wePayLogo.png" width="120px" height="38px" vertical-align="middle" >
                <img src="../images/wePayTuiJian.png" width="62px" height="27px"  >
                <!--<span style="font-size: 14px">微信支付</span>-->
                <!--<span class="tubao">推荐</span>-->
                <span style="margin-left: 20px;color: rgba(15, 15, 15, 0.46)">亿万用户的选择，更快更安全</span>
            </div>
            <div class="right_we">
                支付：<span><%=total_fee%></span> 元
            </div>
        </div>

        <div class="ewm_bor" id="output">
            <!--<img src="../../image/icon_add.png">-->
        </div>
        <div class="hezi">
            <img src="../images/wePayTip.png">
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var orderNo = '<%=out_trade_no%>';
    var codeUrl= '<%=code_url%>';


    if (codeUrl && codeUrl!=''&& codeUrl!='null'){
        generateQRCode("table",250, 250,codeUrl);

    }else {
        $('#output').html('<span style="text-align: center;font-size: 20px;margin: 20px;color: red">订单已支付</span>')
    }

    function generateQRCode(rendermethod, picwidth, picheight, codeUrl) {
        $("#output").qrcode({
            render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
            width: picwidth, //宽度
            height:picheight, //高度
            text: utf16to8(codeUrl), //内容
            typeNumber:-1,//计算模式
            correctLevel:2,//二维码纠错级别
            background:"#ffffff",//背景颜色
            foreground:"#000000"  //二维码颜色

        });
    }

    //中文编码格式转换
    function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }





    //定时器
//    var flag =0;
    if (orderNo && orderNo !='' && orderNo !='null'){
        var time = setInterval(function () {
//            flag++;
            $.ajax({
                type: 'POST',
                url: B.serverUrl + '/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data: {
                    jsonStr: '{"command": "229","content": {"orderNo": "' + orderNo + '"}}'
                },
                async: false,
                dataType: 'json',
                success: function (data) {
                    if(data.obj ){
//                        console.log(data.obj);
                        orderDetail = data.obj;
                        if (orderDetail.payStatus && orderDetail.payStatus =='1' ){
                            clearInterval(time);
                            location.href=B.serverUrl+'/alipay/success.jsp?certificateImageUrl='+orderDetail.certificatePicUrl;
                        }
                    }

                }
            });
           /* if (flag ==10){
                clearInterval(time)
            }*/
        },4000)

    }

</script>
</html>