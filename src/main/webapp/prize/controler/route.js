

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

var myApp=angular.module("myApp", ['ionic'],httpPost);

myApp.config(function($ionicConfigProvider) {
    $ionicConfigProvider.views.maxCache(5);

    // note that you can also chain configs
    $ionicConfigProvider.backButton.text('Go Back').icon('ion-chevron-left');
});

myApp.config(['$stateProvider','$ionicConfigProvider', '$urlRouterProvider', function($stateProvider, $ionicConfigProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/main1');
    $stateProvider

        //主页1
        .state('main1', {
            url: '/main1',
            templateUrl: "view/main1.html",
            controller: 'main1ViewCtrls'
        })
        //主页2
        .state('main2', {
            url: '/main2',
            templateUrl: "view/main2.html",
            controller: 'main2ViewCtrls'
        })
        //主页3
        .state('main3', {
            url: '/main3',
            templateUrl: "view/main3.html",
            controller: 'main3ViewCtrls'
        })
        //主页4
        .state('main4', {
            url: '/main4',
            templateUrl: "view/main4.html",
            controller: 'main4ViewCtrls'
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
        }else{
            return '../img/1.jpg';
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

//手机号中间四位为****
myApp.filter("phonenum", function(){
    return function(text, length){
        if (text) {
            return text.substring(0,3)+'****'+text.substring(7,11);
        };
        return text;
    }
});
//滤镜： 处理富文本
myApp.filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}]);