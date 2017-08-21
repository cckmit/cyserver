<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="wechat/static/layer/need/layer.css" rel="stylesheet" type="text/css">

    <script src="js/zepto.min.js"></script>
    <script src="js/xdomain.min.js"></script>
    <script src="js/H.js"></script>
    <script src="js/B.js"></script>
    <script src="wechat/static/layer/layer.js"></script>

    <script>
        $(function(){
            var code = B.getUrlParamByName("code");
            var state = B.getUrlParamByName("state");

            if (!code || code == "null"){
                layer.open({ content: "未通过微信授权", skin: "msg", time: 2});
                return;
            }

            // 通过code获取openId、appId、accountNum
            var params = {"command":"604","content":{}};
            params.content.code = code;
            params.content.type = "20";

            $.ajax({
                type: "post",
                url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
                data:{
                    jsonStr: JSON.stringify(params)
                },
                dataType: "json",
                success: function(data){
                    if (data && data.success) {
                        if (data.obj){
                            // 将openId、appId、accountNum缓存到localStorage中
                            var openId = data.obj.openId;
                            var appId = data.obj.appId;
                            var accountNum = data.obj.accountNum;

                            if (openId && openId != "null") {
                                localStorage.setItem("openId", openId);
                            }
                            if (appId && appId != "null") {
                                localStorage.setItem("appId", appId);
                            }
                            if (accountNum && accountNum != "null") {
                                localStorage.setItem("accountNum", accountNum);
                            }

                            // 跳转至业务链接页面
                            if (state && state != "null"){
                                window.location.href = state;
                            }
                        }
                        else{
                            layer.open({content:"请求未返回数据", skin:"msg", time:2});
                        }
                    }
                    else{
                        layer.open({content:data.msg, skin:"msg", time:2});
                    }
                }
            });
        });
    </script>
</head>
<body>
<div style="margin: 0;padding:0;width:100%;height:100%;overflow: hidden;text-align: center;">
    <div style="text-align: center;width: 200px;margin:0 auto;padding: 20px 0;font-size: 14px;">数据加载中</div>
</div>
</body>
</html>
