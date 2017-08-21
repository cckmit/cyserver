/*!
 * 文件名称：B.js
 * 文件版本：Version 0.0.1    2016-05-12
 * 文件作者：Kent
 * 编写日期：2016年05月12日
 * 版权所有：北京博视创诚科技有限公司
 * 企业官网：http://www.51un.cn
 * 开源协议：MIT License
 * 文件描述：针对APICloud开发及H.js扩展js
 * 文档地址：
 * 开源地址：
 */

/**
 * 该文件应用需要放在xdomain.js之后。
 */
; ! function (factory) {
    if (typeof require === 'function' && typeof exports === 'object' && typeof module === 'object') {
        var target = module['exports'] || exports;
        factory(target);
    } else if (typeof define === 'function' && define['amd']) {
        define(['exports'], factory);
    } else {
    	factory(window['B'] = {
            v: "0.0.1",


			/* 本地测试 */
            clientUrl:"http://172.17.0.72:8089/alumniweb/web",
            serverUrl:"http://172.17.0.72:8089/",
            domain:"http://172.17.0.72:8089",
            proxy:"/jslib/proxy.html",
            imageServiceHttp:"http://172.17.0.72:8080/cy/file/",  // 图片所在服务器的访问地址
            weChatService:"http://172.17.0.72:8089/wechat/",      // 微信服務地址

            // clientUrl:"http://localhost:8089/cy/alumniweb/web",
            // serverUrl:"/cy",
            // domain:"http://localhost:8089",
            // proxy:"/cy/jslib/proxy.html",
            // imageServiceHttp:"http://localhost:8089/cy/cyfile",  // 图片所在服务器的访问地址

            /* 本地测试 */
           /* clientUrl:"/alumniweb/web",
            serverUrl:"http://weixin.cyapp.net/cy",
            domain:"http://weixin.cyapp.net",
            proxy:"/cy/jslib/proxy.html",
            imageServiceHttp:"http://weixin.cyapp.net/cy/cyfile/",  // 图片所在服务器的访问地址
            weChatService:"http://localhost:8181/wechat/",      // 微信服務地址*/

            
            /*  正式部署 */
            // clientUrl:"/alumniweb/web/",
            // serverUrl:"",
            // imageServiceHttp:"http://weixin.cyapp.net:8088/",  // 图片所在服务器的访问地址

            ready:function(callback){
            	var that = this;

                /* 本地测试 */
                xdomain.slaves({
                    "http://172.17.0.72:8089":that.proxy
                    // "http://weixin.cyapp.net":that.proxy
			    });

            	if(H.isFunction(callback)){
            		callback();
            	}
            },
            // 根据参数名获取url中的参数值
			getUrlParamByName:function(name){
				var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
				var r = decodeURI(window.location.search).substr(1).match(reg);
				if(r!=null){
					return  unescape(r[2]);
				}else{
					return null;
				}
			},
            /**
             * 批量将相对路径转换为绝对路径，适用于富文本内容中的图片或文件的路径转换
			 * if(data && data.obj && data.obj.description) {
			 *      data.obj.description = B.changeSrcFromRelativeToAbsolute(data.obj.description);
			 * }
             */
            changeSrcFromRelativeToAbsolute:function(content){
				if (content) {
					content = content.replace(/src=&quot;\//g, "src=&quot;" + B.imageServiceHttp);
                    content = content.replace(/src=\"\//g, "src=\"" + B.imageServiceHttp);

                    var contentHeader = "<style type='text/css'>\n" +
                        "\t.contentEditor{\n" +
                        "\t\tmargin:5px;\n" +
                        "\t\twidth :97%;\n" +
                        "\t}\n" +
                        "\t.contentEditor img{\n" +
                        "\t\tmax-width: 100%;\n" +
                        "\t\theight:auto;\n" +
                        "\t}\n" +
                        "\t.contentEditor p{\n" +
                        "\t\tmargin-right: 0pt; margin-left: 0pt; text-indent: 0pt; line-height: 150%;\n" +
                        "\t}\n" +
                        "</style>\n" +
                        "<div class='contentEditor'>" ;

                    content = contentHeader + content + "</div>";
				}
                return content ;
			},
            // 将相对路径转化为绝对路径，适用于单个图片或文件的路径转换
            getImageAbsoluteUrl:function(relativeUrl){
                if (B.isWebsiteUrl(relativeUrl)) {
                    // 如果是完整网路地址，直接返回
                    return relativeUrl;
                }
                else {
                    // 如果是相对地址，先转换为绝对路径，再返回
                    return B.imageServiceHttp + relativeUrl;
                }
            },
            // 判断网站地址是否是完整网路地址
            isWebsiteUrl:function (siteUrl){
                var strRegex = "^((https|http)?://)";
                var re = new RegExp(strRegex);
                if (re.test(siteUrl)){
                    return true;
                }else{
                    return false;
                }
            },
            // 判断图片是否存在
            checkImgExist:function (imgUrl) {
                var ImgObj = new Image();
                ImgObj.src = imgUrl;
                //没有图片，则返回-1
                if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {
                    return true;
                } else {
                    return false;
                }
            },
			// 获取指定位数的随机正整数
			getRandomInt:function (num) {
            	if (isNaN(num) || num == 0){
            		num = 4;
				}
				return Math.floor(Math.random()*Math.pow(10,num))
            },
            // 文本转码，由于jmjm方法对于？及参数的处理有问题，暂时不作任何处理返回
            encrypt:function (plainText) {
				return plainText;
            },
            // 文本解码，由于jmjm方法对于？及参数的处理有问题，暂时不作任何处理返回
            decrypt:function (encryptText) {
				return encryptText;
            },
            // 通过位移运算方法和密钥m方式，对文本text进行转码或解码
            jmjm:function (m,text) {
                var last = "";
                for(var i = 0; i < text.length; i++){
                    for(var j = 0; j < m.length; j++){
                        var key = m.charCodeAt(j);
                        var text2 = text.charCodeAt(i) ^ key;
                    }
                    last += String.fromCharCode(text2);
                }
                return last;
            },
            // 关闭页面窗口
            closeWindow:function () {
                var ua = navigator.userAgent.toLowerCase();
                if(ua.match(/MicroMessenger/i)=="micromessenger") {
                    WeixinJSBridge.call('closeWindow');
                } else if(ua.indexOf("alipay")!=-1){
                    AlipayJSBridge.call('closeWebview');
                }else if(ua.indexOf("baidu")!=-1){
                    BLightApp.closeWindow();
                }
                else{
                    window.close();
                }
            }
        });

        /**
         * 微信端获取URL中的参数openId、appId和accountNum
         */
        var openId = B.getUrlParamByName("openId");
        var appId = B.getUrlParamByName("appId");
        var accountNum = B.getUrlParamByName("accountNum");

        if (openId && openId != "null") {
            localStorage.setItem("openId", openId);
        }
        if (appId && appId != "null") {
            localStorage.setItem("appId", appId);
        }
        if (accountNum && accountNum != "null") {
            localStorage.setItem("accountNum", accountNum);
        }
    }
}(function (BExports) {
    var B = typeof BExports !== 'undefined' ? BExports : {};
});