

var httpPost = function($httpProvider) {
    /*******************************************
     说明：$http的post提交时，纠正消息体
     ********************************************/
    // Use x-www-form-urlencoded Content-Type
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    /*
     * The workhorse; converts an object to x-www-form-urlencoded serialization.
     * @param {Object} obj
     * @return {String}
     */
    var param = function(obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
        for (name in obj) {
            value = obj[name];
            if (value instanceof Array) {
                for (i = 0; i < value.length; ++i) {
                    subValue = value[i];
                    fullSubName = name + '[' + i + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value instanceof Object) {
                for (subName in value) {
                    subValue = value[subName];
                    fullSubName = name + '[' + subName + ']';
                    innerObj = {};
                    innerObj[fullSubName] = subValue;
                    query += param(innerObj) + '&';
                }
            } else if (value !== undefined && value !== null)
                query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
        return query.length ? query.substr(0, query.length - 1) : query;
    };
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [
        function(data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
        }
    ];
};
//定义主模块并注入依赖

var myApp=angular.module("myApp", ['ionic','ionic-citypicker'],httpPost);

myApp.config(['$stateProvider','$ionicConfigProvider', '$urlRouterProvider', function($stateProvider, $ionicConfigProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/tabindex');
    $stateProvider

        //招聘主页面
        .state('tabindex', {
            url: '/tabindex',
            templateUrl: "view/main/tabindex.html",
            controller: 'tabindexViewCtrls'
        })

        //招聘主页面
        .state('tabindex.index', {
            url: '/index',
            templateUrl: "view/main/main.html",
            controller: 'mainViewCtrls'
        })

        //招聘主页面 1
        .state('tabindex.index.main1', {
            url: '/main1',
            templateUrl: "view/main/main_list/main1.html",
            controller: 'main1ViewCtrls'
        })
        //招聘主页面 2
        .state('tabindex.index.main2', {
            url: '/main2',
            templateUrl: "view/main/main_list/main2.html",
            controller: 'main2ViewCtrls'
        })
        //招聘主页面 3
        .state('tabindex.index.main3', {
            url: '/main3',
            templateUrl: "view/main/main_list/main3.html",
            controller: 'main3ViewCtrls'
        })

        //职位收藏
        .state('zhiwei', {
            url: '/zhiwei',
            templateUrl: "view/shoucang/zhiwei.html",
            controller: 'zhiweiViewCtrls'
        })

        //职位详情
        .state('zhiweicenter', {
            url: '/zhiweicenter',
            templateUrl: "view/zhiweicenter/zhiweicenter.html",
            controller: 'zhiweiCenterViewCtrls'
        })

        //加入我们
        .state('joinwe', {
            url: '/joinwe',
            templateUrl: "view/joinwe/joinwe.html",
            controller: 'joinweViewCtrls'
        })
        //企业详情
        .state('qiyecenter', {
            url: '/qiyecenter',
            templateUrl: "view/qiyecenter/qiyecenter.html",
            controller: 'qiyecenterViewCtrls'
        })



        //简历状态
        .state('jianlizhuangtai', {
            url: '/jianlizhuangtai',
            templateUrl: "view/jianlizhuangtai/jianlizhuangtai.html",
            controller: 'jianlizhuangtaiViewCtrls'
        })
        //简历状态页面
        .state('jianlizhuangtai.jianli', {
            url: '/jianli/:id',
            templateUrl: "view/jianlizhuangtai/jianli.html",
            controller: 'jianliViewCtrls'
        })

}]);




//滤镜： 判断图片是否有http或者https
myApp.filter('filter_pic', function () {
    return function (text) {
        if(text && text!=''){
            if(text.indexOf("http:") == 0 || text.indexOf("https:") == 0 ){
                return text;
            }else{
                // text = B.serverUrl+text;
                text = B.imageServiceHttp+text;
                return text;
            }
        }
    };
});

//截断超长字符串的过滤器
myApp.filter("truncate", function(){
    return function(text, length){
        if (text) {
            var ellipsis = text.length > length ? "..." : "";
            return text.slice(0, length) + ellipsis;
        };
        return text;
    }
});
