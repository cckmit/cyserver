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
        /**
         * 图片所在服务器的访问地址
         */

            // 本地测试
        var imageServiceHttp = "http://localhost:8080/cy/file/";
        // 正式部署
        // var imageServiceHttp = "http://weixin.cyapp.net:8088/";

        factory(window['B'] = {
            v: "0.0.1",

            // 本地测试
            clientUrl:"http://localhost:8080/cy/alumniweb/web/",
            serverUrl:"/cy",
            domain:"http://localhost:8080",
            proxy:"/cy/jslib/proxy.html",

            // 正式部署
            // clientUrl:"/alumniweb/web/",
            // serverUrl:"",

            ready:function(callback){
                var that = this;

                xdomain.slaves({
                    "http://localhost:8080":that.proxy
                });

                if($.isFunction(callback)){
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
            // 将内容中的路径从相对路径转换成绝对路径
            /**
             * if(data && data.obj && data.obj.description) {
                            data.obj.description = B.changeSrcFromRelativeToAbsolute(data.obj.description);
                        }
             */
            changeSrcFromRelativeToAbsolute:function(content){
                if (content) {
                    content = content.replace(/src=&quot;\//g, "src=&quot;" + imageServiceHttp);
                    content = content.replace(/src=\"\//g, "src=\"" + imageServiceHttp)
                }
                return content ;
            },
            // 获取指定位数的随机正整数
            getRandomInt:function (num) {
                if (!isNaN(num) && num > 0){

                }
                else {
                    num = 4;
                }
                return Math.floor(Math.random()*Math.pow(10,num))
            }

        });
    }
}(function (BExports) {
    var B = typeof BExports !== 'undefined' ? BExports : {};
});